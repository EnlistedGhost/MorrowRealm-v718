package com.rs.game.item;

import java.io.Serializable;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.ItemsEquipIds;

public class Item implements Serializable {

	private static final long serialVersionUID = -6485003878697568087L;

	private short id;
	protected int amount;

	public int getId() {
		return id;
	}

	@Override
	public Item clone() {
		return new Item(id, amount);
	}

	public Item(int id) {
		this(id, 1);
	}

	public Item(int id, int amount) {
		this(id, amount, false);
	}

	public Item(int id, int amount, boolean amt0) {
		this.id = (short) id;
		this.amount = amount;
		if (this.amount <= 0 && !amt0) {
			this.amount = 1;
		}
	}

	public ItemDefinitions getDefinitions() {
		return ItemDefinitions.getItemDefinitions(id);
	}
	public ItemDefinitions getDefs() {
		return ItemDefinitions.getItemDefinitions(id);
	}
	
	public boolean isStackable() {
		return getDefinitions().isStackable();
	}
	public ItemDefinitions getDefs(int itemId) {
		return ItemDefinitions.getItemDefinitions(itemId);
	}
	
	public int getEquipId() {
		return ItemsEquipIds.getEquipId(id);
	}
	
	public boolean isNote() {
		return getDefinitions().isNoted();
	}
	
	public boolean hasNote() {
		String getOriginalName = getDefinitions().getName().toLowerCase();
		String nextItem = getDefs(id + 1).getName().toLowerCase();
		boolean isNewItemNoted = getDefs(id + 1).isNoted();
		return getOriginalName.equals(nextItem) && isNewItemNoted == true;
	}
	
	public int getNotedId() {
		return (id + 1);
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setId(int id) {
		this.id = (short) id;
	}

	public int getAmount() {
		return amount;
	}
	
	public String getName() {
		return getDefinitions().getName();
	}

}
