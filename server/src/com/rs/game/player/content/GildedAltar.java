package com.rs.game.player.content;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;
import com.rs.utils.Logger;

public class GildedAltar {

	public enum bonestoOffer {

		NORMAL(526, 200),
		BIG(532, 300),
		OURG(4834, 500),
		DRAGON(536, 520),
		FROST_DRAGON(18830, 750);

		private int id;
		private double experience;

		private static Map<Integer, bonestoOffer> bones = new HashMap<Integer, bonestoOffer>();

		static {
			for (bonestoOffer bone : bonestoOffer.values()) {
				bones.put(bone.getId(), bone);
			}
		}

		public static bonestoOffer forId(int id) {
			return bones.get(id);
		}

		private bonestoOffer(int id, double experience) {
			this.id = id;
			this.experience = experience;
		}

		public int getId() {
			return id;
		}

		public double getExperience() {
			return experience;
		}

		public static boolean stopOfferGod = false;

		public static void offerprayerGod(final Player player, Item item) {
			final int itemId = item.getId();
			final ItemDefinitions itemDef = new ItemDefinitions(item.getId());
			final bonestoOffer bone = bonestoOffer.forId(item.getId());
			WorldTasksManager.schedule(new WorldTask() {
				int x = player.getX();
				int y = player.getY();
				@Override 
				public void run() {
					try {
						if (x != player.getX() || y != player.getY()) {
							stop();
							return;
						}
						
						if (!player.getInventory().containsItem(itemId, 1)) {
							stop();
							return;
						}
						player.getPackets().sendGameMessage("The gods are very pleased with your offering.");
						player.setNextAnimation(new Animation(896));
						player.setNextGraphics(new Graphics(624));
						player.getInventory().deleteItem(new Item(itemId, 1));
						double xp = bone.getExperience() * player.getAuraManager().getPrayerMultiplier();
						player.getSkills().addXp(Skills.PRAYER, xp);
						player.getInventory().refresh();
					} catch (Throwable e) {
						Logger.handle(e);
					}
				}
			}, 0, 3);
		}
	}
}
