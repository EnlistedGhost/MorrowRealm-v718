package com.rs.game.player.dialogues.impl;

import com.rs.game.player.controlers.dungeoneering.DungLobby;
import com.rs.game.player.dialogues.Dialogue;

/**
 * 
 * @author Tyler
 * 
 *
 */

public class EnteringDung extends Dialogue {

	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
			sendOptionsDialogue("Would you like to enter the lobby?",
					"Yes please.",
					"No thanks.",
					"Lobby information.",
					"How many tokens do I have?");
			stage = 1;
	}
	}

	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.getControlerManager().startControler(
						"RuneDungLobby", 1);
				stage= 2;
			}
			if (componentId == OPTION_2) {
				stage = 2;				
		}
			if (componentId == OPTION_3) {
				player.getDialogueManager().startDialogue("SimpleMessage", "There are currently " 
			+ DungLobby.getLobby().getPlayerSize() + " players in the lobby.");
				stage = 1;
		}
			if (componentId == OPTION_4) {
				player.getDialogueManager().startDialogue("SimpleMessage", "Tokens are currently in construction.");
				stage = 1;
		}
		}
		if (stage == 2) {
			end();
			player.getInterfaceManager().closeChatBoxInterface();
		}
	}

	@Override
	public void finish() {

	}

}