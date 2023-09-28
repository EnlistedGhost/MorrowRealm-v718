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

public class WindMills {

public static String millplayerNAME = "";
public static int millhopperSTATUS = 0;
public static int milloutputSTATUS = 0;
public static String millLOCATION = "";

public static void loadMillStatus(final Player player) {
	String FILE_LOCATION = "data/windmills/WindmillStatus"+player.getDisplayName()+".txt";
		try {
			// Check for and create file if needed
			File millFILE = new File(FILE_LOCATION);
			millFILE.createNewFile(); // if file already exists will do nothing 
			// Fetch current player's display name
			String currentPLAYER = player.getDisplayName();
			// input the file content to the StringBuffer "input"
        	BufferedReader readMILLfile = new BufferedReader(new FileReader(FILE_LOCATION));
        	String line;
        	// Read the file and parse
        	line = readMILLfile.readLine();
        	if (line == null) {
        		// Create default handling status
        		readMILLfile.close();
        		millplayerNAME = "NOT_REGISTERED";// never milled before
        		System.out.println("------------INFO-002-MILL-LOG-(first run for: " + currentPLAYER + " )------------");
        		return;
        	}
        	// Define strings
        	String newLine = line.replace("\t\t", "\t");
			String[] split = newLine.split("\t");
			// Check for player's name and status of milling
			String tab = "\t";
        	System.out.println("------------START-MILL-LOG-(loading)------------");
        	System.out.println("Created/Accessed Milling status for: "+player.getDisplayName()+"");
        	System.out.println("File and location:" + FILE_LOCATION + "\n" + "Data Output: " + split[0] + tab + split[1] + tab + split[2] + tab + split[3]);
        	System.out.println("------------STOP-MILL-LOG-(loading)------------");
			// Set parsed data
			millplayerNAME = split[0];
			millhopperSTATUS = Integer.parseInt(split[1]);
			milloutputSTATUS = Integer.parseInt(split[2]);
			millLOCATION = split[3];
        	// Close previously opened file for reading
        	readMILLfile.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
}

public static boolean writeMillStatus(final Player player, String millPlayerName, int millHopperStatus, int millOutputStatus, String millLocation) {
	String FILE_LOCATION = "data/windmills/WindmillStatus"+player.getDisplayName()+".txt";	
		try {
			// Check for and create file if needed
			File millFILE = new File(FILE_LOCATION);
			millFILE.createNewFile(); // if file already exists will do nothing
			FileOutputStream millFILEoutput = new FileOutputStream(millFILE, false);
			// input the file content to the StringBuffer "input"
        	BufferedReader readMILLfile = new BufferedReader(new FileReader(FILE_LOCATION));
        	StringBuffer inputBuffer = new StringBuffer();
        	String line;
        	// Blank the file for inputing new data | TODO: remove pointless code, FileOutputStream millFILEoutput takes care of this blanking 
        	while ((line = readMILLfile.readLine()) != null) {
        		if(line.startsWith(millPlayerName)){
            		inputBuffer.append(line);
            		inputBuffer.append("");// or ''
        		}
        	}
        	// Close previously opened file for reading
        	readMILLfile.close();
        	// Write blanked data to file
        	String inputStr = inputBuffer.toString();
        	// display the new file for debugging
        	String tab = "\t";
        	System.out.println("------------START-MILL-LOG-(write to file)------------");
        	System.out.println("File and location:" + FILE_LOCATION + "\n" + "Data Input: " + millPlayerName + tab + millHopperStatus + tab + millOutputStatus + tab + millLocation);
        	System.out.println("------------STOP-MILL-LOG-(write to file)------------");
        	// write the new string with the replaced line OVER the same file
        	FileOutputStream fileOutMILL = new FileOutputStream(FILE_LOCATION);
        	fileOutMILL.write(inputStr.getBytes());
        	fileOutMILL.close();
        	// Write updated data to blanked file
			PrintWriter outMILL = new PrintWriter(new BufferedWriter(new FileWriter(FILE_LOCATION, true)));
		    outMILL.println(millPlayerName + tab + tab + millHopperStatus + tab + millOutputStatus + tab + millLocation);
		    outMILL.close();
		} catch (IOException e) {
		    System.err.println(e);
		    return false;
		}
		return true;
}

}