package com.rs.game.player.dialogues.impl;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.player.Skills;

public class SlayerTeleports extends Dialogue {

	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
			sendOptionsDialogue("Slayer Dungeon teleports",
					"<shad=0066CC>Kuradals Dungeon",
					"<shad=339933>Jadinko Lair",
					"<shad=0066CC>Polypore Dungeon",
					"<shad=05F7FF>Slayer Tower", "<shad=FD3EDA>Glacors");
			stage = 1;
		}
	}

	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			int option;
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1690,
						5286, 1));
			}
			if (componentId == OPTION_2) {
				if (player.getSkills().getLevel(Skills.SLAYER) < 80) {
					player.getPackets()
							.sendGameMessage(
									"You need an slayer level of 80 to use this teleport.");
					return;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3012,
						9274, 0));
			}
			if (componentId == OPTION_3) {
				if (player.getSkills().getLevel(Skills.SLAYER) < 95) {
					player.getPackets()
							.sendGameMessage(
									"You need an slayer level of 95 to use this teleport.");
					end();
					return;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4719,
						5467, 0));
			}
			if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3429,
						3538, 0));
				end();
			}
			if (componentId == OPTION_5) {
				if (player.getSkills().getLevel(Skills.SLAYER) < 99) {
					player.getPackets()
							.sendGameMessage(
									"You need an slayer level of 99 to use this teleport.");
					end();
					return;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4184,
						5732, 0));
			}
		}
	}

	@Override
	public void finish() {

	}

}