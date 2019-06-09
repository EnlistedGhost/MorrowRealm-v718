package com.rs.database.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.rs.database.DatabasePool;
import com.rs.game.World;

public class WebsiteIntegration {

	public static void updatePlayersOnline() {
		try {
			int worldSize = World.getPlayers().size();
			ResultSet rs = DatabasePool.executeQuery("SELECT * FROM online LIMIT 1");
			while (rs.next()) {
				rs.moveToCurrentRow();
				rs.updateInt("number", worldSize);
				rs.updateRow();
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return;
	}
	
	public static void inserToItemDatabase(int id, String name, int price) {
		try {
			ResultSet rs = DatabasePool.executeQuery("SELECT * FROM item_database");
				rs.moveToInsertRow();
				rs.updateInt("id", id);
				rs.updateString("name", name);
				rs.updateInt("price", price);
				rs.insertRow();
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return;
	}
	
}
