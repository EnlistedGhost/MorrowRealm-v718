package com.rs.game.minigames;

import java.util.Random;

import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.Hunter.DynamicFormula;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class Implings {

	public static final Animation CAPTURE_ANIMATION = new Animation(6606);
	
	public static void enterPuroPuro(Player player) {
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2591, 4317, 0));
		player.sendMessage("Welcome to Puro-Puro! Have fun and catch some imps!");
		if (player.getEquipment().getWeaponId() != 10010 && !player.getInventory().containsItem(10010, 1)) {
			player.getInventory().addItem(new Item(10010, 1));
			player.sendMessage("You have recieved a butterfly net.");
		}
	}
	
	public enum Imps {
		// int id, level, exp, reward, int rate, lock delay
		  BABY_IMPLING(6055, 1, 20, new Item(995, 1000), 11238, 4),
		 YOUNG_IMPLING(6056, 20, 48, new Item(995, 2000), 11240, 4),
	   GOURMET_IMPLING(6057, 28, 82, new Item(995, 3000), 11242, 4),
		 EARTH_IMPLING(6058, 36, 126, new Item(995, 4000), 11244, 4),
	   ESSENCE_IMPLING(6059, 42, 160, new Item(995, 5000), 11246, 4),
	   ELECTIC_IMPLING(6060, 50, 205, new Item(995, 6000), 11248, 4),
		NATURE_IMPLING(6061, 58, 250, new Item(995, 8000), 11250, 4),
		MAGPIE_IMPLING(6062, 65, 289, new Item(995, 9000), 11252, 4),
		 NINJA_IMPLING(6053, 73, 339, new Item(995, 10000), 11254, 4),
		DRAGON_IMPLING(6054, 83, 390, new Item(995, 12000), 11256, 4),;
		//KINGLY_IMPLING(1, 91, 450, new Item(995, 15000), 75, 4);
		
		int id, level, jarId, lockDelay;
		Item reward;
		double exp;
		
		Imps(int id, int level, double exp, Item reward, int jarId, int lockDelay) {
			this.id = id;
			this.level = level;
			this.exp = exp;
			this.jarId = jarId;
			this.reward = reward;
		}
	}
	
	public static void captureImp(final Player player, final NPC npc) {
		if (player.getLockDelay() > Utils.currentTimeMillis())
			return;
		if (player.getEquipment().getWeaponId() != 10010 || !player.getInventory().containsItem(11260, 1)) {
			player.sendMessage("You must have a net equipped and atleast 1 jar to catch an imp.");
			return;
		}
		for (final Imps imp : Imps.values()) {
			if (npc.getId() == imp.id) {
				if (player.getSkills().getLevelForXp(Skills.HUNTER) < imp.level) {
					player.sendMessage("You need atleast "+imp.level+" hunter to catch this imp.");
					return;
				}
				player.setNextAnimation(CAPTURE_ANIMATION);
				player.getInventory().deleteItem(11260, 1);
				player.getInventory().addItem(imp.jarId, 1);
				player.getSkills().addXp(Skills.HUNTER, imp.exp);
				player.sendMessage("You swing your net and capture the "+npc.getDefinitions().getName()+"!");
				return;	
			}
		}
	}
	
	public static boolean openImpJar(Player player, int itemId) {
		for (Imps imp : Imps.values()) {
			if (imp.jarId == itemId) {
				if (player.getInventory().containsItem(itemId, 1)) {
					player.getInventory().deleteItem(imp.jarId, 1);
					player.getInventory().addItem(imp.reward);
					player.sendMessage("You open the jar and receive x"+Utils.formatNumber(imp.reward.getAmount())+" "+imp.reward.getDefinitions().getName()+"!");
					return true;
				}
				return false;
			}
		}
		return false;
	}
	
	public static boolean isSuccessful(Player player, int dataLevel) {
		int hunterlevel = player.getSkills().getLevel(Skills.HUNTER);
		int level = Utils.random(hunterlevel);
		double ratio = level / (Utils.random(dataLevel + 4) + 1);
		return Math.round(ratio * dataLevel) < dataLevel;
	}
	
}
