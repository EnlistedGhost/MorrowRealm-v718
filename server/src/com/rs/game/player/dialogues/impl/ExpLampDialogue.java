package com.rs.game.player.dialogues.impl;

import com.rs.game.player.Skills;
import com.rs.game.player.dialogues.Dialogue;


public class ExpLampDialogue extends Dialogue {
	
	int skill;
	int exp;
	String lampType;
	int itemid;
	
	@Override
	public void start() {
		this.skill = (int)parameters[0];
		this.exp = (int)parameters[1];
		this.lampType = (String) parameters[2];
		this.itemid = (int)parameters[3];
		if (stage == -1) {
			sendOptionsDialogue(""+lampType+" <col=FF0000>"+Skills.SKILLS[skill]+"</col> Exp.", "Yes, i want to claim now.", "Nevermind.");
			stage = 1;
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.getSkills().addXp(skill, exp);
				player.getInventory().deleteItem(itemid, 1);
				sendDialogue("You have claimed a "+lampType+" Lamp of", "<col=FF0000>"+Skills.SKILLS[skill]+"</col> Experience!");
				stage = 2;
			} else if (componentId == OPTION_2) {
				end();
			}
		} else if (stage == 2) {
			end();
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}
	
}
