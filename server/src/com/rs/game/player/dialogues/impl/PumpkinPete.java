package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.controlers.TutorialIsland;
import com.rs.game.WorldTile;

public class PumpkinPete extends Dialogue {
	
	/**
	 * @author Mario (AlterOPS)
	 **/

	private int npcId;

  @Override
	public void start() {
	npcId = (Integer) parameters[0];
			stage = -1;
			sendNPCDialogue(npcId, 9827, "Happy Halloween!");
	}

  @Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 1;
			sendPlayerDialogue(9827, "Who are you?");
			break;
		case 1:
			stage = 2;
			sendNPCDialogue(npcId, 9827, "I am Pumpkin Pete and I have organized a huge party!");
			break;
		case 2:
			stage = 3;
			sendPlayerDialogue(9827, "How do I get there?");
			break;
		case 3:
			stage = 4;
			sendNPCDialogue(npcId, 9827, "Just go into the big red portal over there.");
			break;
		case 4:
			stage = 5;
			sendPlayerDialogue(9827, "Thanks.");
			break;
		case 5:
			stage = 6;
			sendNPCDialogue(npcId, 9827, "Bye.");
			break;
		case 6:
			stage = 7;
			sendPlayerDialogue(9827, "Bye and happy Halloween!");
			break;
		case 7:
			stage = 8;
			sendNPCDialogue(npcId, 9827, "Yeah.");
			end();
			break;
		}
	}

  @Override
	public void finish() {

	}

}