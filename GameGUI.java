import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import java.util.ArrayList;

public class GameGUI extends Application {
    private Game game;

    private Scene scene;
    private Stage stage;

    private BorderPane mainLayout;
    private AnchorPane statusPane;
    private AnchorPane menuPane;
    private HBox menuHBox;
    private GridPane grid;

    private Label cardsRemaining;
    private Label statusMessage;

    private boolean debug = false;       //##### SET TO TRUE TO ENABLE DEBUGGING TO CONSOLE #######//

    /**
     * Handles the setup of the Game of Set, including initialization of user interface.
     */
    @Override
    public void start(Stage primaryStage) {
        // new game
        game = new Game();

        this.stage = primaryStage;
        stage.setTitle("Game of Set");     // window title

        mainLayout = new BorderPane();      // main window

        //region status area
        statusPane = new AnchorPane();
        statusPane.setStyle("-fx-background-color: deepskyblue;"
                                  + "-fx-border-color: steelblue;"
                                  + "-fx-border-width: 0px 0px 8px 0px;"
                                  + "-fx-border-style: solid");

        TextFlow titleFlow = new TextFlow();
        titleFlow.setTextAlignment(TextAlignment.LEFT);
        titleFlow.setLineSpacing(1.1);

        Text title = new Text("The Game of Set");
        title.setFont(Font.font("Verdana",36));

        Text auth = new Text("\n    by Zach Bernstein");
        auth.setFont(Font.font("Verdana",16));
        auth.setFill(Color.valueOf("#2a2a2a"));

        titleFlow.getChildren().addAll(title, auth);
        AnchorPane.setLeftAnchor(titleFlow, 7.);
        AnchorPane.setTopAnchor(titleFlow, 2.);
        AnchorPane.setBottomAnchor(titleFlow, 7.);

        cardsRemaining = new Label(String.format("Cards remaining: %d", game.cardsRemaining()));
        cardsRemaining.setFont(Font.font("Monaco", 18));
        AnchorPane.setTopAnchor(cardsRemaining, 15.);
        AnchorPane.setRightAnchor(cardsRemaining, 10.);

        statusMessage = new Label("");
        statusMessage.setFont(Font.font("Arial", 18));

        statusPane.getChildren().addAll(titleFlow, cardsRemaining);
        //endregion

        //region menu area
        menuPane = new AnchorPane();
        menuPane.setStyle("-fx-background-color: deepskyblue;"
                                  + "-fx-border-color: steelblue;"
                                  + "-fx-border-width: 8px 0px 0px 0px;"
                                  + "-fx-border-style: solid");

        menuHBox = new HBox(10);
        menuHBox.setPadding(new Insets(1,5,1,5));
        menuHBox.setAlignment(Pos.CENTER_RIGHT);

        AnchorPane.setRightAnchor(statusMessage, 20.);
        AnchorPane.setBottomAnchor(statusMessage, 3.);
        AnchorPane.setTopAnchor(statusMessage, 3.);

        AnchorPane.setLeftAnchor(menuHBox, 3.);
        AnchorPane.setBottomAnchor(menuHBox, 3.);
        AnchorPane.setTopAnchor(menuHBox, 3.);

        menuPane.getChildren().addAll(statusMessage, menuHBox);

        // buttons
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(this::handleExit);

        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(this::handleNewGame);

        Button findSetButton = new Button("Find a Set");
        findSetButton.setOnAction(this::handleFindSet);

        Button add3Button = new Button("Add 3 Cards");
        add3Button.setOnAction(this::handleAdd3);

        menuHBox.getChildren().addAll(exitButton, newGameButton, findSetButton, add3Button);
        //endregion

        //region card area
        grid = new GridPane();
        grid.setStyle("-fx-background-color: darkslategrey");

        grid.setHgap(5); // padding between grid columns
        grid.setVgap(5); // padding between grid rows
        grid.setPadding(new Insets(10)); // padding around entire grid
        //endregion

        // arrange window
        mainLayout.setCenter(grid);
        mainLayout.setTop(statusPane);
        mainLayout.setBottom(menuPane);

        this.drawBoard();

        scene = new Scene(mainLayout);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
    }

    /**
     * Launches the game
     * @param args args
     */
    public static void main(String [] args) {
        launch(args);
    }

    /**
     * A method that repopulates the GridPane to reflect when the underlying Board has changed.
     * Also re-sizes the window (the Stage) to reflect the new, larger (or smaller) game area.
     */
    private void drawBoard() {
        if (debug) System.out.println("redrawing board...");

        // erase board
        grid.getChildren().clear();
        statusMessage.setText("");


        // populate board
        Board b = game.getBoard();
        for (int r = 0; r < b.numRows(); r++) {
            for (int c = 0; c < b.numCols(); c++) {
                Pane cp = new CardPane(b.getBoardSquare(r,c));
                cp.setOnMouseClicked(this::handleSelectCardPane);
                grid.add(cp, c, r);
            }
        }

        // resize window
        if (b.numCols() >= 4) // prevents scene from getting messed up when shrinking window past minimum width/height
            stage.sizeToScene();
    }

    //region event handlers
    /**
     * Event Handler
     * Quits the game
     * @param e an Event (a Button press)
     */
    private void handleExit(ActionEvent e) {
        if (debug) System.out.println("quitting!");

        Platform.exit();
    }

    /**
     * Event Handler
     * Starts a new game
     * @param e an Event (a Button press)
     */
    private void handleNewGame(ActionEvent e) {
        if (debug) System.out.println("starting new game!");

        game = new Game();
        this.drawBoard();
        cardsRemaining.setText(String.format("Cards remaining: %d", game.cardsRemaining()));
        statusMessage.setText("");
        System.gc(); // runs garbage collection, gets rid of old game, deck, etc. so that they don't take up memory
    }

    /**
     * Event Handler
     * logic for when the "Find A Set" Button is clicked.
     * @param e an Event (a Button press)
     */
    private void handleFindSet(ActionEvent e) {
        if (debug) System.out.println("finding a set!");

        BoardSquare[] set = game.findSet();

        // highlight cards in set on board
        if (set.length == 3) {
            statusMessage.setText("Found a set!");
            if (debug) System.out.println("set found!");

            for (BoardSquare bs : set) {
                int row = bs.getRowPos();
                int col = bs.getColPos();

                // loop through grid children until card at specified position is found
                for (Node n : grid.getChildren()) {
                    if (GridPane.getColumnIndex(n) == col && GridPane.getRowIndex(n) == row) {
                        n.setStyle("-fx-background-color: #fff;"
                                           + "-fx-border-width: 3;"
                                           + "-fx-border-color: orangered;"
                                           + "-fx-border-style: dashed;");
                    }
                }
                if (debug) System.out.println(String.format("card location: (c:%d, r:%d)", bs.getColPos(), bs.getRowPos()));
            }
        }
        else {
            statusMessage.setText("No sets found.");
            if (debug) System.out.println("no sets found");
        }
    }

    /**
     * Event Handler
     * Handles adding 3 cards to the board when the corresponding button is pressed.
     * @param e an Event (a button press)
     */
    private void handleAdd3(ActionEvent e) {
        if (game.cardsRemaining() >= 3 && grid.getChildren().size() < 18) {
            if (debug) System.out.println("adding 3 cards");

            game.add3();
            this.drawBoard();
            cardsRemaining.setText(String.format("Cards remaining: %d", game.cardsRemaining()));
        }
        else {
            if (grid.getChildren().size() >= 18)
                statusMessage.setText("Too many cards on board!");
            else if (game.cardsRemaining() < 3)
                statusMessage.setText("Not enough cards in deck!");
        }
    }

    /**
     * Event Handler
     * Logic for clicking on a CardPane to select it, and handling any set testing that needs to be done.
     * @param e a mouse click
     */
    private void handleSelectCardPane(MouseEvent e) {
        CardPane cp = (CardPane) e.getSource();         // get corresponding CardPane
        BoardSquare bs = cp.getBoardSquare();           // get corresponding BoardSquare

        if (debug) System.out.println(String.format("pane clicked: (c:%d, r:%d) | %s", cp.getCol(), cp.getRow(), cp.getCard().toString()));

        // select or deselect card
        if (!bs.isCurrentlySelected()) {
            bs.setCurrentlySelected(true);
            cp.setStyle("-fx-background-color: lightblue;"
                                + "-fx-border-width: 3;"
                                + "-fx-border-color: #000;"
                                + "-fx-border-style: solid;");
            game.addToSelected(cp.getRow(), cp.getCol());
        }
        else if (bs.isCurrentlySelected()) {
            bs.setCurrentlySelected(false);
            cp.setStyle("-fx-background-color: #fff;"
                                  + "-fx-border-width: 3;"
                                  + "-fx-border-color: #000;"
                                  + "-fx-border-style: solid;");
            game.removeSelected(cp.getRow(), cp.getCol());
        }


        // test three cards
        if (game.numSelected() == 3) {
            boolean isSet = game.testSelected();

            if (debug) {
                if (isSet) System.out.println("set found!");
                else System.out.println("set not found!");
            }

            this.drawBoard();
        }

        // update cards remaining label
        cardsRemaining.setText(String.format("Cards remaining: %d", game.cardsRemaining()));
    }
    //endregion
}
