package com.rs.game.player.dialogues.impl;

import com.rs.game.player.Skills;
import com.rs.game.player.dialogues.Dialogue;

/**
 * Setting a skill level.
 * 
 * @author Raghav
 * 
 */
public class SetSkills extends Dialogue {

	int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		if (player.getEquipment().wearingArmour()) {
			sendDialogue("Please remove your armour first.");
			stage = -2;
		} else
			sendOptionsDialogue("Choose a skill", "" + Skills.SKILLS[0], ""
					+ Skills.SKILLS[1], "" + Skills.SKILLS[2], ""
					+ Skills.SKILLS[3], "More options.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (componentId == OPTION_1) {
				player.getAttributes().put("skillId", Skills.ATTACK);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter skill level:" });
			} else if (componentId == OPTION_2) {
				player.getAttributes().put("skillId", Skills.DEFENCE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter skill level:" });
			} else if (componentId == OPTION_3) {
				player.getAttributes()
						.put("skillId", Skills.STRENGTH);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter skill level:" });
			} else if (componentId == OPTION_4) {
				player.getAttributes().put("skillId",
						Skills.HITPOINTS);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter skill level:" });
			} else if (componentId == OPTION_5) {
				stage = 0;
				sendOptionsDialogue("Choose a skill",
						"" + Skills.SKILLS[4], "" + Skills.SKILLS[5],
						"" + Skills.SKILLS[6], "" + Skills.SKILLS[23],
						"Never mind.");
			}
		} else if (stage == 0) {
			if (componentId == OPTION_1) {
				player.getAttributes().put("skillId", Skills.RANGE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter skill level:" });
			} else if (componentId == OPTION_2) {
				player.getAttributes().put("skillId", Skills.PRAYER);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter skill level:" });
			} else if (componentId == OPTION_3) {
				player.getAttributes().put("skillId", Skills.MAGIC);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter skill level:" });
			} else if (componentId == OPTION_4) {
				player.getAttributes().put("skillId",
						Skills.SUMMONING);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter skill level:" });
			} else if (componentId == OPTION_5)
				end();
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}
