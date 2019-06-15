package card;

import java.util.LinkedList;
import java.util.Queue;

public class BoardGenerator {
	private long board;
	private long cardsInGame;
	
	public Thread th;
	private Queue<Long> boardList = new LinkedList<Long>();
	
	public BoardGenerator() {
		
	}
	
	private Runnable threadArg = new Runnable() {
		
		@Override public void run() {
			
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
			
			int i = 1;
			
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
				i++;
				
				if ((cardsInGame & board) == 0) {
					boardList.add(board);
				}
			}
	}};
	
	public Queue<Long> getBourdList() {
		new Thread(threadArg).start();
		return boardList;
	}
	
	public Thread getThread() {
		return th;
	}

	public void setBourdList() {
		th.start();
	}
	
	public void setCardsInGame(long cardsInGame) {
		this.cardsInGame = cardsInGame;
	}

	public boolean isBoardListEmpty() {
		return boardList.isEmpty();
	}

}













