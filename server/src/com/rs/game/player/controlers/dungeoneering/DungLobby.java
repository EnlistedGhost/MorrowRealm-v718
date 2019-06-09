package com.rs.game.player.controlers.dungeoneering;

import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import com.rs.cores.CoresManager;
import com.rs.game.RegionBuilder;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;

/**
 * 
 * @author Tyluur <itstyluur@gmail.com>
 * @since 2013-01-02
 */
public class DungLobby {

	private LobbyTick timer;

	private final CopyOnWriteArrayList<Player> players = new CopyOnWriteArrayList<Player>();

	private final Object lock = new Object();

	private static final DungLobby lobby = new DungLobby();

	public static DungLobby getLobby() {
		return lobby;
	}

	public class LobbyTick extends TimerTask {

		private int seconds;

		@Override
		public void run() {
			try {
				synchronized (lock) {
					if (getPlayerSize() == 0) {
						timer.cancel();
						return;
					}
					if (seconds == 15) {
						sendPlayersToGame();
					}
					seconds++;
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}

		public int getSeconds() {
			return seconds;
		}

	}

	public void sendPlayersToGame() {
		CopyOnWriteArrayList<Player> playing = new CopyOnWriteArrayList<Player>();
		int[] boundChunks = RegionBuilder.findEmptyChunkBound(20, 20); 
		int maxCount = 25;
		synchronized (lock) {
			int count = 0;
			for (Player player : players) {
				if (++count > maxCount) break;
				playing.add(player);
				player.getControlerManager().forceStop();
				players.remove(player);
			}
		}
		synchronized(playing) {
			Dungeoneering dungeoneering = new Dungeoneering(playing, boundChunks);
			for (Player pl : playing) {
				pl.getControlerManager().startControler("RuneDung", dungeoneering);
			}
		}
		for (Player pl : players) {
			pl.sm("The maximum capacity of players in a game is " + maxCount + ". You could not yet enter.");
			CoresManager.fastExecutor.scheduleAtFixedRate(timer = new LobbyTick(), 0, 1000);
		}
	}

	public void enterLobby(Player player) {
		int maxCount = 25;
		synchronized(lock) {
			if (players.size() == 0)
				CoresManager.fastExecutor.scheduleAtFixedRate(timer = new LobbyTick(), 0, 1000);
			player.setNextWorldTile(new WorldTile(3460, 3720, 0));
			getPlayers().add(player);
			player.getPackets().sendGameMessage("You enter the lobby and wait your turn to be a part of the game.", true);
			player.getPackets().sendGameMessage("The maximum capacity of players in a game is " + maxCount + ".", true);
		}
	}

	public void remove(Player player) {
		synchronized (lock) {
			players.remove(player);
		}
	}

	public int getPlayerSize() {
		return players.size();
	}

	public CopyOnWriteArrayList<Player> getPlayers() {
		return players;
	}

	public LobbyTick getTimer() {
		return timer;
	}

}
