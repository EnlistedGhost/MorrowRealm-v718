package com.rs.game.player;

import com.rs.game.item.Item;
import com.rs.utils.Utils;

/**
 * @author JazzyYaYaYa | Nexon
 */
public class DailyChallenges {

	public static final int MAIN_BOARD = 1343;

	public static void sendChallengesBoard(Player player) {
		player.getPackets().sendWindowsPane(MAIN_BOARD, 0);
		player.getInventory().refresh();
		player.closeInterfaces();
	}

	/* This method is handling the whole screen interface. */
	public void handleButtons(Player player, int buttonId) {
		if (buttonId == 56) {
			player.getPackets().sendWindowsPane(
					player.getInterfaceManager().hasRezizableScreen() ? 746
							: 548, 0); /*
										 * <-- Resets the Daily Challenges
										 * interface.
										 */
			player.getInventory().refresh();
			player.closeInterfaces();
		}
	}

}
