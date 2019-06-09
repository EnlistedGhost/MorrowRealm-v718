package com.rs.game.player.content.custom;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.rs.cores.CoresManager;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class TriviaBot {

	public boolean questionAnswered;
	public boolean questionActive;
	public String correctAnswer;
	
	public static ArrayList<Player> wrongAnswers = new ArrayList<Player>();
	
	public static TriviaBot instance;
	public static TriviaBot getInstance() {
		if (instance == null)
			instance = new TriviaBot();
		return instance;
	}
	
	public void start() {
		CoresManager.slowExecutor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if (World.getPlayers().size() > 1) {
					wrongAnswers.clear();
					sendTrivia(generateQuestion());
					questionAnswered = false;
				}
			}
		}, 1, 10, TimeUnit.MINUTES);
	}
	
	public void sendTrivia(String message) {
		for (Player p : World.getPlayers()) {
			if (p == null || p.hasFinished() || !p.hasStarted() || p.hasDisabledTrivia()) {
				continue;
			}
			p.sendMessage("<img=9> <col=0066FF><shad=222222>[Trivia Bot] "+ message +"");
		}
	}
	
	public void sendMessage(Player player, String message) {
		player.sendMessage("<img=9> <col=0066FF><shad=222222>[Trivia Bot] " + message);
	}
	
	public boolean verify(Player player, String answer) {
		if (player.isAdmin() || player.isWikiEditor()) {
			sendMessage(player, "You are not allowed to answer trivia questions.");
			return false;
		}
		
		if (World.getPlayers().size() < 3) {
			sendMessage(player, "<col=FF0000>There must be atleast 3 players online before being able to answer trivia.");
			return false;
		}
		
		if (questionAnswered) {
			sendMessage(player, "The question has already been answered. Better luck next time!");
			return false;
		}
		
		if (correctAnswer == null) {
			sendMessage(player, "There is not current a question needing to be answered.");
			return false;
		}
		
		if (wrongAnswers.contains(player) && !player.isOwner()) {
			sendMessage(player, "<col=FF0000>You've already guess this question wrong. Better luck next time :)");
			return false;
		}
		
		if (answer.equalsIgnoreCase(correctAnswer)) {
			if (player.getInventory().hasFreeSlots()) {
				int reward = Utils.random(50000, 100000);
				player.getInventory().addItem(995, reward);
				questionAnswered = true;
				player.setLastAnswer(Utils.currentTimeMillis());
				sendTrivia("<col=0066ff>"+player.getDisplayName()+"</col><col=006FFF> has answered correctly! Well done!");
				sendMessage(player, "You've won "+Utils.formatNumber(reward)+" coins! Good Job!");
			} else {
				sendMessage(player, "You don't have enough room in your inventory!");
			}
			return true;
		}
		wrongAnswers.add(player);
		sendMessage(player, "Sorry, you've answered the trivia incorrectly!");
		return false;
	}
	
	public void setAnswer(String answer) {
		if (answer == null)
			return;
		getInstance().correctAnswer = answer;
	}
	
	public String generateQuestion() {
		int random = Utils.random(1, 30);
		switch (random) {
			case 1:
				setAnswer("Tutorial Island");
				return "In old school runescape, where did you originaly start at?";
			case 2:
				setAnswer("Andrew Gower");
				return "Who originally made runescape? (first and last name)";
			case 3:
				setAnswer("Parallax Painter");
				return "What game did Andrew Gower first create for the Atari?";
			case 4:
				setAnswer("Wise Old Man");
				return "What npc in Guardian spawns the zombies in Zombies Onslaught minigame?";
			case 5:
				setAnswer("50");
				return "How many years is in a \"Golden Wedding Anniversary\".";
			case 6:
				setAnswer("Kristin Shepard");
				return "Who shot JR in the tv series 'Dallas'?";
			case 7:
				setAnswer("3rd generation");
				return "What does 3G stand for in mobile networking?";
			case 8:
				setAnswer("Caesars Palace");
				return "In the 2009 film 'The Hangover' which Las Vegas hotel did the bachelor party stay at?";
			case 9:
				setAnswer("Nena");
				return "Who had an 80s hit with the song entitled '99 Red Balloons'?";
			case 10:
				setAnswer("Washington DC");
				return "What is the capital of America?";
			case 11:
				setAnswer("No");
				return "Do camels store water in their humps?";
			case 12:
				setAnswer("1024");
				return "How many bytes are there in a kilobyte?";
			case 13:
				setAnswer("Amsterdam");
				return "In which city was Anne Frank's hiding place?";
			case 14:
				setAnswer("100000");
				return "In Guardian, how much health does the Party Demon have? (no commas)";
			case 15:
				setAnswer("6");
				return "What is the max prestige in Guardian?";
			case 16:
				setAnswer("rsrules");
				return "Who originaly started Rune-Server? (No spaces)";
			case 17:
				setAnswer("718");
				return "What revision is Guardian?";
			case 18:
				setAnswer("7");
				return "How many skills do u need to max in order to prestige?";
			case 19:
				setAnswer("Rellekka");
				return "What is the city known for Rock Crabs?";
			case 20:
				setAnswer("2");
				return "How many banks are there in varrock?";
			case 21:
				setAnswer("King Fox");
				return "Who is the owner of Guardian?";
			case 22:
				setAnswer("Ebay");
				return "What Company's slogan Is: 'Buy it. Sell it. Love it.'  ";
			case 23:
				setAnswer("Eldrick");
				return "What's Tiger Woods's real first name?  ";
			case 24:
				setAnswer("John");
				return "In Sex and the City, what is Mr Big’s actual first name?";
			case 25:
				setAnswer("United Kingdom");
				return "Which was the first country to issue postage stamps?";
			case 26:
				setAnswer("Red Bull");
				return "Which soft drink ‘Gives you wings’?";
			case 27:
				setAnswer("George Washington");
				return "Which US President is depicted on a US $1 bank note?";
			case 28:
				setAnswer("Apple");
				return "What computer company is named after a fruit?";
			case 29:
				setAnswer("7");
				return "In UK, how many sides does a twenty pence piece have?";
			case 30:
				setAnswer("2004");
				return "In what year did Texas Longhorns beat Arkansas Razorbacks for the first time in over 10 years? (College Footbal)";
		}
		return "";
	}
	
}
