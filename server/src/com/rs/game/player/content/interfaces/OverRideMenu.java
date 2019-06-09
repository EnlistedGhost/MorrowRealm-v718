package com.rs.game.player.content.interfaces;

import com.rs.game.player.Equipment;
import com.rs.game.player.Player;
import com.rs.game.player.content.custom.TitleHandler;
import com.rs.game.player.content.custom.Overrides.Armour;
import com.rs.game.player.content.custom.TitleHandler.Title;
import com.rs.utils.Utils;

public class OverRideMenu {

	private static final int interId = 1156;
	
	public static void openInterface(Player player) {
		player.isInTitleList = false;
		
		if (!player.getInterfaceManager().containsInterface(interId))
			player.getInterfaceManager().sendInterface(interId);
		
		sendString(player, new int[]{ 108, 109, 90  }, 0, "Makes all armour look like the Assassin Outfit", "3,000 osp");
		sendString(player, new int[]{ 113, 114, 206 }, 1, "Makes all armour look like the Bronze Athlete Outfit", "300 osp");
		sendString(player, new int[]{ 137, 138, 254 }, 2, "Makes all armour look like the Silver Athlete Outfit", "500 osp");
		sendString(player, new int[]{ 110, 111, 200 }, 3, "Makes all armour look like the Golden Athlete Outfit", "1,000 osp");
		sendString(player, new int[]{ 116, 117, 212 }, 4, "Makes any 2h look like a Flame 2h", "300 osp");
		sendString(player, new int[]{ 134, 135, 248 }, 5, "Makes any whip look like a Flame Whip", "300 osp");
		sendString(player, new int[]{ 122, 123, 230 }, 6, "Makes any hatchet look like a Flame Axe", "300 osp");
		sendString(player, new int[]{ 128, 129, 236 }, 10,null, "");
		sendString(player, new int[]{ 125, 126, 224 }, 7, null, "");
		sendString(player, new int[]{ 143, 144, 266 }, 9, null, "");
		sendString(player, new int[]{ 146, 147, 272 }, 8, null, "");
		sendString(player, new int[]{ 119, 120, 218 }, 0, null, "");
		sendString(player, new int[]{ 131, 132, 242 }, 0, null, "");
		sendString(player, new int[]{ 140, 141, 260 }, 0, null, "");
		sendString(player, new int[]{ 149, 150, 278 }, 0, null, "");
		sendString(player, new int[]{ 152, 153, 284 }, 0, null, "");
		sendString(player, new int[]{ 152, 153, 284 }, 0, null, "");
		sendString(player, new int[]{ 167, 168, 308 }, 0, null, "");
		sendString(player, new int[]{ 155, 157, 290 }, 0, null, "");
		sendString(player, new int[]{ 159, 161, 296 }, 0, null, "");
		sendString(player, new int[]{ 163, 165, 302 }, 0, null, "");
		sendString(player, new int[]{ 170, 171, 314 }, 0, null, "");
		sendString(player, new int[]{ 318, 319, 326 }, 0, null, "");
		
		sendString(player, new int[]{ 156, 164, 160 }, 0, null, ""); // leave null
	}
	
	public static void sendString(Player player, int[] cIds, int id, String desc, String price) {
		Armour armour = Armour.getArmour(id);
		player.getPackets().sendIComponentText(interId, cIds[0], desc == null ? "" : armour.getName());
		player.getPackets().sendIComponentText(interId, cIds[1], desc == null ? "" : desc);
		player.getPackets().sendIComponentText(interId, cIds[2], desc == null ? "" : getArmourStatus(player, id, price));
	}
	
	public static String getArmourStatus(Player player, int id, String price) {
		return player.isArmourUnlocked(id) == true ? ("<col=00FF00>Activate</col>") : " <col=FF0000>Cost: "+price+"</col>" ;
	}
	
	
	public static void handleButtons(Player player, int buttonId) {
		if (buttonId == 88) {
			unlockArmour(player, 0, 3000);
		} else if (buttonId == 115) {
			unlockArmour(player, 1, 300);
		} else if (buttonId == 139) {
			unlockArmour(player, 2, 500);
		} else if (buttonId == 112) {
			unlockArmour(player, 3, 1000);
		} else if (buttonId == 118) {
			unlockArmour(player, 4, 300);
		} else if (buttonId == 136) {
			unlockArmour(player, 5, 300);
		} else if (buttonId == 124) {
			unlockArmour(player, 6, 300);
		} else if (buttonId == 130) {
			// removed
		} else if (buttonId == 127) { 
			// removed
		} else if (buttonId == 145) {
			// removed
		} else if (buttonId == 148) {
			// removed
		} else if (buttonId == 121) {
			 
		} else if (buttonId == 133) {
			 
		} else if (buttonId == 142) {
			 
		} else if (buttonId == 151) {
			 
		} else if (buttonId == 154) {
			 
		} else if (buttonId == 169) {
			 
		} else if (buttonId == 158) {
			 
		} else if (buttonId == 162) {
			 
		} else if (buttonId == 166) {
			 
		} else if (buttonId == 172) {
			
		} else if (buttonId == 320) {
			 
		}
		openInterface(player);
	}
	
	private static boolean isValidSlot(int slotId) {
		return slotId == 0 || slotId == 1 || slotId == 4 || slotId == 7 || slotId == 9 || slotId == 10;
	}
	
	public static void unlockArmour(Player player, int id, int osp) {
		if (!player.isArmourUnlocked(id)) {
			if (player.getOsp() < osp) {
				player.sendMessage("You need atleast "+osp+" to unlock this armour override.");
				return;
			}
			player.setOsp(player.getOsp() - osp);
			player.unlockArmour(id);
			player.sendMessage("You've purchased "+Utils.formatString(Armour.getArmour(id).name())+" for "+osp+" points.");
		}
		if (Armour.getArmour(id).isWeapon()) {
			player.setArmour(Armour.getArmour(id), Equipment.SLOT_WEAPON);
		} else {
			for (int i = 0; i < player.getArmour().length; i++) {
				if (isValidSlot(i)) {
					player.setArmour(Armour.getArmour(id), i);
				}
			}
		}
		player.getAppearence().generateAppearenceData();
		player.sendMessage(Utils.formatString(Armour.getArmour(id).name())+" is now active.");
	}
	
	public static void unlockArmourWithCoin(Player player, int id, int price) {
		int coins = player.getInventory().getNumberOf(995);
		if (!player.isArmourUnlocked(id)) {
			if (coins < price) {
				player.sendMessage("You need atleast "+Utils.formatNumber(price)+" coins to unlock this override.");
				return;
			}
			player.getInventory().deleteItem(995, price);
			player.unlockArmour(id);
			player.sendMessage("You've purchased "+Utils.formatString(Armour.getArmour(id).name())+" for "+Utils.formatNumber(price)+" coins.");
		}
		
		if (Armour.getArmour(id).isWeapon()) {
			player.setArmour(Armour.getArmour(id), Equipment.SLOT_WEAPON);
		} else {
			for (int i = 0; i < player.getArmour().length; i++) {
				if (isValidSlot(i)) {
					player.setArmour(Armour.getArmour(id), i);
				}
			}
		}
		player.getAppearence().generateAppearenceData();
		player.sendMessage(Utils.formatString(Armour.getArmour(id).name())+" is now active.");
	}

}
