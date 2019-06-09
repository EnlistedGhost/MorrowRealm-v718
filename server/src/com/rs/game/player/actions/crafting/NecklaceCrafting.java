package com.rs.game.player.actions.crafting;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

public class NecklaceCrafting {

	public enum Necklaces {

		SAPPHIRE(1656, 1607, 22, 55), 
		EMERALD(1658, 1605, 29, 60), 
		RUBY(1660, 1603, 40, 75), 
		DIAMOND(1662,1601, 56, 90),
		ONYX(6565, 6573, 82, 120);

		int itemId, gemId, level, exp;

		Necklaces(int itemId, int gemId, int level, int exp) {
			this.gemId = gemId;
			this.itemId = itemId;
			this.level = level;
			this.exp = exp;
		}
	}
	
	public static void makeNecklace(Player player, int gemId, int typeId, int amount) {
		for (Necklaces n : Necklaces.values()) {
			if (n.gemId == gemId) {
				if (player.getSkills().getLevel(Skills.CRAFTING) < n.level) {
					ItemDefinitions itemDef = ItemDefinitions.getItemDefinitions(n.itemId);
					player.sendMessage("You need atleast "+n.level+" Crafting to make a "+itemDef.getName()+"!");
					return;
				}
				if (!player.getInventory().contains(gemId, typeId)) {
					ItemDefinitions itemDef = ItemDefinitions.getItemDefinitions(n.itemId);
					player.sendMessage("You don't have the required items to make "+itemDef.getName()+"s!");
					return;
				}
				player.getInventory().deleteItem(gemId, amount);
				player.getInventory().deleteItem(typeId, amount);
				player.getInventory().addItem(n.itemId, amount);
				player.getGoals().increase(Skills.CRAFTING);
				player.getSkills().addXp(Skills.CRAFTING, n.exp);
				return;
			}
		}
	}

}
