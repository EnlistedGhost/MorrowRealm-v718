package com.rs.game.player.dialogues.impl;

import com.rs.game.Graphics;
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.DonatorActions;
import com.rs.game.player.actions.ShipTravelAction;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.cities.tzhaar.LavaMine;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.controlers.NomadsRequiem;
import com.rs.game.player.dialogues.Dialogue;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class PortalTeleport extends Dialogue {

	public PortalTeleport() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Pick a teleport type", 
				"Training locations",
				"Minigame locations", 
				"Boss Locations", 
				"Activities",
				"Next Page");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue("Where would you like to go?",
						"Jastiszo: Rock Crabs", 
						"Harmony: Dead Zombie Island",
						"Canifis Ghous",
						"Relleka: Rock Crabs", "");
				stage = 2;
			} else if (componentId == OPTION_2) {

				sendOptionsDialogue("Where would you like to go?", 
						"Barrows Brothers",
						"Clan Wars", 
						"Duel Arena", 
						"Castle Wars", 
						"Next page");
				stage = 3;
			} else if (componentId == OPTION_3) {
				sendOptionsDialogue("Boss Teleports | Page: 1", 
						"Godwars: Nex",
						"Godwars: Bandos", 
						"Godwars: Saradomin.",
						"Tormented Demons", 
						"Next Page");
				stage = 69;
			} else if (componentId == OPTION_4) {
				sendOptionsDialogue("Activities",
						"I want to exchange my crystal key.",
						"Daily Challenges", "Cancel");
				stage = 73;
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Where would you like to go?",
						"Skilling Locations",
						"Dungeon & Cave Teleports & Slayer", "Teleport to Daemonheim", "Teleport to Donator -area", "Missions: Boss Battles");
				stage = 4;
			}
		} else if (stage == 73) {
			if (componentId == OPTION_1) {
				if (player.getInventory().containsItem(989, 1)) {
					player.getInterfaceManager().sendInterface(1123);
					player.sm("Portal detectes your powerful crystal key, ready for exchange.");
					player.getInterfaceManager().closeChatBoxInterface();
				} else {
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("Portal doesn't detect your crystal key.");
				}
			} else if (componentId == OPTION_2)
				ShipTravelAction.TravelUser(player, 2900, 3524, 0);
			else if (componentId == OPTION_3)
				player.getInterfaceManager().closeChatBoxInterface();
		} else if (stage == 69) {
			if (componentId == OPTION_1)
				ShipTravelAction.TravelUser(player,2905, 5203, 0);
			else if (componentId == OPTION_2)
				ShipTravelAction.TravelUser(player,2870, 5363, 0);
			else if (componentId == OPTION_3)
				ShipTravelAction.TravelUser(player,2920, 5254, 0);
			else if (componentId == OPTION_4)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2562, 5739, 0));
			else if (componentId == OPTION_5) {
				stage = 70;
				sendOptionsDialogue("Boss Teleports | Page: 2",
						"Corporeal Beast", 
						"King Black Dragon",
						"Queen Black Dragon", 
						"Godwars: Armadyl", "Next page");
			}
		} else if (stage == 70) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2966,
						4383, 2));
			} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3008,
						3849, 0));

			} else if (componentId == OPTION_3) {
				end();
				if (player.getSkills().getLevelForXp(Skills.SUMMONING) < 60) {
					player.getPackets().sendGameMessage("You need a summoning level of 60 to go through this portal.");
					return;
				}
				player.getControlerManager().startControler("QueenBlackDragonControler");
			} else if (componentId == OPTION_4) {
				ShipTravelAction.TravelUser(player,2838, 5297, 0);
			} else if (componentId == OPTION_5) {
				stage = 77;
				sendOptionsDialogue("Boss Teleports | Page: 3",
						"Relleka Woods: Hati", 
						"Wilderness: Wildywyrms", 
						"Icy Caves: Glacors", 
						"Avatar of Destruction");
			}
		} else if (stage == 77) {
			if (componentId == OPTION_1) {
				ShipTravelAction.TravelUser(player,2706, 3627, 0);
			} else if (componentId == OPTION_2) {
				ShipTravelAction.TravelUser(player,3136, 3732, 0);
			} else if (componentId == OPTION_3) {
				ShipTravelAction.TravelUser(player,4182, 5727, 0);
            } else if (componentId == OPTION_4) {
                ShipTravelAction.TravelUser(player,2570, 10289, 0);
            }

		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3561, 3293, 0));
			} else if (componentId == OPTION_2) {
				ShipTravelAction.TravelUser(player,2994, 9679, 0);
			} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3365, 3275, 0));
				player.getControlerManager().startControler("DuelControler");
			} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, CastleWars.LOBBY);
			} else if (componentId == OPTION_5) {
				stage = 20;
				sendOptionsDialogue("Where would you like to go?",
						"Fight Pits", "Fight Kiln", "Fight Caves", "Crucible",
						"Next page");
			}

		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				ShipTravelAction.TravelUser(player,2408, 3851, 0);
			} else if (componentId == OPTION_2) {
				ShipTravelAction.TravelUser(player,3797, 2873, 0);
			} else if (componentId == OPTION_3) {
				ShipTravelAction.TravelUser(player,3292, 3182, 0);
			} else if (componentId == OPTION_4) {
				ShipTravelAction.TravelUser(player,3439, 3478, 0);
			} else if (componentId == OPTION_5) {
				ShipTravelAction.TravelUser(player,2326, 3801, 0);
			}
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1890,
						3177, 0));
			} else if (componentId == OPTION_2) {
				ShipTravelAction.TravelUser(player,2994, 9679, 0);

			} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3365,
						3275, 0));
				player.getControlerManager().startControler("DuelControler");
			} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, CastleWars.LOBBY);
			} else if (componentId == OPTION_5) {
				stage = 20;
				sendOptionsDialogue("Where would you like to go?",
						"Fight Pits", "Fight Kiln", "Fight Caves", "Crucible",
						"Next page");
			}
		} else if (stage == 20) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4608, 5061, 0));
			} else if (componentId == OPTION_2)
				Magic.sendNormalTeleportSpell(player, 0, 0, FightKiln.OUTSIDE);
			else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, FightCaves.OUTSIDE);
			} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3358, 6112, 0));
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Where would you like to go?",
						"Brimhaven agility", "Trivia", "Dominion Tower",
						"Next page");
				stage = 21;
			}
		} else if (stage == 4) {
			if (componentId == OPTION_1) { // Skilling Locations
				stage = 71;
				sendOptionsDialogue("Skilling Locations | Page: 1",
						"Gnome Agility Course", "Al-Kharid Mining",
						"(NEW): Lava-Flow Mining",
						"Catherby: Fishing & Cooking", "Next page");
			} else if (componentId == OPTION_2) {
				stage = 72;
				sendOptionsDialogue("Where would you like to go?",
						"Revenants Dungeon", "Slayer Tower",
						"Kuradal's Slayer Dungeon", "Taverly Dungeon",
						"Fremennik Slayer Dungeon");
			} else if (componentId == OPTION_3)
				ShipTravelAction.TravelUser(player,3450, 3699, 0);
			else if (componentId == OPTION_4)
				DonatorActions.CheckDonator(player);
			else if (componentId == OPTION_5) {
				stage = 80;
				sendOptionsDialogue("Where would you like to go?", "Nomad Boss Fight", "Nevermind");
			}
		} else if (stage == 80) {
			if (componentId == OPTION_1) {
			//	player.getControlerManager().startControler("NomadsRequiem");
				NomadsRequiem.enterNomadsRequiem(player);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
			}
		} else if (stage == 72) {
			if (componentId == OPTION_1) { // Dungeon & Cave
				ShipTravelAction.TravelUser(player,3065, 3649, 0);
			} else if (componentId == OPTION_2) {
				ShipTravelAction.TravelUser(player,3428, 3539, 0);
			} else if (componentId == OPTION_3)
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1740, 5311, 0));
			else if (componentId == OPTION_4) {
				ShipTravelAction.TravelUser(player,2884, 9801, 0);
			} else if (componentId == OPTION_5) {
				ShipTravelAction.TravelUser(player,2806, 10003, 0);
			}
		} else if (stage == 71) {
			if (componentId == OPTION_1) {
				ShipTravelAction.TravelUser(player,2466, 3438, 0);
			} else if (componentId == OPTION_2) {
				ShipTravelAction.TravelUser(player,3299, 3303, 0);
			} else if (componentId == OPTION_3)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(LavaMine.Lava_Tele));
			else if (componentId == OPTION_4) {
				ShipTravelAction.TravelUser(player,2809, 3436, 0);
			} else if (componentId == OPTION_5) {
				stage = 76;
				sendOptionsDialogue("Skilling Locations | Page: 2",
						"Summoning: Taverley", "Hunter: Puro-Puro",
						"(New) Artisan's Workshop",
						"Runecrafting: Air Obelisks", "Coming Soon");
			}
		} else if (stage == 76) {
			if (componentId == OPTION_1) {
				ShipTravelAction.TravelUser(player,2924, 3441, 0);
			} else if (componentId == OPTION_2) {
				ShipTravelAction.TravelUser(player,2426, 4445, 0);
			} else if (componentId == OPTION_3)
				ShipTravelAction.TravelUser(player,3047, 3336, 0);
			else if (componentId == OPTION_4) {
				ShipTravelAction.TravelUser(player,3085, 3568, 0);
				player.sm("Use the air orbs with air obelisk to gain some experience.");
			} else if (componentId == OPTION_5) {
				player.getInterfaceManager().closeChatBoxInterface();
			}
		} else if (stage == 5) {
			if (componentId == OPTION_1) {// qbdplayer.getInterfaceManager().closeChatBoxInterface();
											// player.getControlerManager().startControler("QueenBlackDragonControler");
				player.getControlerManager().startControler(
						"QueenBlackDragonControler");
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2)

				ShipTravelAction.TravelUser(player,2905, 5203, 0);
			else if (componentId == OPTION_3)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2562,
						5739, 0));
			else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3011,
						9274, 0));
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Where would you like to go?",
						"Gnome Agility.", "Magic Bank.", "Multi Area (PvP)",
						"Wests (PvP)", "Next page");
				stage = 6;
			}
		} else if (stage == 6) {
			if (componentId == OPTION_1)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2470, 3436, 0));

			else if (componentId == OPTION_2)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2538, 4715, 0));

			else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3240, 3611, 0));
				player.getControlerManager().startControler("Wilderness");
			} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2984, 3596, 0));
				player.getControlerManager().startControler("Wilderness");
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Where would you like to go?",
						"Kalphite Queen", "RuneSpan", "Living Rock Caverns",
						"Beggening Options");
				stage = 7;
			}
		} else if (stage == 7) {
			if (componentId == OPTION_1)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3226,
						3108, 0));
			else if (componentId == OPTION_2)
				return;
			else if (componentId == OPTION_3)
				ShipTravelAction.TravelUser(player,3653, 5115, 0);
			else if (componentId == OPTION_4) {
				stage = 1;
			}
		} else if (stage == 50) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3689,
						3503, 0));
			} else if (componentId == OPTION_2) {
				end();

			} else if (componentId == OPTION_3) {
				stage = 1;
			}
		} else if (stage == 21) {

			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2709, 9464, 0));
			} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2647, 9378, 0));
			} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3366, 3083, 0));
			} else if (componentId == OPTION_4) {
				sendOptionsDialogue("Where would you like to go?", "Zombies", "Recipe for Disaster", "Beggening Options");
				stage = 50;
			}
		}

	}
	@Override
	public void finish() {
	}

}
