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
	
	Piece(int row, int col, String type, Color color) {
		super(HiVolts.WIDTH, HiVolts.HEIGHT, color);

		this.col = col;
		this.row = row;
		this.pieceType = type;

		//where to place the rectangles on the screen
		setX(colToX(col));
		setY(rowToY(row));

	}

	public boolean moveUpLeft() {
		slide(xProperty(), getX() - HiVolts.OFFSET, yProperty(), getY() - HiVolts.OFFSET);
		return true;
	}
	
	public boolean moveUpRight() {
		slide(xProperty(), getX() + HiVolts.OFFSET, yProperty(), getY() - HiVolts.OFFSET);
		return true;
	}
	
	public boolean moveLeft() {
		slide(xProperty(), getX() - HiVolts.OFFSET,  null, 0);
		return true;
	}
	
	public boolean moveRight() {
		slide(xProperty(), getX() + HiVolts.OFFSET,  null, 0);
		return true;
	}
	
	public boolean moveDownLeft() {
		slide(xProperty(), getX() - HiVolts.OFFSET, yProperty(), getY() + HiVolts.OFFSET);
		return true;
	}
	
	public boolean moveUp() {
		slide(null, 0, yProperty(), getY() - HiVolts.OFFSET);
		return true;
	}
	
	public boolean moveDown() {
		slide(null, 0, yProperty(), getY() + HiVolts.OFFSET);
		return true;
	}
	
	public boolean moveDownRight() {
		slide(xProperty(), getX() + HiVolts.OFFSET, yProperty(), getY() + HiVolts.OFFSET);
		return true;
	}

	public void moveJump() { 
		toFront();
		Timeline timeline  = new Timeline(); 
		KeyValue xKeyValue  = new KeyValue(xProperty(), colToX(col));
		KeyFrame xKeyFrame  = new KeyFrame(Duration.millis(300), xKeyValue);
		timeline.getKeyFrames().addAll(xKeyFrame);
		
		KeyValue yKeyValue  = new KeyValue(yProperty(), rowToY(row));
		KeyFrame yKeyFrame  = new KeyFrame(Duration.millis(300), yKeyValue);
		timeline.getKeyFrames().addAll(yKeyFrame);
		
		double increase = 1.5;
		
		KeyValue heightValue  = new KeyValue(heightProperty(), HiVolts.HEIGHT * increase);
		KeyFrame heightKeyFrame  = new KeyFrame(Duration.millis(150), heightValue);
		timeline.getKeyFrames().addAll(heightKeyFrame);
		
		//transform back to original height
		heightValue  = new KeyValue(heightProperty(),HiVolts.HEIGHT);
		heightKeyFrame  = new KeyFrame(Duration.millis(300), heightValue);
		timeline.getKeyFrames().addAll(heightKeyFrame);
		
		KeyValue widthValue  = new KeyValue(widthProperty(), HiVolts.WIDTH * increase);
		KeyFrame widthKeyFrame  = new KeyFrame(Duration.millis(150), widthValue);
		timeline.getKeyFrames().addAll(widthKeyFrame);
		
		//transform back to original width
		widthValue  = new KeyValue(widthProperty(), HiVolts.WIDTH);
		widthKeyFrame  = new KeyFrame(Duration.millis(300), widthValue);
		timeline.getKeyFrames().addAll(widthKeyFrame);
		
		timeline.play();
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
