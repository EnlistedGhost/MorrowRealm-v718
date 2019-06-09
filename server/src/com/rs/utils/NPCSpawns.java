package com.rs.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.database.DatabasePool;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;

public final class NPCSpawns {
	
	public static void loadSpawns() {
		if (!new File("data/npcs/CustomSpawns.txt").exists())
			throw new RuntimeException("Couldn't find Spawn data");
		try {
			BufferedReader in = new BufferedReader(new FileReader("data/npcs/CustomSpawns.txt"));
			while (true) {
				String line = in.readLine();
				if (line == null) {
					break;
				}
				
				if (line.startsWith("//"))
					continue;
				
				line = line.replace("\t\t", "\t");
				String[] split = line.split("\t");
				
				int id = Integer.parseInt(split[0]);
				int x = Integer.parseInt(split[1]);
				int y = Integer.parseInt(split[2]);
				int plane = Integer.parseInt(split[3]);
				int hash = Integer.parseInt(split[4]);
				boolean canAttack = Boolean.parseBoolean(split[5]);
				NPCDefinitions defs = NPCDefinitions.getNPCDefinitions(id);
				if (defs.getName() == "null") {
					continue;
				}
				World.spawnNPC(id, new WorldTile(x, y, plane), hash, canAttack, false);
			}
			in.close();
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}
	
	public static boolean writeSpawn(Player player, int npcId, int x, int y, int z, int mapHash, boolean aooa) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("data/npcs/CustomSpawns.txt", true)));
			String tab = "\t", npcName = NPCDefinitions.getNPCDefinitions(npcId).getName();
		    out.println(npcId + tab + x + tab + y + tab + z + tab + mapHash + tab + aooa + tab + npcName);
		    out.close();
		} catch (IOException e) {
		    player.sendMessage("Error writing file.");
		    System.err.println(e);
		    return false;
		}
		return true;
	}
	
	private NPCSpawns() {
	}
}
