import java.util.ArrayList;

// TODO: [EC][DONE] make auto-set-finder algorithm
// TODO: [EC]       compress the board when a set is found and no more cards can be added
// TODO: [EC]       determine if game is *actually* over (i.e. there are no more sets to be found)

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

    public int cardsRemaining() {
        return deck.cardsRemaining();
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
     * @return the number of cards on the board.
     */
    public int numCardsOnBoard() {
        int num = 0;
        for (int r = 0; r < board.numRows(); r++)
            for (int c = 0; c < board.numCols(); c++)
                num++;

        return num;
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
            bs.setCurrentlySelected(false);
        }
        if (set) {
            if (!deck.isEmpty() && this.numCardsOnBoard() == 12) {
                for (BoardSquare bs : selectedCards) {
                    Card newCard = deck.getTopCard();
                    bs.setCard(newCard);
                }
            }
            else if (!deck.isEmpty() && this.numCardsOnBoard() < 12){
                board.compressBoard(selectedCards);
                this.add3();
            }
            else {
                board.compressBoard(selectedCards);
            }
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
     * Finds the first available set on the board.
     * @return an Array of three Cards that form a set.
     */
    public BoardSquare[] findSet() {
        ArrayList<BoardSquare> allCards = new ArrayList<>(12);

        // flatten BoardSquares into one-dimensional array
        for (int row = 0; row < board.numRows(); row++)
            for (int col = 0; col < board.numCols(); col++)
                allCards.add(board.getBoardSquare(row, col));

        // loop through every single unique card
        for (int i1 = 0; i1 < allCards.size(); i1++) {
            for (int i2 = 1; i2 < allCards.size(); i2++) {
                for (int i3 = 2; i3 < allCards.size(); i3++) {
                    if (i1 != i2 && i2 != i3 && i1 != i3) {
                        BoardSquare bs1 = allCards.get(i1);
                        BoardSquare bs2 = allCards.get(i2);
                        BoardSquare bs3 = allCards.get(i3);

                        boolean set = Card.isSet(
                                bs1.getCard(),
                                bs2.getCard(),
                                bs3.getCard()
                        );

                        if (set) {
                            return new BoardSquare[] {bs1, bs2, bs3};
                        }
                    }
                }
            }
        }

        return new BoardSquare[] {};
    }

    /**
     * @return the Game's Board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return a string representation of this Game.
     */
    @Override
    public String toString() {
        return board.toString();
    }
}
