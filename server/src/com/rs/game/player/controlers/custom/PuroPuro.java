package com.rs.game.player.controlers.custom;

import com.rs.Settings;
import com.rs.game.Animation;
import com.rs.game.WorldTile;
import com.rs.game.player.controlers.Controler;

public class PuroPuro extends Controler{

	public static final Animation CAPTURE_ANIMATION = new Animation(6606);

	@Override
	public void start() {
		if (player.getEquipment().getWeaponId() != 11259 && !player.getInventory().containsItem(11259, 1)) {
			player.getInventory().addItem(11259, 1);
			player.sendMessage("Butterfly net not detected. here have one! :D");
		}
		player.sm("You have teleported to Puro-Puro.");
		player.setNextWorldTile(new WorldTile(2591, 4320, 0));
	}
	
	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		leaveInstance();
		return true;
	}

	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		leaveInstance();
		return true;
	}

	@Override
	public boolean processObjectTeleport(WorldTile toTile) {
		leaveInstance();
		return true;
	}
	
	@Override
	public boolean logout() {
		leaveInstance();
		player.setLocation(new WorldTile(Settings.START_PLAYER_LOCATION));
		return false;
	}
	
	public void leaveInstance() {
		removeControler();
		player.sendMessage("You have left the Puro-Puro instance.");
		return;
	}
	
}
