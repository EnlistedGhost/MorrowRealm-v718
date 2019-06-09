package com.rs.game.player.dialogues.impl;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;
import com.rs.cache.loaders.NPCDefinitions;

public class SummoningShop extends Dialogue {

	@Override
	public void start() {
		stage = 1;
		sendEntityDialogue(Dialogue.SEND_1_TEXT_CHAT, new String[] {
				NPCDefinitions.getNPCDefinitions(6970).name,
				"Would you like to see my shops?" }, IS_NPC, 6970, 9847);
	}

	public void run(int interfaceId, int componentId) {
		int option;
		if (stage == 1) {
			sendOptionsDialogue("Choose a shop!",
					"Shop 1 - Starter Ingredients",
					"Shop 2 - Intermediate Ingredients",
					"Shop 3 - Master Ingredients");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				ShopsHandler.openShop(player, 23);
				end();
			}
			if (componentId == OPTION_2) {
				ShopsHandler.openShop(player, 24);
				end();
			}
			if (componentId == OPTION_3) {
				ShopsHandler.openShop(player, 25);
				end();
			}
		}
	}

	@Override
	public void finish() {

	}

}