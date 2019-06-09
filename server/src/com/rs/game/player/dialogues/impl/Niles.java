package com.rs.game.player.dialogues.impl;

import com.rs.game.player.content.LividFarm;
import com.rs.game.player.dialogues.Dialogue;

public class Niles extends Dialogue {

	int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Hello, "+player.getDisplayName()+". I can exchange your Livid Farm Points.", "You have currently: "+player.lividpoints+".");
	}
/*
 * (non-Javadoc)
 * @see com.rs.game.player.dialogues.Dialogue#run(int, int)
 */
	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 0;
			sendPlayerDialogue(9827, "Alright, what can I exchange then with my Livid Farm Points?");
			break;
		case 0:
			sendOptionsDialogue("Livid Points Exchange",
					"Magical Orb - Livid Plant boosting, XP Focusion options. (2,800)", "Highlander Set (2,500)", "Coming soon.",
					"Coming soon.", "Nevermind");
			stage = 2;
			break;
		case 2:
			if (stage == 2) {
				if (componentId == OPTION_1)
					LividFarm.OrbPayment(player);
				else if (componentId == OPTION_2)
					LividFarm.HighLanderSet(player);
				else if (componentId == OPTION_3)
					player.getInterfaceManager().closeChatBoxInterface();
				else if (componentId == OPTION_4)
					player.getInterfaceManager().closeChatBoxInterface();
				else if (componentId == OPTION_4)
					player.getInterfaceManager().closeChatBoxInterface();
			}
			break;
		case 3:
			end();
			break;
		default:
			end();
			break;
		}
	}

	@Override
	public void finish() {

	}

}
