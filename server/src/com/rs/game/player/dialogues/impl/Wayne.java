package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class Wayne extends Dialogue {

	@Override
	public void start() {
		sendEntityDialogue(SEND_3_TEXT_CHAT, new String[] { NPCDefinitions.getNPCDefinitions(581).name,
			"Hello there, "+player.getDisplayName()+", I sell various",
			"<col=0000FF>weapons, armour, and accessories</col>.",
			"What may i get for ya today?"
		}, IS_NPC, 581, 9827);
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			sendOptionsDialogue("Select an Option",
					"Full Helmets",
					"Platebodies",
					"Platelegs",
					"Gloves/Boots",
					"<col=00FF00>Pg. 2");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				ShopsHandler.openShop(player, 67);
				end();
			}
			if (componentId == OPTION_2) {
				ShopsHandler.openShop(player, 68);
				end();
			}
			if (componentId == OPTION_3) {
				ShopsHandler.openShop(player, 69);
				end();
			}
			if (componentId == OPTION_4) {
				ShopsHandler.openShop(player, 70);
				end();
			}
			if (componentId == OPTION_5) {
				sendOptionsDialogue("Select an Option", 
					"Accessories",
					"Defenders",
					"Melee Weapons 1",
					"Melee Weapons 2",
					"<col=00FF00>Pg. 1");
				stage = 3;
			}
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				ShopsHandler.openShop(player, 71);
				end();
			}
			if (componentId == OPTION_2) {
				ShopsHandler.openShop(player, 72);
				end();
			}
			if (componentId == OPTION_3) {
				ShopsHandler.openShop(player, 73);
				end();
			}
			if (componentId == OPTION_4) {
				ShopsHandler.openShop(player, 74);
				end();
			}
			if (componentId == OPTION_5) {
				sendOptionsDialogue("Select an Option",
						"Full Helmets",
						"Platebodies",
						"Platelegs",
						"Gloves/Boots",
						"<col=00FF00>Pg. 2");
				stage = 2;
			}
		}
		
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
