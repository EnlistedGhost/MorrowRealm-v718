package com.rs.game.player.content.mission;

import com.rs.game.Graphics;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class Entrance {
public static final WorldTile LOBBY= 
new WorldTile(1848,6344, 0);
	
	public static void useStairs(Player player) {
	player.sm("You climb up the stairs.");	
	player.teleportPlayer(2906, 3930, 1);
	}
	public static void useStairsDown(Player player) {
		player.sm("You climb up the stairs.");	
		player.teleportPlayer(2907, 3935, 0);
		}
	public static void initLobby(Player player) {
		player.setNextWorldTile(LOBBY);
		player.sm("You are in the lobby of missions, please pick a mission to begin.");
	}
	
	public static void ShipTeleport(final Player player, final int x, final int y, final int z) {
		player.setNextGraphics(new Graphics(3202));
		player.lock();
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.unlock();
				player.getInterfaceManager().closeChatBoxInterface();
				player.closeInterfaces();
				player.setNextWorldTile(new WorldTile(x, y, z));
			}
		}, 5);
	}

	public static void MissionTeleport(final Player player, final int x, final int y, final int z) {
		player.setNextGraphics(new Graphics(3202));
		player.lock();
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.unlock();
				player.sm("You have teleported.");
				player.setNextWorldTile(new WorldTile(x, y, z));
			}
		}, 2);
	}
}
