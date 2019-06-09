package com.rs.game.player.dialogues.impl;

import com.rs.game.Graphics;
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.Skills;
import com.rs.game.player.content.Magic;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class Pikkupstix extends Dialogue {

	public Pikkupstix() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Select a Option", "Summoning Starter Ingredients",
				"Summoning Intermediate Ingredients",
				"Summoning Master Ingredients");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				ShopsHandler.openShop(player, 23);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) {
				ShopsHandler.openShop(player, 24);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_3) {
				ShopsHandler.openShop(player, 25);
				player.getInterfaceManager().closeChatBoxInterface();
			}
		}
	}

	private void teleportPlayer(int x, int y, int z) {
		player.setNextGraphics(new Graphics(111));
		player.setNextWorldTile(new WorldTile(x, y, z));
		player.stopAll();

	}

	@Override
	public void finish() {
	}

}
