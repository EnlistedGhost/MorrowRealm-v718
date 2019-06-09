package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.content.mission.Entrance;
import com.rs.game.player.dialogues.Dialogue;

public class Klarense extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		if (player.getInventory().containsItem(9083, 1)) {
			player.getInventory().refresh();
		sendEntityDialogue(SEND_3_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"I see your seal of passage, congratulations my friend you managed to catch it.",
						"Anyways ship is ready and we are ready to go get Ned's Wife.",
						"Let's begin...."}, IS_NPC, npcId,
				9827);
		stage = -1;
		}
		else {
		player.sm("Klarense couldn't find Seal of Passage, talk to Frank at bar in port sarim.");	
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			Entrance.ShipTeleport(player, 3040, 2965, 0);
			player.getInterfaceManager().sendInterface(543);
			player.getInventory().refresh();
			player.closeInterfaces();
			player.getInterfaceManager().closeChatBoxInterface();
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
