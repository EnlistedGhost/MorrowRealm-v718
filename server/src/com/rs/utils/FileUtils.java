package com.rs.utils;

import java.io.File;

import com.rs.game.player.Player;

public class FileUtils {

	private static String SAVE_PATH = "data/playersaves/characters/";
	public static boolean fileExists(Player player) {
		File file = new File(SAVE_PATH + player.getUsername()+".p");
		return file.exists();
	}
	
	
	
}
