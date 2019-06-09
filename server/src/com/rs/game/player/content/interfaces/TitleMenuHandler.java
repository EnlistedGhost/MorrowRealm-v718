package com.rs.game.player.content.interfaces;

import com.rs.game.player.Player;
import com.rs.game.player.content.custom.TitleHandler;

public class TitleMenuHandler {

	public static int INTER = 496;
	
	public static void launchMenu(Player player) {
		player.getInterfaceManager().sendInterface(INTER);
		String color = "<col=000000>";
		player.getPackets().sendIComponentText(INTER, 3, "Select a New Title");
		player.getPackets().sendIComponentText(INTER, 4, TitleHandler.getTitle(player, 59) + color + player.getDisplayName());
		player.getPackets().sendIComponentText(INTER, 5, TitleHandler.getTitle(player, 60) + color + player.getDisplayName());
		player.getPackets().sendIComponentText(INTER, 6, TitleHandler.getTitle(player, 61) + color + player.getDisplayName());
		player.getPackets().sendIComponentText(INTER, 7, TitleHandler.getTitle(player, 62) + color + player.getDisplayName());
		player.getPackets().sendIComponentText(INTER, 8, TitleHandler.getTitle(player, 63) + color + player.getDisplayName());
		player.getPackets().sendIComponentText(INTER, 9, TitleHandler.getTitle(player, 64) + color + player.getDisplayName());
		player.getPackets().sendIComponentText(INTER, 10, color + player.getDisplayName() + TitleHandler.getTitle(player, 65));
		player.getPackets().sendIComponentText(INTER, 11, color + player.getDisplayName() + TitleHandler.getTitle(player, 66));
		player.getPackets().sendIComponentText(INTER, 12, color + player.getDisplayName() + TitleHandler.getTitle(player, 67));
		player.getPackets().sendIComponentText(INTER, 13, color + player.getDisplayName() + TitleHandler.getTitle(player, 68));
		return;
	}
	
	public static void handleButtons(Player player, int interfaceId, int componentId) {
		if (interfaceId == INTER) {
			if (componentId == 4) {
				if (!player.isOwner()) {
					player.sendMessage("This title is reserved for the Owner only.");
					return;
				}
				TitleHandler.forceSet(player, 59);
				return;
			}
			if (componentId == 5) {
				if (!player.isAdmin()) {
					player.sendMessage("This title is reserved for Admins only.");
					return;
				}
				TitleHandler.forceSet(player, 60);
				return;
			}
			if (componentId == 6) {
				if (!player.isModerator()) {
					player.sendMessage("This title is reserved for Mods only.");
					return;
				}
				TitleHandler.forceSet(player, 61);
				return;
			}
			if (componentId == 7) {
				if (player.getRights() != 4) {
					player.sendMessage("This title is reserved for Regular Donors only.");
					return;
				}
				TitleHandler.forceSet(player, 62);
				return;
			}
			if (componentId == 8) {
				if (player.getRights() != 5) {
					player.sendMessage("This title is reserved for Super Donors only.");
					return;
				}
				TitleHandler.forceSet(player, 63);
				return;
			}
			if (componentId == 9) {
				if (player.getRights() != 6) {
					player.sendMessage("This title is reserved for Extreme Donors only.");
					return;
				}
				TitleHandler.forceSet(player, 64);
				return;
			}
			if (componentId == 10) {
				TitleHandler.forceSet(player, 65);
				return;
			}
			if (componentId == 11) {
				TitleHandler.forceSet(player, 66);
				return;
			}
			if (componentId == 12) {
				TitleHandler.forceSet(player, 67);
				return;
			}
			if (componentId == 13) {
				TitleHandler.forceSet(player, 68);
				return;
			}
		}
	}
}
