package com.rs.game.player.content.shops;

import com.rs.game.item.Item;
import com.rs.game.player.Player;

public enum PointShop {

	GOLIATH_BLACK(22358, 100, false),
	GOLIATH_WHITE(22359, 100, false),
	GOLIATH_YELLOW(22360, 100, false),
	GOLIATH_RED(22361, 100, false),
	
	SWIFT_BLACK(22362, 1700, false),
	SWIFT_WHITE(22363, 1700, false),
	SWIFT_YELLOW(22364, 1700, false),
	SWIFT_RED(22365, 1700, false),
	
	WHIP_GREEN(15444, 500, false),
	WHIP_WHITE(15443, 500, false),
	WHIP_BLUE(15442, 500, false),
	WHIP_YELLOW(15441, 500, false),
	
	CHAOTIC_RAPIER(18349, 1500, false),
	CHAOTIC_MAUL(18353, 1500, false),
	CHAOTIC_LONGSWORD(18351, 1500, false),
	CHAOTIC_CROSSBOW(18357, 1500, false),
	CHAOTIC_KITE(18355, 1500, false),
	CHAOTIC_STAFF(18359, 1500, false),
	KORASI_SWORD(19784, 2000, false),
	DRAGON_CLAWS(14484, 2500, false),
	TOKHAAR_KAL(23659, 250, false);
	
	private int itemId;
	private int buyPrice;
	private boolean resell;
	
	PointShop(int itemId, int price, boolean resell) {
		this.itemId = itemId;
		this.buyPrice = price;
		this.resell = resell;
	}
	
	public static void buy(Player player, Item item, int quantity) {
		for (PointShop v : PointShop.values()) {
			if (item.getId() == v.itemId) {
				int totPrice = (v.buyPrice * quantity);
				int maxQuantity = player.getPvpPoints() / totPrice;
				
				if (quantity > maxQuantity) {
					player.sendMessage("You can't afford this many.");
					return;
				}
				
				if (quantity < 1) {
					player.sendMessage("You must buy atleast one of this item.");
					return;
				}
				
				if (totPrice < v.buyPrice) {
					player.sendMessage("Trying to dupe are we?");
					return;
				}
				
				if (item.getAmount() < 1) {
					player.sendMessage("Shop is currently out of stock.");
					return;
				}
				
				if (player.getPvpPoints() < totPrice) {
					player.getPackets().sendGameMessage("You need " + v.buyPrice + " Pvp Points to buy this item!");
					return;
				}
				
				player.getPackets().sendGameMessage("You have bought a " + item.getDefinitions().getName() + " for "+v.buyPrice+" pvp points.");
				player.getInventory().addItem(item.getId(), quantity);
				player.setPvpPoints(player.getPvpPoints() - totPrice);
				return;
			}
		}
		return;
	}
	
	public static void sell(Player player, Item item, int quantity) {
		for (PointShop v : PointShop.values()) {
			if (item.getId() == v.itemId) {
				int totPrice = (v.buyPrice * quantity);
				if (!v.resell) {
					player.getPackets().sendGameMessage("You can not resell "+item.getName()+".");
					return;
				}
				player.getInventory().deleteItem(item.getId(), quantity);
				player.setPvpPoints(player.getPvpPoints() + totPrice);
				player.getPackets().sendGameMessage("You have sold "+item.getName()+" for " +v.buyPrice+ " pvp points.");
				return;
			}
		}
		return;
	}
	
	public static void sendExamine(Player player, Item item) {
		for (PointShop v : PointShop.values()) {
			if (item.getId() == v.itemId) {
				player.getPackets().sendGameMessage("" + item.getDefinitions().getName() + " is worth " + v.buyPrice + " pvp points.");
				player.getPackets().sendConfig(2564, v.buyPrice);
				return;
			}
		}
	}
	
}
