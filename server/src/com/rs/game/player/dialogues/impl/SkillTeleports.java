package com.rs.game.player.dialogues.impl;

import com.rs.game.WorldTile;
import com.rs.game.player.Skills;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.cities.tzhaar.LavaMine;
import com.rs.game.player.controlers.custom.Dungeoneering;
import com.rs.game.player.dialogues.Dialogue;

public class SkillTeleports extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("Skilling Teleports", "Basic Skilling", "Advanced Skilling", "Cancel");
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue("Basic Skilling",
					"Fishing",
					"Mining",
					"Agility",
					"Woodcutting",
					"More Options");
				stage = 2;
			}
			if (componentId == OPTION_2) {
				sendOptionsDialogue("Advanced Skilling",
					"Mining: Lava Flow Mining",
					"Hunter: Puro-Puro",
					"Fishing: Living Rock Caverns",
					"Slayer: Fremmy Dungeon",
					"More Options");
				stage = 3;
			}
			if (componentId == OPTION_3) {
				end();
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2599, 3421, 0));
				end();
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3179, 3369, 0));
				end();
			}
			if (componentId == OPTION_3) {
				sendOptionsDialogue("Which Course?",
						"Gnome Agility",
						"Barbarian Outpost",
						"Go Back...");
				stage = 7;
			}
			if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3250, 3359, 0));
				end();
			}
			if (componentId == OPTION_5) {
				sendOptionsDialogue("Basic Skilling",
						"Runecrafting",
						"Summoning",
						"Hunter",
						"Farming",
						"Dungeoneering");
				stage = 4;
			}
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(LavaMine.Lava_Tele));
				end();
			}
			if (componentId == OPTION_2) {
				if (!player.getInventory().hasFreeSlots()) {
					end();
					player.sendMessage("You need atleast 1 free slot to start Puro-Puro.");
					return;
				}
				end();
				player.getControlerManager().startControler("PuroPuro");
			}
			if (componentId == OPTION_3) {
				if (player.getSkills().getLevel(Skills.FISHING) < 90) {
					end();
					player.sendMessage("<col=FF0000>You must be atleast 90 Fishing to go to LRC.</col>");
					return;
				}
				player.getPackets().sendGameMessage("Talk to Hank for some fishing supplies!");
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3640, 5138, 0));
				end();
			}
			if (componentId == OPTION_4) {
				if (player.getRights() == 0) {
					end();
					player.sendMessage("You must be donator to access the new slayer dungeon!");
					return;
				}
				player.getControlerManager().startControler("SlayerControler");
				end();
			}
			if (componentId == OPTION_5) {
				sendOptionsDialogue("Advanced Skilling",
						"Smithing: Artisan's Workshop",
						"RuneCrafting: RuneSpan",
						"Slayer: Kuradel's Slayer Tower",
						"Farming: Livid Farming");
				stage = 5;
			}
		} else if (stage == 4) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2600, 3162, 0));
				end();
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2209, 5343, 0));
				end();
			}
			if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2526, 2916, 0));
				end();
			}
			if (componentId == OPTION_4) {
				player.getPackets().sendGameMessage("<col=FF0000>This has been replaced by Livid Farming. (See Advanced Skilling list)");
				end();
			}
			if (componentId == OPTION_5) {
				sendOptionsDialogue("Which Dungeon?",
						"Low Level",
						"Medium Level",
						"High Level", 
						"[<col=FF0000>New</col>] Daemonheim");
				stage = 6;
			}
		} else if (stage == 5) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3047, 3336, 0));
				end();
			}			
			if (componentId == OPTION_2) {
				player.getPackets().sendGameMessage("Buy essence from the wizard to begin!");
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3992, 6108, 1));
				end();
			}
			if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3428, 3539, 0));
				end();
			}
			if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3238, 3350, 0));
				end();
			}
		} else if (stage == 6) {
			if (componentId == OPTION_1) {
				player.getControlerManager().startControler("Dungeoneering", 0);
				end();
			}
			if (componentId == OPTION_2) {
				if (!player.getSkills().hasRequiriments(Skills.DUNGEONEERING, 60)) {
					player.sendMessage("You need atleast 60 Dungeoneering to enter Mid Level Dung.");
					return;
				}
				player.getControlerManager().startControler("Dungeoneering", 1);
				end();
			}
			if (componentId == OPTION_3) {
				if (!player.getSkills().hasRequiriments(Skills.DUNGEONEERING, 99)) {
					end();
					player.sendMessage("You need atleast 99 Dungeoneering to enter High Level Dung.");
					return;
				}
				player.getControlerManager().startControler("Dungeoneering", 2);
				end();
			}
			if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3451, 3712, 0));
				end();
			}
		} else if (stage == 7) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2470, 3436, 0));
				end();
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2541, 3548, 0));
				end();
			}
			if (componentId == OPTION_3) {
				sendOptionsDialogue("Basic Skilling",
						"Fishing",
						"Mining",
						"Agility",
						"Woodcutting",
						"More Options");
				stage = 2;
			}
		}
		
		
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
