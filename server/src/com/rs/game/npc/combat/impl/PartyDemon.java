package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.Hit.HitLook;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class PartyDemon extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 15581 };
	}

	public static int MAX_HIT = 500;
	
	@Override
	public int attack(final NPC npc, final Entity target) {
		npc.setName("Oblivion");
		npc.setCombatLevel(1000);
		int random = Utils.random(5000);
		
		if (npc.getHitpoints() < 10000) {
			if (random <= 500) {
				Entity randomTarget = npc.getPossibleTargets().get(Utils.random(npc.getPossibleTargets().size() - 1));
				Player selected = selectTarget(randomTarget);
				if (selected != null) {
					npc.setNextForceTalk(new ForceTalk("Now burn in hell "+selected.getDisplayName()+"!"));
					startSpecialAttack(npc, randomTarget);
				}
			}
		}
		
		if (random <= 1000) {
			npc.getCombat().removeTarget();
			npc.setNextGraphics(new Graphics(3197));
			npc.setNextForceTalk(new ForceTalk("Feel my wrath weaklings!"));
			for (Entity t : npc.getPossibleTargets()) {
				t.setNextGraphics(new Graphics(3002));
				t.applyHit(new Hit(npc, Utils.random(500), HitLook.REGULAR_DAMAGE));
			}
		} else if (random <= 5000) { // Default Attack
			npc.setNextAnimation(new Animation(16990));
			World.sendProjectile(npc, target, 3195, 34, 16, 40, 95, 16, 0);
			delayHit(npc, 3, target, getMagicHit(npc, getRandomMaxHit(npc, Utils.random(700), NPCCombatDefinitions.MAGE, target)));
		}
		return 4;
	}
	
	public Player selectTarget(Entity target) {
		WorldTile center = new WorldTile(target);
		for (Player player : World.getPlayers()) {
			if (player == null || player.isDead() || player.hasFinished())
				continue;
			if (player.withinDistance(center, 1)) {
				player.sendMessage("<col=FF0000>WARNING</col>: You have been targeted by Oblivion!");
				return player;
			}
		}
		return null;
	}
	
	public void startSpecialAttack(final NPC npc, final Entity target) {
		final WorldTile center = new WorldTile(target);
		World.sendGraphics(npc, new Graphics(2191), center);
		WorldTasksManager.schedule(new WorldTask() {
			int count = 0;
			@Override
			public void run() {
				for (Player player : World.getPlayers()) {
					if (player == null || player.isDead() || player.hasFinished())
						continue;
					if (player.withinDistance(center, 1)) {
						delayHit(npc, 0, player, new Hit(npc, Utils.random(300), HitLook.REGULAR_DAMAGE));
					}
				}
				if (count++ == 10) {
					stop();
					return;
				}
			}
		}, 0, 0);
	}
	
}
