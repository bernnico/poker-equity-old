package calculator;

import java.util.Queue;

public class EquityCalculator extends Thread {
	private BoardGenerator boardGen;
	private Queue<Long[]> boardQueue;
	
	private Long[] board;
	private Long[][] playerCards; // better a list -> later
	
	
	public EquityCalculator(BoardGenerator bg) {
		this.boardGen = bg;
		this.boardQueue = boardGen.getQuee();
		this.playerCards = boardGen.getPlayerCards();
	}

	@Override 
	public void run() {
		int wins, ties;
		
		while (boardGen != null) {
			board = boardQueue.poll();
			if (board == null)  {
				boardGen.interrupt();
				continue;
			}			
			checkPairOrTreeOrFour();
			
			
		}
	}

	private void checkPairOrTreeOrFour() {
		long brd = board[0] + board[1] + board[2] + board[3];
		long pl1 = brd + playerCards[0][0];
		long pl2 = brd + playerCards[1][0];
		
		// player 1
		if ((pl1  | Defs.COMBOS) != 0) {
			if ((pl1 | Defs.FOURS) == Defs.FOURS) {
				
			}
			if ((pl1 | Defs.THREES) == Defs.THREES) {
				
			}
			if ((pl1 | Defs.PAIRS) == Defs.PAIRS) {
				
			}
			
			
		}
		// player 2
		if ((pl2  | Defs.COMBOS) != 0) {
			for (int i = 0; i < Defs.IMAGES; i++) {

			}

		}
	}
}




















