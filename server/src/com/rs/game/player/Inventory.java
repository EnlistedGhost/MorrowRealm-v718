package com.rs.game.player;

import java.io.Serializable;
import java.util.ArrayList;

import com.rs.content.utils.MoneyPouch;
import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.utils.ItemExamines;
import com.rs.utils.Utils;

public final class Inventory implements Serializable {

	private static final long serialVersionUID = 8842800123753277093L;

	private ItemsContainer<Item> items;

	private transient Player player;

	public static final int INVENTORY_INTERFACE = 679;

	public Inventory() {
		items = new ItemsContainer<Item>(28, false);
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void init() {
		player.getPackets().sendItems(93, items);
	}

	public void unlockInventoryOptions() {
		player.getPackets().sendIComponentSettings(INVENTORY_INTERFACE, 0, 0,
				27, 4554126);
		player.getPackets().sendIComponentSettings(INVENTORY_INTERFACE, 0, 28,
				55, 2097152);
	}

	public void reset() {
		items.reset();
		init();
	}

	public void refresh(int... slots) {
		player.getPackets().sendUpdateItems(93, items, slots);
	}
	
	public boolean addItem(int itemId, int amount) {
		if (itemId < 0 || amount < 0 || !Utils.itemExists(itemId) || !player.getControlerManager().canAddInventoryItem(itemId, amount))
			return false;
		Item[] itemsBefore = items.getItemsCopy();
		int amount2 = player.getInventory().getItems().getNumberOf(itemId);
		if((amount2 + amount) < 0) {
			int amount3 = (Integer.MAX_VALUE - amount2);
			if (itemId == 995) {
				if (amount2 == Integer.MAX_VALUE) {
					MoneyPouch.addMoney(amount, player, false);
					return true;
				}
			}
			
			if(amount3 <= 0) {
				player.sm("Not enough space in your inventory");
				return true;
			}
			
			if (!items.add(new Item(itemId, amount3))) {
				items.add(new Item(itemId, amount3));
				player.getPackets().sendGameMessage("Not enough space in your inventory.");
				refreshItems(itemsBefore);
				return false;
			}
			refresh();
			return true;
		}
		
		if(!items.add(new Item(itemId, amount))) {
			player.getPackets().sendGameMessage("Not enough space in your inventory.");
			return false;
		}
		refreshItems(itemsBefore);
		return true;
	}
	
	public boolean add(int id, int amount) {
		if (id > Utils.getItemDefinitionsSize() || amount < 0)
			return false;
		Item item = new Item(id, amount);
		if (!item.isNote()) {
			if (amount > getFreeSlots() && !item.isStackable()) {
				if (item.hasNote()) {
					id = item.getNotedId();
					amount = item.getAmount();
				} else {
					amount = getFreeSlots();
				}
			}
		} else {
			if (amount > getFreeSlots() && !item.isStackable())
				amount = getFreeSlots();
			id = item.getId();
		}
		return addItem(id, amount);
	}
	
	public boolean addItems(int[] ids, int amount) {
		for (Integer i : ids) {
			Item item = new Item(i, amount);
			if (!item.isStackable() && amount > getFreeSlots()) {
				amount = getFreeSlots();
			}
			if (!add(i, amount)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean addItem(Item item) {
		if (item.getId() < 0 || item.getAmount() < 0 || !Utils.itemExists(item.getId()) || !player.getControlerManager().canAddInventoryItem(item.getId(), item.getAmount()))
			return false;
		Item[] itemsBefore = items.getItemsCopy();
		if (!items.add(item)) {
			items.add(new Item(item.getId(), items.getFreeSlots()));
			player.getPackets().sendGameMessage("Not enough space in your inventory.");
			refreshItems(itemsBefore);
			return false;
		}
		refreshItems(itemsBefore);
		return true;
	}

	public void deleteItem(int slot, Item item) {
		if (!player.getControlerManager().canDeleteInventoryItem(item.getId(),
				item.getAmount()))
			return;
		Item[] itemsBefore = items.getItemsCopy();
		items.remove(slot, item);
		refreshItems(itemsBefore);
	}

	public boolean removeItems(Item... list) {
		for (Item item : list) {
			if (item == null)
				continue;
			deleteItem(item);
		}
		return true;
	}

	public void deleteItem(int itemId, int amount) {
		if (!player.getControlerManager().canDeleteInventoryItem(itemId, amount))
			return;
		Item[] itemsBefore = items.getItemsCopy();
		items.remove(new Item(itemId, amount));
		refreshItems(itemsBefore);
	}

	public void deleteItem(Item item) {
		if (!player.getControlerManager().canDeleteInventoryItem(item.getId(), item.getAmount()))
			return;
		Item[] itemsBefore = items.getItemsCopy();
		items.remove(item);
		refreshItems(itemsBefore);
	}
	
	public void deleteItems(Item[] item) {
		Item[] itemsBefore = items.getItemsCopy();
		for (Item i : item) {
			items.remove(i);
		}
		refreshItems(itemsBefore);
	}
	
	/*
	 * No refresh needed its client to who does it :p
	 */
	public void switchItem(int fromSlot, int toSlot) {
		Item[] itemsBefore = items.getItemsCopy();
		Item fromItem = items.get(fromSlot);
		Item toItem = items.get(toSlot);
		items.set(fromSlot, toItem);
		items.set(toSlot, fromItem);
		refreshItems(itemsBefore);
	}

	public void refreshItems(Item[] itemsBefore) {
		int[] changedSlots = new int[itemsBefore.length];
		int count = 0;
		for (int index = 0; index < itemsBefore.length; index++) {
			if (itemsBefore[index] != items.getItems()[index])
				changedSlots[count++] = index;
		}
		int[] finalChangedSlots = new int[count];
		System.arraycopy(changedSlots, 0, finalChangedSlots, 0, count);
		refresh(finalChangedSlots);
	}

	public ItemsContainer<Item> getItems() {
		return items;
	}

	public boolean hasFreeSlots() {
		return items.getFreeSlot() != -1;
	}

	public int getFreeSlots() {
		return items.getFreeSlots();
	}

	public int getNumberOf(int itemId) {
		return items.getNumberOf(itemId);
	}

	public Item getItem(int slot) {
		return items.get(slot);
	}

	public int getItemsContainerSize() {
		return items.getSize();
	}

	public boolean containsItems(Item[] item) {
		for (int i = 0; i < item.length; i++)
			if (!items.contains(item[i]))
				return false;
		return true;
	}
	
	public boolean containsItems(ArrayList<Integer> list_1, ArrayList<Integer> list_2) {
		int size = list_1.size() > list_2.size() ? list_2.size() : list_1.size();
		for (int i = 0; i < size; i++)
			if (!items.contains(new Item(list_1.get(i), list_2.get(i))))
				return false;
		return true;
	}

	public boolean containsItem(int itemId, int ammount) {
		return items.contains(new Item(itemId, ammount));
	}


	public boolean contains(int... itemIds) {
		int count = 0;
		for (int itemId : itemIds) {
			if (items.containsOne(new Item(itemId, 1))) {
				count++;
			}
		}
		if (count >= itemIds.length)
			return true;
		return false;
	}
	
	public boolean containsOneItem(int... itemIds) {
		for (int itemId : itemIds) {
			if (items.containsOne(new Item(itemId, 1)))
				return true;
		}
		return false;
	}
	
	public boolean containsItem(int itemId) {
		return items.containsOne(new Item(itemId, 1));
	}
	
	public void sendExamine(int slotId) {
		if (slotId >= getItemsContainerSize())
			return;
		Item item = items.get(slotId);
		if (item == null)
			return;
		player.getPackets().sendInventoryMessage(0, slotId, ItemExamines.getExamine(item));
	}

	public void refresh() {
		player.getPackets().sendItems(93, items);
	}

}
