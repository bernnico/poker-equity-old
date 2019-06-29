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
		players[0] = new Player("Roma", new Card(Image._2, Suit.c), new Card(Image._2, Suit.s));
		players[1] = new Player("Wadim", new Card(Image._A, Suit.c), new Card(Image._A, Suit.s));
		

		timeStart = System.nanoTime();
		bg.generateBourdsList(players);
		timeStop = System.nanoTime();
		System.out.printf("generator2: %,d\n", (timeStop - timeStart));

		
		EquityCalculator ec = new EquityCalculator(players);

		int equity[] = ec.getPlayerEquity();
		
		
		
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
