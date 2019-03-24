package calculator;

import java.util.LinkedList;
import java.util.Queue;

public class BoardGenerator extends Thread {
	private Long[][] playerCards; // better a list -> later
	private Long[] board = new Long[4];
	private Queue<Long[]> boardQueue = new LinkedList<Long[]>();
	
	private long image1, image2, image3, image4, image5;
	private int suit1, suit2, suit3, suit4, suit5;
	
	public BoardGenerator(Long[][] pc) {
		this.playerCards = pc;
	}

	@Override
	public void run() {
		init();
//		System.out.println("41 " + th.getName());
		while (board[Defs.C] != Defs.LAST_BOURD) {
			moveCard1();
			moveCard2();
			moveCard3();
			moveCard4();
			moveCard5();
		}
//		System.out.println(i);	
	}
	
	private void init() {
		suit1 = Defs.H;
		suit2 = Defs.H;
		suit3 = Defs.H;
		suit4 = Defs.H;
		suit5 = Defs.H;
		
		image5 = Defs._2;
		image4 = Defs._3;
		image3 = Defs._4;
		image2 = Defs._5;
		image1 = Defs._6;
		
		board[Defs.H] = Defs.FIRST_BOURD;
		board[Defs.K] = Defs.MASK_CLEAR_ALL;
		board[Defs.S] = Defs.MASK_CLEAR_ALL;
		board[Defs.C] = Defs.MASK_CLEAR_ALL;
//i++;
	}
	
	private void addToQueue() {
		// check double cards -> later checkDoubleCards() + player list
		if (((playerCards[0][0] & playerCards[1][0] & board[0])
				| (playerCards[0][1] & playerCards[1][1] & board[1])
				| (playerCards[0][2] & playerCards[1][2] & board[2])
				| (playerCards[0][3] & playerCards[1][3] & board[3])) != 0)  {
			return;
		}
		
		if (boardQueue.size() < 100000) {
			Long[] l = new Long[4];
			l[0] = new Long(board[0]);
			l[1] = new Long(board[1]);
			l[2] = new Long(board[2]);
			l[3] = new Long(board[3]);
			boardQueue.add(l);
		} else {
			try {
				this.sleep(Defs.SLEEPTIME);
			} catch (InterruptedException e) {
				//e.printStackTrace();
				addToQueue();
			}
		}
	}
	
	private void moveCard1() {
		while (!(image1 == Defs._A && suit1 == Defs.C)) {
			if (image1 == Defs._A) {
				board[suit1] ^= image1;
				image1 = Defs._2;
				suit1++;
				board[suit1] |= image1;
			} else {
				board[suit1] ^= image1;
				image1 <<= Defs.NEXT_CARD;
				board[suit1] |= image1;
			}
			addToQueue();
//i++;
		}
	}
	
	private void moveCard2() {
		if (!(suit2 == Defs.C && image2 == Defs._K)) {
			board[suit2] ^= image2;
			board[suit1] ^= image1;
			if (image2 == Defs._A) {
				image2 = Defs._2;
				image1 = Defs._3;
				suit2++;
				suit1 = suit2;
			} else {
				image2 <<= Defs.NEXT_CARD;
				if(image2 == Defs._A) {
					image1 = Defs._2;
					suit1 = suit2 + 1;
				} else {
					image1 = image2 << Defs.NEXT_CARD;
					suit1 = suit2;
				}
			}
			board[suit2] |= image2;
			board[suit1] |= image1;
			addToQueue();
//i++;
		}
	}
	
	private void moveCard3() {
		if (!(suit3 == Defs.C && image3 == Defs._Q)
				&& suit2 == Defs.C && image2 == Defs._K) {
			board[suit3] ^= image3;
			board[suit2] ^= image2;
			board[suit1] ^= image1;
			if (image3 == Defs._A) {
				image3 = Defs._2;
				image2 = Defs._3;
				image1 = Defs._4;
				suit3++;
				suit2 = suit3;
				suit1 = suit2;
			} else {
				image3 <<= Defs.NEXT_CARD;
				if(image3 == Defs._A) {
					image2 = Defs._2;
					image1 = Defs._3;
					suit2 = suit3 + 1;
					suit1 = suit2;
				} else {
					image2 = image3 << Defs.NEXT_CARD;
					suit2 = suit3;
					if(image2 == Defs._A) {
						image1 = Defs._2;
						suit1 = suit2 + 1;
					} else {
						image1 = image2 << Defs.NEXT_CARD;
						suit1 = suit2;
					}
				}
			}
			board[suit3] |= image3;
			board[suit2] |= image2;
			board[suit1] |= image1;
			addToQueue();
//i++;
		}
		
	}

	private void moveCard4() {
		if (!(suit4 == Defs.C && image4 == Defs._J)
				&& suit3 == Defs.C && image3 == Defs._Q) {
			board[suit4] ^= image4;
			board[suit3] ^= image3;
			board[suit2] ^= image2;
			board[suit1] ^= image1;
			if (image4 == Defs._A) {
				image4 = Defs._2;
				image3 = Defs._3;
				image2 = Defs._4;
				image1 = Defs._5;
				suit4++;
				suit3 = suit4;
				suit2 = suit3;
				suit1 = suit2;
			} else {
				image4 <<= Defs.NEXT_CARD;
				if(image4 == Defs._A) {
					image3 = Defs._2;
					image2 = Defs._3;
					image1 = Defs._4;
					suit3 = suit4 + 1;
					suit2 = suit3;
					suit1 = suit2;
				} else {
					image3 = image4 << Defs.NEXT_CARD;
					suit3 = suit4;
					if(image3 == Defs._A) {
						image2 = Defs._2;
						image1 = Defs._3;
						suit2 = suit3 + 1;
						suit1 = suit2;
					} else {
						image2 = image3 << Defs.NEXT_CARD;
						suit2 = suit3;
						if(image2 == Defs._A) {
							image1 = Defs._2;
							suit1 = suit2 + 1;
						} else {
							image1 = image2 << Defs.NEXT_CARD;
							suit1 = suit2;
						}
					}
				}
			}
			
			board[suit4] |= image4;
			board[suit3] |= image3;
			board[suit2] |= image2;
			board[suit1] |= image1;
			addToQueue();
//i++;
		}
	}

	private void moveCard5() {
		if ((board[Defs.C] | (Defs._A | Defs._K | Defs._Q | Defs._J)) == board[Defs.C]) {
			board[Defs.C] ^= image1 | image2 | image3 | image4;
			board[suit5] ^= image5;
			if (image5 == Defs._A) {
				image5 = Defs._2;
				image4 = Defs._3;
				image3 = Defs._4;
				image2 = Defs._5;
				image1 = Defs._6;
				suit5++;
				suit4 = suit5;
				suit3 = suit4;
				suit2 = suit3;
				suit1 = suit2;
			} else {
				image5 <<= Defs.NEXT_CARD;
				if(image5 == Defs._A) {
					image4 = Defs._2;
					image3 = Defs._3;
					image2 = Defs._4;
					image1 = Defs._5;
					suit4 = suit5 + 1;
					suit3 = suit4;
					suit2 = suit3;
					suit1 = suit2;
				} else {
					image4 = image5 << Defs.NEXT_CARD;
					suit4 = suit5;
					if(image4 == Defs._A) {
						image3 = Defs._2;
						image2 = Defs._3;
						image1 = Defs._4;
						suit3 = suit4 + 1;
						suit2 = suit3;
						suit1 = suit2;
					} else {
						image3 = image4 << Defs.NEXT_CARD;
						suit3 = suit4;
						if(image3 == Defs._A) {
							image2 = Defs._2;
							image1 = Defs._3;
							suit2 = suit3 + 1;
							suit1 = suit2;
						} else {
							image2 = image3 << Defs.NEXT_CARD;
							suit2 = suit3;
							if(image2 == Defs._A) {
								image1 = Defs._2;
								suit1 = suit2 + 1;
							} else {
								image1 = image2 << Defs.NEXT_CARD;
								suit1 = suit2;
							}
						}
					}
				}
			}
			board[suit5] |= image5;
			board[suit4] |= image4;
			board[suit3] |= image3;
			board[suit2] |= image2;
			board[suit1] |= image1;
			addToQueue();
//i++;
		}
	}

	public Queue<Long[]> getQuee() {
		return boardQueue;
	}

	public Long[][] getPlayerCards() {
		// TODO Auto-generated method stub
		return playerCards;
	}
}
