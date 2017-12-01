import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<BoardSquare>> board;

    /**
     * Constructor.
     * @param deck the Deck of Cards with which to populate this Board
     */
    public Board(Deck deck) {
        board = new ArrayList<>(3);
        for (int row = 0; row < 3; row++) {
            ArrayList<BoardSquare> thisRow = new ArrayList<>(4);
            for (int col = 0; col < 4; col++) {
                BoardSquare tile = new BoardSquare(deck.getTopCard(), row, col);
                thisRow.add(tile);
            }
            board.add(thisRow);
        }
    }

    public void compressBoard(ArrayList<BoardSquare> cardsToRemove) {
        ArrayList<BoardSquare> tempArray = new ArrayList<>();
        for (ArrayList<BoardSquare> row : board)
            tempArray.addAll(row);
        board.clear();

        tempArray.removeAll(cardsToRemove);

        int numCols = tempArray.size() / 3;
        for (int row = 0; row < 3; row++) {
            ArrayList<BoardSquare> thisRow = new ArrayList<>(1);
            for (int col = 0; col < numCols; col++) {
                BoardSquare tile = tempArray.remove(0);

                // update BoardSquare with new row and column positions
                tile.setRowPos(row);
                tile.setColPos(col);
                thisRow.add(tile);
            }
            board.add(thisRow);
        }
    }

    /**
     * Gets the specified BoardSquare.
     * @param row the row position at which to locate the BoardSquare
     * @param col the column position at which to locate the BoardSquare
     * @return the specified BoardSquare
     */
    public BoardSquare getBoardSquare(int row, int col) {
        return board.get(row).get(col);
    }

    /**
     * Gets the Card in the specified BoardSquare.
     * @param row the row position at which to locate the Card
     * @param col the column position at which to locate the Card
     * @return the specified Card
     */
    public Card getCard(int row, int col) {
        return getBoardSquare(row, col).getCard();
    }

    /**
     * Replace the Card at the specified location with a new Card
     * @param card the new Card
     * @param row the row position at which to insert the new Card
     * @param col the column position at which to insert the new Card
     */
    public void replaceCard(Card card, int row, int col) {
        getBoardSquare(row, col).setCard(card);
    }

    /**
     * Adds three more Cards to the Board (one in each row)
     * @param deck the Deck to draw the Cards from
     */
    public void add3(Deck deck) {
        for (ArrayList<BoardSquare> row : board) {
            int currentRow = row.get(0).getRowPos();
            int currentCol = row.size();
            BoardSquare tile = new BoardSquare(deck.getTopCard(), currentRow, currentCol);
            row.add(tile);
        }
    }

    /**
     * @return the number of rows in the Board
     */
    public int numRows() {
        return board.size();
    }

    /**
     * @return the number of columns in the Board
     */
    public int numCols() {
        return board.get(0).size();
    }

    /**
     * @return a String representation of this Board
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (ArrayList<BoardSquare> row : board) {
            sb.append("┃┃   ");
            for (BoardSquare tile : row) {
                sb.append(tile.getCard());
                sb.append("   ┃┃   ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
