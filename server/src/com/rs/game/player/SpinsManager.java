package com.rs.game.player;

import com.rs.utils.Utils;

public class SpinsManager {

	private transient Player player;
	public SpinsManager(Player player) {
		this.player = player;
	}

	public void addSpins() {
		if (player.getLastSpinsReceived() < Utils.currentTimeMillis()) {
			player.setLastSpinsReceived(Utils.currentTimeMillis() + 86400000);// 1 Day
			player.setSpins(player.getSpins() + 2);
			player.sendMessage("You have received 2 free spins for the Squeal of Fortune.");
		}
	}

}