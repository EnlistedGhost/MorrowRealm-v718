package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.controlers.TutorialIsland;
import com.rs.game.WorldTile;

public class PumpkinPete2 extends Dialogue {
	
	/**
	 * @author Mario (AlterOPS)
	 **/

	private int npcId;

  @Override
	public void start() {
	npcId = (Integer) parameters[0];
			stage = -1;
			sendNPCDialogue(npcId, 9827, "Happy Halloween!");
	}

  @Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 1;
			sendPlayerDialogue(9827, "Happy Halloween!");
			end();
			break;
		}
	}

  @Override
	public void finish() {

	}

}