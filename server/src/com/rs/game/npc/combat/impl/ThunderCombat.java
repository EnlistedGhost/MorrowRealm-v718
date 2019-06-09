package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.utils.Utils;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class ThunderCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 11872 };
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		
		int random = Utils.getRandom(4);
		
		if (random == 0) {
			switch (Utils.getRandom(3)) {
				case 0:
				npc.setNextForceTalk(new ForceTalk("RAAAAAARRRRRRGGGGHHHH"));
				break;
				
				case 1:
				npc.setNextForceTalk(new ForceTalk("You're going straight to hell!"));
				break;
				
				case 2:
				String name = "";
				if (target instanceof Player)
					name = ((Player) target).getDisplayName();
				npc.setNextForceTalk(new ForceTalk("I'm going to crush you, " + name));
				break;
				
				case 3:
				name = "";
				if (target instanceof Player)
					name = ((Player) target).getDisplayName();
				npc.setNextForceTalk(new ForceTalk("Die with pain, " + name));
				break;
			}
		}
		
		random = Utils.getRandom(10);
		
		if (random == 0) { // mage attack
			npc.setNextAnimation(new Animation(14525));
			npc.setNextForceTalk(new ForceTalk("Feel my wrath!!"));
			for (Entity t : npc.getPossibleTargets()) {
				if (!t.withinDistance(npc, 18))
					continue;
				((Player) t).getPackets().sendSound(1927, 0, 2);
				int damage = getRandomMaxHit(npc, defs.getMaxHit(), NPCCombatDefinitions.MAGE, t);
				if (damage > 0) {
					delayHit(npc, 0, t, getMagicHit(npc, damage));
					t.setNextGraphics(new Graphics(3428));
				}
			}
		} else if (random == 2) { // Fire Surge Attack
			npc.setNextAnimation(new Animation(14396));
			npc.setNextGraphics(new Graphics(2754));
			for (Entity t : npc.getPossibleTargets()) {
				if (!t.withinDistance(npc, 7))
					continue;
				npc.setNextForceTalk(new ForceTalk("You dare steal my power?!!"));
				((Player) t).getPackets().sendSound(1926, 0, 2);
				int damage = getRandomMaxHit(npc, defs.getMaxHit(), NPCCombatDefinitions.MAGE, t);
				if (damage > 0) {
					delayHit(npc, 2, t, getMagicHit(npc, damage));
				}
			}
		}else if (random == 1) { // True Power Attack
			WorldTasksManager.schedule(new WorldTask() {
				int loop = 0;
				@Override
				public void run() {
					if (loop == 0) {
						for (Entity t : npc.getPossibleTargets()) {
							if (!t.withinDistance(npc, 100))
								continue;
							((Player) t).getPackets().sendSound(1929, 0, 2);
						}
					} else if (loop == 3) {
						npc.setNextAnimation(new Animation(14412));
						npc.setNextGraphics(new Graphics(2776));
					} else if (loop == 4) {
						for (Entity t : npc.getPossibleTargets()) {
							if (!t.withinDistance(npc, 100))
								continue;
							if (!isAtSafeArea((Player) t)) {
								((Player) t).getPackets().sendSound(1936, 0, 2);
								((Player) t).getPackets().sendCameraShake(3, 25, 50, 25, 50);
								if (Utils.random(100) < 10) { // 10% chance of 1 hitting
									delayHit(npc, 0, t, getRegularHit(npc, ((Player) t).getHitpoints()));
								} else {
									delayHit(npc, 0, t, getRegularHit(npc, 400 + Utils.random(100)));
								}
							} else {
								((Player) t).getPackets().sendSound(1936, 0, 2);
								((Player) t).getPackets().sendCameraShake(3, 25, 50, 25, 50);
							}
						}
					} else if (loop == 6) {
						for (Entity t : npc.getPossibleTargets()) {
							if (!t.withinDistance(npc, 100))
								continue;
							((Player) t).getPackets().sendStopCameraShake();
						}
						stop();
					}
					
					loop++;
				}
			}, 0, 1);
			return 20;
		} else {// melee attack
			npc.setNextAnimation(new Animation(defs.getAttackEmote()));
			delayHit(npc, 0, target, getMeleeHit(npc, getRandomMaxHit(npc, defs.getMaxHit(), NPCCombatDefinitions.MELEE, target)));
		}
		
		return defs.getAttackDelay();
	}
	
	public boolean isAtSafeArea(Player player) {
		if (player.getX() == 209 && player.getY() == 5385) {
			return true;
		}
		if (player.getX() == 209 && player.getY() == 5382) {
			return true;
		}
		if (player.getX() == 222 && player.getY() == 5385) {
			return true;
		}
		if (player.getX() == 222 && player.getY() == 5382) {
			return true;
		}
		
		return false;
	}
	public void startTruePower(final NPC npc) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		WorldTasksManager.schedule(new WorldTask() {
			int loop = 0;
			@Override
			public void run() {
				if (loop == 0) {
					for (Entity t : npc.getPossibleTargets()) {
						if (!t.withinDistance(npc, 18))
							continue;
						((Player) t).getPackets().sendSound(1929, 2, 0);
						((Player) t).getPackets().sendCameraShake(3, 25, 50, 25, 50);
					}
				} else if (loop == 3) {
					npc.setNextAnimation(new Animation(14412));
					npc.setNextGraphics(new Graphics(2776));
					for (Entity t : npc.getPossibleTargets()) {
						if (!t.withinDistance(npc, 18))
							continue;
						((Player) t).getPackets().sendSound(1936, 2, 0);
						((Player) t).getPackets().sendStopCameraShake();
					}
				} else if (loop == 5) {
					stop();
				}
				
				loop++;
			}
		}, 0, 1);
	}
	
}