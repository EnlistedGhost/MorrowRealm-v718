package com.rs.game.npc;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.minigames.zombies.WiseOldMan;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.controlers.custom.Dungeoneering;
import com.rs.game.player.controlers.custom.SlayerControler;

public class CheckControler {

	public static void check(Player player, NPC npc) {
		
		if (player.getControlerManager().getControler() instanceof Dungeoneering) {
			Dungeoneering.handleReward(player, npc);
			return;
		}
		
		if (player.getControlerManager().getControler() instanceof SlayerControler) {
			boolean hasSlayerRing = player.getEquipment().getRingId() == 13281;
			int currentPoints = player.getSlayerPoints();
			player.setSlayerPoints(hasSlayerRing == true ? (currentPoints + 80) : (currentPoints + 40));
			player.getSkills().addXp(Skills.SLAYER, npc.getCombatLevel());
			player.sendMessage("You have been awarded "+(hasSlayerRing == true ? "80" : "40")+" slayer points. You now have: "+currentPoints+" points.");
			return;
		}
		
	}
	
	public static void checkSlayerTask(Player player, NPC npc) {
		if (player.getTask().getTaskAmount() > 0) {
			player.getTask().decreaseTask();
			player.getSkills().addXp(Skills.SLAYER, npc.getCombatLevel() / 2);
			player.getGoals().increase(Skills.SLAYER);
			String name = NPCDefinitions.getNPCDefinitions(player.getTask().getCurrentTask()).getName();
			player.sendMessage("You have "+player.getTask().getTaskAmount()+" "+name+" remaining for your Slayer Task.");
		} else {
			player.sendMessage("You have completed your Slayer Task. Return to Kuradel for a reward!");
		}
		
		/*if (npc.getDefinitions().name.toLowerCase().equalsIgnoreCase(player.getTask().getName().toLowerCase())) {
			player.getSkills().addXp(Skills.SLAYER, player.getTask().getXPAmount());
			player.getTask().decreaseAmount();
			if (player.getTask().getTaskAmount() < 1) {
				boolean hasSlayerRing = player.getEquipment().getRingId() == 13281;
				int points = player.getSlayerPoints();
				player.setSlayerPoints(hasSlayerRing == true ? (points+40) : (points+20));
				player.getPackets().sendGameMessage("You have finished your slayer task, talk to Kuradal for a new task.");
				player.getPackets().sendGameMessage("Kuradal rewarded you "+(hasSlayerRing == true ? "40" : "20")+" slayerPoints! You now have " + player.getSlayerPoints() + " slayerPoints.");
				player.setTask(null);
				return;
			}
			player.getTask().setAmountKilled(player.getTask().getAmountKilled() + 1);
			player.getPackets().sendGameMessage("You need to defeat "+ player.getTask().getTaskAmount() + " " + player.getTask().getName().toLowerCase() + " to complete your task.");
		}*/
	}
}
