import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {

	private List<Card> myDeck;
	private Random myRandom;
	
	public Deck() {
		myDeck = new ArrayList<Card>();
		myRandom = new Random();
	}
	
	//A constructor that takes in a list of Cards and creates a new Deck
	//Used when the discardPile needs to be "recycled" in Game.java
	public Deck(List<Card> myList) {
		myDeck = new ArrayList<Card>(myList);
		myRandom = new Random();
		shuffleDeck();
	}

	/*
	*	- Uno decks have 108 cards in them, 100 of them having a color
	*	- 1 zero, and 2 cards each for numbers 1-9 for each color
	*	- 2 cards each for skip, reverse, and draw2 for each color
	*	- 8 cards in the deck are wild cards/draw4 cards
	*/
	public void createDeck() {
		String[] colors = {"red", "blue", "green", "yellow", ""};
		for(int i=0; i<4; i++) {
			myDeck.add(new Card(0, colors[i], Card.CardType.NUMBER));
			for(int j=2; j<20; j++) {
				myDeck.add(new Card(j/2, colors[i], Card.CardType.NUMBER));
			}
			for(int k=0; k<2; k++) {
				myDeck.add(new Card(-1, colors[i], Card.CardType.REVERSE));
				myDeck.add(new Card(-1, colors[i], Card.CardType.SKIP));
				myDeck.add(new Card(-1, colors[i], Card.CardType.DRAW2));
			}
		}
		for(int i=0; i<4; i++) {
			myDeck.add(new Card(-1, colors[4], Card.CardType.DRAW4));
			myDeck.add(new Card(-1, colors[4], Card.CardType.WILD));
		}	
	}
	
	//Shuffles/randomizes the deck
	public void shuffleDeck() {
		Collections.shuffle(myDeck);
	}
	
	//Returns the number of cards left in the deck
	public int size() {
		return myDeck.size();
	}
	
	//Selects a random card from the deck
	public Card drawCard() {
		int index = myRandom.nextInt(myDeck.size());
		return myDeck.remove(index);
	}
	
	
}