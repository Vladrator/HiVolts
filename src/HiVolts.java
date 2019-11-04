import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
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

// Kian


public class HiVolts extends Application {
	private Pane root;
	private GridPane gPane;
	Stage stage;

	// sets final variables that will be used throughout
	final static int OFFSET = 75;
	final static int WIDTH = 50;
	final static int HEIGHT = 50;
	final static int PANESIZE = 1000;
	static String death = "You died... restarting!"; // creates the string that is printed upon death

	static Board board;			// the 2d array representation of the board
	public static Avatar you;   // the object that represents "you" (avatar)
	static Label label;		    // label for displaying information
	static int gameNum = 0; 	// the will increase with each game that is played

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * standard FX routine that sets up the display (called when launch() in main)
	 */
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		startUp(this.stage);
	}

	/**
	 * in this game the scene updates when the user hits a key to move the avatar
	 * after the avatar moves the positions of the mhos are updated
	 * 
	 * once everything has moved check to see if the avatar is alive or whether 
	 * the mhos have died
	 * 
	 * @param stage 
	 */
	void startUp(Stage stage) {
		Scene scene = new Scene(createContent());

		gameNum++; // number of games played
		
		scene.setOnKeyPressed(e -> {

			if (you.move(e.getCode())) {
//				System.out.println("avatar " + you.col + " " + you.row);
				updateMhos(); // updates mhos positions
			}

			if (you.alive == false) { // checks if the avatar is alive
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Restart");
				alert.setHeaderText(null);
				alert.setContentText(death);
				alert.showAndWait();
				startUp(this.stage);
			}

			if(Mho.mhoNum == 0) { // checks if there are any more mhos alive
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Restart");
				alert.setHeaderText(null);
				alert.setContentText("You Win!!!"); // sets pop up panel to this message
				alert.showAndWait();
				startUp(this.stage);
			}
			label.setText("Game " + gameNum + ": " + Mho.mhoNum + " Mhos left");
		});

		stage.setScene(scene);
		stage.show(); // displays scene
	}
	
	/**
	 * sets up a gridpane that is used to display the board along with the button and label
	 */
	private Parent createContent() {
		root = new Pane();
		gPane = new GridPane();
		root.setPrefSize(PANESIZE, PANESIZE); // sets the size of the board on the screen
		Mho.mhoNum = 0;		// will increase as the board is set up with mhos and decrease
		// as they die, game ends when it reaches 0

		board = new Board(12, 12, 20, 12); // set up the board with the given size and number of objects
		setupBoard(root, board);
		addLabel();
		addButton();

		gPane.getChildren().add(root);

		return gPane;
	}

	/**
	 * uses the 2d array of integers and displays the corresponding shape on the FX pane
	 * 
	 * @param root FX pane
	 * @param board 2d array of integer (1 = fence, 2 = avatar, 3 = mho)
	 */
	private void setupBoard(Pane root, Board board) {
		// go through board setting up the fences, mhos and the avitar
		for(int row = 0; row < board.ROWS; row++) {
			for(int col = 0; col < board.COLS; col++) {
				if (board.squares[row][col] == 1) {
					root.getChildren().add(new Fence(row, col));
				}
				if (board.squares[row][col] == 2) {
					you = new Avatar(row, col);
					root.getChildren().add(you);
				}
				if (board.squares[row][col] == 3) {
					root.getChildren().add(new Mho(row, col));
				}
			}
		}
	}

	/**
	 * creates the label and displays it on the gridpane
	 */
	private void addLabel() {
		label = new Label("Game Starting");
		gPane.setHalignment(label, HPos.CENTER);
		gPane.setValignment(label, VPos.BOTTOM);
		gPane.add(label, 0, 0);
	}

	/**
	 * creates the button and displays it on the gridpane
	 */
	private void addButton() {
		Button button = new Button("Button");
		button.setId("restart");
		button.setText("Restart");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) { // if button is pressed it restarts everything
				startUp(stage);
			}
		});
		gPane.setValignment(button, VPos.BOTTOM); // sets button location
		gPane.add(button, 1, 1);
	}

	/**
	 * gets all the children (each visible shapes) of the scene and returns 
	 * them as a list
	 * 
	 * @return returns the list of visible shapes
	 */
	private List<Shape> pieces() {
		return root.getChildren().stream().map(n -> (Shape)n).collect(Collectors.toList());
	}	

	/**
	 * it finds all remaining mhos and tells them to move, afterwards it removes
	 * any mhos that die from the display
	 */
	private void updateMhos() {
		if (you.alive == false) {
			return;
		}

		// move all the mhos
		pieces().forEach(s -> { // goes through all shapes
			if (s instanceof Piece) { // looks for anything that that is a shape
				if (((Piece) s).pieceType == "mho") {
					Mho m = (Mho) s;
					m.move();
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

}