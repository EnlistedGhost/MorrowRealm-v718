package com.rs.game.player.content.custom;

import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public class ModPanel extends Dialogue {

	private int playerIndex;
	
	@Override
	public void start() {
		playerIndex = (Integer) parameters[0];
		Player target = World.getPlayers().get(playerIndex);
		stage = 1;
		if (stage == 1) {
			sendOptionsDialogue("<col=ffffff>Options: <col=ff0000>"+target.getDisplayName()+"",
					"<col=ff0000>Ban 48 Hours</col>", 
					"<col=ff0000>Mute 24 Hours</col>",
					"<col=ff0000>Force Kick</col>", 
					"<col=ff0000>Jail 24 Hours</col>",
					"<col=ff0000>Cancel</col>");
			stage = 2;
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 2) {
			if (componentId == OPTION_1) {
				ban();
				end();
			}
			if (componentId == OPTION_2) {
				mute();
				end();
			}
			if (componentId == OPTION_3) {
				kick();
				end();
			}
			if (componentId == OPTION_4) {
				jail();
				end();
			}
			if (componentId == OPTION_5) {
				end();
			}
		}
	}

	public void ban() {
		Player target = World.getPlayers().get(playerIndex);
	    if (target.getRights() == 2 || target.getRights() == 1 || target.getRights() == 7) {
	    	if (player.getRights() > 0 && player.getRights() != 7) {
	    		player.sendMessage("You are treading dangerous waters...");
	    		target.sendMessage(""+player.getDisplayName()+" risked their privileges attempting to ban you.");
	    		if (target.getRights() == 1) {
	    			if (player.getRights() < 2) {
	    				player.setRights(0);
	    				player.sendMessage("You can't ban higher ranking users.");
	    				target.sendMessage(""+player.getDisplayName()+" has attempted to ban you and failed.");
	    				player.sendMessage("You have been demoted for abusing your priveleges.");
	    				target.sendMessage(""+player.getDisplayName()+" has lost ALL of their priveleges.");
	    				return;
	    			}
	    		}
	    		else if (target.getRights() == 2) {
	    			if (player.getRights() < 7) {
	    				player.setRights(0);
	    				player.sendMessage("You can't ban higher ranking users.");
	    				target.sendMessage(""+player.getDisplayName()+" has attempted to ban you and failed.");
	    				player.sendMessage("You have been demoted for abusing your priveleges.");
	    				target.sendMessage(""+player.getDisplayName()+" has lost ALL of their priveleges.");
	    				return;
	    			}
	    		}
	    		else if (target.getRights() == 7) {
	    			player.setRights(0);
	    			player.sendMessage("You can't ban higher ranking users.");
	    			target.sendMessage(""+player.getDisplayName()+" has attempted to ban you and failed.");
	    			player.sendMessage("You have been demoted for abusing your priveleges.");
	    			target.sendMessage(""+player.getDisplayName()+" has lost ALL of their priveleges.");
	    			return;
	    		}
	    	}
	    }
	   
	    String user = Utils.formatPlayerNameForDisplay(target.getUsername());
		player.setBanned(Utils.currentTimeMillis() + 172800000);
		SerializableFilesManager.savePlayer(target);
		target.forceLogout();
		
	    player.getPackets().sendGameMessage("You have banned " + target.getDisplayName()+" for 48 hours.");
	    World.sendWorldMessage("<col=ff0000><img=7>News: " + target.getDisplayName() + " has been banned by "+player.getDisplayName()+"", false);
	}
	
	public void mute() {
		Player target = World.getPlayers().get(playerIndex);
	    if (target.getRights() == 2 || target.getRights() == 1 || target.getRights() == 7) {
	    	if (player.getRights() > 0 && player.getRights() != 7) {
	    		player.sendMessage("You are treading dangerous waters...");
	    		target.sendMessage(""+player.getDisplayName()+" risked their privileges attempting to mute you.");
	    		if (target.getRights() == 1) {
	    			if (player.getRights() < 2) {
	    				player.setRights(0);
	    				player.sendMessage("You can't mute higher ranking users.");
	    				target.sendMessage(""+player.getDisplayName()+" has attempted to mute you and failed.");
	    				player.sendMessage("You have been demoted for abusing your priveleges.");
	    				target.sendMessage(""+player.getDisplayName()+" has lost ALL of their priveleges.");
	    				return;
	    			}
	    		}
	    		else if (target.getRights() == 2) {
	    			if (player.getRights() < 7) {
	    				player.setRights(0);
	    				player.sendMessage("You can't mute higher ranking users.");
	    				target.sendMessage(""+player.getDisplayName()+" has attempted to mute you and failed.");
	    				player.sendMessage("You have been demoted for abusing your priveleges.");
	    				target.sendMessage(""+player.getDisplayName()+" has lost ALL of their priveleges.");
	    				return;
	    			}
	    		}
	    		else if (target.getRights() == 7) {
	    			player.setRights(0);
	    			player.sendMessage("You can't mute higher ranking users.");
	    			target.sendMessage(""+player.getDisplayName()+" has attempted to mute you and failed.");
	    			player.sendMessage("You have been demoted for abusing your priveleges.");
	    			target.sendMessage(""+player.getDisplayName()+" has lost ALL of their priveleges.");
	    			return;
	    		}
	    	}
	    }

	    player.getPackets().sendGameMessage("You have muted " + target.getDisplayName()+" for 24 hours.");
	    target.setMuted(Utils.currentTimeMillis() + 86400000);
	    target.getPackets().sendGameMessage("You have been muted for 24 hours by "+player.getDisplayName()+".");
	    World.sendWorldMessage("<col=ff0000><img=7>News: " + target.getDisplayName() + " has been muted 24 hours by "+player.getDisplayName()+"", false);
	}
	
	public void kick() {
		Player target = World.getPlayers().get(playerIndex);
	    if (target.getRights() == 2 || target.getRights() == 1 || target.getRights() == 7) {
	    	if (player.getRights() > 0 && player.getRights() != 7) {
	    		player.sendMessage("You are treading dangerous waters...");
	    		target.sendMessage(""+player.getDisplayName()+" risked their privileges attempting to kick you.");
	    		if (target.getRights() == 1) {
	    			if (player.getRights() < 2) {
	    				player.setRights(0);
	    				player.sendMessage("You can't kick higher ranking users.");
	    				target.sendMessage(""+player.getDisplayName()+" has attempted to kick you and failed.");
	    				player.sendMessage("You have been demoted for abusing your priveleges.");
	    				target.sendMessage(""+player.getDisplayName()+" has lost ALL of their priveleges.");
	    				return;
	    			}
	    		}
	    		else if (target.getRights() == 2) {
	    			if (player.getRights() < 7) {
	    				player.setRights(0);
	    				player.sendMessage("You can't kick higher ranking users.");
	    				target.sendMessage(""+player.getDisplayName()+" has attempted to kick you and failed.");
	    				player.sendMessage("You have been demoted for abusing your priveleges.");
	    				target.sendMessage(""+player.getDisplayName()+" has lost ALL of their priveleges.");
	    				return;
	    			}
	    		}
	    		else if (target.getRights() == 7) {
	    			player.setRights(0);
	    			player.sendMessage("You can't kick higher ranking users.");
	    			target.sendMessage(""+player.getDisplayName()+" has attempted to kick you and failed.");
	    			player.sendMessage("You have been demoted for abusing your priveleges.");
	    			target.sendMessage(""+player.getDisplayName()+" has lost ALL of their priveleges.");
	    			return;
	    		}
	    	}
	    }

		target.forceLogout();
		player.getPackets().sendGameMessage("You have kicked: " + target.getDisplayName() + ".");
	    World.sendWorldMessage("<col=ff0000><img=7>News: " + target.getDisplayName() + " has been kicked by "+player.getDisplayName()+"", false);

	}
	
	public void jail() {
		Player target = World.getPlayers().get(playerIndex);
	    if (target.getRights() == 2 || target.getRights() == 1 || target.getRights() == 7) {
	    	if (player.getRights() > 0 && player.getRights() != 7) {
	    		player.sendMessage("You are treading dangerous waters...");
	    		target.sendMessage(""+player.getDisplayName()+" risked their privileges attempting to jail you.");
	    		if (target.getRights() == 1) {
	    			if (player.getRights() < 2) {
	    				player.setRights(0);
	    				player.sendMessage("You can't jail higher ranking users.");
	    				target.sendMessage(""+player.getDisplayName()+" has attempted to jail you and failed.");
	    				player.sendMessage("You have been demoted for abusing your priveleges.");
	    				target.sendMessage(""+player.getDisplayName()+" has lost ALL of their priveleges.");
	    				return;
	    			}
	    		}
	    		else if (target.getRights() == 2) {
	    			if (player.getRights() < 7) {
	    				player.setRights(0);
	    				player.sendMessage("You can't jail higher ranking users.");
	    				target.sendMessage(""+player.getDisplayName()+" has attempted to jail you and failed.");
	    				player.sendMessage("You have been demoted for abusing your priveleges.");
	    				target.sendMessage(""+player.getDisplayName()+" has lost ALL of their priveleges.");
	    				return;
	    			}
	    		}
	    		else if (target.getRights() == 7) {
	    			player.setRights(0);
	    			player.sendMessage("You can't jail higher ranking users.");
	    			target.sendMessage(""+player.getDisplayName()+" has attempted to jail you and failed.");
	    			player.sendMessage("You have been demoted for abusing your priveleges.");
	    			target.sendMessage(""+player.getDisplayName()+" has lost ALL of their priveleges.");
	    			return;
	    		}
	    	}
	    }

		target.setJailed(Utils.currentTimeMillis() + 86400000);
		target.getControlerManager().startControler("JailControler");
	    World.sendWorldMessage("<col=ff0000><img=7>News: " + target.getDisplayName() + " has been jailed 24 hours by "+player.getDisplayName()+"", false);
		target.getPackets().sendGameMessage("You've been Jailed for 24 hours by " + Utils.formatPlayerNameForDisplay(player.getUsername()) + ".");
		player.getPackets().sendGameMessage("You have Jailed 24 hours: " + target.getDisplayName() + ".");
		SerializableFilesManager.savePlayer(target);
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
