package com.rs.game.player.content.contracts;

import java.io.Serializable;

import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class ContractHandler implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private transient Player player;
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	private static final transient int[][] npcs = { 
		{ 50,	2500000 },
		{ 8133,	8000000 },
		{ 6260,	2000000 },
		{ 6222,	2000000 },
		{ 6247,	2000000 },
		{ 6203,	2000000 },
		{ 13460,3000000 },
		{ 8528,	5000000 },
		{ 15581,15000000 },
		{ 11872,20000000 },
		{ 12878,8000000 },
		{ 8596,	1000000 },
		{ 2745, 50000000 },
	};
	
	public static boolean getNewContract(Player player) {
		int index = Utils.random(npcs.length - 1);
		player.setContract(new Contract(npcs[index][0], 995, npcs[index][1]));
		return true;
	}
	
	public boolean checkContract(int id) {
		if (player == null) {
			System.out.println("player is null >_>");
			return false;
		}
		
		if (id == player.getContract().getNpcId()) {
			if (!player.getContract().hasCompleted()) {
				player.getContract().setCompleted(true);
				player.sendMessage("You've completed your contract! Return to the captain at home for a reward!");
				return true;
			}
		}
		
		return false;
	}
	
	

}
