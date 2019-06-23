package com.rs.game.player.content.commands;

import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.quests.impl.HalloweenEvent;
import com.rs.game.player.content.TicketSystem;
import com.rs.game.RegionBuilder;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public class Administrator {

	public static boolean processCommand(Player player, String[] cmd, boolean console, boolean clientCommand) {
		String name;
		Player target;
		boolean loggedIn = true;

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

		if (cmd[0].equals("teleplayer")) {

			if (cmd.length < 4) {
					player.getPackets().sendGameMessage("Use: ;;teleplayer coordX coordY (Optional: coordZ) TargetPlayer");
					return true;
			}

			name = "";

			int fetchname = cmd.length > 4 ? 4 : 3;

			for (int i = fetchname; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			
			target = World.findPlayer(name);
			
			if (target == null || !World.isOnline(name)) {
				player.sendMessage("The player '"+name+"' can't be found.");
				return true;
			}

			target.getPackets().sendGameMessage("" + player.getDisplayName() + " is attempting to teleport you!");
			//int x = Integer.valueOf(2814);
			//int y = Integer.valueOf(3182);
			//int z = Integer.valueOf(0);
			int x = Integer.valueOf(cmd[1]);
			int y = Integer.valueOf(cmd[2]);
			int z = cmd.length > 4 ? Integer.valueOf(cmd[3]) : 0;
			target.resetWalkSteps();
			target.setNextWorldTile(new WorldTile(x, y, z));
			target.getPackets().sendGameMessage("" + player.getDisplayName() + " has teleported you somewhere else!");
			player.sendMessage("You have transported "+target.getDisplayName()+" somewhere else!");
			return true;
		}

		if (cmd[0].equals("teleto")) {
			if (player.isLocked() || player.getControlerManager().getControler() != null) {
				player.getPackets().sendGameMessage("You cannot tele anywhere from here.");
				return true;
			}
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			player.resetWalkSteps();
			player.setNextWorldTile(target);
			return true;
		}
		
		if (cmd[0].equals("teletome")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			if (target.isLocked() || target.getControlerManager().getControler() != null) {
				player.getPackets().sendGameMessage("You cannot teleport this player.");
				return true;
			}
			target.resetWalkSteps();
			target.setNextWorldTile(player);
			return true;
		}
		
		if (cmd[0].equals("object")) {
			try {
				if (cmd.length < 2) {
					player.sendMessage("Usage: ::object id (optional: face)");
					return true;
				}
				int id = Integer.parseInt(cmd[1]);
				int face = cmd.length < 3 ? 0 : Integer.parseInt(cmd[2]);
				World.spawnObject(new WorldObject(id, 10, face, player.getX(), player.getY(), player.getPlane()), true);
			} catch (NumberFormatException e) {
				player.getPackets().sendPanelBoxMessage("Use: object id");
			}
			return true;
		}
		
		if (cmd[0].equals("ticket")) {
			TicketSystem.answerTicket(player);
			return true;
		}
		
		if (cmd[0].equals("getosp")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			if (target == null) {
				return true;
			}
			player.sendMessage(""+target.getDisplayName()+" has "+target.getOsp()+" OSP.");
			SerializableFilesManager.savePlayer(target);
			return true;
		}
		
		if (cmd[0].equals("setosp")) {
			name = "";
			int amount = Integer.parseInt(cmd[1]);
			for (int i = 2; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null) {
				return true;
			}
			
			target.setOsp(amount);
			player.sendMessage(""+target.getDisplayName()+" now has "+target.getOsp()+" OSP.");
			SerializableFilesManager.savePlayer(target);
			return true;
		}
		
		if (cmd[0].equals("npc")) {
			try {
				World.spawnNPC(Integer.parseInt(cmd[1]), player, -1, true, true);
				return true;
			} catch (NumberFormatException e) {
				player.getPackets().sendPanelBoxMessage("Use: ::npc id(Integer)");
			}
			return true;
		}

		if (cmd[0].equals("killnpc")) {
			for (NPC n : World.getNPCs()) {
				if (n == null || n.getId() != Integer.parseInt(cmd[1])) {
					continue;
				}
				n.sendDeath(n);
			}
			return true;
		}
		
		if (cmd[0].equals("take")) {
			int itemid = Integer.parseInt(cmd[1]);
			int amount = Integer.parseInt(cmd[2]);
			if (itemid > Utils.getItemDefinitionsSize()) {
				player.sendMessage("item doesnt exist.");
				return true;
			}
			name = "";
			for (int i = 3; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.getPlayerByDisplayName(name);
			if (target == null) {
				target = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
				if (target != null) {
					target.setUsername(Utils.formatPlayerNameForProtocol(name));
				}
				loggedIn = false;
			}
			if (target == null) {
				return true;
			}
			if (target.getBank().getItem(itemid) != null) {
				target.getBank().removeItem(itemid, amount, true, true);
				player.sendMessage("Item Removed: "+new Item(itemid).getName()+" from "+target.getDisplayName()+"'s bank.");
			}
			if (target.getInventory().containsItem(itemid, amount)) {
				target.getInventory().deleteItem(itemid, amount);
				player.sendMessage("Item Removed: "+new Item(itemid).getName()+" from "+target.getDisplayName()+"'s inventory.");
			}
			if (target.getEquipment().getItems().contains(new Item(itemid))) {
				target.getEquipment().deleteItem(itemid, amount);
				player.sendMessage("Item Removed: "+new Item(itemid).getName()+" from "+target.getDisplayName()+"'s equipment.");
			}
			target.getAppearence().generateAppearenceData();
			return true;
		}
		
		
		if (cmd[0].equals("tradeban")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.getPlayerByDisplayName(name);
			if (target == null) {
				target = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
				if (target != null) {
					target.setUsername(Utils.formatPlayerNameForProtocol(name));
				}
				loggedIn = false;
			}
			if (target == null) {
				return true;
			}
			if (target.getUsername() == player.getUsername()) {
				player.sendMessage("<col=FF0000>You can't trade lock yourself!");
				return true;
			}
			target.setTradeLock();
			SerializableFilesManager.savePlayer(target);
			player.getPackets().sendGameMessage(""+target.getDisplayName()+"'s trade status is now "+(target.isTradeLocked() ? "locked" : "unlocked")+".", true);
			
			if (loggedIn) {
				target.getPackets().sendGameMessage("Your trade status has been set to: "+(target.isTradeLocked() ? "locked" : "unlocked")+".", true);
			}
			return true;
		}
		
		if (cmd[0].equals("getvp")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.getPlayerByDisplayName(name);
			if (target == null) {
				target = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
				if (target != null) {
					target.setUsername(Utils.formatPlayerNameForProtocol(name));
				}
				loggedIn = false;
			}
			if (target == null) {
				return true;
			}
			SerializableFilesManager.savePlayer(target);
			player.getPackets().sendGameMessage(""+target.getDisplayName()+" has "+target.getVotePoints()+" vote points.", true);
			return true;
		}
		
		if (cmd[0].equals("getpvp")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.getPlayerByDisplayName(name);
			if (target == null) {
				target = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
				if (target != null) {
					target.setUsername(Utils.formatPlayerNameForProtocol(name));
				}
				loggedIn = false;
			}
			if (target == null) {
				return true;
			}
			SerializableFilesManager.savePlayer(target);
			player.getPackets().sendGameMessage(target.getDisplayName()+" has "+target.getPvpPoints()+" pvp points.", true);
			return true;
		}
		
		if (cmd[0].equals("getip")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.getPlayerByDisplayName(name);
			if (target == null) {
				target = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
				if (target != null) {
					target.setUsername(Utils.formatPlayerNameForProtocol(name));
				}
				loggedIn = false;
			}
			if (target == null) {
				return true;
			}
			SerializableFilesManager.savePlayer(target);
			player.getPackets().sendPanelBoxMessage("<col=ff0000>"+target.getDisplayName()+"</col> ip address is <col=FF0000>"+target.getSession().getIP()+"</col>");
			return true;
		}
		
		if (cmd[0].equals("resetkdr")) {
			player.setKillCount(0);
			player.setDeathCount(0);
			return true;
		}
		
		if (cmd[0].equals("killme")) {
			player.applyHit(new Hit(player, player.getHitpoints(), HitLook.REGULAR_DAMAGE));
			return true;
		}
		
		if (cmd[0].equals("setskill")) {
			if (!player.getUsername().toLowerCase().equals("feraten")) {
				return true;
			}
			int skillId = Skills.getSkillId(cmd[1]);
			int level = Integer.parseInt(cmd[2]);
			
			if (skillId == -1) {
				player.sendMessage("Incorrect Skill Name '"+cmd[1]+"'");
				return true;
			}
			if (level > 99 && skillId < 24) {
				level = 99;
			}
			player.getSkills().set(skillId, level);
			player.getSkills().setXp(skillId, Skills.getXPForLevel(level));
			player.sendMessage("Your "+Skills.SKILLS[skillId]+" is now level "+level+"!");
			return true;
		}
		
		if (cmd[0].equals("staffmeeting")) {
			for (Player staff : World.getPlayers()) {
				if (staff.getRights() != 1 && staff.getRights() != 2) {
					continue;
				}
				staff.getControlerManager().forceStop();
				staff.getInterfaceManager().sendInterfaces();
				staff.setNextWorldTile(player);
				staff.getPackets().sendGameMessage("You been teleported for a staff meeting by " + player.getDisplayName());
			}
			return true;
		}

		if (cmd[0].equals("regionbuilder")) {
			int chunkX1, chunkY1, chunkX2, chunkY2;
			try
			{
                      	/** These values should be the RegionX, RegionY coordinates of your location, NOT your actual X and Y position.*/
                      	/** The command format is ;;regionbuilder regionx1 regiony1 regionx2 regiony2 */
                      	/** Head to the northeast corner of the region you want to instance, grab the regionx and regiony, then go to the
                        	*   southwest corner, grab the regionx and regiony, and use those 4 values in the command.
                       	*/
				chunkX1 = Integer.parseInt(cmd[1]);
				chunkY1 = Integer.parseInt(cmd[2]);
				chunkX2 = Integer.parseInt(cmd[3]);
				chunkY2 = Integer.parseInt(cmd[4]);		
					
				if(Math.abs(chunkX1 - chunkX2) != Math.abs(chunkY1 - chunkY2))
				{
					player.getPackets().sendGameMessage("Your region must be a square.");
					return false;
				}
					
				int dimension = Math.abs(chunkX1 - chunkX2);
					
				int mapChunks[] = RegionBuilder.findEmptyChunkBound(dimension, dimension);
				
				RegionBuilder.copyAllPlanesMap(chunkX1, chunkY1, mapChunks[0],
						mapChunks[1], dimension);
				RegionBuilder.copyAllPlanesMap(chunkX2, chunkY2, mapChunks[0],
						mapChunks[1], dimension);
                         /** You will be placed at the most south-western tile in the region you grabbed. 
                          *   With that knowledge you can determine your offset that you'd like to add for player start locations.
                          */
				player.setNextWorldTile(new WorldTile(mapChunks[0] * 8,
						mapChunks[1] * 8, 0));
			}
			catch(NumberFormatException e)
			{
				player.getPackets().sendGameMessage("Enter integer valued arguments.");
				return false;
			}
			return true;
		}

		if (cmd[0].equals("halloweenevent")) {
			HalloweenEvent.startEvent();
			return true;
		}
		
		return false;
	}
}
