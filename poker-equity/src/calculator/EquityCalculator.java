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

	long brd = board[0] + board[1] + board[2] + board[3];
	long pl1 = brd + playerCards[0][0];
	long pl2 = brd + playerCards[1][0];
	
	@Override 
	public void run() {
		int wins, ties;
		
		while (boardGen != null) {
			board = boardQueue.poll();
			if (board == null)  {
				boardGen.interrupt();
				continue;
			}
			//flush
			if (board[0] != 0
					&& board[1] != 0
					&& board[2] != 0
					&& board[3] != 0) {
			}
			if ((pl1  | Defs.COMBOS) != 0) {
				checkPairOrTreeOrFour();
			}
		}
	}

	private void checkPairOrTreeOrFour() {
			if ((pl1 | Defs.FOURS) == Defs.FOURS) {
				for (int i = 0; i < Defs.IMAGES; i++) {
					if ((pl1 & (Defs._A >> i)) != 0) {
						
					}
				}
			}
			if ((pl1 | Defs.THREES) == Defs.THREES) {
				
			}
			if ((pl1 | Defs.PAIRS) == Defs.PAIRS) {
				
			}			
		
	}
}




















