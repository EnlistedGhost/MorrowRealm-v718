package com.rs.game.player.dialogues.impl;

import com.rs.game.Graphics;
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.DailyChallenges;
import com.rs.game.player.Skills;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.dialogues.Dialogue;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class MrEx extends Dialogue {

	public MrEx() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Select a Option",
				"I have daily challenges to turn in.",
				"I would like to see my daily challenges board.",
				"Nevermind, i really didn't have anything smart to say.");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.sm("You haven't completed any daily challenges.");
				player.getInterfaceManager().closeChatBoxInterface();
				stage = 2;
			} else if (componentId == OPTION_2) {
				player.getPackets().sendWindowsPane(DailyChallenges.MAIN_BOARD,
						0);
				player.getInterfaceManager().closeChatBoxInterface();

			} else if (componentId == OPTION_3) {
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
