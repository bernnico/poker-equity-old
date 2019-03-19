package player;

import java.util.ArrayList;
import java.util.List;

import card.Card;
import table.Position;

public class Player {
	private String name;
	private int sit;
	private int stack;
	private int bet;
	
	private Position position;
	private List<Card> hand;
	
	
	public Player(String name, int sit, int stack) {
		super();
		this.name = name;
		this.sit = sit;
		this.stack = stack;
		this.bet = 0;
		this.position = null;
		this.hand = new ArrayList<Card>();
	}
}
