package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.controlers.TutorialIsland;
import com.rs.game.WorldTile;

public class Zabeth extends Dialogue {
	
	/**
	 * @author Mario (AlterOPS)
	 **/

	private int npcId;

  @Override
	public void start() {
	npcId = (Integer) parameters[0];
			stage = -1;
			sendPlayerDialogue(9827, "Hello, enjoying the party?");
	}

  @Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "*Hic* Hello *Hic* Awesome PARTAY! I'm having a BLAST! Man, *hic* thanks for asking. You three are alright. *Hic*");
			break;
		case 1:
			stage = 2;
			sendPlayerDialogue(9827, "Exactly how drunk are you?");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "*Hic* I am a space kebbit: look at my shiny green fur! *Hic*");
			break;
		case 3:
			stage = 4;
			sendPlayerDialogue(9827, "Sheesh, you're an idiot.");
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "Woooah, you're well rude, dude!");
			break;
		case 5:
			stage = 6;
			sendPlayerDialogue(9827, "Sorry, how about I make it up to you with another drink?");
			break;
		case 6:
			stage = 7;
			sendNPCDialogue(npcId, 9827, "Nah, it's alright, I forgive you. *Hic* Sounds good to me *Hic* You three are alright.");
			break;
		case 7:
			stage = 8;
			sendPlayerDialogue(9827, "From where can I get a drink.");
			break;
		case 8:
			stage = 9;
			sendNPCDialogue(npcId, 9827, "I don't know, ask Grim.");
			break;
		case 9:
			stage = 10;
			sendPlayerDialogue(9827, "Ok, bye.");
			break;
		case 10:
			stage = 11;
			sendNPCDialogue(npcId, 9827, "*Hic* Byeeee *Hic*.");
			player.drink = 0;
                        player.cake = 1;
			player.doneevent = 0;
			end();
			break;
		}
	}

  @Override
	public void finish() {

	}

}