package com.rs.game.player.content.worldboss;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.rs.Settings;
import com.rs.game.Animation;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.controlers.Controler;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class BossArena extends Controler {
	
	static Map<Integer, Player> inRaid = new ConcurrentHashMap<Integer, Player>();
	static NPC npc;
	
	@Override
	public void start() {
		inRaid.put(player.getIndex(), player);
		sendCornerInterfaces();
		
		if (npc == null)
			npc = World.getNpc(11872);
	}
	
	public void remove() {
		boolean resizable = player.getInterfaceManager().hasRezizableScreen();
		removeControler();
		player.getInterfaceManager().closeOverlay(resizable);
		inRaid.remove(player.getIndex());
		if (inRaid.isEmpty()) {
			if (npc != null && !npc.hasFinished() && !npc.isDead()) {
				npc.heal(npc.getMaxHitpoints() - npc.getHitpoints());
			}
		}
		player.getPackets().sendStopCameraShake();
		sendMessage(player.getDisplayName() + " has left the raid!");
	}
	
	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		if (!isAtSafeArea(player)) {
			player.sendMessage("You must be behind a piller to leave from here.");
			return false;
		}
		return true;
	}
	
	@Override
	public void process() {
		sendCornerInterfaces();
		if (!inCombatArea()) {
			remove();
		}
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
					player.getPackets().sendGameMessage("Oh dear, you have died.");
				} else if (loop == 3) {
					player.sendItemsOnDeath(player);
					player.getEquipment().init();
					player.getInventory().init();
					player.reset();
					player.setNextWorldTile(new WorldTile(Settings.RESPAWN_PLAYER_LOCATION));
					player.setNextAnimation(new Animation(-1));
				} else if (loop == 4) {
					remove();
					player.getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		}, 0, 1);
		return false;
	}
	
	public static boolean isAtSafeArea(Player player) {
		if (player.getX() == 209 && player.getY() == 5385) {
			return true;
		}
		if (player.getX() == 209 && player.getY() == 5382) {
			return true;
		}
		if (player.getX() == 222 && player.getY() == 5385) {
			return true;
		}
		if (player.getX() == 222 && player.getY() == 5382) {
			return true;
		}
		return false;
	}
	
	public void sendCornerInterfaces() {
		boolean isFull = player.getInterfaceManager().hasRezizableScreen();
		if (!player.getInterfaceManager().containsInterface(1390))
			player.getInterfaceManager().sendOverlay(1390, isFull);
		player.getPackets().sendIComponentText(1390, 4, "Players In Raid:");
		player.getPackets().sendIComponentText(1390, 5, ""+inRaid.size()+"");
		player.getPackets().sendIComponentText(1390, 6, "Players in Lobby:");
		player.getPackets().sendIComponentText(1390, 7, ""+LobbyArea.lobby.size()+"");
		player.getPackets().sendIComponentText(1390, 8, "Boss Health:");
		player.getPackets().sendIComponentText(1390, 9, npc == null ? "Unknown" : ""+Utils.formatNumber(npc.getHitpoints())+"");
		player.getPackets().sendIComponentText(1390, 10,"-");
		player.getPackets().sendIComponentText(1390, 11,"-");
	}
	
	public boolean inCombatArea() {
		return player.getX() >= 208 && player.getX() <= 223 && player.getY() >= 5376 && player.getY() <= 5391;
	}
	
	public static void sendMessage(String message) {
		for (Entry<Integer, Player> e : inRaid.entrySet()) {
			Player player = e.getValue();
			if (player == null) {
				continue;
			}
			player.sendMessage(message);
		}
	}
	
}
