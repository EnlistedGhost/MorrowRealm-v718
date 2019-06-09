package com.rs.game.player.dialogues.impl;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class MiscTeleports extends Dialogue {

	private WorldTile donatorZone = new WorldTile(1807, 3211, 0);
	
	@Override
	public void start() {
		sendOptionsDialogue("Misc. Teleports", 
				"Donator Zone");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (componentId == OPTION_1) {
				if (player.getRights() == 0) {
					end();
					player.sendMessage("Only donators may access this area!");
					return;
				}
				sendOptionsDialogue("Which Teleport?", "Pegasus", "Demon");
				stage = 2;
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				end();
				startPegasusTeleport();
			}
			if (componentId == OPTION_2) {
				end();
				startDemonTeleport();
			}
		}
	}

	public void startPegasusTeleport() {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					player.setNextAnimation(new Animation(17106));
					player.setNextGraphics(new Graphics(3223));
				} else if (loop == 9) {
					player.setNextWorldTile(donatorZone);
				} else if (loop == 10) {
					player.setNextAnimation(new Animation(16386));
					player.setNextGraphics(new Graphics(3019));
				} else if (loop == 11) {
					player.setNextAnimation(new Animation(808));
					player.setNextGraphics(new Graphics(-1));
					stop();
				}
				loop++;
			}
		}, 0, 1);
	}
	 
	public void timedHintIcon(final int npcId) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop = 0;
			@Override
			public void run() {
				if (loop == 0) {
					player.getHintIconsManager().addHintIcon(World.getNpc(npcId), 0, -1, false);
				} else if (loop == 30) {
					player.getHintIconsManager().removeUnsavedHintIcon();
					stop();
				}
				loop++;
			}
		}, 0, 1);
	}
	
	public void startDemonTeleport() {
		WorldTasksManager.schedule(new WorldTask() {
			int loop = 0;
			@Override
			public void run() {
				if (loop == 0) {
					player.setNextAnimation(new Animation(17108));
					player.setNextGraphics(new Graphics(3224));
					player.setNextGraphics(new Graphics(3225));
				} else if (loop == 9) {
					player.setNextWorldTile(donatorZone);
				} else if (loop == 10) {
					player.setNextAnimation(new Animation(16386));
					player.setNextGraphics(new Graphics(3019));
				} else if (loop == 11) {
					player.setNextAnimation(new Animation(808));
					player.setNextGraphics(new Graphics(-1));
					stop();
				}
				loop++;
			}
		}, 0, 1);
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
