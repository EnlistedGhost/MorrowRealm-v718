package com.rs.game.player.actions.crafting;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.Magic;
import com.rs.utils.Utils;

public class Enchanting {
	
	private static final int AIR_RUNE = 556, WATER_RUNE = 555, EARTH_RUNE = 557, FIRE_RUNE = 554, COSMIC_RUNE = 564;
	
	public enum Types {
		SAPPHIRE(1, 7, 2550, 3853, 1727, 11074),
		EMERALD(2, 27, 2552, 5521, 1729, 11079),
		RUBY(3, 49, 2568, 11194, 1725, 11088),
		DIAMOND(4, 57, 2570, 11090, 1731, 11095),
		DRAGONSTONE(5, 86, 2572, 11105, 1712, 11188),
		ONYX(6, 87, 6583, 11128, 6585, 11133);
		
		int enchantLevel, level, ring, necklace, amulet, bracelet;
		
		Types(int enchantLevel, int level, int ring, int necklace, int amulet, int bracelet) {
			this.enchantLevel = enchantLevel;
			this.level = level;
			this.ring = ring;
			this.necklace = necklace;
			this.amulet = amulet;
			this.bracelet = bracelet;
		}
	}

	public static boolean startEnchant(Player player, int itemId, int enchantLevel) {
		Base base = getBase(enchantLevel);
		if (!canBeEnchanted(base, itemId)) {
			player.sendMessage("This item can't be enchanted.");
			return false;
		}
		for (Types t : Types.values()) {
			if (enchantLevel == t.enchantLevel) {
				if (player.getSkills().getLevel(Skills.MAGIC) < t.level) {
					player.sendMessage("You need atleast "+t.level+" to enchant "+t.name().toLowerCase()+"");
					return false;
				}
				if (itemId == base.baseRing) {
					return enchant(player, t.level, base.baseRing, t.ring,  enchantLevel);
				}
				if (itemId == base.baseNecklace) {
					return enchant(player, t.level, base.baseNecklace, t.necklace, enchantLevel);
				}
				if (itemId == base.baseAmulet) {
					return enchant(player, t.level, base.baseAmulet, t.amulet, enchantLevel);
				}
				if (itemId == base.baseBracelet) {
					return enchant(player, t.level, base.baseBracelet, t.bracelet, enchantLevel);
				}
			}
		}
		return false;
	}
	
	public static boolean enchant(Player player, int reqLevel, int toEnchant, int toMake, int enchantLevel) {
		String name = ItemDefinitions.getItemDefinitions(toEnchant).getName();
		String makeName = ItemDefinitions.getItemDefinitions(toMake).getName();
		
		if (toEnchant == 1645 || toEnchant == 6581) {
			player.sendMessage("You are unable to enchant "+name+". ~Fox");
			return false;
		}
		
		if (!checkRunes(player, reqLevel, enchantLevel)) {
			return false;
		}
		
		if (enchantLevel <= 2) {
			player.setNextAnimation(new Animation(719));
			player.setNextGraphics(new Graphics(114, 0, 100));
		} else if (enchantLevel > 2 && enchantLevel <= 4) {
			player.setNextAnimation(new Animation(720));
			player.setNextGraphics(new Graphics(115, 0, 100));
		} else if (enchantLevel == 5) {
			player.setNextAnimation(new Animation(721));
			player.setNextGraphics(new Graphics(116, 0, 100));
		} else if (enchantLevel == 6) {
			player.setNextAnimation(new Animation(721));
			player.setNextGraphics(new Graphics(452, 0, 100));
		}
		
		player.getInventory().deleteItem(toEnchant, 1);
		player.getInventory().addItem(toMake, 1);
		player.getSkills().addXp(Skills.MAGIC, (enchantLevel * 10));
		player.sendMessage("You enchant the "+name+" and make a "+makeName+"!");
		return false;
	}
	
	public static boolean checkRunes(Player player, int level, int enchantLevel) {
		if (enchantLevel == 1) {
			return Magic.checkSpellRequirements(player, level, true, COSMIC_RUNE, 1, WATER_RUNE, 1);
		} else if (enchantLevel == 2) {
			return Magic.checkSpellRequirements(player, level, true, COSMIC_RUNE, 1, AIR_RUNE, 3);
		} else if (enchantLevel == 3) {
			return Magic.checkSpellRequirements(player, level, true, COSMIC_RUNE, 1, FIRE_RUNE, 3);
		} else if (enchantLevel == 4) {
			return Magic.checkSpellRequirements(player, level, true, COSMIC_RUNE, 1, EARTH_RUNE, 10);
		} else if (enchantLevel == 5) {
			return Magic.checkSpellRequirements(player, level, true, COSMIC_RUNE, 1, EARTH_RUNE, 15, WATER_RUNE, 15);
		} else if (enchantLevel == 6) {
			return Magic.checkSpellRequirements(player, level, true, COSMIC_RUNE, 1, FIRE_RUNE, 20, EARTH_RUNE, 20);
		}
		return false;
	}
	
	public enum Base {
		
		SAPPHIRE(1, 1637, 1656, 1694, 11072),
		EMERALD(2, 1639, 1658, 1696, 11076),
		RUBY(3, 1641, 1660, 1698, 11085),
		DIAMOND(4, 1643, 1662, 1700, 11092),
		DRAGONSTONE(5, 1645, -1, 1702, -1),
		ONYX(6, 6575, 6577, 6581, 11130);
		
		int level, baseRing, baseNecklace, baseAmulet, baseBracelet;
		Base(int level, int baseRing, int baseNecklace, int baseAmulet, int baseBracelet) {
			this.level = level;
			this.baseRing = baseRing;
			this.baseNecklace = baseNecklace;
			this.baseAmulet = baseAmulet;
			this.baseBracelet = baseBracelet;
		}
		
	}
	
	public static boolean canBeEnchanted(Base b, int itemId) {
		return b.baseAmulet == itemId || b.baseNecklace == itemId || b.baseRing == itemId || b.baseBracelet == itemId;
	}
	
	public static Base getBase(int type) {
		for (Base t : Base.values()) {
			if (type == t.level) {
				return t;
			}
		}
		return null;
	}
	
}
