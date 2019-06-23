package com.rs.game.player.dialogues.impl;

import com.rs.game.WorldTile;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.player.Equipment;
import com.rs.game.player.Appearence;
import com.rs.game.player.Inventory;
import com.rs.game.player.Player;

/**
 * 
 * @author Xiles
 * 
 * Handles leaving the instance.
 *
 */

public class DungLeaving extends Dialogue {

	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
			sendOptionsDialogue("Are you sure you want to leave?",
					"Yes please.",
					"No thanks.");
			stage = 1;
	}
	}

	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.lock(10); 
					player.setNextWorldTile(new WorldTile(3450, 3718, 0));
				}
				player.getPackets().closeInterface(player.getInterfaceManager().hasRezizableScreen() ? 746 : 548, player.getInterfaceManager().hasRezizableScreen() ? 11: 27);
				player.getInterfaceManager().closeChatBoxInterface();
				//Equipment removal
				player.getEquipment().reset();
				for (int i = 0; i < 15; i++) {
				player.getEquipment().refresh(i);
				}
				player.getAppearence().generateAppearenceData();
				player.getInventory().reset();
			}
			if (componentId == OPTION_2) {
				stage = 2;				
		}
		if (stage == 2) {
			end();
			player.getInterfaceManager().closeChatBoxInterface();
		}
	}
	
	public void handleLeaving() {
		player.getControlerManager().forceStop();
		player.getControlerManager().removeControlerWithoutCheck();
		player.lock(10); 
		player.setNextWorldTile(new WorldTile(3450, 3718, 0)); 
		
	}

	@Override
	public void finish() {

	}

}