import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;

// Aaron Yuan

// controls all fx animations
public class Piece extends Rectangle {

	int col; // x value
	int row; // y value
	String pieceType; // whether mho or avatar (type of object)
	Boolean alive = true;

	// map a column to an x coordinate
	public static int colToX(int col) {
		return (col+1) * HiVolts.OFFSET;
	}

	// map a column to a y coordinate
	public static int rowToY(int row) {
		return (row+1) * HiVolts.OFFSET;
	}

	/**
	 * 
	 * @param row // y value
	 * @param col // x value
	 * @param type // avatar or mho
	 */
	Piece(int row, int col, String type) {
		super(HiVolts.WIDTH, HiVolts.HEIGHT);

		this.col = col;
		this.row = row;
		this.pieceType = type;

		// location to place the rectangles on the screen
		setX(colToX(col));
		setY(rowToY(row));

	}

	// functions that take in values from avatar or mho and then calls slide to actually move them
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

	/**
	 * animates the jumps for the avatar
	 */
	public void moveJump() { 
		int animationDuration = 400;

		toFront(); // moves the avatar to the front incase that when it jumps it goes past
		// an object and we want it to go over it not under it as it moves 

		// sets the column where the avatar moves to and the time it takes to get there
		Timeline timeline  = new Timeline(); // sets up a timeline to record events
		KeyValue xKeyValue  = new KeyValue(xProperty(), colToX(col));
		KeyFrame xKeyFrame  = new KeyFrame(Duration.millis(animationDuration), xKeyValue);
		timeline.getKeyFrames().addAll(xKeyFrame);

		// sets the row where the avatar moves to and the time it takes to get there
		KeyValue yKeyValue  = new KeyValue(yProperty(), rowToY(row));
		KeyFrame yKeyFrame  = new KeyFrame(Duration.millis(animationDuration), yKeyValue);
		timeline.getKeyFrames().addAll(yKeyFrame);

		double increase = 2.5; // how much the avatar increases as it jumps

		// transform to increased height half way through jump
		KeyValue heightValue  = new KeyValue(heightProperty(), HiVolts.HEIGHT * increase);
		KeyFrame heightKeyFrame  = new KeyFrame(Duration.millis(animationDuration/2), heightValue);
		timeline.getKeyFrames().addAll(heightKeyFrame);

		// transform back to original height at the end of the jump
		heightValue  = new KeyValue(heightProperty(),HiVolts.HEIGHT);
		heightKeyFrame  = new KeyFrame(Duration.millis(animationDuration), heightValue);
		timeline.getKeyFrames().addAll(heightKeyFrame);
		
		setRotate(0);
		// rotate avatar by 180 degrees half way through jump
		KeyValue rotateValue  = new KeyValue(rotateProperty(), 180);
		KeyFrame rotateKeyFrame  = new KeyFrame(Duration.millis(3 * animationDuration/4), rotateValue);
		timeline.getKeyFrames().addAll(rotateKeyFrame);

		// rotate another 180 degrees by the end of the jump
		rotateValue  = new KeyValue(rotateProperty(), 360);
		rotateKeyFrame  = new KeyFrame(Duration.millis(animationDuration), rotateValue);
		timeline.getKeyFrames().addAll(rotateKeyFrame);

		// transform to increased width half way through jump
		KeyValue widthValue  = new KeyValue(widthProperty(), HiVolts.WIDTH * increase);
		KeyFrame widthKeyFrame  = new KeyFrame(Duration.millis(1 * animationDuration/4), widthValue);
		timeline.getKeyFrames().addAll(widthKeyFrame);

		// transform back to original width at the end of the jump
		widthValue  = new KeyValue(widthProperty(), HiVolts.WIDTH);
		widthKeyFrame  = new KeyFrame(Duration.millis(animationDuration), widthValue);
		timeline.getKeyFrames().addAll(widthKeyFrame);

		timeline.play(); // plays created timeline
	}

	// 
	/**
	 * takes in values from the move functions and uses them to slide the avatar
	 * 
	 * @param xTarget: property for the x value of the mho or avatar you are going to slide
	 * @param newX: final x value
	 * @param yTarget: property for the y value of the mho or avatar you are going to slide
	 * @param newY: final y value
	 */
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

		timeline.play(); // plays created timeline
	}

}
