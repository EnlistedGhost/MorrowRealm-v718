package com.rs.game.player.dialogues.impl;

import com.rs.Settings;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.WorldTile;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.ShopsHandler;

public class Manager extends Dialogue {

	public Manager() {
	}

	@Override
	public void start() {
		player.getHintIconsManager().removeUnsavedHintIcon();
		sendMessage(
				"Welcome to MorrowRealm! I can change your, look and",
				"tell you some things about the server. If you're",
				"interested, I have a few shops too you can look at.",
				"So what would you like to do?");
		stage = 0;
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 0) {
			sendOptionsDialogue(""+Settings.SERVER_NAME+" Manager Options",
					"I would like to change my appearance",
					"I would like to view your shops please",
					"<img=4> I would like to go to the Donator Zone",
					"<col=FF0000>I want to know more about the server");
			stage = 1;
		}
		else if (stage == 1) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue(""+Settings.SERVER_NAME+" - Account Settings",
						"I would like to edit my gender & skin.",
						"I would like to edit my hairstyles.",
						"I would love to change my clothes, Eva.");
				stage = 2;
			} else if (componentId == OPTION_2) {
				sendOptionsDialogue(""+Settings.SERVER_NAME+" - Point Shops",
						"PvP Point Shop",
						"Prestige Point Shop",
						"Skiller Shop");
					stage = 3;
			} else if (componentId == OPTION_3) {
				if (player.getRights() > 0) {
					player.getInterfaceManager().closeChatBoxInterface();
					startDemonTeleport();
				} else {
					end();
					player.sm("You are not donator, Manager won't let you go.");
				}
			} else if (componentId == OPTION_4) {
				sendMessage(
						"Ah yes, well, every teleport you'll ever need", 
						"is on the quest tab, just click a category on the tab",
						"and you'll see various options on where you can go", 
						"for that category. It's easy as that!");
					player.getInterfaceManager().openGameTab(3);
				stage = 4;
			}
		} else if (stage == 2) {
				if (componentId == OPTION_1) {
					player.getInterfaceManager().closeChatBoxInterface();
					PlayerLook.openMageMakeOver(player);
				} else if (componentId == OPTION_2) {
					player.getInterfaceManager().closeChatBoxInterface();
					PlayerLook.openHairdresserSalon(player);
				} else if (componentId == OPTION_3) {
					player.getInterfaceManager().closeChatBoxInterface();
					PlayerLook.openThessaliasMakeOver(player);
				}
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				ShopsHandler.openShop(player, 34);
				end();
			}
			if (componentId == OPTION_2) {
				ShopsHandler.openShop(player, 46);
				end();
			}
			if (componentId == OPTION_3) {
				ShopsHandler.openShop(player, 50);
				end();
			}
		} else if (stage == 4) {
			sendMessage(
				"To make some quick cash, you could always do some skilling", 
				"and sell the items you get from it (logs, ors, etc.).",
				"If you're not into skilling, train up combat and boss", 
				"for some nice gear and profitable items. What else can i do for ya?");
			player.getInterfaceManager().openGameTab(3);
			stage = 5;
		} else if (stage == 5) {
			sendOptionsDialogue(""+Settings.SERVER_NAME+" Manager Options",
					"I'd like to change my appearance",
					"I want to view the Point Shops",
					"I'd like to go to the Donator Zone",
					"I want to know more about the server");
			stage = 1;
			player.getInterfaceManager().openGameTab(4);
		}
	}
	
	public void sendMessage(String... message) {
		sendEntityDialogue(IS_NPC, "MorrowRealm Manager", 13768, 9827, message);
	}

	private WorldTile donatorZone = new WorldTile(1807, 3211, 0);
	
	public void startDemonTeleport() {
		WorldTasksManager.schedule(new WorldTask() {
			int loop = 0;
			@Override
			public void run() {
				if (loop == 0) {
					player.setNextAnimation(new Animation(17108));
					player.setNextGraphics(new Graphics(3224));
					player.setNextGraphics(new Graphics(3225));
				} else if (loop == 9) {
					player.setNextWorldTile(donatorZone);
				} else if (loop == 10) {
					player.setNextAnimation(new Animation(16386));
					player.setNextGraphics(new Graphics(3019));
				} else if (loop == 11) {
					player.setNextAnimation(new Animation(808));
					player.setNextGraphics(new Graphics(-1));
					stop();
				}
				loop++;
			}
		}, 0, 1);
	}
	
	@Override
	public void finish() {
		player.getInterfaceManager().openGameTab(4);
	}

}
