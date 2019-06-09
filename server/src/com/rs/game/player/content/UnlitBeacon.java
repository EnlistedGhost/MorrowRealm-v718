package com.rs.game.player.content;

import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

public class UnlitBeacon {

	public enum logstoOffer {
		
		NORMAL(1511, 5000, 50, 1), 
		OAK(1521, 7000, 75, 10), 
		WILLOW(1519, 9000, 100.5, 15), 
		MAPLE(1517, 11000, 150, 30), 
		YEW(1515, 13000, 200, 50), 
		MAGIC(1513, 15000, 275.5, 75);

		private int id, reward, level;
		private double experience;

		public static logstoOffer forId(int id) {
			for (logstoOffer offer : logstoOffer.values()) {
				if (offer.id == id) {
					return offer;
				}
			}
			return null;
		}

		logstoOffer(int id, int reward, double experience, int level) {
			this.id = id;
			this.reward = reward;
			this.experience = experience;
			this.level = level;
		}
		
		public int getReward() {
			return reward;
		}
		public int getId() {
			return id;
		}
		public int getLevel() {
			return level;
		}
		public double getExperience() {
			return experience;
		}

		public static boolean stopOffer = false;

		public static void RunBeacon(final Player player, final Item item) {
			final logstoOffer logs = logstoOffer.forId(item.getId());

			if (logs.getLevel() > player.getSkills().getLevelForXp(Skills.FIREMAKING)) {
				player.sendMessage("You need a Firemaking level of "+logs.getLevel()+" to burn "+Utils.formatString(logs.name())+" logs.");
				return;
			}
			
			WorldTasksManager.schedule(new WorldTask() {
				private int playerX = player.getX(), playerY = player.getY();
				public void run() {
					try {
						if (player.getX() != playerX || playerY != player.getY()) {
							stop();
							return;
						}
						if (!player.getInventory().containsItem(item.getId(), 1)) {
							stop();
							return;
						}
						player.getPackets().sendGameMessage("You throw a log in to the Unlit Beacon.");
						player.setNextAnimation(new Animation(835));
						int reward; reward = logs.getReward() + Utils.random(2000);
						player.getInventory().addItem(995, reward);
						player.getInventory().deleteItem(item.getId(), 1);
						double xp = logs.getExperience() * player.getAuraManager().getPrayerMultiplier();
						player.getSkills().addXp(Skills.FIREMAKING, xp);
						player.getInventory().refresh();
					} catch (Throwable e) {
						Logger.handle(e);
					}
				}
			}, 0, 3);
		}
	}
}
