import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class HiVolts extends Application {
    private Pane root;
    private GridPane gPane;
    Stage stage;

    final static int OFFSET = 75;
    final static int WIDTH = 50;
    final static int HEIGHT = 50;
    final static int PANESIZE = 1000;

    static Board board;
    static Avatar you;
    static Label label;

    private Parent createContent() {
        root = new Pane();
        gPane = new GridPane();
        root.setPrefSize(PANESIZE, PANESIZE);
        
        board = new Board(12, 12, 20, 12);
        setupBoard(root, board);
        addLabel();
        addButton();

        gPane.getChildren().add(root);

        return gPane;
    }

    private void setupBoard(Pane root, Board board) {
        // go through board setting up the fences, mhos and the avitar
        for(int row = 0; row < board.ROWS; row++) {
            for(int col = 0; col < board.COLS; col++) {
                if (board.squares[row][col] == 1) {
                    root.getChildren().add(new Fence(col, row));
                }
                if (board.squares[row][col] == 2) {
                    you = new Avatar(col, row);
                    root.getChildren().add(you);
                }
                if (board.squares[row][col] == 3) {
                    root.getChildren().add(new Mho(col, row));
                }
            }
        }
    }

    private void addLabel() {
        label = new Label("hello");
        gPane.setHalignment(label, HPos.CENTER);
        gPane.setValignment(label, VPos.BOTTOM);
        gPane.add(label, 0, 0);
    }

    private void addButton() {
        Button button = new Button("Button");
        button.setId("restart");
        button.setText("Restart");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                startUp(stage);
            }
        });
        gPane.setValignment(button, VPos.BOTTOM);
        gPane.add(button, 1, 1);
    }

    private List<Shape> pieces() {
        return root.getChildren().stream().map(n -> (Shape)n).collect(Collectors.toList());
    }

    private void update() {
        // move all the mhos
        pieces().forEach(s -> {
            if (s instanceof Piece) {
                if (((Piece) s).pieceType == "mho") {
                    Mho ss = (Mho) s;
  //                  ss.move();
                }
            }
        });

        // remove any who have died
        root.getChildren().removeIf(n -> {
            if (n instanceof Piece) {
                Piece s = (Piece) n;
                return ! s.alive;
            } else {
                return false;
            }
        });

    }


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        startUp(this.stage);
    }

    void startUp(Stage stage) {
        Scene scene = new Scene(createContent());

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    you.moveLeft();
                    break;
                case D:
 //                   you.moveRight();
                    break;
                case X:
   //                 you.moveDown();
                    break;
                case W:
     //               you.moveUp();
                    break;
            }
            update();
            label.setText("" + e.getCode());
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}