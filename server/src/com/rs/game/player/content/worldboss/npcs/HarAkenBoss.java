package com.rs.game.player.content.worldboss.npcs;

import java.util.ArrayList;
import java.util.List;

import com.rs.game.Entity;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;

@SuppressWarnings("serial")
public class HarAkenBoss extends NPC {

	private static List<Tentacles> tentacles;
	
	public HarAkenBoss(int id, WorldTile tile) {
		super(id, tile, -1, true, true);
		setForceMultiArea(true);
		getCombatDefinitions().setHitpoints(150000);
		setHitpoints(150000);
		tentacles = new ArrayList<Tentacles>();
	}
	
	public void removeTentacles() {
		for (Tentacles t : tentacles)
			t.finish();
		tentacles.clear();
	}

	public void removeTentacle(Tentacles tentacle) {
		tentacles.remove(tentacle);
	}
	
	@Override
	public ArrayList<Entity> getPossibleTargets() {
		ArrayList<Entity> possibleTarget = new ArrayList<Entity>(1);
		List<Integer> playerIndexes = World.getRegion(getRegionId()).getPlayerIndexes();
		if (playerIndexes != null) {
			for (int npcIndex : playerIndexes) {
				Player player = World.getPlayers().get(npcIndex);
				if (player == null || player.isDead() || player.hasFinished() || !player.isRunning())
					continue;
				possibleTarget.add(player);
			}
		}
		return possibleTarget;
	}
	
	

}
