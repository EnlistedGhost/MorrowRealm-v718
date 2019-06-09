package com.rs.game.player.content;

import java.io.Serializable;

import com.rs.game.player.Player;

/**
 * Fully working notes tab, no nulls, no bugs.
 * @author King Fox
 * 
 */
public final class Notes implements Serializable {
	private static final long serialVersionUID = 5564620907978487391L;

	/**
	 * The player.
	 */
	private transient Player player;
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	/**
	 * Unlocks the note interface
	 * 
	 * @param player
	 *            The player to unlock the note interface for.
	 */
	public void unlock() {		 
		player.getPackets().sendIComponentSettings(34, 9, 0, 30, 2621470);
		player.getPackets().sendHideIComponent(34, 3, false);
		player.getPackets().sendHideIComponent(34, 44, false);
		 
		for (int i = 10; i < 16; i++) { 
			player.getPackets().sendHideIComponent(34, i, true); 
		}  
		player.getPackets().sendConfig(1439, -1);
		 
		for (int i = 1430; i < 1450; i++) { 
			player.getPackets().sendConfig(i, i); 
		}
		fullRefresh();
	}

	/**
	 * Fully refreshes the notes list.
	 * 
	 * @param player
	 *            The player to refresh for.
	 */
	public void fullRefresh() {
		try {
			Note note;
			for (int i = 0; i < 30; i++) {
				if (i < player.getCurNotes().size()) {
					note = player.getCurNotes().get(i);
					player.getPackets().sendGlobalString((i + 149), note.text);
				} else {
					player.getPackets().sendGlobalString(149 + i, "");
				}
			}
			player.getPackets().sendConfig(1440, getPrimaryColour(this));
			player.getPackets().sendConfig(1441, getSecondaryColour(this));
		} catch (Exception e) {
			// noting.
		}
	}

	/**
	 * Refreshes the current note.
	 * @return The note to refresh.
	 */
	public boolean refresh() {
		Note note = (Note) player.getAttributes().get("noteToEdit");
		if (note == null) {
			return false;
		}
		player.getPackets().sendGlobalString(149 + player.getCurNotes().indexOf(note), note.text);
		player.getPackets().sendConfig(1440, getPrimaryColour(this));
		player.getPackets().sendConfig(1441, getSecondaryColour(this));
		return true;
	}

	/**
	 * Refreshes the current note.
	 * @return The note to refresh.
	 */
	public boolean refresh(Note note) {
		if (note == null) {
			return false;
		}
		player.getPackets().sendGlobalString(149 + player.getCurNotes().indexOf(note), note.text);
		player.getPackets().sendConfig(1440, getPrimaryColour(this));
		player.getPackets().sendConfig(1441, getSecondaryColour(this));
		return true;
	}
	
	/**
	 * Adds a note.
	 * 
	 * @param note
	 *            The note to add.
	 * @return {@code true} if the note was added successfully.
	 */
	public boolean add(String value) {
		
		Note note = new Note(value, 0);
		
		if (player.getCurNotes().size() >= 30) {
			player.getPackets().sendGameMessage("You may only have 30 notes!", true);
			return false;
		}
		
		if (note.text.length() > 50) {
			player.getPackets().sendGameMessage("You can only enter notes up to 50 characters!", true);
			return false;
		}
		
		int id = player.getCurNotes().size();
		//player.getPackets().sendConfig(1439, id);
		player.getPackets().sendGlobalString(149 + id, note.text);
		
		player.getCurNotes().add(note);
		player.sendMessage("You've added a new note!");
		fullRefresh();
		return false;
	}

	/**
	 * Removes a note.
	 * 
	 * @param note
	 *            The note to remove.
	 * @return {@code true} if the note was removed successfully.
	 */
	public boolean remove(int slotId) {
		try {
			Note toRemove = player.getCurNotes().get(slotId);
			player.getCurNotes().remove(toRemove);
			player.sendMessage("You have removed a note!");
			player.getPackets().sendConfig(1439, 0);
			fullRefresh();
			return true;
		} catch (Exception e) {
			player.sendMessage("There was an error removing this note!");
			fullRefresh();
			return false;
		}
	}

	/**
	 * Gets the primary colour of the notes.
	 * 
	 * @param notes
	 *            The notes.
	 * @return
	 */
	public int getPrimaryColour(Notes notes) {
		int color = 0;
		for (int i = 0; i < 15; i++) {
			if (notes.player.getCurNotes().size() > (i)) {
				color += colourize(notes.player.getCurNotes().get(i).colour, i);
			}
		}
		return color;
	}

	/**
	 * Gets the secondary colour of the notes.
	 * 
	 * @param notes
	 *            The notes.
	 * @return
	 */
	public static int getSecondaryColour(Notes notes) {
		int color = 0;
		for (int i = 0; i < 15; i++) {
			if (notes.player.getCurNotes().size() > (i + 16)) {
				color += colourize(notes.player.getCurNotes().get(i + 16).colour, i);
			}
		}
		return color;
	}

	/**
	 * Gets the colour value of a note.
	 * 
	 * @param colour
	 *            The colour.
	 * @param noteId
	 *            The note id.
	 * @return The colour value.
	 */
	public static int colourize(int colour, int noteId) {
		return (int) (Math.pow(4, noteId) * colour);
	}

	/**
	 * Represents a note on widget 34.
	 * 
	 * @author `Discardedx2
	 */
	public static final class Note implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 9173992500345447484L;
		/**
		 * This note's text.
		 */
		private String text;
		/**
		 * This note's colour.
		 */
		private int colour;

		public Note(String text, int colour) {
			this.text = text;
			this.colour = colour;
		}

		public String getText() {
			return text;
		}

		public int getColour() {
			return colour;
		}

		public void setText(String text) {
			this.text = text;
		}

		public void setColour(int colour) {
			this.colour = colour;
		}
	}
}
