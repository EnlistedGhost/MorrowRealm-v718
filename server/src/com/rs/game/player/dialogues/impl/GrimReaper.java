package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.player.controlers.TutorialIsland;
import com.rs.game.WorldTile;

public class GrimReaper extends Dialogue {
	
	/**
	 * @author Mario (AlterOPS)
	 **/

	private int npcId;

  @Override
	public void start() {
	npcId = (Integer) parameters[0];
			stage = -1;
			sendNPCDialogue(npcId, 9827, "What do you want?");
	}

  @Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 1;
			sendPlayerDialogue(9827, "Do you know where I get a drink?");
			break;
		case 1:
			stage = 2;
			sendNPCDialogue(npcId, 9827, "Yes, I'll give you a drink if you bring me three Vampyre dust.");
			break;
		case 2:
			stage = 3;
			sendPlayerDialogue(9827, "Why Vampyre dust?");
			break;
		case 3:
			stage = 4;
			sendNPCDialogue(npcId, 9827, "That stuff gives me the kick.");
			break;
		case 4:
			stage = 5;
			sendPlayerDialogue(9827, "Ok... Where can I find it?");
			break;
		case 5:
			stage = 6;
			sendNPCDialogue(npcId, 9827, "Just search in the cupboards or beds.");
			break;
		case 6:
			stage = 7;
			sendPlayerDialogue(9827, "Ok, I will find them.");
			break;
		case 7:
			stage = 8;
			sendNPCDialogue(npcId, 9827, "Happy Halloween!");
			break;
		case 8:
			stage = 9;
			sendPlayerDialogue(9827, "Happy Halloween!");
                        player.drink =2;
			player.dust1 = 0;
			player.dust2 = 0;
			player.dust3 = 0;
			end();
			break;
		}
	}

  @Override
	public void finish() {

	}

}
