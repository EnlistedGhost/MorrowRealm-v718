package com.rs.database.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.rs.Launcher;
import com.rs.Settings;
import com.rs.database.DatabasePool;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class DonationManager extends DatabasePool {
	
	
	public static void checkDonation(Player player) {
		try {
			if (!connect(donatedb))
				return;
			String name = Utils.formatString(player.getUsername());
			ResultSet rs = executeQuery("SELECT * FROM donations WHERE username='"+name+"' AND claimed='0'");
			double amount;
			int packageId;
			if (rs.next()) {
				packageId = rs.getInt("package");
				amount = packageId == 1 ? 5.0 :
						 packageId == 2 ? 10.00 :
						 packageId == 3 ? 20.00 : 0;
				getReward(player, packageId);
				player.setDonationAmount(player.getDonationAmount() + amount);
				promotePlayer(player, player.getDonationAmount());
				World.sendWorldMessage("<shad=FF0000>"+name+" has just donated $"+amount+"0, making their total $"+player.getDonationAmount()+"0!", false);
				player.sendMessage("Thank you for your donation! Your support is greatly appreciated!");
				rs.updateInt("claimed", 1);
				rs.updateRow();
				destroy();
				return;
			}
			player.sendMessage("You have nothing waiting for you.");
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return;
	}
	
	public static void getReward(Player player, int packageId) {
		switch (packageId) {
			case 1:
				player.getInventory().addItem(18201, 500);
				player.getInventory().addItem(24155, 2);
				player.getInventory().addItem(24154, 1);
				return;
				
			case 2:
				player.getInventory().addItem(18201, 1000);
				player.getInventory().addItem(24155, 5);
				player.getInventory().addItem(28004, 1);
				return;
				
			case 3:
				player.getInventory().addItem(18201, 2000);
				player.getInventory().addItem(24155, 10);
				player.getInventory().addItem(28004, 1);
				player.setLoyaltyPoints(player.getLoyaltyPoints() + 2000);
				player.sendMessage("You've received 2,000 loyalty points!");
				return;
				
			default:
				player.sendMessage("Invalid package Id Detected. Please contact a server admin.");
				return;
		}
	}
	
	public static void promotePlayer(Player player, double donated) {
		if (player.getRights() != 1 && player.getRights() != 2) {
			if (donated <= 5) {
				if (player.getRights() == 0) {
					player.setRights(4);
					player.sendMessage("You have been promoted to "+getStatus(player.getRights())+"");
					return;
				}
			} else if (donated <= 10) {
				if (player.getRights() == 0 || player.getRights() == 4) {
					player.setRights(5);
					player.sendMessage("You have been promoted to "+getStatus(player.getRights())+"");
					return;
				}
			} else if (donated <= 20) {
				if (player.getRights() == 0 || player.getRights() == 4 || player.getRights() == 5) {
					player.setRights(6);
					player.sendMessage("You have been promoted to "+getStatus(player.getRights())+"");
					return;
				}
			}
		}
	}
	
	public static String getStatus(int rights) {
		if (rights == 4)
			return "<img=4><col=FF0000>Regular Donator</col>";
		if (rights == 5)
			return "<img=5><col=0000FF>Super Donator</col>";
		if (rights == 6)
			return "<img=6><col=00FF00>Extreme Donator</col>";
		return null;
	}
	
}
