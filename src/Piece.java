import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Piece extends Rectangle {

	int col;
	int row;
	String pieceType;
	Boolean alive = true;

	public static int colToX(int col) {
		return (col+1) * HiVolts.OFFSET;
	}

	public static int rowToY(int row) {
		return (row+1) * HiVolts.OFFSET;
	}
	
	Piece(int col, int row, String type, Color color) {
		super(HiVolts.WIDTH, HiVolts.HEIGHT, color);

		this.col = col;
		this.row = row;
		this.pieceType = type;

		//where to place the rectangles on the screen
		setTranslateX(colToX(col));
		setTranslateY(rowToY(row));

	}



	public void moveLeft() {
		setTranslateX(colToX(col));
		
	}
}
