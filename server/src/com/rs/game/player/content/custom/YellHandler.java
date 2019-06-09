package com.rs.game.player.content.custom;

import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class YellHandler {
	
	public static boolean isValidText(Player player, String message) {
		String[] invalid = { "<euro", "<img", "<img=", "<col", "<col=", "<shad", "<shad=", "<str>", "<u>" };
		for (String s : invalid)
			if (message.contains(s)) 
				return false;
		return true;
	}

	public static void sendYell(Player player, String message) {
		if (isValidText(player, message) && player.getRights() != 2) {
			World.sendWorldMessage(RightsManager.getInfo(player) +": "+ message, false);
			return;
		}
		if (player.getMuted() > Utils.currentTimeMillis()) {
			player.sendMessage("<col=FF0000>You are muted. Please try back later.");
			return;
		}
		World.sendWorldMessage(RightsManager.getInfo(player) +": "+ message, false);
		return;
	}
	
}
