public class Card {
    // define Enumerated attributes
    public enum Color      {RED, PURPLE, GREEN}
    public enum Shading    {SOLID, STRIPED, OUTLINED}
    public enum Shape      {OVAL, SQUIGGLE, DIAMOND}
    public enum Number     {ONE, TWO, THREE}

    // define instance variables
    private Color    color;
    private Shading  shading;
    private Shape    shape;
    private Number   number;

    /**
     * Constructor.
     * @param color the color of this Card
     * @param shading the shading of this Card
     * @param shape the shape of this Card
     * @param number the number of this Card
     */
    public Card(Color color, Shading shading, Shape shape, Number number) {
        this.color   = color;
        this.shading = shading;
        this.shape   = shape;
        this.number  = number;
    }

    /**
     * Formats the string representation of a Card.
     * @return the representation of this Card as a String.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // add shading
        switch (shading) {
            case SOLID:
                sb.append("[###]");
                break;
            case STRIPED:
                sb.append("[///]");
                break;
            case OUTLINED:
                sb.append("[___]");
                break;
        }

        // add spacing
        sb.append(" ");

        // add number & shapes
        switch (number) {
            case ONE:
                sb.append("[1]");
                break;
            case TWO:
                sb.append("[2]");
                break;
            case THREE:
                sb.append("[3]");
                break;
        }

        // add spacing
        sb.append(" ");

        // add shape
        switch (shape) {
            case SQUIGGLE:
                sb.append(String.format("%-9s", "SQUIGGLE"));
                break;
            case DIAMOND:
                sb.append(String.format("%-9s", "DIAMOND"));
                break;
            case OVAL:
                sb.append(String.format("%-9s", "OVAL"));
                break;
        }

        // add color
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_PURPLE = "\u001B[35m";

        switch (color) {
            case RED:
                sb.append(ANSI_RED + "[R]" + ANSI_RESET);
                break;
            case GREEN:
                sb.append(ANSI_GREEN + "[G]" + ANSI_RESET);
                break;
            case PURPLE:
                sb.append(ANSI_PURPLE + "[P]" + ANSI_RESET);
                break;
        }

        return sb.toString();

    }

    // region getters

    /**
     * @return this Card's Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return this Card's Number
     */
    public Number getNumber() {
        return number;
    }

    /**
     * @return this Card's Shading
     */
    public Shading getShading() {
        return shading;
    }

    /**
     * @return this Card's Shape
     */
    public Shape getShape() {
        return shape;
    }

    // endregion getters


    /**
     * @param c1 a Card
     * @param c2 a Card
     * @param c3 a Card
     * @return True if the three Card objects form a set, False otherwise.
     */
    public static boolean isSet(Card c1, Card c2, Card c3) {

        // region color
        boolean isColorMatching = ((c1.getColor() == c2.getColor())
                && (c1.getColor() == c3.getColor())
                && (c2.getColor() == c3.getColor())
        );

        boolean isColorUnique = ((c1.getColor() != c2.getColor())
                && (c1.getColor() != c3.getColor())
                && (c2.getColor() != c3.getColor())
        );

        boolean isColorSet = (isColorUnique || isColorMatching);
        // endregion

        // region shading
        boolean isShadingMatching = ((c1.getShading() == c2.getShading())
                && (c1.getShading() == c3.getShading())
                && (c2.getShading() == c3.getShading())
        );

        boolean isShadingUnique = ((c1.getShading() != c2.getShading())
                && (c1.getShading() != c3.getShading())
                && (c2.getShading() != c3.getShading())
        );

        boolean isShadingSet = (isShadingMatching || isShadingUnique);
        // endregion

        // region shape
        boolean isShapeMatching = ((c1.getShape() == c2.getShape())
                && (c1.getShape() == c3.getShape())
                && (c2.getShape() == c3.getShape())
        );

        boolean isShapeUnique = ((c1.getShape() != c2.getShape())
                && (c1.getShape() != c3.getShape())
                && (c2.getShape() != c3.getShape())
        );

        boolean isShapeSet = (isShapeMatching || isShapeUnique);
        // endregion

        // region number
        boolean isNumberMatching = ((c1.getNumber() == c2.getNumber())
                && (c1.getNumber() == c3.getNumber())
                && (c2.getNumber() == c3.getNumber())
        );

        boolean isNumberUnique = ((c1.getNumber() != c2.getNumber())
                && (c1.getNumber() != c3.getNumber())
                && (c2.getNumber() != c3.getNumber())
        );

        boolean isNumberSet = (isNumberMatching || isNumberUnique);
        // endregion

        // check if every single attribute is different
        if (isColorSet && isShadingSet && isShapeSet && isNumberSet)
            return true;
        else
            return false;
    }
}
