package com.rs.game.player.content;

import com.rs.game.Animation;
import com.rs.game.player.Player;
/**
 * 
 * @author JazzyYaYaYa | Nexon | Fuzen Seth
 *
 */
public class Barrels {
public static int APPLES = 1955;
public static int ONE = 1;
public static final Animation TAKEITEM = new Animation(832);

	public static void pickApples(Player player) {
		player.getInventory().addItem(APPLES, ONE);
		player.getInventory().refresh();
		player.setNextAnimation(TAKEITEM);
		player.lock(2);
		player.sm("You have taken some apples from the barrel.");
	}
}
