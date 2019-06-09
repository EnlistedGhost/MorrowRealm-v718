package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.content.Combat;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class KingBlackDragonCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 50 };
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int attackStyle = Utils.getRandom(5);
		int size = npc.getSize();

		if (attackStyle == 0) {
			int distanceX = target.getX() - npc.getX();
			int distanceY = target.getY() - npc.getY();
			if (distanceX > size || distanceX < -1 || distanceY > size
					|| distanceY < -1)
				attackStyle = Utils.getRandom(4) + 1;
			else {
				delayHit(
						npc,
						0,
						target,
						getMeleeHit(
								npc,
								getRandomMaxHit(npc, defs.getMaxHit(),
										NPCCombatDefinitions.MELEE, target)));
				npc.setNextAnimation(new Animation(defs.getAttackEmote()));
				return defs.getAttackDelay();
			}
		} else if (attackStyle == 1 || attackStyle == 2) {
			int damage = Utils.getRandom(650);
			final Player player = target instanceof Player ? (Player) target
					: null;
			npc.setNextAnimation(new Animation(17784));
			npc.setNextGraphics(new Graphics(3441, 0, 100));
			World.sendProjectile(npc, target, 3442, 60, 16, 65, 47, 16, 0);
			if (Combat.hasAntiDragProtection(target)
					|| (player != null && (player.getPrayer()
							.usingPrayer(0, 17) || player.getPrayer()
							.usingPrayer(1, 7))))
				damage = 0;
			if (player != null
					&& player.getFireImmune() > Utils.currentTimeMillis()) {
				if (damage != 0)
					damage = Utils.getRandom(164);
			} else if (damage == 0)
				damage = Utils.getRandom(164);
			else if (player != null) {
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						player.getPackets().sendGameMessage(
								"You are hit by the dragon's fiery breath!",
								true);
						player.setNextGraphics(new Graphics(3443, 50, 0));
					}
				}, 0);
				delayHit(npc, 2, target, getRegularHit(npc, damage));
			}
		} else if (attackStyle == 3) {
			int damage;
			final Player player = target instanceof Player ? (Player) target
					: null;
			npc.setNextAnimation(new Animation(17783));
			npc.setNextGraphics(new Graphics(3435, 0, 100));
			World.sendProjectile(npc, target, 3436, 60, 16, 65, 35, 16, 0);
			if (Combat.hasAntiDragProtection(target)) {
				damage = getRandomMaxHit(npc, 164, NPCCombatDefinitions.MAGE,
						target);
				if (player != null)
					player.getPackets()
							.sendGameMessage(
									"Your shield absorbs most of the dragon's poisonous breath!",
									true);
			} else if (player != null
					&& (player.getPrayer().usingPrayer(0, 17) || player
							.getPrayer().usingPrayer(1, 7))) {
				damage = getRandomMaxHit(npc, 164, NPCCombatDefinitions.MAGE,
						target);
				if (player != null)
					player.getPackets()
							.sendGameMessage(
									"Your prayer absorbs most of the dragon's poisonous breath!",
									true);

			} else {
				damage = Utils.getRandom(650);
				if (player != null) {
					WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
							player.getPackets()
									.sendGameMessage(
											"You are hit by the dragon's poisonous breath!",
											true);
							player.setNextGraphics(new Graphics(3437, 50, 0));
						}
					}, 0);
				}
			}
			if (Utils.getRandom(2) == 0)
				target.getPoison().makePoisoned(80);
			delayHit(npc, 2, target, getRegularHit(npc, damage));
		} else if (attackStyle == 4) {
			int damage;
			final Player player = target instanceof Player ? (Player) target
					: null;
			npc.setNextAnimation(new Animation(17784));
			npc.setNextGraphics(new Graphics(3438, 0, 100));
			World.sendProjectile(npc, target, 3439, 60, 16, 100, 45, 16, 0);
			if (Combat.hasAntiDragProtection(target)) {
				damage = getRandomMaxHit(npc, 164, NPCCombatDefinitions.MAGE,
						target);
				if (player != null)
					player.getPackets()
							.sendGameMessage(
									"Your shield absorbs most of the dragon's freezing breath!",
									true);
			} else if (player != null
					&& (player.getPrayer().usingPrayer(0, 17) || player
							.getPrayer().usingPrayer(1, 7))) {
				damage = getRandomMaxHit(npc, 164, NPCCombatDefinitions.MAGE,
						target);
				if (player != null)
					player.getPackets()
							.sendGameMessage(
									"Your prayer absorbs most of the dragon's freezing breath!",
									true);
			} else {
				damage = Utils.getRandom(650);
				if (player != null) {
					WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
							player.getPackets()
									.sendGameMessage(
											"You are hit by the dragon's freezing breath!",
											true);
							player.setNextGraphics(new Graphics(3440, 50, 0));
						}
					}, 1);
				}
			}
			if (Utils.getRandom(2) == 0)
				target.addFreezeDelay(15000);
			delayHit(npc, 2, target, getRegularHit(npc, damage));
		} else {
			int damage;
			final Player player = target instanceof Player ? (Player) target
					: null;
			npc.setNextAnimation(new Animation(17785));
			npc.setNextGraphics(new Graphics(3432, 0, 100));
			World.sendProjectile(npc, target, 3433, 60, 16, 120, 35, 16, 0);
			if (Combat.hasAntiDragProtection(target)) {
				damage = getRandomMaxHit(npc, 164, NPCCombatDefinitions.MAGE,
						target);
				if (player != null)
					player.getPackets()
							.sendGameMessage(
									"Your shield absorbs most of the dragon's shocking breath!",
									true);
			} else if (player != null
					&& (player.getPrayer().usingPrayer(0, 17) || player
							.getPrayer().usingPrayer(1, 7))) {
				damage = getRandomMaxHit(npc, 164, NPCCombatDefinitions.MAGE,
						target);
				if (player != null)
					player.getPackets()
							.sendGameMessage(
									"Your prayer absorbs most of the dragon's shocking breath!",
									true);
			} else {
				damage = Utils.getRandom(650);
				if (player != null) {
					WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
							player.getPackets()
									.sendGameMessage(
											"You are hit by the dragon's shocking breath!",
											true);
							player.setNextGraphics(new Graphics(3434, 25, 0));
						}
					}, 0);
				}
			}
			delayHit(npc, 2, target, getRegularHit(npc, damage));
		}
		return defs.getAttackDelay();
	}
}