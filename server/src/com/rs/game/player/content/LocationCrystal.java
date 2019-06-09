package com.rs.game.player.content;

import java.io.Serializable;

import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.dialogues.Dialogue;

/**
 * 
 * @author Cody
 * 
 */

public class LocationCrystal implements Serializable {

	private static final long serialVersionUID = -3788650257010921632L;
	private Player player;
	private transient WorldTile tile;
	private Location[] locations;

	public LocationCrystal(Player player) {
		this.player = player;
		locations = new Location[3];
		for (int i = 0; i < locations.length; i++)
			locations[i] = new Location("Empty", null);
	}

	public void handleClick() {
		if (locations == null) {
			locations = new Location[3];
			for (int i = 0; i < locations.length; i++)
				locations[i] = new Location("Empty", null);
		}
		player.getDialogueManager().startDialogue(new Dialogue() {

			private int index;

			@Override
			public void start() {
				if (player.getControlerManager().getControler() != null) {
					player.getPackets().sendGameMessage(
							"You're too busy to do this.");
					end();
					return;
				}
				sendOptionsDialogue("Select an Option", "View saved locations",
						"Save new location", "Delete all saved locations",
						"Nothing");
			}

			@Override
			public void run(int interfaceId, int componentId) {
				if (stage == -1) {
					if (componentId == OPTION_1) {
						String[] names = new String[4];
						for (int i = 0; i < locations.length; i++)
							names[i] = locations[i].getName();
						names[3] = "Nothing";
						sendOptionsDialogue("Select an Option", names);
						stage = 0;
					} else if (componentId == OPTION_2) {
						index = -1;
						for (int i = 0; i < locations.length; i++) {
							if (locations[i].getName().toLowerCase()
									.equals("empty"))
								index = i;
						}
						if (index == -1) {
							sendDialogue("You have no empty locations left. You must delete one before you can save another.");
							stage = 1;
							return;
						}
						tile = new WorldTile(player.getX(), player.getY(),
								player.getPlane());
						player.getAttributes().put("LocationCrystal",
								index);
						player.getPackets().sendInputNameScript(
								"Enter location name");
						stage = 2;
					} else if (componentId == OPTION_3) {
						sendOptionsDialogue("Are you sure?", "Yes", "No");
						stage = 3;
					} else
						end();
				} else if (stage == 0) {
					index = 0;
					if (componentId == OPTION_1)
						index = 0;
					else if (componentId == OPTION_2)
						index = 1;
					else if (componentId == OPTION_3)
						index = 2;
					else {
						end();
						return;
					}
					Location location = locations[index];
					if (location.getName().toLowerCase().equals("empty")) {
						sendOptionsDialogue("Select an Option",
								"Save new location", "Nothing");
						stage = 4;
					} else {
						sendOptionsDialogue("Select an Option",
								"Teleport to location", "Delete location",
								"Rename", "Nothing");
						stage = 5;
					}
				} else if (stage == 1) {
					end();
				} else if (stage == 2) {
					end();
				} else if (stage == 3) {
					if (componentId == OPTION_1) {
						locations = new Location[3];
						for (int i = 0; i < locations.length; i++)
							locations[i] = new Location("Empty", null);
					}
					end();
				} else if (stage == 4) {
					if (componentId == OPTION_1) {
						tile = new WorldTile(player.getX(), player.getY(),
								player.getPlane());
						player.getAttributes().put("LocationCrystal",
								index);
						player.getPackets().sendInputNameScript(
								"Enter location name");
						stage = 2;
					} else
						end();
				} else if (stage == 5) {
					Location location = locations[index];
					if (componentId == OPTION_1)
						Magic.sendNormalTeleportSpell(player, 0, 0,
								location.getTile());
					else if (componentId == OPTION_2) {
						sendOptionsDialogue("Are you sure?", "Yes", "No");
						stage = 6;
					} else if (componentId == OPTION_3) {
						player.getAttributes().put("LocationCrystal",
								index);
						player.getPackets().sendInputNameScript(
								"Enter new name");
						stage = 2;
					} else
						end();
				} else if (stage == 6) {
					if (componentId == OPTION_1)
						locations[index] = new Location("Empty", null);
					end();
				}
			}

			@Override
			public void finish() {
				player.getInterfaceManager().closeChatBoxInterface();
			}

		});
	}

	public void saveLocation(String name, int index) {
		locations[index] = new Location(name, tile);
		player.getDialogueManager().finishDialogue();
		player.getPackets().sendGameMessage("Your location has been saved.");
	}

	public class Location implements Serializable {

		private static final long serialVersionUID = -1921478357927613366L;

		private String name;
		private WorldTile tile;

		public Location(String name, WorldTile tile) {
			this.name = name;
			this.tile = tile;
		}

		public String getName() {
			return name;
		}

		public WorldTile getTile() {
			return tile;
		}
	}

}