package com.rs.game.player.content.custom;

import com.rs.game.player.Player;

public class RightsManager {

	public enum Ranks {
		// ("TITLE", "COLOR", "SHADE", RIGHTS);
		Owner("Owner", "FFFF00", "AF0000", 7),
		Admin("Admin", "FF00FF", "DD00DD", 2),
		Mod("Mod", "FF9500", "ff4400", 1),
		R_Donator("R. Donor", "FF0000", "AA0000", 4),
		S_Donator("S. Donor", "0085F2", "0000FF", 5),
		E_Donator("E. Donor", "00FF00", "00AA00", 6),
		GFX_Artist("GFX Artist", "FF00AF", "AA00AA", 8),
		Support("Support", "", "", 9),
		Wiki_Editor("Wiki Editor", "00FF00", "00AA00", 10);
		
		int playerRights;
		String title, color, shade;
		Ranks(String title, String color, String shade, int rights) {
			this.title = title;
			this.color = color;
			this.shade = shade;
			this.playerRights = rights;
		}
		
		public int getRights() {
			return playerRights;
		}
		public String getColor() {
			return color;
		}
		public String getShade() {
			return shade;
		}
		public String getTitle() {
			return title;
		}
		public String getColorAndShade() {
			return "<shad="+shade+"><col="+color+">";
		}
	}
	
	public static Ranks forRights(int rights) {
		for (Ranks t : Ranks.values()) {
			if (t.getRights() == rights) {
				return t;
			}
		}
		return null;
	}
	
	public static String getInfo(Player player) {
		int crownId = player.getRights() == 1 ? 0 : player.getRights() == 2 ? 1 : player.getRights();
		Ranks t = forRights(player.getRights());
		if (player.isAdmin())
			t = Ranks.Admin;
		if (player.isOwner()) {
			t = Ranks.Owner;
			crownId = 1;
			return "["+t.getColorAndShade() + t.getTitle()+"</col></shad>] <img="+crownId+">"+"<img="+crownId+">"+player.getDisplayName()+"";
		}
		if (t != null) { 
			return "["+t.getColorAndShade() + t.getTitle()+"</col></shad>] <img="+crownId+">"+player.getDisplayName()+"";
		}
		return "[Player] "+player.getDisplayName()+"";
	}
	
}
