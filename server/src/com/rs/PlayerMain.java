package com.rs;

import com.rs.utils.Utils;

public class PlayerMain {
	
	private static String[] latestNews = { 
		"Welcome to MorrowRealm! This game is still a prototype, but feel free to explore!",
		"Remeber to update your highscores by typing ::hs!",
		"Donate to help keep the server alive and help pay for advertisements!", 
		"You can ::prestige once you get all combat skills to 99!",
		"Official Website and Wiki coming soon! Be sure to check in often!",
		"View your achievements by clicking Account Manager on the quest tab!",
		"Forums coming soon! Remember to check back often!"
	};
	
	public static String getLatestNews() {
		return latestNews[Utils.random(latestNews.length - 1)];
	}
	
}