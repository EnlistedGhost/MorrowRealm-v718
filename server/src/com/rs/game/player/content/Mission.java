package com.rs.game.player.content;

import com.rs.Settings;
import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.net.decoders.handlers.ButtonHandler;
import com.rs.utils.Utils;

/**
 * @author JazzyYaYaYa | Nexon | Fuzen Seth
 */
public class Mission {
public static int MissionBoard = 72;

	public static void OpenMissionBoard(Player player) {
		player.getInterfaceManager().sendInterface(MissionBoard);
		//ButtonHandler.sendMissionBoard(player);
		sendComponents(player);
	}

	public static void refresh(Player player) {
		player.refreshMoneyPouch();
		player.refreshAllowChatEffects();
		player.refreshOtherChatsSetup();
		player.refreshHitPoints();
		player.refreshSpawnedItems();
		player.refreshSpawnedObjects();
		player.getInventory().refresh();
		player.saveIP();
		player.refreshMouseButtons();
	}
	public static void setMission (Player player) {
		player.stopAll();
		player.sm("Your current mission is active.");
		player.getQuestManager().init();
		refresh(player);
		
	}
	public static void sendComponents(Player player) {
			player.getPackets().sendIComponentText(72, 0, ""+Settings.SERVER_NAME+" | Mission List");
			player.getPackets().sendIComponentText(72, 63, "Ned's Ship Passport");
			player.getPackets().sendIComponentText(72, 65, "Pain of Ozan");
			player.getPackets().sendIComponentText(72, 68, "Gods of "+Settings.SERVER_NAME+"");
			player.getPackets().sendIComponentText(72, 69, "History!");
			player.getPackets().sendIComponentText(72, 70, "Saving the Sea");
			player.getPackets().sendIComponentText(72, 71, "Darker Dungeons");
			player.getPackets().sendIComponentText(72, 0,  "MyScape Portal");
			player.getPackets().sendIComponentText(72, 4, "addtext");
			player.getPackets().sendIComponentText(72, 6, "addtext");
			player.getPackets().sendIComponentText(72, 8, "addtext");
			player.getPackets().sendIComponentText(72, 10, "addtext");
			player.getPackets().sendIComponentText(72, 12, "addtext");
			player.getPackets().sendIComponentText(72, 14, "addtext");
						}
	
	public static void handleButtons(Player player, int componentId) {
		if (componentId == 68) {
			player.getInventory().refresh();
			player.closeInterfaces();
		}
		}
}
