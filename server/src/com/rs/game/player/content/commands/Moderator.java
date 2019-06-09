package com.rs.game.player.content.commands;

import com.rs.Settings;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.minigames.zombies.WiseOldMan;
import com.rs.game.player.Player;
import com.rs.game.player.content.TicketSystem;
import com.rs.game.player.controlers.custom.Dungeoneering;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public class Moderator {

	public static boolean processCommand(Player player, String[] cmd, boolean console, boolean clientCommand) {
		String name;
		Player target;

		/*if (cmd[0].equals("tele")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::tele coordX coordY (Optional: height)");
					return true;
				}
				try {
					int x = Integer.valueOf(cmd[1]);
					int y = Integer.valueOf(cmd[2]);
					int z = cmd.length > 3 ? Integer.valueOf(cmd[3]) : 0;
					player.resetWalkSteps();
					player.setNextWorldTile(new WorldTile(x, y, z));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::tele coordX coordY plane");
				}
			}*/
		
		/*if (clientCommand) {
			if (cmd[0].equals("tele")) {
				int x = Integer.valueOf(cmd[1]);
				int y = Integer.valueOf(cmd[2]);
				int z = Integer.valueOf(cmd[3]);
				player.setNextWorldTile(new WorldTile(x, y, z));
				return true;
			}
			return true;
		}*/
		
		if (cmd[0].equals("bank")) {
			if (player.isAtWorldBoss()) {
				player.sendMessage("You are not allowed to bank from here.");
				return true;
			}
			if (player.getControlerManager().getControler() instanceof Dungeoneering) {
				player.sm("<col=FF0000>You can not allowed to bank here.");
				return true;
			}
			if (player.getControlerManager().getControler() instanceof WiseOldMan) {
				player.sm("<col=FF0000>You can not allowed to bank here.");
				return true;
			}
			if (!player.canSpawn() && player.getRights() < 1 && player.getRights() > 2) {
				player.getPackets().sendGameMessage("You can't bank while you're in this area.");
				return true;
			}
			
			player.stopAll();
			player.getBank().openBank();
			return true;
		}
		
		if (cmd[0].equals("sethome")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			target.setLocation(Settings.START_PLAYER_LOCATION);
			SerializableFilesManager.savePlayer(target);
			player.sendMessage(""+target.getDisplayName()+"'s location has been set to home.");
			return true;
		}
		
		if (cmd[0].equals("checkbank")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			try {
				player.getPackets().sendItems(95, target.getBank().getContainerCopy());
				player.getBank().openPlayerBank(target);
			} catch (Exception e) {
				player.getPackets().sendGameMessage("The player " + name + " is currently unavailable.");
			}
			return true;
		}
		
		if (cmd[0].equals("checkinv")) {
			name = "";
			for (int i = 1; i < cmd.length; i++)
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			target = World.findPlayer(name);
			
			if (target == null) {
				return true;
			}
			
			player.getPackets().sendPanelBoxMessage("==============================");
			player.getPackets().sendPanelBoxMessage("Inventory of "+target.getDisplayName()+"");
			player.getPackets().sendPanelBoxMessage("==============================");
			
			for (int i = 0; i < 28; i++) {
				Item item = target.getInventory().getItem(i);
				if (item == null)
					continue;
				player.getPackets().sendPanelBoxMessage(""+item.getName()+" (Amount: "+item.getAmount()+")");
			}
			
			player.getPackets().sendPanelBoxMessage("==============================");
			return true;
		}
		
		if (cmd[0].equals("getmoney")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			int pouch = target.getMoneyPouch() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) target.getMoneyPouch();
			int inv = target.getInventory().getNumberOf(995);
			Item bank = target.getBank().getItem(995);
			int total;
			player.getPackets().sendPanelBoxMessage(target.getDisplayName()+" has <col=00FFFF>"+pouch+"</col> coins in their pouch.");
			player.getPackets().sendPanelBoxMessage(target.getDisplayName()+" has <col=00FFFF>"+inv+"</col> coins in their inventory.");
			
			if (bank != null) {
				total = pouch + inv + bank.getAmount();
				player.getPackets().sendPanelBoxMessage(target.getDisplayName()+" has <col=00FFFF>"+bank.getAmount()+"</col> coins in their bank.");
			} else {
				total = pouch + inv;
				player.getPackets().sendPanelBoxMessage(target.getDisplayName()+" has <col=00FFFF>0</col> coins in their bank.");
			}
			
			player.getPackets().sendGameMessage(""+target.getDisplayName()+" has a total of "+Utils.formatNumber(total)+" coins");
			SerializableFilesManager.savePlayer(target);
			return true;
		}
		
		if (cmd[0].equals("color")) {
			String color = cmd[1];
			player.getPackets().sendGameMessage("<col="+color+">Testing Color: "+color+"</col>");
			return true;
		}
		
		if (cmd[0].equals("crown")) {
			int crown = Integer.parseInt(cmd[1]);
			player.getPackets().sendGameMessage("Crown Id "+crown+": <img="+crown+">");
			return true;
		}
		
		if (cmd[0].equals("getuser")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			player.getPackets().sendPanelBoxMessage("Target's Log In Name: <col=FF0000>"+Utils.formatPlayerNameForDisplay(target.getUsername())+".");
			return true;
		}
		
		if (cmd[0].equals("mute")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			target.setMuted(Utils.currentTimeMillis() + (player.getRights() == 2 ? 172800000 : 3600000));
			if (World.isOnline(name))
				target.getPackets().sendGameMessage("You've been muted for "+ (player.getRights() == 2 ? "48 hours" : "1 hour")+".");
			player.getPackets().sendGameMessage("You have muted "+target.getDisplayName()+" for "+ (player.getRights() == 2 ? "48 hours" : "1 hour")+".");
			SerializableFilesManager.savePlayer(target);
			return true;
		}
		
		if (cmd[0].equals("unmute")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			target.setMuted(0);
			if (World.isOnline(name))
				target.getPackets().sendGameMessage("You've been unmuted by "+player.getDisplayName()+".");
			player.getPackets().sendGameMessage("You have umuted "+target.getDisplayName()+".");
			SerializableFilesManager.savePlayer(target);
			return true;
		}
		
		if (cmd[0].equals("jail")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			if (target.isAtExpertDung()) {
				player.getPackets().sendGameMessage("You cannot jail someone while they're in dung.");
				return true;
			}
			
			target.setJailed(Utils.currentTimeMillis() + (24 * 60 * 60 * 1000));
			
			if (World.getPlayerByDisplayName(name) == null) {
				target.getControlerManager().setControler("JailControler");
			} else {
				target.getControlerManager().startControler("JailControler");
			}
			if (World.isOnline(name))
				target.getPackets().sendGameMessage("You've been Jailed for 24 hours by " + player.getDisplayName() + ".");
			player.getPackets().sendGameMessage("You have Jailed 24 hours: " + target.getDisplayName() + ".");
			SerializableFilesManager.savePlayer(target);
			return true;
		}
		
		if (cmd[0].equals("kick")) {
			name = "";
			for (int i = 1; i < cmd.length; i++)
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			target = World.getPlayerByDisplayName(name);
			if (target == null) {
				player.getPackets().sendGameMessage(Utils.formatPlayerNameForDisplay(name) + " is not logged in.");
				return true;
			}
			target.getSession().getChannel().close();
			player.getPackets().sendGameMessage("You have kicked: " + target.getDisplayName() + ".");
			return true;
		}
		
		if (cmd[0].equals("fkick")) {
			name = "";
			for (int i = 1; i < cmd.length; i++)
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			
			target = World.getPlayerByDisplayName(name);
			
			if (target == null) {
				player.getPackets().sendGameMessage(Utils.formatPlayerNameForDisplay(name) + " is not logged in.");
				return true;
			}
			
			target.forceLogout();
			player.getPackets().sendGameMessage("You have kicked: " + target.getDisplayName() + ".");
			return true;
		}
		
		if (cmd[0].equals("unjail")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			target.setJailed(0);
			if (World.isOnline(name)) {
				target.getControlerManager().startControler("JailControler");
				target.getPackets().sendGameMessage("You've been unjailed by "+ Utils.formatPlayerNameForDisplay(player.getUsername()) + ".");
			}
			player.getPackets().sendGameMessage("You have unjailed: " + target.getDisplayName() + ".");
			SerializableFilesManager.savePlayer(target);
			return true;
		}
		
		if (cmd[0].equals("sendhome")) {
			name = "";
			for (int i = 1; i < cmd.length; i++)
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			target = World.getPlayerByDisplayName(name);
			if (target == null)
				player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
			else {
				if (target.isAtExpertDung()) {
					player.getPackets().sendGameMessage("You can not send this player home while they're in dungeoneering.");
					return true;
				}
				target.unlock();
				target.getControlerManager().forceStop();
				if (target.getNextWorldTile() == null)
					target.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
				player.getPackets().sendGameMessage("You have sent home: " + target.getDisplayName()+ ".");
			}
			return true;
		}
		
		if (cmd[0].equals("unban")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			if (target == null)
				return true;
			if (target.getBanned() < Utils.currentTimeMillis()) {
				player.sendMessage(""+target.getDisplayName()+" is not banned.");
				return true;
			}
			target.setBanned(0);
			player.getPackets().sendGameMessage("You have successfully unbanned "+Utils.formatString(name)+".");
			SerializableFilesManager.savePlayer(target);
			return true;
		}
		
		if (cmd[0].equals("ban")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			if (target == null)
				return true;
			if (target.isOwner()) {
				player.getPackets().sendGameMessage("You can't ban the owner.");
				return true;
			}
			if (target.getBanned() > Utils.currentTimeMillis()) {
				player.getPackets().sendGameMessage(""+Utils.formatString(name)+" is still banned for "+Utils.formatMs(target.getBanned() - Utils.currentTimeMillis())+" (h:m:s).");
				return true;
			}
			long banTime = player.isModerator() == true ? 86400000 : 172800000;
			target.setBanned(Utils.currentTimeMillis() + banTime);
			player.getPackets().sendGameMessage("You have successfully banned "+Utils.formatString(name)+" for "+Utils.formatMs(banTime)+" (h:m:s)");
			SerializableFilesManager.savePlayer(target);
			if (World.isOnline(name)) {
				target.getSession().getChannel().close();
			}
			return true;
		}
		
		if (cmd[0].equals("hide")) {
			if (player.getControlerManager().getControler() != null) {
				player.getPackets().sendGameMessage("You cannot hide in a public event!");
				return true;
			}
			player.getAppearence().switchHidden();
			player.getPackets().sendGameMessage("Hidden? " + player.getAppearence().isHidden());
			return true;
		}	
		return false;
	}

}
