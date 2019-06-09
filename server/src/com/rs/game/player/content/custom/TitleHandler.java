package com.rs.game.player.content.custom;

import com.rs.cache.loaders.ClientScriptMap;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;

public class TitleHandler {

	public static boolean goesAfterName(int titleId) {
		int[] SPEC_TITLES = {
			32, 33, 34, 35, 36, 37, 40, 43, 54, 47, 48, 49, 51, 53, 55, 56, 58, 45, 
			66, 68, 69, 82, 83, 84, 85, 86, 87
		};
		for (Integer i : SPEC_TITLES) {
			if (titleId == i) {
				return true;
			}
		}
		return false;
	}
	
	public enum Title {
		
		Owner(59, "Owner ", false, "FFFF00", "AF0000", false, 0, 0),
		Admin(60, "Admin ", false,  "FF9900", "", false, 0, 0),
		Moderator(61, "Moderator ",  false, "FF9500", "FF4400", false, 0, 0),
		
		R_Donator(62, "R. Donator ",  false, "FF0000", "AA0000", false, 0, 0),
		S_Donator(63, "S. Donator ",  false, "0085F2", "0000FF", false, 0, 0),
		E_Donator(64, "E. Donator ",  false, "00FF00", "00AA00", false, 0, 0),
		
		Skiller(66, " the Skiller", true, "00AF00", "", true, 0, 0),
		Fighter(67, "Treasure Hunter ", false, "AF0000", "", true, 0, 0),
		Begger(68, " the Begger", true, "AF00AF", "", true, 0, 0),
		Gambler(69, " the Gambler", true, "FF00AF", "", true, 0, 0),
		Wiki_Editor(70, "Wiki Editor ",  false, "00AF00", "", false, 0, 0),
		
		First_Prestiger(71, "Apprentice ",  false, "007F00", "", true, 1, 0),
		Second_Prestiger(72, "Novice ",  false, "007F00", "", true, 2, 0),
		Third_Prestiger(73, "Expert ",  false, "FF5500", "", true, 3, 0),
		Fourth_Prestiger(74, "Master ",  false, "FF5500", "", true, 4, 0),
		Fifth_Prestiger(75, "Grand Master ",  false, "005FFF", "", true, 5, 0),
		Sixth_Prestiger(76, "Sensei ",  false, "005FFF", "", true, 6, 0),
		
		Archmage(77, "Archmage ",  false, "AF0000", "", true, 0, 0),
		Artful_Dodger(78, "Artful Dodger ",  false, "AF0000", "", true, 0, 0),
		The_Brave(79, "The Brave ",  false, "AF0000", "", true, 0, 0),
		
		The_Skillful(80, "The Skillful ",  false, "FF0000", "", true, 0, 0),
		Slayer(81, "Slayer ",  false, "FF5500", "", true, 0, 0),
		
		Armadyl(82, " of Armadyl", true, "007F00", "", true, 0, 0),
		Bandos(83, " of Bandos", true, "AF5F00", "", true, 0, 0),
		Saradomin(84, " of Saradomin", true, "005FFF", "", true, 0, 0),
		Zamorak(85, " of Zamorak", true, "AF0000", "", true, 0, 0),
		
		The_Loyal(86, " the Loyal", true, "AF00FF", "", true, 0, 5000),
		The_Dedicated(87, " the Dedicated", true, "AF00FF", "", true, 0, 10000),
		Veteran(88, "Veteran ", false, "AF5F00", "", true, 0, 50000),
		Legend(89, "Legendary ", false, "AF5F00", "", true, 6, 50000),
		
		Survivor(90, "Survivor ", false, "AF5F00", "", true, 0, 0),
		Guardian(91, "The Guardian ", false, "00AAFF", "", true, 0, 0);
		
		private int id, prestige, loyalty;
		private String name, color, shade;
		private boolean canSet, afterName;
		
		Title(int id, String name, boolean afterName, String color, String shade, boolean canBeSet, int prestige, int loyaltyPoints) {
			this.id = id;
			this.afterName = afterName;
			this.color = color;
			this.name = name;
			this.loyalty = loyaltyPoints;
			this.shade = shade;
			this.canSet = canBeSet;
			this.prestige = prestige;
		}
		
		public int getLoyaltyReq() {
			return loyalty;
		}
		
		public boolean goesAfterName() {
			return afterName;
		}
		
		public int getPrestigeReq() {
			return prestige;
		}
		
		public String getName() {
			return name;
		}
		
		public String getColor() {
			return color;
		}
		
		public String getShade() {
			return shade;
		}
		
		public int getId() {
			return id;
		}
		
		public boolean canBeSet() {
			return canSet;
		}
		
		public String getFullTitle() {
			return "<col="+color+"><shad="+shade+">"+name+"</shad></col>";
		}
	}
	
	public static Title forId(int id) {
		for (Title t : Title.values()) {
			if (t.getId() == id) {
				return t;
			}
		}
		return null;
	}
	
	public static void set(Player player, int titleId) {
		Title title = forId(titleId);
		
		if (titleId == 91 && !player.isTitleUnlocked(91)) {
			player.sendMessage("Only awarded to those that have played Guardian 718.");
			return;
		}
		
		if (titleId == 71 && player.getPrestigeLevel() != 1) {
			player.sendMessage("You may only use this title if you are 1st prestige.");
			resetTitle(player);
			return;
		}
		
		if (titleId == 72 && player.getPrestigeLevel() != 2) {
			player.sendMessage("You may only use this title if you are 2nd prestige.");
			resetTitle(player);
			return;
		}
		
		if (titleId == 73 && player.getPrestigeLevel() != 3) {
			player.sendMessage("You may only use this title if you are 3rd prestige.");
			resetTitle(player);
			return;
		}
		
		if (titleId == 74 && player.getPrestigeLevel() != 4) {
			player.sendMessage("You may only use this title if you are 4th prestige.");
			resetTitle(player);
			return;
		}
		
		if (titleId == 75 && player.getPrestigeLevel() != 5) {
			player.sendMessage("You may only use this title if you are 5th prestige.");
			resetTitle(player);
			return;
		}
		
		if (titleId == 76 && player.getPrestigeLevel() != 6) {
			player.sendMessage("You may only use this title if you are 6th prestige.");
			resetTitle(player);
			return;
		}
		
		if (title != null) {
			if (!title.canBeSet()) {
				player.sendMessage("Please use ::select to set this title.");
				return;
			}
			
			if (title.getPrestigeReq() > 0 && title.getLoyaltyReq() > 0 && !player.isTitleUnlocked(titleId)) {
				if (title.getLoyaltyReq() > player.getLoyaltyPoints() || title.getPrestigeReq() > player.getPrestigeLevel()) {
					player.sendMessage("You need "+Utils.formatNumber(title.getLoyaltyReq())+" LP and a Prestige level of "+title.getPrestigeReq()+" to set this title!");
					return;
				}
				player.unlockTitle(titleId);
				player.setLoyaltyPoints(player.getLoyaltyPoints() - title.getLoyaltyReq());
				player.sendMessage("You have puchased "+title.getFullTitle()+" for "+title.getLoyaltyReq()+"");
			}
			
			if (title.getLoyaltyReq() > 0 && title.getPrestigeReq() == 0 && !player.isTitleUnlocked(titleId)) {
				if (title.getLoyaltyReq() > player.getLoyaltyPoints()) {
					player.sendMessage("You need atleast "+Utils.formatNumber(title.getLoyaltyReq())+" Loyalty Points to set "+title.getFullTitle()+"");
					return;
				}
				player.unlockTitle(titleId);
				player.setLoyaltyPoints(player.getLoyaltyPoints() - title.getLoyaltyReq());
				player.sendMessage("You have purchased '"+title.getFullTitle()+"' for "+Utils.formatNumber(title.getLoyaltyReq())+" Loyalty Points.");
			}
			
			if (titleId == 77 && !player.isTitleUnlocked(77)) {
				if (player.getSkills().getXp(Skills.MAGIC) < 200000000) {
					player.sendMessage("You need atleast <col=FF0000>200M Magic Exp</col> to set "+title.getFullTitle()+"!");
					return;
				}
				player.unlockTitle(77);
			}
			
			if (titleId == 67 && !player.isTitleUnlocked(67)) {
				if (player.getCasketsOpened() < 50) {
					player.sendMessage("You need to open atleast 50 Clue Scrolls caskets for this title.");
					return;
				}
				player.unlockTitle(67);
			}
			
			if (titleId == 78 && !player.isTitleUnlocked(78)) {
				if (player.getSkills().getXp(Skills.AGILITY) < 200000000) {
					player.sendMessage("You need atleast <col=FF0000>200M Magic Exp</col> to set "+title.getFullTitle()+"!");
					return;
				}
				player.unlockTitle(78);
			}
			
			if (titleId == 80 && !player.isTitleUnlocked(80)) {
				if (!player.getSkills().hasMaxedSkills()) {
					player.sendMessage("You need atleast <col=FF0000>99 in all non-combat skills</col> to set "+title.getFullTitle()+"!");
					return;
				}
				player.unlockTitle(80);
			}
			
			if (titleId == 81 && !player.isTitleUnlocked(81)) {
				if (player.getNpcKills() < 2500) {
					player.sendMessage("You need atleast <col=FF0000>2,500 NPC Kills</col> to set "+title.getFullTitle()+"!");
					return;
				}
				player.unlockTitle(81);
			}
			
			if (titleId == 83 && !player.isTitleUnlocked(83)) {
				if (player.getBandosKills() < 50) {
					player.sendMessage("You need atleast <col=FF0000>50 Bandos Kills</col> to set "+title.getFullTitle()+"!");
					return;
				}
				player.unlockTitle(83);
			}
			
			if (titleId == 82 && !player.isTitleUnlocked(82)) {
				if (player.getArmadylKills() < 50) {
					player.sendMessage("You need atleast <col=FF0000>50 Armadyl Kills</col> to set "+title.getFullTitle()+"!");
					return;
				}
				player.unlockTitle(82);
			}
			
			if (titleId == 84 && !player.isTitleUnlocked(84)) {
				if (player.getSaradominKills() < 50) {
					player.sendMessage("You need atleast <col=FF0000>50 Saradomin Kills</col> to set "+title.getFullTitle()+"!");
					return;
				}
				player.unlockTitle(84);
			}
			
			if (titleId == 85 && !player.isTitleUnlocked(85)) {
				if (player.getZamorakKills() < 50) {
					player.sendMessage("You need atleast <col=FF0000>50 Zamorak Kills</col> to set "+title.getFullTitle()+"!");
					return;
				}
				player.unlockTitle(85);
			}
			
			if (titleId == 79 && !player.isTitleUnlocked(79)) {
				player.sendMessage("You need to <col=FF0000>Kill Corporeal Beast with a flower</col> to set "+title.getFullTitle()+"!");
				return;
			}
			
			if (titleId == 90 && !player.isTitleUnlocked(90)) {
				if (player.getMaxZombieWave() < 50) {
					player.sendMessage("You need to kill atleast 50 zombies in one session of Zombie Onslaught");
					return;
				}
				player.unlockTitle(90);
			}
			
			player.getAppearence().setTitle(titleId);player.setTitle(title); 
			player.getAppearence().generateAppearenceData();
			player.sendMessage("Your new title is now: "+player.getTitle()+"");
			return;
		}
		
		if (player.getRights() == 0) {
			if (titleId < 5 || titleId > 15) {
				player.sendMessage("Please donate if you would like to use more titles!");
				player.sendMessage("Free titles are titles 5 through 15. See ::titlelist for available titles");
				return;
			}
		}
		
		player.getAppearence().setTitle(titleId);
		player.setTitle(title); 
		player.getAppearence().generateAppearenceData();
		player.sendMessage("Your new title is now: "+player.getTitle()+"");
	}
	
	public static void forceSet(Player player, int titleId) {
		Title title = forId(titleId);
		String newTitle = ClientScriptMap.getMap(player.getAppearence().isMale() ? 1093 : 3872).getStringValue(titleId);
		
		if (title != null) {
			player.getAppearence().setTitle(titleId);
			player.setTitle(title); 
			player.getAppearence().generateAppearenceData();
			player.sendMessage("Your new title is now: "+player.getTitle()+"");
			return;
		}
	}
	
	public static String getTitle(Player player) {
		int titleId = player.getAppearence().getTitle();
		if (player.getTitle() != null) {
			return player.getTitle().getFullTitle();
		}
		return ClientScriptMap.getMap(player.getAppearence().isMale() ? 1093 : 3872).getStringValue(titleId);
	}
	
	public static String getTitle(Player player, int titleId) {
		return forId(titleId) == null ? "--------" : forId(titleId).getFullTitle();
	}
	
	public static String getTitle(Title title) {
		return title == null ? "--------" : title.getFullTitle();
	}
	
	public static void openMenu(Player player) {
		Title title;
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendIComponentText(275, 1, "Titles List");
		player.getPackets().sendIComponentText(275, 10, "Scroll down to see more titles, there are a lot!");
		player.getPackets().sendIComponentText(275, 11, "Titles unlocked will have a [*] next to them!");
		player.getPackets().sendIComponentText(275, 12, "To set your title, type ::title id#");
		player.getPackets().sendIComponentText(275, 13, "L and P represent the Loyalty and Prestige Requirements");
		player.getPackets().sendIComponentText(275, 14, "-------------------------------------");
		player.getPackets().sendIComponentText(275, 15, "");
		for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(275); i++) {
			if (i < 59) {
				int mapId = player.getAppearence().isMale() ? 1093 : 3872;
				player.getPackets().sendIComponentText(275, (i + 16), "ID "+i+" - "+ClientScriptMap.getMap(mapId).getStringValue(i)+"");
				continue;
			}
			title = forId(i);
			if (title == null) {
				continue;
			}
			player.getPackets().sendIComponentText(275, (i + 16), ""+i+" - "+(player.isTitleUnlocked(i) == true ? "[*] " : "")+" "+getTitle(title)+" (L: "+title.getLoyaltyReq()+", P: "+title.getPrestigeReq()+")");
		}
	}
	
	
	public static void resetTitle(Player player) {
		player.setTitle(null);
		player.getAppearence().setTitle(0);
	}
	
}
