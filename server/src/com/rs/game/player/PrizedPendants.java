package com.rs.game.player;

public class PrizedPendants {

	private Player player;
	public PrizedPendants(Player p) {
		this.player = p;
	}
	
	public enum Pendants {
		
		ATTACK_PENDANT(24714, Skills.ATTACK, 1.05),
		STRENGTH_PENDANT(24716, Skills.STRENGTH, 1.05),
		DEFENCE_PENDANT(24718, Skills.DEFENCE, 1.05),
		RANGING_PENDANT(24720, Skills.RANGE, 1.05),
		MAGIC_PENDANT(24722, Skills.MAGIC, 1.05),
		PRAYER_PENDANT(24724, Skills.PRAYER, 1.05),
		RUNECRAFTING_PENDANT(24726, Skills.RUNECRAFTING, 1.05),
		CONSTRUCTION_PENDANT(24728, Skills.CONSTRUCTION, 1.05),
		DUNGEONEERING_PENDANT(24730, Skills.DUNGEONEERING, 1.05),
		CONSTITUTION_PENDANT(24732, Skills.HITPOINTS, 1.05),
		AGILITY_PENDANT(24734, Skills.AGILITY, 3.0),
		HERBLORE_PENDANT(24736, Skills.HERBLORE, 1.05),
		THIEVERY_PENDANT(24738, Skills.THIEVING, 1.05),
		CRAFTING_PENDANT(24740, Skills.CRAFTING, 1.05),
		FLETCHING_PENDANT(24742, Skills.FLETCHING, 1.05),
		SLAYING_PENDANT(24744, Skills.SLAYER, 1.05),
		HUNTING_PENDANT(24746, Skills.HUNTER, 1.05),
		MINING_PENDANT(24748, Skills.MINING, 1.05),
		SMITHING_PENDANT(24750, Skills.SMITHING, 1.05),
		FISHING_PENDANT(24752, Skills.FISHING, 1.05),
		COOKING_PENDANT(24754, Skills.COOKING, 1.05),
		FIREMAKING_PENDANT(24756, Skills.FIREMAKING, 1.05),
		WOODCUTTING_PENDANT(24758, Skills.WOODCUTTING, 1.05),
		FARMING_PENDANT(24760, Skills.FARMING, 1.05),
		SUMMONING_PENDANT(24762, Skills.SUMMONING, 1.05);
		
		int id;
		int skill;
		double modifier;
		
		Pendants(int id, int skill, double modifier) {
			this.id = id;
			this.skill = skill;
			this.modifier = modifier;
		}
		
		public double getModifier() {
			return modifier;
		}
		public int getSkillId() {
			return skill;
		}
	}
	
	public Pendants forId(int itemId) {
		for (Pendants pendant : Pendants.values()) {
			if (player.getEquipment().getAmuletId() == pendant.id) {
				return pendant;
			}
		}
		return null;
	}
	
	public boolean hasAmulet() {
		Pendants pendant = forId(player.getEquipment().getAmuletId());
		if (pendant != null) {
			return true;
		}
		return false;
	}
	
	public double getModifier() {
		Pendants pendant = forId(player.getEquipment().getAmuletId());
		if (pendant != null) {
			return pendant.getModifier();
		}
		return 1.0;
	}
	
	public int getSkill() {
		Pendants pendant = forId(player.getEquipment().getAmuletId());
		if (pendant != null) {
			return pendant.getSkillId();
		}
		return -1;
	}
	
}
