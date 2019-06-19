package calculator;

import java.util.Queue;

import card.BoardsList;
import card.Card;
import card.Image;
import card.Suit;
import debug.Debug;

public class EquityCalculatorFast {
	private static long boardCards;
	private static long playerHands[];
	private static int playerEquity[];
	private static long playerHaveCards[];
	private static int playerWinCards[];

	Defs defs = new Defs();
	long timeStart = System.nanoTime();

	public EquityCalculatorFast(Queue<Long> boardList, long boardCards, long... playerHands) {
//		this.boardList = boardList;
//		this.boardCards = boardCards;
//		this.playerHands = playerHands;
//		this.playerEquity = new int[playerHands.length];
//		this.playerHaveCards = new long[playerHands.length];
//		thi.splayerWinCards = new int[playerHands.length];

	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println("fast");
		playerWinCards = new int[2];
		playerEquity = new int[2];
		playerHands = new long[2];
		playerHaveCards = new long[2];

		run2();
	}

	public static void run3() {
		Card card = new Card();

		long player1 = card.getCardAsLong(Image._8, Suit.c) | card.getCardAsLong(Image._J, Suit.h);
		long player2 = card.getCardAsLong(Image._K, Suit.c) | card.getCardAsLong(Image._J, Suit.c);

		boardCards = card.getCardAsLong(Image._K, Suit.k) | card.getCardAsLong(Image._Q, Suit.k)
				| card.getCardAsLong(Image._9, Suit.s) | card.getCardAsLong(Image._T, Suit.k)
				| card.getCardAsLong(Image._Q, Suit.c);

		playerHaveCards[0] = player1 | boardCards;
		playerHaveCards[1] = player2 | boardCards;

		playerWinCards[0] = 0;
		playerWinCards[1] = 0;

		checkFlush();

		// checkStraight();

		checkCombos();

		System.out.printf("player1:\t\t%x\n", playerWinCards[0]);
		System.out.println((playerWinCards[0] & 0x1000_0000) == 0x1000_0000);
		System.out.printf("player2:\t\t%x", playerWinCards[1]);
	}

//	@Override
	public static void run2() {
		Card card = new Card();
		long player1 = card.getCardAsLong(Image._K, Suit.c) | card.getCardAsLong(Image._Q, Suit.c);
		long player2 = card.getCardAsLong(Image._Q, Suit.k) | card.getCardAsLong(Image._Q, Suit.s);

		BoardsList gen = new BoardsList();
//		gen.setCardsInGame(player1 | player2);
		gen.generateBourdsList(player1 , player2);

		playerHands[0] = player1;
		playerHands[1] = player2;

		while (gen.size() != 1712304)
			;
		long timeStart = System.nanoTime();
		int size = 0;
//		
//		while (!gen.isEmpty()) {
		while (size != 1712304) {
			// CPU:
			// evaluate time (48 nCp 5) in 600.000.000n

			boardCards = gen.getNext(size++);

			playerWinCards[0] = 0;
			playerWinCards[1] = 0;

			playerHaveCards[0] = playerHands[0] | boardCards;
			playerHaveCards[1] = playerHands[1] | boardCards;

			checkFlush();

			// checkStraight();

			checkCombos();

			Debug.addPlayerCards(playerWinCards[0]);

			if (playerWinCards[0] > playerWinCards[1]) {
				playerEquity[0]++;
			} else if (playerWinCards[0] < playerWinCards[1]) {
				playerEquity[1]++;
			}

		}
		long timeStop = System.nanoTime() - timeStart;
		System.out.printf("complett: %,d\n", timeStop);

		Debug.printAllHands(size);
		System.out.println("size:\t\t" + size);
		System.out.println("player1\t\t" + playerEquity[0]);
		System.out.println("player2\t\t" + playerEquity[1]);
		Debug.printWinLose(size, playerEquity[0], 1);
		Debug.printWinLose(size, playerEquity[1], 2);
	}

	private static void checkFlush() {

		for (int player = 0; player < playerHaveCards.length; player++) {
			boolean flushFound = true;
			if ((playerHaveCards[player] & 0x01_1111_1111_1111L) != 0
					&& (playerHaveCards[player] & 0x02_2222_2222_2222L) != 0
					&& (playerHaveCards[player] & 0x04_4444_4444_4444L) != 0
					&& (playerHaveCards[player] & 0x08_8888_8888_8888L) != 0) {
				flushFound = false;
			}

			long cardsInt = 0;

			int flagFlushH = 0, flagFlushK = 0, flagFlushS = 0, flagFlushC = 0;
			int constellationFlushH = 0, constellationFlushK = 0, constellationFlushS = 0, constellationFlushC = 0;
			int flagStraight = 0, constellationStraight = 0;

			for (int image = 0; image < 13; image++) {

				if (flushFound) { // flush
					if ((playerHaveCards[player] & (0x01_0000_0000_0000L >> (image << 2))) != 0) {
						flagFlushH++;
						constellationFlushH |= 0x00_1000 >> image;
					}
					if ((playerHaveCards[player] & (0x02_0000_0000_0000L >> (image << 2))) != 0) {
						flagFlushK++;
						constellationFlushK |= 0x00_1000 >> image;
					}
					if ((playerHaveCards[player] & (0x04_0000_0000_0000L >> (image << 2))) != 0) {
						flagFlushS++;
						constellationFlushS |= 0x00_1000 >> image;
					}
					if ((playerHaveCards[player] & (0x08_0000_0000_0000L >> (image << 2))) != 0) {
						flagFlushC++;
						constellationFlushC |= 0x00_1000 >> image;
					}
				}

				// Straight
				if ((playerHaveCards[player] & (0x0F_0000_0000_0000L >> (image << 2))) != 0) {
					if (flagStraight != 5) {
						flagStraight++;
						constellationStraight |= 0x00_1000 >> image;
					}
				} else if (flagStraight < 5) {
					flagStraight = 0;
					constellationStraight = 0;
				}
			}

			if (flagFlushH > 4) {
				if (flagStraight == 5 && (constellationFlushH & constellationStraight) == constellationStraight) {
					playerWinCards[player] = 0x5000_0000 | constellationFlushH; // st_flaush
				} else {
					playerWinCards[player] = 0x2000_0000 | constellationFlushH; // flush indicator
				}
			} else if (flagFlushK > 4) {
				if (flagStraight == 5 && (constellationFlushK & constellationStraight) == constellationStraight) {
					playerWinCards[player] = 0x5000_0000 | constellationFlushK; // st_flaush
				} else {
					playerWinCards[player] = 0x2000_0000 | constellationFlushK; // flush indicator
				}
			} else if (flagFlushS > 4) {
				if (flagStraight == 5 && (constellationFlushS & constellationStraight) == constellationStraight) {
					playerWinCards[player] = 0x5000_0000 | constellationFlushS; // st_flaush
				} else {
					playerWinCards[player] = 0x2000_0000 | constellationFlushS; // flush indicator
				}
			} else if (flagFlushC > 4) {
				if (flagStraight == 5 && (constellationFlushC & constellationStraight) == constellationStraight) {
					playerWinCards[player] = 0x5000_0000 | constellationFlushC; // st_flaush
				} else {
					playerWinCards[player] = 0x2000_0000 | constellationFlushC; // flush indicator
				}
			} else if (flagStraight == 5) {
				playerWinCards[player] = 0x1000_0000 | constellationStraight; // straight
			}
		}
	}

	private static boolean setFlushIfFound(int player, long currentSuit) {
		long cardsInt = 0;

		int flagFlushH = 0, flagFlushK = 0, flagFlushS = 0, flagFlushC = 0;
		int constellationFlushH = 0, constellationFlushK = 0, constellationFlushS = 0, constellationFlushC = 0;
		int flagStraight = 0, constellationStraight = 0;

		for (int image = 0; image < 13; image++) {

			// Flash
			if ((playerHaveCards[player] & (0x01_0000_0000_0000L >> (image << 2))) != 0) {
				flagFlushH++;
				constellationFlushH |= 0x00_1000 >> image;
			}
			if ((playerHaveCards[player] & (0x02_0000_0000_0000L >> (image << 2))) != 0) {
				flagFlushK++;
				constellationFlushK |= 0x00_1000 >> image;
			}
			if ((playerHaveCards[player] & (0x04_0000_0000_0000L >> (image << 2))) != 0) {
				flagFlushS++;
				constellationFlushS |= 0x00_1000 >> image;
			}
			if ((playerHaveCards[player] & (0x08_0000_0000_0000L >> (image << 2))) != 0) {
				flagFlushC++;
				constellationFlushC |= 0x00_1000 >> image;
			}

			// for combos
			if (currentSuit == 0x01_0000_0000_0000L) {
				// Straight
				if ((playerHaveCards[player] & (0x0F_0000_0000_0000L >> (image << 2))) != 0) {
					if (flagStraight != 5) {
						flagStraight++;
						constellationStraight |= 0x00_1000 >> image;

					}

				} else if (flagStraight < 5) {
					flagStraight = 0;
					constellationStraight = 0;
				}

			} else if (currentSuit == 0x02_0000_0000_0000L) {

			} else if (currentSuit == 0x04_0000_0000_0000L) {

			} else {

			}
		}

		if (flagFlushH > 4) {
			if (flagStraight == 5 && (constellationFlushH & constellationStraight) == constellationStraight) {
				playerWinCards[player] = 0x5000_0000 | constellationFlushH; // st_flaush
			} else {
				playerWinCards[player] = 0x2000_0000 | constellationFlushH; // flush indicator
			}
			return true;
		} else if (flagFlushK > 4) {
			if (flagStraight == 5 && (constellationFlushK & constellationStraight) == constellationStraight) {
				playerWinCards[player] = 0x5000_0000 | constellationFlushK; // st_flaush
			} else {
				playerWinCards[player] = 0x2000_0000 | constellationFlushK; // flush indicator
			}
			return true;
		} else if (flagFlushS > 4) {
			if (flagStraight == 5 && (constellationFlushS & constellationStraight) == constellationStraight) {
				playerWinCards[player] = 0x5000_0000 | constellationFlushS; // st_flaush
			} else {
				playerWinCards[player] = 0x2000_0000 | constellationFlushS; // flush indicator
			}
			return true;
		} else if (flagFlushC > 4) {
			if (flagStraight == 5 && (constellationFlushC & constellationStraight) == constellationStraight) {
				playerWinCards[player] = 0x5000_0000 | constellationFlushC; // st_flaush
			} else {
				playerWinCards[player] = 0x2000_0000 | constellationFlushC; // flush indicator
			}
			return true;
		} else if (flagStraight == 5) {
			playerWinCards[player] = 0x1000_0000 | constellationStraight; // straight
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

			for (int image = 0; image < 9; image++) {

				step = image << 2;

				if ((playerHaveCards[player] & (0x0F_0000_0000_0000L >> step)) != 0
						&& (playerHaveCards[player] & (0x00_F000_0000_0000L >> step)) != 0
						&& (playerHaveCards[player] & (0x00_0F00_0000_0000L >> step)) != 0
						&& (playerHaveCards[player] & (0x00_00F0_0000_0000L >> step)) != 0
						&& (playerHaveCards[player] & (0x00_000F_0000_0000L >> step)) != 0) {

					highCard = 0x00_1F00 >> image;

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
			if ((playerWinCards[player] & 0x1000_0000) == 0 // straight
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

				} else if ((playerHaveCards[player] & (0x0F_0000_0000_0000L >> (image << 2))) // 0b1111
						== 0x0F_0000_0000_0000L >> (image << 2)) {
					playerWinCards[player] = 0x4000_0000 | (13 - image);
					break;

				} else if ((playerHaveCards[player] & (0x07_0000_0000_0000L >> (image << 2))) // 0b0111
						== 0x07_0000_0000_0000L >> (image << 2)
						|| (playerHaveCards[player] & (0x0B_0000_0000_0000L >> (image << 2))) // 0b1011
								== 0x0B_0000_0000_0000L >> (image << 2)
						|| (playerHaveCards[player] & (0x0D_0000_0000_0000L >> (image << 2))) // 0b1101
								== 0x0D_0000_0000_0000L >> (image << 2)
						|| (playerHaveCards[player] & (0x0E_0000_0000_0000L >> (image << 2))) // 0b1110
								== 0x0E_0000_0000_0000L >> (image << 2)) {

					if (pairHit > 0) {
						playingCombination |= (13 - image) << 24;
						playerWinCards[player] |= (playingCombination | 0x3000_0000) & 0x3FF0_0000;
						break;

					} else if (threeHit == 0) {
						playingCombination |= (13 - image) << 24;

					} else if (threeHit == 1) {
						playingCombination |= (13 - image) << 20;
						playerWinCards[player] |= (playingCombination | 0x3000_0000) & 0x3FF0_0000;
						break;

					}
					threeHit++;

				} else if ((playerHaveCards[player] & (0x03_0000_0000_0000L >> (image << 2))) // 0b0011
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
						playerWinCards[player] |= (playingCombination | 0x3000_0000) & 0x3FF0_0000;
						break;

					} else if (pairHit == 0) {
						playingCombination |= (13 - image) << 20;

					} else if (pairHit == 1) {
						playingCombination |= (13 - image) << 16;

					} else {
						if ((playingCombination & 0x0F_FFFF) == 0) {
							playingCombination |= 1 << (12 - image);

						}
						break;
					}
					pairHit++;

				} else {
					playingCombination |= 1 << (12 - image);
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
