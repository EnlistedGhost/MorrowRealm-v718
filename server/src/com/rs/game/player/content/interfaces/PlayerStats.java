package com.rs.game.player.content.interfaces;

import com.rs.Settings;
import com.rs.game.player.Player;

public class PlayerStats {

	public static int INTER_ID = 1314;
	public static int PACKET_ID = 49;
	
	public static void openStatMenu(Player player, Player other) {
		player.getInterfaceManager().sendInterface(INTER_ID);
		player.getPackets().sendIComponentText(1314, 91, ""+Settings.SERVER_NAME+" Player Stats");
		player.getPackets().sendIComponentText(1314, 90, ""+other.getDisplayName() +"'s stats");
		player.getPackets().sendIComponentText(1314, 61, ""+other.getSkills().getLevel(0));//attack
		player.getPackets().sendIComponentText(1314, 62, ""+other.getSkills().getLevel(2)); //str
		player.getPackets().sendIComponentText(1314, 63, ""+other.getSkills().getLevel(1)); //def
		player.getPackets().sendIComponentText(1314, 65, ""+other.getSkills().getLevel(4)); //range
		player.getPackets().sendIComponentText(1314, 66, ""+other.getSkills().getLevel(5)); //prayer
		player.getPackets().sendIComponentText(1314, 64, ""+other.getSkills().getLevel(6)); //mage
		player.getPackets().sendIComponentText(1314, 78, ""+other.getSkills().getLevel(20)); //rc
		player.getPackets().sendIComponentText(1314, 81, ""+other.getSkills().getLevel(22)); //construction
		player.getPackets().sendIComponentText(1314, 76, ""+other.getSkills().getLevel(24)); //dung
		player.getPackets().sendIComponentText(1314, 82, ""+other.getSkills().getLevel(3)); //hitpoints
		player.getPackets().sendIComponentText(1314, 83, ""+other.getSkills().getLevel(16)); //agiality
		player.getPackets().sendIComponentText(1314, 84, ""+other.getSkills().getLevel(15)); //herblore
		player.getPackets().sendIComponentText(1314, 80, ""+other.getSkills().getLevel(17)); //thiving
		player.getPackets().sendIComponentText(1314, 70, ""+other.getSkills().getLevel(12)); //crafting
		player.getPackets().sendIComponentText(1314, 85, ""+other.getSkills().getLevel(9)); //fletching
		player.getPackets().sendIComponentText(1314, 77, ""+other.getSkills().getLevel(18)); //slayer
		player.getPackets().sendIComponentText(1314, 79, ""+other.getSkills().getLevel(21)); //hunter
		player.getPackets().sendIComponentText(1314, 68, ""+other.getSkills().getLevel(14)); //mining
		player.getPackets().sendIComponentText(1314, 69, ""+other.getSkills().getLevel(13)); //smithing
		player.getPackets().sendIComponentText(1314, 74, ""+other.getSkills().getLevel(10)); //fishing
		player.getPackets().sendIComponentText(1314, 75, ""+other.getSkills().getLevel(7)); //cooking
		player.getPackets().sendIComponentText(1314, 73, ""+other.getSkills().getLevel(11)); //firemaking
		player.getPackets().sendIComponentText(1314, 71, ""+other.getSkills().getLevel(8)); //wc
		player.getPackets().sendIComponentText(1314, 72, ""+other.getSkills().getLevel(19)); //farming
		player.getPackets().sendIComponentText(1314, 67, ""+other.getSkills().getLevel(23)); //summining
		player.getPackets().sendIComponentText(1314, 30, "Prestige Level:"); //boss
		player.getPackets().sendIComponentText(1314, 60, "" +other.getPrestigeLevel()); //boss
		player.getPackets().sendIComponentText(1314, 87, ""+other.getMaxHitpoints()); //hitpoints
		player.getPackets().sendIComponentText(1314, 86, ""+other.getSkills().getCombatLevelWithSummoning()); //combatlevel
		player.getPackets().sendIComponentText(1314, 88, ""+other.getSkills().getTotalLevel(other)); //total level
		player.getPackets().sendIComponentText(1314, 89, ""+other.getSkills().getTotalXp(other)); //total level
		player.getAttributes().put("finding_player", Boolean.FALSE);
	return;
		
	}
	
}
