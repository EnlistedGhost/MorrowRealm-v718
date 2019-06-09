package com.rs.game.player.dialogues.impl;

import com.rs.game.player.actions.crafting.Crafting;
import com.rs.game.player.dialogues.Dialogue;

public class CraftingDialogue extends Dialogue {
	
	@Override
	public void start() {
		if (stage == -1) {
			sendOptionsDialogue("Amulet Crafting", "Amulet", "Necklace", "Ring", "Bracelet");
			stage = 1;
		}
	}
	
	private static final int AMULET = 1673, NECKLACE = 1654, RING = 1635, BRACELET = 11069;
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 0) {
			end();
			return;
		}
		if (stage == 1) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue("How Many Amulets?", "Make 1", "Make 5", "Make 10", "Make All");
				stage = 2;
			} else if (componentId == OPTION_2) {
				sendOptionsDialogue("How Many Necklaces?", "Make 1", "Make 5", "Make 10", "Make All");
				stage = 3;
			} else if (componentId == OPTION_3) {
				sendOptionsDialogue("How Many Rings?", "Make 1", "Make 5", "Make 10", "Make All");
				stage = 4;
			} else if (componentId == OPTION_4) {
				sendOptionsDialogue("How Many Bracelets?", "Make 1", "Make 5", "Make 10", "Make All");
				stage = 5;
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				Crafting.makeAmulet(player, 1595, AMULET, 1);
				end();
			} else if (componentId == OPTION_2) {
				Crafting.makeAmulet(player, 1595, AMULET, 5);
				end();
			} else if (componentId == OPTION_3) {
				Crafting.makeAmulet(player, 1595, AMULET, 10);
				end();
			} else if (componentId == OPTION_4) {
				Crafting.makeAmulet(player, 1595, AMULET, 28);
				end();
			}
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				Crafting.makeAmulet(player, 1597, NECKLACE, 1);
				end();
			} else if (componentId == OPTION_2) {
				Crafting.makeAmulet(player, 1597, NECKLACE, 5);
				end();
			} else if (componentId == OPTION_3) {
				Crafting.makeAmulet(player, 1597, NECKLACE, 10);
				end();
			} else if (componentId == OPTION_4) {
				Crafting.makeAmulet(player, 1597, NECKLACE, 28);
				end();
			}
		} else if (stage == 4) {
			if (componentId == OPTION_1) {
				Crafting.makeAmulet(player, 1592, RING, 1);
				end();
			} else if (componentId == OPTION_2) {
				Crafting.makeAmulet(player, 1592, RING, 5);
				end();
			} else if (componentId == OPTION_3) {
				Crafting.makeAmulet(player, 1592, RING, 10);
				end();
			} else if (componentId == OPTION_4) {
				Crafting.makeAmulet(player, 1592, RING, 28);
				end();
			}
		} else if (stage == 5) {
			if (componentId == OPTION_1) {
				Crafting.makeAmulet(player, 11065, BRACELET, 1);
				end();
			} else if (componentId == OPTION_2) {
				Crafting.makeAmulet(player, 11065, BRACELET, 5);
				end();
			} else if (componentId == OPTION_3) {
				Crafting.makeAmulet(player, 11065, BRACELET, 10);
				end();
			} else if (componentId == OPTION_4) {
				Crafting.makeAmulet(player, 11065, BRACELET, 28);
				end();
			}
		}
		
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
