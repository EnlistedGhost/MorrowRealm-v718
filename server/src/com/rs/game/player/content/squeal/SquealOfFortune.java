package com.rs.game.player.content.squeal;

 import java.io.Serializable;
import java.text.DecimalFormat;

 import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

 public class SquealOfFortune extends SquealSlotItems implements Serializable {
	private static final long serialVersionUID = -2063410653116131907L;
		
	public ItemsContainer<Item> items = new ItemsContainer<Item>(13, true);
	
	private transient Player player;
	private transient Item reward;
	private transient boolean spinning;

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void open() {
		if(player.getSpins() <= 0) {
			player.getPackets().sendGameMessage("You do not have enough spins to open Squeal of Fortune");
		} else if(player.getSpins() >= 0) {
			player.getPackets().sendConfigByFile(11026, player.getSpins() + 1);
			player.getPackets().sendRunScript(5879);
			player.getPackets().sendConfigByFile(11155, 3);
			player.getPackets().sendWindowsPane(1253, 0);
			sendItems();
		}
	}
	
	public void close() {
		this.spinning = false;
		this.reward = null;
		player.getPackets().sendWindowsPane(player.getInterfaceManager().hasRezizableScreen() ? 746 : 548, -1);
        player.closeInterfaces();
	}

	public void sendItems() {
		if (items == null)
			items = new ItemsContainer<Item>(13, true);
		items.clear();
		items.add(getRare(1)); // Rare
		items.add(getCommon(1)); // Common
		items.add(getSRare()); // Super-Rare
		items.add(getCommon(2)); // Common
		items.add(getRare(2)); // Rare
		items.add(getCommon(3)); // Common
		items.add(getUnCommon(1)); // UnCommon
		items.add(getCommon(4)); // Common
		items.add(getRare(3)); // Rare
		items.add(getUnCommon(2)); // UnCommon
		items.add(getCommon(5)); // Com
		items.add(getUnCommon(3)); // UnCom
		items.add(getCommon(6)); // Common
		player.getPackets().sendItems(665, false, items);
	}

	public void claimItem() {
		if (reward == null) {
			player.sendMessage("Sorry, there was an error! Please report it ASAP!");
			player.getPackets().sendWindowsPane(player.getInterfaceManager().hasRezizableScreen() ? 746 : 548, -1);
			return;
		}
		
		if (player.getInventory().hasFreeSlots()) {
			player.getInventory().add(reward.getId(), reward.getAmount());
			player.sendMessage("<col=FF0000>You're reward has been placed in your inventory.</col>");
		} else {
			player.getBank().addItem(reward.getId(), reward.getAmount(), true);
			player.sendMessage("<col=FF0000>You're reward has been placed in your bank.</col>");
		}
		
		player.getPackets().sendConfigByFile(11026, player.getSpins() + 1);
		player.getPackets().sendWindowsPane(player.getInterfaceManager().hasRezizableScreen() ? 746 : 548, -1);
		this.reward = null;
		this.spinning = false;
	}
	
	public void spin() {
		if (spinning == true) {
			return;
		}
		
		int rewardType = Utils.random(1, 1000);
		int slot = 0;
		int slotchange = Utils.getRandom(6);
		
		if (rewardType >= 1 && rewardType <= 925) { // Common Slots
			slot = slotchange == 1 ? 1 :
				   slotchange == 2 ? 3 :
				   slotchange == 3 ? 5 :
				   slotchange == 4 ? 7 :
				   slotchange == 5 ? 10 : 12;
		} else if (rewardType >= 926 && rewardType <= 989) { // Uncommon
			slot = slotchange >= 5 ? 6 :
				   slotchange <= 2 ? 9 : 11;
		} else if (rewardType >= 990 && rewardType <= 997) { // Rare
			slot = slotchange >= 5 ? 0 :
				   slotchange <= 2 ? 4 : 8;
		} else if (rewardType >= 998) { // Super Rare
			slot = 2;
		}
		
		player.setSpins(player.getSpins() - 1);
		player.getPackets().sendConfigByFile(11026, player.getSpins() + 1);
		player.getPackets().sendConfigByFile(10860, slot);
		player.getPackets().sendGlobalConfig(1781, 0);
		player.getPackets().sendGlobalConfig(1781, 0);
		this.reward = items.get(slot);
		items.clear();
		this.spinning = true;
		if (reward != null && reward.getName() != null)
			System.out.println(""+player.getDisplayName()+" has won "+reward.getName()+" from Squeal of Fortune.");
	}
	
 }