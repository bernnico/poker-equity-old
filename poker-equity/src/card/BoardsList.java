package card;

import player.Player;

public class BoardsList {
	
	/* ========================= TESTS ========================== */
	private long timeStart;
	/* ========================================================== */
	
	private long list[];
	private long cardsInGame;
	
	public BoardsList() {
		timeStart = System.nanoTime();
		
		list = new long[1712304];
	}
		
	private void run() {
		timeStart = System.nanoTime();
			
		long card5 = 0x01L; //Card.getCardsAsLong(Image._2, Suit.s);
		long card4 = 0x02L; //Card.getCardsAsLong(Image._2, Suit.h);
		long card3 = 0x04L; //Card.getCardsAsLong(Image._2, Suit.d);
		long card2 = 0x08L; //Card.getCardsAsLong(Image._2, Suit.c);
		long card1 = 0x10L; //Card.getCardsAsLong(Image._3, Suit.s);
		
		long board = card1|card2|card3|card4|card5;
		
		long lastCard5 = 0x00_8000_0000_0000L; //Card.getCardsAsLong(Image._K, Suit.c);
		long lastCard4 = 0x01_0000_0000_0000L; //Card.getCardsAsLong(Image._A, Suit.s);
		long lastCard3 = 0x02_0000_0000_0000L; //Card.getCardsAsLong(Image._A, Suit.h);
		long lastCard2 = 0x04_0000_0000_0000L; //Card.getCardsAsLong(Image._A, Suit.d);
		long lastCard1 = 0x08_0000_0000_0000L; //Card.getCardsAsLong(Image._A, Suit.c);
		
		long lastBoard = lastCard1|lastCard2|lastCard3|lastCard4|lastCard5;
		
		int size = 0;
		
		if ((cardsInGame & board) == 0) {
			list[size++] = board;
		}
	
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
				list[size++] = board;
			}
		}
		long timeStop = System.nanoTime()  - timeStart;
		System.out.printf("generator: %,d\n", timeStop);
	}
	
	public long[] generateBourdsList(Player... players) {
		list = new long[1712304];
		
		setCardsInGame(players);
		this.run();
		
		return list;
	}
	
	private void setCardsInGame(Player... players) {
		this.cardsInGame = 0;
		
		for (int i = 0; i < players.length; i++) {
			cardsInGame |= players[i].getHandAsLong();
		}
	}
	
	public long getNext(int i) {
		return list[i];
	}
	
}









