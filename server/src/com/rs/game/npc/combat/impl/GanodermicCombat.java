package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.slayer.GanodermicBeast;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class GanodermicCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 14696, 14697 };
	}

	@Override
	public int attack(NPC n, Entity target) {
		GanodermicBeast npc = (GanodermicBeast) n;
		NPCCombatDefinitions defs = npc.getCombatDefinitions();
		Player player = (Player) target;
		if (player.withinDistance(npc, 3)) {
			delayHit(
					npc,
					1,
					target,
					getMagicHit(
							npc,
							getRandomMaxHit(npc, 400,
									NPCCombatDefinitions.MAGE, target)));
			npc.setNextAnimation(new Animation(15470));
			npc.setNextGraphics(new Graphics(2034));
			World.sendProjectile(npc, target, 2034, 10, 18, 50, 50, 0, 0);
			return defs.getAttackDelay();
		} else {
			delayHit(
					npc,
					1,
					target,
					getMagicHit(
							npc,
							getRandomMaxHit(npc, 400,
									NPCCombatDefinitions.MAGE, target)));
			npc.setNextAnimation(new Animation(15470));
			npc.setNextGraphics(new Graphics(2034));
			World.sendProjectile(npc, target, 2034, 10, 18, 50, 50, 0, 0);
		}
		return defs.getAttackDelay();
	}

}
