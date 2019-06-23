package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.controlers.TutorialIsland;
import com.rs.game.WorldTile;

public class Zabeth2 extends Dialogue {
	
	/**
	 * @author Mario (AlterOPS)
	 **/

	private int npcId;

  @Override
	public void start() {
	npcId = (Integer) parameters[0];
			stage = -1;
			sendNPCDialogue(npcId, 9827, "Do you have my drink?");
	}

  @Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 1;
			sendOptionsDialogue("Zabeth", "From where can I get it?",
					"No.");
			break;
		case 1:
			switch(componentId) {
			case OPTION_1:
			stage = 2;
			sendNPCDialogue(npcId, 9827, "I don't know, ask Grim.");
			end();
				break;
			case OPTION_2:
			default:
				end();
				break;
			}
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