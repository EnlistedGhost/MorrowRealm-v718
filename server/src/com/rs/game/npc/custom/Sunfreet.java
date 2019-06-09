package com.rs.game.npc.custom;

import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;

@SuppressWarnings("serial")
public class Sunfreet extends NPC {

	public Sunfreet(int id, WorldTile tile, int mapAreaNameHash, boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		getCombatDefinitions().setHitpoints(5000);
		setHitpoints(5000);
		setCapDamage(250);
		setForceMultiArea(true);
		setForceMultiAttacked(true);
		setCombatLevel(530);
	}
	
}
