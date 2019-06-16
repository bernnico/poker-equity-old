package calculator;

import java.util.Queue;

public class EquityCalculator extends Thread {
	private Queue<Long> boardList;
	private long boardCards;
	private long playerHands[];
	private int playerEquity[];
	private long playerHaveCards[];
	private int playerWinCards[];
	
	Defs defs = new Defs();

	public EquityCalculator(Queue<Long> boardList, long boardCards, long... playerHands) {
		this.boardList = boardList;
		this.boardCards = boardCards;
		this.playerHands = playerHands;
		this.playerEquity = new int[playerHands.length];
		this.playerHaveCards = new long[playerHands.length];
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
	
	private void checkFlush() {
		
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
				flashFound = true;
			} 
			if (!flashFound && (playerHaveCards[player] & 0x02_2222_2222_2222L) != 0) {
				flashFound = setFlushIfFound(player, 0x02_0000_0000_0000L); // Ak
				flashFound = true;
			}
			if (!flashFound && (playerHaveCards[player] & 0x04_4444_4444_4444L) != 0) {
				flashFound = setFlushIfFound(player, 0x04_0000_0000_0000L); // As
				flashFound = true;
			}
			if (!flashFound && (playerHaveCards[player] & 0x08_8888_8888_8888L) != 0) {
				flashFound = setFlushIfFound(player, 0x08_0000_0000_0000L); // Ac
			}
		}
		
	}

	private boolean setFlushIfFound(int player, long currentSuit) {
		
		int flush= 0;
		int playingCombination = 0;
		
		for (int image = 0; image < 13; image++) {
			
			if ((playerHaveCards[player] & (currentSuit >> (image << 2))) != 0) {
				flush++;
				playingCombination |= 1 << (13 - image);
			}

			if (flush == 5) {
				playerWinCards[player] = 0x5000_0000; // flush indicator
				playerWinCards[player] |= playingCombination;
				
				return true;
			} 
		}
		
		return false;
	}

	private void checkStraight() {
		
		// 0x0F_0000_0000_0000 : all Asses
		// 0x00_F000_0000_0000 : all Kings
		// 0x00_0F00_0000_0000 : all Queens
		// 0x00_00F0_0000_0000 : all Jacks
		// 0x00_000F_0000_0000 : all Tens

		int highCard = 0;
		
		for (int player = 0; player < playerHaveCards.length; player++) {
			
			for (int i = 0; i < 9; i += 4) {				
				if ((       playerHaveCards[player] & (0x0F_0000_0000_0000L >> i)) != 0
						&& (playerHaveCards[player] & (0x00_F000_0000_0000L >> i)) != 0
						&& (playerHaveCards[player] & (0x00_0F00_0000_0000L >> i)) != 0
						&& (playerHaveCards[player] & (0x00_00F0_0000_0000L >> i)) != 0
						&& (playerHaveCards[player] & (0x00_000F_0000_0000L >> i)) != 0) {
					
					highCard = 0x1F << (13 - (i >> 2));
					
					
					if ((playerHaveCards[player] & 0x5000_0000) != 0x5000_0000) { // flush indicator
						if ((playerHaveCards[player] & 0xFFFF) == highCard) {
							playerWinCards[player] = 0x8000_0000; // straitgh flush
							playerWinCards[player] |= highCard;
							break;
						}
						
						continue;
					} 
					
					playerWinCards[player] = 0x4000_0000; // straight
					playerWinCards[player] |= highCard;
					
					break;
				}
			}
			
			// find 5 4 3 2 A
			if ((       playerHaveCards[player] & 0x4000_0000) == 0 // straight
					&& (playerHaveCards[player] & 0x00_0000_0000_F000L) != 0 // 5
					&& (playerHaveCards[player] & 0x00_0000_0000_0F00L) != 0 // 4
					&& (playerHaveCards[player] & 0x00_0000_0000_00F0L) != 0 // 3
					&& (playerHaveCards[player] & 0x00_0000_0000_000FL) != 0 // 2
					&& (playerHaveCards[player] & 0x0F_0000_0000_0000L) != 0) { // A
				
				if ((playerHaveCards[player] & 0x5000_0000) != 0) { // flush indicator
					if ((playerHaveCards[player] & 0xFFFF) == 0x100F) { // 5 4 3 2 A
						playerWinCards[player] ^= 0x5000_0000;
						playerWinCards[player] |= 0x8000_0000; // royal flush
						return;
					}
				} 
				
				playerWinCards[player] = 0x4000_000F; // straight 5 4 3 2 (A)
			}
		}
	}
	
	private void checkCombos() {
		
//		long pair  = 0x03_0000_0000_0000L;
//		long three = 0x07_0000_0000_0000L;
//		long four  = 0x0F_0000_0000_0000L;
		
		
		// 0b0000_0000_0000_0000_0000_0000_0000_0000
		// --main-0000-0000-0000-_set-htwo-ltwo-kick
		
		int pairHit = 0;
		int threeHit = 0;
		
		int playingCombination = 0;
		
		for (int player = 0; player < playerHaveCards.length; player++) {
			
			for (int image = 0; image < 13; image++) {
				
				if ((playerWinCards[player] & 0x6000_0000) != 0x6000_0000 // full jouse
						&& (playerHaveCards[player] & 0x03_0000_0000_0000L) == 0x03_0000_0000_0000L) { // pairs
					
					if (pairHit == 0) {
						playingCombination |= (13 - image) << 8;
						
					} else if (pairHit == 1) {
						playingCombination |= (13 - image) << 4;
						
					} else if ((playingCombination & 0xF) == 0) {
						playingCombination |= (13 - image);
						
					}
					
					pairHit++;
					
				} else if ((playerWinCards[player] & 0x6000_0000) != 0x6000_0000 // full jouse
						&& (playerHaveCards[player] & 0x07_0000_0000_0000L) == 0x07_0000_0000_0000L) { // threes
		
					if (threeHit == 0) {
						playingCombination |= (13 - image) << 12;
						
					} else if (threeHit == 1) {
						playingCombination |= (13 - image) << 8;
						
						playerWinCards[player] |= 0x6000_0000; //full house
						playerWinCards[player] |= playingCombination; //full house
					}
					
					threeHit++;
					
				} else if ((playerHaveCards[player] & 0x0F_0000_0000_0000L) == 0x0F_0000_0000_0000L) { // fours
					playerWinCards[player] = 0x7000_0000 | (13 - image);
					
					continue;
					
				} else { // high card
					playingCombination |= 13 - image;
					
				}
				
				if ((playerWinCards[player] & 0x4000_0000) != 0x4000_0000  // straight
						&& (playerWinCards[player] & 0x5000_0000) != 0x5000_0000 // flush
						&& (playerWinCards[player] & 0x6000_0000) != 0x6000_0000) { // full house 
					playerWinCards[player] = playingCombination;
				}
			}
			
		}
	}

	public int[] getPlayerEquity() {
		// TODO Auto-generated method stub
		return null;
	}
	
}






















