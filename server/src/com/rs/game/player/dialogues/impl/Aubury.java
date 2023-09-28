package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;
//imports
import com.rs.game.player.Player;
import com.rs.game.player.content.Magic;
import com.rs.game.WorldTile;
import com.rs.utils.ShopsHandler;

public class Aubury extends Dialogue {
	
	@Override
	public void start(){
		sendEntityDialogue(IS_NPC, "Aubury", 1, 9827, "Do you want to buy some runes "+player.getDisplayName()+"?");
		stage = 1;
	}
	
	@Override
	public void run(int interfaceId, int componentId){
		if (stage == 1){
			sendOptionsDialogue("Please Select an Option",
				"Yes, please.",
				"No thanks.",
				"Can you teleport me to the rune essence?");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				sendEntityDialogue(IS_NPC, "Aubury", 1, 9827, "Here's what I have available.");
				stage = 4;
			}
			if (componentId == OPTION_2) {
				sendEntityDialogue(IS_NPC, "Aubury", 1, 9827, "Alright, bye.");
				stage = 3;
			}
			if (componentId == OPTION_3) {
				sendEntityDialogue(IS_NPC, "Aubury", 1, 9827, "I'll do it this time, but I may ask for a favor in the future.");
				stage = 5;
			}
		} else if (stage == 3) {
			end();
		} else if (stage == 4) {
			ShopsHandler.openShop(player, 11);
			end();
		} else if (stage == 5) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2911, 4832, 0));
			end();
		}
	}
	
	@Override
	public void finish() {
		
	}
}