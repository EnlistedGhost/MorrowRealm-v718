package com.rs.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.net.ServerChannelHandler;

public final class Logger {

	public static void handle(Throwable throwable) {
		System.out.println("ERROR! THREAD NAME: "+ Thread.currentThread().getName());
		throwable.printStackTrace();
	}

	public static void printTradeLog(Player player, Player target, Item[] items) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter("data/logs/trade_logs.txt", true));
			
			String username = Utils.formatPlayerNameForDisplay(player.getUsername());
			String tradeWith = Utils.formatPlayerNameForDisplay(target.getUsername());
			
			bf.append("["+Utils.getDateTime()+"] "+username+" trading with "+tradeWith+"");
			bf.newLine();
			bf.append("(To: "+username+", From: "+tradeWith+")");
			bf.newLine();
			
			for (int i = 0; i < items.length; i++) {
				if (items[i] == null)
					continue;
				Item item = new Item(items[i].getId(), items[i].getAmount());
				bf.append("x"+items[i].getAmount()+" "+item.getName()+"");
				bf.newLine();
			}
			
			bf.append("=======================");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
			//nothing.
		}
	}
	
	public static void debug(long processTime) {
		log(Logger.class, "---DEBUG--- start");
		log(Logger.class, "WorldProcessTime: " + processTime);
		log(Logger.class, "WorldRunningTasks: " + WorldTasksManager.getTasksCount());
		log(Logger.class, "ConnectedChannels: " + ServerChannelHandler.getConnectedChannelsSize());
		log(Logger.class, "---DEBUG--- end");
	}

	public static void log(Object classInstance, Object message) {
		log(classInstance.getClass().getSimpleName(), message);
	}

	public static void log(String className, Object message) {
		String text = "[" + className + "] " + message.toString();
		System.out.println(text);
	}

	private Logger() {

	}

}
