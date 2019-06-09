package com.rs.game.player.controlers.custom;

import com.rs.Settings;
import com.rs.game.Animation;
import com.rs.game.WorldTile;
import com.rs.game.player.controlers.Controler;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class SlayerControler extends Controler {

	@Override
	public void start() {
		player.setNextWorldTile(new WorldTile(2803, 10000, 0));
		player.sendMessage("Welcome to the new Slayer Dungeon, "+player.getDisplayName()+"!");
	}
	
	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		removeControler();
		return true;
	}

	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		removeControler();
		return true;
	}

	@Override
	public boolean processObjectTeleport(WorldTile toTile) {
		removeControler();
		return true;
	}
	
	@Override
	public boolean logout() {
		removeControler();
		player.setLocation(new WorldTile(Settings.START_PLAYER_LOCATION));
		return false;
	}
	
	@Override
	public boolean sendDeath() {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.setNextAnimation(new Animation(836));
				} else if (loop == 1) {
					player.getPackets().sendGameMessage("You are removed from the dungeon.");
				} else if (loop == 3) {
					player.getEquipment().init();
					player.getInventory().init();
					player.reset();
					player.setNextWorldTile(Settings.START_PLAYER_LOCATION);
					player.setNextAnimation(new Animation(-1));
				} else if (loop == 4) {
					removeControler();
					player.getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		}, 0, 1);
		return false;
	}

}
