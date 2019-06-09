package com.rs.game.player.content;

import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;

public class ArtisanWorkshop {

	public static int INGOTWITH = 1072,
			IRONINGOTS = 20632,
			STEELINGOTS = 20504,
			MITHRILINGOTS = 20634,
			ADAMANTINGOTS = 20635,
			RUNEINGOTS = 20636;

	public static int[] ingots = { IRONINGOTS, STEELINGOTS, MITHRILINGOTS, ADAMANTINGOTS, RUNEINGOTS };

	public static int FULLINVENTORY = 28;

	public static void removeIngots(Player player, int ingot) {
		int amount = player.getInventory().getNumberOf(ingot);
		if (player.getInventory().containsItem(ingot, 1)) {
			player.getInventory().deleteItem(ingot, amount);
			player.getInventory().refresh();
			Item item = new Item(ingot, amount);
			player.sendMessage("You return "+item.getDefinitions().getName()+" ("+item.getAmount()+") to the machine.");
		}
	}

	public static void DepositIngots(Player player) {
		for (int i = 0; i < ingots.length; i++) {
			removeIngots(player, ingots[i]);
		}
	}

	public static int[] armor = {
		20572, 20572, 20577, 20582, 20587, 20597, 20602, 20607, 20612, 20617, 20622,
		20627, 20573, 20578, 20583, 20588, 20593, 20598, 20603, 20608, 20613, 20618,
		20623, 20628, 20574, 20579, 20584, 20589, 20594, 20599, 20604, 20609, 20614,
		20619, 20624, 20629, 20575, 20580, 20585, 20590, 20595, 20600, 20605, 20610,
		20615, 20620, 20625, 20630,	20576, 20581, 20586, 20591, 20596, 20601, 20606,
		20611, 20616, 20621, 20626, 20631
	};
	
	public static void DepositArmour(Player player) {		
		for (int i = 0; i < armor.length; i++) {
			if (player.getInventory().containsItem(armor[i], 1)) {
				int amount = player.getInventory().getNumberOf(armor[i]);
				player.getInventory().deleteItem(armor[i], amount);
			}
		}
		player.getInventory().refresh();
		player.setNextAnimation(new Animation(896));
		player.sm("You deposit all your armoury in Chute, long live dwarfs!");
	}
	
	public static void handleButtons(Player player, int componentId) {
		int freeSlots = player.getInventory().getItems().getFreeSlots();
		Item item = null;
		if (componentId == 201)	{
			item = new Item(IRONINGOTS, freeSlots);
		}
		if (componentId == 213)	{
			item = new Item(STEELINGOTS, freeSlots);
		}
		if (componentId == 225)	{
			item = new Item(MITHRILINGOTS, freeSlots);
		}
		if (componentId == 237)	{
			item = new Item(ADAMANTINGOTS, freeSlots);
		}
		if (componentId == 249)	{
			item = new Item(RUNEINGOTS, freeSlots);
		}
		if (item != null) {
			player.getInventory().addItem(item);
			player.sm("You take "+item.getDefinitions().getName()+" ("+item.getAmount()+") from the machine.");
			player.getInventory().refresh();
			player.closeInterfaces();
		}
	}
}
