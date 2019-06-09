package com.rs.game.player.content.shops;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.guardian.ItemManager;
import com.rs.utils.Utils;

public class SkillerShop {

	public static void buy(Player player, Item item, int quantity) {
		int value = ItemManager.getPrice(item.getId());
		int totPrice = (value * quantity);
		int maxQuantity = (int)player.getSkillPoints() / totPrice;
				
		if (quantity > maxQuantity) {
			player.sendMessage("You need atleast "+totPrice+" skill Points for this item.");
			return;
		}
		
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
		
		if (player.getSkillPoints() < totPrice) {
			player.getPackets().sendGameMessage("You need atleast " + totPrice + " Skill Points for this item.");
			return;
		}
		
		player.getPackets().sendGameMessage("You have bought: " + item.getDefinitions().getName() + " (<col=00FFFF>"+Utils.formatNumber(totPrice)+" Skill Points</col>)");
		player.getInventory().addItem(item.getId(), quantity);
		player.setSkillPoints(player.getSkillPoints() - totPrice);
		return;
	}
	
	public static void sell(Player player, Item item, int quantity) {
		int value = ItemManager.getPrice(item.getId());
		
		if (!player.getInventory().containsItem(item.getId(), quantity)) {
			return;
		}
		
		long sellPrice = (value * quantity);
		
		if (sellPrice < 1) {
			return;
		}
		
		player.setSkillPoints(player.getSkillPoints() + sellPrice);
		player.getInventory().deleteItem(item.getId(), quantity);
		player.sendMessage("You have sold "+item.getName()+" for "+sellPrice+" Skill Points.");
		return;
	}
	
	public static void sendExamine(Player player, Item item, boolean isBuying) {
		int value = ItemManager.getPrice(item.getId());
		player.getPackets().sendConfig(2564, value);
		player.sendMessage(""+item.getName()+" can be "+(isBuying ? "bought" : "sold")+" for "+value+" Skill Points.");
	}
	
}
