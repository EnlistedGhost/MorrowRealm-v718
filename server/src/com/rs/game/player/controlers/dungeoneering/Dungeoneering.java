package com.rs.game.player.controlers.dungeoneering;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import com.rs.cores.CoresManager;
import com.rs.game.Entity;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.npc.others.RuneDungMonsters;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.FadingScreen;
import com.rs.utils.Logger;
import com.rs.utils.Misc;

/**
 * 
 * @author Tyluur <itstyluur@gmail.com>
 * @since 2013-01-02
 * Modified by Tylerr
 */
public class Dungeoneering implements Serializable {

	private static final long serialVersionUID = 1110146966680563250L;

	private CopyOnWriteArrayList<Player> team; 

	private int gameInterface = 256;
	private long startTime;

	private ArrayList<WorldTile> randomNormals = new ArrayList<WorldTile>();
	private ArrayList<WorldTile> randomBosses = new ArrayList<WorldTile>();

	private Map<Integer, RuneDungMonsters> monsters = new HashMap<Integer, RuneDungMonsters>();

	private Floors floor;

	private int normalR;
	private int bossR;

	private boolean loadedGame = false;

	/**
	 * An array of values, used for the empty Dynamic region.
	 */
	private int[] boundChunks;

	public Dungeoneering(final CopyOnWriteArrayList<Player> team, int[] boundChunks) {
		this.team = team;		
		this.boundChunks = boundChunks;
		this.startTime = Misc.currentTimeMillis();
		startGame();
	}
	
	public void fade(final Player player) {
		final long time = FadingScreen.fade(player);
		CoresManager.slowExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					FadingScreen.unfade(player, time, new Runnable() {
						@Override
						public void run() {

						}
					});
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}

		});
	}

	private void startGame() {;
		if (!loadedGame) {
			int lvl = 0;
			for (Player player : team) {
				fade(player);
				lvl += player.getSkills().getLevel(Skills.DUNGEONEERING);
			}
			setFloor(getFloorByLevel(lvl));
			CoresManager.slowExecutor.schedule(new Runnable() {
				@Override
				public void run() {
					if (getFloor() == Floors.FIRST)
						RegionBuilder.copyAllPlanesMap(14, 554, boundChunks[0], boundChunks[1], 20);
					else
						RegionBuilder.copyAllPlanesMap(10, 688, boundChunks[0], boundChunks[1], 20);
					CoresManager.slowExecutor.schedule(new Runnable() {

						@Override
						public void run() {
							for (Player player : team) {
								int maxPrayer = player.getSkills().getLevelForXp(Skills.PRAYER) * 10;
								player.getPrayer().restorePrayer(maxPrayer);
								player.heal(player.getHitpoints() * 10);
								player.setNextWorldTile(getWorldTile(10, 8));
								player.getCombatDefinitions().resetSpells(true);
								player.stopAll();
								player.sm("<col=FF0000>You step into the misty dungeon and prepare for battle on the " + getFloor().name().toLowerCase() + " floor.");
							}
							loadObjects();
							loadMonsters();
						}
					}, 0, TimeUnit.MILLISECONDS);
				}
			}, 0, TimeUnit.MILLISECONDS);
			loadedGame = true;
		}
	}

	private Floors getFloorByLevel(int level) {
		if (level < 49)
			return Floors.FIRST;
		else
			return Floors.SECOND;
	}
	
	private void loadObjects() {
		Iterator<Portals> it = Portals.getMap().values().iterator();
		while(it.hasNext()) {
			Portals portal = it.next();
			World.spawnObject(new WorldObject(2273, 10, 0, getWorldTile(portal.spawnTile().getX(), portal.spawnTile().getY())), true);
		}
	}

	private void loadTileList() {
		if (getFloor() == Floors.FIRST) {
			WorldTile[] tiles = new WorldTile[] { getWorldTile(24, 25), getWorldTile(24, 7), getWorldTile(40, 7), getWorldTile(8, 23), getWorldTile(25, 7), getWorldTile(23, 22), getWorldTile(20, 21), getWorldTile(20, 29) };
			for (WorldTile tile : tiles)
				randomNormals.add(tile);
			WorldTile[] bossTiles = new WorldTile[] { getWorldTile(40, 20), getWorldTile(40, 21), getWorldTile(40, 22), getWorldTile(40, 23) };
			for (WorldTile tile : bossTiles)
				randomBosses.add(tile);
		} else if (getFloor() == Floors.SECOND) {
			WorldTile[] tiles = new WorldTile[] { getWorldTile(23, 8),
					getWorldTile(23, 24), getWorldTile(23, 28),
					getWorldTile(39, 24) };
			for (WorldTile tile : tiles)
				randomNormals.add(tile);
			WorldTile[] bossTiles = new WorldTile[] { getWorldTile(25, 55),
					getWorldTile(35, 55), 
					getWorldTile(39, 39),
					getWorldTile(39, 10) };
			for (WorldTile tile : bossTiles)
				randomBosses.add(tile);
		}
	}

	private void loadMonsters() {
		new RuneDungMonsters(11226, getWorldTile(7, 5), MonsterTypes.NORMAL);
		loadTileList();
		Iterator<Monsters> it = Monsters.getMap().values().iterator();
		while(it.hasNext()) {
			Monsters monster = it.next();
			if (monster.getFloor() == getFloor()) {
				WorldTile spawnLoc = monster.getTypes() == MonsterTypes.NORMAL ? getRandomNormalSpawn() : getRandomBossSpawn();
				if (monster.getTypes() == MonsterTypes.NORMAL)
					randomNormals.remove(normalR);
				else
					randomBosses.remove(bossR);
				monsters.put(monster.getId(), new RuneDungMonsters(monster.getId(), spawnLoc, monster.getTypes()));
			}
		}
	}

	private WorldTile getRandomBossSpawn(){
		bossR = Misc.random(randomBosses.size());
		return randomBosses.get(bossR);
	}

	private WorldTile getRandomNormalSpawn() {
		normalR = Misc.random(randomNormals.size());
		return randomNormals.get(normalR);
	}

	public void removeMonster(int npc) {
		synchronized (monsters) {
			monsters.remove(npc);
			sendInfo();
			for (Player player : team) 
			player.getSkills().addXp(Skills.DUNGEONEERING, 18);
			if (getFloor() == Floors.SECOND) {
				for (Player player : team) {
				player.getSkills().addXp(Skills.DUNGEONEERING, 2000);
				//player.setDungTokens(player.getDungTokens() + 200);
			}
			}
		}
			if (monsters.size() == 0) {
				for (Player player : team) {
					player.getDialogueManager().startDialogue("SimpleMessage", "You have completed the dungeon, speak with the starting NPC.");
					NPC guide = findNPC(11226); 
					if (guide != null)
						player.getHintIconsManager().addHintIcon(guide, 0, -1, false);						
				}
			}
		}
	
	public NPC findNPC(int id) {
		for (NPC npc : World.getNPCs()) {
			if (npc == null || npc.getId() != id)
				continue;
			return npc;
		}
		return null;
	}

	public void sendInfo() {
		for (Player player : team) {
			for (int i = 0; i < 16; i++) {
				player.getPackets().sendIComponentText(gameInterface, i, "");
			}
			player.getPackets().sendIComponentText(gameInterface, 5, "Dungeoneering Information");
			player.getPackets().sendIComponentText(gameInterface, 11, "Damage Inflicted:");
			player.getPackets().sendIComponentText(gameInterface, 12, "Monsters Left:");
			player.getPackets().sendIComponentText(gameInterface, 13, "Players:");
			player.getPackets().sendIComponentText(gameInterface, 14, "Floor:");
			if (player.getControlerManager().getControler() instanceof RuneDungGame)
				player.getPackets().sendIComponentText(gameInterface, 6, "                     " + ((RuneDungGame) player.getControlerManager().getControler()).getDamage());
			player.getPackets().sendIComponentText(gameInterface, 7, "                     "  + getMonsterSize());
			player.getPackets().sendIComponentText(gameInterface, 8, "                     " + getTeam().size());
			player.getPackets().sendIComponentText(gameInterface, 9, "                     " + (getFloor() == null ? -1 : getFloor().ordinal() + 1));
			player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 11 : 27, gameInterface);
		}
	}

	public void finishGame(Player player) {
		if (((RuneDungGame) player.getControlerManager().getControler()).isGaveReward())
			return;
		if (team.size() == 0)
			removeMapChunks();
		((RuneDungGame) player.getControlerManager().getControler()).setGaveReward(true);
		giveReward(player);
		player.unlock();
		player.getAppearence().generateAppearenceData();
	}

	public void giveReward(Player player) {
		int interfaceId = 921;
		int damage = ((RuneDungGame) player.getControlerManager().getControler()).getDamage();
		long time = (TimeUnit.MILLISECONDS.toSeconds(Misc.currentTimeMillis() - startTime));
		int tokens = (int) (time / 5) + damage / 100 * 6;
		for (int i = 0; i < 45; i++) {
			player.getPackets().sendIComponentText(interfaceId, i, "");
		}
		player.getPackets().sendIComponentText(interfaceId, 44, "Dungeoneering Rewards");
		player.getPackets().sendIComponentText(interfaceId, 11, "Partners");
		player.getPackets().sendIComponentText(interfaceId, 17, "Partner No. 1:");
		player.getPackets().sendIComponentText(interfaceId, 12, "Partner No. 2:");
		player.getPackets().sendIComponentText(interfaceId, 13, "Partner No. 3:");
		if (team.size() > 0)
			player.getPackets().sendIComponentText(interfaceId, 14, team.get(0).getDisplayName());
		if (team.size() >= 2)
			player.getPackets().sendIComponentText(interfaceId, 15, team.get(1).getDisplayName());
		if (team.size() >= 3)
			player.getPackets().sendIComponentText(interfaceId, 16, team.get(2).getDisplayName());
		player.getPackets().sendIComponentText(interfaceId, 24, "Details");
		player.getPackets().sendIComponentText(interfaceId, 25, "Floor Completed:");
		player.getPackets().sendIComponentText(interfaceId, 26, "Total Damage:");
		player.getPackets().sendIComponentText(interfaceId, 27, "Time Spent:");
		player.getPackets().sendIComponentText(interfaceId, 28, "EXP/Tokens:");
		int totalDmg = 0;
		for (Player t : team) {
			synchronized (t) {
				totalDmg += ((RuneDungGame) t.getControlerManager().getControler()).getDamage();
			}
		}
		player.getPackets().sendIComponentText(interfaceId, 29, "" + (getFloor().ordinal()) + 1);
		player.getPackets().sendIComponentText(interfaceId, 30, "" + totalDmg);
		player.getPackets().sendIComponentText(interfaceId, 31, "" + time + " sec.");
		player.getPackets().sendIComponentText(interfaceId, 32, "" + (damage / 100 * 2 * 25) + "/" + tokens);
		player.getInterfaceManager().sendInterface(interfaceId);
		player.sm("You have received " + (damage / 10 * 2 * 25) + " dungeoneering experience, and " + tokens + " tokens.");
		int lvl = player.getSkills().getLevel(Skills.DUNGEONEERING);
		player.getSkills().addXp(Skills.DUNGEONEERING, getExperienceToAdd(lvl, damage));
		player.setDungPoints(player.getDungPoints() + tokens);
		int maxPrayer = player.getSkills().getLevelForXp(Skills.PRAYER) * 10;
		player.getPrayer().restorePrayer(maxPrayer);
		player.heal(player.getHitpoints() * 10);
	}

	
	private double getExperienceToAdd(int lvl, int damage) {
		double doubler = lvl < 30 ? 1 : lvl > 30 && lvl < 50 ? 1.5 : lvl > 50 && lvl < 60 ? 3 : lvl > 60 && lvl < 70 ? 3.5 : lvl > 70 && lvl < 90 ? 6 : lvl > 90 && lvl < 100 ? 7.5 : lvl > 100 ? 15 : 4;
		return (damage / 10) * doubler;
	}

	public void removeFromGame(Player player, boolean logout) {
		player.getCombatDefinitions().setSpellBook(0);
		player.getCombatDefinitions().setDungSpellBook(false);
		player.getPackets().closeInterface(player.getInterfaceManager().hasRezizableScreen() ? 746 : 548, player.getInterfaceManager().hasRezizableScreen() ? 11: 27);
		finishGame(player);
		if (!logout)
			player.setNextWorldTile(new WorldTile(3450, 3718, 0));
		else
			player.setLocation(new WorldTile(3450, 3718, 0));
		team.remove(player);
	}

	private void removeMapChunks() {
		Iterator<RuneDungMonsters> monsterIterator = monsters.values().iterator();
		synchronized (monsters) {
			while (monsterIterator.hasNext()) {
				RuneDungMonsters npc = monsterIterator.next();
				npc.setFinished(true);
				World.updateEntityRegion(npc);
				monsterIterator.remove();
			}
		}
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				RegionBuilder.destroyMap(boundChunks[0], boundChunks[1], 8, 8);
			}
		}, 1200, TimeUnit.MILLISECONDS);
	}

	/**
	 * Retrieves a new {@code WorldTile} using the boundChunks of the dynamic region.
	 * @param mapX The 'x' coordinate value.
	 * @param mapY The 'y' coordinate value.
	 * @return a new {@code WorldTile}
	 */
	public WorldTile getWorldTile(int mapX, int mapY) {
		return new WorldTile(boundChunks[0] * 8 + mapX, boundChunks[1] * 8 + mapY, 0);
	}

	public CopyOnWriteArrayList<Player> getTeam() {
		return team;
	}

	public Map<Integer, RuneDungMonsters> getMonsters() {
		return monsters;
	}

	public int getMonsterSize() {
		return monsters.size();
	}

	public ArrayList<WorldTile> getNormalsList() {
		return randomNormals;
	}

	public int getRandom() {
		return normalR;
	}

	public Floors getFloor() {
		return floor;
	}

	public void setFloor(Floors floor) {
		this.floor = floor;
	}

	public int[] getBoundChunks() {
		return boundChunks;
	}
	
	public boolean logout() {
		return false;
	}

	public void sendDrop(WorldTile worldTile, MonsterTypes type, Entity owner) {
		int[] rares = { 11848, 20833, 16425, 16689, 17259, 18349, 14484, 18353, 14484, 23679, 20135, 20139, 20143, 23695};
		int[] basics = { 11928, 11924, 11926, 19784, 13736, 13738, 13742, 13744, 13887, 13893, 13899, 13884, 13890, 13896, 13902, 4151, 4151, 20147, 20151, 20155, 23679, 11700, 17239, 17607, 17341, 16889, 16935, 17019, 17063,
				16647, 16669, 16691, 16383, 16361, 16405, 16274, 16273, 15753,
				15761, 16281, 16303, 16347, 16369, 16391, 16413, 16447, 16655,
				16656, 16677, 16699, 16721, 16897, 16943, 16957, 16961, 16973,
				17027, 17095, 17247, 17349, 20872, 15765, 16285, 16307, 16351,
				16373, 16395, 16417, 16457, 16659, 16681, 16703, 16725, 16805,
				16901, 16947, 17031, 17111, 17251, 17353 };
		int count;
		switch(type){
		case BOSSES:
			count = 0;
			for(int i = 0; i < 2; i++) {
				if (++count > 2) break;
				// i modified these 
				World.addGroundItem(new Item(rares[Misc.random(rares.length)]), worldTile, (Player) owner, false, 60, false);
			}
			break;
		case NORMAL:
			count = 0;
			for(int i = 0; i < 2; i++) {
				if (++count > 2) break;
				Item item = new Item(basics[Misc.random(basics.length)]);
				if (item.getDefinitions().isNoted())
					item.setId(item.getDefinitions().getCertId());
				World.addGroundItem(item, worldTile, (Player) owner, false, 60, false);
			}
			break;
		}
	}


}
