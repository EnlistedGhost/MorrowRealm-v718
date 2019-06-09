package com.rs.tools;

import java.io.File;
import java.io.IOException;

import com.rs.game.player.Player;
import com.rs.utils.SerializableFilesManager;

public class EcoReset {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		File[] chars = new File("data/playersaves/characters").listFiles();
		for (File acc : chars) {
			try {
				Player player = (Player) SerializableFilesManager.loadSerializedFile(acc);
				player.getBank().reset();
				player.getInventory().reset();
				player.getEquipment().reset();
				player.setMoneyPouch(0);
				SerializableFilesManager.storeSerializableClass(player, acc);
				System.out.println("Reset Player: "+acc.getName()+"");
			} catch (Throwable e) {
				e.printStackTrace();
				System.out.println("failed: " + acc.getName());
			}
		}
		System.out.println("Done.");
	}
	
}
