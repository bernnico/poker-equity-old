package card;

import java.util.concurrent.ConcurrentLinkedQueue;

public class BoardGenerator {
	
	long timeStart = System.nanoTime();
	
	private long board;
	private long cardsInGame;
	private ConcurrentLinkedQueue<Long> boardList;
	
	public BoardGenerator() {
		boardList = new ConcurrentLinkedQueue<Long>();
	}
	
	private Runnable threadArg = new Runnable() {
		
		@Override public void run() {
			timeStart = System.nanoTime();
			
			
			
			Card card = new Card();
			
			long card5 = card.getCardAsLong(Image._2, Suit.h);
			long card4 = card.getCardAsLong(Image._2, Suit.k);
			long card3 = card.getCardAsLong(Image._2, Suit.s);
			long card2 = card.getCardAsLong(Image._2, Suit.c);
			long card1 = card.getCardAsLong(Image._3, Suit.h);
			
			board = card1 | card2 | card3 | card4 | card5;
			
			long lastBoard;
			
			long lastCard5 = card.getCardAsLong(Image._K, Suit.c);
			long lastCard4 = card.getCardAsLong(Image._A, Suit.h);
			long lastCard3 = card.getCardAsLong(Image._A, Suit.k);
			long lastCard2 = card.getCardAsLong(Image._A, Suit.s);
			long lastCard1 = card.getCardAsLong(Image._A, Suit.c);
			
			lastBoard = lastCard1 | lastCard2 | lastCard3 | lastCard4 | lastCard5;
			
			boardList.add(board);
			
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
								} else {
									
								}
							}
						}
					}
				}
				
				board = card1 | card2 | card3 | card4 | card5;
				
				if ((cardsInGame & board) == 0) {
					boardList.add(board);
				}
			}
			
			System.out.println("generator: " + ( System.nanoTime() - timeStart));
		}
	};
	
	public ConcurrentLinkedQueue<Long> getBourdList() {
		if (boardList.isEmpty()) {
			new Thread(threadArg).start();
		}

		return boardList;
	}
	
	public void setCardsInGame(long cardsInGame) {
		this.cardsInGame = cardsInGame;
	}

	public boolean isBourdListEmpty() {
		return boardList.isEmpty();
	}
	
	public long getNextBoard() {
		return boardList.poll();
	}
	
}


