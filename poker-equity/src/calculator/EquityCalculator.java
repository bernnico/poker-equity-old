package calculator;

import java.util.Queue;

import card.Card;
import card.Image;
import card.Suit;

public class EquityCalculator extends Thread {
	private static Queue<Long> boardList;
	private static long boardCards;
	private static long playerHands[];
	private static int playerEquity[];
	private static long playerHaveCards[];
	private static int playerWinCards[];
	
	Defs defs = new Defs();

	public EquityCalculator(Queue<Long> boardList, long boardCards, long... playerHands) {
		this.boardList = boardList;
//		this.boardCards = boardCards;
//		this.playerHands = playerHands;
		this.playerEquity = new int[playerHands.length];
//		this.playerHaveCards = new long[playerHands.length];
//		thi.splayerWinCards = new int[playerHands.length];
	}
	
	public static void main(String[] args) throws InterruptedException {
		Card card = new Card();
		
		playerWinCards = new int[2];
		
		long player1 = card.getCardAsLong(Image._A, Suit.c) | card.getCardAsLong(Image._A, Suit.h);
		long player2 = card.getCardAsLong(Image._K, Suit.c) | card.getCardAsLong(Image._K, Suit.h);
		
		System.out.printf("player1 hand:\t%x\n", player1);
		System.out.printf("player2 hand:\t%x\n", player2);
		
		
		playerHands = new long[2];
		playerHands[0] = player1;
		playerHands[1] = player2;
		
		boardCards = card.getCardAsLong(Image._2, Suit.c)
				| card.getCardAsLong(Image._3, Suit.c)
				| card.getCardAsLong(Image._Q, Suit.k)
				| card.getCardAsLong(Image._5, Suit.k)
				| card.getCardAsLong(Image._6, Suit.c);
		
		
		playerHaveCards = new long[2];
		for (int i = 0; i < playerHaveCards.length; i++) {
			playerHaveCards[i] = playerHands[i] | boardCards;
		}
		
		System.out.printf("player1 cards:\t%x\n", playerHaveCards[0]);
		System.out.printf("player2 cards:\t%x\n", playerHaveCards[1]);
		
		checkFlush();
		System.out.println("\t\tflush");
		System.out.printf("player1:\t%x\n", playerWinCards[0]);
		System.out.printf("player2:\t%x\n", playerWinCards[1]);
		
		checkStraight();
		System.out.println("\t\tstraight");
		System.out.printf("player1:\t%x\n", playerWinCards[0]);
		System.out.printf("player2:\t%x\n", playerWinCards[1]);
		
		checkCombos();
		System.out.println("\t\tcombos");
		System.out.printf("player1:\t%x\n", playerWinCards[0]);
		System.out.printf("player2:\t%x\n", playerWinCards[1]);
	
	}

	@Override
	public void run() {
		
		for (int i = 0; i < playerHaveCards.length; i++) {
			playerHaveCards[i] = playerHands[i] | boardCards;
		}

		while (!boardList.isEmpty()) {
			
			checkFlush();
			
			checkStraight();
			
			checkCombos();
			
		}
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
			playerWinCards[player] = 0x0500_0000; // flush indicator
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
					
					
					if ((playerWinCards[player] & 0x0500_0000) == 0x0500_0000) { // flush indicator
						if ((playerWinCards[player] & highCard) == highCard) {
							playerWinCards[player] = 0x0800_0000; // straitgh flush
							playerWinCards[player] |= highCard;
							break;
						}
						
						continue;
					} 
					
					playerWinCards[player] = 0x0400_0000; // straight
					playerWinCards[player] |= highCard;
					
					break;
				}
			}
			
			// find 5 4 3 2 A
			if (       (playerWinCards[player] & 0x0400_0000) == 0 // straight
					&& (playerWinCards[player] & 0x0800_0000) == 0 // straight flush
					&& (playerHaveCards[player] & 0x00_0000_0000_F000L) != 0 // 5
					&& (playerHaveCards[player] & 0x00_0000_0000_0F00L) != 0 // 4
					&& (playerHaveCards[player] & 0x00_0000_0000_00F0L) != 0 // 3
					&& (playerHaveCards[player] & 0x00_0000_0000_000FL) != 0 // 2
					&& (playerHaveCards[player] & 0x0F_0000_0000_0000L) != 0) { // A
				
				if ((playerWinCards[player] & 0x0500_0000) == 0x0500_0000) { // flush indicator
					if ((playerWinCards[player] & 0x100F) == 0x100F) { // 5 4 3 2 A
						
						playerWinCards[player] = 0x0800_100F; // royal flush
						return;
					}
				} 
				
				playerWinCards[player] = 0x0400_000F; // straight 5 4 3 2 (A)
			}
		}
	}
	
	private static void checkCombos() {
		
//		long pair  = 0x03_0000_0000_0000L;
//		long three = 0x07_0000_0000_0000L;
//		long four  = 0x0F_0000_0000_0000L;
		
		
		// 0b0000_0000_0000_0000_0000_0000_0000_0000
		// --0000-main-_set-htwo-ltwo-kick-kick-kick
		
		int pairHit = 0, threeHit = 0;	
		int playingCombination = 0;
		
		for (int player = 0; player < playerHaveCards.length; player++) {
			
			if ((playerWinCards[player] & 0x0800_0000) == 0x0800_0000) { // straight flush
				continue;
			}
			
			for (int image = 0; image < 13; image++) {
				
				if ((playerHaveCards[player] & (0x0F_0000_0000_0000L >> (image << 2))) == 0) {
					continue;
					
				} else if ((playerWinCards[player] & 0x0600_0000) != 0x0600_0000) { // full house -> twos and threes
					
					if (       (playerHaveCards[player] & (0x03_0000_0000_0000L >> (image << 2))) // 0b0011
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
					
						if (pairHit == 0) {
							playingCombination |= (13 - image) << 16;
							
						} else if (pairHit == 1) {
							playingCombination |= (13 - image) << 12;
							
						} else if ((playingCombination & 0xF) == 0) {
							playingCombination |= (13 - image);
						}
						
						pairHit++;
						
					} else if ((playerHaveCards[player] & (0x07_0000_0000_0000L >> (image << 2))) // 0b0111
							== 0x03_0000_0000_0000L >> (image << 2) 
							|| (playerHaveCards[player] & (0x0B_0000_0000_0000L >> (image << 2))) // 0b1011
							== 0x05_0000_0000_0000L >> (image << 2) 
							|| (playerHaveCards[player] & (0x0D_0000_0000_0000L >> (image << 2))) // 0b1101
							== 0x06_0000_0000_0000L >> (image << 2) 
							|| (playerHaveCards[player] & (0x0E_0000_0000_0000L >> (image << 2))) // 0b1110
							== 0x09_0000_0000_0000L >> (image << 2)) { 

						if (threeHit == 0) {
							playingCombination |= (13 - image) << 20;
							
						} else if (threeHit == 1) {
							playingCombination |= (13 - image) << 16;
							
							playerWinCards[player] |= 0x0600_0000; //full house
							playerWinCards[player] |= playingCombination; //full house
						}
						
						threeHit++;
						
					}
				} else if ((playerHaveCards[player] & (0x0F_0000_0000_0000L >> (image << 2))) // 0b1111
						== 0x0F_0000_0000_0000L >> (image << 2)) {
					playerWinCards[player] = 0x0700_0000 | (13 - image);
					
					continue;
					
				} else { // high card
					playingCombination |= 1 << (12 - image);
					
				}	
			}
			
			if ((playerWinCards[player] & 0x0400_0000) != 0x0400_0000  // straight
					&& (playerWinCards[player] & 0x0500_0000) != 0x0500_0000 // flush
					&& (playerWinCards[player] & 0x0600_0000) != 0x0600_0000) { // full house 
				playerWinCards[player] = playingCombination;
			}
		}
	}

	public int[] getPlayerEquity() {
		// TODO Auto-generated method stub
		return null;
	}
	
}






















