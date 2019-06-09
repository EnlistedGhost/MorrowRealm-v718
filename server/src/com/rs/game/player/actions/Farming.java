package com.rs.game.player.actions;

import com.rs.game.Animation;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

public class Farming {
	
public static int RAKE = 5341;
public static final Animation USERAKE = new Animation(2273);

public enum patches {
	
	FLOWER_PATCH("Flower Patch", 1, 100, "rake"),
	HERB_PATCH("Herb Patch", 50, 200, "rake"), 
	ALLOTMENT("Allotment", 75, 300, "rake");
	
	private String patchType;
	private int level;
	private int xp;
	private String actionType;
	
	patches(String patchType, int level, int xp, String actionType) {
		this.patchType = patchType;
		this.level = level;
		this.xp = xp;
		this.actionType = actionType;
	}
	public String getPatchType() {
		return patchType;
	}
	public int getReqLevel() {
		return level;
	}
	public int getXp() {
		return xp;
	}
	public String getActionType() {
		return actionType;
	}
}

	public static void startRake(Player player, String patch) {
		for (patches p : patches.values()) {
			if (p.patchType.equalsIgnoreCase(patch)) {
				if (player.getSkills().getLevel(Skills.FARMING) >= p.level) {
					if (p.actionType.equals("rake")) {
						if (player.getInventory().containsItem(RAKE, 1)) {
							player.lock(4);
							player.sm("You "+p.actionType+" the "+p.patchType+".");
							player.setNextAnimation(USERAKE);
							player.getSkills().addXp(Skills.FARMING, p.xp);
							player.getGoals().increase(Skills.FARMING);
						} else {
							player.sm("You must first have a rake to farm this!");
						}
						return;
					}
				} else {
					player.sm("You must have atleast "+p.level+" farming to "+p.actionType+" this!");
				}
			}
		}
	}
}
