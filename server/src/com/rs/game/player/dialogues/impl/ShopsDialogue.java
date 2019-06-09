package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

public class ShopsDialogue extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("Select a Shop", 
				"General Store", 
				"Melee Armour",
				"Melee Weapons",
				"",
				"");
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
