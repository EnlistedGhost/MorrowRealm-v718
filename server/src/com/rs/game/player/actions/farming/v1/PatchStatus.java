package com.rs.game.player.actions.farming.v1;

import java.io.Serializable;

public class PatchStatus implements Serializable {
	
	/**
	 * @author Jake | Santa Hat @Rune-Server
	 */
	
	private static final long serialVersionUID = 4641462661859309514L;
	
	private int objectId;
	private int configId;
	private int configValue;
	private int maxConfigValue;
	private String inspect;

	public PatchStatus(int objectId, int configId, int configValue, int maxConfigValue, String inspect) {
		this.objectId = objectId;
		this.configId = configId;
		this.configValue = configValue;
		this.maxConfigValue = maxConfigValue;
		this.inspect = inspect;
	}
	
	public int getObjectId() {
		return objectId;
	}

	public int getConfigId() {
		return configId;
	}

	public int getConfigValue() {
		return configValue;
	}
	
	public int getMaxConfigValue() {
		return maxConfigValue;
	}
	
	public String getInspectText() {
		return inspect;
	}
	
}