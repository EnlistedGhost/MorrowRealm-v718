package com.rs.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;

import com.rs.game.World;
import com.rs.utils.Utils;

import com.rs.game.player.Player;
import com.rs.game.World;
import com.rs.game.WorldTile;

public class RegistrationLogger {

public static void loadRegistrationStatus(final Player player) {//TODO Fix this shit
	String FILE_LOCATION = "data/speakbot/src/M_RealmReg.txt";
		try {
            // Check for and create file if needed
            File regFILE = new File(FILE_LOCATION);
            regFILE.createNewFile(); // if file already exists will do nothing
            // Define values
            String line;
            int line_count;
            int user_count;
            line_count = 1;
            user_count = 0;
            String space = ",";
            String userline = "";
            String[] split;
            // Read File
            BufferedReader readMESSAGEfile = new BufferedReader(new FileReader(FILE_LOCATION));
            line = readMESSAGEfile.readLine();
            // Check for player's name and status of reg
            System.out.println("------------START-REGISTRATION-LOG-(loading)------------");
            //System.out.println("Created/Accessed Registration status for: "+player.getDisplayName()+"");
            System.out.println("File and location:" + FILE_LOCATION + "\n");
            // Read the file and parse
            while (line != null) {
                line_count ++;
                userline = line.toLowerCase();
                // Create default handling status
                System.out.println("LineCount: " + line_count + " | " + "Data Output: " + line);
                if (userline.contains(player.getDisplayName().toLowerCase())) {
                    user_count ++;
                    split = line.split(",");
                    int regLiveStatus = Integer.parseInt(split[2]);
                    if (regLiveStatus > 0) {
                        player.sendMessage("<col=7289DA>Registration Status:</col> Complete!");
                    } else {
                        player.sendMessage("<col=7289DA>Registration Status:</col> Not finished, use <col=7289DA>;;confirmreg</col> to verify registartion.");
                    }
                }
                line = readMESSAGEfile.readLine();
            }
            if (user_count < 1) {
                //
                System.out.println("WARNING: User registration info was not found! Player: " + player.getDisplayName());
                player.sendMessage("<col=7289DA>Registration Status:</col> Not started, use <col=7289DA>" + player.getDisplayName() + " !register @speakbot</col> within Discord to begin.");
            }
            System.out.println("------------STOP-REGISTRATION-LOG-(loading)------------");
            // Set parsed data
            // Close previously opened file for reading
            readMESSAGEfile.close();
            //
            //FileOutputStream refreshFile = new FileOutputStream(regFILE, false);
            //refreshFile.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
}

public static boolean rewriteRegistrationStatus(final Player player, String regDisName) {
    String FILE_LOCATION = "data/speakbot/src/M_RealmReg.txt";
    String FILE_LOCATION_TEMP = "data/speakbot/src/M_RealmReg_Temp.txt";
    try {
            // Check for and create file if needed
            File regFILE = new File(FILE_LOCATION);
            regFILE.createNewFile(); // if file already exists will do nothing
            // Check for and create file if needed
            File regFILE_temp = new File(FILE_LOCATION_TEMP);
            regFILE_temp.createNewFile(); // if file already exists will do nothing
            // Define Values
            String line;
            String space = ",";
            String[] split;
            String[] regsplit;
            String userline = "";
            String checkline = "";
            String lwrUsername = "";
            int line_count;
            line_count = 1;
            //
            BufferedReader readMESSAGEfile = new BufferedReader(new FileReader(FILE_LOCATION));
            // Read Line
            line = readMESSAGEfile.readLine();
            regsplit = line.split(",");
            lwrUsername = player.getDisplayName();
            userline = lwrUsername.toLowerCase();
            checkline = regsplit[1].toLowerCase();
            // Check for player's name and status of reg
            System.out.println("------------START-REGISTRATION-LOG-(Rewrite)------------");
            //System.out.println("Created/Accessed Registration status for: "+player.getDisplayName()+"");
            System.out.println("File and location:" + FILE_LOCATION_TEMP);
            // Verify first line read
            if (checkline.contains(userline)) {
                split = line.split(",");
                //
                PrintWriter outREGISTRATION = new PrintWriter(new BufferedWriter(new FileWriter(FILE_LOCATION_TEMP, true)));
                outREGISTRATION.println(split[0] + space + split[1] + space + "1" + space + split[3]);
                outREGISTRATION.close();
                System.out.println("LineCount: " + line_count + " | " + "New Data Output: " + split[0] + space + split[1] + space + "1" + space + split[3]);
            }
            // Read the file and parse
            while (line != null) {
                line_count ++;
                // Define checks
                regsplit = line.split(",");
                userline = lwrUsername.toLowerCase();
                checkline = regsplit[1].toLowerCase();
                // Create default handling status
                System.out.println("LineCount: " + line_count + " | " + "Data Output: " + line);
                if (checkline.contains(userline)) {
                    split = line.split(",");
                    //
                    PrintWriter outREGISTRATION = new PrintWriter(new BufferedWriter(new FileWriter(FILE_LOCATION_TEMP, true)));
                    outREGISTRATION.println(split[0] + space + split[1] + space + "1" + space + split[3]);
                    outREGISTRATION.close();
                    System.out.println("LineCount: " + line_count + " | " + "New Data Output: " + split[0] + space + split[1] + space + "1" + space + split[3]);
                } else {
                    //
                    PrintWriter outREGISTRATION = new PrintWriter(new BufferedWriter(new FileWriter(FILE_LOCATION_TEMP, true)));
                    outREGISTRATION.println(line);
                    outREGISTRATION.close();
                }
                line = readMESSAGEfile.readLine();
            }
            // Close previously opened file for reading
            readMESSAGEfile.close();
            //
            System.out.println("------------STOP-REGISTRATION-LOG-(Rewrite)------------");
            //
            regFILE.delete();
            //
            regFILE_temp.renameTo(regFILE);
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
        return true;
}

public static boolean editRegistrationStatus(final Player player, String regDisName) {
    String FILE_LOCATION = "data/speakbot/src/M_RealmReg.txt";
        try {
            // Check for and create file if needed
            File regFILE = new File(FILE_LOCATION);
            regFILE.createNewFile(); // if file already exists will do nothing
            // Define values
            String line;
            int line_count;
            int user_count;
            int reged_count;
            String space = ",";
            String userline = "";
            String checkline = "";
            String unregedline = "";
            String disName = regDisName;
            String lwrUsername = "";
            String[] split;
            String[] regsplit;
            // Set values
            line_count = 1;
            user_count = 0;
            reged_count = 0;
            //
            BufferedReader readMESSAGEfile = new BufferedReader(new FileReader(FILE_LOCATION));
            // Read line
            line = readMESSAGEfile.readLine();
            regsplit = line.split(",");
            lwrUsername = player.getDisplayName();
            userline = lwrUsername.toLowerCase();
            checkline = regsplit[1].toLowerCase();
            // Check for player's name and status of reg
            System.out.println("------------START-REGISTRATION-LOG-(editing)------------");
            System.out.println("File and location:" + FILE_LOCATION);
            // Debugs for testing, comment out for live runs
            //System.out.println("Searching for:" + lwrUsername.toLowerCase());
            //System.out.println("Created/Accessed Registration status for: "+player.getDisplayName()+"");
            // Check first line read
            if (checkline.contains(userline)) {
                user_count ++;
                split = line.split(",");
                if (Integer.parseInt(split[2]) < 1) {
                    //
                    unregedline += split[3] + space;
                } else {
                    //
                    regsplit = line.split(",");
                    reged_count ++;
                }
            }
            // Read the file and parse
            while (line != null) {
                line_count ++;
                // Define checks
                regsplit = line.split(",");
                userline = lwrUsername.toLowerCase();
                checkline = regsplit[1].toLowerCase();
                // Create default handling status
                System.out.println("LineCount: " + line_count + " | " + "Data Output: " + line);
                System.out.println("Search for: " + userline + " | " + "Found: " + checkline);
                // Player found logic
                if (checkline.contains(userline)) {
                    user_count ++;
                    split = line.split(",");
                    if (Integer.parseInt(split[2]) < 1) {
                        //
                        unregedline += split[3] + space;
                    } else {
                        //
                        regsplit = line.split(",");
                        reged_count ++;
                    }
                }
                line = readMESSAGEfile.readLine();
            }
            // Close previously opened file for reading
            readMESSAGEfile.close();
            //
            if (user_count < 1) {
                //
                System.out.println("WARNING: User registration info was not found! Player: " + player.getDisplayName());
                player.sendMessage("<col=7289DA>Registration Status:</col> Not started, use <col=7289DA>" + player.getDisplayName() + " !register @speakbot</col> within Discord to begin.");
            }
            if (user_count > 1) {
                //
                if (reged_count > 0) {
                    //
                    player.sendMessage("<col=7289DA>Registration Status:</col> You have already registered this player to Discord user: <col=7289DA>" + regsplit[3] + "</col>");
                    player.sendMessage("<col=7289DA>Registration Status:</col> To unregister use <col=7289DA>;;removereg</col> here, or <col=7289DA>!unregister @speakbot</col> within discord.");
                } else if (disName == "DefaultUser") {
                    System.out.println("WARNING: Multiple registration info was found for Player: " + player.getDisplayName());
                    player.sendMessage("<col=7289DA>Registration Status:</col> Multiple registrations found, please use: <col=7289DA>!confirmreg DiscordUserName</col>");
                    player.sendMessage("<col=7289DA>Registration Status:</col> Available Discord Usernames: " + unregedline);
                } else {
                    if (Integer.parseInt(regsplit[2]) < 1) {
                        //
                        rewriteRegistrationStatus(player, regsplit[3]);
                    }
                }
            }
            if (user_count == 1) {
                if (Integer.parseInt(regsplit[2]) < 1) {
                    //
                    rewriteRegistrationStatus(player, regsplit[3]);
                } else {
                    //
                    player.sendMessage("<col=7289DA>Registration Status:</col> You have already registered this player to Discord user: <col=7289DA>" + regsplit[3] + "</col>");
                    player.sendMessage("<col=7289DA>Registration Status:</col> To unregister use <col=7289DA>;;removereg</col> here, or <col=7289DA>!unregister @speakbot</col> within discord.");
                }
            }
            //
            System.out.println("------------STOP-REGISTRATION-LOG-(editing)------------");
            //
            //FileOutputStream refreshFile = new FileOutputStream(regFILE, false);
            //refreshFile.close();
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
        return true;
}

public static boolean removeRegistrationStatus(String regID, String regPlayerName, int regStatus) {
    String FILE_LOCATION = "data/speakbot/src/M_RealmReg.txt";
    String FILE_LOCATION_TEMP = "data/speakbot/src/M_RealmReg_Temp.txt";
        try {
            // Check for and create file if needed
            File regFILE_temp = new File(FILE_LOCATION_TEMP);
            regFILE_temp.createNewFile(); // if file already exists will do nothing 
            // Check for and create file if needed
            File regFILE = new File(FILE_LOCATION);
            regFILE.createNewFile(); // if file already exists will do nothing
            //
            BufferedReader readMESSAGEfile = new BufferedReader(new FileReader(FILE_LOCATION));
            String line;
            int line_count;
            int user_count;
            String userline = "";
            // Define values
            line_count = 1;
            user_count = 0;
            line = readMESSAGEfile.readLine();
            // Check for player's name and status of reg
            System.out.println("------------START-REGISTRATION-LOG-(remove)------------");
            //System.out.println("Created/Accessed Registration status for: "+player.getDisplayName()+"");
            System.out.println("File and location:" + FILE_LOCATION);
            // Read the file and parse
            while (line != null) {
                line_count ++;
                // Create default handling status
                System.out.println("LineCount: " + line_count + " | " + "Data Output: " + line);
                String[] split = line.split(",");
                userline = line.toLowerCase();
                if (line.contains(regID) || userline.contains(regPlayerName.toLowerCase())) {
                    user_count ++;
                    // Do nothing, we don't wany to record the player so that its omitted
                } else {
                    // Write updated data to file
                    PrintWriter outREGISTRATION = new PrintWriter(new BufferedWriter(new FileWriter(FILE_LOCATION_TEMP, true)));
                    outREGISTRATION.println(line);
                    outREGISTRATION.close();
                }
                line = readMESSAGEfile.readLine();
            }
            if (user_count < 1) {
                //
                System.out.println("WARNING: User registration info was not found! Discord ID: " + regID);
            }
            System.out.println("------------STOP-REGISTRATION-LOG-(remove)------------");
            // Set parsed data
            // Close previously opened file for reading
            readMESSAGEfile.close();
            //
            regFILE.delete();
            //
            regFILE_temp.renameTo(regFILE);
            //
            //FileOutputStream refreshFile = new FileOutputStream(regFILE, false);
            //refreshFile.close();
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
        return true;
}

public static boolean writeRegistrationStatus(String regID, String regPlayerName, int regStatus, String regDisName) {
	String FILE_LOCATION = "data/speakbot/src/M_RealmReg.txt";
		try {
			// Check for and create file if needed
			File regFILE = new File(FILE_LOCATION);
			regFILE.createNewFile(); // if file already exists will do nothing
        	// display the new file for debugging
        	String tab = "\t";
            String space = ",";
        	System.out.println("------------START-REGISTRATION-LOG-(write to file)------------");
        	System.out.println("File and location:" + FILE_LOCATION + "\n" + "Data Input: " + regID + tab + regPlayerName + tab + regStatus);
        	System.out.println("------------STOP-REGISTRATION-LOG-(write to file)------------");
        	// Write updated data to file
			PrintWriter outREGISTRATION = new PrintWriter(new BufferedWriter(new FileWriter(FILE_LOCATION, true)));
		    outREGISTRATION.println(regID + space + regPlayerName + space + regStatus + space + regDisName);
		    outREGISTRATION.close();
		} catch (IOException e) {
		    System.err.println(e);
		    return false;
		}
		return true;
}

}