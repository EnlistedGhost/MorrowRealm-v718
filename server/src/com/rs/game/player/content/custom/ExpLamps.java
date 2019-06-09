package com.rs.game.player.content.custom;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

public class ExpLamps {

	public enum Lamps {
		
		Attack(1000, 2500, 5000, 10000),
		Defence(1000, 2500, 5000, 10000),
		Strength(1000, 2500, 5000, 10000),
		Constitution(1000, 2500, 5000, 10000),
		Ranged(1000, 2500, 5000, 10000),
		Prayer(1000, 2500, 5000, 10000),
		Magic(1000, 2500, 5000, 10000),
		Cooking(1000, 2500, 5000, 10000),
		Woodcutting(1000, 2500, 5000, 10000),
		Fletching(1000, 2500, 5000, 10000),
		Fishing(1000, 2500, 5000, 10000),
		Firemaking(1000, 2500, 5000, 10000),
		Crafting(1000, 2500, 5000, 10000),
		Smithing(1000, 2500, 5000, 10000),
		Mining(1000, 2500, 5000, 10000),
		Herblore(1000, 2500, 5000, 10000),
		Agility(1000, 2500, 5000, 10000),
		Thieving(1000, 2500, 5000, 10000),
		Slayer(1000, 2500, 5000, 10000),
		Farming(1000, 2500, 5000, 10000),
		Runecrafting(1000, 2500, 5000, 10000),
		Hunter(1000, 2500, 5000, 10000),
		Construction(1000, 2500, 5000, 10000),
		Summoning(1000, 2500, 5000, 10000),
		Dungeoneering(1000, 2500, 5000, 10000);
		
		private int exp_small, exp_med, exp_large, exp_huge;
		
		Lamps(int expSmall, int expMed, int expLarge, int expHuge) {
			this.exp_small = expSmall;
			this.exp_med = expMed;
			this.exp_large = expLarge;
			this.exp_huge = expHuge;
		}
		
	}

	
	public static boolean claimLamp(Player player, int itemId) {
		ItemDefinitions def = ItemDefinitions.getItemDefinitions(itemId);
		String name = def.getName().toLowerCase();
		for (Lamps lamp : Lamps.values()) {
			String skillName = Skills.SKILLS[lamp.ordinal()].toLowerCase();
			int exp = -1;
			String type = null;		
			if (name.contains(skillName)) {
				int skillId = lamp.ordinal();
				if (name.contains("small")) {
					type = "Small";
					exp = lamp.exp_small;
				} else if (name.contains("medium")) {
					type = "Medium";
					exp = lamp.exp_med;
				} else if (name.contains("large")) {
					type = "Large";
					exp = lamp.exp_large;
				} else if (name.contains("huge")) {
					type = "Huge";
					exp = lamp.exp_huge;
				}
				if (type != null && exp != -1) {
					player.getDialogueManager().startDialogue("ExpLampDialogue", skillId, exp, type, itemId);
					return true;
				}
				return false;
			}
		}
		return false;
	}
}
