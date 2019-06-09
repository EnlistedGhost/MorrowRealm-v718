package com.rs.game.minigames.zombies;

import java.util.Map.Entry;

import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;

public class Zombies extends WiseOldMan {
	
	public static void clearInventory(Player player) {
		player.getInventory().deleteItem(BANDAGES, 28);
		player.getInventory().deleteItem(BLOOD_RUNE, Integer.MAX_VALUE);
		player.getInventory().deleteItem(DEATH_RUNE, Integer.MAX_VALUE);
		player.getInventory().deleteItem(WATER_RUNE, Integer.MAX_VALUE);
		player.getInventory().deleteItem(POT_1, 28);
		player.getInventory().deleteItem(POT_2, 28);
	}
	
	public static boolean killAllEvents() {
		for (Entry<Integer, NPC> npcs : npcList.entrySet()) {
			if (npcs.getValue() == null)
				continue;
			npcs.getValue().reset();
			npcs.getValue().finish();
		}
		wave = 0;
		wiseMan.reset();
		wiseMan.finish();
		wiseMan = null;
		sendResetCamera();
		players.clear();
		npcList.clear();
		shakeStarted = false;
		return true;
	}
	
	public static void applyReward(Player player, int waveId) {
		player.setZombieKillstreak(player.getZombieKillstreak() + 1);
		int killStreak = player.getZombieKillstreak();
		
		int points = player.getZombieKillstreak() < 10 ? 1 :
			player.getZombieKillstreak() < 20 ? 2 :
			player.getZombieKillstreak() < 30 ? 3 :
			player.getZombieKillstreak() < 40 ? 4 : 5;
		
		player.setOsp(player.getOsp() + points);
		
		npcList.remove(waveId);
		
		if (killStreak == 10) {
			if (!player.getInventory().hasFreeSlots()) {
				player.getInventory().deleteItem(BANDAGES, 2);
			}
			player.getInventory().add(POT_1, POTS_AMOUNT);
			player.getInventory().add(POT_2, POTS_AMOUNT);
			player.sendMessage("<col=FF0000>You've earned some flasks!");
		}
		
		if (killStreak == 15) {
			player.getPrayer().restorePrayer(100);
			player.sendMessage("<col=FF0000>You've earned 100 prayer points!");
		}
		
		if (killStreak == 25 || killStreak == 50 || killStreak == 75 || killStreak == 100) {
			if (!player.getInventory().hasFreeSlots()) {
				player.getInventory().deleteItem(BANDAGES, 3);
			}
			player.getInventory().addItem(DEATH_RUNE, RUNE_AMOUNT);
			player.getInventory().addItem(BLOOD_RUNE, RUNE_AMOUNT);
			player.getInventory().addItem(WATER_RUNE, RUNE_AMOUNT);
			player.sendMessage("<col=FF0000>You've earned some barrage runes!");
		}
		
		if (player.getZombieKillstreak() > player.getMaxZombieWave()) {
			player.setMaxZombieWave(player.getZombieKillstreak());
		}
		
		player.sendMessage("You've been awarded with "+points+" OSP. You now have "+player.getOsp()+" OSP.");
		player.sendMessage("You're on a "+player.getZombieKillstreak()+" Killstreak. Your max is "+player.getMaxZombieWave()+"");
		player.lastOnslaughtKill = Utils.currentTimeMillis();
	}
	
	public static void removePlayer(Player player) {
		players.remove(player.getIndex());
		clearInventory(player);
		player.getPackets().sendResetCamera();
		player.setZombieKillstreak(0);
		player.getControlerManager().removeControlerWithoutCheck();
	}
	
	private static String[] joinMessages = {
		"Ready to die so soon",
		"This'll be the end of you",
		"Welcome to my domain",
		"Here you shall fall like the rest",
	};
	
	public static String getJoinMessage(Player player) {
		return joinMessages[Utils.random(joinMessages.length - 1)] + ", "+player.getDisplayName()+"";
	}
	
	public static String[] messages = {
		"Arise my minion.. ARRIISSEEE!!!",
		"All of ye who enter, SHALL PARISH!",
		"Kill thine enemies!",
		"Come to life my minion! Arise!",
	};
	
	public static void sendCameraShake() {
		for (Entry<Integer, Player> plr : players.entrySet()) {
			Player pl = plr.getValue();
			if (pl == null)
				continue;
			pl.getPackets().sendCameraShake(3, 8, 18, 8, 18);
		}
	}
	
	public static void sendResetCamera() {
		for (Entry<Integer, Player> plr : players.entrySet()) {
			Player pl = plr.getValue();
			if (pl == null)
				continue;
			pl.getPackets().sendResetCamera();
		}
	}
	
	public static void applyHits() {
		for (Entry<Integer, Player> plr : players.entrySet()) {
			Player pl = plr.getValue();
			if (pl == null)
				continue;
			pl.applyHit(new Hit(null, DISEASE_DAMAGE, HitLook.DESEASE_DAMAGE));
		}
	}
	
	public static void send(String message) {
		for (Entry<Integer, Player> plr : players.entrySet()) {
			Player player = plr.getValue();
			if (player == null)
				continue;
			player.sendMessage(message);
		}
	}
	
}
