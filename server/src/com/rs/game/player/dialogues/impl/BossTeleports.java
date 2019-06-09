package com.rs.game.player.dialogues.impl;

import com.rs.game.WorldTile;
import com.rs.game.player.actions.ShipTravelAction;
import com.rs.game.player.content.Magic;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.NomadsRequiem;
import com.rs.game.player.controlers.custom.PartyDemon;
import com.rs.game.player.dialogues.Dialogue;

public class BossTeleports extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("Boss Teleports", 
				"Godwars Bosses",
				"King Black Dragon", 
				"Corporeal Beast",
				"Queen Black Dragon", 
				"More Options");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue("Godwars Bosses", 
						"Godwars: Bandos",
						"Godwars: Armadyl", 
						"Godwars: Saradomin",
						"Godwars: Zamorak", 
						"Godwars: Nex");
				stage = 0;
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3067, 10254, 0));
				end();
			}
			if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2966, 4383, 2));
				end();
			}
			if (componentId == OPTION_4) {
				if (player.getControlerManager().getControler() != null) {
					end();
					player.sendMessage("Please finish what you're doing before starting this.");
					return;
				}
				player.getControlerManager().startControler("QueenBlackDragonControler");
				end();
			}
			if (componentId == OPTION_5) {
				sendOptionsDialogue("Boss Teleports", 
						"Hati's Relleka Woods",
						"Glacor's Icy Caves",
						"Nomad's Requiem",
						"Avatar of Destruction", 
						"More Options...");
				stage = 1;
			}
		} else if (stage == 0) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2870, 5363, 0));
				player.getControlerManager().startControler("GodWars");
				end();
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2828, 5302, 0));
				player.getControlerManager().startControler("GodWars");
				end();
			}
			if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2923, 5250, 0));
				player.getControlerManager().startControler("GodWars");
				end();
			}
			if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2926, 5325, 0));
				player.getControlerManager().startControler("GodWars");
				end();
			}
			if (componentId == OPTION_5) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2904, 5203, 0));
				player.getControlerManager().startControler("GodWars");
				end();
			}
		} else if (stage == 1) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2706, 3627, 0));
				end();
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4182, 5727, 0));
				end();
			}
			if (componentId == OPTION_3) {
				player.getControlerManager().startControler("NomadsRequiem");
				end();
			}
			if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2570, 10289, 0));
				end();
			}
			if (componentId == OPTION_5) {
				sendOptionsDialogue("Boss Teleports", 
						"Tormented Demons",
						"Party Demon",
						"Blink",
						"Kalphite Queen", 
						"[<col=FF0000>New</col>] Yk'Lagor the Thunderous");
				stage = 2;
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2529, 5852, 0));
				player.sendMessage("Walk north until you come into an open area. TDS are there.");
				end();
			}
			if (componentId == OPTION_2) {
				if (player.getControlerManager().getControler() == null) {
					player.getControlerManager().startControler("PartyDemon");
					end();
				} else {
					player.sendMessage("You are currently in another instance!");
					player.sendMessage("Please leave what you're doing before you enter this.");
					end();
				}
			}
			
			if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2671, 9519, 0));
				end();
			}
			
			if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3507, 9494, 0));
				end();
			}
			
			if (componentId == OPTION_5) {
				if (player.getRights() == 0) {
					player.sendMessage("Please donate if you wish to fight this boss.");
					player.sendMessage("Drops for this boss include non-cosmetic Kalphite Armour and Drygores.");
					end();
					return;
				}
				end();
				player.getControlerManager().startControler("LobbyArea");
			}
		}
		
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}