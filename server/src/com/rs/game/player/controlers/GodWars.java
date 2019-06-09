package com.rs.game.player.controlers;

import com.rs.game.WorldObject;

public class GodWars extends Controler {

	@Override
	public void start() {
		setArguments(new Object[] { 0, 0, 0, 0, 0, 0 });
		sendInterfaces();
		player.setAtMultiArea(true);
	}

	@Override
	public boolean logout() {
		return false; // so doesnt remove script
	}

	@Override
	public boolean login() {
		sendInterfaces();
		return false; // so doesnt remove script
	}

	@Override
	public boolean processObjectClick1(final WorldObject object) {
		if (object.getId() == 57225) {
			player.getDialogueManager().startDialogue("NexEntrance");
			return false;
		}
		return true;
	}

	@Override
	public void sendInterfaces() {
		player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 34 : 8, getInterface());
		player.getPackets().sendIComponentText(601, 8, ""+player.getArmadylKills());
		player.getPackets().sendIComponentText(601, 9, ""+player.getBandosKills());
		player.getPackets().sendIComponentText(601, 10, ""+player.getSaradominKills());
		player.getPackets().sendIComponentText(601, 11, ""+player.getZamorakKills());
	}

	private int getInterface() {
		switch ((Integer) getArguments()[0]) {
		case 1: // zamorak area
			return 599;
		case 2:// zamorak boss area
			return 598;
		default:
			return 601;
		}
	}

	@Override
	public boolean sendDeath() {
		remove();
		removeControler();
		return true;
	}

	@Override
	public void magicTeleported(int type) {
		remove();
		removeControler();
	}

	@Override
	public void forceClose() {
		remove();
	}

	public void remove() {
		player.getPackets().closeInterface(
				player.getInterfaceManager().hasRezizableScreen() ? 34 : 8);
	}

}
