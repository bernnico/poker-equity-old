package card;

import player.Player;

public class BoardsListInt {
	/* ========================= TESTS ========================== */
	private long timeStart, timeStop;
	/* ========================================================== */

	private int[][] ncp = new int[6][];
	private int[] matrix5;
//	private int[] matrix3;
	
	private int list[][];
//	private long cardsInGame;

	public BoardsListInt() {
		timeStart = System.nanoTime();

		list = new int[2][2598960];
		
		ncp[0] = new int[1];
		ncp[1] = new int[13];
		ncp[2] = new int[78];
		ncp[3] = new int[286];
		ncp[4] = new int[715];
		ncp[5] = new int[1287];

		fill13ncp(1);
		fill13ncp(2);
		fill13ncp(3);
		fill13ncp(4);
		fill13ncp(5);
		
		matrix5 = new int[] {   0x0005, 0x0050, 0x0500, 0x5000,
				
				               0x0014, 0x0140, 0x1400, 0x4001,
				               0x0104, 0x1040, 0x0401, 0x4010,
				               0x1004, 0x0041, 0x0410, 0x4100,
				               
				               0x0023, 0x0230, 0x2300, 0x3002,
	                           0x0203, 0x2030, 0x0302, 0x3020,
	                           0x2003, 0x0032, 0x0320, 0x3200,
	                           
	                           0x0113, 0x1013, 0x1103, 0x1130,
                               0x0131, 0x1031, 0x1301, 0x1310,
                               0x0311, 0x3011, 0x3101, 0x3110,
                               
                               0x0122, 0x1022, 0x1202, 0x1220,
                               0x0212, 0x2012, 0x2102, 0x2120,
                               0x0221, 0x2021, 0x2201, 0x2210,
                               
                               0x1112, 0x1121, 0x1211, 0x2111};
		
//		run();
		
		timeStop = System.nanoTime();
		System.out.printf("generator int: %,d\n", (timeStop - timeStart));
	}

	private void fill13ncp(int cards) {
		int[] firstCards = new int[cards];
		int[] lastCards = new int[cards];
		
		firstCards[cards - 1] = 0x0001;
		lastCards[cards - 1] = firstCards[cards - 1]  << 13 - cards;
		ncp[cards][0] |= firstCards[cards - 1];
		
		for (int i = 1; i < cards; i++) {
			firstCards[cards - 1 - i] = firstCards[cards - i] << 1;
			lastCards[cards - 1 - i] = firstCards[cards - 1 - i] << 13 - cards;
			
			ncp[cards][0] |= firstCards[cards - 1 - i];
		}
		run(ncp[cards], firstCards, lastCards);
	}

	private void run(int[] boards, int[] firstCards, int[] lastCards) {
//		System.out.printf(" 0\t%4x\n", boards[0]);
		
		for (int next = 1; next < boards.length; next++) {
			if (firstCards[0] != lastCards[0]) {
				firstCards[0] <<= 1;
			} else if (firstCards.length > 1) {
				if (firstCards[1] != lastCards[1]) {
					firstCards[1] <<= 1;
					firstCards[0] = firstCards[1] << 1;
				} else if (firstCards.length > 2) {
					if (firstCards[2] != lastCards[2]) {
						firstCards[2] <<= 1;
						firstCards[1] = firstCards[2] << 1;
						firstCards[0] = firstCards[1] << 1;
					} else if (firstCards.length > 3) {
						if (firstCards[3] != lastCards[3]) {
							firstCards[3] <<= 1;
							firstCards[2] = firstCards[3] << 1;
							firstCards[1] = firstCards[2] << 1;
							firstCards[0] = firstCards[1] << 1;
						} else if (firstCards.length > 4) {
							if (firstCards[4] != lastCards[4]) {
								firstCards[4] <<= 1;
								firstCards[3] = firstCards[4] << 1;
								firstCards[2] = firstCards[3] << 1;
								firstCards[1] = firstCards[2] << 1;
								firstCards[0] = firstCards[1] << 1;
							}
						}
					}
				}
			}
			for (int card = 0; card < firstCards.length; card++) {
				boards[next] |= firstCards[card];
			}
//			System.out.printf("%2d\t%4x\n",next, boards[next]);
		}
	}
	
	public void run() {
		timeStart = System.nanoTime();
		int sh = 0, dc = 0;
		int s, h, d, c;
		int sSize, hSize, dSize, cSize;
		
		int size = 0;
		for (int i = 0; i < matrix5.length; i++) {
			s = matrix5[i] >> 12;
			h = matrix5[i] >>  8 & 0x0F;
			d = matrix5[i] >>  4 & 0x0F;
			c = matrix5[i]       & 0x0F;
		
			sSize = ncp[s].length;
			hSize = ncp[h].length;
			dSize = ncp[d].length;
			cSize = ncp[c].length;
			
			for (int is = 0; is < sSize; is++) {
				for (int ih = 0; ih < hSize; ih++) {
					sh = ncp[s][is] << 16 | ncp[h][ih];
					
					for (int id = 0; id < dSize; id++) {
						for (int ic = 0; ic < cSize; ic++) {
							dc = ncp[d][id] << 16 | ncp[c][ic];
							list[0][size] = sh;
							list[1][size] = dc;
							size++;
						}
					}
				}
			}
		}
		timeStop = System.nanoTime();
		System.out.printf("generator int:\t\t%,d\n", (timeStop - timeStart));
	}



//	public int[] generateBourdsList(Player... players) {
//		list = new int[2598960];
//		
//		setCardsInGame(players);
//		this.run();
//		
//		return list;
//	}
//	
//	private void setCardsInGame(Player... players) {
//		this.cardsInGame = 0;
//		
//		for (int i = 0; i < players.length; i++) {
//			cardsInGame |= players[i].getHandAsLong();
//		}
//	}
//	
//	public long getNext(int i) {
//		return list[i];
//	}

}
