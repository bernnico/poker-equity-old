package card;

public class CardShort {
	private short cards[] = new short[4];
	
	public CardShort(String cards) {
		this.cards = setCards(cards);
	}
	
	public short[] getCardShort() {
		return cards;
	}
	
	public int[] getCardInt() {
		int cardsInt[] = new int[2];
		cardsInt[0] = cards[0] << 16 | cards[1]; // s and h
		cardsInt[1] = cards[2] << 16 | cards[3]; // d and c
		
		return cardsInt;
	}

	private short[] setCards(String cards) {
		short result[] = new short[4];
		int image, suit;
		
		for (int i = 0; i < cards.length(); i += 2) {
			if (cards.length() % 2 != 0) {
				throw new IllegalArgumentException();
			}
			switch (cards.charAt(i)) {
			case '2': image = 1 << 0; break;
			case '3': image = 1 << 1; break;
			case '4': image = 1 << 2; break;
			case '5': image = 1 << 3; break;
			case '6': image = 1 << 4; break;
			case '7': image = 1 << 5; break;
			case '8': image = 1 << 6; break;
			case '9': image = 1 << 7; break;
			case 'T': image = 1 << 8; break;
			case 'J': image = 1 << 9; break;
			case 'Q': image = 1 << 10; break;
			case 'K': image = 1 << 11; break;
			case 'A': image = 1 << 12; break;
			default: throw new IllegalArgumentException();
			}
			switch (cards.charAt(i+1)) {
			case 's': suit = 0; break;
			case 'h': suit = 1; break;
			case 'd': suit = 2; break;
			case 'c': suit = 3; break;
			default: throw new IllegalArgumentException();
			}
			result[suit] |= image;
		}
		return result;
	}
	
}
