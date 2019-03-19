package table;

import java.util.LinkedList;

import player.Player;

public class Table {
	private int howMax;
	private LinkedList<Player> players;
	private Player playerOnBTN;
	private int pot;
	private int bb;
	private  BettingRound round;
	
	
	public Table(int howMax, int bb) {
		this.howMax = howMax;
		this.players = new LinkedList<Player>();
		this.playerOnBTN = null;
		this.pot = 0;
		this.bb = bb;
		this.round = BettingRound.PREFLOP;
	}
	
	public void addPlayer(Player player) {
		players.addFirst(player);
	}
	

}
