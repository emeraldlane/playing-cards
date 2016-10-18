package com.emeraldlane.playingcards;

/**
 * The PlayingCard class models a standard poker playing card with suit, rank, and color.  
 * 
 * @author jhenry
 *
 */
public class PlayingCard implements Comparable<PlayingCard> {
	
	private final Suit suit;
	private final Rank rank;
	private final Color color;
	
	public PlayingCard(Rank rank, Suit suit, Color color) {
		this.rank = rank;
		this.suit = suit;
		this.color = color;
	}

	public Suit getSuit() {
		return suit;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public Color getColor() {
		return color;
	}
	
	public String toString() {
		return rank.toString() + " of " + suit.toString() + "s";
	}

	/**
	 * Returns less than 0 if this card rank is lower than card passed in, 0 if equal, greater than 0 if greater. Default order is higher rank beats lower rank from 2..10,J,Q,K,A.
	 * 
	 * @param card The card being compared to this card
	 * @return less than 0 if this card rank is lower than card passed in, 0 if equal, greater than 0 if greater
	 */
	@Override
	public int compareTo(PlayingCard card) {
		return rank.compareTo(card.rank);
	}
}
