package com.rs.game.player.content.shops;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public enum PrestigeShop {

	VANGUARD_HELM(21472, 2, false),
	VANGUARD_BODY(21473, 2, false),
	VANGUARD_LEGS(21474, 2, false),
	VANGUARD_GLOVES(21475, 2, false),
	VANGUARD_BOOTS(21476, 2, false),
	
	TICKSTER_HELM(21467, 2, false),
	TICKSTER_BODY(21468, 2, false),
	TICKSTER_LEGS(21469, 2, false),
	TICKSTER_GLOVES(21470, 2, false),
	TICKSTER_BOOTS(21471, 2, false),
	
	BATTLEMAGE_HELM(21462, 2, false),
	BATTLEMAGE_BODY(21463, 2, false),
	BATTLEMAGE_LEGS(21464, 2, false),
	BATTLEMAGE_GLOVES(21465, 2, false),
	BATTLEMAGE_BOOTS(21466, 2, false),
	
	CLASS_5_SCIMITAR(14295, 5, false),
	CLASS_5_DAGGER(14305, 5, false),
	CLASS_5_WARHAMMER(14315, 5, false),
	CLASS_5_STAFF(14385, 5, false),
	
	ARCANE_STREAM(18335, 10, false);
	
	PrestigeShop(int itemId, int value, boolean resell) {
		this.itemId = itemId;
		this.value = value;
		this.resell = resell;
	}

	int itemId;
	int value;
	boolean resell;
	
	public static void buy(Player player, Item item, int quantity) {
		for (PrestigeShop v : PrestigeShop.values()) {
			if (item.getId() == v.itemId) {
				int totPrice = (v.value * quantity);
				int maxQuantity = player.getPrestigePoints() / totPrice;
				
				if (quantity > maxQuantity) {
					player.sendMessage("You can't afford this many.");
					return;
				}
				if (quantity <= 0) {
					player.sendMessage("You must buy atleast one of this item.");
					return;
				}
				if (totPrice < v.value) {
					player.sendMessage("Trying to dupe are we?");
					return;
				}
				if (item.getAmount() <= 0) {
					player.sendMessage("Shop is currently out of stock.");
					return;
				}
				if (player.getPrestigePoints() < totPrice) {
					player.getPackets().sendGameMessage("You need atleast " + v.value + " Prestige Points for this item.");
					return;
				}
				
				player.getPackets().sendGameMessage("You have bought: " + item.getDefinitions().getName() + " (<col=00FFFF>"+Utils.formatNumber(totPrice)+" Prestige Pts</col>)");
				player.getInventory().addItem(item.getId(), quantity);
				player.setPrestigePoints(player.getPrestigePoints() - totPrice);
				return;
			}
		}
		return;
	}
	
	public static void sell(Player player, Item item, int quantity) {
		for (PrestigeShop v : PrestigeShop.values()) {
			if (item.getId() == v.itemId) {
				if (!v.resell) {
					player.getPackets().sendGameMessage("You can not resell "+item.getName()+"");
					return;
				}
				player.getInventory().deleteItem(item.getId(), quantity);
				player.getPackets().sendGameMessage("You have put back "+item.getName()+".");
			}
		}
		return;
	}
	
	public static void sendExamine(Player player, Item item) {
		for (PrestigeShop v : PrestigeShop.values()) {
			if (item.getId() == v.itemId) {
				player.getPackets().sendGameMessage("" + item.getDefinitions().getName() + " is worth " + v.value + " prestige points.");
				player.getPackets().sendConfig(2564, v.value);
				return;
			}
		}
	}
	
}
