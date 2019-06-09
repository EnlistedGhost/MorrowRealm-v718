package com.rs.game.player.dialogues.impl;

import com.rs.Settings;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class CharacterOptions extends Dialogue {

	@Override
	public void start() {
		sendEntityDialogue(SEND_4_TEXT_CHAT, new String[] { NPCDefinitions.getNPCDefinitions(13768).name,
			"Hello and welcome to "+Settings.SERVER_NAME+"!",
			"How may I help you today? I have various shops and the",
			"ability to change your your physical appearence!",
			"How may I be of assistance for you today?"
			}, IS_NPC, 13768, 9827);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue(""+Settings.SERVER_NAME+" Account Manager",
					"I'd like to access to my character settings.",
					"Show me your shops please!",
					"Can you take me to the Donator Zone?", 
					"- Coming Soon-");
			stage = 1;
		}
		
		if (stage == 1) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue(""+Settings.SERVER_NAME+" - Account Settings",
						"I would like to edit my gender & skin.",
						"I would like to edit my hairstyles.",
						"I would love to change my clothes, Eva.");
				stage = 2;
			}
			if (componentId == OPTION_2) {
				sendOptionsDialogue(""+Settings.SERVER_NAME+" - Point Shops",
						"PvP Point Shop",
						"Prestige Point Shop",
						"Vote Point Shop",
						"(New!) Credits Shop",
						"Exit");
				stage = 3;
			}
			if (componentId == OPTION_3) {
				stage = 10;
					sendEntityDialogue(SEND_3_TEXT_CHAT, new String[] { NPCDefinitions.getNPCDefinitions(13768).name, 
							"No I'm sorry, but I'm afraid not. If you want to",
							"go, then please use the teleport option on the quest",
							"tab. It can be found under 'Misc. Tele' option."}, IS_NPC, 13768, 9827);
			}
			if (componentId == OPTION_4) {
				end();
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				PlayerLook.openMageMakeOver(player);
			} else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				PlayerLook.openHairdresserSalon(player);
			} else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				PlayerLook.openThessaliasMakeOver(player);
			}
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				ShopsHandler.openShop(player, 34);
				end();
			}
			if (componentId == OPTION_2) {
				ShopsHandler.openShop(player, 46);
				end();
			}
			if (componentId == OPTION_3) {
				ShopsHandler.openShop(player, 42);
				end();
			}
			if (componentId == OPTION_4) {
				ShopsHandler.openShop(player, 50);
				end();
			}
			if (componentId == OPTION_5) {
				end();
			}
		}
	
		else if (stage == 10) {
			end();
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
