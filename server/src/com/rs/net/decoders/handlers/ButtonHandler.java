package com.rs.net.decoders.handlers;

import java.util.HashMap;
import java.util.TimerTask;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cores.CoresManager;
import com.rs.database.impl.VoteManager;
import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.minigames.Crucible;
import com.rs.game.minigames.Implings;
import com.rs.game.minigames.duel.DuelControler;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.familiar.Familiar.SpecialAttack;
import com.rs.game.player.CombatDefinitions;
import com.rs.game.player.EmotesManager;
import com.rs.game.player.Equipment;
import com.rs.game.player.Inventory;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.FightPitsViewingOrb;
import com.rs.game.player.actions.HomeTeleport;
import com.rs.game.player.actions.Rest;
import com.rs.game.player.actions.smithing.Smithing.ForgingInterface;
import com.rs.game.player.actions.summoning.Summoning;
import com.rs.game.player.content.ArtisanWorkshop;
import com.rs.game.player.content.ConstructFurniture;
import com.rs.game.player.content.CrystalSystem;
import com.rs.game.player.content.ItemConstants;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.Notes.Note;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.content.Runecrafting;
import com.rs.game.player.content.SandwichLadyHandler;
import com.rs.game.player.content.Shop;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.game.player.content.SkillsDialogue;
import com.guardian.ItemManager;
import com.rs.game.player.content.interfaces.OverRideMenu;
import com.rs.game.player.content.interfaces.PvmRewards;
import com.rs.game.player.content.interfaces.RunePortal;
import com.rs.game.player.content.interfaces.TitleMenuHandler;
import com.rs.game.player.content.shops.SlayerShop;
//import com.rs.game.player.content.custom.PlayerLoginTimeout;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.player.dialogues.impl.LevelUp;
import com.rs.game.player.dialogues.impl.Transportation;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.io.InputStream;
import com.rs.net.decoders.WorldPacketsDecoder;
import com.rs.utils.ItemExamines;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

public class ButtonHandler {

	public static void handleButtons(final Player player, InputStream stream, int packetId) {
		int interfaceHash = stream.readIntV2();
		int interfaceId = interfaceHash >> 16;
		final int componentId = interfaceHash - (interfaceId << 16);
		final int slotId2 = stream.readUnsignedShort128();
		final int slotId = stream.readUnsignedShortLE128();
		// Reset player timeout
		//PlayerLoginTimeout.PlayerTimeoutReset();
		
		if (Settings.ENABLE_BUTTON_DEBUG && player.isOwner()) {
			Logger.log("ButtonHandler", "InterfaceId: " + interfaceId + ", componentId: " + componentId + ", slotId: " + slotId + ", slotId2: " + slotId2 + ", packetId: " + packetId);
		}
		
		if (Utils.getInterfaceDefinitionsSize() <= interfaceId) {
			return;
		}
		
		if (player.isDead() || !player.getInterfaceManager().containsInterface(interfaceId)) {
			return;
		}
		
		if (componentId != 65535 && Utils.getInterfaceDefinitionsComponentsSize(interfaceId) <= componentId) {
			return;
		}
		
		if (!player.getControlerManager().processButtonClick(interfaceId, componentId, slotId, packetId))
			return;
		
		if (slotId2 >= 11238 && slotId2 <= 11256) {
			if (Implings.openImpJar(player, slotId2)) {
				return;
			}
		}
		
		if (interfaceId == 560) {
			if (componentId == 16) {
				player.getPackets().sendOpenURL("http://morrowrealm.xyz/");
				player.sendMessage("Opening Official Site...please wait a moment for the page to load.");
				return;
			}
		}
		
		if (interfaceId == 1156) {
			if (player.isInTitleList) {
				PvmRewards.handleButtons(player, componentId);
			} else {
				OverRideMenu.handleButtons(player, componentId);
			}
			return;
		}
		
		if (interfaceId == 34) {
			if (packetId == 55) {
				if (componentId == 9) {
					player.getNotes().remove(slotId);
				}
			} else if (packetId == 14) {
				if (componentId == 3) {
					player.getAttributes().put("entering_note", Boolean.TRUE); 
					player.getPackets().sendInputLongTextScript("Enter a new note:"); 
				} else if (componentId == 8) {
					player.sendMessage("Please right click the note you wish to delete instead.");
				}
			} else if (packetId == 67) {
				Note note = (Note) player.getCurNotes().get(slotId);
				player.getAttributes().put("editing_note", Boolean.TRUE); 
				player.getAttributes().put("noteToEdit", note); 
				player.getPackets().sendInputLongTextScript("What would you like to put instead?"); 
			} else if (packetId == 5) {
				if (componentId == 9) {
					Note note = (Note)  player.getCurNotes().get(slotId); 
					int color = note.getColour() == 0 ? 1 :
								note.getColour() == 1 ? 2 :
								note.getColour() == 2 ? 3 : 0;
						note.setColour(color);
					player.getNotes().refresh(note);
				}
			}
			return;
		}
		
		if (interfaceId == 629 && (componentId == 68 || componentId == 67)) {
			player.closeInterfaces();
			return;
		}
		
		if (interfaceId == 1312) {
			VoteManager.handleVoteButtons(player, componentId);
		}
		
		if (interfaceId == 397) {
			ConstructFurniture.handleButtons(player, componentId);
		}
		
		if (interfaceId == 297) {
			SandwichLadyHandler.handleButtons(player, componentId);
		}
		
		if (interfaceId == 496) {
			TitleMenuHandler.handleButtons(player, interfaceId, componentId);
		}
		
		if (interfaceId == RunePortal.INTER) {
			if (componentId == 68) {
				player.closeInterfaces();
			}
		}
		
		if (interfaceId == 1123) {
			CrystalSystem.handleButtons(player, componentId);
		}
		
		if (interfaceId == 161) {
			if (componentId == 15) {
				player.getInterfaceManager().sendInterface(164);
				player.getPackets().sendIComponentText(164, 20, ""+player.getSlayerPoints()+"");
				return;
			}
		}
		
		
		if (interfaceId == 1174) {
			int skill = 0;
			if (componentId == 37) {
				skill = Skills.ATTACK;
			} else if (componentId == 38) {
				skill = Skills.STRENGTH;
			} else if (componentId == 39) {
				skill = Skills.DEFENCE;
			} else if (componentId == 40) {
				skill = Skills.PRAYER;
			} else if (componentId == 41) {
				skill = Skills.MAGIC;
			} else if (componentId == 42) {
				skill = Skills.RANGE;
			} else if (componentId == 43) {
				skill = Skills.HITPOINTS;
			}
			
			if (!player.getInventory().containsItem(22340, 1)) {
				player.sendMessage("It appears you don't have an Exp Book!");
				return;
			}
			
			player.getInventory().deleteItem(22340, 1);
			player.getSkills().addXp(skill, 100);
			player.getInventory().refresh();
			player.sm("You have chosen "+Skills.SKILLS[skill]+" experience.");
			player.closeInterfaces();
		}
		
		if (interfaceId == 178) {
			if (componentId == 19) {
				player.getPackets().sendOpenURL(Settings.DONATE);
				player.closeInterfaces();
			}
			if (componentId == 47) {
				player.closeInterfaces();
			}
			if (componentId == 28) {
				player.closeInterfaces();
			}
		}
		if (interfaceId == 1143) {
			switch (componentId) {
			case 1: // home button
				player.getPackets().sendConfig(2226, -1);
				break;
			case 3: // favorites
				player.getPackets().sendConfig(2226, 8);
				break;
			case 7: // auras
				player.getPackets().sendConfig(2226, 1);
				break;
			case 8: // effects
				player.getPackets().sendConfig(2226, 9);
				break;
			case 9: // emotes
				player.getPackets().sendConfig(2226, 2);
				break;
			case 10: // costumes
				player.getPackets().sendConfig(2226, 3);
				break;
			case 11: // titles
				player.getPackets().sendConfig(2226, 4);
				break;
			case 12: // recolors
				player.getPackets().sendConfig(2226, 5);
				break;
			case 13: // special offers
				player.getPackets().sendConfig(2226, 6);
				break;
			case 103: // close button
				player.closeInterfaces();
				if (player.getInterfaceManager().hasRezizableScreen()) {
					player.getInterfaceManager().sendFullScreenInterfaces();
				} else {
					player.getInterfaceManager().sendFixedInterfaces();
				}
				break;
			}
		}
		
		if (interfaceId == 506) {
			if (player.getControlerManager().getControler() != null) {
				player.sendMessage("Portal is currently unavailabe.");
				return;
			}
			RunePortal.handleButtons(player, componentId);
		}
		
		if (interfaceId == 1253) {
		 	if (componentId == 93) {
		 		player.getSqueal().spin();
			} else if (componentId == 7) {
				player.getPackets().sendOpenURL("http://google.com/");
			} else if (componentId == 239) {
				player.getSqueal().close();
		 	} else if (componentId == 192) {
		 		player.getSqueal().claimItem();
				player.closeInterfaces();
		 	} else if (componentId == 273) {
		 		player.getSqueal().open();
			} else if (componentId == 258) {
				player.getSqueal().claimItem();
				player.getSqueal().close();
			} else if (componentId == 106) {
				player.getSqueal().close();
			}
		}
		
		if (interfaceId == 1072) {
			ArtisanWorkshop.handleButtons(player, componentId);
		}
		if (interfaceId == 387) {
			if (componentId == 41) {
				player.getInterfaceManager().sendInterface(1178);
			}
		}
		
		if (interfaceId == 1226) {
			if (componentId == 12) {
				player.getPackets().sendOpenURL(Settings.DONATE);
				player.closeInterfaces();
			} else {
				player.closeInterfaces();
			}
		}
		
		if (interfaceId == 1139) {
			if (componentId == 18) {
				player.getSqueal().open();
			} else if (componentId == 23) {
		 		player.getPackets().sendOpenURL("http://google.com/");
			}
		}
		
		if (interfaceId == 1252) {
		 	if (componentId == 3) {
		 		player.getSqueal().open();
		 	} else if (componentId == 5) {
				player.getPackets().closeInterface(player.getInterfaceManager().hasRezizableScreen() ? 11 : 0);
				player.getPackets().sendGameMessage("This icon will appear next time you login.");
			}
		} 

		if (interfaceId == 548 && componentId == 68) {
			player.getPackets().sendIComponentText(1139, 10, " " + player.getSpins() + " ");
		}
		
		if (interfaceId == 548 || interfaceId == 746) {
			
			if (componentId == 207 || componentId == 159) {
				if (packetId == 14) {
					player.getPackets().sendRunScript(5557, 1);
					player.getPackets().sendRunScript(5560, player.getCoins(), "n");
				} else if (packetId == 67) {
					player.getAttributes().put("money_remove", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "How much would you like to withdraw?" });
				} else if (packetId == 5) {
					player.sendMessage("Your money pouch currently holds: <col=FF0000>"+Utils.formatNumber(player.getMoneyPouch())+"</col> coins.");
				} else if (packetId == 55) {
					if (player.getInterfaceManager().containsScreenInter()) {
						player.getPackets().sendGameMessage("Please finish what you're doing before opening the price checker.");
						return;
					}
					player.stopAll();
					player.getPriceCheckManager().openPriceCheck();
				}
				return;
			}
			
			if (componentId == 17 || componentId == 54) {
				if (packetId == 14) {
					player.getSkills().switchXPDisplay();
				} else if (packetId == 67) {
					player.getSkills().switchXPPopup();
				} else if (packetId == 5) {
					player.getSkills().setupXPCounter();
				}
				return;
			}
			
			if (componentId == 148 || componentId == 199) {
				if (player.getInterfaceManager().containsScreenInter() || player.getInterfaceManager().containsInventoryInter()) {
					player.getPackets().sendGameMessage("Please finish what you're doing before opening the world map.");
					return;
				}
				player.getPackets().sendWindowsPane(755, 0);
				int posHash = player.getX() << 14 | player.getY();
				player.getPackets().sendGlobalConfig(622, posHash);
				player.getPackets().sendGlobalConfig(674, posHash);
			}
			
			return;
		}
		
		if (interfaceId == 1264) {
			if (componentId == 0) {
				player.closeInterfaces();
				player.getPackets().sendWindowsPane(player.getInterfaceManager().hasRezizableScreen() ? 746 : 548, 0);
			}
		}

		
		if (interfaceId == 182) {
			if (player.getInterfaceManager().containsInventoryInter())
				return;
			if (componentId == 6 || componentId == 13)
				if (!player.hasFinished())
					player.logout(componentId == 6);
		} else if (interfaceId == 1165) {
			// if (componentId == 22)
			// Summoning.closeDreadnipInterface(player);
		} else if (interfaceId == 880) {
			if (componentId >= 7 && componentId <= 19)
				Familiar.setLeftclickOption(player, (componentId - 7) / 2);
			else if (componentId == 21)
				Familiar.confirmLeftOption(player);
			else if (componentId == 25)
				Familiar.setLeftclickOption(player, 7);
			else if (interfaceId == 1265) {
				Logger.log("ButtonHandler", "Shop Button Id: " + componentId + "");
				Shop shop = (Shop) player.getAttributes().get("Shop");
				if (shop == null)
					return;
				Integer slot = (Integer) player.getAttributes().get("ShopSelectedSlot");
				boolean isBuying = (boolean) player.getAttributes()
						.get("shop_buying") == true;
				int amount = (int) player.getAttributes().get(
						"amount_shop");
				if (componentId == 20) {
					player.getAttributes().put("ShopSelectedSlot",
							slotId);
					if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
						shop.sendInfo(player, slotId, isBuying);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
						shop.handleShop(player, slotId, 1);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
						shop.handleShop(player, slotId, 5);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
						shop.handleShop(player, slotId, 10);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
						shop.handleShop(player, slotId, 50);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
						shop.handleShop(player, slotId, 500);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
						shop.sendExamine(player, slotId);
				} else if (componentId == 201) {
					if (slot == null)
						return;
					if (isBuying) {
						if (amount < 1)
							return;

						shop.buy(player, slot, amount);
					} else {
						shop.sell(player, slot, amount);
					}
				} else if (componentId == 208) { // +5
					player.getAttributes().put("amount_shop",
							amount + 5);
					player.getPackets().sendIComponentText(interfaceId, 67,
							String.valueOf(amount));
				} else if (componentId == 15) {
					player.getAttributes().put("amount_shop",
							amount + 1);
					player.getPackets().sendIComponentText(interfaceId, 67,
							String.valueOf(amount));
				} else if (componentId == 214) {
					if (amount < 1) {
						player.getPackets().sendGameMessage(
								"You can not go any further.");
						return;
					}
					player.getAttributes().put("amount_shop",
							amount - 1);
					player.getPackets().sendIComponentText(interfaceId, 67,
							String.valueOf(amount));
				} else if (componentId == 15) {
					player.getAttributes().put("amount_shop",
							amount + 5);
					player.getPackets().sendIComponentText(interfaceId, 67,
							String.valueOf(amount));
				} else if (componentId == 220) {
					player.getAttributes().put("amount_shop", 1);
					player.getPackets().sendIComponentText(interfaceId, 67,
							String.valueOf(amount));
				} else if (componentId == 211) {
					if (slot == null)
						return;
					player.getAttributes().put(
							"amount_shop",
							isBuying ? shop.getMainStock()[slot].getAmount()
									: player.getInventory().getItems()
											.getItems()[slot].getAmount());
					player.getPackets().sendIComponentText(interfaceId, 67,
							String.valueOf(amount));
				} else if (componentId == 29) {
					player.getAttributes().put("shop_buying", false);
					player.getAttributes().put("amount_shop", 1);
				} else if (componentId == 28) {
					player.getAttributes().put("shop_buying", true);
					player.getAttributes().put("amount_shop", 1);
				}
			} else if (interfaceId == 1266) {
				if (componentId == 0) {
					if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
						player.getInventory().sendExamine(slotId);
					else {
						Shop shop = (Shop) player.getAttributes()
								.get("Shop");
						if (shop == null)
							return;
						if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
							shop.sendValue(player, slotId);
						else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
							shop.sell(player, slotId, 1);
						else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
							shop.sell(player, slotId, 5);
						else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
							shop.sell(player, slotId, 10);
						else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
							shop.sell(player, slotId, 50);
					}
				}
			}
		} else if (interfaceId == 662) {
			if (player.getFamiliar() == null) {
				if (player.getPet() == null) {
					return;
				}
				if (componentId == 49)
					player.getPet().call();
				else if (componentId == 51)
					player.getDialogueManager().startDialogue("DismissD");
				return;
			}
			if (componentId == 49)
				player.getFamiliar().call();
			else if (componentId == 51)
				player.getDialogueManager().startDialogue("DismissD");
			else if (componentId == 67)
				player.getFamiliar().takeBob();
			else if (componentId == 69)
				player.getFamiliar().renewFamiliar();
			else if (componentId == 74) {
				if (player.getFamiliar().getSpecialAttack() == SpecialAttack.CLICK)
					player.getFamiliar().setSpecial(true);
				if (player.getFamiliar().hasSpecialOn())
					player.getFamiliar().submitSpecial(player);
			}
		} else if (interfaceId == 747) {
			if (componentId == 8) {
				Familiar.selectLeftOption(player);
			} else if (player.getPet() != null) {
				if (componentId == 11 || componentId == 20) {
					player.getPet().call();
				} else if (componentId == 12 || componentId == 21) {
					player.getDialogueManager().startDialogue("DismissD");
				} else if (componentId == 10 || componentId == 19) {
					player.getPet().sendFollowerDetails();
				}
			} else if (player.getFamiliar() != null) {
				if (componentId == 11 || componentId == 20)
					player.getFamiliar().call();
				else if (componentId == 12 || componentId == 21)
					player.getDialogueManager().startDialogue("DismissD");
				else if (componentId == 13 || componentId == 22)
					player.getFamiliar().takeBob();
				else if (componentId == 14 || componentId == 23)
					player.getFamiliar().renewFamiliar();
				else if (componentId == 19 || componentId == 10)
					player.getFamiliar().sendFollowerDetails();
				else if (componentId == 18) {
					if (player.getFamiliar().getSpecialAttack() == SpecialAttack.CLICK)
						player.getFamiliar().setSpecial(true);
					if (player.getFamiliar().hasSpecialOn())
						player.getFamiliar().submitSpecial(player);
				}
			}

		} else if (interfaceId == 309)
			PlayerLook.handleHairdresserSalonButtons(player, componentId,
					slotId);
		else if (interfaceId == 729)
			PlayerLook.handleThessaliasMakeOverButtons(player, componentId,
					slotId);
		else if (interfaceId == 187) {
			if (componentId == 1) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getMusicsManager().playAnotherMusic(slotId / 2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getMusicsManager().sendHint(slotId / 2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getMusicsManager().addToPlayList(slotId / 2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getMusicsManager().removeFromPlayList(slotId / 2);
			} else if (componentId == 4)
				player.getMusicsManager().addPlayingMusicToPlayList();
			else if (componentId == 10)
				player.getMusicsManager().switchPlayListOn();
			else if (componentId == 11)
				player.getMusicsManager().clearPlayList();
			else if (componentId == 13)
				player.getMusicsManager().switchShuffleOn();
		} else if (interfaceId == 275) {
			if (componentId == 14) {
				player.getPackets().sendOpenURL(Settings.WEBSITE);
			}
		} else if (interfaceId == 464 || interfaceId == 590) {
			player.getEmotesManager().useBookEmote(componentId, slotId);
		} else if (interfaceId == 192) {
			if (componentId == 2)
				player.getCombatDefinitions().switchDefensiveCasting();
			else if (componentId == 7)
				player.getCombatDefinitions().switchShowCombatSpells();
			else if (componentId == 9)
				player.getCombatDefinitions().switchShowTeleportSkillSpells();
			else if (componentId == 11)
				player.getCombatDefinitions().switchShowMiscallaneousSpells();
			else if (componentId == 13)
				player.getCombatDefinitions().switchShowSkillSpells();
			else if (componentId >= 15 & componentId <= 17)
				player.getCombatDefinitions()
						.setSortSpellBook(componentId - 15);
			else
				Magic.processNormalSpell(player, componentId, packetId);
		} else if (interfaceId == 334) {
			if (componentId == 22)
				player.closeInterfaces();
			else if (componentId == 21)
				player.getTrade().accept(false);
		} else if (interfaceId == 335) {
			if (componentId == 18)
				player.getTrade().accept(true);
			else if (componentId == 20)
				player.closeInterfaces();
			else if (componentId == 32) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getTrade().removeItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getTrade().removeItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getTrade().removeItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getTrade().removeItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getAttributes().put("trade_item_X_Slot",
							slotId);
					player.getAttributes().put("trade_isRemove",
							Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getTrade().sendValue(slotId, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getTrade().sendExamine(slotId, false);
			} else if (componentId == 35) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getTrade().sendValue(slotId, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getTrade().sendExamine(slotId, true);
			}

		} else if (interfaceId == 336) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getTrade().addItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getTrade().addItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getTrade().addItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getTrade().addItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getAttributes().put("trade_item_X_Slot",
							slotId);
					player.getAttributes().remove("trade_isRemove");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getTrade().sendValue(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 300) {
			ForgingInterface.handleIComponents(player, componentId);
		} else if (interfaceId == 206) {
			if (componentId == 15) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getPriceCheckManager().removeItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getPriceCheckManager().removeItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getPriceCheckManager().removeItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getPriceCheckManager().removeItem(slotId,
							Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getAttributes().put("pc_item_X_Slot",
							slotId);
					player.getAttributes().put("pc_isRemove",
							Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				}
			}
		} else if (interfaceId == 672) {
			if (componentId == 16) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					Summoning.createPouch(player, slotId2, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					Summoning.createPouch(player, slotId2, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					Summoning.createPouch(player, slotId2, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					Summoning.createPouch(player, slotId2, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					Summoning.createPouch(player, slotId2, 28);// x
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET) {
					player.getPackets().sendGameMessage(
							"You currently need "
									+ ItemDefinitions.getItemDefinitions(
											slotId2)
											.getCreateItemRequirements());
				}
			}
		} else if (interfaceId == 207) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getPriceCheckManager().addItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getPriceCheckManager().addItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getPriceCheckManager().addItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getPriceCheckManager().addItem(slotId,
							Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getAttributes().put("pc_item_X_Slot",
							slotId);
					player.getAttributes().remove("pc_isRemove");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 665) {
			if (player.getFamiliar() == null
					|| player.getFamiliar().getBob() == null)
				return;
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getFamiliar().getBob().addItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFamiliar().getBob().addItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFamiliar().getBob().addItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFamiliar().getBob()
							.addItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getAttributes().put("bob_item_X_Slot",
							slotId);
					player.getAttributes().remove("bob_isRemove");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 671) {
			if (player.getFamiliar() == null
					|| player.getFamiliar().getBob() == null)
				return;
			if (componentId == 27) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFamiliar().getBob()
							.removeItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getAttributes().put("bob_item_X_Slot",
							slotId);
					player.getAttributes().put("bob_isRemove",
							Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				}
			} else if (componentId == 29)
				player.getFamiliar().takeBob();
		} else if (interfaceId == 916) {
			SkillsDialogue.handleSetQuantityButtons(player, componentId);
		} else if (interfaceId == 193) {
			if (componentId == 5)
				player.getCombatDefinitions().switchShowCombatSpells();
			else if (componentId == 7)
				player.getCombatDefinitions().switchShowTeleportSkillSpells();
			else if (componentId >= 9 && componentId <= 11)
				player.getCombatDefinitions().setSortSpellBook(componentId - 9);
			else if (componentId == 18)
				player.getCombatDefinitions().switchDefensiveCasting();
			else
				Magic.processAncientSpell(player, componentId, packetId);
		} else if (interfaceId == 430) {
			if (componentId == 5)
				player.getCombatDefinitions().switchShowCombatSpells();
			else if (componentId == 7)
				player.getCombatDefinitions().switchShowTeleportSkillSpells();
			else if (componentId == 9)
				player.getCombatDefinitions().switchShowMiscallaneousSpells();
			else if (componentId >= 11 & componentId <= 13)
				player.getCombatDefinitions()
						.setSortSpellBook(componentId - 11);
			else if (componentId == 20)
				player.getCombatDefinitions().switchDefensiveCasting();
			else
				Magic.processLunarSpell(player, componentId, packetId);
		} else if (interfaceId == 261) {
			if (player.getInterfaceManager().containsInventoryInter())
				return;
			if (componentId == 22) {
				if (player.getInterfaceManager().containsScreenInter()) {
					player.getPackets()
							.sendGameMessage(
									"Please close the interface you have open before setting your graphic options.");
					return;
				}
				player.stopAll();
				player.getInterfaceManager().sendInterface(742);
			} else if (componentId == 12)
				player.switchAllowChatEffects();
			else if (componentId == 13) { // chat setup
				player.getInterfaceManager().sendSettings(982);
			} else if (componentId == 14)
				player.switchMouseButtons();
			else if (componentId == 24) // audio options
				player.getInterfaceManager().sendSettings(429);
			else if (componentId == 26)
				player.getInterfaceManager().sendInterface(623);
		} else if (interfaceId == 429) {
			if (componentId == 18)
				player.getInterfaceManager().sendSettings();
		} else if (interfaceId == 982) {
			if (componentId == 5)
				player.getInterfaceManager().sendSettings();
			else if (componentId == 41)
				player.setPrivateChatSetup(player.getPrivateChatSetup() == 0 ? 1
						: 0);
			else if (componentId >= 49 && componentId <= 66)
				player.setPrivateChatSetup(componentId - 48);
			else if (componentId >= 72 && componentId <= 91)
				player.setFriendChatSetup(componentId - 72);
		} else if (interfaceId == 271) {
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					if (componentId == 8 || componentId == 42)
						player.getPrayer().switchPrayer(slotId);

					else if (componentId == 43
							&& player.getPrayer().isUsingQuickPrayer())
						player.getPrayer().switchSettingQuickPrayer();
				}
			});
		} else if (interfaceId == 320) {
			player.stopAll();
			int lvlupSkill = -1;
			int skillMenu = -1;
			switch (componentId) {
			case 150: // Attack
				skillMenu = 1;
				if (player.getAttributes().remove("leveledUp[0]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 1);
				} else {
					lvlupSkill = 0;
					player.getPackets().sendConfig(1230, 10);
				}
				break;
			case 9: // Strength
				skillMenu = 2;
				if (player.getAttributes().remove("leveledUp[2]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 2);
				} else {
					lvlupSkill = 2;
					player.getPackets().sendConfig(1230, 20);
				}
				break;
			case 22: // Defence
				skillMenu = 5;
				if (player.getAttributes().remove("leveledUp[1]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 5);
				} else {
					lvlupSkill = 1;
					player.getPackets().sendConfig(1230, 40);
				}
				break;
			case 40: // Ranged
				skillMenu = 3;
				if (player.getAttributes().remove("leveledUp[4]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 3);
				} else {
					lvlupSkill = 4;
					player.getPackets().sendConfig(1230, 30);
				}
				break;
			case 58: // Prayer
				if (player.getAttributes().remove("leveledUp[5]") != Boolean.TRUE) {
					skillMenu = 7;
					player.getPackets().sendConfig(965, 7);
				} else {
					lvlupSkill = 5;
					player.getPackets().sendConfig(1230, 60);
				}
				break;
			case 71: // Magic
				if (player.getAttributes().remove("leveledUp[6]") != Boolean.TRUE) {
					skillMenu = 4;
					player.getPackets().sendConfig(965, 4);
				} else {
					lvlupSkill = 6;
					player.getPackets().sendConfig(1230, 33);
				}
				break;
			case 84: // Runecrafting
				if (player.getAttributes().remove("leveledUp[20]") != Boolean.TRUE) {
					skillMenu = 12;
					player.getPackets().sendConfig(965, 12);
				} else {
					lvlupSkill = 20;
					player.getPackets().sendConfig(1230, 100);
				}
				break;
			case 102: // Construction
				skillMenu = 22;
				if (player.getAttributes().remove("leveledUp[21]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 22);
				} else {
					lvlupSkill = 21;
					player.getPackets().sendConfig(1230, 698);
				}
				break;
			case 145: // Hitpoints
				skillMenu = 6;
				if (player.getAttributes().remove("leveledUp[3]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 6);
				} else {
					lvlupSkill = 3;
					player.getPackets().sendConfig(1230, 50);
				}
				break;
			case 15: // Agility
				skillMenu = 8;
				if (player.getAttributes().remove("leveledUp[16]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 8);
				} else {
					lvlupSkill = 16;
					player.getPackets().sendConfig(1230, 65);
				}
				break;
			case 28: // Herblore
				skillMenu = 9;
				if (player.getAttributes().remove("leveledUp[15]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 9);
				} else {
					lvlupSkill = 15;
					player.getPackets().sendConfig(1230, 75);
				}
				break;
			case 46: // Thieving
				skillMenu = 10;
				if (player.getAttributes().remove("leveledUp[17]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 10);
				} else {
					lvlupSkill = 17;
					player.getPackets().sendConfig(1230, 80);
				}
				break;
			case 64: // Crafting
				skillMenu = 11;
				if (player.getAttributes().remove("leveledUp[12]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 11);
				} else {
					lvlupSkill = 12;
					player.getPackets().sendConfig(1230, 90);
				}
				break;
			case 77: // Fletching
				skillMenu = 19;
				if (player.getAttributes().remove("leveledUp[9]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 19);
				} else {
					lvlupSkill = 9;
					player.getPackets().sendConfig(1230, 665);
				}
				break;
			case 90: // Slayer
				skillMenu = 20;
				if (player.getAttributes().remove("leveledUp[18]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 20);
				} else {
					lvlupSkill = 18;
					player.getPackets().sendConfig(1230, 673);
				}
				break;
			case 108: // Hunter
				skillMenu = 23;
				if (player.getAttributes().remove("leveledUp[22]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 23);
				} else {
					lvlupSkill = 22;
					player.getPackets().sendConfig(1230, 689);
				}
				break;
			case 140: // Mining
				skillMenu = 13;
				if (player.getAttributes().remove("leveledUp[14]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 13);
				} else {
					lvlupSkill = 14;
					player.getPackets().sendConfig(1230, 110);
				}
				break;
			case 135: // Smithing
				skillMenu = 14;
				if (player.getAttributes().remove("leveledUp[13]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 14);
				} else {
					lvlupSkill = 13;
					player.getPackets().sendConfig(1230, 115);
				}
				break;
			case 34: // Fishing
				skillMenu = 15;
				if (player.getAttributes().remove("leveledUp[10]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 15);
				} else {
					lvlupSkill = 10;
					player.getPackets().sendConfig(1230, 120);
				}
				break;
			case 52: // Cooking
				skillMenu = 16;
				if (player.getAttributes().remove("leveledUp[7]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 16);
				} else {
					lvlupSkill = 7;
					player.getPackets().sendConfig(1230, 641);
				}
				break;
			case 130: // Firemaking
				skillMenu = 17;
				if (player.getAttributes().remove("leveledUp[11]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 17);
				} else {
					lvlupSkill = 11;
					player.getPackets().sendConfig(1230, 649);
				}
				break;
			case 125: // Woodcutting
				skillMenu = 18;
				if (player.getAttributes().remove("leveledUp[8]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 18);
				} else {
					lvlupSkill = 8;
					player.getPackets().sendConfig(1230, 660);
				}
				break;
			case 96: // Farming
				skillMenu = 21;
				if (player.getAttributes().remove("leveledUp[19]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 21);
				} else {
					lvlupSkill = 19;
					player.getPackets().sendConfig(1230, 681);
				}
				break;
			case 114: // Summoning
				skillMenu = 24;
				if (player.getAttributes().remove("leveledUp[23]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 24);
				} else {
					lvlupSkill = 23;
					player.getPackets().sendConfig(1230, 705);
				}
				break;
			case 120: // Dung
				skillMenu = 25;
				if (player.getAttributes().remove("leveledUp[24]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 25);
				} else {
					lvlupSkill = 24;
					player.getPackets().sendConfig(1230, 705);
				}
				break;
			}

			/*
			 * player.getInterfaceManager().sendInterface( lvlupSkill != -1 ?
			 * 741 : 499);
			 */
			player.getInterfaceManager().sendScreenInterface(317, 1218);
			player.getPackets().sendInterface(false, 1218, 1, 1217); // seems to
																		// fix
			if (lvlupSkill != -1)
				LevelUp.switchFlash(player, lvlupSkill, false);
			if (skillMenu != -1)
				player.getAttributes().put("skillMenu", skillMenu);
		} else if (interfaceId == 1218) {
			if ((componentId >= 33 && componentId <= 55) || componentId == 120
					|| componentId == 151 || componentId == 189)
				player.getPackets().sendInterface(false, 1218, 1, 1217); // seems
																			// to
																			// fix
		} else if (interfaceId == 499) {
			int skillMenu = -1;
			if (player.getAttributes().get("skillMenu") != null)
				skillMenu = (Integer) player.getAttributes().get(
						"skillMenu");
			if (componentId >= 10 && componentId <= 25)
				player.getPackets().sendConfig(965,
						((componentId - 10) * 1024) + skillMenu);
			else if (componentId == 29)
				// close inter
				player.stopAll();

		} else if (interfaceId == 387) {
			if (player.getInterfaceManager().containsInventoryInter())
				return;
			if (componentId == 6) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int hatId = player.getEquipment().getHatId();
					if (hatId == 24437 || hatId == 24439 || hatId == 24440 || hatId == 24441) {
						player.getDialogueManager().startDialogue("FlamingSkull", player.getEquipment().getItem(Equipment.SLOT_HAT), -1);
						return;
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					ButtonHandler.sendRemove(player, Equipment.SLOT_HAT);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET) {
					player.getEquipment().sendExamine(Equipment.SLOT_HAT);
				}
			} else if (componentId == 9) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20769 || capeId == 20771) {
						player.getSkills().restoreSummoning();
						player.setNextAnimation(new Animation(8502));
						player.setNextGraphics(new Graphics(1308));
						player.getPackets().sendGameMessage("You restored your Summoning points with your awesome cape!", true);
					}
				}
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20769 || capeId == 20771) {
						SkillCapeCustomizer.startCustomizing(player, capeId);
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20767) {
						SkillCapeCustomizer.startCustomizing(player, capeId);
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					ButtonHandler.sendRemove(player, Equipment.SLOT_CAPE);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET) {
					player.getEquipment().sendExamine(Equipment.SLOT_CAPE);
				}
			} else if (componentId == 12) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706 || amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true, Transportation.EMOTE, Transportation.GFX, 4, new WorldTile(3087, 3496, 0))) {
							Item amulet = player.getEquipment().getItem(Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(Equipment.SLOT_AMULET);
							}
						}
					} else if (amuletId == 1704 || amuletId == 10352) {
						player.getPackets().sendGameMessage("The amulet has ran out of charges. You need to recharge it if you wish it use it once more.");
					}
			} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706 || amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true, Transportation.EMOTE, Transportation.GFX, 4, new WorldTile(2918, 3176, 0))) {
							Item amulet = player.getEquipment().getItem(Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706 || amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true, Transportation.EMOTE, Transportation.GFX, 4, new WorldTile(3105, 3251, 0))) {
							Item amulet = player.getEquipment().getItem(Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706
							|| amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true, Transportation.EMOTE, Transportation.GFX, 4, new WorldTile(3293, 3163, 0))) {
							Item amulet = player.getEquipment().getItem(Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_AMULET);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_AMULET);
			} else if (componentId == 15) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int weaponId = player.getEquipment().getWeaponId();
					if (weaponId == 15484) {
						player.getInterfaceManager().gazeOrbOfOculus();
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					// Snow Ball Handling
					Item snowball = player.getEquipment().getItem(Equipment.SLOT_WEAPON);
					if (snowball.getId() == 10501) {
            			player.getPackets().sendPlayerOption("null", 1, true);
        			}
					ButtonHandler.sendRemove(player, Equipment.SLOT_WEAPON);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET){
					player.getEquipment().sendExamine(Equipment.SLOT_WEAPON);
				}
			} else if (componentId == 18) {
				ButtonHandler.sendRemove(player, Equipment.SLOT_CHEST);
			} else if (componentId == 21) {
				ButtonHandler.sendRemove(player, Equipment.SLOT_SHIELD);
			} else if (componentId == 24) {
				ButtonHandler.sendRemove(player, Equipment.SLOT_LEGS);
			} else if (componentId == 27) {
				ButtonHandler.sendRemove(player, Equipment.SLOT_HANDS);
			} else if (componentId == 30) {
				ButtonHandler.sendRemove(player, Equipment.SLOT_FEET);
			} else if (componentId == 33) {
				ButtonHandler.sendRemove(player, Equipment.SLOT_RING);
			} else if (componentId == 36){
				ButtonHandler.sendRemove(player, Equipment.SLOT_ARROWS);
			} else if (componentId == 45) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					ButtonHandler.sendRemove(player, Equipment.SLOT_AURA);
					player.getAuraManager().removeAura();
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET) {
					player.getEquipment().sendExamine(Equipment.SLOT_AURA);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					player.getAuraManager().activate();
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					player.getAuraManager().sendAuraRemainingTime();
				}
			} else if (componentId == 40) {
				player.stopAll();
				player.getInterfaceManager().sendInterface(17);
			} else if (componentId == 37) {
				openEquipmentBonuses(player, false);
			}
		} else if (interfaceId == 1265) {
			Shop shop = (Shop) player.getAttributes().get("Shop");
			if (shop == null)
				return;
			Integer slot = (Integer) player.getAttributes().get(
					"ShopSelectedSlot");
			boolean isBuying = player.getAttributes().get(
					"shop_buying") != null;
			if (componentId == 20) {
				player.getAttributes()
						.put("ShopSelectedSlot", slotId);
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					shop.sendInfo(player, slotId, isBuying);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					shop.handleShop(player, slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					shop.handleShop(player, slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					shop.handleShop(player, slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					shop.handleShop(player, slotId, 50);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET)
					player.getPackets().sendGameMessage(
							"You aren't allowed to buy all of that item.");
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					shop.handleShop(player, slotId, 500);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					shop.sendExamine(player, slotId);
			} else if (componentId == 201) {
				if (slot == null)
					return;
				if (isBuying)
					shop.buy(player, slot, shop.getAmount());
				else {
					shop.sell(player, slot, shop.getAmount());
					player.getPackets().sendConfig(2563, 0);
					player.getPackets().sendConfig(2565, 1); // this is to
																// update the
																// tab.
				}
			} else if (componentId == 208) {
				shop.setAmount(player, shop.getAmount() + 5);
			} else if (componentId == 15) {
				shop.setAmount(player, shop.getAmount() + 1);
			} else if (componentId == 214) {
				if (shop.getAmount() <= 1) {
					return;
				}
				shop.setAmount(player, shop.getAmount() - 1);
			} else if (componentId == 217) {
				if (shop.getAmount() < 6) {
					return;
				}
				shop.setAmount(player, shop.getAmount() - 5);
			} else if (componentId == 220) {
				shop.setAmount(player, 1);
			} else if (componentId == 211) {
				if (slot == null)
					return;
				shop.setAmount(
						player,
						isBuying ? shop.getMainStock()[slot].getAmount()
								: player.getInventory().getItems().getItems()[slot]
										.getAmount());
			} else if (componentId == 29) {
				player.getPackets().sendConfig(2561, 93);
				player.getAttributes().remove("shop_buying");
				shop.setAmount(player, 1);
			} else if (componentId == 28) {
				player.getAttributes().put("shop_buying", true);
				shop.setAmount(player, 1);
			}
		} else if (interfaceId == 1266) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
				else {
					Shop shop = (Shop) player.getAttributes().get(
							"Shop");
					if (shop == null)
						return;
					player.getPackets().sendConfig(2563, slotId);
					if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
						shop.sendValue(player, slotId);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
						shop.sell(player, slotId, 1);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
						shop.sell(player, slotId, 5);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
						shop.sell(player, slotId, 10);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
						shop.sell(player, slotId, 50);
				}
			}
			// }
		} else if (interfaceId == 640) {
			if (componentId == 18 || componentId == 22) {
				player.getAttributes().put("WillDuelFriendly", true);
				player.getPackets().sendConfig(283, 67108864);
			} else if (componentId == 19 || componentId == 21) {
				player.getAttributes().put("WillDuelFriendly", false);
				player.getPackets().sendConfig(283, 134217728);
			} else if (componentId == 20) {
				DuelControler.challenge(player);
			}
		} else if (interfaceId == 650) {
			if (componentId == 15) {
				player.stopAll();
				player.setNextWorldTile(new WorldTile(2974, 4384, player
						.getPlane()));
				player.getControlerManager().startControler(
						"CorpBeastControler");
			} else if (componentId == 16)
				player.closeInterfaces();
		} else if (interfaceId == 667) {
			if (componentId == 14) {
				if (slotId >= 14)
					return;
				Item item = player.getEquipment().getItem(slotId);
				if (item == null)
					return;
				if (packetId == 3)
					player.getPackets().sendGameMessage(ItemExamines.getExamine(item));
				else if (packetId == 216) {
					sendRemove(player, slotId);
					ButtonHandler.refreshEquipBonuses(player);
				}
			} else if (componentId == 46 && player.getAttributes().remove("Banking") != null) {
				player.getBank().openBank();
			}
		} else if (interfaceId == 670) {
			if (componentId == 0) {
				if (slotId >= player.getInventory().getItemsContainerSize())
					return;
				Item item = player.getInventory().getItem(slotId);
				if (item == null)
					return;
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					if (sendWear(player, slotId, item.getId()))
						ButtonHandler.refreshEquipBonuses(player);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == Inventory.INVENTORY_INTERFACE) { // inventory
			if (componentId == 0) {
				if (slotId > 27 || player.getInterfaceManager().containsInventoryInter())
					return;
				Item item = player.getInventory().getItem(slotId);
				if (item == null || item.getId() != slotId2)
					return;
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					InventoryOptionsHandler.handleItemOption1(player, slotId, slotId2, item);
					if (Settings.DEBUG && player.getRights() == 2)
						System.out.println("Option 1");
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					InventoryOptionsHandler.handleItemOption2(player, slotId, slotId2, item);
					if (Settings.DEBUG && player.getRights() == 2)
						System.out.println("Option 2");
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					InventoryOptionsHandler.handleItemOption3(player, slotId, slotId2, item);
					if (Settings.DEBUG && player.getRights() == 2)
						System.out.println("Option 3");
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					InventoryOptionsHandler.handleItemOption4(player, slotId, slotId2, item);
					if (Settings.DEBUG && player.getRights() == 2)
						System.out.println("Option 4");
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					InventoryOptionsHandler.handleItemOption5(player, slotId, slotId2, item);
					if (Settings.DEBUG && player.getRights() == 2)
						System.out.println("Option 5");
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET) {
					InventoryOptionsHandler.handleItemOption6(player, slotId, slotId2, item);
					if (Settings.DEBUG && player.getRights() == 2)
						System.out.println("Option 6");
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON7_PACKET) {
					InventoryOptionsHandler.handleItemOption7(player, slotId, slotId2, item);
					if (Settings.DEBUG && player.getRights() == 2)
						System.out.println("Option 7");
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET) {
					InventoryOptionsHandler.handleItemOption8(player, slotId, slotId2, item);
					if (Settings.DEBUG && player.getRights() == 2)
						System.out.println("Option 8");
				}
			}
		} else if (interfaceId == 742) {
			if (componentId == 46) // close
				player.stopAll();
		} else if (interfaceId == 743) {
			if (componentId == 20) // close
				player.stopAll();
		} else if (interfaceId == 741) {
			if (componentId == 9) // close
				player.stopAll();
		} else if (interfaceId == 749) {
			if (componentId == 4) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) // activate
					player.getPrayer().switchQuickPrayers();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) // switch
					player.getPrayer().switchSettingQuickPrayer();
			}
		} else if (interfaceId == 750) {
			if (componentId == 4) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					player.toogleRun(player.isResting() ? false : true);
					if (player.isResting())
						player.stopAll();
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					if (player.isResting()) {
						player.stopAll();
						return;
					}
					long currentTime = Utils.currentTimeMillis();
					if (player.getEmotesManager().getNextEmoteEnd() >= currentTime) {
						player.getPackets().sendGameMessage("You can't rest while perfoming an emote.");
						return;
					}
					if (player.getLockDelay() >= currentTime) {
						player.getPackets().sendGameMessage("You can't rest while perfoming an action.");
						return;
					}
					player.stopAll();
					player.getActionManager().setAction(new Rest());
				}
			}
		} else if (interfaceId == 11) {
			if (componentId == 17) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().depositItem(slotId, 1, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().depositItem(slotId, 5, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getBank().depositItem(slotId, 10, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getBank().depositItem(slotId, Integer.MAX_VALUE, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getAttributes().put("bank_item_X_Slot", slotId);
					player.getAttributes().remove("bank_isWithdraw");
					player.getPackets().sendRunScript(108, new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
			} else if (componentId == 18)
				player.getBank().depositAllInventory(false);
			else if (componentId == 20)
				player.getBank().depositAllEquipment(false);
		} else if (interfaceId == 762) {
			if (componentId == 15)
				player.getBank().switchInsertItems();
			else if (componentId == 19)
				player.getBank().switchWithdrawNotes();
			else if (componentId == 33)
				player.getBank().depositAllInventory(true);
			else if (componentId == 37)
				player.getBank().depositAllEquipment(true);
			else if (componentId == 35)
				player.getBank().depositPouch();
			else if (componentId == 46) {
				player.closeInterfaces();
				player.getInterfaceManager().sendInterface(767);
				player.setCloseInterfacesEvent(new Runnable() {
					@Override
					public void run() {
						player.getBank().openBank();
					}
				});
			} else if (componentId >= 46 && componentId <= 64) {
				int tabId = 9 - ((componentId - 46) / 2);
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().setCurrentTab(tabId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().collapse(tabId);
			} else if (componentId == 95) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().withdrawItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().withdrawItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getBank().withdrawItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getBank().withdrawLastAmount(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getAttributes().put("bank_item_X_Slot", slotId);
					player.getAttributes().put("bank_isWithdraw", Boolean.TRUE);
					player.getPackets().sendInputIntegerScript(true, "How many would you like to withdraw?");
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getBank().withdrawItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET)
					player.getBank().withdrawItemButOne(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getBank().sendExamine(slotId);

			} else if (componentId == 119) {
				openEquipmentBonuses(player, true);
			}
		} else if (interfaceId == 763) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().depositItem(slotId, 1, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().depositItem(slotId, 5, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getBank().depositItem(slotId, 10, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getBank().depositLastAmount(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getAttributes().put("deposit_x_slot", slotId);
					player.getAttributes().put("deposit_x_bank", Boolean.TRUE);
					player.getPackets().sendInputIntegerScript(true, "How many would you like to deposit?");
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET) {
					player.getBank().depositItem(slotId, Integer.MAX_VALUE, true);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET) {
					player.getInventory().sendExamine(slotId);
				}
			}
		} else if (interfaceId == 767) {
			if (componentId == 10)
				player.getBank().openBank();
		} else if (interfaceId == 884) {
			if (componentId == 4) {
				int weaponId = player.getEquipment().getWeaponId();
				if (player.hasInstantSpecial(weaponId)) {
					player.performInstantSpecial(weaponId);
					return;
				}
				submitSpecialRequest(player);
			} else if (componentId >= 7 && componentId <= 10)
				player.getCombatDefinitions().setAttackStyle(componentId - 7);
			else if (componentId == 11)
				player.getCombatDefinitions().switchAutoRelatie();
		} else if (interfaceId == 755) {
			if (componentId == 44)
				player.getPackets().sendWindowsPane(
						player.getInterfaceManager().hasRezizableScreen() ? 746
								: 548, 2);
			else if (componentId == 42) {
				player.getHintIconsManager().removeAll();
				player.getPackets().sendConfig(1159, 1);
			}
		} else if (interfaceId == 20)
			SkillCapeCustomizer.handleSkillCapeCustomizer(player, componentId);
		else if (interfaceId == 1056) {
			if (componentId == 173)
				player.getInterfaceManager().sendInterface(917);
		} else if (interfaceId == 751) {
			if (componentId == 26) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFriendsIgnores().setPrivateStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFriendsIgnores().setPrivateStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFriendsIgnores().setPrivateStatus(2);
			} else if (componentId == 32) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setFilterGame(false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setFilterGame(true);
			} else if (componentId == 29) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setPublicStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.setPublicStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setPublicStatus(2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					player.setPublicStatus(3);
			} else if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFriendsIgnores().setFriendsChatStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFriendsIgnores().setFriendsChatStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFriendsIgnores().setFriendsChatStatus(2);
			} else if (componentId == 23) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setClanStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.setClanStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setClanStatus(2);
			} else if (componentId == 20) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setTradeStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.setTradeStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setTradeStatus(2);
			} else if (componentId == 17) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setAssistStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.setAssistStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setAssistStatus(2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET) {
					// ASSIST XP Earned/Time
				}
			} else if (componentId == 14) {
				player.getInterfaceManager().sendInterface(594);
			}
		} else if (interfaceId == 1163 || interfaceId == 1164
				|| interfaceId == 1168 || interfaceId == 1170
				|| interfaceId == 1173)
			player.getDominionTower().handleButtons(interfaceId, componentId);
		else if (interfaceId == 900)
			PlayerLook.handleMageMakeOverButtons(player, componentId);
		else if (interfaceId == 1028)
			PlayerLook.handleCharacterCustomizingButtons(player, componentId);
		else if (interfaceId == 1108 || interfaceId == 1109)
			player.getFriendsIgnores().handleFriendChatButtons(interfaceId,
					componentId, packetId);
		else if (interfaceId == 1079)
			player.closeInterfaces();
		else if (interfaceId == 374) {
			if (componentId >= 5 && componentId <= 9)
				player.setNextWorldTile(new WorldTile(
						FightPitsViewingOrb.ORB_TELEPORTS[componentId - 5]));
			else if (componentId == 15)
				player.stopAll();
		} else if (interfaceId == 1092) {
			player.stopAll();
			WorldTile destTile = null;
			switch (componentId) {
			case 47:
				destTile = HomeTeleport.LUMBRIDGE_LODE_STONE;
				break;
			case 42:
				destTile = HomeTeleport.BURTHORPE_LODE_STONE;
				break;
			case 39:
				destTile = HomeTeleport.LUNAR_ISLE_LODE_STONE;
				break;
			case 7:
				destTile = HomeTeleport.BANDIT_CAMP_LODE_STONE;
				break;
			case 50:
				destTile = HomeTeleport.TAVERLY_LODE_STONE;
				break;
			case 40:
				destTile = HomeTeleport.ALKARID_LODE_STONE;
				break;
			case 51:
				destTile = HomeTeleport.VARROCK_LODE_STONE;
				break;
			case 45:
				destTile = HomeTeleport.EDGEVILLE_LODE_STONE;
				break;
			case 46:
				destTile = HomeTeleport.FALADOR_LODE_STONE;
				break;
			case 48:
				destTile = HomeTeleport.PORT_SARIM_LODE_STONE;
				break;
			case 44:
				destTile = HomeTeleport.DRAYNOR_VILLAGE_LODE_STONE;
				break;
			case 41:
				destTile = HomeTeleport.ARDOUGNE_LODE_STONE;
				break;
			case 43:
				destTile = HomeTeleport.CATHERBAY_LODE_STONE;
				break;
			case 52:
				destTile = HomeTeleport.YANILLE_LODE_STONE;
				break;
			case 49:
				destTile = HomeTeleport.SEERS_VILLAGE_LODE_STONE;
				break;
			}
			if (destTile != null)
				player.getActionManager().setAction(new HomeTeleport(destTile));
		} else if (interfaceId == 1214)
			player.getSkills().handleSetupXPCounter(componentId);
		else if (interfaceId == 1292) {
			if (componentId == 12)
				Crucible.enterArena(player);
			else if (componentId == 13)
				player.closeInterfaces();
		}
	}

	public static void sendRemove(Player player, int slotId) {
		if (slotId >= 15)
			return;
		player.stopAll(false, false);
		Item item = player.getEquipment().getItem(slotId);
		if (item == null
				|| !player.getInventory().addItem(item.getId(),
						item.getAmount()))
			return;
		// Snow Ball Handling
		if (item.getId() == 10501) {
            player.getPackets().sendPlayerOption("null", 1, true);
        }
		player.getEquipment().getItems().set(slotId, null);
		player.getEquipment().refresh(slotId);
		player.getAppearence().generateAppearenceData();
		if (Runecrafting.isTiara(item.getId()))
			player.getPackets().sendConfig(491, 0);
		if (slotId == 3)
			player.getCombatDefinitions().desecreaseSpecialAttack(0);
	}

	public static boolean sendWear(Player player, int slotId, int itemId) {
		if (player.hasFinished() || player.isDead())
			return false;
		player.stopAll(false, false);
		Item item = player.getInventory().getItem(slotId);
		
		if (item == null || item.getId() != itemId)
			return false;

		// Snow Ball Handling
		if (item.getId() == 10501) {
            player.getPackets().sendPlayerOption("Pelt", 1, true);
        } else {
        	player.getPackets().sendPlayerOption("null", 1, true);
        }
		
		if (item.getDefinitions().isNoted() || !item.getDefinitions().isWearItem(player.getAppearence().isMale())) {
			player.getPackets().sendGameMessage(""+item.getDefinitions().getName()+" is wearable by females only.");
			return true;
		}
		
		int targetSlot = Equipment.getItemSlot(itemId);
		if (targetSlot == -1) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return true;
		}
		
		if (!ItemConstants.canWear(item, player))
			return true;
		
		boolean isTwoHandedWeapon = targetSlot == 3 && Equipment.isTwoHandedWeapon(item);
		
		if (isTwoHandedWeapon && !player.getInventory().hasFreeSlots() && player.getEquipment().hasShield()) {
			player.getPackets().sendGameMessage("Not enough free space in your inventory.");
			return true;
		}
		
		HashMap<Integer, Integer> requiriments = item.getDefinitions().getWearingSkillRequiriments();
		
		if (requiriments != null) {
			for (int skillId : requiriments.keySet()) {
				if (skillId > 24 || skillId < 0)
					continue;
				int level = requiriments.get(skillId);
				
				if (level < 0 || level > 120)
					continue;

				if (player.getSkills().getLevelForXp(skillId) < level) {
					String name = Skills.SKILLS[skillId].toLowerCase();
					player.getPackets().sendGameMessage("You need to have a" + (name.startsWith("a") ? "n" : "") + " " + name + " level of " + level + ".");
					return true;
				}
			}
		}
		
		if (!player.getControlerManager().canEquip(targetSlot, itemId))
			return false;
		player.stopAll(false, false);
		player.getInventory().deleteItem(slotId, item);
		if (targetSlot == 3) {
			if (isTwoHandedWeapon && player.getEquipment().getItem(5) != null) {
				if (!player.getInventory().addItem(
						player.getEquipment().getItem(5).getId(),
						player.getEquipment().getItem(5).getAmount())) {
					player.getInventory().getItems().set(slotId, item);
					player.getInventory().refresh(slotId);
					return true;
				}
				player.getEquipment().getItems().set(5, null);
			}
		} else if (targetSlot == 5) {
			if (player.getEquipment().getItem(3) != null
					&& Equipment.isTwoHandedWeapon(player.getEquipment()
							.getItem(3))) {
				if (!player.getInventory().addItem(
						player.getEquipment().getItem(3).getId(),
						player.getEquipment().getItem(3).getAmount())) {
					player.getInventory().getItems().set(slotId, item);
					player.getInventory().refresh(slotId);
					return true;
				}
				player.getEquipment().getItems().set(3, null);
			}

		}
		if (player.getEquipment().getItem(targetSlot) != null
				&& (itemId != player.getEquipment().getItem(targetSlot).getId() || !item
						.getDefinitions().isStackable())) {
			if (player.getInventory().getItems().get(slotId) == null) {
				player.getInventory()
						.getItems()
						.set(slotId,
								new Item(player.getEquipment()
										.getItem(targetSlot).getId(), player
										.getEquipment().getItem(targetSlot)
										.getAmount()));
				player.getInventory().refresh(slotId);
			} else
				player.getInventory().addItem(
						new Item(player.getEquipment().getItem(targetSlot)
								.getId(), player.getEquipment()
								.getItem(targetSlot).getAmount()));
			player.getEquipment().getItems().set(targetSlot, null);
		}
		if (targetSlot == Equipment.SLOT_AURA)
			player.getAuraManager().removeAura();
		int oldAmt = 0;
		if (player.getEquipment().getItem(targetSlot) != null) {
			oldAmt = player.getEquipment().getItem(targetSlot).getAmount();
		}
		Item item2 = new Item(itemId, oldAmt + item.getAmount());
		player.getEquipment().getItems().set(targetSlot, item2);
		player.getEquipment().refresh(targetSlot,
				targetSlot == 3 ? 5 : targetSlot == 3 ? 0 : 3);
		player.getAppearence().generateAppearenceData();
		player.getPackets().sendSound(2240, 0, 1);
		if (targetSlot == 3)
			player.getCombatDefinitions().desecreaseSpecialAttack(0);
		player.getCharges().wear(targetSlot);
		return true;
	}

	public static boolean sendWear2(Player player, int slotId, int itemId) {
		if (player.hasFinished() || player.isDead())
			return false;
		player.stopAll(false, false);
		Item item = player.getInventory().getItem(slotId);
		
		if (item == null || item.getId() != itemId)
			return false;

		// Snow Ball Handling
		if (item.getId() == 10501) {
            player.getPackets().sendPlayerOption("Pelt", 1, true);
        } else {
        	player.getPackets().sendPlayerOption("null", 1, true);
        }
		
		if (item.getDefinitions().isNoted() || !item.getDefinitions().isWearItem(player.getAppearence().isMale()) && itemId != 4084) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return false;
		}

		int targetSlot = Equipment.getItemSlot(itemId);
		
		if (itemId == 4084) {
			targetSlot = 3;
		}
		if (targetSlot == -1) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return false;
		}
		
		if (!ItemConstants.canWear(item, player))
			return false;
		
		boolean isTwoHandedWeapon = targetSlot == 3 && Equipment.isTwoHandedWeapon(item);
		if (isTwoHandedWeapon && !player.getInventory().hasFreeSlots() && player.getEquipment().hasShield()) {
			player.getPackets().sendGameMessage("Not enough free space in your inventory.");
			return false;
		}
		HashMap<Integer, Integer> requiriments = item.getDefinitions().getWearingSkillRequiriments();
		boolean hasRequiriments = true;
		if (requiriments != null) {
			for (int skillId : requiriments.keySet()) {
				if (skillId > 24 || skillId < 0)
					continue;
				int level = requiriments.get(skillId);
				if (level < 0 || level > 120)
					continue;
				if (player.getSkills().getLevelForXp(skillId) < level) {
					if (hasRequiriments)
						player.getPackets().sendGameMessage("You are not high enough level to use this item.");
					hasRequiriments = false;
					String name = Skills.SKILLS[skillId].toLowerCase();
					player.getPackets().sendGameMessage(
							"You need to have a"
									+ (name.startsWith("a") ? "n" : "") + " "
									+ name + " level of " + level + ".");
				}

			}
		}
		if (!hasRequiriments)
			return false;
		if (!player.getControlerManager().canEquip(targetSlot, itemId))
			return false;
		player.getInventory().getItems().remove(slotId, item);
		if (targetSlot == 3) {
			if (isTwoHandedWeapon && player.getEquipment().getItem(5) != null) {
				if (!player.getInventory().getItems()
						.add(player.getEquipment().getItem(5))) {
					player.getInventory().getItems().set(slotId, item);
					return false;
				}
				player.getEquipment().getItems().set(5, null);
			}
		} else if (targetSlot == 5) {
			if (player.getEquipment().getItem(3) != null
					&& Equipment.isTwoHandedWeapon(player.getEquipment()
							.getItem(3))) {
				if (!player.getInventory().getItems()
						.add(player.getEquipment().getItem(3))) {
					player.getInventory().getItems().set(slotId, item);
					return false;
				}
				player.getEquipment().getItems().set(3, null);
			}

		}
		if (player.getEquipment().getItem(targetSlot) != null
				&& (itemId != player.getEquipment().getItem(targetSlot).getId() || !item
						.getDefinitions().isStackable())) {
			if (player.getInventory().getItems().get(slotId) == null) {
				player.getInventory()
						.getItems()
						.set(slotId,
								new Item(player.getEquipment()
										.getItem(targetSlot).getId(), player
										.getEquipment().getItem(targetSlot)
										.getAmount()));
			} else
				player.getInventory()
						.getItems()
						.add(new Item(player.getEquipment().getItem(targetSlot)
								.getId(), player.getEquipment()
								.getItem(targetSlot).getAmount()));
			player.getEquipment().getItems().set(targetSlot, null);
		}
		if (targetSlot == Equipment.SLOT_AURA)
			player.getAuraManager().removeAura();
		int oldAmt = 0;
		if (player.getEquipment().getItem(targetSlot) != null) {
			oldAmt = player.getEquipment().getItem(targetSlot).getAmount();
		}
		Item item2 = new Item(itemId, oldAmt + item.getAmount());
		player.getEquipment().getItems().set(targetSlot, item2);
		player.getEquipment().refresh(targetSlot,
				targetSlot == 3 ? 5 : targetSlot == 3 ? 0 : 3);
		if (targetSlot == 3)
			player.getCombatDefinitions().desecreaseSpecialAttack(0);
		player.getCharges().wear(targetSlot);
		return true;
	}

	public static void submitSpecialRequest(final Player player) {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
							player.getCombatDefinitions().switchUsingSpecialAttack();
						}
					}, 0);
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 200);
	}

	public static void sendWear(Player player, int[] slotIds) {
		if (player.hasFinished() || player.isDead())
			return;
		boolean worn = false;
		Item[] copy = player.getInventory().getItems().getItemsCopy();
		for (int slotId : slotIds) {
			Item item = player.getInventory().getItem(slotId);
			if (item == null)
				continue;
			if (sendWear2(player, slotId, item.getId()))
				worn = true;
		}
		player.getInventory().refreshItems(copy);
		if (worn) {
			player.getAppearence().generateAppearenceData();
			player.getPackets().sendSound(2240, 0, 1);
		}
	}

	public static void sendWear(Player player, int slotId) {
		if (player.hasFinished() || player.isDead())
			return;
		boolean worn = false;
		Item[] copy = player.getInventory().getItems().getItemsCopy();

		Item item = player.getInventory().getItem(slotId);
		
		if (item == null)
			return;
		
		if (sendWear2(player, slotId, item.getId()))
			worn = true;
		
		player.getInventory().refreshItems(copy);
		
		if (worn) {
			player.getAppearence().generateAppearenceData();
			player.getPackets().sendSound(2240, 0, 1);
		}
	}
	
	public static void openEquipmentBonuses(final Player player, boolean banking) {
		player.stopAll();
		player.getInterfaceManager().sendInventoryInterface(670);
		player.getInterfaceManager().sendInterface(667);
		player.getPackets().sendConfigByFile(4894, banking ? 1 : 0);
		player.getPackets().sendItems(93, player.getInventory().getItems());
		player.getPackets().sendInterSetItemsOptionsScript(670, 0, 93, 4, 7, "Equip", "Compare", "Stats", "Examine");
		player.getPackets().sendUnlockIComponentOptionSlots(670, 0, 0, 27, 0, 1, 2, 3);
		player.getPackets().sendIComponentSettings(667, 14, 0, 13, 1030);
		refreshEquipBonuses(player);
		if (banking) {
			player.getAttributes().put("Banking", Boolean.TRUE);
			player.setCloseInterfacesEvent(new Runnable() {
				@Override
				public void run() {
					player.getAttributes().remove("Banking");
				}

			});
		}
	}

	public static void refreshEquipBonuses(Player player) {
		player.getPackets().sendIComponentText(667, 28,
				"Stab: +" + player.getCombatDefinitions().getBonuses()[0]);
		player.getPackets().sendIComponentText(667, 29,
				"Slash: +" + player.getCombatDefinitions().getBonuses()[1]);
		player.getPackets().sendIComponentText(667, 30,
				"Crush: +" + player.getCombatDefinitions().getBonuses()[2]);
		player.getPackets().sendIComponentText(667, 31,
				"Magic: +" + player.getCombatDefinitions().getBonuses()[3]);
		player.getPackets().sendIComponentText(667, 32,
				"Range: +" + player.getCombatDefinitions().getBonuses()[4]);
		player.getPackets().sendIComponentText(667, 33,
				"Stab: +" + player.getCombatDefinitions().getBonuses()[5]);
		player.getPackets().sendIComponentText(667, 34,
				"Slash: +" + player.getCombatDefinitions().getBonuses()[6]);
		player.getPackets().sendIComponentText(667, 35,
				"Crush: +" + player.getCombatDefinitions().getBonuses()[7]);
		player.getPackets().sendIComponentText(667, 36,
				"Magic: +" + player.getCombatDefinitions().getBonuses()[8]);
		player.getPackets().sendIComponentText(667, 37,
				"Range: +" + player.getCombatDefinitions().getBonuses()[9]);
		player.getPackets()
				.sendIComponentText(
						667,
						38,
						"Summoning: +"
								+ player.getCombatDefinitions().getBonuses()[10]);
		player.getPackets()
				.sendIComponentText(
						667,
						39,
						"Absorb Melee: +"
								+ player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_MELEE_BONUS]
								+ "%");
		player.getPackets()
				.sendIComponentText(
						667,
						40,
						"Absorb Magic: +"
								+ player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_MAGE_BONUS]
								+ "%");
		player.getPackets()
				.sendIComponentText(
						667,
						41,
						"Absorb Ranged: +"
								+ player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_RANGE_BONUS]
								+ "%");
		player.getPackets().sendIComponentText(667, 42,
				"Strength: " + player.getCombatDefinitions().getBonuses()[14]);
		player.getPackets()
				.sendIComponentText(
						667,
						43,
						"Ranged Str: "
								+ player.getCombatDefinitions().getBonuses()[15]);
		player.getPackets().sendIComponentText(667, 44,
				"Prayer: +" + player.getCombatDefinitions().getBonuses()[16]);
		player.getPackets().sendIComponentText(
				667,
				45,
				"Magic Damage: +"
						+ player.getCombatDefinitions().getBonuses()[17] + "%");
	}

}
