package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Lonely
 */

public class SantaCage2 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Please, hurry " + player.getDisplayName() +".");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
				sendPlayerDialogue(9827, "Yes Santa, I will get to it right away!");
				stage = 1;
				break;
		case 1:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}