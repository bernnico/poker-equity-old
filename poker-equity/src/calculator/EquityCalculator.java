package calculator;

import java.util.Queue;

public class EquityCalculator extends Thread {
	private Queue<Long> boardList;
	private long boardCards;
	private long playerCards[];
	private int playerEquity[];

	public EquityCalculator(Queue<Long> boardList, long boardCards, long... playerCards) {
		this.boardList = boardList;
		this.boardCards = boardCards;
		this.playerCards = playerCards;
		this.playerEquity = new int[playerCards.length];
	}


	@Override
	public void run() {

		while (!boardList.isEmpty()) {
			
			
			
			
			
		}
	}

	private void checkPairOrTreeOrFour() {
		

	}
	
	public int[] getPlayerEquity() {
		
		return null;
	}
	
}


