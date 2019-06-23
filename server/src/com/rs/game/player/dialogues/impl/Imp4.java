package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Lonely
 */

public class Imp4 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		if (player.getInventory().getFreeSlots() < 1 && player.xmas4Complete == 0) {
			stage = 9;
			sendNPCDialogue(npcId, 9827, "Before you bother talking to me, get more inventory space.");
		} else if(player.xmas4Complete == 1){
			stage = 9;
			sendNPCDialogue(npcId, 9827, "See ya around.");
		}else {
		sendPlayerDialogue(9827, "Hey there!");
	}
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendNPCDialogue(npcId, 9827, "Well hello there, how are you");
			break;
		case 0:
			stage = 1;
			sendPlayerDialogue(9827, "I'm okay and you?");
			break;
		case 1:
			stage = 2;
			sendNPCDialogue(npcId, 9827, "Good, how can I help you today?");
				break;
		case 2:
			stage = 3;
			sendPlayerDialogue(9827, "I need your key to help free Santa.");
			break;
		case 3:
			stage = 4;
			sendNPCDialogue(npcId, 9827, "Oh, okay sure.");
			break;
		case 4:
			stage = 5;
			player.getInventory().addItem(1548, 1);
			sendNPCDialogue(npcId, 9827, "Here you go.");
			player.xmas4Complete = 1;
			break;
		case 5:
			stage = 6;
			sendPlayerDialogue(9827, "Wow, that was easier than I thought, thanks.");
			break;
		case 6:
			stage = 7;
			sendNPCDialogue(npcId, 9827, "Eh, it's the holidays, why make it tougher than it needs to be. We both know in the end you'll get the key");
			break;
		case 7:
			stage = 8;
			sendPlayerDialogue(9827, "Well it saves time I guess, thanks again.");
			break;
		case 8:
			stage = 9;
			sendNPCDialogue(npcId, 9827, "No problem, good luck");
			break;
		case 9:
			end();
			break;
			
		}
	}

	@Override
	public void finish() {
		
	}
}