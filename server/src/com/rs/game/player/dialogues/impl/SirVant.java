package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.content.mission.Entrance;
import com.rs.game.player.dialogues.Dialogue;

public class SirVant extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		if (player.getInventory().containsItem(16, 1)) {
			player.getInventory().refresh();
			player.getInventory().deleteItem(16, 1);
		sendEntityDialogue(SEND_3_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Congratulations, you managed to kill the demon I see!",
						"I've been here years trying to think how to beat this, you did it!",
						"Now, let me give you a little reward for this..."}, IS_NPC, npcId,
				9827);
		stage = -1;
		}
		else {
		player.sm("I should achieve: 1x Magic Whistle to speak with Sir Vant.");	
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
