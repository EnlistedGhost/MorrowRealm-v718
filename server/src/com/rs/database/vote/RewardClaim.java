package com.rs.database.vote;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.content.custom.TitleHandler;
import com.rs.utils.Utils;

public class RewardClaim {

	public static boolean claimReward(Player player, VoteReward reward) {
		Item item = null;
		if (reward.getReward() == 0) {
			item = new Item(995, 5000000); // 1M Coins
		} else if (reward.getReward() == 1) {
			item = new Item(24155, 1); // Double Spin Ticket
		} else if (reward.getReward() == 2) {
			item = new Item(15069, 1); // Red Voting Hat
		} else if (reward.getReward() == 3) {
			item = new Item(15071, 1); // Blue Voting Hat
		} else if (reward.getReward() == 4) {
			item = new Item(299, 200); // 200 Mith seeds
		} else if (reward.getReward() == 5) {
			item = new Item(7947, 100); // x100 Monkfish
		} else if (reward.getReward() == 6) {
			item = new Item(21521, 75); // 75 Tiger Shark
		} else if (reward.getReward() == 7) {
			item = null;
		} else if (reward.getReward() == 8) {
			item = null;
		} else if (reward.getReward() == 9) {
			item = null;
		} else if (reward.getReward() == 10) {
			item = null;
		}
		
		if (item == null) {
			player.getPackets().sendGameMessage("Reward not found.");
			return false;
		}
		
		if (!player.getInventory().hasFreeSlots()) {
			player.sendMessage("Please make some room for your vote reward.");
			return false;
		}
		
		if (player.getInventory().getFreeSlots() < item.getAmount() && !item.isStackable()) {
			player.getBank().addItem(item.getId(), item.getAmount(), true);
			player.setLastVote(Utils.currentTimeMillis() + 43200000);
			player.getPackets().sendGameMessage("Thank you for voting! Your reward has been placed in your bank.");
			return true;
		}
		
		player.getInventory().addItem(item);
		player.getPackets().sendGameMessage("Thank you for voting! Your reward has been placed in your inventory.");
		player.setLastVote(Utils.currentTimeMillis() + 43200000);
		return true;
		
	}
	
}
