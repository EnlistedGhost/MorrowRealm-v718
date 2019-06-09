package com.rs.game.player.content;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class ItemSearch {
	
	private static int MAX_RESULTS = 250;
	
    public static void searchForItem(Player player, String itemName) {
    	int count = 0;
        for (int i = 0; i < Utils.getItemDefinitionsSize(); i++) {
            Item item = new Item(i);
            if (item.getDefinitions().getName().toLowerCase().contains(itemName.toLowerCase())) {
            	count++;
            	if (count == MAX_RESULTS) {
                	player.getPackets().sendPanelBoxMessage("<col=FF0000>Found over 250 results for " + Utils.formatPlayerNameForDisplay(itemName) + ". Only 250 listed.");
            		return;
            	}
            	String suffix = item.getDefinitions().isNoted() ? "(noted)" : "";
        		player.getPackets().sendPanelBoxMessage("<col=00FFFF>"+Utils.formatPlayerNameForDisplay(item.getName()) + suffix+"</col> (Id: <col=00FF00>"+item.getId()+"</col>)");
            }
        }
    	player.getPackets().sendPanelBoxMessage("<col=FF0000>Found " + count + " results for the item " + Utils.formatPlayerNameForDisplay(itemName) + ".");
        }
}