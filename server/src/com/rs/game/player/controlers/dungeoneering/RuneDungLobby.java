package com.rs.game.player.controlers.dungeoneering;

import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.controlers.Controler;

/**
 * 
 * @author Tyluur <itstyluur@gmail.com>
 * @since 2013-01-02
 */
public class RuneDungLobby extends Controler {

	private int lobbyInterface = 46;

	
	@Override
	public void start() {
		player.lock();
		DungLobby.getLobby().enterLobby(getPlayer());
	}

	@Override
	public void process() {
		sendLobbyInfo();
	}

	@Override
	public void forceClose() {
		remove(false);
	}

	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		player.getPackets().sendGameMessage("You can't teleport out of the arena!");
		return false;
	}
	
	@Override
	public boolean processObjectClick1(WorldObject object) {
		player.sm("You can't do this right now.");
		return false;
	}

	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		player.getPackets().sendGameMessage("You can't teleport out of the arena!");
		return false;
	}

	@Override
	public boolean processObjectTeleport(WorldTile toTile) {
		player.getPackets().sendGameMessage("You can't teleport out of the arena!");
		return false;
	}
	
	private void remove(boolean logout) {
		DungLobby.getLobby().remove(getPlayer());
		if (logout)
			player.setLocation(new WorldTile(3450, 3718, 0));
		else {
			player.setNextWorldTile(new WorldTile(3450, 3718, 0));
		}
		super.removeControler();
	}
	
	@Override
	public boolean logout() {
		remove(true);
		return true;
	}

	private void sendLobbyInfo() {
		for (int i = 0; i < 16; i++) {
			player.getPackets().sendIComponentText(46, i, "");
		}
		player.getPackets().sendIComponentText(46, 15, "Game Lobby");
		player.getPackets().sendIComponentText(46, 14, "Waiting Size:");
		player.getPackets().sendIComponentText(46, 13, "Time:");
		player.getPackets().sendIComponentText(46, 11, "" + DungLobby.getLobby().getPlayerSize());
		player.getPackets().sendIComponentText(46, 13, "Time Till Game:");
		player.getPackets().sendIComponentText(46, 10, "" + (15 - DungLobby.getLobby().getTimer().getSeconds()));
		player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 11 : 27, lobbyInterface);
	}

}
