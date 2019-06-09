package com.rs.game.player.dialogues.impl;

import com.rs.game.minigames.ZarosGodwars;
import com.rs.game.player.dialogues.Dialogue;

public class HomePortal extends Dialogue{

	@Override
	public void start() {
		sendDialogue("I am the portal of many places. I can take you",
				"Wherever you may need to travel. Please select a destination",
				"Warrior, and be weary of where you go!");
		
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("",
					"<shad=00FF00>Dominion Tower", 
					"<shad=FD3EDA>Fight Caves",
					"<shad=05F7FF>Barrows", 
					"<shad=FFCD05>Duel Arena",
					"<shad=990033>FightKiln");
			stage = 0;
		}
		
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
