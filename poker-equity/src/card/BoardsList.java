package card;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BoardsList extends Thread {
	
	/* ====================TESTS ========================== */
	private long timeStart;
	private boolean isParallel = false;
	/* =====================================================*/
	
	private long board;
	private long cardsInGame;
	private ConcurrentLinkedQueue<Long> boardList;
	private ArrayList<Long> list;
	
	private long list2[] = new long[1712304];
	private int size = 0;
	
	public BoardsList() {
		timeStart = System.nanoTime();
		if (isParallel)
			boardList = new ConcurrentLinkedQueue<Long>();
		else	
			list = new ArrayList<Long>();
	}
		
	@Override public void run() {
		// CPU i5 4200u
		// generatete all (52 nCp 5) in 140,000,000n
		// generatete all parallel in 1,529,177,367n
		// generatete all without add() in 16,000,000n
		
		Card card = new Card();
			
		long card5 = card.getCardAsLong(Image._2, Suit.h);
		long card4 = card.getCardAsLong(Image._2, Suit.k);
		long card3 = card.getCardAsLong(Image._2, Suit.s);
		long card2 = card.getCardAsLong(Image._2, Suit.c);
		long card1 = card.getCardAsLong(Image._3, Suit.h);
		board = card1|card2|card3|card4|card5;
		add(board);
			
		long lastBoard;
		long lastCard5 = card.getCardAsLong(Image._K, Suit.c);
		long lastCard4 = card.getCardAsLong(Image._A, Suit.h);
		long lastCard3 = card.getCardAsLong(Image._A, Suit.k);
		long lastCard2 = card.getCardAsLong(Image._A, Suit.s);
		long lastCard1 = card.getCardAsLong(Image._A, Suit.c);
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
//				add(board);
				
				list2[size] = board;
				size++;
			}
		}
		size++;
		long timeStop = System.nanoTime()  - timeStart;
		System.out.printf("generator: %,d\n", timeStop);
	}
	
	public void generateBourdsList(long ... cardsOnTheHands) {
		setCardsInGame(cardsOnTheHands);
		if (isParallel)
			super.start();
		else
			this.run();
	}
	
	private void setCardsInGame(long ... cardsOnTheHands) {
		this.cardsInGame = 0;
		for (int i = 0; i < cardsOnTheHands.length; i++) {
			this.cardsInGame |= cardsOnTheHands[i];
		}
	}
	
	public long getNext(int i) {
		if (isParallel)
			return boardList.poll();
		else
//			return list.get(i);
			return list2[i];
	}
	
	public int size() {
		if (isParallel)
			return boardList.size();
		else
//			return list.size();
			return size;
	}
	
	public boolean isEmpty() {
		if (isParallel)
			return boardList.isEmpty();
		else
//			return list.isEmpty();
			return !(size == 1712304);
	}
	
	private boolean add(long board) {
		if (isParallel)
			return boardList.add(board);
		else
			return list.add(board);
	}
	
}








