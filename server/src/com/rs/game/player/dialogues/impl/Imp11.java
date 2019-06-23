package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Lonely
 */

public class Imp11 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendPlayerDialogue(9827, "Finally I've found you! Hand over your key to Santa's Cage!");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendNPCDialogue(npcId, 9827, "Excuse me?");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "Who do you think you are to 'demand' me to do anything?");
			break;
		case 1:
			stage = 2;
			sendPlayerDialogue(9827, "I'm here to free Santa!");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Let me guess, you want my key?");
		case 3:
			stage = 4;
			sendPlayerDialogue(9827, "Not much of a guess, I said that when I first got here.");
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "Well too bad, we all want something in the world.");
			break;
		case 5:
			stage = 6;
			sendPlayerDialogue(9827, "*Sneakingly* Oh I understand, what is the thing that you want most in the world?");
			break;
		case 6:
			stage = 7;
			sendNPCDialogue(npcId, 9827, "Well...when I was running away from Jack's Mansion, I had a wiff of that feast food, and it smelt so good!");
			break;
		case 7:
			stage = 8;
			sendPlayerDialogue(9827, "What If I could get you a piece of the food, would you trade it for your key?");
			break;
		case 8:
			stage = 9;
			sendNPCDialogue(npcId, 9827, "If you can get me a Yule Log and Mulled Wine, it's a deal.");
			break;
		case 9:
			stage = 10;
			sendPlayerDialogue(9827, "I'll see what I can do");
			player.xmasFoodGathering = 1;
			break;
		case 10:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}