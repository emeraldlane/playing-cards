package com.emeraldlane.playingcards;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * The classic child's game of War.
 * 
 * <p>Each player is dealt a hand containing half the deck.</p>
 * 
 * <p>Players lay down one card each, with the higher of the two cards winning the trick of two cards, which the winner places in a personal stack of winnings.</p>
 * 
 * <p>When the cards played are tied, the players lay three additional cards face down on top of the card, followed by a fourth face up 
 * (and while stating "I declare war!", emphasizing each syllable while placing down each card). The player with higher of the two final 
 * cards played wins all the cards in play. In the case of additional ties, the players repeat the process until a winner is determined.
 * In the case that one or both players run out of cards during a tie-break, the last card played by a player is used. If both players 
 * run out of cards during a tie-break and there is still a tie, no cards are awarded and all are removed from play.</p>
 * 
 * <p>When a player runs out of cards in their hand, they can replace their hand with the tricks they have won thus far.</p>
 * 
 * <p>Play ends when one player runs out of cards in both their hand and winnings or when the players decide to end the game out of boredom. 
 * In this case, the player with the most cards wins.</p>
 * 
 * @author jhenry
 */
public class War {
	
	private PokerDeck deck;
	
	private class Player {
		Stack<PlayingCard> hand = new Stack<>();
		ArrayList<PlayingCard> tricks = new ArrayList<>();
		String possessive;
		
		Player(String possessive) {
			this.possessive = possessive;
		}
		
		PlayingCard getCard() {
			PlayingCard card;
			if (hand.isEmpty()) {
				card = null;
			} else {
				card = hand.pop();
				trick.push(card);
			}
			if (hand.isEmpty()) {
				if (!tricks.isEmpty()) {
					printf("Adding %d wins back into %s hand.%n", tricks.size(), possessive);
					hand.addAll(tricks);
					tricks.clear();
				}
			}
			return card;
		}
	}
	
	private Player user = new Player("your");
	private Player computer = new Player("my");
	Stack<PlayingCard> trick = new Stack<>();
	
	private int round;
	private int maxRounds;
	private int delay;
	private Scanner scanner = new Scanner(System.in);
	
	private War(int maxRounds, int delay) {
		round = 1;
		this.maxRounds = maxRounds;
		this.delay = delay;
		deck = new PokerDeck() {
			@Override
			protected PlayingCard newPlayingCard(Rank rank, Suit suit) {
				return super.newPlayingCard(rank, suit);
			}
		};
	}
	
	private static int getIntArg(String[] args, int index, int defaultValue) {
		int value = defaultValue;
		if (index < args.length) {
			try {
				value = Integer.parseInt(args[index]);
			} catch (NumberFormatException e) {
				// ignore exception
			}
		}
		System.out.println(value);
		return value;
	}

	public static void main(String[] args) {
		War game = new War(getIntArg(args, 0, 2), getIntArg(args, 1, 250));
		game.start();
	}
	
	private void printf(String s, Object ... args) {
		System.out.printf(s, args);
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// ignore
		}
	}
	
	private void start() {
		printf("Let's play War!%n");
		printf("shuffling...%n");
		deck.shuffle();
		deal();
		while (round <= maxRounds) {
			PlayingCard userCard = getPlayerCard();
			if (userCard == null) {
				break;
			}
			PlayingCard computerCard = computer.getCard();
			if (computerCard == null) {
				break;
			}
			play(userCard, computerCard);
		}
		int userWinCount = user.tricks.size() + user.hand.size();
		int computerWinCount = computer.tricks.size() + computer.hand.size();
		printf("You have %d cards and I have %d cards.%n", userWinCount, computerWinCount);
		if (userWinCount > computerWinCount) {
			printf("You win!%n");
		} else if (userWinCount == computerWinCount) {
			printf("We tied!%n");
		} else {
			printf("I won!%n");
		}
	}
	
	private void deal() {
		printf("dealing...%n");
		Player toPlayer = user;
		PlayingCard card = deck.dealOneCard();
		while (card != null) {
			toPlayer.hand.push(card);
			card = deck.dealOneCard();
			toPlayer = toPlayer == user ? computer : user;
		}
	}
	
	private PlayingCard getPlayerCard() {
		System.out.print("Play a card! (press <Enter>)");
		scanner.nextLine();
		if (user.hand.size() == 1) {
			round++;
		}
		return user.getCard();
	}
	
	private void play(PlayingCard userCard, PlayingCard computerCard) {
		int comparison = 0;
		System.out.print("Your card: ");
		System.out.print(userCard.toString());
		printf("%n");
		System.out.print("My card: ");
		System.out.print(computerCard.toString());
		printf("%n");
		comparison = userCard.compareTo(computerCard);
		while (comparison == 0) {
			printf("It's a tie!%n");
			PlayingCard lastUserCard = userCard;
			PlayingCard lastComputerCard = computerCard;
			String[] ideclarewar = {"***** I *****", "***** DE *****", "***** CLARE *****", "***** WAR *****"};
			for (int i = 0; i < ideclarewar.length; i++) {
				if (!user.hand.isEmpty()) {
					PlayingCard newUserCard = getPlayerCard();
					if (newUserCard != null) {
						lastUserCard = newUserCard;
						printf("You add a card...%n");
					}
				}
				if (!computer.hand.isEmpty()) {
					PlayingCard newComputerCard = computer.getCard();
					if (newComputerCard != null) {
						lastComputerCard = newComputerCard;
						printf("I add a card...%n");
					}
				}
				System.out.println(ideclarewar[i]);
			}
			comparison = lastUserCard.compareTo(lastComputerCard);
			printf("Your last card: %s%n", lastUserCard.toString());
			printf("My last card: %s%n", lastComputerCard.toString());
			if (user.hand.isEmpty() || computer.hand.isEmpty()) {
				break;
			}
		}
		if (comparison == 0) {
			printf("Nobody wins this hand and the cards are removed from play.%n");
		} else if (comparison > 0) {
			printf("You win %d cards!%n", trick.size());
			user.tricks.addAll(trick);
		} else {
			printf("I win %d cards!%n", trick.size());
			computer.tricks.addAll(trick);
		}
		trick.clear();
	}
}
