package com.rs.content.utils;

import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class MoneyPouch {
	
	static boolean disabled = false;
	
	public static void removeMoney(int amount, Player player, boolean addToInventory) {
		
		if (player.getMoneyPouch() == 0 || amount <= 0) {
			return;
		}
		
		int inInventory = player.getInventory().getItems().getNumberOf(995);
		
		if (inInventory == Integer.MAX_VALUE) {
			if (addToInventory)
				player.sendMessage("Your inventory can't hold any more coins!");
			return;
		}
		
		if (amount > player.getMoneyPouch())
			amount = player.getMoneyPouch();
		
		int toWithdraw = (inInventory + amount);
		
		if (toWithdraw < 0)
			amount = (Integer.MAX_VALUE - inInventory);
		
		player.getPackets().sendRunScript(5561, 0, "n", amount);
		player.getPackets().sendRunScript(5560, player.getMoneyPouch() - amount, "n");
		player.setMoneyPouch(player.getMoneyPouch() - amount);
		if (addToInventory == true) {
			player.getInventory().addItem(995, amount);
		}
		player.out(Utils.formatNumber(amount) +" coins have been removed from your money pouch.");
		return;
	}
	
	
	public static boolean addMoney(int amount, Player player, boolean delete) {
		if (disabled && player.getRights() != 2) {
			player.sm("Temporarily Disabled.");
			return false;
		}
		if (player.getMoneyPouch() == Integer.MAX_VALUE) {
			player.sendMessage("Your money pouch is full.");
			return false;
		}
		if ((player.getMoneyPouch() + amount) < 1) {
			amount = (int) (Integer.MAX_VALUE - player.getMoneyPouch());
		}
		if (amount > 0) {
			player.getPackets().sendRunScript(5561, 1, amount);
			player.setMoneyPouch(player.getMoneyPouch() + amount);
			if (delete)
				player.getInventory().deleteItem(995, amount);
			player.sendMessage(Utils.formatNumber(amount)+" coin"+(amount > 1 ? "s" : "")+" have been added to your money pouch.");
			player.refreshMoneyPouch();
			return true;
		}
		player.refreshMoneyPouch();
		return false;
	}
	
}
