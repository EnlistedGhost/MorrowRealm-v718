package com.rs.game.player.content.shops;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.guardian.ItemManager;
import com.rs.utils.Utils;

public enum DungPointShop {

	CHAOTIC_RAPIER(18349, 200000, true),
	CHAOTIC_LONGSWORD(18351, 200000, true),
	CHAOTIC_MAUL(18353, 200000, true),
	CHAOTIC_STAFF(18355, 200000, true),
	CHAOTIC_CROSSBOW(18357, 200000, true),
	CHAOTIC_KITESHIELD(18359, 200000, true),
	ARCANE_STREAM(18335, 100000, true);
	
	private int itemId;
	private int buyPrice;
	private boolean resell;
	
	DungPointShop(int itemId, int price, boolean resell) {
		this.itemId = itemId;
		this.buyPrice = price;
		this.resell = resell;
	}
	
	/**
	 * cant let u watch the console, shows "sensitive" information
	 * @param player
	 * @param item
	 * @param quantity
	 */
	public static void buy(Player player, Item item, int quantity) {
		for (DungPointShop v : DungPointShop.values()) {
			if (item.getId() == v.itemId) {
				int value = ItemManager.getPrice(v.itemId);
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
				
				if (player.getDungPoints() < totPrice) {
					player.getPackets().sendGameMessage("You need " + Utils.formatNumber(totPrice) + " DP to buy this item! (You have "+Utils.formatNumber(player.getDungPoints())+")");
					return;
				}
				
				player.getPackets().sendGameMessage("You have bought "+quantity+" " + item.getDefinitions().getName() + " for "+Utils.formatNumber(totPrice)+" dp.");
				player.getInventory().addItem(item.getId(), quantity);
				player.setDungPoints(player.getDungPoints() - totPrice);
				return;
			}
		}
		return;
	}

	public static void sell(Player player, Item item, int quantity) {
		int value = ItemManager.getPrice(item.getId());
		if (!player.getInventory().containsItem(item.getId(), quantity))
			return;
		for (DungPointShop v : DungPointShop.values()) {
			if (item.getId() == v.itemId) {
				if (!v.resell) {
					player.getPackets().sendGameMessage("You can not resell "+item.getName()+"");
					return;
				}
				
				int itemAmount = player.getInventory().getNumberOf(item.getId());
				
				if (quantity > itemAmount)
					quantity = itemAmount;
				
				player.getInventory().deleteItem(item.getId(), quantity);
				player.setDungPoints(player.getDungPoints() + (value * quantity));
				player.getPackets().sendGameMessage("You have sold "+quantity+" "+item.getName()+""+(quantity > 1 ? "s" : "")+" for " +( value*quantity )+ " dung points.");
				return;
			}
		}
		return;
	}
	
	public static void sendExamine(Player player, Item item) {
		int value = ItemManager.getPrice(item.getId());
		for (DungPointShop v : DungPointShop.values()) {
			if (item.getId() == v.itemId) {
				player.getPackets().sendGameMessage("" + item.getDefinitions().getName() + " is worth " + Utils.formatNumber(value) + " dung points.");
				player.getPackets().sendConfig(2564, v.buyPrice);
				return;
			}
		}
	}
	
}
