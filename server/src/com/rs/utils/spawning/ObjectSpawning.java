package com.rs.utils.spawning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.player.Player;

public class ObjectSpawning {
	
	private static String FILE_LOCATION = "data/map/CustomObjects.txt";
	
	public static void loadSpawns() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(FILE_LOCATION));
			while (true) {
				String line = in.readLine();
				
				if (line == null) {
					break;
				}
				
				if (line.startsWith("//")) {
					continue;
				}
				
				String newLine = line.replace("\t\t", "\t");
				String[] split = newLine.split("\t");
				
				int id = Integer.parseInt(split[0]);
				int x = Integer.parseInt(split[1]);
				int y = Integer.parseInt(split[2]);
				int z = Integer.parseInt(split[3]);
				int face = Integer.parseInt(split[4]);
				boolean clipped = Boolean.parseBoolean(split[5]);
				
				World.spawnObject(new WorldObject(id, 10, face, x, y, z), clipped);
			}
			in.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static boolean writeObject(Player player, int objectId, int x, int y, int z, int face, boolean clipped) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(FILE_LOCATION, true)));
			String tab = "\t";
			String objectName = ObjectDefinitions.getObjectDefinitions(objectId).name.toLowerCase();
		    out.println(objectId + tab + x + tab + y + tab + z + tab + face + tab + clipped + tab + objectName);
		    out.close();
		} catch (IOException e) {
			if (player != null) {
				player.sendMessage("Error writing file.");
			}
		    System.err.println(e);
		    return false;
		}
		return true;
	}

}
