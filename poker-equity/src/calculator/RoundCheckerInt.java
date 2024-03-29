package calculator;

import debug.Debug;
import player.Player;

public class RoundCheckerInt extends Thread {
	private static int cores = 1;
	
	private int playerEquity[];
	private long playerHaveCards[];
	
	private long playerHaveCardsInt[];
	
	private int playerBestCards[];
	private Player players[];
	
	private int index;
	
	private long generatedBoards[];

	public RoundCheckerInt(Player players[], long generatedBoards[], int index) {
		this.playerEquity = new int[players.length + 1];
		this.playerHaveCards = new long[players.length];
		this.playerHaveCardsInt = new long[players.length];
		this.playerBestCards = new int[players.length];
		
		this.players = players;
		this.generatedBoards = generatedBoards;
		this.index = index;
	}

	@Override
	public void run() {
		long timeStart = System.nanoTime();

		long playerHands[] = new long[players.length];
		for (int i = 0; i < players.length; i++) {
			playerHands[i] = players[i].getHandAsLong();
		}

		long cardsOnTheBoard = 0;
		int size = index;

		while (size < 1712304) {			
			cardsOnTheBoard = generatedBoards[size];
			size += cores;

			playerHaveCards[0] = playerHands[0] | cardsOnTheBoard;
			playerHaveCards[1] = playerHands[1] | cardsOnTheBoard;

			playerBestCards[0] = 0;
			playerBestCards[1] = 0;

			checkFlush();
			checkStraight();
			checkCombos();
			
			if (playerBestCards[0] > playerBestCards[1]) {
				playerEquity[0]++;
			} 
			else if (playerBestCards[0] < playerBestCards[1]) {
				playerEquity[1]++;
			} 
			else {
				playerEquity[2]++;
			}
			
//			Debug.addPlayerCards(playerBestCards[0]);
		}

		long timeStop = System.nanoTime() - timeStart;
		System.out.printf("complett: %,d\n", timeStop);
//		Debug.printAllHands(size);
	}

	private void checkFlush() {
		for (int player = 0; player < playerHaveCards.length; player++) {
			// is flush possible
			if (       (playerHaveCards[player] & 0x01_1111_1111_1111L) != 0
					&& (playerHaveCards[player] & 0x02_2222_2222_2222L) != 0
					&& (playerHaveCards[player] & 0x04_4444_4444_4444L) != 0
					&& (playerHaveCards[player] & 0x08_8888_8888_8888L) != 0) {
				continue;
			}

			boolean flushPossible = false;
			// As
			if ((playerHaveCards[player] & 0x01_1111_1111_1111L) != 0) {
				flushPossible = setFlushIfFound(player, 0x01_0000_0000_0000L);
			}
			// Ah
			if (!flushPossible && (playerHaveCards[player] & 0x02_2222_2222_2222L) != 0) {
				flushPossible = setFlushIfFound(player, 0x02_0000_0000_0000L);
			}
			// Ad
			if (!flushPossible && (playerHaveCards[player] & 0x04_4444_4444_4444L) != 0) {
				flushPossible = setFlushIfFound(player, 0x04_0000_0000_0000L);
			}
			// Ac
			if (!flushPossible && (playerHaveCards[player] & 0x08_8888_8888_8888L) != 0) {
				setFlushIfFound(player, 0x08_0000_0000_0000L);
			}
		}
	}

	private boolean setFlushIfFound(int player, long currentSuit) {
		int flagFlush = 0;
		int playingCombination = 0;

		for (int image = 0; image < 13; image++) {
			if ((playerHaveCards[player] & (currentSuit >> (image << 2))) != 0) {
				flagFlush++;
				playingCombination |= 0x00_1000 >> image;
			}
			// TODO
			// flagFlush == 5
		}
		if (flagFlush > 4) {
			playerBestCards[player] = 0x4000_0000 | playingCombination;
			return true;
		} else if (flagFlush > 2) {
			return true;
		}
		return false;
	}

	private void checkStraight() {
		int highCard = 0, step = 0;
		long plhand = 0;
		int cnt = 0;

		for (int player = 0; player < playerHaveCards.length; player++) {
			cnt = 0;
			plhand = playerHaveCards[player];

			for (int image = 0; image < 9; image++) {
				step = image << 2;
				
				if ((plhand & (0x0F_0000_0000_0000L >> step)) != 0) {
					cnt++;
				} else {
					continue;
				}
				// straight, highest first
				if (       (plhand & (0x00_F000_0000_0000L >> step)) != 0
						&& (plhand & (0x00_0F00_0000_0000L >> step)) != 0
						&& (plhand & (0x00_00F0_0000_0000L >> step)) != 0
						&& (plhand & (0x00_000F_0000_0000L >> step)) != 0) {

					highCard = 0x00_1F00 >> image;

					// straight and flush
					if ((playerBestCards[player] & 0x4000_0000) == 0x4000_0000) {
						if ((playerBestCards[player] & highCard) == highCard) {
							playerBestCards[player] = 0x7000_0000;
							playerBestCards[player] |= highCard;
							break; // straight flush
						}
						continue; // straight < flush
					}
					// straight founded and no flush
					else {
						playerBestCards[player] = 0x3000_0000;
						playerBestCards[player] |= highCard;
						break; // highest straight without flush
					}
				}
				if (cnt > 2) {
					break;
				}
			}
			// check lowest straight 5 4 3 2 A
			if (       (plhand & 0x0F_0000_0000_0000L) != 0 // A
					&& (plhand & 0x00_0000_0000_F000L) != 0 // 5
					&& (plhand & 0x00_0000_0000_0F00L) != 0 // 4
					&& (plhand & 0x00_0000_0000_00F0L) != 0 // 3
					&& (plhand & 0x00_0000_0000_000FL) != 0 // 2
					&& (playerBestCards[player] & 0x7000_0000) != 0x7000_0000) { // no straight flush

				// straight and flush
				if ((playerBestCards[player] & 0x4000_0000) == 0x4000_0000) {
					if ((playerBestCards[player] & 0x100F) == 0x100F) { // 5 4 3 2 A
						playerBestCards[player] = 0x7000_000F; // royal flush
						continue; // straight flush, go to next player
					}
				}
				// no highest straight
				else if ((playerBestCards[player] & 0x3000_0000) != 0x3000_0000) {
					playerBestCards[player] = 0x3000_000F; // straight 5 4 3 2 (A)
				}
			}

			// flush correction
			if ((playerBestCards[player] & 0x4000_0000) == 0x4000_0000) {
				int j = 0;
				for (int i = 0; i < 13; i++) {
					if ((playerBestCards[player] & (1 << (12 - i))) != 0) {
						if (j == 5) {
							playerBestCards[player] &= ~(0x00_1FFF >> i);
							break;
						}
						j++;
					}
				}
			}

		}
	}

	private void checkCombos() {
		int pairHit = 0, threeHit = 0, noCombo = 0;
		int cardsWithoutHit = 0;
		int bestCombo = 0;
		int currentImage = 0;

		for (int player = 0; player < playerHaveCards.length; player++) {
			// straight or flush -> four or fullhouse not possible
			if (
//					(playerBestCards[player] & 0xF000_0000) == 0x7000_0000 
					(playerBestCards[player] & 0xF000_0000) != 0) {
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
				// four
				else if (currentImage == 0b1111) {
					playerBestCards[player] = 0x6000_0000 | (13 - image) << 4;

					// set fifth card
					long playerHaveCardsWithout4 = playerHaveCards[player] & ~(0x0F_0000_0000_0000L >> (image << 2));
					for (int i = 0; i < 13; i++) {
						if ((playerHaveCardsWithout4 & (0x0F_0000_0000_0000L >> (i << 2))) != 0) {
							playerBestCards[player] |= 13 - i;
						break;
						}
					}
					
//					playerBestCards[player] |=
//							  (playerHaveCardsWithout4 & (0x0F_0000_0000_0000L)) != 0 ? 13
//							: (playerHaveCardsWithout4 & (0x00_F000_0000_0000L)) != 0 ? 12
//							: (playerHaveCardsWithout4 & (0x00_0F00_0000_0000L)) != 0 ? 11
//							: (playerHaveCardsWithout4 & (0x00_00F0_0000_0000L)) != 0 ? 10
//							: (playerHaveCardsWithout4 & (0x00_000F_0000_0000L)) != 0 ? 9
//							: (playerHaveCardsWithout4 & (0x00_0000_F000_0000L)) != 0 ? 8
//							: (playerHaveCardsWithout4 & (0x00_0000_0F00_0000L)) != 0 ? 7
//							: (playerHaveCardsWithout4 & (0x00_0000_00F0_0000L)) != 0 ? 6
//							: (playerHaveCardsWithout4 & (0x00_0000_000F_0000L)) != 0 ? 5
//							: (playerHaveCardsWithout4 & (0x00_0000_0000_F000L)) != 0 ? 4
//							: (playerHaveCardsWithout4 & (0x00_0000_0000_0F00L)) != 0 ? 3
//							: (playerHaveCardsWithout4 & (0x00_0000_0000_00F0L)) != 0 ? 2
//							: 1;
//					break;
				}
				// high cards
				else if (currentImage == 0b001 || currentImage == 0b0010 || currentImage == 0b0100
						|| currentImage == 0b1000) {

					if (noCombo != 5) { // highest 5 cards
						cardsWithoutHit |= 1 << (12 - image);
						noCombo++;
					}
				}
				// two
				else if (currentImage == 0b0011 || currentImage == 0b0101 || currentImage == 0b0110
						|| currentImage == 0b1001 || currentImage == 0b1010 || currentImage == 0b1100) {

					if (threeHit != 0) {
						// set fullhouse
						bestCombo |= (13 - image) << 20;
						playerBestCards[player] |= (bestCombo | 0x5000_0000) & 0x5FF0_0000;
						break;

					} else if (pairHit == 0) {
						bestCombo = (13 - image) << 20;
						pairHit++;

					} else if (pairHit == 1) {
						bestCombo |= (13 - image) << 16;
						bestCombo |= 0x1000_0000;
						pairHit++;

					} else {
						cardsWithoutHit |= 1 << (12 - image);
						noCombo++;
					}
				}
				// three
				else {

					if (pairHit != 0) {
						// set fullhouse
						bestCombo |= (13 - image) << 24;
						playerBestCards[player] |= (bestCombo | 0x5000_0000) & 0x5FF0_0000;
						break;

					} else if (threeHit != 0) {
						// set fullhouse
						bestCombo |= (13 - image) << 20;
						playerBestCards[player] |= (bestCombo | 0x5000_0000) & 0x5FF0_0000;
						break;

					} else {
						bestCombo = (13 - image) << 24;
						bestCombo |= 0x2000_0000;
					}
					threeHit++;
				}
			}
			// no // straight // flush // full house // four // st-flush
			if (playerBestCards[player] == 0) {
				playerBestCards[player] = bestCombo;

				// two pair
				if ((bestCombo & 0x00F0_0000) != 0 && (bestCombo & 0x000F_0000) != 0) {
					for (int i = 0; i < 13; i++) {
						if ((cardsWithoutHit & (1 << (12 - i))) != 0) {
							playerBestCards[player] |= 1 << (12 - i);
							break;
						}
					}
				}
				// a pair
				else if ((bestCombo & 0x00F0_0000) != 0) {
					int j = 0;
					for (int i = 0; i < 13; i++) {
						if ((cardsWithoutHit & (1 << (12 - i))) != 0) {
							playerBestCards[player] |= 1 << (12 - i);
							if (j++ == 2)
								break;
						}
					}
				}
				// three
				else if ((bestCombo & 0x0F00_0000) != 0) {
					int j = 0;
					for (int i = 0; i < 13; i++) {
						if ((cardsWithoutHit & (1 << (12 - i))) != 0) {
							playerBestCards[player] |= 1 << (12 - i);
							if (j++ == 1)
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

	public static void setCores(int cores) {
		RoundCheckerInt.cores = cores;
	}

	public int[] getPlayerEquity() {
		return playerEquity;
	}
	
}









