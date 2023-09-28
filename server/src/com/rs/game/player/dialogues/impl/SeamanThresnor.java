package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;
//imports
import com.rs.game.player.Player;
import com.rs.game.player.content.Magic;
import com.rs.game.WorldTile;

public class SeamanThresnor extends Dialogue {
	
	@Override
	public void start(){
		sendEntityDialogue(IS_NPC, "Seaman Thresnor", 1, 9827, "Do you want to go on a trip to Karamja "+player.getDisplayName()+"?");
		stage = 0;
	}
	
	@Override
	public void run(int interfaceId, int componentId){
		if (stage == 0) {
			sendEntityDialogue(IS_NPC, "Seaman Thresnor", 1, 9827, "It'll cost you 30 coins.");
			stage = 1;
		} else if (stage == 1){
			sendOptionsDialogue("Please Select an Option",
				"Yes, please",
				"No, thank you.");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				sendEntityDialogue(IS_NPC, "Seaman Thresnor", 1, 9827, "Well I'm on a tight schedule, forget the 30 coins this time and climb on board.");
				stage = 4;
			}
			if (componentId == OPTION_2) {
				sendEntityDialogue(IS_NPC, "Seaman Thresnor", 1, 9827, "Alright, bye.");
				stage = 3;
			}
		} else if (stage == 3) {
			end();
		} else if (stage == 4) {
			player.setNextWorldTile(new WorldTile(2956, 3143, 1));
			end();
		}
	}
	
	@Override
	public void finish() {
		
	}
}