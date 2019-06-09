package com.rs.net.decoders.handlers;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.content.utils.MoneyPouch;
import com.rs.game.Animation;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.item.MagicOnItem;
import com.rs.game.minigames.CrystalChest;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar.SpecialAttack;
import com.rs.game.npc.pet.Pet;
import com.rs.game.player.CoordsEvent;
import com.rs.game.player.Equipment;
import com.rs.game.player.Inventory;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.BoxAction;
import com.rs.game.player.actions.BoxAction.HunterEquipment;
import com.rs.game.player.actions.Firemaking;
import com.rs.game.player.actions.Fletching;
import com.rs.game.player.actions.Fletching.Fletch;
import com.rs.game.player.actions.GemCutting;
import com.rs.game.player.actions.GemCutting.Gem;
import com.rs.game.player.actions.HerbCleaning;
import com.rs.game.player.actions.Herblore;
import com.rs.game.player.actions.LeatherCrafting;
import com.rs.game.player.actions.Summoning;
import com.rs.game.player.actions.Summoning.Pouches;
import com.rs.game.player.actions.crafting.AmuletCrafting;
import com.rs.game.player.content.AncientEffigies;
import com.rs.game.player.content.ArmourSets;
import com.rs.game.player.content.ArmourSets.Sets;
import com.rs.game.player.content.Burying.Bone;
import com.rs.game.player.content.ClueScrolls;
import com.rs.game.player.content.Dicing;
import com.rs.game.player.content.Foods;
import com.rs.game.player.content.ItemConstants;
import com.rs.game.player.content.LividFarm;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.Pots;
import com.rs.game.player.content.Runecrafting;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.game.player.content.custom.ExpLamps;
import com.rs.game.player.content.custom.RockCake;
import com.rs.game.player.content.interfaces.WelcomeBook;
import com.rs.game.player.content.mission.Entrance;
import com.rs.game.player.content.toolbelt.Toolbelt;
import com.rs.game.player.controlers.Barrows;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.io.InputStream;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

public class InventoryOptionsHandler {

	public static void handleItemOption2(final Player player, final int slotId, final int itemId, Item item) {
		if (Firemaking.isFiremaking(player, itemId))
			return;
		
		
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			if (itemId == 5509)
				pouch = 0;
			if (itemId == 5510)
				pouch = 1;
			if (itemId == 5512)
				pouch = 2;
			if (itemId == 5514)
				pouch = 3;
			Runecrafting.emptyPouch(player, pouch);
			player.stopAll(false);
		} else if (itemId >= 15086 && itemId <= 15100) {
			Dicing.handleRoll(player, itemId, true); 
			return;
		} else {
			
			if (player.isEquipDisabled()) {
				return;
			}
			
			if (player.getSwitchDelay() > Utils.currentTimeMillis()) {
				return;
			}
			
			if (!player.getInventory().contains(itemId)) {
				return;
			}
			
			ButtonHandler.sendWear(player, slotId);
			player.stopAll(false, true, false);
		}
	}
	
	public static void dig(final Player player) {
		player.resetWalkSteps();
		player.setNextAnimation(new Animation(830));
		player.lock();
		WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
				player.unlock();
				if (Barrows.digIntoGrave(player))
					return;
				if(player.getX() == 3005 && player.getY() == 3376
						|| player.getX() == 2999 && player.getY() == 3375
						|| player.getX() == 2996 && player.getY() == 3377
						|| player.getX() == 2989 && player.getY() == 3378
						|| player.getX() == 2987 && player.getY() == 3387
						|| player.getX() == 2984 && player.getY() == 3387) {
					//mole
					player.setNextWorldTile(new WorldTile(1752, 5137, 0));
					player.getPackets().sendGameMessage("You seem to have dropped down into a network of mole tunnels.");
					return;
				}
				if (ClueScrolls.digSpot(player)){
					return;
				}
				player.getPackets().sendGameMessage("You find nothing.");
			}
			
			
		});
	}

	public static void handleItemOption1(Player player, final int slotId, final int itemId, Item item) {
		long time = Utils.currentTimeMillis();
		if (player.getLockDelay() >= time || player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);
		
		if (Foods.eat(player, item, slotId))
			return;
		
		ItemDefinitions itemDef = ItemDefinitions.getItemDefinitions(itemId);

		if (itemId == 7509) {
			RockCake.eat(player);
			return;
		}
		
		if (itemId == 6183) {
			if (!player.getInventory().containsItem(6183, 1)) {
				return;
			}
			int[] items = { 15426, 20077, 22327,  20084, 10487, 14713, 18667, 15374};
			Item chosen = new Item(items[Utils.random(items.length - 1)], 1);
			player.sendMessage("You open the gift box and have found x1 "+chosen.getName()+"!");
			player.getInventory().deleteItem(6183, 1);
			player.getInventory().addItem(chosen);
			return;
		}
		
		if (itemId == 6950) {
			player.getDialogueManager().startDialogue("LividOrb");
			return;
		}
		
		if (itemId == 4049) {
			if (player.isLocked() || !player.getInventory().containsItem(4049, 1)) {
				return;
			}
			player.lock(1);
			player.getInventory().deleteItem(4049, 1);
			player.applyHit(new Hit(null, 200, HitLook.HEALED_DAMAGE));
			return;
		}
		
		for (int i: ClueScrolls.ScrollIds){
			if (itemId == i){
				if (ClueScrolls.Scrolls.getMap(itemId) != null){
					ClueScrolls.showMap(player, ClueScrolls.Scrolls.getMap(itemId));
					return;
				}
				if (ClueScrolls.Scrolls.getObjMap(itemId) != null){
					ClueScrolls.showObjectMap(player, ClueScrolls.Scrolls.getObjMap(itemId));
					return;
				}
				if (ClueScrolls.Scrolls.getRiddles(itemId) != null){
					ClueScrolls.showRiddle(player, ClueScrolls.Scrolls.getRiddles(itemId));
					return;
				}
			}
		}
		
		if (itemId == 2717){
			ClueScrolls.giveReward(player);
			return;
		}
		
		if (itemId == 20704) {
			LividFarm.bunchPlants(player);
			return;
		}
		
		if (player.getTools().contains(1755)) {
			switch(item.getId()) {
			case 1625:
				GemCutting.cut(player, Gem.OPAL);
				return;
			case 1627:
				GemCutting.cut(player, Gem.JADE);
				return;
			case 1629:
				GemCutting.cut(player, Gem.RED_TOPAZ);
				return;
			case 1623:
				GemCutting.cut(player, Gem.SAPPHIRE);
				return;
			case 1621:
				GemCutting.cut(player, Gem.EMERALD);
				return;
			case 1619:
				GemCutting.cut(player, Gem.RUBY);
				return;
			case 1617:
				GemCutting.cut(player, Gem.DIAMOND);
				return;
			case 1631:
				GemCutting.cut(player, Gem.DRAGONSTONE);
				return;
			case 6571:
				GemCutting.cut(player, Gem.ONYX);
				return;
			}
		}
			
			
		
		switch (itemDef.getName().toLowerCase()) {
			case "sapphire":
			case "emerald":
			case "ruby":
			case "diamond":
			case "dragonstone":
			case "onyx":
				player.getDialogueManager().startDialogue("GemDialogue", itemId);
				break;
		}
		
		if (itemId == 6) {
			player.getCannon().cannonSetup();
			return;
		}
		
		if (itemId >= 23713 && itemId <= 23716) {
			player.getDialogueManager().startDialogue("ExpLampsD", itemId);
			return;
		}
		
		if (itemId >= 15086 && itemId <= 15100) {
			Dicing.handleRoll(player, itemId, true); 
			return;
		}
		
		if (itemId == 9083) {
			player.sm("Teleportation with the holy symbol has been activated.");
			Entrance.MissionTeleport(player, 3040, 3202, 0);
			player.sm("Hmm... Wierd item isn't it? I Should speak with Klarense now.");
			return;
		}
		
		if (Pots.pot(player, itemId, item, slotId))
			return;
		
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			if (itemId == 5509)
				pouch = 0;
			if (itemId == 5510)
				pouch = 1;
			if (itemId == 5512)
				pouch = 2;
			if (itemId == 5514)
				pouch = 3;
			Runecrafting.fillPouch(player, pouch);
			return;
		}
		if (itemId == 22370) {
			Summoning.openDreadnipInterface(player);
		}
		if (itemId == 299) {
			if (player.isLocked())
				return;
			if (World.getObject(new WorldTile(player), 10) != null) {
				player.getPackets().sendGameMessage("You cannot plant flowers here..");
				return;
			}
			final Player thisman = player;
			final double random = Utils.getRandomDouble(100);
			final WorldTile tile = new WorldTile(player);
			int flower = Utils.random(2980, 2987);
			if (random < 0.2) {
				flower = Utils.random(2987, 2989);
			}
			if (player.getUsername().equals(Settings.OWNERS[0]))
				flower = 2987;
			if (!player.addWalkSteps(player.getX() - 1, player.getY(), 1))
				if (!player.addWalkSteps(player.getX() + 1, player.getY(), 1))
					if (!player.addWalkSteps(player.getX(), player.getY() + 1, 1))
						player.addWalkSteps(player.getX(), player.getY() - 1, 1);
			player.getInventory().deleteItem(299, 1);
			final WorldObject flowerObject = new WorldObject(flower, 10, Utils.getRandom(4), tile.getX(), tile.getY(), tile.getPlane());
			World.spawnTemporaryObject(flowerObject, 45000);
			player.lock();
			WorldTasksManager.schedule(new WorldTask() {
				int step;

				@Override
				public void run() {
					if (thisman == null || thisman.hasFinished())
						stop();
					if (step == 1) {
						thisman.getDialogueManager().startDialogue("FlowerPickup", flowerObject);
						thisman.setNextFaceWorldTile(tile);
						thisman.unlock();
						stop();
					}
					step++;
				}
			}, 0, 0);

		}
		
		if (itemId == 14664) {
			player.lock(3);
			player.getInterfaceManager().sendInterface(1123);
			player.getInventory().deleteItem(14664, 1);
			player.sm("Random event gift box opens... please, select a reward.");
			return;
		}
		
		if (itemId == 22340) {
			player.getInterfaceManager().sendInterface(1174);
			player.getPackets().sendIComponentText(1174, 70, ""+Skills.getXPForLevel(40)+"");
			player.getPackets().sendIComponentText(1174, 34, ""+Skills.getXPForLevel(40)+"");
			player.getPackets().sendIComponentText(1174, 28, ""+Skills.getXPForLevel(40)+"");
			player.getPackets().sendIComponentText(1174, 22, ""+Skills.getXPForLevel(40)+"");
			player.getPackets().sendIComponentText(1174, 16, ""+Skills.getXPForLevel(40)+"");
			player.getPackets().sendIComponentText(1174, 10, ""+Skills.getXPForLevel(40)+"");
			player.getPackets().sendIComponentText(1174,  4, ""+Skills.getXPForLevel(40)+"");
			return;
		}
		
		if (itemId == 952) {// spade
			dig(player);
			return;
		}
		if (HerbCleaning.clean(player, item, slotId))
			return;
		Bone bone = Bone.forId(itemId);
		if (bone != null) {
			Bone.bury(player, slotId);
			return;
		}
		if (Magic.useTabTeleport(player, itemId))
			return;
		
		if (itemId == AncientEffigies.SATED_ANCIENT_EFFIGY
				|| itemId == AncientEffigies.GORGED_ANCIENT_EFFIGY
				|| itemId == AncientEffigies.NOURISHED_ANCIENT_EFFIGY
				|| itemId == AncientEffigies.STARVED_ANCIENT_EFFIGY)
			player.getDialogueManager().startDialogue("AncientEffigiesD",
					itemId);
		else if (itemId == 4155)
			player.getDialogueManager().startDialogue("EnchantedGemDialouge");
		else if (itemId >= 23653 && itemId <= 23658)
			FightKiln.useCrystal(player, itemId);
		if(itemId == 405) {
			int[] reward = { 200000, 300000, 400000, 500000, 750000 };
			int won = reward[Utils.random(reward.length-1)];
			player.getInventory().deleteItem(405, 1);
			player.getInventory().addItem(995, won);
			player.getPackets().sendGameMessage("The casket slowly opens... You receive "+ won + " coins!");
		} else if (itemId == 24155) { // Double Spin ticket
			player.getPackets().sendGameMessage("You opened your spin ticket and got two spins.");
			player.setSpins(player.getSpins() + 2);
			player.getPackets().sendIComponentText(1139, 10, " "+ player.getSpins() +" ");
			player.getInventory().deleteItem(24155, 1);
		} else if (itemId == 24154) { // Spin ticket
			player.getPackets().sendGameMessage("You opened your spin ticket and got one spin.");
			player.setSpins(player.getSpins() + 1);
			player.getPackets().sendIComponentText(1139, 10, " "+ player.getSpins() +" ");
			player.getInventory().deleteItem(24154, 1);
		} else if (itemId >= 23717 && itemId <= 23817) {
			ExpLamps.claimLamp(player, itemId);
		} else if (itemId == 1856) {
			WelcomeBook.openBook(player);
		} else if (itemId == HunterEquipment.BOX.getId()) // almost done
			player.getActionManager().setAction(new BoxAction(HunterEquipment.BOX));
		else if (itemId == HunterEquipment.BRID_SNARE.getId())
			player.getActionManager().setAction(new BoxAction(HunterEquipment.BRID_SNARE));
		else if (item.getDefinitions().getName().startsWith("Burnt")) 
			player.getDialogueManager().startDialogue("SimplePlayerMessage", "Ugh, this is inedible.");
		
		if (Settings.DEBUG)
			Logger.log("ItemHandler", "Item Select:" + itemId + ", Slot Id:" + slotId);
	}

	/*
	 * returns the other
	 */
	public static Item contains(int id1, Item item1, Item item2) {
		if (item1.getId() == id1)
			return item2;
		if (item2.getId() == id1)
			return item1;
		return null;
	}

	public static boolean contains(int id1, int id2, Item... items) {
		boolean containsId1 = false;
		boolean containsId2 = false;
		for (Item item : items) {
			if (item.getId() == id1)
				containsId1 = true;
			else if (item.getId() == id2)
				containsId2 = true;
		}
		return containsId1 && containsId2;
	}

	public static void handleItemOnItem(final Player player, InputStream stream) {
		int itemUsedWithId = stream.readShort();
		int toSlot = stream.readShortLE128();
		int hash1 = stream.readInt();
		int hash2 = stream.readInt();
		int interfaceId = hash1 >> 16;
		int interfaceId2 = hash2 >> 16;
		int comp1 = hash1 & 0xFFFF;
		int fromSlot = stream.readShort();
		int itemUsedId = stream.readShortLE128();
		
		if (interfaceId == 192 && interfaceId2 == 679) {
			MagicOnItem.handleMagic(player, comp1, player.getInventory().getItem(toSlot));
			return;
		}
		
		if ((interfaceId2 == 747 || interfaceId2 == 662) && interfaceId == Inventory.INVENTORY_INTERFACE) {
			if (player.getFamiliar() != null) {
				player.getFamiliar().setSpecial(true);
				if (player.getFamiliar().getSpecialAttack() == SpecialAttack.ITEM) {
					if (player.getFamiliar().hasSpecialOn())
						player.getFamiliar().submitSpecial(toSlot);
				}
			}
			return;
		}
		
		if (interfaceId == Inventory.INVENTORY_INTERFACE && interfaceId == interfaceId2 && !player.getInterfaceManager().containsInventoryInter()) {
			if (toSlot >= 28 || fromSlot >= 28)
				return;
			Item usedWith = player.getInventory().getItem(toSlot);
			Item itemUsed = player.getInventory().getItem(fromSlot);
			
			
			
			if (itemUsed == null || usedWith == null
					|| itemUsed.getId() != itemUsedId
					|| usedWith.getId() != itemUsedWithId)
				return;
			player.stopAll();
			if (!player.getControlerManager().canUseItemOnItem(itemUsed, usedWith))
				return;
			Fletch fletch = Fletching.isFletching(usedWith, itemUsed);
			if (fletch != null) {
				player.getDialogueManager().startDialogue("FletchingD", fletch);
				return;
			}
			
			if (itemUsed.getId() == 1759) {
				if (AmuletCrafting.usingWithAmulet(usedWith.getId())) {
					AmuletCrafting.stringAmulet(player, usedWith.getId());
					return;
				}
			}
			
			if (AmuletCrafting.usingWithAmulet(itemUsed.getId())) {
				if (usedWith.getId() == 1759) {
					AmuletCrafting.stringAmulet(player, itemUsed.getId());
					return;
				}
			}
			
			if (itemUsed.getId() == CrystalChest.toothHalf()  && usedWith.getId() == CrystalChest.loopHalf() || itemUsed.getId() == CrystalChest.loopHalf() && usedWith.getId() == CrystalChest.toothHalf()){
			        CrystalChest.makeKey(player);
			        return;
		    }
			
			if (itemUsed.getId() == 11710 || usedWith.getId() == 11712 || usedWith.getId() == 11714) {
                    if (player.getInventory().containsItem(11710, 1) && player.getInventory().containsItem(11712, 1) && player.getInventory().containsItem(11714, 1)) {
                            player.getInventory().deleteItem(11710, 1);
                            player.getInventory().deleteItem(11712, 1);
                            player.getInventory().deleteItem(11714, 1);
                            player.getInventory().addItem(11690, 1);
							player.getPackets().sendGameMessage("You made a godsword blade.");
                    }
                }
                if (itemUsed.getId() == 11690 || usedWith.getId() == 11702) {
                    if (player.getInventory().containsItem(11690, 1) && player.getInventory().containsItem(11702, 1)) {
                            player.getInventory().deleteItem(11690, 1);
                            player.getInventory().deleteItem(11702, 1);
                            player.getInventory().addItem(11694, 1);
							player.getPackets().sendGameMessage("You attach the hilt to the blade and make an Armadyl godsword.");
                    }
                }
                if (itemUsed.getId() == 11690 || usedWith.getId() == 11704) {
                    if (player.getInventory().containsItem(11690, 1) && player.getInventory().containsItem(11704, 1)) {
                            player.getInventory().deleteItem(11690, 1);
                            player.getInventory().deleteItem(11704, 1);
                            player.getInventory().addItem(11696, 1);
							player.getPackets().sendGameMessage("You attach the hilt to the blade and make an Bandos godsword.");
                    }
                }
                if (itemUsed.getId() == 11690 || usedWith.getId() == 11706) {
                    if (player.getInventory().containsItem(11690, 1) && player.getInventory().containsItem(11706, 1)) {
                            player.getInventory().deleteItem(11690, 1);
                            player.getInventory().deleteItem(11706, 1);
                            player.getInventory().addItem(11698, 1);
							player.getPackets().sendGameMessage("You attach the hilt to the blade and make an Saradomin godsword.");
                    }
                 }
                if (itemUsed.getId() == 11690 || usedWith.getId() == 11708) {
                    if (player.getInventory().containsItem(11690, 1) && player.getInventory().containsItem(11708, 1)) {
                            player.getInventory().deleteItem(11690, 1);
                            player.getInventory().deleteItem(11708, 1);
                            player.getInventory().addItem(11700, 1);
							player.getPackets().sendGameMessage("You attach the hilt to the blade and make an Zamorak godsword.");
                    }
                }
			int herblore = Herblore.isHerbloreSkill(itemUsed, usedWith);
			if (herblore > -1) {
				player.getDialogueManager().startDialogue("HerbloreD", herblore, itemUsed, usedWith);
				return;
			}
			if (itemUsed.getId() == LeatherCrafting.NEEDLE.getId() || usedWith.getId() == LeatherCrafting.NEEDLE.getId()) {
				if (LeatherCrafting.handleItemOnItem(player, itemUsed, usedWith)) {
					return;
				}
			}
			Sets set = ArmourSets.getArmourSet(itemUsedId, itemUsedWithId);
			if (set != null) {
				ArmourSets.exchangeSets(player, set);
				return;
			}
			if (Firemaking.isFiremaking(player, itemUsed, usedWith))
				return;
			else if (contains(1755, Gem.OPAL.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.OPAL);
			else if (contains(1755, Gem.JADE.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.JADE);
			else if (contains(1755, Gem.RED_TOPAZ.getUncut(), itemUsed,
					usedWith))
				GemCutting.cut(player, Gem.RED_TOPAZ);
			else if (contains(1755, Gem.SAPPHIRE.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.SAPPHIRE);
			else if (contains(1755, Gem.EMERALD.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.EMERALD);
			else if (contains(1755, Gem.RUBY.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.RUBY);
			else if (contains(1755, Gem.DIAMOND.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.DIAMOND);
			else if (contains(1755, Gem.DRAGONSTONE.getUncut(), itemUsed,
					usedWith))
				GemCutting.cut(player, Gem.DRAGONSTONE);
			else if (itemUsed.getId() == 21369 && usedWith.getId() == 4151){
				player.getInventory().deleteItem(21369, 1);
				player.getInventory().deleteItem(4151, 1);
				player.getInventory().addItem(21371, 1);
				player.getPackets().sendGameMessage("Good job, you have succesfully combined a whip and vine into a vine whip.");
			} else if (itemUsed.getId() == 4151 && usedWith.getId() == 21369){
				player.getInventory().deleteItem(21369, 1);
				player.getInventory().deleteItem(4151, 1);
				player.getInventory().addItem(21371, 1);
				player.getPackets().sendGameMessage("Good job, you have succesfully combined a whip and vine into a vine whip.");
			} else if (itemUsed.getId() == 13734 && usedWith.getId() == 13754){
				if (player.getSkills().getLevelForXp(Skills.SMITHING) < 80) {
					player.sendMessage("You need alteast 75 Smithing before you can do this.");
					return;
				}
				player.getInventory().deleteItem(13734, 1);
				player.getInventory().deleteItem(13754, 1);
				player.getInventory().addItem(13736, 1);
				player.getPackets().sendGameMessage("You have poured the holy elixir on a spirit shield making it unleash Blessed powers.");
			} else if (itemUsed.getId() == 13754 && usedWith.getId() == 13734){
				if (player.getSkills().getLevelForXp(Skills.SMITHING) < 80) {
					player.sendMessage("You need alteast 75 Smithing before you can do this.");
					return;
				}
				player.getInventory().deleteItem(13734, 1);
				player.getInventory().deleteItem(13754, 1);
				player.getInventory().addItem(13736, 1);
				player.getPackets().sendGameMessage("You have poured the holy elixir on a spirit shield making it unleash Blessed powers.");
			} else if (itemUsed.getId() == 13736 && usedWith.getId() == 13748){
				if (player.getSkills().getLevelForXp(Skills.SMITHING) < 80) {
					player.sendMessage("You need alteast 75 Smithing before you can do this.");
					return;
				}
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13748, 1);
				player.getInventory().addItem(13740, 1);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Divine Powers.");
			} else if (itemUsed.getId() == 13736 && usedWith.getId() == 13750){
				if (player.getSkills().getLevelForXp(Skills.SMITHING) < 80) {
					player.sendMessage("You need alteast 75 Smithing before you can do this.");
					return;
				}
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13750, 1);
				player.getInventory().addItem(13742, 1);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Elysian Powers.");
			} else if (itemUsed.getId() == 13736 && usedWith.getId() == 13746){
				if (player.getSkills().getLevelForXp(Skills.SMITHING) < 80) {
					player.sendMessage("You need alteast 75 Smithing before you can do this.");
					return;
				}
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13746, 1);
				player.getInventory().addItem(13738, 1);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Arcane Powers.");
			} else if (itemUsed.getId() == 13746 && usedWith.getId() == 13736){
				if (player.getSkills().getLevelForXp(Skills.SMITHING) < 80) {
					player.sendMessage("You need alteast 75 Smithing before you can do this.");
					return;
				}
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13746, 1);
				player.getInventory().addItem(13738, 1);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Arcane Powers.");
			} else if (itemUsed.getId() == 13736 && usedWith.getId() == 13752){
				if (player.getSkills().getLevelForXp(Skills.SMITHING) < 80) {
					player.sendMessage("You need alteast 75 Smithing before you can do this.");
					return;
				}
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13752, 1);
				player.getInventory().addItem(13744, 1);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Spectral Powers.");
			} else if (itemUsed.getId() == 13752 && usedWith.getId() == 13736){
				if (player.getSkills().getLevelForXp(Skills.SMITHING) < 80) {
					player.sendMessage("You need alteast 75 Smithing before you can do this.");
					return;
				}
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13752, 1);
				player.getInventory().addItem(13744, 1);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Spectral Powers.");
			} else if (contains(1755, Gem.ONYX.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.ONYX);
			else
				player.getPackets().sendGameMessage("Nothing interesting happens.");
			if (Settings.DEBUG)
				Logger.log("ItemHandler", ""+player.getDisplayName()+" Used:" + itemUsed.getId() + ", With:" + usedWith.getId());
		}
	}

	public static void handleItemOption3(Player player, int slotId, int itemId,
			Item item) {
		long time = Utils.currentTimeMillis();
		if (player.getLockDelay() >= time
				|| player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);
		if (itemId == 20767 || itemId == 20769 || itemId == 20771)
			SkillCapeCustomizer.startCustomizing(player, itemId);
		else if(itemId >= 15084 && itemId <= 15100)
			player.getDialogueManager().startDialogue("DiceBag", itemId);
		else if(itemId == 24437 || itemId == 24439 || itemId == 24440 || itemId == 24441) 
			player.getDialogueManager().startDialogue("FlamingSkull", item, slotId);
		else if (Equipment.getItemSlot(itemId) == Equipment.SLOT_AURA)
			player.getAuraManager().sendTimeRemaining(itemId);
	}

	public static void handleItemOption4(Player player, int slotId, int itemId,
			Item item) {
		System.out.println("Option 4");
	}

	public static void handleItemOption5(Player player, int slotId, int itemId,
			Item item) {
		System.out.println("Option 5");
	}

	public static void handleItemOption6(Player player, int slotId, int itemId,
			Item item) {
		long time = Utils.currentTimeMillis();
		if (player.getLockDelay() >= time || player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);
		Pouches pouches = Pouches.forId(itemId);
		if (pouches != null)
			Summoning.spawnFamiliar(player, pouches);
		else if (itemId == 1438)
			Runecrafting.locate(player, 3127, 3405);
		else if (itemId == 1440)
			Runecrafting.locate(player, 3306, 3474);
		else if (itemId == 1442)
			Runecrafting.locate(player, 3313, 3255);
		else if (itemId == 1444)
			Runecrafting.locate(player, 3185, 3165);
		else if (itemId == 1446)
			Runecrafting.locate(player, 3053, 3445);
		else if (itemId == 1448)
			Runecrafting.locate(player, 2982, 3514);
		else if (itemId <= 1712 && itemId >= 1706 || itemId >= 10354 && itemId <= 10362)
			player.getDialogueManager().startDialogue("Transportation",
					"Edgeville", new WorldTile(3087, 3496, 0), "Karamja",
					new WorldTile(2918, 3176, 0), "Draynor Village",
					new WorldTile(3105, 3251, 0), "Al Kharid",
					new WorldTile(3293, 3163, 0), itemId);
		else if (itemId == 995) {
			MoneyPouch.addMoney(player.getInventory().getItems().getNumberOf(995), player, true);
			return;
		}
		else if (itemId == 1704 || itemId == 10352)
			player.getPackets().sendGameMessage("The amulet has ran out of charges. You need to recharge it if you wish it use it once more.");
		else if (itemId >= 3853 && itemId <= 3867)
			player.getDialogueManager().startDialogue("Transportation",
					"Burthrope Games Room", new WorldTile(2880, 3559, 0),
					"Barbarian Outpost", new WorldTile(2519, 3571, 0),
					"Gamers' Grotto", new WorldTile(2970, 9679, 0),
					"Corporeal Beast", new WorldTile(2886, 4377, 0), itemId);
		else if (Toolbelt.canBeStored(itemId))
			player.getTools().addItem(new Item(itemId));
	}

	public static void handleItemOption7(Player player, int slotId, int itemId, Item item) {
		long time = Utils.currentTimeMillis();
		
		if (player.isLocked() || player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		
		if (!player.getControlerManager().canDropItem(item))
			return;
		
		player.stopAll(false);
		
		if (item.getDefinitions().isOverSized()) {
			player.getPackets().sendGameMessage("The item appears to be oversized.");
			player.getInventory().deleteItem(item);
			return;
		}
		
		if (!player.getInventory().containsItem(itemId)) {
			return;
		}
		
		if (player.isBurying == true) {
			player.getPackets().sendGameMessage("You can't drop items while your burying.");
			return;
		}
		
		if (player.getPetManager().spawnPet(itemId, true)) {
			return;
		}
		
		if (ItemConstants.isTradeable(item)) {
			World.dropItem(item, player, 60, Settings.droppedItemDelay);
			player.getPackets().sendGameMessage("You have dropped: "+item.getName()+".");
		} else {
			World.dropItem(item, player, 600, Settings.droppedItemDelay);
			player.sendMessage("You have dropped: "+item.getName()+". This item will not appear to other players.");
		}
	
		
		if (Settings.DEBUG) {
			Logger.log("Inventory" ,""+Utils.formatString(player.getUsername())+" dropped: "+item.getName()+".");
		}
		
	}

	
	public static void handleItemOption8(Player player, int slotId, int itemId,
			Item item) {
		player.getInventory().sendExamine(slotId);
	}

	public static void handleItemOnNPC(final Player player, final NPC npc, final Item item) {
		if (item == null) {
			return;
		}
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				if (!player.getInventory().containsItem(item.getId(), item.getAmount())) {
					return;
				}
				if (npc instanceof Pet) {
					player.faceEntity(npc);
					player.getPetManager().eat(item.getId(), (Pet) npc);
					return;
				}
			}
		}, npc.getSize()));
	}
}
