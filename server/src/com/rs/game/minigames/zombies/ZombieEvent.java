package com.rs.game.minigames.zombies;

import java.util.TimerTask;
import java.util.Map.Entry;

import com.rs.Settings;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class ZombieEvent extends Zombies {

	public static void startInvasion() {
		CoresManager.fastExecutor.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (players.isEmpty()) {
					System.out.println("Playerlist empty in onslaught. Ending event.");
					killAllEvents();
					this.cancel();
					return;
				}
				ZombieEvent.startShake();
				if (npcList.size() < 50) {
					ZombieEvent.spawnNextWave();
				}
			}
		}, START_DELAY, SPAWN_DELAY); // 8 seconds
	}
	
	public static void spawnNextWave() {
		int x, y;
		wave++;
		wiseMan.setNextAnimation(new Animation(ANIM_ID));
		wiseMan.setNextForceTalk(new ForceTalk(Zombies.messages[Utils.random(Zombies.messages.length - 1)]));
		wiseMan.setNextGraphics(new Graphics(246));
			x = Utils.random(10) < 5 ? center.getX() + Utils.random(5) : center.getX() - Utils.random(5);
			y = Utils.random(10) < 5 ? center.getY() + Utils.random(5) : center.getY() - Utils.random(5);
		NPC npc = new NPC(npcId, wave, new WorldTile(x ,y, 0), -1, true, true);
		npc.setAtMultiArea(true);
		npc.setForceMultiArea(true);
		npc.setForceMultiAttacked(true);
		npc.setForceAgressive(true);
		npc.setNextGraphics(new Graphics(246));
		npc.setCombatLevel(100);
		npcList.put(wave, npc);
		npcList.get(wave).setName("Evil Minion #"+wave+"");
		sendSpawnSound();
		send("<col=FF0000>Wave "+wave+" inbound! There are "+npcList.size()+" zombies on the field!");
		wiseMan.faceEntity(npc);
	}
	
	public static boolean runEventCheck() {
		if (players.isEmpty()) {
			killAllEvents();
			return false;
		}
		return true;
	}
	
	public static void sendSpawnSound() {
		for (Entry<Integer, Player> plr : players.entrySet()) {
			Player pl = plr.getValue();
			if (pl == null)
				continue;
			pl.getPackets().sendSound(6, 0, 1);
		}
	}
	
	public static void startShake() {
		if (shakeStarted || Utils.random(1000) > 100)
			return;
		shakeStarted = true;
		wiseMan.setNextForceTalk(new ForceTalk("Let's end this here and now!"));
		Zombies.sendCameraShake();
		CoresManager.fastExecutor.schedule(new TimerTask() {
			int count = 0;
			@Override
			public void run() {
				if (count == 30) {
					sendResetCamera();
					count = 0;
					shakeStarted = false;
					this.cancel();
					return;
				}
				applyHits();
				count++;
			}
		}, 0, DISEASE_DELAY);
	}
	
}
