package com.rs.game.player.dialogues.impl;

//import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.dialogues.Dialogue;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class ArmorGeneratorDelux extends Dialogue {

	public ArmorGeneratorDelux() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Armor Generator (Delux)", "NewAge Armor.",
				"Legacy Armor.", "Nevermind.");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				stage = 2;
				sendOptionsDialogue("Armor Generator (Delux)", "Black (t) Set",
					"Black (g) Set", "Adamant (t) Set", 
					"Adamant (g) set",
					"More...");
			} else if (componentId == OPTION_2) {
				stage = 3;
				sendOptionsDialogue("Armor Generator (Delux)", "Black (t) Set",
					"Black (g) Set", "Adamant (t) Set", 
					"Adamant (g) set",
					"More...");
			} else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				if (player.getInventory().getFreeSlots() > 4) {
					player.getInventory().addItem(2583, 1);
					player.getInventory().addItem(2585, 1);
					player.getInventory().addItem(3472, 1);
					player.getInventory().addItem(2589, 1);
					player.getInventory().addItem(2587, 1);
					player.getInterfaceManager().closeChatBoxInterface();
				} else {
					player.sm("You don't have enough free space!");
					player.getInterfaceManager().closeChatBoxInterface();
				}
			} else if (componentId == OPTION_2) {
				if (player.getInventory().getFreeSlots() > 4) {
					player.getInventory().addItem(2591, 1);
					player.getInventory().addItem(2593, 1);
					player.getInventory().addItem(3473, 1);
					player.getInventory().addItem(2595, 1);
					player.getInventory().addItem(2611, 1);
					player.getInterfaceManager().closeChatBoxInterface();
				} else {
					player.sm("You don't have enough free space!");
					player.getInterfaceManager().closeChatBoxInterface();
				}
			} else if (componentId == OPTION_3) {
				if (player.getInventory().getFreeSlots() > 4) {
					player.getInventory().addItem(2599, 1);
					player.getInventory().addItem(2601, 1);
					player.getInventory().addItem(3474, 1);
					player.getInventory().addItem(2603, 1);
					player.getInventory().addItem(2605, 1);
					player.getInterfaceManager().closeChatBoxInterface();
				} else {
					player.sm("You don't have enough free space!");
					player.getInterfaceManager().closeChatBoxInterface();
				}
			} else if (componentId == OPTION_4) {
				if (player.getInventory().getFreeSlots() > 4) {
					player.getInventory().addItem(2607, 1);
					player.getInventory().addItem(2609, 1);
					player.getInventory().addItem(3475, 1);
					player.getInventory().addItem(2611, 1);
					player.getInventory().addItem(2613, 1);
					player.getInterfaceManager().closeChatBoxInterface();
				} else {
					player.sm("You don't have enough free space!");
					player.getInterfaceManager().closeChatBoxInterface();
				}
			} else if (componentId == OPTION_5) {
				stage = 4;
				sendOptionsDialogue("Armor Generator (Delux)", "Rune (t) Set",
					"Rune (g) Set", "Main Menu");
			}
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				if (player.getInventory().getFreeSlots() > 4) {
					player.getInventory().addItem(26581, 1);
					player.getInventory().addItem(26583, 1);
					player.getInventory().addItem(26585, 1);
					player.getInventory().addItem(26587, 1);
					player.getInventory().addItem(26655, 1);
					player.getInterfaceManager().closeChatBoxInterface();
				} else {
					player.sm("You don't have enough free space!");
					player.getInterfaceManager().closeChatBoxInterface();
				}
			} else if (componentId == OPTION_2) {
				if (player.getInventory().getFreeSlots() > 4) {
					player.getInventory().addItem(26573, 1);
					player.getInventory().addItem(26575, 1);
					player.getInventory().addItem(26577, 1);
					player.getInventory().addItem(26579, 1);
					player.getInventory().addItem(26653, 1);
					player.getInterfaceManager().closeChatBoxInterface();
				} else {
					player.sm("You don't have enough free space!");
					player.getInterfaceManager().closeChatBoxInterface();
				}
			} else if (componentId == OPTION_3) {
				if (player.getInventory().getFreeSlots() > 4) {
					player.getInventory().addItem(26597, 1);
					player.getInventory().addItem(26599, 1);
					player.getInventory().addItem(26601, 1);
					player.getInventory().addItem(26603, 1);
					player.getInventory().addItem(26659, 1);
					player.getInterfaceManager().closeChatBoxInterface();
				} else {
					player.sm("You don't have enough free space!");
					player.getInterfaceManager().closeChatBoxInterface();
				}
			} else if (componentId == OPTION_4) {
				if (player.getInventory().getFreeSlots() > 4) {
					player.getInventory().addItem(26589, 1);
					player.getInventory().addItem(26591, 1);
					player.getInventory().addItem(26593, 1);
					player.getInventory().addItem(26595, 1);
					player.getInventory().addItem(26657, 1);
					player.getInterfaceManager().closeChatBoxInterface();
				} else {
					player.sm("You don't have enough free space!");
					player.getInterfaceManager().closeChatBoxInterface();
				}
			} else if (componentId == OPTION_5) {
				stage = 5;
				sendOptionsDialogue("Armor Generator (Delux)", "Rune (t) Set",
					"Rune (g) Set", "Main Menu");
			}
		} else if (stage == 4) {
			if (componentId == OPTION_1) {
				if (player.getInventory().getFreeSlots() > 4) {
					player.getInventory().addItem(2623, 1);
					player.getInventory().addItem(2625, 1);
					player.getInventory().addItem(3477, 1);
					player.getInventory().addItem(2629, 1);
					player.getInventory().addItem(2627, 1);
					player.getInterfaceManager().closeChatBoxInterface();
				} else {
					player.sm("You don't have enough free space!");
					player.getInterfaceManager().closeChatBoxInterface();
				}
			} else if (componentId == OPTION_2) {
				if (player.getInventory().getFreeSlots() > 4) {
					player.getInventory().addItem(2615, 1);
					player.getInventory().addItem(2617, 1);
					player.getInventory().addItem(3476, 1);
					player.getInventory().addItem(2621, 1);
					player.getInventory().addItem(2619, 1);
					player.getInterfaceManager().closeChatBoxInterface();
				} else {
					player.sm("You don't have enough free space!");
					player.getInterfaceManager().closeChatBoxInterface();
				}
			} else if (componentId == OPTION_3) {
				stage = 1;
				sendOptionsDialogue("Armor Generator (Delux)", "NewAge Armor.",
				"Legacy Armor.", "Nevermind.");
			}
		} else if (stage == 5) {
			if (componentId == OPTION_1) {
				if (player.getInventory().getFreeSlots() > 4) {
					player.getInventory().addItem(26613, 1);
					player.getInventory().addItem(26615, 1);
					player.getInventory().addItem(26617, 1);
					player.getInventory().addItem(26619, 1);
					player.getInventory().addItem(26663, 1);
					player.getInterfaceManager().closeChatBoxInterface();
				} else {
					player.sm("You don't have enough free space!");
					player.getInterfaceManager().closeChatBoxInterface();
				}
			} else if (componentId == OPTION_2) {
				if (player.getInventory().getFreeSlots() > 4) {
					player.getInventory().addItem(26605, 1);
					player.getInventory().addItem(26607, 1);
					player.getInventory().addItem(26609, 1);
					player.getInventory().addItem(26611, 1);
					player.getInventory().addItem(26661, 1);
					player.getInterfaceManager().closeChatBoxInterface();
				} else {
					player.sm("You don't have enough free space!");
					player.getInterfaceManager().closeChatBoxInterface();
				}
			} else if (componentId == OPTION_3) {
				stage = 1;
				sendOptionsDialogue("Armor Generator (Delux)", "NewAge Armor.",
				"Legacy Armor.", "Nevermind.");
			}
		}
}
	

	@Override
	public void finish() {
	}

}
