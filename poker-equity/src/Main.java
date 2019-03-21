import card.BourdGenerator;
import card.Card;
import card.Image;
import card.Suit;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		System.out.println(Thread.currentThread().getName());
		
		
		BourdGenerator bg = new BourdGenerator();
		long timeStart = System.nanoTime();
		bg.setBourdList();
		
		System.out.println(System.nanoTime()-timeStart);
		
		;
		
		
		System.out.println(Thread.currentThread().getName());
		bg.th.join();
		System.out.println(bg.getBourdList().size());
	}
}
