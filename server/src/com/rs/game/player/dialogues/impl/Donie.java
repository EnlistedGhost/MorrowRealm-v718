package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;

public class Donie extends Dialogue {

	int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(
					SEND_1_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"Hello there, can I help you?" }, IS_NPC, npcId, 9827);
		stage = 0;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		// TODO add the other 3 dialogues (total of 4) and set to random
		if (stage == 0) {
			sendOptionsDialogue("Select an option",
					"Where am I?",
					"How are you today?",
					"Are there any quests I can do here?",
					"Where can I get a haircut like yours?");
			stage = 1;
		} else if (stage == 1) {
			if (componentId == OPTION_1) {
				sendEntityDialogue(SEND_1_TEXT_CHAT,
						new String[] { player.getDisplayName(),
								"Where am I?" }, IS_PLAYER,
						player.getIndex(), 9827);
				stage = 2;
				//end();
			} else if (componentId == OPTION_2) {
				sendEntityDialogue(SEND_1_TEXT_CHAT,
						new String[] { player.getDisplayName(),
								"How are you today?" }, IS_PLAYER,
						player.getIndex(), 9827);
				stage = 3;
				//end();
			} else if (componentId == OPTION_3) {
				sendEntityDialogue(SEND_1_TEXT_CHAT,
						new String[] { player.getDisplayName(),
								"Do you know of any quests I can do?" }, IS_PLAYER,
						player.getIndex(), 9827);
				stage = 4;
			} else if (componentId == OPTION_4) {
				sendEntityDialogue(SEND_1_TEXT_CHAT,
						new String[] { player.getDisplayName(),
								"Where can I get a haircut like yours?" }, IS_PLAYER,
						player.getIndex(), 9827);
				stage = 5;
				//end();
			} else
				end();
		} else if (stage == 2) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"This is the town of Lumbridge my friend." }, IS_NPC, npcId, 9827);
			stage = 0;
		} else if (stage == 3) {
				sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Aye, not too bad thank you. Lovely weather in Gielinor this fine day." }, IS_NPC, npcId, 9827);
				stage = 6;
		} else if (stage == 6) {
				sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { player.getDisplayName(),
						"Weather?" }, IS_PLAYER,
						player.getIndex(), 9827);
				stage = 7;
		} else if (stage == 7) {
				sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Yes weather, you know." }, IS_NPC, npcId, 9827);
				stage = 8;
		} else if (stage == 8) {
				sendEntityDialogue(SEND_3_TEXT_CHAT,
					new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"The state or condition of the atmosphere at a time and place, ",
						"with respect to variables such as temperature, moisture, ",
						"wind velocity, and barometric pressure." }, IS_NPC, npcId, 9827);
				stage = 9;
		} else if (stage == 9) {
				sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { player.getDisplayName(),
						"..." }, IS_PLAYER,
						player.getIndex(), 9827);
				stage = 10;
		} else if (stage == 10) {
				sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Not just a pretty face eh? Ha ha ha." }, IS_NPC, npcId, 9827);
				stage = 14;
		} else if (stage == 4) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"Sorry, there's nothing right now. Check back later." }, IS_NPC, npcId, 9827);
			stage = 14;
		} else if (stage == 5) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"Yes, it does look like you need a hairdresser." }, IS_NPC, npcId, 9827);
			stage = 11;
		} else if (stage == 11) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
						new String[] { player.getDisplayName(),
								"Oh thanks!" }, IS_PLAYER,
						player.getIndex(), 9827);
			stage = 12;
		} else if (stage == 12) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"No problem. The hairdresser in Falador will probably be able to sort you out." }, IS_NPC, npcId, 9827);
			stage = 13;
		} else if (stage == 13) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"The Lumbridge general store sells useful maps if you don't know the way." }, IS_NPC, npcId, 9827);
			stage = 14;
		} else if (stage == 14) {
			end();
		} else
			end();
	}

	@Override
	public void finish() {

	}

}
