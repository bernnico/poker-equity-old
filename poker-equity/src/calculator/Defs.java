package calculator;

public class Defs {

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
	protected static final long _2 = 0x00000000000001L;
	
	protected static final int SUITS = 4;
	protected static final int H = 0;
	protected static final int K = 1;
	protected static final int S = 2;
	protected static final int C = 3;
	
	protected static final long FIRST_BOURD    = 0x00000000011111L;
	protected static final long LAST_BOURD     = 0x01111100000000L;
	protected static final int  NEXT_CARD	   = 0x04;
	protected static final long MASK_CLEAR_ALL = 0x00L;

	/* EquityCalculator */
	protected static final int SLEEPTIME = 1000;
	
	protected static final long COMBOS  = 0x06666666666666L;
	protected static final long PAIRS   = 0x02222222222222L;
	protected static final long THREES  = 0x03333333333333L;
	protected static final long FOURS   = 0x04444444444444L;
	
	protected static final long pair = 			0x1000000000000000L;
	protected static final long twoPair = 		0x2000000000000000L;
	protected static final long three = 		0x3000000000000000L;
	protected static final long straight = 		0x4000000000000000L;
	protected static final long flush = 		0x5000000000000000L;
	protected static final long fullHouse = 	0x6000000000000000L;
	protected static final long four = 			0x7000000000000000L;
	protected static final long straightFlush = 0x8000000000000000L;
}















