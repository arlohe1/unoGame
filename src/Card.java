
public class Card {
	
	private int myNum;
	private String myColor;
	private CardType myType;
	
	public Card(int num, String col, CardType type) {
		myNum = num;
		myColor = col;
		myType = type;
	}
	
	//Returns the number on the card (0 through 9)
	//Returns -1 if not a NUMBER card
	public int getNumber() {
		return myNum;
	}
	
	//Returns color of the card
	//Empty string ("") for cards with no color (for wild/draw 4 cards)
	public String getColor() {
		return myColor;
	}
	
	//Returns type of Card as an enum
	public CardType getType() {
		return myType;
	}
	
	static enum CardType {
		NUMBER, REVERSE, SKIP, WILD, DRAW2, DRAW4;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String type = "";
		if(this.myType == CardType.NUMBER) {
			type = ""+myNum;
		} else {
			type = ""+myType;
		}
		sb.append("**********\n");
		for(int i=0; i<6; i++) {
			if(i!=2 && i!=3) {
				sb.append("*         *\n");
			} else if(i == 2) {
				whiteSpace(sb, type);
			} else if(i==3) {
				whiteSpace(sb, myColor);
			}
		}
		sb.append("**********\n");
		return sb.toString();
	}
	
	//Function that automatically centers text on the card
	//Used to center the number/type and the color of the card
	public void whiteSpace(StringBuilder s, String c) {
		s.append("*");
		int firstSpace = (9-c.length())/2;
		for(int j=0; j<firstSpace; j++) {
			s.append(" ");
		}
		s.append(c);
		for(int j=0; j<(9-firstSpace-c.length()); j++) {
			s.append(" ");
		}
		s.append("*\n");
	}
}