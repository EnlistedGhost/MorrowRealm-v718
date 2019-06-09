package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.content.mission.MissionRewards;
import com.rs.game.player.dialogues.Dialogue;

public class MissionGive extends Dialogue {

	public MissionGive() {
	}
	int npcId;
	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Select a option", 
				"I would like to pick a mission.",
				"I have obtained a item, i don't know what to do.",
				"I have completed mission.",
				"Could you open my bank account please.");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue("Where would you like to go?",
						"1 - Hoarfroast Hollow.", "2 - Making History", "3 - Save the Lumbridge.",
						"4 - Deep in the caves.", "More missions will come.");
				stage = 2;
			}
			else if (componentId == OPTION_2) {
			stage = 1;	
			sendEntityDialogue(
					SEND_4_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"I couldn't find anything that can help you.", 
							"If you have obtained an item from mission and don't know what to do,", 
							"feel free to ask help from me, my friend."}, IS_NPC, npcId,
					9827);
			} 
			else if (componentId == OPTION_3) {
			MissionRewards.RewardHandler(player);
			} 
			else if (componentId == OPTION_4) {
			player.getBank().openBank();
			player.getInterfaceManager().closeChatBoxInterface();
			}
			else if (stage == 2) {
				if (componentId == OPTION_1) {	
					stage = 1;
					sendEntityDialogue(
							SEND_4_TEXT_CHAT,
							new String[] {
									NPCDefinitions.getNPCDefinitions(npcId).name,
									"Start location: Draynor Village, speak with Ned, location in one of the houses. ",
									"Difficulty: Easy.",
									"Type: Investigation.",
									"Boss: N/A." }, IS_NPC, npcId,
							9827);
				} else if (componentId == OPTION_2) {
			stage = 1;	
			sendEntityDialogue(
					SEND_4_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"Start location: Falador Castle, speak with Squire Astrol. ",
							"Difficulty: Medium.",
							"Type: Investigation & Battle.",
							"Boss: Ice Demon." }, IS_NPC, npcId,
					9827);
				} else if (componentId == OPTION_3) {	
					stage = 1;
					sendEntityDialogue(
							SEND_4_TEXT_CHAT,
							new String[] {
									NPCDefinitions.getNPCDefinitions(npcId).name,
									"Start location: Lumbridge Castle, speak with the cook. ",
									"Difficulty: Hard.",
									"Type: Investigation & Battle.",
									"Boss(es): King Black Dragon."}, IS_NPC, npcId,
							9827);
				} else if (componentId == OPTION_4) {
					stage = 1;
					sendEntityDialogue(
							SEND_4_TEXT_CHAT,
							new String[] {
									NPCDefinitions.getNPCDefinitions(npcId).name,
									"Start location: Lumbridge Castle, speak with the cook. ",
									"Difficulty: Hard.",
									"Type: Investigation & Battle.",
									"Boss(es): King Black Dragon."}, IS_NPC, npcId,
							9827);
					sendNPCDialogue(npcId,9827,
							"Start location: Edgeville Monastery, speak with Brother Althric. ",
							"Difficulty: Extreme hard.",
							"Type: Investigation & Battle.",
							"Boss: Ice Queen & Minions.");
				} else if (componentId == OPTION_5) {
					player.sm("More missions to come.");
					player.getInterfaceManager().closeChatBoxInterface();
	}
			
			}
			}
		
		
		}
	@Override
	public void finish() {
	}

}
