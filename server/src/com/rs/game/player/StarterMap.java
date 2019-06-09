package com.rs.game.player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;



import com.rs.Settings;
import com.rs.utils.Logger;

/**
 * Info is saved to data/starters.ini
 * 
 * @author Emperial
 * 
 */
public class StarterMap {

	private static StarterMap INSTANCE = new StarterMap();

	public static StarterMap getSingleton() {
		return INSTANCE;
	}

	public static List<String> starters = new ArrayList<String>();
	private final static String path = "./data/starters.ini";
	private static File map = new File(path);

	public static void init() {
		try {
			Logger.log("StarterMap", "Loading Starters");
			BufferedReader reader = new BufferedReader(new FileReader(map));
			String s;
			while ((s = reader.readLine()) != null) {
				starters.add(s);
			}
			Logger.log("StarterMap", "Loaded Starter map, There are " + starters.size() + " IP's in Configuration");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private static void save() {
		BufferedWriter bf;
		try {
			clearMapFile();
			bf = new BufferedWriter(new FileWriter(path, true));
			for (String ip : starters) {
				bf.write(ip);
				bf.newLine();
			}
			bf.flush();
			bf.close();
		} catch (IOException e) {
			System.err.println("Error saving starter map!");
		}
	}

	public static void clearMapFile() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(map);
			writer.print("");
			writer.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static void removeIP(String ip) {
		if (starters.contains(ip))
			starters.remove(ip);
		save();
	}

	public void addIP(String ip) {
		if (getCount(ip) >= Settings.MAX_STARTER_AMOUNT)
			return;
		starters.add(ip);
		save();
	}

	public static int getCount(String ip) {
		int count = 0;
		for (String i : starters) {
			if (i.equals(ip))
				count++;
		}
		return count;
	}

}
