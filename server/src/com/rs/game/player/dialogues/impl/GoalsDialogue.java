package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

public class GoalsDialogue extends Dialogue {

	private String goalDesc;
	
	@Override
	public void start() {
		this.goalDesc = (String) parameters[0];
		if (stage == -1) {
			sendDialogue("You have just completed:", "<col=FF0000>"+goalDesc+"</col>");
			stage = 1;
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			end();
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
