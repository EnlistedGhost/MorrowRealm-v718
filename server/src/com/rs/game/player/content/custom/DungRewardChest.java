package com.rs.game.player.content.custom;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Player;

public enum DungRewardChest {
	
	TEST_1(new WorldTile(2348, 9383, 0), 14515, new Item(995, 1000)),
	TEST_2(new WorldTile(2356, 9380, 0), 14516, new Item(995, 1000)),
	TEST_3(new WorldTile(2359, 9376, 0), 14517, new Item(995, 1000)),
	TEST_4(new WorldTile(2360, 9366, 0), 14518, new Item(995, 1000)),
	TEST_5(new WorldTile(2351, 9361, 0), 14515, new Item(995, 1000)),
	TEST_6(new WorldTile(2364, 9355, 0), 14516, new Item(995, 1000)),
	TEST_7(new WorldTile(2335, 9374, 0), 14517, new Item(995, 1000)),
	TEST_8(new WorldTile(2310, 9374, 0), 14518, new Item(995, 1000)),
	TEST_9(new WorldTile(2312, 9400, 0), 14515, new Item(995, 1000));
	
	WorldTile tile;
	int keyId;
	Item reward;
	
	DungRewardChest(WorldTile loc, int keyId, Item reward) {
		this.tile = loc;
		this.keyId = keyId;
		this.reward = reward;
	}
	
	public static void openChest(WorldObject object, Player player) {
		WorldTile chestLoc = new WorldTile(object.getX(), object.getY(), object.getPlane());
		for (DungRewardChest chest : DungRewardChest.values()) {
			if (chest.tile == chestLoc) {
				String keyName = ItemDefinitions.getItemDefinitions(chest.keyId).getName();
				if (!player.getInventory().containsItem(chest.keyId, 1)) {
					player.sendMessage("You must have the "+keyName+" to open this chest.");
					return;
				}
				player.getInventory().deleteItem(chest.keyId, 1);
				player.getInventory().addItem(chest.reward);
				return;
			}
		}
	}
}
