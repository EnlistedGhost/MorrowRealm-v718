package com.rs.game.player.dialogues.impl;

import com.rs.Settings;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.content.PartyRoom;
import com.rs.game.player.controlers.TutorialIsland;
import com.rs.game.player.dialogues.Dialogue;

public class DungeonTutorial extends Dialogue {

	int npcId;
	TutorialIsland controler;
	public static final int DG_TUTOR = 9712;

	@Override
	public void start() {
		sendEntityDialogue(
				SEND_3_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(DG_TUTOR).name,
						"Dungeoneering in "+Settings.SERVER_NAME+" is really simple, all you need to do",
						"is to have this Ring of Kinship, keep it always in your inventory",
						"Before you're starting to dungeoneering." }, IS_NPC,
				DG_TUTOR, 9843);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendPlayerDialogue(9827,
					"Why should i do have the Ring of Kinship with me?");
		} else if (stage == 0) {
			stage = 1;
			sendNPCDialogue(npcId, 9827, "Are you stupid? it will help you",
					"you should really use it, but you don't have to.",
					"By using this magical ring you'll gain even more Dungeoneering -exprience.");
		} else if (stage == 1) {
			stage = 2;
			sendNPCDialogue(
					npcId,
					9827,
					"Any skill, in dungeoneering will give you XP for dungeoneering.",
					"Have fun, you'll also receive rusty coins with them you can buy",
					"some cool chaotics items at the shop of daemonhiem,",
					"sadly you must figure out the shop yourself, i'm not here to waste my time anymore.");
		} else if (stage == 2) {
			stage = 3;
			sendEntityDialogue(SEND_1_TEXT_CHAT, new String[] { "",
					"The dungeoneering tutor handles you Ring of Kinship." },
					IS_ITEM, 15707, 1);
			player.getInventory().addItem(15707, 1);
		} else {
			player.sm("You have received Ring of Kinship already.");
			end();
		}
	}

	@Override
	public void finish() {

	}

}
