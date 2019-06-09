package com.rs.game.player.dialogues.impl;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;

public class PkingTeleports extends Dialogue {

	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
			sendOptionsDialogue("Pking teleports", "Magic Bank",
					"Revenants", "Edgeville");
			stage = 1;
		}
	}

	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			int option;
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0D, new WorldTile(
						2539, 4716, 0));
				end();
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(
						3071, 3649, 0));
				end();
			}
			if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(
						3087, 3496, 0));
				end();
			}
		}
	}

	@Override
	public void finish() {

	}

}