package calculator;

import java.util.Queue;

public class EquityCalculator extends Thread {
	private BoardGenerator_old boardGen;
	private Queue<Long[]> boardQueue;

	private Long[] board;
	private Long[][] playerCards; // better a list -> later

	public EquityCalculator(BoardGenerator_old bg) {
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
			if (board == null) {
				boardGen.interrupt();
				continue;
			}
			// flush
			if (board[0] != 0 && board[1] != 0 && board[2] != 0 && board[3] != 0) {

			}
			if ((pl1 | Defs.COMBOS) != 0) {
				checkPairOrTreeOrFour();
			}
		}
	}

	/*
	 * =transcription=
	 * 
	 * ___15_14_13_12 11 10_09_08_07_06_05_04_03_02_01_00 <-LSB
	 * 0x_0__0__0__0__0__0__0__0__0__0__0__0__0__0__0__0 played hand = plX
	 * 
	 * 7th byte
	 * 0b_0__0__0__0__0__0__0__0
	 * __RF F FH FL ST SE 2P P => played cards combination
	 * 
	 * 6th bytes
	 * 0b_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0 
	 * _________A K Q J T 9 8 7 6 5 4 3 2 => four
	 * 
	 * 5th bytes
	 * 0b_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0_0 
	 * _________A K Q J T 9 8 7 6 5 4 3 2 => 5th card
	 * 
	 * 
	 */

	private void checkPairOrTreeOrFour() {
		boolean fourFlag = false, fourKickerFlag = false, threeFlag = false, pairFlag = false;
		long pair = 0, three = 0, four = 0, card3 = 0, card4 = 0, card5 = 0;

		if ((pl1 & Defs.FOURS) != 0) {
			pl1 |= Defs.four;
			for (int i = 0; i < Defs.IMAGES; i++) {
				// find four
				if ((pl1 & ((Defs._A * 4) >> i)) != 0) {
					four = 0x00_01_00_00_00_00_00_00L << (long) i;
					fourFlag = true;
				}
				// find 5th card
				else if ((pl1 & ((Defs._A * 3) >> i)) != 0) {
					four = 0x00_00_01_00_00_00_00_00L << (long) i;
					fourKickerFlag = true;
				}
				if (fourFlag && fourKickerFlag) {
					pl1 &= ~Defs.four;
					pl1 |= four | card5;
					return;
				}
			}
		}
		// pair, set or fullhouse
		else if ((pl1 & Defs.PAIRS) != 0) {
			for (int i = 0; i < Defs.IMAGES; i++) {
				long localImage = Defs._A >> i;
				// find pair
				if ((pl1 & (localImage << 1)) != 0) {
					// or three
					if ((pl1 & (localImage)) != 0) {
						if (!threeFlag) {
							three = 0x00_01_00_00_00_00_00_00L << (long) i;
							pl1 |= Defs.three | three;
							threeFlag = true;
						} else {
							pair = 0x00_00_01_00_00_00_00_00L << (long) i;
							pl1 ^= Defs.three;
							pl1 |= Defs.fullHouse | pair;
							return;
						}
					} else if ((pl1 | (2 * Defs.pair)) == 0) {
						if (!pairFlag) {
							pair = 0x00_01_0_00_00_00_00_00L << (long) i;
							pl1 |= Defs.pair;
							pairFlag = true;
						} else {
							pair = 0x00_00_01_00_00_00_00_00L << (long) i;
							pl1 += Defs.pair;
						}
					} else {
						pl1 |= localImage;
					}
					pl1 &= (Defs._A * 7) >> i;
				}
			}
		}
		// find high card
		else {
			
		}

	}
}
