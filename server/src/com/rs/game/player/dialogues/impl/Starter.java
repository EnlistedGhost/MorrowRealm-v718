package com.rs.game.player.dialogues.impl;

import com.rs.game.Graphics;
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.Inventory;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.DonatorActions;
import com.rs.game.player.actions.ShipTravelAction;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.cities.tzhaar.LavaMine;
import com.rs.game.player.content.custom.StarterItems;
import com.rs.game.player.content.interfaces.WelcomeBook;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.controlers.NomadsRequiem;
import com.rs.game.player.dialogues.Dialogue;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class Starter extends Dialogue {

	public Starter() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Pick a Starter Type", 
				"I would like a Fighter's Starter",
				"I would like an Archer's Starter",
				"I would like a Magician's Starter");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				StarterItems.giveStarter(player, StarterItems.FIGHTER);
				player.unlock();
				WelcomeBook.openRules(player);
			} else if (componentId == OPTION_2) {
				StarterItems.giveStarter(player, StarterItems.ARCHER);
				player.unlock();
				WelcomeBook.openRules(player);
			} else if (componentId == OPTION_3) {
				StarterItems.giveStarter(player, StarterItems.MAGICIAN);
				player.unlock();
				WelcomeBook.openRules(player);

			} 
		}
	}

	@Override
	public void finish() {
	}

}
