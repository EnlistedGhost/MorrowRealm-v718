package com.rs.game.player.content;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.player.Equipment;
import com.rs.game.player.Player;
import com.rs.game.player.QuestManager.Quests;

public class ItemConstants {
	
	static String[] safeItems = { 
		"completionist", "max", "ring of wealth", "tokhaar-kal", "fire cape"
	};
	
	public static boolean isSafeOnDeath(int itemId) {
		String name = ItemDefinitions.getItemDefinitions(itemId).getName().toLowerCase();
		for (String n : safeItems) {
			if (name.contains(n.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	public static int getDegradeItemWhenWear(int id) {
		// pvp armors
		if (id == 13958 || id == 13961 || id == 13964 || id == 13967
				|| id == 13970 || id == 13973 || id == 13858 || id == 13861
				|| id == 13864 || id == 13867 || id == 13870 || id == 13873
				|| id == 13876 || id == 13884 || id == 13887 || id == 13890
				|| id == 13893 || id == 13896 || id == 13899 || id == 13902
				|| id == 13905 || id == 13908 || id == 13911 || id == 13914
				|| id == 13917 || id == 13920 || id == 13923 || id == 13926
				|| id == 13929 || id == 13932 || id == 13935 || id == 13938
				|| id == 13941 || id == 13944 || id == 13947 || id == 13950
				|| id == 13958)
			return id + 2; // if you wear it it becomes corrupted LOL
		return -1;
	}

	// return amt of charges
	public static int getItemDefaultCharges(int id) {
		// pvp armors
		if (id == 13910 || id == 13913 || id == 13916 || id == 13919
				|| id == 13922 || id == 13925 || id == 13928 || id == 13931
				|| id == 13934 || id == 13937 || id == 13940 || id == 13943
				|| id == 13946 || id == 13949 || id == 13952)
			return 1500;
		if (id == 13960 || id == 13963 || id == 13966 || id == 13969
				|| id == 13972 || id == 13975)
			return 3000;
		if (id == 13860 || id == 13863 || id == 13866 || id == 13869
				|| id == 13872 || id == 13875 || id == 13878 || id == 13886
				|| id == 13889 || id == 13892 || id == 13895 || id == 13898
				|| id == 13901 || id == 13904 || id == 13907 || id == 13960)
			return 6000; // 1hour
		// nex armors
		if (id == 20137 || id == 20141 || id == 20145 || id == 20149
				|| id == 20153 || id == 20157 || id == 20161 || id == 20165
				|| id == 20169 || id == 20173)
			return 60000;
		return -1;
	}

	// return what id it degrades to, -1 for disapear which is default so we
	// dont add -1
	public static int getItemDegrade(int id) {
		if (id == 11285) // DFS
			return 11283;
		// nex armors
		if (id == 20137 || id == 20141 || id == 20145 || id == 20149
				|| id == 20153 || id == 20157 || id == 20161 || id == 20165
				|| id == 20169 || id == 20173)
			return id + 1;
		return -1;
	}

	public static int getDegradeItemWhenCombating(int id) {
		// nex armors
		if (id == 20147 || id == 20151 || id == 20155 || id == 20159 || id == 20163 || id == 20167 || id == 20171)
			return id + 2;
		return -1;
	}

	public static boolean itemDegradesWhileHit(int id) {
		if (id == 2550)
			return true;
		return false;
	}

	public static boolean itemDegradesWhileWearing(int id) {
		String name = ItemDefinitions.getItemDefinitions(id).getName().toLowerCase();
		if (name.contains("c. dragon") || name.contains("corrupt dragon")
				|| name.contains("vesta's") || name.contains("statius'")
				|| name.contains("morrigan's") || name.contains("zuriel's"))
			return true;
		return false;
	}

	public static boolean itemDegradesWhileCombating(int id) {
		String name = ItemDefinitions.getItemDefinitions(id).getName().toLowerCase();
		if (name.contains("pernix") || name.contains("virtux") || name.contains("zaryte"))
			return true;
		return false;
	}

	public static boolean canWear(Item item, Player player) {
		String itemName = ItemDefinitions.getItemDefinitions(item.getId()).getName().toLowerCase();
		if (itemName.contains("completionist")) {
			if (player.getSkills().getTotalLevel(player) < 2496) {
				player.sendMessage("You need a total level of 2496 before you can wear this.");
				return false;
			}
		} else if (itemName.contains("max")) {
			if (player.getSkills().getTotalLevel(player) < 2475) {
				player.sendMessage("You need a total level of 2475 before you can wear this.");
				return false;
			}
		} else if (itemName.contains("fire cape")) {
			if (!player.isCompletedFightCaves()) {
				player.getPackets().sendGameMessage("You need to complete fightcaves before you can equip firecape.");
				return false;
			}
		} else if (itemName.contains("blue cape") || itemName.contains("red cape")) {
			if (!player.getQuestManager().completedQuest(Quests.NOMADS_REQUIEM)) {
				player.getPackets().sendGameMessage("You need to have completed Nomad's Requiem miniquest to use this cape.");
				return false;
			}
		} 
		return true;
	}
	
	public static boolean isTradeable(Item item) {
		if (item.getDefinitions().getName().toLowerCase().contains("flaming skull"))
			return false;
		String name = ItemDefinitions.getItemDefinitions(item.getId()).getName().toLowerCase();
		if (name.contains("statius's") || name.contains("vesta's")
				|| name.contains("lucky") || name.contains("zuriel's")
				|| name.contains("flaming skull")
				|| name.contains("abyssal whip (lime)")
				|| name.contains("ganodermic")
				|| name.contains("arcane stream")
				|| name.contains("armadyl battlestaff")
				|| name.contains("fist of guthix")
				|| name.contains("pk token")
				|| name.contains("morrigan's") 
				|| name.contains("fire cape")
				|| name.contains("tokhaar") 
				|| name.contains("drygore") 
				|| name.contains("kalphite") 
				|| name.contains("void") 
				|| name.contains("third-age") 
				|| name.contains("spin ticket") 
				|| name.contains("partyhat")
				|| name.contains("defender"))
			return false;
		switch (item.getId()) {
		case 25200: // Skill Gems
		case 6570: // firecape
		case 6529: // tokkul
		case 24155:
		case 20767:
		case 20768:
		case 20748:
		case 20749:
		case 20752:
		case 20753:
		case 20754:
		case 20769:
		case 20770:
		case 20771:
		case 20772:
		case 24154:
		case 7462: // barrow gloves
		case 23659: // tookhaar-kal
			return false;
		default:
			return true;
		}
	}
}
