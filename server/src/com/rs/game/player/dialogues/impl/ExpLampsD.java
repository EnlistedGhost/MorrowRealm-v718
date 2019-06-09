package com.rs.game.player.dialogues.impl;

import com.rs.game.player.Skills;
import com.rs.game.player.dialogues.Dialogue;

public class ExpLampsD extends Dialogue {

	private int itemId;
	
	@Override
	public void start() {
		this.itemId = (Integer) parameters[0];
		if (stage == -1) {
			sendOptionsDialogue("Experience Type", "Combat Exp", "Skill Exp");
			stage = 1;
		}
	}

	
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue("Experience Type", "Attack", "Strength", "Defence", "Ranged", "Magic");
				stage = 2;
			} else if (componentId == OPTION_2) {
				sendOptionsDialogue("Experience Type", "Cooking", "Woodcutting", "Fletching", "Fishing", "More Options");
				stage = 3;
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				addSkillExp(Skills.ATTACK, getExp());
				end();
			} else if (componentId == OPTION_2) {
				addSkillExp(Skills.STRENGTH, getExp());
				end();
			} else if (componentId == OPTION_3) {
				addSkillExp(Skills.DEFENCE, getExp());
				end();
			} else if (componentId == OPTION_4) {
				addSkillExp(Skills.RANGE, getExp());
				end();
			} else if (componentId == OPTION_5) {
				addSkillExp(Skills.MAGIC, getExp());
				end();
			}
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				addSkillExp(Skills.COOKING, getExp());
				end();
			} else if (componentId == OPTION_2) {
				addSkillExp(Skills.WOODCUTTING, getExp());
				end();
			}else if (componentId == OPTION_3) {
				addSkillExp(Skills.FLETCHING, getExp());
				end();
			} else if (componentId == OPTION_4) {
				addSkillExp(Skills.FISHING, getExp());
				end();
			}else if (componentId == OPTION_5) {
				sendOptionsDialogue("Experience Type", "Firemaking", "Crafting", "Smithing", "Mining", "More Options");
				stage = 4;
			}
		} else if (stage == 4) {
			if (componentId == OPTION_1) {
				addSkillExp(Skills.FIREMAKING, getExp());
				end();
			} else if (componentId == OPTION_2) {
				addSkillExp(Skills.CRAFTING, getExp());
				end();
			} else if (componentId == OPTION_3) {
				addSkillExp(Skills.SMITHING, getExp());
				end();
			} else if (componentId == OPTION_4) {
				addSkillExp(Skills.MINING, getExp());
				end();
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Experience Type", "Herblore", "Agility", "Thieving", "Slayer", "More Options");
				stage = 5;
			}
		} else if (stage == 5) {
			if (componentId == OPTION_1) {
				addSkillExp(Skills.HERBLORE, getExp());
				end();
			} else if (componentId == OPTION_2) {
				addSkillExp(Skills.AGILITY, getExp());
				end();
			} else if (componentId == OPTION_3) {
				addSkillExp(Skills.THIEVING, getExp());
				end();
			} else if (componentId == OPTION_4) {
				addSkillExp(Skills.SLAYER, getExp());
				end();
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Experience Type", "Farming", "Construction", "Hunter", "Summoning", "More Options");
				stage = 6;
			}
		} else if (stage == 6) {
			if (componentId == OPTION_1) {
				addSkillExp(Skills.FARMING, getExp());
				end();
			} else if (componentId == OPTION_2) {
				addSkillExp(Skills.CONSTRUCTION, getExp());
				end();
			} else if (componentId == OPTION_3) {
				addSkillExp(Skills.HUNTER, getExp());
				end();
			} else if (componentId == OPTION_4) {
				addSkillExp(Skills.SUMMONING, getExp());
				end();
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Experience Type", "Dungeoneering", "First Page");
				stage = 7;
			}
		} else if (stage == 7) {
			if (componentId == OPTION_1) {
				addSkillExp(Skills.DUNGEONEERING, getExp());
				end();
			} else if (componentId == OPTION_2) {
				sendOptionsDialogue("Experience Type", "Combat Exp", "Skill Exp");
				stage = 1;
			}
		}
	}
	
	public void addSkillExp(int skill, int exp) {
		if (!player.getInventory().containsItem(itemId, 1) || exp == 0) {
			end();
			player.sendMessage("Either you tried something you aren't suppose to");
			player.sendMessage("or something legit went wrong. Please report this asap.");
			return;
		}
		player.getSkills().addXp(skill, exp);
		player.getInventory().deleteItem(itemId, 1);
		end();
	}
	
	public int getExp() {
		if (itemId == 23713)
			return 100;
		if (itemId == 23714)
			return 500;
		if (itemId == 23715)
			return 1000;
		if (itemId == 23716)
			return 5000;
		return 0;
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}
	
	
}
