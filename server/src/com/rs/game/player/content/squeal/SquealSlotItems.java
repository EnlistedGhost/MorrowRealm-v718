package com.rs.game.player.content.squeal;

import com.rs.game.item.Item;

public class SquealSlotItems {

	public static Item[] SUPER_RARE_ITEM = { 
		new Item(995, 200000000),
		new Item(995, 10000000),
		new Item(1038),
		new Item(1040),
		new Item(1042),
		new Item(1046),
		new Item(1048),
		new Item(1050),
	};
	public static Item[] RARE_SLOT1 = { 
		new Item(23715),
		new Item(23679),
		new Item(23680),
		new Item(23681),
		new Item(23682),
		new Item(23683),
		new Item(23690)
	};
	public static Item[] RARE_SLOT2 = { 
		new Item(23684),
		new Item(23685),
		new Item(23686),
		new Item(23687),
		new Item(23688),
		new Item(23689)
	};
	public static Item[] RARE_SLOT3 = { 
		new Item(23691),
		new Item(23692),
		new Item(23693),
		new Item(23694),
		new Item(23695),
		new Item(23696),
		new Item(23697),
		new Item(23698),
		new Item(23699),
		new Item(23700)
	};
	public static Item[] UNCOMMON_SLOT1 = { 
		new Item(9181),
		new Item(9183),
		new Item(857)
	};
	public static Item[] UNCOMMON_SLOT2 = { 
		new Item(1119),
		new Item(1121),
		new Item(1123),
		new Item(1127)
	};
	public static Item[] UNCOMMON_SLOT3 = { 
		new Item(12976),
		new Item(1133),
		new Item(23714)
	};
	public static Item[] COMMON_SLOT1 = {
		new Item(23713),
		new Item(9177),
		new Item(9179),
		new Item(9174)
	};
	public static Item[] COMMON_SLOT2 = {
		new Item(2, 30),
		new Item(886, 300)
	};
	public static Item[] COMMON_SLOT3 = { 
		new Item(1515, 15),
		new Item(444, 20)
	};
	public static Item[] COMMON_SLOT4 = { 
		new Item(563, 40),
		new Item(843),
		new Item(1325),
		new Item(1313),
		new Item(1361)
	};
	public static Item[] COMMON_SLOT5 = { 
		new Item(1367),
		new Item(1297),
		new Item(1327),
		new Item(1205)
	};
	public static Item[] COMMON_SLOT6 = { 
		new Item(853),
		new Item(1311), 
		new Item(1365), 
		new Item(1353),
		new Item(1295), 
		new Item(1325) 
	};
	
	public static Item getSRare() {
		return SUPER_RARE_ITEM[(int) (Math.random() * SUPER_RARE_ITEM.length)];
	}

	public static Item getRare(int slot) {
		return slot == 2 ? RARE_SLOT2[(int) (Math.random() * RARE_SLOT2.length)] :
			   slot == 3 ? RARE_SLOT3[(int) (Math.random() * RARE_SLOT3.length)] :
					RARE_SLOT1[(int) (Math.random() * RARE_SLOT1.length)];
	}

	public static Item getCommon(int slot) {
		return slot == 2 ? COMMON_SLOT2[(int) (Math.random() * COMMON_SLOT2.length)] :
			   slot == 3 ? COMMON_SLOT3[(int) (Math.random() * COMMON_SLOT3.length)] :
			   slot == 4 ? COMMON_SLOT4[(int) (Math.random() * COMMON_SLOT4.length)] :
			   slot == 5 ? COMMON_SLOT5[(int) (Math.random() * COMMON_SLOT5.length)] :
			   slot == 6 ? COMMON_SLOT6[(int) (Math.random() * COMMON_SLOT6.length)] :
				   COMMON_SLOT1[(int) (Math.random() * COMMON_SLOT1.length)];
	}

	public static Item getUnCommon(int slot) {
		return slot == 2 ? UNCOMMON_SLOT2[(int) (Math.random() * UNCOMMON_SLOT2.length)] :
			   slot == 3 ? UNCOMMON_SLOT3[(int) (Math.random() * UNCOMMON_SLOT3.length)] :
				   UNCOMMON_SLOT1[(int) (Math.random() * UNCOMMON_SLOT1.length)];
	}
	
}
