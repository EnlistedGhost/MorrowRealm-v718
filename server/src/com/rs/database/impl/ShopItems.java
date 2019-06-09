package com.rs.database.impl;

import java.util.ArrayList;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class ShopItems {
	
	public enum StoreItems {
		
		FULL_TORVA_SET(4, 20135, 20139, 20143, 24983, 25060),
		FULL_PRIMAL_SET(5, 16293, 16359, 16711, 17259, 17361),
		FULL_PERNIX_SET(6, 20147, 20151, 20155, 24974, 16689, 24989);
	
		private int productId;
		private int[] items;
	
		StoreItems(int productId, int... items) {
			this.productId = productId;
			this.items = items;
		}
		
		public int getProductId() {
			return productId;
		}
	
		public int[] getItems() {
			return items;
		}
		
		public String getName() {
			return Utils.formatString(name());
		}
	}
	
	public static void claimItems(Player player, int id, int amount) {
		Item item;
		for (StoreItems s : StoreItems.values()) {
			if (s.productId == id) {
				for (int i = 0; i < s.items.length; i++) {
					item = new Item(s.items[i], amount);
					player.getInventory().addItem(item);
				}
				player.sendMessage("You've successfully claimed: <col=FF0000>"+s.getName()+"</col>");
				return;
			}
		}
		player.sendMessage("There was an error processing your order.");
		return;
	}
	
}
