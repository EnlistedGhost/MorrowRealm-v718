package com.rs.game.player.content;

import java.io.Serializable;

import com.rs.game.Animation;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

@SuppressWarnings("all")
public class DwarfCannon implements Serializable{
    private static final long serialVersionUID = 6100930614455400025L;

    public static boolean DISABLED = false;
    private transient int[] CANNON_IDS = { 6, 8, 10, 12 };
    private transient Item[] CANNON_PARTS = { new Item(6), new Item(8), new Item(10), new Item(12) };
    private transient int[] CANNON = { 7, 8, 9, 6 };
    private transient int[] GOLD_ITEMS = { 20494, 20495, 20496, 20497 };
    private transient int[] ROYAL_ITEMS = { 20498, 20499, 20500, 20501 };
    private transient int[] ITEMS = { 6, 8, 10, 12, 2 };
    private transient boolean first = true;
    private transient boolean isFiring = false;
    private transient int cannonDirection;
    private transient boolean loadedOnce = false;
    private transient boolean rotating;
    private transient boolean settingUp = false;
    private transient WorldObject lastObject;
    private transient Player player;
	private transient WorldObject cannonOwner;
	private transient int ballsInCannon;
    private transient boolean hasCannon;
    
    public boolean hasCannon() {
    	return hasCannon;
    }
    
    public void cannonSetup(){
    	if (DISABLED && player.getRights() != 2) {
    		return;
    	}
    	if (player.getSkills().getLevelForXp(Skills.RANGE) < 85) {
    		player.sm("You must have a Range level of 85 to setup the Dwarf Multi-Cannon.");
    		return;
    	}
    	
    	if (!player.isAtMultiArea() && player.getRights() != 2) {
    		player.sm("The Dwarf Multi-Cannon can only be set up in a multi area.");
    		return;
    	}
    	
    	if (hasCannon) {
    		player.sm("You already have a cannon setup.");
    		return;
    	}
    	
    	if (!player.getInventory().containsItems(new Item[] { new Item(6), new Item(8), new Item(10), new Item(12) })) {
    		player.sm("You do not have all the required items to set up the Dwarf Multi-Cannon.");
    		return;
    	}
    	
    	player.setNextAnimation(new Animation(827));
    	setSettingUp(true);
    	
    	player.lock();
    	
    	WorldTasksManager.schedule(new WorldTask() {
    		int count = 0;
    		
    		@Override
    		public void run() {
    			if (count > 0) {
    				World.removeObject(getLastObject(), false);
    				World.getRegion(getLastObject().getRegionId()).removeObject(getLastObject());
    			}
    			setLastObject(new WorldObject(CANNON[count], 10, 0, player, player));
    			World.spawnObject(getLastObject(), true);
    			player.sm(getMessage(count));
    			player.getInventory().deleteItem(ITEMS[count], 1);
    			if (count == 3) {
    				setSettingUp(false);
	    			hasCannon = true;
	    			player.unlock();
	    			setObject(getLastObject());
	    			stop();
    			}
    			player.setNextAnimation(new Animation(827));
    			count++;
    		}
    	}, 0, 1);
    }
    
    public String getMessage(int count) {
    	switch (count) {
    		case 0: return "You place the cannon base on the ground...";
    		case 1: return "You add the stand...";
    		case 2: return "You add the barrel...";
    		case 3: return "You add the furnace...";
    	}
    	return "";
    }

    public void preRotationSetup(WorldObject object) {
    	if (isRotating()) {
    		return;
    	}
    	if (getBallsInCannon() < 1) {
    		player.sm("Your cannon has no ammo left!");
    		setFiring(false);
	    	setRotating(false);
	    	return;
    	}
    	setFirst(isFirst() == false ? true : isFirst());
    	setRotating(true);
    	startRotation(object);
    }

    public void startRotation(final WorldObject object) {
	WorldTasksManager.schedule(new WorldTask() {
	    int count = (hasLoadedOnce() == true ? getCannonDirection() : 0);

	    @Override
	    public void run() {
		if (isRotating() == false) {
		    this.stop();
		} else if (isRotating() == true) {
			if (count == 0)
				setLoadedOnce(isFirst() == true ? true : hasLoadedOnce());
			World.sendObjectAnimation(player, object, getAnimation(isFirst(), count));
			setCannonDirection(count);
			if (count == 7) {
				setFirst(isFirst() == true ? false : isFirst());
				count = -1;
			}
		}
		count++;
		if (fireDwarfCannon(object)) {
		    if (!retainsCannonBalls()) {
		    	player.sm("Your cannon has ran out of ammo!");
		    	setFiring(false);
		    	setRotating(false);
		    	setLoadedOnce(false);
		    	setFirst(true);
		    	this.stop();
		    }
		}
	    }
	}, 0, 0);
    }
    
    public Animation getAnimation(boolean isFirst, int count) {
    	switch (count) {
    		case 0: return (isFirst == true ? new Animation(305) : new Animation(303));
    		case 1: return (isFirst == true ? new Animation(307) : new Animation(305));
    		case 2: return (isFirst == true ? new Animation(289) : new Animation(307));
    		case 3: return (isFirst == true ? new Animation(184) : new Animation(289));
    		case 4: return (isFirst == true ? new Animation(182) : new Animation(184));
    		case 5: return (isFirst == true ? new Animation(178) : new Animation(182));
    		case 6: return (isFirst == true ? new Animation(291) : new Animation(178));
    		case 7: return (isFirst == true ? new Animation(303) : new Animation(291));
    	}
    	return null;
    }
    
    public void pickUpDwarfCannon(WorldObject object) {
    	
    	if (object == null)
    		return;
    	
    	if (object.getOwner() == null) {
    		player.sm("An error has occured. This issue is known, please do not report it.");
    		return;
    	}
    	
    	if (object.getOwner().getUsername() != player.getUsername()) {
    		player.sm("You are not the owner of this Dwarf Cannon.");
    		return;
    	}
    	
    	if (!hasCannon) {
    		player.sm("You do not currently have a cannon setup.");
    		return;
    	}
    	
    	if (isSettingUp()) {
    		player.sm("Please finish setting up your current cannon before picking it up.");
    		return;
    	}
    	
    	if (player.getInventory().getFreeSlots() < 5) {
    		player.getBank().addItems(CANNON_PARTS, true);
    		player.getBank().addItem(2, getBallsInCannon(), true);
        	player.sendMessage("You disassemble your cannon...items have been place into your bank.");
    	} else {
    		player.getInventory().addItems(CANNON_IDS, 1);
        	player.getInventory().addItem(2, getBallsInCannon());
        	player.sendMessage("You disassemble your cannon...");
    	}
	    
	    setBallsInCannon(0);
	    setRotating(false);
	    setFiring(false);
	    setLoadedOnce(false);
	    setFirst(true);
	    this.hasCannon = false;
	    setCannonDirection(0);
	    setObject(null);
	    World.removeObject(object, true);
    }

    public void loadDwarfCannon(WorldObject object) {
    	int ballAmount = player.getInventory().getNumberOf(2);
    	
    	if (object.getOwner() == null) {
    		player.sm("An error has occured. This issue is known, please do not report it.");
    		return;
    	}
    	
    	if (object.getOwner().getUsername() != player.getUsername()) {
    		player.sm("You are not the owner of this Dwarf Cannon.");
    		return;
    	}
    	
    	if (ballAmount == 0) {
    		player.sendMessage("You don't have any ammo in your inventory!");
    		return;
    	}
    	
    	if (getBallsInCannon() > 200) {
    		player.sendMessage("Your cannon still has "+getBallsInCannon()+" shots left!");
    		return;
    	}
    	
    	int amount = (ballAmount > 200 ? 200 : ballAmount);
    	player.getInventory().deleteItem(2, amount);
    	player.sm("You load the cannon with "+amount+" cannonballs.");
    	setBallsInCannon(amount);
		preRotationSetup(object);
    }

    public boolean fireDwarfCannon(WorldObject object) {
    	boolean hitTarget = false;
    	
    	if (getBallsInCannon() == 0) {
    		setFiring(false);
    		return false;
    	}
    	
		for (NPC n : World.getNPCs()) {
			int damage = Utils.getRandom(100);
			int disX = n.getX() - object.getX();
			int disY = n.getY() - object.getY();
			
			WorldTile tile = new WorldTile(object.getX(), object.getY(), object.getPlane());
			
			if (n == null || n.isDead() || !n.getDefinitions().hasAttackOption())
				continue;
			
			if (!n.withinDistance(tile, 10))
				continue;
			
			hitTarget = getCannonDirection() == 0 ? (disY <= 8 && disY >= 0) && (disX >= -1 && disX <= 1) :
				  getCannonDirection() == 1 ? (disY <= 8 && disY >= 0) && (disX <= 8 && disX >= 0) :
				  getCannonDirection() == 2 ? (disY <= 1 && disY >= -1) && (disX <= 8 && disX >= 0) :
				  getCannonDirection() == 3 ? (disY >= -8 && disY <= 0) && (disX <= 8 && disX >= 0) :
				  getCannonDirection() == 4 ? (disY >= -8 && disY <= 0) && (disX <= 1 && disX >= -1) :
				  getCannonDirection() == 5 ? (disY >= -8 && disY <= 0) && (disX >= -8 && disX <= 0) :
				  getCannonDirection() == 6 ? (disY >= -1 && disY <= 1) && (disX >= -8 && disX <= 0) :
				  getCannonDirection() == 7 ?(disY <= 8 && disY >= 0) && (disX >= -8 && disX <= 0) : false;
			
			if (hitTarget) {
				setBallsInCannon(getBallsInCannon() - 1);
				n.getCombat().setTarget(player);
				World.sendProjectile(player, object, n, 53, 52, 52, 30, 0, 0, 2);
				n.applyHit(new Hit(player, damage, HitLook.CANNON_DAMAGE));
				player.getSkills().addXp(Skills.RANGE, (damage / 5));
				return true;
			}
		}
		return false;
	}
    
    public int getCannonDirection() {
    	return cannonDirection;
    }
    public void setCannonDirection(int cannonDirection) {
    	this.cannonDirection = cannonDirection;
    }
    public boolean hasLoadedOnce() {
    	return loadedOnce;
    }
    public void setLoadedOnce(boolean loadedOnce) {
    	this.loadedOnce = loadedOnce;
    }
    public boolean retainsCannonBalls() {
    	return getBallsInCannon() > 0;
    }
    public boolean isRotating() {
    	return rotating;
    }
    public void setRotating(boolean rotating) {
    	this.rotating = rotating;
    }
    public boolean isFirst() {
    	return first;
    }
    public void setFirst(boolean first) {
    	this.first = first;
    }
    public void setPlayer(Player player) {
    	this.player = player;
    }
    public boolean isFiring() {
    	return isFiring;
    }
    public void setFiring(boolean isFiring) {
    	this.isFiring = isFiring;
    }
    public WorldObject getLastObject() {
    	return lastObject;
    }
    public void setLastObject(WorldObject lastObject) {
    	this.lastObject = lastObject;
    }
    public boolean isSettingUp() {
    	return settingUp;
    }
    public void setSettingUp(boolean settingUp) {
    	this.settingUp = settingUp;
    }

	public WorldObject getObject() {
		return cannonOwner;
	}

	public void setObject(WorldObject cannonOwner) {
		this.cannonOwner = cannonOwner;
	}

	public int getBallsInCannon() {
		return ballsInCannon;
	}

	public void setBallsInCannon(int ballsInCannon) {
		this.ballsInCannon = ballsInCannon;
	}
}