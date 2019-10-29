import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.WritableValue;
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
		setX(colToX(col));
		setY(rowToY(row));

	}

	public void moveUpLeft() {
		slide(xProperty(), getX() - HiVolts.OFFSET, yProperty(), getY() - HiVolts.OFFSET);
	}
	
	public void moveUpRight() {
		slide(xProperty(), getX() + HiVolts.OFFSET, yProperty(), getY() - HiVolts.OFFSET);
	}
	
	public void moveLeft() {
		slide(xProperty(), getX() - HiVolts.OFFSET,  null, 0);
	}
	
	public void moveRight() {
		slide(xProperty(), getX() + HiVolts.OFFSET,  null, 0);
	}
	
	public void moveDownLeft() {
		slide(xProperty(), getX() - HiVolts.OFFSET, yProperty(), getY() + HiVolts.OFFSET);
	}
	
	public void moveUp() {
		slide(null, 0, yProperty(), getY() - HiVolts.OFFSET);
	}
	
	public void moveDown() {
		slide(null, 0, yProperty(), getY() + HiVolts.OFFSET);
	}
	
	public void moveDownRight() {
		slide(xProperty(), getX() + HiVolts.OFFSET, yProperty(), getY() + HiVolts.OFFSET);
	}

	public void moveJump() { 
		toFront();
		setX(colToX(col));
		setY(rowToY(row));
	}
	
	public void slide(DoubleProperty xTarget, double newX, DoubleProperty yTarget, double newY) {
		Timeline timeline  = new Timeline(); 
		
		if (yTarget != null) {
			KeyValue yKeyValue  = new KeyValue(yTarget, newY);
			KeyFrame yKeyFrame  = new KeyFrame(Duration.millis(300), yKeyValue);
			timeline.getKeyFrames().addAll(yKeyFrame); 
		}
		
		if (xTarget != null) {
			KeyValue xKeyValue  = new KeyValue(xTarget, newX);
			KeyFrame xKeyFrame  = new KeyFrame(Duration.millis(300), xKeyValue);
			timeline.getKeyFrames().addAll(xKeyFrame); 
		}

		timeline.play();
	}

}
