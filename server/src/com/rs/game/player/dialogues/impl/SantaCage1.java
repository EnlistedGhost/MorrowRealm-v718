package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Lonely
 */

public class SantaCage1 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendPlayerDialogue(9827, "Oh my, Santa you're alive!");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendPlayerDialogue(9827, "What did Jack do to you?!?");
			break;
			
		case 0:
			stage = 1;
			//sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Sure! What's wrong?", 
					//"I'm far to busy.", "Nevermind.");
			sendNPCDialogue(npcId, 9827, "I'm so glad you came " + player.getDisplayName() + ", Jack",
					"hates Christmas so much he santa-napped",
					"me and locked me in this cage.");
			break;
			
		case 1:
			stage = 111;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "What can I do to help??", 
					"Why should I?", "No thank you.");
			break;
		case 111:
			if(componentId == OPTION_1) {
				sendPlayerDialogue(9827, "What can I do to get you out Santa?");
				stage = 4;
				break;
			} else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "Why should I help you? I never",
						"got my InfoCoins I asked for!");
				stage = 2;
				break;
			} else if(componentId == OPTION_3) {
				stage = 3;
				sendPlayerDialogue(9827, "No thanks, let someone else help you.");
				break;
			} 
		case 2:
			stage = 22;
			sendNPCDialogue(npcId, 9827, "I will try and make that up to you",
					"but until then, please help save Christmas!");
			break;
		case 22:
			sendPlayerDialogue(9827, "What can I do to get you out Santa?");
			stage = 4;
			break;
		case 3:
			end();
			break;
		case 33:
			stage = 11;
			end();
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "I overheard Jack talking to his Guard about how he has",
					"4 of his minions scattered with pieces of the key");
			break;
		case 5:
			stage = 6;
			sendNPCDialogue(npcId, 9827, "Please " + player.getDisplayName() + " find them and get ",
					"the key to free me");
			break;
		case 6:
			stage = 7;
			sendPlayerDialogue(9827, "Do you know where the imps are located?");
			break;
		case 7:
			stage = 8;
			sendNPCDialogue(npcId, 9827, "I overheard Jack say brief of where they might have ran to. ",
					"I know I heard him say one of them hid",
					"on White Wolf Mountain.");
			break;
		case 8:
			stage = 9;
			sendNPCDialogue(npcId, 9827, "The second one I would check out ",
					"somewhere around East Falador.",
					"Apparently he's very fascinated with fountains.");
			break;
		case 9:
			stage = 10;
			sendNPCDialogue(npcId, 9827, "Another Imp went way out in the wild",
					"where you players can stretch your legs training a certain skill,");
			break;
		case 10:
			stage = 11;
			sendNPCDialogue(npcId, 9827, "and if my memory serves me right, the last imp",
					"went somewhere around Edgeville.");
			break;
		case 11:
			stage = 14;
			sendNPCDialogue(npcId, 9827, "Please, hurry " + player.getDisplayName() +".");
			break;
		case 14:
				sendPlayerDialogue(9827, "Yes Santa, I will get to it right away!");
				player.christmas = 2;
				stage = 33;
				break;
		}
	}

	@Override
	public void finish() {
		
	}
}