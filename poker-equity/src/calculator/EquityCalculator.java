package calculator;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import card.BoardGenerator;
import card.Card;
import card.Image;
import card.Suit;

public class EquityCalculator extends Thread {
	private static ConcurrentLinkedQueue<Long> boardList;
	private static long boardCards;
	private static long playerHands[];
	private static int playerEquity[];
	private static long playerHaveCards[];
	private static int playerWinCards[];
	
	Defs defs = new Defs();
	long timeStart = System.nanoTime();

	public EquityCalculator(Queue<Long> boardList, long boardCards, long... playerHands) {
//		this.boardList = boardList;
//		this.boardCards = boardCards;
//		this.playerHands = playerHands;
//		this.playerEquity = new int[playerHands.length];
//		this.playerHaveCards = new long[playerHands.length];
//		thi.splayerWinCards = new int[playerHands.length];
	}
	
	
	
//	 	STRAIGHT	= 1 << 28;
//		FLUSH		= 2 << 28;
//		FULLHOUSE	= 3 << 28;
//		FOUR		= 4 << 28;
//		ST_FLUSH	= 5 << 28;
	
	public static void main(String[] args) throws InterruptedException {
		Card card = new Card();
		BoardGenerator gen= new BoardGenerator();
		
		
		playerWinCards = new int[2];
		playerEquity = new int[2];
		playerHands = new long[2];
		playerHaveCards = new long[2];
		
		long player1 = card.getCardAsLong(Image._A, Suit.c) | card.getCardAsLong(Image._A, Suit.h);
		long player2 = card.getCardAsLong(Image._K, Suit.c) | card.getCardAsLong(Image._K, Suit.h);
		
		
		gen.setCardsInGame(player1 | player2);
		boardList = gen.getBourdList();
//		
//		System.out.printf("player1 hand:\t%x\n", player1);
//		System.out.printf("player2 hand:\t%x\n", player2);
//		
//		
		playerHands[0] = player1;
		playerHands[1] = player2;
//		
//		boardCards = card.getCardAsLong(Image._2, Suit.c)
//				| card.getCardAsLong(Image._2, Suit.k)
//				| card.getCardAsLong(Image._A, Suit.k)
//				| card.getCardAsLong(Image._K, Suit.k)
//				| card.getCardAsLong(Image._6, Suit.c);
//		
//		
//		for (int i = 0; i < playerHaveCards.length; i++) {
//			playerHaveCards[i] = playerHands[i] | boardCards;
//		}
//		
//		System.out.printf("player1 cards:\t%x\n", playerHaveCards[0]);
//		System.out.printf("player2 cards:\t%x\n", playerHaveCards[1]);
//		
//		checkFlush();
//		System.out.println("\t\tflush");
//		System.out.printf("player1:\t%x\n", playerWinCards[0]);
//		System.out.printf("player2:\t%x\n", playerWinCards[1]);
//		
//		checkStraight();
//		System.out.println("\t\tstraight");
//		System.out.printf("player1:\t%x\n", playerWinCards[0]);
//		System.out.printf("player2:\t%x\n", playerWinCards[1]);
//		
//		checkCombos();
//		System.out.println("\t\tcombos");
//		System.out.printf("player1:\t%x\n", playerWinCards[0]);
//		System.out.printf("player2:\t%x\n", playerWinCards[1]);
	
		
		run2();
		
	}

//	@Override
	public static void run2() {
		
		for (int i = 0; i < playerHaveCards.length; i++) {
			playerHaveCards[i] = playerHands[i] | boardCards;
		}
		
		while (boardList.size() < 1);
		
		int size = 0;
		while (!boardList.isEmpty()) {
			size++;
			
			boardCards = boardList.poll();
			
			playerHaveCards[0] = playerHands[0] | boardCards;
			playerHaveCards[1] = playerHands[1] | boardCards;
			
			checkFlush();
			
			checkStraight();
			
			checkCombos();
			
			if (playerWinCards[0] > playerWinCards[1]) {
				playerEquity[0]++;
			} else if (playerWinCards[0] < playerWinCards[1]) {
				playerEquity[1]++;
			}
		}
		
		System.out.println(size);
		System.out.println(playerEquity[0]);
		System.out.println(playerEquity[1]);
		
		System.out.println(1.0 * playerEquity[0] / size);
		System.out.println(1.0 * playerEquity[1] / size);
		
		
	}
	
	private static void checkFlush() {
		
		// 0x01_1111_1111_1111 : all Hearts
		// 0x01_2222_2222_2222 : all Diamonds
		// 0x04_4444_4444_4444 : all Spades
		// 0x08_8888_8888_8888 : all Clubs
		
		for (int player = 0; player < playerHaveCards.length; player++) {
			
			if (   (playerHaveCards[player] & 0x01_1111_1111_1111L) != 0
				&& (playerHaveCards[player] & 0x02_2222_2222_2222L) != 0
				&& (playerHaveCards[player] & 0x04_4444_4444_4444L) != 0
				&& (playerHaveCards[player] & 0x08_8888_8888_8888L) != 0) {
				
				continue;
			}
			
			boolean flashFound = false;
			
			if ((playerHaveCards[player] & 0x01_1111_1111_1111L) != 0) {
				flashFound = setFlushIfFound(player, 0x01_0000_0000_0000L); // Ah
			} 
			if (!flashFound && (playerHaveCards[player] & 0x02_2222_2222_2222L) != 0) {
				flashFound = setFlushIfFound(player, 0x02_0000_0000_0000L); // Ak
			}
			if (!flashFound && (playerHaveCards[player] & 0x04_4444_4444_4444L) != 0) {
				flashFound = setFlushIfFound(player, 0x04_0000_0000_0000L); // As
			}
			if (!flashFound && (playerHaveCards[player] & 0x08_8888_8888_8888L) != 0) {
				setFlushIfFound(player, 0x08_0000_0000_0000L); // Ac
			}
		}
		
	}

	private static boolean setFlushIfFound(int player, long currentSuit) {
		
		// 0b0000_0000_0000_0000_0000_0000_0000_0000
		// --0000-main-0000-0000-000*-****-****-****
		
		int flush= 0;
		int playingCombination = 0;
		
		for (int image = 0; image < 13; image++) {
			
			if ((playerHaveCards[player] & (currentSuit >> (image << 2))) != 0) {
				flush++;
				playingCombination |= 1 << (12 - image);	
			}
		}
		
		if (flush > 4) {
			playerWinCards[player] = 0x2000_0000; // flush indicator
			playerWinCards[player] |= playingCombination;
			
			return true;
		}
		
		return false;
	}

	private static void checkStraight() {
		
		// 0x0F_0000_0000_0000 : all Asses
		// 0x00_F000_0000_0000 : all Kings
		// 0x00_0F00_0000_0000 : all Queens
		// 0x00_00F0_0000_0000 : all Jacks
		// 0x00_000F_0000_0000 : all Tens

		int highCard = 0, step = 0;
		
		for (int player = 0; player < playerHaveCards.length; player++) {
			
			for (int i = 0; i < 9; i++) {
				
				step = i << 2; 
				
				if ((       playerHaveCards[player] & (0x0F_0000_0000_0000L >> step)) != 0
						&& (playerHaveCards[player] & (0x00_F000_0000_0000L >> step)) != 0
						&& (playerHaveCards[player] & (0x00_0F00_0000_0000L >> step)) != 0
						&& (playerHaveCards[player] & (0x00_00F0_0000_0000L >> step)) != 0
						&& (playerHaveCards[player] & (0x00_000F_0000_0000L >> step)) != 0) {
					
					highCard = 0x1F << (8 - i);
					
					
					if ((playerWinCards[player] & 0x2000_0000) == 0x2000_0000) { // flush indicator
						if ((playerWinCards[player] & highCard) == highCard) {
							playerWinCards[player] = 0x5000_0000; // straitgh flush
							playerWinCards[player] |= highCard;
							break;
						}
						
						continue;
					} 
					
					playerWinCards[player] = 0x1000_0000; // straight
					playerWinCards[player] |= highCard;
					
					break;
				}
			}
			
			// find 5 4 3 2 A
			if (       (playerWinCards[player] & 0x1000_0000) == 0 // straight
					&& (playerWinCards[player] & 0x5000_0000) == 0 // straight flush
					&& (playerHaveCards[player] & 0x00_0000_0000_F000L) != 0 // 5
					&& (playerHaveCards[player] & 0x00_0000_0000_0F00L) != 0 // 4
					&& (playerHaveCards[player] & 0x00_0000_0000_00F0L) != 0 // 3
					&& (playerHaveCards[player] & 0x00_0000_0000_000FL) != 0 // 2
					&& (playerHaveCards[player] & 0x0F_0000_0000_0000L) != 0) { // A
				
				if ((playerWinCards[player] & 0x2000_0000) == 0x2000_0000) { // flush indicator
					if ((playerWinCards[player] & 0x100F) == 0x100F) { // 5 4 3 2 A
						
						playerWinCards[player] = 0x5000_100F; // royal flush
						return;
					}
				} 
				
				playerWinCards[player] = 0x1000_000F; // straight 5 4 3 2 (A)
			}
		}
	}
	
	private static void checkCombos() {
		
//		long pair  = 0x03_0000_0000_0000L;
//		long three = 0x07_0000_0000_0000L;
//		long four  = 0x0F_0000_0000_0000L;
		
		
		// 0b0000_0000_0000_0000_0000_0000_0000_0000
		// --0mia-_set-htwo-ltwo-000k-kick-kick-kick
		
		int pairHit = 0, threeHit = 0;	
		int playingCombination = 0;
		
		for (int player = 0; player < playerHaveCards.length; player++) {
			pairHit = 0;
			threeHit = 0;
			playingCombination = 0;
			
			if ((playerWinCards[player] & 0x5000_0000) == 0x5000_0000) { // straight flush
				continue;
				
			}
			
			for (int image = 0; image < 13; image++) {
				
				if ((playerHaveCards[player] & (0x0F_0000_0000_0000L >> (image << 2))) == 0) {
					continue;
					
				} else if ((playerWinCards[player] & 0x3000_0000) != 0x3000_0000) { // full house -> twos and threes
					
					if ((playerHaveCards[player] & (0x07_0000_0000_0000L >> (image << 2))) // 0b0111
							== 0x03_0000_0000_0000L >> (image << 2) 
							|| (playerHaveCards[player] & (0x0B_0000_0000_0000L >> (image << 2))) // 0b1011
							== 0x05_0000_0000_0000L >> (image << 2) 
							|| (playerHaveCards[player] & (0x0D_0000_0000_0000L >> (image << 2))) // 0b1101
							== 0x06_0000_0000_0000L >> (image << 2) 
							|| (playerHaveCards[player] & (0x0E_0000_0000_0000L >> (image << 2))) // 0b1110
							== 0x09_0000_0000_0000L >> (image << 2)) { 

						if (threeHit == 0) {
							playingCombination |= (13 - image) << 24;
							
						} else if (threeHit == 1) {
							playingCombination |= (13 - image) << 20;
							
							playingCombination &= 0x3FFF_0000;
							playerWinCards[player] |= 0x3000_0000 & 0x3FFF_0000; //full house
							playerWinCards[player] |= playingCombination;
						}
						
						threeHit++;
						
					} else if (       (playerHaveCards[player] & (0x03_0000_0000_0000L >> (image << 2))) // 0b0011
							== 0x03_0000_0000_0000L >> (image << 2) 
							|| (playerHaveCards[player] & (0x05_0000_0000_0000L >> (image << 2))) // 0b0101
							== 0x05_0000_0000_0000L >> (image << 2) 
							|| (playerHaveCards[player] & (0x06_0000_0000_0000L >> (image << 2))) // 0b0110
							== 0x06_0000_0000_0000L >> (image << 2) 
							|| (playerHaveCards[player] & (0x09_0000_0000_0000L >> (image << 2))) // 0b1001
							== 0x09_0000_0000_0000L >> (image << 2) 
							|| (playerHaveCards[player] & (0x0A_0000_0000_0000L >> (image << 2))) // 0b1010
							== 0x0A_0000_0000_0000L >> (image << 2) 
							|| (playerHaveCards[player] & (0x0C_0000_0000_0000L >> (image << 2))) // 0b1100
							== 0x0C_0000_0000_0000L >> (image << 2)) { 
					
						if (threeHit == 1) {
							playingCombination |= (13 - image) << 20;
							
							playingCombination &= 0x3FFF_0000;
							playerWinCards[player] |= 0x3000_0000 & 0x3FFF_0000; //full house
							playerWinCards[player] |= playingCombination;
							
						} else if (pairHit == 0) {
							playingCombination |= (13 - image) << 20;
							
						} else if (pairHit == 1) {
							playingCombination |= (13 - image) << 16;
							
						} else {
							playingCombination |= 1 << (12 - image);
							
						}
						
						pairHit++;
						
					} else {
						playingCombination |= 1 << (12 - image);
						
					}
					
				} else if ((playerHaveCards[player] & (0x0F_0000_0000_0000L >> (image << 2))) // 0b1111
						== 0x0F_0000_0000_0000L >> (image << 2)) {
					playerWinCards[player] = 0x4000_0000 | (13 - image);
					
					continue;
					
				}
			}
			
			if ((playerWinCards[player] & 0xF000_0000) == 0) { // straight // flush // full house 
				playerWinCards[player] = playingCombination;
				
			}
		}
	}

	public int[] getPlayerEquity() {
		// TODO Auto-generated method stub
		return null;
	}
	
}






















