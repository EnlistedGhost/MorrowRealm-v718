package com.rs.game.player.content;

import com.rs.game.player.Player;
import com.rs.game.player.content.commands.Administrator;
import com.rs.game.player.content.commands.Donator;
import com.rs.game.player.content.commands.Moderator;
import com.rs.game.player.content.commands.Owner;
import com.rs.game.player.content.commands.Regular;
import com.rs.game.player.content.commands.WikiEditor;

public final class Commands {

	public static boolean processCommands(Player player, String command, boolean console, boolean clientCommand) {
		if (command.length() == 0)
			return false;
		String[] cmd = command.toLowerCase().split(" ");
		if (cmd.length == 0) 
			return false;
		
		if (player.isOwner()) {
			if (Owner.processCommand(player, cmd, console, clientCommand)) {
				return true;
			}
		}
		
		if (player.isOwner() || player.isAdmin()) {
			if (Administrator.processCommand(player, cmd, console, clientCommand)) {
				return true;
			}
		}
		
		if (player.isOwner() || player.isAdmin() || player.isModerator()) {
			if (Moderator.processCommand(player, cmd, console, clientCommand)) {
				return true;
			}
		}
		
		if (player.isOwner() || player.isAdmin() || player.isDonor()) {
			if (Donator.processCommand(player, cmd, console, clientCommand)) {
				return true;
			}
		}
		
		if (player.isOwner() || player.isAdmin() || player.isWikiEditor()){
			if (WikiEditor.processCommand(player, cmd, console, clientCommand)) {
				return true;
			}
		}
			
		return Regular.processCommand(player, cmd, console, clientCommand);
	}
	
	private Commands() {

	}
}