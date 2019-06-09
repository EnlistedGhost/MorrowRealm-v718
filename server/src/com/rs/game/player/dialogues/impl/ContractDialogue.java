package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.content.contracts.ContractHandler;
import com.rs.game.player.dialogues.Dialogue;

public class ContractDialogue extends Dialogue {

	public  static final int idNo = 13633;
	
	@Override
	public void start() {
		sendEntityDialogue(SEND_3_TEXT_CHAT, new String[] { "Contractor",
				"So you're looking to get rewarded for your boss fights eh?",
				"I offer random boss contracts that come with hefty rewards.",
				"What do you need, "+player.getDisplayName()+""
			}, IS_NPC, idNo, 9827);
			stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		String npcName = "";
		
		if (player.getContract() != null) {
			npcName = NPCDefinitions.getNPCDefinitions(player.getContract().getNpcId()).getName();
		}
		
		if (stage == 1) {
			sendOptionsDialogue("Select an Option",
					"I would like to get a new contract",
					"I have completed my current contract",
					"I wish to check on my progress",
					"I want to get a different contract",
					"Nevermind, i got it.");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				if (player.getContract() != null) {
					if (player.getContract().hasCompleted()) {
						sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { "Contractor",
								"Your current contract is complete. Turn it in",
								"for your reward and collect another if you like."
						}, IS_NPC, idNo, 9827);
						stage = 1;
					} else {
						sendEntityDialogue(SEND_4_TEXT_CHAT, new String[] { "Contractor",
								"You already have an active contract.<br>",
								"Complete this one or get a new one if you want.<br>",
								"Current Contract: <col=FF0000>"+npcName+"</col><br>",
								"Reward: <col=0000FF>"+player.getContract().getRewardAmount() / 1000000+"M</col>"
						}, IS_NPC, idNo, 9827);
						stage = 1;
					}
				} else {
					ContractHandler.getNewContract(player);
					npcName = NPCDefinitions.getNPCDefinitions(player.getContract().getNpcId()).getName();
					sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { "Contractor",
						"Your new contract is to kill:<br>",
						"<col=FF0000>"+npcName+"<br>",
						"Reward: <col=0000FF>"+player.getContract().getRewardAmount() / 1000000+"M</col>"
					}, IS_NPC, idNo, 9827);
					stage = 1;
				}
			}
			
			if (componentId == OPTION_2) {
				if (player.getContract() != null) {
					if (player.getContract().hasCompleted()) {
						if (player.getInventory().add(995, player.getContract().getRewardAmount())) {
							player.setContract(null);
							sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { "Contractor",
								"Very good, here is your reward as promised.<br>",
								"Collect a new contract and keep up the good work!"
							}, IS_NPC, idNo, 9827);
							stage = 1;
						}
					} else {
						sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { "Contractor",
							"Your current contract has not been completed.<br>",
							"Current: <col=FF0000>"+npcName+"</col><br>",
							"Reward: <col=0000FF>"+player.getContract().getRewardAmount() / 1000000+"M</col>"
						}, IS_NPC, idNo, 9827);
						stage = 1;
					}
					stage = 1;
				} else {
					sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { "Contractor",
						"You don't currently have a contract.",
						"Collect a new contract if you wish to start one."
					}, IS_NPC, idNo, 9827);
					stage = 1;
				}
			}
			
			if (componentId == OPTION_3) {
				if (player.getContract() == null) {
					sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { "Contractor",
							"You don't currently have a contract.",
							"Collect a new contract if you wish to start one."
						}, IS_NPC, idNo, 9827);
						stage = 1;
				} else {
					if (player.getContract().hasCompleted()) {
						sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { "Contractor",
								"Your current contract is complete. Turn it in",
								"for your reward and collect another if you like."
						}, IS_NPC, idNo, 9827);
						stage = 1;
					} else {
						sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { "Contractor",
							"Your current contract has not been completed.<br>",
							"Current: <col=FF0000>"+npcName+"</col><br>",
							"Reward: <col=0000FF>"+player.getContract().getRewardAmount() / 1000000+"M</col>"
						}, IS_NPC, idNo, 9827);
						stage = 1;
					}
				}
			}
			
			if (componentId == OPTION_4) {
				sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { "Contractor",
					"Choosing this will cost you <col=FF0000>500k</col><br>",
					"Do you wish to proceed?"
				}, IS_NPC, idNo, 9827);
				stage = 3;				
			}
			
			if (componentId == OPTION_5) {
				end();
			}
			
		} else if (stage == 3) {
			sendOptionsDialogue("Select an Option",
					"Yes, I wish to change my contract",
					"No, i do not want to change my contract");
			stage = 4;
		} else if (stage == 4) {
			if (componentId == OPTION_1) {
				if (player.getContract() == null) {
					sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { "Contractor",
						"You don't currently have a contract.",
						"Collect a new contract if you wish to start one."
					}, IS_NPC, idNo, 9827);
					stage = 1;
				} else {
					if (player.getInventory().containsItem(995, 500000)) {
						player.setContract(null);
						ContractHandler.getNewContract(player);
						npcName = NPCDefinitions.getNPCDefinitions(player.getContract().getNpcId()).getName();
						sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { "Contractor",
								"Your new contract is to kill:<br>",
								"<col=FF0000>"+npcName+"<br>",
								"Reward: <col=0000FF>"+player.getContract().getRewardAmount() / 1000000+"M</col>"
						}, IS_NPC, idNo, 9827);
						player.getInventory().deleteItem(995, 500000);
						stage = 1;
					} else {
						sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { "Contractor",
								"Come back when you have more coins.",
								"You need atleast 500k in your inventory."
						}, IS_NPC, idNo, 9827);
						stage = 1;
					}
				}
			} else {
				end();
			}
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}
	
}
