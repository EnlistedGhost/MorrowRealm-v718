package com.rs.game.player.content.custom;

import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.SpeakBotLogger;

public class SpeakBotInterface {
	  
	public static void startspeakCountdown() {
		WorldTasksManager.schedule(new WorldTask() {

			int timer;
				
			@Override
			public void run() {
				if (timer > 5) {
					SpeakBotLogger.loadSpeakBotStatus();
					timer =- 5;
				}
				timer++;
			}
		}, 0, 1);
	}
}