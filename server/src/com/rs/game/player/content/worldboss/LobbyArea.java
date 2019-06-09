package com.rs.game.player.content.worldboss;

import java.util.Map;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.rs.Settings;
import com.rs.cores.CoresManager;
import com.rs.game.WorldObject;
import com.rs.game.player.Player;
import com.rs.game.player.controlers.Controler;
import com.rs.utils.Utils;

public class LobbyArea extends Controler {
	
	static Map<Integer, Player> lobby = new ConcurrentHashMap<Integer, Player>();
	
	@Override
	public void start() {
		player.sendMessage("Welcome to the World Boss Queue!");
		lobby.put(player.getIndex(), player);
		player.teleportPlayer(1955, 3239, 0);
		sendLobbyMessage(player.getDisplayName() + " has joined the World Boss Queue!");
		if (!countStarted) {
			countStarted = true;
			startCountdown();
			System.out.println("World Boss Queue started by "+player.getDisplayName()+"");
		}
	}
	
	public static void sendLobbyMessage(String message) {
		for (Entry<Integer, Player> e : lobby.entrySet()) {
			Player player = e.getValue();
			if (player == null) {
				continue;
			}
			player.sendMessage(message);
		}
	}
	
	@Override
	public void process() {
		if (startRaid) {
			boolean resizable = player.getInterfaceManager().hasRezizableScreen();
			player.getInterfaceManager().closeOverlay(resizable);
			lobby.remove(player.getIndex());
			teleToSafeSpot();
			player.getControlerManager().startControler("BossArena");
			return;
		}
		
		if (!isInLobby() || player.isDead()) {
			boolean resizable = player.getInterfaceManager().hasRezizableScreen();
			removeControler();
			lobby.remove(player.getIndex());
			player.getInterfaceManager().closeOverlay(resizable);
			sendLobbyMessage("[<col=FF0000>Raid Lobby</col>] "+player.getDisplayName() + " has left the World Boss Queue!");
		} else {
			sendTimeRemaining(waitTime);
		}
	}
	
	@Override
	public boolean processObjectClick1(WorldObject obj) {
		int objectId = obj.getId();
		if (objectId == 42022) {
			player.teleport(Settings.START_PLAYER_LOCATION);
			player.sendMessage("You've left the World Boss Queue!");
			return true;
		}
		return false;
	}
	
	
	private static int waitTime = 60;
	private static boolean countStarted;
	private static boolean startRaid;
	
	public static void startCountdown() {
		CoresManager.fastExecutor.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				
				if (lobby.isEmpty()) {
					startRaid = false;
					countStarted = false;
					waitTime = 60;
					this.cancel();
					return;
				}
				
				if (waitTime > 0) {
					waitTime--;
				} else {
					startRaid = true;
					countStarted = false;
					waitTime = 60;
					this.cancel();
				}
			}
		}, 5000, 1200);
	}
	
	public void sendTimeRemaining(int time) {
		boolean isFull = player.getInterfaceManager().hasRezizableScreen();
		if (!player.getInterfaceManager().containsInterface(1390))
			player.getInterfaceManager().sendOverlay(1390, isFull);
		
		player.getPackets().sendIComponentText(1390, 4, "Players In Raid:");
		player.getPackets().sendIComponentText(1390, 5, ""+BossArena.inRaid.size()+"");
		
		player.getPackets().sendIComponentText(1390, 6, "Players in Lobby:");
		player.getPackets().sendIComponentText(1390, 7, ""+LobbyArea.lobby.size()+"");
		
		player.getPackets().sendIComponentText(1390, 8, "Boss Health:");
		player.getPackets().sendIComponentText(1390, 9, BossArena.npc == null ? "Unknown" : ""+Utils.formatNumber(BossArena.npc.getHitpoints())+"");
		
		player.getPackets().sendIComponentText(1390, 10,"Raid starts in: ");
		player.getPackets().sendIComponentText(1390, 11,""+time+" sec.");
	}
	
	public boolean teleToSafeSpot() {
		int random = Utils.random(3);
		if (random == 0) {
			player.teleportPlayer(209, 5382, 0);
		} else if (random == 1) {
			player.teleportPlayer(209, 5385, 0);
		} else if (random == 2) {
			player.teleportPlayer(222, 5382, 0);
		} else if (random == 3) {
			player.teleportPlayer(222, 5385, 0);
		}
		return false;
	}
	
	public boolean isInLobby() {
		return player.getX() >= 1950 && player.getX() <= 1959 && player.getY() >= 3233 && player.getY() <= 3245;
	}
	
}
