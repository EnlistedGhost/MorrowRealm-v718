package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class BettyMagic extends Dialogue {

	@Override
	public void start() {
		sendEntityDialogue(SEND_3_TEXT_CHAT, new String[] { NPCDefinitions.getNPCDefinitions(583).name,
			"Hello there, "+player.getDisplayName()+", I sell",
			"<col=0000FF>runes, mage armour, and staves</col>.",
			"What may i get for ya today?"
		}, IS_NPC, 583, 9827);
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			sendOptionsDialogue("Select an Option",
					"Staves / Runes",
					"Mage Armour",
					"Accessories",
					"<col=00FF00>Pg. 2");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				ShopsHandler.openShop(player, 76);
				end();
			}
			if (componentId == OPTION_2) {
				ShopsHandler.openShop(player, 77);
				end();
			}
			if (componentId == OPTION_3) {
				ShopsHandler.openShop(player, 71);
				end();
			}
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
