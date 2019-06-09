package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.content.Combat;
import com.rs.utils.Utils;

/**
 * @author  Jazzy Ya Ya Ya | Nexon | Fuzen Seth
 */
public class AvatarCombat extends CombatScript {

    @Override
    public Object[] getKeys() {
        return new Object[] { 8596 };
    }

    @Override
    public int attack(final NPC npc, final Entity target) {
        final NPCCombatDefinitions defs = npc.getCombatDefinitions();
        int attackStyle = Utils.getRandom(5);
        int size = npc.getSize();

        if (attackStyle == 0) {
            int distanceX = target.getX() - npc.getX();
            int distanceY = target.getY() - npc.getY();
            if (distanceX > size || distanceX < -1 || distanceY > size
                    || distanceY < -1)
                attackStyle = Utils.getRandom(4) + 1;
            else {
                delayHit(
                        npc,
                        0,
                        target,
                        getMeleeHit(
                                npc,
                                getRandomMaxHit(npc, defs.getMaxHit(),
                                        NPCCombatDefinitions.MELEE, target)));
                npc.setNextAnimation(new Animation(defs.getAttackEmote()));
                return defs.getAttackDelay();
            }
        } else if (attackStyle == 1 || attackStyle == 2) {
            int damage = Utils.getRandom(650);
            final Player player = target instanceof Player ? (Player) target
                    : null;
            if  (player != null && (player.getPrayer()
                    .usingPrayer(0, 17) || player.getPrayer()
                    .usingPrayer(1, 7)))
                damage = 0;
            if (player != null
                    && player.getFireImmune() > Utils.currentTimeMillis()) {
                if (damage != 0)
                    damage = Utils.getRandom(164);
            } else if (damage == 0)
                damage = Utils.getRandom(164);
            else if (player != null)
                player.getPackets().sendGameMessage(
                        "You are hit by the avatar's powerful smash!", true);
            delayHit(npc, 2, target, getRegularHit(npc, damage));
            World.sendProjectile(npc, target, 3346, 34, 16, 30, 35, 16, 0);
            npc.setNextAnimation(new Animation(11197));

        } else if (attackStyle == 3) {
            int damage;
            final Player player = target instanceof Player ? (Player) target
                    : null;
            if (player != null
                    && (player.getPrayer().usingPrayer(0, 17) || player
                    .getPrayer().usingPrayer(1, 7))) {
                damage = getRandomMaxHit(npc, 164, NPCCombatDefinitions.MAGE,
                        target);
                if (player != null)
                    player.getPackets()
                            .sendGameMessage(
                                    "Your prayer absorbs most of the Avatar's breath.",
                                    true);
            } else {
                damage = Utils.getRandom(480);
                if (player != null)
                    player.getPackets().sendGameMessage(
                            "You got powerful smash hit from the Avatar of Destruction.",
                            true);
            }
            if (Utils.getRandom(2) == 0)
                target.getPoison().makePoisoned(80);
            delayHit(npc, 2, target, getRegularHit(npc, damage));
            World.sendProjectile(npc, target, 3346, 34, 16, 30, 35, 16, 0);
            npc.setNextAnimation(new Animation(11197));
        } else if (attackStyle == 4) {
            int damage;
            final Player player = target instanceof Player ? (Player) target
                    : null;
             if (player != null
                    && (player.getPrayer().usingPrayer(0, 17) || player
                    .getPrayer().usingPrayer(1, 7))) {
                damage = getRandomMaxHit(npc, 164, NPCCombatDefinitions.MAGE,
                        target);
                if (player != null)
                    player.getPackets()
                            .sendGameMessage(
                                    "You are hit by the avatar's powerful smash!",
                                    true);
            } else {
                damage = Utils.getRandom(350);
                if (player != null)
                    player.getPackets().sendGameMessage(
                            "You are hit by the avatar's powerful smash!",
                            true);
            }
            if (Utils.getRandom(2) == 0)
                target.addFreezeDelay(15000);
            delayHit(npc, 2, target, getRegularHit(npc, damage));
            World.sendProjectile(npc, target, 3346, 34, 16, 30, 35, 16, 0);
            npc.setNextAnimation(new Animation(11197));
        } else {
            int damage;
            final Player player = target instanceof Player ? (Player) target
                    : null;
            if (player != null
                    && (player.getPrayer().usingPrayer(0, 17) || player
                    .getPrayer().usingPrayer(1, 7))) {
                damage = getRandomMaxHit(npc, 164, NPCCombatDefinitions.MAGE,
                        target);
                if (player != null)
                    player.getPackets()
                            .sendGameMessage(
                                    "Your prayer absorbs most of the dragon's shocking breath!",
                                    true);
            } else {
                damage = Utils.getRandom(501);
                if (player != null)
                    player.getPackets().sendGameMessage(
                            "You are hit by the avatar's powerful smash!",
                            true);
            }
            delayHit(npc, 2, target, getRegularHit(npc, damage));
            World.sendProjectile(npc, target, 3346, 34, 16, 30, 35, 16, 0);
            npc.setNextAnimation(new Animation(11197));
        }
        return defs.getAttackDelay();
    }
}
