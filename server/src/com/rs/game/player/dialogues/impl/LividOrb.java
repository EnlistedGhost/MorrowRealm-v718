package com.rs.game.player.dialogues.impl;

import com.rs.game.WorldTile;
import com.rs.game.player.content.LividFarm;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class LividOrb extends Dialogue {

	public LividOrb() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Magical Orb", "I want to bunch all my 27 plants.",
				"I want to check my current Livid Points.", "Teleport to Livid Farm.", 
				"Nevermind.");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				LividFarm.OrbBunch(player);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) {
			player.sm("You have currently: "+player.lividpoints+".");
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2109,
						3943, 0));
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
			}
		} else if (stage == 4) {
			if (componentId == OPTION_1) {	
			 LividFarm.setFarming(player);
			player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) { // Skilling Locations
				LividFarm.setCrafting(player);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_3) {
				LividFarm.setMagic(player);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4)
				stage = 1;
			sendOptionsDialogue("Magical Orb", "I want to bunch all my 27 plants.",
					"I want to check my current Livid Points.", "Teleport to Livid Farm.", 
					"I want to focus experience to...",
					"Nevermind.");
		}
}
	

	@Override
	public void finish() {
	}

}
