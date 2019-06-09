package com.rs.game.player.content;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import com.rs.cores.CoresManager;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Logger;
import com.rs.utils.Utils;
/**
 * @author King Fox
 */
public class PlayerGoals implements Serializable {
	private static final long serialVersionUID = 3904190801396385307L;
	
	private transient Player player;
	
	public int[] ACHIEVEMENTS;
	
	public PlayerGoals() {
		ACHIEVEMENTS = new int[25];
	}

	public void increase(int goalId) {
		if (ACHIEVEMENTS[goalId] < Integer.MAX_VALUE) {
			ACHIEVEMENTS[goalId]++;
			player.setSkillPoints(player.getSkillPoints() + 1);
		}
		
		if (ACHIEVEMENTS[goalId] == getReqs(goalId)) {
			checkRewards(goalId);
		}
		
	}
	
	public void setPlayer(Player player) {
		this.player = player;
		if (ACHIEVEMENTS == null || ACHIEVEMENTS.length == 0)
			ACHIEVEMENTS = new int[25];
	}
	
	public void checkstuff(int id) {
		checkRewards(id);
	}
	
	public void autoComplete(int id) {
		ACHIEVEMENTS[id] = getReqs(id);
		checkRewards(id);
	}
	
	public void checkRewards(int goalId) {
		if (getDesc(goalId) == null)
			return;
		player.getDialogueManager().startDialogue("GoalsDialogue", getDesc(goalId));
		World.sendWorldMessage("<img=7><shad=FF4400>[News] "+player.getDisplayName()+" has just completed: "+getDesc(goalId)+"!", false);
		player.setSkillPoints(player.getSkillPoints() + 100);
		if (hasCompletedAll(false)) {
			World.sendWorldMessage("<img=7><shad=FF4400>[News] "+player.getDisplayName()+" has completed all achievements!", false);
		}
	}
	
	public enum SkillGoals {
		
		ATTACK		(""),
		DEFENCE		(""),
		STRENGTH	(""),
		HITPOINTS	(""),
		RANGE		(""),
		PRAYER		(""),
		MAGIC		(""),
		COOKING		("Cook "+getReqs(Skills.COOKING)+" Fish"),
		WOODCUTTING	("Chop "+getReqs(Skills.WOODCUTTING)+" Trees"),
		FLETCHING	("Fletch "+getReqs(Skills.FLETCHING)+" Bows"),
		FISHING		("Catch "+getReqs(Skills.FISHING)+" Fish"),
		FIREMAKING	("Light "+getReqs(Skills.FIREMAKING)+" Logs"),
		CRAFTING	("Craft "+getReqs(Skills.CRAFTING)+" Items"),
		SMITHING	("Smith "+getReqs(Skills.SMITHING)+" Items"),
		MINING		("Mine "+getReqs(Skills.MINING)+" Ores"),
		HERBLORE	("Make "+getReqs(Skills.HERBLORE)+" Potions"),
		AGILITY		("Cross "+getReqs(Skills.AGILITY)+" Obstacles"),
		THIEVING	("Thieve "+getReqs(Skills.THIEVING)+" Times"),
		SLAYER		("Slay "+getReqs(Skills.SLAYER)+" NPCs"),
		FARMING		("Rake "+getReqs(Skills.FARMING)+" Patches"),
		RUNECRAFT	("Siphon "+getReqs(Skills.RUNECRAFTING)+" NPCs"),
		HUNTER		("Capture "+getReqs(Skills.HUNTER)+" NPCs"),
		CONSTRUCT	("Build "+getReqs(Skills.CONSTRUCTION)+" Times"),
		SUMMONING	("Make "+getReqs(Skills.SUMMONING)+" Pouches"),
		DUNG		("Kill "+getReqs(Skills.DUNGEONEERING)+" Dung NPCs");
		
		private String desc;
		SkillGoals(String goalName) {
			this.desc = goalName;
		}
		
	}
	
	public int getNumberCompleted() {
		int count = 0;
		for (int i = 7; i < ACHIEVEMENTS.length;i++) {
			if (ACHIEVEMENTS[i] == getReqs(i)) {
				count++;
			}
		}
		return count;
	}
	
	public String getDesc(int skill) {
		for (SkillGoals sg : SkillGoals.values()) {
			if (sg.ordinal() == skill) {
				return sg.desc;
			}
		}
		return "";
	}
	
	public static final int totalAchs = 18;
	
	public void openGoalMenu() {
		
		player.getInterfaceManager().sendInterface(629);
		player.getPackets().sendIComponentText(629, 11, "Achievements");
		player.getPackets().sendIComponentText(629, 12, "");
		
		player.getPackets().sendIComponentText(629, 67, "Close");
		player.getPackets().sendIComponentText(629, 68, "Close");
		
		player.getPackets().sendIComponentText(629, 41, ""+(player.getBandosKills() >= 10 ? "<str=FF0000>" : "")+"Kill Bandos x10 "+(player.getBandosKills() >= 10 ? "" : "("+player.getBandosKills()+"/10)")+"");
		player.getPackets().sendIComponentText(629, 42, ""+(player.getArmadylKills() >= 10 ? "<str=FF0000>" : "")+"Kill Armadyl x10 "+(player.getArmadylKills() >= 10 ? "" : "("+player.getArmadylKills()+"/10)")+"");
		player.getPackets().sendIComponentText(629, 43, ""+(player.getZamorakKills() >= 10 ? "<str=FF0000>" : "")+"Kill Zamorak x10 "+(player.getZamorakKills() >= 10 ? "" : "("+player.getZamorakKills()+"/10)")+"");
		player.getPackets().sendIComponentText(629, 44, ""+(player.getSaradominKills() >= 10 ? "<str=FF0000>" : "")+"Kill Saradomin x10 "+(player.getSaradominKills() >= 10 ? "" : "("+player.getSaradominKills()+"/10)")+"");
		player.getPackets().sendIComponentText(629, 45, "----------");
		player.getPackets().sendIComponentText(629, 46, "----------");
		player.getPackets().sendIComponentText(629, 47, "----------");
		
		for (int i = 7; i < 25; i++) {
			String prefix = ACHIEVEMENTS[i] >= getReqs(i) ? "<str=FF0000>" : "";
			String suffix = ACHIEVEMENTS[i] >= getReqs(i) ? "</str>" : "";
			int current = ACHIEVEMENTS[i];
			String status = ACHIEVEMENTS[i] >= getReqs(i) ? "" : " ("+current+"/"+ getReqs(i)+")";
			player.getPackets().sendIComponentText(629, (i + 41), "" + prefix + getDesc(i) + suffix + status+"");
		}
		
	}

	public boolean hasCompletedAll(boolean list) {
		for (int i = 7; i < ACHIEVEMENTS.length;i++) {
			if (ACHIEVEMENTS[i] < getReqs(i)) {
				if (list)
					player.sendMessage("You still need to complete: "+getDesc(i)+"");
				return false;
			}
		}
		return false;
	}
	
	public static int getReqs(int skill) {
		switch (skill) {
		case Skills.AGILITY:
			return 100;
		case Skills.HERBLORE:
			return 500;
		case Skills.THIEVING:
			return 1000;
		case Skills.CRAFTING:
			return 250;
		case Skills.MINING:
			return 500;
		case Skills.SMITHING:
			return 300;
		case Skills.FISHING:
			return 300;
		case Skills.COOKING:
			return 300;
		case Skills.FIREMAKING:
			return 500;
		case Skills.WOODCUTTING:
			return 400;
		case Skills.SLAYER:
			return 300;
		case Skills.FARMING:
			return 200;
		case Skills.CONSTRUCTION:
			return 200;
		case Skills.HUNTER:
			return 300;
		case Skills.SUMMONING:
			return 200;
		case Skills.DUNGEONEERING:
			return 200;
		case Skills.FLETCHING:
			return 300;
		case Skills.RUNECRAFTING:
			return 500;
		default:
			return -1;
		}
	}
	
	
}
