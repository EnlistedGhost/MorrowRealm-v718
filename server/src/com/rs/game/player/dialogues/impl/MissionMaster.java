package com.rs.game.player.dialogues.impl;

import com.rs.Settings;
import com.rs.game.Graphics;
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.Skills;
import com.rs.game.player.content.Magic;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.dialogues.Dialogue;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class MissionMaster extends Dialogue {

	public MissionMaster() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Select a Option",
				"I would like to see my current missions.",
				"What is the idea of this?", "Sorry i have nothing to say.");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue("Select a Mission",
						"The Basic Information", "Ned's Ship Passage (Medium)",
						"Hoarfroast Hollow (Hard)",
						"Knight of "+Settings.SERVER_NAME+" (Extreme)", "Next Page");
				stage = 2;
			} else if (componentId == OPTION_2) {
				stage = 1;
				sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
						"This is the mission system and im here to",
						"locate you any mission and i will be helping",
						"you and telling you what you need actually do.",
						"Remember that you will need fighting gear sometimes.");
				stage = 3;
			} else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				stage = 1;
				sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
						"This is the mission system and im here to",
						"locate you any mission and i will be helping",
						"you and telling you what you need actually do.",
						"Remember that you will need fighting gear sometimes.");
			} else if (componentId == OPTION_2) {
				stage = 1;
				sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
						"Main boss of Ned's Ship Passage is: Ice Demon",
						"Attack styles: Strong magic also melee.",
						"Length of this mission is: Short.",
						"Start: Talk to Ned in Draynor Village house.");
			} else if (componentId == OPTION_3) {
				stage = 1;
				sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
						"Knight of "+Settings.SERVER_NAME+" (Extreme) - Not completed yet.");
			} else if (componentId == OPTION_4) {
				stage = 1;
				sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Coming Soon");
			} else if (componentId == OPTION_5) {
				stage = 1;
				sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Coming soon");
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

	private void teleportPlayer(int x, int y, int z) {
		player.setNextGraphics(new Graphics(111));
		player.setNextWorldTile(new WorldTile(x, y, z));
		player.stopAll();

	}

	@Override
	public void finish() {
	}

}
