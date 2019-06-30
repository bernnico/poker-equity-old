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
	
	
	public int[] getEquity() {
		long list[] = bg.generateBourdsList(players);
		
		for (int i = 0; i < 10; i++) {
			roundCheckerThreads[i] = new RoundChecker(players, list, 0);
		}
		
		
		return null;
	}

}









