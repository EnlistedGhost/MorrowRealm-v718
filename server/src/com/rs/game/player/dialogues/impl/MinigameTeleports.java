package com.rs.game.player.dialogues.impl;

import com.rs.Settings;
import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;

public class MinigameTeleports extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("MiniGame Teleports",
				"Fight Caves", 
				"The Kiln",
				"Clan Wars PvP", 
				"Barrows",
				"Next Page ->");
		
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4613, 5129, 0));
				end();
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4743, 5170, 0));
				end();
			}
			if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2994, 9679, 0));
				end();
			}
			if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3563, 3288, 0));
				end();
			}
			if (componentId == OPTION_5) {
				sendOptionsDialogue("MiniGame Teleports",
						"Dominion Tower", 
						"The Crucible", 
						"Zombie Onslaught");
				stage = 1;
			}
		} else if (stage == 1) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3373, 3090, 0));
				end();
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3360, 6115, 0));
				end();
			}
			if (componentId == OPTION_3) {
				if (!Settings.ZOMBIE_ENABLED) {
					end();
					player.sendMessage("Zombie's has been disabled.");
					return;
				}
				if (player.getFamiliar() != null) {
					end();
					player.sendMessage("You're not allowed to take a familiar into Zombie Onslaught.");
					return;
				}
				if (player.getInventory().getFreeSlots() < 28) {
					end();
					player.sendMessage("Inventory MUST be empty to enter Zombie Onslaught.");
					return;
				}
				end();
				player.getControlerManager().startControler("WiseOldMan");
			}
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
