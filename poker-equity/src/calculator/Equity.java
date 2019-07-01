package calculator;

import card.BoardsList;
import player.Player;

public class Equity {
	private int cores;
	private RoundChecker roundCheckerThreads[];
	private Player players[];
	private BoardsList bg = new BoardsList();
	
	
	
	public Equity(Player players[]) {
		this.cores = Runtime.getRuntime().availableProcessors();
		this.roundCheckerThreads = new RoundChecker[cores / 2];
		this.players = players;
		
	}
	
	
	public int[] getEquity() throws InterruptedException {
		long list[] = bg.generateBourdsList(players);
		
		for (int i = 0; i < roundCheckerThreads.length; i++) {
			roundCheckerThreads[i] = new RoundChecker(players, list, i);
			roundCheckerThreads[i].start();
		}
		
		for (int i = 0; i < roundCheckerThreads.length; i++) {
			roundCheckerThreads[i].join();
		}
		
		int equity[] = new int[players.length + 1];
		
		for (int i = 0; i < roundCheckerThreads.length; i++) {
			equity = roundCheckerThreads[i].getPlayerEquity();
		}
		
		
		return null;
	}

}









