package com.rs.game.player.controlers.custom;

import java.util.ArrayList;
import com.rs.Settings;
import com.rs.content.utils.MoneyPouch;
import com.rs.game.Animation;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.controlers.Controler;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class Dungeoneering extends Controler {

	private static final WorldTile[] startingTile = { 
		new WorldTile(2640, 9827, 0),
		new WorldTile(2828, 10316, 0),
		new WorldTile(2356, 9392, 0),
	};
	
	public static ArrayList<String> players = new ArrayList<String>();
	
	public static boolean isInDung(String username) {
		return players.contains(username);
	}
	
	
	int level;
	
	@Override
	public void start() {
		this.level = (int)getArguments()[0];
		player.setNextWorldTile(startingTile[level]);
		player.setDungLevel(level);
		players.add(player.getUsername());
	}
	
	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		players.remove(player.getUsername());
		removeControler();
		return true;
	}

	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		players.remove(player.getUsername());
		removeControler();
		return true;
	}

	@Override
	public boolean processObjectTeleport(WorldTile toTile) {
		players.remove(player.getUsername());
		removeControler();
		return true;
	}
	
	@Override
	public boolean logout() {
		players.remove(player.getUsername());
		removeControler();
		player.setLocation(new WorldTile(Settings.START_PLAYER_LOCATION));
		return true;
	}
	
	@Override
	public boolean sendDeath() {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.setNextAnimation(new Animation(836));
				} else if (loop == 1) {
					player.sendMessage("You have been removed from this instance.");
				} else if (loop == 3) {
					player.getEquipment().init();
					player.getInventory().init();
					player.reset();
					player.setNextWorldTile(Settings.START_PLAYER_LOCATION);
					player.setNextAnimation(new Animation(-1));
				} else if (loop == 4) {
					removeControler();
					players.remove(player.getUsername());
					player.getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		}, 0, 1);
		return true;
	}
	
	public static void handleReward(Player player, NPC npc) {
		int level = npc.getCombatLevel();
			level *= 0.75;
			
		if (!players.contains(player.getUsername())) {
			return;
		}
		
		int random = Utils.random(500, 600);
		int reward = (random * player.getDungLevel() + 1) + level;
		player.setDungPoints(player.getDungPoints() + reward);
		player.sendMessage("You have been awarded with "+reward+" Dung Points. You now have "+Utils.formatNumber(player.getDungPoints())+"..");
		player.getSkills().addXp(Skills.DUNGEONEERING, level);
		player.getGoals().increase(Skills.DUNGEONEERING);
		return;
	}
	
}

