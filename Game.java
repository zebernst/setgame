import java.util.ArrayList;

public class Game {
    // instance variables
    private Deck deck;
    private Board board;
    private ArrayList<BoardSquare> selectedCards;

    /**
     * Constructor
     */
    public Game() {
        this.deck = new Deck();
        deck.shuffle();
        this.board = new Board(deck);
        this.selectedCards = new ArrayList<>(3);
    }

    /**
     * @return whether or not the game is out of cards.
     */
    public boolean outOfCards() {
        return deck.isEmpty();
    }

    /**
     * call the Board's add3 method using the Game's Deck.
     */
    public void add3() {
        board.add3(deck);
    }

    /**
     * Adds a Card object to the ArrayList of selected Cards.
     * @param row the row that the Card is located in.
     * @param col the column that the Card is located in.
     */
    public void addToSelected(int row, int col) {
        BoardSquare bs = board.getBoardSquare(row, col);
        bs.setCurrentlySelected(true);
        selectedCards.add(bs);
    }

    /**
     * Removes the specified BoardSquare from the selectedCards array.
     * @param row the row of the BoardSquare to remove.
     * @param col the col of the BoardSquare to remove.
     */
    public void removeSelected(int row, int col) {
        BoardSquare bs = board.getBoardSquare(row, col);
        selectedCards.remove(bs);
        bs.setCurrentlySelected(false);
    }

    /**
     * @return the size of the selectedCards ArrayList.
     */
    public int numSelected() {
        return selectedCards.size();
    }


    /**
     * Test whether or not the cards in selectedCards are a set, and adjust the board accordingly.
     *
     * This includes setting all BoardSquares to have currentlySelected = false, and then clearing the
     * selectedCards array.
     * @return whether or not the three cards in selectedCards are a set.
     */
    public boolean testSelected() {
        boolean set = Card.isSet(
                selectedCards.get(0).getCard(),
                selectedCards.get(1).getCard(),
                selectedCards.get(2).getCard()
        );

        // set currentlySelected to false and replace with new Cards (if applicable)
        for (BoardSquare bs : selectedCards) {
            if (set) {
                Card newCard = deck.getTopCard();
                bs.setCard(newCard);
            }
            bs.setCurrentlySelected(false);
        }
        // clear ArrayList
        selectedCards.clear();

        // return bool
        return set;
    }

    /**
     * @return the array of currently selected BoardSquares.
     */
    public ArrayList<BoardSquare> getSelected() {
        return selectedCards;
    }

    /**
     * @return a string representation of this Game.
     */
    @Override
    public String toString() {
        return board.toString();
    }
}
