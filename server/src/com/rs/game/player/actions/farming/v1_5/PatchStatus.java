package com.rs.game.player.actions.farming.v1_5;

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
	private int patchTime;

	public PatchStatus(int objectId, int configId, int configValue, int maxConfigValue, String inspect, int patchTime) {
		this.objectId = objectId;
		this.configId = configId;
		this.configValue = configValue;
		this.maxConfigValue = maxConfigValue;
		this.inspect = inspect;
		this.patchTime = patchTime;
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

	public int getPatchTime() {
		return patchTime;
	}
}