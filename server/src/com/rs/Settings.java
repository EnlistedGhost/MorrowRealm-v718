package com.rs;

import java.math.BigInteger;

import com.rs.game.WorldTile;
import com.rs.utils.Utils;

public final class Settings {

	public static final String SERVER_NAME = "MorrowRealm v718";
	public static int PORT_ID = 43444;
	
	public static final String[] OWNERS = { "feraten" };
	public static boolean ZOMBIE_ENABLED = true;
	
	public static final WorldTile START_PLAYER_LOCATION = new WorldTile(3238, 3218, 0);
	public static final WorldTile RESPAWN_PLAYER_LOCATION = new WorldTile(3238, 3218, 0);

	public static final int droppedItemDelay = 180;
	public static final double SKILL_RING_MOD = 1.2;
	
	public static final boolean enableConsole = true;
	public static boolean enableSfs = false;
	
	public static final int CLIENT_BUILD = 718;
	public static final int CUSTOM_CLIENT_BUILD = 1;
	
	public static boolean DUNGEON_ENABLED = true;
	public static boolean CLEAN_HOME = false;
	public static boolean DISABLE_SQUEAL = false;
	public static boolean ENABLE_BUTTON_DEBUG = false;
	public static boolean ENABLE_PACKET_DEBUG = false;
	
	public static final String CACHE_PATH = "data/cache/";
	public static final int RECEIVE_DATA_LIMIT = 7500;
	public static final int PACKET_SIZE_LIMIT = 7500;
	
	public static final String 
		   WEBSITE = "http://morrowrealm.xyz/",
			 FORUM = "http://morrowrealm.xyz/forum/",
			DONATE = "http://morrowrealm.xyz/store.php",
			  WIKI = "http://morrowrealm.xyz/wiki/",
			  VOTE = "http://morrowrealm.xyz/vote/",
		HIGHSCORES = "http://morrowrealm.xyz/highscores.php",
		     SPINS = "http://morrowrealm.xyz/";
	
	public static String[] RARE_DROPS = {
		"pernix", 
		"torva", 
		"virtus", 
		"bandos", 
		"armadyl chestplate", 
		"armadyl chainskirt",
		"armadyl boots", 
		"armadyl gloves",
		"armadyl buckler",
		"hilt",
		"kalphite",
		"drygore",
		"hati",
		"korasi", 
		"divine", 
		"steadfast", 
		"glaiven", 
		"ragefire",
		"spirit shield",
		"arcane", 
		"chaotic", 
		"obliteration", 
		"annihilation", 
		"decimation",
		"frostmourne",
		"party hat"
	};
	
	public static boolean DEBUG = true;
	public static boolean HOSTED = true;
	public static final int MAX_STARTER_AMOUNT = 3;
	public static boolean MANAGMENT_SERVER_ENABLED = false;
	public static final String GUI_SIGN = "Artisticy GUI";
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	public static long cycleTime;
	
	public static void setCycleTime(long time) {
		cycleTime = time;
	}
	
	public static long getCycleTime() {
		return cycleTime;
	}
	
	public static final int START_PLAYER_HITPOINTS = 100;
	public static final long MAX_PACKETS_DECODER_PING_DELAY = 30000;
	public static final int DROP_RATE = 1;
	public static int LOGIN_LIMIT = 2;
	
	public static final int WORLD_CYCLE_TIME = 600; // the speed of world in ms
	public static final int AIR_GUITAR_MUSICS_COUNT = 50;
	public static final int QUESTS = 183;
	
	public static final String START_CONTROLER = "null";
	public static final int PLAYERS_LIMIT = 2000;
	public static final int LOCAL_PLAYERS_LIMIT = 250;
	public static final int NPCS_LIMIT = Short.MAX_VALUE;
	public static final int LOCAL_NPCS_LIMIT = 250;
	public static final int MIN_FREE_MEM_ALLOWED = 30000000; // 30mb

	/**
	 * Game constants
	 */
	public static final int[] MAP_SIZES = { 104, 120, 136, 168, 72 };

	public static final String GRAB_SERVER_TOKEN = "hAJWGrsaETglRjuwxMwnlA/d5W6EgYWx";
	public static final int[] GRAB_SERVER_KEYS = { 1441, 78700, 44880, 39771,
			363186, 44375, 0, 16140, 7316, 271148, 810710, 216189, 379672,
			454149, 933950, 21006, 25367, 17247, 1244, 1, 14856, 1494, 119,
			882901, 1818764, 3963, 3618 };

	// an exeption(grab server has his own keyset unlike rest of client)
	public static final BigInteger GRAB_SERVER_PRIVATE_EXPONENT = new BigInteger("95776340111155337321344029627634178888626101791582245228586750697996713454019354716577077577558156976177994479837760989691356438974879647293064177555518187567327659793331431421153203931914933858526857396428052266926507860603166705084302845740310178306001400777670591958466653637275131498866778592148380588481");
	public static final BigInteger GRAB_SERVER_MODULUS = new BigInteger("119555331260995530494627322191654816613155476612603817103079689925995402263457895890829148093414135342420807287820032417458428763496565605970163936696811485500553506743979521465489801746973392901885588777462023165252483988431877411021816445058706597607453280166045122971960003629860919338852061972113876035333");

	public static final BigInteger PRIVATE_EXPONENT = new BigInteger("90587072701551327129007891668787349509347630408215045082807628285770049664232156776755654198505412956586289981306433146503308411067358680117206732091608088418458220580479081111360656446804397560752455367862620370537461050334224448167071367743407184852057833323917170323302797356352672118595769338616589092625");
	public static final BigInteger MODULUS = new BigInteger("102876637271116124732338500663639643113504464789339249490399312659674772039314875904176809267475033772367707882873773291786014475222178654932442254125731622781524413208523465520758537060408541610254619166907142593731337618490879831401461945679478046811438574041131738117063340726565226753787565780501845348613");

	private Settings() {

	}
}
