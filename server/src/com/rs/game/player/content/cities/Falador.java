package com.rs.game.player.content.cities;

import com.rs.game.Animation;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

/**
 * 
 * @author JazzyYaYaYa | Nexon | Fuzen Seth
 * Do not remove the authority.
 */
public class Falador {
public static int DIG = 2408;
public static final Animation DIGGING = new Animation(828);

	public static void EnterDungeon(final Player player) {
		player.lock(3);
		player.setNextAnimation(DIGGING);
		player.stopAll();
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.unlock();
				player.sm("You enter to the Entrana Dungeon...");
				player.teleportPlayer(2828, 9772, 0);
			}
		}, 3);
	}
	
	
}
