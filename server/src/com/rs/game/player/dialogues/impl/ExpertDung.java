package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class ExpertDung extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_1_TEXT_CHAT, new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Welcome to Dungeoneering, how may i help you?" }, IS_NPC, npcId, 9827);

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"I would like to choose a class",
					"I would like to view your shops",
					"Nevermind...");
		} else if (stage == 0) {
			if (componentId == OPTION_1) {
				stage = 1;
				sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
						"Warrior",
						"Ranger",
						"Spellcaster");
			} 
			if (componentId == OPTION_2) {
				stage = 2;
				sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
						"Warrior Shop",
						"Ranger Shop",
						"Spellcaster Shop",
						"Food & Potions");
			}
			if (componentId == OPTION_3) {
				end();
			}
		} else if (stage == 1) {
			if (componentId == OPTION_1) {
				starterItems(WARRIOR);
			}
			if (componentId == OPTION_2) {
				starterItems(RANGER);
			}
			if (componentId == OPTION_3) {
				starterItems(SPELLCASTER);
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				dungShop(WARRIOR);
			}
			if (componentId == OPTION_2) {
				dungShop(RANGER);
			}
			if (componentId == OPTION_3) {
				dungShop(SPELLCASTER);
			}
			if (componentId == OPTION_4) {
				dungShop(FOOD);
			}
		}
		
	}

	private int WARRIOR = 1;
	private int RANGER = 2;
	private int SPELLCASTER = 3;
	private int FOOD = 4;

	public void dungShop(int classId) {
		if (classId == WARRIOR) {
			end();
			ShopsHandler.openShop(player, 43);
		}
		if (classId == RANGER) {
			end();
			ShopsHandler.openShop(player, 44);
		}
		if (classId == SPELLCASTER) {
			end();
			ShopsHandler.openShop(player, 45);
		}
		if (classId == FOOD) {
			end();
			ShopsHandler.openShop(player, 48);
		}
	}
	
	public void starterItems(int classId) {
		if (classId == WARRIOR) {
			player.getInventory().addItem(14529, 1);
			player.getInventory().addItem(1755, 1);
			player.getInventory().addItem(4151, 1);
			player.getInventory().addItem(11732, 1);
			player.getPackets().sendGameMessage("<col=0000FF>Use the chisel on the armor set for the rest of your gear.</col>");
			end();
		}
		if (classId == RANGER) {
			
			end();
		}
		if (classId == SPELLCASTER) {
	
			end();
		}
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

	
	
}
