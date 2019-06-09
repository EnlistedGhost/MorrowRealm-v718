package com.rs.game.player.content;

import java.util.HashMap;
import java.util.Map;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class Burying {

	public enum Bone {
		NORMAL(526, 150),

		BIG(532, 300),

		DRAGON(536, 430),

		OURG(4834, 420),

		FROST_DRAGON(18830, 500);

		private int id;
		private double experience;

		private static Map<Integer, Bone> bones = new HashMap<Integer, Bone>();

		static {
			for (Bone bone : Bone.values()) {
				bones.put(bone.getId(), bone);
			}
		}

		public static Bone forId(int id) {
			return bones.get(id);
		}

		private Bone(int id, double experience) {
			this.id = id;
			this.experience = experience;
		}

		public int getId() {
			return id;
		}

		public double getExperience() {
			return experience;
		}

		public static final Animation BURY_ANIMATION = new Animation(827);

		public static void bury(final Player player, int inventorySlot) {
			final Item item = player.getInventory().getItem(inventorySlot);
			if (item == null || Bone.forId(item.getId()) == null)
				return;
			if (player.getBoneDelay() > Utils.currentTimeMillis())
				return;
			final Bone bone = Bone.forId(item.getId());
			final ItemDefinitions itemDef = new ItemDefinitions(item.getId());
			player.addBoneDelay(3000);
			player.getPackets().sendSound(2738, 0, 1);
			player.setNextAnimation(new Animation(827));
			player.isBurying = true;
			player.getPackets().sendGameMessage(
					"You dig a hole in the ground...");
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					player.getPackets().sendGameMessage(
							"You bury the " + itemDef.getName().toLowerCase());
					player.getInventory().deleteItem(item.getId(), 1);
					player.getSkills().addXp(Skills.PRAYER,
							bone.getExperience());
					stop();
					player.isBurying = false;
				}

			}, 2);
		}
	}
}
