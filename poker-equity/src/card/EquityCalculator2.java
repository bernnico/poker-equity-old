package card;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class EquityCalculator2 {
	private BoardsList bg;
	private Queue<Long[]> boardList;
	private Thread boardGen, equityTread;
	private double[] boardCards;
	private List<double[]> playerCards;
	
	static final private long COMBOS = 0x06666666666666L;
	
	private Runnable threadArg = new Runnable() {
		@Override public void run() {
			int wins, ties;
			while (boardGen != null) {
				checkPairOrTreeOrFour();
			}
		}

		private void checkPairOrTreeOrFour() {
			
		}
	};
	
	public EquityCalculator2() {
//		bg = new BoardGenerator2();
//		this.boardList = bg.getBourdList();
//		this.boardGen = bg.getThread();
//		equityTread = new Thread(threadArg);
//		this.boardCards = new double[4];
//		this.playerCards = new ArrayList<double[]>();
	}
	
	public double[] getEquity() {
		
		
		
		
		return null;
	}

}
