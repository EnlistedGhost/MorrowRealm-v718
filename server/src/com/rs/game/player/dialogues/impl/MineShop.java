package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.content.cities.tzhaar.LavaMine;
import com.rs.game.player.dialogues.Dialogue;

/**
 * 
 * @author JazzyYaYaYa | Nexon
 *
 */

public class MineShop extends Dialogue {

	private static final int MINER = 13335;

	public MineShop() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Purchase Mining Items", "Buy: Gold Mining Set (322 tokkuls)",
				"Buy: Mining Ring (137 tokkuls)", "Buy: Mining Lamp (45 tokkuls))", "How to get tokkuls to buy items?",
				"I don't want to purchase anything.");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				LavaMine.HandleMiningSetReward(player);
				}
			}  if (componentId == OPTION_2) {
				LavaMine.HandleMiningRingReward(player);
				stage = 3;
			}  if (componentId == OPTION_3) {
				LavaMine.HandleMiningLampReward(player);
				stage = 69;
			}  if (componentId == OPTION_4) {
				sendEntityDialogue(
						SEND_3_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(MINER).name,
								"That's simple my big friend, mine the crust",
								"and you will be gaining mining experience and tokens.",
								"This is your way to purchase great items and gain great experience." }, IS_NPC,
						MINER, 9843);
				stage = 1;
			} if (componentId == OPTION_5) {
		player.getInterfaceManager().closeChatBoxInterface();
			}
		
	}

	@Override
	public void finish() {
	}

}
