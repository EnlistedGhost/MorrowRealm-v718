package com.rs.game.player.content.interfaces;

import com.rs.Settings;
import com.rs.game.player.Player;

public class WelcomeBook {

	public static void openBook(Player player) {
		player.getInterfaceManager().purgeMenu(275);
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendIComponentText(275, 1, Settings.SERVER_NAME);
		player.getPackets().sendIComponentText(275, 10, "Welcome, "+player.getDisplayName()+", to "+Settings.SERVER_NAME+"");
		player.getPackets().sendIComponentText(275, 11, "");
		player.getPackets().sendIComponentText(275, 12, "To help you get started, you've received this book and");
		player.getPackets().sendIComponentText(275, 13, "some cash. To get more cash, click on the quest tab and");
		player.getPackets().sendIComponentText(275, 14, "select an activity you would like to do. Depending on what");
		player.getPackets().sendIComponentText(275, 15, "you're doing, you can get some pretty good stuff! A great");
		player.getPackets().sendIComponentText(275, 16, "place to start would be at rock crabs for starters!");
		player.getPackets().sendIComponentText(275, 17, "<img=1>Also, make sure you read ::rules or you may be sorry!<img=1>");
		player.getPackets().sendIComponentText(275, 18, "");
		player.getPackets().sendIComponentText(275, 19, "<shad=FF0000>Don't forget to vote for us! ");
		player.getPackets().sendIComponentText(275, 20, "<shad=FF0000>To vote, type ::vote and follow the instructions!");
		player.getPackets().sendIComponentText(275, 21, "");
		player.getPackets().sendIComponentText(275, 22, "");
		player.getPackets().sendIComponentText(275, 23, "");
		player.getPackets().sendIComponentText(275, 24, "");
		player.getPackets().sendIComponentText(275, 25, "");
		player.getPackets().sendIComponentText(275, 26, "");
		return;
	}
	
	public static void openRules(Player player) {
		player.getInterfaceManager().sendInterface(1245);
		player.getPackets().sendIComponentText(1245, 330, "Guardian Rules");
		player.getPackets().sendIComponentText(1245, 13, "1. Do not spam yell, friends/ or game chat");
		player.getPackets().sendIComponentText(1245, 14, "2. You are NOT allowed to AFK train, or sit idle");
		player.getPackets().sendIComponentText(1245, 15, "3. Do NOT abuse any bugs/glitches, please report them!");
		player.getPackets().sendIComponentText(1245, 16, "4. Harassment will NOT be tolerated!");
		player.getPackets().sendIComponentText(1245, 17, "5. Excessive bad languange is prohibited");
		player.getPackets().sendIComponentText(1245, 18, "6. Scamming will result in a trade-block/ban!");
		player.getPackets().sendIComponentText(1245, 19, "7. Please do not be disrespectful!");
		player.getPackets().sendIComponentText(1245, 20, "8. Just remember to be friendly and play fair!");
		player.getPackets().sendIComponentText(1245, 21, "~Fox");
		player.getPackets().sendIComponentText(1245, 22, "");
		player.getPackets().sendIComponentText(1245, 23, "");
		return;
	}
}
