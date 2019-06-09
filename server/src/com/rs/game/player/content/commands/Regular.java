package com.rs.game.player.content.commands;

import com.rs.Settings;
import com.rs.database.impl.DonationManager;
import com.rs.database.impl.Highscores;
import com.rs.database.impl.OnlineShop;
import com.rs.database.vote.RewardClaim;
import com.rs.database.vote.VoteChecker;
import com.rs.database.vote.VoteReward;
import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.custom.TitleHandler;
import com.rs.game.player.content.custom.TriviaBot;
import com.rs.game.player.content.custom.YellHandler;
import com.rs.game.player.content.interfaces.CommandsBook;
import com.rs.game.player.content.interfaces.PlayersOnline;
import com.rs.game.player.content.interfaces.PvmRewards;
import com.rs.game.player.content.interfaces.TitleMenuHandler;
import com.rs.game.player.content.interfaces.WelcomeBook;
import com.rs.utils.Encrypt;
import com.rs.utils.PkRank;
import com.rs.utils.Utils;

public class Regular {

	public static boolean processCommand(Player player, String[] cmd, boolean console, boolean clientCommand) {
		if (clientCommand)
			return true;
		
		if (cmd[0].equals("setemail")) {
			player.getAttributes().put("editingEmail", Boolean.TRUE); 
			player.getPackets().sendInputLongTextScript("Please enter your paypal email:"); 
			return true;
		}
		
		if (cmd[0].equals("boss")) {
			player.sendMessage("The world boss has been moved to the quest tab.");
			player.sendMessage("Quest Tab -> Bosses -> Last Page");
			return true;
		}
		
		if (cmd[0].equals("starter")) {
			if (player.getClassName() == "" || player.getClassName() == null) {
				player.getDialogueManager().startDialogue("NewStarter");
				return true;
			}
			player.sendMessage("You've already selected a starter!");
			return true;
		}

		if (cmd[0].equals("answer")) {
			String name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			TriviaBot.getInstance().verify(player, name);
		}
		
		if (cmd[0].equals("stopc")) {
			player.getControlerManager().forceStop();
			Magic.sendNormalTeleportSpell(player, 0, 0, Settings.START_PLAYER_LOCATION);
			player.getInterfaceManager().sendInterfaces();
			return true;
		}
		
		if (cmd[0].equals("checkpurchase")) {
			OnlineShop.checkPurchase(player);
			return true;
		}
		
		if (cmd[0].equals("checkpd")) {
			NPC npc = World.getNpc(15581);
			if (npc == null || npc.isDead() || npc.hasFinished()) {
				player.sendMessage("Can not locate the Party Demon! Is it dead?");
				return true;
			}
			player.sendMessage("Hitpoints remaining: <col=FF0000>"+Utils.formatNumber(npc.getHitpoints())+"</col> (Max: <col=FF0000>"+Utils.formatNumber(npc.getCombatDefinitions().getHitpoints())+"</col>)");
			return true;
		}
		
		if (cmd[0].equals("clearchat")) {
			for (int i = 0; i < 300; i++) {
				player.sendMessage("");
			}
			return true;
		}
		
		if (cmd[0].equals("home")) {
			if (player.isDead() || player.isLocked()) {
				player.sendMessage("You are unable to teleport home at this time.");
				return true;
			}
			Magic.sendNormalTeleportSpell(player, 0, 0, Settings.START_PLAYER_LOCATION);
			return true;
		}
		
		if (cmd[0].equals("hs")) {	
			if (Highscores.updateHighscores(player)) {
				player.setHsUpdate(Utils.currentTimeMillis() + (1000 * 60 * 5));
				player.sendMessage("Highscores updated!");
				System.err.println(""+player.getDisplayName()+" has updated their highscores.");
				return true;
			}
			return true;
		}
		
		if (cmd[0].equals("setpass")) {
			String newPass = "";
			for (int i = 1; i < cmd.length; i++) {
				newPass += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			if (newPass.length() > 15) {
				player.sendMessage("Password is too long. (Max Length: 15)");
				return true;
			}
			if (newPass.length() < 3) {
				player.sendMessage("Password is too short. (Minimum Length: 3)");
				return true;
			}
			player.setPassword(Encrypt.encryptSHA1(newPass));
			player.sendMessage("Your password has been set to: <col=FF0000>"+newPass+"</col> (Encrypted using SHA1)");
			return true;
		}
		
		if (cmd[0].equals("claimdonor")) {
			if (player.getInventory().getFreeSlots() < 28) {
				player.sendMessage("Please clear out your inventory before claiming a donation.");
				return true;
			}
			DonationManager.checkDonation(player);
			return true;
		}
		
		if (cmd[0].equals("coords")) {
			int x = player.getX();
			int y = player.getY();
			int z = player.getPlane();
			player.getPackets().sendGameMessage("Your coords: <col=00FFFF>"+x+", "+y+", "+z+"</col>");
			return true;
		}
		
		if (cmd[0].equals("select")) {
			TitleMenuHandler.launchMenu(player);
			return true;
		}
		
		if (cmd[0].equals("title")) {
			int titleId = Integer.parseInt(cmd[1]);
			TitleHandler.set(player, titleId);
			return true;
		}
		
		if (cmd[0].equals("titlelist")) {
			PvmRewards.openInterface(player);
			return true;
		}
		
		if (cmd[0].equals("rules")) {
			
			WelcomeBook.openRules(player);
			return true;
		}

		if (cmd[0].equals("prestige")) {
			if (player.getEquipment().wearingArmour()) {
				player.sendMessage("Please remove your equipment before prestiging.");
				return true;
			}
			player.getPrestige().prestige();
			return true;
		}

		if (cmd[0].equals("skull")) {
			if (player.getPrestigeLevel() == 0) {
				player.sendMessage("You must be atleast 1st Prestige to enable your skull.");
				return true;
			}
			player.setSkullEnabled(!player.isSkullEnabled());
			player.sendMessage("You have " + (player.isSkullEnabled() == true ? "<col=00FF00>ENABLED</col>" : "<col=FF0000>DISABLED</col>" ) + " your prestige skull!");
			player.getAppearence().generateAppearenceData();
			return true;
		}
		
		if (cmd[0].equals("myprestige")) {
			player.setNextForceTalk(new ForceTalk("<col=ff0000> I am currently " + player.getPrestigeLevel() + "" + player.getPrestige().getSuffix() + " prestige!"));
			return true;
		}

		if (cmd[0].equals("commands")) {
			CommandsBook.sendBook(player);
			return true;
		}

		if (cmd[0].equals("claimvote") || cmd[0].equals("checkvote") || cmd[0].equals("reward") || cmd[0].equals("claim")) {
			try {
				if (player.hasVoted()) {
					return true;
				}
				VoteReward reward = VoteChecker.checkVote(player);
				if (reward == null) {
					player.getPackets().sendGameMessage("You have no items waiting for you.");
					player.getPackets().sendGameMessage("If you feel there was an error, please contact King Fox.");
					return true;
				}
				RewardClaim.claimReward(player, reward);
			} catch (Exception e){
				player.getPackets().sendGameMessage("[GTL Vote] An SQL error has occured.");
			}
            return true;
		}

		if (cmd[0].equals("yell")) {
			if (player.getRights() == 0 && !player.isDicer()) {
				player.sendMessage("Only donators, staff, or dicers may yell.");
				return true;
			}
			if (player.getMuted() > Utils.currentTimeMillis()) {
				player.sendMessage("You are currently muted. Please check back later.");
				return true;
			}
			
			String message1 = "";
			
			for (int i = 1; i < cmd.length; i++)
				message1 += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			
			YellHandler.sendYell(player, Utils.fixChatMessage(message1));
			return true;
		}

		if (cmd[0].equals("empty")) {
			player.getInventory().reset();
			return true;
		}

		if (cmd[0].equals("pkranks")) {
			PkRank.showRanks(player);
			return true;
		}

		if (cmd[0].equals("kdr")) {
			double kill = player.getKillCount();
			double death = player.getDeathCount();
			double dr = kill / death;
			player.setNextForceTalk(new ForceTalk("<col=ff0000>I have " + player.getKillCount() + " kills and " + player.getDeathCount() + " deaths. KDR: " + dr));
			return true;
		}

		if (cmd[0].equals("players")) {
			PlayersOnline.openPlayersOnline(player);
			player.getPackets().sendPanelBoxMessage("There are currently " + World.getPlayers().size() + " players playing " + Settings.SERVER_NAME + ".");
			return true;
		}

		if (cmd[0].equals("help")) {
			player.getInventory().addItem(1856, 1);
			player.getPackets().sendGameMessage("You receive a guide book about " + Settings.SERVER_NAME+ ".");
			return true;
		}

		if (cmd[0].equals("lockxp")) {
			player.setXpLocked(!player.isXpLocked());
			player.getPackets().sendGameMessage("You have " + (player.isXpLocked() == true ? "Locked" : "Unlocked") + " your xp.");
			return true;
		}
		
		if (cmd[0].equals("lock")) {
			int skillId = Skills.getSkillId(cmd[1]);
			if (skillId == -1) {
				player.sendMessage("Incorrect Skill Name '"+cmd[1]+"'");
				return true;
			}
			player.getSkills().lockSkill(skillId);
			return true;
		}
		
		if (cmd[0].equals("unlock")) {
			int skillId = Skills.getSkillId(cmd[1]);
			if (skillId == -1) {
				player.sendMessage("Incorrect Skill Name '"+cmd[1]+"'");
				return true;
			}
			player.getSkills().unlockSkill(skillId);
			return true;
		}
		
		if (cmd[0].equals("list")) {
			String lockedSkills = "";
			for (int i = 0; i < player.getSkills().getLockedSkills().length; i++) {
				if (player.getSkills().isLocked(i)) {
					lockedSkills += ""+Skills.SKILLS[i]+", ";
				}
			}
			player.sendMessage("Currently Locked: "+lockedSkills+"");
			return true;
		}
		if (cmd[0].equals("hideyell")) {
			player.setYellOff(!player.isYellOff());
			player.getPackets().sendGameMessage("You have turned " + (player.isYellOff() ? "off" : "on") + " yell.");
			return true;
		}
		
		if (cmd[0].equals("forums")) {
			player.getPackets().sendOpenURL(Settings.FORUM);
			return true;
		}
		if (cmd[0].equals("wiki")) {
			player.getPackets().sendOpenURL(Settings.WIKI);
			return true;
		}
		if (cmd[0].equals("highscores")) {
			player.getPackets().sendOpenURL(Settings.HIGHSCORES);
			return true;
		}
		if (cmd[0].equals("donate")) {
			player.getPackets().sendOpenURL(Settings.DONATE);
			return true;
		}
		if (cmd[0].equals("vote")) {
			player.getPackets().sendOpenURL(Settings.VOTE);
			return true;
		}
		
		return false;
	}

}
