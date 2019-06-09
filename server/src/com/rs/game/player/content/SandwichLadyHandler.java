package com.rs.game.player.content;

import com.rs.Settings;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;

/**
 * @author JazzyYaYaYa | Nexon | Fuzen Seth
 */
public class SandwichLadyHandler {
	
	public static int SnackScreen = 297; // <-- This is the construction skill board.
	public static final WorldTile WRONG_ANSWER_TELE = new WorldTile(3015,3196, 0);
	public static final WorldTile HOME_TELE = Settings.START_PLAYER_LOCATION;


	
	public static void CanLeave(Player player) {
		if (player.getInventory().containsItem(6961, 1)) {
			player.getControlerManager().forceStop();
			player.getInterfaceManager().sendInterfaces();
			player.setNextWorldTile(HOME_TELE);
			player.sm("Congratulations, you've completed Sandwich Lady event!");
			player.getInventory().addItem(14664, 1);
			player.getInventory().refresh();
			player.closeInterfaces();
			
		}
		else {
			player.closeInterfaces();
			player.sm("You haven't completed the event yet, talk to lady.");
		}
	}
	
	public static void handleButtons(Player player, int componentId) {
		if (componentId == 10) {
			player.getInventory().addItem(6961, 1);
			player.sm("You have picked baguette, you are free to go.");
			player.getInventory().refresh();
			player.closeInterfaces();
			player.getInterfaceManager().closeInterface(SnackScreen, SnackScreen);
		} else {
			if (player.getInventory().containsItem(6961, 1)) {
			player.sm("You have already picked a snack.");
			}
		}
		if (componentId == 18) {
			player.lock(5);
			player.getControlerManager().forceStop();
			player.getInterfaceManager().closeInterface(SnackScreen, SnackScreen);
			player.closeInterfaces();
			player.setNextFaceWorldTile(WRONG_ANSWER_TELE);
			player.sm("You have picked wrong snack, she'll force teleports you away.");
			} 
		if (componentId == 12) {
			player.closeInterfaces();
			player.lock(5);
			player.getControlerManager().forceStop();
			player.setNextFaceWorldTile(WRONG_ANSWER_TELE);
			player.sm("You have picked wrong snack, she'll force teleports you away.");
				} 
		if (componentId == 22) {
			player.closeInterfaces();
			player.getControlerManager().forceStop();
			player.setNextFaceWorldTile(WRONG_ANSWER_TELE);
			player.sm("You have picked wrong snack, she'll force teleports you away.");
				} 
		if (componentId == 14) {
			player.closeInterfaces();
			player.getControlerManager().forceStop();
			player.setNextFaceWorldTile(WRONG_ANSWER_TELE);
			player.sm("You have picked wrong snack, she'll force teleports you away.");
				} 
		if (componentId == 16) {
			player.closeInterfaces();
			player.getControlerManager().forceStop();
			player.setNextFaceWorldTile(WRONG_ANSWER_TELE);
			player.sm("You have picked wrong snack, she'll force teleports you away.");
				} 
		if (componentId == 20) {
			player.getControlerManager().forceStop();
			player.closeInterfaces();
			player.setNextFaceWorldTile(WRONG_ANSWER_TELE);
			player.sm("You have picked wrong snack, she'll force teleports you away.");
		} 
	}
	}	