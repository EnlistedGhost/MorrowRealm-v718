package com.rs.game.player.content.cities.tzhaar;

import com.rs.game.Animation;
import com.rs.game.WorldTile;
import com.rs.game.npc.others.LivingRock;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.mining.MiningBase;
import com.rs.utils.Utils;
/**
 * 
 * @author JazzyYaYaYa | Nexon
 *
 */
public class LavaMine extends MiningBase {
	public static final WorldTile Lava_Tele = new WorldTile(2182,5665, 0);
	private LivingRock rock;

	public LavaMine(LivingRock rock) {
		this.rock = rock;
	}
	public static void HandleMiningLampReward(Player player) {
		if (player.getInventory().containsItem(6529, 45)) {
		player.getInventory().deleteItem(6529, 45);
		player.getInventory().addItem(23789, 1);
		player.getInventory().refresh();
		player.sm("You have bought Mining Experience -lamp for 45 tokkul.");
		player.getInterfaceManager().closeChatBoxInterface();
	}
		else {
			player.getInterfaceManager().closeChatBoxInterface();
			player.sm("You don't have enough tokkuls, you need 322 tokkul.");
		}
		}
	
	public static void HandleMiningRingReward(Player player) {
		if (player.getInventory().containsItem(6529, 137)) {
		player.getInventory().deleteItem(6529, 137);
		player.getInventory().addItem(21413, 1);
		player.getInventory().refresh();
		player.sm("You have bought Mining Ring for 137 tokkul.");
		player.getInterfaceManager().closeChatBoxInterface();
	}
		else {
			player.getInterfaceManager().closeChatBoxInterface();
			player.sm("You don't have enough tokkuls, you need 322 tokkul.");
		}
		}
	
	public static void HandleMiningSetReward(Player player) {
		if (player.getInventory().containsItem(6529, 322)) {
		player.getInventory().deleteItem(6529, 322);
		player.getInventory().addItem(20787, 1);
		player.getInventory().addItem(20788, 1);
		player.getInventory().addItem(20789, 1);
		player.getInventory().addItem(20790, 1);
		player.getInventory().addItem(20791, 1);
		player.getInventory().refresh();
		player.sm("You have bought Gold Mining Set for 322 tokkul.");
		player.getInterfaceManager().closeChatBoxInterface();
	}
		else {
			player.getInterfaceManager().closeChatBoxInterface();
			player.sm("You don't have enough tokkuls, you need 322 tokkul.");
		}
		}
	
	
	
	@Override
	public boolean start(Player player) {
		if (!checkAll(player))
			return false;
		setActionDelay(player, getMiningDelay(player));
		return true;
	}

	private int getMiningDelay(Player player) {
		int oreBaseTime = 50;
		int oreRandomTime = 20;
		int mineTimer = oreBaseTime
				- player.getSkills().getLevel(Skills.MINING)
				- Utils.getRandom(pickaxeTime);
		if (mineTimer < 1 + oreRandomTime)
			mineTimer = 1 + Utils.getRandom(oreRandomTime);
		mineTimer /= player.getAuraManager().getMininingAccurayMultiplier();
		return mineTimer;
	}

	private boolean checkAll(Player player) {
		if (!setPickaxe(player)) {
			player.getPackets().sendGameMessage(
					"You need a pickaxe to mine this rock.");
			return false;
		}
		if (!hasPickaxe(player)) {
			player.getPackets().sendGameMessage(
					"You dont have the required level to use this pickaxe.");
			return false;
		}
		if (!hasMiningLevel(player))
			return false;
		if (!player.getInventory().hasFreeSlots()) {
			player.getPackets().sendGameMessage(
					"Not enough space in your inventory.");
			return false;
		}
		if (!rock.canMine(player)) {
			player.getPackets()
					.sendGameMessage(
							"You must wait at least one minute before you can mine a living rock creature that someone else defeated.");
			return false;
		}
		return true;
	}

	private boolean hasMiningLevel(Player player) {
		if (73 > player.getSkills().getLevel(Skills.MINING)) {
			player.getPackets().sendGameMessage(
					"You need a mining level of 73 to mine this rock.");
			return false;
		}
		return true;
	}

	@Override
	public boolean process(Player player) {
		player.setNextAnimation(new Animation(emoteId));
		return checkRock(player);
	}

	@Override
	public int processWithDelay(Player player) {
		addOre(player);
		rock.takeRemains();
		player.setNextAnimation(new Animation(-1));
		return -1;
	}

	private void addOre(Player player) {
		player.getSkills().addXp(Skills.MINING, 25);
		player.getInventory().addItem(15263, Utils.random(5, 25));
		player.getPackets().sendGameMessage(
				"You manage to mine some living minerals.", true);
	}

	private boolean checkRock(Player player) {
		return !rock.hasFinished();
	}
}
