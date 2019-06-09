package com.rs.game.player.content.cities;

import com.rs.game.ForceMovement;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;

/**
 * 
 * @author JazzyYaYaYa | Nexon | Fuzen Seth
 *
 */
public class Lumbridge {

	public static void GateForceWalkAction(Player player) {
		WorldTile toTile = player.transform(0, 1, 0);
		player.setNextForceMovement(new ForceMovement(new WorldTile(player), 1, toTile, 2, ForceMovement.NORTH)); /*<-- TODO */
	}
}
