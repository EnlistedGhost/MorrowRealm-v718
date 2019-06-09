package com.rs.game.npc.combat.impl;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.alex.store.Index;
import com.rs.cache.Cache;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Region;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

/**
 * 
 * @author Jae
 * 
 * Handles custom boss, Sunfreet.
 *
 */

public class Sunfreet extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 15222 };
	}

/**
 *  CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                
            }
        }, 0, 10, TimeUnit.MINUTES);
 */
	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int attackStyle = Utils.random(0,6);
		int attackDelay = defs.getAttackDelay();
		switch (attackStyle) {
				
			case 0:
			case 1:
			case 2:
				npc.setNextAnimation(new Animation(defs.getAttackEmote()));
				delayHit(npc, 0, target, getMeleeHit(npc, getRandomMaxHit(npc, defs.getMaxHit(), NPCCombatDefinitions.MELEE, target)));
				attackDelay = 4;
				break;
				
			case 3:
			case 4:
				final Player p = (Player) target;
				npc.setNextAnimation(new Animation(16314));
				p.sendMessage("Sunfreet is readying his power attack!");
				CoresManager.fastExecutor.scheduleAtFixedRate(new TimerTask() {
					int count = 0;
					@Override
					public void run() {
						if (count == 5) {
							if (npc.isDead() ||	npc.hasFinished())
								this.cancel();
							if (target.withinDistance(npc, 3)) {
								target.applyHit(new Hit(npc, 375, HitLook.REGULAR_DAMAGE));
								p.sendMessage("You're hit hard by the Sunfreet's powerful attack!");
							}
							this.cancel();
						}
						count++;
					}
				}, 0, 1000);
				attackDelay = 10;
				break;
				
			case 5:
			case 6:
				attackDelay = 14;
				npc.setNextAnimation(new Animation(16318));
				for (Entity t : npc.getPossibleTargets()) {
					if (!t.withinDistance(npc, 18))
						continue;
					int damage = getRandomMaxHit(npc, defs.getMaxHit(), NPCCombatDefinitions.MELEE, target) + Utils.random(100);
					if (damage > 0) {
						WorldTasksManager.schedule(new WorldTask() {
							@Override
							public void run() {
								if (npc.isDead())
									this.stop();
								target.setNextGraphics(new Graphics (3002));
								target.setNextGraphics(new Graphics (3003));
								npc.setNextAnimation(new Animation(16317));
								target.applyHit(new Hit(npc, Utils.random(650) / 2, HitLook.MAGIC_DAMAGE));
								target.applyHit(new Hit(npc, Utils.random(350) / 2, HitLook.MAGIC_DAMAGE));
								npc.heal(200);
								npc.applyHit(new Hit(npc, 200, HitLook.HEALED_DAMAGE));
								World.sendProjectile(npc, target, 3004, 60, 32, 50, 50, 0, 0);
								delayHit(npc, 1, target, getMagicHit(npc, Utils.random(100)));
								delayHit(npc, 1, target, getMagicHit(npc, Utils.random(100)));
							}
						}, 7);
					}
				}
				break;
		}
		return attackDelay;
	}
}
