package com.rs.game.npc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.content.utils.MoneyPouch;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.minigames.zombies.WiseOldMan;
import com.rs.game.minigames.zombies.Zombies;
import com.rs.game.npc.combat.NPCCombat;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.player.Player;
import com.rs.game.player.content.ClueScrolls;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Logger;
import com.rs.utils.MapAreas;
import com.rs.utils.NPCBonuses;
import com.rs.utils.NPCCombatDefinitionsL;
import com.rs.utils.NPCDrops;
import com.rs.utils.Utils;

public class NPC extends Entity implements Serializable {

    private static final long serialVersionUID = -4794678936277614443L;
    private int id, waveId;
    private WorldTile respawnTile;
    private int mapAreaNameHash;
    private boolean canBeAttackFromOutOfArea;
    private boolean randomwalk;
    private int[] bonuses; // 0 stab, 1 slash, 2 crush,3 mage, 4 range, 5 stab
    // def, blahblah till 9
    private boolean spawned;
    private transient NPCCombat combat;
    public WorldTile forceWalk;
    private long lastAttackedByTarget;
    private boolean cantInteract;
    private int capDamage;
    private int lureDelay;
    private boolean cantFollowUnderCombat;
    private boolean forceAgressive;
    private int forceTargetDistance;
    private boolean forceFollowClose;
    private boolean forceMultiAttacked;
    private boolean noDistanceCheck;
    // npc masks
    private transient Transformation nextTransformation;
    // name changing masks
    private String name;
    private transient boolean changedName;
    private int combatLevel;
    private transient boolean changedCombatLevel;
    private transient boolean locked;

    public NPC(int id, WorldTile tile, int mapAreaNameHash, boolean canBeAttackFromOutOfArea) {
        this(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, false);
    }

    public NPC(int id, WorldTile tile, int mapAreaNameHash, boolean canBeAttackFromOutOfArea, boolean spawned) {
        super(tile);
        if (getName() != "null" || getName() != null) {
        	this.id = id;
        	this.respawnTile = new WorldTile(tile);
        	this.mapAreaNameHash = mapAreaNameHash;
        	this.canBeAttackFromOutOfArea = canBeAttackFromOutOfArea;
        	this.setSpawned(spawned);
        	combatLevel = -1;
        	setHitpoints(getMaxHitpoints());
        	setDirection(getRespawnDirection());
        	setRandomWalk((getDefinitions().walkMask & 0x2) != 0 || forceRandomWalk(id));
        	bonuses = NPCBonuses.getBonuses(id);
        	combat = new NPCCombat(this);
        	capDamage = -1;
        	lureDelay = 12000;
        	initEntity();
        	World.addNPC(this);
        	World.updateEntityRegion(this);
        	loadMapRegions();
        	checkMultiArea();
        }
    }
    
    public NPC(int id, int waveId, WorldTile tile, int mapAreaNameHash, boolean canBeAttackFromOutOfArea, boolean spawned) {
        super(tile);
        if (getName() != "null" || getName() != null) {
        	this.id = id;
        	this.waveId = waveId;
        	this.respawnTile = new WorldTile(tile);
        	this.mapAreaNameHash = mapAreaNameHash;
        	this.canBeAttackFromOutOfArea = canBeAttackFromOutOfArea;
        	this.setSpawned(spawned);
        	combatLevel = -1;
        	setHitpoints(getMaxHitpoints());
        	setDirection(getRespawnDirection());
        	setRandomWalk((getDefinitions().walkMask & 0x2) != 0 || forceRandomWalk(id));
        	bonuses = NPCBonuses.getBonuses(id);
        	combat = new NPCCombat(this);
        	capDamage = -1;
        	lureDelay = 12000;
        	initEntity();
        	World.addNPC(this);
        	World.updateEntityRegion(this);
        	loadMapRegions();
        	checkMultiArea();
        }
    }
    @Override
    public boolean needMasksUpdate() {
        return super.needMasksUpdate() || nextTransformation != null
                || changedCombatLevel || changedName;
    }

    public void transformIntoNPC(int id) {
        setNPC(id);
        nextTransformation = new Transformation(id);
    }

    public void setNPC(int id) {
        this.id = id;
        bonuses = NPCBonuses.getBonuses(id);
    }

    @Override
    public void resetMasks() {
        super.resetMasks();
        nextTransformation = null;
        changedCombatLevel = false;
        changedName = false;
    }

    public int getMapAreaNameHash() {
        return mapAreaNameHash;
    }

    public void setCanBeAttackFromOutOfArea(boolean b) {
        canBeAttackFromOutOfArea = b;
    }

    public boolean canBeAttackFromOutOfArea() {
        return canBeAttackFromOutOfArea;
    }

    public NPCDefinitions getDefinitions() {
        return NPCDefinitions.getNPCDefinitions(id);
    }

    public NPCCombatDefinitions getCombatDefinitions() {
        return NPCCombatDefinitionsL.getNPCCombatDefinitions(id);
    }

    @Override
    public int getMaxHitpoints() {
        return getCombatDefinitions().getHitpoints();
    }

    public int getId() {
        return id;
    }

    public static boolean flag = false;

    public void processNPC() {
    	if (isDead() || locked) {
            return;
        }
    	
        if (!combat.process()) {
            if (!isForceWalking()) {
                if (!cantInteract) {
                    if (!checkAgressivity()) {
                        if (getFreezeDelay() < Utils.currentTimeMillis()) {
                            if (((hasRandomWalk()) && World.getRotation( getPlane(), getX(), getY()) == 0)
                                    && Math.random() * 1000.0 < 100.0) {
                                int moveX = (int) Math.round(Math.random() * 10.0 - 5.0);
                                int moveY = (int) Math .round(Math.random() * 10.0 - 5.0);
                                resetWalkSteps();
                                if (getMapAreaNameHash() != -1) {
                                    if (!MapAreas.isAtArea(getMapAreaNameHash(), this)) {
                                        forceWalkRespawnTile();
                                        return;
                                    }
                                    addWalkSteps(getX() + moveX, getY() + moveY, 5);
                                } else {
                                    addWalkSteps(respawnTile.getX() + moveX, respawnTile.getY() + moveY, 5);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (isForceWalking()) {
            if (getFreezeDelay() < Utils.currentTimeMillis()) {
                if (getX() != forceWalk.getX() || getY() != forceWalk.getY()) {
                    if (!hasWalkSteps()) {
                        addWalkSteps(forceWalk.getX(), forceWalk.getY(),
                                getSize(), true);
                    }
                    if (!hasWalkSteps()) { // failing finding route
                        setNextWorldTile(new WorldTile(forceWalk)); // force
                        forceWalk = null; // so ofc reached forcewalk place
                    }
                } else {
                    forceWalk = null;
                }
            }
        }
        
        int[] nonWalkableNpcs = { 
        		13929, 7530, 7531, 2467, 6970, 537, 524, 1918,
        		529, 2617, 4288, 6892, 3820, 538, 587, 5112, 11674,
        		1699, 2259, 552, 11678, 6070, 554, 551, 534, 585,
        		1597, 548, 1167, 528, 457, 576, 4247, 546, 549,
        		13768, 583, 581, 550
        };
        
        for (int i : nonWalkableNpcs) {
        	if (id == i) {
        		setRandomWalk(false);
        	}
        }
        
    }
    
    

    @Override
    public void processEntity() {
        super.processEntity();
        processNPC();
    }

    public int getRespawnDirection() {
        NPCDefinitions definitions = getDefinitions();
        if (definitions.anInt853 << 32 != 0 && definitions.respawnDirection > 0 && definitions.respawnDirection <= 8) {
            return (4 + definitions.respawnDirection) << 11;
        }
        return 0;
    }

    /*
     * forces npc to random walk even if cache says no, used because of fake
     * cache information
     */
    private static boolean forceRandomWalk(int npcId) {
        switch (npcId) {
            case 11226:
                return true;
            case 3341:
            case 3342:
            case 3343:
                return true;
            default:
                return false;
            /*
             * default: return NPCDefinitions.getNPCDefinitions(npcId).name
             * .equals("Icy Bones");
             */
        }
    }

    public void sendSoulSplit(final Hit hit, final Entity user) {
        final NPC target = this;
        if (hit.getDamage() > 0) {
            World.sendProjectile(user, this, 2263, 11, 11, 20, 5, 0, 0);
        }
        user.heal(hit.getDamage() / 5);
        WorldTasksManager.schedule(new WorldTask() {
            @Override
            public void run() {
                setNextGraphics(new Graphics(2264));
                if (hit.getDamage() > 0) {
                    World.sendProjectile(target, user, 2263, 11, 11, 20, 5, 0,
                            0);
                }
            }
        }, 1);
    }

    @Override
    public void handleIngoingHit(final Hit hit) {
        if (capDamage != -1 && hit.getDamage() > capDamage) {
            hit.setDamage(capDamage);
        }
        if (hit.getLook() != HitLook.MELEE_DAMAGE
                && hit.getLook() != HitLook.RANGE_DAMAGE
                && hit.getLook() != HitLook.MAGIC_DAMAGE) {
            return;
        }
        Entity source = hit.getSource();
        if (source == null) {
            return;
        }
        if (source instanceof Player) {
            final Player p2 = (Player) source;
            if (p2.getPrayer().hasPrayersOn()) {
                if (p2.getPrayer().usingPrayer(1, 18)) {
                    sendSoulSplit(hit, p2);
                }
                if (hit.getDamage() == 0) {
                    return;
                }
                if (!p2.getPrayer().isBoostedLeech()) {
                    if (hit.getLook() == HitLook.MELEE_DAMAGE) {
                        if (p2.getPrayer().usingPrayer(1, 19)) {
                            p2.getPrayer().setBoostedLeech(true);
                            return;
                        } else if (p2.getPrayer().usingPrayer(1, 1)) { // sap
                            // att
                            if (Utils.getRandom(4) == 0) {
                                if (p2.getPrayer().reachedMax(0)) {
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your opponent has been weakened so much that your sap curse has no effect.",
                                            true);
                                } else {
                                    p2.getPrayer().increaseLeechBonus(0);
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your curse drains Attack from the enemy, boosting your Attack.",
                                            true);
                                }
                                p2.setNextAnimation(new Animation(12569));
                                p2.setNextGraphics(new Graphics(2214));
                                p2.getPrayer().setBoostedLeech(true);
                                World.sendProjectile(p2, this, 2215, 35, 35,
                                        20, 5, 0, 0);
                                WorldTasksManager.schedule(new WorldTask() {
                                    @Override
                                    public void run() {
                                        setNextGraphics(new Graphics(2216));
                                    }
                                }, 1);
                                return;
                            }
                        } else {
                            if (p2.getPrayer().usingPrayer(1, 10)) {
                                if (Utils.getRandom(7) == 0) {
                                    if (p2.getPrayer().reachedMax(3)) {
                                        p2.getPackets()
                                                .sendGameMessage(
                                                "Your opponent has been weakened so much that your leech curse has no effect.",
                                                true);
                                    } else {
                                        p2.getPrayer().increaseLeechBonus(3);
                                        p2.getPackets()
                                                .sendGameMessage(
                                                "Your curse drains Attack from the enemy, boosting your Attack.",
                                                true);
                                    }
                                    p2.setNextAnimation(new Animation(12575));
                                    p2.getPrayer().setBoostedLeech(true);
                                    World.sendProjectile(p2, this, 2231, 35,
                                            35, 20, 5, 0, 0);
                                    WorldTasksManager.schedule(new WorldTask() {
                                        @Override
                                        public void run() {
                                            setNextGraphics(new Graphics(2232));
                                        }
                                    }, 1);
                                    return;
                                }
                            }
                            if (p2.getPrayer().usingPrayer(1, 14)) {
                                if (Utils.getRandom(7) == 0) {
                                    if (p2.getPrayer().reachedMax(7)) {
                                        p2.getPackets()
                                                .sendGameMessage(
                                                "Your opponent has been weakened so much that your leech curse has no effect.",
                                                true);
                                    } else {
                                        p2.getPrayer().increaseLeechBonus(7);
                                        p2.getPackets()
                                                .sendGameMessage(
                                                "Your curse drains Strength from the enemy, boosting your Strength.",
                                                true);
                                    }
                                    p2.setNextAnimation(new Animation(12575));
                                    p2.getPrayer().setBoostedLeech(true);
                                    World.sendProjectile(p2, this, 2248, 35,
                                            35, 20, 5, 0, 0);
                                    WorldTasksManager.schedule(new WorldTask() {
                                        @Override
                                        public void run() {
                                            setNextGraphics(new Graphics(2250));
                                        }
                                    }, 1);
                                    return;
                                }
                            }

                        }
                    }
                    if (hit.getLook() == HitLook.RANGE_DAMAGE) {
                        if (p2.getPrayer().usingPrayer(1, 2)) { // sap range
                            if (Utils.getRandom(4) == 0) {
                                if (p2.getPrayer().reachedMax(1)) {
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your opponent has been weakened so much that your sap curse has no effect.",
                                            true);
                                } else {
                                    p2.getPrayer().increaseLeechBonus(1);
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your curse drains Range from the enemy, boosting your Range.",
                                            true);
                                }
                                p2.setNextAnimation(new Animation(12569));
                                p2.setNextGraphics(new Graphics(2217));
                                p2.getPrayer().setBoostedLeech(true);
                                World.sendProjectile(p2, this, 2218, 35, 35,
                                        20, 5, 0, 0);
                                WorldTasksManager.schedule(new WorldTask() {
                                    @Override
                                    public void run() {
                                        setNextGraphics(new Graphics(2219));
                                    }
                                }, 1);
                                return;
                            }
                        } else if (p2.getPrayer().usingPrayer(1, 11)) {
                            if (Utils.getRandom(7) == 0) {
                                if (p2.getPrayer().reachedMax(4)) {
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your opponent has been weakened so much that your leech curse has no effect.",
                                            true);
                                } else {
                                    p2.getPrayer().increaseLeechBonus(4);
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your curse drains Range from the enemy, boosting your Range.",
                                            true);
                                }
                                p2.setNextAnimation(new Animation(12575));
                                p2.getPrayer().setBoostedLeech(true);
                                World.sendProjectile(p2, this, 2236, 35, 35,
                                        20, 5, 0, 0);
                                WorldTasksManager.schedule(new WorldTask() {
                                    @Override
                                    public void run() {
                                        setNextGraphics(new Graphics(2238));
                                    }
                                });
                                return;
                            }
                        }
                    }
                    if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
                        if (p2.getPrayer().usingPrayer(1, 3)) { // sap mage
                            if (Utils.getRandom(4) == 0) {
                                if (p2.getPrayer().reachedMax(2)) {
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your opponent has been weakened so much that your sap curse has no effect.",
                                            true);
                                } else {
                                    p2.getPrayer().increaseLeechBonus(2);
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your curse drains Magic from the enemy, boosting your Magic.",
                                            true);
                                }
                                p2.setNextAnimation(new Animation(12569));
                                p2.setNextGraphics(new Graphics(2220));
                                p2.getPrayer().setBoostedLeech(true);
                                World.sendProjectile(p2, this, 2221, 35, 35,
                                        20, 5, 0, 0);
                                WorldTasksManager.schedule(new WorldTask() {
                                    @Override
                                    public void run() {
                                        setNextGraphics(new Graphics(2222));
                                    }
                                }, 1);
                                return;
                            }
                        } else if (p2.getPrayer().usingPrayer(1, 12)) {
                            if (Utils.getRandom(7) == 0) {
                                if (p2.getPrayer().reachedMax(5)) {
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your opponent has been weakened so much that your leech curse has no effect.",
                                            true);
                                } else {
                                    p2.getPrayer().increaseLeechBonus(5);
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your curse drains Magic from the enemy, boosting your Magic.",
                                            true);
                                }
                                p2.setNextAnimation(new Animation(12575));
                                p2.getPrayer().setBoostedLeech(true);
                                World.sendProjectile(p2, this, 2240, 35, 35,
                                        20, 5, 0, 0);
                                WorldTasksManager.schedule(new WorldTask() {
                                    @Override
                                    public void run() {
                                        setNextGraphics(new Graphics(2242));
                                    }
                                }, 1);
                                return;
                            }
                        }
                    }

                    // overall

                    if (p2.getPrayer().usingPrayer(1, 13)) { // leech defence
                        if (Utils.getRandom(10) == 0) {
                            if (p2.getPrayer().reachedMax(6)) {
                                p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your leech curse has no effect.",
                                        true);
                            } else {
                                p2.getPrayer().increaseLeechBonus(6);
                                p2.getPackets()
                                        .sendGameMessage(
                                        "Your curse drains Defence from the enemy, boosting your Defence.",
                                        true);
                            }
                            p2.setNextAnimation(new Animation(12575));
                            p2.getPrayer().setBoostedLeech(true);
                            World.sendProjectile(p2, this, 2244, 35, 35, 20, 5,
                                    0, 0);
                            WorldTasksManager.schedule(new WorldTask() {
                                @Override
                                public void run() {
                                    setNextGraphics(new Graphics(2246));
                                }
                            }, 1);
                            return;
                        }
                    }
                }
            }
        }

    }

    @Override
    public void reset() {
        super.reset();
        setDirection(getRespawnDirection());
        combat.reset();
        bonuses = NPCBonuses.getBonuses(id); // back to real bonuses
        forceWalk = null;
    }

    @Override
    public void finish() {
        if (hasFinished()) {
            return;
        }
		
        setFinished(true);
        World.updateEntityRegion(this);
        World.removeNPC(this);
		
    }

    public void setRespawnTask() {
        if (!hasFinished()) {
            reset();
            setLocation(respawnTile);
            finish();
        }
        CoresManager.slowExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    spawn();
                } catch (Throwable e) {
                    Logger.handle(e);
                }
            }
        }, getCombatDefinitions().getRespawnDelay() * 600,
                TimeUnit.MILLISECONDS);
    }

    public void deserialize() {
        if (combat == null) {
            combat = new NPCCombat(this);
        }
        spawn();
    }

    public void spawn() {
        setFinished(false);
        World.addNPC(this);
        setLastRegionId(0);
        World.updateEntityRegion(this);
        loadMapRegions();
        checkMultiArea();
    }

    public NPCCombat getCombat() {
        return combat;
    }

    @Override
    public void sendDeath(Entity source) {
        final NPCCombatDefinitions defs = getCombatDefinitions();
        resetWalkSteps();
        combat.removeTarget();
        drop();
        setNextAnimation(null);
        
        WorldTasksManager.schedule(new WorldTask() {
            int loop;

            @Override
            public void run() {
                if (loop == 0) {
                    setNextAnimation(new Animation(defs.getDeathEmote()));
                } else if (loop >= defs.getDeathDelay()) {
                    reset();
                    setLocation(respawnTile);
                    finish();
                    if (!isSpawned()) {
                        setRespawnTask();
                    }
                    stop();
                }
                loop++;
            }
        }, 0, 1);
    }
    
    public int getWaveId() {
    	return waveId;
    }
    
    public void drop() {
        try {
            Player killer = getMostDamageReceivedSourcePlayer();
            if (killer == null)
                return;
         	Drop[] drops = NPCDrops.getDrops(id);
         	
         	checkStoof(killer);
         	
            if (drops == null) 
                return;
            
            Drop[] possibleDrops = new Drop[drops.length];
            int possibleDropsCount = 0;
            	
            for (Drop drop : drops) {
            	double dropRate = drop.getRate();
            	
                if (dropRate == 100) {
                    sendDrop(killer, drop);
                } else {
                	dropRate = (dropRate * killer.getDropBonus());
                	if (Utils.random(1, 100) <= dropRate) {
                		possibleDrops[possibleDropsCount++] = drop;
                	}
                }
            }
            
            if (possibleDropsCount > 0) {
                sendDrop(killer, possibleDrops[Utils.getRandom(possibleDropsCount - 1)]);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
    }
    
    public void checkStoof(Player p) {
    	
    	if (p.getContract() != null) {
    		p.getCHandler().checkContract(getId());
    	}
    	
        CheckControler.check(p, this);
        
        checkGoals(p);
         	
        CheckControler.check(p, this);
            
        if (p.getControlerManager().getControler() instanceof WiseOldMan) {
           Zombies.applyReward(p, getWaveId());
           return;
        }
        p.setNpcKills(p.getNpcKills() + 1);
            
        if (p.getNpcKills() == 2500) {
        	p.unlockTitle(81);
        }
            
        if (p.getTask().getCurrentTask() == id) {
        	CheckControler.checkSlayerTask(p, this);
        }
    }
    
    private void checkGoals(Player killer) {
    	if (id == 6260) { // Bandos
        	killer.setBandosKills(killer.getBandosKills() + 1);
        	if (killer.getBandosKills() == 50)
        		killer.unlockTitle(83);
        	if (killer.getBandosKills() == 10) {
        		killer.sendMessage("<col=FF0000>You have completed: Kill Bandos 10 times.");
        	}
        } else if (id == 6222) { // Armadyl
        	killer.setArmadylKills(killer.getArmadylKills() + 1);
        	if (killer.getArmadylKills() == 50)
        		killer.unlockTitle(82);
        	if (killer.getArmadylKills() == 10) {
        		killer.sendMessage("<col=FF0000>You have completed: Kill Armadyl 10 times.");
        	}
        } else if (id == 6247) { // Saradomin
        	killer.setSaradominKills(killer.getSaradominKills() + 1);
        	if (killer.getSaradominKills() == 50)
        		killer.unlockTitle(84);
        	if (killer.getSaradominKills() == 10) {
        		killer.sendMessage("<col=FF0000>You have completed: Kill Saradomin 10 times.");
        	}
        } else if (id == 6203) { // Zamorak
        	killer.setZamorakKills(killer.getZamorakKills() + 1);
        	if (killer.getZamorakKills() == 50)
        		killer.unlockTitle(85);
        	if (killer.getZamorakKills() == 10) {
        		killer.sendMessage("<col=FF0000>You have completed: Kill Zamorak 10 times.");
        	}
        } else if (id == 8133) { // Corporeal Beast
        	int[] flowers = { 2462, 2464, 2466, 2468, 2470, 2472, 2474, 2476 };
        	for (Integer i : flowers) {
        		if (killer.getEquipment().getWeaponId() == i) {
                	killer.unlockTitle(79);
        		}
        	}
        }
    }
    
    int[] scrollNpcs = { 2885, 2418, 1615, 1265, 4698, 84, 1569 };
    
    public void sendDrop(Player player, Drop drop) {
        int size = getSize();
        double scrollDropRate = Utils.random(1, 100);
        
        for (Integer i : scrollNpcs) {
        	if (getId() == i) {
        		if (player.getRights() == 2 || player.getRights() == 7) {
        			player.sendMessage("Scroll Drop Rate: "+scrollDropRate+"");
        		}
        		if (scrollDropRate <= 7) {
        			int scrollId = ClueScrolls.ScrollIds[Utils.random(ClueScrolls.ScrollIds.length - 1)];
        			World.addGroundItem(new Item(scrollId, 1), new WorldTile(getCoordFaceX(size), getCoordFaceY(size), getPlane()), player, false, 180, true);
        		}
        	}
        }
        
        ItemDefinitions defs = ItemDefinitions.getItemDefinitions(drop.getItemId());
        String dropName = defs.getName().toLowerCase();
        Item item = new Item(drop.getItemId(), drop.getMinAmount() + Utils.getRandom(drop.getExtraAmount()));
        World.addGroundItem(item, new WorldTile(getCoordFaceX(size), getCoordFaceY(size), getPlane()), player, false, 180, true);
        sendRares(player, dropName);
    }
    
    public void sendRares(Player player, String dropName) {
    	 for (String itemName : Settings.RARE_DROPS) {
             if (dropName.contains(itemName.toLowerCase())) {
            	 World.sendWorldMessage("<img=7><col=FF6600>News: "+player.getDisplayName()+" has received <col=FF9900>"+Utils.formatString(dropName)+"</col> <col=FF6600>as a rare drop!", false);
            	 return;
             }
         }
    }
    
    @Override
    public int getSize() {
        return getDefinitions().size;
    }

    public int getMaxHit() {
        return getCombatDefinitions().getMaxHit();
    }

    public int[] getBonuses() {
        return bonuses;
    }

    @Override
    public double getMagePrayerMultiplier() {
        return 0;
    }

    @Override
    public double getRangePrayerMultiplier() {
        return 0;
    }

    @Override
    public double getMeleePrayerMultiplier() {
        return 0;
    }

    public WorldTile getRespawnTile() {
        return respawnTile;
    }

    public boolean isUnderCombat() {
        return combat.underCombat();
    }

    @Override
    public void setAttackedBy(Entity target) {
        super.setAttackedBy(target);
        if (target == combat.getTarget()
                && !(combat.getTarget() instanceof Familiar)) {
            lastAttackedByTarget = Utils.currentTimeMillis();
        }
    }

    public boolean canBeAttackedByAutoRelatie() {
        return Utils.currentTimeMillis() - lastAttackedByTarget > lureDelay;
    }

    public boolean isForceWalking() {
        return forceWalk != null;
    }

    public void setTarget(Entity entity) {
        if (isForceWalking()) // if force walk not gonna get target
        {
            return;
        }
        combat.setTarget(entity);
        lastAttackedByTarget = Utils.currentTimeMillis();
    }

    public void removeTarget() {
        if (combat.getTarget() == null) {
            return;
        }
        combat.removeTarget();
    }

    public void forceWalkRespawnTile() {
        setForceWalk(respawnTile);
    }

    public void setForceWalk(WorldTile tile) {
        resetWalkSteps();
        forceWalk = tile;
    }

    public boolean hasForceWalk() {
        return forceWalk != null;
    }

    public ArrayList<Entity> getPossibleTargets() {
        ArrayList<Entity> possibleTarget = new ArrayList<Entity>();
        for (int regionId : getMapRegionsIds()) {
            List<Integer> playerIndexes = World.getRegion(regionId).getPlayerIndexes();
            if (playerIndexes != null) {
                for (int playerIndex : playerIndexes) {
                    Player player = World.getPlayers().get(playerIndex);
                    if (player == null
                            || player.isDead()
                            || player.hasFinished()
                            || !player.isRunning()
                            || !player
                            .withinDistance(
                            this,
                            forceTargetDistance > 0 ? forceTargetDistance
                            : (getCombatDefinitions()
                            .getAttackStyle() == NPCCombatDefinitions.MELEE ? 4
                            : getCombatDefinitions()
                            .getAttackStyle() == NPCCombatDefinitions.SPECIAL ? 64
                            : 8))
                            || (!forceMultiAttacked
                            && (!isAtMultiArea() || !player
                            .isAtMultiArea())
                            && player.getAttackedBy() != this && (player
                            .getAttackedByDelay() > Utils
                            .currentTimeMillis() || player
                            .getFindTargetDelay() > Utils
                            .currentTimeMillis()))
                            || !clipedProjectile(player, false)
                            || (!forceAgressive && !Wilderness.isAtWild(this) && player
                            .getSkills().getCombatLevelWithSummoning() >= getCombatLevel() * 2)) {
                        continue;
                    }
                    possibleTarget.add(player);
                }
            }
        }
        return possibleTarget;
    }

    public boolean checkAgressivity() {
        if (!forceAgressive) {
            NPCCombatDefinitions defs = getCombatDefinitions();
            if (defs.getAgressivenessType() == NPCCombatDefinitions.PASSIVE) {
                return false;
            }
        }
        ArrayList<Entity> possibleTarget = getPossibleTargets();
        if (!possibleTarget.isEmpty()) {
            Entity target = possibleTarget.get(Utils.random(possibleTarget.size()));
            setTarget(target);
            target.setAttackedBy(target);
            target.setFindTargetDelay(Utils.currentTimeMillis() + 10000);
            return true;
        }
        return false;
    }

    public boolean isCantInteract() {
        return cantInteract;
    }

    public void setCantInteract(boolean cantInteract) {
        this.cantInteract = cantInteract;
        if (cantInteract) {
            combat.reset();
        }
    }

    public int getCapDamage() {
        return capDamage;
    }

    public void setCapDamage(int capDamage) {
        this.capDamage = capDamage;
    }

    public int getLureDelay() {
        return lureDelay;
    }

    public void setLureDelay(int lureDelay) {
        this.lureDelay = lureDelay;
    }

    public boolean isCantFollowUnderCombat() {
        return cantFollowUnderCombat;
    }

    public void setCantFollowUnderCombat(boolean canFollowUnderCombat) {
        this.cantFollowUnderCombat = canFollowUnderCombat;
    }

    public Transformation getNextTransformation() {
        return nextTransformation;
    }

    @Override
    public String toString() {
        return getDefinitions().name + " - " + id + " - " + getX() + " " + getY() + " " + getPlane();
    }

    public boolean isForceAgressive() {
        return forceAgressive;
    }

    public void setForceAgressive(boolean forceAgressive) {
        this.forceAgressive = forceAgressive;
    }

    public int getForceTargetDistance() {
        return forceTargetDistance;
    }

    public void setForceTargetDistance(int forceTargetDistance) {
        this.forceTargetDistance = forceTargetDistance;
    }

    public boolean isForceFollowClose() {
        return forceFollowClose;
    }

    public void setForceFollowClose(boolean forceFollowClose) {
        this.forceFollowClose = forceFollowClose;
    }

    public boolean isForceMultiAttacked() {
        return forceMultiAttacked;
    }

    public void setForceMultiAttacked(boolean forceMultiAttacked) {
        this.forceMultiAttacked = forceMultiAttacked;
    }

    public boolean hasRandomWalk() {
        return randomwalk;
    }

    public void setRandomWalk(boolean forceRandomWalk) {
        this.randomwalk = forceRandomWalk;
    }

    public String getCustomName() {
        return name;
    }

    public void setName(String string) {
        this.name = getDefinitions().name.equals(string) ? null : string;
        changedName = true;
    }

    public int getCustomCombatLevel() {
        return combatLevel;
    }

    public int getCombatLevel() {
        return combatLevel >= 0 ? combatLevel : getDefinitions().combatLevel;
    }

    public String getName() {
        return name != null ? name : getDefinitions().name;
    }

    public void setCombatLevel(int level) {
        combatLevel = getDefinitions().combatLevel == level ? -1 : level;
        changedCombatLevel = true;
    }

    public boolean hasChangedName() {
        return changedName;
    }

    public boolean hasChangedCombatLevel() {
        return changedCombatLevel;
    }

    public WorldTile getMiddleWorldTile() {
        int size = getSize();
        return new WorldTile(getCoordFaceX(size), getCoordFaceY(size),
                getPlane());
    }

    public boolean isSpawned() {
        return spawned;
    }

    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    public boolean isNoDistanceCheck() {
        return noDistanceCheck;
    }

    public void setNoDistanceCheck(boolean noDistanceCheck) {
        this.noDistanceCheck = noDistanceCheck;
    }

    public boolean withinDistance(Player tile, int distance) {
        return super.withinDistance(tile, distance);
    }

    /**
     * Gets the locked.
     *
     * @return The locked.
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets the locked.
     *
     * @param locked The locked to set.
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    
    public void setBonuses(int[] bonuses) {
		this.bonuses = bonuses;
	}
}
