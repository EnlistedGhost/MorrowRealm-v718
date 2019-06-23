package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Lonely
 */

public class Imp21 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendPlayerDialogue(9827, "Hello!");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendNPCDialogue(npcId, 9827, "Mm yes, good day sire!");
			break;
		case 0:
			stage = 1;
			sendPlayerDialogue(9827, "I have come to get your part of the key",
					"to free Santa.");
			break;

		case 1:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Mmm, of course, why am I not surprised?");
			break;
		case 3:
			stage = 4;
			sendPlayerDialogue(9827, "Please, I will do what ever it takes.");
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "Oh come on now, not another begger.");
			break;
		case 5:
			stage = 6;
			sendNPCDialogue(npcId, 9827, "How about this, you help me and I help you...deal?");
			break;
		case 6:
			stage = 7;
			sendPlayerDialogue(9827, "And what is it you need help with?");
			break;
		case 7:
			stage = 8;
			sendNPCDialogue(npcId, 9827, "Well.... I've lost my balls.");
			break;
		case 8:
			stage = 9;
			sendPlayerDialogue(9827, "Your what?!");
			break;
		case 9:
			stage = 10;
			sendNPCDialogue(npcId, 9827, "Well, what you blokes call 'beads'. ");
			break;
		case 10:
			stage = 11;
			sendPlayerDialogue(9827, "Oh...uhh....weird....");
			break;
		case 11:
			stage = 12;
			sendNPCDialogue(npcId, 9827, "Anyway, I lost my 4 balls somewhere around this area, I was",
					"rummaging through human stuff trying to hide and I must",
					"have misplaced them.");
			break;
		case 12:
			stage = 13;
			sendPlayerDialogue(9827,"So you're saying if I find these beads you'll give me ",
					"your part of the key?");
			break;
		case 13:
			stage = 14;
			sendNPCDialogue(npcId, 9827, "Yes, now go before i change my mind.");
			player.xmasBookcaseBig = 1;
			player.xmasBookcaseSmall = 1;
			player.xmasChest = 1;
			player.xmasDrawer = 1;
			player.xmasBeadStarted = 1;
			break;
		case 14:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}