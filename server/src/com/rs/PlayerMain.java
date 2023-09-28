package com.rs;

import com.rs.utils.Utils;

public class PlayerMain {
	
	private static String[] latestNews = { 
		"Welcome to MorrowRealm! This game is still a prototype, but feel free to explore!",
		"Recently overhauled Fletching skill, be sure to try it out!",
		"Donate to help keep the server alive and help pay for advertisements!", 
		"Newly added BirdNest looting, Client zooming and more!",
		"Official Website and Wiki coming soon! Be sure to check in often!",
		"Be sure to check out the Christmas event in Lumbridge!",
		"Forums coming soon! Remember to check back often!",
		"Wishing you all a Merry Christmas and Happy Holidays!"
	};
	
	public static String getLatestNews() {
		return latestNews[Utils.random(latestNews.length - 1)];
	}
	
}
