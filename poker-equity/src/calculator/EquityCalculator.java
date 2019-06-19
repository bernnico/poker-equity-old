package calculator;

import java.util.Queue;

import card.BoardsList;
import card.Card;
import card.Image;
import card.Suit;
import debug.Debug;

public class EquityCalculator {
	private static long boardCards;
	private static long playerHands[];
	private static int playerEquity[];
	private static long playerHaveCards[];
	private static int playerBestCards[];

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

	public static void main(String[] args) throws InterruptedException {
		playerBestCards = new int[3]; // last index is ties
		playerEquity = new int[3];
		playerHands = new long[2];
		playerHaveCards = new long[2];
		
		run2();
	}

//	@Override
	public static void run2() {
		Card card = new Card();
		long player1 = card.getCardAsLong(Image._5, Suit.c) 
				     | card.getCardAsLong(Image._6, Suit.c);
		long player2 = card.getCardAsLong(Image._5, Suit.s)
				     | card.getCardAsLong(Image._7, Suit.s);
		
		long boardCards2 = card.getCardAsLong(Image._A, Suit.c) 
				   | card.getCardAsLong(Image._K, Suit.c)
				   | card.getCardAsLong(Image._Q, Suit.c) 
				   | card.getCardAsLong(Image._J, Suit.c)
				   | card.getCardAsLong(Image._8, Suit.c);

		BoardsList gen = new BoardsList();
		gen.generateBourdsList(player1, player2);
		
		playerHands[0] = player1;
		playerHands[1] = player2;
		
		while (gen.isEmpty()) {
//			System.out.println(gen.size());
		}
		long timeStart = System.nanoTime();
		int size = 0;

		int i = 1;
		
		while (size != 1712304) {
			// CPU: i5 4200u
			// evaluate time (48 nCp 5) in 620.000.000n

			boardCards = gen.getNext(size++);

			playerHaveCards[0] = playerHands[0] | boardCards;
			playerHaveCards[1] = playerHands[1] | boardCards;
			
			playerBestCards[0] = 0;
			playerBestCards[1] = 0;

			checkFlush();
			checkStraight();
			checkCombos();
			
			Debug.addPlayerCards(playerBestCards[0]);

			if (playerBestCards[0] > playerBestCards[1]) {
				playerEquity[0]++;
				
			} else if (playerBestCards[0] < playerBestCards[1]) {
				playerEquity[1]++;
				
			} else if ( i == 0 ) {}
			else 
//				if (

//						|| !(((playerBestCards[0] & 0xF000_0000) == 0x1000_0000)
//						|| ((playerBestCards[0] & 0xF000_0000) == 0x2000_0000)
//						|| ((playerBestCards[0] & 0xF000_0000) == 0x3000_0000)
//						((playerBestCards[0] & 0xF000_0000) == 0x4000_0000)
//						|| ((playerBestCards[0] & 0xF000_0000) == 0x5000_0000))
//						&&	((playerBestCards[0] & 0xF000_0000) == 0)
//						&& ((playerBestCards[0] & 0x0F00_0000) != 0)
						
						
//						&& ((playerBestCards[0] & 0x00F0_0000) != 0)
//						&& ((playerBestCards[0] & 0x000F_0000) != 0)
//						) 
			{
				
//				if (boardCards2 == boardCards)
//				System.out.println(card.getCardAsString(boardCards) + "\t" + playerBestCards[0] + "\t" +  i);
					
				playerEquity[2]++;
			}
		}
		
		long timeStop = System.nanoTime()  - timeStart;
		System.out.printf("complett: %,d\n", timeStop);
		Debug.printAllHands(size);
		System.out.println("size:\t\t" + size);
		System.out.println("player0\t\t" + playerEquity[2]);
		System.out.println("player1\t\t" + playerEquity[0]);
		System.out.println("player2\t\t" + playerEquity[1]);
		System.out.println(100.0 * playerEquity[2] / size);
		System.out.println(100.0 * (0.5 * playerEquity[2] + playerEquity[0]) / size);
		System.out.println(100.0 * (0.5 * playerEquity[2] + playerEquity[1]) / size);
		
	}

	public static void run3() {
		Card card = new Card();

		long player1 = card.getCardAsLong(Image._5, Suit.c) 
			     	 | card.getCardAsLong(Image._6, Suit.c);
		long player2 = card.getCardAsLong(Image._5, Suit.s)
			     	 | card.getCardAsLong(Image._7, Suit.s);

		boardCards = card.getCardAsLong(Image._K, Suit.h) 
				   | card.getCardAsLong(Image._K, Suit.k)
				   | card.getCardAsLong(Image._A, Suit.h) 
				   | card.getCardAsLong(Image._A, Suit.k)
				   | card.getCardAsLong(Image._A, Suit.s);
		
		System.out.println("player1: " + card.getCardAsString(player1));
		System.out.println("player2: " + card.getCardAsString(player2));
		System.out.println("boardt: " + card.getCardAsString(boardCards));
		
		
		
		playerHaveCards[0] = player1 | boardCards;
		playerHaveCards[1] = player2 | boardCards;

		playerBestCards[0] = 0;
		playerBestCards[1] = 0;

		checkFlush();
		checkStraight();
		checkCombos();

		System.out.printf("player1:\t\t%x\n", playerBestCards[0]);
		System.out.printf("player2:\t\t%x\n", playerBestCards[1]);
		
		System.out.println("payer1 win " + (playerBestCards[0] > playerBestCards[1]));
		System.out.println("tiel " + (playerBestCards[0] == playerBestCards[1]));
		
	}

	private static void checkFlush() {
		// 0x01_1111_1111_1111 : all Hearts
		// 0x01_2222_2222_2222 : all Diamonds
		// 0x04_4444_4444_4444 : all Spades
		// 0x08_8888_8888_8888 : all Clubs

		for (int player = 0; player < playerHaveCards.length; player++) {
			if ((playerHaveCards[player] & 0x01_1111_1111_1111L) != 0
					&& (playerHaveCards[player] & 0x02_2222_2222_2222L) != 0
					&& (playerHaveCards[player] & 0x04_4444_4444_4444L) != 0
					&& (playerHaveCards[player] & 0x08_8888_8888_8888L) != 0) {
				continue;
			}

			boolean flushFound = false;

			if ((playerHaveCards[player] & 0x01_1111_1111_1111L) != 0) {
				flushFound = setFlushIfFound(player, 0x01_0000_0000_0000L); // Ah
			}
			if (!flushFound && (playerHaveCards[player] & 0x02_2222_2222_2222L) != 0) {
				flushFound = setFlushIfFound(player, 0x02_0000_0000_0000L); // Ak
			}
			if (!flushFound && (playerHaveCards[player] & 0x04_4444_4444_4444L) != 0) {
				flushFound = setFlushIfFound(player, 0x04_0000_0000_0000L); // As
			}
			if (!flushFound && (playerHaveCards[player] & 0x08_8888_8888_8888L) != 0) {
				setFlushIfFound(player, 0x08_0000_0000_0000L); // Ac
			}
		}

	}

	private static boolean setFlushIfFound(int player, long currentSuit) {
		// 0b0000_0000_0000_0000_0000_0000_0000_0000
		// --0mai-0000-0000-0000-000*-****-****-****

		int flag = 0;
		int playingCombination = 0;

		for (int image = 0; image < 13; image++) {
			if ((playerHaveCards[player] & (currentSuit >> (image << 2))) != 0) {
				flag++;
				playingCombination |= 0x00_1000 >> image;
			}
		}
		
		if (flag > 4) {
			playerBestCards[player] = 0x2000_0000 | playingCombination; // flush indicator
			return true;
		}
		return false;
	}

	private static void checkStraight() {
		int highCard = 0, step = 0;

		for (int player = 0; player < playerHaveCards.length; player++) {	
			for (int image = 0; image < 9; image++) {
				step = image << 2;
				
				if ((playerHaveCards[player] & (0x0F_0000_0000_0000L >> step)) != 0
						&& (playerHaveCards[player] & (0x00_F000_0000_0000L >> step)) != 0
						&& (playerHaveCards[player] & (0x00_0F00_0000_0000L >> step)) != 0
						&& (playerHaveCards[player] & (0x00_00F0_0000_0000L >> step)) != 0
						&& (playerHaveCards[player] & (0x00_000F_0000_0000L >> step)) != 0) {
					
					highCard = 0x00_1F00 >> image;
					
					if ((playerBestCards[player] & 0x2000_0000) == 0x2000_0000) { // flush indicator
						if ((playerBestCards[player] & highCard) == highCard) {
							playerBestCards[player] = 0x5000_0000; // straitgh flush
							playerBestCards[player] |= highCard;
							break;
						}
						continue;
					}
					playerBestCards[player] = 0x1000_0000; // straight
					playerBestCards[player] |= highCard;
					break;
				}
			}
			// find 5 4 3 2 A
			if (highCard == 0 // straight
//					&& (playerWinCards[player] & 0x5000_0000) == 0 // straight flush
					&& (playerHaveCards[player] & 0x0F_0000_0000_0000L) != 0 // A
					&& (playerHaveCards[player] & 0x00_0000_0000_F000L) != 0 // 5
					&& (playerHaveCards[player] & 0x00_0000_0000_0F00L) != 0 // 4
					&& (playerHaveCards[player] & 0x00_0000_0000_00F0L) != 0 // 3
					&& (playerHaveCards[player] & 0x00_0000_0000_000FL) != 0) { // 2
				
				if ((playerBestCards[player] & 0x2000_0000) == 0x2000_0000) { // flush indicator
					
					if ((playerBestCards[player] & 0x100F) == 0x100F) { // 5 4 3 2 A
						playerBestCards[player] = 0x5000_100F; // royal flush
						
					}
					return;
				}
				playerBestCards[player] = 0x1000_000F; // straight 5 4 3 2 (A)
			}
		}
	}

	private static void checkCombos() {
		// 0b0000_0000_0000_0000_0000_0000_0000_0000
		// --0mia-_set-htwo-ltwo-000k-kick-kick-kick

		int pairHit = 0, threeHit = 0, noCombo = 0;
		int cardsWithoutHit = 0;
		int bestCombo = 0;
		int currentImage = 0;

		for (int player = 0; player < playerHaveCards.length; player++) {
			
			// straight flush
			if ((playerBestCards[player] & 0x5000_0000) == 0x5000_0000) {
				continue;
			}
			
			pairHit = 0;
			threeHit = 0;
			noCombo = 0;
			cardsWithoutHit = 0;
			bestCombo = 0;
			
			for (int image = 0; image < 13; image++) {
				
				currentImage = (int) ((playerHaveCards[player] >> ((12 - image) << 2)) & 0x0F);
				
				// non Hit
				if (currentImage == 0) {
					continue;
					
				} 
				// four  + + + + + + + + + + + + + + + + + + +
				else if (currentImage == 0b1111) {
					
					playerBestCards[player] = 0x4000_0000; // set four
					playerBestCards[player] |= (13 - image) << 4; // set four image 

					// set fifth card
					long playerHaveCardsWithout4 = playerHaveCards[player] & ~(0x0F_0000_0000_0000L >> (image << 2));
					
					playerBestCards[player] |= (playerHaveCardsWithout4 & (0x0F_0000_0000_0000L)) != 0 ? 13 : 
						(playerHaveCardsWithout4 & (0x00_F000_0000_0000L)) != 0 ? 12 :
							(playerHaveCardsWithout4 & (0x00_0F00_0000_0000L)) != 0 ? 11 :
								(playerHaveCardsWithout4 & (0x00_00F0_0000_0000L)) != 0 ? 10 :
									(playerHaveCardsWithout4 & (0x00_000F_0000_0000L)) != 0 ? 9 :
										(playerHaveCardsWithout4 & (0x00_0000_F000_0000L)) != 0 ? 8 :
											(playerHaveCardsWithout4 & (0x00_0000_0F00_0000L)) != 0 ? 7 :
												(playerHaveCardsWithout4 & (0x00_0000_00F0_0000L)) != 0 ? 6 :
													(playerHaveCardsWithout4 & (0x00_0000_000F_0000L)) != 0 ? 5 :
														(playerHaveCardsWithout4 & (0x00_0000_0000_F000L)) != 0 ? 4 :
															(playerHaveCardsWithout4 & (0x00_0000_0000_0F00L)) != 0 ? 3 :
																(playerHaveCardsWithout4 & (0x00_0000_0000_00F0L)) != 0 ? 2 : 1;
					break;
				} 
				// three
				else if ((playerHaveCards[player] & (0x07_0000_0000_0000L >> (image << 2)))   // 0b0111
						== 0x07_0000_0000_0000L >> (image << 2)
						|| (playerHaveCards[player] & (0x0B_0000_0000_0000L >> (image << 2))) // 0b1011
								== 0x0B_0000_0000_0000L >> (image << 2)
						|| (playerHaveCards[player] & (0x0D_0000_0000_0000L >> (image << 2))) // 0b1101
								== 0x0D_0000_0000_0000L >> (image << 2)
						|| (playerHaveCards[player] & (0x0E_0000_0000_0000L >> (image << 2))) // 0b1110
								== 0x0E_0000_0000_0000L >> (image << 2)) {

					if (pairHit != 0) {
						// set fullhouse
						bestCombo |= (13 - image) << 24;
						playerBestCards[player] |= (bestCombo | 0x3000_0000) & 0x3FF0_0000;
						break;

					} else if (threeHit != 0) {
						// set fullhouse
						bestCombo >>= 4;
						bestCombo |= (13 - image) << 24;
						playerBestCards[player] |= (bestCombo | 0x3000_0000) & 0x3FF0_0000;
						break;

					} else {
						bestCombo = (13 - image) << 24;
					}
					threeHit++;
					
				} 
				// two
				else if ((playerHaveCards[player] & (0x03_0000_0000_0000L >> (image << 2))) // 0b0011
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
					
					if (threeHit != 0) {
						// set fullhouse
						bestCombo |= (13 - image) << 20;
						playerBestCards[player] |= (bestCombo | 0x3000_0000) & 0x3FF0_0000;
						break;
						
					} else if (pairHit == 0) {
						bestCombo = (13 - image) << 20;
						pairHit++;
						
					} else if (pairHit != 0) {
						bestCombo |= (13 - image) << 16;
						
					} else {
						cardsWithoutHit |= 1 << (12 - image);
						noCombo++;
						
					}
					
					
				}
				// high cards
				else {
					if (noCombo != 5) { // highest 5 cards
						cardsWithoutHit |= 1 << (12 - image);
						noCombo++;
					}
				}
			}
			
			// non // straight // flush // full house // four // st-flush
			if (playerBestCards[player] == 0) {
				playerBestCards[player] = bestCombo;
				
				// two pair
				if ((bestCombo & 0x00F0_0000) != 0 && (bestCombo & 0x000F_0000) != 0) {	
					int j = 0;
					for (int i = 0; i < 13; i++) {
						if ((cardsWithoutHit & (1 << (13 - i))) != 0) {
							playerBestCards[player] |= 1 << (13 - i);
							break;
						}
					}	
				} 
				// a pair
				else if ((bestCombo & 0x00F0_0000) != 0) { 
					int j = 0;
					for (int i = 0; i < 13; i++) {
						if ((cardsWithoutHit & (1 << (13 - i))) != 0) {
							playerBestCards[player] |= 1 << (13 - i);
							if(j++ == 2)
								break;
						}
					}
				} else if ((bestCombo & 0x0F00_0000) != 0) { 
					int j = 0;
					for (int i = 0; i < 13; i++) {
						if ((cardsWithoutHit & (1 << (13 - i))) != 0) {
							playerBestCards[player] |= 1 << (13 - i);
							if(j++ == 1)
								break;
						}
					}
				} 
				// high cards
				else { 
					playerBestCards[player] = cardsWithoutHit;

				}
			}
		}
	}
	
	public int[] getPlayerEquity() {
		// TODO Auto-generated method stub
		return null;
	}

}
