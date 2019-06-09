package com.rs.database.impl;

import java.sql.ResultSet;

import com.rs.database.DatabasePool;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;

public class Highscores extends DatabasePool {
	
	public static boolean updateHighscores(Player player) {
		if (player.getRights() == 10) {
			player.sendMessage("You're not allowed to update your highscores.");
			return false;
		}
		if (!connect(hsdb)) {
			player.sendMessage("A connection could not be established with the database. Please try again.");
			return false;
		}
		
		try {
			String username = Utils.formatString(player.getUsername());
			ResultSet rs = executeQuery("SELECT * FROM hs_users WHERE username='"+username+"' LIMIT 1");
			boolean exists = rs.next();
			
			if (exists) {
				rs.moveToCurrentRow();
				rs.updateString("username", username);
				rs.updateInt("rights", player.getRights());
				rs.updateInt("prestige", player.getPrestigeLevel());
				rs.updateInt("difficulty", player.getDifficulty());
				rs.updateInt("maxZombies", player.getMaxZombieWave());
				rs.updateInt("fightCaves", player.isCompletedFightCaves() == true ? 1 : 0);
				rs.updateInt("fightKiln", player.isCompletedFightKiln() == true ? 1 : 0);
				rs.updateLong("overall_xp", getTotalXp(player));
				for (int i = 0; i < 25; i++) {
					rs.updateInt(""+Skills.SKILLS[i].toLowerCase()+"_xp", (int)player.getSkills().getXp(i));
				}
				rs.updateRow();
				destroy();
				return true;
			} else {
				rs.moveToInsertRow();
				rs.updateString("username", username);
				rs.updateInt("rights", player.getRights());
				rs.updateInt("prestige", player.getPrestigeLevel());
				rs.updateInt("difficulty", player.getDifficulty());
				rs.updateInt("maxZombies", player.getMaxZombieWave());
				rs.updateInt("fightCaves", player.isCompletedFightCaves() == true ? 1 : 0);
				rs.updateInt("fightKiln", player.isCompletedFightKiln() == true ? 1 : 0);
				rs.updateLong("overall_xp", getTotalXp(player));
				for (int i = 0; i < 25; i++) {
					rs.updateInt(""+Skills.SKILLS[i].toLowerCase()+"_xp", (int)player.getSkills().getXp(i));
				}
				rs.insertRow();
				destroy();
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			player.sendMessage("An error has occured when attempting to update your scores.");
			player.sendMessage("Please try again.");
			destroy();
			return false;
		}
	}
	
	public static long getTotalXp(Player player) {
		long totalxp = 0;
		for (double xp : player.getSkills().getXp()) {
			totalxp += xp;
		}
		return totalxp;
	}

}
