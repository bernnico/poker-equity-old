import calculator.EquityCalculator;
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
		players[0] = new Player("Roma", new Card(Image._5, Suit.c), new Card(Image._6, Suit.s));
		players[1] = new Player("Wadim", new Card(Image._9, Suit.c), new Card(Image._J, Suit.h));
		

		timeStart = System.nanoTime();
		long list[] = bg.generateBourdsList(players);
		timeStop = System.nanoTime();
		System.out.printf("generator2: %,d\n", (timeStop - timeStart));

		
		EquityCalculator ec0 = new EquityCalculator(players, list, 0);
		EquityCalculator ec1 = new EquityCalculator(players, list, 1);
		EquityCalculator ec2 = new EquityCalculator(players, list, 2);
		EquityCalculator ec3 = new EquityCalculator(players, list, 3);
		ec0.start();
		ec1.start();
		ec2.start();
		ec3.start();
		ec0.join();
		ec1.join();
		ec2.join();
		ec3.join();

		// new int[3]; //
		int equity0[] = ec0.getPlayerEquity();
		int equity1[] = ec1.getPlayerEquity();
		int equity2[] = ec2.getPlayerEquity();
		int equity3[] = ec3.getPlayerEquity();
		
		for (int i = 0; i < equity0.length; i++) {
			System.out.println(i + ":\t" + (equity0[i] + equity1[i] + equity2[i] + equity3[i]));
		}
		
		
		
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
