package table;

public enum Position {
	SB,
	BB,
	UTG,
	UTG1, // not for 6-max
	UTG2, // not for 6-max
	MP,
	MP2, // not for 6-max
	MP3, // not for 9-max
	CO,
	BTN;
	
	public Position[] get10Max() {
		return new Position[] {SB, BB, UTG, UTG1, UTG2, MP, MP2, MP3, CO, BTN};
	}
	
	public Position[] get9Max() {
		return new Position[] {SB, BB, UTG, UTG1, UTG2, MP, MP2, CO, BTN};
	}
	
	public Position[] get6Max() {
		return new Position[] {SB, BB, UTG, MP, CO, BTN};
	}
	
	public Position[] get2Max() {
		return new Position[] {SB, BB};
	}

}
