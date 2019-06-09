package com.rs.game.player.content;

import com.rs.game.Animation;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.player.Player;

/**
 * 
 * @author Adam
 */

public class FlowerGame {

	public static int Flower[] = { 2980, 2981, 2982, 2983, 2984, 2985, 2986, 2987, 2988 };

	public static int randomFlower() {
		return Flower[(int) (Math.random() * Flower.length)];
	}


	public static void plant(final Player player) {
		/*if (!player.isTrustedflower()) {
			player.sm("You are an untrusted host you can't play.");
			return;
		}*/
		
		player.setNextAnimation(new Animation(827));
		World.spawnTempGroundObject(new WorldObject(randomFlower(), 10, 0, player.getX(), player.getY(), player.getPlane()), 299, 35000);
		player.getInventory().deleteItem(299, 1);

		if (!player.addWalkSteps(player.getX() - 1, player.getY(), 1))
			if (!player.addWalkSteps(player.getX() + 1, player.getY(), 1))
				if (!player.addWalkSteps(player.getX(), player.getY() + 1, 1))
					player.addWalkSteps(player.getX(), player.getY() - 1, 1);

	}

	public static void cheat(final Player player) {
		player.setNextAnimation(new Animation(827));
		World.spawnTempGroundObject(new WorldObject(2983, 10, 0, player.getX(), player.getY(), player.getPlane()), 299, 35000);
		player.getInventory().deleteItem(299, 1);

		if (!player.addWalkSteps(player.getX() - 1, player.getY(), 1))
			if (!player.addWalkSteps(player.getX() + 1, player.getY(), 1))
				if (!player.addWalkSteps(player.getX(), player.getY() + 1, 1))
					player.addWalkSteps(player.getX(), player.getY() - 1, 1);
	}

}
