import card.BoardGenerator;
import card.Card;
import card.Image;
import card.Suit;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		//System.out.println("09 " + Thread.currentThread().getName());
		
		
		BoardGenerator bg = new BoardGenerator();
		long timeStart = System.nanoTime();
		bg.setCardsInGame(0x08888888888L);
		bg.getBourdList();
		
		
		for (int i = 0; i < 10; i++) {
			if (bg.isBourdListEmpty())
				i--;
			else
				System.out.println(bg.getBourdList().poll());
		}
		
		
		
		 
		
		//System.out.println("21 " + Thread.currentThread().getName());
//		bg.th.join();
		//System.out.println(bg.getBourdList().size());
		
		//System.out.println(System.nanoTime()-timeStart);
		
		//test
	}
}
