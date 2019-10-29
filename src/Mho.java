import java.net.URISyntaxException;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

class Mho extends Piece {

	Mho(int col, int row) {
		super(col, row, "mho", Color.BLUEVIOLET);
		Image img = null;
		try {
			img = new Image(getClass().getResource("/resources/venus.jpg").toURI().toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setFill(new ImagePattern(img));
	}

	/*
	 * If you are directly vertical or hortizontal to a Mho, the Mho MUST move directly towards you one square. 
	 * 	If this results in the Mho landing on an electric fence, the Mho is removed from the board. If it lands on an empty square, 
	 * 		it remains on the board.
	 * 	If the Mho is not directly horizontal or vertical from you, then it has three squares it can move to, the one that is 
	 * 		diagonally towards you, one that is horizontal so that it would be closer to you, and one that is vertical so that it 
	 * 		would be closer to you. 
	 * 
	 * 	The Mho will try to move onto an empty square in the following order: 
	 * 		A- Diagonally towards you
	 * 		B- If the horizontal distance towards You is greater than or equal to the vertical distance, move horizontally towards You;
	 * 		   If the horizontal distance towards You is less than or equal to the vertical distance, move vertically towards You;
	 * 		If none of these can result in landing on an empty square, repeat the order with the attempt to move onto an electric fence
If none of these can result in landing on an electric fence, do not move (this happens when a Mho can only try to move onto other Mhos)
	 */
	public void move() {
		System.out.println("mho " + col + " " + row + " avatar " + HiVolts.you.col + " " + HiVolts.you.row);

		// if the Mho is on the diagonal as the avatar it will move toward it
		if (Math.abs(HiVolts.you.col - col) == Math.abs(HiVolts.you.row - row)) {
			// top right diagonal
			if (HiVolts.you.row < row && HiVolts.you.col > col) {
				moveUpRight();
			} 

			// top left diagonal
			if (HiVolts.you.row < row && HiVolts.you.col < col) {
				moveUpLeft();
			}

			// bottom left diagonal
			if (HiVolts.you.row > row && HiVolts.you.col < col) {
				moveDownLeft();
			}

			// bottom right diagonal
			if (HiVolts.you.row > row && HiVolts.you.col > col) {
				moveDownRight();
			}
			return;
		}

		// if the Mho is on the same row as the avatar it will move toward it
		if (HiVolts.you.row == row) {
			if (HiVolts.you.col > col) {
				moveRight();
			} else {
				moveLeft();
			}
			return;
		}

		// if the Mho is on the same column as the avatar it will move toward it
		if (HiVolts.you.col == col) {
			if (HiVolts.you.row > row) {
				moveDown();
			} else {
				moveUp();
			}
			return;
		}


	}

	/**
	 * 
	 * @param col
	 * @param row
	 * @return true if you should move
	 */
	Boolean check (int col, int row) {

		if (HiVolts.board.squares[row][col] == 1) {
			alive = false;
			return true;
		}

		if (HiVolts.board.squares[row][col] == 2) {
			HiVolts.you.alive = false;
			HiVolts.death = "Killed by a Mho";
			return true;
		}

		if (HiVolts.board.squares[row][col] == 3) {
			HiVolts.didNotMove.add(this);
			return false;
		}

		return true;
	}

	public void moveRight() {
		if (check(col+1, row)) {
			HiVolts.board.squares[row][col] = 0;
			col = col + 1;
			HiVolts.board.squares[row][col] = 3;
			super.moveRight();
		}
	}

	public void moveLeft() {
		if (check(col-1, row)) {
			HiVolts.board.squares[row][col] = 0;
			col = col - 1;
			HiVolts.board.squares[row][col] = 3;
			super.moveLeft();
		}
	}

	public void moveUp() {
		if (check(col, row-1)) {
			HiVolts.board.squares[row][col] = 0;
			row = row - 1;
			HiVolts.board.squares[row][col] = 3;
			super.moveUp();
		}
	}

	public void moveDown() {
		if (check(col, row+1)) {
			HiVolts.board.squares[row][col] = 0;
			row = row + 1;
			HiVolts.board.squares[row][col] = 3;
			super.moveDown();
		}
	}

	public void moveUpRight() {
		if (check(col+1, row-1)) {
			HiVolts.board.squares[row][col] = 0;
			col = col + 1;
			row = row - 1;
			HiVolts.board.squares[row][col] = 3;
			super.moveUpRight();
		}
	}

	public void moveUpLeft() {
		if (check(col-1, row-1)) {
			HiVolts.board.squares[row][col] = 0;
			col = col - 1;
			row = row - 1;
			HiVolts.board.squares[row][col] = 3;
			super.moveUpLeft();
		}
	}

	public void moveDownLeft() {
		if (check(col-1, row+1)) {
			HiVolts.board.squares[row][col] = 0;
			col = col - 1;
			row = row + 1;
			HiVolts.board.squares[row][col] = 3;
			super.moveDownLeft();
		}
	}

	public void moveDownRight() {
		if (check(col+1, row+1)) {
			HiVolts.board.squares[row][col] = 0;
			col = col + 1;
			row = row + 1;
			HiVolts.board.squares[row][col] = 3;
			super.moveDownRight();
		}
	}

}
