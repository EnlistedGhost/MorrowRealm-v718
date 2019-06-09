package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class EstateAgent extends Dialogue {

	public EstateAgent() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue(
				"Select a Option",
				"I would like to purchase some planks.",
				"Nice cape you have there! Is it because you're so good on it?",
				"Ummh, i have nothing really to say...");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				ShopsHandler.openShop(player, 38);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) {
				player.sm("Estate Agent is a business man, he got no time to talk with you.");
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
			}
		}
	}

	@Override
	public void finish() {
	}

}
