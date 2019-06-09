package com.rs.game.minigames.zombies;

import java.util.concurrent.ConcurrentHashMap;

import com.rs.Settings;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.content.Pots;
import com.rs.game.player.controlers.Controler;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class WiseOldMan extends Controler {

	protected static ConcurrentHashMap<Integer, Player> players = new ConcurrentHashMap<Integer, Player>();
	protected static ConcurrentHashMap<Integer, NPC> npcList = new ConcurrentHashMap<Integer, NPC>();
	
	protected static boolean endAll = false;// kills all the events
	protected static WorldTile center = new WorldTile(2922, 3607, 0); // center of the arena
	protected static int ANIM_ID = 1979;// wise man anim Id
	protected static NPC wiseMan;
	protected static int START_DELAY = 15000; // 15 seconds
	protected static int SPAWN_DELAY = 6000; // 15 seconds
	protected static int DISEASE_DAMAGE = 10;
	protected static int DISEASE_DELAY = 2000; // 1.5 seconds
	protected static int wave = 0;
	protected static int npcId = 8164;
	protected static int BANDAGES = 4049;
	
	protected static int DEATH_RUNE = 560;
	protected static int BLOOD_RUNE = 565;
	protected static int WATER_RUNE = 555;
	protected static int RUNE_AMOUNT = 50;

	protected static int POT_1 = 15308; //extreme atk pot
	protected static int POT_2 = 15312; //extreme str flask
	protected static int POTS_AMOUNT = 1;
	
	protected static boolean shakeStarted;
	
	@Override
	public void start() {
		player.getPrayer().resetStatAdjustments();
		player.sendMessage("<col=0000FF>Welcome to Zombies Onslaught! Zombies will begin spawning every 20 seconds");
		player.sendMessage("<col=0000FF>either until everyone's dead or has left the instance.");
		player.teleport(new WorldTile(2923, 3598, 0));
		players.put(player.getIndex(), player);
		Zombies.send("<col=008F00>"+player.getDisplayName()+" has joined Zombie Onslaught!");
		
		if (wiseMan == null) {
			wiseMan = new NPC(2253, center, -1, true, true);
			ZombieEvent.startInvasion();
		} else {
			wiseMan.setNextForceTalk(new ForceTalk(Zombies.getJoinMessage(player)));
		}
		
		Pots.resetBoosts(player);
		player.getPrayer().drainPrayer(player.getPrayer().getPrayerpoints());
	}
	
	@Override
	public boolean processMagicTeleport(WorldTile tile) {
		player.sendMessage("You are not allowed to teleport out of here. Only way out is death!");
		return false;
	}
	
	@Override
	public boolean logout() {
		Zombies.removePlayer(player);
		Zombies.send(player.getDisplayName()+" has abandoned Zombie Onslaught!");
		player.setLocation(new WorldTile(Settings.START_PLAYER_LOCATION));
		return true;
	}
	
	@Override
	public boolean sendDeath() {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.setNextAnimation(new Animation(836));
				} else if (loop == 1) {
					World.sendWorldMessage("<col=FF3A00>[News] "+player.getDisplayName()+" has failed zombies after defeating "+player.getZombieKillstreak()+" Zombies!", false);
					player.setZombieKillstreak(0);
					player.getPackets().sendResetCamera();
				} else if (loop == 3) {
					player.getEquipment().init();
					player.getInventory().init();
					player.reset();
					player.setNextWorldTile(Settings.START_PLAYER_LOCATION);
					player.setNextAnimation(new Animation(-1));
				} else if (loop == 4) {
					Zombies.removePlayer(player);
					player.getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		}, 0, 1);
		return true;
	}
	
	public static boolean sendSound = false;
	
	@Override
	public void process() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
