package card;

public class Card {
	private Image image;
	public Suit suit;
	
	public Card(Image image, Suit suit) {
		this.image = image;
		this.suit = suit;
		
	}
	
	public long[] getCardAsLong() {
		long[] card = new long[4];
		card[suit.ordinal()] = 1L << (image.ordinal() * 4);
		
		return card;
	}

}
