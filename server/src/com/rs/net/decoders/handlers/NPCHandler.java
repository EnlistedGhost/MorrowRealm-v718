package com.rs.net.decoders.handlers;

import java.util.TimerTask;
// for xmas 2019
import java.util.concurrent.TimeUnit;

import com.rs.Settings;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.minigames.Implings;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.others.FireSpirit;
import com.rs.game.npc.others.LivingRock;
import com.rs.game.npc.slayer.Strykewyrm;
import com.rs.game.player.CoordsEvent;
import com.rs.game.player.Player;
import com.rs.game.player.actions.fishing.Fishing;
import com.rs.game.player.actions.fishing.Fishing.FishingSpots;
import com.rs.game.player.actions.mining.LivingMineralMining;
import com.rs.game.player.actions.mining.MiningBase;
import com.rs.game.player.actions.runecrafting.SiphonActionCreatures;
import com.rs.game.player.actions.thieving.PickPocketAction;
import com.rs.game.player.actions.thieving.PickPocketableNPC;
import com.rs.game.player.content.LividFarm;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.content.Magic;
//import com.rs.game.player.content.custom.PlayerLoginTimeout;
import com.rs.game.player.dialogues.impl.ContractDialogue;
import com.rs.game.player.dialogues.impl.FremennikShipmaster;
import com.rs.game.player.dialogues.impl.Aubury;
import com.rs.game.player.dialogues.impl.CustomsOfficerKaramja;
import com.rs.game.player.dialogues.impl.CaptainTobias;
import com.rs.game.player.dialogues.impl.SeamanLorris;
import com.rs.game.player.dialogues.impl.SeamanThresnor;
import com.rs.game.player.dialogues.impl.LumbridgeSage;
import com.rs.io.InputStream;
import com.rs.utils.Logger;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;
import com.rs.cores.CoresManager;
// for xmas 2019
import com.rs.game.player.content.FadingScreen;

public class NPCHandler {

	public static void handleExamine(final Player player, InputStream stream) {
		// Reset player timeout
		//PlayerLoginTimeout.PlayerTimeoutReset();
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		if(forceRun)
			player.setRun(forceRun);
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.hasFinished() || !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		if (player.getRights() == 2) {
			player.getPackets().sendGameMessage("NPC - [id=" + npc.getId() + ", loc=[" + npc.getX() + ", " + npc.getY() + ", " + npc.getPlane() + "]].");
		}
		player.getPackets().sendNPCMessage(0, npc, "Examined NPC: " + npc.getDefinitions().name + " (Id: "+npc.getId()+", Health: "+npc.getHitpoints()+".");
		if (Settings.DEBUG)
			Logger.log("NPCHandler", "examined npc: " + npcIndex+", "+npc.getId());
	}
	
	public static void handleOption1(final Player player, InputStream stream) {
		// Reset player timeout
		//PlayerLoginTimeout.PlayerTimeoutReset();
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.isCantInteract() || npc.isDead() || npc.hasFinished() || !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		player.stopAll(false);
		if(forceRun)
			player.setRun(forceRun);
		if (npc.getDefinitions().name.contains("Banker") || npc.getDefinitions().name.contains("banker")) {
			player.faceEntity(npc);
			if (!player.withinDistance(npc, 2))
				return;
			npc.faceEntity(player);
			player.getDialogueManager().startDialogue("Banker", npc.getId());
			return;
		}
		if(SiphonActionCreatures.siphon(player, npc)) 
			return;
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				npc.resetWalkSteps();
				player.faceEntity(npc);
				if (!player.getControlerManager().processNPCClick1(npc))
					return;
				FishingSpots spot = FishingSpots.forId(npc.getId() | 1 << 24);
				if (spot != null) {
					player.getActionManager().setAction(new Fishing(spot, npc));
					return; // its a spot, they wont face us
				}else if (npc.getId() >= 8837 && npc.getId() <= 8839) {
					player.getActionManager().setAction(new LivingMineralMining((LivingRock) npc));
					return;
				}
				npc.faceEntity(player);
				if (npc.getId() == 3709)
					player.getDialogueManager().startDialogue("PlayerShopDialogue", npc.getId());
				else if (npc.getId() == 7530)
					LividFarm.CheckforLogs(player);
				else if (npc.getId() == 949)
					player.getDialogueManager().startDialogue("QuestGuide", npc.getId(), null);
				else if (npc.getId() == 15451 && npc instanceof FireSpirit) {
					FireSpirit spirit = (FireSpirit) npc;
					spirit.giveReward(player);
				} else if(npc.getId() == 6654)
					npc.setNextForceTalk(new ForceTalk("Smith, smith smith! There is work to do!"));
				else if(npc.getId() == 65)
					npc.setNextForceTalk(new ForceTalk("Boring... how long do we need to do this?!"));
				else if(npc.getId() == 6647)
					npc.setNextForceTalk(new ForceTalk("Shut up students! Work harder, go go go!"));
				else if (npc.getId() == 7531)
					player.getDialogueManager().startDialogue("Niles", npc.getId());
				else if (npc.getId() == 5913)
					player.getDialogueManager().startDialogue("Aubury", npc.getId());
				else if (npc.getId() == 705)
					player.getDialogueManager().startDialogue("Melee instructor", npc.getId());
				else if (npc.getId() == 4707)
					player.getDialogueManager().startDialogue("Magic instructor", npc.getId());
				else if (npc.getId() == 1861)
					player.getDialogueManager().startDialogue("Ranged instructor", npc.getId());
				else if (npc.getId() == 4906)
					player.getDialogueManager().startDialogue("Wilfred", npc.getId());
				else if (npc.getId() == 2238)
					player.getDialogueManager().startDialogue("Donie", npc.getId());
				else if (npc.getId() == 11700)
					player.getDialogueManager().startDialogue("CaptainTobias", npc.getId());
				else if (npc.getId() == 11701)
					player.getDialogueManager().startDialogue("SeamanLorris", npc.getId());
				else if (npc.getId() == 11702)
					player.getDialogueManager().startDialogue("SeamanThresnor", npc.getId());
				else if (npc.getId() == 380)
					player.getDialogueManager().startDialogue("CustomsOfficerKaramja", npc.getId());
				else if (npc.getId() == 14194)
					player.getDialogueManager().startDialogue("PKGear", npc.getId());
				else if (npc.getId() == 918)
					player.getDialogueManager().startDialogue("Ned", npc.getId());
				else if (npc.getId() == 734)
					player.getDialogueManager().startDialogue("Bartender", npc.getId());
				else if (npc.getId() == 375)
					player.getDialogueManager().startDialogue("Frank", npc.getId());
				else if (npc.getId() == 8629)
					player.getDialogueManager().startDialogue("SandwichLady", npc.getId());
				else if (npc.getId() == 6988)
					player.getDialogueManager().startDialogue("Pikkupstix", npc.getId());
				else if (npc.getId() == 9085)
					player.getDialogueManager().startDialogue("SlayerMaster", false);
				else if (npc.getId() == 3374)
					player.getDialogueManager().startDialogue("Max", npc.getId(), null);
				else if (npc.getId() == 9713)
					player.getDialogueManager().startDialogue("ExpertDung", npc.getId());
				else if (npc.getId() == 11226)
					player.getDialogueManager().startDialogue("DungLeaving");
				else if (npc.getId() == 9462)
					Strykewyrm.handleStomping(player, npc);
				else if (npc.getId() == 457)
					ShopsHandler.openShop(player, 27);
				else if (npc.getId() == 6971)
					ShopsHandler.openShop(player, 39);
				else if (npc.getId() == 1918)
                    ShopsHandler.openShop(player, 33);
				else if (npc.getId() == 14850)
                    ShopsHandler.openShop(player, 47);
				else if (npc.getId() == 8591) {
					player.getDialogueManager().startDialogue("NomadMultiShop", npc.getId());
				} else if (npc.getId() >= 6053 && npc.getId() <= 6064)
					Implings.captureImp(player, npc);
				else if (npc.getId() == 7879) // 1 to 24 in java if im sure, change if not
					player.getDialogueManager().startDialogue("Man", npc.getId(), false);
				else if (npc.getId() == 7873) // 1 to 24 in java if im sure, change if not	
					player.getDialogueManager().startDialogue("Man", npc.getId(), false);
				else if (npc.getId() == 9707)
					player.getDialogueManager().startDialogue("FremennikShipmaster", npc.getId(), true);
				else if (npc.getId() == 9708)
					player.getDialogueManager().startDialogue("FremennikShipmaster", npc.getId(), false);
				else if (npc.getId() == 8555)
					PlayerLook.openMageMakeOver(player);
				else if (npc.getId() == 7876) // 1 to 24 in java if im sure, change if not
					player.getDialogueManager().startDialogue("Man", npc.getId(), false);
				else if (npc.getId() == 7875) // 1 to 24 in java if im sure, change if not
					player.getDialogueManager().startDialogue("Man", npc.getId(), false);
				else if (npc.getId() == 13727)
                    player.getDialogueManager().startDialogue("Xuans", npc.getId());
				else if (npc.getId() == 13335)
                    player.getDialogueManager().startDialogue("MineShop", npc.getId());
				else if (npc.getId() == 13929)
					ShopsHandler.openShop(player, 42);
				else if (npc.getId() == ContractDialogue.idNo)
					player.getDialogueManager().startDialogue("ContractDialogue");
				else if (npc.getId() == 13926)
                    player.getDialogueManager().startDialogue("Veteran", npc.getId());
				else if (npc.getId() == 4247)
                    player.getDialogueManager().startDialogue("EstateAgent", npc.getId());
				else if (npc.getId() == 230)
                    player.getDialogueManager().startDialogue("PortalTeleport", npc.getId());
				else if (npc.getId() == 456)
                    player.getDialogueManager().startDialogue("Switcher", npc.getId());
				else if (npc.getId() == 744)
	                player.getDialogueManager().startDialogue("Klarense", npc.getId());
				else if (npc.getId() == 13768)
                    player.getDialogueManager().startDialogue("Manager", npc.getId());
				else if (npc.getId() == 4526)
                    player.getDialogueManager().startDialogue("Bouquet", npc.getId());
				else if (npc.getId() == 755)
                    player.getDialogueManager().startDialogue("Morgan", npc.getId());
				else if (npc.getId() == 1918)
                    player.getDialogueManager().startDialogue("Mandrith", npc.getId());
				else if (npc.getId() == 6970)
					player.getDialogueManager().startDialogue("SummoningShop", npc.getId(), false);
				else if (npc.getId() == 598)
					player.getDialogueManager().startDialogue("Hairdresser", npc.getId());
				else if (npc.getId() == 548)
					player.getDialogueManager().startDialogue("Thessalia", npc.getId());
				else if (npc.getId() == 581)
					player.getDialogueManager().startDialogue("Wayne");
				else if (npc.getId() == 583)
					player.getDialogueManager().startDialogue("BettyMagic");
				else if (npc.getId() == 550)
					player.getDialogueManager().startDialogue("LowesArchery", npc.getId());
				else if (npc.getId() == 549)
					ShopsHandler.openShop(player, 15);
				else if (npc.getId() == 15418)
					player.getDialogueManager().startDialogue("RuneSpan", npc.getId());
				else if (npc.getId() == 3820)
					ShopsHandler.openShop(player, 29);
				else if (npc.getId() == 576)
					ShopsHandler.openShop(player, 31);
				else if (npc.getId() == 4288)
					ShopsHandler.openShop(player, 30);
				else if (npc.getId() == 6537)
					ShopsHandler.openShop(player, 34);
				else if (npc.getId() == 1167)
					ShopsHandler.openShop(player, 35);
				else if (npc.getId() == 6892)
					player.getDialogueManager().startDialogue("PetShop", npc.getId());
				else if (npc.getId() == 2467)
					player.getDialogueManager().startDialogue("SuppliesShop", npc.getId());
			    else if (npc.getId() == 15501)
					player.getDialogueManager().startDialogue("MakeOverMage", npc.getId(), 0);
				//Halloween event
				else if (npc.getId() == 12377)
					  player.getDialogueManager().startDialogue("PumpkinPete", npc.getId());
				else if (npc.getId() == 12378)
					  player.getDialogueManager().startDialogue("PumpkinPete2", npc.getId());	
				else if (npc.getId() == 12375 && player.cake == 0)
					  player.getDialogueManager().startDialogue("Zabeth", npc.getId());
				else if (npc.getId() == 12375 && player.drink == 0)
					  player.getDialogueManager().startDialogue("Zabeth2", npc.getId());	
				else if (npc.getId() == 12375 && player.drink == 1)
					  player.getDialogueManager().startDialogue("Zabeth3", npc.getId());
				else if (npc.getId() == 12379 && player.drink == 0)
					  player.getDialogueManager().startDialogue("GrimReaper", npc.getId());
				else if (npc.getId() == 12379 && player.dust1 == 0)
					  player.getDialogueManager().startDialogue("GrimReaper2", npc.getId());
				else if (npc.getId() == 12379 && player.dust1 == 1 && player.dust2 == 1 && player.dust3 == 1)
					  player.getDialogueManager().startDialogue("GrimReaper3", npc.getId());
				else if (npc.getId() == 12375 && player.doneevent == 1)
					  player.getDialogueManager().startDialogue("PumpkinPete2", npc.getId());
				else if (npc.getId() == 12379 && player.doneevent == 1)
					  player.getDialogueManager().startDialogue("PumpkinPete2", npc.getId());
				else if (npc.getId() == 12392)
					  player.getDialogueManager().startDialogue("PumpkinPete2", npc.getId());
				// End event
				/**
				*  Christmas Event
				*/
				else if (npc.getId() == 9376) { // Imp 1
					if (player.christmas < 2)
					player.getPackets().sendGameMessage("The imp has no interest in you.");
					else if (player.xmasFoodGathering == 1 && player.xmasWine == 0 && player.xmasYule == 0) {
						player.getDialogueManager().startDialogue("Imp12", npc.getId());
						return;
					}else if (player.xmasFoodGathering == 1 && player.xmasWine == 1 && player.xmasYule == 1) {
						player.getDialogueManager().startDialogue("Imp13", npc.getId());
						return;
					}else if (player.xmasFoodReceived == 1){
						player.getDialogueManager().startDialogue("Imp14", npc.getId());
						return;
				}else {
					player.getDialogueManager().startDialogue("Imp11", npc.getId());
				}	return;
				} else if (npc.getId() == 9377) {
					if (player.christmas < 2)
					player.getPackets().sendGameMessage("The imp has no interest in you.");
					else if (player.xmasBeadStarted == 1 && (player.xmasDrawer < 3 || player.xmasBookcaseBig < 3 || player.xmasBookcaseSmall < 3 || player.xmasChest < 3)){
						player.getDialogueManager().startDialogue("Imp22", npc.getId());
						return;
					} else if (player.xmasDrawer == 3 && player.xmasBookcaseBig == 3 && player.xmasBookcaseSmall == 3 && player.xmasChest == 3){
						player.getDialogueManager().startDialogue("Imp23", npc.getId());
						return;
					
					} else if (player.xmasBeadComplete == 1){
						player.getDialogueManager().startDialogue("Imp24", npc.getId());
						return;
					}else {
					player.getDialogueManager().startDialogue("Imp21", npc.getId());
					}return;
				} else if (npc.getId() == 9378) {
					if (player.christmas <= 1)
					player.getPackets().sendGameMessage("The imp has no interest in you.");
					else
					player.getDialogueManager().startDialogue("Imp3", npc.getId());
					return;
				} else if (npc.getId() == 9379) {
					if (player.christmas <= 1)
					player.getPackets().sendGameMessage("The imp has no interest in you.");
					else
					player.getDialogueManager().startDialogue("Imp4", npc.getId());
					return;
				} else if (npc.getId() == 8540) {
					player.getDialogueManager().startDialogue("SnowQueen2", npc.getId());
					return;
				} else if (npc.getId() == 13642) {
					if ((player.getInventory().containsItem(29961, 250))) {
						player.christmas = 5;
						//
						player.getInventory().deleteItem(29961, 250);
						//
						final long time = FadingScreen.fade(player);
                    	CoresManager.slowExecutor.schedule(new Runnable() {
                        	@Override
                        	public void run() {
                            	FadingScreen.unfade(player, time, new Runnable() {
                                	@Override
                                	public void run() {
                                        player.setNextWorldTile(new WorldTile(87, 110, 0));
                                	}
                            	});
                        	}
                    	}, 3000, TimeUnit.MILLISECONDS);
					}
					if (player.christmas < 1) {
					player.getDialogueManager().startDialogue("SnowQueen1", npc.getId());	
					}else {
						player.getDialogueManager().startDialogue("SnowQueen2", npc.getId());
					}
					return;
				} else if (npc.getId() == 9412 || npc.getId() == 9414 || npc.getId() == 9416 || npc.getId() == 9418 || npc.getId() == 9420 || npc.getId() == 9422) {
					player.getDialogueManager().startDialogue("PartyGoers", npc.getId());
				}
				/**
				 * End Christmas
				 */
				//
				// Sheep shearing handler
				else if (npc.getId() == 43 || npc.getId() == 1765 || npc.getId() == 5156 || npc.getId() == 5157 || npc.getId() == 5160 || npc.getId() == 5161) {
					// Functionality thanks to Edimmu @ rune-server | https://www.rune-server.ee/members/edimmu/
					// Implemented by EnlistedGhost | github.com/enlistedghost
					final int npcId = npc.getId();
					if (player.getInventory().containsItem(1735, 1)) {
						if(Utils.getRandom(2) == 0) {
							npc.setNextForceTalk(new ForceTalk("Baa!"));
							npc.playSound(756, 1);
							npc.addWalkSteps(npcId, npcId, 4, true);
							npc.setRun(true);
							player.getPackets().sendGameMessage("The sheep runs away from you.");
						} else {
							player.playSound(761, 1);
							player.getInventory().addItem(1737, 1);
							player.getPackets().sendGameMessage("You shear the sheep of it's fleece.");
							player.setNextAnimation(new Animation(893));
							npc.transformIntoNPC(5149);
							CoresManager.fastExecutor.schedule(new TimerTask() {
						    	@Override
						    	public void run() {
						    		npc.transformIntoNPC(npcId);
						    	}
							}, 17000);
						}
					} else {
						player.getPackets().sendGameMessage("You need a pair of shears to shear the sheep.");
					}
				} else if (npc.getId() == 2244) {
					player.getDialogueManager().startDialogue("LumbridgeSage", npc.getId());
				}
				else {
					if (Settings.DEBUG) {
						if (player.isOwner()) {
							System.out.println("cliked 1 at npc id : " + npc.getId() + ", " + npc.getX() + ", " + npc.getY() + ", " + npc.getPlane());
						}
					}
				}
			}
		}, npc.getSize()));
	}
	
	public static void handleOption2(final Player player, InputStream stream) {
		// Reset player timeout
		//PlayerLoginTimeout.PlayerTimeoutReset();
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.isCantInteract() || npc.isDead()
				|| npc.hasFinished()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		player.stopAll(false);
		if(forceRun)
			player.setRun(forceRun);
		if (npc.getDefinitions().name.contains("Banker")
				|| npc.getDefinitions().name.contains("banker")) {
			player.faceEntity(npc);
			if (!player.withinDistance(npc, 2))
				return;
			npc.faceEntity(player);
			player.getBank().openBank();
			return;
		}
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				npc.resetWalkSteps();
				player.faceEntity(npc);
				FishingSpots spot = FishingSpots.forId(npc.getId() | (2 << 24));
				if (spot != null) {
					player.getActionManager().setAction(new Fishing(spot, npc));
					return;
				}
				PickPocketableNPC pocket = PickPocketableNPC.get(npc.getId());
				if (pocket != null) {
					player.getActionManager().setAction(new PickPocketAction(npc, pocket));
					return;
				}
				if (npc instanceof Familiar) {
					if (npc.getDefinitions().hasOption("store")) {
						if (player.getFamiliar() != npc) {
							player.getPackets().sendGameMessage("That isn't your familiar.");
							return;
						}
						player.getFamiliar().store();
					} else if (npc.getDefinitions().hasOption("cure")) {
						if (player.getFamiliar() != npc) {
							player.getPackets().sendGameMessage("That isn't your familiar.");
							return;
						}
						if (!player.getPoison().isPoisoned()) {
							player.getPackets().sendGameMessage("Your aren't poisoned or diseased.");
							return;
						} else {
							player.getFamiliar().drainSpecial(2);
							player.addPoisonImmune(120);
						}
					}
					return;
				}
				npc.faceEntity(player);
				if (!player.getControlerManager().processNPCClick2(npc))
					return;
				
				if (npc.getId() == 9707)
					FremennikShipmaster.sail(player, true);
				else if (npc.getId() == 9708)
					FremennikShipmaster.sail(player, false);
				else if (npc.getId() == 1 << 24)
					player.getDialogueManager().startDialogue("Man", npc.getId(), false);
				else if (npc.getId() == 13455)
					player.getBank().openBank();
				else if (npc.getId() == 528 || npc.getId() == 529 || npc.getId() == 2304)
					ShopsHandler.openShop(player, 1);
				else if (npc.getId() == 538)
					ShopsHandler.openShop(player, 2);
				else if (npc.getId() == 581)
					player.getDialogueManager().startDialogue("Wayne");
				else if (npc.getId() == 583)
					player.getDialogueManager().startDialogue("BettyMagic");
				else if (npc.getId() == 538)
					ShopsHandler.openShop(player, 6);
				else if (npc.getId() == 5913) {// Aubury shop
					ShopsHandler.openShop(player, 11);
				} else if (npc.getId() == 11700) {// Captain Tobias
					npc.setNextForceTalk(new ForceTalk("Alright boys, let's sail!"));
					player.setNextWorldTile(new WorldTile(2956, 3143, 1));
				} else if (npc.getId() == 11701) {// Seaman Lorris
					npc.setNextForceTalk(new ForceTalk("Alright boys, let's sail!"));
					player.setNextWorldTile(new WorldTile(2956, 3143, 1));
				} else if (npc.getId() == 11702) {// Seaman Thresnor
					npc.setNextForceTalk(new ForceTalk("Alright boys, let's sail!"));
					player.setNextWorldTile(new WorldTile(2956, 3143, 1));
				} else if (npc.getId() == 380) {// Customs Officer
					npc.setNextForceTalk(new ForceTalk("Anchors away!"));
					player.setNextWorldTile(new WorldTile(3032, 3217, 1));
				} else if (npc.getId() == 14854)
					ShopsHandler.openShop(player, 7);
				else if (npc.getId() == 522 || npc.getId() == 523)
					ShopsHandler.openShop(player, 8);
				else if (npc.getId() == 598)
					PlayerLook.openHairdresserSalon(player);
				else if (npc.getId() == 546)
					ShopsHandler.openShop(player, 10);
				else if (npc.getId() == 537)
					ShopsHandler.openShop(player, 9);
				else if (npc.getId() == 875)
					ShopsHandler.openShop(player, 13);
				else if (npc.getId() == 550)
					player.getDialogueManager().startDialogue("LowesArchery", npc.getId());
				else if (npc.getId() == 549)
					ShopsHandler.openShop(player, 15);
				else if (npc.getId() == 13191)
					ShopsHandler.openShop(player, 17);
				else if (npc.getId() == 548)
					ShopsHandler.openShop(player, 18);
				else if (npc.getId() == 551)
					ShopsHandler.openShop(player, 35);
				else if (npc.getId() == 554)
					ShopsHandler.openShop(player, 36);
				else if (npc.getId() == 585)
					ShopsHandler.openShop(player, 19);
				else if (npc.getId() == 587)
					ShopsHandler.openShop(player, 20);
				else if (npc.getId() == 519)// Bob (from Bob's Brilliant Axes)
					ShopsHandler.openShop(player, 21);// Bob's Brilliant Axes
				else if (npc.getId() == 8864 && npc.getX() != 3643 && npc.getY() != 5135)
					ShopsHandler.openShop(player, 22); // All Others
				else if (npc.getId() == 8864 && npc.getX() == 3643 && npc.getY() == 5135)
					ShopsHandler.openShop(player, 64); // LRC Shop
				else if (npc.getId() == 526)
					ShopsHandler.openShop(player, 23);	
				else if (npc.getId() == 527)
					ShopsHandler.openShop(player, 24);	
				else if (npc.getId() == 530)
					ShopsHandler.openShop(player, 25);
				else if (npc.getId() == 576)
					ShopsHandler.openShop(player, 31);
				else if (npc.getId() == 5112)
					ShopsHandler.openShop(player, 26);
				else if (npc.getId() == 6892)
					ShopsHandler.openShop(player, 32);
				else if (npc.getId() == 3021)
					ShopsHandler.openShop(player, 40);
				else if (npc.getId() == 13727)
                    player.getDialogueManager().startDialogue("Xuan", npc.getId());
				else if (npc.getId() == 6970)
					player.getDialogueManager().startDialogue("SummoningShop", npc.getId(), false);
				else if (npc.getId() == 8555)
					PlayerLook.openMageMakeOver(player);
				else {
		
					if (Settings.DEBUG)
						System.out.println("cliked 2 at npc id : "
								+ npc.getId() + ", " + npc.getX() + ", "
								+ npc.getY() + ", " + npc.getPlane());
				}
			}
		}, npc.getSize()));
	}

	public static void handleOption3(final Player player, InputStream stream) {
		// Reset player timeout
		//PlayerLoginTimeout.PlayerTimeoutReset();
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.isCantInteract() || npc.isDead()
				|| npc.hasFinished()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		player.stopAll(false);
		if(forceRun)
			player.setRun(forceRun);
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				npc.resetWalkSteps();
				if (!player.getControlerManager().processNPCClick3(npc))
					return;
				player.faceEntity(npc);
				if (npc.getId() >= 8837 && npc.getId() <= 8839) {
					MiningBase.propect(player, "You examine the remains...", "The remains contain traces of living minerals.");
					return;
				}
				if (npc.getId() == 13727) {
				    player.getPackets().sendGameMessage("Title cleared.");
					player.getAppearence().setTitle(0);
					player.getDisplayName();
					player.getAppearence().generateAppearenceData();
				}
				if (npc.getId() == 9085) {
					ShopsHandler.openShop(player, 66);
				}
				if (npc.getId() == 3374) {
					ShopsHandler.openShop(player, 18);
				}
				npc.faceEntity(player);
				if (npc.getId() == 548) {
					PlayerLook.openThessaliasMakeOver(player);
                }
				if (npc.getId() == 5532) {
					npc.setNextForceTalk(new ForceTalk("Senventior Disthine Molenko!"));
					player.getControlerManager().startControler("SorceressGarden");
				}
				if (npc.getId() == 5913) {// Aubury tele
					npc.setNextForceTalk(new ForceTalk("Senventior Disthine Molenko!"));
					Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2911, 4832, 0));
				}
			}
		}, npc.getSize()));
		if (Settings.DEBUG)
			System.out.println("cliked 3 at npc id : "
					+ npc.getId() + ", " + npc.getX() + ", "
					+ npc.getY() + ", " + npc.getPlane());
		}
}
