package com.rs.game.player.dialogues.impl;

import com.rs.Settings;
import com.rs.game.player.content.custom.Overrides.Armour;
import com.rs.game.player.content.interfaces.OverRideMenu;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class Xuans extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Hey "+ player.getUsername() + ", Im Xuan. Im here to sell auras for Loyalty Points, im just telling you this to let you know :) Well what would you like to ask?");
	}

	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Xuan", 
					"Show me your Shop",
					"How much points do i have?",
					"How do i get Loyalty Points?",
					"Show me the Overrides Shop",
					"Reset my Overrides");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				ShopsHandler.openShop(player, 28);
				end();
			}
			if (componentId == OPTION_2) {
				sendNPCDialogue(npcId, 9827, "You currently have " + player.getLoyaltyPoints() + " Loyalty Points.");
				stage = 3;
			}
			if (componentId == OPTION_3) {
				sendNPCDialogue(npcId, 9827,
						"The only way to get Loyalty Points is by playing "+Settings.SERVER_NAME+" for 30 minutes.");
				stage = 3;
			}
			if (componentId == OPTION_4) {
				OverRideMenu.openInterface(player);
				end();
			}
			if (componentId == OPTION_5) {
				sendNPCDialogue(npcId, 9827, 
						"This will not reset what you have unlocked, but will reset whatever you have currently set.", 
						"So if you'd like to return your look to normal, this is the option.");
				stage = 4;

			}
		} else if (stage == 3) {
			end();
		} else if (stage == 4) {
			sendOptionsDialogue("Choose an Option", "Yes, reset my overrides to normal", "Nevermind, i like the way i look.");
			stage = 5;
		} else if (stage == 5) {
			if (componentId == OPTION_1) {
				player.setArmour(new Armour[15]);
				player.getAppearence().generateAppearenceData();
				end();
			}
			if (componentId == OPTION_2) {
				end();
			}
		}
	}

	@Override
	public void finish() {

	}

}