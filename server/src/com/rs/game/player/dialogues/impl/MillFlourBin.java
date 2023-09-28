package com.rs.game.player.dialogues.impl;

import java.util.TimerTask;

import com.rs.game.player.Player;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.WindMills;
import com.rs.game.player.Skills;
import com.rs.game.Animation;
import com.rs.cores.CoresManager;
import com.rs.utils.Utils;

public class MillFlourBin extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("Ready to gather your milled wheat?", "Yes, I want my flour!",
					"Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {

		if (componentId == OPTION_1) {
			WindMills.loadMillStatus(player);
			if (WindMills.millplayerNAME == "NOT_REGISTERED") {
				WindMills.writeMillStatus(player, player.getDisplayName(), 0, 0, "Ardougne");
			}
			if (player.getInventory().getFreeSlots() >= 0) {
				int player_output_STATUS = WindMills.milloutputSTATUS;
				int player_hopper_STATUS = WindMills.millhopperSTATUS;
				if (player_output_STATUS > 0) {
					int loop_POT_count = 1;
					int empty_POT_ID = 1931;
					int full_POT_ID = 1933;
					int player_POT_COUNT = player.getInventory().getItems().getNumberOf(empty_POT_ID);
					// Process flour function
					while (player_POT_COUNT >= loop_POT_count) {
						player.lock(2);
						player.setNextAnimation(new Animation(3151));
						player.getInventory().deleteItem(empty_POT_ID, 1);
						player.getInventory().addItem(full_POT_ID, 1);
						player.getSkills().addXp(Skills.COOKING, 1);
						player.getPackets().sendGameMessage("You have obtained a pot of flour.");
						player_output_STATUS --;
						WindMills.writeMillStatus(player, player.getDisplayName(), player_hopper_STATUS, player_output_STATUS, "Ardougne");
						loop_POT_count ++;
					}
					if (player_POT_COUNT < 1) {
						player.getPackets().sendGameMessage("You have ran out of pots in which to fill with flour");
					}
					end();
				} else {
					player.getPackets().sendGameMessage("You have no more available flour in the bin.");
					end();
				}
			} else {
				player.getPackets().sendGameMessage("ERROR 001: Contact MorrowRealm admins!");
				end();
			}
		} else {
			end();
		}
	}

	@Override
	public void finish() {

	}

}