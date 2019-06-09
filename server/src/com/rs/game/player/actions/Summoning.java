package com.rs.game.player.actions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.minigames.clanwars.ClanWars;
import com.rs.game.minigames.clanwars.ClanWars.Rules;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.npc.NPC;
import com.rs.game.player.actions.SummoningPouch;
import com.rs.game.npc.others.DreadNip;

@SuppressWarnings("serial")
public class Summoning implements Serializable {

	public static final class Pouches implements Serializable {

		public static Pouches forId(int id) {
			return (Pouches) pouches.get(Integer.valueOf(id));
		}

		public int getScrollId() {
			return scrollId;
		}

		public int getNpcId() {
			return npcId;
		}

		public int getConfigId() {
			return configId;
		}

		public int getPouchId() {
			return pouchId;
		}

		public int getLevel() {
			return level;
		}

		public double getUseExp() {
			return useExp;
		}

		public double getCreationExp() {
			return creationExp;
		}

		public long getTime() {
			return time;
		}

		public int getSpawnCost() {
			return spawnCost;
		}

		public static Map<Integer, Pouches> getPouches() {
			return pouches;
		}

		public static Pouches[] values() {
			Pouches apouches[];
			int i;
			Pouches apouches1[];
			System.arraycopy(apouches = ENUM$VALUES, 0,
					apouches1 = new Pouches[i = apouches.length], 0, i);
			return apouches1;
		}

		/**
		 * @param pouches
		 * 
		 */
		public static Pouches valueOf(String s) {
			return null;// (Pouches)Enum.valueOf(Summoning$Pouches, s);
		}

		public static final Pouches SPIRIT_WOLF;
		public static final Pouches DREADFOWL;
		public static final Pouches SPIRIT_SPIDER;
		public static final Pouches THORNY_SNAIL;
		public static final Pouches GRANITE_CRAB;
		public static final Pouches SPIRIT_MOSQUITO;
		public static final Pouches DESERT_WYRM;
		public static final Pouches SPIRIT_SCORPIAN;
		public static final Pouches SPIRIT_TZ_KIH;
		public static final Pouches ALBINO_RAT;
		public static final Pouches SPIRIT_KALPHITE;
		public static final Pouches COMPOST_MOUNT;
		public static final Pouches GIANT_CHINCHOMPA;
		public static final Pouches VAMPYRE_BAT;
		public static final Pouches HONEY_BADGER;
		public static final Pouches BEAVER;
		public static final Pouches VOID_RAVAGER;
		public static final Pouches VOID_SPINNER;
		public static final Pouches VOID_TORCHER;
		public static final Pouches VOID_SHIFTER;
		public static final Pouches BRONZE_MINOTAUR;
		public static final Pouches BULL_ANT;
		public static final Pouches MACAW;
		public static final Pouches EVIL_TURNIP;
		public static final Pouches SPIRIT_COCKATRICE;
		public static final Pouches SPIRIT_GUTHATRICE;
		public static final Pouches SPIRIT_SARATRICE;
		public static final Pouches SPIRIT_ZAMATRICE;
		public static final Pouches SPIRIT_PENGATRICE;
		public static final Pouches SPIRIT_CORAXATRICE;
		public static final Pouches SPIRIT_VULATRICE;
		public static final Pouches IRON_MINOTAUR;
		public static final Pouches PYRELORD;
		public static final Pouches MAGPIE;
		public static final Pouches BLOATED_LEECH;
		public static final Pouches SPIRIT_TERRORBIRD;
		public static final Pouches ABYSSAL_PARASITE;
		public static final Pouches SPIRIT_JELLY;
		public static final Pouches STEEL_MINOTAUR;
		public static final Pouches IBIS;
		public static final Pouches SPIRIT_KYATT;
		public static final Pouches SPIRIT_LARUPIA;
		public static final Pouches SPIRIT_GRAAHK;
		public static final Pouches KARAMTHULU_OVERLOAD;
		public static final Pouches SMOKE_DEVIL;
		public static final Pouches ABYSSAL_LURKER;
		public static final Pouches SPIRIT_COBRA;
		public static final Pouches STRANGER_PLANT;
		public static final Pouches MITHRIL_MINOTAUR;
		public static final Pouches BARKER_TOAD;
		public static final Pouches WAR_TORTOISE;
		public static final Pouches BUNYIP;
		public static final Pouches FRUIT_BAT;
		public static final Pouches RAVENOUS_LOCUST;
		public static final Pouches ARCTIC_BEAR;
		public static final Pouches PHEONIX;
		public static final Pouches OBSIDIAN_GOLEM;
		public static final Pouches GRANITE_LOBSTER;
		public static final Pouches PRAYING_MANTIS;
		public static final Pouches FORGE_REGENT;
		public static final Pouches ADAMANT_MINOTAUR;
		public static final Pouches TALON_BEAST;
		public static final Pouches GIANT_ENT;
		public static final Pouches FIRE_TITAN;
		public static final Pouches MOSS_TITAN;
		public static final Pouches ICE_TITAN;
		public static final Pouches HYDRA;
		public static final Pouches SPIRIT_DAGANNOTH;
		public static final Pouches LAVA_TITAN;
		public static final Pouches SWAMP_TITAN;
		public static final Pouches RUNE_MINOTAUR;
		public static final Pouches UNICORN_STALLION;
		public static final Pouches GEYSER_TITAN;
		public static final Pouches WOLPERTINGER;
		public static final Pouches ABYSSAL_TITAN;
		public static final Pouches IRON_TITAN;
		public static final Pouches PACK_YAK;
		public static final Pouches STEEL_TITAN;
		public static final Pouches TZREKJAD;
		public static final Pouches BABY_TROLL;
		public static final Pouches REDDRAGON;
		public static final Pouches BLUEDRAGON;
		public static final Pouches GREENDRAGON;
		public static final Pouches BLACKDRAGON;
		public static final Pouches WILYHELLCAT;
		public static final Pouches HELLCAT;
		private static final Map<Integer, Pouches> pouches;
		private int npcId;
		private int pouchId;
		private int level;
		private int spawnCost;
		private double useExp;
		private double creationExp;
		private int configId;
		private long time;
		private int scrollId;
		private static final Pouches ENUM$VALUES[];

		static {
			SPIRIT_WOLF = new Pouches("SPIRIT_WOLF", 0, 6830, 67, 12047, 1,
					0.10000000000000001D, 4.7999999999999998D, 0x57e40L, 1,
					12425);
			DREADFOWL = new Pouches("DREADFOWL", 1, 6825, 69, 12043, 4,
					0.10000000000000001D, 9.3000000000000007D, 0x3a980L, 1,
					12445);
			SPIRIT_SPIDER = new Pouches("SPIRIT_SPIDER", 2, 6841, 83, 12059, 8,
					0.20000000000000001D, 12.6D, 0xdbba0L, 2, 12428);
			THORNY_SNAIL = new Pouches("THORNY_SNAIL", 3, 6807, 119, 12019, 13,
					0.20000000000000001D, 12.6D, 0xea600L, 2, 12459);
			GRANITE_CRAB = new Pouches("GRANITE_CRAB", 4, 6796, 75, 12009, 16,
					0.20000000000000001D, 21.600000000000001D, 0x107ac0L, 2,
					12533);
			SPIRIT_MOSQUITO = new Pouches("SPIRIT_MOSQUITO", 5, 7332, 177,
					12778, 17, 0.20000000000000001D, 46.5D, 0xafc80L, 2, 12838);
			DESERT_WYRM = new Pouches("DESERT_WYRM", 6, 6832, 121, 12049, 18,
					0.40000000000000002D, 31.199999999999999D, 0x116520L, 1,
					12460);
			SPIRIT_SCORPIAN = new Pouches("SPIRIT_SCORPIAN", 7, 6838, 101,
					12055, 19, 0.90000000000000002D, 83.200000000000003D,
					0xf9060L, 2, 12432);
			SPIRIT_TZ_KIH = new Pouches("SPIRIT_TZ_KIH", 8, 7362, 179, 12808,
					22, 1.1000000000000001D, 96.799999999999997D, 0x107ac0L, 3,
					12839);
			ALBINO_RAT = new Pouches("ALBINO_RAT", 9, 6848, 103, 12067, 23,
					2.2999999999999998D, 202.40000000000001D, 0x142440L, 3,
					12430);
			SPIRIT_KALPHITE = new Pouches("SPIRIT_KALPHITE", 10, 6995, 99,
					12063, 25, 2.5D, 220D, 0x142440L, 3, 12446);
			COMPOST_MOUNT = new Pouches("COMPOST_MOUNT", 11, 6872, 137, 12091,
					28, 0.59999999999999998D, 49.799999999999997D, 0x15f900L,
					6, 12440);
			GIANT_CHINCHOMPA = new Pouches("GIANT_CHINCHOMPA", 12, 7354, 165,
					12800, 29, 2.5D, 255.19999999999999D, 0x1c61a0L, 1, 12834);
			VAMPYRE_BAT = new Pouches("VAMPYRE_BAT", 13, 6836, 71, 12053, 31,
					1.6000000000000001D, 136D, 0x1e3660L, 4, 12447);
			HONEY_BADGER = new Pouches("HONEY_BADGER", 14, 6846, 105, 12065,
					32, 1.6000000000000001D, 140.80000000000001D, 0x16e360L, 4,
					12433);
			BEAVER = new Pouches("BEAVER", 15, 6808, 89, 12021, 33,
					0.69999999999999996D, 57.600000000000001D, 0x18b820L, 4,
					12429);
			VOID_RAVAGER = new Pouches("VOID_RAVAGER", 16, 7371, 157, 12818,
					34, 0.69999999999999996D, 59.600000000000001D, 0x18b820L,
					4, 12443);
			VOID_SPINNER = new Pouches("VOID_SPINNER", 17, 7334, 157, 12780,
					34, 0.69999999999999996D, 59.600000000000001D, 0x18b820L,
					4, 12443);
			VOID_TORCHER = new Pouches("VOID_TORCHER", 18, 7352, 157, 12798,
					34, 0.69999999999999996D, 59.600000000000001D, 0x560f40L,
					4, 12443);
			VOID_SHIFTER = new Pouches("VOID_SHIFTER", 19, 7369, 157, 12814,
					34, 0.69999999999999996D, 59.600000000000001D, 0x560f40L,
					4, 12443);
			BRONZE_MINOTAUR = new Pouches("BRONZE_MINOTAUR", 20, 6854, 149,
					12073, 36, 2.3999999999999999D, 316.80000000000001D,
					0x1b7740L, 9, 12461);
			BULL_ANT = new Pouches("BULL_ANT", 21, 6969, 91, 12087, 40,
					0.59999999999999998D, 52.799999999999997D, 0x1b7740L, 5,
					12431);
			MACAW = new Pouches("MACAW", 22, 6852, 73, 12071, 41,
					0.80000000000000004D, 72.400000000000006D, 0x1c61a0L, 5,
					12422);
			EVIL_TURNIP = new Pouches("EVIL_TURNIP", 23, 6834, 77, 12051, 42,
					2.1000000000000001D, 184.80000000000001D, 0x1b7740L, 5,
					12448);
			SPIRIT_COCKATRICE = new Pouches("SPIRIT_COCKATRICE", 24, 6876, 149,
					12095, 43, 0.90000000000000002D, 75.200000000000003D,
					0x20f580L, 5, 12458);
			SPIRIT_GUTHATRICE = new Pouches("SPIRIT_GUTHATRICE", 25, 6878, 149,
					12097, 43, 0.90000000000000002D, 75.200000000000003D,
					0x20f580L, 5, 12458);
			SPIRIT_SARATRICE = new Pouches("SPIRIT_SARATRICE", 26, 6880, 149,
					12099, 43, 0.90000000000000002D, 75.200000000000003D,
					0x20f580L, 5, 12458);
			SPIRIT_ZAMATRICE = new Pouches("SPIRIT_ZAMATRICE", 27, 6882, 149,
					12101, 43, 0.90000000000000002D, 75.200000000000003D,
					0x20f580L, 5, 12458);
			SPIRIT_PENGATRICE = new Pouches("SPIRIT_PENGATRICE", 28, 6884, 149,
					12103, 43, 0.90000000000000002D, 75.200000000000003D,
					0x20f580L, 5, 12458);
			SPIRIT_CORAXATRICE = new Pouches("SPIRIT_CORAXATRICE", 29, 6886,
					149, 12105, 43, 0.90000000000000002D, 75.200000000000003D,
					0x20f580L, 5, 12458);
			SPIRIT_VULATRICE = new Pouches("SPIRIT_VULATRICE", 30, 6888, 149,
					12107, 43, 0.90000000000000002D, 75.200000000000003D,
					0x20f580L, 5, 12458);
			IRON_MINOTAUR = new Pouches("IRON_MINOTAUR", 31, 6856, 149, 12075,
					46, 4.5999999999999996D, 404.80000000000001D, 0x21dfe0L, 9,
					12462);
			PYRELORD = new Pouches("PYRELORD", 32, 7378, 185, 12816, 46,
					2.2999999999999998D, 202.40000000000001D, 0x1d4c00L, 5,
					12829);
			MAGPIE = new Pouches("MAGPIE", 33, 6824, 81, 12041, 47,
					0.90000000000000002D, 83.200000000000003D, 0x1f20c0L, 5,
					12426);
			BLOATED_LEECH = new Pouches("BLOATED_LEECH", 34, 6844, 131, 12061,
					49, 2.3999999999999999D, 215.19999999999999D, 0x1f20c0L, 5,
					12444);
			SPIRIT_TERRORBIRD = new Pouches("SPIRIT_TERRORBIRD", 35, 6795, 129,
					12007, 52, 0.69999999999999996D, 68.400000000000006D,
					0x20f580L, 6, 12441);
			ABYSSAL_PARASITE = new Pouches("ABYSSAL_PARASITE", 36, 6819, 125,
					12035, 54, 1.1000000000000001D, 94.799999999999997D,
					0x1b7740L, 6, 12454);
			SPIRIT_JELLY = new Pouches("SPIRIT_JELLY", 37, 6993, 123, 12027,
					55, 5.5D, 484D, 0x275e20L, 6, 12453);
			STEEL_MINOTAUR = new Pouches("STEEL_MINOTAUR", 38, 6858, 149,
					12077, 56, 5.5999999999999996D, 492.80000000000001D,
					0x2a1d40L, 9, 12463);
			IBIS = new Pouches("IBIS", 39, 6991, 85, 12531, 56,
					1.1000000000000001D, 98.799999999999997D, 0x22ca40L, 6,
					12424);
			SPIRIT_KYATT = new Pouches("SPIRIT_KYATT", 40, 7364, 169, 12812,
					57, 5.7000000000000002D, 501.60000000000002D, 0x2cdc60L, 6,
					12836);
			SPIRIT_LARUPIA = new Pouches("SPIRIT_LARUPIA", 41, 7366, 181,
					12784, 57, 5.7000000000000002D, 501.60000000000002D,
					0x2cdc60L, 6, 12840);
			SPIRIT_GRAAHK = new Pouches("SPIRIT_GRAAHK", 42, 7338, 167, 12810,
					57, 5.5999999999999996D, 501.60000000000002D, 0x2cdc60L, 6,
					12835);
			KARAMTHULU_OVERLOAD = new Pouches("KARAMTHULU_OVERLOAD", 43, 6810,
					135, 12023, 58, 5.7999999999999998D, 510.39999999999998D,
					0x284880L, 6, 12455);
			SMOKE_DEVIL = new Pouches("SMOKE_DEVIL", 44, 6866, 133, 12085, 61,
					3.1000000000000001D, 268D, 0x2bf200L, 7, 12468);
			ABYSSAL_LURKER = new Pouches("ABYSSAL_LURKER", 45, 6821, 87, 12037,
					62, 1.8999999999999999D, 109.59999999999999D, 0x258960L, 7,
					12427);
			SPIRIT_COBRA = new Pouches("SPIRIT_COBRA", 46, 6803, 115, 12015,
					63, 3.1000000000000001D, 276.80000000000001D, 0x334500L, 7,
					12436);
			STRANGER_PLANT = new Pouches("STRANGER_PLANT", 47, 6828, 141,
					12045, 64, 3.2000000000000002D, 281.60000000000002D,
					0x2cdc60L, 7, 12467);
			MITHRIL_MINOTAUR = new Pouches("MITHRIL_MINOTAUR", 48, 6860, 149,
					12079, 66, 6.5999999999999996D, 580.79999999999995D,
					0x325aa0L, 9, 12464);
			BARKER_TOAD = new Pouches("BARKER_TOAD", 49, 6890, 107, 12123, 66,
					1.0D, 87D, 0x75300L, 7, 12452);
			WAR_TORTOISE = new Pouches("WAR_TORTOISE", 50, 6816, 117, 12031,
					67, 0.69999999999999996D, 58.600000000000001D, 0x275e20L,
					7, 12439);
			BUNYIP = new Pouches("BUNYIP", 51, 6814, 153, 12029, 68,
					1.3999999999999999D, 119.2D, 0x284880L, 7, 12438);
			FRUIT_BAT = new Pouches("FRUIT_BAT", 52, 6817, 79, 12033, 69,
					1.3999999999999999D, 121.2D, 0x2932e0L, 7, 12423);
			RAVENOUS_LOCUST = new Pouches("RAVENOUS_LOCUST", 53, 7374, 97,
					12820, 70, 1.5D, 132D, 0x15f900L, 4, 12830);
			ARCTIC_BEAR = new Pouches("ARCTIC_BEAR", 54, 6840, 109, 12057, 71,
					1.1000000000000001D, 93.200000000000003D, 0x19a280L, 8,
					12451);
			PHEONIX = new Pouches("PHEONIX", 55, 8549, -1, 14623, 72, 3D, 301D,
					0x1b7740L, 8, -1);
			OBSIDIAN_GOLEM = new Pouches("OBSIDIAN_GOLEM", 56, 7346, 173,
					12792, 73, 7.2999999999999998D, 642.39999999999998D,
					0x325aa0L, 8, 12826);
			GRANITE_LOBSTER = new Pouches("GRANITE_LOBSTER", 57, 6850, 93,
					12069, 74, 3.7000000000000002D, 325.60000000000002D,
					0x2c8e40L, 8, 12449);
			PRAYING_MANTIS = new Pouches("PRAYING_MANTIS", 58, 6799, 95, 12011,
					75, 3.6000000000000001D, 329.60000000000002D, 0x3f2be0L, 8,
					12450);
			FORGE_REGENT = new Pouches("FORGE_REGENT", 59, 7336, 187, 12782,
					76, 1.5D, 134D, 0x2932e0L, 9, 12841);
			ADAMANT_MINOTAUR = new Pouches("ADAMANT_MINOTAUR", 60, 6862, 149,
					12081, 76, 8.5999999999999996D, 668.79999999999995D,
					0x3c6cc0L, 9, 12465);
			TALON_BEAST = new Pouches("TALON_BEAST", 61, 7348, 143, 12794, 77,
					3.7999999999999998D, 1015.2D, 0x2cdc60L, 9, 12831);
			GIANT_ENT = new Pouches("GIANT_ENT", 62, 6801, 139, 12013, 78,
					1.6000000000000001D, 136.80000000000001D, 0x2cdc60L, 8,
					12457);
			FIRE_TITAN = new Pouches("FIRE_TITAN", 63, 7356, 159, 12802, 79,
					7.9000000000000004D, 695.20000000000005D, 0x38c340L, 9,
					12824);
			MOSS_TITAN = new Pouches("MOSS_TITAN", 64, 7358, 159, 12804, 79,
					7.9000000000000004D, 695.20000000000005D, 0x38c340L, 9,
					12824);
			ICE_TITAN = new Pouches("ICE_TITAN", 65, 7360, 159, 12806, 79,
					7.9000000000000004D, 695.20000000000005D, 0x38c340L, 9,
					12824);
			HYDRA = new Pouches("HYDRA", 66, 6812, 145, 12025, 80,
					1.6000000000000001D, 140.80000000000001D, 0x2cdc60L, 8,
					12442);
			SPIRIT_DAGANNOTH = new Pouches("SPIRIT_DAGANNOTH", 67, 6805, 147,
					12017, 83, 4.0999999999999996D, 364.80000000000001D,
					0x342f60L, 9, 12456);
			LAVA_TITAN = new Pouches("LAVA_TITAN", 68, 7342, 171, 12788, 83,
					8.3000000000000007D, 730.39999999999998D, 0x37d8e0L, 9,
					12837);
			SWAMP_TITAN = new Pouches("SWAMP_TITAN", 69, 7330, 155, 12776, 85,
					4.2000000000000002D, 373.60000000000002D, 0x334500L, 9,
					12832);
			RUNE_MINOTAUR = new Pouches("RUNE_MINOTAUR", 70, 6864, 149, 12083,
					86, 8.5999999999999996D, 756.79999999999995D, 0x8a3ea0L, 9,
					12466);
			UNICORN_STALLION = new Pouches("UNICORN_STALLION", 71, 6823, 113,
					12039, 88, 1.8D, 154.40000000000001D, 0x317040L, 9, 12434);
			GEYSER_TITAN = new Pouches("GEYSER_TITAN", 72, 7340, 161, 12786,
					89, 8.9000000000000004D, 783.20000000000005D, 0x3f2be0L,
					10, 12833);
			WOLPERTINGER = new Pouches("WOLPERTINGER", 73, 6870, 151, 12089,
					92, 4.5999999999999996D, 404.80000000000001D, 0x38c340L,
					10, 12437);
			ABYSSAL_TITAN = new Pouches("ABYSSAL_TITAN", 74, 7350, 175, 12796,
					93, 1.8999999999999999D, 163.19999999999999D, 0x1d4c00L,
					10, 12827);
			IRON_TITAN = new Pouches("IRON_TITAN", 75, 7376, 183, 12822, 95,
					8.5999999999999996D, 417.60000000000002D, 0x36ee80L, 10,
					12828);
			PACK_YAK = new Pouches("PACK_YAK", 76, 6874, 111, 12093, 96,
					4.7999999999999998D, 422.19999999999999D, 0x3519c0L, 10,
					12435);
			STEEL_TITAN = new Pouches("STEEL_TITAN", 77, 7344, 163, 12790, 99,
					4.9000000000000004D, 435.19999999999999D, 0x3a9800L, 10,
					12825);
			TZREKJAD = new Pouches("TZREKJAD", 77, 3604, 163, 21512, 1,
					4.9000000000000004D, 435.19999999999999D, 0x3a9800L, 10,
					12825);
			BABY_TROLL = new Pouches("BABY_TROLL", 77, 3604, 163, 21512, 1,
					4.9000000000000004D, 435.19999999999999D, 0x3a9800L, 0,
					12825);
			REDDRAGON = new Pouches("REDDRAGON", 77, 6901, 163, 12470, 1,
					4.9000000000000004D, 435.19999999999999D, 0x3a9800L, 10,
					12825);
			BLUEDRAGON = new Pouches("BLUEDRAGON", 77, 6902, 163, 12472, 1,
					4.9000000000000004D, 435.19999999999999D, 0x3a9800L, 10,
					12825);
			GREENDRAGON = new Pouches("GREENDRAGON", 77, 6905, 163, 12474, 1,
					4.9000000000000004D, 435.19999999999999D, 0x3a9800L, 10,
					12825);
			BLACKDRAGON = new Pouches("BLACKDRAGON", 77, 6907, 163, 12476, 1,
					4.9000000000000004D, 435.19999999999999D, 0x3a9800L, 10,
					12825);
			WILYHELLCAT = new Pouches("WILYHELLCAT", 77, 9304, 163, 7585, 1,
					4.9000000000000004D, 435.19999999999999D, 0x3a9800L, 10,
					12825);

			HELLCAT = new Pouches("HELLCAT", 77, 9295, 163, 7581, 1,
					4.9000000000000004D, 435.19999999999999D, 0x3a9800L, 10,
					12825);

			ENUM$VALUES = (new Pouches[] { SPIRIT_WOLF, DREADFOWL,
					SPIRIT_SPIDER, THORNY_SNAIL, GRANITE_CRAB, SPIRIT_MOSQUITO,
					DESERT_WYRM, SPIRIT_SCORPIAN, SPIRIT_TZ_KIH, ALBINO_RAT,
					SPIRIT_KALPHITE, COMPOST_MOUNT, GIANT_CHINCHOMPA,
					VAMPYRE_BAT, HONEY_BADGER, BEAVER, VOID_RAVAGER,
					VOID_SPINNER, VOID_TORCHER, VOID_SHIFTER, BRONZE_MINOTAUR,
					BULL_ANT, MACAW, EVIL_TURNIP, SPIRIT_COCKATRICE,
					SPIRIT_GUTHATRICE, SPIRIT_SARATRICE, SPIRIT_ZAMATRICE,
					SPIRIT_PENGATRICE, SPIRIT_CORAXATRICE, SPIRIT_VULATRICE,
					IRON_MINOTAUR, PYRELORD, MAGPIE, BLOATED_LEECH,
					SPIRIT_TERRORBIRD, ABYSSAL_PARASITE, SPIRIT_JELLY,
					STEEL_MINOTAUR, IBIS, SPIRIT_KYATT, SPIRIT_LARUPIA,
					SPIRIT_GRAAHK, KARAMTHULU_OVERLOAD, SMOKE_DEVIL,
					ABYSSAL_LURKER, SPIRIT_COBRA, STRANGER_PLANT,
					MITHRIL_MINOTAUR, BARKER_TOAD, WAR_TORTOISE, BUNYIP,
					FRUIT_BAT, RAVENOUS_LOCUST, ARCTIC_BEAR, PHEONIX,
					OBSIDIAN_GOLEM, GRANITE_LOBSTER, PRAYING_MANTIS,
					FORGE_REGENT, ADAMANT_MINOTAUR, TALON_BEAST, GIANT_ENT,
					FIRE_TITAN, MOSS_TITAN, ICE_TITAN, HYDRA, SPIRIT_DAGANNOTH,
					LAVA_TITAN, SWAMP_TITAN, RUNE_MINOTAUR, UNICORN_STALLION,
					GEYSER_TITAN, WOLPERTINGER, ABYSSAL_TITAN, IRON_TITAN,
					PACK_YAK, STEEL_TITAN, TZREKJAD, BABY_TROLL, REDDRAGON,
					BLUEDRAGON, GREENDRAGON, BLACKDRAGON, WILYHELLCAT, HELLCAT });
			pouches = new HashMap<Integer, Pouches>();
			Pouches apouches[];
			int j = (apouches = values()).length;
			for (int i = 0; i < j; i++) {
				Pouches pouch = apouches[i];
				pouches.put(Integer.valueOf(pouch.pouchId), pouch);
			}

		}

		private Pouches(String s, int i, int npcId, int configId, int pouchId,
				int level, double useExp, double creationExp, long time,
				int spawnCost, int scrollId) {
			super();
			this.npcId = npcId;
			this.pouchId = pouchId;
			this.level = level;
			this.spawnCost = spawnCost;
			this.useExp = useExp;
			this.creationExp = creationExp;
			this.time = time;
			this.scrollId = scrollId;
		}
	}

	public Summoning(Pouches pouches) {
		setPouches(pouches);
	}

	public static void sendInterface(Player player) {
		player.getInterfaceManager().sendInterface(INTERFACE);
		Object options[] = { Integer.valueOf(78), Integer.valueOf(1),
				"Infuse-All<col=FF9040>", "Infuse-10<col=FF9040>",
				"Infuse-5<col=FF9040>", "Infuse<col=FF9040>",
				Integer.valueOf(10), Integer.valueOf(8),
				Integer.valueOf(INTERFACE << 16 | 0x10) };
		player.getPackets().sendRunScript(757, options);
		player.getPackets().sendIComponentSettings(INTERFACE, 16, 0, 462, 190);
	}

	public static Familiar createFamiliar(Player player, Pouches pouch) {
		String loc = "com.rs.game.npc.familiar.";
		try {
			Familiar fam = 
			(Familiar) Class.forName(loc + NPCDefinitions.getNPCDefinitions(pouch.getNpcId()).name.replace(" ", "").replace("-", ""))
					.getConstructor(new Class[] { Player.class, Pouches.class, WorldTile.class, Integer.TYPE, Boolean.TYPE })
					.newInstance(new Object[] { player, pouch, player, Integer.valueOf(-1), Boolean.valueOf(true) });
			if (fam != null) {
				return fam;
			}
		} catch (Throwable e) {
			//e.printStackTrace();
		}
		return null;
	}

	public static void spawnFamiliar(Player player, Pouches pouch) {
		if (player.getFamiliar() != null || player.getPet() != null) {
			player.getPackets().sendGameMessage("You already have a follower.");
			return;
		}
		if (!player.getControlerManager().canSummonFamiliar())
			return;
		ItemDefinitions def = ItemDefinitions.getItemDefinitions(pouch
				.getPouchId());
		if (def == null)
			return;
		HashMap<Integer, Integer> skillReq = def.getWearingSkillRequiriments();
		boolean hasRequiriments = true;
		if (skillReq != null) {
			for (int skillId : skillReq.keySet()) {
				if (skillId > 24 || skillId < 0)
					continue;
				int level = skillReq.get(skillId);
				if (level < 0 || level > 120)
					continue;
				if (player.getSkills().getLevelForXp(skillId) < level) {
					if (hasRequiriments)
						player.getPackets()
								.sendGameMessage(
										"You are not high enough level to use this pouch.");
					hasRequiriments = false;
					String name = Skills.SKILLS[skillId].toLowerCase();
					player.getPackets().sendGameMessage(
							"You need to have a"
									+ (name.startsWith("a") ? "n" : "") + " "
									+ name + " level of " + level
									+ " to summon this.");
				}
			}
		}
		if (!hasRequiriments
				|| player.getSkills().getLevel(Skills.SUMMONING) < pouch
						.getSpawnCost())
			return;
		if (player.getCurrentFriendChat() != null) {
			ClanWars war = player.getCurrentFriendChat().getClanWars();
			if (war != null) {
				if (war.get(Rules.NO_FAMILIARS)
						&& (war.getFirstPlayers().contains(player) || war
								.getSecondPlayers().contains(player))) {
					player.getPackets().sendGameMessage(
							"You can't summon familiars during this war.");
					return;
				}
			}
		}
		final Familiar npc = createFamiliar(player, pouch);
		if (npc == null) {
			player.getPackets().sendGameMessage(
					"This familiar is not added yet.");
			return;
		}
		player.getInventory().deleteItem(pouch.getPouchId(), 1);
		player.getSkills().drainSummoning(pouch.getSpawnCost());
		player.setFamiliar(npc);
	}

	public static int amountCanCreate(final Player player, int itemId,
			int amount) {
		SummoningPouch pouch = SummoningPouch.get(itemId);
		return player.getInventory().getItems()
				.getNumberOf(pouch.getItems()[2]);
	}

	public static boolean hasPouch(Player player) {
		for (Pouches pouch : Pouches.values())
			if (player.getInventory().containsOneItem(pouch.pouchId))
				return true;
		return false;
	}

	public static void createPouch(final Player player, int itemId, int amount) {
		SummoningPouch pouch = SummoningPouch.get(itemId);
		if (pouch == null) {
			player.getPackets().sendGameMessage(
					"You do not have the items required to create this pouch.");
			return;
		}
		int amountToCreate = amountCanCreate(player, itemId, amount);
		if (amount > amountToCreate) {
			amount = player.getInventory().getItems().getNumberOf(itemId);
		}
		if (amount > 28) {
			amount = 1;
		}
		player.getInterfaceManager().closeScreenInterface();
		boolean end = false;
		int i = 0;
		for (i = 0; i < amount; i++) {
			for (Item item : pouch.getItems()) {
				if (!player.getInventory().getItems().contains(item)) {
					if (amount == 1) {
						player.getPackets().sendGameMessage("You do not have the items required to create this pouch.");
					}
					end = true;
					break;
				}
			}
			if (end) {
				break;
			}
			double multiplier = 3;
			player.getGoals().increase(Skills.SUMMONING);
			player.getInventory().removeItems(pouch.getItems());
			player.getInventory().addItem(pouch.getPouchId(), 1);
			player.getSkills().addXp(Skills.SUMMONING, (pouch.getSummonExperience() * amount) * multiplier);
			player.getPackets().sendObjectAnimation(new WorldObject(28716, 10, 1, 2209, 5344, 0), new Animation(8509));
		}
		if (i == 1) {
			player.getPackets().sendGameMessage("You infuse a " + ItemDefinitions.getItemDefinitions(itemId).getName().toLowerCase() + ".");
		} else if (i > 0) {
			player.getPackets().sendGameMessage("You infuse some " + ItemDefinitions.getItemDefinitions(itemId) .getName().toLowerCase() + "es.");
		} else {
			return;
		}
		player.setNextAnimation(new Animation(9068));
	}

	public static void attackDreadnipTarget(NPC target, Player player) {
		if (target.isDead()) {
			player.getPackets().sendGameMessage("Your target is already dead!");
			return;
		} else if (player.getAttributes().get("hasDN") != null) {
			player.getPackets().sendGameMessage("Your target is already dead!");
			return;
		} else if (player.getFamiliar() != null && target == player.getFamiliar()) {
			player.getPackets().sendGameMessage("");
			return;
		}
		closeDreadnipInterface(player);
		DreadNip npc = new DreadNip(player, 14416, player, -1, false);
		if (target == npc)
			return;
		player.getAttributes().put("hasDN", npc);
		npc.setTarget(target);
		npc.setNextAnimation(new Animation(14441));
	}

	public static void openDreadnipInterface(Player player) {
		player.getInterfaceManager().closeInventory();
		player.getInterfaceManager().sendTab(
				player.getInterfaceManager().hasRezizableScreen() ? 115 : 175,
				1165);
	}

	public static void closeDreadnipInterface(Player player) {
		player.getInterfaceManager().closeInventory();
		player.getInterfaceManager().sendInventory();
	}

	public Pouches getPouches() {
		return pouches;
	}

	public void setPouches(Pouches pouches) {
		this.pouches = pouches;
	}

	public static int INTERFACE = 672;
	private Pouches pouches;

	public static void infusePouches(Player player) {
		player.getInterfaceManager().sendInterface(INTERFACE);// close to this
		Object[] options = new Object[] { 78, 1, "List<col=FF9040>",
				"Infuse-X<col=FF9040>", "Infuse-All<col=FF9040>",
				"Infuse-10<col=FF9040>", "Infuse-5<col=FF9040>",
				"Infuse<col=FF9040>", 10, 8, INTERFACE << 16 | 16 };
		// to slot, from slot, options, width, height, component
		player.getPackets().sendRunScript(757, options);
		player.getPackets().sendIComponentSettings(INTERFACE, 16, 0, 430, 190);
	}
}