package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;

public class Morgan extends Dialogue {

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
						"I know why you're here don't need to say!",
						"Quick, quick quick! Go in tower and HELP NED'S WIFE!",
						"There will be the dangerous Ice Demon that you're going to kill."}, IS_NPC, npcId,
				9827);
		stage = 1;
		}
		else {
		player.sm("You don't have Seal of Passage.");	
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			player.getInventory().refresh();
			player.getInterfaceManager().closeChatBoxInterface();
			stage = -2;
		}
	}
	@Override
	public void finish() {

	}

}
