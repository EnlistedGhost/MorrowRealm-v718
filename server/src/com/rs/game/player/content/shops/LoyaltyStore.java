package com.rs.game.player.content.shops;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public enum LoyaltyStore {
	
	POSION_PURGE(20958, 5000),
	GREATER_POISON_PURGE(22268, 9000),
	RUNIC_ACCURACY(20962, 5000),
	GREATER_RUNIC_ACCURACY(22270, 10000),
	SHARPSHOOTER(20967, 5000),
	GREATER_SHARPSHOOTER(22272, 8000),
	LUMBERJACK(22280, 5000),
	GREATER_LUMBERJACK(22282, 9000),
	QUARRYMASTER(22284, 5000),
	GREATER_QUARRYMASTER(22286, 8000),
	CALL_OF_THE_SEA(20966, 5000),
	GREATER_COS(22274, 10000),
	REVERANCE(20965, 5000),
	GREATER_REVERANCE(22276, 8000),
	FIVE_FINGER_DISCOUNT(22288, 5000),
	GREATER_FFD(22290, 8000),
	RESOURCEFUL(22292, 5000),
	EQUILIBRIUM(22294, 10000),
	PENANCE(22300, 7000),
	ISPIRATION(22296, 5000),
	VAMPYRISM(22298, 10000),
	WISDOM(22302, 9000),
	SUPREME_INVIGORATE(23846, 9000),
	SUPREME_HARMONY(23854, 9000),
	SUPREME_CORRUPTION(23874, 9000),
	SUPREME_SALVATION(23876, 9000);
	
	private int itemId;
	private int buyPrice;
	private static boolean disabled = false;
	
	LoyaltyStore(int itemId, int price) {
		this.itemId = itemId;
		this.buyPrice = price;
	}
	
	public static void buy(Player player, Item item, int quantity) {
		for (LoyaltyStore v : LoyaltyStore.values()) {
			if (item.getId() == v.itemId) {
				int totPrice = (v.buyPrice * quantity);
				try {
					int maxQuantity = player.getLoyaltyPoints() / totPrice;
					if (quantity > maxQuantity) {
						player.sendMessage("You can't afford this many. You're max is "+maxQuantity+"");
						return;
					}
				} catch (ArithmeticException e) {
					player.sendMessage("Unable to buy this item.");
				}
				if (quantity <= 0) {
					player.sendMessage("You must buy atleast one of this item.");
					return;
				}
				if (totPrice < v.buyPrice) {
					player.sendMessage("Trying to dupe are we?");
					return;
				}
				if (item.getAmount() <= 0) {
					player.sendMessage("Shop is currently out of stock.");
					return;
				}
				
				if (player.getLoyaltyPoints() < totPrice) {
					player.getPackets().sendGameMessage("You need " + totPrice + " Loyalty Points to buy this item!");
					return;
				}
				
				player.getPackets().sendGameMessage("You have bought: " + item.getDefinitions().getName() + " (<col=00ffff>"+Utils.formatNumber(totPrice)+"</col> Loyalty Pts)");
				player.getInventory().addItem(item.getId(), quantity);
				player.setLoyaltyPoints(player.getLoyaltyPoints() - totPrice);
				return;
			}
		}
		return;
	}
	
	public static void sell(Player player, Item item, int quantity) {
		int amount = player.getInventory().getNumberOf(item.getId());
		if (amount < quantity) {
			quantity = amount;
		}
		if (disabled) {
			player.sendMessage("<col=FF0000>Shop is temporarily disabled.</col>");
			return;
		}
		for (LoyaltyStore v : LoyaltyStore.values()) {
			if (item.getId() == v.itemId) {
				player.getInventory().deleteItem(item.getId(), quantity);
				player.setLoyaltyPoints(player.getLoyaltyPoints() + (v.buyPrice * quantity));
				player.getPackets().sendGameMessage("You have sold "+quantity+" "+item.getName()+""+(quantity > 1 ? "s" : "")+" for " +( v.buyPrice*quantity )+ " loyalty points.");
			}
		}
		return;
	}
	
	public static void sendExamine(Player player, Item item) {
		if (disabled) {
			player.sendMessage("<col=FF0000>Shop is temporarily disabled.</col>");
			return;
		}
		for (LoyaltyStore v : LoyaltyStore.values()) {
			if (item.getId() == v.itemId) {
				player.getPackets().sendGameMessage("" + item.getDefinitions().getName() + " is worth " + v.buyPrice + " loyalty points.");
				player.getPackets().sendConfig(2564, v.buyPrice);
				return;
			}
		}
	}
	
}
