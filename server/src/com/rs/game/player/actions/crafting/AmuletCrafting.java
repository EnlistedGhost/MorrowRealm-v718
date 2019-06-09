package com.rs.game.player.actions.crafting;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

public class AmuletCrafting {
	
	public enum Amulets {
		SAPPHIRE(1675, 1694, 1607, 24, 65),
		EMERALD(1677, 1696, 1605, 31, 70),
		RUBY(1679, 1698, 1603, 50, 85),
		DIAMOND(1681, 1700, 1601, 70, 100),
		DRAGONSTONE(1683, 1702, 1615, 80, 150),
		ONYX(6579, 6581, 6573, 90, 165);
			
		int itemId, strungId, gemId, level, exp;
		Amulets(int itemId, int strungId, int gemId, int level, int exp) {
			this.itemId = itemId;
			this.strungId = strungId;
			this.gemId = gemId;
			this.level = level;
			this.exp = exp;
		}
		
		public int getAmuletId() {
			return itemId;
		}
		
	}
	
	public static boolean usingWithAmulet(int amuletId) {
		for (Amulets amulet : Amulets.values()) {
			if (amuletId == amulet.itemId) {
				return true;
			}
		}
		return false;
	}
	
	public static void stringAmulet(Player player, int amuletId) {
		if (!player.getInventory().containsItem(1759, 1) || !player.getInventory().containsItem(amuletId, 1)) {
			return;
		}
		for (Amulets amulet : Amulets.values()) {
			if (amuletId == amulet.itemId) {
				if (player.getSkills().getLevel(Skills.CRAFTING) < amulet.level) {
					ItemDefinitions itemDef = ItemDefinitions.getItemDefinitions(amulet.itemId);
					player.sendMessage("You need atleast "+amulet.level+" Crafting to string a "+itemDef.getName()+"!");
					return;
				}
				ItemDefinitions defs = ItemDefinitions.getItemDefinitions(amulet.strungId);
				player.getInventory().deleteItem(amuletId, 1);
				player.getInventory().deleteItem(1759, 1);
				player.getInventory().addItem(amulet.strungId, 1);
				player.sendMessage("You've made a "+defs.getName()+"!");
				player.getSkills().addXp(Skills.CRAFTING, 15);
				return;
			}
		}
	}
	
	public static double getMultiplier(Player player) {
		return player.getEquipment().getRingId() == 21411 ? Settings.SKILL_RING_MOD : 1.00;
	}
	
	public static void makeAmulet(Player player, int gemId, int partId, int amount) {
		for (Amulets amulet : Amulets.values()) {
			if (gemId == amulet.gemId) {
				if (player.getSkills().getLevel(Skills.CRAFTING) < amulet.level) {
					ItemDefinitions itemDef = ItemDefinitions.getItemDefinitions(amulet.itemId);
					player.sendMessage("You need atleast "+amulet.level+" Crafting to make a "+itemDef.getName()+"!");
					return;
				}
				if (!player.getInventory().containsItem(gemId, 1)) {
					return;
				}
				if (!player.getInventory().containsItem(partId, 1)) {
					ItemDefinitions itemDef = ItemDefinitions.getItemDefinitions(partId);
					player.sendMessage("You don't have any "+itemDef.getName()+"s!");
					return;
				}
				player.getInventory().deleteItem(gemId, 1);
				player.getInventory().deleteItem(partId, 1);
				player.getInventory().addItem(amulet.itemId, 1);
				player.getSkills().addXp(Skills.CRAFTING, amulet.exp);
				return;
			}
		}
	}
	
}
