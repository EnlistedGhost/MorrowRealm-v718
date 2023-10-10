package com.rs.game.player.dialogues.impl;

import com.rs.game.player.content.custom.StarterItems;
import com.rs.game.player.dialogues.Dialogue;

public class NewStarter extends Dialogue {

	int starter;
	int difficulty;
	
	@Override
	public void start() {
		if (player.getInterfaceManager().containsScreenInter()) {
			player.getInterfaceManager().closeScreenInterface();
		}
		sendEntityDialogue(IS_NPC, "MorrowRealm Manager", 13768, 9827, 
			"Welcome to MorrowRealm, "+player.getDisplayName()+"!",
			"Please, before you begin you will need to", 
			"choose your starter set and agree to continue.",
			"The agreement to continue is very important.");
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		
		if (player.getInterfaceManager().containsScreenInter()) {
			player.getInterfaceManager().closeScreenInterface();
		}
		
		if (stage == 1) {
			this.difficulty = 5; // Secondary diff set
			sendOptionsDialogue("Pick a Starter Type",
					"I would like a Fighter's Starter",
					"I would like an Archer's Starter",
					"I would like a Magician's Starter");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				this.starter = StarterItems.FIGHTER;
				sendNext_1();
			} else if (componentId == OPTION_2) {
				this.starter = StarterItems.ARCHER;
				sendNext_1();
			}else if (componentId == OPTION_3) {
				this.starter = StarterItems.MAGICIAN;
				sendNext_1();
			}
		} else if (stage == 3) {
			sendOptionsDialogue("Do you understand and wish to continue?", 
					"Yes, contiue",
					"No, I'm leaving");
			stage = 4;
		} else if (stage == 4) {
			if (componentId == OPTION_1) {
				sendNext_2();
			}
			else if (componentId == OPTION_2) {
				sendNext_3();
			}
		} else if (stage == 5) {
			player.setDifficulty(difficulty);
			StarterItems.giveStarter(player, starter);
			end();
		}
	}
	
	public void sendNext_1() {
		sendEntityDialogue(IS_NPC, "MorrowRealm Manager", 13768, 9827, 
				"Very good! Now all you need to do is decide",
				"if you wish to continue. You must understand that", 
				"MorrowRealm is a fun RSPS 2012 remake, and in no way",
				"affiliated with Jagex and/or their game titles!");
		stage = 3;
	}
	
	public void sendNext_2() {
		sendEntityDialogue(IS_NPC, "MorrowRealm Manager", 13768, 9827, 
				"Very good! You've chosen '"+diffs[difficulty - 1]+"',",
				"Now what are you waiting for? Go out and have some fun!", 
				"Just click the button below and you'll receive",
				"a few starter items! We hope you enjoy your stay!");
		stage = 5;
	}

		public void sendNext_3() {
		sendEntityDialogue(IS_NPC, "MorrowRealm Manager", 13768, 9827, 
				"That's too bad! You've chosen 'No, I'm leaving',",
				"Please close this application/client and leave!", 
				"We cannot allow users who do not understand or",
				"agree to the terms and conditions to stay!");
		stage = 5;
	}
	
	public static String[] diffs = { "Very Easy", "Easy", "Normal", "Hard", "Yes, contiue" };

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
