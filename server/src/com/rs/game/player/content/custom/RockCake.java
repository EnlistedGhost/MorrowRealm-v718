package com.rs.game.player.content.custom;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class RockCake {

	private static final Animation EAT_ANIM = new Animation(829);
	private static final int ROCK_CAKE = 7509;
	
	public static void eat(Player player) {
		if (!player.getInventory().containsItem(ROCK_CAKE)) {
			return;
		}
		if (player.getFoodDelay() > Utils.currentTimeMillis()) {
			return;
		}
		player.setNextAnimation(EAT_ANIM);
		player.getPackets().sendGameMessage("You eat the Dwarven Rock Cake..");
		player.getInventory().deleteItem(ROCK_CAKE, 1);
		long foodDelay = 1800;
		player.getActionManager().setActionDelay((int) foodDelay / 1000);
		player.addFoodDelay(foodDelay);
		player.applyHit(new Hit(player, 100, HitLook.REGULAR_DAMAGE));
	}
	
}
