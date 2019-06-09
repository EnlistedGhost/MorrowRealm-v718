package com.rs.game.player.content;

import com.rs.game.Animation;
import com.rs.game.player.Player;
/**
 * 
 * @author JazzyYaYaYa | Fuzen Seth | Nexon
 *
 */
public class Searchables {
private static int GRAIN = 1947;
public static final Animation BALESSEARCH = new Animation(1026);

	public static void SearchBales(Player player) {
		player.getInventory().addItem(GRAIN, 1);
		player.setNextAnimation(BALESSEARCH);
		player.getInventory().refresh();
		player.sm("grain.");
	}
}
