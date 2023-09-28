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
import com.rs.utils.RegistrationLogger;

import com.rs.game.player.Player;
//import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;

import com.rs.utils.SerializableFilesManager;

public class SpeakBotLogger {

public static void loadSpeakBotStatus() {
	String FILE_LOCATION = "data/speakbot/src/M_RealmChat_IN.txt";
		try {
            Player target;
            String name;
			// Check for and create file if needed
			File speakbotFILE = new File(FILE_LOCATION);
			speakbotFILE.createNewFile(); // if file already exists will do nothing 
			// Fetch current player's display name
			//String currentPLAYER = player.getDisplayName();
			// input the file content to the StringBuffer "input"
        	BufferedReader readMESSAGEfile = new BufferedReader(new FileReader(FILE_LOCATION));
        	String line;
            int line_count;
        	// Define values
            line_count = 1;
        	line = readMESSAGEfile.readLine();
			// Check for player's name and status of speakbot
        	System.out.println("------------START-SPEAKBOT-LOG-(loading)------------");
        	//System.out.println("Created/Accessed SpeakBot status for: "+player.getDisplayName()+"");
            System.out.println("File and location:" + FILE_LOCATION + "\n");
            // Read the file and parse
            while (line != null) {
                line_count ++;
                // Create default handling status
                System.out.println("LineCount: " + line_count + " | " + "Data Output: " + line);
                String[] split = line.split(" ");
                if (line.startsWith("!fixplayer")) {
                    //3238, 3218, 0
                    name = "";
                    name += split[2];
                    target = World.findPlayer(name);
                    if (target == null || !World.isOnline(name)) {
                        return;
                    }
                    target.getPackets().sendGameMessage("Admin " + split[1] + " has sent a fix player command, you will be teleported momentarily.");
                    //int x = Integer.valueOf(2814);
                    //int y = Integer.valueOf(3182);
                    //int z = Integer.valueOf(0);
                    //int x = Integer.valueOf(cmd[1]);
                    //int y = Integer.valueOf(cmd[2]);
                    //int z = cmd.length > 4 ? Integer.valueOf(cmd[3]) : 0;
                    target.resetWalkSteps();
                    target.setNextWorldTile(new WorldTile(3238, 3218, 0));//Home
                    target.getPackets().sendGameMessage("Admin " + split[1] + " has teleported you to player default home location.");
                } else if (line.startsWith("!teleplayer")) {
                    name = "";
                    name += split[5];
                    target = World.findPlayer(name);
                    if (target == null || !World.isOnline(name)) {
                        return;
                    }
                    target.getPackets().sendGameMessage("Admin " + split[1] + " has sent a fix teleport command, you will be teleported momentarily.");
                    //int x = Integer.valueOf(2814);
                    //int y = Integer.valueOf(3182);
                    //int z = Integer.valueOf(0);
                    int x = Integer.valueOf(split[2]);
                    int y = Integer.valueOf(split[3]);
                    int z = Integer.valueOf(split[4]);
                    target.resetWalkSteps();
                    target.setNextWorldTile(new WorldTile(x, y, z));
                    target.getPackets().sendGameMessage("Admin " + split[1] + " has teleported you to: " + split[2] + ", " + split[3] + ", " + split[4]);
                } else if (line.startsWith("!regplayer")) {
                    //
                    int regLength = split.length; 
                    if (regLength == 4) {
                        RegistrationLogger.writeRegistrationStatus(split[3], split[2], 0, split[1]);
                    }
                } else if (line.startsWith("!unregplayer")) {
                    //
                    RegistrationLogger.removeRegistrationStatus(split[2], split[1], 0);
                } else if (line.startsWith("!tipplayer")) {
                    //
                } else if (line.startsWith("!banplayer")) {
                    //
                    name = "";
                    name += split[2];
                    target = World.findPlayer(name);
                    if (target == null) {
                        return;
                    }
                    if (target.isOwner()) {
                        //player.getPackets().sendGameMessage("You can't ban the owner.");
                        return;
                    }
                    if (target.getBanned() > Utils.currentTimeMillis()) {
                        //player.getPackets().sendGameMessage(""+Utils.formatString(name)+" is still banned for "+Utils.formatMs(target.getBanned() - Utils.currentTimeMillis())+" (h:m:s).");
                        return;
                    }
                    long banTime = target.isModerator() == true ? 86400000 : 172800000;
                    target.setBanned(Utils.currentTimeMillis() + banTime);
            //player.getPackets().sendGameMessage("You have successfully banned "+Utils.formatString(name)+" for "+Utils.formatMs(banTime)+" (h:m:s)");
                    SerializableFilesManager.savePlayer(target);
                    if (World.isOnline(name)) {
                        target.getSession().getChannel().close();
                    }
                } else if (line.startsWith("!kickplayer")) {
                    //
                    name = "";
                    name += split[2];
                    target = World.getPlayerByDisplayName(name);
                    if (target == null) {
                        return;
                    }
                    target.forceLogout();
            //player.getPackets().sendGameMessage("You have kicked: " + target.getDisplayName() + ".");
                } else if (line.startsWith("!muteplayer")) {
                    //
                    name = "";
                    name += split[2];
                    target = World.findPlayer(name);
                    if (target == null) {
                        return;
                    }
                    target.setMuted(Utils.currentTimeMillis() + (target.getRights() == 2 ? 172800000 : 3600000));
                    if (World.isOnline(name)) {
                        target.getPackets().sendGameMessage("You've been muted for "+ (target.getRights() == 2 ? "48 hours" : "1 hour")+".");
                    }
            //player.getPackets().sendGameMessage("You have muted "+target.getDisplayName()+" for "+ (player.getRights() == 2 ? "48 hours" : "1 hour")+".");
                    SerializableFilesManager.savePlayer(target);
                } else if (line.startsWith("!giveplayer")) {
                    //
                    name = "";
                    int itemId = Integer.parseInt(split[2]);
                    int amount = Integer.parseInt(split[3]);
                    name += split[4];
                    target = World.findPlayer(name);
                    if (target == null || !World.isOnline(name)) {
                        return;
                    }
                    Item item = new Item(itemId, amount);
                    target.getInventory().addItem(itemId, amount);
                    SerializableFilesManager.savePlayer(target);
                    target.getPackets().sendGameMessage("Admin " + split[1] + " has given you an item");
                } else {
                    World.sendWorldMessage("<col=7289DA>-Discord Yell-</col> "+ line, false);
                }
                line = readMESSAGEfile.readLine();
            }
        	System.out.println("------------STOP-SPEAKBOT-LOG-(loading)------------");
			// Set parsed data
        	// Close previously opened file for reading
        	readMESSAGEfile.close();
            FileOutputStream refreshFile = new FileOutputStream(speakbotFILE, false);
            refreshFile.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
}

public static boolean writeSpeakBotStatus(String speakbotPlayerName, String speakbotStatus, int speakbotFlag) {
	String FILE_LOCATION = "data/speakbot/src/M_RealmChat.txt";
		try {
			// Check for and create file if needed
			File speakbotFILE = new File(FILE_LOCATION);
			speakbotFILE.createNewFile(); // if file already exists will do nothing
        	// display the new file for debugging
        	String tab = "\t";
            String msgTYPE = "";
        	System.out.println("------------START-SPEAKBOT-LOG-(write to file)------------");
        	System.out.println("File and location:" + FILE_LOCATION + "\n" + "Data Input: " + speakbotPlayerName + tab + speakbotStatus);
        	System.out.println("------------STOP-SPEAKBOT-LOG-(write to file)------------");
            if (speakbotFlag == 0) {
                msgTYPE = "unread ! ";
            } else if (speakbotFlag == 1) {
                msgTYPE = "bugreport ! ";
            } else if (speakbotFlag == 2) {
                msgTYPE = "rlmlogin ! ";
            } else if (speakbotFlag == 3) {
                msgTYPE = "rlmlogout ! ";
            } else {
                msgTYPE = "rlmdown ! ";
            }
        	// Write updated data to file
			PrintWriter outSPEAKBOT = new PrintWriter(new BufferedWriter(new FileWriter(FILE_LOCATION, true)));
		    outSPEAKBOT.println(msgTYPE + speakbotPlayerName + " " + speakbotStatus + "\n");
		    outSPEAKBOT.close();
		} catch (IOException e) {
		    System.err.println(e);
		    return false;
		}
		return true;
}

}