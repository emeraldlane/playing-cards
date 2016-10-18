package com.emeraldlane.playingcards;

/**
 * The suits of poker-style playing cards; spade, heart, club, and diamond.
 * 
 * @author jhenry
 */
public enum Suit {	
	Spade(Color.Black),
	Heart(Color.Red),
	Club(Color.Black),
	Diamond(Color.Red);
	
	private final Color color;
	
	private Suit(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
}
