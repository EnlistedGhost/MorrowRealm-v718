package com.rs.game.player.actions.farming.v1_5;

import com.rs.game.item.Item;

public class Seeds {
	
	/**
	 * @author Jake | Santa Hat @Rune-Server
	 */

	public enum Seed {

		/**
		* Allotments
		*/
		POTATO(new Item(5318, 4), new int[] { 6, 7, 8, 9, 10 }, 1, new Item(1942, 4), new int[] { 8553, 8552, 8550, 8551, 8554, 8555 }, 300, 25),
		ONION(new Item(5319, 4), new int[] { 13, 14, 15, 16, 17 }, 5, new Item(1957, 4), new int[] { 8553, 8552, 8550, 8551, 8554, 8555 }, 330, 55),
		CABBAGE(new Item(5324, 4), new int[] { 20, 21, 22, 23, 24 }, 7, new Item(1965, 4), new int[] { 8553, 8552, 8550, 8551, 8554, 8555 }, 360, 85),
		TOMATO(new Item(5322, 4), new int[] { 27, 28, 29, 30, 31 }, 12, new Item(1982, 4), new int[] { 8553, 8552, 8550, 8551, 8554, 8555 }, 420, 110),
		SWEETCORN(new Item(5320, 4), new int[] { 34, 35, 37, 39, 40 }, 20, new Item(5986, 4), new int[] { 8553, 8552, 8550, 8551, 8554, 8555 }, 480, 200),
		STRAWBERRY(new Item(5323, 4), new int[] { 43, 45, 46, 48, 49 }, 31, new Item(5504, 4), new int[] { 8553, 8552, 8550, 8551, 8554, 8555 }, 540, 301),
		WATERMELON(new Item(5321, 4), new int[] { 52, 53, 55, 59, 60 }, 47, new Item(5982, 4), new int[] { 8553, 8552, 8550, 8551, 8554, 8555 }, 600, 550),

		/**
		* Flower Patches
		*/
		MARIGOLD(new Item(5096, 1), new int[] { 8, 9, 10, 11, 12 }, 2, new Item(6010, 1), new int[] { 7848, 7847, 7849 }, 310, 30),
		ROSEMARY(new Item(5097, 1), new int[] { 13, 14, 15, 16, 17 }, 11, new Item(6014, 1), new int[] { 7848, 7847, 7849 }, 400, 45),
		NASTURTIUM(new Item(5098, 1), new int[] { 18, 19, 20, 21, 22 }, 24, new Item(6012, 1), new int[] { 7848, 7847, 7849 }, 480, 110),
		WOAD(new Item(5099, 1), new int[] { 23, 24, 25, 26, 27 }, 25, new Item(1793, 1), new int[] { 7848, 7847, 7849 }, 490, 120),
		LIMPWURT(new Item(5100, 1), new int[] { 28, 29, 30, 31, 32 }, 26, new Item(225, 1), new int[] { 7848, 7847, 7849 }, 500, 130),
		LILY(new Item(14589, 1), new int[] { 37, 38, 39, 40, 41 }, 52, new Item(14583, 1), new int[] { 7848, 7847, 7849 }, 700, 450),

		/**
		* Herb Patches
		*/
		GUAM(new Item(5291, 1), new int[] { 4, 5, 6, 7, 8 }, 9, new Item(199, 4), new int[] { 8151, 8150, 8152 }, 360, 50),
		MARRENTILL(new Item(5292, 1), new int[] { 11, 12, 13, 14, 15 }, 14, new Item(201, 4), new int[] { 8151, 8150, 8152 }, 420, 80),
		TARROMIN(new Item(5293, 1), new int[] { 18, 19, 20, 21, 22 }, 19, new Item(203, 4), new int[] { 8151, 8150, 8152 }, 480, 110),
		HARRALANDER(new Item(5294, 1), new int[] { 25, 26, 27, 28, 29 }, 26, new Item(205, 4), new int[] { 8151, 8150, 8152 }, 540, 150),
		RANARR(new Item(5295, 1), new int[] { 32, 33, 34, 35, 36 }, 32, new Item(207, 4), new int[] { 8151, 8150, 8152 }, 600, 190),
		TOADFLAX(new Item(5296, 1), new int[] { 39, 40, 41, 42, 43 }, 38, new Item(3049, 4), new int[] { 8151, 8150, 8152 }, 660, 225),
		IRIT(new Item(5297, 1), new int[] { 46, 47, 48, 49, 50 }, 44, new Item(209, 4), new int[] { 8151, 8150, 8152 }, 720, 270),
		AVANTOE(new Item(5298, 1), new int[] { 53, 54, 55, 56, 57 }, 50, new Item(211, 4), new int[] { 8151, 8150, 8152 }, 780, 300),
		WERGALI(new Item(14870, 1), new int[] { 60, 61, 62, 63, 64 }, 46, new Item(14836, 4), new int[] { 8151, 8150, 8152 }, 750, 285),
		KWUARM(new Item(5299, 1), new int[] { 69, 70, 71, 72, 73 }, 56, new Item(213, 4), new int[] { 8151, 8150, 8152 }, 820, 325),
		SNAPDRAGON(new Item(5300, 1), new int[] { 75, 76, 77, 78, 79 }, 62, new Item(3051, 4), new int[] { 8151, 8150, 8152 }, 880, 360),
		CADANTINE(new Item(5301, 1), new int[] { 82, 83, 84, 85, 86 }, 67, new Item(215, 4), new int[] { 8151, 8150, 8152 }, 920, 390),
		LANTADYME(new Item(5302, 1), new int[] { 89, 90, 91, 92, 93 }, 73, new Item(2485, 4), new int[] { 8151, 8150, 8152 }, 980, 420),
		DWARF_WEED(new Item(5303, 1), new int[] { 96, 97, 98, 99, 100 }, 79, new Item(217, 4), new int[] { 8151, 8150, 8152 }, 1020, 480),
		TORSTOL(new Item(5304, 1), new int[] { 103, 104, 105, 106, 107 }, 85, new Item(219, 4), new int[] { 8151, 8150, 8152 }, 1080, 520),
		FELLSTALK(new Item(21621, 1), new int[] { 106, 107, 108, 109, 110 }, 91, new Item(21626, 4), new int[] { 8151, 8150, 8152 }, 1120, 670);

		private Item item;
		private int[] configValues;
		private int level;
		private Item produce;
		private int[] suitablePatches;
		private int time; //Time to grow (In Seconds)
		private int exp;

		Seed(Item item, int[] configValues, int level, Item produce, int[] suitablePatches, int time, int exp) {
			this.item = item;
			this.configValues = configValues;
			this.level = level;
			this.produce = produce;
			this.suitablePatches = suitablePatches;
			this.time = time;
			this.exp = exp;
		}

		public Item getItem() {
			return item;
		}

		public int[] getConfigValues() {
			return configValues;
		}

		public int getLevel() {
			return level;
		}

		public int getSeedExp() {
			return exp;
		}

		public Item getProduce() {
			return produce;
		}

		public int[] getSuitablePatch() {
			return suitablePatches;
		}

		public int getTime() {
			return time;
		}
	}
}