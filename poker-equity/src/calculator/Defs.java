package calculator;

import card.Card;
import card.Image;
import card.Suit;

public class Defs {
	static Card card = new Card();

	/* BoardGenerator */
	protected static final int IMAGES = 13;
	protected static final long _A = 0x01000000000000L;
	protected static final long _K = 0x00100000000000L;
	protected static final long _Q = 0x00010000000000L;
	protected static final long _J = 0x00001000000000L;
	protected static final long _T = 0x00000100000000L;
	protected static final long _9 = 0x00000010000000L;
	protected static final long _8 = 0x00000001000000L;
	protected static final long _7 = 0x00000000100000L;
	protected static final long _6 = 0x00000000010000L;
	protected static final long _5 = 0x00000000001000L;
	protected static final long _4 = 0x00000000000100L;
	protected static final long _3 = 0x00000000000010L;
	protected static final long _2 = card.getCardAsLong(Image._2, Suit.c);
	
	protected static final int SUITS = 4;
	protected static final int H = 0;
	protected static final int K = 1;
	protected static final int S = 2;
	protected static final int C = 3;
	
	protected static final int  NEXT_CARD	   = 0x04;
	protected static final long MASK_CLEAR_ALL = 0x00L;

	/* EquityCalculator */
	protected static final int SLEEPTIME = 1000;
	
	protected static final long COMBOS  = 0x06666666666666L;
	protected static final long PAIRS   = 0x02222222222222L;
	protected static final long THREES  = 0x03333333333333L;
	protected static final long FOURS   = 0x04444444444444L;
	
	
	// most significant 4 bit
	protected static final long PAIR =		0x01 << 60;
	protected static final long TOW_PAIR =	PAIR + 1;
	protected static final long THREE =		PAIR + 2;
	protected static final long STRAIGHT =	PAIR + 3;
	protected static final long FLUSH =		PAIR + 4;
	protected static final long FULLHOUSE =	PAIR + 5;
	protected static final long FOUR =		PAIR + 6;
	protected static final long ST_FLUSH =	PAIR + 7;
}















