package com.emeraldlane.playingcards;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * The PokerDeck class is a collection of 52 unique playing cards across four suits and 13 ranks. 
 * Cards can be randomized with a call to shuffle() and dealt with a call to dealOneCard(). 
 * 
 * @author jhenry
 */
public class PokerDeck {
	
	public static final int DECK_SIZE = 52;
	protected ArrayList<PlayingCard> playingCards;
	
	/**
	 * The PokerDeck constructor will create a standard deck of playing cards ordered by rank and suit. 
	 */
	public PokerDeck() {
		playingCards = new ArrayList<>(52);
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				playingCards.add(newPlayingCard(rank, suit));
			}
		}
	}
	
	/**
	 * Default PlayingCard factory method. Override to create decks with new types of PlayingCards
	 * 
	 * @param rank the rank of the card
	 * @param suit the suit of the card
	 * @return
	 */
	protected PlayingCard newPlayingCard(Rank rank, Suit suit) {
		Color color = (suit == Suit.Spade || suit == Suit.Club) ? Color.Black : Color.Red;
		return new PlayingCard(rank, suit, color);
	}
	
	/**
	 * Returns the number of cards remaining in the deck.
	 * 
	 * @return the number of cards
	 */
	public int count() {
		return playingCards.size();
	}
	
	/**
	 * The shuffle method assigns a random value to each card in the current deck and then sorts the cards by the assigned random values. 
	 */
	public void shuffle() {
		TreeMap<Double, PlayingCard> map = new TreeMap<>();
		for (PlayingCard card: playingCards) {
			map.put(Math.random(), card);
		}
		playingCards = new ArrayList<>(map.values());
	}
	
	/**
	 * Removes the last playing card from the deck and returns it.
	 * 
	 * @return the playing card removed from the deck
	 */
	public PlayingCard dealOneCard() {
		PlayingCard card;
		if (playingCards.isEmpty()) {
			card = null;
		} else {
			card = playingCards.remove(playingCards.size() - 1);
		}
		return card;
	}
	
	/**
	 * @return a string showing each card currently in the deck
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		int maxDisplay = 7;
		int displayed = 0;
		for (int i = playingCards.size()-1; i >= 0 && displayed < maxDisplay; i--) {
			if (buffer.length() > 0) {
				buffer.append(", ");
			}
			buffer.append(playingCards.get(i).toString());
			displayed++;
			if (displayed == maxDisplay && displayed < playingCards.size()) {
				buffer.append("...");
			}
		}
		return buffer.toString();
	}
}
