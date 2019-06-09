package com.rs.game.player.actions.crafting;

import com.rs.game.player.dialogues.Dialogue;

public class GemDialogue extends Dialogue {
	
	int gemId;
	
	@Override
	public void start() {
		this.gemId = (int) parameters[0];
		if (stage == -1) {
			sendOptionsDialogue("Amulet Crafting", "Add to Amulet", "Add to Necklace", "Add to Ring", "Add to Bracelet");
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
				AmuletCrafting.makeAmulet(player, gemId, AMULET, 1);
				end();
			} else if (componentId == OPTION_2) {
				NecklaceCrafting.makeNecklace(player, gemId, NECKLACE, 1);
				end();
			} else if (componentId == OPTION_3) {
				RingCrafting.makeRing(player, gemId, RING, 1);
				end();
			} else if (componentId == OPTION_4) {
				BraceletCrafting.makeBracelet(player, gemId, BRACELET, 1);
				end();
			}
		}
		
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
