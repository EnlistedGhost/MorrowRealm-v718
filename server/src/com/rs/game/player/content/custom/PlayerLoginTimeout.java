package com.rs.game.player.content.custom;

import java.util.Calendar;

import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class PlayerLoginTimeout {

	public static int resetPlayerTimeout = 0;

	private transient Player player;
	
	public PlayerLoginTimeout(Player player) {
		this.player = player;
	}
	  
	public void startCountdown() {
		WorldTasksManager.schedule(new WorldTask() {
				
			int timer;
				
			@Override
			public void run() {
				if (timer < (5 * 60)) {
					if (timer == (4 * 60)) {
						player.sendMessage("<col=FF3333>Login Timeout:</col>You will be disconnected in 1 minute of further innactivity.");
					}
				}else if (timer == (5 * 60) && player.getRights() == 0) {
					//disconnect player
					player.forceLogout();
					player.sendMessage("<col=FF3333>Login Timeout:</col>You have been logged out! Well normally you would have been lol.");
				}
				if (resetPlayerTimeout > 0) {
					resetPlayerTimeout = 0;
					timer = 0;
				}
				timer++;
			}
		}, 0, 1);
	}
	  
	public void PlayerCheckIn() {
			startCountdown();
	}

	public static void PlayerTimeoutReset() {
		resetPlayerTimeout++;
	}
}