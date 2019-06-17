package card;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BoardsList extends Thread {
	
	/* ====================TESTS ========================== */
	long timeStart;
	boolean isParallel = false;
	/* =====================================================*/
	
	private long board;
	private long cardsInGame;
	private ConcurrentLinkedQueue<Long> boardList;
	private ArrayList<Long> list;
	
	public BoardsList() {
		timeStart = System.nanoTime();
		if (isParallel)
			boardList = new ConcurrentLinkedQueue<Long>();
		else	
			list = new ArrayList<Long>();
	}
		
	@Override 
	public void run() {
		Card card = new Card();
			
		long card5 = card.getCardAsLong(Image._2, Suit.h);
		long card4 = card.getCardAsLong(Image._2, Suit.k);
		long card3 = card.getCardAsLong(Image._2, Suit.s);
		long card2 = card.getCardAsLong(Image._2, Suit.c);
		long card1 = card.getCardAsLong(Image._3, Suit.h);
			
		board = card1 | card2 | card3 | card4 | card5;
//		board = 0x001FL;
			
		long lastBoard;
			
		long lastCard5 = card.getCardAsLong(Image._K, Suit.c);
		long lastCard4 = card.getCardAsLong(Image._A, Suit.h);
		long lastCard3 = card.getCardAsLong(Image._A, Suit.k);
		long lastCard2 = card.getCardAsLong(Image._A, Suit.s);
		long lastCard1 = card.getCardAsLong(Image._A, Suit.c);
		
		lastBoard = lastCard1 | lastCard2 | lastCard3 | lastCard4 | lastCard5;
//		lastBoard = 0x000F_8000_0000_0000L;
			
		add(board);
	
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
				add(board);
			}
		}
		long timeStop = System.nanoTime()  - timeStart;
		System.out.printf("generator: %,d\n", timeStop);
	}
	
	public void generateBourdsList() {
		if (isParallel)
				super.start();
		else
				this.run();
	}
	
	public void setCardsInGame(long cardsInGame) {
		this.cardsInGame = cardsInGame;
	}
	
	public long getNext(int i) {
		if (isParallel)
			return boardList.poll();
		else
			return list.get(i);
	}
	
	public int size() {
		if (isParallel)
			return boardList.size();
		else
			return list.size();
	}
	
	public boolean isEmpty() {
		if (isParallel)
			return boardList.isEmpty();
		else
			return list.isEmpty();
	}
	
	private boolean add(long board) {
		if (isParallel)
			return boardList.add(board);
		else
			return list.add(board);
	}
	
}








