package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class NomadMultiShop extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] {NPCDefinitions.getNPCDefinitions(npcId).name,
			"Welcome to the Donator Zone, "+player.getDisplayName()+",",
			"Is there anything you would like me to help you with?",}, 
			IS_NPC, npcId, 9827);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 1;
			sendOptionsDialogue("Select an Option", 
					"View Donator Shops", 
					"Clean my Bank (<col=FF0000>Now Working!</col>)", 
					"Nevermind...");
		}
		
		else if (stage == 1) {
			if (componentId == OPTION_1) {
				stage = 2;
				sendOptionsDialogue("Select an Option", 
						"Donator Pet Shop", 
						"Donator Armor #1", 
						"Donator Flasks & Pots",
						"Donator Armour #2");
				
			}
			if (componentId == OPTION_2) {
				if (player.getRights() > 0) {
					end();
					player.getDialogueManager().startDialogue("Cleanbank");
				} else {
					sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] {"Nomad", 
							"Sorry, but this option isnt available for you."}, 
							IS_NPC, npcId, 9827);
					stage = 1;
				}
			}
			if (componentId == OPTION_3) {
				end();
			}
		}
		
		else if (stage == 2) {
			if (componentId == OPTION_1) {
				end();
				ShopsHandler.openShop(player, 32);
			}
			if (componentId == OPTION_2) {
				end();
				ShopsHandler.openShop(player, 33);
			}
			if (componentId == OPTION_3) {
				end();
				ShopsHandler.openShop(player, 7);
			}
			if (componentId == OPTION_4) {
				end();
				ShopsHandler.openShop(player, 65);
			}
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

	
}
