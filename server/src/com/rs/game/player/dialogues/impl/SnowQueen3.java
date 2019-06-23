package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Lonely
 */

public class SnowQueen3 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Thank you " + player.getDisplayName() +" for finding Santa",
				"and saving Christmas");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
				sendPlayerDialogue(9827, "No problem, I'm just glad everyone can have a Christmas now.");
				stage = 1;
				break;
		case 1:
			sendNPCDialogue(npcId, 9827, "And it's all thanks to you " + player.getDisplayName() +".");
			stage = 2;
			break;
		case 2:
			sendNPCDialogue(8540, 9827, "Please take this as a token of our gratitude.");
			player.getInventory().addItem(25202, 1);
			
			stage = 3;
			break;
		case 3:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}