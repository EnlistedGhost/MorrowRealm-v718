package com.rs.game.player.content.cities;

import com.rs.game.WorldTile;
import com.rs.game.player.Player;
/**
 * 
 * @author JazzyYaYaYa | Nexon
 *
 */
public class Travels {
	public static final WorldTile Daero = new WorldTile(2182,5665, 0); // <-- Add this.
	
	public static void runDaero(Player player) {
		player.setNextFaceWorldTile(Daero);
		player.sm("Daero holds his *add* and teleports you.");
	}
	
}
