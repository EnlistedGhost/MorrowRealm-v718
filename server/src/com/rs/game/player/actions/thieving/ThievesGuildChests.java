package com.rs.game.player.actions.thieving;

import com.rs.game.Animation;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.player.Equipment;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Action;
import com.rs.utils.Utils;

public class ThievesGuildChests extends Action {
	
	private static final Animation PICKLOCK_CHEST_ANIMATION = new Animation(536);
	
	private WorldObject object;
	
	public ThievesGuildChests(WorldObject object) {
		this.object = object;
	}

	@Override
	public boolean start(Player player) {
		if (checkAll(player)) {
			player.faceObject(object);
			setActionDelay(player, 1);
			player.lock();
			player.setNextAnimation(PICKLOCK_CHEST_ANIMATION);
	    	player.sm("You attempt to pick the lock.");
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
			player.sm("You fail to pick the lock.");
		} else {
			player.sm("You manage to pick the lock.");
			player.sm("You open the chest.");
			handleChest(player, object, 15000); //15000 - 15 secs
			if (object.getId() == 52296) {
				player.getSkills().addXp(Skills.THIEVING, 27.5);
				player.sm("Theres is a single blue handkerchief inside.");
				player.getDialogueManager().startDialogue("SimpleMessage", "You gain a hanky point.");
				return -1;
			}
			player.getSkills().addXp(Skills.THIEVING, 55);
			player.sm("Theres is a single red handkerchief inside.");
			player.getDialogueManager().startDialogue("SimpleMessage", "You gain 4 hanky points.");
		}
		return -1;
	}

	@Override
	public void stop(Player player) {
		setActionDelay(player, 1);
		player.unlock();
	}
	
	private boolean checkAll(Player player) {
		if (player.getSkills().getLevel(Skills.THIEVING) < 24) { player.getDialogueManager().startDialogue("SimpleMessage", "You need a thieving level of 24 to pick lock this chest.");
			return false;
		}
		if (object.getId() == 52299 && !player.getInventory().containsItem(1523)) {
    		player.sm("You attempt to pick the lock.");
    		player.sm("You need a lockpick for this.");
    		return false;
    	}
		return true;

	}
	
	public static boolean handleChest(Player player, WorldObject object, long timer) {
		WorldObject openedChest = new WorldObject(52300,  
				object.getType(), object.getRotation(), object.getX(), 
				object.getY(), object.getPlane());
		player.faceObject(openedChest);
		World.spawnTemporaryObject(openedChest, timer, true);
		return true;			
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
	
}