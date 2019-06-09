package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;


public class Cleanbank extends Dialogue {

	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
			sendOptionsDialogue("Are you sure you want to reset your bank?", "Yes", "No");
			stage = 1;
		}
	}

	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1)
				player.getBank().reset();
				player.getBank().refreshItems();
			end();
			if (componentId == OPTION_2)
				end();
		}
	}

	@Override
	public void finish() {

	}

}