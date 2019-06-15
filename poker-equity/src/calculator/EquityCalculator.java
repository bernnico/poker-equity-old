package calculator;

import java.util.Queue;

public class EquityCalculator extends Thread {
	private Queue<Long> boardList;
	private long boardCards;
	private long playerHands[];
	private int playerEquity[];
	private long playerWinCards[];
	
	Defs defs = new Defs();

	public EquityCalculator(Queue<Long> boardList, long boardCards, long... playerHands) {
		this.boardList = boardList;
		this.boardCards = boardCards;
		this.playerHands = playerHands;
		this.playerEquity = new int[playerHands.length];
		this.playerWinCards = new long[playerHands.length];
	}

	@Override
	public void run() {
		
		Defs defs = new Defs();
		
		for (int i = 0; i < playerWinCards.length; i++) {
			playerWinCards[i] = playerHands[i] | boardCards;
		}

		while (!boardList.isEmpty()) {
			
			// TODO
			if (!((boardCards & defs.allHeart) != 0
					&& (boardCards & defs.allDiamond) != 0
					&& (boardCards & defs.allSpade) != 0
					&& (boardCards & defs.allClubs) != 0)) {
				checkFlush();
			}
			
			
			
			checkStraight();
			
			checkCombos();
			
			
			
			
		}
	}
	
	private void checkFlush() {
		int cnt = 0;
		for (int player = 0; player < playerWinCards.length; player++) {
			
			for (int image = 0; image < Defs.IMAGES; image = Defs.IMAGES * Defs.SUITS) {
				
				if ((boardCards & defs.allHeart) != 0) {
					if ((playerWinCards[player] & (1L << image)) != 0) {
						cnt++;
						// TODO Auto-generated method stub  << ======================================
						
					}
				}
				if ((boardCards & defs.allDiamond) != 0) {
					
				}
				if ((boardCards & defs.allSpade) != 0) {
					
				}
				if ((boardCards & defs.allClubs) != 0) {
					
				}
				
			}
		}
		
	}
	
	private void checkCombos() {
		// TODO Auto-generated method stub
	}

	private void checkStraight() {
		
		
	}

	public int[] getPlayerEquity() {
		// TODO Auto-generated method stub
		
		return null;
	}
	
}


