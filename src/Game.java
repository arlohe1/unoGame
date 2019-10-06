import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
	
		private Scanner scanner1 ;
		private Deck myDeck;
		private List<Card> discardPile ;
		private Card currentCard;
		private Player[] allPlayers;
		private Player currPlayer;
		private int currIndex;
		private Player nextPlayer;
		private String currentColor;
		private String uno;
		int numPlayers;
		
		public Game() {
			scanner1 = new Scanner(System.in);
			myDeck = new Deck();
			discardPile = new ArrayList<Card>();
			myDeck.createDeck();
			currentCard = null;
			currPlayer = null;
			nextPlayer = null;
			currentColor = null;
			uno = null;
			allPlayers = null;
		}
		
		//Sets up the game by initializing the players, asking for the player's names, and playing an initial card from the deck
		public void setup() {
			System.out.println("====================================================================================================\n");
			System.out.println("Hello, welcome to UNO. How many people are playing this game?");
			String number = scanner1.nextLine();
			boolean validInput = false;
			while(!validInput) {
				if(!number.matches("[0-9]+")) {
					System.out.println("You entered an invalid input. Please try again.");
					number = scanner1.nextLine().toLowerCase().trim();
				} else {
					validInput = true;
				}
			}
			numPlayers = Integer.parseInt(number);
			allPlayers = new Player[numPlayers];
			for(int i=1; i<=numPlayers; i++) {
				System.out.println("What is Player "+i+"'s name?");
				String name = scanner1.nextLine();	
				allPlayers[i-1] = new Player(name);
			}
			//variables used to track the player whose turn it is currently
			currPlayer = allPlayers[0];
			currIndex = 0;
			nextPlayer = allPlayers[1];
			
			//gives each player 5 cards to start the game off
			for(int i=0; i<numPlayers; i++) {
				for(int j=0; j<5; j++) {
					allPlayers[i].addCard(drawCard());
				}
			}

			//draws a card from the deck and sets that as the current card to begin
			//ensures that the game will begin on a fair, non-penalizing card by continuing to draw until a number card is reached
			currentCard = drawCard();
			while(currentCard.getType() != Card.CardType.NUMBER) {
				currentCard = drawCard();
			}
			
			//sets the current color of the game to the current card's color
			//a separate color variable was necessary as it is possible to change the valid color of the game with a draw/draw4 cards (which have no color)
			currentColor = currentCard.getColor();
		}
		
		
		/*
		*	- A function that handles the input of the turn
		*	- Requires a valid input by repeatedly asking until a valid input is given
		*	- Functionality for "saying" UNO when you play your second to last card is also added here
		*/
		public Card playersTurn() {
			if(currPlayer.getNumCards() > 1) {
				System.out.println("What card would you like to play? "
						+ "Please enter a number from 1 to "+currPlayer.getNumCards()+" or \"DRAW\" if you would like to draw a card from the deck.");
			} else {
				System.out.println("You have only one card left. Please type \"1\" to play it or or \"DRAW\" if you would like to draw a card from the deck.");
			}
			String[] input = scanner1.nextLine().toLowerCase().trim().split(" ");
			String choice = input[0];
			if(choice.matches("[0-9]+")) {
				while(Integer.parseInt(choice) > currPlayer.getNumCards() || Integer.parseInt(choice) < 1) {
					System.out.println("You selected an invalid choice. Please try again.");
					input = scanner1.nextLine().toLowerCase().trim().split(" ");
					choice = input[0];
				}
			} 
			if(input.length > 1) {
				uno = input[1];
			}
			//player cannot play any of their cards and is choosing to draw a card from the deck
			if(choice.equals("draw")) {
				Card draw = drawCard();
				System.out.println("You drew the following card from the deck: ");
				System.out.println(draw);
				//if the card drawn from the deck can be played, the player can choose to play it immediately
				System.out.println("Would you like to play it? Enter \"YES\" or \"NO\"");
				input = scanner1.nextLine().toLowerCase().trim().split(" ");
				choice = input[0];
				//using an array to determine if "UNO" was said by the player as they played their card
				//if "UNO" was said, that input is stored to be used later
				if(input.length > 1) {
					uno = input[1];
				}
				boolean validInput = false;
				while(!validInput) {
					//player choose to play the card they drew from the deck (if it is valid)
					if(choice.equals("yes")) {
						if(validPlay(draw)) {
							return draw;
						}
					} else if(choice.equals("no")){
						//player cannot play the card they drew from the deck and is choosing to keep it
						//no card is played and the currentCard remains the same
						System.out.println("You now have "+(currPlayer.getNumCards()+1)+" cards.");
						currPlayer.addCard(draw);
						return null;
					}
					System.out.println("You selected an invalid choice. Please enter \"YES\" or \"NO\"");
					choice = scanner1.nextLine().toLowerCase().trim();	
				}
			} else {
				//a player tries to play a card they chose from their hand
				while(!choice.matches("[0-9]+")) {
						System.out.println("You entered an invalid input. Please try again.");
						input = scanner1.nextLine().toLowerCase().trim().split(" ");
						choice = input[0];
				}
				int cardNum =  Integer.parseInt(choice);
				Card temp = currPlayer.tryCard(cardNum - 1);
				return temp;
			}
			return null;
		}
		
		/*
		*	- Checks to see if the card that is being played is valid with respect to the currentCard
		*	- A card must match by either color, number, or type
		*	- Draw4 and wild cards can be played no matter what the currentCard is 
		*/
		public boolean validPlay(Card curr) {
			if(curr == null) {
				return true;
			}
			if(currentCard == null) {
				return true;
			}
			Card.CardType currType = curr.getType();
			if(currType == Card.CardType.DRAW4 || currType == Card.CardType.WILD) {
				return true;
			} else if(curr.getColor().equals(currentColor)) {
				return true;
			} else if((currType != Card.CardType.NUMBER) && (currType == currentCard.getType())) {
				return true;
			} else if(curr.getNumber() == currentCard.getNumber()) {
				return true;
			} else {
				return false;
			}
		}
		
		/*
		*	- Draws a card from the deck
		*	- If the deck is empty, all of the cards in the discard pile are transferred back into the
		*		deck (by initializing a new deck). This allows the deck to decrease in size as the game
		*		progresses, thereby increasing the probability of drawing cards that have not yet been drawn, 
		*		preventing the duplication of cards, and more accurately modeling the way a real card game would be played
		*/
		public Card drawCard() {
			if(myDeck.size() == 0) {
				myDeck = new Deck(discardPile);
				discardPile.clear();
			}
			return myDeck.drawCard();
		}
		
		//Penalizes the specified player the specified amount of cards
		public void penalize(int x, Player p) {
			if(x == 2) {
				p.addCard(drawCard());
				p.addCard(drawCard());
			} else if(x == 4) {
				for(int i=0; i<4; i++) {
					p.addCard(drawCard());
				}
			}
		}
		
		//Prompts the user to change the current color of the game if they play a draw4 or wild card
		public void changeColor(Card curr) {
			System.out.println("You played a "+curr.getType()+" card.");	
			System.out.println("Please pick a color: \"RED\", \"GREEN\", \"YELLOW\", or \"BLUE\"");
			String colChoice = scanner1.nextLine().toLowerCase().trim();
			boolean valid = false;
			while(!valid) {
				if(!(colChoice.equals("red") || colChoice.equals("green") || colChoice.equals("yellow") || colChoice.equals("blue"))) {
					System.out.println("You selected an invalid color. Please try again!");
					colChoice = scanner1.nextLine().toLowerCase().trim();
				} else {
					valid = true;
				}
			}
			currentColor = colChoice;
			System.out.println("You picked the color: "+currentColor);
		}
		
		//Checks to see if a player has one yet by seeing if they've run out of cards
		public boolean hasWinner() {
			int temp = currIndex - 1;
			if(currIndex == 0) {
				temp = allPlayers.length-1;
			}
			if(allPlayers[temp].getNumCards() == 0) {
				System.out.println("Congratulations, "+allPlayers[temp].getName()+"! You won!");
				return true;
			}
			return false;
		}
		
		//Shows a screen in between turns to prevent players from seeing each others cards
		//Also provides some information about the number of cards remaining for each player
		public void passComputer() {
			System.out.println("====================================================================================================");
			System.out.println("====================================================================================================\n\n\n\n\n\n\n\n\n");
			for(int i=0; i<numPlayers; i++) {
				Player temp = allPlayers[i];
				System.out.println(temp.getName()+" has "+temp.getNumCards()+" cards left.");
			}
			System.out.println("\n\nPlease pass the computer to "+currPlayer.getName());
			System.out.println("Press ENTER to continue");
			System.out.println("\n\n\n\n\n\n\n");
			String input = scanner1.nextLine();
			while(!input.equals("")) {
				input = scanner1.nextLine();
			}
		}
		
		//Switches the current player in preparation for the next turn
		//Takes into account changes due to penalties, reverse, draw2/draw4, etc.
		public void switchPlayer(int indexChange) {
			//the player should change unless there exists only two players and a non-number/non-wild card was played
			if(indexChange == 1 || allPlayers.length != 2) {
				currIndex = currIndex + indexChange;
				//if the new index goes out of bounds, adjust the index
				if(currIndex >= allPlayers.length) {
					currIndex = currIndex - allPlayers.length;
				} else if(currIndex <0) {
					currIndex = allPlayers.length-indexChange;
				}
				currPlayer = allPlayers[currIndex];
				if(currIndex == allPlayers.length-1) {
					nextPlayer = allPlayers[0];
				} else {
					nextPlayer = allPlayers[currIndex+1];
				}
			}
		}
		
		/*
		- Contains most of the logic for the game. occurs once a player's has given a card they would like to play
		- The card is checked to see if it can be played (given the current card)
		- Depending on the card, the appropriate penalties and actions are carried out
		- UNO penalties are also given out here depending on whether or not the player said it while playing and 
			whether or not they said it at the correct time
		*/
		public void playGame() {
			boolean turn = true;
			System.out.println("\n====================================================================================================");
			System.out.println("====================================================================================================\n");
			//displays the user's current hand, currentCard in play, and the currentColor
			System.out.println(currPlayer.getName()+", it is your turn. You have "+currPlayer.getNumCards()+" cards left. Your current hand is: ");
			System.out.println(currPlayer);
			System.out.println("The current card is:");
			System.out.println(currentCard);
			System.out.println("The current color is: "+currentColor.toUpperCase());
			while(turn) {
				Card c = playersTurn();
				//checks to see if the card the user tried to play is valid
				//if the card is invalid, the user is told to play again (see else below)
				boolean isValid = validPlay(c);
				if(isValid) {
					//players will automatically be switched at the end of the turn, unless a penalty card is played
					int indexChange = 1;
					if(c != null) {
						currentCard = c;
						currentColor = c.getColor();
						//removes the valid card from the currentPlayer's hand and adds it to the discard pile
						discardPile.add(currPlayer.removeCard(currentCard));
						if(currentCard.getType() == Card.CardType.DRAW2) {
							penalize(2, nextPlayer);
							indexChange = 2;
						} else if(currentCard.getType() == Card.CardType.DRAW4) {
							penalize(4, nextPlayer);
							indexChange = 2;
							changeColor(currentCard);
						} else if(currentCard.getType() == Card.CardType.SKIP) {
							indexChange = 2;
						} else if(currentCard.getType() == Card.CardType.REVERSE) {
							indexChange = -1;
						} else if(currentCard.getType() == Card.CardType.WILD) {
							changeColor(currentCard);
						}
					}
					turn = !turn;
					if(uno != null) {
						//player said UNO when they played their second to last card (correct)
						if(currPlayer.getNumCards() == 1) {
							System.out.println("You said UNO! You now have one card left!");
						} else {
							//player said UNO at an incorrect time (when they didn't have two cards left)
							//player is penalized with two additional cards
							System.out.println("You said UNO at an incorrect time. You have been penalized with two additional cards.");
							penalize(2, currPlayer);
						}
					uno = null;
					} else {
						//player forgot to say uno when playing their second to last card
						//player is penalized with two additional cards
						if(currPlayer.getNumCards() == 1) {
							System.out.println("You forgot to say UNO! You have been penalized with two additional cards.");
							penalize(2, currPlayer);
						}
					}
					
					//switches the players in preparation for the next turn
					switchPlayer(indexChange);
				} else {
					//player must play again if they tried to play an invalid card
					System.out.println("You tried to play an invalid card. Please try again!");
				}
			}			
		}
		
		//The actual game!!
		public static void main(String[] args) {
			Scanner scanner2 = new Scanner(System.in);
			Game g = new Game();
			//sets up the game
			g.setup();
			boolean end = false;
			//a while loop that allows multiple games to be played without having to re-run the entire program
			while(!end) {
				//makes the players play until the game has a winner
				while(!g.hasWinner()) {
					g.playGame();
					g.passComputer();
				}
				//once a winner has been found, the players are asked if they want to play again
				//appropriate actions are taken depending on the input (see below)
				System.out.println("Would you like to play again? Type \"YES\" to do so. Type any other key to end the game.");
				String choice = scanner2.nextLine();
				if(choice.toLowerCase().trim().equals("yes")) {
					end = false;
					g = new Game();
					g.setup();
				}
			} 
			scanner2.close();
			System.out.println("Thanks for playing! Good bye!");
		}

	
}