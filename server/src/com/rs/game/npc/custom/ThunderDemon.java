package com.rs.game.npc.custom;

import com.rs.game.Hit;
import com.rs.game.WorldTile;
import com.rs.game.Hit.HitLook;
import com.rs.game.npc.NPC;

@SuppressWarnings("serial")
public class ThunderDemon extends NPC {

	public ThunderDemon(int id, WorldTile tile, int mapAreaNameHash, boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		getCombatDefinitions().setHitpoints(75000);
		setHitpoints(75000);
		setCapDamage(1000);
		setForceMultiArea(true);
		setForceMultiAttacked(true);
		setForceAgressive(false);
		setCombatLevel(5000);
	}
	
	@Override
	public double getMagePrayerMultiplier() {
		return 0.6;
	}

	@Override
	public double getRangePrayerMultiplier() {
		return 0.6;
	}

	@Override
	public double getMeleePrayerMultiplier() {
		return 0.6;
	}
	
	@Override
	public void handleIngoingHit(Hit hit) {
		if (hit.getLook() != HitLook.RANGE_DAMAGE)
			return;
		super.handleIngoingHit(hit);
		if (hit.getSource() != null) {
			int recoil = (int) (hit.getDamage() * 0.2);
			if (recoil > 0) {
				hit.getSource().applyHit(new Hit(this, recoil, HitLook.REFLECTED_DAMAGE));
			}
		}
	}
	
}
