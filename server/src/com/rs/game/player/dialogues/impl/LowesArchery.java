package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class LowesArchery extends Dialogue {

	@Override
	public void start() {
		sendEntityDialogue(SEND_3_TEXT_CHAT, new String[] { NPCDefinitions.getNPCDefinitions(550).name,
				"Hello there, "+player.getDisplayName()+", I sell",
				"<col=0000FF>ranged armour, bows, and ammunition.</col>.",
				"What may i get for ya today?"
		}, IS_NPC, 550, 9827);
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			sendOptionsDialogue("Lowes Archery Shop", 
					"Bows, Arrows, & Bolts", 
					"Ranged Armours");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				end();
				ShopsHandler.openShop(player, 75);
			}
			if (componentId == OPTION_2) {
				end();
				ShopsHandler.openShop(player, 78);
			}
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
