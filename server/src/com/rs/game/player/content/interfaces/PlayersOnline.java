package com.rs.game.player.content.interfaces;

import com.rs.Settings;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.game.player.content.custom.RightsManager;
import com.rs.game.player.content.custom.TitleHandler;

public class PlayersOnline {

	public static void openPlayersOnline(Player player) {
		player.getInterfaceManager().purgeMenu(275);
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendIComponentText(275, 1, "Players Online");
		int worldSize = World.getPlayers().size();
		player.getPackets().sendIComponentText(275, 10, "Players Currently Online: "+worldSize+"");
		int i = 0;
		for (Player p : World.getPlayers()) {
			i++; 
			player.getPackets().sendIComponentText(275, (i+11), p.getDisplayInfo());
		}
	}
	
	public static String getRank(Player player) {
		int crownId = -1;
		if (player.getRights() == 1)
			crownId = 0;
		if (player.getRights() == 2)
			crownId = 1;
		if (player.getRights() == 4)
			crownId = 4;
		if (player.getRights() == 5)
			crownId = 5;
		if (player.getRights() == 6)
			crownId = 6;
		if (player.getRights() == 7) {
			crownId = 1;
			return "<img="+crownId+">"+"<img="+crownId+">";
		}
		return "<img="+crownId+">";
	}
	
}
