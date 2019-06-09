package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.utils.Utils;

public class PkerCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 15174 };
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		if (Utils.getRandom(4) == 0) {
			switch (Utils.getRandom(6)) {
			case 0:
				npc.setNextForceTalk(new ForceTalk("Gl Mate"));
				break;
			case 1:
				npc.setNextForceTalk(new ForceTalk("Stop safing!"));
				break;
			case 2:
				npc.setNextForceTalk(new ForceTalk("Awwwww"));
				break;
			case 3:
				npc.setNextForceTalk(new ForceTalk("Gf?"));
				break;
			case 4:
				npc.setNextForceTalk(new ForceTalk("Bring it on!"));
				break;
			case 5:
				npc.setNextForceTalk(new ForceTalk("Food left?"));
				break;
			case 6:
				npc.setNextForceTalk(new ForceTalk("Pray off noob!"));
				break;
			}
		}
		if (Utils.getRandom(3) == 0) {
			npc.setNextAnimation(new Animation(829));
			npc.heal(460);
		}
		if (Utils.getRandom(2) == 0) {
			npc.setNextAnimation(new Animation(1062));
			npc.setNextGraphics(new Graphics(252, 0, 100));
			npc.playSound(2537, 1);
			for (Entity t : npc.getPossibleTargets()) {
				delayHit(npc, 1, target, getMeleeHit(npc, getRandomMaxHit(npc, 345, NPCCombatDefinitions.MELEE, target)), getMeleeHit(npc, getRandomMaxHit(npc, 345, NPCCombatDefinitions.MELEE, target)));
			}
		} else { // Melee - Whip Attack
			npc.setNextAnimation(new Animation(defs.getAttackEmote()));
			delayHit(npc, 0, target, getMeleeHit(npc, getRandomMaxHit(npc, defs.getMaxHit(), NPCCombatDefinitions.MELEE, target)));
		}
		return defs.getAttackDelay();
	}
}