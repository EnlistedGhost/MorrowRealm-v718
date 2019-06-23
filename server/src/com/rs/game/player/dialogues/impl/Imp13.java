package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Lonely
 */

public class Imp13 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendPlayerDialogue(9827, "I was able to get your food for you");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendNPCDialogue(npcId, 9827, "Finally, I'm starving up here!");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "Gimme my food!");
			player.getInventory().deleteItem(15430, 28);
			player.getInventory().deleteItem(15431, 28);
			break;
		case 1:
			stage = 2;
			sendPlayerDialogue(9827, "Jeez! Maybe people would be nicer if you weren't so mean");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Just take this and get out of my sight.");
			player.getInventory().addItem(1546, 1);
			break;
		case 3:
			stage = 4;
			sendPlayerDialogue(9827, "Thanks.");
			player.xmasFoodReceived = 1;
			player.xmasWine = 2;
			player.xmasYule = 2;
			break;
		case 4:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}