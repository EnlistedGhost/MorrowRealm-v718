package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;

public class Bouquet extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		if (player.getInventory().containsItem(9083, 1)) {
			player.getInventory().refresh();
		sendEntityDialogue(
				SEND_3_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"I'm here to take you to the mysterious Ice Temple.",
						"I hope you're ready for the Ice Demon. You must save us all! ",
						"Ice demon is planning to come here and kill us."}, IS_NPC, npcId,
				9827);
		stage = -1;
		}
		else {
		player.sm("You need item: Seal of Passage.");	
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			player.getInventory().refresh();
			player.teleportPlayer(2914, 3923, 0);
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
