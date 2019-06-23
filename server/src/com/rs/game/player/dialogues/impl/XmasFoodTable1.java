package com.rs.game.player.dialogues.impl;

import com.rs.Settings;
import com.rs.game.item.Item;
import com.rs.game.player.content.DisplayNameAction;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.content.TicketSystem;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;



public class XmasFoodTable1 extends Dialogue {

	public XmasFoodTable1() {
	}

	@Override
	public void start() {
		sendOptionsDialogue("What would you like to take?",
				"Yule Log",
				"Christmas Pudding",
				"Turkey Drumstick",
				"Roast Potatoes", 
				"Mulled Wine");
		stage = 2;

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 2) {
			if (componentId == OPTION_1) {
				if (player.getInventory().getFreeSlots() < 1) {
					player.sm("You do not have enough inventory space.");
					end();
				} else {
					player.getInventory().addItem(15430, 1);
					player.xmasYule = 1;
					end();
				}
			} else if (componentId == OPTION_2) {
				if (player.getInventory().getFreeSlots() < 1) {
					player.sm("You do not have enough inventory space.");
					end();
				} else {
					player.getInventory().addItem(26546, 1);
					end();
				}
			} else if (componentId == OPTION_3) {
				if (player.getInventory().getFreeSlots() < 1) {
					player.sm("You do not have enough inventory space.");
					end();
				} else {
					player.getInventory().addItem(15428, 1);
					end();
				}
			} else if (componentId == OPTION_4) {
				if (player.getInventory().getFreeSlots() < 1) {
					player.sm("You do not have enough inventory space.");
					end();
				} else {
					player.getInventory().addItem(15429, 1);
					end();
				}
			} else if (componentId == OPTION_5) {
				if (player.getInventory().getFreeSlots() < 1) {
					player.sm("You do not have enough inventory space.");
					end();
				} else {
					player.getInventory().addItem(15431, 1);
					player.xmasWine = 1;
					end();
				}
			}
		}

	}

	@Override
	public void finish() {
	}

}