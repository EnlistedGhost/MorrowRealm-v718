package com.rs.game.player.actions.crafting;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

public class RingCrafting {

	public enum Rings {

		SAPPHIRE(1637, 1607, 20, 50), 
		EMERALD(1639, 1605, 27, 55), 
		RUBY(1641, 1603, 34, 70), 
		DIAMOND(1643,1601, 43, 85),
		DRAGONSTONE(1645,1615, 55, 100),
		ONYX(6564, 6573, 67, 115);

		int itemId, gemId, level, exp;

		Rings(int itemId, int gemId, int level, int exp) {
			this.gemId = gemId;
			this.itemId = itemId;
			this.level = level;
			this.exp = exp;
		}
	}
	
	public static void makeRing(Player player, int gemId, int typeId, int amount) {
		for (Rings n : Rings.values()) {
			if (n.gemId == gemId) {
				if (player.getSkills().getLevel(Skills.CRAFTING) < n.level) {
					ItemDefinitions itemDef = ItemDefinitions.getItemDefinitions(n.itemId);
					player.sendMessage("You need atleast "+n.level+" Crafting to make a "+itemDef.getName()+"!");
					return;
				}
				if (!player.getInventory().containsItem(typeId, 1)) {
					ItemDefinitions itemDef = ItemDefinitions.getItemDefinitions(typeId);
					player.sendMessage("You don't have any "+itemDef.getName()+"s!");
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
	
	public static double getMultiplier(Player player) {
		return player.getEquipment().getRingId() == 21414 ? Settings.SKILL_RING_MOD : 1.00;
	}

}
