package calculator;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import card.BoardGenerator;
import card.Card;
import card.Image;
import card.Suit;

public class EquityCalculator {
	private static ConcurrentLinkedQueue<Long> boardList;
	private static long boardCards;
	private static long playerHands[];
	private static int playerEquity[];
	private static long playerHaveCards[];
	private static int playerWinCards[];

	private static int high = 0;
	private static int pair = 0;
	private static int twoPair = 0;
	private static int three = 0;
	private static int straight = 0;
	private static int flush = 0;
	private static int fullHouse = 0;
	private static int four = 0;
	private static int stFlush = 0;

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
		playerWinCards = new int[2];
		playerEquity = new int[2];
		playerHands = new long[2];
		playerHaveCards = new long[2];

		run2();
	}

//	@Override
	public static void run2() {
		Card card = new Card();
		BoardGenerator gen = new BoardGenerator();
		long player1 = card.getCardAsLong(Image._K, Suit.c) | card.getCardAsLong(Image._Q, Suit.c);
		long player2 = card.getCardAsLong(Image._Q, Suit.k) | card.getCardAsLong(Image._Q, Suit.s);

		gen.setCardsInGame(player1 | player2);
		boardList = gen.getBourdList();

		playerHands[0] = player1;
		playerHands[1] = player2;

		while (boardList.size() < 1000)
			;

		int size = 0;
		while (!boardList.isEmpty()) {
			size++;

			boardCards = gen.getNextBoard();
//			System.out.printf("%x\n", boardCards);

			playerWinCards[0] = 0;
			playerWinCards[1] = 0;

			playerHaveCards[0] = playerHands[0] | boardCards;
			playerHaveCards[1] = playerHands[1] | boardCards;

			checkFlush();

			checkStraight();

			checkCombos();

			if ((playerWinCards[0] & 0x5000_0000) == 0x5000_0000)
				stFlush++;
			if ((playerWinCards[0] & 0x4000_0000) == 0x4000_0000)
				four++;
			if ((playerWinCards[0] & 0x3000_0000) == 0x3000_0000)
				fullHouse++;
			if ((playerWinCards[0] & 0x2000_0000) == 0x2000_0000)
				flush++;
			if ((playerWinCards[0] & 0x1000_0000) == 0x1000_0000)
				straight++;

			// --0mia-_set-htwo-ltwo-000k-kick-kick-kick
			if ((playerWinCards[0] & 0x0F00_0000) != 0 && (playerWinCards[0] & 0xF0FF_0000) == 0)
				three++;
			if ((playerWinCards[0] & 0x00FF_0000) != 0 && (playerWinCards[0] & 0xFF00_0000) == 0)
				twoPair++;
			if ((playerWinCards[0] & 0x00F0_0000) != 0 && (playerWinCards[0] & 0x0F0F_0000) == 0)
				pair++;
			if ((playerWinCards[0] & 0x0000_1FFF) == playerWinCards[0])
				high++;

			if (playerWinCards[0] > playerWinCards[1]) {
				playerEquity[0]++;
			} else if (playerWinCards[0] < playerWinCards[1]) {
				playerEquity[1]++;
			}

		}

		System.out.println("high\t\t" + 1.0 * high / size);
		System.out.println("pair\t\t" + 1.0 * pair / size);
		System.out.println("twoPair\t\t" + 1.0 * twoPair / size);
		System.out.println("three\t\t" + 1.0 * three / size);
		System.out.println("straight\t" + 1.0 * straight / size);
		System.out.println("flush\t\t" + 1.0 * flush / size);
		System.out.println("fullHouse\t" + 1.0 * fullHouse / size);
		System.out.println("four\t\t" + 1.0 * four / size);
		System.out.println("stFlush\t\t" + 1.0 * stFlush / size);

		System.out.println("size:\t\t" + size);
		System.out.println("player1\t\t" + playerEquity[0]);
		System.out.println("player2\t\t" + playerEquity[1]);

		System.out.println(1.0 * playerEquity[0] / size);
		System.out.println(1.0 * playerEquity[1] / size);

	}

	public static void run3() {
		Card card = new Card();

		long player1 = card.getCardAsLong(Image._A, Suit.c) | card.getCardAsLong(Image._J, Suit.h);
		long player2 = card.getCardAsLong(Image._K, Suit.c) | card.getCardAsLong(Image._J, Suit.c);

		boardCards = card.getCardAsLong(Image._Q, Suit.k) | card.getCardAsLong(Image._A, Suit.k)
				| card.getCardAsLong(Image._A, Suit.s) | card.getCardAsLong(Image._K, Suit.k)
				| card.getCardAsLong(Image._Q, Suit.c);

		playerHaveCards[0] = player1 | boardCards;
		playerHaveCards[1] = player2 | boardCards;

		playerWinCards[0] = 0;
		playerWinCards[1] = 0;

		checkFlush();

		checkStraight();

		checkCombos();

		System.out.printf("player1:\t\t%x\n", playerWinCards[0]);
		System.out.println((playerWinCards[0] & 0x4000_0000) == 0x4000_0000);
		System.out.printf("player2:\t\t%x", playerWinCards[1]);

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
//				playingCombination |= 1 << (12 - image);
				playingCombination |= 0x00_1000 >> image;
			}
		}
		if (flag > 4) {
			playerWinCards[player] = 0x2000_0000 | playingCombination; // flush indicator
//			playerWinCards[player] |= playingCombination;
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

		int highCard = 0, step = 0, card = 0, straight = 0, playingCombination = 0;
		boolean isAss = true;

		for (int player = 0; player < playerHaveCards.length; player++) {			
			for (int image = 0; image < 9; image++) {
				step = image << 2;

				if ((playerHaveCards[player] & (0x0F_0000_0000_0000L >> step)) != 0
						&& (playerHaveCards[player] & (0x00_F000_0000_0000L >> step)) != 0
						&& (playerHaveCards[player] & (0x00_0F00_0000_0000L >> step)) != 0
						&& (playerHaveCards[player] & (0x00_00F0_0000_0000L >> step)) != 0
						&& (playerHaveCards[player] & (0x00_000F_0000_0000L >> step)) != 0) {

//					highCard = 0x1F << (8 - image);
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
	
	private static void checkStraight2() {
		
		int highCard = 0, straight = 0, playingCombination = 0;
		
		boolean isAss = true;

		for (int player = 0; player < playerHaveCards.length; player++) {
			
			if ((playerHaveCards[player] & 0x0F_0000_0000_0000L) != 0) { // A
				highCard = 13;
				straight++;
				playingCombination = 0x00_1000;
			} else {
				isAss = false;
			}
			if ((playerHaveCards[player] & 0x0_F000_0000_0000L) != 0) { // K
				highCard = 12;
				straight++;
				playingCombination |= 0x00_0800;
			} else {
				straight = 0;
				playingCombination = 0;
			}
			if ((playerHaveCards[player] & 0x0F_0F00_0000_0000L) != 0) { // Q
				highCard = 11;
				straight++;
				playingCombination |= 0x00_0400;
			} else {
				straight = 0;
				playingCombination = 0;
			}
			if ((playerHaveCards[player] & 0x0F_00F0_0000_0000L) != 0) { // J
				highCard = 10;
				straight++;
				playingCombination |= 0x00_0200;
			} else {
				straight = 0;
				playingCombination = 0;
			}
			if ((playerHaveCards[player] & 0x0F_000F_0000_0000L) != 0) { // T
				highCard = 9;
				straight++;
				playingCombination |= 0x00_0100;
			} else {
				straight = 0;
				playingCombination = 0;
			}
			if ((playerHaveCards[player] & 0x0F_0000_F000_0000L) != 0) { // 9
				highCard = 8;
				straight++;
				playingCombination |= 0x00_0080;
			} else {
				straight = 0;
				playingCombination = 0;
			}
			if ((playerHaveCards[player] & 0x0F_0000_0F00_0000L) != 0) { // 8
				highCard = 7;
				straight++;
				playingCombination |= 0x00_0040;
			} else {
				straight = 0;
				playingCombination = 0;
			}
			if ((playerHaveCards[player] & 0x0F_0000_00F0_0000L) != 0) { // 7
				highCard = 6;
				straight++;
				playingCombination |= 0x00_0020;
			} else {
				straight = 0;
				playingCombination = 0;
			}
			if ((playerHaveCards[player] & 0x0F_0000_000F_0000L) != 0) { // 6
				highCard = 5;
				straight++;
				playingCombination |= 0x00_0010;
			} else {
				straight = 0;
				playingCombination = 0;
			}
			if (isAss && (playerHaveCards[player] & 0x0F_0000_0000_F000L) != 0) { // 5
				straight++;
			} else {
				return;
			}
			if ((playerHaveCards[player] & 0x0F_0000_0000_0F00L) == 0) { // 4
				return;
			}
			if ((playerHaveCards[player] & 0x0F_0000_0000_00F0L) == 0) { // 3
				return;
			}
			if ((playerHaveCards[player] & 0x0F_0000_0000_000FL) == 0) { // 2
				return;
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

				} else

				if ((playerHaveCards[player] & (0x07_0000_0000_0000L >> (image << 2))) // 0b0111
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
