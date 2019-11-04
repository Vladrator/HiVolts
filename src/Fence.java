import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

// Presley

class Fence extends Rectangle {

	Fence(int row, int col) {
		
		// sets up the fences dimensions and properties through HiVolts
		super(HiVolts.WIDTH, HiVolts.HEIGHT, Color.BLACK);
		
		// where to place the fences on the screen
		setTranslateX(Piece.colToX(col));
	    setTranslateY(Piece.rowToY(row));
	}
	
}
