package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;

public class Ned extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(
				SEND_3_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hey there, i'm extremely busy now unless your going to...",
						"help me out? Please you must understand that I have to",
						"save my wife, she's in serious danger."}, IS_NPC, npcId,
				9827);

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendEntityDialogue(
					SEND_4_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"I'm happy of that you're helping me! We must head straight to",
							"Port Sarim ships, also when you're walking there",
							"You should find Frank, he'll give you special details.",
							"Port Sarim Employees has taken my passport, I need it back!"}, IS_NPC, npcId,
					9827);
			stage = 2;
		}
		if (stage == 2) {
		player.getInterfaceManager().closeChatBoxInterface();
		player.sm("I should head to Port Sarim and speak with Redbeard Frank.");
		player.closeInterfaces();
			stage = 2;
		}
	}

	@Override
	public void finish() {

	}

}
