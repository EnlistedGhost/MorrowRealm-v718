package com.rs.game.player.controlers.dungeoneering;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.rs.game.WorldTile;

/**
 * The enumeration containing all of the information about the dungeoneering portals.
 * 
 * @author Tyluur <itstyluur@gmail.com>
 * @since 2013-01-02
 */
public enum Portals {

	FIRST(14, 7, 17, 7),
	SECOND(14, 8, 17, 8),
	THIRD(7, 14, 7, 18),
	FOURTH(8, 14, 8, 18),
	FIFTH(7, 18, 7, 14),
	SIXTH(8, 18, 8, 14),
	SEVENTH(23, 14, 23, 17),
	EIGTH(23, 17, 23, 14),
	NINTH(24, 17, 24, 14),
	TENTH(24, 14, 24, 17),
	ELEVENTH(30, 23, 33, 23),
	TWELFTH(30, 24, 33, 24),
	THIRTEENTH(33, 24, 30, 24),
	FOURTEENTH(33, 23, 30, 23),
	FIFTEENTH(14, 23, 17, 23),
	SIXTEENTH(17, 23, 14, 23),
	SEVENTEENTH(14, 24, 17, 24),
	EIGHTEENTH(17, 24, 14, 24),
	NINETEENTH(30, 8, 33, 8),
	TWENTIETH(33, 8, 30, 8),
	TWENTYFIRST(30, 7, 33, 7),
	TWENTYSECOND(33, 7, 30, 7),
	TWENTYTHIRD(40, 14, 40, 17),
	TWENTYFOURTH(40, 17, 40, 14),
	TWENTYFIFTH(39, 14, 39, 17),
	TWENTYSIXTH(39, 17, 39, 14),
	TWENTYSEVENTH(39, 28, 39, 33),
	TWENTYEIGTH(39, 33, 39, 28),
	TWENTYNINTH(33, 34, 28, 34),
	THIRTY(28, 34, 33, 34),
	THIRTYFIRST(23, 33, 23, 30),
	THIRTYSECOND(23, 30, 23, 33),
	THIRTYTHIRD(17, 34, 12, 34),
	THIRTYFOURTH(7, 33, 7, 30),
	THIRTYFIFTH(24, 46, 24, 49),
	THIRTYSIXTH(24, 49, 24, 46),
	THIRTYSEVENTH(39, 46, 39, 49),
	THIRTYEIGTH(39, 49, 39, 46);
	
	private WorldTile spawnTile;
	private WorldTile tile;
	
	Portals(int... values) {
		this.spawnTile = new WorldTile(values[0], values[1], 0);
		this.tile = new WorldTile(values[2], values[3], 0);
	}
	
	public WorldTile getTile() {
		return tile;
	}

	public WorldTile spawnTile() {
		return spawnTile;
	}

	private static final Map<WorldTile, Portals> map = new HashMap<WorldTile, Portals>();
	
	static {
		for (Portals doors : Portals.values())
			map.put(doors.spawnTile(), doors);
	}
	
	public static Map<WorldTile, Portals> getMap() {
		return map;
	}
	
	public static Portals forId(WorldTile worldTile) {
		return map.get(worldTile);
	}

	public static Portals forId(int x, int y) {
		Iterator<Portals> iterator = map.values().iterator();
		while(iterator.hasNext()) {
			WorldTile tile = iterator.next().spawnTile;
			if (tile.getX() == x && tile.getY() == y)
				return iterator.next();
		}
		return null;
	}
	

}
