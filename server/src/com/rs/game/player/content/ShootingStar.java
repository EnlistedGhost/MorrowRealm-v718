package com.rs.game.player.content;

import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;

public class ShootingStar {
	
	/**
	 * @author Jake | Santa Hat @Rune-Server
	 */
	
	/**
	 * The Player's Constructor
	 */
	Player player;
	
	public ShootingStar(Player player) {
		this.player = player;
	}
	
	/**
	 * Total Star Dust Mined
	 */
	public static short stardustMined;
	
	/**
	 * Used For Getting The Star's Stage.
	 */
	public static byte stage = 8;
	
	/**
	 * Increases The Star's Stage
	 */
	public static int starSize = 38661;
	
	/**
	 * Shooting star crash locations.
	 */
	public final static WorldTile[] LOCATION =  { 
		new WorldTile(3031, 3347, 0), // Behind Falador Bank
		new WorldTile(2974, 3238, 0), // Rimmington Mine
		new WorldTile(3245, 3509, 0) // Varrock Lodestone
		};
	
	/**
	 * Used To Save The Star's Location.
	 */
	private static WorldTile lastTile = LOCATION[Utils.random(0, 3)];
	
	/**
	 * Increases The X Position Of The Star.
	 */
	private int starX = lastTile.getX();
	
	/**
	 * Spawn Random Crashed Star
	 */
	public static void spawnRandomStar() {
		World.spawnObject(new WorldObject(38660, 10, 0 , lastTile), true);
		World.sendWorldMessage("<img=7><col=ff0000>News: A Shooting Star has just crashed!", false);
	}
	
	/**
	 * Check's If The Player Can Mine.
	 */
	public boolean mineCrashedStar() {
		if (player.getSkills().getLevel(Skills.MINING) < stage * 10) {
			player.getPackets().sendGameMessage("You need a mining level of at least 10 times the star's stage.");
			return false;
		} else {
			processMining();
		}
		return true;
	}
	
	/**
	 * Check's Who Was The First Person To Find The Crashed Star.
	 */
	public void checkIfFirst() {
		for (Player players : World.getPlayers()) {
			if (player.taggedStar == false) {
				player.getSkills().addXp(Skills.MINING, player.getSkills().getLevel(Skills.MINING) * 75);
				player.getPackets().sendGameMessage("Congratulations, You were the first to reach the shooting star!");
				players.taggedStar = true;
			}
		}
	}
	
	/**
	 * Increases The Crashed Star's Stage.
	 */
	public void processMining() {
			if (stardustMined == 33 || stardustMined == 50 || stardustMined == 68
					|| stardustMined == 86 || stardustMined == 116 || stardustMined == 147
						|| stardustMined == 160) {
				starSize++;
				stage--;
				World.spawnObject(new WorldObject(-1, 10, 0 , starX, lastTile.getY(), lastTile.getPlane()), true); // Deletes The Pre-Existing Star
				starX++;
				World.spawnObject(new WorldObject(starSize, 10, 0 , starX, lastTile.getY(), lastTile.getPlane()), true); // Spawns A New Star.
				return;
			} else if (stardustMined >= 200) { //Fully Mined
				starX++;
				World.spawnObject(new WorldObject(-1, 10, 0 , starX, lastTile.getY() + 9, lastTile.getPlane()), true); //Delete's The Last Star
				starSize = 38661; // Object ID
				stage = 8; // The Size Of The Star (Stage)
				player.stopAll();
				player.canTalkToSprite = true; // So Player's Can Only Get Reward Once.
				World.spawnNPC(8091, lastTile, -1, true, true);
				/*
				 * Resets The Tag
				 */
				for (Player players : World.getPlayers()) {
					players.taggedStar = false;
				}
				return;
			}
		}

}