package card;

public enum Suit {
//	_non, // = 0
	h, //clubs,
	k, //diamonds,
	s, //hearts,
	c; //spades
	
	public String getSuit(int image) {
		if (image == 0) return "\u2661";
		else if (image == 1) return "\u2663";
		else if (image == 2) return "\u2660";
		else if (image == 3) return "\u2662";
		
		return "+";
	}
}  
