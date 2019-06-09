package com.rs.content.utils;

import com.rs.game.player.Player;

/**
 * 
 * @author Adam
 *
 */

public class Lamps {

	public static void handleLamps(Player player, int itemId) {
		switch (itemId) {
		case 23717://attack
			player.getSkills().addXp(0, 200);
			player.getInventory().deleteItem(itemId, 1);
			player.out("You gain xp.");
			return;
		case 23721:
			player.getSkills().addXp(2, 200);
			player.out("You gain xp.");
			player.getInventory().deleteItem(itemId, 1);
			return;
		case 23725:
			player.getSkills().addXp(1, 200);
			player.out("You gain xp.");
			player.getInventory().deleteItem(itemId, 1);
			return;
		case 23729:
			player.getSkills().addXp(4, 200);
			player.out("You gain xp.");
			player.getInventory().deleteItem(itemId, 1);
			return;
		case 23733:
			player.getSkills().addXp(6, 200);
			player.out("You gain xp.");
			player.getInventory().deleteItem(itemId, 1);
			return;
		case 23737:
			player.getSkills().addXp(5, 200);
			player.out("You gain xp.");
			player.getInventory().deleteItem(itemId, 1);
			return;
		case 23741:
			player.getSkills().addXp(20, 200);
			player.out("You gain xp.");
			player.getInventory().deleteItem(itemId, 1);
			return;
		}
	}
	
}
