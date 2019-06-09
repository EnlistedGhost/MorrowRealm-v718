package com.rs.game.player.content.shops;

import java.util.ArrayList;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public enum DungShop  {
	
	ROCKTAILS(15272, 0, true),
	ANTIFIRE(15304, 0, true),
	ANTIPOISON(175, 0, true),
	
	TWISTED_BIRD(19886, 35, true),
	SPLIT_DRAGONTOOTH(19887, 70, true),
	DEMON_HORN_NECKLACE(19888, 150, true),

	RUNIC_HOOD(16751, 50, true),
	RUNIC_BOTTOM(16861, 50, true),
	RUNIC_SHOES(16927, 50, true),
	RUNIC_GLOVES(17167, 50, true),
	RUNIC_TOP(17233, 50, true),

	SPIRITBLOOM_HOOD(16753, 120, true),
	SPIRITBLOOM_BOTTOM(16863, 120, true),
	SPIRITBLOOM_SHOES(16929, 120, true),
	SPIRITBLOOM_GLOVES(17169, 120, true),
	SPIRITBLOOM_TOP(17235, 120, true),

	CELESTIAL_HOOD(16755, 220, true),
	CELESTIAL_BOTTOM(16865, 220, true),
	CELESTIAL_SHOES(16931, 220, true),
	CELESTIAL_GLOVES(17171, 220, true),
	CELESTIAL_TOP(17237, 220, true),

	POLYPORE_STAFF(22494, 75, true),

	/** Melee Dung Sets **/
	GORGON_2H(16905, 50, true),
	GORGON_1H(16963, 30, true),
	GORGON_RAPIER(16951, 25, true),
	
	GORGON_GLOVES(16289, 45, true),
	GORGON_BOOTS(16355, 45, true),
	GORGON_CHAINBODY(16729, 45, true),
	GORGON_PLATEBODY(17255, 75, true),
	GORGON_PLATELEGS(16685, 45, true),
	GORGON_FULLHELM(16707, 45, true),
	GORGON_KITE(16971, 75, true),

	PROMETHIUM_2H(16907, 100, true),
	PROMETHIUM_1H(16401, 80, true),
	PROMETHIUM_RAPIER(16953, 75, true),
	
	PROMETHIUM_GLOVES(16291, 90, true),
	PROMETHIUM_BOOTS(16357, 90, true),
	PROMETHIUM_CHAINBODY(16731, 90, true),
	PROMETHIUM_PLATEBODY(17257, 150, true),
	PROMETHIUM_PLATELEGS(16687, 90, true),
	PROMETHIUM_FULLHELM(16709, 90, true),
	PROMETHIUM_KITE(17359, 150, true),

	PRIMAL_2H(16909, 300, true),
	PRIMAL_1H(16403, 230, true),
	PRIMAL_RAPIER(16955, 180, true),
	
	PRIMAL_GLOVES(16293, 220, true),
	PRIMAL_BOOTS(16359, 220, true),
	PRIMAL_CHAINBODY(16733, 220, true),
	PRIMAL_PLATEBODY(17259, 300, true),
	PRIMAL_PLATELEGS(16689, 220, true),
	PRIMAL_FULLHELM(16711, 220, true),
	PRIMAL_KITE(17361, 300, true),
	
	;
	private int itemId;
	private int buyPrice;
	private boolean resell;
	
	DungShop(int itemId, int price, boolean resell) {
		this.itemId = itemId;
		this.buyPrice = price;
		this.resell = resell;
	}
	
	public int getItemId() {
		return itemId;
	}
	
	public static void buy(Player player, Item item, int quantity) {
		for (DungShop v : DungShop.values()) {
			if (item.getId() == v.itemId) {
				if (player.getExpertDungKills() < v.buyPrice) {
					player.getPackets().sendGameMessage("You need atleast " + v.buyPrice + " Dung Kills for this item.");
					return;
				}
				player.getInventory().addItem(item.getId(), 1);
				return;
			}
		}
		return;
	}
	
	public static void sell(Player player, Item item, int quantity) {
		for (DungShop v : DungShop.values()) {
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
		for (DungShop v : DungShop.values()) {
			if (item.getId() == v.itemId) {
				player.getPackets().sendGameMessage("You need " + v.buyPrice + " kills for " + item.getDefinitions().getName() + ".");
				player.getPackets().sendConfig(2564, v.buyPrice);
				return;
			}
		}
	}
	
	
}
