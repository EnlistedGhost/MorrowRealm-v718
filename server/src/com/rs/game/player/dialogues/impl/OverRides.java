package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

public class OverRides extends Dialogue {

	
	@Override
	public void start() {
		sendOptionsDialogue("Which Piece?", "");
		
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
