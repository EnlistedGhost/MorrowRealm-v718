package com.rs.game.player.actions.holidayevents.christmas2019;

import java.util.List;

import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Equipment;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.net.decoders.handlers.ObjectHandler;
import com.rs.utils.Utils;

/**
 * Handles the Christmas 2019 event for Snowmen
 * 
 * @author EnlistedGhost
 * 
 */
public class Snowman2019 {

public static boolean isSnwmn(int npcId) {
		if (npcId == 15941)
			return true;
		else
			return false;
	}

public static void checkSnwmn(Player player) {
		NPC snwmn = null;
		int lastDistance = -1;
		for (int regionId : player.getMapRegionsIds()) {
			List<Integer> npcIndexes = World.getRegion(regionId)
					.getNPCsIndexes();
			if (npcIndexes == null)
				continue;
			for (int npcIndex : npcIndexes) {
				NPC npc = World.getNPCs().get(npcIndex);
				if (npc == null)
					continue;
				if (!isSnwmn(npc.getId()) || npc.isUnderCombat()
						|| npc.isDead() || !npc.withinDistance(player, 4)
						|| !npc.clipedProjectile(player, true))
					continue;
				int distance = Utils.getDistance(npc.getX(), npc.getY(),
						player.getX(), player.getY());
				if (lastDistance == -1 || lastDistance > distance) {
					snwmn = npc;
					lastDistance = distance;
				}
			}
		}
		if (snwmn != null) {
			snwmn.setNextForceTalk(new ForceTalk(
					"Who dares disturb my snow pile!"));
			snwmn.setTarget(player);
		}
	}

public static void handleSnwmn(final Player player) {
		int posX = player.getX();
		int posY = player.getY();
		World.spawnNPC(15941, new WorldTile(posX-1, posY, 0), -1, true, true);
		World.spawnNPC(15941, new WorldTile(posX, posY-1, 0), -1, true, true);
		World.spawnNPC(15941, new WorldTile(posX-1, posY-1, 0), -1, true, true);
		if (player.getAttackedBy() != null
				&& player.getAttackedByDelay() > Utils.currentTimeMillis()) {
			player.getPackets().sendGameMessage(
					"The other snowmen are waiting to defend their pile!");
			return;
		}
		checkSnwmn(player);
}

}