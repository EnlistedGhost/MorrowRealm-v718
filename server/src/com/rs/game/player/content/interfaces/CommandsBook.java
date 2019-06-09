package com.rs.game.player.content.interfaces;

import com.rs.Settings;
import com.rs.game.player.Player;
import com.rs.net.encoders.WorldPacketsEncoder;

public class CommandsBook {

	public static int INTER = 1206;
	
	public static void sendBook(Player player) {
		player.getInterfaceManager().sendInterface(INTER);
		WorldPacketsEncoder packets = player.getPackets();
		
		packets.sendIComponentText(INTER, 39, "Commands List");
		
		packets.sendIComponentText(INTER, 58, ""); // prev button
		packets.sendIComponentText(INTER, 59, ""); // next button
		
		// left side
		packets.sendIComponentText(INTER, 41, "<u=FF0000><col=FF0000>::starter</col></u>");
		packets.sendIComponentText(INTER, 42, "  Will allow you to choose a starter");
		packets.sendIComponentText(INTER, 43, "<u=FF0000><col=FF0000>::hs</col></u>");
		packets.sendIComponentText(INTER, 44, "  Updates your highscores");
		packets.sendIComponentText(INTER, 45, "<u=FF0000><col=FF0000>::highscores</col></u>");
		packets.sendIComponentText(INTER, 46, "  Takes you to out highscores page");
		packets.sendIComponentText(INTER, 47, "<u=FF0000><col=FF0000>::setpass</col></u>");
		packets.sendIComponentText(INTER, 48, "  Allows you to set a new pass");
		packets.sendIComponentText(INTER, 49, "<u=FF0000><col=FF0000>::claimdonor</col></u>");
		packets.sendIComponentText(INTER, 50, "  Checks the database for your donation");
		packets.sendIComponentText(INTER, 51, "<u=FF0000><col=FF0000>::titlelist</col></u>");
		packets.sendIComponentText(INTER, 52, "  Opens a list of unlocked titles");
		packets.sendIComponentText(INTER, 53, "<u=FF0000><col=FF0000>::rules</col></u>");
		packets.sendIComponentText(INTER, 54, "  Opens the rules interface");
		
		// left side
		packets.sendIComponentText(INTER, 65, "<u=FF0000><col=FF0000>::prestige</col></u>");
		packets.sendIComponentText(INTER, 66, "  Will advance you in prestige");
		packets.sendIComponentText(INTER, 67, "<u=FF0000><col=FF0000>::skull</col></u>");
		packets.sendIComponentText(INTER, 68, "  Shows/hides prestige skull");
		packets.sendIComponentText(INTER, 69, "");
		packets.sendIComponentText(INTER, 70, "");
		packets.sendIComponentText(INTER, 71, "");
		packets.sendIComponentText(INTER, 72, "");
		packets.sendIComponentText(INTER, 73, "");
		packets.sendIComponentText(INTER, 74, "");
		packets.sendIComponentText(INTER, 75, "");
		packets.sendIComponentText(INTER, 76, "");
		packets.sendIComponentText(INTER, 77, "");
		packets.sendIComponentText(INTER, 78, "");
	}
	
}
