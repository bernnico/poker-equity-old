import calculator.Equity;
import calculator.RoundChecker;
import card.BoardsList;
import card.Card;
import card.Image;
import card.Suit;
import player.Player;

public class Main {
	
	static long timeStart = System.nanoTime();
	static long timeStop = System.nanoTime();

	public static void main(String[] args) throws InterruptedException {
		BoardsList bg = new BoardsList();
		
		
		Player[] players = new Player[2];
		players[0] = new Player("Wadim", new Card(Image._9, Suit.c), new Card(Image._J, Suit.h));
		players[1] = new Player("Roma", new Card(Image._5, Suit.c), new Card(Image._6, Suit.s));
		
		Equity eq = new Equity();
		for (int i = 0; i < 10; i++) {
			int e[]  = eq.getEquity(players);
			for (int j = 0; j < 3; j++) {
				System.out.println(j + ":   " + e[j]);
			}
		}

		timeStart = System.nanoTime();
		long list[] = bg.generateBourdsList(players);
		timeStop = System.nanoTime();
		System.out.printf("generator2: %,d\n", (timeStop - timeStart));

		RoundChecker ec0;
		RoundChecker ec1;
//		EquityCalculator ec2;
//		EquityCalculator ec3;
//		EquityCalculator ec4;
//		EquityCalculator ec5;
		
		for (int i = 0; i < 0; i++) {
			ec0 = new RoundChecker(players, list, 0);
			ec1 = new RoundChecker(players, list, 1);
//			ec2 = new EquityCalculator(players, list, 2);
//			ec3 = new EquityCalculator(players, list, 3);
//			ec4 = new EquityCalculator(players, list, 4);
//			ec5 = new EquityCalculator(players, list, 5);
			
			ec0.start();
			ec1.start();
//			ec2.start();
//			ec3.start();
//			ec4.start();
//			ec5.start();
			
			ec0.join();
			ec1.join();
//			ec2.join();
//			ec3.join();
//			ec4.join();
//			ec5.join();

			// new int[3]; //
			int equity0[] = ec0.getPlayerEquity();
			int equity1[] = ec1.getPlayerEquity();
			int equity2[] = new int[3]; //ec2.getPlayerEquity();
			int equity3[] = new int[3]; //ec3.getPlayerEquity();
			int equity4[] = new int[3]; //ec4.getPlayerEquity();
			int equity5[] = new int[3]; //ec5.getPlayerEquity();
			
			for (int j = 0; j < equity0.length; j++) {
				System.out.println(j + ":\t" +
						(equity0[j] + equity1[j] + equity2[j] + equity3[j] + equity4[j] + equity5[j]));
			}
		}
		System.out.println(Runtime.getRuntime().availableProcessors());
		
		
		
		
//	System.out.printf("complett: %,d\n", timeStop);
//	System.out.println("\nsize:\t" + size);
//	System.out.println("player0\t" + (size - playerEquity[0] - playerEquity[1]));
//	System.out.println("player1\t" + playerEquity[0]);
//	System.out.println("player2\t" + playerEquity[1]);
//	System.out.println(100.0 * playerEquity[2] / size);
//	System.out.println(100.0 * (0.5 * playerEquity[2] + playerEquity[0]) / size);
//	System.out.println(100.0 * (0.5 * playerEquity[2] + playerEquity[1]) / size);
	}
}
