package com.rs.game.player.content.mission;

import com.rs.game.player.Player;
/**
 * 
 * @author JazzyYaYaYa | Nexon | Fuzen Seth
 *
 */
public class MissionRewards {
public static final int FirstM = 16; //Magic Whistle
public static final int SecondM = -1; //Not identified
public static final int ThirdM = -1; //Not identified
public static final int FourthM = -1; //Not identified
public static final int FivethM = -1; //Not identified

	public static void RewardHandler(Player player) {
		if (player.getInventory().containsItem(FirstM, 1)) {
			player.getInventory().addItem(19784, 1);
			player.getInventory().deleteItem(FirstM, 1);
			player.getInventory().refresh();
		}
		else {
			player.getDialogueManager().startDialogue("SimplePlayerMessage", "I haven't completed any missions yet.");
			
		}
		
	}
}
