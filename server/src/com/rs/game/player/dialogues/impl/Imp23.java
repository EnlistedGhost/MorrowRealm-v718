package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Lonely
 */

public class Imp23 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendPlayerDialogue(9827, "Hey I think I found all your beads");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendNPCDialogue(npcId, 9827, "Oh great! Thanks.");
			break;
		case 0:
			stage = 1;
			sendPlayerDialogue(9827, "Well, a deal is a deal....");
			break;
		
		case 2:
			end();
			break;

		case 1:
			if (player.getInventory().getFreeSlots() < 1){
				stage = 2;
				sendNPCDialogue(npcId, 9827, "If you want this key you better make more space.");
				break;	
			}else {
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Yep, thanks "+ player.getDisplayName() +", here you go.");
			player.getInventory().addItem(1543, 1);
			player.getInventory().deleteItem(1470, 1);
			player.getInventory().deleteItem(1472, 1);
			player.getInventory().deleteItem(1474, 1);
			player.getInventory().deleteItem(1476, 1);
			player.xmasDrawer = 4;
			player.xmasBookcaseBig = 4;
			player.xmasBookcaseSmall = 4;
			player.xmasChest = 4;
			player.xmasBeadComplete = 1;
			}
			break;
		case 3:
			stage = 4;
			sendPlayerDialogue(9827, "Thanks!");
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