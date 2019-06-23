package card;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BoardsList {
	
	/* ====================TESTS ========================== */
	private long timeStart;
	/* =====================================================*/
	
	private long list2[];
	
	public BoardsList() {
		timeStart = System.nanoTime();
		
		list2 = new long[1712304];
	}
		
	public void run(long cardsInGame) {
		Card card = new Card();
			
		long card5 = card.getCardsAsLong(Image._2, Suit.s);
		long card4 = card.getCardsAsLong(Image._2, Suit.h);
		long card3 = card.getCardsAsLong(Image._2, Suit.d);
		long card2 = card.getCardsAsLong(Image._2, Suit.c);
		long card1 = card.getCardsAsLong(Image._3, Suit.s);
		long board = card1|card2|card3|card4|card5;
		
		int size = 0;
		if ((cardsInGame & board) == 0) {
			list2[size++] = board;
		}
		
		long lastBoard;
		long lastCard5 = card.getCardsAsLong(Image._K, Suit.c);
		long lastCard4 = card.getCardsAsLong(Image._A, Suit.s);
		long lastCard3 = card.getCardsAsLong(Image._A, Suit.h);
		long lastCard2 = card.getCardsAsLong(Image._A, Suit.d);
		long lastCard1 = card.getCardsAsLong(Image._A, Suit.c);
		lastBoard = lastCard1|lastCard2|lastCard3|lastCard4|lastCard5;
	
		while (board != lastBoard) {
			if (card1 != lastCard1) {
				card1 <<= 1;
			} else {                          
				if (card2 != lastCard2) {
					card2 <<= 1;
					card1 = card2 << 1;
				} else {
					if (card3 != lastCard3) {
						card3 <<= 1;
						card2 = card3 << 1;
						card1 = card2 << 1;
					} else {
						if (card4 != lastCard4) {
							card4 <<= 1;
							card3 = card4 << 1;
							card2 = card3 << 1;
							card1 = card2 << 1;
						} else {
							if (card5 != lastCard5) {
								card5 <<= 1;
								card4 = card5 << 1;
								card3 = card4 << 1;
								card2 = card3 << 1;
								card1 = card2 << 1;	
							}
						}
					}
				}
			}
			board = card1|card2|card3|card4|card5;
			
			if ((cardsInGame & board) == 0) {
				list2[size++] = board;
			}
		}
		long timeStop = System.nanoTime()  - timeStart;
		System.out.printf("generator: %,d\n", timeStop);
	}
	
	public void generateBourdsList(long cardsOnTheHands) {
		this.run(cardsOnTheHands);
	}
	
	private void setCardsInGame(long ... cardsOnTheHands) {
	}
	
	public long getNext(int i) {
		return list2[i];
	}
	

	
}








