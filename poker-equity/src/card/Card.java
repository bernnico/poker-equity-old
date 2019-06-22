package card;

public class Card {
	private Image image;
	private Suit suit;

	public Card() {
		image = Image._2;
		suit = Suit.s;
	}

	public long getCardsAsLong(Image image, Suit suit) {
		long card = (1L << (image.ordinal() << 2)) << suit.ordinal();
		return card;
	}
	
	public long getCardsAsLong(String st) {
		long card = 0;
		int im, su;
		
		for (int i = 0; i < st.length(); i += 2) {
			
			if (st.length() % 2 != 0) {
				throw new IllegalArgumentException();
			}
			
			switch (st.charAt(i)) {
			case '2': im = 0; break;
			case '3': im = 1; break;
			case '4': im = 2; break;
			case '5': im = 3; break;
			case '6': im = 4; break;
			case '7': im = 5; break;
			case '8': im = 6; break;
			case '9': im = 7; break;
			case 'T': im = 8; break;
			case 'J': im = 9; break;
			case 'Q': im = 10; break;
			case 'K': im = 11; break;
			case 'A': im = 12; break;
			default: throw new IllegalArgumentException();
			}
			
			switch (st.charAt(i+1)) {
			case 's': su = 0; break;
			case 'h': su = 1; break;
			case 'd': su = 2; break;
			case 'c': su = 3; break;
			default: throw new IllegalArgumentException();
			}
			card |= (1L << (im << 2)) << su;
		}
		
		return card;
	}

	public String getCardAsString(long cards) {
		String string = "";

		long images = 0x0F_0000_0000_0000L;
		int suits = 0;
		
		for (int i = 0; i < 13; i++) {
			if ((cards & images) != 0) {
				suits = (int) ((cards & images) >> ((12 - i) << 2));

				if ((0x01 & suits) == 0x01) {
					string += image.getImage(12 - i) + suit.getSuit(0) + "_";
				}
				if ((0x02 & suits) == 0x02) {
					string += image.getImage(12 - i) + suit.getSuit(1) + "_";
				}
				if ((0x04 & suits) == 0x04) {
					string += image.getImage(12 - i) + suit.getSuit(2) + "_";
				}
				if ((0x08 & suits) == 0x08) {
					string += image.getImage(12 - i) + suit.getSuit(3) + "_";
				}
			}
			images >>= 4;
		}
		return string;
	}
	
	public String getCardAsString(int cards) {
		String ret = "";

		long images = 0x0F_0000_0000_0000L;
		int suits = 0;
		
		for (int i = 0; i < 13; i++) {
			if ((cards & images) != 0) {
				suits = (int) ((cards & images) >> ((12 - i) << 2));

				if ((0x01 & suits) == 0x01) {
					ret += image.getImage(12 - i) + suit.getSuit(0) + "_";
				}
				if ((0x02 & suits) == 0x02) {
					ret += image.getImage(12 - i) + suit.getSuit(1) + "_";
				}
				if ((0x04 & suits) == 0x04) {
					ret += image.getImage(12 - i) + suit.getSuit(2) + "_";
				}
				if ((0x08 & suits) == 0x08) {
					ret += image.getImage(12 - i) + suit.getSuit(3) + "_";
				}
			}
			images >>= 4;
		}
		return ret;
	}
	
	public String getBestHand(int cards) {
		String ret = "";
		
		if ((cards & 0xF000_0000) == 0x1000_0000) {
			ret = getStraightAsString(cards);
		}
		else if ((cards & 0xF000_0000) == 0x2000_0000) {
			ret = getFlushAsString(cards);
		}
		else if ((cards & 0xF000_0000) == 0x3000_0000) {
			ret = getFullhouseAsString(cards);
		}
		else if ((cards & 0xF000_0000) == 0x4000_0000) {
			ret = getFourAsString(cards);
		}
		else if ((cards & 0xF000_0000) == 0x5000_0000) {
			ret = getStFlushAsString(cards);
		}

		
		return ret;
	}

	private String getStFlushAsString(int cards) {	
		// TODO Auto-generated method stub
		return null;
	}

	private String getFourAsString(int cards) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getFullhouseAsString(int cards) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getFlushAsString(int cards) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getStraightAsString(int cards) {
		// TODO Auto-generated method stub
		return null;
	}
}






