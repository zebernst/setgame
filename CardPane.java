import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;

public class CardPane extends VBox {
    private int row;
    private int col;
    private BoardSquare bs;

    /**
     * Constructor.
     * Creates a VBox representation of a given Card object.
     * @param bs the BoardSquare (and the Card it contains) that are used to create this CardPane
     * @return the CardPane representing the Card.
     */
    public CardPane(BoardSquare bs) {
        // call VBox constructor
        super();

        // store position
        this.bs = bs;
        this.row = bs.getRowPos();
        this.col = bs.getColPos();
        Card card = bs.getCard();

        // define variables
        Color cardColor;
        Color outlineColor;
        Paint fillType;

        // begin styling
        this.setSpacing(9); // sets spacing between children
        this.setPadding(new Insets(10)); // padding between shapes and card border
        this.setPrefSize(140, 210); // sets dimensions
        this.setAlignment(Pos.CENTER); // forces children to be centered in the middle of the card
        this.setStyle("-fx-background-color: #fff;"
                              + "-fx-border-width: 3;"
                              + "-fx-border-color: #000;"
                              + "-fx-border-style: solid;");


        // convert card color to javaFX color
        switch (card.getColor()) {
            case GREEN:     cardColor = Color.GREEN;  break;
            case PURPLE:    cardColor = Color.PURPLE; break;
            case RED:       cardColor = Color.RED;    break;
            default:        cardColor = Color.TRANSPARENT;
        }

        // set shading
        switch (card.getShading()) {
            case SOLID:
                fillType        = cardColor;
                outlineColor    = cardColor;
                break;
            case STRIPED:
                fillType        = new ImagePattern(createHatch(cardColor), 0, 0, 17, 17, false);
                outlineColor    = cardColor;
                break;
            case OUTLINED:
                fillType        = Color.TRANSPARENT;
                outlineColor    = cardColor;
                break;
            default:
                fillType        = Color.BLACK;
                outlineColor    = Color.BLACK;
        }

        // draw shape
        Node shape;
        for (int i = 0; i <= card.getNumber().ordinal(); i++) {
            switch (card.getShape()) {
                case DIAMOND:   shape = drawDiamond(outlineColor,   fillType); break;
                case OVAL:      shape = drawEllipse(outlineColor,   fillType); break;
                case SQUIGGLE:  shape = drawSquiggle(outlineColor,  fillType); break;
                default:        shape = drawRectangle(outlineColor, fillType);
            }
            this.getChildren().add(shape);
        }
    }

    /**
     * @return the column position of the CardPane.
     */
    public int getCol() {
        return col;
    }

    /**
     * @return the row position of the CardPane
     */
    public int getRow() {
        return row;
    }

    /**
     * @return the Card that this CardPane represents.
     */
    public Card getCard() {
        return bs.getCard();
    }

    /**
     * @return the BoardSquare that contains the Card that this CardPane represents.
     */
    public BoardSquare getBoardSquare() {
        return bs;
    }

    /**
     * Draw a diamond shape with the specified fill and outline.
     * @param outlineColor the Color of the shape outline.
     * @param fillType the type of fill of the shape.
     * @return the drawn diamond (a Polygon).
     */
    private Polygon drawDiamond(Color outlineColor, Paint fillType) {
        Polygon p = new Polygon();
        Double[] points = new Double[] {
                0.0, 25.0,
                50.0, 50.0,
                100.0, 25.0,
                50.0, 0.0
        };
        p.getPoints().addAll(points);
        p.setFill(fillType);
        p.setStroke(outlineColor);
        p.setStrokeWidth(3);

        return p;
    }

    /**
     * Draw an ellipse with the specified fill and outline.
     * @param outlineColor the Color of the shape outline.
     * @param fillType the type of fill of the shape.
     * @return the drawn ellipse (a Rectangle).
     */
    private Rectangle drawEllipse(Color outlineColor, Paint fillType) {
        Rectangle e = new Rectangle(0,0,100,50);
        e.setArcHeight(50);
        e.setArcWidth(40);
        e.setFill(fillType);
        e.setStroke(outlineColor);
        e.setStrokeWidth(3);

        return e;
    }

    /**
     * Draw a squiggle with the specified fill and outline.
     * @param outlineColor the Color of the shape outline.
     * @param fillType the type of fill of the shape.
     * @return the drawn squiggle (an SVGPath).
     */
    private SVGPath drawSquiggle(Color outlineColor, Paint fillType) {
        SVGPath svg = new SVGPath();
        svg.setContent("M 26 148 C 22 152 19 152 22 132 C 23 119 37 101 47 100 C 57 99 70 117 83 118 C 96 119 109 105 115 101 C 122 98 122 105 118 120 C 114 133 99 149 87 150 C 75 150 60 134 49 134 C 38 133 30 144 26 148 Z");
        svg.setFill(fillType);
        svg.setStroke(outlineColor);
        svg.setStrokeWidth(3);

        return svg;
    }

    /**
     * Draw a rectangle with the specified fill and outline.
     * @param outlineColor the Color of the shape outline.
     * @param fillType the type of fill of the shape.
     * @return the drawn rectangle (a Rectangle).
     */
    private Rectangle drawRectangle(Color outlineColor, Paint fillType) {
        Rectangle r = new Rectangle(0,0,100,50);
        r.setFill(fillType);
        r.setStroke(outlineColor);
        r.setStrokeWidth(3);

        return r;
    }

    /**
     * Creates a hatch pattern to use as a fill option.
     * @param hatchColor the color to use in the hatching.
     * @return an Image of the hatch pattern.
     */
    private Image createHatch(Color hatchColor) {
        Pane pane = new Pane();
        pane.setPrefSize(20, 20);
        Line fw = new Line(0, 0, 25, 25);
        fw.setRotate(0);
        fw.setStroke(hatchColor);
        fw.setStrokeWidth(5);
        pane.getChildren().addAll(fw);
        new Scene(pane);
        return pane.snapshot(null, null);
    }
}
