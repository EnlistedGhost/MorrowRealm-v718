package com.rs.game.player.actions.woodcutting;

import com.rs.game.item.Item;
import com.rs.utils.Utils;

public class BirdNests {
	
	private static final int[] RING_LOOT = { 1635, 1637, 1639, 1641, 1643 };
	
	private static final int[] SEED_LOOT = { 5096, 5097, 5098, 5099, 5100, 5101, 5102, 5103, 5104, 5105, 5106,
		5283, 5284, 5285, 5286, 5287, 5288, 5289, 5290, 5291, 5292, 5293, 5294, 5295, 5296, 5297, 5298, 5299,
		5300, 5301, 5302, 5303, 5304};
	
	public enum Nests {
		
		RING_NEST(5074, new Item(RING_LOOT[Utils.random(RING_LOOT.length)], 1)),
		SEED_NEST(5073, new Item(SEED_LOOT[Utils.random(SEED_LOOT.length)], 1)),
		RED_EGG(5070, new Item(5076, 1)),
		BLUE_EGG(5072, new Item(5077, 1)),
		GREEN_EGG(5071, new Item(5078, 1)),
		RAVEN_EGG(11966, new Item(11964, 1));
		
		private Item item;
		private int nest;
		
		Nests(int nest, Item item) {
			this.nest = nest;
			this.item = item;
		}
		
		public int getNestId() {
			return nest;
		}
		
		public Item getItem() {
			return item;
		}
	}
}