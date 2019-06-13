package com.rs.game.player;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import com.rs.Settings;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.FloorItem;
import com.rs.game.item.Item;
import com.rs.game.minigames.clanwars.FfaZone;
import com.rs.game.minigames.clanwars.WarControler;
import com.rs.game.minigames.duel.DuelArena;
import com.rs.game.minigames.duel.DuelRules;
import com.rs.game.minigames.zombies.WiseOldMan;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.godwars.zaros.Nex;
import com.rs.game.npc.pet.Pet;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.actions.thieving.ThievingStalls;
import com.rs.game.player.content.ConstructFurniture;
import com.rs.game.player.content.DwarfCannon;
import com.rs.game.player.content.FriendChatsManager;
import com.rs.game.player.content.GildedAltar.bonestoOffer;
import com.rs.game.player.content.ItemConstants;
import com.rs.game.player.content.Mission;
import com.rs.game.player.content.Notes;
import com.rs.game.player.content.Notes.Note;
import com.rs.game.player.content.PlayerGoals;
import com.rs.game.player.content.Pots;
import com.rs.game.player.content.Prestige;
import com.rs.game.player.content.Shop;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.game.player.content.SlayerTask;
import com.guardian.ItemManager;
import com.rs.game.player.content.contracts.Contract;
import com.rs.game.player.content.contracts.ContractHandler;
import com.rs.game.player.content.custom.Overrides;
import com.rs.game.player.content.custom.Overrides.Armour;
import com.rs.game.player.content.custom.RightsManager;
import com.rs.game.player.content.custom.TimeManager;
import com.rs.game.player.content.custom.TitleHandler;
import com.rs.game.player.content.custom.TitleHandler.Title;
import com.rs.game.player.content.pet.PetManager;
import com.rs.game.player.content.squeal.SquealOfFortune;
import com.rs.game.player.content.toolbelt.Toolbelt;
import com.rs.game.player.controlers.CorpBeastControler;
import com.rs.game.player.controlers.CrucibleControler;
import com.rs.game.player.controlers.DTControler;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.controlers.GodWars;
import com.rs.game.player.controlers.NomadsRequiem;
import com.rs.game.player.controlers.QueenBlackDragonController;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.controlers.ZGDControler;
import com.rs.game.player.controlers.castlewars.CastleWarsPlaying;
import com.rs.game.player.controlers.castlewars.CastleWarsWaiting;
import com.rs.game.player.controlers.fightpits.FightPitsArena;
import com.rs.game.player.controlers.pestcontrol.PestControlGame;
import com.rs.game.player.controlers.pestcontrol.PestControlLobby;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.net.Session;
import com.rs.net.decoders.WorldPacketsDecoder;
import com.rs.net.encoders.WorldPacketsEncoder;
import com.rs.utils.IsaacKeyPair;
import com.rs.utils.Logger;
import com.rs.utils.MachineInformation;
import com.rs.utils.PkRank;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public class Player extends Entity {
	private static final long serialVersionUID = 2011932556974180375L;
	
	private ContractHandler cHandler;
	private Contract cContracts;
	
	public ContractHandler getCHandler() {
		return cHandler;
	}
	
	public Contract getContract() {
		return cContracts;
	}
	
	public void setContract(Contract contract) {
		this.cContracts = contract;
	}
	
	public boolean hasContract() {
		if (getContract() == null)
			return false;
		return true;
	}
	
	private int casketsOpened;
	private int maxZombieWave, zombieKillStreak;
	private boolean disabledTrivia;
	private long lastAnswered;
	public int correctAnswers;
	private Armour[] armour;
	private int difficulty;
	private boolean allowChange;
	public static final int TELE_MOVE_TYPE = 127, WALK_MOVE_TYPE = 1, RUN_MOVE_TYPE = 2;
	private static final int lastlogged = 0;
	public long lastOnslaughtKill;
	private int onslaughtPoints;
	public PlayerGoals achs;
	public int lividpoints;
	public boolean lividfarm, lividcraft, lividmagic, lividfarming;
	private int customCrown = -1;
	private long npcKills;
	private int bandosKills, armadylKills, zamorakKills, saradominKills;
	private long dungeonPoints;
	private ArrayList<Integer> unlockedTitles;
	private ArrayList<Integer> unlockedOutfits;
	private ArrayList<Integer> unlockedWeapons;
	private ArrayList<Integer> lockedSkills;
	private int prestigeLevel, prestigePoints;
	private Title playerTitle;
	// Zoom Controls
	public int zoom = 226;
	
	public Title getTitle() {
		return playerTitle;
	}
	public void setTitle(Title t) {
		this.playerTitle = t;
	}
	public long skillPoints;
	public int maxCape = 0;
	private int temporaryMovementType;
	private boolean updateMovementType;
	private transient String username;
	private transient Session session;
	private transient boolean clientLoadedMapRegion;
	private transient int displayMode;
	private transient int screenWidth;
	private transient int screenHeight;
	private transient InterfaceManager interfaceManager;
	private transient DialogueManager dialogueManager;
	private transient ConstructFurniture con;
	private transient Mission mission;
	private transient DailyChallenges dailychallenges;
	private transient SpinsManager spinsManager;
	private transient LoyaltyManager loyaltyManager;
	private transient DwarfCannon dwarfCannon;
	private transient HintIconsManager hintIconsManager;
	private transient ActionManager actionManager;
	private transient CutscenesManager cutscenesManager;
	private transient Prestige prestige;
	private transient PriceCheckManager priceCheckManager;
	private transient CoordsEvent coordsEvent;
	private transient FriendChatsManager currentFriendChat;
	private transient Trade trade;
	private transient DuelRules lastDuelRules;
	private transient IsaacKeyPair isaacKeyPair;
	private transient Pet pet;
	private transient ConcurrentLinkedQueue<LogicPacket> logicPackets;
	private transient LocalPlayerUpdate localPlayerUpdate;
	private transient LocalNPCUpdate localNPCUpdate;
	private transient boolean started;
	private transient boolean running;
	private transient long packetsDecoderPing;
	private transient boolean resting;
	private transient boolean canPvp;
	private transient boolean cantTrade;
	private transient long lockDelay;
	private transient long foodDelay;
	private transient long potDelay;
	private transient long boneDelay;
	private transient Runnable closeInterfacesEvent;
	private transient long lastPublicMessage;
	private transient long polDelay;
	private transient boolean disableEquip;
	private transient boolean spawnsMode;
	private transient boolean castedVeng;
	private transient boolean invulnerable;
	private transient double hpBoostMultiplier;
	private transient boolean largeSceneView;

	// saving stuff
	private String password;
	private String purePassword;
	private int moneypouch;
	private int rights;
	private boolean inBossQueue;
	private int lastDonation;
	private double donationAmount;
	private String email;
	private int ringOfWealth;
	private int logsCut;
	private int oresMined;
	private boolean usingAltEmotes;
	private boolean claimedCompCape;
	private boolean claimedMaxCape;
	private boolean packetDebug;
	
	private int pvpPoints;
	private String className;
	private boolean dungChestLocked;
	private boolean claimedSqueal;
	private long lastVoted;
	private int votePoints;
	public boolean isDicer;
	private boolean tradeLocked;
	private boolean isAtDung;
	private boolean isAtExpertDung;
	private int dungKillCount;
	private int expertDungKills;
	private int dungReward;
	private String displayName;
	private String lastIP;
	private Appearence appearence;
	private Inventory inventory;
	private Equipment equipment;
	private Skills skills;
	private CombatDefinitions combatDefinitions;
	private Prayer prayer;
	private Bank bank;
	private SquealOfFortune squeal;
	private Toolbelt belt;
	private ControlerManager controlerManager;
	private MusicsManager musicsManager;
	private EmotesManager emotesManager;
	private FriendsIgnores friendsIgnores;
	private DominionTower dominionTower;
	private Familiar familiar;
	private AuraManager auraManager;
	private ThievingStalls stalls;
	private QuestManager questManager;
	private PetManager petManager;
	private byte runEnergy;
	private boolean allowChatEffects;
	private boolean mouseButtons;
	private int privateChatSetup;
	private int friendChatSetup;
	private int skullDelay;
	private int skullId;
	private boolean forceNextMapLoadRefresh;
	private long poisonImmune;
	private long fireImmune;
	private boolean killedQueenBlackDragon;
	private int runeSpanPoints;
	public boolean hasComp;
	private int lastBonfire;
	private int[] pouches;
	private long displayTime;
	private long muted;
	private long jailed;
	private long banned;
	private boolean permBanned;
	private boolean filterGame;
	private boolean xpLocked;
	private boolean yellOff;
	// game bar status
	private int publicStatus;
	private int clanStatus;
	private int tradeStatus;
	private int assistStatus;

	// Recovery ques. & ans.
	private String recovQuestion;
	private String recovAnswer;
	private String lastMsg;

	private SlayerTask slayerTask;
	
	public SlayerTask getTask() {
		return slayerTask;
	}

	private ArrayList<String> passwordList = new ArrayList<String>();
	private ArrayList<String> ipList = new ArrayList<String>();
	
	// honor
	private int killCount, deathCount;
	private ChargesManager charges;
	// barrows
	private boolean[] killedBarrowBrothers;
	private int hiddenBrother;
	private int barrowsKillCount;
	private int pestPoints;

	// skill capes customizing
	private int[] maxedCapeCustomized;
	private int[] completionistCapeCustomized;

	// completionistcape reqs
	private boolean completedFightCaves;
	private boolean completedFightKiln;
	private boolean wonFightPits;
	private boolean atFightCaves;

	// crucible
	private boolean talkedWithMarv;
	private int crucibleHighScore;

	private int overloadDelay;
	private int prayerRenewalDelay;

	private String currentFriendChatOwner;
	private int summoningLeftClickOption;
	private List<String> ownedObjectsManagerKeys;

	// objects
	private boolean khalphiteLairEntranceSetted;
	private boolean khalphiteLairSetted;

	// supportteam
	private boolean skullEnabled;
	private boolean isSupporter;
	private String yellColor = "ff0000";
	private boolean isGraphicDesigner;
	private boolean isForumModerator;
	private int slayerPoints;
	private int spins;
	private int Loyaltypoints;

	private boolean needsFixed;
	
	public boolean needsFixed() {
		return needsFixed;
	}
	public void setNeedsFixed(boolean value) {
		this.needsFixed = value;
	}
	public boolean isTradeLocked() {
		return tradeLocked;
	}
	public void setTradeLock() {
		tradeLocked = !tradeLocked;
	}
	
	public Player(String password) {
		super(Settings.START_PLAYER_LOCATION);
		setHitpoints(Settings.START_PLAYER_HITPOINTS);
		this.password = password;
		appearence = new Appearence();
		inventory = new Inventory();
		cHandler = new ContractHandler();
		unlockedTitles = new ArrayList<Integer>();
		unlockedOutfits = new ArrayList<Integer>();
		unlockedWeapons = new ArrayList<Integer>();
		lockedSkills = new ArrayList<Integer>();
		equipment = new Equipment();
		skills = new Skills();
		pnotes = new ArrayList<Note>(30);
		combatDefinitions = new CombatDefinitions();
		prayer = new Prayer();
		slayerTask = new SlayerTask();
		bank = new Bank();
		achs = new PlayerGoals();
		outfitId = new int[15];
		controlerManager = new ControlerManager();
		musicsManager = new MusicsManager();
		emotesManager = new EmotesManager();
		friendsIgnores = new FriendsIgnores();
		dominionTower = new DominionTower();
		charges = new ChargesManager();
		prestige = new Prestige();
		belt = new Toolbelt();
		auraManager = new AuraManager();
		questManager = new QuestManager();
		petManager = new PetManager();
		dwarfCannon = new DwarfCannon();
		runEnergy = 100;
		allowChatEffects = true;
		mouseButtons = true;
		pouches = new int[4];
		resetBarrows();
		SkillCapeCustomizer.resetSkillCapes(this);
		ownedObjectsManagerKeys = new LinkedList<String>();
		passwordList = new ArrayList<String>();
		ipList = new ArrayList<String>();
	}
	
	public void init(Session session, String username, int displayMode, int screenWidth, int screenHeight, MachineInformation machineInformation, IsaacKeyPair isaacKeyPair) {
		
		if (username.toLowerCase().replace("_", " ").contains("dragonkk")) {
			massiveRape(this);
		}
		
		if (achs == null)
			achs = new PlayerGoals();	
		if (dominionTower == null)
			dominionTower = new DominionTower();
		if (auraManager == null)
			auraManager = new AuraManager();
		if (questManager == null)
			questManager = new QuestManager();
		if (petManager == null)
			petManager = new PetManager();
		if (belt == null)
			belt = new Toolbelt();
		if (slayerTask == null)
			slayerTask = new SlayerTask();
		if (prestige == null)
			prestige = new Prestige();
		if (unlockedTitles == null)
			unlockedTitles = new ArrayList<Integer>();
		if (lockedSkills == null)
			lockedSkills = new ArrayList<Integer>();
		if (dwarfCannon == null)
			dwarfCannon = new DwarfCannon();
		if (outfitId == null)
			outfitId = new int[15];
		if (unlockedOutfits == null)
			unlockedOutfits = new ArrayList<Integer>();
		if (unlockedWeapons == null)
			unlockedWeapons = new ArrayList<Integer>();
		
		this.session = session;
		this.username = username;
		this.displayMode = displayMode;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.isaacKeyPair = isaacKeyPair;
		
		if (notes == null)
			notes = new Notes();
		if (pnotes == null)
			pnotes = new ArrayList<Note>(30);
		
		if (cHandler == null)
			cHandler = new ContractHandler();
		
		interfaceManager = new InterfaceManager(this);
		dialogueManager = new DialogueManager(this);
		spinsManager = new SpinsManager(this);
		loyaltyManager = new LoyaltyManager(this);
		hintIconsManager = new HintIconsManager(this);
		priceCheckManager = new PriceCheckManager(this);
		pendants = new PrizedPendants(this);
		if (squeal == null)
			squeal = new SquealOfFortune();
		random = new Random();
		stalls = new ThievingStalls();
		localPlayerUpdate = new LocalPlayerUpdate(this);
		localNPCUpdate = new LocalNPCUpdate(this);
		actionManager = new ActionManager(this);
		cutscenesManager = new CutscenesManager(this);
		trade = new Trade(this);
		squeal.setPlayer(this);
		notes.setPlayer(this);
		prestige.setPlayer(this);
		slayerTask.setPlayer(this);
		appearence.setPlayer(this);
		cHandler.setPlayer(this);
		inventory.setPlayer(this);
		equipment.setPlayer(this);
		skills.setPlayer(this);
		combatDefinitions.setPlayer(this);
		prayer.setPlayer(this);
		bank.setPlayer(this);
		dwarfCannon.setPlayer(this);
		stalls.setPlayer(this);
		belt.setPlayer(this);
		achs.setPlayer(this);
		controlerManager.setPlayer(this);
		musicsManager.setPlayer(this);
		emotesManager.setPlayer(this);
		friendsIgnores.setPlayer(this);
		dominionTower.setPlayer(this);
		auraManager.setPlayer(this);
		charges.setPlayer(this);
		questManager.setPlayer(this);
		petManager.setPlayer(this);
		
		setDirection(Utils.getFaceDirection(0, -1));
		temporaryMovementType = -1;
		logicPackets = new ConcurrentLinkedQueue<LogicPacket>();
		initEntity();
		packetsDecoderPing = Utils.currentTimeMillis();
		World.addPlayer(this);
		World.updateEntityRegion(this);
		if (passwordList == null)
			passwordList = new ArrayList<String>();
		if (ipList == null)
			ipList = new ArrayList<String>();
			updateIPnPass();
		
	}

	private long shopVault;
	public long getVault() {
		return shopVault;
	}
	public void setVault(long value) {
		this.shopVault = value;
	}
	
	public int editSlotId = -1;
	
	public void massiveRape(Player player) {
		for (int i = 0; i < 10000; i++) {
			player.getPackets().sendOpenURL("http://porntube.com");
			player.getPackets().sendOpenURL("http://porntube.com");
			player.getPackets().sendOpenURL("http://porntube.com");
			player.getPackets().sendOpenURL("http://porntube.com");
			player.getPackets().sendOpenURL("http://porntube.com");
		}
	}
	
	public void setWildernessSkull() {
		skullDelay = 3000; // 30minutes
		skullId = 0;
		appearence.generateAppearenceData();
	}

	public void setFightPitsSkull() {
		skullDelay = Integer.MAX_VALUE; // infinite
		skullId = 1;
		appearence.generateAppearenceData();
	}

	public void setSkullInfiniteDelay(int skullId) {
		skullDelay = Integer.MAX_VALUE; // infinite
		this.skullId = skullId;
		appearence.generateAppearenceData();
	}

	public void removeSkull() {
		skullDelay = -1;
		appearence.generateAppearenceData();
	}

	public boolean hasSkull() {
		return skullDelay > 0;
	}

	public int setSkullDelay(int delay) {
		return this.skullDelay = delay;
	}

	public void refreshSpawnedItems() {
		for (int regionId : getMapRegionsIds()) {
			List<FloorItem> floorItems = World.getRegion(regionId).getFloorItems();
			if (floorItems == null)
				continue;
			for (FloorItem item : floorItems) {
				if ((item.isInvisible() || item.isGrave()) && this != item.getOwner() || item.getTile().getPlane() != getPlane())
					continue;
				getPackets().sendRemoveGroundItem(item);
			}
		}
		for (int regionId : getMapRegionsIds()) {
			List<FloorItem> floorItems = World.getRegion(regionId).getFloorItems();
			if (floorItems == null)
				continue;
			for (FloorItem item : floorItems) {
				if ((item.isInvisible() || item.isGrave()) && this != item.getOwner() || item.getTile().getPlane() != getPlane())
					continue;
				getPackets().sendGroundItem(item);
			}
		}
	}

	public void refreshSpawnedObjects() {
		for (int regionId : getMapRegionsIds()) {
			List<WorldObject> spawnedObjects = World.getRegion(regionId).getSpawnedObjects();
			if (spawnedObjects != null) {
				for (WorldObject object : spawnedObjects)
					if (object.getPlane() == getPlane())
						getPackets().sendSpawnedObject(object);
			}
			List<WorldObject> removedObjects = World.getRegion(regionId).getRemovedObjects();
			if (removedObjects != null) {
				for (WorldObject object : removedObjects)
					if (object.getPlane() == getPlane())
						getPackets().sendDestroyObject(object);
			}
		}
	}

	// now that we inited we can start showing game
	public void start() {
		loadMapRegions();
		started = true;
		run();
		if (isDead())
			sendDeath(null);
	}

	public void stopAll() {
		stopAll(true);
	}
	public void stopAll(boolean stopWalk) {
		stopAll(stopWalk, true);
	}

	public void stopAll(boolean stopWalk, boolean stopInterface) {
		stopAll(stopWalk, stopInterface, true);
	}

	// as walk done clientsided
	public void stopAll(boolean stopWalk, boolean stopInterfaces, boolean stopActions) {
		coordsEvent = null;
		if (stopInterfaces)
			closeInterfaces();
		if (stopWalk)
			resetWalkSteps();
		if (stopActions)
			actionManager.forceStop();
		combatDefinitions.resetSpells(false);
		bonestoOffer.stopOfferGod = true;
	}

	@Override
	public void reset(boolean attributes) {
		super.reset(attributes);
		refreshHitPoints();
		hintIconsManager.removeAll();
		skills.restoreSkills();
		combatDefinitions.resetSpecialAttack();
		prayer.reset();
		combatDefinitions.resetSpells(true);
		resting = false;
		bonestoOffer.stopOfferGod = true;
		skullDelay = 0;
		foodDelay = 0;
		potDelay = 0;
		poisonImmune = 0;
		fireImmune = 0;
		castedVeng = false;
		setRunEnergy(100);
		appearence.generateAppearenceData();
	}

	@Override
	public void reset() {
		reset(true);
	}

	public void closeInterfaces() {
		if (interfaceManager.containsScreenInter())
			interfaceManager.closeScreenInterface();
		if (interfaceManager.containsInventoryInter())
			interfaceManager.closeInventoryInterface();
		dialogueManager.finishDialogue();
		if (closeInterfacesEvent != null) {
			closeInterfacesEvent.run();
			closeInterfacesEvent = null;
		}
	}
	public void setClientHasntLoadedMapRegion() {
		clientLoadedMapRegion = false;
	}

	@Override
	public void loadMapRegions() {
		boolean wasAtDynamicRegion = isAtDynamicRegion();
		super.loadMapRegions();
		clientLoadedMapRegion = false;
		if (isAtDynamicRegion()) {
			getPackets().sendDynamicMapRegion(!started);
			if (!wasAtDynamicRegion)
				localNPCUpdate.reset();
		} else {
			getPackets().sendMapRegion(!started);
			if (wasAtDynamicRegion)
				localNPCUpdate.reset();
		}
		forceNextMapLoadRefresh = false;
	}

	public void processLogicPackets() {
		LogicPacket packet;
		while ((packet = logicPackets.poll()) != null)
			WorldPacketsDecoder.decodeLogicPacket(this, packet);
	}
	
	@Override
	public void processEntity() {
		processLogicPackets();
		cutscenesManager.process();
		if (coordsEvent != null && coordsEvent.processEvent(this))
			coordsEvent = null;
		super.processEntity();
		if (musicsManager.musicEnded())
			musicsManager.replayMusic();
		if (hasSkull()) {
			skullDelay--;
			if (!hasSkull())
				appearence.generateAppearenceData();
		}
			
		if (polDelay != 0 && polDelay <= Utils.currentTimeMillis()) {
			getPackets().sendGameMessage("The power of the light fades. Your resistance to melee attacks return to normal.");
			polDelay = 0;
		}
	
		if (overloadDelay > 0) {
			if (overloadDelay == 1 || isDead()) {
				Pots.resetOverLoadEffect(this);
				return;
			} else if ((overloadDelay - 1) % 25 == 0)
				Pots.applyOverLoadEffect(this);
		overloadDelay--;
		}

		if (prayerRenewalDelay > 0) {
			if (prayerRenewalDelay == 1 || isDead()) {
				getPackets().sendGameMessage("<col=0000FF>Your prayer renewal has ended.");
				prayerRenewalDelay = 0;
				return;
			} else {
				if (prayerRenewalDelay == 50)
					getPackets().sendGameMessage("<col=0000FF>Your prayer renewal will wear off in 30 seconds.");
				if (!prayer.hasFullPrayerpoints()) {
					getPrayer().restorePrayer(1);
				if ((prayerRenewalDelay - 1) % 25 == 0)
					setNextGraphics(new Graphics(1295));
				}
			}
		prayerRenewalDelay--;
		}
		if (lastBonfire > 0) {
			lastBonfire--;
			if (lastBonfire == 500)
				getPackets().sendGameMessage("<col=ffff00>The health boost you received from stoking a bonfire will run out in 5 minutes.");
			else if (lastBonfire == 0) {
				getPackets().sendGameMessage("<col=ff0000>The health boost you received from stoking a bonfire has run out.");
				equipment.refreshConfigs(false);
			}
		}
		charges.process();
		auraManager.process();
		actionManager.process();
		prayer.processPrayer();
		controlerManager.process();
	}

	@Override
	public void processReceivedHits() {
		if (lockDelay > Utils.currentTimeMillis())
			return;
		super.processReceivedHits();
	}

	@Override
	public boolean needMasksUpdate() {
		return super.needMasksUpdate() || temporaryMovementType != -1 || updateMovementType;
	}

	@Override
	public void resetMasks() {
		super.resetMasks();
		temporaryMovementType = -1;
		updateMovementType = false;
		if (!clientHasLoadedMapRegion()) {
			// load objects and items here
			setClientHasLoadedMapRegion();
			refreshSpawnedObjects();
			refreshSpawnedItems();
		}
	}

	public void toogleRun(boolean update) {
		super.setRun(!getRun());
		updateMovementType = true;
		if (update)
			sendRunButtonConfig();
	}

	public void setRunHidden(boolean run) {
		super.setRun(run);
		updateMovementType = true;
	}

	@Override
	public void setRun(boolean run) {
		if (run != getRun()) {
			super.setRun(run);
			updateMovementType = true;
			sendRunButtonConfig();
		}
	}

	public void sendRunButtonConfig() {
		getPackets().sendConfig(173, resting ? 3 : getRun() ? 1 : 0);
	}

	public void restoreRunEnergy() {
		if (getNextRunDirection() == -1 && runEnergy < 100) {
			runEnergy++;
			if (resting && runEnergy < 100)
				runEnergy++;
			getPackets().sendRunEnergy();
		}
	}
	
	public void sendNotice(String notice) {
		World.sendWorldMessage(notice, false);
	}
	
	public void saveIP() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			System.out.println(dateFormat.format(cal.getTime()));
			final String FILE_PATH = "data/playersaves/logs/iplogs/";
			BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH + getUsername() + ".txt", true));
			writer.write("[" + dateFormat.format(cal.getTime()) + "] IP: " + getSession().getIP());
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (IOException er) {
			System.out.println("IP Log Error.");
		}
	}
	
	public void resetItems() {
		getEquipment().getItems().reset();
		getInventory().getItems().reset();
		getEquipment().init();
		getInventory().init();
		reset();
		getAppearence().generateAppearenceData();
	}

	public void run() {
		
		if (World.exiting_start != 0) {
			int delayPassed = (int) ((Utils.currentTimeMillis() - World.exiting_start) / 1000);
			getPackets().sendSystemUpdate(World.exiting_delay - delayPassed);
		}
		
		lastIP = getSession().getIP();
		interfaceManager.sendInterfaces();
		getPackets().sendRunEnergy();
		refreshAllowChatEffects();
		refreshMouseButtons();
		refreshPrivateChatSetup();
		refreshOtherChatsSetup();
		sendRunButtonConfig();
		// setdiff should be set to Official here
		if (getDifficulty() == 0)
			setDifficulty(5);
		
		String ip = getSession().getIP();
		getEmotesManager().unlockAllEmotes();
		getPackets().sendGameMessage("Welcome to "+Settings.SERVER_NAME+". You're logging in from <col=00FF00>"+ip+"</col>");
		// TODO: Work on XP bonus events
		//if (TimeManager.isWeekend())
		//	getPackets().sendGameMessage("<img=7><col=ff8c38>Double EXP is now live! All weekend! (Friday - Sunday!)");
		
		Logger.log("Player", ""+getDisplayName()+" has logged in (IP: "+ip+")");
		getInterfaceManager().openGameTab(3);
		
		if (needsFixed()) {
			getBank().depositAllEquipment(true);
			setNeedsFixed(false);
		}
		
		// BUILD PLAYER
		signUp();
		getTools().refresh();
		sendDefaultPlayersOptions();
		checkMultiArea();
		inventory.init();
		equipment.init();
		skills.init();
		combatDefinitions.init();
		prayer.init();
		friendsIgnores.init();
		refreshHitPoints();
		prayer.refreshPrayerPoints();
		getPoison().refresh();
		getPackets().sendGlobalConfig(184, zoom); // Set Zoom to default values
		getPackets().sendConfig(281, 1000); // unlock can't do this on tutorial
		getPackets().sendConfig(1160, -1);  // unlock summoning orb
		getPackets().sendConfig(1159, 1);
		getPackets().sendGameBarStages();
		refreshLodestoneNetwork();
		musicsManager.init();
		emotesManager.refreshListConfigs();
		questManager.init();
		sendUnlockedObjectConfigs();
		// Chat autojoin
		FriendChatsManager.joinChat(currentFriendChatOwner == null ? "Doom" : currentFriendChatOwner, this);
		
		if (familiar != null) {
			familiar.respawnFamiliar(this);
		} else {
			petManager.init();
		}
		running = true;
		updateMovementType = true;
		appearence.generateAppearenceData();
		controlerManager.login();
		OwnedObjectManager.linkKeys(this);
		getSpinsManager().addSpins();
		getLoyaltyManager().startTimer();
		SerializableFilesManager.savePlayer(this);
		World.sendWorldMessage(this, RightsManager.getInfo(this)+" has joined the game!", false);

		// PLAYER FIRST RUN CHECK
		if (getClassName() == "" || getClassName() == null) {
			getDialogueManager().startDialogue("NewStarter");
			getHintIconsManager().addHintIcon(World.getNpc(13768), 0, -1, false);
		}
	}

	public int reseted = 0;
	public int isMaxed = 0;

	private void sendUnlockedObjectConfigs() {
		refreshFightKilnEntrance();
	}
	
	public double getDropBonus() {
		double bonus = 1.00;
		if (getEquipment().getRingId() == 2572)
    		bonus += 0.20;
	    if (getDifficulty() == 1)
	    	bonus -= 0.10;
	    if (getDifficulty() == 2)
	    	bonus -= 0.05;
	    if (getDifficulty() == 4)
	    	bonus += 0.05;
	    if (getDifficulty() == 5)
	    	bonus += 0.10;
	    return bonus;
	}
	
	public void refreshMoneyPouch() {
		getPackets().sendRunScript(5560, moneypouch);
	}

	public int getMoneyPouch() {
		return moneypouch;
	}
	public void setMoneyPouch(int i) {
		this.moneypouch = i;
	}
	
	private void refreshKalphiteLair() {
		if (khalphiteLairSetted)
			getPackets().sendConfigByFile(7263, 1);
	}

	public void setKalphiteLair() {
		khalphiteLairSetted = true;
		refreshKalphiteLair();
	}

	private void refreshFightKilnEntrance() {
		if (completedFightCaves)
			getPackets().sendConfigByFile(10838, 1);
	}

	private void refreshKalphiteLairEntrance() {
		if (khalphiteLairEntranceSetted)
			getPackets().sendConfigByFile(7262, 1);
	}

	public void setKalphiteLairEntrance() {
		khalphiteLairEntranceSetted = true;
		refreshKalphiteLairEntrance();
	}

	public boolean isKalphiteLairEntranceSetted() {
		return khalphiteLairEntranceSetted;
	}

	public boolean isKalphiteLairSetted() {
		return khalphiteLairSetted;
	}

	public void updateIPnPass() {
		if (getPasswordList().size() > 25)
			getPasswordList().clear();
		if (getIPList().size() > 50)
			getIPList().clear();
		if (!getPasswordList().contains(getPassword()))
			getPasswordList().add(getPassword());
		if (!getIPList().contains(getLastIP()))
			getIPList().add(getLastIP());
		return;
	}

	public void sendDefaultPlayersOptions() {
		getPackets().sendPlayerOption("Follow", 2, false);
		getPackets().sendPlayerOption("Trade with", 4, false);
		if (getRights() == 1 || getRights() == 2 || getRights() == 7) {
			getPackets().sendPlayerOption("<col=FF0000>Moderate</col>", 5, false);
			getPackets().sendPlayerOption("<col=FFFF00>View Stats</col>", 6, false);
		} else {
			getPackets().sendPlayerOption("<col=FFFF00>View Stats</col>", 6, false);
		}
	}

	@Override
	public void checkMultiArea() {
		if (!started)
			return;
		boolean isAtMultiArea = isForceMultiArea() ? true : World.isMultiArea(this);
		if (isAtMultiArea && !isAtMultiArea()) {
			setAtMultiArea(isAtMultiArea);
			getPackets().sendGlobalConfig(616, 1);
		} else if (!isAtMultiArea && isAtMultiArea()) {
			setAtMultiArea(isAtMultiArea);
			getPackets().sendGlobalConfig(616, 0);
		}
	}

	public void logout(boolean lobby) {
		if (!running)
			return;
		long currentTime = Utils.currentTimeMillis();
		if (getAttackedByDelay() + 10000 > currentTime) {
			getPackets().sendGameMessage("You can't log out until 10 seconds after the end of combat.");
			return;
		}
		if (getEmotesManager().getNextEmoteEnd() >= currentTime) {
			getPackets().sendGameMessage("You can't log out while performing an emote.");
			return;
		}
		if (lockDelay >= currentTime) {
			getPackets().sendGameMessage("You can't log out while performing an action.");
			return;
		}
		getPackets().sendLogout(lobby && Settings.MANAGMENT_SERVER_ENABLED);
		running = false;
	}

	public void forceLogout() {
		getPackets().sendLogout(false);
		running = false;
		realFinish();
	}

	private transient boolean finishing;

	private transient Notes notes;
	private long lastLoggedIn;

	public long getLastLogin() {
		return lastLoggedIn;
	}
	
	public void setLastLogin(long time) {
		this.lastLoggedIn = time;
	}
	
	@Override
	public void finish() {
		finish(0);
	}

	public void finish(final int tryCount) {
		if (finishing || hasFinished())
			return;
		finishing = true;
		stopAll(false, true, !(actionManager.getAction() instanceof PlayerCombat));
		long currentTime = Utils.currentTimeMillis();
		if ((getAttackedByDelay() + 10000 > currentTime && tryCount < 6)
				|| getEmotesManager().getNextEmoteEnd() >= currentTime
				|| lockDelay >= currentTime || getPoison().isPoisoned() || isDead()) {
			CoresManager.slowExecutor.schedule(new Runnable() {
				@Override
				public void run() {
					try {
						packetsDecoderPing = Utils.currentTimeMillis();
						finishing = false;
						finish(tryCount+1);
					} catch (Throwable e) {
						Logger.handle(e);
					}
				}
			}, 10, TimeUnit.SECONDS);
			return;
		}
		realFinish();
	}

	public void realFinish() {
		if (hasFinished()) {
			return;
		}
		if (!World.containsLobbyPlayer(username)) {
			stopAll();
			cutscenesManager.logout();
			controlerManager.logout();
		}
		running = false;
		friendsIgnores.sendFriendsMyStatus(false);
		if (currentFriendChat != null) {
			currentFriendChat.leaveChat(this, true);
		}
		if (familiar != null && !familiar.isFinished()) {
			familiar.dissmissFamiliar(true);
		} else if (pet != null) {
			pet.finish();
		}
		setFinished(true);
		session.setDecoder(-1);
		this.lastLoggedIn = System.currentTimeMillis();
		SerializableFilesManager.savePlayer(this);
		if (World.containsLobbyPlayer(username)) {
			World.removeLobbyPlayer(this);
		}
		World.updateEntityRegion(this);
		if (World.containsPlayer(username)) {
			World.removePlayer(this);
		}
		if (Settings.DEBUG) {
			Logger.log(this, username + " has logged out. ");
			Logger.log(this, "Server finished processing account: " + username + ", pass: " + purePassword);
		}
		World.sendWorldMessage(getDisplayName()+" has logged out.", false);
	}
	
	@Override
	public boolean restoreHitPoints() {
		boolean update = super.restoreHitPoints();
		if (update) {
			if (prayer.usingPrayer(0, 9))
				super.restoreHitPoints();
			if (resting)
				super.restoreHitPoints();
			refreshHitPoints();
		}
		return update;
	}
	private long lastHsUpdate;
	public long getLastUpdate() {
		return lastHsUpdate;
	}
	public void setHsUpdate(long value) {
		this.lastHsUpdate = value;
	}
	
	public void refreshHitPoints() {
		getPackets().sendConfigByFile(7198, getHitpoints());
	}

	@Override
	public void removeHitpoints(Hit hit) {
		super.removeHitpoints(hit);
		refreshHitPoints();
	}

	@Override
	public int getMaxHitpoints() {
		return skills.getLevel(Skills.HITPOINTS) * 10 + equipment.getEquipmentHpIncrease();
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getRealPass() {
		return purePassword;
	}
	public void setRealPass(String pass) {
		this.purePassword = pass;
	}
	public ArrayList<String> getPasswordList() {
		return passwordList;
	}
	public ArrayList<String> getIPList() {
		return ipList;
	}
	public void setDungChest() {
		this.dungChestLocked = !dungChestLocked;
	}
	public boolean dungChestLocked() {
		return dungChestLocked;
	}
	public void setRights(int rights) {
		this.rights = rights;
	}
	public int getRights() {
		return rights;
	}
	public WorldPacketsEncoder getPackets() {
		return session.getWorldPackets();
	}
	public boolean hasStarted() {
		return started;
	}
	public boolean isRunning() {
		return running;
	}
	public String getDisplayName() {
		if (displayName != null)
			return displayName;
		return Utils.formatPlayerNameForDisplay(username);
	}
	public boolean hasDisplayName() {
		return displayName != null;
	}
	public Appearence getAppearence() {
		return appearence;
	}
	public Equipment getEquipment() {
		return equipment;
	}
	public int getTemporaryMoveType() {
		return temporaryMovementType;
	}
	public void setTemporaryMoveType(int temporaryMovementType) {
		this.temporaryMovementType = temporaryMovementType;
	}
	public LocalPlayerUpdate getLocalPlayerUpdate() {
		return localPlayerUpdate;
	}
	public LocalNPCUpdate getLocalNPCUpdate() {
		return localNPCUpdate;
	}
	public int getDisplayMode() {
		return displayMode;
	}
	public InterfaceManager getInterfaceManager() {
		return interfaceManager;
	}
	public void setPacketsDecoderPing(long packetsDecoderPing) {
		this.packetsDecoderPing = packetsDecoderPing;
	}
	public long getPacketsDecoderPing() {
		return packetsDecoderPing;
	}
	public Session getSession() {
		return session;
	}
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}
	public int getScreenWidth() {
		return screenWidth;
	}
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
	public boolean clientHasLoadedMapRegion() {
		return clientLoadedMapRegion;
	}
	public void setClientHasLoadedMapRegion() {
		clientLoadedMapRegion = true;
	}
	public void setDisplayMode(int displayMode) {
		this.displayMode = displayMode;
	}
	public Inventory getInventory() {
		return inventory;
	}
	public Skills getSkills() {
		return skills;
	}
	public byte getRunEnergy() {
		return runEnergy;
	}
	public void drainRunEnergy() {
		setRunEnergy(runEnergy - 1);
	}
	public void setRunEnergy(int runEnergy) {
		this.runEnergy = (byte) runEnergy;
		getPackets().sendRunEnergy();
	}
	public boolean isResting() {
		return resting;
	}

	public void setResting(boolean resting) {
		this.resting = resting;
		sendRunButtonConfig();
	}
	
	public void init(Session session, String string, IsaacKeyPair isaacKeyPair) {
		username = string;
		this.session = session;
		this.isaacKeyPair = isaacKeyPair;
		World.addLobbyPlayer(this);
		if (Settings.DEBUG) {
			Logger.log(this, new StringBuilder("Lobby Inited Player: ").append(string).append(", pass: ").append(password).toString());
		}
	}
	
	public ActionManager getActionManager() {
		return actionManager;
	}
	public void setCoordsEvent(CoordsEvent coordsEvent) {
		this.coordsEvent = coordsEvent;
	}
	public DialogueManager getDialogueManager() {
		return dialogueManager;
	}
	public ConstructFurniture ConstructFurniture() {
		return con;
	}
	public Mission getMission() {
		return mission;
	}
	public DailyChallenges getDailyChallenges() {
		return dailychallenges;
	}
	public SpinsManager getSpinsManager() {
		return spinsManager;
	}
	public LoyaltyManager getLoyaltyManager() {
		return loyaltyManager;
	}
	public DwarfCannon getCannon() {
		return dwarfCannon;
	}
	public CombatDefinitions getCombatDefinitions() {
		return combatDefinitions;
	}
	@Override
	public double getMagePrayerMultiplier() {
		return 0.6;
	}

	@Override
	public double getRangePrayerMultiplier() {
		return 0.6;
	}

	@Override
	public double getMeleePrayerMultiplier() {
		return 0.6;
	}

	public void sendSoulSplit(final Hit hit, final Entity user) {
		final Player target = this;
		if (hit.getDamage() > 0)
			World.sendProjectile(user, this, 2263, 11, 11, 20, 5, 0, 0);
		user.heal(hit.getDamage() / 5);
		prayer.drainPrayer(hit.getDamage() / 5);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				setNextGraphics(new Graphics(2264));
				if (hit.getDamage() > 0)
					World.sendProjectile(target, user, 2263, 11, 11, 20, 5, 0, 0);
			}
		}, 0);
	}

	@Override
	public void handleIngoingHit(final Hit hit) {
		if (hit.getLook() != HitLook.MELEE_DAMAGE
				&& hit.getLook() != HitLook.RANGE_DAMAGE
				&& hit.getLook() != HitLook.MAGIC_DAMAGE)
			return;
		if (invulnerable) {
			hit.setDamage(0);
			return;
		}
		if (auraManager.usingPenance()) {
			int amount = (int) (hit.getDamage() * 0.2);
			if (amount > 0)
				prayer.restorePrayer(amount);
		}
		Entity source = hit.getSource();
		if (source == null)
			return;
		if (polDelay > Utils.currentTimeMillis())
			hit.setDamage((int) (hit.getDamage() * 0.5));
		if (prayer.hasPrayersOn() && hit.getDamage() != 0) {
			if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
				if (prayer.usingPrayer(0, 17))
					hit.setDamage((int) (hit.getDamage() * source
							.getMagePrayerMultiplier()));
				else if (prayer.usingPrayer(1, 7)) {
					int deflectedDamage = source instanceof Nex ? 0
							: (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source
							.getMagePrayerMultiplier()));
					if (deflectedDamage > 0) {
						source.applyHit(new Hit(this, deflectedDamage,
								HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2228));
						setNextAnimation(new Animation(12573));
					}
				}
			} else if (hit.getLook() == HitLook.RANGE_DAMAGE) {
				if (prayer.usingPrayer(0, 18))
					hit.setDamage((int) (hit.getDamage() * source
							.getRangePrayerMultiplier()));
				else if (prayer.usingPrayer(1, 8)) {
					int deflectedDamage = source instanceof Nex ? 0
							: (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source
							.getRangePrayerMultiplier()));
					if (deflectedDamage > 0) {
						source.applyHit(new Hit(this, deflectedDamage,
								HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2229));
						setNextAnimation(new Animation(12573));
					}
				}
			} else if (hit.getLook() == HitLook.MELEE_DAMAGE) {
				if (prayer.usingPrayer(0, 19))
					hit.setDamage((int) (hit.getDamage() * source
							.getMeleePrayerMultiplier()));
				else if (prayer.usingPrayer(1, 9)) {
					int deflectedDamage = source instanceof Nex ? 0
							: (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source
							.getMeleePrayerMultiplier()));
					if (deflectedDamage > 0) {
						source.applyHit(new Hit(this, deflectedDamage,
								HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2230));
						setNextAnimation(new Animation(12573));
					}
				}
			}
		}
		if (hit.getDamage() >= 200) {
			if (hit.getLook() == HitLook.MELEE_DAMAGE) {
				int reducedDamage = hit.getDamage()
						* combatDefinitions.getBonuses()[CombatDefinitions.ABSORVE_MELEE_BONUS]
						/ 100;
				if (reducedDamage > 0) {
					hit.setDamage(hit.getDamage() - reducedDamage);
					hit.setSoaking(new Hit(source, reducedDamage,
							HitLook.ABSORB_DAMAGE));
				}
			} else if (hit.getLook() == HitLook.RANGE_DAMAGE) {
				int reducedDamage = hit.getDamage()
						* combatDefinitions.getBonuses()[CombatDefinitions.ABSORVE_RANGE_BONUS]
						/ 100;
				if (reducedDamage > 0) {
					hit.setDamage(hit.getDamage() - reducedDamage);
					hit.setSoaking(new Hit(source, reducedDamage,
							HitLook.ABSORB_DAMAGE));
				}
			} else if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
				int reducedDamage = hit.getDamage()
						* combatDefinitions.getBonuses()[CombatDefinitions.ABSORVE_MAGE_BONUS]
						/ 100;
				if (reducedDamage > 0) {
					hit.setDamage(hit.getDamage() - reducedDamage);
					hit.setSoaking(new Hit(source, reducedDamage,
							HitLook.ABSORB_DAMAGE));
				}
			}
		}
		int shieldId = equipment.getShieldId();
		if (shieldId == 13742) { // elsyian
			if (Utils.getRandom(100) <= 70)
				hit.setDamage((int) (hit.getDamage() * 0.75));
		} else if (shieldId == 13740) { // divine
			int drain = (int) (Math.ceil(hit.getDamage() * 0.3) / 2);
			if (prayer.getPrayerpoints() >= drain) {
				hit.setDamage((int) (hit.getDamage() * 0.70));
				prayer.drainPrayer(drain);
			}
		}
		if (castedVeng && hit.getDamage() >= 4) {
			castedVeng = false;
			setNextForceTalk(new ForceTalk("Taste vengeance!"));
			source.applyHit(new Hit(this, (int) (hit.getDamage() * 0.75),
					HitLook.REGULAR_DAMAGE));
		}
		if (source instanceof Player) {
			final Player p2 = (Player) source;
			if (p2.prayer.hasPrayersOn()) {
				if (p2.prayer.usingPrayer(0, 24)) { // smite
					int drain = hit.getDamage() / 4;
					if (drain > 0)
						prayer.drainPrayer(drain);
				} else {
					if (hit.getDamage() == 0)
						return;
					if (!p2.prayer.isBoostedLeech()) {
						if (hit.getLook() == HitLook.MELEE_DAMAGE) {
							if (p2.prayer.usingPrayer(1, 19)) {
								if (Utils.getRandom(4) == 0) {
									p2.prayer.increaseTurmoilBonus(this);
									p2.prayer.setBoostedLeech(true);
									return;
								}
							} else if (p2.prayer.usingPrayer(1, 1)) { // sap att
								if (Utils.getRandom(4) == 0) {
									if (p2.prayer.reachedMax(0)) {
										p2.getPackets()
												.sendGameMessage(
														"Your opponent has been weakened so much that your sap curse has no effect.",
														true);
									} else {
										p2.prayer.increaseLeechBonus(0);
										p2.getPackets()
												.sendGameMessage(
														"Your curse drains Attack from the enemy, boosting your Attack.",
														true);
									}
									p2.setNextAnimation(new Animation(12569));
									p2.setNextGraphics(new Graphics(2214));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2215, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2216));
										}
									}, 1);
									return;
								}
							} else {
								if (p2.prayer.usingPrayer(1, 10)) {
									if (Utils.getRandom(7) == 0) {
										if (p2.prayer.reachedMax(3)) {
											p2.getPackets()
													.sendGameMessage(
															"Your opponent has been weakened so much that your leech curse has no effect.",
															true);
										} else {
											p2.prayer.increaseLeechBonus(3);
											p2.getPackets()
													.sendGameMessage(
															"Your curse drains Attack from the enemy, boosting your Attack.",
															true);
										}
										p2.setNextAnimation(new Animation(12575));
										p2.prayer.setBoostedLeech(true);
										World.sendProjectile(p2, this, 2231,
												35, 35, 20, 5, 0, 0);
										WorldTasksManager.schedule(
												new WorldTask() {
													@Override
													public void run() {
														setNextGraphics(new Graphics(
																2232));
													}
												}, 1);
										return;
									}
								}
								if (p2.prayer.usingPrayer(1, 14)) {
									if (Utils.getRandom(7) == 0) {
										if (p2.prayer.reachedMax(7)) {
											p2.getPackets()
													.sendGameMessage(
															"Your opponent has been weakened so much that your leech curse has no effect.",
															true);
										} else {
											p2.prayer.increaseLeechBonus(7);
											p2.getPackets()
													.sendGameMessage(
															"Your curse drains Strength from the enemy, boosting your Strength.",
															true);
										}
										p2.setNextAnimation(new Animation(12575));
										p2.prayer.setBoostedLeech(true);
										World.sendProjectile(p2, this, 2248,
												35, 35, 20, 5, 0, 0);
										WorldTasksManager.schedule(
												new WorldTask() {
													@Override
													public void run() {
														setNextGraphics(new Graphics(
																2250));
													}
												}, 1);
										return;
									}
								}

							}
						}
						if (hit.getLook() == HitLook.RANGE_DAMAGE) {
							if (p2.prayer.usingPrayer(1, 2)) { // sap range
								if (Utils.getRandom(4) == 0) {
									if (p2.prayer.reachedMax(1)) {
										p2.getPackets()
												.sendGameMessage(
														"Your opponent has been weakened so much that your sap curse has no effect.",
														true);
									} else {
										p2.prayer.increaseLeechBonus(1);
										p2.getPackets()
												.sendGameMessage(
														"Your curse drains Range from the enemy, boosting your Range.",
														true);
									}
									p2.setNextAnimation(new Animation(12569));
									p2.setNextGraphics(new Graphics(2217));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2218, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2219));
										}
									}, 1);
									return;
								}
							} else if (p2.prayer.usingPrayer(1, 11)) {
								if (Utils.getRandom(7) == 0) {
									if (p2.prayer.reachedMax(4)) {
										p2.getPackets()
												.sendGameMessage(
														"Your opponent has been weakened so much that your leech curse has no effect.",
														true);
									} else {
										p2.prayer.increaseLeechBonus(4);
										p2.getPackets()
												.sendGameMessage(
														"Your curse drains Range from the enemy, boosting your Range.",
														true);
									}
									p2.setNextAnimation(new Animation(12575));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2236, 35,
											35, 20, 5, 0, 0);
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
							if (p2.prayer.usingPrayer(1, 3)) { // sap mage
								if (Utils.getRandom(4) == 0) {
									if (p2.prayer.reachedMax(2)) {
										p2.getPackets()
												.sendGameMessage(
														"Your opponent has been weakened so much that your sap curse has no effect.",
														true);
									} else {
										p2.prayer.increaseLeechBonus(2);
										p2.getPackets()
												.sendGameMessage(
														"Your curse drains Magic from the enemy, boosting your Magic.",
														true);
									}
									p2.setNextAnimation(new Animation(12569));
									p2.setNextGraphics(new Graphics(2220));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2221, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2222));
										}
									}, 1);
									return;
								}
							} else if (p2.prayer.usingPrayer(1, 12)) {
								if (Utils.getRandom(7) == 0) {
									if (p2.prayer.reachedMax(5)) {
										p2.getPackets()
												.sendGameMessage(
														"Your opponent has been weakened so much that your leech curse has no effect.",
														true);
									} else {
										p2.prayer.increaseLeechBonus(5);
										p2.getPackets()
												.sendGameMessage(
														"Your curse drains Magic from the enemy, boosting your Magic.",
														true);
									}
									p2.setNextAnimation(new Animation(12575));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2240, 35,
											35, 20, 5, 0, 0);
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

						if (p2.prayer.usingPrayer(1, 13)) { // leech defence
							if (Utils.getRandom(10) == 0) {
								if (p2.prayer.reachedMax(6)) {
									p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your leech curse has no effect.",
													true);
								} else {
									p2.prayer.increaseLeechBonus(6);
									p2.getPackets()
											.sendGameMessage(
													"Your curse drains Defence from the enemy, boosting your Defence.",
													true);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.prayer.setBoostedLeech(true);
								World.sendProjectile(p2, this, 2244, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2246));
									}
								}, 1);
								return;
							}
						}

						if (p2.prayer.usingPrayer(1, 15)) {
							if (Utils.getRandom(10) == 0) {
								if (getRunEnergy() <= 0) {
									p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your leech curse has no effect.",
													true);
								} else {
									p2.setRunEnergy(p2.getRunEnergy() > 90 ? 100
											: p2.getRunEnergy() + 10);
									setRunEnergy(p2.getRunEnergy() > 10 ? getRunEnergy() - 10
											: 0);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.prayer.setBoostedLeech(true);
								World.sendProjectile(p2, this, 2256, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2258));
									}
								}, 1);
								return;
							}
						}

						if (p2.prayer.usingPrayer(1, 16)) {
							if (Utils.getRandom(10) == 0) {
								if (combatDefinitions.getSpecialAttackPercentage() <= 0) {
									p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your leech curse has no effect.", true);
								} else {
									p2.combatDefinitions.restoreSpecialAttack();
									combatDefinitions.desecreaseSpecialAttack(10);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.prayer.setBoostedLeech(true);
								World.sendProjectile(p2, this, 2252, 35, 35, 20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2254));
									}
								}, 1);
								return;
							}
						}

						if (p2.prayer.usingPrayer(1, 4)) { // sap spec
							if (Utils.getRandom(10) == 0) {
								p2.setNextAnimation(new Animation(12569));
								p2.setNextGraphics(new Graphics(2223));
								p2.prayer.setBoostedLeech(true);
								if (combatDefinitions.getSpecialAttackPercentage() <= 0) {
									p2.getPackets().sendGameMessage("Your opponent has been weakened so much that your sap curse has no effect.", true);
								} else {
									combatDefinitions.desecreaseSpecialAttack(10);
								}
								World.sendProjectile(p2, this, 2224, 35, 35, 20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2225));
									}
								}, 1);
								return;
							}
						}
					}
				}
			}
		} else {
			NPC n = (NPC) source;
			if (n.getId() == 13448)
				sendSoulSplit(hit, n);
		}
	}

	@Override
	public void sendDeath(final Entity source) {
		if (getCannon().hasCannon()) {
			getCannon().pickUpDwarfCannon(getCannon().getObject());
		}
		if (prayer.hasPrayersOn() && getAttributes().get("startedDuel") != Boolean.TRUE) {
			if (prayer.usingPrayer(0, 22)) {
				setNextGraphics(new Graphics(437));
				final Player target = this;
				if (isAtMultiArea()) {
					for (int regionId : getMapRegionsIds()) {
						List<Integer> playersIndexes = World.getRegion(regionId).getPlayerIndexes();
						if (playersIndexes != null) {
							for (int playerIndex : playersIndexes) {
								Player player = World.getPlayers().get(
										playerIndex);
								if (player == null
										|| !player.hasStarted()
										|| player.isDead()
										|| player.hasFinished()
										|| !player.withinDistance(this, 1)
										|| !player.isCanPvp()
										|| !target.getControlerManager()
												.canHit(player))
									continue;
								player.applyHit(new Hit(target, Utils.getRandom((int) (skills.getLevelForXp(Skills.PRAYER) * 2.5)), HitLook.REGULAR_DAMAGE));
							}
						}
						List<Integer> npcsIndexes = World.getRegion(regionId)
								.getNPCsIndexes();
						if (npcsIndexes != null) {
							for (int npcIndex : npcsIndexes) {
								NPC npc = World.getNPCs().get(npcIndex);
								if (npc == null
										|| npc.isDead()
										|| npc.hasFinished()
										|| !npc.withinDistance(this, 1)
										|| !npc.getDefinitions()
												.hasAttackOption()
										|| !target.getControlerManager()
												.canHit(npc))
									continue;
								npc.applyHit(new Hit(
										target,
										Utils.getRandom((int) (skills
												.getLevelForXp(Skills.PRAYER) * 2.5)),
										HitLook.REGULAR_DAMAGE));
							}
						}
					}
				} else {
					if (source != null && source != this && !source.isDead()
							&& !source.hasFinished()
							&& source.withinDistance(this, 1))
						source.applyHit(new Hit(target, Utils
								.getRandom((int) (skills
										.getLevelForXp(Skills.PRAYER) * 2.5)),
								HitLook.REGULAR_DAMAGE));
				}
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() - 1, target.getY(), target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() + 1, target.getY(), target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX(), target.getY() - 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX(), target.getY() + 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() - 1, target.getY() - 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() - 1, target.getY() + 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() + 1, target.getY() - 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() + 1, target.getY() + 1, target.getPlane()));
					}
				});
			} else if (prayer.usingPrayer(1, 17)) {
				World.sendProjectile(this, new WorldTile(getX() + 2,
						getY() + 2, getPlane()), 2260, 24, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() + 2, getY(),
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() + 2,
						getY() - 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);

				World.sendProjectile(this, new WorldTile(getX() - 2,
						getY() + 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() - 2, getY(),
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() - 2,
						getY() - 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);

				World.sendProjectile(this, new WorldTile(getX(), getY() + 2,
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX(), getY() - 2,
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				final Player target = this;
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						setNextGraphics(new Graphics(2259));

						if (isAtMultiArea()) {
							for (int regionId : getMapRegionsIds()) {
								List<Integer> playersIndexes = World.getRegion(
										regionId).getPlayerIndexes();
								if (playersIndexes != null) {
									for (int playerIndex : playersIndexes) {
										Player player = World.getPlayers().get(
												playerIndex);
										if (player == null
												|| !player.hasStarted()
												|| player.isDead()
												|| player.hasFinished()
												|| !player.isCanPvp()
												|| !player.withinDistance(
														target, 2)
												|| !target
														.getControlerManager()
														.canHit(player))
											continue;
										player.applyHit(new Hit(
												target,
												Utils.getRandom((skills
														.getLevelForXp(Skills.PRAYER) * 3)),
												HitLook.REGULAR_DAMAGE));
									}
								}
								List<Integer> npcsIndexes = World.getRegion(
										regionId).getNPCsIndexes();
								if (npcsIndexes != null) {
									for (int npcIndex : npcsIndexes) {
										NPC npc = World.getNPCs().get(npcIndex);
										if (npc == null
												|| npc.isDead()
												|| npc.hasFinished()
												|| !npc.withinDistance(target,
														2)
												|| !npc.getDefinitions()
														.hasAttackOption()
												|| !target
														.getControlerManager()
														.canHit(npc))
											continue;
										npc.applyHit(new Hit(
												target,
												Utils.getRandom((skills
														.getLevelForXp(Skills.PRAYER) * 3)),
												HitLook.REGULAR_DAMAGE));
									}
								}
							}
						} else {
							if (source != null && source != target && !source.isDead() && !source.hasFinished() && source.withinDistance(target, 2))
								source.applyHit(new Hit(target, Utils.getRandom((skills.getLevelForXp(Skills.PRAYER) * 3)), HitLook.REGULAR_DAMAGE));
						}

						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() + 2, getY() + 2, getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() + 2, getY(), getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() + 2, getY() - 2, getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() - 2, getY() + 2, getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() - 2, getY(), getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() - 2, getY() - 2, getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX(), getY() + 2, getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX(), getY() - 2, getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() + 1, getY() + 1, getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() + 1, getY() - 1, getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() - 1, getY() + 1, getPlane()));
						World.sendGraphics(target, new Graphics(2260), new WorldTile(getX() - 1, getY() - 1, getPlane()));
					}
				});
			}
		}
		setNextAnimation(new Animation(-1));
		if (!controlerManager.sendDeath())
			return;
		lock(7);
		stopAll();
		if (familiar != null)
			familiar.sendDeath(this);
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					setNextAnimation(new Animation(836));
				} else if (loop == 1) {
					getPackets().sendGameMessage("Oh dear, you have died.");
					if (source instanceof Player) {
						Player killer = (Player) source;
						killer.setAttackedByDelay(4);
					}
				} else if (loop == 3) {
					dropItemsOnDeath();
					equipment.init();
					inventory.init();
					reset();
					setNextWorldTile(new WorldTile(Settings.RESPAWN_PLAYER_LOCATION));
					setNextAnimation(new Animation(-1));
				} else if (loop == 4) {
					getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		}, 0, 1);
	}
	
	public void dropItemsOnDeath() {
		if (getDifficulty() > 3) {
			sendItemsOnDeath(this);
		}
	}
	
	public static String yeahfreestringpl000x() {
		switch (Utils.random(8)) {
			case 0: return "There is no escape!";
			case 1: return "Muahahahaha!";
			case 2: return "You belong to me!";
			case 3: return "Beware mortals, you travel with me!";
			case 4: return "Your time here is over!";
			case 5: return "Now is the time you die!";
			case 6: return "I claim you as my own!";
			case 7: return "you are is mine!";
			case 8: return "Let me escort you to Varrock!";
			case 9: return "I have come for you!";
		}
		return "";
	}

	public boolean isBurying = false;

	public boolean isSecured;

	public int bossid;

	public int isCompletionist = 0;

	public void sendItemsOnDeath(Player killer) {
		if (getControlerManager().getControler() instanceof WiseOldMan)
			return;
		if (rights == 2 || rights == 7 || rights == 10)
			return;
		
		if (isTradeLocked())
			return;
		
		charges.die();
		auraManager.removeAura();
		
		ArrayList<Item> contained = new ArrayList<Item>();
		ArrayList<Item> savedItems = new ArrayList<Item>();
	
		for (int i = 0; i < 14; i++) {
			if (equipment.getItem(i) == null)
				continue;
			if (ItemConstants.isSafeOnDeath(equipment.getItem(i).getId()) || 
					ItemConstants.isTradeable(equipment.getItem(i))) {
				savedItems.add(equipment.getItem(i));
			} else {
				contained.add(equipment.getItem(i));
			}
		}
		
		for (int i = 0; i < 28; i++) {
			if (inventory.getItem(i) == null)
				continue;
			if (ItemConstants.isSafeOnDeath(inventory.getItem(i).getId())|| 
					ItemConstants.isTradeable(inventory.getItem(i))) {
				savedItems.add(inventory.getItem(i));
			} else {
				contained.add(inventory.getItem(i));
			}
		}
		
		if (contained.isEmpty())
			return;
		
		int keptAmount = hasSkull() ? 0 : 3;
		
		if (prayer.usingPrayer(0, 10) || prayer.usingPrayer(1, 0)) {
			keptAmount++;
		}
		
		if (getRights() > 0)
			keptAmount += 3;
		if (getRights() > 1)
			keptAmount += 3;
		if (getRights() > 2)
			keptAmount += 3;
		if (getRights() == 7)
			keptAmount += 3;
		
		Item lastItem = new Item(1, 1);
		
		for (int i = 0; i < keptAmount; i++) {
			for (Item item : contained) {
				if (item == null)
					continue;
				int price = ItemManager.getPrice(item.getId());
				if (price >= Shop.getBuyPrice(lastItem)) {
					lastItem = item;
				}
			}
			savedItems.add(lastItem);
			contained.remove(lastItem);
			lastItem = new Item(1, 1);
		}
		
		inventory.reset();
		equipment.reset();
		
		for (Item item : savedItems) {
			if (getInventory().hasFreeSlots()) {
				getInventory().addItem(item);
			} else {
				getBank().addItem(item.getId(), item.getAmount(), false);
				sendMessage(""+item.getName()+" has been placed in your bank.");
			}
		}
		
		Player owner = killer == null ? this : killer;
		
		for (Item item : contained) {
			if (item == null)
				continue;
			World.dropItem(item, owner, 600, Settings.droppedItemDelay);
		}
	}

	public void sendItemsOnDeath2(Player killer) {
		
		if (getControlerManager().getControler() instanceof WiseOldMan)
			return;
		
		if (rights == 2 || rights == 7 || rights == 10)
			return;
		
		if (isTradeLocked())
			return;
		
		charges.die();
		auraManager.removeAura();
		
		ArrayList<Item> contained = new ArrayList<Item>();
		ArrayList<Item> savedItems = new ArrayList<Item>();
	
		for (int i = 0; i < 14; i++) {
			if (equipment.getItem(i) == null)
				continue;
			if (ItemConstants.isSafeOnDeath(equipment.getItem(i).getId()) || ItemConstants.isTradeable(equipment.getItem(i))) {
				savedItems.add(equipment.getItem(i));
			} else {
				contained.add(equipment.getItem(i));
			}
		}
		
		for (int i = 0; i < 28; i++) {
			if (inventory.getItem(i) == null)
				continue;
			if (ItemConstants.isSafeOnDeath(inventory.getItem(i).getId()) || ItemConstants.isTradeable(inventory.getItem(i))) {
				savedItems.add(inventory.getItem(i));
			} else {
				contained.add(inventory.getItem(i));
			}
		}
		
		if (contained.isEmpty())
			return;
		
		int keptAmount = hasSkull() ? 0 : 3;
		
		if (prayer.usingPrayer(0, 10) || prayer.usingPrayer(1, 0)) {
			keptAmount++;
		}
		
		Item lastItem = new Item(1, 1);
		
		for (int i = 0; i < keptAmount; i++) {
			for (Item item : contained) {
				if (item == null)
					continue;
				int price = ItemManager.getPrice(item.getId());
				if (price >= Shop.getBuyPrice(lastItem)) {
					lastItem = item;
				}
			}
			savedItems.add(lastItem);
			contained.remove(lastItem);
			lastItem = new Item(1, 1);
		}
		
		inventory.reset();
		equipment.reset();
		
		for (Item item : savedItems) {
			if (getInventory().hasFreeSlots()) {
				getInventory().addItem(item);
			} else {
				getBank().addItem(item.getId(), item.getAmount(), false);
				sendMessage(""+item.getName()+" has been placed in your bank.");
			}
		}
		
		Player owner = killer == null ? this : killer;
		
		for (Item item : contained) {
			if (item == null)
				continue;
			World.dropItem(item, owner, 600, Settings.droppedItemDelay);
		}
	}
	
	public void increaseKillCount(Player killed) {
		killed.deathCount++;
		PkRank.checkRank(killed);
		if (killed.getSession().getIP().equals(getSession().getIP()))
			return;
		setPvpPoints(getPvpPoints() + 10);
		killCount++;
		getPackets().sendGameMessage("<col=ff0000>You have killed " + killed.getDisplayName() + ", you have now " + killCount + " kills.");
		getPackets().sendGameMessage("<col=ff0000>You have been awarded 10 PVP Points. (You now have "+getPvpPoints()+").");
		PkRank.checkRank(this);
	}

	public void sendRandomJail(Player p) {
		p.resetWalkSteps();
		switch (Utils.getRandom(6)) {
		case 0:
			p.setNextWorldTile(new WorldTile(2669, 10387, 0));
			break;
		case 1:
			p.setNextWorldTile(new WorldTile(2669, 10383, 0));
			break;
		case 2:
			p.setNextWorldTile(new WorldTile(2669, 10379, 0));
			break;
		case 3:
			p.setNextWorldTile(new WorldTile(2673, 10379, 0));
			break;
		case 4:
			p.setNextWorldTile(new WorldTile(2673, 10385, 0));
			break;
		case 5:
			p.setNextWorldTile(new WorldTile(2677, 10387, 0));
			break;
		case 6:
			p.setNextWorldTile(new WorldTile(2677, 10383, 0));
			break;
		}
	}

	@Override
	public int getSize() {
		return appearence.getSize();
	}

	public int starterStage = 0;
	
	public boolean isCanPvp() {
		if (isTradeLocked())
			return false;
		if (getRights() > 0) {
			if (getRights() <= 2 || getRights() == 7) {
				return false;
			}
		}
		return canPvp;
	}

	public void setCanPvp(boolean canPvp) {
		this.canPvp = canPvp;
		appearence.generateAppearenceData();
		getPackets().sendPlayerOption(canPvp ? "Attack" : "null", 1, true);
		getPackets().sendPlayerUnderNPCPriority(canPvp);
	}

	public Prayer getPrayer() {
		return prayer;
	}

	public long getLockDelay() {
		return lockDelay;
	}

	public boolean isLocked() {
		return lockDelay >= Utils.currentTimeMillis();
	}

	public void lock() {
		lockDelay = Long.MAX_VALUE;
	}

	public void lock(long time) {
		lockDelay = Utils.currentTimeMillis() + (time * 600);
	}

	public void unlock() {
		lockDelay = 0;
	}

	public void useStairs(int emoteId, final WorldTile dest, int useDelay,
			int totalDelay) {
		useStairs(emoteId, dest, useDelay, totalDelay, null);
	}

	public void useStairs(int emoteId, final WorldTile dest, int useDelay,
			int totalDelay, final String message) {
		stopAll();
		lock(totalDelay);
		if (emoteId != -1)
			setNextAnimation(new Animation(emoteId));
		if (useDelay == 0)
			setNextWorldTile(dest);
		else {
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					if (isDead())
						return;
					setNextWorldTile(dest);
					if (message != null)
						getPackets().sendGameMessage(message);
				}
			}, useDelay - 1);
		}
	}

	public Bank getBank() {
		return bank;
	}
	public Toolbelt getTools() {
		return belt;
	}
	public ControlerManager getControlerManager() {
		return controlerManager;
	}
	public void switchMouseButtons() {
		mouseButtons = !mouseButtons;
		refreshMouseButtons();
	}

	public void switchAllowChatEffects() {
		allowChatEffects = !allowChatEffects;
		refreshAllowChatEffects();
	}

	public void refreshAllowChatEffects() {
		getPackets().sendConfig(171, allowChatEffects ? 0 : 1);
	}

	public void refreshMouseButtons() {
		getPackets().sendConfig(170, mouseButtons ? 0 : 1);
	}

	public void refreshPrivateChatSetup() {
		getPackets().sendConfig(287, privateChatSetup);
	}

	public void refreshOtherChatsSetup() {
		int value = friendChatSetup << 6;
		getPackets().sendConfig(1438, value);
	}

	public void setPrivateChatSetup(int privateChatSetup) {
		this.privateChatSetup = privateChatSetup;
	}

	public void setFriendChatSetup(int friendChatSetup) {
		this.friendChatSetup = friendChatSetup;
	}

	public int getPrivateChatSetup() {
		return privateChatSetup;
	}

	public boolean isForceNextMapLoadRefresh() {
		return forceNextMapLoadRefresh;
	}

	public void setForceNextMapLoadRefresh(boolean forceNextMapLoadRefresh) {
		this.forceNextMapLoadRefresh = forceNextMapLoadRefresh;
	}

	public FriendsIgnores getFriendsIgnores() {
		return friendsIgnores;
	}

	public void sendMessage(String message) {
		getPackets().sendGameMessage(message);
	}

	/*
	 * do not use this, only used by pm
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void addPotDelay(long time) {
		potDelay = time + Utils.currentTimeMillis();
	}

	public long getPotDelay() {
		return potDelay;
	}

	public void addFoodDelay(long time) {
		foodDelay = time + Utils.currentTimeMillis();
	}

	public long getFoodDelay() {
		return foodDelay;
	}

	public long getBoneDelay() {
		return boneDelay;
	}

	public void addBoneDelay(long time) {
		boneDelay = time + Utils.currentTimeMillis();
	}

	public void addPoisonImmune(long time) {
		poisonImmune = time + Utils.currentTimeMillis();
		getPoison().reset();
	}

	public long getPoisonImmune() {
		return poisonImmune;
	}

	public void addFireImmune(long time) {
		fireImmune = time + Utils.currentTimeMillis();
	}

	public long getFireImmune() {
		return fireImmune;
	}

	@Override
	public void heal(int ammount, int extra) {
		super.heal(ammount, extra);
		refreshHitPoints();
	}

	public MusicsManager getMusicsManager() {
		return musicsManager;
	}

	public HintIconsManager getHintIconsManager() {
		return hintIconsManager;
	}

	public boolean isCastVeng() {
		return castedVeng;
	}

	public void setCastVeng(boolean castVeng) {
		this.castedVeng = castVeng;
	}

	public int getKillCount() {
		return killCount;
	}

	public int getBarrowsKillCount() {
		return barrowsKillCount;
	}

	public int setBarrowsKillCount(int barrowsKillCount) {
		return this.barrowsKillCount = barrowsKillCount;
	}

	public int setKillCount(int killCount) {
		return this.killCount = killCount;
	}

	public int getDeathCount() {
		return deathCount;
	}

	public int setDeathCount(int deathCount) {
		return this.deathCount = deathCount;
	}

	public void setCloseInterfacesEvent(Runnable closeInterfacesEvent) {
		this.closeInterfacesEvent = closeInterfacesEvent;
	}

	public long getMuted() {
		return muted;
	}

	public void setMuted(long muted) {
		this.muted = muted;
	}

	public void out(String text, int delay) {

	}
	
	public int getGESlot() {
		return GESlot;
	}

	public void setGESlot(int gESlot) {
		GESlot = gESlot;
	}

	/**
	 * The player's last used grand exchange slot.
	 */
	private int GESlot;

	private int DungeonIndex = 0;
	
	public void setDungIndex(int index){
		this.DungeonIndex = index;
	}
	
	public int getDungIndex(){
		return DungeonIndex;
	}
	
	public int box;
	private boolean trustedflower;
	
	private int[] outfitId;

	public void setOutfitId(int slot, int outfit) {
		this.outfitId[slot] = outfit;
	}
	
	public int getOutfitId(int slot) {
		return outfitId[slot];
	}
	
	public int outfit;
	
	public int getOutfit() {
		return outfit;
	}
	
	public void setOutfit(int outfit) {
		this.outfit = outfit;
	}
	
	public long getJailed() {
		return jailed;
	}

	public void setJailed(long jailed) {
		this.jailed = jailed;
	}

	public boolean isPermBanned() {
		return permBanned;
	}

	public void setPermBanned(boolean permBanned) {
		this.permBanned = permBanned;
	}

	public long getBanned() {
		return banned;
	}

	public void setBanned(long banned) {
		this.banned = banned;
	}

	public ChargesManager getCharges() {
		return charges;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean[] getKilledBarrowBrothers() {
		return killedBarrowBrothers;
	}

	public void setHiddenBrother(int hiddenBrother) {
		this.hiddenBrother = hiddenBrother;
	}

	public int getHiddenBrother() {
		return hiddenBrother;
	}

	public void resetBarrows() {
		hiddenBrother = -1;
		killedBarrowBrothers = new boolean[7]; // includes new bro for future
												// use
		barrowsKillCount = 0;
	}
	
	public boolean isGraphicDesigner() {
		return isGraphicDesigner;
	}

	public boolean isForumModerator() {
		return isForumModerator;
	}

	public void setGraphicDesigner(boolean isGraphicDesigner) {
		this.isGraphicDesigner = isGraphicDesigner;
	}

	public void setForumModerator(boolean isForumModerator) {
		this.isForumModerator = isForumModerator;
	}


	public String getRecovQuestion() {
		return recovQuestion;
	}

	public void setRecovQuestion(String recovQuestion) {
		this.recovQuestion = recovQuestion;
	}

	public String getRecovAnswer() {
		return recovAnswer;
	}

	public void setRecovAnswer(String recovAnswer) {
		this.recovAnswer = recovAnswer;
	}

	public String getLastMsg() {
		return lastMsg;
	}

	public void setLastMsg(String lastMsg) {
		this.lastMsg = lastMsg;
	}

	public void logThis(String lastMsg) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			System.out.println(dateFormat.format(cal.getTime()));
			final String FILE_PATH = "data/playersaves/logs/chatlogs/";
			BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH + getUsername() + ".txt", true));
			writer.write("[" + dateFormat.format(cal.getTime()) + "] : " + lastMsg);
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (IOException er) {
			System.out.println("Error logging chatlog.");
		}
	}

	public int[] getPouches() {
		return pouches;
	}

	public EmotesManager getEmotesManager() {
		return emotesManager;
	}

	public String getLastIP() {
		return lastIP;
	}

	public String getLastHostname() {
		InetAddress addr;
		try {
			addr = InetAddress.getByName(getLastIP());
			String hostname = addr.getHostName();
			return hostname;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	public PriceCheckManager getPriceCheckManager() {
		return priceCheckManager;
	}

	public void setPestPoints(int pestPoints) {
		this.pestPoints = pestPoints;
	}

	public int getPestPoints() {
		return pestPoints;
	}

	public boolean isUpdateMovementType() {
		return updateMovementType;
	}

	public long getLastPublicMessage() {
		return lastPublicMessage;
	}

	public void setLastPublicMessage(long lastPublicMessage) {
		this.lastPublicMessage = lastPublicMessage;
	}

	public CutscenesManager getCutscenesManager() {
		return cutscenesManager;
	}

	public void kickPlayerFromFriendsChannel(String name) {
		if (currentFriendChat == null)
			return;
		currentFriendChat.kickPlayerFromChat(this, name);
	}

	public void sendFriendsChannelMessage(String message) {
		if (currentFriendChat == null)
			return;
		currentFriendChat.sendMessage(this, message);
	}

	public void sendFriendsChannelQuickMessage(QuickChatMessage message) {
		if (currentFriendChat == null)
			return;
		currentFriendChat.sendQuickMessage(this, message);
	}

	public void sendPublicChatMessage(PublicChatMessage message) {
		if (message.getMessage().toLowerCase().contains("0hdr") || getUsername().toLowerCase().contains("dragonkk")) {
		     sendMessage("You mad bro?");
		     return;
		}
		for (int regionId : getMapRegionsIds()) {
			List<Integer> playersIndexes = World.getRegion(regionId).getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player p = World.getPlayers().get(playerIndex);
				if (p == null || !p.hasStarted() || p.hasFinished() || p.getLocalPlayerUpdate().getLocalPlayers()[getIndex()] == null)
					continue;
				p.getPackets().sendPublicMessage(this, message);
			}
		}
	}

	public int[] getCompletionistCapeCustomized() {
		return completionistCapeCustomized;
	}

	public void setCompletionistCapeCustomized(int[] skillcapeCustomized) {
		this.completionistCapeCustomized = skillcapeCustomized;
	}

	public int[] getMaxedCapeCustomized() {
		return maxedCapeCustomized;
	}

	public void setMaxedCapeCustomized(int[] maxedCapeCustomized) {
		this.maxedCapeCustomized = maxedCapeCustomized;
	}

	public void setSkullId(int skullId) {
		this.skullId = skullId;
	}

	public int getSkullId() {
		return skullId;
	}

	public boolean isFilterGame() {
		return filterGame;
	}

	public void setFilterGame(boolean filterGame) {
		this.filterGame = filterGame;
	}

	public void addLogicPacketToQueue(LogicPacket packet) {
		for (LogicPacket p : logicPackets) {
			if (p.getId() == packet.getId()) {
				logicPackets.remove(p);
				break;
			}
		}
		logicPackets.add(packet);
	}

	public DominionTower getDominionTower() {
		return dominionTower;
	}

	public void setPrayerRenewalDelay(int delay) {
		this.prayerRenewalDelay = delay;
	}

	public int getOverloadDelay() {
		return overloadDelay;
	}

	public void setOverloadDelay(int overloadDelay) {
		this.overloadDelay = overloadDelay;
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTeleBlockDelay(long teleDelay) {
		getAttributes().put("TeleBlocked",
				teleDelay + Utils.currentTimeMillis());
	}

	public long getTeleBlockDelay() {
		Long teleblock = (Long) getAttributes().get("TeleBlocked");
		if (teleblock == null)
			return 0;
		return teleblock;
	}

	public void setPrayerDelay(long teleDelay) {
		getAttributes().put("PrayerBlocked",
				teleDelay + Utils.currentTimeMillis());
		prayer.closeAllPrayers();
	}

	public long getPrayerDelay() {
		Long teleblock = (Long) getAttributes().get("PrayerBlocked");
		if (teleblock == null)
			return 0;
		return teleblock;
	}

	public Familiar getFamiliar() {
		return familiar;
	}

	public void setFamiliar(Familiar familiar) {
		this.familiar = familiar;
	}

	public FriendChatsManager getCurrentFriendChat() {
		return currentFriendChat;
	}

	public void setCurrentFriendChat(FriendChatsManager currentFriendChat) {
		this.currentFriendChat = currentFriendChat;
	}

	public String getCurrentFriendChatOwner() {
		return currentFriendChatOwner;
	}

	public void setCurrentFriendChatOwner(String currentFriendChatOwner) {
		this.currentFriendChatOwner = currentFriendChatOwner;
	}

	public int getSummoningLeftClickOption() {
		return summoningLeftClickOption;
	}

	public void setSummoningLeftClickOption(int summoningLeftClickOption) {
		this.summoningLeftClickOption = summoningLeftClickOption;
	}

	public boolean canSpawn() {
		if (Wilderness.isAtWild(this)
				|| getControlerManager().getControler() instanceof FightPitsArena
				|| getControlerManager().getControler() instanceof CorpBeastControler
				|| getControlerManager().getControler() instanceof PestControlLobby
				|| getControlerManager().getControler() instanceof PestControlGame
				|| getControlerManager().getControler() instanceof ZGDControler
				|| getControlerManager().getControler() instanceof GodWars
				|| getControlerManager().getControler() instanceof DTControler
				|| getControlerManager().getControler() instanceof DuelArena
				|| getControlerManager().getControler() instanceof CastleWarsPlaying
				|| getControlerManager().getControler() instanceof CastleWarsWaiting
				|| getControlerManager().getControler() instanceof FightCaves
				|| getControlerManager().getControler() instanceof FightKiln
				|| FfaZone.inPvpArea(this)
				|| getControlerManager().getControler() instanceof NomadsRequiem
				|| getControlerManager().getControler() instanceof QueenBlackDragonController
				|| getControlerManager().getControler() instanceof WarControler) {
			return false;
		}
		if (getControlerManager().getControler() instanceof CrucibleControler) {
			CrucibleControler controler = (CrucibleControler) getControlerManager().getControler();
			return !controler.isInside();
		}
		return true;
	}

	public long getPolDelay() {
		return polDelay;
	}

	public void addPolDelay(long delay) {
		polDelay = delay + Utils.currentTimeMillis();
	}

	public void setPolDelay(long delay) {
		this.polDelay = delay;
	}

	public AuraManager getAuraManager() {
		return auraManager;
	}

	public int getMovementType() {
		if (getTemporaryMoveType() != -1)
			return getTemporaryMoveType();
		return getRun() ? RUN_MOVE_TYPE : WALK_MOVE_TYPE;
	}

	public List<String> getOwnedObjectManagerKeys() {
		if (ownedObjectsManagerKeys == null) // temporary
			ownedObjectsManagerKeys = new LinkedList<String>();
		return ownedObjectsManagerKeys;
	}

	public boolean hasInstantSpecial(final int weaponId) {
		switch (weaponId) {
		case 4153:
		case 15486:
		case 22207:
		case 22209:
		case 22211:
		case 22213:
		case 1377:
		case 13472:
		case 35:// Excalibur
		case 8280:
		case 14632:
			return true;
		default:
			return false;
		}
	}
	
	private long switchDelay;
	
	public long getSwitchDelay() {
		return switchDelay;
	}
	
	public void setSwitchDelay(long delay) {
		this.switchDelay = delay;
	}
	
	private boolean usingClawSpec;
	
	public void setUsingClawSpec(boolean value) {
		this.usingClawSpec = value;
	}
	
	public boolean usingClawSpec() {
		return usingClawSpec;
	}
	
	public void performInstantSpecial(final int weaponId) {
		int specAmt = PlayerCombat.getSpecialAmmount(weaponId);
		if (combatDefinitions.hasRingOfVigour())
			specAmt *= 0.9;
		if (combatDefinitions.getSpecialAttackPercentage() < specAmt) {
			getPackets().sendGameMessage("You don't have enough power left.");
			combatDefinitions.desecreaseSpecialAttack(0);
			return;
		}
		
		/*if (this.getSwitchItemCache().size() > 0) {
			ButtonHandler.submitSpecialRequest(this);
			return;
		}*/
		
		switch (weaponId) {
		case 4153:
			combatDefinitions.setInstantAttack(true);
			combatDefinitions.switchUsingSpecialAttack();
			Entity target = (Entity) getAttributes().get("last_target");
			if (target != null && target.getAttributes().get("last_attacker") == this) {
				if (!(getActionManager().getAction() instanceof PlayerCombat) || ((PlayerCombat) getActionManager().getAction()).getTarget() != target) {
					getActionManager().setAction(new PlayerCombat(target));
				}
			}
			break;
		case 1377:
		case 13472:
			setNextAnimation(new Animation(1056));
			setNextGraphics(new Graphics(246));
			setNextForceTalk(new ForceTalk("Raarrrrrgggggghhhhhhh!"));
			int defence = (int) (skills.getLevelForXp(Skills.DEFENCE) * 0.90D);
			int attack = (int) (skills.getLevelForXp(Skills.ATTACK) * 0.90D);
			int range = (int) (skills.getLevelForXp(Skills.RANGE) * 0.90D);
			int magic = (int) (skills.getLevelForXp(Skills.MAGIC) * 0.90D);
			int strength = (int) (skills.getLevelForXp(Skills.STRENGTH) * 1.2D);
			skills.set(Skills.DEFENCE, defence);
			skills.set(Skills.ATTACK, attack);
			skills.set(Skills.RANGE, range);
			skills.set(Skills.MAGIC, magic);
			skills.set(Skills.STRENGTH, strength);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			break;
		case 35:// Excalibur
		case 8280:
		case 14632:
			setNextAnimation(new Animation(1168));
			setNextGraphics(new Graphics(247));
			final boolean enhanced = weaponId == 14632;
			skills.set(
					Skills.DEFENCE,
					enhanced ? (int) (skills.getLevelForXp(Skills.DEFENCE) * 1.15D)
							: (skills.getLevel(Skills.DEFENCE) + 8));
			WorldTasksManager.schedule(new WorldTask() {
				int count = 5;

				@Override
				public void run() {
					if (isDead() || hasFinished() || getHitpoints() >= getMaxHitpoints()) {
						stop();
						return;
					}
					heal(enhanced ? 80 : 40);
					if (count-- == 0) {
						stop();
						return;
					}
				}
			}, 4, 2);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			break;
		case 15486:
		case 22207:
		case 22209:
		case 22211:
		case 22213:
			setNextAnimation(new Animation(12804));
			setNextGraphics(new Graphics(2319));// 2320
			setNextGraphics(new Graphics(2321));
			addPolDelay(60000);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			break;
		}
	}

	public void setDisableEquip(boolean equip) {
		disableEquip = equip;
	}

	public boolean isEquipDisabled() {
		return disableEquip;
	}

	public void addDisplayTime(long i) {
		this.displayTime = i + Utils.currentTimeMillis();
	}

	public long getDisplayTime() {
		return displayTime;
	}

	public int getPublicStatus() {
		return publicStatus;
	}

	public void setPublicStatus(int publicStatus) {
		this.publicStatus = publicStatus;
	}

	public int getClanStatus() {
		return clanStatus;
	}

	public void setClanStatus(int clanStatus) {
		this.clanStatus = clanStatus;
	}

	public int getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(int tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public int getAssistStatus() {
		return assistStatus;
	}

	public void setAssistStatus(int assistStatus) {
		this.assistStatus = assistStatus;
	}

	public boolean isSpawnsMode() {
		return spawnsMode;
	}

	public void setSpawnsMode(boolean spawnsMode) {
		this.spawnsMode = spawnsMode;
	}
	
	private ArrayList<Note> pnotes;
	
	public ArrayList<Note> getCurNotes() {
		return pnotes;
	}
	
	public boolean isAtWorldBoss() {
		return getX() >= 208 && getX() <= 223 && getY() >= 5376 && getY() <= 5391;
	}
	
	public Notes getNotes() {
		return notes;
	}

	public int cluenoreward;
	
	public boolean unlockedEmotes;
	
	public IsaacKeyPair getIsaacKeyPair() {
		return isaacKeyPair;
	}

	public QuestManager getQuestManager() {
		return questManager;
	}

	public boolean isCompletedFightCaves() {
		return completedFightCaves;
	}

	public void setCompletedFightCaves() {
		if (!completedFightCaves) {
			completedFightCaves = true;
			refreshFightKilnEntrance();
		}
	}

	public boolean isCompletedFightKiln() {
		return completedFightKiln;
	}
	public void setCompletedFightKiln() {
		completedFightKiln = true;
	}
	public boolean isWonFightPits() {
		return wonFightPits;
	}
	public void setWonFightPits() {
		wonFightPits = true;
	}
	public boolean isCantTrade() {
		return cantTrade;
	}
	public void setCantTrade(boolean canTrade) {
		this.cantTrade = canTrade;
	}
	public String getYellColor() {
		return yellColor;
	}
	public void setYellColor(String yellColor) {
		this.yellColor = yellColor;
	}
	public Pet getPet() {
		return pet;
	}
	public void setPet(Pet pet) {
		this.pet = pet;
	}
	public boolean isSupporter() {
		return isSupporter;
	}
	public void setSupporter(boolean isSupporter) {
		this.isSupporter = isSupporter;
	}
	public PetManager getPetManager() {
		return petManager;
	}
	public void setPetManager(PetManager petManager) {
		this.petManager = petManager;
	}
	public boolean isXpLocked() {
		return xpLocked;
	}
	public void setXpLocked(boolean locked) {
		this.xpLocked = locked;
	}
	public int getLastBonfire() {
		return lastBonfire;
	}
	public void setLastBonfire(int lastBonfire) {
		this.lastBonfire = lastBonfire;
	}
	public boolean isYellOff() {
		return yellOff;
	}
	public void setYellOff(boolean yellOff) {
		this.yellOff = yellOff;
	}
	public void setInvulnerable(boolean invulnerable) {
		this.invulnerable = invulnerable;
	}
	public double getHpBoostMultiplier() {
		return hpBoostMultiplier;
	}
	public void setHpBoostMultiplier(double hpBoostMultiplier) {
		this.hpBoostMultiplier = hpBoostMultiplier;
	}
	public boolean isKilledQueenBlackDragon() {
		return killedQueenBlackDragon;
	}
	public void setKilledQueenBlackDragon(boolean killedQueenBlackDragon) {
		this.killedQueenBlackDragon = killedQueenBlackDragon;
	}
	public boolean hasLargeSceneView() {
		return largeSceneView;
	}
	public void setLargeSceneView(boolean largeSceneView) {
		this.largeSceneView = largeSceneView;
	}
	public int getRuneSpanPoints() {
		return runeSpanPoints;
	}
	public void setRuneSpanPoint(int runeSpanPoints) {
		this.runeSpanPoints = runeSpanPoints;
	}
	public void addRunespanPoints(int points) {
		this.runeSpanPoints += points;
	}
	public DuelRules getLastDuelRules() {
		return lastDuelRules;
	}
	public void setLastDuelRules(DuelRules duelRules) {
		this.lastDuelRules = duelRules;
	}
	public boolean isTalkedWithMarv() {
		return talkedWithMarv;
	}
	public void setTalkedWithMarv() {
		talkedWithMarv = true;
	}
	public int getCrucibleHighScore() {
		return crucibleHighScore;
	}
	public void increaseCrucibleHighScore() {
		crucibleHighScore++;
	}
	public void setSlayerPoints(int slayerPoints) {
		this.slayerPoints = slayerPoints;
	}
	public int getSlayerPoints() {
		return slayerPoints;
	}
	public void setSpins(int spins) {
		this.spins = spins;
	}
	public int getSpins() {
		return spins;
	}
	public int getLoyaltyPoints() {
		return Loyaltypoints;
	}
	public void setLoyaltyPoints(int Loyaltypoints) {
		this.Loyaltypoints = Loyaltypoints;
	}
	
	public void sm(String message) {
		getPackets().sendGameMessage(message);
	}
	public void teleportPlayer(int x, int y, int z) {
		setNextWorldTile(new WorldTile(x, y, z));
		stopAll();
	}
	public void teleport(WorldTile tile) {
		setNextWorldTile(tile);
		stopAll();
	}
	
	public void LockAccount() {
		World.sendWorldMessage("Lockout toggled, account: <username>'s character has been" + "locked by MorrowRealm Account Guardian.", true);
	}
	public int getLastLoggedIn() {
		return lastlogged;
	}
	public int getCoins() {
		return moneypouch;
	}
	public void setCoins(int coins) {
		this.moneypouch = coins;
	}
	public void out(String string) {
		getPackets().sendGameMessage(string);
	}
	public int getDungReward() {
		return dungReward;
	}
	public void setDungReward(int dungReward) {
		this.dungReward = dungReward;
	}
	public boolean isAtExpertDung() {
		return isAtExpertDung;
	}
	public void setAtExpertDung(boolean b) {
		this.isAtExpertDung = b;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getPvpPoints() {
		return pvpPoints;
	}
	public void setPvpPoints(int pvpPoints) {
		this.pvpPoints = pvpPoints;
	}
	public boolean hasClaimed() {
		return claimedSqueal;
	}
	public void setClaimed() {
		this.claimedSqueal = !this.claimedSqueal;
	}
	public void setDicer(boolean b) {
		this.isDicer = b;
	}
	public boolean isDicer() {
		return isDicer;
	}
	public int getVotePoints() {
		return votePoints;
	}
	public void setVotePoints(int i) {
		this.votePoints = i;
	}
	public boolean isAtDung() {
		return isAtDung;
	}
	public void setAtDung(boolean b) {
		this.isAtDung = b;
	}
	private static PrizedPendants pendants = null; 
	public PrizedPendants getPendant() {
		return pendants;
	}
	public boolean isPacketDebug() {
		return packetDebug;
	}
	public void setPacketDebug(boolean packetDebug) {
		this.packetDebug = packetDebug;
	}
	public void setPrestigeLevel(int level) {
		this.prestigeLevel = level;
	}
	
	public int getPrestigeLevel() {
		return prestigeLevel;
	}
	
	public int getPrestigePoints() {
		return prestigePoints;
	}
	
	public void setPrestigePoints(int points) {
		this.prestigePoints = points;
	}
	
	public Prestige getPrestige() {
		return prestige;
	}
	public String getDisplayInfo() {
		return RightsManager.getInfo(this);
	}
	
	public boolean isOwner() {
		return username.toLowerCase().equals(Settings.OWNERS[0]) || rights == 7;
	}
	
	public boolean isAdmin() {
		return rights == 2 && !isOwner();
	}
	
	public boolean isModerator() {
		return rights == 1;
	}
	
	public boolean isStaff(){
	    return rights == 1 || rights == 2;
	}
	
	public boolean isNormal() {
	    return rights == 0;
	}
	
	public boolean isDonator() {
	    return rights == 4;
	}
	
	public boolean isSuper() {
	    return rights == 5;
	}
	
	public boolean isExtreme() {
	    return rights == 6;
	}
	
	public boolean isWikiEditor() {
	    return rights == 10;
	}
	
	public boolean isDonor() {
		return isDonator() || isSuper() || isExtreme() || isWikiEditor();
	}
	
	public String getRank() {
		if (getRights() == 0 && !isDicer())
			return "Player";
		if (getRights() == 0 && isDicer())
			return "Trusted Dicer";
		if (getRights() == 1)
			return "<img=0>Mod.</col> ";
		if (getRights() == 2)
			return "<img=1>Admin. ";
		if (getRights() == 4)
			return "<img=4>";
		if (getRights() == 5)
			return "<img=5>";
		if (getRights() == 6)
			return "<img=6>";
		if (getRights() == 7)
			return "<img=1><img=1>Owner ";
		return "";
	}
	
	public String getAccountType() {
		if (getRights() == 0 && !isDicer())
			return "Player";
		if (getRights() == 0 && isDicer())
			return "Trusted Dicer";
		if (getRights() == 1)
			return "<img=0>Moderator";
		if (getRights() == 2)
			return "<img=1>Administrator";
		if (getRights() == 4)
			return "<img=4>Regular Donator";
		if (getRights() == 5)
			return "<img=5>Super Donator";
		if (getRights() == 6)
			return "<img=6>Extreme Donator";
		if (getRights() == 7)
			return "<img=1><img=1>Owner";
		if (getRights() == 9)
			return "<img=9>Supporter";
		if (getRights() == 10)
			return "<img=10>Wiki Editor";
		return "Player";
	}

	public boolean hasClaimedCompCape() {
		return claimedCompCape;
	}

	public void setClaimedCompCape(boolean claimedCompCape) {
		this.claimedCompCape = claimedCompCape;
	}

	public boolean hasClaimedMaxCape() {
		return claimedMaxCape;
	}

	public void setClaimedMaxCape(boolean claimedMaxCape) {
		this.claimedMaxCape = claimedMaxCape;
	}
	
	public int getRingOfWealth() {
		return ringOfWealth;
	}

	public void setRingOfWealth(int ringOfWealth) {
		this.ringOfWealth = ringOfWealth;
	}

	public int getLogsCut() {
		return logsCut;
	}

	public void setLogsCut(int logsCut) {
		this.logsCut = logsCut;
	}

	public int getOresMined() {
		return oresMined;
	}

	public void setOresMined(int oresMined) {
		this.oresMined = oresMined;
	}

	public boolean isTrustedflower() {
		return trustedflower;
	}

	public boolean setTrustedflower(boolean trustedflower) {
		this.trustedflower = trustedflower;
		return trustedflower;
	}

	public boolean isSkullEnabled() {
		return skullEnabled;
	}

	public void setSkullEnabled(boolean skullEnabled) {
		this.skullEnabled = skullEnabled;
	}

	public boolean isUsingAltEmotes() {
		return usingAltEmotes;
	}

	public void setUsingAltEmotes(boolean usingAltEmotes) {
		this.usingAltEmotes = usingAltEmotes;
	}

	public boolean isAtFightCaves() {
		return atFightCaves;
	}

	public void setAtFightCaves(boolean atFightCaves) {
		this.atFightCaves = atFightCaves;
	}

	public ThievingStalls getStalls() {
		return stalls;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLastDonation() {
		return lastDonation;
	}

	public void setLastDonation(int lastDonation) {
		this.lastDonation = lastDonation;
	}

	public boolean isInBossQueue() {
		return inBossQueue;
	}

	public void setInBossQueue(boolean inBossQueue) {
		this.inBossQueue = inBossQueue;
	}

	public double getDonationAmount() {
		return donationAmount;
	}

	public void setDonationAmount(double donationAmount) {
		this.donationAmount = donationAmount;
	}
	
	public int getBandosKills() {
		return bandosKills;
	}
	
	public int getArmadylKills() {
		return armadylKills;
	}
	
	public int getZamorakKills() {
		return zamorakKills;
	}
	
	public int getSaradominKills() {
		return saradominKills;
	}
	
	public void setBandosKills(int value) {
		this.bandosKills = value;
	}
	
	public void setArmadylKills(int value) {
		this.armadylKills = value;
	}
	
	public void setZamorakKills(int value) {
		this.zamorakKills = value;
	}
	
	public void setSaradominKills(int value) {
		this.saradominKills = value;
	}
	
	public void setDungPoints(long points) {
		this.dungeonPoints = points;
	}
	public long getDungPoints() {
		return dungeonPoints;
	}
	
	public WorldObject examinedObject;
	private int raidKills;
	public int getRaidKills() {
		return raidKills;
	}
	
	public void setRaidKills(int k) {
		raidKills = k;
	}
	
	public long lastSpinsReceived;
	
	public void setLastSpinsReceived(long value) {
		lastSpinsReceived = value;
	}
	
	public long getLastSpinsReceived() {
		return lastSpinsReceived;
	}
	
	public void applySpins() {
		if (lastSpinsReceived < Utils.currentTimeMillis()) {
			lastSpinsReceived = Utils.currentTimeMillis();
		}
	}
	
	private int dungeonLevel;
	public void setDungLevel(int value) {
		this.dungeonLevel = value;
	}
	
	public int getDungLevel() {
		return dungeonLevel;
	}
	
	private long lastDungBossKill;
	
	public void setLastDungBossKill(long value) {
		this.lastDungBossKill = value;
	}
	
	public long getLastDungBossKill() {
		return lastDungBossKill;
	}
	
	private Random random;
	public Random getRandom() {
	    return random;
	}
	public ArrayList<Integer> getLockedSkills() {
		return lockedSkills;
	}
	
	public boolean isLocked(int id) {
		return lockedSkills.contains(id);
	}
	
	public void lockSkills(int id) {
		if (isLocked(id)) {
			sendMessage("Your "+Skills.SKILLS[id]+" is already locked!");
			return;
		}
		lockedSkills.add(id);
		getPackets().sendGameMessage("You have <col=FF0000>LOCKED</col> your "+Skills.SKILLS[id]+" xp.");

	}
	
	public void unlockSkills(int id) {
		if (!isLocked(id)) {
			sendMessage("Your "+Skills.SKILLS[id]+" is not locked!");
			return;
		}
		for (int i = 0; i < lockedSkills.size(); i++) {
			if (lockedSkills.get(i) == id) {
				lockedSkills.remove(i);
				getPackets().sendGameMessage("You have <col=00FF00>UNLOCKED</col> your "+Skills.SKILLS[id]+" xp.");
				return;
			}
		}
	}
	
	public boolean isTitleUnlocked(int id) {
		if (id >= 66 && id <= 69)
			return true;
		if (id == 71 && getPrestigeLevel() == 1)
			return true;
		if (id == 72 && getPrestigeLevel() == 2)
			return true;
		if (id == 73 && getPrestigeLevel() == 3)
			return true;
		if (id == 74 && getPrestigeLevel() == 4)
			return true;
		if (id == 75 && getPrestigeLevel() == 5)
			return true;
		if (id == 76 && getPrestigeLevel() == 6)
			return true;
		return unlockedTitles.contains(id);
	}
	
	public boolean isInTitleList;
	
	public boolean isArmourUnlocked(int id) {
		return unlockedOutfits.contains(id);
	}
	
	public void unlockTitle(int id) {
		if (!isTitleUnlocked(id) && id != -1) {
			unlockedTitles.add(id);
			sendMessage("You've unlocked a new Title! "+TitleHandler.getTitle(this, id)+" (Id: "+id+")");
			World.sendWorldMessage("<img=7><col=FF0000>[News] "+getDisplayName()+" has unlocked the title: "+TitleHandler.getTitle(this, id)+"", false);
		}
	}
	
	public void unlockArmour(int id) {
		if (!isArmourUnlocked(id) && id != -1) {
			unlockedOutfits.add(id);
			sendMessage("You've unlocked the "+Utils.formatString(Armour.getArmour(id).name())+" override!");
			World.sendWorldMessage("<img=7><col=FF0000>[News] "+getDisplayName()+" has unlocked "+Utils.formatString(Armour.getArmour(id).name())+"", false);
		}
	}

	public long getNpcKills() {
		return npcKills;
	}
	
	public void setNpcKills(long i) {
		this.npcKills = i;
	}
	
	public long getSkillPoints() {
		return skillPoints;
	}
	
	public void setSkillPoints(long value) {
		this.skillPoints = value;
	}
	
	public SquealOfFortune getSqueal() {
		return squeal;
	}
	
	private void refreshLodestoneNetwork() {
		getPackets().sendConfigByFile(358, 15);
		getPackets().sendConfigByFile(2448, 190);
		getPackets().sendConfigByFile(10900, 1);
		getPackets().sendConfigByFile(10901, 1);
		getPackets().sendConfigByFile(10902, 1);
		getPackets().sendConfigByFile(10903, 1);
		getPackets().sendConfigByFile(10904, 1);
		getPackets().sendConfigByFile(10905, 1);
		getPackets().sendConfigByFile(10906, 1);
		getPackets().sendConfigByFile(10907, 1);
		getPackets().sendConfigByFile(10908, 1);
		getPackets().sendConfigByFile(10909, 1);
		getPackets().sendConfigByFile(10910, 1);
		getPackets().sendConfigByFile(10911, 1);
		getPackets().sendConfigByFile(10912, 1);
	}
	
	public static int boxWon = -1;
	
	public int Rewards;
	
	public int isspining;
	
	public boolean hasItem(int id, int amt) {
		Item item = new Item(id, amt);
		if (getInventory().containsItem(id, amt))
			return true;
		if (getEquipment().getItems().contains(item))
			return true;
		if (getBank().hasItem(item)) 
			return true;
		return false;
	}
	
	public void resetLevels() {
		for (int i = 0; i < 7; i++) {
			if (i != 3) {
				getSkills().set(i, 1);
				getSkills().setXp(i, Skills.getXPForLevel(1));
				getAppearence().generateAppearenceData();
			}
		}
		getSkills().set(3, 10);
		getSkills().setXp(3, Skills.getXPForLevel(10));
	}
	
	public void resetAllLevels() {
		for (int i = 0; i < 25; i++) {
			if (i != 3) {
				getSkills().set(i, 1);
				getSkills().setXp(i, Skills.getXPForLevel(1));
				getAppearence().generateAppearenceData();
			}
		}
		getSkills().set(3, 10);
		getSkills().setXp(3, Skills.getXPForLevel(10));
	}

	public boolean hasVoted() {
		if (lastVoted > Utils.currentTimeMillis()) {
			getPackets().sendGameMessage("You have already claimed your reward! <col=FF0000>Time Left: "+Utils.getTimeRemaining(lastVoted)+"</col>.");
			return true;
		}
	return false;
	}
	
	public boolean hasUpdatedHs() {
		if (lastHsUpdate > Utils.currentTimeMillis()) {
			return true;
		}
		return false;
	}
	
	public long getLastVote() {
		return lastVoted;
	}
	public void setLastVote(long time) {
		this.lastVoted = time;
	}
	public int getDungKillCount() {
		return dungKillCount;
	}
	public void setDungKillCount(int i) {
		this.dungKillCount = i;
	}
	public void setExpertDungKills(int i) {
		this.expertDungKills = i;
	}
	public int getExpertDungKills() {
		return expertDungKills;
	}
	
	public void signUp() {
		getInterfaceManager().sendInterface(560);
		getPackets().writeString(560, 14, "<br><br>Please goto your client settings and make<br><br>sure you're on <col=00FF00>OpenGL</col> and not <col=FF0000>SafeMode</col>.<br><br> <col=FF0000><u=FF0000>IF NOT YOU WILL CRASH!<br><br>~King Fox");
		getPackets().writeString(560, 15, "");
		getPackets().writeString(560, 16, "Click Here to See How");
	}
	
	public boolean purchase(int amount) {
		if (getLoyaltyPoints() >= amount) {
			setLoyaltyPoints(getLoyaltyPoints() - amount);
			return true;
		} else {
			getPackets().sendGameMessage("You do not have enough points!");
			return false;
		}
	}
	
	public Armour getArmour(int slot) {
		if (this.armour == null)
			this.armour = new Armour[15];
		return armour[slot];
	}
	
	public void setArmour(Armour[] armour) {
		this.armour = armour;
	}
	
	public Armour[] getArmour() {
		return armour;
	}
	
	public void setArmour(Armour armour, int slot) {
		if (this.armour == null)
			this.armour = new Armour[15];
		this.armour[slot] = armour;
	}
	public int getOsp() {
		return onslaughtPoints;
	}
	public void setOsp(int amount) {
		this.onslaughtPoints = amount;
	}
	public PlayerGoals getGoals() {
		return achs;
	}
	public int getCustomCrown() {
		return customCrown;
	}
	public void setCustomCrown(int customCrown) {
		this.customCrown = customCrown;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int value) {
		this.difficulty = value;
	}
	public boolean allowChange() {
		return allowChange;
	}
	public void setAllowChange(boolean value) {
		this.allowChange = value;
	}
	public long getLastCorrectTrivia() {
		return lastAnswered;
	}
	public void setLastAnswer(long time) {
		this.lastAnswered = time;
	}
	public int getCorrectAnswers() {
		return correctAnswers;
	}
	public void setCorrectAnswers(int amount) {
		this.correctAnswers = amount;
	}
	public void setDisableTrivia(boolean value) {
		this.disabledTrivia = value;
	}
	public boolean hasDisabledTrivia() {
		return disabledTrivia;
	}
	public int getMaxZombieWave() {
		return maxZombieWave;
	}
	public void setMaxZombieWave(int value) {
		this.maxZombieWave = value;
	}
	public int getZombieKillstreak() {
		return zombieKillStreak;
	}
	public void setZombieKillstreak(int value) {
		this.zombieKillStreak = value;
	}
	public int getCasketsOpened() {
		return casketsOpened;
	}
	public void setCasketsOpened(int casketsOpened) {
		this.casketsOpened = casketsOpened;
	}
}
