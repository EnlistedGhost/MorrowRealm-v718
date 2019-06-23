package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Lonely
 */

public class Imp24 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendPlayerDialogue(9827, "Thanks again!");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendNPCDialogue(npcId, 9827, "You didn't get anything from me.");
			break;
		case 0:
			stage = 2;
			sendPlayerDialogue(9827, "Okay........");
			break;
		
		case 2:
			end();
			break;

		
		}
	}

	@Override
	public void finish() {
		
	}
}