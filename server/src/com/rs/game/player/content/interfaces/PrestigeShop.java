package com.rs.game.player.content.interfaces;

import com.rs.game.player.Player;

public class PrestigeShop {

	public enum ShopItems {
		
		SLOT_1(1038, 1, 1000, 33, 31),
		SLOT_2(1038, 1, 1000, 177, 37),
		SLOT_3(1038, 1, 1000, 178, 42),
		SLOT_4(1038, 1, 1000, 179, 47),
		SLOT_5(1038, 1, 1000, 180, 52),
		SLOT_6(1038, 1, 1000, 181, 57),
		SLOT_7(1038, 1, 1000, 182, 92),
		SLOT_8(1038, 1, 1000, 65, 63),
		
		SLOT_9(1038, 1, 1000, 89, 69),
		SLOT_10(1038, 1, 1000, 88, 74),
		SLOT_11(1038, 1, 1000, 87, 79),
		SLOT_12(1038, 1, 1000, 86, 84),
		SLOT_13(1038, 1, 1000, 183, 97),
		SLOT_14(1038, 1, 1000, 184, 102),
		SLOT_15(1038, 1, 1000, 185, 113),
		SLOT_16(1038, 1, 1000, 110, 108),
		
		SLOT_17(1038, 1, 1000, 186, 118),
		SLOT_18(1038, 1, 1000, 187, 123),
		SLOT_19(1038, 1, 1000, 188, 128),
		SLOT_20(1038, 1, 1000, 189, 133),
		SLOT_21(1038, 1, 1000, 190, 138),
		SLOT_22(1038, 1, 1000, 191, 143),
		SLOT_23(1038, 1, 1000, 192, 154),
		SLOT_24(1038, 1, 1000, 151, 149),
		
		SLOT_25(1038, 1, 1000, 196, 174),
		SLOT_26(1038, 1, 1000, 195, 169),
		SLOT_27(1038, 1, 1000, 193, 159),
		SLOT_28(1038, 1, 1000, 194, 164);
		
		int itemId, amount, value, itemComp, priceComp;
		ShopItems(int itemId, int amount, int value, int itemComp, int priceComp) {
			this.itemId = itemId;
			this.amount = amount;
			this.value = value;
		}
		
		public int getItemId() {
			return itemId;
		}
		
		public int getAmount() {
			return amount;
		}
		
		public int getValue() {
			return value;
		}
		
		public int getItemComponent() {
			return itemComp;
		}
		
		public int getPriceComponent() {
			return priceComp;
		}
		
	}
	
	public static void initShop(Player player) {
		for (ShopItems item : ShopItems.values()) {
			player.getPackets().sendIComponentText(419, item.priceComp, ""+item.value+"");
			player.getPackets().sendItemOnIComponent(419, item.itemComp, item.itemId, item.amount);
		}
		player.getInterfaceManager().sendInterface(419);
	}
}
