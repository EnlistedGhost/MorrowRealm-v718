package com.rs.game.player.content;

import java.io.Serializable;

import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.World;

public class Prestige implements Serializable {
	private static final long serialVersionUID = -2585949397756268407L;

	private transient Player player;
	private boolean skullEnabled;
	
	public static int MAX_PRESTIGE = 6;
	
	public void prestige() {
		
		if (player.getPrestigeLevel() == MAX_PRESTIGE) {
			player.getPackets().sendGameMessage("You can not prestige any further.");
			return;
		}
		
		if (!player.getSkills().hasMaxedCombat()) {
			player.getPackets().sendGameMessage("You must have all combat skills 99 before you can prestige!");
			return;
		}
		
		player.setPrestigeLevel(player.getPrestigeLevel() + 1);
		player.setPrestigePoints(player.getPrestigePoints() + (player.getPrestigeLevel() == 6 ? 10 : 5));
		
		player.sendMessage("You have advanced to Prestige Level "+player.getPrestigeLevel()+", Congratulations!");
		player.sendMessage("You now have "+player.getPrestigePoints()+" Prestige Points!");
		player.resetLevels();
		// player.setAllowChange(true);
		// player.sendMessage("<col=FF0000>You can now change your difficulty to a higher setting.");
		if (player.getPrestigeLevel() < 6) {
			World.sendWorldMessage(""+player.getDisplayName()+" has advanced to " + player.getPrestigeLevel() + suffix + " Prestige!", false);
		} else {
			World.sendWorldMessage(""+player.getDisplayName()+" has advanced to Master Prestige!", false);
		}
		
	}
	
	public int getSkullId() {
		return player.getPrestigeLevel() == 1 ? 6 :
			player.getPrestigeLevel() == 2 ? 5 :
				player.getPrestigeLevel() == 3 ? 4 :
					player.getPrestigeLevel() == 4 ? 3 :
						player.getPrestigeLevel() == 5 ? 2 :
							player.getPrestigeLevel() == 6 ? 16 : -1;
	}
	
	public void setSkullEnabled(boolean enabled) {
		this.skullEnabled = enabled;
	}
	
	public boolean isSkullEnabled() {
		return skullEnabled;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	private String suffix = "";

	public String getSuffix() {
		return suffix = player.getPrestigeLevel() == 1 ? "st" : 
			player.getPrestigeLevel() == 2 ? "nd" : 
				player.getPrestigeLevel() == 3 ? "rd" : 
					player.getPrestigeLevel() > 3 ? "th" : "" ;
	}
}