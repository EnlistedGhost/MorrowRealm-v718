package com.rs.game.player.content;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.rs.game.item.Item;
import com.rs.game.player.Player;

public class MoneyPouch implements Serializable {

	private static final long serialVersionUID = -3847090682601697992L;

	private transient Player player;
	private boolean usingPouch;
	private int coinAmount;

	public MoneyPouch(Player player) {
		this.player = player;
	}

	public void switchPouch() {
		usingPouch = !usingPouch;
		refresh(true);
	}

	private void refresh(boolean swap) {
		if (swap)
			player.getPackets().sendRunScript(5557, 1);
		player.getPackets().sendRunScript(5560, coinAmount);
	}

	public void sendDynamicInteraction(int amount, boolean remove) {
		int newAmount = remove ? amount - coinAmount : amount + coinAmount;
		Item item = new Item(995, amount - (remove ? 0 : Integer.MAX_VALUE));
		if (remove) {
			if (newAmount <= 0) {
				if (player.getInventory().containsItem(item.getId(), item.getAmount()))
					player.getInventory().getItems().remove(item);
				else
					player.getPackets().sendGameMessage("You don't have enough coins.");
				return;
			} else
				player.getPackets().sendGameMessage(getFormattedNumber(amount) + " coins have been removed from your money pouch.");
		} else {
			if (!remove) {
				if ((coinAmount + amount) > Integer.MAX_VALUE) {
					player.sm("Your money pouch can not hold that many coins.");
				return;
				}
			}
			if (newAmount > Integer.MAX_VALUE) {
				if (player.getInventory().getItems().add(item)) {
					player.getPackets().sendGameMessage("Your money-pouch is currently full. Your coins will now go to your inventory.");
				} else {
					player.getPackets().sendGameMessage("Your inventory is currently full.");
					return;
				}
			} else {
				player.getPackets().sendGameMessage(getFormattedNumber(amount) + " coins have been added to your money pouch.");
		
			}
		}
		setAmount(newAmount, amount, remove);
	}

	private String getFormattedNumber(int amount) {
		return new DecimalFormat("#,###,##0").format(amount).toString();
	}

	public void sendExamine() {
		player.getPackets().sendGameMessage("Your money pouch current contains "+ getFormattedNumber(coinAmount) + " coins.");
	}

	public void setAmount(int coinAmount, int addedAmount, boolean remove) {
		this.coinAmount = coinAmount;
		player.getPackets().sendRunScript(5561, remove ? 0 : 1, addedAmount);
		refresh(false);
	}

	public boolean isUsingPouch() {
		return usingPouch;
	}

	public int getCoinsAmount() {
		return coinAmount;
	}
}