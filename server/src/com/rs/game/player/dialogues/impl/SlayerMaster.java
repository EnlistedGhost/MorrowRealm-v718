package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

public class SlayerMaster extends Dialogue {

	@Override
	public void start() {
		sendEntityDialogue(IS_NPC, "Kuradal", 9085, 9827, "Hello warrior, What can i do for you?");
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			sendOptionsDialogue("Slayer Options", 
					"I would like a new Slayer Task", 
					"Reset my slayer task (Cost: 500k)",
					"Tell me my current task",
					"I have completed my current task",
					"Show me the Rewards Shop");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				if (player.getTask().isComplete()) {
					sendEntityDialogue(IS_NPC, "Kuradal", 9085, 9827, "Turn in your previous task before getting a new one!");
					stage = 1;
					return;
				}
				if (player.getTask().getTaskAmount() > 0){
					sendEntityDialogue(IS_NPC, "Kuradal", 9085, 9827, "Please reset or finsh your ", "task if you want another!");
					stage = 1;
					return;
				}
				player.getTask().getNewTask();
				int amount = player.getTask().getTaskAmount();
				sendEntityDialogue(IS_NPC, "Kuradal", 9085, 9827, "Your new task is to kill:", "x"+amount+" "+player.getTask().getName()+"");
				stage = 1;
			} else if (componentId == OPTION_2) {
				if (player.getInventory().containsItem(995, 500000)) {
					player.getTask().getNewTask();
					int amount = player.getTask().getTaskAmount();
					player.getInventory().deleteItem(995, 500000);
					sendEntityDialogue(IS_NPC, "Kuradal", 9085, 9827, "Your new task is to kill:", "x"+amount+" "+player.getTask().getName()+"");
				} else {
					sendEntityDialogue(IS_NPC, "Kuradal", 9085, 9827, "Please come back when you", "have atleast 500k on you.");
					stage = 1;
				}
			} else if (componentId == OPTION_3) {
				int amount = player.getTask().getTaskAmount();
				if (player.getTask().getTaskAmount() == -2) {
					sendEntityDialogue(IS_NPC, "Kuradal", 9085, 9827, "You do not have a task.");
				} else {
					sendEntityDialogue(IS_NPC, "Kuradal", 9085, 9827, "Your current task is to kill:", "x"+amount+" "+player.getTask().getName()+"");
				}
				stage = 1;
			} else if (componentId == OPTION_4) {
				if (player.getTask().getCurrentTask() > 0) {
					sendEntityDialogue(IS_NPC, "Kuradal", 9085, 9827, "You still have remaining:", "x"+player.getTask().getTaskAmount()+" "+player.getTask().getName()+"");
					stage = 1;
				} else {
					if (player.getTask().isComplete()) {
						sendEntityDialogue(IS_NPC, "Kuradal", 9085, 9827, "Very good! Have some slayer points!", "You can now set a new task.");
						boolean hasSlayerRing = player.getEquipment().getRingId() == 13281;
						player.setSlayerPoints(player.getSlayerPoints() + (hasSlayerRing == true ? 40 : 20));
						player.getTask().resetTask();
						stage = 1;
					} else {
						sendEntityDialogue(IS_NPC, "Kuradal", 9085, 9827, "You do not currently have a task.");
						stage = 1;
					}
				}
			} else if (componentId == OPTION_5) {
				end();
				player.getInterfaceManager().sendInterface(164);
				player.getPackets().sendIComponentText(164, 20, ""+player.getSlayerPoints()+"");
			}
		}
		
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
