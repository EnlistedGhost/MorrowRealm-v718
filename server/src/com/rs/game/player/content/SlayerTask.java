package com.rs.game.player.content;

import java.io.Serializable;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;
import com.rs.game.npc.*;

/**
 * @author King Fox
 */
public class SlayerTask implements Serializable {
	private static final long serialVersionUID = -3885979679549716755L;
	
	private transient static int[] tasks = { 1648, 1637, 1612, 1643, 6215 };
	private transient static int[] amount = { 15, 20, 25, 30, 35 };
	
	private int currentTask = -1;
	private int currentTaskAmount = -1;
	
	private transient Player player;
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void resetTask() {
		this.currentTask = -1;
		this.currentTaskAmount = -2;
	}
	
	public String getName() {
		return NPCDefinitions.getNPCDefinitions(currentTask).getName();
	}
	
	public void getNewTask() {
		this.currentTask = tasks[Utils.random(tasks.length - 1)];
		this.currentTaskAmount = amount[Utils.random(tasks.length - 1)];
	}
	
	public int getCurrentTask() {
		return currentTask;
	}
	
	public int getTaskAmount() {
		return currentTaskAmount;
	}
	
	public void decreaseTask() {
		this.currentTaskAmount--;
	}
	
	public boolean isComplete() {
		return currentTaskAmount == 0;
	}
	
	public SlayerTask() {
		
	}
}
