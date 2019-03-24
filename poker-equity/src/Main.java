import card.BoardGenerator2;
import card.Card;
import card.Image;
import card.Suit;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		//System.out.println("09 " + Thread.currentThread().getName());
		
		
		BoardGenerator2 bg = new BoardGenerator2();
		long timeStart = System.nanoTime();
		bg.setBourdList();
		
		
		
		//System.out.println("21 " + Thread.currentThread().getName());
		bg.th.join();
		//System.out.println(bg.getBourdList().size());
		
		System.out.println(System.nanoTime()-timeStart);
	}
}
