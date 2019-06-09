package com.rs.game.player.controlers.dungeoneering;

import java.util.Iterator;

import com.rs.game.Animation;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.controlers.Controler;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since 2013-01-02
 */
public class RuneDungGame extends Controler {

	private int damage;
	private Dungeoneering dungeoneering;
	private boolean gaveReward;

	@Override
	public void start() {
		dungeoneering = (Dungeoneering) getArguments()[0];
		dungeoneering.sendInfo();
		player.setForceMultiArea(true);
		player.unlock();
	}
	
	
	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		player.getPackets().sendGameMessage("You can't teleport out of the arena!");
		return false;
	}

	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		player.getPackets().sendGameMessage("You can't teleport out of the arena!");
		return false;
	}

	@Override
	public boolean processObjectTeleport(WorldTile toTile) {
		player.getPackets().sendGameMessage("You can't teleport out of the arena!");
		return false;
	}

	@Override
	public boolean sendDeath() {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					player.setNextAnimation(new Animation(836));
				} else if (loop == 1) {
					player.getPackets().sendGameMessage("Oh dear, you have died.");
				} else if (loop == 3) {
					player.reset();
					player.setNextWorldTile(dungeoneering.getWorldTile(7, 7));
					player.setNextAnimation(new Animation(-1));
				} else if (loop == 4) {
					player.getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		}, 0, 1);
		return false;
	}

	@Override
	public void moved() {
		dungeoneering.sendInfo();
	}

	@Override
	public boolean processObjectClick1(WorldObject object) {
		Iterator<Portals> iterator = Portals.getMap().values().iterator();
		while(iterator.hasNext()) {
			Portals portal = iterator.next();
			WorldTile tile = portal.spawnTile();
			WorldTile dynamic = dungeoneering.getWorldTile(tile.getX(), tile.getY());
			if (object.getX() == dynamic.getX() && object.getY() == dynamic.getY()) {
				player.setNextWorldTile(dungeoneering.getWorldTile(portal.getTile().getX(), portal.getTile().getY()));
			}
		}
		return false;
	}

	@Override
	public void process() {
		if (!player.getInterfaceManager().containsScreenInter() && !player.getInterfaceManager().containsInterface(256))
			dungeoneering.sendInfo();
	}

	@Override
	public boolean logout() {
		dungeoneering.removeFromGame(player, true);
		return true;
	}

	@Override
	public boolean processButtonClick(int interfaceId, int componentId,
			int slotId, int packetId) {
		if (interfaceId == 679 && packetId == 14) {
			Item item = player.getInventory().getItem(slotId);
			if (item != null && item.getId() == 18169) {
				player.applyHit(new Hit(player, 100, HitLook.HEALED_DAMAGE));
			}
		}
		return true;
	}

	@Override
	public void forceClose() {
		if (dungeoneering != null)
			dungeoneering.removeFromGame(getPlayer(), false);
		super.removeControler();
	}

	public int getDamage() {
		return damage;
	}

	public void addDamage(int damage) {
		dungeoneering.sendInfo();
		this.damage += damage;
	}

	public Dungeoneering getDungeoneering() {
		return dungeoneering;
	}


	public boolean isGaveReward() {
		return gaveReward;
	}


	public void setGaveReward(boolean gaveReward) {
		this.gaveReward = gaveReward;
	}

}
