package com.rs.net.decoders.handlers;

import java.util.concurrent.TimeUnit;
//
import java.util.TimerTask;//TODO: Remove after creating classes for dependencies

import com.rs.Settings;
import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.game.Animation;
import com.rs.game.ForceMovement;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.minigames.CastleWars;
import com.rs.game.minigames.Crucible;
import com.rs.game.minigames.CrystalChest;
import com.rs.game.minigames.FightPits;
import com.rs.game.player.CoordsEvent;
import com.rs.game.player.OwnedObjectManager;
import com.rs.game.player.Player;
import com.rs.game.player.QuestManager.Quests;
import com.rs.game.player.Skills;
import com.rs.game.player.quests.impl.HalloweenEvent;
import com.rs.game.player.quests.impl.HalloweenObject;
import com.rs.game.player.actions.firemaking.Bonfire;
import com.rs.game.player.actions.BoxAction.HunterEquipment;
import com.rs.game.player.actions.BoxAction.HunterNPC;
import com.rs.game.player.actions.cooking.Cooking;
import com.rs.game.player.actions.cooking.Cooking.Cookables;
import com.rs.game.player.actions.CowMilkingAction;
import com.rs.game.player.actions.holidayevents.christmas2019.Snowman2019;
//import com.rs.game.player.actions.farming.v0_5.Farming;
import com.rs.game.player.actions.farming.v1_5.FarmingSystem;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.actions.smithing.Smithing.ForgingBar;
import com.rs.game.player.actions.smithing.Smithing.ForgingInterface;
import com.rs.game.player.actions.summoning.Summoning;
import com.rs.game.player.actions.woodcutting.Woodcutting;
import com.rs.game.player.actions.woodcutting.Woodcutting.TreeDefinitions;
import com.rs.game.player.actions.mining.EssenceMining;
import com.rs.game.player.actions.mining.EssenceMining.EssenceDefinitions;
import com.rs.game.player.actions.mining.Mining;
import com.rs.game.player.actions.mining.Mining.RockDefinitions;
import com.rs.game.player.actions.mining.MiningBase;
import com.rs.game.player.actions.runecrafting.SihponActionNodes;
import com.rs.game.player.actions.thieving.Thieving;
import com.rs.game.player.actions.thieving.ThievesGuildChests;
import com.rs.game.player.actions.thieving.ThievesGuildDoors;
import com.rs.game.player.content.ArtisanWorkshop;
import com.rs.game.player.content.Barrels;
import com.rs.game.player.content.ClueScrolls;
import com.rs.game.player.content.Fillables;
import com.rs.game.player.content.GildedAltar.bonestoOffer;
import com.rs.game.player.content.Hunter;
import com.rs.game.player.content.LividFarm;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.MaxedUser;
import com.rs.game.player.content.PartyRoom;
import com.rs.game.player.content.Runecrafting;
import com.rs.game.player.content.SandwichLadyHandler;
import com.rs.game.player.content.Searchables;
import com.rs.game.player.content.UnlitBeacon;
import com.rs.game.player.content.agility.BarbarianOutpostAgility;
import com.rs.game.player.content.agility.GnomeAgility;
import com.rs.game.player.content.cities.Entrana;
import com.rs.game.player.content.mission.Entrance;
//import com.rs.game.player.content.custom.PlayerLoginTimeout;
import com.rs.game.player.controlers.Falconry;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.controlers.NomadsRequiem;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.dialogues.impl.ExpertDung;
import com.rs.game.player.dialogues.impl.MiningGuildDwarf;
import com.rs.game.player.dialogues.impl.MillHopper;
import com.rs.game.player.dialogues.impl.MillFlourBin;
import com.rs.game.player.dialogues.impl.MillControls;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.game.player.content.ShootingStar;
import com.rs.game.ForceTalk;
import com.rs.game.player.content.FadingScreen;
import com.rs.cores.CoresManager;
import com.rs.net.decoders.handlers.SignPost;
import com.rs.io.InputStream;
import com.rs.utils.Logger;
import com.rs.utils.PkRank;
import com.rs.utils.Utils;
import com.rs.cores.CoresManager;//TODO: Remove after creating classes for dependencies

public final class ObjectHandler {

    private ObjectHandler() {
    }

    public static void handleOption(final Player player, InputStream stream, int option) {
        // Reset player timeout
        //PlayerLoginTimeout.PlayerTimeoutReset();
        if (!player.hasStarted() || !player.clientHasLoadedMapRegion() || player.isDead()) {
            return;
        }
        long currentTime = Utils.currentTimeMillis();
        if (player.getLockDelay() >= currentTime || player.getEmotesManager().getNextEmoteEnd() >= currentTime) {
            return;
        }
        boolean forceRun = stream.readUnsignedByte128() == 1;
        final int id = stream.readIntLE();
        int x = stream.readUnsignedShortLE();
        int y = stream.readUnsignedShortLE128();
        int rotation = 0;
        if (player.isAtDynamicRegion()) {
            rotation = World.getRotation(player.getPlane(), x, y);
            if (rotation == 1) {
                ObjectDefinitions defs = ObjectDefinitions
                        .getObjectDefinitions(id);
                y += defs.getSizeY() - 1;
            } else if (rotation == 2) {
                ObjectDefinitions defs = ObjectDefinitions
                        .getObjectDefinitions(id);
                x += defs.getSizeY() - 1;
            }
        }
        final WorldTile tile = new WorldTile(x, y, player.getPlane());
        final int regionId = tile.getRegionId();
        if (!player.getMapRegionsIds().contains(regionId)) {
            return;
        }
        WorldObject mapObject = World.getRegion(regionId).getObject(id, tile);
        if (mapObject == null || mapObject.getId() != id) {
            return;
        }
        if (player.isAtDynamicRegion() && World.getRotation(player.getPlane(), x, y) != 0) { //temp fix
            ObjectDefinitions defs = ObjectDefinitions.getObjectDefinitions(id);
            if (defs.getSizeX() > 1 || defs.getSizeY() > 1) {
                for (int xs = 0; xs < defs.getSizeX() + 1
                        && (mapObject == null || mapObject.getId() != id); xs++) {
                    for (int ys = 0; ys < defs.getSizeY() + 1
                            && (mapObject == null || mapObject.getId() != id); ys++) {
                        tile.setLocation(x + xs, y + ys, tile.getPlane());
                        mapObject = World.getRegion(regionId).getObject(id,
                                tile);
                    }
                }
            }
            if (mapObject == null || mapObject.getId() != id) {
                return;
            }
        }
        final WorldObject object = !player.isAtDynamicRegion() ? mapObject
                : new WorldObject(id, mapObject.getType(),
                (mapObject.getRotation() + rotation % 4), x, y, player.getPlane());
        player.stopAll(false);
        if (forceRun) {
            player.setRun(forceRun);
        }
        
       
        switch (option) {
            case 1:
                handleOption1(player, object);
                break;
            case 2:
                handleOption2(player, object);
                break;
            case 3:
                handleOption3(player, object);
                break;
            case 4:
                handleOption4(player, object);
                break;
            case 5:
                handleOption5(player, object);
                break;
            case -1:
                handleOptionExamine(player, object);
                break;
        }
    }

    private static void handleOption1(final Player player, final WorldObject object) {
        final ObjectDefinitions objectDef = object.getDefinitions();
        final int id = object.getId();
        final int x = object.getX();
        final int y = object.getY();
        if (SihponActionNodes.siphon(player, object)) {
            return;
        }
        player.setCoordsEvent(new CoordsEvent(object, new Runnable() {
            @Override
            public void run() {
                player.stopAll();
                player.faceObject(object);
                if (!player.getControlerManager().processObjectClick1(object)) {
                    return;
                }
                if (ClueScrolls.objectSpot(player, object)){
					return;
				}
                if (CastleWars.handleObjects(player, id)) {
                    return;
                }
                if (object.getId() == 36586) {
                	player.lock(1);
                	player.getInventory().addItem(4049, 1);
                	return;
                }
                if (object.getId() == 19205) {
                    Hunter.createLoggedObject(player, object, true);
                }

                // Rune Essence Exit Portal handler
                if (object.getId() == 2465) {
                    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3253, 3402, 0));
                }

                // Karamja->Sarim port gangplank (off loading)
                if (object.getId() == 2082) {
                    player.setNextWorldTile(new WorldTile(2956, 3145, 0));
                }

                // Karamja->Sarim port gangplank (on loading)
                if (object.getId() == 2081) {
                    player.setNextWorldTile(new WorldTile(2956, 3143, 1));
                }

                // Sarim->Karamja port gangplank (off loading)
                if (object.getId() == 2084) {
                    player.setNextWorldTile(new WorldTile(3030, 3217, 0));
                }

                // Sarim->Karamja port gangplank (on loading)
                if (object.getId() == 2083) {
                    player.setNextWorldTile(new WorldTile(3032, 3217, 1));
                }

                // Sign Post handler
                if (SignPost.handleSigns(player, object))
                    return;

                // Farming handler "Option 1"
                for (int i = 0; i < FarmingSystem.farmingPatches.length; i++) {
                    if (object.getId() == FarmingSystem.farmingPatches[i]) {
                        FarmingSystem.executeAction(player, object);
                    }
                }

                // Karamja Dungeon (Volcano) Entrance (into)
                if (object.getId() == 492) {
                    player.setNextAnimation(new Animation(832));
                    CoresManager.fastExecutor.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    player.setNextWorldTile(new WorldTile(2855, 9568, 0));
                                }
                    }, 1000);
                }

                // Karamja Dungeon (Volcano) Entrance (out of)
                if (object.getId() == 1764) {
                     player.setNextAnimation(new Animation(828));
                    CoresManager.fastExecutor.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    player.setNextWorldTile(new WorldTile(2857, 3170, 0));
                                }
                    }, 1000);
                }

                // Karamja TzHaar City Entrance (into)
                if (object.getId() == 68134) {
                    player.setNextWorldTile(new WorldTile(2480, 5175, 0));
                }

                // Karamja TzHaar City Entrance (out of)
                if (object.getId() == 9359) {
                    player.setNextWorldTile(new WorldTile(2845, 3170, 0));
                }

                // Windmill (milling) functionality handler
                //
                // Hopper
                if (object.getId() == 70035) {
                    player.getDialogueManager().startDialogue("MillHopper");
                }
                // Flour Bin
                if (object.getId() == 1782) {
                    player.getDialogueManager().startDialogue("MillFlourBin");
                }
                // Controls
                if (object.getId() == 2721) {
                    player.getDialogueManager().startDialogue("MillControls");
                }

                // Spinning Wheel functionality handler
                //66850
                if (objectDef.name.toLowerCase() == "spinning wheel") {
                    //
                    if (objectDef.containsOption(0, "Spin")) {
                        //
                    }
                }

                //
                // Barbarian Village Stronghold of Security dungeon (handling)
                //
                // TODO: Clean this up into its own class
                //
                if (object.getId() == 16154) {// Correct object
                    player.setNextAnimation(new Animation(832));
                    CoresManager.fastExecutor.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    player.setNextWorldTile(new WorldTile(1860, 5244, 0));// To level 1
                                }
                    }, 1000);
                }
                // Level 1 Stairs handling
                if (object.getId() == 16148) {// Correct object
                    player.setNextAnimation(new Animation(828));
                    CoresManager.fastExecutor.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    player.setNextWorldTile(new WorldTile(3081, 3421, 0));// Correct Back to Surface
                                }
                    }, 1000);
                }
                // Level 1 decent handling
                if (object.getId() == 16149) {// Correct object
                    player.setNextAnimation(new Animation(832));
                    CoresManager.fastExecutor.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    player.setNextWorldTile(new WorldTile(2042, 5245, 0));// To level 2
                                }
                    }, 1000);
                }
                // Level 2 Stairs handling
                if (object.getId() == 16080) {// Correct object
                    player.setNextAnimation(new Animation(828));
                    CoresManager.fastExecutor.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    player.setNextWorldTile(new WorldTile(1902, 5221, 0));// Correct Back to Level 1
                                }
                    }, 1000);
                }
                // Level 2 Rope handling
                if (object.getId() == 16078) {// Correct object
                    player.setNextAnimation(new Animation(828));
                    CoresManager.fastExecutor.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    player.setNextWorldTile(new WorldTile(1902, 5221, 0));// Correct Back to Level 1
                                }
                    }, 1000);
                }
                // Level 2 decent handling
                if (object.getId() == 16081) {// Correct object
                    player.setNextAnimation(new Animation(832));
                    CoresManager.fastExecutor.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    player.setNextWorldTile(new WorldTile(2122, 5251, 0));// To Level 3
                                }
                    }, 1000);
                }
                // Level 3 Stairs handling
                if (object.getId() == 16114) {// Correct object
                    player.setNextAnimation(new Animation(828));
                    CoresManager.fastExecutor.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    player.setNextWorldTile(new WorldTile(2027, 5218, 0));// Correct Back to level 2
                                }
                    }, 1000);
                }
                // Level 3 Goo covered vine handling
                if (object.getId() == 16112) {// Correct object
                    player.setNextAnimation(new Animation(828));
                    CoresManager.fastExecutor.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    player.setNextWorldTile(new WorldTile(2027, 5218, 0));// Correct Back to level 2
                                }
                    }, 1000);
                }
                // Level 3 decent handling
                if (object.getId() == 16115) {// Correct object
                    player.setNextAnimation(new Animation(832));
                    CoresManager.fastExecutor.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    player.setNextWorldTile(new WorldTile(2358, 5215, 0));// To Level 4
                                }
                    }, 1000);
                }
                // Level 4 Stairs handling (#2/Chest room entrance)
                if (object.getId() == 16048) {// Correct object
                    player.setNextAnimation(new Animation(828));
                    CoresManager.fastExecutor.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    player.setNextWorldTile(new WorldTile(2149, 5264, 0));// Correct Back to level 3
                                }
                    }, 1000);
                }
                // Level 4 Ladder handling
                if (object.getId() == 16049) {// Correct object
                    player.setNextAnimation(new Animation(828));
                    CoresManager.fastExecutor.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    player.setNextWorldTile(new WorldTile(2149, 5264, 0));// Correct Back to level 3
                                }
                    }, 1000);
                }
                //
                // END Stronghold handling
                //

                // Whirlpool to Mithril Dragons
                if (object.getId() == 67966) {// Correct object
                    player.setNextAnimation(new Animation(7269));//6723 7269
                    CoresManager.fastExecutor.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    player.setNextWorldTile(new WorldTile(1694, 5296, 1));// To Dungeon
                                }
                    }, 1000);
                }
                //TODO: dungeon step

                // Falador cabbage field Stile (handling)
                if (object.getId() == 7527) {
                    int y_coord = player.getY();
                    if (y_coord < 3283) {
                        player.setNextWorldTile(new WorldTile(3063, 3284, 0));
                        return;
                    } else if (y_coord > 3282) {
                        player.setNextWorldTile(new WorldTile(3063, 3281, 0));
                        return;
                    }
                }

                // North of Lumbridge Sheep field Stile (handling)
                if (object.getId() == 45205) {
                    int y_coord = player.getY();
                    if (y_coord < 3277) {
                        player.setNextWorldTile(new WorldTile(3197, 3278, 0));
                        return;
                    } else if (y_coord > 3276) {
                        player.setNextWorldTile(new WorldTile(3197, 3275, 0));
                        return;
                    }
                }

                // Ardougne wheat field, Sheep and Cows area Stile (handling)
                if (object.getId() == 34776) {
                    int y_coord = player.getY();
                    int x_coord = player.getX();
                    if (y_coord > 3355) {// Sheep
                        if (x_coord > 2647) {
                            player.setNextWorldTile(new WorldTile(2646, 3375, 0));
                            return;
                        } else if (x_coord < 2647) {
                            player.setNextWorldTile(new WorldTile(2648, 3375, 0));
                            return;
                        }
                    }
                    else if (y_coord < 3355) {// Wheat field
                        if (x_coord < 2639) {
                            player.setNextWorldTile(new WorldTile(2640, 3350, 0));
                            return;
                        } else if (x_coord > 2638) {// Check if you're using the Cows Stile or Wheat Stile
                            if (x_coord > 2648) {// Cows
                                if (x_coord > 2656) {
                                    //
                                    player.setNextWorldTile(new WorldTile(2654, 3347, 0));
                                    return;
                                } else {
                                    //
                                    player.setNextWorldTile(new WorldTile(2657, 3347, 0));
                                    return;
                                }
                            }else {
                                player.setNextWorldTile(new WorldTile(2637, 3350, 0));
                                return;
                            }
                        }
                    }
                }

                // Karamja Dungeon Open Wall (handling)
                if (object.getId() == 2606) {
                    int y_coord = player.getY();
                    if (y_coord < 9600) {
                        player.setNextWorldTile(new WorldTile(2836, 9601, 0));
                        return;
                    } else if (y_coord > 9600) {
                        player.setNextWorldTile(new WorldTile(2836, 9599, 0));
                        return;
                    }
                }

                // Karamja Banana Tree (handling)
                if (object.getId() == 2073) {
                    if (player.getInventory().getFreeSlots() < 1) {
                        player.sm("You have no more space in your inventory.");
                        return;
                    } else {
                        //
                        player.getInventory().addItem(1963, 1);
                        player.lock(3);
                        player.sm("You gather a banana from the tree.");
                        return;
                    }
                }

                // Snow Pile handling 66496 28296
                if (object.getId() == 66496) {
                    if (player.christmas < 1) {
                        player.setNextForceTalk(new ForceTalk("I think I should speak with the Snow Queen first."));
                        return;
                    } else if (player.getInventory().getFreeSlots() < 1) {
                        player.sm("You have no more space in your inventory.");
                        return;
                    } else {
                        //
                        player.setNextAnimation(new Animation(827));
                        player.lock(3);
                        player.getInventory().addItem(10501, 1);
                        player.sm("You gather some snow into a ball.");
                        return;
                    }
                }

                // Snow Pile handling 66496 28296
                if (object.getId() == 28296) {
                    if (player.christmas < 1) {
                        player.setNextForceTalk(new ForceTalk("I think I should speak with the Snow Queen first."));
                        return;
                    } else if (player.getInventory().getFreeSlots() < 1) {
                        player.sm("You have no more space in your inventory.");
                        return;
                    } else {
                        //
                        player.setNextAnimation(new Animation(827));
                        player.lock(3);
                        player.getInventory().addItem(10501, 1);
                        player.sm("You gather some snow into a ball.");
                        Snowman2019.handleSnwmn(player);
                        return;
                    }
                }

                //Snow Ball handling
                /*if (object.getId() == 10501) {
                    int weaponId_snbl = player.getEquipment().getWeaponId();
                    if (weaponId_snbl == 10501) {
                        player.getPackets().sendPlayerOption("Pelt", 1, true);
                    } else {
                        player.getPackets().sendPlayerOption("null", 1, true);
                    }
                }*/

                //Halloween event
                if (id == 27896 || id == 32046 || id == 31747 || id == 30838
                || id == 31842 || id == 46567 || id == 46568 || id == 31818
                || id == 46549 || id == 31819 || id == 46566 || id == 62621) {
                    HalloweenObject.HandleObject(player, object);
                }

                /**
                 * Christmas event
                 */
                if (id == 47774) { // Snow to Jack
                    if (player.christmas < 1) {
                        player.setNextForceTalk(new ForceTalk("I'm not too sure where this will lead me, maybe I should speak with the Snow Queen first."));
                    }else if (player.christmas >= 5) {
                        player.setNextForceTalk(new ForceTalk("I don't think I am allowed in there."));
                    }else {
                        // TODO: This is broken, figure out instances later, 
                        // just send players to the same area for now
                        //player.getControlerManager().startControler("SantaCage1Controler");
                        //player.setNextWorldTile(getWorldTile(24, 8));
                        player.setNextForceTalk(new ForceTalk("I worry too much about Santa to leave! I should vanquish more Snowmen!"));
                    }
                    return;
                }
                if (id == 47857) { // Santa in Cage
                    if (player.christmas < 2) {
                    player.getDialogueManager().startDialogue("SantaCage1", 8540);
                    
                    }else {
                    player.getDialogueManager().startDialogue("SantaCage2", 8540);
                    }   
                    return;
                }
                if (id == 47775) {// Jack to Snow
                    final long time = FadingScreen.fade(player);
                    CoresManager.slowExecutor.schedule(new Runnable() {
                        @Override
                        public void run() {
                            FadingScreen.unfade(player, time, new Runnable() {
                                @Override
                                public void run() {
                                    player.setNextWorldTile(new WorldTile(2674, 5662, 0));
                                }
                                
                            
                            });
                        }
                    }, 3000, TimeUnit.MILLISECONDS);
                    return;
                }
                if (id == 12258) { //Portal to Snow
                    final long time = FadingScreen.fade(player);
                    CoresManager.slowExecutor.schedule(new Runnable() {
                        @Override
                        public void run() {
                            FadingScreen.unfade(player, time, new Runnable() {
                                @Override
                                public void run() {
                                    if (player.christmas >= 5) {
                                        player.setNextWorldTile(new WorldTile(78, 91, 0));
                                    } else {
                                    player.setNextWorldTile(new WorldTile(2646, 5659, 0));
                                }
                                }
                            
                            });
                        }
                    }, 3000, TimeUnit.MILLISECONDS);
                    return;
                }
                if (id == 47766) { // Portal to Home
                    final long time = FadingScreen.fade(player);
                    CoresManager.slowExecutor.schedule(new Runnable() {
                        @Override
                        public void run() {
                            FadingScreen.unfade(player, time, new Runnable() {
                                @Override
                                public void run() {
                                    player.setNextWorldTile(new WorldTile(3235, 3227, 0)); // MAKE SURE THESE COORDS CHANGE TO WHERE YOU WANT THEM SPAWNED BACK TO
                                }
                                
                            
                            });
                        }
                    }, 3000, TimeUnit.MILLISECONDS);
                    return;
                }
                if (id == 25034 && x == 3041 && y == 3365) { // Christmas Drawer
                    if (player.xmasDrawer == 0) {
                        player.sm("I shouldn't go through things that aren't mine.");
                        return;
                    } else if (player.xmasDrawer == 1) {
                        if (player.getInventory().getFreeSlots() < 1) {
                            player.sm("Please get more free space before checking this.");
                            return;
                        }else {
                            player.getInventory().addItem(1470, 1);
                            player.sm("You found a Red Bead!");
                            player.xmasDrawer = 3;
                            return;
                        }
                    } else{
                        player.sm("I already found what I needed in there.");
                        return;
                    }
                }
                if (id == 24204 && x == 3048 && y == 3355 && player.getPlane() == 2) { // Christmas Chest
                    if (player.xmasChest == 0) {
                        player.sm("I shouldn't go through things that aren't mine.");
                        return;
                    } else if (player.xmasChest == 1) {
                        if (player.getInventory().getFreeSlots() < 1) {
                            player.sm("Please get more free space before checking this.");
                            return;
                        }else {
                            player.getInventory().addItem(1472, 1);
                            player.sm("You found a Yellow Bead!");
                            player.xmasChest = 3;
                            return;
                        }
                    } else{
                        player.sm("I already found what I needed in there.");
                        return;
                    }
                }
                if (id == 9611 && x == 3048 && y == 3364) { // Christmas Small Book Case
                    if (player.xmasBookcaseSmall == 0) {
                        player.sm("I shouldn't go through things that aren't mine.");
                        return;
                    } else if (player.xmasBookcaseSmall == 1) {
                        if (player.getInventory().getFreeSlots() < 1) {
                            player.sm("Please get more free space before checking this.");
                            return;
                        }else {
                            player.getInventory().addItem(1474, 1);
                            player.sm("You found a Black Bead!");
                            player.xmasBookcaseSmall = 3;
                            return;
                        }
                    } else{
                        player.sm("I already found what I needed in there.");
                        return;
                    }
                }
                if (id == 15544 && x == 3026 && y == 3354) { // Christmas Big Book Case
                    if (player.xmasBookcaseBig == 0) {
                        player.sm("I shouldn't go through things that aren't mine.");
                        return;
                    } else if (player.xmasBookcaseBig == 1) {
                        if (player.getInventory().getFreeSlots() < 1) {
                            player.sm("Please get more free space before checking this.");
                            return;
                        }else {
                            player.getInventory().addItem(1476, 1);
                            player.sm("You found a White Bead!");
                            player.xmasBookcaseBig = 3;
                            return;
                        }
                    } else{
                        player.sm("I already found what I needed in there.");
                        return;
                    }
                }
                if (id == 47777 || id == 47778) {
                    if (player.christmas < 7) {// turned off for fun. people should be able to get food!
                        player.getDialogueManager().startDialogue("XmasFoodTable1");
                        return;
                    } else 
                        player.sm("Ahh, smells like Christmas!");
                    return;
                }
                /**
                 * Falador East House Stairs & Ladder
                 */
                if (id == 35781 && x == 3048 && y == 3352) { // Stairs to Ladder Christmas
                    player.useStairs(-1, new WorldTile(3049, 3354, 1), 1, 1);
                }
                
                if (id == 35782 && x == 3049 && y == 3353 && player.getPlane() == 1) { // Ladder to Stairs Christmas
                    player.useStairs(-1, new WorldTile(3049, 3354, 0), 1, 1);
                }
                
                if (id == 11739 && x == 3050 && y == 3355 && player.getPlane() == 1) { //Ladder to Chest
                    player.useStairs(828, new WorldTile(3050, 3354, 2), 2, 3);
                }
                
                if (id == 11741 && x == 3050 && y == 3355 && player.getPlane() == 2) { // Chest to Ladder
                    player.useStairs(828,  new WorldTile(3049, 3355, 1), 2, 3);
                }
                /**
                 *End Falador Stairs and Ladder 
                 */
                /**
                 * End Christmas
                 */


                 /**thieves guild**/
                 //doors
                 if (id == 52302 || id == 52304) {
                    player.getActionManager().setAction(new ThievesGuildDoors(object));
                    return;
                }
                //chests
                if (id == 52296 || id == 52299) {
                    player.getActionManager().setAction(new ThievesGuildChests(object));
                    return;
                }
                /**thieves guild end**/

                HunterNPC hunterNpc = HunterNPC.forObjectId(id);
                
                if (id == 55764) {
                	player.getDialogueManager().startDialogue("BossDoorD");
                	return;
                }
                
                if (id == 6) {
                	player.getCannon().fireDwarfCannon(object);
                	return;
                }
                
                if (id == 40443) {
					LividFarm.deposit(player);
					return;
				}
				if (id == 40492) {
					LividFarm.MakePlants(player);
					return;
				}
				if (id == 40486) {
					LividFarm.MakePlants(player);
					return;
				}
				if (id == 40505) {
					LividFarm.MakePlants(player);
					return;
				}
				if (id == 40534) {
					LividFarm.MakePlants(player);
					return;
				}
				if (id == 40464) {
					LividFarm.MakePlants(player);
					return;
				}
				if (id == 40489) {
					LividFarm.MakePlants(player);
					return;
				}
				if (id == 40487) {
					LividFarm.MakePlants(player);
					return;
				}
				if (id == 40532) {
					LividFarm.MakePlants(player);
					return;
				}
				if (id == 40499) {
					LividFarm.MakePlants(player);
					return;
				}
				if (id == 40533) {
					LividFarm.MakePlants(player);
					return;
				}
				if (id == 40504) {
					LividFarm.MakePlants(player);
					return;
				}
				
				if (id == 40444) {
                	if (player.getInventory().getFreeSlots() < 1) {
                		player.getPackets().sendGameMessage("Not enough space in your inventory.");
                		return;
                	}
                	player.getInventory().addItem(1511, 1);
                	player.sendMessage("You take a log from the pile...");
                	player.setNextAnimation(new Animation(881));
                	player.lock(2);
                	player.lividfarm = false;
                	return;
                }
				
                if (id == 47237) {
                    if (player.getSkills().getLevel(Skills.AGILITY) < 90) {
                        player.getPackets().sendGameMessage("You need 90 agility to use this shortcut.");
                        return;
                    }
                    if (player.getX() == 1641 && player.getY() == 5260 || player.getX() == 1641 && player.getY() == 5259 || player.getX() == 1640 && player.getY() == 5259) {
                        player.setNextWorldTile(new WorldTile(1641, 5268, 0));
                    } else {
                        player.setNextWorldTile(new WorldTile(1641, 5260, 0));
                    }
                }
                
                if (id == 75868) {
                	if (player.getInventory().containsItem(989, 1)) {
                		player.getPackets().sendGameMessage("The magical chest has found your key and is ready for exchange!");
                		player.getInterfaceManager().sendInterface(1123);
                	} else {
                		player.getPackets().sendGameMessage("The magical chest doesnt detect your Crystal Key...");
                	}
                }
                
                if (id == 47232) {
                    if (player.getSkills().getLevel(Skills.SLAYER) < 75) {
                        player.getPackets().sendGameMessage("You need 75 slayer to enter Kuradal's dungeon.");
                        return;
                    }
                    player.setNextWorldTile(new WorldTile(1661, 5257, 0));
                }
                if (id == 11368) {
                    SandwichLadyHandler.CanLeave(player);
                }
                if (id == 24991) {
                    Hunter.PuroPuroTeleport(player, 2591, 4320, 0);
                    player.getInventory().addItem(10010, 1);
                    player.getInventory().refresh();
                }
                if (id == 59462) {
                    player.getDialogueManager().startDialogue("PortalTeleport", 1);
                }
                if (id == 25014) {
                    Hunter.PuroPuroTeleport(player, 2426, 4445, 0);
                    player.sm("You have returned to the  city of Zanaris.");
                    player.getInventory().refresh();
                }
                if (id == 29395) {
                    player.getInterfaceManager().sendInterface(ArtisanWorkshop.INGOTWITH);
                }
                if (id == 47142) {
                    Entrance.useStairs(player);
                }

                if (id == 47144) {
                    Entrance.useStairsDown(player);
                }
                if (id == 16885) {
                    Barrels.pickApples(player);
                }
                if (id == 29394) {
                    player.getInterfaceManager().sendInterface(
                            ArtisanWorkshop.INGOTWITH);
                }
                if (id == 29396) {
                    ArtisanWorkshop.DepositArmour(player);
                    player.getInventory().refresh();
                }
                if (id == 2562) {
                    player.sm("You investigate...");
                    if (!player.hasClaimedCompCape()) {
                    	 MaxedUser.CheckCompletionist(player);
                    }
                    if (!player.hasClaimedMaxCape()) {
                    	 MaxedUser.CheckMaxed(player);
                    }
                    player.lock(2);
                }
                if (id == 47231) {
                    player.setNextWorldTile(new WorldTile(1685, 5287, 1));
                }// 397
                if (id == Entrana.LADDER) {
                    Entrana.EnterDungeon(player);
                }
                if (id == 31529) {
                    player.teleportPlayer(3034, 2980, 1);
                }
                if (id == 31530) {
                    player.teleportPlayer(3034, 2980, 0);
                }
                if (id == 57225) {
                    player.getDialogueManager().startDialogue("NexEntrance");
                }
                if (id == 13704) {
                    player.getInterfaceManager().sendInterface(397);
                    player.sm("Pick a furniture that you would like to build.");
                }
                if (id == 28716 || id == 67036) {
                    Summoning.sendInterface(player);
                }
                if (id == 26865) {
                    player.teleportPlayer(4512, 5585, 0);
                    player.sm("You go trought the door.");
                }
                if (id == 26866) {
                    player.teleportPlayer(4519, 5540, 0);
                    player.sm("You go trought the door.");
                }
                if (id == 57169) {
                    player.getActionManager().setAction(new Mining(object, RockDefinitions.Crust_Ore));
                }
                if (id == 57170) {
                    player.getActionManager().setAction(
                            new Mining(object, RockDefinitions.Crust_Ore));
                }
                if (id == 57171) {
                    player.getActionManager().setAction(
                            new Mining(object, RockDefinitions.Crust_Ore));
                }
                if (id == 57172) {
                    player.getActionManager().setAction(
                            new Mining(object, RockDefinitions.Crust_Ore));
                }
                if (id == 57173) {
                    player.getActionManager().setAction(
                            new Mining(object, RockDefinitions.Crust_Ore));
                }
                if (id == 57168) {
                    player.getActionManager().setAction(
                            new Mining(object, RockDefinitions.Crust_Ore));
                }
                if (id == 57167) {
                    player.getActionManager().setAction(
                            new Mining(object, RockDefinitions.Crust_Ore));
                }
                if (id == 57166) {
                    player.getActionManager().setAction(
                            new Mining(object, RockDefinitions.Crust_Ore));
                }
                if (id == 57165) {
                    player.getActionManager().setAction(
                            new Mining(object, RockDefinitions.Crust_Ore));
                }
                if (id == 26898) {
                    player.teleportPlayer(2215, 3797, 2);
                    player.sm("You have teleported to the travelling boat.");
                }
                if (id == 40760) {
                    player.getDialogueManager().startDialogue("Noticeboard");
                }
                if (id == 47236) {
                    if (player.getX() == 1650 && player.getY() == 5281 || player.getX() == 1651 && player.getY() == 5281 || player.getX() == 1650 && player.getY() == 5281) {
                        player.addWalkSteps(1651, 5280, 1, false);
                    }
                    if (player.getX() == 1652 && player.getY() == 5280 || player.getX() == 1651 && player.getY() == 5280 || player.getX() == 1653 && player.getY() == 5280) {
                        player.addWalkSteps(1651, 5281, 1, false);
                    }
                    if (player.getX() == 1650 && player.getY() == 5301 || player.getX() == 1650 && player.getY() == 5302 || player.getX() == 1650 && player.getY() == 5303) {
                        player.addWalkSteps(1649, 5302, 1, false);
                    }
                    if (player.getX() == 1649 && player.getY() == 5303 || player.getX() == 1649 && player.getY() == 5302 || player.getX() == 1649 && player.getY() == 5301) {
                        player.addWalkSteps(1650, 5302, 1, false);
                    }
                    if (player.getX() == 1626 && player.getY() == 5301 || player.getX() == 1626 && player.getY() == 5302 || player.getX() == 1626 && player.getY() == 5303) {
                        player.addWalkSteps(1625, 5302, 1, false);
                    }
                    if (player.getX() == 1625 && player.getY() == 5301 || player.getX() == 1625 && player.getY() == 5302 || player.getX() == 1625 && player.getY() == 5303) {
                        player.addWalkSteps(1626, 5302, 1, false);
                    }
                    if (player.getX() == 1609 && player.getY() == 5289 || player.getX() == 1610 && player.getY() == 5289 || player.getX() == 1611 && player.getY() == 5289) {
                        player.addWalkSteps(1610, 5288, 1, false);
                    }
                    if (player.getX() == 1609 && player.getY() == 5288 || player.getX() == 1610 && player.getY() == 5288 || player.getX() == 1611 && player.getY() == 5288) {
                        player.addWalkSteps(1610, 5289, 1, false);
                    }
                    if (player.getX() == 1606 && player.getY() == 5265 || player.getX() == 1605 && player.getY() == 5265 || player.getX() == 1604 && player.getY() == 5265) {
                        player.addWalkSteps(1605, 5264, 1, false);
                    }
                    if (player.getX() == 1606 && player.getY() == 5264 || player.getX() == 1605 && player.getY() == 5264 || player.getX() == 1604 && player.getY() == 5264) {
                        player.addWalkSteps(1605, 5265, 1, false);
                    }
                    if (player.getX() == 1634 && player.getY() == 5254 || player.getX() == 1634 && player.getY() == 5253 || player.getX() == 1634 && player.getY() == 5252) {
                        player.addWalkSteps(1635, 5253, 1, false);
                    }
                    if (player.getX() == 1635 && player.getY() == 5254 || player.getX() == 1635 && player.getY() == 5253 || player.getX() == 1635 && player.getY() == 5252) {
                        player.addWalkSteps(1634, 5253, 1, false);
                    }
                }
                if (object.getId() == 28716) {
                    Summoning.sendInterface(player);
                    player.setNextFaceWorldTile(object);
                    return;
                }
                if (id == 47233) {
                    if (player.getSkills().getLevel(Skills.AGILITY) < 80) {
                        player.getPackets().sendGameMessage("You need 80 agility to use this shortcut.");
                        return;
                    }
                    if (player.getX() == 1633 && player.getY() == 5294) {
                        player.getPackets().sendGameMessage("That not safe!");
                        return;
                    }
                    player.lock(3);
                    player.setNextAnimation(new Animation(4853));
                    final WorldTile toTile = new WorldTile(object.getX(), object.getY() + 1, object.getPlane());
                    player.setNextForceMovement(new ForceMovement(player, 0, toTile, 2, ForceMovement.EAST));
                    WorldTasksManager.schedule(new WorldTask() {
                        @Override
                        public void run() {
                            player.setNextWorldTile(toTile);
                        }
                    }, 1);
                }
                if (id == 6282) {
                    player.setNextWorldTile(new WorldTile(2994, 9679, 0));
                    player.closeInterfaces();
                }
                if (id == 29958 || id == 4019 || id == 50205 || id == 50206 || id == 50207 || id == 53883 || id == 54650 || id == 55605 || id == 56083 || id == 56084 || id == 56085 || id == 56086) {
                    final int maxSummoning = player.getSkills().getLevelForXp(23);
                    if (player.getSkills().getLevel(23) < maxSummoning) {
                        player.lock(5);
                        player.getPackets().sendGameMessage("You feel the obelisk", true);
                        player.setNextAnimation(new Animation(8502));
                        player.setNextGraphics(new Graphics(1308));
                        WorldTasksManager.schedule(new WorldTask() {
                            @Override
                            public void run() {
                                player.getSkills().restoreSummoning();
                                player.getPackets().sendGameMessage("...and recharge all your skills.",
                                        true);
                            }
                        }, 2);
                    } else {
                        player.getPackets().sendGameMessage("You already have full summoning.", true);
                    }
                    return;
                }
                if (hunterNpc != null) {
                    if (OwnedObjectManager.removeObject(player, object)) {
                        player.setNextAnimation(hunterNpc.getEquipment().getPickUpAnimation());
                        player.getInventory().getItems().addAll(hunterNpc.getItems());
                        player.getInventory().addItem(hunterNpc.getEquipment().getId(), 1);
                        player.getSkills().addXp(Skills.HUNTER, hunterNpc.getXp());
                    } else {
                        player.getPackets().sendGameMessage("This isn't your trap.");
                    }
                } else if (id == HunterEquipment.BOX.getObjectId() || id == 19192) {
                    if (OwnedObjectManager.removeObject(player, object)) {
                        player.setNextAnimation(new Animation(5208));
                        player.getInventory().addItem(HunterEquipment.BOX.getId(), 1);
                    } else {
                        player.getPackets().sendGameMessage("This isn't your trap.");
                    }
                } else if (id == HunterEquipment.BRID_SNARE.getObjectId() || id == 19174) {
                    if (OwnedObjectManager.removeObject(player, object)) {
                        player.setNextAnimation(new Animation(5207));
                        player.getInventory().addItem(HunterEquipment.BRID_SNARE.getId(), 1);
                    } else {
                        player.getPackets().sendGameMessage("This isn't your trap.");
                    }
                } else if (id == 2350
                        && (object.getX() == 3352 && object.getY() == 3417 && object
                        .getPlane() == 0)) {
                    player.useStairs(832, new WorldTile(3177, 5731, 0), 1, 2);
                } else if (id == 2353
                        && (object.getX() == 3177 && object.getY() == 5730 && object
                        .getPlane() == 0)) {
                    player.useStairs(828, new WorldTile(3353, 3416, 0), 1, 2);
                } else if (id == 11554 || id == 11552) {
                    player.getPackets().sendGameMessage(
                            "That rock is currently unavailable.");
                } else if (id == 38279) {
                    player.getDialogueManager().startDialogue("RunespanPortalD");
                } else if (id == 2491) {
                    player.getActionManager()
                            .setAction(
                            new EssenceMining(
                            object,
                            player.getSkills().getLevel(
                            Skills.MINING) < 30 ? EssenceDefinitions.Rune_Essence
                            : EssenceDefinitions.Pure_Essence));
                } else if (id == 2478) {
                    Runecrafting.craftEssence(player, 556, 1, 5, false, 11, 2,
                            22, 3, 34, 4, 44, 5, 55, 6, 66, 7, 77, 88, 9, 99,
                            10);
                } else if (id == 2479) {
                    Runecrafting.craftEssence(player, 558, 2, 5.5, false, 14,
                            2, 28, 3, 42, 4, 56, 5, 70, 6, 84, 7, 98, 8);
                } else if (id == 2480) {
                    Runecrafting.craftEssence(player, 555, 5, 6, false, 19, 2,
                            38, 3, 57, 4, 76, 5, 95, 6);
                } else if (id == 2481) {
                    Runecrafting.craftEssence(player, 557, 9, 6.5, false, 26,
                            2, 52, 3, 78, 4);
                } else if (id == 2482) {
                    Runecrafting.craftEssence(player, 554, 14, 7, false, 35, 2,
                            70, 3);
                } else if (id == 2483) {
                    Runecrafting.craftEssence(player, 559, 20, 7.5, false, 46,
                            2, 92, 3);
                } else if (id == 2484) {
                    Runecrafting.craftEssence(player, 564, 27, 8, true, 59, 2);
                } else if (id == 2487) {
                    Runecrafting
                            .craftEssence(player, 562, 35, 8.5, true, 74, 2);
                } else if (id == 17010) {
                    Runecrafting.craftEssence(player, 9075, 40, 8.7, true, 82,
                            2);
                } else if (id == 2486) {
                    Runecrafting.craftEssence(player, 561, 45, 9, true, 91, 2);
                } else if (id == 2485) {
                    Runecrafting.craftEssence(player, 563, 50, 9.5, true);
                } else if (id == 2488) {
                    Runecrafting.craftEssence(player, 560, 65, 10, true);
                } else if (id == 30624) {
                    Runecrafting.craftEssence(player, 565, 77, 10.5, true);
                } else if (id == 2452) {
                    int hatId = player.getEquipment().getHatId();
                    if (hatId == Runecrafting.AIR_TIARA
                            || hatId == Runecrafting.OMNI_TIARA
                            || player.getInventory().containsItem(1438, 1)) {
                        Runecrafting.enterAirAltar(player);
                    }
                } else if (id == 2455) {
                    int hatId = player.getEquipment().getHatId();
                    if (hatId == Runecrafting.EARTH_TIARA
                            || hatId == Runecrafting.OMNI_TIARA
                            || player.getInventory().containsItem(1440, 1)) {
                        Runecrafting.enterEarthAltar(player);
                    }
                } else if (id == 2456) {
                    int hatId = player.getEquipment().getHatId();
                    if (hatId == Runecrafting.FIRE_TIARA
                            || hatId == Runecrafting.OMNI_TIARA
                            || player.getInventory().containsItem(1442, 1)) {
                        Runecrafting.enterFireAltar(player);
                    }
                } else if (id == 2454) {
                    int hatId = player.getEquipment().getHatId();
                    if (hatId == Runecrafting.WATER_TIARA
                            || hatId == Runecrafting.OMNI_TIARA
                            || player.getInventory().containsItem(1444, 1)) {
                        Runecrafting.enterWaterAltar(player);
                    }
                } else if (id == 2457) {
                    int hatId = player.getEquipment().getHatId();
                    if (hatId == Runecrafting.BODY_TIARA
                            || hatId == Runecrafting.OMNI_TIARA
                            || player.getInventory().containsItem(1446, 1)) {
                        Runecrafting.enterBodyAltar(player);
                    }
                } else if (id == 2453) {
                    int hatId = player.getEquipment().getHatId();
                    if (hatId == Runecrafting.MIND_TIARA
                            || hatId == Runecrafting.OMNI_TIARA
                            || player.getInventory().containsItem(1448, 1)) {
                        Runecrafting.enterMindAltar(player);
                    }
                } else if (id == 47120) { // zaros altar
                    // recharge if needed
                    if (player.getPrayer().getPrayerpoints() < player
                            .getSkills().getLevelForXp(Skills.PRAYER) * 10) {
                        player.lock(12);
                        player.setNextAnimation(new Animation(12563));
                        player.getPrayer().setPrayerpoints(
                                (int) ((player.getSkills().getLevelForXp(
                                Skills.PRAYER) * 10) * 1.15));
                        player.getPrayer().refreshPrayerPoints();
                    }
                    player.getDialogueManager().startDialogue("ZarosAltar");
                } else if (id == 19222) {
                    Falconry.beginFalconry(player);
                } else if (id == 36786) {
                    player.getDialogueManager().startDialogue("Banker", 4907);
                } else if (id == 42377 || id == 42378) {
                    player.getDialogueManager().startDialogue("Banker", 2759);
                } else if (id == 42217 || id == 782 || id == 34752) {
                    player.getDialogueManager().startDialogue("Banker", 553);
                } else if (id == 57437) {
                    player.getBank().openBank();
                } else if (id == 42425 && object.getX() == 3220
                        && object.getY() == 3222) { // zaros portal
                    player.useStairs(10256, new WorldTile(3353, 3416, 0), 4, 5,
                            "And you find yourself into a digsite.");
                    player.addWalkSteps(3222, 3223, -1, false);
                    player.getPackets().sendGameMessage(
                            "You examine portal and it aborves you...");
                } else if (id == 9356) {
                    FightCaves.enterFightCaves(player);
                } else if (id == 68107) {
                    FightKiln.enterFightKiln(player, false);
                } else if (id == 68223) {
                    FightPits.enterLobby(player, false);
                } else if (id == 46500 && object.getX() == 3351 && object.getY() == 3415) { // zaros portal
                    player.useStairs(-1, new WorldTile(
                            Settings.RESPAWN_PLAYER_LOCATION.getX(),
                            Settings.RESPAWN_PLAYER_LOCATION.getY(),
                            Settings.RESPAWN_PLAYER_LOCATION.getPlane()), 2, 3,
                            "You found your way back to home.");
                    player.addWalkSteps(3351, 3415, -1, false);
                } else if (id == 9293) {
                    if (player.getSkills().getLevel(Skills.AGILITY) < 70) {
                        player.getPackets()
                                .sendGameMessage(
                                "You need an agility level of 70 to use this obstacle.",
                                true);
                        return;
                    }
                    int x = player.getX() == 2886 ? 2892 : 2886;
                    WorldTasksManager.schedule(new WorldTask() {
                        int count = 0;

                        @Override
                        public void run() {
                            player.setNextAnimation(new Animation(844));
                            if (count++ == 1) {
                                stop();
                            }
                        }
                    }, 0, 0);
                    player.setNextForceMovement(new ForceMovement(
                            new WorldTile(x, 9799, 0), 3,
                            player.getX() == 2886 ? 1 : 3));
                    player.useStairs(-1, new WorldTile(x, 9799, 0), 3, 4);
                } else if (id == 29370 && (object.getX() == 3150 || object.getX() == 3153) && object.getY() == 9906) { // edgeville dungeon cut
                    if (player.getSkills().getLevel(Skills.AGILITY) < 53) {
                        player.getPackets().sendGameMessage("You need an agility level of 53 to use this obstacle.");
                        return;
                    }
                    final boolean running = player.getRun();
                    player.setRunHidden(false);
                    player.lock(8);
                    player.addWalkSteps(x == 3150 ? 3155 : 3149, 9906, -1, false);
                    player.getPackets().sendGameMessage("You pulled yourself through the pipes.", true);
                    WorldTasksManager.schedule(new WorldTask() {
                        boolean secondloop;

                        @Override
                        public void run() {
                            if (!secondloop) {
                                secondloop = true;
                                player.getAppearence().setRenderEmote(295);
                            } else {
                                player.getAppearence().setRenderEmote(-1);
                                player.setRunHidden(running);
                                player.getSkills().addXp(Skills.AGILITY, 7);
                                stop();
                            }
                        }
                    }, 0, 5);
                } //start forinthry dungeon
                else if (id == 18341 && object.getX() == 3036 && object.getY() == 10172) {
                    player.useStairs(-1, new WorldTile(3039, 3765, 0), 0, 1);
                } else if (id == 20599 && object.getX() == 3038 && object.getY() == 3761) {
                    player.useStairs(-1, new WorldTile(3037, 10171, 0), 0, 1);
                } else if (id == 18342 && object.getX() == 3075 && object.getY() == 10057) {
                    player.useStairs(-1, new WorldTile(3071, 3649, 0), 0, 1);
                } else if (id == 20600 && object.getX() == 3072 && object.getY() == 3648) {
                    player.useStairs(-1, new WorldTile(3077, 10058, 0), 0, 1);
                } //nomads requiem
                else if (id == 18425 && !player.getQuestManager().completedQuest(Quests.NOMADS_REQUIEM)) {
                    NomadsRequiem.enterNomadsRequiem(player);
                } else if (id == 42219) {
                    player.useStairs(-1, new WorldTile(1886, 3178, 0), 0, 1);
                    if (player.getQuestManager().getQuestStage(Quests.NOMADS_REQUIEM) == -2) //for now, on future talk with npc + quest reqs
                    {
                        player.getQuestManager().setQuestStageAndRefresh(Quests.NOMADS_REQUIEM, 0);
                    }
                } else if (id == 8689) {
                    player.getActionManager().setAction(new CowMilkingAction());
                } else if (id == 42220) {
                    player.useStairs(-1, new WorldTile(3082, 3475, 0), 0, 1);
                } //start falador mininig
                else if (id == 30942 && object.getX() == 3019 && object.getY() == 3450) {
                    player.useStairs(828, new WorldTile(3020, 9850, 0), 1, 2);
                } else if (id == 6226 && object.getX() == 3019 && object.getY() == 9850) {
                    player.useStairs(833, new WorldTile(3018, 3450, 0), 1, 2);
                } else if (id == 31002 && player.getQuestManager().completedQuest(Quests.PERIL_OF_ICE_MONTAINS)) {
                    player.useStairs(833, new WorldTile(2998, 3452, 0), 1, 2);
                } else if (id == 31012 && player.getQuestManager().completedQuest(Quests.PERIL_OF_ICE_MONTAINS)) {
                    player.useStairs(828, new WorldTile(2996, 9845, 0), 1, 2);
                } else if (id == 30943 && object.getX() == 3059 && object.getY() == 9776) {
                    player.useStairs(-1, new WorldTile(3061, 3376, 0), 0, 1);
                } else if (id == 30944 && object.getX() == 3059 && object.getY() == 3376) {
                    player.useStairs(-1, new WorldTile(3058, 9776, 0), 0, 1);
                } else if (id == 2112 && object.getX() == 3046 && object.getY() == 9756) {
                    if (player.getSkills().getLevelForXp(Skills.MINING) < 60) {
                        player.getDialogueManager().startDialogue("SimpleNPCMessage", MiningGuildDwarf.getClosestDwarfID(player), "Sorry, but you need level 60 Mining to go in there.");
                        return;
                    }
                    WorldObject openedDoor = new WorldObject(object.getId(),
                            object.getType(), object.getRotation() - 1,
                            object.getX(), object.getY() + 1, object.getPlane());
                    if (World.removeTemporaryObject(object, 1200, false)) {
                        World.spawnTemporaryObject(openedDoor, 1200, false);
                        player.lock(2);
                        player.stopAll();
                        player.addWalkSteps(
                                3046, player.getY() > object.getY() ? object.getY()
                                : object.getY() + 1, -1, false);
                    }
                } else if (id == 2113) {
                    if (player.getSkills().getLevelForXp(Skills.MINING) < 60) {
                        player.getDialogueManager().startDialogue("SimpleNPCMessage", MiningGuildDwarf.getClosestDwarfID(player), "Sorry, but you need level 60 Mining to go in there.");
                        return;
                    }
                    player.useStairs(-1, new WorldTile(3021, 9739, 0), 0, 1);
                } else if (id == 6226 && object.getX() == 3019 && object.getY() == 9740) {
                    player.useStairs(828, new WorldTile(3019, 3341, 0), 1, 2);
                } else if (id == 6226 && object.getX() == 3019 && object.getY() == 9738) {
                    player.useStairs(828, new WorldTile(3019, 3337, 0), 1, 2);
                } else if (id == 6226 && object.getX() == 3018 && object.getY() == 9739) {
                    player.useStairs(828, new WorldTile(3017, 3339, 0), 1, 2);
                } else if (id == 6226 && object.getX() == 3020 && object.getY() == 9739) {
                    player.useStairs(828, new WorldTile(3021, 3339, 0), 1, 2);
                } else if (id == 30963) {
                    player.getBank().openBank();
                } else if (id == 6045) {
                    player.getPackets().sendGameMessage("You search the cart but find nothing.");
                } else if (id == 5906) {
                    if (player.getSkills().getLevel(Skills.AGILITY) < 42) {
                        player.getPackets().sendGameMessage("You need an agility level of 42 to use this obstacle.");
                        return;
                    }
                    player.lock();
                    WorldTasksManager.schedule(new WorldTask() {
                        int count = 0;

                        @Override
                        public void run() {
                            if (count == 0) {
                                player.setNextAnimation(new Animation(2594));
                                WorldTile tile = new WorldTile(object.getX() + (object.getRotation() == 2 ? -2 : +2), object.getY(), 0);
                                player.setNextForceMovement(new ForceMovement(tile, 4, Utils.getMoveDirection(tile.getX() - player.getX(), tile.getY() - player.getY())));
                            } else if (count == 2) {
                                WorldTile tile = new WorldTile(object.getX() + (object.getRotation() == 2 ? -2 : +2), object.getY(), 0);
                                player.setNextWorldTile(tile);
                            } else if (count == 5) {
                                player.setNextAnimation(new Animation(2590));
                                WorldTile tile = new WorldTile(object.getX() + (object.getRotation() == 2 ? -5 : +5), object.getY(), 0);
                                player.setNextForceMovement(new ForceMovement(tile, 4, Utils.getMoveDirection(tile.getX() - player.getX(), tile.getY() - player.getY())));
                            } else if (count == 7) {
                                WorldTile tile = new WorldTile(object.getX() + (object.getRotation() == 2 ? -5 : +5), object.getY(), 0);
                                player.setNextWorldTile(tile);
                            } else if (count == 10) {
                                player.setNextAnimation(new Animation(2595));
                                WorldTile tile = new WorldTile(object.getX() + (object.getRotation() == 2 ? -6 : +6), object.getY(), 0);
                                player.setNextForceMovement(new ForceMovement(tile, 4, Utils.getMoveDirection(tile.getX() - player.getX(), tile.getY() - player.getY())));
                            } else if (count == 12) {
                                WorldTile tile = new WorldTile(object.getX() + (object.getRotation() == 2 ? -6 : +6), object.getY(), 0);
                                player.setNextWorldTile(tile);
                            } else if (count == 14) {
                                stop();
                                player.unlock();
                            }
                            count++;
                        }
                    }, 0, 0);
                    //BarbarianOutpostAgility start
                } else if (id == 20210) {
                    BarbarianOutpostAgility.enterObstaclePipe(player, object);
                } else if (id == 43526) {
                    BarbarianOutpostAgility.swingOnRopeSwing(player, object);
                } else if (id == 43595 && x == 2550 && y == 3546) {
                    BarbarianOutpostAgility.walkAcrossLogBalance(player, object);
                } else if (id == 20211 && x == 2538 && y == 3545) {
                    BarbarianOutpostAgility.climbObstacleNet(player, object);
                } else if (id == 2302 && x == 2535 && y == 3547) {
                    BarbarianOutpostAgility.walkAcrossBalancingLedge(player, object);
                } else if (id == 1948) {
                    BarbarianOutpostAgility.climbOverCrumblingWall(player, object);
                } else if (id == 43533) {
                    BarbarianOutpostAgility.runUpWall(player, object);
                } else if (id == 43597) {
                    BarbarianOutpostAgility.climbUpWall(player, object);
                } else if (id == 43587) {
                    BarbarianOutpostAgility.fireSpringDevice(player, object);
                } else if (id == 43527) {
                    BarbarianOutpostAgility.crossBalanceBeam(player, object);
                } else if (id == 43531) {
                    BarbarianOutpostAgility.jumpOverGap(player, object);
                } else if (id == 43532) {
                    BarbarianOutpostAgility.slideDownRoof(player, object);
                } //rock living caverns
                else if (id == 45077) {
                    player.lock();
                    if (player.getX() != object.getX() || player.getY() != object.getY()) {
                        player.addWalkSteps(object.getX(), object.getY(), -1, false);
                    }
                    WorldTasksManager.schedule(new WorldTask() {
                        private int count;

                        @Override
                        public void run() {
                            if (count == 0) {
                                player.setNextFaceWorldTile(new WorldTile(object.getX() - 1, object.getY(), 0));
                                player.setNextAnimation(new Animation(12216));
                                player.unlock();
                            } else if (count == 2) {
                                player.setNextWorldTile(new WorldTile(3651, 5122, 0));
                                player.setNextFaceWorldTile(new WorldTile(3651, 5121, 0));
                                player.setNextAnimation(new Animation(12217));
                            } else if (count == 3) {
                                //TODO find emote
                                //player.getPackets().sendObjectAnimation(new WorldObject(45078, 0, 3, 3651, 5123, 0), new Animation(12220));
                            } else if (count == 5) {
                                player.unlock();
                                stop();
                            }
                            count++;
                        }
                    }, 1, 0);
                } else if (id == 45076) {
                    player.getActionManager().setAction(new Mining(object, RockDefinitions.LRC_Gold_Ore));
                } else if (id == 5999) {
                    player.getActionManager().setAction(new Mining(object, RockDefinitions.LRC_Coal_Ore));
                } else if (id == 45078) {
                    player.useStairs(2413, new WorldTile(3012, 9832, 0), 2, 2);
                } else if (id == 45079) {
                    player.getBank().openDepositBox();
                } //champion guild
                else if (id == 24357 && object.getX() == 3188 && object.getY() == 3355) {
                    player.useStairs(-1, new WorldTile(3189, 3354, 1), 0, 1);
                } else if (id == 24359 && object.getX() == 3188 && object.getY() == 3355) {
                    player.useStairs(-1, new WorldTile(3189, 3358, 0), 0, 1);
                } else if (id == 1805 && object.getX() == 3191 && object.getY() == 3363) {
                    WorldObject openedDoor = new WorldObject(object.getId(),
                            object.getType(), object.getRotation() - 1,
                            object.getX(), object.getY(), object.getPlane());
                    if (World.removeTemporaryObject(object, 1200, false)) {
                        World.spawnTemporaryObject(openedDoor, 1200, false);
                        player.lock(2);
                        player.stopAll();
                        player.addWalkSteps(
                                3191, player.getY() >= object.getY() ? object.getY() - 1
                                : object.getY(), -1, false);
                        if (player.getY() >= object.getY()) {
                            player.getDialogueManager().startDialogue("SimpleNPCMessage", 198, "Greetings bolt adventurer. Welcome to the guild of", "Champions.");
                        }
                    }
                } //start of varrock dungeon
                else if (id == 29355 && object.getX() == 3230 && object.getY() == 9904) //varrock dungeon climb to bear
                {
                    player.useStairs(828, new WorldTile(3229, 3503, 0), 1, 2);
                } else if (id == 24264) {
                    player.useStairs(833, new WorldTile(3229, 9904, 0), 1, 2);
                } else if (id == 24366) {
                    player.useStairs(828, new WorldTile(3237, 3459, 0), 1, 2);
                } else if (id == 882 && object.getX() == 3237 && object.getY() == 3458) {
                    player.useStairs(833, new WorldTile(3237, 9858, 0), 1, 2);
                } else if (id == 29355 && object.getX() == 3097 && object.getY() == 9867) //edge dungeon climb
                {
                    player.useStairs(828, new WorldTile(3096, 3468, 0), 1, 2);
                } else if (id == 26934) {
                    player.useStairs(833, new WorldTile(3097, 9868, 0), 1, 2);
                } else if (id == 29355 && object.getX() == 3088 && object.getY() == 9971) {
                    player.useStairs(828, new WorldTile(3087, 3571, 0), 1, 2);
                } else if (id == 65453) {
                    player.useStairs(833, new WorldTile(3089, 9971, 0), 1, 2);
                } else if (id == 12389 && object.getX() == 3116 && object.getY() == 3452) {
                    player.useStairs(833, new WorldTile(3117, 9852, 0), 1, 2);
                } else if (id == 29355 && object.getX() == 3116 && object.getY() == 9852) {
                    player.useStairs(833, new WorldTile(3115, 3452, 0), 1, 2);
                } else if (id == 69526) {
                    GnomeAgility.walkGnomeLog(player);
                } else if (id == 69383) {
                    GnomeAgility.climbGnomeObstacleNet(player);
                } else if (id == 69508) {
                    GnomeAgility.climbUpGnomeTreeBranch(player);
                } else if (id == 2312) {
                    GnomeAgility.walkGnomeRope(player);
                } else if (id == 4059) {
                    GnomeAgility.walkBackGnomeRope(player);
                } else if (id == 69507) {
                    GnomeAgility.climbDownGnomeTreeBranch(player);
                } else if (id == 69384) {
                    GnomeAgility.climbGnomeObstacleNet2(player);
                } else if (id == 69377 || id == 69378) {
                    GnomeAgility.enterGnomePipe(player, object.getX(), object.getY());
                } else if (Wilderness.isDitch(id)) {// wild ditch
                    player.getDialogueManager().startDialogue(
                            "WildernessDitch", object);
                } else if (id == 42611) {// Magic Portal
                    player.getDialogueManager().startDialogue("MagicPortal");
                } else if (object.getDefinitions().name.equalsIgnoreCase("Obelisk") && object.getY() > 3525) {
                    //Who the fuck removed the controler class and the code from SONIC!!!!!!!!!!
                    //That was an hour of collecting coords :fp: Now ima kill myself.
                } else if (id == 27254) {// Edgeville portal
                    player.getPackets().sendGameMessage(
                            "You enter the portal...");
                    player.useStairs(10584, new WorldTile(3087, 3488, 0), 2, 3,
                            "..and are transported to Edgeville.");
                    player.addWalkSteps(1598, 4506, -1, false);
                } else if (id == 12202) {// mole entrance
                    if (!player.getInventory().containsItem(952, 1)) {
                        player.getPackets().sendGameMessage("You need a spade to dig this.");
                        return;
                    }
                    if (player.getX() != object.getX() || player.getY() != object.getY()) {
                        player.lock();
                        player.addWalkSteps(object.getX(), object.getY());
                        WorldTasksManager.schedule(new WorldTask() {
                            @Override
                            public void run() {
                                InventoryOptionsHandler.dig(player);
                            }
                        }, 1);
                    } else {
                        InventoryOptionsHandler.dig(player);
                    }
                } else if (id == 12230 && object.getX() == 1752 && object.getY() == 5136) {// mole exit 
                    player.setNextWorldTile(new WorldTile(2986, 3316, 0));
                } else if (id == 15522) {// portal sign
                    if (player.withinDistance(new WorldTile(1598, 4504, 0), 1)) {// PORTAL
                        // 1
                        player.getInterfaceManager().sendInterface(327);
                        player.getPackets().sendIComponentText(327, 13,
                                "Edgeville");
                        player.getPackets()
                                .sendIComponentText(
                                327,
                                14,
                                "This portal will take you to edgeville. There "
                                + "you can multi pk once past the wilderness ditch.");
                    }
                    if (player.withinDistance(new WorldTile(1598, 4508, 0), 1)) {// PORTAL
                        // 2
                        player.getInterfaceManager().sendInterface(327);
                        player.getPackets().sendIComponentText(327, 13,
                                "Mage Bank");
                        player.getPackets()
                                .sendIComponentText(
                                327,
                                14,
                                "This portal will take you to the mage bank. "
                                + "The mage bank is a 1v1 deep wilderness area.");
                    }
                    if (player.withinDistance(new WorldTile(1598, 4513, 0), 1)) {// PORTAL
                        // 3
                        player.getInterfaceManager().sendInterface(327);
                        player.getPackets().sendIComponentText(327, 13,
                                "Magic's Portal");
                        player.getPackets()
                                .sendIComponentText(
                                327,
                                14,
                                "This portal will allow you to teleport to areas that "
                                + "will allow you to change your magic spell book.");
                    }
                } else if (id == 38811 || id == 37929) {// corp beast
                    if (object.getX() == 2971 && object.getY() == 4382) {
                        player.getInterfaceManager().sendInterface(650);
                    } else if (object.getX() == 2918 && object.getY() == 4382) {
                        player.stopAll();
                        player.setNextWorldTile(new WorldTile(
                                player.getX() == 2921 ? 2917 : 2921, player
                                .getY(), player.getPlane()));
                    }
                } else if (id == 37928 && object.getX() == 2883
                        && object.getY() == 4370) {
                    player.stopAll();
                    player.setNextWorldTile(new WorldTile(3214, 3782, 0));
                    player.getControlerManager().startControler("Wilderness");
                } else if (id == 38815 && object.getX() == 3209
                        && object.getY() == 3780 && object.getPlane() == 0) {
                    if (player.getSkills().getLevelForXp(Skills.WOODCUTTING) < 37
                            || player.getSkills().getLevelForXp(Skills.MINING) < 45
                            || player.getSkills().getLevelForXp(
                            Skills.SUMMONING) < 23
                            || player.getSkills().getLevelForXp(
                            Skills.FIREMAKING) < 47
                            || player.getSkills().getLevelForXp(Skills.PRAYER) < 55) {
                        player.getPackets()
                                .sendGameMessage(
                                "You need 23 Summoning, 37 Woodcutting, 45 Mining, 47 Firemaking and 55 Prayer to enter this dungeon.");
                        return;
                    }
                    player.stopAll();
                    player.setNextWorldTile(new WorldTile(2885, 4372, 0));
                    player.getControlerManager().forceStop();
                    // TODO all reqs, skills not added
                } else if (id == 9369) {
                    player.getControlerManager().startControler("FightPits");
                } else if (id == 2079) {
                    CrystalChest.searchChest(player);
                } else if (id == 20600) {
                    player.setNextWorldTile(new WorldTile(3077, 10058, 0));
                } else if (id == 18342) {
                    player.setNextWorldTile(new WorldTile(3071, 3649, 0));
                } else if (id == 52859) {
                    Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(1297, 4510, 0), new int[0]);
                    player.getPackets().sendGameMessage("Have fun hunting Frost Dragons.", true);
                } else if (id == 2475) {
                    Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(3186, 5725, 0), new int[0]);
                    player.getControlerManager().startControler("FunPk");
                } else if (id == 4493) {
                    player.setNextWorldTile(new WorldTile(3433, 3538, 1));
                } else if (id == 4494) {
                    player.setNextWorldTile(new WorldTile(3438, 3538, 0));
                } else if (id == 4496) {
                    player.setNextWorldTile(new WorldTile(3412, 3541, 1));
                } else if (id == 4495) {
                    player.setNextWorldTile(new WorldTile(3417, 3541, 2));
                } else if (id == 9319) {
                    player.setNextAnimation(new Animation(828));
                    if (object.getX() == 3447 && object.getY() == 3576 && object.getPlane() == 1) {
                        player.setNextWorldTile(new WorldTile(3446, 3576, 2));
                    }
                    if (object.getX() == 3422 && object.getY() == 3550 && object.getPlane() == 0) {
                        player.setNextWorldTile(new WorldTile(3422, 3551, 1));
                    }
                    player.stopAll();
                } else if (id == 9320) {
                    player.setNextAnimation(new Animation(828));
                    if (object.getX() == 3447 && object.getY() == 3576 && object.getPlane() == 2) {
                        player.setNextWorldTile(new WorldTile(3446, 3576, 1));
                    }
                    if (object.getX() == 3422 && object.getY() == 3550 && object.getPlane() == 1) {
                        player.setNextWorldTile(new WorldTile(3422, 3551, 0));
                    }
                    player.stopAll();
                } else if (id == 52875) {
                    Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(3033, 9598, 0), new int[0]);
                } else if (id == 54019 || id == 54020 || id == 55301) {
                    PkRank.showRanks(player);
                } else if (id == 1817 && object.getX() == 2273
                        && object.getY() == 4680) { // kbd lever
                    Magic.pushLeverTeleport(player, new WorldTile(3067, 10254,
                            0));
                } else if (id == 1816 && object.getX() == 3067
                        && object.getY() == 10252) { // kbd out lever
                    Magic.pushLeverTeleport(player,
                            new WorldTile(2273, 4681, 0));
                } else if (id == 32015 && object.getX() == 3069
                        && object.getY() == 10256) { // kbd stairs
                    player.useStairs(828, new WorldTile(3017, 3848, 0), 1, 2);
                    player.getControlerManager().startControler("Wilderness");
                } else if (id == 1765 && object.getX() == 3017
                        && object.getY() == 3849) { // kbd out stairs
                    player.stopAll();
                    player.setNextWorldTile(new WorldTile(3069, 10255, 0));
                    player.getControlerManager().forceStop();
                } else if (id == 48496) {
					player.getDialogueManager().startDialogue("EnteringDung");
                } else if (id == 14315) {
                    player.getControlerManager().startControler("PestControlLobby", 1);
                } else if (id == 5959) {
                    Magic.pushLeverTeleport(player,
                            new WorldTile(2539, 4712, 0));
                } else if (id == 5960) {
                    Magic.pushLeverTeleport(player,
                            new WorldTile(3089, 3957, 0));
                } else if (id == 1814) {
                    Magic.pushLeverTeleport(player,
                            new WorldTile(3155, 3923, 0));
                } else if (id == 1815) {
                    Magic.pushLeverTeleport(player,
                            new WorldTile(2561, 3311, 0));
                } else if (id == 62675) {
                    player.getCutscenesManager().play("DTPreview");
                } else if (id == 62681) {
                    player.getDominionTower().viewScoreBoard();
                } else if (id == 62678 || id == 62679) {
                    player.getDominionTower().openModes();
                } else if (id == 62688) {
                    player.getDialogueManager().startDialogue("DTClaimRewards");
                } else if (id == 62677) {
                    player.getDominionTower().talkToFace();
                } else if (id == 62680) {
                    player.getDominionTower().openBankChest();
                } else if (id == 48797) {
                    player.useStairs(-1, new WorldTile(3877, 5526, 1), 0, 1);
                } else if (id == 48798) {
                    player.useStairs(-1, new WorldTile(3246, 3198, 0), 0, 1);
                } else if (id == 48678 && x == 3858 && y == 5533) {
                    player.useStairs(-1, new WorldTile(3861, 5533, 0), 0, 1);
                } else if (id == 48678 && x == 3858 && y == 5543) {
                    player.useStairs(-1, new WorldTile(3861, 5543, 0), 0, 1);
                } else if (id == 48678 && x == 3858 && y == 5533) {
                    player.useStairs(-1, new WorldTile(3861, 5533, 0), 0, 1);
                } else if (id == 48677 && x == 3858 && y == 5543) {
                    player.useStairs(-1, new WorldTile(3856, 5543, 1), 0, 1);
                } else if (id == 48677 && x == 3858 && y == 5533) {
                    player.useStairs(-1, new WorldTile(3856, 5533, 1), 0, 1);
                } else if (id == 48679) {
                    player.useStairs(-1, new WorldTile(3875, 5527, 1), 0, 1);
                } else if (id == 48688) {
                    player.useStairs(-1, new WorldTile(3972, 5565, 0), 0, 1);
                } else if (id == 48683) {
                    player.useStairs(-1, new WorldTile(3868, 5524, 0), 0, 1);
                } else if (id == 48682) {
                    player.useStairs(-1, new WorldTile(3869, 5524, 0), 0, 1);
                } else if (id == 62676) { // dominion exit
                    player.useStairs(-1, new WorldTile(3374, 3093, 0), 0, 1);
                } else if (id == 62674) { // dominion entrance
                    player.useStairs(-1, new WorldTile(3744, 6405, 0), 0, 1);
                } else if (id == 3192) {
                    PkRank.showRanks(player);
                } else if (id == 65349) {
                    player.useStairs(-1, new WorldTile(3044, 10325, 0), 0, 1);
                } else if (id == 32048 && object.getX() == 3043 && object.getY() == 10328) {
                    player.useStairs(-1, new WorldTile(3045, 3927, 0), 0, 1);
                }
                else if (id == 26194) {
                    player.getDialogueManager().startDialogue("PartyRoomLever");
                } else if (id == 61190 || id == 61191 || id == 61192 || id == 61193) {
                    if (objectDef.containsOption(0, "Chop down")) {
                        player.getActionManager().setAction(
                                new Woodcutting(object,
                                TreeDefinitions.NORMAL));
                    }
                } else if (id == 20573) {
                    player.getControlerManager().startControler("RefugeOfFear");
                } //crucible
                else if (id == 67050) {
                    player.useStairs(-1, new WorldTile(3359, 6110, 0), 0, 1);
                } else if (id == 67053) {
                    player.useStairs(-1, new WorldTile(3120, 3519, 0), 0, 1);
                } else if (id == 67051) {
                    player.getDialogueManager().startDialogue("Marv", false);
                } else if (id == 67052) {
                    Crucible.enterCrucibleEntrance(player);
                } else {
                    switch (objectDef.name.toLowerCase()) {
                        case "dummy":
                            player.sm("You seem to have already experience of combat, you don't need to hurt dummies.");
                            break;
                        case "hay bales":
                            if (objectDef.containsOption(0, "Search")) {
                                player.sm("You search and you've found some...");
                                player.lock(2);
                                Searchables.SearchBales(player);
                            }
                            break;
                        case "trapdoor":
                        case "manhole":
                            if (objectDef.containsOption(0, "Open")) {
                                WorldObject openedHole = new WorldObject(object.getId() + 1,
                                        object.getType(), object.getRotation(), object.getX(),
                                        object.getY(), object.getPlane());
                                //if (World.removeTemporaryObject(object, 60000, true)) {
                                player.faceObject(openedHole);
                                World.spawnTemporaryObject(openedHole, 60000, true);
                                //}
                            }
                            break;
                        case "closed chest":
                            if (objectDef.containsOption(0, "Open")) {
                                player.setNextAnimation(new Animation(536));
                                player.lock(2);
                                WorldObject openedChest = new WorldObject(object.getId() + 1,
                                        object.getType(), object.getRotation(), object.getX(),
                                        object.getY(), object.getPlane());
                                //if (World.removeTemporaryObject(object, 60000, true)) {
                                player.faceObject(openedChest);
                                World.spawnTemporaryObject(openedChest, 60000, true);
                                //}
                            }
                            break;
                        case "open chest":
                            if (objectDef.containsOption(0, "Search")) {
                                player.getPackets().sendGameMessage("You search the chest but find nothing.");
                            }
                            break;
                        case "spiderweb":
                            if (object.getRotation() == 2) {
                                player.lock(2);
                                if (Utils.getRandom(1) == 0) {
                                    player.addWalkSteps(player.getX(), player.getY() < y ? object.getY() + 2 : object.getY() - 1, -1, false);
                                    player.getPackets().sendGameMessage("You squeeze though the web.");
                                } else {
                                    player.getPackets().sendGameMessage(
                                            "You fail to squeeze though the web; perhaps you should try again.");
                                }
                            }
                            break;
                        case "web":
                            if (objectDef.containsOption(0, "Slash")) {
                                player.setNextAnimation(new Animation(PlayerCombat
                                        .getWeaponAttackEmote(player.getEquipment()
                                        .getWeaponId(), player
                                        .getCombatDefinitions()
                                        .getAttackStyle())));
                                slashWeb(player, object);
                            }
                            break;
                        case "anvil":
                            if (objectDef.containsOption(0, "Smith")) {
                                ForgingBar bar = ForgingBar.getBar(player);
                                if (bar != null) {
                                    ForgingInterface.sendSmithingInterface(player, bar);
                                } else {
                                    player.getPackets().sendGameMessage("You have no bars which you have smithing level to use.");
                                }
                            }
                            break;
                        case "tin ore rocks":
                            player.getActionManager().setAction(
                                    new Mining(object, RockDefinitions.Tin_Ore));
                            break;
                        case "gold ore rocks":
                            player.getActionManager().setAction(
                                    new Mining(object, RockDefinitions.Gold_Ore));
                            break;
                        case "iron ore rocks":
                            player.getActionManager().setAction(
                                    new Mining(object, RockDefinitions.Iron_Ore));
                            break;
                        case "silver ore rocks":
                            player.getActionManager().setAction(
                                    new Mining(object, RockDefinitions.Silver_Ore));
                            break;
                        case "coal rocks":
                            player.getActionManager().setAction(
                                    new Mining(object, RockDefinitions.Coal_Ore));
                            break;
                        case "clay rocks":
                            player.getActionManager().setAction(
                                    new Mining(object, RockDefinitions.Clay_Ore));
                            break;
                        case "copper ore rocks":
                            player.getActionManager().setAction(
                                    new Mining(object, RockDefinitions.Copper_Ore));
                            break;
                        case "adamantite ore rocks":
                            player.getActionManager().setAction(
                                    new Mining(object, RockDefinitions.Adamant_Ore));
                            break;
                        case "runite ore rocks":
                            player.getActionManager().setAction(
                                    new Mining(object, RockDefinitions.Runite_Ore));
                            break;
                        case "granite rocks":
                            player.getActionManager().setAction(
                                    new Mining(object, RockDefinitions.Granite_Ore));
                            break;
                        case "sandstone rocks":
                            player.getActionManager().setAction(
                                    new Mining(object, RockDefinitions.Sandstone_Ore));
                            break;
                        case "mithril ore rocks":
                            player.getActionManager().setAction(new Mining(object, RockDefinitions.Mithril_Ore));
                            break;
                        case "crashed star":
                            player.getActionManager().setAction(new Mining(object, RockDefinitions.CRASHED_STAR));
                            break;
                        case "bank deposit box":
                            if (objectDef.containsOption(0, "Deposit")) {
                                player.getBank().openDepositBox();
                            }
                            break;
                        case "bank":
                        case "bank chest":
                        case "bank booth":
                        case "counter":
                            if (objectDef.containsOption(0, "Bank") || objectDef.containsOption(0, "Use")) {
                                player.getBank().openBank();
                            }
                            break;
                        // Woodcutting start
                        case "tree":
                            if (objectDef.containsOption(0, "Chop down")) {
                                player.getActionManager().setAction(
                                        new Woodcutting(object,
                                        TreeDefinitions.NORMAL));
                            }
                            break;
                        case "evergreen":
                            if (objectDef.containsOption(0, "Chop down")) {
                                player.getActionManager().setAction(
                                        new Woodcutting(object,
                                        TreeDefinitions.EVERGREEN));
                            }
                            break;
                        case "dead tree":
                            if (objectDef.containsOption(0, "Chop down")) {
                                player.getActionManager().setAction(
                                        new Woodcutting(object,
                                        TreeDefinitions.DEAD));
                            }
                            break;
                        case "oak":
                            if (objectDef.containsOption(0, "Chop down")) {
                                player.getActionManager()
                                        .setAction(
                                        new Woodcutting(object,
                                        TreeDefinitions.OAK));
                            }
                            break;
                        case "willow":
                            if (objectDef.containsOption(0, "Chop down")) {
                                player.getActionManager().setAction(
                                        new Woodcutting(object,
                                        TreeDefinitions.WILLOW));
                            }
                            break;
                        case "maple tree":
                            if (objectDef.containsOption(0, "Chop down")) {
                                player.getActionManager().setAction(
                                        new Woodcutting(object,
                                        TreeDefinitions.MAPLE));
                            }
                            break;
                        case "ivy":
                            if (objectDef.containsOption(0, "Chop")) {
                                player.getActionManager()
                                        .setAction(
                                        new Woodcutting(object,
                                        TreeDefinitions.IVY));
                            }
                            break;
                        case "yew":
                            if (objectDef.containsOption(0, "Chop down")) {
                                player.getActionManager()
                                        .setAction(
                                        new Woodcutting(object,
                                        TreeDefinitions.YEW));
                            }
                            break;
                        case "magic tree":
                            if (objectDef.containsOption(0, "Chop down")) {
                                player.getActionManager().setAction(
                                        new Woodcutting(object,
                                        TreeDefinitions.MAGIC));
                            }
                            break;
                        case "cursed magic tree":
                            if (objectDef.containsOption(0, "Chop down")) {
                                player.getActionManager().setAction(
                                        new Woodcutting(object,
                                        TreeDefinitions.CURSED_MAGIC));
                            }
                            break;
                        // Woodcutting end
                        case "gate":
                        case "large door":
                        case "metal door":
                            if (object.getType() == 0
                                    && objectDef.containsOption(0, "Open")) {
                                if (!handleGate(player, object)) {
                                    handleDoor(player, object);
                                }
                            }
                            break;
                        case "door":
                            if (object.getType() == 0
                                    && (objectDef.containsOption(0, "Open") || objectDef
                                    .containsOption(0, "Unlock"))) {
                                handleDoor(player, object);
                            }
                            break;
                        case "ladder":
                            if (object.getId() != 16148) {//ignore security dungeon ladder
                                if (object.getId() != 16080) {//ignore security dungeon ladder
                                    handleLadder(player, object, 1);
                                }
                            }
                            break;
                        case "staircase":
                            handleStaircases(player, object, 1);
                            break;
                        case "small obelisk":
                            if (objectDef.containsOption(0, "Renew-points")) {
                                int summonLevel = player.getSkills().getLevelForXp(Skills.SUMMONING);
                                if (player.getSkills().getLevel(Skills.SUMMONING) < summonLevel) {
                                    player.lock(3);
                                    player.setNextAnimation(new Animation(8502));
                                    player.getSkills().set(Skills.SUMMONING, summonLevel);
                                    player.getPackets().sendGameMessage(
                                            "You have recharged your Summoning points.", true);
                                } else {
                                    player.getPackets().sendGameMessage("You already have full Summoning points.");
                                }
                            }
                            break;
                        case "altar":
                            if (objectDef.containsOption(0, "Pray") || objectDef.containsOption(0, "Pray-at")) {
                                final int maxPrayer = player.getSkills()
                                        .getLevelForXp(Skills.PRAYER) * 10;
                                if (player.getPrayer().getPrayerpoints() < maxPrayer) {
                                    player.lock(5);
                                    player.getPackets().sendGameMessage(
                                            "You pray to the gods...", true);
                                    player.setNextAnimation(new Animation(645));
                                    WorldTasksManager.schedule(new WorldTask() {
                                        @Override
                                        public void run() {
                                            player.getPrayer().restorePrayer(
                                                    maxPrayer);
                                            player.getPackets()
                                                    .sendGameMessage(
                                                    "...and recharged your prayer.",
                                                    true);
                                        }
                                    }, 2);
                                } else {
                                    player.getPackets().sendGameMessage(
                                            "You already have full prayer.");
                                }
                                if (id == 6552) {
                                    player.getDialogueManager().startDialogue(
                                            "AncientAltar");
                                }
                            }
                            break;
                        case "bandos altar":
                            if (objectDef.containsOption(0, "Pray") || objectDef.containsOption(0, "Pray-at")) {
                                final int maxPrayer = player.getSkills()
                                        .getLevelForXp(Skills.PRAYER) * 10;
                                if (player.getPrayer().getPrayerpoints() < maxPrayer) {
                                    player.lock(5);
                                    player.getPackets().sendGameMessage(
                                            "You pray to the gods...", true);
                                    player.setNextAnimation(new Animation(645));
                                    WorldTasksManager.schedule(new WorldTask() {
                                        @Override
                                        public void run() {
                                            player.getPrayer().restorePrayer(
                                                    maxPrayer);
                                            player.getPackets()
                                                    .sendGameMessage(
                                                    "...and recharged your prayer.",
                                                    true);
                                        }
                                    }, 2);
                                } else {
                                    player.getPackets().sendGameMessage(
                                            "You already have full prayer.");
                                }
                                if (id == 6552) {
                                    player.getDialogueManager().startDialogue(
                                            "AncientAltar");
                                }
                            }
                            break;
                        case "armadyl altar":
                            if (objectDef.containsOption(0, "Pray") || objectDef.containsOption(0, "Pray-at")) {
                                final int maxPrayer = player.getSkills()
                                        .getLevelForXp(Skills.PRAYER) * 10;
                                if (player.getPrayer().getPrayerpoints() < maxPrayer) {
                                    player.lock(5);
                                    player.getPackets().sendGameMessage(
                                            "You pray to the gods...", true);
                                    player.setNextAnimation(new Animation(645));
                                    WorldTasksManager.schedule(new WorldTask() {
                                        @Override
                                        public void run() {
                                            player.getPrayer().restorePrayer(
                                                    maxPrayer);
                                            player.getPackets()
                                                    .sendGameMessage(
                                                    "...and recharged your prayer.",
                                                    true);
                                        }
                                    }, 2);
                                } else {
                                    player.getPackets().sendGameMessage(
                                            "You already have full prayer.");
                                }
                                if (id == 6552) {
                                    player.getDialogueManager().startDialogue(
                                            "AncientAltar");
                                }
                            }
                            break;
                        case "saradomin altar":
                            if (objectDef.containsOption(0, "Pray") || objectDef.containsOption(0, "Pray-at")) {
                                final int maxPrayer = player.getSkills()
                                        .getLevelForXp(Skills.PRAYER) * 10;
                                if (player.getPrayer().getPrayerpoints() < maxPrayer) {
                                    player.lock(5);
                                    player.getPackets().sendGameMessage(
                                            "You pray to the gods...", true);
                                    player.setNextAnimation(new Animation(645));
                                    WorldTasksManager.schedule(new WorldTask() {
                                        @Override
                                        public void run() {
                                            player.getPrayer().restorePrayer(
                                                    maxPrayer);
                                            player.getPackets()
                                                    .sendGameMessage(
                                                    "...and recharged your prayer.",
                                                    true);
                                        }
                                    }, 2);
                                } else {
                                    player.getPackets().sendGameMessage(
                                            "You already have full prayer.");
                                }
                                if (id == 6552) {
                                    player.getDialogueManager().startDialogue(
                                            "AncientAltar");
                                }
                            }
                            break;
                        case "zamorak altar":
                            if (objectDef.containsOption(0, "Pray") || objectDef.containsOption(0, "Pray-at")) {
                                final int maxPrayer = player.getSkills().getLevelForXp(Skills.PRAYER) * 10;
                                if (player.getPrayer().getPrayerpoints() < maxPrayer) {
                                    player.lock(5);
                                    player.getPackets().sendGameMessage("You pray to the gods...", true);
                                    player.setNextAnimation(new Animation(645));
                                    WorldTasksManager.schedule(new WorldTask() {
                                        @Override
                                        public void run() {
                                            player.getPrayer().restorePrayer(maxPrayer);
                                            player.getPackets().sendGameMessage( "...and recharged your prayer.", true);
                                        }
                                    }, 2);
                                } else {
                                    player.getPackets().sendGameMessage("You already have full prayer.");
                                }
                                if (id == 6552) {
                                    player.getDialogueManager().startDialogue("AncientAltar");
                                }
                            }
                            break;
                        default:

                            break;
                    }
                }
                if (Settings.DEBUG) {
                	Logger.log(player.getDisplayName(), "Object Click 1: " + id + " - " + object.getX() + ", " + object.getY() + ", " + object.getPlane() + " ("+object.getDefinitions().name+")");
                }
            }
        }, objectDef.getSizeX(), Wilderness.isDitch(id) ? 4 : objectDef
                .getSizeY(), object.getRotation()));
    }

    private static void handleOption2(final Player player, final WorldObject object) {
        final ObjectDefinitions objectDef = object.getDefinitions();
        final int id = object.getId();
        player.setCoordsEvent(new CoordsEvent(object, new Runnable() {
            @Override
            public void run() {
                player.stopAll();
                player.faceObject(object);
                if (!player.getControlerManager().processObjectClick2(object)) {
                    return;
                } else if (object.getDefinitions().containsOption("Smelt")) {
                    player.getDialogueManager().startDialogue("SmeltingD",  object);
                } else if (object.getDefinitions().name.equalsIgnoreCase("furnace")) {
                    player.getDialogueManager().startDialogue("SmeltingD",  object);
                } else if (id == 17010) {
                    player.getDialogueManager().startDialogue("LunarAltar");
                } else if (id == 6) {
                	player.getCannon().pickUpDwarfCannon(object);
                }

                // Farming handler "Option 2"
                for (int i = 0; i < FarmingSystem.farmingPatches.length; i++) {
                    if (object.getId() == FarmingSystem.farmingPatches[i]) {
                        FarmingSystem.inspectPatch(player, object);
                    }
                }

                // Snow Pile handling 66496
                if (object.getId() == 66496) {
                    player.lock(3);
                    player.getPackets().sendGameMessage("You decide doing that would just be silly.");
                    return;
                }
                
                if (id == 40444) {
                	int amount = 5;
                	if (player.getInventory().getFreeSlots() < 5) {
                		amount = player.getInventory().getFreeSlots();
                	}
                	if (player.getInventory().getFreeSlots() < 1) {
                		player.getPackets().sendGameMessage("Not enough space in your inventory.");
                		return;
                	}
                	player.getInventory().addItem(1511, amount);
                	player.sendMessage("You take some logs from the pile...");
                	player.setNextAnimation(new Animation(881));
                	player.lock(2);
                	player.lividfarm = false;
                	return;
                }
                
                if (id == 4874 || id == 4875 || id == 4876 || id == 4877 || id == 4878) {
                	if (player.getInventory().getFreeSlots() < 1) {
                        player.getPackets().sendGameMessage("Not enough space in your inventory.");
                        return;
                    }
                	player.getStalls().handleStall(player, id);
                	return;
                }

                if (id == 29394) {
                    ArtisanWorkshop.DepositIngots(player);
                }
                if (id == 29395) {
                    ArtisanWorkshop.DepositIngots(player);
                } else if (id == 62677) {
                    player.getDominionTower().openRewards();
                } else if (id == 62688) {
                    player.getDialogueManager().startDialogue(
                            "SimpleMessage",
                            "You have a Dominion Factor of "
                            + player.getDominionTower()
                            .getDominionFactor() + ".");
                } else if (id == 68107) {
                    FightKiln.enterFightKiln(player, true);
                } else if (id == 34384 || id == 34383 || id == 14011
                        || id == 7053 || id == 34387 || id == 34386
                        || id == 34385) {
                    Thieving.handleStalls(player, object);
                } else if (id == 2418) {
                    PartyRoom.openPartyChest(player);
                } else if (id == 2646) {
                    World.removeTemporaryObject(object, 50000, true);
                    player.getInventory().addItem(1779, 1);
                    //crucible
                } else if (id == 67051) {
                    player.getDialogueManager().startDialogue("Marv", true);
                } else if (object.getDefinitions().name.equalsIgnoreCase("Crashed star")) {
                    player.getPackets().sendGameMessage("The current size of the star is "+(ShootingStar.stage)+ ".");
                } else {
                    switch (objectDef.name.toLowerCase()) {
                        case "cabbage":
                            if (objectDef.containsOption(1, "Pick") && player.getInventory().addItem(1965, 1)) {
                                player.setNextAnimation(new Animation(827));
                                player.lock(2);
                                World.removeTemporaryObject(object, 60000, false);
                            }
                            break;
                        case "wheat":
                            if (objectDef.containsOption(1, "Pick") && player.getInventory().addItem(1947, 1)) {
                                player.setNextAnimation(new Animation(827));
                                player.lock(2);
                                World.removeTemporaryObject(object, 60000, false);
                            }
                            break;
                        case "potato":
                            if (objectDef.containsOption(1, "Pick") && player.getInventory().addItem(1942, 1)) {
                                player.setNextAnimation(new Animation(827));
                                player.lock(2);
                                World.removeTemporaryObject(object, 60000, false);
                            }
                            break;
                        case "flax":
                            if (objectDef.containsOption(1, "Pick") && player.getInventory().addItem(1779, 1)) {
                                player.setNextAnimation(new Animation(827));
                                player.lock(2);
                                World.removeTemporaryObject(object, 60000, false);
                            }
                            break;
                        case "onion":
                            if (objectDef.containsOption(1, "Pick") && player.getInventory().addItem(1957, 1)) {
                                player.setNextAnimation(new Animation(827));
                                player.lock(2);
                                World.removeTemporaryObject(object, 60000, false);
                            }
                            break;
                        case "bank":
                        case "bank chest":
                        case "bank booth":
                        case "counter":
                            if (objectDef.containsOption(1, "Bank")) {
                                player.getBank().openBank();
                            }
                            break;
                        case "gates":
                        case "gate":
                        case "metal door":
                            if (object.getType() == 0
                                    && objectDef.containsOption(1, "Open")) {
                                handleGate(player, object);
                            }
                            break;
                        case "door":
                            if (object.getType() == 0
                                    && objectDef.containsOption(1, "Open")) {
                                handleDoor(player, object);
                            }
                            break;
                        case "ladder":
                            handleLadder(player, object, 2);
                            break;
                        case "staircase":
                            handleStaircases(player, object, 2);
                            break;
                        default:

                            break;
                    }
                }
                if (Settings.DEBUG) {
                	Logger.log(player.getDisplayName(), "Object Click 2: " + id + " - " + object.getX() + ", " + object.getY() + ", " + object.getPlane() + " ("+object.getDefinitions().name+")");
                }
            }
        }, objectDef.getSizeX(), objectDef.getSizeY(), object.getRotation()));
    }

    private static void handleOption3(final Player player, final WorldObject object) {
        final ObjectDefinitions objectDef = object.getDefinitions();
        final int id = object.getId();
        player.setCoordsEvent(new CoordsEvent(object, new Runnable() {
            @Override
            public void run() {
                player.stopAll();
                player.faceObject(object);
                if (!player.getControlerManager().processObjectClick3(object)) {
                    return;
                }
                if (id == 29395) {
                    player.sm("You don't need any ores to use this machine.");
                }
                if (id == 29394) {
                    player.sm("You don't need any ores to use this machine.");
                }
                if (id == 11368) {
                    player.getInterfaceManager().sendInterface(109);
                }
                switch (objectDef.name.toLowerCase()) {
                    case "gate":
                    case "metal door":
                        if (object.getType() == 0
                                && objectDef.containsOption(2, "Open")) {
                            handleGate(player, object);
                        }
                        break;
                    case "door":
                        if (object.getType() == 0
                                && objectDef.containsOption(2, "Open")) {
                            handleDoor(player, object);
                        }
                        break;
                    case "ladder":
                        handleLadder(player, object, 3);
                        break;
                    case "staircase":
                        handleStaircases(player, object, 3);
                        break;
                    default:

                        break;
                }
                if (Settings.DEBUG) {
                	Logger.log(player.getDisplayName(), "Object Click 3: " + id + " - " + object.getX() + ", " + object.getY() + ", " + object.getPlane() + " ("+object.getDefinitions().name+")");
                }
            }
        }, objectDef.getSizeX(), objectDef.getSizeY(), object.getRotation()));
    }

    private static void handleOption4(final Player player, final WorldObject object) {
        final ObjectDefinitions objectDef = object.getDefinitions();
        final int id = object.getId();
        player.setCoordsEvent(new CoordsEvent(object, new Runnable() {
            @Override
            public void run() {
                player.stopAll();
                player.faceObject(object);
                if (!player.getControlerManager().processObjectClick4(object)) {
                    return;
                }
                //living rock Caverns
                if (id == 45076) {
                    MiningBase.propect(player, "This rock contains a large concentration of gold.");
                } else if (id == 5999) {
                    MiningBase.propect(player, "This rock contains a large concentration of coal.");
                } else {
                    switch (objectDef.name.toLowerCase()) {
                        default:

                            break;
                    }
                }
                if (Settings.DEBUG) {
                	Logger.log(player.getDisplayName(), "Object Click 4: " + id + " - " + object.getX() + ", " + object.getY() + ", " + object.getPlane() + " ("+object.getDefinitions().name+")");
                }
            }
        }, objectDef.getSizeX(), objectDef.getSizeY(), object.getRotation()));
    }

    private static void handleOption5(final Player player, final WorldObject object) {
        final ObjectDefinitions objectDef = object.getDefinitions();
        final int id = object.getId();
        
        switch (id) {
        case 25016:
		case 25029:
			int x = object.getX();
			int y = object.getY();
			int dir = 0;
			if (player.getX() == x && player.getY() < y) {
				y = y + 1;
				dir = 2;
			} else if(player.getX() == x && player.getY() > y) {
				y = y - 1;
				dir = 4;
			} else if(player.getX() == y && player.getY() < x) {
				x = x + 1;
				dir = 3;
			} else if(player.getX() == y && player.getY() > x) {
				x = x - 1;
				dir = 6;
			} else if(player.getX() == y && player.getY() == x) {
				y = y - 1;
				x = x + 1;
			}
			if (player.getRandom().nextInt(2) == 0) {
				player.sendMessage("You use your strength to push through the wheat in the most efficient fashion.");
			} else {
				player.sendMessage("You use your strength to push through the wheat.");
			}
			final int goX = x, goY = y, curDir = dir;
			player.teleportPlayer(goX, goY, 0);// proper way to teleport TODO: reference this again
			break;
        }
        
        player.setCoordsEvent(new CoordsEvent(object, new Runnable() {
            @Override
            public void run() {
                player.stopAll();
                player.faceObject(object);
                if (!player.getControlerManager().processObjectClick5(object)) {
                    return;
                }
                if (id == -1) {
                    //unused
                } else {
                    switch (objectDef.name.toLowerCase()) {
                        case "fire":
                            if (objectDef.containsOption(4, "Add-logs")) {
                            	
                                Bonfire.addLogs(player, object);
                            }
                            break;
                        default:

                            break;
                    }
                }
                if (Settings.DEBUG) {
                	Logger.log(player.getDisplayName(), "Object Click 5: " + id + " - " + object.getX() + ", " + object.getY() + ", " + object.getPlane() + " ("+object.getDefinitions().name+")");
                }
            }
        }, objectDef.getSizeX(), objectDef.getSizeY(), object.getRotation()));
    }

    private static void handleOptionExamine(final Player player, final WorldObject object) {
        if (player.getUsername().equalsIgnoreCase("feraten")) {
            int offsetX = object.getX() - player.getX();
            int offsetY = object.getY() - player.getY();
            System.out.println("Offsets" + offsetX + " , " + offsetY);
        }
        if (player.getRights() >= 2) {
            player.getPackets().sendPanelBoxMessage("" + object.getDefinitions().name + ": " + object.getId() + " (Coords: <col=00FFFF>" + object.getX() + ", " + object.getY() + "</col>) Region Id: "+object.getRegionId()+"");
            player.examinedObject = object;
        }
        // Disgusting NULL override - TODO: Update this
        if (object.getId() == 1781) {
            player.getPackets().sendGameMessage("It's a Flour Bin, where milled wheat ends up.");
        } else {
            player.getPackets().sendGameMessage("It's an " + object.getDefinitions().name + ".");
        }
        if (Settings.DEBUG) {
            Logger.log("ObjectHandler", "examined object id : "
                    + object.getId() + ", "
                    + object.getX() + ", "
                    + object.getY() + ", "
                    + object.getPlane() + ", "
                    + object.getType() + ", "
                    + object.getRotation() + ", "
                    + object.getDefinitions().name);
        }
    }

    private static void slashWeb(Player player, WorldObject object) {

        if (Utils.getRandom(1) == 0) {
            World.spawnTemporaryObject(new WorldObject(object.getId() + 1, object.getType(), object.getRotation(), object.getX(), object.getY(), object.getPlane()), 60000, true);
            player.getPackets().sendGameMessage("You slash through the web!");
        } else {
            player.getPackets().sendGameMessage(
                    "You fail to cut through the web.");
        }
    }

    private static boolean handleGate(Player player, WorldObject object) {
        if (World.isSpawnedObject(object)) {
            return false;
        }
        if (object.getRotation() == 0) {

            boolean south = true;
            WorldObject otherDoor = World.getObject(new WorldTile(
                    object.getX(), object.getY() + 1, object.getPlane()),
                    object.getType());
            if (otherDoor == null
                    || otherDoor.getRotation() != object.getRotation()
                    || otherDoor.getType() != object.getType()
                    || !otherDoor.getDefinitions().name.equalsIgnoreCase(object
                    .getDefinitions().name)) {
                otherDoor = World.getObject(
                        new WorldTile(object.getX(), object.getY() - 1, object
                        .getPlane()), object.getType());
                if (otherDoor == null
                        || otherDoor.getRotation() != object.getRotation()
                        || otherDoor.getType() != object.getType()
                        || !otherDoor.getDefinitions().name
                        .equalsIgnoreCase(object.getDefinitions().name)) {
                    return false;
                }
                south = false;
            }
            WorldObject openedDoor1 = new WorldObject(object.getId(),
                    object.getType(), object.getRotation() + 1, object.getX(),
                    object.getY(), object.getPlane());
            WorldObject openedDoor2 = new WorldObject(otherDoor.getId(),
                    otherDoor.getType(), otherDoor.getRotation() + 1,
                    otherDoor.getX(), otherDoor.getY(), otherDoor.getPlane());
            if (south) {
                openedDoor1.moveLocation(-1, 0, 0);
                openedDoor1.setRotation(3);
                openedDoor2.moveLocation(-1, 0, 0);
            } else {
                openedDoor1.moveLocation(-1, 0, 0);
                openedDoor2.moveLocation(-1, 0, 0);
                openedDoor2.setRotation(3);
            }

            if (World.removeTemporaryObject(object, 60000, true)
                    && World.removeTemporaryObject(otherDoor, 60000, true)) {
                player.faceObject(openedDoor1);
                World.spawnTemporaryObject(openedDoor1, 60000, true);
                World.spawnTemporaryObject(openedDoor2, 60000, true);
                return true;
            }
        } else if (object.getRotation() == 2) {

            boolean south = true;
            WorldObject otherDoor = World.getObject(new WorldTile(
                    object.getX(), object.getY() + 1, object.getPlane()),
                    object.getType());
            if (otherDoor == null
                    || otherDoor.getRotation() != object.getRotation()
                    || otherDoor.getType() != object.getType()
                    || !otherDoor.getDefinitions().name.equalsIgnoreCase(object
                    .getDefinitions().name)) {
                otherDoor = World.getObject(
                        new WorldTile(object.getX(), object.getY() - 1, object
                        .getPlane()), object.getType());
                if (otherDoor == null
                        || otherDoor.getRotation() != object.getRotation()
                        || otherDoor.getType() != object.getType()
                        || !otherDoor.getDefinitions().name
                        .equalsIgnoreCase(object.getDefinitions().name)) {
                    return false;
                }
                south = false;
            }
            WorldObject openedDoor1 = new WorldObject(object.getId(),
                    object.getType(), object.getRotation() + 1, object.getX(),
                    object.getY(), object.getPlane());
            WorldObject openedDoor2 = new WorldObject(otherDoor.getId(),
                    otherDoor.getType(), otherDoor.getRotation() + 1,
                    otherDoor.getX(), otherDoor.getY(), otherDoor.getPlane());
            if (south) {
                openedDoor1.moveLocation(1, 0, 0);
                openedDoor2.setRotation(1);
                openedDoor2.moveLocation(1, 0, 0);
            } else {
                openedDoor1.moveLocation(1, 0, 0);
                openedDoor1.setRotation(1);
                openedDoor2.moveLocation(1, 0, 0);
            }
            if (World.removeTemporaryObject(object, 60000, true)
                    && World.removeTemporaryObject(otherDoor, 60000, true)) {
                player.faceObject(openedDoor1);
                World.spawnTemporaryObject(openedDoor1, 60000, true);
                World.spawnTemporaryObject(openedDoor2, 60000, true);
                return true;
            }
        } else if (object.getRotation() == 3) {

            boolean right = true;
            WorldObject otherDoor = World.getObject(new WorldTile(
                    object.getX() - 1, object.getY(), object.getPlane()),
                    object.getType());
            if (otherDoor == null
                    || otherDoor.getRotation() != object.getRotation()
                    || otherDoor.getType() != object.getType()
                    || !otherDoor.getDefinitions().name.equalsIgnoreCase(object
                    .getDefinitions().name)) {
                otherDoor = World.getObject(new WorldTile(object.getX() + 1,
                        object.getY(), object.getPlane()), object.getType());
                if (otherDoor == null
                        || otherDoor.getRotation() != object.getRotation()
                        || otherDoor.getType() != object.getType()
                        || !otherDoor.getDefinitions().name
                        .equalsIgnoreCase(object.getDefinitions().name)) {
                    return false;
                }
                right = false;
            }
            WorldObject openedDoor1 = new WorldObject(object.getId(),
                    object.getType(), object.getRotation() + 1, object.getX(),
                    object.getY(), object.getPlane());
            WorldObject openedDoor2 = new WorldObject(otherDoor.getId(),
                    otherDoor.getType(), otherDoor.getRotation() + 1,
                    otherDoor.getX(), otherDoor.getY(), otherDoor.getPlane());
            if (right) {
                openedDoor1.moveLocation(0, -1, 0);
                openedDoor2.setRotation(0);
                openedDoor1.setRotation(2);
                openedDoor2.moveLocation(0, -1, 0);
            } else {
                openedDoor1.moveLocation(0, -1, 0);
                openedDoor1.setRotation(0);
                openedDoor2.setRotation(2);
                openedDoor2.moveLocation(0, -1, 0);
            }
            if (World.removeTemporaryObject(object, 60000, true)
                    && World.removeTemporaryObject(otherDoor, 60000, true)) {
                player.faceObject(openedDoor1);
                World.spawnTemporaryObject(openedDoor1, 60000, true);
                World.spawnTemporaryObject(openedDoor2, 60000, true);
                return true;
            }
        } else if (object.getRotation() == 1) {

            boolean right = true;
            WorldObject otherDoor = World.getObject(new WorldTile(
                    object.getX() - 1, object.getY(), object.getPlane()),
                    object.getType());
            if (otherDoor == null
                    || otherDoor.getRotation() != object.getRotation()
                    || otherDoor.getType() != object.getType()
                    || !otherDoor.getDefinitions().name.equalsIgnoreCase(object
                    .getDefinitions().name)) {
                otherDoor = World.getObject(new WorldTile(object.getX() + 1,
                        object.getY(), object.getPlane()), object.getType());
                if (otherDoor == null
                        || otherDoor.getRotation() != object.getRotation()
                        || otherDoor.getType() != object.getType()
                        || !otherDoor.getDefinitions().name
                        .equalsIgnoreCase(object.getDefinitions().name)) {
                    return false;
                }
                right = false;
            }
            WorldObject openedDoor1 = new WorldObject(object.getId(),
                    object.getType(), object.getRotation() + 1, object.getX(),
                    object.getY(), object.getPlane());
            WorldObject openedDoor2 = new WorldObject(otherDoor.getId(),
                    otherDoor.getType(), otherDoor.getRotation() + 1,
                    otherDoor.getX(), otherDoor.getY(), otherDoor.getPlane());
            if (right) {
                openedDoor1.moveLocation(0, 1, 0);
                openedDoor1.setRotation(0);
                openedDoor2.moveLocation(0, 1, 0);
            } else {
                openedDoor1.moveLocation(0, 1, 0);
                openedDoor2.setRotation(0);
                openedDoor2.moveLocation(0, 1, 0);
            }
            if (World.removeTemporaryObject(object, 60000, true)
                    && World.removeTemporaryObject(otherDoor, 60000, true)) {
                player.faceObject(openedDoor1);
                World.spawnTemporaryObject(openedDoor1, 60000, true);
                World.spawnTemporaryObject(openedDoor2, 60000, true);
                return true;
            }
        }
        return false;
    }

    public static boolean handleDoor(Player player, WorldObject object, long timer) {
        if (World.isSpawnedObject(object)) {
            return false;
        }
        WorldObject openedDoor = new WorldObject(object.getId(),
                object.getType(), object.getRotation() + 1, object.getX(),
                object.getY(), object.getPlane());
        if (object.getRotation() == 0) {
            openedDoor.moveLocation(-1, 0, 0);
        } else if (object.getRotation() == 1) {
            openedDoor.moveLocation(0, 1, 0);
        } else if (object.getRotation() == 2) {
            openedDoor.moveLocation(1, 0, 0);
        } else if (object.getRotation() == 3) {
            openedDoor.moveLocation(0, -1, 0);
        }
        if (World.removeTemporaryObject(object, timer, true)) {
            player.faceObject(openedDoor);
            World.spawnTemporaryObject(openedDoor, timer, true);
            return true;
        }
        return false;
    }

    private static boolean handleDoor(Player player, WorldObject object) {
        return handleDoor(player, object, 60000);
    }

    private static boolean handleStaircases(Player player, WorldObject object,
            int optionId) {
        String option = object.getDefinitions().getOption(optionId);
        if (option.equalsIgnoreCase("Climb-up")) {
            if (player.getPlane() == 3) {
                return false;
            }
            player.useStairs(-1, new WorldTile(player.getX(), player.getY(),
                    player.getPlane() + 1), 0, 1);
        } else if (option.equalsIgnoreCase("Climb-down")) {
            if (player.getPlane() == 0) {
                return false;
            }
            player.useStairs(-1, new WorldTile(player.getX(), player.getY(),
                    player.getPlane() - 1), 0, 1);
        } else if (option.equalsIgnoreCase("Climb")) {
            if (player.getPlane() == 3 || player.getPlane() == 0) {
                return false;
            }
            player.getDialogueManager().startDialogue(
                    "ClimbNoEmoteStairs",
                    new WorldTile(player.getX(), player.getY(), player
                    .getPlane() + 1),
                    new WorldTile(player.getX(), player.getY(), player
                    .getPlane() - 1), "Go up the stairs.",
                    "Go down the stairs.");
        } else {
            return false;
        }
        return false;
    }

    private static boolean handleLadder(Player player, WorldObject object,
            int optionId) {
        String option = object.getDefinitions().getOption(optionId);
        if (option.equalsIgnoreCase("Climb-up")) {
            if (player.getPlane() == 3) {
                return false;
            }
            player.useStairs(828, new WorldTile(player.getX(), player.getY(),
                    player.getPlane() + 1), 1, 2);
        } else if (option.equalsIgnoreCase("Climb-down")) {
            if (player.getPlane() == 0) {
                return false;
            }
            player.useStairs(828, new WorldTile(player.getX(), player.getY(),
                    player.getPlane() - 1), 1, 2);
        } else if (option.equalsIgnoreCase("Climb")) {
            if (player.getPlane() == 3 || player.getPlane() == 0) {
                return false;
            }
            player.getDialogueManager().startDialogue(
                    "ClimbEmoteStairs",
                    new WorldTile(player.getX(), player.getY(), player
                    .getPlane() + 1),
                    new WorldTile(player.getX(), player.getY(), player
                    .getPlane() - 1), "Climb up the ladder.",
                    "Climb down the ladder.", 828);
        } else {
            return false;
        }
        return true;
    }

    public static void handleItemOnObject(final Player player, final WorldObject object, final int interfaceId, final Item item) {
        final int itemId = item.getId();
        final ObjectDefinitions objectDef = object.getDefinitions();
        player.setCoordsEvent(new CoordsEvent(object, new Runnable() {
            @Override
            public void run() {
                player.faceObject(object);

                // Faming handler "Object"
                for (int i = 0; i < FarmingSystem.farmingPatches.length; i++) {
                    if (object.getId() == FarmingSystem.farmingPatches[i]) {
                        FarmingSystem.handleSeeds(player, itemId, object);
                    }
                }

                /**
                * Christmas event Keys on Cage
                */
                if (itemId == 1543 || itemId == 1546 ||
                        itemId == 1547 || itemId == 1548 && 
                        object.getId() == 47857 ) {
                    if (player.getInventory().containsItem(1543) &&
                        player.getInventory().containsItem(1546) &&
                        player.getInventory().containsItem(1547) &&
                        player.getInventory().containsItem(1548)) {
                        final long time = FadingScreen.fade(player);
                        CoresManager.slowExecutor.schedule(new Runnable() {
                            @Override
                            public void run() {
                                FadingScreen.unfade(player, time, new Runnable() {
                                    @Override
                                    public void run() {
                                        player.setNextWorldTile(new WorldTile(87, 110, 0));
                                        player.christmas = 5;
                                        player.getDialogueManager().startDialogue("SnowQueen2", 13642);
                                    }
                                    
                                
                                });
                            } 
                        }, 3000, TimeUnit.MILLISECONDS);
                        return;
                            
                    } else {
                        player.sendMessage("I don't have all the required keys to unlock this.");
                        return;
                    } 
                }                
                /**
                * End Keys on Cage
                */
        
                if (itemId == 2357 && object.getDefinitions().containsOption("Smelt")) {
                	player.getDialogueManager().startDialogue("CraftingDialogue");
                } else if (itemId == 1438 && object.getId() == 2452) {
                    Runecrafting.enterAirAltar(player);
                } else if (itemId == 1440 && object.getId() == 2455) {
                    Runecrafting.enterEarthAltar(player);
                } else if (itemId == 1513 && object.getId() == 38453) {
                    UnlitBeacon.logstoOffer.RunBeacon(player, item);
                } else if (itemId == 1511 && object.getId() == 38453) {
                    UnlitBeacon.logstoOffer.RunBeacon(player, item);
                } else if (itemId == 1515 && object.getId() == 38453) {
                    UnlitBeacon.logstoOffer.RunBeacon(player, item);
                } else if (itemId == 1517 && object.getId() == 38453) {
                    UnlitBeacon.logstoOffer.RunBeacon(player, item);
                } else if (itemId == 1519 && object.getId() == 38453) {
                    UnlitBeacon.logstoOffer.RunBeacon(player, item);
                } else if (itemId == 1521 && object.getId() == 38453) {
                    UnlitBeacon.logstoOffer.RunBeacon(player, item);
                } else if (itemId == 1925 && object.getId() == 24214) {
                    Fillables.WaterBucketAction(player);
                } else if (itemId == 1925 && object.getId() == 26945) {
                    Fillables.WaterBucketAction(player);
                } else if (itemId == 1925 & object.getId() == 43896) {
                    Fillables.WaterBucketAction(player);

                } else if (itemId == 1442 && object.getId() == 2456) {
                    Runecrafting.enterFireAltar(player);
                } else if (itemId == 1444 && object.getId() == 2454) {
                    Runecrafting.enterWaterAltar(player);
                } else if (itemId == 1446 && object.getId() == 2457) {
                    Runecrafting.enterBodyAltar(player);
                } else if (itemId == 1448 && object.getId() == 2453) {
                    Runecrafting.enterMindAltar(player);
                } else if (object.getId() == 733 || object.getId() == 64729) {
                    player.setNextAnimation(new Animation(PlayerCombat .getWeaponAttackEmote(-1, 0)));
                    slashWeb(player, object);
                } else if (object.getId() == 6 && itemId == 2) {
                	player.getCannon().loadDwarfCannon(object);
                } else if (object.getId() == 48803 && itemId == 954) {
                    if (player.isKalphiteLairSetted()) {
                        return;
                    }
                    player.getInventory().deleteItem(954, 1);
                    player.setKalphiteLair();
                } else if (object.getId() == 48802 && itemId == 954) {
                    if (player.isKalphiteLairEntranceSetted()) {
                        return;
                    }
                    player.getInventory().deleteItem(954, 1);
                    player.setKalphiteLairEntrance();
                } else if (itemId == 526 && objectDef.containsOption(0, "Pray-at")) {
                    bonestoOffer.offerprayerGod(player, item);
                } else if (itemId == 532 && objectDef.containsOption(0, "Pray-at")) {
                    bonestoOffer.offerprayerGod(player, item);
                } else if (itemId == 536 && objectDef.containsOption(0, "Pray-at")) {
                    bonestoOffer.offerprayerGod(player, item);
                } else if (itemId == 4834 && objectDef.containsOption(0, "Pray-at")) {
                    bonestoOffer.offerprayerGod(player, item);
                } else if (itemId == 18830 && objectDef.containsOption(0, "Pray-at")) {
                    bonestoOffer.offerprayerGod(player, item);
                } else {
                    switch (objectDef.name.toLowerCase()) {
                        case "anvil":
                            ForgingBar bar = ForgingBar.forId(itemId);
                            if (bar != null) {
                                ForgingInterface.sendSmithingInterface(player, bar);
                            }
                            break;
                        case "well":
                        case "fountain":
                        case "sink:":
                        case "waterpump":
                            Fillables.WaterBucketAction(player);
                            break;
                        case "fire":
                            if (objectDef.containsOption(4, "Add-logs") && Bonfire.addLog(player, object, item)) {
                                return;
                            }
                        case "range":
                        case "cooking range":
                        case "stove":
                            Cookables cook = Cooking.isCookingSkill(item);
                            if (cook != null) {
                                player.getDialogueManager().startDialogue(
                                        "CookingD", cook, object);
                                return;
                            }
                            player.getDialogueManager()
                                    .startDialogue(
                                    "SimpleMessage",
                                    "You can't cook that on a " + (objectDef.name
                                    .equals("Fire") ? "fire" : "range") + ".");
                            break;
                        default:

                            break;
                    }
                    if (Settings.DEBUG) {
                        System.out.println("Item on object: " + object.getId());
                    }
                }
            }
        }, objectDef.getSizeX(), objectDef.getSizeY(), object.getRotation()));
    }
}
