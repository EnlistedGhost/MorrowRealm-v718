package com.rs.game.player.content;

import com.rs.game.player.Player;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;
/**
 * @author JazzyYaYaYa | Fuzen Seth | Nexon
 */
public class DisplayNameAction {
	
	public static int MoneyAmount = 15000000;
	
	public static void RemoveDisplay (Player player) {
		player.setDisplayName(Utils.formatPlayerNameForDisplay(player.getUsername()));
		player.getInterfaceManager().closeChatBoxInterface();
		SerializableFilesManager.savePlayer(player);
		player.getAppearence().generateAppearenceData();
		player.sm("Display name removed, if you don't see your original name please relog.");
	}

	public static void ProcessChange (Player player) {
		if (player.getInventory().containsItem(995, MoneyAmount)) {
			player.getInventory().deleteItem(995, MoneyAmount);
			player.getInventory().refresh();
			player.getInterfaceManager().closeChatBoxInterface();  
			player.getAttributes().put("setdisplay", Boolean.TRUE); 
			player.getPackets().sendInputNameScript("Enter the display name you wish:"); 	  
		} else {
			player.getInterfaceManager().closeChatBoxInterface();
			player.sm("Changing display name will cost you 15m coins.");
		}
	}
	
}
