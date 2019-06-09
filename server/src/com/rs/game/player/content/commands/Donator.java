package com.rs.game.player.content.commands;

import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.minigames.zombies.WiseOldMan;
import com.rs.game.player.Player;
import com.rs.game.player.content.DisplayNameAction;
import com.rs.game.player.content.custom.TitleHandler;
import com.rs.game.player.controlers.custom.Dungeoneering;
import com.rs.utils.DisplayNames;
import com.rs.utils.Utils;

public class Donator {

	public static boolean processCommand(Player player, String[] cmd, boolean console, boolean clientCommand) {
		
		if (clientCommand)
			return true;
		
		if (cmd[0].equals("switchemotes")) {
			player.setUsingAltEmotes(!player.isUsingAltEmotes());
			player.sendMessage("Alternate emotes are now "+(player.isUsingAltEmotes() ? "enabled" : "disabled")+"");
			return true;
		}
		
		if (cmd[0].equals("roll")) {
			player.sendMessage("Please purchase a dice bag from King Fox for $30 if you wish to dice.");
			return true;
		}
		
		if (cmd[0].equals("setdisplay")) {
			player.sendMessage("Click on the noticeboard at home if you wish to change your display name.");
			return true;
		}
		
		if (cmd[0].equals("bank")) {
			if (player.isAtWorldBoss()) {
				player.sendMessage("You are not allowed to bank from here.");
				return true;
			}
			if (player.getControlerManager().getControler() instanceof Dungeoneering) {
				player.sm("<col=FF0000>You can not allowed to bank here.");
				return true;
			}
			if (player.getControlerManager().getControler() instanceof WiseOldMan) {
				player.sm("<col=FF0000>You can not allowed to bank here.");
				return true;
			}
			if (!player.canSpawn() && player.getRights() < 1 && player.getRights() > 2) {
				player.getPackets().sendGameMessage("You can't bank while you're in this area.");
				return true;
			}
			
			player.stopAll();
			player.getBank().openBank();
			return true;
		}
	return false;
	}
	
}
