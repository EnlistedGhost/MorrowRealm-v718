package com.rs.game.player.content.custom;

import com.rs.game.player.Player;

public class StarterItems {

	public static final int FIGHTER = 1;
	public static final int ARCHER = 2;
	public static final int MAGICIAN = 3;
	
	public static void giveStarter(Player player, int classId) {
		if (classId == FIGHTER) {
			player.setClassName("Fighter");
			starterSet(player, FIGHTER);
		}
		
		if (classId == ARCHER) {
			player.setClassName("Archer");
			starterSet(player, ARCHER);
		}
		
		if (classId == MAGICIAN) {
			player.setClassName("Magician");
			starterSet(player, MAGICIAN);
		}
		
		player.getInventory().addItem(995, 10000);
		//player.getInventory().addItem(22340, 1);
		//player.getInventory().addItem(1856, 1);
		player.getInventory().refresh();
		player.getInterfaceManager().closeChatBoxInterface();
		player.getPackets().sendGameMessage("Your have chosen the combat class: "+player.getClassName()+".");
	}
	
	
	public static void starterSet(Player player, int set) {
		switch (set) {
		
			case FIGHTER:
				player.getInventory().addItem(1075, 1); // Bronze Platelegs
				player.getInventory().addItem(1087, 1); // Bronze Plateskirt
				player.getInventory().addItem(1117, 1); // Bronze Platebody
				player.getInventory().addItem(1155, 1); // Bronze Full Helm
				player.getInventory().addItem(12985, 1);// Bronze Gauntlets
				player.getInventory().addItem(4119, 1); // Bronze Boots
				player.getInventory().addItem(14642, 1);// Blue Cape
				//player.getInventory().addItem(1052, 1); // Cape of Legends
				player.getInventory().addItem(1277, 1); // Bronze Sword (short)
				player.getInventory().addItem(380, 10); // Lobster
				break;
				
			case ARCHER:
				player.getInventory().addItem(1059, 1); // Leather Gloves
				player.getInventory().addItem(1095, 1); // Leather Chaps
				player.getInventory().addItem(1129, 1); // Leather Body
				player.getInventory().addItem(1061, 1); // Leather Boots
				player.getInventory().addItem(1167, 1); // Leather Cowl
				player.getInventory().addItem(841, 1);	// Bow
				player.getInventory().addItem(14642, 1);// Blue Cape
				player.getInventory().addItem(882, 250);// Bronze Arrow
				player.getInventory().addItem(380, 10); // Lobster
				break;
				
			case MAGICIAN:
				player.getInventory().addItem(579, 1);	// Wizard Hat
				player.getInventory().addItem(577, 1);	// Wizard Robe Top
				player.getInventory().addItem(1011, 1); // Wizard Robe Skirt
				player.getInventory().addItem(25873, 1);// Wizard Gloves
				player.getInventory().addItem(10689, 1);// Wizard Boots
				player.getInventory().addItem(1381, 1); // Staff of Air
				player.getInventory().addItem(14642, 1);// Blue Cape
				player.getInventory().addItem(558, 275);// Mind Rune
				//player.getInventory().addItem(555, 250);
				//player.getInventory().addItem(556, 250);
				//player.getInventory().addItem(557, 250);
				//player.getInventory().addItem(554, 250);
				//player.getInventory().addItem(559, 250);
				player.getInventory().addItem(380, 10); // Lobster
				break;
		}
	}
	
}
