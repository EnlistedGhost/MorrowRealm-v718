package com.rs.game.player.dialogues.impl;

import com.rs.game.World;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.player.Player;

/**
 *@Author Lonely
 */

public class SnowQueen2 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		if (player.christmas <= 2){
		sendNPCDialogue(npcId, 9827, "Keep at it, remember Corrupt Snowmen hide in snowpiles! " + player.getDisplayName() + ".");
	} else if (player.christmas == 5){
		sendNPCDialogue(npcId, 9827, "Thank you so much "+ player.getDisplayName() +" for all your help.");
	} else {
		stage = 4;
		sendNPCDialogue(npcId, 9827, "Thanks again "+ player.getDisplayName() +" for all your help.");
	}
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			if (player.christmas <= 2) {
			sendPlayerDialogue(9827, "Thanks, I'll gather the 250 SantaCoins as quickly as I can.");
			stage = 111;
			}else {
				sendPlayerDialogue(9827, "No problem, I'm just glad I could help save Christmas");
				stage = 0;
			}
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(8540, 9827, "Please take this as a token of our gratitude");
			break;
		case 1:
			stage = 2;
			sendPlayerDialogue(9827, "Thank alot! Merry Christmas!");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Merry Christmas!");
			break;
		case 3:
			stage = 112;
			sendNPCDialogue(8540, 9827, "Merry Christmas, Ho ho ho!");
			break;
		case 4:
			stage = 111;
			sendPlayerDialogue(9827, "No Problem! It was my pleasure");
			break;
		case 111:
			end();
			break;
		case 112:
			player.sendMessage("You have completed the Christmas event!");
			player.getInterfaceManager().sendInterface(1244);
			player.getPackets().sendIComponentText(1244, 27, "~MorrowRealm 2019~");
			player.getPackets().sendIComponentText(1244, 26, "You are awarded:");
			player.getPackets().sendIComponentText(1244, 25, "MorrowRealm Christmas Event of 2019!");
			player.getPackets().sendGlobalString(359, "<br>Tinsel Snake Pet</br> <br>Christmas Amulet</br> <br>Santa Hat</br>");
			player.getPackets().sendGameMessage("Congratulations! <col=FF0000>"+ player.getDisplayName() +"</col> you completed the MorrowRealm Christmas Event of 2019!");
			if (player.getInventory().getFreeSlots() < 4) {
				player.getBank().addItem(22973, 1, true);
				player.getBank().addItem(1050, 1, true);
				// player.getBank().addItem(23713, 1, true);
				player.getBank().addItem(26492, 1, true);
				// player.getInventory().deleteItem(1543, 1);
				player.sm("Some items were placed in your bank.");
				player.christmas = 6;
			} else {
				player.getInventory().addItem(22973, 1);
				player.getInventory().addItem(1050, 1);
				// player.getInventory().addItem(23715, 1);
				player.getInventory().addItem(26492, 1);
				// player.getInventory().deleteItem(1543, 1);
				player.christmas = 6;
			}
			
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}