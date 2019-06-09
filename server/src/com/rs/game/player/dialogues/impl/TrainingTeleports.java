package com.rs.game.player.dialogues.impl;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;

public class TrainingTeleports extends Dialogue {

	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
			sendOptionsDialogue("Training teleports (pg. 1)",
					"Rock Crabs",
					"Greater Demons",
					"Black Demons",
					"Green Dragons",
					"More Options");
			stage = 1;
		}
	}

	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2410, 3853, 0));
			if (componentId == OPTION_2)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2644, 9517, 0));
			if (componentId == OPTION_3)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2866, 9777, 0));
			if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3349, 3668, 0));
				player.getControlerManager().startControler("Wilderness");
			}
			if (componentId == OPTION_5) {
				sendOptionsDialogue("Training Teleports (pg. 2)",
						"PolyPore Dungeon",
						"Jadinko Lair",
						"Desert Strykwyrms",
						"Brimhaven",
						"Abyssal Demons");
				stage = 2;
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				end();
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4723, 5465, 0));
			}
			if (componentId == OPTION_2) {
				end();
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3012, 9272, 0));
			}
			if (componentId == OPTION_3) {
				end();
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3376, 3144, 0));
			}
			if (componentId == OPTION_4) {
				end();
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2709, 9487, 0));
			}
			if (componentId == OPTION_5) {
				end();
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3061, 4851, 0));
			}
		}
	}

	@Override
	public void finish() {

	}

}