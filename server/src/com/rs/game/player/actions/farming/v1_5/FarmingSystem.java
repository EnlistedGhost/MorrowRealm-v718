package com.rs.game.player.actions.farming.v1;

import java.util.Iterator;

import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.farming.v1.Seeds.Seed;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class FarmingSystem {
	
	/**
	 * @author Jake | Santa Hat @Rune-Server
	 */
	
	
	/**
	 * A list of all the Farming Allotments / Patches
	 */
	public static int[] farmingPatches = { 8552, 8553, 7848, 8151, 8550, 8551, 7847, 8150 };
	
	/**
	 * Handles the seeds on patch
	 */
	public static void handleSeeds(Player player, int seedId, WorldObject object) {
		if (!canPlantSeeds(player, object)) {
			player.out("You must clear the weeds before you may plant some seeds here.");
			return;
		}
		for (PatchStatus patch : player.farmingPatch) {
			if (patch.getObjectId() == object.getId()) {
				player.out("There is already something growing here.");
				return;
			}
		}
		for (Seeds.Seed seed : Seeds.Seed.values()) {
			if (seedId == seed.getItem().getId()) {
				if (player.getSkills().getLevel(Skills.FARMING) < seed.getLevel()) {
					player.out("You need at least "+seed.getLevel()+" Farming in order to plant this.");
					return;
				}
				if (!player.getInventory().containsItem(seed.getItem().getId(), seed.getItem().getAmount())) {
					player.out("You need at least "+seed.getItem().getAmount()+" "+Item.getItemName(seedId)+"'s.");
					return;
				}
				for (int i = 0; i < seed.getSuitablePatch().length; i++) {
					if (seed.getSuitablePatch()[i] == object.getId()) {
						player.out("You plant some "+Item.getItemName(seedId)+"'s.");
						player.getInventory().deleteItem(seed.getItem());
						player.setNextAnimation(new Animation(2291));
						player.farmingPatch.add(new PatchStatus(object.getId(), object.getDefinitions().configFileId, seed.getConfigValues()[0], seed.getConfigValues()[4], "Some "+Item.getItemName(seedId)+"'s have been planted here."));
						startGrowth(player, object, seed.getTime() / 2);
					}
				}
			}
		}
	}

	/**
	 * Is the Patch Raked? Can the Player plant seeds?
	 */
	private static boolean canPlantSeeds(Player player, WorldObject object) {
		for (WorldObject o : player.rakedPatch) {
			if (object.getId() == o.getId()) 
				return true;
		}
		return false;
	}
	
	
	/**
	 * Starts the growth
	 * 5 Stages of Growth
	 */
	private static void startGrowth(final Player player, final WorldObject object, int time) {
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				if (player.farmingPatch.size() == 0) { stop();
					return;	
				}
				PatchStatus status = null;
				for (PatchStatus patch : player.farmingPatch) {
					if (patch.getConfigId() == object.getDefinitions().configFileId) {
						status = patch;
					}
				}
				if (status == null) { stop(); 
					return;
				}
				if ((status.getConfigValue() + 1) == status.getMaxConfigValue()) {
					player.getPackets().sendConfigByFile(status.getConfigId(), status.getMaxConfigValue());
					player.out("<col=ff0000>[Farming] Your crops have fully grown.");
					stop();
				} else {
					player.farmingPatch.add(new PatchStatus(object.getId(), object.getDefinitions().configFileId, status.getConfigValue() + 1, status.getMaxConfigValue(), status.getInspectText()));
					player.farmingPatch.remove(status);
					player.getPackets().sendConfigByFile(status.getConfigId(), status.getConfigValue() + 1);
				}
			}
		}, 0, time);
	}
	
	/**
	 * Should the Player Rake or Harvest?
	 */
	public static void executeAction(Player player, WorldObject object) {
		if (canHarvest(player, object)) 
			harvestCrops(player, object);
		else 
			rake(player, object);
	}
	
	/**
	 * Harvest's The Crops
	 */
	private static void harvestCrops(Player player, WorldObject object) {
		if (canHarvest(player, object)) {
			player.getPackets().sendConfigByFile(object.getDefinitions().configFileId, 0);
			sendItems(player, object);
			for (Iterator<PatchStatus> patches = player.farmingPatch.iterator(); patches.hasNext();) {
				PatchStatus patch = patches.next();
				if (patch.getConfigId() == object.getDefinitions().configFileId) 
					patches.remove(); //Removes the Crops
			}
			for (Iterator<WorldObject> rakedPatches = player.rakedPatch.iterator(); rakedPatches.hasNext();) {
				WorldObject rakedPatch = rakedPatches.next();
				if (rakedPatch.getId() == object.getId()) 
					rakedPatches.remove(); //Removes the Raked Patch
			}
		}
	}
	
	/**
	 * Sends the Farming crops to the Players Inventory
	 */
	private static void sendItems(Player player, WorldObject object) {
		for (PatchStatus patch : player.farmingPatch) {
			if (patch.getObjectId() == object.getId()) {
				for (Seeds.Seed seed : Seed.values()) {
					if (seed.getConfigValues()[4] == patch.getMaxConfigValue()) {
						player.setNextAnimation(new Animation(2286));
						player.getInventory().addItem(seed.getProduce());
						player.out("You harvest the "+Item.getItemName(seed.getProduce().getId())+"'s.");
					}
				}
			}
		}
	}
	
	/**
	 * Can the Player Harvest?
	 */
	public static boolean canHarvest(Player player, WorldObject object) {
		for (PatchStatus patch : player.farmingPatch) {
			if (patch.getConfigId() == object.getDefinitions().configFileId) {
				if ((patch.getConfigValue() + 1) == patch.getMaxConfigValue()) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Sends the Configs Upon Login
	 */
	public static void sendPatchOnLogin(Player player) {
		player.rakedPatch.clear();
		for (PatchStatus patch : player.farmingPatch) {
			continueGrowth(player);
		}
	}
	/**
	 * Continues the Growth of the crops when the player logs back in.
	 */
	public static void continueGrowth(final Player player) {
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				if (player.farmingPatch.size() == 0) { stop();
					return;	
				}
				for (PatchStatus patch : player.farmingPatch) {
					if ((patch.getConfigValue() + 1) == patch.getMaxConfigValue()) {
						player.getPackets().sendConfigByFile(patch.getConfigId(), patch.getMaxConfigValue());
						player.out("[Farming] Your crops have fully grown.");
						stop();
					} else {
						player.farmingPatch.add(new PatchStatus(patch.getObjectId(), patch.getConfigId(), patch.getConfigValue() + 1, patch.getMaxConfigValue(), patch.getInspectText()));
						player.getPackets().sendConfigByFile(patch.getConfigId(), patch.getConfigValue() + 1);
						player.farmingPatch.remove(patch);
					}	
				}
			}
		}, 0, 10);
	}

	/**
	 * Rakes the patch
	 */
	private static void rake(final Player player, final WorldObject object) {
		if (!player.getInventory().containsItem(5341, 1)) {
			player.out("You'll need a rake to get rid of the weeds.");
			return;
		}
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			int configValue;
			@Override
			public void run() {
				player.lock();
				if (loop == 0 || loop == 3 || loop == 6) {	
					configValue++;
					player.setNextAnimation(new Animation(2273));
					player.getPackets().sendConfigByFile(object.getDefinitions().configFileId, configValue);
					player.getInventory().addItem(6055, 1);
					player.getSkills().addXp(Skills.FARMING, 1);
				} else if (loop == 6){
					player.rakedPatch.add(object);
					player.out("You successfully clear all the weeds.");
				} else if (loop >= 7) {
					player.unlock();
					stop();
				}
				
			loop++;
			}
		}, 0, 1);
	}
	
	/**
	 * Right Click Patch - Inspect Option - Call this in ObjectHandler - Option2
	 */
	public static void inspectPatch(Player player, WorldObject object) {
		if (player.farmingPatch.size() == 0) {
			player.getDialogueManager().startDialogue("SimpleMessage", "There is currently nothing growing here.");
			return;
		}
		for (PatchStatus patch : player.farmingPatch) {
			if (object.getId() == patch.getObjectId()) {
				player.getDialogueManager().startDialogue("SimpleMessage", ""+patch.getInspectText());
			} else {
				player.getDialogueManager().startDialogue("SimpleMessage", "There is currently nothing growing here.");
			}
		}
	}
	
}