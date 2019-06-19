package card;

public enum Image {
//	_non,
	_2,
	_3,
	_4,
	_5,
	_6,
	_7,
	_8,
	_9,
	_T,
	_J,
	_Q,
	_K,
	_A;
	
	public String getImage(int image) {
		if (image == 0) return "2";
		else if (image == 1) return "3";
		else if (image == 2) return "4";
		else if (image == 3) return "5";
		else if (image == 4) return "6";
		else if (image == 5) return "7";
		else if (image == 6) return "8";
		else if (image == 7) return "9";
		else if (image == 8) return "T";
		else if (image == 9) return "J";
		else if (image == 10) return "Q";
		else if (image == 11) return "K";
		else if (image == 12) return "A";
		
		return "-";
	}
	
	
}
