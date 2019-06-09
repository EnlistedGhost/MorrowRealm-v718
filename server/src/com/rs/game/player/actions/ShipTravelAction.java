package com.rs.game.player.actions;

import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class ShipTravelAction {
public static int TRAVELINT = 776; //<-- Not identified

public static void TravelUser(final Player player, final int x, final int y, final int z) {
	
	player.lock(6);
	player.stopAll();
	player.getInterfaceManager().setWindowsPane(TRAVELINT);
	WorldTasksManager.schedule(new WorldTask() {
		@Override
		public void run() {
			player.unlock();
			player.setNextWorldTile(new WorldTile(x, y, z));
			player.closeInterfaces();
		
		}
	}, 2);
	player.getPackets().sendWindowsPane(
			player.getInterfaceManager().hasRezizableScreen() ? 746
					: 548, 0);
}
}
