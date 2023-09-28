package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;
//imports
import com.rs.game.player.Player;
import com.rs.game.player.content.Magic;
import com.rs.game.WorldTile;

public class CustomsOfficerKaramja extends Dialogue {
	
	@Override
	public void start(){
		sendEntityDialogue(IS_NPC, "Customs Officer", 1, 9827, "Is there something I can assist you with "+player.getDisplayName()+"?");
		stage = 1;
	}
	
	@Override
	public void run(int interfaceId, int componentId){
		if (stage == 1){
			sendOptionsDialogue("Please Select an Option",
				"Can I board this ship?",
				"No thanks.");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				sendEntityDialogue(IS_NPC, "Customs Officer", 1, 9827, "You need to be searched before you can board.");
				stage = 4;
			}
			if (componentId == OPTION_2) {
				sendEntityDialogue(IS_NPC, "Customs Officer", 1, 9827, "Alright, bye.");
				stage = 3;
			}
		} else if (stage == 3) {
			end();
		} else if (stage == 4) {
			sendOptionsDialogue("Please Select an Option",
				"Why?",
				"What's the reason?");
			stage = 5;
		} else if (stage == 5) {
			sendEntityDialogue(IS_NPC, "Customs Officer", 1, 9827, "Because Asgarnia has banned the import of intoxicating spirits.");
			stage = 6;
		} else if (stage == 6) {
			sendOptionsDialogue("Please Select an Option",
				"Search away I have nothing to hide.",
				"Inspect me as much as you'd like.");
			stage = 7;
		} else if (stage == 7) {
			sendEntityDialogue(IS_NPC, "Customs Officer", 1, 9827, "Well I'm in a hurry to beat the bad weather, just get on board.");
			stage = 8;
		} else if (stage == 8) {
			player.setNextWorldTile(new WorldTile(3032, 3217, 1));
			end();
		}
	}
	
	@Override
	public void finish() {
		
	}
}