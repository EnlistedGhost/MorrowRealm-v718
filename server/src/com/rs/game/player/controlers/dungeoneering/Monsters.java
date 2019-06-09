package com.rs.game.player.controlers.dungeoneering;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Tyluur <itstyluur@gmail.com>
 * Modified by Jaejoong Kim @ Xiduth.
 * @since 2013-09-04
 */
public enum Monsters {
	
	/**
	 * First floor npcs
	 */
	HILL_GIANT(1681, MonsterTypes.NORMAL, Floors.FIRST),
	
	PYREFIEND(8598, MonsterTypes.NORMAL, Floors.FIRST),
	
	ICE_GIANT(111, MonsterTypes.NORMAL, Floors.FIRST),
	
	NECHRYAEL(1613, MonsterTypes.NORMAL, Floors.FIRST),
	
	ASTEA_FROSTWEB_LOW(9998, MonsterTypes.BOSSES, Floors.FIRST),

	ASTEA_FROSTWEB_HIGH(9999, MonsterTypes.BOSSES, Floors.FIRST),
	
	

	/**
	 * Second floor npcs
	 */
	BLINK(12878, MonsterTypes.NORMAL, Floors.SECOND),
	
	GREATER_DEMON(83, MonsterTypes.NORMAL, Floors.SECOND),
	
	SKELETON_HERO(6103, MonsterTypes.NORMAL, Floors.SECOND),
	
	DETAIL(15174, MonsterTypes.BOSSES, Floors.SECOND),
	
	THUNDER(9999, MonsterTypes.BOSSES, Floors.SECOND),
	
	JORDAN(15172, MonsterTypes.BOSSES, Floors.SECOND);
	
	private int id;
	private MonsterTypes types;
	private Floors floor;
	
	Monsters(int id, MonsterTypes type, Floors floor) {
		this.id = id;
		this.setTypes(type);
		this.floor = floor;
	}
	
	private static final Map<Integer, Monsters> map = new HashMap<Integer, Monsters>();
	
	public static Map<Integer, Monsters> getMap() {
		return map;
	}
	
	static {
		for (Monsters monsters : Monsters.values()) {
			map.put(monsters.id, monsters);
		}
	}
	
	public static Monsters getMonster(int id) {
		return map.get(id);
	}
	
	public int getId() { 
		return id;
	}

	public MonsterTypes getTypes() {
		return types;
	}
	
	public Floors getFloor() {
		return floor;
	}

	public void setTypes(MonsterTypes types) {
		this.types = types;
	}
	
}