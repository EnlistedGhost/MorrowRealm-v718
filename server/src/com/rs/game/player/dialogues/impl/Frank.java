package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;

public class Frank extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		if (player.getInventory().containsItem(1917, 1)) {
			player.getInventory().deleteItem(1917, 1);
			player.getInventory().refresh();
		sendEntityDialogue(
				SEND_3_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Thanks for the beer buddy, i know why are here you want the",
						"passport license for ned to get his rights back to ",
						"use his boat, i understand and i've heard about is problem."}, IS_NPC, npcId,
				9827);
		stage = -1;
		}
		else {
		player.sm("Hand Frank a beer, buy one from bar near you from the Bartender.");	
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			player.getInventory().addItem(9083,1);
			player.getInventory().refresh();
			sendEntityDialogue(SEND_1_TEXT_CHAT, new String[] { "",
			"Redbeard Frank hands you the ship passport for Klarense." },
			IS_ITEM, 9083, 1);
			stage = 1;
		}
		if (stage == 1) {
			player.closeInterfaces();
			player.getInterfaceManager().closeChatBoxInterface();
	}
	}
	@Override
	public void finish() {

	}

}
