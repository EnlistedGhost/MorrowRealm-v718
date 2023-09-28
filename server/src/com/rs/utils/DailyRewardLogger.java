package com.rs.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;

import com.rs.game.player.Player;

public class DailyRewardLogger {

public static String dailyRewardPlayerNAME = "";
public static int dailyRewardSTATUS = 0;

public static void loadDailyRewardStatus(final Player player) {
	String FILE_LOCATION = "data/dailyrewards/DailyRewards"+player.getDisplayName()+".txt";
		try {
			// Check for and create file if needed
			File rewardFILE = new File(FILE_LOCATION);
			rewardFILE.createNewFile(); // if file already exists will do nothing 
			// Fetch current player's display name
			String currentPLAYER = player.getDisplayName();
			// input the file content to the StringBuffer "input"
        	BufferedReader readREWARDfile = new BufferedReader(new FileReader(FILE_LOCATION));
        	String line;
        	// Read the file and parse
        	line = readREWARDfile.readLine();
        	if (line == null) {
        		// Create default handling status
        		readREWARDfile.close();
        		dailyRewardPlayerNAME = "NOT_REGISTERED";// never daily rewarded before
        		System.out.println("------------INFO-004-DAILYREWARD-LOG-(first run for: " + currentPLAYER + " )------------");
        		return;
        	}
        	// Define strings
        	String newLine = line.replace("\t\t", "\t");
			String[] split = newLine.split("\t");
			// Check for player's name and status of daily rewarding
			String tab = "\t";
        	System.out.println("------------START-DAILYREWARD-LOG-(loading)------------");
        	System.out.println("Created/Accessed DailyRewarding status for: "+player.getDisplayName()+"");
        	System.out.println("File and location:" + FILE_LOCATION + "\n" + "Data Output: " + split[0] + tab + split[1]);
        	System.out.println("------------STOP-DAILYREWARD-LOG-(loading)------------");
			// Set parsed data
			dailyRewardPlayerNAME = split[0];
			dailyRewardSTATUS = Integer.parseInt(split[1]);
        	// Close previously opened file for reading
        	readREWARDfile.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
}

public static boolean writeDailyRewardStatus(final Player player, String dailyrewardsPlayerName, int dailyrewardsStatus) {
	String FILE_LOCATION = "data/dailyrewards/DailyRewards"+player.getDisplayName()+".txt";
		try {
			// Check for and create file if needed
			File rewardFILE = new File(FILE_LOCATION);
			rewardFILE.createNewFile(); // if file already exists will do nothing
			FileOutputStream rewardFILEoutput = new FileOutputStream(rewardFILE, false);
			// input the file content to the StringBuffer "input"
        	BufferedReader readREWARDfile = new BufferedReader(new FileReader(FILE_LOCATION));
        	StringBuffer inputBuffer = new StringBuffer();
        	String line;
        	// Blank the file for inputing new data | TODO: remove pointless code, FileOutputStream rewardFILEoutput takes care of this blanking 
        	while ((line = readREWARDfile.readLine()) != null) {
        		if(line.startsWith(dailyrewardsPlayerName)){
            		inputBuffer.append(line);
            		inputBuffer.append("");// or ''
        		}
        	}
        	// Close previously opened file for reading
        	readREWARDfile.close();
        	// Write blanked data to file
        	String inputStr = inputBuffer.toString();
        	// display the new file for debugging
        	String tab = "\t";
        	System.out.println("------------START-DAILYREWARD-LOG-(write to file)------------");
        	System.out.println("File and location:" + FILE_LOCATION + "\n" + "Data Input: " + dailyrewardsPlayerName + tab + dailyrewardsStatus);
        	System.out.println("------------STOP-DAILYREWARD-LOG-(write to file)------------");
        	// write the new string with the replaced line OVER the same file
        	FileOutputStream fileOutDAILYREWARD = new FileOutputStream(FILE_LOCATION);
        	fileOutDAILYREWARD.write(inputStr.getBytes());
        	fileOutDAILYREWARD.close();
        	// Write updated data to blanked file
			PrintWriter outDAILYREWARD = new PrintWriter(new BufferedWriter(new FileWriter(FILE_LOCATION, true)));
		    outDAILYREWARD.println(dailyrewardsPlayerName + tab + tab + dailyrewardsStatus);
		    outDAILYREWARD.close();
		} catch (IOException e) {
		    System.err.println(e);
		    return false;
		}
		return true;
}

}