import java.net.URISyntaxException;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.ImagePattern;

//Aaron

class Avatar extends Piece {

	// displays the image the avatar takes
	Avatar(int row, int col) {
		super(row, col, "you"); // calls piece and gives, the location of the avatar
											// and what type you are, in this case you means your avatar
		Image img = null;
		try {
			// takes image from the reasources folder prints it
			img = new Image(getClass().getResource("/resources/Red.png").toURI().toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setFill(new ImagePattern(img));
	}

	Boolean check (int col, int row) {
		if (HiVolts.board.squares[row][col] == 0) {
			return true;
		}
		if (HiVolts.board.squares[row][col] == 1) {
			HiVolts.death = "You took on a fence!!!";
		}
		if (HiVolts.board.squares[row][col] == 3) {
			HiVolts.death = "Ran into a Snowtrooper";
		}
		alive = false;
		return false;
	}

	/*takes the input from HiVolts about what key is pressed and runs a function to move the figure
	 * Q: up and left
		W: up
		E: up and right
		A: left
		S: sit (stay on the same square for one turn)
		D: right
		Z: down and left
		X: down
		C: down and right
		J: jump
	 */
	public boolean move(KeyCode code){
		switch (code) {
		case Q:
		case NUMPAD7:
			moveUpLeft();
			break;
		case W:
		case NUMPAD8:
			moveUp();
			break;
		case E:
		case NUMPAD9:
			moveUpRight();
			break;
		case A:
		case NUMPAD4:
			moveLeft();
			break;
		case S:
		case NUMPAD5:
			break;
		case D:
		case NUMPAD6:
			moveRight();
			break;
		case Z:
		case NUMPAD1:
			moveDownLeft();
			break;
		case X:
		case NUMPAD2:
			moveDown();
			break;
		case C:
		case NUMPAD3:
			moveDownRight();
			break;
		case J:
		case NUMPAD0:
			moveJump();
			return false;	
		default:
			return false;
		}
		return true;
	}

	// is called from move and sets the location where the avatar was in the 2d array to nothing 
	// and where it will be to 2/avatar. Once it does that it calls the super piece which moves the avatar
	public boolean moveUpLeft() {
		if (check(col-1, row-1)) {
			HiVolts.board.squares[row][col] = 0; // set current location to nothing
			col = col - 1; // get new x
			row = row - 1; // get new y
			HiVolts.board.squares[row][col] = 2; // set new location to avatar
			super.moveUpLeft(); // calls move function in piece
		}
		return true;
	}
 
	// the comments in the function above can be added to all following functions
	public boolean moveLeft() {
		if (check(col-1, row)) {
			HiVolts.board.squares[row][col] = 0;
			col = col - 1;
			HiVolts.board.squares[row][col] = 2;
			super.moveLeft();
		}
		return true;
	}

	public boolean moveUpRight() {
		if (check(col+1, row-1)) {
			HiVolts.board.squares[row][col] = 0;
			col = col + 1;
			row = row - 1;
			HiVolts.board.squares[row][col] = 2;
			super.moveUpRight();
		}
		return true;
	}

	public boolean moveRight() {
		if (check(col+1, row)) {
			HiVolts.board.squares[row][col] = 0;
			col = col + 1;
			HiVolts.board.squares[row][col] = 2;
			super.moveRight();
		}
		return true;
	}

	public boolean moveDownLeft() {
		if (check(col-1, row+1)) {
			HiVolts.board.squares[row][col] = 0;
			col = col - 1;
			row = row + 1;
			HiVolts.board.squares[row][col] = 2;
			super.moveDownLeft();
		}
		return true;
	}

	public boolean moveUp() {
		if (check(col, row-1)) {
			HiVolts.board.squares[row][col] = 0;
			row = row - 1;
			HiVolts.board.squares[row][col] = 2;
			super.moveUp();
		}
		return true;
	}

	public boolean moveDown() {
		if (check(col, row+1)) {
			HiVolts.board.squares[row][col] = 0;
			row = row + 1;
			HiVolts.board.squares[row][col] = 2;
			super.moveDown();
		}
		return true;
	}

	public boolean moveDownRight() {
		if (check(col+1, row+1)) {
			HiVolts.board.squares[row][col] = 0;
			col = col + 1;
			row = row + 1;
			HiVolts.board.squares[row][col] = 2;
			super.moveDownRight();
		}
		return true;
	}

	// creates new random coordinates for the avatar, sets the current coordinate to 0 in the 2d array
	// and sets the new coodriante to 2 or avatar. Tehn calls pieces jump function to make the avatar jump
	Random rn = new Random();
	public void moveJump() {
		
		while (true) { // always does it
			int newCol = rn.nextInt(11); // new x coodrinate
			int newRow = rn.nextInt(11); // new y coodrinate
			if (HiVolts.board.squares[newRow][newCol] == 0) { // checks if new square is empty
				HiVolts.board.squares[row][col] = 0; // set current x,y to nothing
				HiVolts.board.squares[newRow][newCol] = 2; // if empty set new x,y to avatar
				col = newCol;
				row = newRow;
				super.moveJump(); // calls function in piece to do it
				return;
			}
			
			if (HiVolts.board.squares[newRow][newCol] == 3) { // checks if new square is a mho
				HiVolts.board.squares[row][col] = 0; // sets current square to nothing
				HiVolts.board.squares[newRow][newCol] = 2; // sets new square to avatar
				super.moveJump(); // tells piece to jump to new location
				col = newCol;
				row = newRow;
				alive = false; // sets alive to false, killing the avatar
				HiVolts.death = "You landed on a Snowtrooper!!!"; // prints this message on the screen
				return;
			}
		}
	}
}