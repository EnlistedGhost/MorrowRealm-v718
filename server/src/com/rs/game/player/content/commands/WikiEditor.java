package com.rs.game.player.content.commands;

import java.math.BigInteger;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.DisplayNameAction;
import com.rs.game.player.content.ItemSearch;
import com.rs.game.player.content.custom.Overrides.Armour;
import com.rs.game.player.content.custom.TitleHandler;
import com.rs.game.player.content.interfaces.OverRideMenu;
import com.rs.game.player.content.interfaces.PrestigeShop;
import com.rs.utils.DisplayNames;
import com.rs.utils.Utils;

public class WikiEditor {

	public static boolean processCommand(Player player, String[] cmd, boolean console, boolean clientCommand) {
		
		/*if (cmd[0].equals("tele")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::tele coordX coordY (Optional: height)");
					return true;
				}
				try {
					int x = Integer.valueOf(cmd[1]);
					int y = Integer.valueOf(cmd[2]);
					int z = cmd.length > 3 ? Integer.valueOf(cmd[3]) : 0;
					player.resetWalkSteps();
					player.setNextWorldTile(new WorldTile(x, y, z));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::tele coordX coordY plane");
				}
			}*/
		
		/*if (clientCommand) {
			if (cmd[0].equals("tele")) {
				int x = Integer.valueOf(cmd[1]);
				int y = Integer.valueOf(cmd[2]);
				int z = Integer.valueOf(cmd[3]);
				player.setNextWorldTile(new WorldTile(x, y, z));
				return true;
			}
			return true;
		}*/
		
		if (cmd[0].equals("models")) {
			int id = Integer.parseInt(cmd[1]);
			Item item = new Item(id);
			int mmodel1 = item.getDefinitions().maleEquip1;
			int fmodel1 = item.getDefinitions().femaleEquip1;
			player.sendMessage("Male: "+mmodel1+" Female: "+fmodel1+"");
			return true;
		}

		if (cmd[0].equals("rs")) {
			int script = Integer.parseInt(cmd[1]);
			player.getPackets().sendRunScript(script);
			return true;
		}
		
		if (cmd[0].equals("cut")) {
			int scene = Integer.parseInt(cmd[1]);
			player.getPackets().sendCutscene(scene);
			return true;
		}
		
		if (cmd[0].equals("inter")) {
			int interId = Integer.parseInt(cmd[1]);
			if (interId > Utils.getInterfaceDefinitionsSize()) {
				player.getPackets().sendGameMessage("Invalid Interface Id. Max is "+Utils.getInterfaceDefinitionsSize()+"");
				return true;
			}
			player.getInterfaceManager().sendInterface(interId);
			return true;
		}
		
		if (cmd[0].equals("open")) {
			int interId = Integer.parseInt(cmd[1]);
			if (interId > Utils.getInterfaceDefinitionsSize()) {
				player.getPackets().sendGameMessage("Invalid Interface Id. Max is "+Utils.getInterfaceDefinitionsSize()+"");
				return true;
			}
			player.getInterfaceManager().sendInterface(interId);
			for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(interId); i++) {
				player.getPackets().sendIComponentText(interId, i, "" + i + "");
			}
			return true;
		}
		
		if (cmd[0].equals("test")) {
			try {
				BigInteger inte = new BigInteger(""+cmd[1]+"");
				int amount = inte.bitLength() > 31 ? Integer.MAX_VALUE : inte.intValue();
				System.out.println("(Bit Length: "+inte.bitLength()+") Input: "+inte.toString()+"");
				System.out.println("Output: "+amount+"");
				System.out.println("Formatted BigInteger: "+Utils.formatNumber(inte)+"");
				Utils.formatNumber(inte);
			} catch (NumberFormatException e) {
				player.sendMessage("Amount must be a number!");
			}
		}
		
		if (cmd[0].equals("item")) {
			int itemId = Integer.parseInt(cmd[1]);
			long amount = cmd.length < 3 ? 1 : Long.parseLong(cmd[2]);
			if (itemId < 1 || itemId > Utils.getItemDefinitionsSize()) {
				player.sendMessage("Invalid Item Id. Item Def size is "+Utils.getItemDefinitionsSize()+"");
				return true;
			}
			
			if (amount > Integer.MAX_VALUE || amount < 1) {
				amount = Integer.MAX_VALUE;
			}
			
			String itemName =  new Item(itemId).getName(); 
			
			if (itemName == "null" || itemName == null) {
				player.sendMessage("Nulled Items can't be spawned.");
				return true;
			}
			
			if (player.getInventory().add(itemId, (int)amount)) {
				player.sendMessage("Spawned: "+itemName+" (Amount: "+Utils.formatNumber(amount)+")");
			}
			return true;
		}
		
		if (cmd[0].equals("winter")) {
			int interId = Integer.parseInt(cmd[1]);
			if (interId > Utils.getInterfaceDefinitionsSize()) {
				player.getPackets().sendGameMessage("Invalid Interface Id. Max is "+Utils.getInterfaceDefinitionsSize()+"");
				return true;
			}
			player.getInterfaceManager().sendWalkableInterface(interId);
			return true;
		}
		
		if (cmd[0].equals("fullinter")) {
			int interId = Integer.parseInt(cmd[1]);
			if (interId > Utils.getInterfaceDefinitionsSize()) {
				player.getPackets().sendGameMessage("Invalid Interface Id. Max is "+Utils.getInterfaceDefinitionsSize()+"");
				return true;
			}
			player.getPackets().sendWindowsPane(interId, 0);
			return true;
		}
		
		if (cmd[0].equals("resetinter")) {
			player.closeInterfaces();
			if (player.getInterfaceManager().hasRezizableScreen()) {
				player.getInterfaceManager().sendFullScreenInterfaces();
			} else {
				player.getInterfaceManager().sendFixedInterfaces();
			}
			
		}
		if (cmd[0].equals("sound")) {
			if (cmd.length < 2) {
				player.getPackets().sendPanelBoxMessage("Use: ::sound id effect");
				return true;
			}
			try {
				int soundId = Integer.valueOf(cmd[1]);
				int effect = cmd.length > 2 ? Integer.valueOf(cmd[2]) : 1;
				player.getPackets().sendSound(soundId, 0, effect);
			} catch (NumberFormatException e) {
				player.getPackets().sendPanelBoxMessage("Use: ::sound id effect");
			}
			return true;
		}
		
		if (cmd[0].equals("music")) {
			if (cmd.length < 2) {
				player.getPackets().sendPanelBoxMessage("Use: ::music id");
				return true;
			}
			try {
				int musicId = Integer.valueOf(cmd[1]);
				player.getPackets().sendMusic(musicId);
			} catch (NumberFormatException e) {
				player.getPackets().sendPanelBoxMessage("Use: ::music id");
			}
			return true;
		}
		
		if (cmd[0].equals("gfx")) {
			try {
				int gfxId = Integer.parseInt(cmd[1]);
				int h = Integer.parseInt(cmd[2]);
				if (gfxId > Utils.getGraphicDefinitionsSize()) {
					player.getPackets().sendGameMessage("Invalid Graphics Id. Max is "+Utils.getGraphicDefinitionsSize()+"");
					return true;
				}
				player.setNextGraphics(new Graphics(gfxId, 0, h));
			} catch (NumberFormatException e) {
				player.getPackets().sendPanelBoxMessage("Use: ::gfx id");
			}
			return true;
		}
		
		if (cmd[0].equals("anim")) {
			try {
				int animId = Integer.parseInt(cmd[1]);
				if (animId > Utils.getAnimationDefinitionsSize()) {
					player.getPackets().sendGameMessage("Invalid Animation Id. Max is "+Utils.getAnimationDefinitionsSize()+"");
					return true;
				}
				player.setNextAnimation(new Animation(animId));
			} catch (NumberFormatException e) {
				player.getPackets().sendPanelBoxMessage("Use: ::anim id");
			}
			return true;
		}
		
		/*if (cmd[0].equals("master")) {
			for (int i = 0; i <= 24; i++) {
				player.getSkills().set(i, i < 24 ? 99 : 120);
				player.getSkills().setXp(i, Skills.getXPForLevel(i < 24 ? 99 : 120));
				player.sendMessage("Your "+Skills.SKILLS[i]+" has been set to "+(i < 24 ? 99 : 120)+"");
				player.getAppearence().generateAppearenceData();
			}
			player.sendMessage("Your skills have been set sucessfully.");
			return true;
		}*/
	
		if (cmd[0].equals("getid")) {
			String name = "";
			for (int i = 1; i < cmd.length; i++) {
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			}
			ItemSearch.searchForItem(player, name);
			return true;
		}
		
		/*if (cmd[0].equals("god")) {
			player.setHitpoints(Short.MAX_VALUE);
			player.getEquipment().setEquipmentHpIncrease(Short.MAX_VALUE - 990);
			for (int i = 0; i < 10; i++) {
				player.getCombatDefinitions().getBonuses()[i] = 5000;
			}
			for (int i = 14; i < player.getCombatDefinitions().getBonuses().length; i++) {
				player.getCombatDefinitions().getBonuses()[i] = 5000;
			}
			return true;
		}*/
		
		if (cmd[0].equals("tonpc")) {
			if (cmd.length < 2) {
				player.getPackets().sendPanelBoxMessage("Use: ::tonpc id(-1 for player)");
				return true;
			}
			try {
				player.getAppearence().transformIntoNPC(Integer.valueOf(cmd[1]));
			} catch (NumberFormatException e) {
				player.getPackets().sendPanelBoxMessage("Use: ::tonpc id(-1 for player)");
			}
			return true;
		}
		
		if (cmd[0].equals("switchemotes")) {
			player.setUsingAltEmotes(!player.isUsingAltEmotes());
			player.sendMessage("Alternate emotes are now "+(player.isUsingAltEmotes() ? "enabled" : "disabled")+"");
			return true;
		}
		
		if (cmd[0].equals("title")) {
			try {
				if (cmd.length < 2) {
					player.getPackets().sendGameMessage("Use: ::title id");
					return true;
				}
				TitleHandler.set(player, Integer.parseInt(cmd[1]));
			} catch (NumberFormatException e) {
				player.getPackets().sendGameMessage("Use: ::title id");
			}
			return true;
		}
		
		if (cmd[0].equals("bank")) {
			if (player.isAtWorldBoss()) {
				player.sendMessage("You are not allowed to bank from here.");
				return true;
			}
			player.stopAll();
			player.getBank().openBank();
			return true;
		}
		
		if (cmd[0].equals("spec")) {
			player.getCombatDefinitions().resetSpecialAttack();
			return true;
		}
		
		if (cmd[0].equals("setdisplay")) {
			player.sendMessage("Click on the noticeboard at home if you wish to change your display name.");
			return true;
		}
		
		if (cmd[0].equals("getcmds")) {
			player.getPackets().sendPanelBoxMessage("::cut id# - begins a cutscene");
			player.getPackets().sendPanelBoxMessage("::inter id# - opens an interface");
			player.getPackets().sendPanelBoxMessage("::sound id# - plays a sound");
			player.getPackets().sendPanelBoxMessage("::music id# = plays a song");
			player.getPackets().sendPanelBoxMessage("::open id# - opens an interface with line numbers");
			player.getPackets().sendPanelBoxMessage("::gfx id# - starts a graphic");
			player.getPackets().sendPanelBoxMessage("::anim id# - starts a player animation");
			player.getPackets().sendPanelBoxMessage("::master - maxes your stats");
			player.getPackets().sendPanelBoxMessage("::tonpc id# - transforms you into an npc");
			player.getPackets().sendPanelBoxMessage("::item id# amount - spawns an item (amount is optional)");
			player.getPackets().sendPanelBoxMessage("::switchemote - switches certain skilling emotes");
			player.getPackets().sendPanelBoxMessage("::title id# - changes your title");
			player.getPackets().sendPanelBoxMessage("::bank - anywhere banking");
			player.getPackets().sendPanelBoxMessage("::spec - maxes out your spec bar");
			player.getPackets().sendPanelBoxMessage("::setdisplay - set a display name");
			player.getPackets().sendPanelBoxMessage("::resetdisplay - resets your display name.");
			return true;
		}
		
		return false;
	}
	
	
}
