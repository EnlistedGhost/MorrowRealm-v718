package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.World;
import com.rs.game.Hit.HitLook;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class BlinkCombat extends CombatScript {
	// blue gfxs - 2854-2855
	// blue ranged - 2853
	// blue running magic - 2869

	// ouch sounds: 3005, 3006, 3010, 3014, 3048, 2978
	// 3017 he saw me
	// random 3004,
	// 3026, a face! A huuuge face.
	// 3042, a whole new world
	// 3046 theres no place like home
	// 3049 the spire doors everwhere
	// here it comes.. : 2989
	// KAPOW: 3002
	// Magicindaface:

	// Animations
	// 14946 - fall down and stand back up quickly
	// 14949 - ranged attack. Throw knives
	// 14956 - magic

	private static final Animation MELEE = new Animation(12310);
	private boolean specialRapidMagic, sprayAttack;

	private void attackSounds(NPC npc) {
		switch (Utils.random(5)) {
		case 0:
			npc.playSound(3004, 2);
			break;
		case 1:
			npc.setNextForceTalk(new ForceTalk("A face! A huuuge face!"));
			npc.playSound(3026, 2);
			break;
		case 2:
			npc.setNextForceTalk(new ForceTalk("A whole new world!"));
			npc.playSound(3042, 2);
			break;
		case 3:
			npc.setNextForceTalk(new ForceTalk("There's no place like home"));
			npc.playSound(3046, 2);
			break;
		case 4:
			npc.setNextForceTalk(new ForceTalk(
					"The...spire...doors...everywhere..."));
			npc.playSound(3049, 2);
			break;
		}
	}

	public void sprayAttack(final NPC npc, final Entity target) {
		npc.setNextForceTalk(new ForceTalk("Taste.... my... Spray!"));
		sprayAttack = true;
		WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
				npc.setNextGraphics(new Graphics(2869));
				for (Entity t : npc.getPossibleTargets()) {
					if (!t.withinDistance(npc, 3))
						continue;
					delayHit(npc, 1, t, new Hit(npc, Utils.random(100, 850),
							HitLook.MAGIC_DAMAGE));
					t.setNextGraphics(new Graphics(2855, 0, 0));
				}
				sprayAttack = false;
			}
		}, 3);

	}

	@Override
	public Object[] getKeys() {
		return new Object[] { 12878 };
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		int attackStyle = Utils.random(3);
		int distanceX = target.getX() - npc.getX();
		int distanceY = target.getY() - npc.getY();
		int size = npc.getSize();

		if (Utils.random(15) <= 5) // playing his random sounds
			attackSounds(npc);
		if (!specialRapidMagic && Utils.random(15) == 2 && !sprayAttack
				|| !specialRapidMagic && npc.getHitpoints() <= 990
				&& !sprayAttack) {
			npc.setNextForceTalk(new ForceTalk(
					(npc.getHitpoints() <= 990 ? "Ah! Grrr... Can't.. Stop me!"
							: "Aha!")));
			npc.setNextAnimation(new Animation(14956));
			npc.setNextGraphics(new Graphics(2854));
			specialRapidMagic = true;
			WorldTasksManager.schedule(new WorldTask() {
				int count = 0;

				@Override
				public void run() {
					npc.setNextAnimation(new Animation(14956));
					npc.setNextGraphics(new Graphics(2854));
					for (Entity t : npc.getPossibleTargets()) {
						if (!t.withinDistance(npc, 10))
							continue;
						delayHit(npc, 1, t, new Hit(npc, Utils.random(150),
								HitLook.MAGIC_DAMAGE));
						t.setNextGraphics(new Graphics(2855, 0, 0));
					}
					if (count++ >= (npc.getHitpoints() <= 990 ? 1500 : 4)
							|| npc.isDead()) {
						stop();
						specialRapidMagic = false;
						return;
					}
				}
			}, 0, 2);
		} else if (!sprayAttack && Utils.random(25) <= 2)
			sprayAttack(npc, target);

		/*
		 * / npc.setNextForceTalk(new ForceTalk( "H..here it comes!"));
		 * npc.playSound(2989, 2); WorldTasksManager.schedule(new WorldTask() {
		 * 
		 * @Override public void run() { npc.setNextForceTalk(new ForceTalk(
		 * "Kapow!")); npc.playSound(3002, 2); npc.setNextAnimation(new
		 * Animation(14956)); } }, 2);
		 */
		if (attackStyle == 2 && !specialRapidMagic && !sprayAttack) { // melee
			if (distanceX > size || distanceX < -1 || distanceY > size
					|| distanceY < -1)
				attackStyle = Utils.random(2); // set mage
			else {
				npc.setNextAnimation(MELEE);
				delayHit(
						npc,
						0,
						target,
						getMeleeHit(
								npc,
								getRandomMaxHit(npc, 350,
										NPCCombatDefinitions.MELEE, target)));
				return 4;
			}

		}
		if (attackStyle == 1 && !specialRapidMagic && !sprayAttack) { // range
			npc.setNextAnimation(new Animation(14949, 20));
			World.sendProjectile(npc, target, 2853, 18, 18, 50, 50, 0, 0);
			for (Entity t : npc.getPossibleTargets()) {
				if (!t.withinDistance(npc, 11))
					continue;
				World.sendProjectile(target, t, 2853, 18, 18, 50, 50, 0, 0);
				delayHit(
						npc,
						1,
						t,
						getRangeHit(
								npc,
								getRandomMaxHit(npc, 350,
										NPCCombatDefinitions.RANGE, t)));
			}
			if (Utils.random(2) == 0)
				delayHit(
						npc,
						1,
						target,
						getRangeHit(
								npc,
								getRandomMaxHit(npc, 100,
										NPCCombatDefinitions.RANGE, target)));

		} else if (attackStyle == 0 && !specialRapidMagic && !sprayAttack) {
			if (Utils.random(3) == 0)
				npc.setNextForceTalk(new ForceTalk("Magicinyaface!"));
			npc.setNextAnimation(new Animation(14956));
			npc.setNextGraphics(new Graphics(2854));
			for (Entity t : npc.getPossibleTargets()) {
				if (!t.withinDistance(npc, 11))
					continue;
				delayHit(
						npc,
						2,
						t,
						getMagicHit(
								npc,
								getRandomMaxHit(npc, 450,
										NPCCombatDefinitions.MAGE, t)));
				t.setNextGraphics(new Graphics(2855, 20, 0));
			}
		}
		return 5;
	}
}