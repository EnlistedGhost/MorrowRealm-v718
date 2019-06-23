package com.rs.game.player.controlers;


import java.util.concurrent.TimeUnit;







import com.rs.Settings;
import com.rs.cores.CoresManager;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
//import com.rs.game.item.Item;
import com.rs.game.player.Skills;
import com.rs.game.player.content.FadingScreen;
import com.rs.game.player.controlers.Controler;
import com.rs.utils.Utils.EntityDirection;

/**
 * 
 * @author Lonely
 *
 */

public class SantaCage1Controler extends Controler {
	
	private int[] boundChunks;
	
	static int sizeX = 6; // horizontal size
	static int sizeY = 7; // vertical size
	
	static int chunkX = 321; // bottom left chunk x
	static int chunkY = 696; // bottom left chunk y
	
	@Override
	public void start() {
		player.lock();
		final long time = FadingScreen.fade(player);
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				boundChunks = RegionBuilder.findEmptyChunkBound(sizeX, sizeY); 
				RegionBuilder.copyAllPlanesMap(321, 696, boundChunks[0], boundChunks[1], sizeX, sizeY);
				FadingScreen.unfade(player, time, new Runnable() {
					@Override
					public void run() {
						int maxPrayer = player.getSkills().getLevelForXp(Skills.PRAYER) * 10;
						player.setForceMultiArea(true);
						player.getPrayer().restorePrayer(maxPrayer);
						player.heal(player.getHitpoints() * 10);
						player.setNextWorldTile(getWorldTile(24, 8));
						player.getCombatDefinitions().resetSpells(true);
						player.stopAll();
						player.unlock();
						//World.addGroundItem(new Item(1050, 1), new WorldTile(getWorldTile(24, 9)));
						/**
						 * Npcs
						 */
						World.spawnNPC(8517, new WorldTile(getWorldTile(30, 23)), -1, false, EntityDirection.NORTH); // Jack Frost
						World.spawnNPC(6743, new WorldTile(getWorldTile(30, 25)), -1, false, EntityDirection.SOUTH); // Dragon Snowman
						World.spawnNPC(6747, new WorldTile(getWorldTile(19, 13)), -1, false, EntityDirection.SOUTH); // 1
						World.spawnNPC(6748, new WorldTile(getWorldTile(17, 14)), -1, false, EntityDirection.NORTH); // 2
						World.spawnNPC(6749, new WorldTile(getWorldTile(20, 15)), -1, false, EntityDirection.SOUTH); // 3
						World.spawnNPC(6747, new WorldTile(getWorldTile(11, 16)), -1, false, EntityDirection.SOUTH); // 1
						World.spawnNPC(6748, new WorldTile(getWorldTile(9, 19)), -1, false, EntityDirection.NORTH); // 2
						World.spawnNPC(6749, new WorldTile(getWorldTile(12, 19)), -1, false, EntityDirection.SOUTH); // 3
						World.spawnNPC(6747, new WorldTile(getWorldTile(8, 32)), -1, false, EntityDirection.SOUTH); // 1
						World.spawnNPC(6748, new WorldTile(getWorldTile(11, 34)), -1, false, EntityDirection.NORTH); // 2
						World.spawnNPC(6749, new WorldTile(getWorldTile(11, 31)), -1, false, EntityDirection.SOUTH); // 3
						World.spawnNPC(6747, new WorldTile(getWorldTile(10, 41)), -1, false, EntityDirection.SOUTH); // 1
						World.spawnNPC(6748, new WorldTile(getWorldTile(11, 45)), -1, false, EntityDirection.NORTH); // 2
						World.spawnNPC(6749, new WorldTile(getWorldTile(14, 44)), -1, false, EntityDirection.SOUTH); // 3
						World.spawnNPC(6747, new WorldTile(getWorldTile(36, 45)), -1, false, EntityDirection.SOUTH); // 1
						World.spawnNPC(6748, new WorldTile(getWorldTile(40, 43)), -1, false, EntityDirection.NORTH); // 2
						World.spawnNPC(6749, new WorldTile(getWorldTile(37, 41)), -1, false, EntityDirection.SOUTH); // 3
						World.spawnNPC(6747, new WorldTile(getWorldTile(38, 28)), -1, false, EntityDirection.SOUTH); // 1
						World.spawnNPC(6748, new WorldTile(getWorldTile(41, 28)), -1, false, EntityDirection.NORTH); // 2
						World.spawnNPC(6749, new WorldTile(getWorldTile(39, 24)), -1, false, EntityDirection.SOUTH); // 3
						World.spawnNPC(6747, new WorldTile(getWorldTile(35, 17)), -1, false, EntityDirection.SOUTH); // 1
						World.spawnNPC(6748, new WorldTile(getWorldTile(34, 14)), -1, false, EntityDirection.NORTH); // 2
						World.spawnNPC(6749, new WorldTile(getWorldTile(31, 16)), -1, false, EntityDirection.SOUTH); // 3
						/**
						 * Objects
						 */
						World.spawnObject(new WorldObject(47857, 10, 0, new WorldTile(getWorldTile(24, 23))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(29, 26))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(21, 26))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(21, 25))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(21, 24))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(21, 23))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(21, 22))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(21, 21))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(22, 20))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(27, 20))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(28, 21))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(28, 22))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(28, 23))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(28, 24))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(28, 25))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(28, 26))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(28, 28))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(28, 29))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(28, 30))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(27, 31))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(26, 31))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(23, 31))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(22, 31))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(21, 28))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(21, 29))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(21, 30))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(22, 8))), true);//Walk
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(22, 9))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(22, 10))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(22, 11))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(23, 12))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(24, 13))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(24, 14))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(24, 15))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(23, 16))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(22, 17))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(22, 18))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(27, 8))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(27, 9))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(28, 10))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(29, 11))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(29, 12))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(29, 13))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(29, 14))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(29, 15))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(29, 16))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(28, 17))), true);
						World.spawnObject(new WorldObject(555, 10, 0, new WorldTile(getWorldTile(27, 18))), true);
						}
					
				
				});
			}
		}, 3000, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		endDungeon(false);
		removeControler();
		return false;
	}


	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		endDungeon(false);
		removeControler();
		return false;
	}


	@Override
	public boolean processObjectTeleport(WorldTile toTile) {
		endDungeon(false);
		removeControler();
		return false;
	}
	
	@Override
	public boolean logout() {
		endDungeon(true);
		return true;
	}
	
	private void removeMapChunks() {
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				RegionBuilder.destroyMap(boundChunks[0], boundChunks[1], sizeX, sizeY);
			}
		}, 1200, TimeUnit.MILLISECONDS);
	}
	
	public void endDungeon(boolean logout) {
		player.setForceMultiArea(false);
		if (!logout)
			player.setNextWorldTile(Settings.START_PLAYER_LOCATION);
		else
			player.setLocation(Settings.START_PLAYER_LOCATION);
		removeMapChunks();
	}
	
	public WorldTile getWorldTile(int mapX, int mapY) {
		return new WorldTile(boundChunks[0] * 8 + mapX, boundChunks[1] * 8 + mapY, 0);
	}
	
}