import java.util.Random;

public class Board {
	public int ROWS;
	public int COLS;
	int FENCES;
	int MHOS;
	public int[][] squares;
	boolean test = true;
	int [][] testSquares = { {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
			{1, 0, 2, 0, 1, 1, 1, 0, 0, 1, 0, 1},
			{1, 0, 0, 0, 1, 3, 0, 0, 0, 1, 0, 1},
			{1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1},
			{1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
	};



	/*
	 * 0 = empty
	 * 1 = fence
	 * 2 = avitar
	 * 3 = mho
	 */
	public static void main(String[] args) {
		Board board = new Board(12, 12, 0, 0);
		board.printBoard();
	}

	public Board(int rows, int cols, int fences, int mhos) {
		ROWS = rows;
		COLS = cols;
		FENCES = fences;
		MHOS = mhos;

		squares = new int[ROWS][COLS];
		createFences();
		createMhos();
		placeAvitar();
		if (test) {
			for (int i = 0; i <= 11; i++) { 
				for (int x =0; x <= 11 ; x++) {
					squares[i][x] = testSquares[i][x];
				}
			}
		}
	}

	private void createFences() {
		setBorder();
		// now generate 20 random fence posts
		Random rn = new Random();
		for(int num = 0; num < FENCES; num ++) {
			int row=0;
			int col=0;
			boolean test = true;
			while(test == true) {
				col = rn.nextInt(COLS-2) + 1;
				row = rn.nextInt(ROWS-2) + 1;
				if(squares[row][col] == 0) {
					test = false;
					break;
				} 

			}
			squares[row][col] = 1;

		}
	}

	private void setBorder() {
		for(int row=0; row < ROWS; row++) {
			if (row == 0 || row == ROWS-1) {
				for(int col = 0; col < COLS; col++) {
					squares[row][col] = 1;
				}
			} else {
				squares[row][0] = 1;
				squares[row][COLS-1] = 1;
			}
		}
	}

	private void createMhos() {
		// now generate 20 random fence posts
		Random rn = new Random();
		for(int num = 0; num < MHOS; num ++) {
			int row=0;
			int col=0;
			boolean test = true;
			while(test == true) {
				col = rn.nextInt(COLS-2)+1;
				row = rn.nextInt(ROWS-2)+1;
				if(squares[row][col] == 0) {
					test = false;
					break;
				} else {
					break;
				}

			}
			squares[row][col] = 3;

		}

	}

	private void placeAvitar() {
		Random rn = new Random();
		for(int num = 0; num < 1; num ++) {
			int row=0;
			int col=0;
			boolean test = true;
			while(test == true) {
				col = rn.nextInt(COLS - 2)+1;
				row = rn.nextInt(ROWS - 2)+1;
				if(squares[row][col] == 0) {
					test = false;
					break;
				} else {
					break;
				}

			}
			squares[row][col] = 2;

		}

	}

	public void printBoard() {
		for(int row=0; row < ROWS; row++) {
			for(int col=0; col <COLS; col++) {
				if (squares[row][col] == 0) {
					System.out.print(".");
				} else {
					System.out.print(squares[row][col]);
				}
			}
			System.out.println();
		}
	}
}