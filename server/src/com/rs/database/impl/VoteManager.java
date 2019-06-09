package com.rs.database.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.rs.Settings;
import com.rs.database.DatabasePool;
import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.content.custom.TitleHandler;
import com.rs.utils.Utils;

public class VoteManager {

	public static boolean checkPlayerVote(Player player) {
		try {
			String playerName = player.getUsername().toLowerCase().replace("_", " ");
			ResultSet rs = DatabasePool.executeQuery("SELECT * FROM voter_database WHERE username='"+playerName+"' LIMIT 1");
				if (!rs.next()) {
					player.getPackets().sendGameMessage("You havent voted yet, type ::vote to do so now!");
					return true;
				}
				boolean hasVoted = rs.getBoolean("hasVoted");
				if (hasVoted) {
					claimPrize(player);
				} else {
					player.getPackets().sendGameMessage("You have already claimed your reward! Vote again for another!");
				}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return false;
	}
	
	
	public static void handleVoteButtons(Player player, int componentId) {
		}
	
	public static void claimPrize(Player player) {
		if (player.getInventory().getItems().getFreeSlots() < 28) {
			player.sendMessage("<col=FF0000>Please bank your inventory before claiming your vote reward.");
			return;
		}
		player.getPackets().sendIComponentText(1312, 38, "<col=00FF00>1,000,000 GP");
		player.getPackets().sendIComponentText(1312, 46, "<col=00FF00>Double Spin Ticket");
		player.getPackets().sendIComponentText(1312, 54, "<col=00FF00>500 Skill Gems");
		player.getPackets().sendIComponentText(1312, 62, "<col=00FF00>Blue Voting Hat");
		player.getPackets().sendIComponentText(1312, 70, "<col=00FF00>200 Mithril Seeds");
		player.getPackets().sendIComponentText(1312, 78, "<col=00FF00>Voter Title");
		player.getPackets().sendIComponentText(1312, 86, "<col=00FF00>Red Voting Hat");
		player.getPackets().sendIComponentText(1312, 94, "<col=00FF00>150 Monkfish");
		player.getPackets().sendIComponentText(1312, 102, "<col=00FF00>75 Tiger Sharks (300 heal)");
		player.getInterfaceManager().sendInterface(1312);
	}
	
	public static String formatName(String name) {
		if (name == null)
			return "";
		name = name.replaceAll("_", " ");
		name = name.toLowerCase();
		return name;
	}
	
}
