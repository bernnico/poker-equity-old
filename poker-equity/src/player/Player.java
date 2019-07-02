package player;

import card.Card;

public class Player {
	private String name;
	private Card[] cards;
//	private long hand;
//	private int winCards;
	
	private int equity;
	
//	private int sit;
//	private int stack;
//	private int turn;
//	private Position position;
	
	public Player(String name) {
		this.name = name;
	}
	
	public Player(String name, Card... cards) {
		this.name = name;
		this.cards = cards;
		
		setHand(cards);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Card[] getHand() {
		return cards;
	}

	public void setHand(Card... cards) {
		this.cards = cards;
	}
	
	public long getHandAsLong() {
		long hand = 0;
		
		for (int i = 0; i < cards.length; i++) {
			hand |= cards[i].getCardsAsLong();
		}
		return hand;
	}
	
	public int getEquity() {
		return equity;
	}

	public void setEquity(int equity) {
		this.equity = equity;
	}
	
}










