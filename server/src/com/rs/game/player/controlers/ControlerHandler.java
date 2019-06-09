package com.rs.game.player.controlers;

import java.util.HashMap;

import com.rs.game.minigames.BrimhavenAgility;
import com.rs.game.minigames.RefugeOfFear;
import com.rs.game.minigames.clanwars.FfaZone;
import com.rs.game.minigames.clanwars.RequestController;
import com.rs.game.minigames.clanwars.WarControler;
import com.rs.game.minigames.creations.StealingCreationGame;
import com.rs.game.minigames.creations.StealingCreationLobby;
import com.rs.game.minigames.duel.DuelArena;
import com.rs.game.minigames.duel.DuelControler;
import com.rs.game.minigames.zombies.WiseOldMan;
import com.rs.game.player.content.worldboss.BossArena;
import com.rs.game.player.content.worldboss.LobbyArea;
import com.rs.game.player.controlers.castlewars.CastleWarsPlaying;
import com.rs.game.player.controlers.castlewars.CastleWarsWaiting;
import com.rs.game.player.controlers.custom.Dungeoneering;
import com.rs.game.player.controlers.custom.PartyDemon;
import com.rs.game.player.controlers.custom.PuroPuro;
import com.rs.game.player.controlers.custom.SlayerControler;
import com.rs.game.player.controlers.dungeoneering.RuneDungGame;
import com.rs.game.player.controlers.dungeoneering.RuneDungLobby;
import com.rs.game.player.controlers.events.DeathEvent;
import com.rs.game.player.controlers.fightpits.FightPitsArena;
import com.rs.game.player.controlers.fightpits.FightPitsLobby;
import com.rs.game.player.controlers.pestcontrol.PestControlGame;
import com.rs.game.player.controlers.pestcontrol.PestControlLobby;
import com.rs.utils.Logger;

public class ControlerHandler {

	private static final HashMap<Object, Class<Controler>> handledControlers = new HashMap<Object, Class<Controler>>();

	@SuppressWarnings("unchecked")
	public static final void init() {
		try {
			handledControlers.put("Wilderness", (Class<Controler>) Class.forName(Wilderness.class.getCanonicalName()));
			handledControlers.put("Kalaboss", (Class<Controler>) Class.forName(Kalaboss.class.getCanonicalName()));
			handledControlers.put("GodWars", (Class<Controler>) Class.forName(GodWars.class.getCanonicalName()));
			handledControlers.put("ZGDControler", (Class<Controler>) Class.forName(ZGDControler.class.getCanonicalName()));
			handledControlers.put("TutorialIsland", (Class<Controler>) Class.forName(TutorialIsland.class.getCanonicalName()));
			handledControlers.put("StartTutorial", (Class<Controler>) Class.forName(StartTutorial.class.getCanonicalName()));
			handledControlers.put("DuelArena", (Class<Controler>) Class.forName(DuelArena.class.getCanonicalName()));
			handledControlers.put("DuelControler", (Class<Controler>) Class.forName(DuelControler.class.getCanonicalName()));
			handledControlers.put("CorpBeastControler", (Class<Controler>) Class.forName(CorpBeastControler.class.getCanonicalName()));
			handledControlers.put("DTControler", (Class<Controler>) Class.forName(DTControler.class.getCanonicalName()));
			handledControlers.put("JailControler", (Class<Controler>) Class.forName(JailControler.class.getCanonicalName()));
			handledControlers.put("CastleWarsPlaying", (Class<Controler>) Class.forName(CastleWarsPlaying.class.getCanonicalName()));
			handledControlers.put("CastleWarsWaiting", (Class<Controler>) Class.forName(CastleWarsWaiting.class.getCanonicalName()));
			handledControlers.put("NewHomeControler", (Class<Controler>) Class.forName(NewHomeControler.class.getCanonicalName()));
			handledControlers.put("clan_wars_request", (Class<Controler>) Class.forName(RequestController.class.getCanonicalName()));
			handledControlers.put("clan_war", (Class<Controler>) Class.forName(WarControler.class.getCanonicalName()));
			handledControlers.put("RuneDung", (Class<Controler>) Class.forName(RuneDungGame.class.getCanonicalName()));
			handledControlers.put("RuneDungLobby", (Class<Controler>) Class.forName(RuneDungLobby.class.getCanonicalName()));
			handledControlers.put("clan_wars_ffa", (Class<Controler>) Class.forName(FfaZone.class.getCanonicalName()));
			handledControlers.put("NomadsRequiem", (Class<Controler>) Class.forName(NomadsRequiem.class.getCanonicalName()));
			handledControlers.put("BorkControler", (Class<Controler>) Class.forName(BorkControler.class.getCanonicalName()));
			handledControlers.put("BrimhavenAgility", (Class<Controler>) Class.forName(BrimhavenAgility.class.getCanonicalName()));
			handledControlers.put("FightCavesControler", (Class<Controler>) Class.forName(FightCaves.class.getCanonicalName()));
			handledControlers.put("ImpossibleJadControler", (Class<Controler>) Class.forName(ImpossibleJad.class.getCanonicalName()));
			handledControlers.put("FightKilnControler", (Class<Controler>) Class.forName(FightKiln.class.getCanonicalName()));
			handledControlers.put("FightPitsLobby", (Class<Controler>) Class.forName(FightPitsLobby.class.getCanonicalName()));
			handledControlers.put("FightPitsArena", (Class<Controler>) Class.forName(FightPitsArena.class.getCanonicalName()));
			handledControlers.put("PestControlGame", (Class<Controler>) Class.forName(PestControlGame.class.getCanonicalName()));
			handledControlers.put("PestControlLobby", (Class<Controler>) Class.forName(PestControlLobby.class.getCanonicalName()));
			handledControlers.put("Barrows", (Class<Controler>) Class.forName(Barrows.class.getCanonicalName()));
			handledControlers.put("RefugeOfFear", (Class<Controler>) Class.forName(RefugeOfFear.class.getCanonicalName()));
			handledControlers.put("Falconry", (Class<Controler>) Class.forName(Falconry.class.getCanonicalName()));
			handledControlers.put("QueenBlackDragonControler",(Class<Controler>) Class.forName(QueenBlackDragonController.class.getCanonicalName()));
			handledControlers.put("HouseControler", (Class<Controler>) Class.forName(HouseControler.class.getCanonicalName()));
			handledControlers.put("RandomEvent", (Class<Controler>) Class.forName(RandomEvent.class.getCanonicalName()));
			handledControlers.put("DeathEvent", (Class<Controler>) Class.forName(DeathEvent.class.getCanonicalName()));
			handledControlers.put("SorceressGarden", (Class<Controler>) Class.forName(SorceressGarden.class.getCanonicalName()));
			handledControlers.put("CrucibleControler", (Class<Controler>) Class.forName(CrucibleControler.class.getCanonicalName()));
			handledControlers.put("StealingCreationsGame", (Class<Controler>) Class.forName(StealingCreationGame.class.getCanonicalName()));
			handledControlers.put("StealingCreationsLobby", (Class<Controler>) Class.forName(StealingCreationLobby.class.getCanonicalName()));
			handledControlers.put("SlayerControler", (Class<Controler>) Class.forName(SlayerControler.class.getCanonicalName()));
			handledControlers.put("PuroPuro", (Class<Controler>) Class.forName(PuroPuro.class.getCanonicalName()));
			handledControlers.put("PartyDemon", (Class<Controler>) Class.forName(PartyDemon.class.getCanonicalName()));
			handledControlers.put("Dungeoneering", (Class<Controler>) Class.forName(Dungeoneering.class.getCanonicalName()));
			handledControlers.put("WiseOldMan", (Class<Controler>) Class.forName(WiseOldMan.class.getCanonicalName()));
			handledControlers.put("LobbyArea", (Class<Controler>) Class.forName(LobbyArea.class.getCanonicalName()));
			handledControlers.put("BossArena", (Class<Controler>) Class.forName(BossArena.class.getCanonicalName()));

		} catch (Throwable e) {
			Logger.handle(e);
		}
	}

	public static final void reload() {
		handledControlers.clear();
		init();
	}

	public static final Controler getControler(Object key) {
		if (key instanceof Controler)
			return (Controler) key;
		Class<Controler> classC = handledControlers.get(key);
		if (classC == null)
			return null;
		try {
			return classC.newInstance();
		} catch (Throwable e) {
			Logger.handle(e);
		}
		return null;
	}
}
