package com.rs.net.decoders.handlers;

import com.rs.Settings;
import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.minigames.Implings;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.others.FireSpirit;
import com.rs.game.npc.others.LivingRock;
import com.rs.game.npc.slayer.Strykewyrm;
import com.rs.game.player.CoordsEvent;
import com.rs.game.player.Player;
import com.rs.game.player.actions.Fishing;
import com.rs.game.player.actions.Fishing.FishingSpots;
import com.rs.game.player.actions.mining.LivingMineralMining;
import com.rs.game.player.actions.mining.MiningBase;
import com.rs.game.player.actions.runecrafting.SiphonActionCreatures;
import com.rs.game.player.actions.thieving.PickPocketAction;
import com.rs.game.player.actions.thieving.PickPocketableNPC;
import com.rs.game.player.content.LividFarm;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.dialogues.impl.ContractDialogue;
import com.rs.game.player.dialogues.impl.FremennikShipmaster;
import com.rs.io.InputStream;
import com.rs.utils.Logger;
import com.rs.utils.ShopsHandler;

public class NPCHandler {

	public static void handleExamine(final Player player, InputStream stream) {
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
					npc.setNextForceTalk(new ForceTalk("Boring... how long we need to do this?!"));
				else if(npc.getId() == 6647)
					npc.setNextForceTalk(new ForceTalk("Shut up students! Work harder, go go go!"));
				else if (npc.getId() == 7531)
					player.getDialogueManager().startDialogue("Niles", npc.getId());
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
				else if (npc.getId() == 2600 << 2700) // 1 to 24 in java if im sure, change if not
					player.getDialogueManager().startDialogue("Man", npc.getId(), false);
				else if (npc.getId() == 3000 << 4000) // 1 to 24 in java if im sure, change if not	
					player.getDialogueManager().startDialogue("Man", npc.getId(), false);
				else if (npc.getId() == 9707)
					player.getDialogueManager().startDialogue("FremennikShipmaster", npc.getId(), true);
				else if (npc.getId() == 9708)
					player.getDialogueManager().startDialogue("FremennikShipmaster", npc.getId(), false);
				else if (npc.getId() == 8555)
					PlayerLook.openMageMakeOver(player);
				else if (npc.getId() == 320 << 400) // 1 to 24 in java if im sure, change if not
					player.getDialogueManager().startDialogue("Man", npc.getId(), false);
				else if (npc.getId() == 8080 << 8125) // 1 to 24 in java if im sure, change if not
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
							player.getPackets().sendGameMessage("Your arent poisoned or diseased.");
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
				else if (npc.getId() == 14854)
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
				else if (npc.getId() == 519)
					ShopsHandler.openShop(player, 21);
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
					npc.setNextForceTalk(new ForceTalk("Senventior Disthinte Molesko!"));
					player.getControlerManager().startControler("SorceressGarden");
				}
			}
		}, npc.getSize()));
		if (Settings.DEBUG)
			System.out.println("cliked 3 at npc id : "
					+ npc.getId() + ", " + npc.getX() + ", "
					+ npc.getY() + ", " + npc.getPlane());
		}
}
