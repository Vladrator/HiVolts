import java.net.URISyntaxException;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

// Kian

class Mho extends Piece {
	
	public static int mhoNum = 0; // number of mhos
//	int myNum;
	boolean hitFence; 
	
	Mho(int row, int col) {
		super(row, col, "mho"); // has piece create mhos
		
		mhoNum++; // for each mho that is created the number increases
//		myNum = mhoNum;
		
		Image img = null; // prints snowtrooper image as mho
		try {
			img = new Image(getClass().getResource("/resources/Snowtrooper.png").toURI().toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setFill(new ImagePattern(img));
	}

	/* Directions: 
	 * 
	 * Phase 1:
	 * If you are directly vertical or horizontal to a mho, the mho MUST move directly towards you one square. 
	 * 	If this results in the mho landing on an electric fence, the mho is removed from the board. If it lands on an empty square, 
	 * 		it remains on the board.
	 * 
	 * Phase 2: 
	 * 	If the mho is not directly horizontal or vertical from you, then it has three squares it can move to, the one that is 
	 * 		diagonally towards you, one that is horizontal so that it would be closer to you, and one that is vertical so that it 
	 * 		would be closer to you. 
	 * 
	 * 			The mho will try to move onto an empty square in the following order: 
	 * 				A- Diagonally towards you
	 * 				B- If the horizontal distance towards You is greater than or equal to the vertical distance, move horizontally towards You;
	 * 		   		If the horizontal distance towards You is less than or equal to the vertical distance, move vertically towards You;
	 * 
	 * Phase 3: 
	 * 		If none of these can result in landing on an empty square, repeat the order with the attempt to move onto an electric fence
	 *      If none of these can result in landing on an electric fence, do not move (this happens when a Mho can only try to move onto other Mhos)
	 */
	
	public void move() {
		
//		System.out.println("mho " + myNum + ": " + col + " " + row + " avatar " + HiVolts.you.col + " " + HiVolts.you.row); // used for debugging

		// Phase 1:
		
		hitFence = true; // starts off that hitting the fence is allowed for veritcal and horizontal movements
		// if the mho is on the same row as the avatar it will move toward it horizontally
		if (HiVolts.you.row == row) {
			if (HiVolts.you.col > col) {
				if (moveRight()) { // move right?
					return;
				}
			} else {
				if (moveLeft()) { // move left
					return;
				}
			}
		}

		// if the mho is on the same column as the avatar it will move toward it vertically
		if (HiVolts.you.col == col) {
			if (HiVolts.you.row > row) {
				if (moveDown()) { // move down?
					return;
				}
			} else {
				if (moveUp()) { // move up
					return;
				}
			}
		}
		
		// Phase 2: 
		
		hitFence = false; // Hitting the fence is now no longer allowed as layed out in the rules above for phase 2
		if (moveCloser()) { // if not on the same row or column as the avatar it will try a different method
			return;
		}
		if (!alive) { // if dead
			return;
		}
		
		// Phase 3:
		
		hitFence = true; // as layed out in the rules above in phase 3, hitting the fence is now allowed
		// did not move so try to move again, allowing the mho to hit a fence
		moveCloser();
		
	}
	
	private boolean moveCloser() {
		
		// if the Mho is on the diagonal to the avatar it will move toward it
		if (Math.abs(HiVolts.you.col - col) == Math.abs(HiVolts.you.row - row)) {
			
			// up right diagonal
			if (HiVolts.you.row < row && HiVolts.you.col > col) {
				if (moveUpRight()) {
					return true;
				}
			} 

			// up left diagonal
			if (HiVolts.you.row < row && HiVolts.you.col < col) {
				if (moveUpLeft()) {
					return true;
				}
			}

			// down left diagonal
			if (HiVolts.you.row > row && HiVolts.you.col < col) {
				if (moveDownLeft()) {
					return true;
				}
			}

			// down right diagonal
			if (HiVolts.you.row > row && HiVolts.you.col > col) {
				if (moveDownRight()) {
					return true;
				}
			}
		}

		// if the Mho is not diagonal nor horizontal nor vertical to the avatar try to get close
		int hDist = HiVolts.you.col - col; // horizontal distance of mho to avatar
		int vDist = HiVolts.you.row - row; // vertical distance of mho to avatar
		
		if (Math.abs(vDist) > Math.abs(hDist)) { // avatar is closer in the horizontal plane
			if (hDist < 0 ) {
				if (vDist < 0) { // try to move left or near in any way
					if (moveUpLeft()) {
						return  true;
					}
					if (moveUp()) {
						return  true;
					}
				} else {
					if (moveDownLeft()) {
						return true;
					}
					if (moveDown()) {
						return true;
					}
				}
				if (moveLeft()) {
					return true;
				}
			} else { // try to move right or near in any way
				if (vDist < 0) {
					if (moveUpRight()) {
						return true;
					}
					if (moveUp()) {
						return  true;
					}
				} else {
					if (moveDownRight()) {
						return true;
					}
					if (moveDown()) {
						return true;
					}
				}
				if (moveRight()) {
					return true;
				}
			}
		} else { // avatar is closer in the vertical plane
			if (vDist < 0 ) { // if vertical distance < 0, means the avatar is above
				if (hDist < 0) { // try to move up or near in any way
					if (moveUpLeft()) {
						return true;
					}
					if (moveLeft()) {
						return true;
					}
				} else {
					if (moveUpRight()) {
						return true;
					}
					if (moveRight()) {
						return true;
					}
				}
				if (moveUp()) {
					return true;
				}
			} else { // try to move down or near in any way
				if (hDist < 0) {
					if (moveDownLeft()) {
						return true;
					}
					if (moveLeft()) {
						return true;
					}
				} else {
					if (moveDownRight()) {
						return true;
					}
					if (moveRight()) {
						return true;
					}
				}
				if (moveDown()) {
					return true;
				}
			}
		}
//		System.out.println("did not move" + col + " " + row); // used for debugging
		return false;
	}

	
	/**
	 * This initiates all the movements by moving the mhos location in the 2d array
	 * Then calls its super, Piece, that uses the new 2d array to display the new board
	 * This is the check function that is used by the move functions to determine if it
	 * can move to a certain square, and will also cause a pop up display to tell
	 * you that you died if a mho kills you
	 * 
	 * @param col
	 * @param row
	 * @return true
	 */
	Boolean check (int col, int row) {
		if (!alive) {
			return false;
		}
		if (HiVolts.board.squares[row][col] == 1) {
			if (!hitFence) {
				return false;
			}
			alive = false;
			HiVolts.board.squares[this.row][this.col] = 0;
			mhoNum--;
			return false;
		}

		if (HiVolts.board.squares[row][col] == 2) {
			HiVolts.you.alive = false;
			HiVolts.death = "The Dark Side killed you!!!";
			return true;
		}

		if (HiVolts.board.squares[row][col] == 3) {
			return false;
		}

		return true;
	}

	/**
	 * This initiates all the movements by moving the mhos location in the 2d array
	 * Then calls its super, Piece, that uses the new 2d array to display the new board
	 * it also calls the check function at that start to see if such a move is even 
	 * possible based on the rest of the board (2d array) and what phase the program 
	 * is in (see above)
	 */
	public boolean moveRight() {
		if (check(col+1, row)) { // checks if new position is correct based on 2d array (board) and phase (seed above)
			HiVolts.board.squares[row][col] = 0; // sets the current mho location in the 2d array to empty
			col = col + 1; // sets x variable to new x value
			HiVolts.board.squares[row][col] = 3; // sets the new mho location in the 2d array to 3 (mho)
			super.moveRight(); // with everything set it calls its super, piece, to initiate the in the display
			return true;
		}
		return false;
	}

	public boolean moveLeft() {
		if (check(col-1, row)) {
			HiVolts.board.squares[row][col] = 0;
			col = col - 1;
			HiVolts.board.squares[row][col] = 3;
			super.moveLeft();
			return true;
		}
		return false;
	}

	public boolean moveUp() {
		if (check(col, row-1)) {
			HiVolts.board.squares[row][col] = 0;
			row = row - 1;  // sets y variable to new x value
			HiVolts.board.squares[row][col] = 3;
			super.moveUp();
			return true;
		}
		return false;
	}

	public boolean moveDown() {
		if (check(col, row+1)) {
			HiVolts.board.squares[row][col] = 0;
			row = row + 1;
			HiVolts.board.squares[row][col] = 3;
			super.moveDown();
			return true;
		}
		return false;
	}

	public boolean moveUpRight() {
		if (check(col+1, row-1)) {
			HiVolts.board.squares[row][col] = 0;
			col = col + 1;
			row = row - 1;
			HiVolts.board.squares[row][col] = 3;
			super.moveUpRight();
			return true;
		}
		return false;
	}

	public boolean moveUpLeft() {
		if (check(col-1, row-1)) {
			HiVolts.board.squares[row][col] = 0;
			col = col - 1;
			row = row - 1;
			HiVolts.board.squares[row][col] = 3;
			super.moveUpLeft();
			return true;
		}
		return false;
	}

	public boolean moveDownLeft() {
		if (check(col-1, row+1)) {
			HiVolts.board.squares[row][col] = 0;
			col = col - 1;
			row = row + 1;
			HiVolts.board.squares[row][col] = 3;
			super.moveDownLeft();
			return true;
		}
		return false;
	}

	public boolean moveDownRight() {
		if (check(col+1, row+1)) {
			HiVolts.board.squares[row][col] = 0;
			col = col + 1;
			row = row + 1;
			HiVolts.board.squares[row][col] = 3;
			super.moveDownRight();
			return true;
		}
		return false;
	}

}
