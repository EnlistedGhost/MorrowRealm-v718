package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class SuppliesShop extends Dialogue {
	
	int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_2_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, 
				"Hello, would you like to buy something?" }, IS_NPC, npcId, 9827);
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			sendOptionsDialogue("Supplies Shop", 
					"Melee Gear/Barrows",
					"Ranger Gear",
					"Mage Gear",
					"Accessories",
					"More Options");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue("Choose a Shop", 
					"Weapons #1",
					"Weapons #2",
					"Armour",
					"Defenders",
					"Barrows");
				stage = 3;
			}
			if (componentId == OPTION_2) {
				sendOptionsDialogue("Choose a Shop", 
					"Range Shop #1",
					"Range Shop #2",
					"Go Back");
				stage = 4;
			}
			if (componentId == OPTION_3) {
				sendOptionsDialogue("Choose a Shop", 
					"Mage Shop #1",
					"Mage Shop #2",
					"Go Back");
				stage = 6;
			}
			if (componentId == OPTION_4) {
				ShopsHandler.openShop(player, 53);
				end();
			}
			if (componentId == OPTION_5) {
				sendOptionsDialogue("Choose a Shop", 
					"Skiller Outfits",
					"Potions",
					"Go Back");
				stage = 5;
			}
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				ShopsHandler.openShop(player, 51);
				end();
			}
			if (componentId == OPTION_2) {
				ShopsHandler.openShop(player, 58);
				end();
			}
			if (componentId == OPTION_3) {
				ShopsHandler.openShop(player, 52);
				end();
			}
			if (componentId == OPTION_4) {
				ShopsHandler.openShop(player, 59);
				end();
			}
			if (componentId == OPTION_5) {
				ShopsHandler.openShop(player, 29);
				end();
			}
		} else if (stage == 4) {
			if (componentId == OPTION_1) {
				ShopsHandler.openShop(player, 54);
				end();
			}
			if (componentId == OPTION_2) {
				ShopsHandler.openShop(player, 55);
				end();
			}
			if (componentId == OPTION_3) {
				sendOptionsDialogue("Supplies Shop", 
					"Melee Gear",
					"Ranger Gear",
					"Mage Gear",
					"Accessories",
					"Next Page");
				stage = 2;
			}
		} else if (stage == 5) {
			if (componentId == OPTION_1) {
				ShopsHandler.openShop(player, 50);
				end();
			}
			if (componentId == OPTION_2) {
				ShopsHandler.openShop(player, 31);
				end();
			}
			if (componentId == OPTION_3) {
				sendOptionsDialogue("Supplies Shop", 
						"Melee Gear",
						"Ranger Gear",
						"Mage Gear",
						"Accessories",
						"More Options");
				stage = 2;
			}
		} else if (stage == 6) {
			if (componentId == OPTION_1) {
				ShopsHandler.openShop(player, 56);
				end();
			}
			if (componentId == OPTION_2) {
				ShopsHandler.openShop(player, 57);
				end();
			}
			if (componentId == OPTION_3) {
				sendOptionsDialogue("Supplies Shop", 
						"Melee Gear",
						"Ranger Gear",
						"Mage Gear",
						"Accessories",
						"More Options");
				stage = 2;
			}
		}
		
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
