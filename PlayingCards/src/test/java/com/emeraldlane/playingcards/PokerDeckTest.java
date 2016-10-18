package com.emeraldlane.playingcards;

import java.util.HashMap;

import junit.framework.TestCase;

/**
 * Test the PokerDeck dealOneCard and testShuffle methods.
 * 
 * @author jhenry
 */
public class PokerDeckTest extends TestCase {
	
	private static final int DECK_SIZE = 52;
	private static final int SHUFFLE_TESTS = 100_000; 
	
	/**
	 * Asserts that the deck size is always 52 minus the number of cards dealt.
	 */
	public void testDealOneCard() {
		PokerDeck deck = new PokerDeck();
		assertEquals(DECK_SIZE, deck.count());
		int cardsDealt = 0;
		PlayingCard card = null;
		for (int i = 0; i <= DECK_SIZE; i++) { // attempt to deal 53 cards (final deal should yield null)
			card = deck.dealOneCard();
			if (card == null) {
				assertTrue(i == DECK_SIZE);
			} else {
				cardsDealt++;
			}
			assertEquals(DECK_SIZE-cardsDealt, deck.count());
		}
		assertTrue("A call to dealOneCard on an empty deck should yield null.", card == null);
	}
	
	/**
	 * Test that the shuffle method produces a set of random cards, where each card has equal likelihood of being in any position in the deck.
	 * The expected number of times a card ends up in any given position will be close to the number of tests run divided by the 52 positions.
	 * Assert that the standard deviation from the mean is within 1% for every card after 100,000 (SHUFFLE_TESTS) tests of shuffle.  
	 */
	public void testShuffle() {	
		HashMap<Suit, HashMap<Rank, int[]>> cardPositions = new HashMap<Suit, HashMap<Rank, int[]>>();
		for (Suit suit: Suit.values()) { 
			HashMap<Rank, int[]> positions = new HashMap<Rank, int[]>();
			for (Rank rank: Rank.values()) {
				positions.put(rank, new int[DECK_SIZE]);
			}
			cardPositions.put(suit, positions);
		}
		
		for (int i = 0; i < SHUFFLE_TESTS; i++) {
			PokerDeck deck = new PokerDeck();
			deck.shuffle();
			for (int j = 0; j < DECK_SIZE; j++) {
				PlayingCard card = deck.dealOneCard();
				cardPositions.get(card.getSuit()).get(card.getRank())[j]++;
			}
		}
		
		double expectedMean = SHUFFLE_TESTS / DECK_SIZE;
		for (Suit suit: Suit.values()) {
			for (Rank rank: Rank.values()) {
				int[] positions = cardPositions.get(suit).get(rank);
				double variance = variance(expectedMean, positions);
			    double standardDeviation = Math.sqrt(variance);
			    assertTrue("standard deviation: " + standardDeviation + ", mean: " + expectedMean + ", tests: " + SHUFFLE_TESTS, 
			    		standardDeviation / expectedMean < 0.01);			    
			}
		}
	}
	
	/**
	 * Calculate the standard deviation of a set of integers from the expected mean value of the integers.
	 * 
	 * @param expectedMean the expected mean value of the integers
	 * @param data an array of ints
	 * @return the standard deviation of the data from the mean 
	 */
	private static double variance(double expectedMean, int[] data) {
		double mean = expectedMean;
	    double variance = 0.0;
	    double divisor = data.length - 1;
	    for (int x : data) {
	    	double deviation = x - mean;
	    	variance += (deviation * deviation) / divisor;
	    }
	    return Math.sqrt(variance);
	}
}
