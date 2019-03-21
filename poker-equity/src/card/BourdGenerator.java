package card;

import java.util.LinkedList;
import java.util.Queue;

public class BourdGenerator {
	
	static final private long _A = 0x01000000000000L;
	static final private long _K = 0x00100000000000L;
	static final private long _Q = 0x00010000000000L;
	static final private long _J = 0x00001000000000L;
	static final private long _T = 0x00000100000000L;
	static final private long _9 = 0x00000010000000L;
	static final private long _8 = 0x00000001000000L;
	static final private long _7 = 0x00000000100000L;
	static final private long _6 = 0x00000000010000L;
	static final private long _5 = 0x00000000001000L;
	static final private long _4 = 0x00000000000100L;
	static final private long _3 = 0x00000000000010L;
	static final private long _2 = 0x00000000000001L;
	
	static final private int H = 0;
	static final private int K = 1;
	static final private int S = 2;
	static final private int C = 3;
	
	static final private long FIRST_BOURD    = 0x00000000011111L;
	static final private long LAST_BOURD     = 0x01111100000000L;
	static final private int  NEXT_CARD	     = 0x04;
	static final private long MASK_CLEAR_ALL = 0x00L;
	
	private long[] bourd = new long[4];
	private long image1, image2, image3, image4, image5;
	private int suit1, suit2, suit3, suit4, suit5;
	
	public Thread th;
	private Queue<Long[]> bourdList = new LinkedList<Long[]>();
	
	public Queue<Long[]> getBourdList() {
		return bourdList;
	}
	
int i = 0;
	public void setBourdList() {
		init();
		th = new Thread(){
			@Override public void run() {
				
			System.out.println(th.getName());
			while (bourd[C] != LAST_BOURD) {
				moveCard1();
				moveCard2();
				moveCard3();
				moveCard4();
				moveCard5();
				
			}
			System.out.println(i);
			
			if (bourdList.size() < 1000) {
				Long[] l = new Long[4];
				l[0] = new Long(bourd[0]);
				l[1] = new Long(bourd[1]);
				l[2] = new Long(bourd[2]);
				l[3] = new Long(bourd[3]);
				bourdList.add(l);
				//System.out.println(Thread.currentThread().getName());
			} else {
				
			}

		}};
		th.start();
		System.out.println(Thread.currentThread().getName());
	}
	
	private void init() {
		suit1 = H;
		suit2 = H;
		suit3 = H;
		suit4 = H;
		suit5 = H;
		
		image5 = _2;
		image4 = _3;
		image3 = _4;
		image2 = _5;
		image1 = _6;
		
		bourd[H] = FIRST_BOURD;
		bourd[K] = MASK_CLEAR_ALL;
		bourd[S] = MASK_CLEAR_ALL;
		bourd[C] = MASK_CLEAR_ALL;
i++;
	}

	private void moveCard1() {
		while (!(image1 == _A && suit1 == C)) {
			if (image1 == _A) {
				bourd[suit1] ^= image1;
				image1 = _2;
				suit1++;
				bourd[suit1] |= image1;
			} else {
				bourd[suit1] ^= image1;
				image1 <<= NEXT_CARD;
				bourd[suit1] |= image1;
			}
i++;
		}
	}
	
	private void moveCard2() {
		if (!(suit2 == C && image2 == _K)) {
			bourd[suit2] ^= image2;
			bourd[suit1] ^= image1;
			if (image2 == _A) {
				image2 = _2;
				image1 = _3;
				suit2++;
				suit1 = suit2;
			} else {
				image2 <<= NEXT_CARD;
				if(image2 == _A) {
					image1 = _2;
					suit1 = suit2 + 1;
				} else {
					image1 = image2 << NEXT_CARD;
					suit1 = suit2;
				}
			}
			bourd[suit2] |= image2;
			bourd[suit1] |= image1;
i++;
		}
	}
	
	private void moveCard3() {
		if (!(suit3 == C && image3 == _Q)
				&& suit2 == C && image2 == _K) {
			bourd[suit3] ^= image3;
			bourd[suit2] ^= image2;
			bourd[suit1] ^= image1;
			if (image3 == _A) {
				image3 = _2;
				image2 = _3;
				image1 = _4;
				suit3++;
				suit2 = suit3;
				suit1 = suit2;
			} else {
				image3 <<= NEXT_CARD;
				if(image3 == _A) {
					image2 = _2;
					image1 = _3;
					suit2 = suit3 + 1;
					suit1 = suit2;
				} else {
					image2 = image3 << NEXT_CARD;
					suit2 = suit3;
					if(image2 == _A) {
						image1 = _2;
						suit1 = suit2 + 1;
					} else {
						image1 = image2 << NEXT_CARD;
						suit1 = suit2;
					}
				}
			}
			bourd[suit3] |= image3;
			bourd[suit2] |= image2;
			bourd[suit1] |= image1;
i++;
		}
		
	}

	private void moveCard4() {
		if (!(suit4 == C && image4 == _J)
				&& suit3 == C && image3 == _Q) {
			bourd[suit4] ^= image4;
			bourd[suit3] ^= image3;
			bourd[suit2] ^= image2;
			bourd[suit1] ^= image1;
			if (image4 == _A) {
				image4 = _2;
				image3 = _3;
				image2 = _4;
				image1 = _5;
				suit4++;
				suit3 = suit4;
				suit2 = suit3;
				suit1 = suit2;
			} else {
				image4 <<= NEXT_CARD;
				if(image4 == _A) {
					image3 = _2;
					image2 = _3;
					image1 = _4;
					suit3 = suit4 + 1;
					suit2 = suit3;
					suit1 = suit2;
				} else {
					image3 = image4 << NEXT_CARD;
					suit3 = suit4;
					if(image3 == _A) {
						image2 = _2;
						image1 = _3;
						suit2 = suit3 + 1;
						suit1 = suit2;
					} else {
						image2 = image3 << NEXT_CARD;
						suit2 = suit3;
						if(image2 == _A) {
							image1 = _2;
							suit1 = suit2 + 1;
						} else {
							image1 = image2 << NEXT_CARD;
							suit1 = suit2;
						}
					}
				}
			}
			
			bourd[suit4] |= image4;
			bourd[suit3] |= image3;
			bourd[suit2] |= image2;
			bourd[suit1] |= image1;
i++;
		}
	}

	private void moveCard5() {
		if ((bourd[C] | (_A | _K | _Q | _J)) == bourd[C]) {
			bourd[C] ^= image1 | image2 | image3 | image4;
			bourd[suit5] ^= image5;
			if (image5 == _A) {
				image5 = _2;
				image4 = _3;
				image3 = _4;
				image2 = _5;
				image1 = _6;
				suit5++;
				suit4 = suit5;
				suit3 = suit4;
				suit2 = suit3;
				suit1 = suit2;
			} else {
				image5 <<= NEXT_CARD;
				if(image5 == _A) {
					image4 = _2;
					image3 = _3;
					image2 = _4;
					image1 = _5;
					suit4 = suit5 + 1;
					suit3 = suit4;
					suit2 = suit3;
					suit1 = suit2;
				} else {
					image4 = image5 << NEXT_CARD;
					suit4 = suit5;
					if(image4 == _A) {
						image3 = _2;
						image2 = _3;
						image1 = _4;
						suit3 = suit4 + 1;
						suit2 = suit3;
						suit1 = suit2;
					} else {
						image3 = image4 << NEXT_CARD;
						suit3 = suit4;
						if(image3 == _A) {
							image2 = _2;
							image1 = _3;
							suit2 = suit3 + 1;
							suit1 = suit2;
						} else {
							image2 = image3 << NEXT_CARD;
							suit2 = suit3;
							if(image2 == _A) {
								image1 = _2;
								suit1 = suit2 + 1;
							} else {
								image1 = image2 << NEXT_CARD;
								suit1 = suit2;
							}
						}
					}
				}
			}
			bourd[suit5] |= image5;
			bourd[suit4] |= image4;
			bourd[suit3] |= image3;
			bourd[suit2] |= image2;
			bourd[suit1] |= image1;
i++;
		}
	}
}













