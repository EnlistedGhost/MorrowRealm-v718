package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class PKGear extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("PK Gear", "Melee Pk Gear", "Mage Pk Gear", "Ranger Pk Gear", "Other Gear");
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				ShopsHandler.openShop(player, 60);
			} else if (componentId == OPTION_2) {
				ShopsHandler.openShop(player, 61);
			} else if (componentId == OPTION_3) {
				ShopsHandler.openShop(player, 62);
			} else if (componentId == OPTION_4) {
				ShopsHandler.openShop(player, 30);
			}
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
