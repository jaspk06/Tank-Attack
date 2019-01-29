import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * <h1>Tank Object</h1>
 * <p>
 * The Tank class contains the properties and methods that the object Tank can
 * perform
 * </p>
 * 
 * @author Our Group
 *
 */

public class Tank {
	// Properties
	/**
	 * The angle at which the tank is rotated
	 */
	int angle;
	/**
	 * This variable is the x position of the tank
	 */
	double x;
	/**
	 * This variable is the y position of the tank
	 */
	double y;

	/**
	 * This variable is the health
	 */
	int health;
	
	int score = 0;

	/**
	 * Shape Variables
	 */
	Color color;
	Ellipse2D.Double circle;
	Ellipse2D.Double light1;
	Ellipse2D.Double light2;
	Ellipse2D.Double light3;
	Ellipse2D.Double light4;
	Rectangle2D.Double nozel;
	Rectangle2D.Double area;
	Ellipse2D.Double ship;

	// Methods
	/**
	 * moves the tank in the direction that it is facing
	 */
	public void moveForward() {
		if (this.angle == 90) {
			this.y += 2;
		} else if (this.angle == 270) {
			this.y -= 2;
		} else if (this.angle == 180) {
			this.x -= 2;
		} else if (this.angle >= 0 && this.angle < 90) {
			this.y += 2 * Math.sin(Math.toRadians(this.angle));
			this.x += 2 * Math.cos(Math.toRadians(this.angle));

		} else if (this.angle > 270 && this.angle < 360) {
			this.y += 2 * Math.sin(Math.toRadians(this.angle));
			this.x += 2 * Math.cos(Math.toRadians(this.angle));
		} else if (this.angle > 90 && this.angle < 180) {
			this.y += 2 * Math.sin(Math.toRadians(this.angle));
			this.x += 2 * Math.cos(Math.toRadians(this.angle));
		} else if (this.angle > 180 && this.angle < 270) {
			this.y += 2 * Math.sin(Math.toRadians(this.angle));
			this.x += 2 * Math.cos(Math.toRadians(this.angle));
		} else {
			this.y -= 2 * Math.sin(Math.toRadians(this.angle));
			this.x += 2 * Math.cos(Math.toRadians(this.angle));
		}
	}

	public void moveBackward() {
		if (this.angle == 90) {
			this.y -= 2;
		} else if (this.angle == 270) {
			this.y += 2;
		} else if (this.angle == 180) {
			this.x += 2;
		} else if (this.angle >= 0 && this.angle < 90) {
			this.y -= 2 * Math.sin(Math.toRadians(this.angle));
			this.x -= 2 * Math.cos(Math.toRadians(this.angle));

		} else if (this.angle > 270 && this.angle < 360) {
			this.y -= 2 * Math.sin(Math.toRadians(this.angle));
			this.x -= 2 * Math.cos(Math.toRadians(this.angle));
		} else if (this.angle > 90 && this.angle < 180) {
			this.y -= 2 * Math.sin(Math.toRadians(this.angle));
			this.x -= 2 * Math.cos(Math.toRadians(this.angle));
		} else if (this.angle > 180 && this.angle < 270) {
			this.y -= 2 * Math.sin(Math.toRadians(this.angle));
			this.x -= 2 * Math.cos(Math.toRadians(this.angle));
		} else {
			this.y += 2 * Math.sin(Math.toRadians(this.angle));
			this.x -= 2 * Math.cos(Math.toRadians(this.angle));
		}
		
	}

	public void rotateRight() {
		this.angle += 3;
		this.angle = restrictTheta(this.angle);
		
	}

	public void rotateLeft() {
		this.angle -= 3;
		this.angle = restrictTheta(this.angle);
		
	}

	public int restrictTheta(int angle) {
		if (angle < 360 && angle >= 0) {
			return angle;
		} else if (angle < 0) {
			angle = 360 + angle;
			return angle;
		} else {
			angle = 0;
			return angle;
		}

	}

	// Constructor
	public Tank(double x, double y, int angle, Color color) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.health = 100;
		this.color = color;

	}
}
