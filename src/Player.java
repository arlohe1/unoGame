import java.util.ArrayList;
import java.util.List;

public class Player {
	
	//Each player has a name, and an ArrayList of Card objects
	List<Card> myHand;
	String name;
	
	public Player(String n) {
		 myHand = new ArrayList<Card>();
		 name = n;
	}
	
	public String getName() {
		return name;
	}
	
	//Returns a specific Card without removing it from the player's hand
	//Used so that Players can try to play a Card, before it is deemed valid/invalid
	public Card tryCard(int i) {
		return myHand.get(i);
	}
	
	/*
	*	- Removes a Card from the player's hand and returns it
	*	- Iterates through the hand to remove the card
	*	- Essentially a constant time operation as a hand as a max size of 108 (the total number of cards in play)
	*/
	public Card removeCard(Card c) {
		for(int i=0; i<myHand.size(); i++) {
			if(myHand.get(i).equals(c)) {
				return myHand.remove(i);
			}
		}
		return null;
	}
	
	//Adds a Card to the player's hand (in case they are penalized, etc)
	public void addCard(Card c) {
		myHand.add(c);
	}
	
	//Returns the number of cards remaining in the player's hand
	public int getNumCards() {
		return myHand.size();
	}
	
	/*
	*	- Prints out all of the cards in the player's hand
	*	- Quite complicated since I had to read each Card in the List once per line
	*	- (You can't just print each Card one at a time, as then they won't all show up in line)
	*/
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		for(int x=0; x<myHand.size(); x++) {
			String concat = "     ";
			if(x == myHand.size()-1) {
				concat = "\n";
			}
			sb.append("**********"+concat);
		}
		for(int i=0; i<2; i++) {
			for(int x=0; x<myHand.size(); x++) {
				String concat = "     ";
				if(x == myHand.size()-1) {
					concat = "\n";
				}
				sb.append("*        *"+concat);
			}
		}
		for(int x=0; x<myHand.size(); x++) {
			String concat = "     ";
			if(x == myHand.size()-1) {
				concat = "\n";
			}
			sb.append(whiteSpace(0, myHand.get(x))+concat);
		}
		for(int x=0; x<myHand.size(); x++) {
			String concat = "     ";
			if(x == myHand.size()-1) {
				concat = "\n";
			}
			sb.append(whiteSpace(1, myHand.get(x))+concat);
		}
		for(int i=0; i<2; i++) {
			for(int x=0; x<myHand.size(); x++) {
				String concat = "     ";
				if(x == myHand.size()-1) {
					concat = "\n";
				}
				sb.append("*        *"+concat);
			}
		}
		for(int x=0; x<myHand.size(); x++) {
			String concat = "     ";
			if(x == myHand.size()-1) {
				concat = "\n";
			}
			sb.append("**********"+concat);
		}
		return sb.toString();
	}
	
	/*
	*	- Similar to whiteSpace() in Card, but with a few tweaks necessary for printing the Cards in line
	*	- Function that automatically centers text on the card
	*	- Used to center the number/type and the color of the card
	*/
	private String whiteSpace(int x, Card c) {
		
		StringBuilder temp = new StringBuilder();
		String type = "";
		if(x==0) {
			if(c.getType() == Card.CardType.NUMBER) {
				type = ""+c.getNumber();
			} else {
				type = ""+c.getType();
			}
		} else {
			type=c.getColor();
		}
		temp.append("*");
		int firstSpace = (9-type.length())/2;
		for(int j=0; j<firstSpace; j++) {
			temp.append(" ");
		}
		temp.append(type);
		for(int j=0; j<(9-firstSpace-type.length()-1); j++) {
			temp.append(" ");
		}
		temp.append("*");
		
		return temp.toString();
	}
	
}