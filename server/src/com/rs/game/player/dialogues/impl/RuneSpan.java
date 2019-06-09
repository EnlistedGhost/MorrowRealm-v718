package com.rs.game.player.dialogues.impl;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class RuneSpan extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Can i help you, "+player.getDisplayName()+"?");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 1;
			sendOptionsDialogue("Select an Option", "View store", "Teleport to another floor", "nevermind");
		}
		
		else if (stage == 1) {
			if (componentId == OPTION_1) {
				end();
				ShopsHandler.openShop(player, 41);
			}
			if (componentId == OPTION_2) {
				stage = 2;
				sendOptionsDialogue("Select a Floor", "Floor 1", "Floor 2", "Floor 3", "cancel");
			}
			if (componentId == OPTION_3) {
				end();
			}
		}
		
		else if (stage == 2) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3995, 6109, 1));
				end();
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4135, 6088, 1));
				end();
			}
			if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4297, 6040, 1));
				end();
			}
			if (componentId == OPTION_4) {
				end();
			}
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
	}

}
