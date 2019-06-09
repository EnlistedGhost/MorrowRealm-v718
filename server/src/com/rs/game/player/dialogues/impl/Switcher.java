package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class Switcher extends Dialogue {

	public Switcher() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Select a Option",
				"I would like to switch my prayer book.",
				"I would like to switch my spells.",
				"I need to buy some bones",
				"Sorry i don't need any book switch for now.");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue("Pick a prayer book.",
						"I would like to take curse prayers.",
						"I would like to take normal prayers.");
				stage = 2;
			} else if (componentId == OPTION_2) {
				sendOptionsDialogue("Pick a spellbook",
						"I would like to use: Modern Spellbook.",
						"I would like to use: Ancient Spellbook.",
						"I would like to use: Lunar Spellbook.");
				stage = 3;
			} else if (componentId == OPTION_3) {
				end();
				ShopsHandler.openShop(player, 63);
			} else if (componentId == OPTION_4) {
				end();
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				player.getPrayer().setPrayerBook(true);
				player.getInterfaceManager().closeChatBoxInterface();
				player.sm("You pick the power of Zaros: Curse Prayers.");
			} else if (componentId == OPTION_2) {
				player.getPrayer().setPrayerBook(false);
				player.getInterfaceManager().closeChatBoxInterface();
				player.sm("You pick the power of Normal Prayers.");
			}

		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				player.getCombatDefinitions().setSpellBook(0);
				player.getInterfaceManager().closeChatBoxInterface();
				player.sm("You have picked the modern spells.");
			} else if (componentId == OPTION_2) {
				player.getCombatDefinitions().setSpellBook(1);
				player.getInterfaceManager().closeChatBoxInterface();
				player.sm("You have picked the ancient spells.");
			} else if (componentId == OPTION_3) {
				player.getCombatDefinitions().setSpellBook(2);
				player.getInterfaceManager().closeChatBoxInterface();
				player.sm("You have picked the lunar spells.");
			}
		}
	}

	@Override
	public void finish() {
	}

}
