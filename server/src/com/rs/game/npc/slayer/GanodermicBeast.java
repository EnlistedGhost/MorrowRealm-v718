package com.rs.game.npc.slayer;

import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

@SuppressWarnings("serial")
public class GanodermicBeast extends NPC {

	private boolean sprayed;

	public boolean isSprayed() {
		return sprayed;
	}

	public void setSprayed(boolean spray) {
		sprayed = spray;
	}

	public GanodermicBeast(int id, WorldTile tile, int mapAreaNameHash,
			boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
	}

	@Override
	public void handleIngoingHit(Hit hit) {
		int damage = hit.getDamage();
		if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
			if (damage > 0) {
				damage *= (int) 1.75;
			}
		}
		hit.setDamage(damage);
		super.handleIngoingHit(hit);
	}

	public void startSpray(Player player) {
		if (isSprayed()) {
			player.getPackets().sendGameMessage(
					"This NPC has already been sprayed with neem oil.");
			return;
		}
		NPC before = this;
		transformIntoNPC(14697);
		setSprayed(true);
		this.setHitpoints(before.getHitpoints());
		setNextForceTalk(new ForceTalk("Rarghh"));
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				if (isDead()) {
					stop();
				}
				transformIntoNPC(14696);
				setSprayed(false);
			}
		}, 120);
	}

	@Override
	public void sendDeath(Entity source) {
		transformIntoNPC(14696);
		setSprayed(false);
		super.sendDeath(source);
	}
}