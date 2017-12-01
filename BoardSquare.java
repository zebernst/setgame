public class BoardSquare {
    private Card card;
    private int rowPos;
    private int colPos;
    private boolean currentlySelected;

    /**
     * Constructor.
     * @param card the Card occupying this BoardSquare
     * @param rowPos the row position of this BoardSquare
     * @param colPos the column position of this BoardSquare
     */
    public BoardSquare(Card card, int rowPos, int colPos) {
        setCard(card);
        setRowPos(rowPos);
        setColPos(colPos);
        setCurrentlySelected(false);
    }

    // region getters & setters

    /**
     * @return the Card occupying this BoardSquare
     */
    public Card getCard() {
        return card;
    }

    /**
     * @return the row position of this BoardSquare
     */
    public int getRowPos() {
        return rowPos;
    }

    /**
     * @return the column position of this BoardSquare
     */
    public int getColPos() {
        return colPos;
    }

    /**
     * @return whether or not this BoardSquare is currently selected
     */
    public boolean isCurrentlySelected() {
        return currentlySelected;
    }


    /**
     * Sets the Card that occupies this BoardSquare
     * @param card the Card that should occupy this BoardSquare
     */
    public void setCard(Card card) {
        this.card = card;
    }

    /**
     * @param rowPos the row position of this BoardSquare
     */
    public void setRowPos(int rowPos) {
        this.rowPos = rowPos;
    }

    /**
     * @param colPos the column position of this BoardSquare
     */
    public void setColPos(int colPos) {
        this.colPos = colPos;
    }

    /**
     * @param val boolean value to determine if this BoardSquare is currently selected or not
     */
    public void setCurrentlySelected(boolean val) {
        this.currentlySelected = val;
    }

    // endregion


    /**
     * @return a String representation of this BoardSquare
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("BoardSquare at (r:%d, c:%d) contains ", rowPos, colPos));
        sb.append(card);

        return sb.toString();
    }
}
