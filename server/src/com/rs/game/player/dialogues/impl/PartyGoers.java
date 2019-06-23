package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Lonely
 */

public class PartyGoers extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Merry Christmas!");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendPlayerDialogue(9827, "Merry Christmas!");
			break;
		case 0:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}