package com.rs.game.player.content.shops;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.guardian.ItemManager;
import com.rs.utils.Utils;

public class SlayerShop {

	public static void buy(Player player, Item item, int quantity) {
		int value = ItemManager.getPrice(item.getId());
		int totPrice = (value * quantity);
			
		if (quantity <= 0) {
			player.sendMessage("You must buy atleast one of this item.");
			return;
		}
				
		if (totPrice < value) {
			return;
		}
				
		if (item.getAmount() <= 0) {
			player.sendMessage("Shop is currently out of stock.");
			return;
		}
				
		if (player.getSlayerPoints() < totPrice) {
			player.getPackets().sendGameMessage("You need " + Utils.formatNumber(totPrice) + " DP to buy this item! (You have "+Utils.formatNumber(player.getSlayerPoints())+")");
			return;
		}
				
		player.getPackets().sendGameMessage("You have bought "+quantity+" " + item.getDefinitions().getName() + " for "+Utils.formatNumber(totPrice)+" Slayer Points.");
		player.getInventory().addItem(item.getId(), quantity);
		player.setSlayerPoints(player.getSlayerPoints() - totPrice);
	}
	
	public static void sendExamine(Player player, Item item) {
		int value = ItemManager.getPrice(item.getId());
		player.getPackets().sendGameMessage("" + item.getDefinitions().getName() + " is worth " + Utils.formatNumber(value) + " slayer points.");
		player.getPackets().sendConfig(2564, value);
	}
	
	public static void sell(Player player, Item item, int quantity) {
		player.sendMessage("You're not allowed to resell items to this store.");
	}
	
}