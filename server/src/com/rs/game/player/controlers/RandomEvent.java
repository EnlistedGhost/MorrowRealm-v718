package com.rs.game.player.controlers;

import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

/**
 * Handles Random Event Controler
 * 
 * @author Demon Dylan
 *
 */

public class RandomEvent extends Controler {


	@Override
	public void start() {
	//todo
	}
	@Override
	public boolean sendDeath() {
		player.lock(7);
		player.stopAll();
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					player.setNextAnimation(new Animation(836));
				} else if (loop == 1) {
					player.getPackets().sendGameMessage(
							"Oh dear, you have died.");
				} else if (loop == 3) {
					player.reset();
					player.setNextWorldTile(new WorldTile(2602, 4775, 0));
					player.setNextAnimation(new Animation(-1));
					stop();
				}
				loop++;
			}
		}, 0, 1);
		return false;
	}


	@Override
	public boolean login() {
		player.getPackets().sendGameMessage("Your logged into a random event.");
		return false;
	}

	@Override
	public boolean logout() {
		return false;
	}

	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		player.getPackets().sendGameMessage("You can't teleport in this area!");
		return false;
	}

	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		player.getPackets().sendGameMessage("You can't teleport in this area!");
		return false;
	}

	@Override
	public boolean processObjectClick1(WorldObject object) {
		if (object.getId() == 15645)
			return true;
		return false;
	}

}