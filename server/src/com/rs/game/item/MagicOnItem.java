package com.rs.game.item;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.crafting.Enchanting;
import com.rs.game.player.content.Shop;

public class MagicOnItem {

	public static final int LOW_ALCHEMY = 38;
	public static final int HIGH_ALCHEMY = 59;
	public static final int SUPER_HEAT = 50;
	
	public static final int LV1_ENCHANT = 29;
	public static final int LV2_ENCHANT = 41;
	public static final int LV3_ENCHANT = 53;
	public static final int LV4_ENCHANT = 61;
	public static final int LV5_ENCHANT = 76;
	public static final int LV6_ENCHANT = 88;
	
	public static boolean enabled = true;
	
	public static void handleMagic(Player player, int magicId, Item item) {
		if (enabled == false) {
			player.sendMessage("Magic on Items has been temporarily disabled.");
			return;
		}
		int itemId = item.getId();
		
		switch (magicId) {
		
			case LOW_ALCHEMY:
				processAlchemy(player, itemId, true);
				break;
				
			case HIGH_ALCHEMY:
				processAlchemy(player, itemId, false);
				break;
				
			case SUPER_HEAT:
				player.sendMessage("Super Heat is currently unavailable.");
				break;
				
			case LV1_ENCHANT:
				Enchanting.startEnchant(player, itemId, 1);
				break;
				
			case LV2_ENCHANT:
				Enchanting.startEnchant(player, itemId, 2);
				break;
				
			case LV3_ENCHANT:
				Enchanting.startEnchant(player, itemId, 3);
				break;
				
			case LV4_ENCHANT:
				Enchanting.startEnchant(player, itemId, 4);
				break;
				
			case LV5_ENCHANT:
				Enchanting.startEnchant(player, itemId, 5);
				break;
				
			case LV6_ENCHANT:
				Enchanting.startEnchant(player, itemId, 6);
				break;
				
			default:
				player.sendMessage("MagicId: "+magicId+" on item: "+item.getDefinitions().getName()+" ("+itemId+")");
				break;
		}
	}
	
	public static void processAlchemy(Player player, int itemId, boolean lowAlch) {
		
		if (player.isLocked())
			return;
		
		if (player.getSkills().getLevel(Skills.MAGIC) < (lowAlch == true ? 21 : 55)) {
			player.getPackets().sendGameMessage("You do not have the required level to cast this spell.");
			return;
		}
		
		if (!player.getInventory().containsItem(itemId, 1)) {
			return;
		}
		
		if (itemId == 995) {
			player.getPackets().sendGameMessage("You can't "+(lowAlch == true ? "low" : "high")+" alch this!");
			return;
		}
		
		if (hasFireStaff(player)) {
			if (!player.getInventory().containsItem(561, 1) || !player.getInventory().containsItem(554, (lowAlch == true ? 3 : 5))) {
				player.getPackets().sendGameMessage("You do not have the required runes to cast this spell.");
				return;
			}
		} else {
			if (!player.getInventory().containsItem(554, (lowAlch == true ? 3 : 5))) {
				player.getPackets().sendGameMessage("You do not have the required runes to cast this spell.");
				return;
			}
		}
		
		player.setNextAnimation(getAnim(hasFireStaff(player), lowAlch));
		player.setNextGraphics(getGfx(hasFireStaff(player), lowAlch));
		
		player.getInventory().deleteItem(561, 1);
		
		if (!hasFireStaff(player)) {
			player.getInventory().deleteItem(554, (lowAlch == true ? 3 : 5));
		}
		
		player.getInventory().deleteItem(itemId, 1);
		
		int baseValue = Shop.getSellPrice(new Item(itemId));

		double low = player.getEquipment().getAmuletId() == 25056 ? 0.75 : 0.65;
		double high = player.getEquipment().getAmuletId() == 25056 ? 0.95 : 0.85;
		
			baseValue *= lowAlch == true ? low : high;
			
		player.lock(1);
		player.getInventory().addItem(995, baseValue);
		player.getSkills().addXp(Skills.MAGIC, (lowAlch == true ? 10 : 15));
	}
	
	public static Animation getAnim(boolean hasStaff, boolean lowAlch) {
		if (hasStaff && lowAlch == true) {
			return new Animation(9625);
		}
		if (hasStaff && lowAlch == false) {
			return new Animation(9633);
		}
		if (!hasStaff && lowAlch == true) {
			return new Animation(712);
		}
		if (!hasStaff && lowAlch == false) {
			return new Animation(713);
		}
		return null;
	}
	
	public static Graphics getGfx(boolean hasStaff, boolean lowAlch) {
		if (hasStaff && lowAlch == true) {
			return new Graphics(1692);
		}
		if (hasStaff && lowAlch == false) {
			return new Graphics(1693);
		}
		if (!hasStaff && lowAlch == true) {
			return new Graphics(112);
		}
		if (!hasStaff && lowAlch == false) {
			return new Graphics(113);
		}
		return null;
	}
	
	public static boolean hasFireStaff(Player player) {
		return player.getEquipment().getWeaponId() == 1387;
	}
	
}
