package com.rs.game.player.actions.crafting;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

public class Crafting {

	public static final int AMULET = 1673, NECKLACE = 1654, RING = 1635, BRACELET = 11069, GOLD = 2357;
	
	public static void makeAmulet(Player player, int mouldId, int toMake, int amount) {
		String itemName = ItemDefinitions.getItemDefinitions(mouldId).getName();
		String itemName2 = ItemDefinitions.getItemDefinitions(toMake).getName();
		

		if (!player.getInventory().containsItem(mouldId) && !player.getTools().contains(mouldId)) {
			player.sendMessage("It appears you do not have a "+itemName+"!");
			return;
		}
		
		if (player.getSkills().getLevel(Skills.SMITHING) < 40) {
			player.sendMessage("You need atleast 40 smithing to make a "+itemName2+"!");
			return;
		}
		
		if (!player.getInventory().containsItem(GOLD, amount)) {
			player.sendMessage("You don't have any gold!");
			return;
		}
		
		if (amount > player.getInventory().getNumberOf(GOLD)) {
			amount = player.getInventory().getNumberOf(GOLD);
		}
		player.getInventory().deleteItem(GOLD, amount);
		player.getInventory().addItem(toMake, amount);
		player.getSkills().addXp(Skills.SMITHING, (20 * amount));
	}
	
	public static double getMultiplier(Player player) {
		return player.getEquipment().getRingId() == 21414 ? Settings.SKILL_RING_MOD : 1.00;
	}
	
}
	
	

