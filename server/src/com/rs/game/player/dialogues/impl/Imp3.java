package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Lonely
 */

public class Imp3 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		if (player.getInventory().getFreeSlots() < 1 && player.xmasQuizComplete == 0) {
			stage = 50;
			sendNPCDialogue(npcId, 9827, "Before you bother talking to me, get more inventory space.");
		} else if(player.xmasQuizComplete == 1) {
			stage = 50;
			sendNPCDialogue(npcId, 9827, "I think this business is done here.");
		} else {
			sendPlayerDialogue(9827, "Hey! Stop! Hand over your key.");
	}
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendNPCDialogue(npcId, 9827, "My key?");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "Ah, you must mean the key to free santa.");
			break;
		case 1:
				sendPlayerDialogue(9827, "Yeah.. so can you give me it?");
				stage = 2;
				break;
			
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Possibly? How well do you think you know Lonely Souls?");
			break;
		case 3:
			stage = 4;
			sendPlayerDialogue(9827, "I think I know a lot about here, why do you ask?");
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "If you can answer my trivia correctly, I'll give you my key.");
			break;
		case 5:
			stage = 6;
			sendPlayerDialogue(9827, "Sure, bring it on.");

			break;
		case 6:
			stage = 7;
			sendNPCDialogue(npcId, 9827, "What date was Lonely Souls released?");
			break;
		case 7:
			stage = 8;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"March 18, 2014",
					"April 4, 2014",
					"May 15, 2014",
					"July 4th, 2014");
			break;
		case 8:
			if (componentId == OPTION_1) {
				stage = 50;
				sendNPCDialogue(npcId, 9827, "Ha! You mess up on the first question. How pathetic.");
			} else if (componentId == OPTION_2) {
				stage = 9;
				sendNPCDialogue(npcId, 9827, "(1/3) Wow, I'm surprised you actually knew that, next question");
			} else if (componentId == OPTION_3) {
				stage = 50;
				sendNPCDialogue(npcId, 9827, "Ha! You mess up on the first question. How pathetic.");
			} else if (componentId == OPTION_4){
				stage = 50;
				sendNPCDialogue(npcId, 9827, "Ha! You mess up on the first question. How pathetic.");
			} break;
		case 9:
			stage = 10;
			sendNPCDialogue(npcId, 9827, "Who was Jack Frost talking to when you found Santa?");
			break;
		case 10:
			stage = 11;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"Queen of Snow",
					"Warrior Snowman",
					"Dragon Snowman",
					"Sorcerer Snowman");
			break;
		case 11:
			if (componentId == OPTION_1) {
				stage = 50;
				sendNPCDialogue(npcId, 9827, "Aww, so so close. . . . . but no.");
			} else if (componentId == OPTION_2) {
				stage = 50;
				sendNPCDialogue(npcId, 9827, "Aww, so so close. . . . . but no.");
			} else if (componentId == OPTION_3) {
				stage = 12;
				sendNPCDialogue(npcId, 9827, "(2/3) Well how about that, you actually pay attention. I'm impressed.");
			} else if (componentId == OPTION_4) {
				stage = 50;
				sendNPCDialogue(npcId, 9827, "Aww, so so close. . . . . but no.");
			} break;
		case 12:
			stage = 13;
			sendNPCDialogue(npcId, 9827, "How many of my brothers including me do you have to find to free Santa?");
			break;
		case 13:
			stage = 14;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"1",
					"2",
					"3",
					"4");
			break;
		case 14:
			if (componentId == OPTION_1) {
				stage = 50;
				sendNPCDialogue(npcId, 9827, "Yeah, you wish it was that easy. Ha!");
			} else if (componentId == OPTION_2) {
				stage = 50;
				sendNPCDialogue(npcId, 9827, "Yeah, you wish it was that easy. Ha!");
			} else if (componentId == OPTION_3) {
				stage = 50;
				sendNPCDialogue(npcId, 9827, "Yeah, you wish it was that easy. Ha!");
			} else if (componentId == OPTION_4) {
					stage = 50;
					player.getInventory().addItem(1547, 1);
				sendNPCDialogue(npcId, 9827, " (3/3) Well, now you only have to worry about 3, here you go, you passed.");
				player.xmasQuizComplete = 1;
			} break;
			
		case 50:
			end();
			break;
			
		}
	}

	@Override
	public void finish() {
		
	}
}