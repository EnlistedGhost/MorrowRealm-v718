package com.rs.game.player.content;

import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;

/**
 * @author JazzyYaYaYa | Nexon | Fuzen Seth
 * Remade by King Fox
 */
public class ConstructFurniture {

	public static int CFG = 397;
	
	public static void handleButtons(Player player, int componentId) {
		if (componentId == 45) {
			if (player.getInventory().containsItem(960, 2)) {
				player.getSkills().addXp(22, 75);
				player.sm("You build: 1x armchair.");
				player.setNextAnimation(new Animation(883));
				player.getInventory().deleteItem(960, 2);
				player.getInventory().refresh();
				player.getGoals().increase(Skills.CONSTRUCTION);
				player.closeInterfaces();
			} else {
				player.sm("You need: 2 planks to build a chair.");
			}
		} else if (componentId == 46) {
			if (player.getInventory().containsItem(960, 4)) {
				player.getSkills().addXp(22, 111);
				player.sm("You build: 1x bookcase.");
				player.getInventory().deleteItem(960, 4);
				player.getGoals().increase(Skills.CONSTRUCTION);
				player.setNextAnimation(new Animation(883));
				player.closeInterfaces();
			} else {
				player.sm("You need: 4 planks to build a bookcase.");
			}
		} else if (componentId == 47) {
			if (player.getInventory().containsItem(8778, 4)) {
				player.getSkills().addXp(22, 165);
				player.sm("You build: 1x Beer Barrels.");
				player.getInventory().deleteItem(8778, 4);
				player.getGoals().increase(Skills.CONSTRUCTION);
				player.setNextAnimation(new Animation(883));
				player.closeInterfaces();
			} else {
				player.sm("You will need: 3 oak planks to build a Beer Barrels.");
			}
		} else if (componentId == 48) {
			if (player.getInventory().containsItem(8778, 8)) {
				player.getSkills().addXp(22, 165);
				player.sm("You build: 1x Kitchen Table.");
				player.setNextAnimation(new Animation(883));
				player.getInventory().deleteItem(8778, 8);
				player.getGoals().increase(Skills.CONSTRUCTION);
				player.closeInterfaces();
			} else {
				player.sm("You will need: 8 oak planks to build a Kitchen Table.");
			}
		} else if (componentId == 49) {
			if (player.getInventory().containsItem(8778, 4) && player.getInventory().containsItem(960, 1)) {
				player.getSkills().addXp(22, 265);
				player.sm("You build: 1x Dining Table.");
				player.getInventory().deleteItem(8778, 4);
				player.getInventory().deleteItem(960, 1);
				player.getGoals().increase(Skills.CONSTRUCTION);
				player.setNextAnimation(new Animation(883));
				player.closeInterfaces();
			} else {
			player.sm("You will need: 8 oak planks and 1x plank to build a Dining Table.");
			}
		} else if (componentId == 50) {
			if (player.getInventory().containsItem(8780, 2) && player.getInventory().containsItem(960, 1)) {
				player.getSkills().addXp(22, 400);
				player.sm("You build: 1x Dining bench.");
				player.setNextAnimation(new Animation(883));
				player.getInventory().deleteItem(8780, 2);
				player.getInventory().deleteItem(960, 1);
				player.getGoals().increase(Skills.CONSTRUCTION);
				player.closeInterfaces();
			} else {
				player.sm("You will need: 2 oak planks and 2x teak plank to build a Dining bench.");
			}
		}
	return;
	}
}
