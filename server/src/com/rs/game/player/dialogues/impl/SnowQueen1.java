package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Lonely
 */

public class SnowQueen1 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Please! Would you please help us " + player.getDisplayName() + ".");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
				sendPlayerDialogue(9827, "What's wrong?");
				stage = 1;
				break;
		case 1:
			sendNPCDialogue(npcId, 9827, "Santa! Santa he's missing! He was snatched away while I was speaking with him!");
			stage = 2;
			break;
		case 2:
			sendPlayerDialogue(9827, "What?! Who would do such a thing!");
			stage = 3;
			break;
		case 3:
			sendNPCDialogue(npcId, 9827, "Jack! The evil Jack Frost froze our beloved",
					"Guardian and snatched Santa! Everyone ran and now our",
					"feast is ruined!");
			stage = 4;
			break;
		case 4:
			sendPlayerDialogue(9827, "Well, how I can help find Santa Your Majesty?");
			stage = 5;
			break;
		case 5:
			sendNPCDialogue(npcId, 9827, "Jack Frost demanded a ransom of 250 SantaCoins...",
					"It's just absurd and especially when those Corrupt Snowmen stole mine!",
					"They live in their cozy snowpiles, would you help?");
			stage = 6;
			break;
		case 6:
			sendPlayerDialogue(9827, "Gather snowballs from snowpiles and vanquish Corrupt Snowmen, Got it!");
			stage = 7;
			break;
		case 7:
			sendNPCDialogue(npcId, 9827, "Oh, you have my eternal gratitude "+ player.getDisplayName() +"!",
					"");
			stage = 111;
			player.christmas = 1;
			break;
		case 111:
			stage = 4;
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}