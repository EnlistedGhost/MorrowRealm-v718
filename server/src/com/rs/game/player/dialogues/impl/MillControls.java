package com.rs.game.player.dialogues.impl;

import com.rs.game.player.Player;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.WindMills;
import com.rs.game.Animation;

public class MillControls extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("Ready to process your wheat into flour?", "Yes, grind it up!",
					"Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {

		if (componentId == OPTION_1) {
			WindMills.loadMillStatus(player);
			int player_output_STATUS = WindMills.milloutputSTATUS;
			int player_hopper_STATUS = WindMills.millhopperSTATUS;

			// Remove empty bin and render filled bin (object)
			//
			// CLEANED: (Leftovers for previous code reference)
			// World.spawnObject(new WorldObject(1782, 10, 0, 2632, 3385, 0), true);
			if (WindMills.millplayerNAME == "NOT_REGISTERED") {
				WindMills.writeMillStatus(player, player.getDisplayName(), 0, 0, "Ardougne");
			}
			if (player_hopper_STATUS > 0) {
				// Process flour function
				player.lock(2);
				player.setNextAnimation(new Animation(9497));
				player_hopper_STATUS --;
				// Inform user that toggle is thrown and hopper is usable again
				WindMills.writeMillStatus(player, player.getDisplayName(), player_hopper_STATUS, player_output_STATUS, "Ardougne");
				player.getPackets().sendGameMessage("The mill grinds up the wheat, you can now load the hopper again.");
				end();
			} else {
				sendDialogue("Nothing is in the hopper",
					"you must load wheat into the hopper first.");
			}
		} else {
			end();
		}
	}

	@Override
	public void finish() {

	}

}