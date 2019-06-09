package com.rs.game.player.content;

import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;

public class MaxedUser {

	public static boolean isCompletionist(Player player) {
		return player.getSkills().getTotalLevel(player) == 2496;
	}
	public static boolean isMaxed(Player player) {
		return player.getSkills().getTotalLevel(player) >= 2475;
	}
	
	public static void CheckCompletionist(Player player) {
		if (!isCompletionist(player)) {
			player.sendMessage("You must have all 99 stats and 120 dung to get the Comp Cape.");
			return;
		}
		if (player.hasClaimedCompCape()) {
			player.sendMessage("You have already claimed your Completionist Cape!");
			return;
		}
		player.setClaimedCompCape(true);
		World.sendWorldMessage("<img=7><col=FF6600>News: "+player.getDisplayName()+" has earned their first Completionist Cape!", false);
		player.sendMessage("You only get one of these so dont lose it!");
		player.getInventory().addItem(20770, 1);
		player.getInventory().addItem(20771, 1);
	}
	

	public static void CheckMaxed(Player player) {
		if (!isMaxed(player)) {
			player.sendMessage("You must have atleast 99 in all stats to claim the Max Cape.");
			return;
		}
		
		if (player.hasClaimedMaxCape()) {
			player.sendMessage("You've already claimed a Max Cape.");
			return;
		}
		player.setClaimedMaxCape(true);
		player.getInventory().addItem(20767, 1);
		player.getInventory().addItem(20768, 1);
		player.sendMessage("You only get one of these so dont lose it!");
		World.sendWorldMessage("<img=7><col=FF6600>News: "+player.getDisplayName()+" has earned their first Max Cape!", false);
	}
		
}
