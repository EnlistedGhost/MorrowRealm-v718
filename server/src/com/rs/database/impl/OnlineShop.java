package com.rs.database.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.rs.database.DatabasePool;
import com.rs.database.impl.ShopItems.StoreItems;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class OnlineShop extends DatabasePool {
	
	public static void checkPurchase(Player player) {
		String username = player.getUsername().replace("_", " ");
		int userId = getUid(username);
		ResultSet rs = executeQuery("SELECT * FROM microcart_orders WHERE userid="+userId+"");
		try {
			while (rs.next()) {
				int productId = rs.getInt("productid");
				int quantity = rs.getInt("quantity");
				boolean claimed = rs.getBoolean("claimed");
				double price = rs.getDouble("price");
				boolean paid = rs.getBoolean("paid");
				if (paid == false || claimed == true) {
					continue;
				}
				for (StoreItems s : StoreItems.values()) {
					if (s.getProductId() == productId) {
						if (player.getInventory().getFreeSlots() < (s.getItems().length * quantity)) {
							player.sendMessage("Unable to claim item: <col=FF0000>"+s.getName()+"</col>. Reason: <col=FF0000>Not enough space</col>.");
							continue;
						}
						player.getInventory().addItems(s.getItems(), quantity);
						rs.moveToCurrentRow();
						rs.updateBoolean("claimed", true);
						rs.updateRow();
						player.sendMessage("Sucessfully Claimed: <col=FF0000>"+s.getName()+"</col> (Amount: "+quantity+", Price: $"+price+"0)");
					}
				}
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public enum StoreItems {
		
		FULL_TORVA_SET(4, 20135, 20139, 20143, 24983, 25060),
		FULL_PRIMAL_SET(5, 16293, 16359, 16711, 17259, 17361),
		FULL_PERNIX_SET(6, 20147, 20151, 20155, 24974, 24989, 25058);
	
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

}
