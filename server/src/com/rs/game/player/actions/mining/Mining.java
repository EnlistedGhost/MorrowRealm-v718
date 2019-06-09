package com.rs.game.player.actions.mining;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;

public final class Mining extends MiningBase {

	public static enum RockDefinitions {

			Clay_Ore(1, 5, 434, 10, 1, 11552, 5, 0), 
			Copper_Ore(1, 17.5, 436, 10,1, 11552, 5, 0), 
			Novite_Ore(1, 17.5, 436, 10, 1, 49766, 5, 0), 
			Tin_Ore(1, 17.5, 438, 15, 1, 11552, 5, 0), 
			Iron_Ore(15, 35, 440, 15, 1,11552, 10, 0), 
			Sandstone_Ore(35, 30, 6971, 30, 1, 11552, 10, 0), 
			Silver_Ore(20, 40, 442, 25, 1, 11552, 20, 0), 
			Coal_Ore(30, 50, 453, 50,10, 11552, 30, 0), 
			Crust_Ore(77, 125, 6529, 50, 20, -1, -1, -1),
			Granite_Ore(45, 50, 6979, 50, 10, 11552, 20,0),
			Gold_Ore(40, 60, 444, 80, 20, 11554, 40, 0),
			Mithril_Ore(55, 80, 447, 100, 20, 11552, 60, 0),
			Adamant_Ore(70, 95, 449,130, 25, 11552, 180, 0), 
			Runite_Ore(85, 125, 451, 150, 30,11552, 360, 0),
			LRC_Coal_Ore(77, 50, 453, 50, 10, -1, -1, -1),
			LRC_Gold_Ore(80, 60, 444, 40, 10, -1, -1, -1);

		private int level;
		private double xp;
		private int oreId;
		private int oreBaseTime;
		private int oreRandomTime;
		private int emptySpot;
		private int respawnDelay;
		private int randomLifeProbability;

		private RockDefinitions(int level, double xp, int oreId,
				int oreBaseTime, int oreRandomTime, int emptySpot,
				int respawnDelay, int randomLifeProbability) {
			this.level = level;
			this.xp = xp;
			this.oreId = oreId;
			this.oreBaseTime = oreBaseTime;
			this.oreRandomTime = oreRandomTime;
			this.emptySpot = emptySpot;
			this.respawnDelay = respawnDelay;
			this.randomLifeProbability = randomLifeProbability;
		}

		public int getLevel() {
			return level;
		}

		public double getXp() {
			return xp;
		}

		public int getOreId() {
			return oreId;
		}

		public int getOreBaseTime() {
			return oreBaseTime;
		}

		public int getOreRandomTime() {
			return oreRandomTime;
		}

		public int getEmptyId() {
			return emptySpot;
		}

		public int getRespawnDelay() {
			return respawnDelay;
		}

		public int getRandomLifeProbability() {
			return randomLifeProbability;
		}
	}

	private WorldObject rock;
	private RockDefinitions definitions;

	public Mining(WorldObject rock, RockDefinitions definitions) {
		this.rock = rock;
		this.definitions = definitions;
	}

	@Override
	public boolean start(Player player) {
		if (!checkAll(player)) {
                    return false;
                }
		player.getPackets().sendGameMessage("You swing your pickaxe at the rock.", true);
		setActionDelay(player, getMiningDelay(player));
		return true;
	}

	private int getMiningDelay(Player player) {
		int summoningBonus = 0;
		if (player.getFamiliar() != null) {
			if (player.getFamiliar().getId() == 7342 || player.getFamiliar().getId() == 7342) {
                            summoningBonus += 10;
                        } else if (player.getFamiliar().getId() == 6832 || player.getFamiliar().getId() == 6831) {
                            summoningBonus += 1;
                        }
		}
		int mineTimer = definitions.getOreBaseTime() - (player.getSkills().getLevel(Skills.MINING) + summoningBonus) - Utils.getRandom(pickaxeTime);
                    if (mineTimer < 1 + definitions.getOreRandomTime()) {
                        mineTimer = 1 + Utils.getRandom(definitions.getOreRandomTime());
                    }
		mineTimer /= player.getAuraManager().getMininingAccurayMultiplier();
		return mineTimer;
	}

	private boolean checkAll(Player player) {
		
		if (!hasPickaxe(player) && !player.getTools().contains(1265)) {
			player.getPackets().sendGameMessage("You need a pickaxe to mine this rock.");
			return false;
		}
		
		if (!setPickaxe(player)) {
			player.getPackets().sendGameMessage("You dont have the required level to use this pickaxe.");
			return false;
		}
		
		if (!hasMiningLevel(player)) {
			return false;
        }
		
		if (!player.getInventory().hasFreeSlots()) {
			player.getPackets().sendGameMessage("Not enough space in your inventory.");
			return false;
		}
		return true;
	}

	private boolean hasMiningLevel(Player player) {
		if (definitions.getLevel() > player.getSkills().getLevel(Skills.MINING)) {
			player.getPackets().sendGameMessage(
					"You need a mining level of " + definitions.getLevel()
							+ " to mine this rock.");
			return false;
		}
		return true;
	}
	@Override
	public boolean process(Player player) {
		if (player.isUsingAltEmotes()) {
			player.setNextAnimation(new Animation(17310));
			player.setNextGraphics(new Graphics(3304));
			return checkRock(player);
		} else {
			player.setNextAnimation(new Animation(emoteId));
		}
		return checkRock(player);
	}

	private boolean usedDeplateAurora;

	@Override
	public int processWithDelay(Player player) {
		addOre(player);
		if (definitions.getEmptyId() != -1) {
			if (!usedDeplateAurora && (1 + Math.random()) < player.getAuraManager().getChanceNotDepleteMN_WC()) {
				usedDeplateAurora = true;
			} else if (Utils.getRandom(definitions.getRandomLifeProbability()) == 0) {
				World.spawnTemporaryObject(new WorldObject(definitions.getEmptyId(), rock.getType(), rock.getRotation(), rock.getX(), rock.getY(), rock.getPlane()), definitions.respawnDelay * 600, false);
				player.setNextAnimation(new Animation(-1));
				return -1;
			}
		}
		if (!player.getInventory().hasFreeSlots() && definitions.getOreId() != -1) {
			player.setNextAnimation(new Animation(-1));
			player.getPackets().sendGameMessage("Not enough space in your inventory.");
			return -1;
		}
		return getMiningDelay(player);
	}

	private void addOre(Player player) {
		double xpBoost = 1.00;
		int idSome = 0;
		if (definitions == RockDefinitions.Granite_Ore) {
			idSome = Utils.getRandom(2) * 2;
			if (idSome == 2)
				xpBoost += 0.010;
			else if (idSome == 4)
				xpBoost += 0.025;
		} else if (definitions == RockDefinitions.Sandstone_Ore) {
			idSome = Utils.getRandom(3) * 2;
			xpBoost += idSome / 2 * 10;
		}
		
		if (hasMiningSuit(player))
			xpBoost += 0.500;
		
		double totalXp = definitions.getXp() * xpBoost;
		player.getSkills().addXp(Skills.MINING, totalXp);
		
		if (definitions.getOreId() != -1) {
			player.setOresMined(player.getOresMined() + 1);
			checkOresMined(player, player.getOresMined());
			player.getInventory().addItem(definitions.getOreId() + idSome, 1);
			String oreName = ItemDefinitions.getItemDefinitions(definitions.getOreId() + idSome).getName().toLowerCase();
			player.getPackets().sendGameMessage("You mine some " + oreName + ".", true);
			player.getGoals().increase(Skills.MINING);
		}
	}
	
	public static double getMultiplier(Player player) {
		return player.getEquipment().getRingId() == 21413 ? Settings.SKILL_RING_MOD : 1.00;
	}

	private void checkOresMined(Player player, int ores) {
		boolean toBank = player.getInventory().hasFreeSlots();
		
		if (ores == 50) {
			player.sendMessage("<col=0000FF>You have mined "+Utils.formatNumber(ores)+" ores and recieved the Golden Miner Gloves!");
			if (!toBank) {
				player.getBank().addItem(20787, 1, true);
				player.sendMessage("<col=0000FF>Golden Miner Gloves has been sent to your bank.");
			} else {
				player.getInventory().addItem(20787, 1);
			}
		}
		
		if (ores == 100) {
			player.sendMessage("<col=0000FF>You have mined "+Utils.formatNumber(ores)+" ores and recieved the Golden Miner Top!");
			if (!toBank) {
				player.getBank().addItem(20791, 1, true);
				player.sendMessage("<col=0000FF>Golden Miner Top has been sent to your bank.");
			} else {
				player.getInventory().addItem(20791, 1);
			}
		}
		
		if (ores == 150) {
			player.sendMessage("<col=0000FF>You have mined "+Utils.formatNumber(ores)+" ores and recieved the Golden Miner Legs!");
			if (!toBank) {
				player.getBank().addItem(20790, 1, true);
				player.sendMessage("<col=0000FF>Golden Miner Legs has been sent to your bank.");
			} else {
				player.getInventory().addItem(20790, 1);
			}
		}
		
		if (ores == 200) {
			player.sendMessage("<col=0000FF>You have mined "+Utils.formatNumber(ores)+" ores and recieved the Golden Miner Hat!");
			if (!toBank) {
				player.getBank().addItem(20789, 1, true);
				player.sendMessage("<col=0000FF>Golden Miner Hat has been sent to your bank.");
			} else {
				player.getInventory().addItem(20789, 1);
			}
		}
		
		if (ores == 300) {
			player.sendMessage("<col=0000FF>You have mined "+Utils.formatNumber(ores)+" ores and recieved the Golden Miner Boots!");
			if (!toBank) {
				player.getBank().addItem(20788, 1, true);
				player.sendMessage("<col=0000FF>Golden Miner Boots has been sent to your bank.");
			} else {
				player.getInventory().addItem(20788, 1);
			}
		}
	}
	
	private boolean hasMiningSuit(Player player) {
		if (player.getEquipment().getHatId() == 20789
				&& player.getEquipment().getChestId() == 20791
				&& player.getEquipment().getLegsId() == 20790
				&& player.getEquipment().getBootsId() == 20788)
			return true;
		return false;
	}

	private boolean checkRock(Player player) {
		return World.getRegion(rock.getRegionId()).containsObject(rock.getId(), rock);
	}

}
