import java.util.ArrayList;
import java.util.Collections;

public class ObjectOrientedDesign {

	/**
 * Question 7.1
 * The DeckofCards class implements a deck of cards used for blackjack.
 */
	public static class DeckOfCards {
		ArrayList<Card> deck;

		enum Face {TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE,
			TEN, JACK, QUEEN, KING, ACE};
		enum Suit {CLUB, SPADE, HEART, DIAMOND};

		class Card {
			private Suit suit;
			private Face face;
			private ArrayList<Integer> rank;	// Aces can have a value of 1 or 11.

			Card(Suit suit, Face face, ArrayList<Integer> rank) {
				this.suit = suit;
				this.face = face;
				this.rank = rank;
			}
		}	

		public DeckOfCards() {
			deck = new ArrayList<Card>();

			for(Suit suit: Suit.values()) {
				for(Face face: Face.values()) {
					ArrayList<Integer> rank = new ArrayList<Integer>();
					switch (face) {
						case TWO: rank.add(2); break;
						case THREE: rank.add(3); break;
						case FOUR: rank.add(4); break;
						case FIVE: rank.add(5); break;
						case SIX: rank.add(6); break;
						case SEVEN: rank.add(7); break;
						case EIGHT: rank.add(8); break;
						case NINE: rank.add(9); break;
						case TEN: rank.add(10); break;
						case JACK: rank.add(10); break;
						case QUEEN: rank.add(10); break;
						case KING: rank.add(10); break;
						case ACE: rank.add(1); rank.add(11); break;
						default: System.out.println("Invalid month!");
					}
					Card card = new Card(suit, face, rank);
					deck.add(card);
				}
			}
			Collections.shuffle(deck);
		}

		public int[] getCard() {
			if (deck.isEmpty() == true) return null;
			Card card = deck.remove(0);
			return new int[] {card.face.ordinal(), card.suit.ordinal()};
		}
	}

	public static void main(String[] args) {	
		DeckOfCards deck = new DeckOfCards();
		int[] card = deck.getCard();
		while(card != null) {
			System.out.println("getCard() = " + card[0] + "/" + card[1]);
			card = deck.getCard();
		}
	}		
}