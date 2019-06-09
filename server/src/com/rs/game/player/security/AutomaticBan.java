package com.rs.game.player.security;

import com.rs.Settings;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public class AutomaticBan {
	
	public void BanUser(Player player) {
		player.setPermBanned(true);
		player.getSession().getChannel().close();
		SerializableFilesManager.savePlayer(player);
		World.sendWorldMessage("A player has been detected from breaking "+Settings.SERVER_NAME+" rules.", true);
	}

	private static Player player;

	public void getJAG() {
		if (player.isSecured) {
			player.LockAccount();
		} else {
			BanUser(player);
		}

	}
}
