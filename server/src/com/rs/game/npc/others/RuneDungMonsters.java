package com.rs.game.npc.others;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Hit;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.controlers.dungeoneering.Dungeoneering;
import com.rs.game.player.controlers.dungeoneering.MonsterTypes;
import com.rs.game.player.controlers.dungeoneering.RuneDungGame;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

/**
 * 
 * @author Tyluur <itstyluur@gmail.com>
 * @since 2013-01-02
 */
public class RuneDungMonsters extends NPC {

	private MonsterTypes type;

	public RuneDungMonsters(int id, WorldTile tile, MonsterTypes type) {
		super(id, tile, -1, true);
		this.type = type;
		if (getBonuses() == null) {
			setBonuses(new int[13]);
		}
		for (int i = 0; i < 5; i++) {
			getBonuses()[i] = 500;
		}
		setForceMultiArea(true);
	}

	@Override
	public void sendDeath(final Entity source) {
		final NPCCombatDefinitions defs = getCombatDefinitions();
		resetWalkSteps();
		getCombat().removeTarget();
		setNextAnimation(null);
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					setNextAnimation(new Animation(defs.getDeathEmote()));				
				} else if (loop >= defs.getDeathDelay()) {
					if (source instanceof Player && ((Player) source).getControlerManager().getControler() instanceof RuneDungGame && ((RuneDungGame) ((Player) source).getControlerManager().getControler()).getDungeoneering() != null) {
						Dungeoneering dungeoneering = ((RuneDungGame) ((Player) source).getControlerManager().getControler()).getDungeoneering();
						dungeoneering.removeMonster(getId());
					}
					reset();
					finish();
					stop();
				}
				loop++;
			}
		}, 0, 1);
	}

	@Override
	public void handleIngoingHit(final Hit hit) {
		super.handleIngoingHit(hit);
		if (hit.getSource() instanceof Player && ((Player) hit.getSource()).getControlerManager().getControler() instanceof RuneDungGame) {
			((RuneDungGame) ((Player) hit.getSource()).getControlerManager().getControler()).addDamage(hit.getDamage());
		}
	}

	private static final long serialVersionUID = -9016489388663505254L;

}
