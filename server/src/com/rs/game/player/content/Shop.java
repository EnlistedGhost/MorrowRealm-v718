package com.rs.game.player.content;

import java.util.concurrent.CopyOnWriteArrayList;

import com.guardian.ItemManager;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.content.utils.MoneyPouch;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.content.shops.DungPointShop;
import com.rs.game.player.content.shops.LoyaltyStore;
import com.rs.game.player.content.shops.PointShop;
import com.rs.game.player.content.shops.PrestigeShop;
import com.rs.game.player.content.shops.SkillerShop;
import com.rs.game.player.content.shops.SlayerShop;
import com.rs.utils.ItemExamines;
import com.rs.utils.ItemSetsKeyGenerator;
import com.rs.utils.Utils;

public class Shop {
	
	private static final int MAIN_STOCK_ITEMS_KEY = ItemSetsKeyGenerator.generateKey();
	
	private static final int MAX_SHOP_ITEMS = 40;
	public static final int COINS = 995;

	private String name;
	private Item[] mainStock;
	private int[] defaultQuantity;
	private Item[] generalStock;
	private int money;
	private int amount;
	private int shopId;
	
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int i) {
		this.shopId = i;
	}
	
	private CopyOnWriteArrayList<Player> viewingPlayers;

	public Shop(String name, int money, Item[] mainStock, boolean isGeneralStore) {
		viewingPlayers = new CopyOnWriteArrayList<Player>();
		this.name = name;
		this.money = money;
		this.mainStock = mainStock;
		defaultQuantity = new int[mainStock.length];
		for (int i = 0; i < defaultQuantity.length; i++)
			defaultQuantity[i] = mainStock[i].getAmount();
		if (isGeneralStore && mainStock.length < MAX_SHOP_ITEMS)
			generalStock = new Item[MAX_SHOP_ITEMS - mainStock.length];
	}

	public boolean isGeneralStore() {
		return generalStock != null;
	}
	
	public void addPlayer(final Player player) {
		viewingPlayers.add(player);
		player.getAttributes().put("Shop", this);
		player.setCloseInterfacesEvent(new Runnable() {
			@Override
			public void run() {
				viewingPlayers.remove(player);
				player.getAttributes().remove("Shop");
				player.getAttributes().remove("shop_buying");
				player.getAttributes().remove("amount_shop");
			}
		});
		player.getPackets().sendConfig(118, MAIN_STOCK_ITEMS_KEY);
		player.getPackets().sendConfig(1496, -1);
		player.getPackets().sendConfig(532, money);
		player.getPackets().sendConfig(2565, 0);
		sendStore(player);
		player.getPackets().sendGlobalConfig(199, -1);
		player.getInterfaceManager().sendInterface(1265);
		for (int i = 0; i < MAX_SHOP_ITEMS; i++)
			player.getPackets().sendGlobalConfig(946 + i, i < defaultQuantity.length ? defaultQuantity[i] : generalStock != null ? 0 : -1);// prices
		player.getPackets().sendGlobalConfig(1241, 16750848);
		player.getPackets().sendGlobalConfig(1242, 15439903);
		player.getPackets().sendGlobalConfig(741, -1);
		player.getPackets().sendGlobalConfig(743, -1);
		player.getPackets().sendGlobalConfig(744, 0);
			if (generalStock != null)
				player.getPackets().sendHideIComponent(1265, 19, false);
		player.getPackets().sendIComponentSettings(1265, 20, 0, getStoreSize() * 6, 1150);
		player.getPackets().sendIComponentSettings(1265, 26, 0, getStoreSize() * 6, 82903066);
		sendInventory(player);
		player.getPackets().sendIComponentText(1265, 85, name);
		player.getAttributes().put("shop_buying", true);
		player.getAttributes().put("amount_shop", 1);
	}

	public void sendInventory(Player player) {
		player.getInterfaceManager().sendInventoryInterface(1266);
		player.getPackets().sendItems(93, player.getInventory().getItems());
		player.getPackets().sendUnlockIComponentOptionSlots(1266, 0, 0, 27, 0, 1, 2, 3, 4, 5);
		player.getPackets().sendInterSetItemsOptionsScript(1266, 0, 93, 4, 7, "Value", "Sell 1", "Sell 5", "Sell 10", "Sell 50", "Examine");
	}

	public void buy(Player player, int slotId, int quantity) {
		if (slotId >= getStoreSize())
			return;
		Item item = slotId >= mainStock.length ? generalStock[slotId - mainStock.length] : mainStock[slotId];
		if (item == null)
			return;

		if (item.getAmount() <= 0) {
			player.getPackets().sendGameMessage("There is no stock of that item at the moment.");
			return;
		}
		
		if (getShopId() == 7 || getShopId() == 32 || getShopId() == 33 || getShopId() == 65) {
			if (player.getRights() == 0) {
				player.sendMessage("You are not permitted to purchase items from this store.");
				return;
			}
		}
		
		if (getShopId() == 34) {
			PointShop.buy(player, item, quantity);
			return;
		}
		if (getShopId() == 28) {
			LoyaltyStore.buy(player, item, quantity);
			return;
		}
		if (getShopId() == 42) {
			DungPointShop.buy(player, item, quantity);
			return;
		}
		
		if (getShopId() == 66) {
			SlayerShop.buy(player, item, quantity);
			return;
		}
		
		if (getShopId() == 46) {
			PrestigeShop.buy(player, item, quantity);
			return;
		}
		if (getShopId() == 50) {
			SkillerShop.buy(player, item, quantity);
			return;
		}
			
		int price = getBuyPrice(item);
		
		if (price == 0)
			return;
		
		int amountCoins = player.getInventory().getItems().getNumberOf(money);
		int inPouch = player.getMoneyPouch();
		
		boolean takeFromPouch = false;
		
		if (money == 995) {
			if (amountCoins < price) {
				amountCoins = inPouch;
				takeFromPouch = true;
			}
		}
	
		int maxQuantity = amountCoins / price;
		int buyQ = item.getAmount() > quantity ? quantity : item.getAmount();
		boolean enoughCoins = maxQuantity >= buyQ;
		
		
		if (!enoughCoins) {
			player.getPackets().sendGameMessage("You can not afford "+item.getName()+".");
			buyQ = maxQuantity;
		} else if (quantity > buyQ)
			player.getPackets().sendGameMessage("The shop has run out of stock.");
		if (item.getDefinitions().isStackable()) {
			if (player.getInventory().getFreeSlots() < 1) {
				player.getPackets().sendGameMessage("Not enough space in your inventory.");
				return;
			}
		} else {
			int freeSlots = player.getInventory().getFreeSlots();
			if (buyQ > freeSlots) {
				buyQ = freeSlots;
				player.getPackets().sendGameMessage("Not enough space in your inventory.");
			}
		}
		if (buyQ != 0) {
			int totalPrice = price * buyQ;
			if (takeFromPouch) { 
				MoneyPouch.removeMoney(totalPrice, player, false);
				player.getInventory().addItem(item.getId(), buyQ);
				item.setAmount(item.getAmount() - buyQ);
				if (item.getAmount() <= 0 && slotId >= mainStock.length)
					generalStock[slotId - mainStock.length] = null;
				refreshShop();
				sendInventory(player);
			} else {
				player.getInventory().deleteItem(money, totalPrice);
				player.getInventory().addItem(item.getId(), buyQ);
				item.setAmount(item.getAmount() - buyQ);
				if (item.getAmount() <= 0 && slotId >= mainStock.length)
					generalStock[slotId - mainStock.length] = null;
				refreshShop();
				sendInventory(player);
			}
		}
		if (quantity == 500)
		player.getAttributes().put("last_shop_purchase", Utils.currentTimeMillis() + 10000);
	}
	public void restock() {
		boolean needRefresh = false;
		for (int i = 0; i < mainStock.length; i++) {
			if (mainStock[i].getAmount() < defaultQuantity[i]) {
				mainStock[i].setAmount(mainStock[i].getAmount() + 50000);
				needRefresh = true;
			} else if (mainStock[i].getAmount() > defaultQuantity[i]) {
				mainStock[i].setAmount(mainStock[i].getAmount() + -50000);
				needRefresh = true;
			}
		}
		if (generalStock != null) {
			for (int i = 0; i < generalStock.length; i++) {
				Item item = generalStock[i];
				if (item == null)
					continue;
				item.setAmount(item.getAmount() - 50000);
				if (item.getAmount() <= 0)
					generalStock[i] = null;
				needRefresh = true;
			}
		}
		if (needRefresh)
			refreshShop();
	}
	
	public void restoreItems() {
		boolean needRefresh = false;
		for (int i = 0; i < mainStock.length; i++) {
			if (mainStock[i].getAmount() < defaultQuantity[i]) {
				mainStock[i].setAmount(mainStock[i].getAmount() + 1);
				needRefresh = true;
			} else if (mainStock[i].getAmount() > defaultQuantity[i]) {
				mainStock[i].setAmount(mainStock[i].getAmount() + -1);
				needRefresh = true;
			}
		}
		if (generalStock != null) {
			for (int i = 0; i < generalStock.length; i++) {
				Item item = generalStock[i];
				if (item == null)
					continue;
				item.setAmount(item.getAmount() - 1);
				if (item.getAmount() <= 0)
					generalStock[i] = null;
				needRefresh = true;
			}
		}
		if (needRefresh)
			refreshShop();
	}

	private boolean addItem(int itemId, int quantity) {
		for (Item item : mainStock) {
			if (item.getId() == itemId) {
				item.setAmount(item.getAmount() + quantity);
				refreshShop();
				return true;
			}
		}
		if (generalStock != null) {
			for (Item item : generalStock) {
				if (item == null)
					continue;
				if (item.getId() == itemId) {
					item.setAmount(item.getAmount() + quantity);
					refreshShop();
					return true;
				}
			}
			for (int i = 0; i < generalStock.length; i++) {
				if (generalStock[i] == null) {
					generalStock[i] = new Item(itemId, quantity);
					refreshShop();
					return true;
				}
			}
		}
		return false;
	}

	public void sell(Player player, int slotId, int quantity) {
		if (player.getInventory().getItemsContainerSize() < slotId)
			return;
		Item item = player.getInventory().getItem(slotId);
		
		if (item == null)
			return;
		
		int originalId = item.getId();
		
		if (item.getDefinitions().isNoted()) {
			item = new Item(item.getDefinitions().getCertId(), item.getAmount());
		}
		
		if (item.getDefinitions().isDestroyItem() || ItemConstants.getItemDefaultCharges(item.getId()) != -1 || !ItemConstants.isTradeable(item) || item.getId() == money) {
			player.getPackets().sendGameMessage("You can't sell this item.");
			return;
		}
		
		if (getShopId() == 7 || getShopId() == 32 || getShopId() == 33 || getShopId() == 65) {
			if (player.getRights() == 0) {
				player.sendMessage("You are not permitted to sell items to this store.");
				return;
			}
		}
		
		if (money == 18201) {
			player.sendMessage("Donator items are non-refundable.");
			return;
		}
		if (getShopId() == 66) {
			player.sendMessage("You can not sell items to this shop.");
			return;
		}
		if (getShopId() == 34) {
			player.sendMessage("You can not sell items to this shop.");
			return;
		}
		if (getShopId() == 28) {
			player.sendMessage("You can not sell items to this shop.");
			return;
		}
		if (getShopId() == 42) {
			player.sendMessage("You can not sell items to this shop.");
			return;
		}
		if (getShopId() == 46) {
			player.sendMessage("You can not sell items to this shop.");
			return;
		}
		if (getShopId() == 50) {
			player.sendMessage("You can not sell items to this shop.");
			return;
		}
		
		
		int dq = getDefaultQuantity(item.getId());
		
		if (dq == -1 && generalStock == null) {
			player.getPackets().sendGameMessage("You can't sell this item to this shop.");
			return;
		}
		
		int price = getSellPrice(item);
		
		int numberOff = player.getInventory().getItems().getNumberOf(originalId);
		if (quantity > numberOff)
			quantity = numberOff;
		
		if (!addItem(item.getId(), quantity)) {
			player.getPackets().sendGameMessage("Shop is currently full.");
			return;
		}
		player.getInventory().deleteItem(originalId, quantity);
		player.getInventory().addItem(money, price * quantity);
	}

	
	public void sendValue(Player player, int slotId) {
		if (player.getInventory().getItemsContainerSize() < slotId)
			return;
		Item item = player.getInventory().getItem(slotId);
		if (item == null)
			return;
		if (item.getDefinitions().isNoted())
			item = new Item(item.getDefinitions().getCertId(), item.getAmount());
		if (item.getDefinitions().isNoted() || !ItemConstants.isTradeable(item) || item.getId() == money) {
			player.getPackets().sendGameMessage("You can't sell this item.");
			return;
		}
		
		if (getShopId() == 50) {
			SkillerShop.sendExamine(player, item, true);
			return;
		}
		
		if (getShopId() == 34) {
			PointShop.sendExamine(player, item);
			return;
		}
		
		if (getShopId() == 42){
			DungPointShop.sendExamine(player, item);
			return;
		}
		
		if (getShopId() == 28){
			LoyaltyStore.sendExamine(player, item);
			return;
		}
		
		if (getShopId() == 46) {
			PrestigeShop.sendExamine(player, item);
			return;
		}
		if (getShopId() == 66) {
			SlayerShop.sendExamine(player, item);
			return;
		}
		int dq = getDefaultQuantity(item.getId());
		if (dq == -1 && generalStock == null) {
			player.getPackets().sendGameMessage("You can't sell this item to this shop.");
			return;
		}
		
		int price = getSellPrice(item);
		
		
		if (money == 995)
			player.getPackets().sendGameMessage(item.getDefinitions().getName() + ": shop will buy for: " + price+ " " + ItemDefinitions.getItemDefinitions(money).getName().toLowerCase() + ". Right-click the item to sell.");

	}

	public int getDefaultQuantity(int itemId) {
		for (int i = 0; i < mainStock.length; i++)
			if (mainStock[i].getId() == itemId)
				return defaultQuantity[i];
		return -1;
	}

	public void sendInfo(Player player, int slotId, boolean isBuying) {
		if (slotId >= getStoreSize())
			return;
		Item[] stock = isBuying ? mainStock : player.getInventory().getItems().getItems();
		Item item = slotId >= stock.length ? generalStock[slotId - stock.length] : stock[slotId];
		if (item == null)
			return;
		//int dq = slotId >= mainStock.length ? 0 : defaultQuantity[slotId];
		int price = isBuying ? getBuyPrice(item) : getSellPrice(item);
		
		if (getShopId() == 50) {
			SkillerShop.sendExamine(player, item, isBuying);
			return;
		}
		
		if (getShopId() == 34) {
			PointShop.sendExamine(player, item);
			return;
		}
		
		if (getShopId() == 42){
			DungPointShop.sendExamine(player, item);
			return;
		}
		
		if (getShopId() == 28){
			LoyaltyStore.sendExamine(player, item);
			return;
		}
		if (getShopId() == 66) {
			SlayerShop.sendExamine(player, item);
			return;
		}
		if (getShopId() == 46) {
			PrestigeShop.sendExamine(player, item);
			return;
		}
		
		player.getPackets().sendConfig(2564, price);
		player.getPackets().sendGameMessage(item.getDefinitions().getName() + ": shop will " + (isBuying ? "sell" : "buy") + " for " + price + " " + ItemDefinitions.getItemDefinitions(money).getName().toLowerCase() + ".");
	}

	public void sendExamine(Player player, int slotId) {
		if (slotId >= getStoreSize())
			return;
		Item item = slotId >= mainStock.length ? generalStock[slotId
				- mainStock.length] : mainStock[slotId];
		if (item == null)
			return;
		player.getPackets().sendGameMessage(ItemExamines.getExamine(item));
	}

	public void refreshShop() {
		for (Player player : viewingPlayers) {
			sendStore(player);
			player.getPackets().sendIComponentSettings(620, 25, 0, getStoreSize() * 6, 1150);
		}
	}

	public int getStoreSize() {
		return mainStock.length + (generalStock != null ? generalStock.length : 0);
	}

	public void sendStore(Player player) {
		Item[] stock = new Item[mainStock.length + (generalStock != null ? generalStock.length : 0)];
		System.arraycopy(mainStock, 0, stock, 0, mainStock.length);
		if (generalStock != null)
			System.arraycopy(generalStock, 0, stock, mainStock.length, generalStock.length);
		player.getPackets().sendItems(MAIN_STOCK_ITEMS_KEY, stock);
	}

	public void sendSellStore(Player player, Item[] inventory) {
		Item[] stock = new Item[inventory.length + (generalStock != null ? generalStock.length : 0)];
		System.arraycopy(inventory, 0, stock, 0, inventory.length);
		if (generalStock != null)
			System.arraycopy(generalStock, 0, stock, inventory.length, generalStock.length);
			player.getPackets().sendItems(MAIN_STOCK_ITEMS_KEY, stock);
	}

	public void handleShop(Player player, int slotId, int amount) {
		boolean isBuying = player.getAttributes().get("shop_buying") != null;
		if (isBuying)
			buy(player, slotId, amount);
		else
			sell(player, slotId, amount);
	}

	public Item[] getMainStock() {
		return this.mainStock;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(Player player, int amount) {
		this.amount = amount;
		player.getPackets().sendIComponentText(1265, 67, String.valueOf(amount)); 
	}
	
	public static int getBuyPrice(Item item) {
		return ItemManager.getPrice(item.getId());
	}
	
	public static int getSellPrice(Item item) {
		return (int) (getBuyPrice(item) / 1.2);
	}
}