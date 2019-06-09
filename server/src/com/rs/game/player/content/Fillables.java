package com.rs.game.player.content;

import com.rs.game.player.Player;

/**
 * 
 * @author JazzyYaYaYa | Fuzen Seth | Nexon
 *
 */
public class Fillables {
/*Bucket types*/
public static int EMPTY_BUCKET = 3727;
public static int BUCKET_OF_WAX = 30;
public static int BUCKET_OF_SAND = 1783;
public static int BUCKET_OF_MILK = 1927;
public static int BUCKET_OF_WATER = 1929;
public static int BUCKET_OF_SLIME = 4286;
public static int BUCKET_OF_SAP = 4687;
public static int BUCKET_OF_RUBBLE = 7622;
public static int BUCKET_OF_COAL = 12652;
public static int BUCKET_OF_ICY_WATER = 15427;
public static int BUCKET_OF_OIL = 20933;
public static int one = 1;

	public static void EmptyWaterBucket(Player player) {
	if (player.getInventory().containsItem(BUCKET_OF_WATER, one)) {
		player.getInventory().deleteItem(BUCKET_OF_WATER, one);
		player.getInventory().addItem(EMPTY_BUCKET, one);
		player.getInventory().refresh();
		player.sm("You empty the bucket.");
	}
	}
	public static void WaterBucketAction (Player player) {
		if (player.getInventory().containsItem(EMPTY_BUCKET, one)) {
			player.getInventory().addItem(BUCKET_OF_WATER, one);
			player.lock(2);
			player.getInventory().refresh();
		}	
	}
}
