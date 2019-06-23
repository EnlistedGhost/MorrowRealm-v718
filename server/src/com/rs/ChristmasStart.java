package com.rs;

import java.util.concurrent.TimeUnit;

import com.rs.cores.CoresManager;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.utils.Utils.EntityDirection;

public class ChristmasStart {



		private static int[] boundChunks;
		
		static int sizeX = 7; // horizontal size
		static int sizeY = 7; // vertical size
	
		static int chunkX = 329; // bottom left chunk x
		static int chunkY = 704; // bottom left chunk y
		
		public static void startXmas() {
			CoresManager.slowExecutor.schedule(new Runnable() {
				@Override
				public void run() {
					boundChunks = RegionBuilder.findEmptyChunkBound(sizeX, sizeY); 
					RegionBuilder.copyAllPlanesMap(chunkX, chunkY, boundChunks[0], boundChunks[1], sizeX, sizeY); 
					
						
							/**
							 * Npcs
							 */
							World.spawnNPC(13642, new WorldTile(getWorldTile(23, 47)), -1, false, EntityDirection.SOUTH); // Snow Queen
							World.spawnNPC(8540, new WorldTile(getWorldTile(24, 46)), -1, false, EntityDirection.SOUTHWEST); // Santa
							World.spawnNPC(9412, new WorldTile(getWorldTile(18, 29)), -1, false, EntityDirection.EAST);
							World.spawnNPC(9414, new WorldTile(getWorldTile(18, 26)), -1, false, EntityDirection.EAST);
							World.spawnNPC(9422, new WorldTile(getWorldTile(22, 25)), -1, false, EntityDirection.WEST);
							World.spawnNPC(9416, new WorldTile(getWorldTile(24, 25)), -1, false, EntityDirection.EAST);
							World.spawnNPC(9420, new WorldTile(getWorldTile(24, 29)), -1, false, EntityDirection.EAST);
							World.spawnNPC(9418, new WorldTile(getWorldTile(28, 26)), -1, false, EntityDirection.WEST);
							/**
							 *  Objects
							 */
							
							World.removeObject(new WorldObject(47789, 10, 0, new WorldTile(getWorldTile(22, 25))), true);
							//World.removeObject(new WorldObject(47789, 10, 0, new WorldTile(2654, 5657, 0)), true);
							
							/**
							 * Frozen NPC Objects
							 */
							World.spawnObject(new WorldObject(47790, 10, 0, new WorldTile(2650, 5658, 0)), true);
							World.spawnObject(new WorldObject(47793, 10, 0, new WorldTile(2650, 5661, 0)), true);
							World.spawnObject(new WorldObject(47805, 10, 0, new WorldTile(2654, 5657, 0)), true);
							World.spawnObject(new WorldObject(47796, 10, 0, new WorldTile(2656, 5657, 0)), true);
							World.spawnObject(new WorldObject(47802, 10, 0, new WorldTile(2656, 5661, 0)), true);
							World.spawnObject(new WorldObject(47799, 10, 0, new WorldTile(2660, 5658, 0)), true);
							/**
							 * 
							 */
					};
			}, 3000, TimeUnit.MILLISECONDS);
		}
	
	public static WorldTile getWorldTile(int mapX, int mapY) {
		return new WorldTile(boundChunks[0] * 8 + mapX, boundChunks[1] * 8 + mapY, 0);
	}
}