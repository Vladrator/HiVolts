import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

class Fence extends Rectangle {

	Fence(int col, int row) {
		super(HiVolts.WIDTH, HiVolts.HEIGHT, Color.BLACK);
		
		//where to place the rectangles on the screen
		setTranslateX(Piece.colToX(col));
	    setTranslateY(Piece.rowToY(row));
	}
	

}
