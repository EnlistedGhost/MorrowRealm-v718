package com.rs.game.player.dialogues.impl;

import com.rs.game.player.Player;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.WindMills;
import com.rs.game.Animation;

public class MillHopper extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("Seems clear, want to load some wheat?", "Yes, load a bundle of wheat.",
				"Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		// Init
		int player_WHEAT_ID = 1947;
		// Function
		if (componentId == OPTION_1) {
			WindMills.loadMillStatus(player);
			int player_outputSTATUS = WindMills.milloutputSTATUS;
			int player_hopperSTATUS = WindMills.millhopperSTATUS;
			if (WindMills.millplayerNAME == "NOT_REGISTERED") {
				WindMills.writeMillStatus(player, player.getDisplayName(), 0, 0, "Ardougne");
			}
			if (player_outputSTATUS > 29) {
				sendDialogue("Flour bin is full",
					"collect flour from the bin below first.");
			}
			if (player_hopperSTATUS < 1) {
				if (player.getInventory().containsItem(player_WHEAT_ID, 1)) {
					player.lock(2);
					player.setNextAnimation(new Animation(3151));
					sendDialogue("You load some wheat into the hopper",
						"use the controls to process the load.");
					player.getInventory().deleteItem(player_WHEAT_ID, 1);
					player_hopperSTATUS ++;
					player_outputSTATUS ++;
					WindMills.writeMillStatus(player, player.getDisplayName(), player_hopperSTATUS, player_outputSTATUS, "Ardougne");
				} else {
					sendDialogue("Out of wheat",
					"you have no more available wheat to load.");
				}
			} else {
				sendDialogue("Hopper is full",
					"process the load using the controls.");
			}
		} else {
			end();
		}
	}

	@Override
	public void finish() {

	}

}
