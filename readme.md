# The Game of Set
*This project is the culmination of my CS 110 Intermediate Programming course at UVM*

### Intro
The project was completed in three phases, roughly corresponding with the "model-view-controller" (MVC) pattern. 
Phase I consists of the underlying model on which the game is based. I was given UML diagrams for the Card, Board, 
BoardSquare, and Deck classes. Phase II built off of these classes and provided me a GameText class, which acts as a
text-based "view" of the MVC. I was tasked with building the corresponding "controller", the Game class, to make
GameText work correctly. Phase III was the final part of the project, in which I was to "decouple" the text-based view
from the controller and create a GUI using JavaFX. Once this was finished, I also completed some of the extra credit 
options and added features such as the "find-a-set" button and the "new game" button.

### Project Structure
Phase I:
- [`Card.java`](Card.java) is the model for a card in the game of Set.
- [`Deck.java`](Deck.java) is the model for the deck of cards in the game, supporting methods such as `shuffle()` and `getTopCard()`.
- [`Board.java`](Board.java) is the model for the board of cards in the game.
- [`BoardSquare.java`](BoardSquare.java) is the model for one slot on the board, essentially acting as a wrapper over one Card.

Phase II:
- [`Game.java`](Game.java) is the controller for the game's logic.
- [`GameText.java`](GameText.java) is the text-based view of the game of Set.

Phase III:
- [`GameGUI.java`](GameGUI.java) is the JavaFX GUI for the game of Set.
- [`CardPane.java`](CardPane.java) is an extra class I made to refactor out the logic needed to create and update
  the representation of a Card in the GUI into its own self-contained class.

Misc:
- [`SetDriver.java`](SetDriver.java) is the testing file used to make sure that logic was working correctly.
- [`Set.jar`](Set.jar) is an executable that will run the JavaFX GUI without needing to compile the source code first.
