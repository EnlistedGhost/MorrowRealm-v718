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

public class Bartender extends Dialogue {

	public Bartender() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Select a Option", "I came to buy a special beer for Rebeard Frank.",
				"I had nothing to say, thanks and bye!");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
			if (player.getInventory().containsItem(995, 10)) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.sm("You have bought a beer for 10 coins.");
				player.getInventory().deleteItem(995, 10);
				player.getInventory().addItem(1917, 1);
				player.getInventory().refresh();
			}
			else {
				player.getInterfaceManager().closeChatBoxInterface();
				player.sm("You don't have enough money, you need atleast 10 coins.");
			}
			} else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.sm("You had nothing to say to the Bartender.");
			} 
		}

	}
	@Override
	public void finish() {
	}

}
