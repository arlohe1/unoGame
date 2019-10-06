README.md

----- Instructions -----

In UNO, the aim of the game is to be the first player to finish all of the cards sin his/her hand. Each player begins with a hand of five cards.

Cards vary in number (between 0 and 9) and color (red, green, blue, and yellow). There are also five different types of special cards: reverse, skip, draw 2, wild, and draw 4. There are 108 cards in an UNO deck, which consists of four colors, where each color has 23 cards: one 0 card, two cards each for numbers 1 through 9, and two cards each for skip, reverse, and draw 2. Finally, there are four wild cards and four draw 4 cards. When the UNO deck has run out of cards, the cards that have already been played are reshuffled and added back to the deck to be used again. 

A player can only play a card if their card matches either the number or type of the current card in play. However, wild cards and draw 4 cards can be played at any time. 

Reverse cards flip the direction of play. Skip cards will cause the next person's turn to be skipped. Draw 2 and Draw 4 cards will penalize the next person with either two or four cards drawn from the deck, respectively, and cause that person to be skipped. Finally, wild cards and skip cards both give the player the option to change the color in play. Note: when only two players are playing, reverse cards act as skip cards.

When a player is playing their second to last card, they must also say (or in this case, type) the word "UNO" while playing their card (or in in this case, typing the choice of card they would like to play). A failure to say "UNO" will result in an automatic two card penalty on that player. Furthermore, saying "UNO" at an incorrect moment (when the player is not playing their second to last card) will also result in a two card penalty. 

The game ends when one person has played all of their cards. 

----- Design -----

In this game, I have 4 classes: Card, Player, Deck, and Game.

Card contains three fields:
	1) myNum - represents the number on the card
	2) myColor - represents the color of the card
	3) myType - represents the type of the card (represented as an enum)
Cards also have their own unique toString() function, which enables the Card to be represented in a visually-appealing format.

Players have two fields:
	1) myHand - an ArrayListof Card objects representing the Player's current hand
	2) name - the name of the player
I chose to represent the hand of the player as an ArrayList because the number of Cards in a Player's hand changes as the game goes on. A Player can have anywhere from 0 to 108 (the total number of cards in the game) cards, technically. An ArrayList allows for easy lookup, removal, and insertion.
Players have the abilities to try to play a Card, which returns a specific Card without removing it from the player's hand so that that Players can try to play a Card, before it is deemed valid/invalid. They also have the ability to remove Cards from their hand (play a Card) and add a Card (draw a Card from the deck). 
By far one of the trickiest parts of this program was the toString() function for Player. It cycles through the Player's hand for each line of the Cards, as every part of every Card in a line must be printed before the function can move on to the next line.

Deck has two fields:
	1)myDeck - an ArrayList of Card objects representing the Cards in the deck
	2)myRandom - a Random object used to generate random numbers
Deck has a maximum size of 108 cards. Rather than have a constant probability of drawing different types of Cards as the game is played, I chose to implement the Deck like a deck of cards in the real world, as it would make the game more realistic. By this, I mean that when a Card is drawn, the probability of drawingn the remaining Card increases, and the card that was drawn cannot be drawn again. Also, once the Deck is depleted, all of the used/discard cards are reshuffled and added back into the Deck.

Finally, the last and most important class is Game. This is where all of the logic of the game is implemented. It has several fields, the most important ones being a Deck (of Card objects) and an Array (whose size is inputted by the user) of Player objects. As the game is played, the Deck is slowly depleted of Cards (after which the Cards are recylced, as discussed earlier), and we cycle through the Players in the Array by incrementing our current position and taking into account whether or not any penalties/special cards were given. I chose to use an Array to represent the Players because 1) the number of Players in the game cannot change and 2) an Array is of fixed-size and therefore allows for efficient lookups when accessing the properties of a specific player.

