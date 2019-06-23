package com.rs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.alex.store.Index;
import com.rs.cache.Cache;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.ItemsEquipIds;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.Region;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.npc.combat.CombatScriptsHandler;
import com.rs.game.player.Player;
import com.rs.game.player.content.FishingSpotsHandler;
import com.rs.game.player.content.FriendChatsManager;
import com.guardian.ItemManager;
import com.guardian.PriceLoader;
import com.rs.game.player.controlers.ControlerHandler;
import com.rs.game.player.cutscenes.CutscenesHandler;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.player.dialogues.DialogueHandler;
import com.rs.net.ServerChannelHandler;
import com.rs.utils.DTRank;
import com.rs.utils.DisplayNames;
import com.rs.utils.IPBanL;
import com.rs.utils.ItemBonuses;
import com.rs.utils.ItemExamines;
import com.rs.utils.Logger;
import com.rs.utils.MusicHints;
import com.rs.utils.NPCBonuses;
import com.rs.utils.NPCCombatDefinitionsL;
import com.rs.utils.NPCDrops;
import com.rs.utils.NPCSpawns;
import com.rs.utils.ObjectSpawns;
import com.rs.utils.PkRank;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;
import com.rs.utils.console.Console;
import com.rs.utils.console.ConsoleLogger;
import com.rs.utils.huffman.Huffman;
import com.rs.utils.spawning.ObjectSpawning;

public final class Launcher {
	
    public static void main(String[] args) throws Exception {
    	long start, end;
    		start = Utils.currentTimeMillis();
    	try {
    		
    		/*if (Settings.enableConsole) {
    			Console.getInstance().setVisible(true);
    			System.setOut(new ConsoleLogger(System.out));
    			System.setErr(new ConsoleLogger(System.err));
    		}*/
    		
    		System.out.println("" + Settings.SERVER_NAME + " is starting, standby...");
    		Cache.init();
    		ItemManager.inits();
    		ItemsEquipIds.init();
    		Huffman.init();
    		DisplayNames.init();
    		IPBanL.init();
    		PkRank.init();
    		DTRank.init();
    		ObjectSpawns.init();
    		NPCCombatDefinitionsL.init();
    		NPCBonuses.init();
    		NPCDrops.init();
    		ItemExamines.init();
    		ItemBonuses.init();
    		MusicHints.init();
    		ShopsHandler.init();
    		FishingSpotsHandler.init();
    		CombatScriptsHandler.init();
    		DialogueHandler.init();
    		ControlerHandler.init();
    		CutscenesHandler.init();
    		FriendChatsManager.init();
    		CoresManager.init();
    		World.init();
    		RegionBuilder.init();
            ServerChannelHandler.init();
            NPCSpawns.loadSpawns();
            ObjectSpawning.loadSpawns();
            // Christmas Event
            ChristmasStart.startXmas();
            System.out.println("Christmas Ending Chunk Copied Successfully");
        } catch (Throwable e) {
            Logger.handle(e);
            Logger.log("Launcher", "Failed starting Server Channel Handler. Shutting down...");
            System.exit(1);
            return;
        }
        addCleanMemoryTask();
        addUptimeTask();
        end = Utils.currentTimeMillis();
        System.out.println("MorrowRealm Server is now Online (Port: "+Settings.PORT_ID+", Launch Time: "+((double)(end - start) / 1000)+"s)");
    }
    
    public static void addUptimeTask() {
        CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                for (Player players : World.getPlayers()) {
                    if (World.getPlayers().size() > 0) {
                    	players.getInterfaceManager().updatePortal();
                    }
                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }
    
    public static void printItems() {
 	   for (int i = 0; i < Utils.getItemDefinitionsSize(); i++) {
 		   Item item = new Item(i);
 		   if (item == null || item.getName() == "null")
 			   continue;
 		   String noted = item.isNote() ? " -[Noted]" : "";
 		   String wear = item.getDefs().isWearItem() ? " -[Wearable]" : "";
 		   System.out.println(""+i+" - "+item.getName() + noted + wear +"");
 	   }
    }
    
   public static void printNpcs() {
	   for (int i = 0; i < Utils.getNPCDefinitionsSize(); i++) {
		   NPCDefinitions defs = NPCDefinitions.getNPCDefinitions(i);
		   String name = NPCDefinitions.getNPCDefinitions(i).getName();
		   if (name != "null") {
			   System.out.println(""+i+" - "+name+" (Level: "+defs.combatLevel+")");
		   }
	   }
   }
   
   private static void addCleanMemoryTask() {
        CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                	ItemDefinitions.clearItemsDefinitions();
                	NPCDefinitions.clearNPCDefinitions();
                	ObjectDefinitions.clearObjectDefinitions();
                	for (Region region : World.getRegions().values()) {
                		region.removeMapFromMemory();
                	}
                	for (Index index : Cache.STORE.getIndexes()) {
                        index.resetCachedFiles();
                    }
                	CoresManager.fastExecutor.purge();
                	System.gc();
                } catch (Throwable e) {
                    Logger.handle(e);
                }
            }
        }, 0, 10, TimeUnit.MINUTES);
    }

    public static void shutdown() {
        try {
        	ServerChannelHandler.shutdown();
            CoresManager.shutdown();
        } finally {
            System.exit(0);
        }
    }

    private Launcher() {
    	
    }
    
}
