package com.rs.game.player.dialogues.impl;

import com.rs.Settings;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class Veteran extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827,
				"Since "+Settings.SERVER_NAME+" is new i'm here to give you something special.",
				"You'll get a free early bird bonus and i'll be handling it.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			if (player.getInventory().containsItem(20763, 1)) {
				player.sm("You have already received Veteran's cape.");
				player.getInterfaceManager().closeChatBoxInterface();
			} else {
				player.getInventory().addItem(20763, 1);
				player.sm("For being a veteran player of "+Settings.SERVER_NAME+" you'll receive a special cape.");
				player.getInterfaceManager().closeChatBoxInterface();
			}

			break;

		default:
			end();
			break;
		}

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}
