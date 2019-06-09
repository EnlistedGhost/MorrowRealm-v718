package com.rs.game.player.content.interfaces;

import com.rs.game.player.Player;
import com.rs.game.player.content.custom.TitleHandler;
import com.rs.game.player.content.custom.TitleHandler.Title;

public class PvmRewards {

	private static final int interId = 1156;
	public static void openInterface(Player player) {
		player.isInTitleList = true;
		
		if (!player.getInterfaceManager().containsInterface(interId))
			player.getInterfaceManager().sendInterface(interId);
		
		sendString(player, new int[]{ 108, 109, 90  }, 82, "Defeat Kree'Aara atleast 50 times.");
		sendString(player, new int[]{ 113, 114, 206 }, 83, "Defeat General Graardor atleast 50 times.");
		sendString(player, new int[]{ 137, 138, 254 }, 84, "Defeat Commander Zilyana atleast 50 times.");
		sendString(player, new int[]{ 110, 111, 200 }, 85, "Defeat K'ril Tsutsaroth atleast 50 times.");
		sendString(player, new int[]{ 116, 117, 212 }, 79, "Finish off Corporeal Beast...with a flower.");
		sendString(player, new int[]{ 134, 135, 248 }, 81, "Kill a total of 2,500 monsters.");
		sendString(player, new int[]{ 122, 123, 230 }, 77, "Reach the maximum amount of Magic exp (200m)");
		sendString(player, new int[]{ 128, 129, 236 }, 78, "Reach the maximum amount of Agility exp (200m)");
		sendString(player, new int[]{ 125, 126, 224 }, 80, "Reach 99 in all non-combat skills.");
		sendString(player, new int[]{ 143, 144, 266 }, 71, "Only useable when you're at Prestige 1.");
		sendString(player, new int[]{ 146, 147, 272 }, 72, "Only useable when you're at Prestige 2.");
		sendString(player, new int[]{ 119, 120, 218 }, 73, "Only useable when you're at Prestige 3.");
		sendString(player, new int[]{ 131, 132, 242 }, 74, "Only useable when you're at Prestige 4.");
		sendString(player, new int[]{ 140, 141, 260 }, 75, "Only useable when you're at Prestige 5.");
		sendString(player, new int[]{ 149, 150, 278 }, 76, "Only useable when you're at Prestige 6.");
		sendString(player, new int[]{ 152, 153, 284 }, 86, "Requires 5,000 Loyalty Points");
		sendString(player, new int[]{ 152, 153, 284 }, 86, "Requires 5,000 Loyalty Points");
		sendString(player, new int[]{ 167, 168, 308 }, 87, "Requires 10,000 Loyalty Points");
		sendString(player, new int[]{ 155, 157, 290 }, 88, "Requires 50,000 Loyalty Points");
		sendString(player, new int[]{ 159, 161, 296 }, 89, "Requires 50,000 Loyalty Points and Prestige 6");
		sendString(player, new int[]{ 163, 165, 302 }, 90, "Reach 50 kills in one session of Zombies Onslaught");
		sendString(player, new int[]{ 170, 171, 314 }, 67, "Open atleast 50 Clue Scrolls caskets.");
		sendString(player, new int[]{ 318, 319, 326 }, 91, "Only awarded to those that have played Guardian 718.");
		
		sendString(player, new int[]{ 156, 164, 160 }, 0, null); // leave null
	}
	
	public static void sendString(Player player, int[] cIds, int titleId, String desc) {
		Title title = TitleHandler.forId(titleId);
		if (title != null) {
			boolean goesAfterName = title.goesAfterName();
			String titleName = (goesAfterName == true ? player.getDisplayName() + title.getFullTitle() : title.getFullTitle() + player.getDisplayName());
			player.getPackets().sendIComponentText(interId, cIds[0], desc == null ? "" : titleName);
			player.getPackets().sendIComponentText(interId, cIds[1], desc == null ? "" : desc);
			player.getPackets().sendIComponentText(interId, cIds[2], desc == null ? "" : getStatus(player, titleId));
		}
	}
	
	public static void handleButtons(Player player, int buttonId) {
		if (buttonId == 88) {
			TitleHandler.set(player, 82); // armadyl
		} else if (buttonId == 115) {
			TitleHandler.set(player, 83); // bandos
		} else if (buttonId == 139) {
			TitleHandler.set(player, 84); // saradomin
		} else if (buttonId == 112) {
			TitleHandler.set(player, 85); // zamorak
		} else if (buttonId == 118) {
			TitleHandler.set(player, 79); 
		} else if (buttonId == 136) {
			TitleHandler.set(player, 81); 
		} else if (buttonId == 124) {
			TitleHandler.set(player, 77); 
		} else if (buttonId == 130) {
			TitleHandler.set(player, 78); 
		} else if (buttonId == 127) {
			TitleHandler.set(player, 80); 
		} else if (buttonId == 145) {
			TitleHandler.set(player, 71); 
		} else if (buttonId == 148) {
			TitleHandler.set(player, 72); 
		} else if (buttonId == 121) {
			TitleHandler.set(player, 73); 
		} else if (buttonId == 133) {
			TitleHandler.set(player, 74); 
		} else if (buttonId == 142) {
			TitleHandler.set(player, 75); 
		} else if (buttonId == 151) {
			TitleHandler.set(player, 76); 
		} else if (buttonId == 154) {
			TitleHandler.set(player, 86); 
		} else if (buttonId == 169) {
			TitleHandler.set(player, 87); 
		} else if (buttonId == 158) {
			TitleHandler.set(player, 88); 
		} else if (buttonId == 162) {
			TitleHandler.set(player, 89); 
		} else if (buttonId == 166) {
			TitleHandler.set(player, 90); 
		} else if (buttonId == 172) {
			TitleHandler.set(player, 67); 
		} else if (buttonId == 320) {
			TitleHandler.set(player, 91); 
		}
		openInterface(player);
	}
	
	public static String getStatus(Player player, int id) {
		return player.isTitleUnlocked(id) == true ? "<col=00FF00>Activate</col>" : "<col=FF0000>Locked</col>" ;
	}
}
