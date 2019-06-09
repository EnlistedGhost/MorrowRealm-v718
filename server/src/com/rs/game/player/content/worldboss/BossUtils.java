package com.rs.game.player.content.worldboss;

import java.util.Map;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.rs.Settings;
import com.rs.cores.CoresManager;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.content.worldboss.npcs.HarAkenBoss;
import com.rs.game.player.content.worldboss.npcs.Tentacles;
import com.rs.utils.Utils;

public class BossUtils extends BossArena {

	private static NPC boss;
	private static NPC[] tentacles;
	
	static Map<Integer, Player> inRaid = new ConcurrentHashMap<Integer, Player>();
	
	private static boolean started;
	public static void startLobbyCheck() {
		if (started) {
			return;
		}
		CoresManager.fastExecutor.scheduleAtFixedRate(new TimerTask() {
			int timer;
			@Override
			public void run() {
				
				if (!started) {
					started = true;
					spawnNpcs();
				}
				
				if (boss.isDead() || boss.hasFinished() || boss == null) {
					if (timer < 60) {
						sendTimer(timer);
						timer++;
					} else {
						destroy();
						System.out.println("Event Destroyed 1");
						this.cancel();
						return;
					}
				}
				
				if (started) {
					if (inRaid.isEmpty()) {
						destroy();
						System.out.println("Event Destroyed 2");
						this.cancel();
						return;
					}
					updateAll();
				}
			}
		}, 2000, 1200);
	}
	
	public static void spawnNpcs() {
		if (boss == null) {
			boss = new HarAkenBoss(15212, new WorldTile(3305, 3689, 0));
			tentacles = new NPC[3];
			tentacles[0] = new Tentacles(15210, new WorldTile(3299, 3695, 0), (HarAkenBoss) boss);
			tentacles[1] = new Tentacles(15209, new WorldTile(3314, 3698, 0), (HarAkenBoss) boss);
			tentacles[2] = new Tentacles(15209, new WorldTile(3312, 3682, 0), (HarAkenBoss) boss);
		}
	}
	
	public static void sendTimer(int timer) {
		for (Entry<Integer, Player> e : inRaid.entrySet()) {
			Player player = e.getValue();
			if (player == null)
				continue;
			player.getPackets().sendIComponentText(1390, 8, "Ending Raid in:");
			player.getPackets().sendIComponentText(1390, 9, ""+(60 - timer)+" sec.");
		}
	}
	
	public static void destroy() {
		removeAll();
		killAll();
		started = false;
	}
	
	public static void updateAll() {
		for (Entry<Integer, Player> e : inRaid.entrySet()) {
			Player player = e.getValue();
			if (player == null)
				continue;
			if (!inCombatArea(player)) {
				removeFromBattle(player);
				player.sendMessage("You've been removed from the raid!");
			}
			player.getPackets().sendIComponentText(1390, 4, "Players In Raid:");
			player.getPackets().sendIComponentText(1390, 5, ""+inRaid.size()+"");
			player.getPackets().sendIComponentText(1390, 6, "Boss Health:");
			player.getPackets().sendIComponentText(1390, 7, ""+Utils.formatNumber(boss.getHitpoints())+"");
		}
	}
	
	public static boolean inCombatArea(Player player) {
		return player.getX() >= 3287 && player.getX() <= 3326 && player.getY() >= 3668 && player.getY() <= 3704;
	}
	
	public static void removeAll() {
		for (Entry<Integer, Player> e : inRaid.entrySet()) {
			Player p = e.getValue();
			if (p == null)
				continue;
			removeFromBattle(p);
			p.teleport(Settings.START_PLAYER_LOCATION);
			p.getInterfaceManager().closeOverlay(p.getInterfaceManager().hasRezizableScreen());
		}
	}
	
	public static void killAll() {
		if (boss != null && !boss.isDead())
			boss.sendDeath(null);
		for (int i = 0; i < 3; i++) {
			if (tentacles[i] != null && !tentacles[i].isDead()) {
				tentacles[i].sendDeath(null);
			}
		}
		boss = null;
		tentacles = null;
	}
	
	public static void removeFromBattle(Player player) {
		player.getControlerManager().removeControlerWithoutCheck();
		inRaid.remove(player.getIndex());
		player.getInterfaceManager().closeOverlay(player.getInterfaceManager().hasRezizableScreen());
	}
	
}
