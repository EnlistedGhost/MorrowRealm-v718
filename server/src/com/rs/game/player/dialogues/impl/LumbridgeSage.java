package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;
//imports
import com.rs.game.player.Player;
//import com.rs.game.player.content.Magic;
//import com.rs.game.WorldTile;
//import com.rs.utils.ShopsHandler;

public class LumbridgeSage extends Dialogue {

	int npcId;
	
	@Override
	public void start(){
		npcId = (Integer) parameters[0];
		sendEntityDialogue(
			SEND_1_TEXT_CHAT,
			new String[] {
				NPCDefinitions.getNPCDefinitions(npcId).name,
				"Greetings, "+player.getDisplayName()+". How may I help you?" }, IS_NPC, npcId, 9827);
		stage = 1;
	}
	
	@Override
	public void run(int interfaceId, int componentId){
		if (stage == 1){
			sendOptionsDialogue("Select an Option",
				"Who are you?",
				"Tell me about the town of Lumbridge.",
				"Goodbye.");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				sendEntityDialogue(SEND_4_TEXT_CHAT,
					new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"I am Phileas, the Lumbridge Sage. In times past, ",
						"people came from all around to ask me for advice. ",
						"My renown seems to have diminished somewhat in recent years, ",
						"though. Can I help you with anything?" }, IS_NPC, npcId, 9827);
				stage = 1;
			}
			if (componentId == OPTION_2) {
				sendEntityDialogue(SEND_3_TEXT_CHAT,
					new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Lumbridge is one of the older towns in the human-controlled kingdoms. ",
						"It was founded over two hundred years ago towards the end of the Fourth Age. ",
						"It's called Lumbridge because of this bridge built over the River Lum. " }, IS_NPC, npcId, 9827);
				stage = 3;
			}
			if (componentId == OPTION_3) {
				sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Happy travels, "+player.getDisplayName() }, IS_NPC, npcId, 9827);
				stage = 5;
			}
		} else if (stage == 3) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"The town is governed by Duke Horacio, who is a ",
						"good friend of our monarch, King Roald of Misthalin. " }, IS_NPC, npcId, 9827);
			stage = 6;
		} else if (stage == 4) {
			//ShopsHandler.openShop(player, 11);
			end();
		} else if (stage == 5) {
			//Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2911, 4832, 0));
			end();
		} else if (stage == 6) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Recently, however, there have been great ",
						"changes due to the Battle of Lumbridge. " }, IS_NPC, npcId, 9827);
			stage = 7;
		} else if (stage == 7) {
			sendOptionsDialogue("Select an Option",
				"What about the battle?",
				"Tell me about the battle please!");
			stage = 8;
		} else if (stage == 8) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Indeed, not long ago there was a great fight between Saradomin ",
						"and Zamorak on the battlefield to the west of the castle. " }, IS_NPC, npcId, 9827);
			stage = 9;
		} else if (stage == 9) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Titanic forces were unleashed as neither side could gain the upper hand. ",
						"Each side sought advantages, but it was close until the end. " }, IS_NPC, npcId, 9827);
			stage = 10;
		} else if (stage == 10) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"The battle lasted for months, but in the end the forces of ",
						"the holy Saradomin were triumphant. Zamorak was defeated... but... " }, IS_NPC, npcId, 9827);
			stage = 11;
		} else if (stage == 11) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Before Saradomin could complete his victory, Moia, ",
						"the general of Zamorak's forces, transported him away. " }, IS_NPC, npcId, 9827);
			stage = 12;
		} else if (stage == 12) {
			sendEntityDialogue(SEND_3_TEXT_CHAT,
					new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Now, the battlefield lies empty save for a single Saradominist devotee, ",
						"and Lumbridge lies in ruins. Luckily, Foreman George is organising a ",
						"rebuilding effort, to the north of the castle." }, IS_NPC, npcId, 9827);
			stage = 1;
		}
	}
	
	@Override
	public void finish() {
		
	}
}