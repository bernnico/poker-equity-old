package calculator;

import card.Card;
import card.Image;
import card.Suit;

public class Defs {
	static Card card = new Card();

	/* BoardGenerator */
	protected static final int IMAGES = 13;
	protected static final long _A_h = 0x01L << 48;
	protected static final long _A_k = 0x02L << 48;
	protected static final long _A_s = 0x04L << 48;
	protected static final long _A_c = 0x08L << 48;
	
	protected static final int SUITS = 4;
	protected static final int H = 0;
	protected static final int K = 1;
	protected static final int S = 2;
	protected static final int C = 3;
	
	protected static final int  NEXT_CARD	   = 0x04;
	
	protected static long allHeart;
	protected static long allDiamond;
	protected static long allSpade;
	protected static long allClubs;
	
	protected static long straight = 0x0F << 13;
	
	/* EquityCalculator */
	protected static final int SLEEPTIME = 1000;
	
	protected static final long COMBOS  = 0x06L << (13 << 4);
	protected static final long PAIRS   = 0x02L << (13 << 4);
	protected static final long THREES  = 0x03L << (13 << 4);
	protected static final long FOURS   = 0x04L << (13 << 4);
	
	// most significant 4 bit
	protected static final long COMBO_MASK	= 0x0FL << 28;
	protected static final long PAIR		= 0x01L << 28;
	protected static final long TOW_PAIR	= 0x02L << 28;
	protected static final long THREE		= 0x03L << 28;
	protected static final long STRAIGHT	= 0x04L << 28;
	protected static final long FLUSH		= 0x05L << 28;
	protected static final long FULLHOUSE	= 0x06L << 28;
	protected static final long FOUR		= 0x07L << 28;
	protected static final long ST_FLUSH	= 0x08L << 28;
		
	
	
	
	public static void main(String[] args) throws InterruptedException {
		init();
		
		System.out.printf("%x\n",allHeart);
		System.out.printf("%x\n",allDiamond);
		System.out.printf("%x\n",allSpade);
		System.out.printf("%x\n",allClubs);
		
		
	}
	public Defs() {
		init();
	}

	private static void init() {
		
		for (int i = 0; i < IMAGES * SUITS; i += NEXT_CARD) {
			allHeart   |= card.getCardAsLong(Image._2, Suit.h) << i;
			allDiamond |= card.getCardAsLong(Image._2, Suit.k) << i;
			allSpade   |= card.getCardAsLong(Image._2, Suit.s) << i;
			allClubs   |= card.getCardAsLong(Image._2, Suit.c) << i;
		}
	}
	
}































