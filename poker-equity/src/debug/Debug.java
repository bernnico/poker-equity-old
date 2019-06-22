package debug;

public class Debug {
	
//	private long runTimeNono[] = new long[10];
	
	private static int stFlush;
	private static int four;
	private static int fullHouse;
	private static int flush;
	private static int straight;
	private static int three;
	private static int twoPair;
	private static int pair;
	private static int high;
	
	public Debug() {
		
	}
	
	public static void printAllHands(int size) {
		System.out.printf("high\t\t%f\n", 100.0 * high / size);
		System.out.printf("pair\t\t%f\n", 100.0 * pair / size);
		System.out.printf("twoPair\t\t%f\n", 100.0 * twoPair / size);
		System.out.printf("three\t\t%f\n", 100.0 * three / size);
		System.out.printf("straight\t%f\n", 100.0 * straight / size);
		System.out.printf("flush\t\t%f\n", 100.0 * flush / size);
		System.out.printf("fullHouse\t%f\n", 100.0 * fullHouse / size);
		System.out.printf("four\t\t%f\n", 100.0 * four / size);
		System.out.printf("stFlush\t\t%f\n", 100.0 * stFlush / size);
		System.out.printf("all\t\t%f\n",
				100.0 * (high + pair + twoPair + three + straight + flush + fullHouse + four + stFlush) / size);
	}
	
	public static void printWinLose(int size, int playerEquity, int player) {
		
		System.out.printf("payer%d wins in\t%f\n", player,  100.0 * playerEquity / size);
//		System.out.printf("payer1 loses in\t%f\n", 100.0 * playerEquity / size);
	}
	
	
	public static void addPlayerCards(long playerWinCards) {
		
		if ((playerWinCards & 0xF000_0000) == 0x7000_0000)
			stFlush++;
		else if ((playerWinCards & 0xF000_0000) == 0x6000_0000)
			four++;
		else if ((playerWinCards & 0xF000_0000) == 0x5000_0000)
			fullHouse++;
		else if ((playerWinCards & 0xF000_0000) == 0x4000_0000)
			flush++;
		else if ((playerWinCards & 0xF000_0000) == 0x3000_0000)
			straight++;

		// --0mia-_set-htwo-ltwo-000k-kick-kick-kick
		else if ((playerWinCards & 0xF000_0000) == 0x2000_0000)
			three++;
		else if ((playerWinCards & 0xF000_0000) == 0x1000_0000)
			twoPair++;
		else if ((playerWinCards & 0x00F0_0000) != 0 && (playerWinCards & 0xFF0F_0000) == 0)
			pair++;
		else if ((playerWinCards & 0x0000_1FFF) == playerWinCards)
			high++;
	}

}







