package com.rs.game.player.content.contracts;

import java.io.Serializable;

public class Contract implements Serializable {
	private static final long serialVersionUID = -8769578633982303218L;
	
	private int npcId, rewardId, rewardAmount;
	private boolean completed;
	
	public Contract(int npcId, int reward, int rewardAmount) {
		this.npcId = npcId;
		this.rewardId = reward;
		this.rewardAmount = rewardAmount;
		this.completed = false;
	}
	
	public int getNpcId() {
		return npcId;
	}
	
	public int getRewardId() {
		return rewardId;
	}
	
	public int getRewardAmount() {
		return rewardAmount;
	}
	
	public boolean hasCompleted() {
		return completed;
	}
	
	public void setCompleted(boolean value) {
		this.completed = value;
	}
	
}
