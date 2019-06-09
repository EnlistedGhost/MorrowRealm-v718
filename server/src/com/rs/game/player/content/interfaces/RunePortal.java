package com.rs.game.player.content.interfaces;

import com.rs.game.player.Player;
import com.rs.game.player.content.PlayerGoals;
import com.rs.utils.Utils;

public class RunePortal {
  
	public static void sendPortal(Player player) {
		 player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 114 : 174, 506);
         player.getPackets().sendIComponentText(506, 0, ""); // title
         player.getPackets().sendIComponentText(506, 2, "Account Manager"); // top button
         player.getPackets().sendIComponentText(506, 4, "*Removed*"); // Skilling
         player.getPackets().sendIComponentText(506, 6, "*Removed*"); // Bosses
         player.getPackets().sendIComponentText(506, 8, "*Removed*"); // Minigames
         player.getPackets().sendIComponentText(506, 10,"*Removed*"); // PvP Tele
         player.getPackets().sendIComponentText(506, 12,"*Removed*"); // Training
         player.getPackets().sendIComponentText(506, 14,"*Removed*"); // Misc.
	}
	
    public static void handleButtons(Player player, int componentId) {
        if (componentId == 2) {
        	player.getDialogueManager().startDialogue("AccountManager", 230);
            return;
        }
        if (componentId == 4) {
            // player.getDialogueManager().startDialogue("SkillTeleports", 230);
            return;
        }
        if (componentId == 6) {
            // player.getDialogueManager().startDialogue("BossTeleports", 230);
            return;
        }
        if (componentId == 8) {
            // player.getDialogueManager().startDialogue("MinigameTeleports", 230);
            return;
        }
        if (componentId == 10) {
            // player.getDialogueManager().startDialogue("PkingTeleports", 230);
            return;
        }
        if (componentId == 12) {
        	// player.getDialogueManager().startDialogue("TrainingTeleports", 230);
            return;
        }
        if (componentId == 14) {
        	// player.getDialogueManager().startDialogue("MiscTeleports", 230);
            return;
        }
    }
    
    public static int INTER = 629;
    public static void sendAccountInfo(Player player) {
    	double kill = player.getKillCount();
		double death = player.getDeathCount();
		double dr = kill / death;
		
		player.getInterfaceManager().sendInterface(INTER);
		player.getPackets().sendIComponentText(INTER, 11, "Account Info");
		player.getPackets().sendIComponentText(INTER, 12, "");
		
		player.getPackets().sendIComponentText(INTER, 67, "Close");
		player.getPackets().sendIComponentText(INTER, 68, "Close");
		
		player.getPackets().sendIComponentText(INTER, 41, "Total Donated:");
		player.getPackets().sendIComponentText(INTER, 42, "Account Type:");
		player.getPackets().sendIComponentText(INTER, 43, "Loyalty Pts.:");
		player.getPackets().sendIComponentText(INTER, 44, "Kills / Deaths:");
		player.getPackets().sendIComponentText(INTER, 45, "----------");
		player.getPackets().sendIComponentText(INTER, 46, "Dung Points:");
		player.getPackets().sendIComponentText(INTER, 47, "Pk Points:");
		player.getPackets().sendIComponentText(INTER, 48, "Prestige Lvl / Points:");
		player.getPackets().sendIComponentText(INTER, 49, "----------");
		player.getPackets().sendIComponentText(INTER, 50, "Achievements:");
		player.getPackets().sendIComponentText(INTER, 51, "Skill Points:");
		player.getPackets().sendIComponentText(INTER, 52, "Difficulty:");
		
		player.getPackets().sendIComponentText(INTER, 54, "$"+player.getDonationAmount()+"0");
		player.getPackets().sendIComponentText(INTER, 55, ""+player.getAccountType()+"");
		player.getPackets().sendIComponentText(INTER, 56, ""+Utils.formatNumber(player.getLoyaltyPoints())+"");
		player.getPackets().sendIComponentText(INTER, 57, ""+kill+" / "+death+"");
		player.getPackets().sendIComponentText(INTER, 58, "----------");
		player.getPackets().sendIComponentText(INTER, 59, ""+Utils.formatNumber(player.getDungPoints())+"");
		player.getPackets().sendIComponentText(INTER, 60, ""+Utils.formatNumber(player.getPvpPoints())+"");
		player.getPackets().sendIComponentText(INTER, 61, ""+player.getPrestigeLevel()+" / "+Utils.formatNumber(player.getPrestigePoints())+"");
		player.getPackets().sendIComponentText(INTER, 62, "----------");
		player.getPackets().sendIComponentText(INTER, 63, ""+player.getGoals().getNumberCompleted()+" / "+PlayerGoals.totalAchs+"");
		player.getPackets().sendIComponentText(INTER, 64, ""+Utils.formatNumber(player.getSkillPoints())+"");
		player.getPackets().sendIComponentText(INTER, 65, ""+getDiffString(player.getDifficulty())+"");
    }
    
    private static String getDiffString(int diff) {
    	if (diff == 1)
    		return "Very Easy";
    	if (diff == 2)
    		return "Easy";
    	if (diff == 3)
    		return "Normal";
    	if (diff == 4)
    		return "Hard";
    	if (diff == 5)
    		return "Official"; // Extreme
    	return "";
    }
  
}
