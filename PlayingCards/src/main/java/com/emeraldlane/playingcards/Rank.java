package com.emeraldlane.playingcards;

/**
 * The ranks of a poker-style playing card, ordered from two to Ace.
 * 
 * @author jhenry
 */
public enum Rank {
	Two(2), 
	Three(3), 
	Four(4), 
	Five(5), 
	Six(6), 
	Seven(7), 
	Eight(8), 
	Nine(9), 
	Ten(10), 
	Jack(11), 
	Queen(12), 
	King(13),
	Ace(14);
	
	private int value;
	
	Rank(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
