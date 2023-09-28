package com.rs.game.player.content.custom;

import java.util.Calendar;
import java.util.TimerTask;

import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.DailyRewardLogger;
import com.rs.cores.CoresManager;

public class DailyReward {

private transient Player player;
	
	public DailyReward(Player player) {
		this.player = player;
	}
	
	public static int dayOfWeek() {
	      Calendar cal = Calendar.getInstance();
	      return cal.get(Calendar.DAY_OF_WEEK);
	}
	  
	public int getRewardByDay() {
		switch (dayOfWeek()) {
		case 1:
			return 995;//coins
		case 2:
			return 380;//noted lobsters | 386 noted sharks
		case 3:
			return 2435;//noted prayer potions
		case 4:
			return 995;//coins
		case 5:
			return 1620;//uncut ruby | 1632 uncut dragonstone
		case 6:
			return 533;//big bones | 537 dragon bones
		case 7:
			return 24155;//double spin ticket
		}
		return 0;
	}
	  
	public int getAmount() {
		switch (dayOfWeek()) {
		case 1:
			return 2000;
		case 2:
			return 5;
		case 3:
			return 1;
		case 4:
			return 3000;
		case 5:
			return 3;
		case 6:
			return 5;
		case 7:
			return 1;
		}
		return 0;
	}

	public String getItemName() {
	  	switch (dayOfWeek()) {
	  	case 1:
			return "2000 Coins";
		case 2:
			return "5 Noted lobsters";
		case 3:
			return "1 Noted prayer potion";
		case 4:
			return "3000 Coins";
		case 5:
			return "3 Uncut rubies";
		case 6:
			return "5 Big bones";
		case 7:
			return "1 Double spin ticket";
		}
		return "Nothing (Contact the devs! | Error:514)";
	}
	  
	public void startCountdown() {

		DailyRewardLogger.loadDailyRewardStatus(player);
		int player_DailyReward_Status_1 = DailyRewardLogger.dailyRewardSTATUS;
		if (DailyRewardLogger.dailyRewardPlayerNAME == "NOT_REGISTERED") {
					DailyRewardLogger.writeDailyRewardStatus(player, player.getDisplayName(), 0);
		}
		if (player_DailyReward_Status_1 == dayOfWeek()) {
			player.sendMessage("<col=FF3333>Daily Check-in:</col>You have already recieved your "+getItemName()+" for playing today, be sure to log in tomorrow!");
	  	}
	  	if (player_DailyReward_Status_1 != dayOfWeek()) {
	  		player.sendMessage("<col=FF3333>Daily Check-in:</col>You will receive your reward in 1 minute, make sure you have at least one free inventory space.");
	  	}

	  	CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				DailyRewardLogger.loadDailyRewardStatus(player);
				int player_DailyReward_Status = DailyRewardLogger.dailyRewardSTATUS;
				if (DailyRewardLogger.dailyRewardPlayerNAME == "NOT_REGISTERED") {
					DailyRewardLogger.writeDailyRewardStatus(player, player.getDisplayName(), 0);
				}
				if (player_DailyReward_Status != dayOfWeek()) {
					player_DailyReward_Status = dayOfWeek();
					player.sendMessage("<col=FF3333>Daily Check-in:</col>Completed [<col=FF3333>"+dayOfWeek()+"</col>/<col=FF3333>7</col>] | You have received: "+getItemName());
					player.getInventory().addItem(getRewardByDay(), getAmount());
					DailyRewardLogger.writeDailyRewardStatus(player, player.getDisplayName(), player_DailyReward_Status);
				}
				if (player_DailyReward_Status == dayOfWeek()) {
					player.sendMessage("<col=FF3333>Daily Check-in:</col>Completed [<col=FF3333>"+dayOfWeek()+"</col>/<col=FF3333>7</col>].");
				}
			}
		}, 61000);
	}
	  
	  public void dailyCheckIn() {
			startCountdown();
			}
		}