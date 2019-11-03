import java.net.URISyntaxException;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

class Mho extends Piece {
	
	public static int mhoNum = 0;
	int myNum;
	boolean hitFence;
	
	Mho(int row, int col) {
		super(row, col, "mho", Color.BLUEVIOLET);
		
		mhoNum++;
		myNum = mhoNum;
		
		Image img = null;
		try {
			img = new Image(getClass().getResource("/resources/venus.jpg").toURI().toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		setFill(new ImagePattern(img));
	}

	/*
	 * If you are directly vertical or hortizontal to a Mho, the Mho MUST move directly towards you one square. 
	 * 	If this results in the Mho landing on an electric fence, the Mho is removed from the board. If it lands on an empty square, 
	 * 		it remains on the board.
	 * 	If the Mho is not directly horizontal or vertical from you, then it has three squares it can move to, the one that is 
	 * 		diagonally towards you, one that is horizontal so that it would be closer to you, and one that is vertical so that it 
	 * 		would be closer to you. 
	 * 
	 * 			The Mho will try to move onto an empty square in the following order: 
	 * 				A- Diagonally towards you
	 * 				B- If the horizontal distance towards You is greater than or equal to the vertical distance, move horizontally towards You;
	 * 		   		If the horizontal distance towards You is less than or equal to the vertical distance, move vertically towards You;
	 * 
	 * 		If none of these can result in landing on an empty square, repeat the order with the attempt to move onto an electric fence
	 *      If none of these can result in landing on an electric fence, do not move (this happens when a Mho can only try to move onto other Mhos)
	 */
	public void move() {
		hitFence = true;
		
		System.out.println("mho " + myNum + ": " + col + " " + row + " avatar " + HiVolts.you.col + " " + HiVolts.you.row);

		// if the Mho is on the same row as the avatar it will move toward it
		if (HiVolts.you.row == row) {
			if (HiVolts.you.col > col) {
				if (moveRight()) {
					return;
				}
			} else {
				if (moveLeft()) {
					return;
				}
			}
		}

		// if the Mho is on the same column as the avatar it will move toward it
		if (HiVolts.you.col == col) {
			if (HiVolts.you.row > row) {
				if (moveDown()) {
					return;
				}
			} else {
				if (moveUp()) {
					return;
				}
			}
		}
		hitFence = false;
		if (moveCloser()) {
			return;
		} 
		hitFence = true;
		// did nto move so try to move again, allowing the mho to hit a fence
		moveCloser();
		
	}
	
	private boolean moveCloser() {
		// if the Mho is on the diagonal as the avatar it will move toward it
		if (Math.abs(HiVolts.you.col - col) == Math.abs(HiVolts.you.row - row)) {
			// top right diagonal
			if (HiVolts.you.row < row && HiVolts.you.col > col) {
				if (moveUpRight()) {
					return true;
				}
			} 

			// top left diagonal
			if (HiVolts.you.row < row && HiVolts.you.col < col) {
				if (moveUpLeft()) {
					return true;
				}
			}

			// bottom left diagonal
			if (HiVolts.you.row > row && HiVolts.you.col < col) {
				if (moveDownLeft()) {
					return true;
				}
			}

			// bottom right diagonal
			if (HiVolts.you.row > row && HiVolts.you.col > col) {
				if (moveDownRight()) {
					return true;
				}
			}
		}

		// if the Mho is not diagonal not horizontal or vertical to the avatar
		int hDist = HiVolts.you.col - col;
		int vDist = HiVolts.you.row - row;
		if (HiVolts.you.col == col || HiVolts.you.row == row) {
			System.out.print("equal");
		}
		
		if (Math.abs(vDist) > Math.abs(hDist)) { // avatar is closser in the horizontal plane
			if (hDist < 0 ) { // try to move left
				if (vDist < 0) {
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
			} else { // try to move right
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
				if (hDist < 0) {
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
			} else { // try to move down
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
		System.out.println("did not move" + col + " " + row); 
		return false;
	}

	
	/**
	 * 
	 * @param col
	 * @param row
	 * @return true if you should move
	 */
	Boolean check (int col, int row) {
		if (HiVolts.board.squares[row][col] == 1) {
			if (!hitFence) {
				return false;
			}
			alive = false;
			HiVolts.board.squares[this.row][this.col] = 0;
			mhoNum--;
			return true;
		}

		if (HiVolts.board.squares[row][col] == 2) {
			HiVolts.you.alive = false;
			HiVolts.death = "Killed by a Mho";
			return true;
		}

		if (HiVolts.board.squares[row][col] == 3) {
			System.out.println(myNum + " add to list");
//			HiVolts.didNotMove.add(this);
			return false;
		}

		return true;
	}

	public boolean moveRight() {
		if (check(col+1, row)) {
			HiVolts.board.squares[row][col] = 0;
			col = col + 1;
			HiVolts.board.squares[row][col] = 3;
			super.moveRight();
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
			row = row - 1;
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
