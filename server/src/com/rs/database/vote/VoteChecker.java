package com.rs.database.vote;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.rs.database.DatabasePool;
import com.rs.game.player.Player;

public class VoteChecker extends DatabasePool {

	public static VoteReward checkVote(Player player) {
		if (!connect(votedb)) {
			player.sendMessage("Database is currently inactive. Please contact King Fox.");
			return null;
		}
		
		String username = player.getDisplayName().replace(" ", "_");
		
		try {
			ResultSet rs = executeQuery("SELECT * FROM `has_voted` WHERE `username` = '" + username + "' AND `given` = '0'");
			while (rs.next()) {
				int rewardId = rs.getInt("rewardid");
				VoteReward reward = new VoteReward(username, rewardId);
				
				rs.updateBoolean("given", true);
				rs.updateRow();
				
				destroy();
				return reward;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error checking vote reward for '"+player.getDisplayName()+"'");
			return null;
		}
		return null;
	}
	
}