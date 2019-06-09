package com.rs.game.player.actions;

import com.rs.Settings;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
/**
 * 
 * @author JazzyYaYaYa | Nexon | Fuzen Seth
 * Announcement: Nexonisabawz
 */
public class DonatorActions {
public static final WorldTile DONATOR_HOME = new WorldTile(4707, 5606, 0);
	
	public static void CheckDonator(Player player) {
		if (player.getRights() > 0) {
			player.setNextWorldTile(DONATOR_HOME);
			player.sm("You are teleporting to the main area of donators.");
			player.sm("We respect that you want to help us.");
			player.getInterfaceManager().closeChatBoxInterface();
		}
		else {
			player.sm("You must have donated to "+Settings.SERVER_NAME+" to use this teleport type.");
			player.getInterfaceManager().closeChatBoxInterface();
		}
		
	}
	
	
}
