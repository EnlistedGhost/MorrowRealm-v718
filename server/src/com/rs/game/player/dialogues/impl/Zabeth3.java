package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.controlers.TutorialIsland;
import com.rs.game.WorldTile;

public class Zabeth3 extends Dialogue {
	
	/**
	 * @author Mario (AlterOPS)
	 **/

	private int npcId;

  @Override
	public void start() {
	npcId = (Integer) parameters[0];
			stage = -1;
			sendNPCDialogue(npcId, 9827, "*Hic* Do you have my drink? *Hic*");
	}

  @Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 1;
			sendPlayerDialogue(9827, "Yes");
			break;
		case 1:
			stage = 2;
			sendNPCDialogue(npcId, 9827, "Thanks! Here is a gift for you.");
			player.getInventory().deleteItem(22329, 1);
			player.getInventory().addItem(19832, 1);
			player.drink = 3;
			player.doneevent = 1;
			break;
		case 2:
			stage = 3;
			sendPlayerDialogue(9827, "Bye");
			break;
		case 3:
			stage = 4;
			sendNPCDialogue(npcId, 9827, "Bye");
			player.sendMessage("You have completed the Halloween event!");
			player.getInterfaceManager().sendInterface(1244);
			end();
			break;
		}
	}

  @Override
	public void finish() {

	}

}