import java.net.URISyntaxException;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

class Avatar extends Piece {

	Avatar(int row, int col) {
		super(row, col, "you", Color.AQUA);
		Image img = null;
		try {
			img = new Image(getClass().getResource("/resources/venus.jpg").toURI().toString());
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
			HiVolts.death = "You took on a fence :)";
		}
		if (HiVolts.board.squares[row][col] == 3) {
			HiVolts.death = "Ran into Mho";
		}
		alive = false;
		return false;
	}

	/*
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
		case NUMPAD0:///////
			moveJump();
			return false;	
		default:
			return false;
		}
		return true;
	}

	public boolean moveUpLeft() {
		if (check(col-1, row-1)) {
			HiVolts.board.squares[row][col] = 0;
			col = col - 1;
			row = row - 1;
			HiVolts.board.squares[row][col] = 2;
			super.moveUpLeft();
		}
		return true;
	}

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

	Random rn = new Random();
	public void moveJump() {
		
		while (true) {
			int newCol = rn.nextInt(11);
			int newRow = rn.nextInt(11);
			if (HiVolts.board.squares[newRow][newCol] == 0) {
				HiVolts.board.squares[row][col] = 0;
				HiVolts.board.squares[newRow][newCol] = 2;
				col = newCol;
				row = newRow;
				super.moveJump();
				return;
			}
			if (HiVolts.board.squares[newRow][newCol] == 3) {
				HiVolts.board.squares[row][col] = 0;
				HiVolts.board.squares[newRow][newCol] = 2;
				super.moveJump();
				col = newCol;
				row = newRow;
				alive = false;
				HiVolts.death = "You landed on a Mho!";
				return;
			}
		}
	}
}