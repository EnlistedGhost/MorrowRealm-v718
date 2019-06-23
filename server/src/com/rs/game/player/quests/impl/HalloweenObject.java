package com.rs.game.player.quests.impl;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.utils.Utils;
/**
 * 
 * @author Lol Ftw Lol & AlterOPSnet
 *
 */
public class HalloweenObject {
	// all int
	public static int DUST_ID = 3325, ONE = 1;
	// all string
	public static String[] HALLOWEENMESSAGES = { "Empty...",
			"What the ****... ", "ewww... Its Horrible" };
	public static String DUST = "You found some Vampyre dust!";
	public static String EMPTY = "Empty...";

	public static void HandleTeleportExit(Player player,
			final WorldObject object) {
		if (object.getX() == 4319 && object.getY() == 5453) {
			player.setNextWorldTile(new WorldTile(2957, 3290, 0));
			player.setNextGraphics(new Graphics(1767));
			player.setNextAnimation(new Animation(7312));
		}
	}

	public static void HandleTeleportEnter(Player player) {
		player.setNextGraphics(new Graphics(1767));
		player.setNextAnimation(new Animation(7312));
		player.setNextWorldTile(new WorldTile(4320, 5454, 0));
		player.cake = 0;
	}

	public static void HandleEmptyObject(Player player) {
		player.out(HALLOWEENMESSAGES[Utils.random(HALLOWEENMESSAGES.length)]);
	}

	public static void HandleTowel(Player player, final WorldObject object) {
		if (player.dust1 == 0 && object.getX() == 4328 && object.getY() == 5475) {
			player.sendMessage(DUST);
			player.dust1 = ONE;
			player.getInventory().addItem(DUST_ID, ONE);
		} else {
			player.sendMessage(EMPTY);
		}
	}

	public static void HandleCabinet(Player player, final WorldObject object) {
		if (player.dust2 == 0 && object.getX() == 4334 && object.getY() == 5471) {
			player.sendMessage(DUST);
			player.dust2 = ONE;
			player.getInventory().addItem(DUST_ID, ONE);
		} else {
			player.sendMessage(EMPTY);
		}
	}

	public static void HandleWardrobe(Player player, final WorldObject object) {
		if (player.dust3 == 0 && object.getX() == 4318 && object.getY() == 5473) {
			player.sendMessage(DUST);
			player.dust3 = ONE;
			player.getInventory().addItem(DUST_ID, ONE);
		} else {
			player.sendMessage(EMPTY);
		}
	}

	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 46935) {
			HalloweenObject.HandleTeleportExit(player, object);
		}
		if (id == 62621) {
			HalloweenObject.HandleTeleportEnter(player);
		}
		if (id == 31819) {
			HalloweenObject.HandleTowel(player, object);
		}
		if (id == 46549) {
			HalloweenObject.HandleCabinet(player, object);
		}
		if (id == 46566) {
			HalloweenObject.HandleWardrobe(player, object);
		}
		if (id == 27896 || id == 32046 || id == 31747 || id == 30838
				|| id == 31842 || id == 46567 || id == 46568 || id == 31818) {
			HalloweenObject.HandleEmptyObject(player);
		}
	}
}