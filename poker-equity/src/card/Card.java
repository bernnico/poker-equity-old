package card;

public class Card {
	
	public Card() {
		
	}
	
	public long getCardAsLong(Image image, Suit suit) {
		long card = (1L << (image.ordinal() << 2)) << suit.ordinal();
		
		return card;
	}

}
