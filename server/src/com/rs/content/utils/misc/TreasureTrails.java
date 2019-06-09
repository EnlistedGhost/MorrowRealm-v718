package com.rs.content.utils.misc;

import com.rs.game.player.Player;

/**
 * 
 * @author Adam
 * @since Aug,8.
 * 
 * 
 *
 */

public class TreasureTrails {
	
	public static int rewards[]= {14484, 1937};
	
	public static int rewards() {
		return rewards[(int) (Math.random() * rewards.length)];
	}
	
	/**
	 * 
	 * @param player
	 */
	public static void handleClues(Player player) {
		if (player.getInventory().containsOneItem(2677)){
			player.getInterfaceManager().sendInterface(337);
		}
	}
	
	public static void clueDig() {
		
	}

}
