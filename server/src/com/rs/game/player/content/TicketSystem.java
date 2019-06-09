package com.rs.game.player.content;

import java.util.ArrayList;
import java.util.Iterator;

import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;

public class TicketSystem {

	public static final ArrayList<TicketEntry> tickets = new ArrayList<TicketEntry>();

	public static boolean canSubmitTicket() {
		filterTickets();
		return true;
	}

	public static void filterTickets() {
		for (Iterator<TicketEntry> it = tickets.iterator(); it.hasNext();) {
			TicketEntry entry = it.next();
			if (entry.player.hasFinished())
				it.remove();
		}
	}

	public static void removeTicket(Player player) {
		Object att = player.getAttributes().get("ticketTarget");
		if (att == null)
			return;
		TicketEntry ticket = (TicketEntry) att;
		Player target = ticket.getPlayer();
		target.setNextWorldTile(ticket.getTile());
		target.getAttributes().remove("ticketRequest");
		player.getAttributes().remove("ticketTarget");
	}

	public static void answerTicket(Player player) {
		removeTicket(player);
		filterTickets();
		if (tickets.isEmpty()) {
			player.getPackets().sendGameMessage("There are no tickets open, congratulations!");
			return;
		} else if (player.getAttributes().get("ticketTarget") != null) {
			removeTicket(player);
		}
		while (tickets.size() > 0) {
			TicketEntry ticket = tickets.get(0);// next in line
			Player target = ticket.player;
			if (target == null)
				continue; // shouldn't happen but k
			if (target.getInterfaceManager().containsChatBoxInter()
					|| target.getControlerManager().getControler() != null
					|| target.getInterfaceManager().containsInventoryInter()
					|| target.getInterfaceManager().containsScreenInter()) {
				tickets.remove(0);
				continue;
			}
			player.getAttributes().put("ticketTarget", ticket);
			player.setNextWorldTile(target);
			tickets.remove(ticket);
			player.setNextForceTalk(new ForceTalk("Hello, how may I help you today?"));
			break;
		}
	}

	public static void requestTicket(Player player) {
		if (player.getInterfaceManager().containsScreenInter()) {
			player.getPackets().sendGameMessage("Please finish what you're doing before requesting a ticket.");
			return;
		}
		if (!canSubmitTicket() || player.getAttributes().get("ticketRequest") != null || player.getControlerManager().getControler() != null) {
			player.getPackets().sendGameMessage("You cannot send a ticket yet!");
			return;
		}
		player.getAttributes().put("ticketRequest", true);
		tickets.add(new TicketEntry(player));
		for (Player p : World.getPlayers()) {
			if (p.getRights() < 1 && p.getRights() > 2) {
				continue;
			}
			p.getPackets().sendGameMessage("A ticket has been submitted by " + player.getDisplayName() + "! ::ticket to solve it!");
			p.getPackets().sendGameMessage("There is currently " + tickets.size() + " tickets active.");
		}
		player.sm("You have sent a ticket to the staff, please wait.");
	}

	public static class TicketEntry {
		private Player player;
		private WorldTile tile;

		public TicketEntry(Player player) {
			this.player = player;
			this.tile = player;
		}

		public Player getPlayer() {
			return player;
		}

		public WorldTile getTile() {
			return tile;
		}
	}
}
