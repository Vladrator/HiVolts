import java.util.Random;

// Presley Zhang

public class Board {
	
	// some global variables that will stake the same throughout the program
	public int ROWS;
	public int COLS;
	int FENCES;
	int MHOS;
	public int[][] squares; // creates the baord (2d array)
	boolean test = false; // true will use fake board, false wont
	
	// here one can create a fake board to test the game
	int [][] testSquares = { {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
			{1, 0, 0, 0, 3, 0, 0, 0, 0, 1, 0, 1},
			{1, 0, 0, 3, 1, 3, 0, 0, 0, 1, 0, 1},
			{1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1},
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
	public static void main(String[] args) { // creates a board in the console
		Board board = new Board(12, 12, 0, 0);
		board.printBoard(); // prints the board
	}

	/**
	 * sets up the board and determines if the test board is used or not
	 * 
	 * @param rows number of rows
	 * @param cols number of columns
	 * @param fences number of fences
	 * @param mhos number of mhos
	 */
	public Board(int rows, int cols, int fences, int mhos) { // runs from main
		ROWS = rows;
		COLS = cols;
		FENCES = fences;
		MHOS = mhos;

		squares = new int[ROWS][COLS]; // sets up the boards dimensions
		createFences();
		createMhos();
		placeAvitar();
		if (test) { // runs fake board instead of generating a new one
			for (int i = 0; i <= 11; i++) { 
				for (int x =0; x <= 11 ; x++) {
					squares[i][x] = testSquares[i][x]; // creates a new test board by using the one above
				}
			}
		}
	}

	/**
	 * prints all the fences randomly around the gridpane (only prints a specified number)
	 * prints each on an empty square
	 */
	private void createFences() { 
		setBorder();
		// now generate 20 random fence posts
		Random rn = new Random();
		for(int num = 0; num < FENCES; num ++) {
			int row=0;
			int col=0;
			boolean test = true;
			while(test == true) { // determines if the square is empty or not
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
	
	/**
	 * prints the fences that make the boarder of the screen (boarder size can be changed)
	 * (on an empty space)
	 */
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

	/**
	 * prints all the mhos randomly around the gridpane (only prints a specified number)
	 * (on an empty space)
	 */
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
				if(squares[row][col] == 0) { // determines if the square is empty or not
					test = false;
					break;
				} 
			}
			squares[row][col] = 3;

		}

	}

	/**
	 * prints the avatar randomly in the gridpane (on an empty space)
	 */
	private void placeAvitar() {
		Random rn = new Random();
		for(int num = 0; num < 1; num ++) {
			int row=0;
			int col=0;
			boolean test = true;
			while(test == true) { 
				col = rn.nextInt(COLS - 2)+1;
				row = rn.nextInt(ROWS - 2)+1;
				if(squares[row][col] == 0) { // determines if the square is empty or not
					test = false;
					break;
				}

			}
			squares[row][col] = 2;

		}

	}

/**
 * prints out a board that can be seen in the console that shows the location of everything
 * currently not being called
 */
	public void printBoard() {
		for(int row=0; row < ROWS; row++) {
			for(int col=0; col <COLS; col++) {
				if (squares[row][col] == 0) {
					System.out.print("."); // prints out a dot to represent an empty square
				} else {
					System.out.print(squares[row][col]);
				}
			}
			System.out.println();
			
		}
	}
	
	/**
	 * Example of a possible 12x12 board that is printed because of the function above: 
	 * (. = empty, 1 = fence, 2 = avatar, 3 = mho)
	 * 111111111111
	 * 1..1.......1
	 * 1..1......31
	 * 1...1.3....1
	 * 1..........1
	 * 1...1...1..1
	 * 1..3...2...1
	 * 1..........1
	 * 1..........1
	 * 1...3..1...1
	 * 11.......3.1
	 * 111111111111
	 */
}