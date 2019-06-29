package player;

import card.Card;

public class Player {
	private String name;
	private long hand;
	
//	private int sit;
//	private int stack;
//	private int turn;
//	private Position position;
	
	
	public Player(String name) {
		this.name = name;
//		this.hand = 0;
	}
	
	public Player(String name, Card... card) {
		this.name = name;
		this.hand = 0;
		
		for (int i = 0; i < card.length; i++) {
			hand |= card[i].getCardsAsLong();
		}
	}

	public String getName() {
		
		return name;
	}

	public void setName(String name) {
		
		this.name = name;
	}

	public long getHand() {
		
		return hand;
	}

	public void setHand(long hand) {
		
		this.hand = hand;
	}
	
}










