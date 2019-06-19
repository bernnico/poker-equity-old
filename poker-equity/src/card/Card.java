package card;

public class Card {
	private Image image;
	private Suit suit;

	public Card() {
		image = Image._2;
		suit = Suit.h;
	}

	public long getCardAsLong(Image image, Suit suit) {
		long card = (1L << (image.ordinal() << 2)) << suit.ordinal();
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

		
		return ret;
	}
}






