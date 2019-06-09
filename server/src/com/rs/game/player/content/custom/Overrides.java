package com.rs.game.player.content.custom;

import com.rs.game.item.Item;
import com.rs.game.player.Equipment;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class Overrides {

	public enum Armour {
		
		// id, chest, legs, feet, hands, hat, cape
		ASSASSIN(false, new Item(24641), new Item(24643), new Item(24645), new Item(24647), new Item(24639), new Item(24649)),
		BRONZE_ATHLETE(false, new Item(24793), new Item(24794), new Item(24791), new Item(24792), new Item(24795), null),
		SILVER_ATHLETE(false, new Item(24798), new Item(24799), new Item(24796), new Item(24797), new Item(24800), null),
		GOLDEN_ATHLETE(false, new Item(24803), new Item(24804), new Item(24801), new Item(24802), new Item(24805), null),
		
		FLAME_2H(true, new Item(24886)),
		FLAME_WHIP(true, new Item(24894)),
		FLAME_AXE(true, new Item(24900)),
		
		DRYGORE_LONGSWORD(true, new Item(28813)),
		DRYGORE_RAPIER(true, new Item(28818)),
		DRYGORE_MACE(true, new Item(29987)),
		
		KALPHITE_ARMOUR(false, new Item(28791), new Item(28792), new Item(28794), new Item(28793), new Item(25816), new Item(28790));
		
		boolean isWeapon;
		Item[] item;
		
		Armour(boolean weapon, Item...itemIds) {
			this.isWeapon = weapon;
			this.item = itemIds;
		}
		public int getSize() {
			return item.length;
		}
		public boolean isWeapon() {
			return isWeapon;
		}
		public Item getModel(int slot) {
			return item[slot];
		}
		public int getMaleModel(int slot) {
			return getModel(slot).getDefs().maleEquip1;
		}
		public int getFemaleModel(int slot) {
			return getModel(slot).getDefs().femaleEquip1;
		}
		public String getName() {
			return Utils.formatString(name());
		}
		
		public static Armour getArmour(int id) {
			for (Armour armour : Armour.values()) {
				if (id == armour.ordinal()) {
					return armour;
				}
			}
			return null;
		}
	}

	public static int getArmour(Player player, int slot, boolean male) {
		int oMale = player.getEquipment().getItem(slot).getDefinitions().maleEquip1;
		int oFemale = player.getEquipment().getItem(slot).getDefinitions().femaleEquip1;
		
		Armour armour = player.getArmour(slot);
		
		if (slot == Equipment.SLOT_WEAPON && player.getEquipment().getWeaponId() != -1) {
			return getWeaponModel(player, armour, male, slot, oMale, oFemale);
		}
		
		if (slot == Equipment.SLOT_CHEST && armour.getModel(0) != null) {
			return male ? armour.getModel(0).getDefs().maleEquip1 : armour.getModel(0).getDefs().femaleEquip1;
		}
		if (slot == Equipment.SLOT_LEGS && armour.getModel(1) != null) {
			return male ? armour.getModel(1).getDefs().maleEquip1 : armour.getModel(1).getDefs().femaleEquip1;
		}
		if (slot == Equipment.SLOT_FEET && armour.getModel(2) != null) {
			return male ? armour.getModel(2).getDefs().maleEquip1 : armour.getModel(2).getDefs().femaleEquip1;
		}
		if (slot == Equipment.SLOT_HANDS && armour.getModel(3) != null) {
			return male ? armour.getModel(3).getDefs().maleEquip1 : armour.getModel(3).getDefs().femaleEquip1;
		}
		if (slot == Equipment.SLOT_HAT && armour.getModel(4) != null) {
			return male ? armour.getModel(4).getDefs().maleEquip1 : armour.getModel(4).getDefs().femaleEquip1;
		}
		if (slot == Equipment.SLOT_CAPE && armour.getModel(5) != null) {
			return male ? armour.getModel(5).getDefs().maleEquip1 : armour.getModel(5).getDefs().femaleEquip1;
		}
		
		return male ? oMale : oFemale;
	}
	
	public static int getWeaponModel(Player player, Armour armour, boolean male, int slot, int oMale, int oFemale) {
		if (slot == Equipment.SLOT_WEAPON && player.getEquipment().getWeaponId() != -1) {
			String weaponName = player.getEquipment().getItem(Equipment.SLOT_WEAPON).getName().toLowerCase();
			
			if (weaponName.contains("godsword") || weaponName.contains("2h")) {
				if (armour.ordinal() == 4) {
					return male ? armour.getMaleModel(0) : armour.getFemaleModel(0);
				}
			}
			if (weaponName.contains("abyssal whip")) {
				if (armour.ordinal() == 5) {
					return male ? armour.getMaleModel(0) : armour.getFemaleModel(0);
				}
			}
			if (weaponName.contains("hatchet")) {
				if (armour.ordinal() == 6) {
					return male ? armour.getMaleModel(0) : armour.getFemaleModel(0);
				}
			}
			if (weaponName.contains("longsword") || weaponName.contains("korasi")) {
				if (armour.ordinal() == 7) {
					return male ? armour.getMaleModel(0) : armour.getFemaleModel(0);
				}
			}
			if (weaponName.contains("rapier")) {
				if (armour.ordinal() == 8) {
					return male ? armour.getMaleModel(0) : armour.getFemaleModel(0);
				}
			}
			if (weaponName.contains("mace")) {
				if (armour.ordinal() == 9) {
					return male ? armour.getMaleModel(0) : armour.getFemaleModel(0);
				}
			}
		}
		return male ? oMale : oFemale;
	}
	
}
