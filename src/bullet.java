import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class bullet extends Tank {
	/*
	 * Properties
	 */

	/*
	 * Methods
	 */
	public void hit(String strObject) {
		if(strObject == "Tank") {
			//tank.getHit();
		} else {
			//bullet.die();
		}
	}	
	
	
	/*
	 * Constructor
	 */
	public bullet(double x, double y, int angle, Color color) {
		super(x, y, angle, color);
		this.x = x;
		this.y = y;
		this.angle = angle;
		//img = new Ellipse2D.Double(x, y, 3, 1);
	}
}
