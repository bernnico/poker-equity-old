package calculator;

import card.BoardsList;
import player.Player;

public class Equity {
	private RoundChecker threads[];
	
	private BoardsList bg = new BoardsList();
	
	public Equity() {
		int cores = Runtime.getRuntime().availableProcessors() / 2;
		this.threads = new RoundChecker[cores];
		RoundChecker.setCores(cores);
	}
	
	public int[] getEquity(Player players[]) throws InterruptedException {
		long list[] = bg.generateBourdsList(players);
		
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new RoundChecker(players, list, i);
			threads[i].start();
		}
		
		for (int i = 0; i < threads.length; i++) {
			threads[i].join();
		}
		
		int equity[] = new int[players.length + 1];
		int eq[];
		
		for (int i = 0; i < threads.length; i++) {
			eq = threads[i].getPlayerEquity();
			
			for (int j = 0; j < eq.length; j++) {
				equity[j] += eq[j];
			}
		}
		
		for (int i = 0; i < players.length; i++) {
			players[i].setEquity(equity[i]);
		}
		
		return equity;
	}

}









