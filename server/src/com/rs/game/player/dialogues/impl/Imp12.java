package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Lonely
 */

public class Imp12 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Do you have my food yet?");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendPlayerDialogue(9827, "Not yet, I'm working on it.");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "If you want this key you better move a little faster than that.");
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