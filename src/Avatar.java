import javafx.scene.paint.Color;

class Avatar extends Piece {

	Avatar(int col, int row) {
		super(col, row, "you", Color.AQUA);
	}

	Boolean check (int col, int row) {
		if (HiVolts.board.squares[row][col] == 0) {
			return true;
		}
		alive = false;
		return false;
	}
	
	public void moveLeft() {
		if (check(col-1, row)) {
			col = col - 1;
			super.moveLeft();
		}
	}
}
