package com.rs.net.sfs;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.rs.game.player.Player;

public class CheckIP {

	public static boolean isGoodIp(String IP) throws IOException, SAXException {
		for (Answer a : new StopForumSpam().build().ip(IP).query()) {
			if (a.isAppears()) {
				System.out.println("Rejected IP: "+IP+" Reason: Blacklisted");
				return false;
			}
			System.out.println(a);
		}
		return true;
	}
	
	public static void getResults(Player player, Player target) {
		try {
			for (Answer a : new StopForumSpam().build().ip(target.getSession().getIP()).query()) {
				if (target == null || player == null)
					return;
				player.sendMessage(a.toString());
			}
		} catch (IOException | SAXException e) {
			player.sendMessage("Error in check IP Address.");
		}
	}
	
	
}
