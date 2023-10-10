package com.rs.game.player.actions.thieving;

import com.rs.game.Animation;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.player.Equipment;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Action;
import com.rs.utils.Utils;

public class ThievesGuildDoors extends Action {
	
	private static final Animation PICKLOCK_DOOR_ANIMATION = new Animation(536);
	
	private WorldObject object;
	
	public ThievesGuildDoors(WorldObject object) {
		this.object = object;
	}

	@Override
	public boolean start(Player player) {
		if (isInsideCell(player)) {
			handleDoor(player, object, 15000);
			return false;
		}
		if (checkAll(player)) {
			player.faceObject(object);
			setActionDelay(player, 1);
			player.lock();
			player.setNextAnimation(PICKLOCK_DOOR_ANIMATION);
			player.sm("You examine the lock on the door...");
			if (object.getId() == 52304 && player.getInventory().containsItem(1523))
				player.sm("You poke around with your lockpick...");
			return true;
		}
		return false;
	}

	@Override
	public boolean process(Player player) {
		return checkAll(player);
	}

	@Override
	public int processWithDelay(Player player) {
		if (!isSuccesfull(player)) {
			player.sm("The door stubbornly refuses to be picked.");
		} else {
			player.sm("The door swings open.");
			handleDoor(player, object, 30000); //30000 - 15 secs
			if (object.getId() == 52302) {
				player.getSkills().addXp(Skills.THIEVING, 55);
				return -1;
			}
			player.getSkills().addXp(Skills.THIEVING, 110);
		}
		return -1;
	}

	@Override
	public void stop(Player player) {
		setActionDelay(player, 1);
		player.unlock();
	}
	
	private boolean checkAll(Player player) {
		if (player.getSkills().getLevel(Skills.THIEVING) < 24) { player.getDialogueManager().startDialogue("SimpleMessage", "You need a thieving level of 24 to pick lock this door.");
			return false;
		}
		if (object.getId() == 52304 && !player.getInventory().containsItem(1523)) {
    		player.sm("You examine the lock on the door...");
    		player.sm("You'll not get this open without a lockpick of some sort.");
    		return false;
    	}
		return true;

	}
	
	private boolean isInsideCell(Player player) {
		if ((player.getX() >= 4775 && player.getX() <= 4781) && (player.getY() == 5917 || player.getY() == 5918)) {
			return true;
		}
		if ((player.getX() >= 4775 && player.getX() <= 4781) && (player.getY() == 5914 || player.getY() == 5913)) {
			return true;
		}
		return false;
	}
	
	private boolean isSuccesfull(Player player) {
		if (player.getSkills().getLevelForXp(Skills.THIEVING) >= 99)
			return true;
		int thievingLevel = player.getSkills().getLevel(Skills.THIEVING);
		int increasedChance = getIncreasedChance(player);
		int level = Utils.getRandom(thievingLevel + increasedChance);
		double ratio = level / 24;
		if (Math.round(ratio * thievingLevel) < 24 / player.getAuraManager().getThievingAccurayMultiplier())
			return false;
		return true;
	}
	
	private int getIncreasedChance(Player player) {
		int chance = 0;
		if (Equipment.getItemSlot(Equipment.SLOT_HANDS) == 10075)
			chance += 12;
		player.getEquipment();
		if (Equipment.getItemSlot(Equipment.SLOT_CAPE) == 15349)
			chance += 15;
		return chance;
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
	
}