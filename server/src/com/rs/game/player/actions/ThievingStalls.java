package com.rs.game.player.actions;

import java.io.Serializable;

import com.rs.game.Animation;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;

public class ThievingStalls implements Serializable {

	private static final long serialVersionUID = 1153410720477418692L;
	private Player player;
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public enum Stalls {
		
		BEGINNER_STALL(4875, 4, 1, 50, 50, 4000),
		NOVICE_STALL(4876, 4, 50, 75, 70, 6000),
		EXPERT_STALL(4877, 4, 75, 90, 90, 8000),
		ADVANCED_STALL(4878, 4, 90, 99, 110, 10000);
		
		int id, delay, minLvl, maxLvl, exp, reward;
		
		Stalls(int id, int delay, int minLvl, int maxLvl, int exp, int reward) {
			this.id = id;
			this.delay = delay;
			this.minLvl = minLvl;
			this.maxLvl = maxLvl;
			this.exp = exp;
			this.reward = reward;
		}
		
	}
	
	public void handleStall(Player player, int stallId) {
		for (Stalls s : Stalls.values()) {
			if (stallId == s.id) {
				if (player.getSkills().getLevel(Skills.THIEVING) < s.minLvl) {
					player.sendMessage("You need atleast "+s.minLvl+" Thieving to steal from this stall!");
					return;
				}
				if (player.getSkills().getLevel(Skills.THIEVING) >= s.maxLvl) {
					player.sendMessage("You can no longer benefit from stealing here.");
					return;
				}
				player.getInventory().addItem(995, s.reward);
	            player.getSkills().addXp(Skills.THIEVING, s.exp);
	            player.lock(s.delay);
	            player.getPackets().sendGameMessage("You successfully thieve from the stall.");
	            player.setNextAnimation(new Animation(881));
	            player.getGoals().increase(Skills.THIEVING);
	            return;
			}
		}
		return;
	}
	
	
}
