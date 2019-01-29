import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class APanel extends JPanel {
	// Properties
	double x = 0;
	double y = 0;
	int angle = 0;
	int intCount1;
	int intCount;
	String strMap[][];
	Tank cTank1;
	Tank cTank2;
	ArrayList<bullet> bullets = new ArrayList<bullet>();

	boolean blnBullet = false;
	// Methods
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 720);
		if (main.blnDrawMap == true) {
			drawTank(g, cTank1);
			drawTank(g, cTank2);
			if (blnBullet == true) {
				for(int intCount =0;intCount < bullets.size();intCount++) {
					drawBullet(g, bullets.get(intCount));
				}
			}
		
		}
		drawMap(g);
	}

	public void passTank(Tank tank) {
		cTank1 = tank;
	}

	public void passTank2(Tank tank) {
		cTank2 = tank;
	}

	public void passBullet(ArrayList al) {
		bullets = al;
		blnBullet = true;
	}
	

	public void drawMap(Graphics g) {
		if (main.blnDrawMap == true) {
			strMap = gameutil.loadMap(main.strMapName);
			for (intCount = 0; intCount < 18; intCount++) {
				for (intCount1 = 0; intCount1 < 24; intCount1++) {
					if (strMap[intCount][intCount1].equals("wall")) {
						g.setColor(Color.WHITE);
						g.fillRect(intCount1 * 40, intCount * 40, 40, 40);
					}
				}
			}
		}

	}

	public void drawBullet(Graphics g, bullet b) {
		b.ship = new Ellipse2D.Double(b.x, b.y, 10, 10);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(b.color);
		g2d.fill(b.ship);
		g2d.draw(b.ship);

	}

	public void drawTank(Graphics g, Tank cTank) {

		/*
		 * ============= | Tank 1 | =============
		 */
		AffineTransform at = new AffineTransform();

		cTank.ship = new Ellipse2D.Double(cTank.x, cTank.y, 60, 60);
		cTank.area = new Rectangle2D.Double(cTank.x + 5, cTank.y + 5, 50, 50);
		cTank.circle = new Ellipse2D.Double(cTank.ship.getCenterX() - 17.5, cTank.ship.getCenterY() - 17.5, 35, 35);
		cTank.nozel = new Rectangle2D.Double(cTank.ship.getCenterX(), cTank.ship.getCenterY() - 4.5, 35, 9);
		cTank.light1 = new Ellipse2D.Double(cTank.ship.getCenterX() - 27, cTank.ship.getCenterY() - 3, 5, 5);
		cTank.light2 = new Ellipse2D.Double(cTank.ship.getCenterX() + 21, cTank.ship.getCenterY() - 3, 5, 5);
		cTank.light3 = new Ellipse2D.Double(cTank.ship.getCenterX() - 3, cTank.ship.getCenterY() - 27, 5, 5);
		cTank.light4 = new Ellipse2D.Double(cTank.ship.getCenterX() - 3, cTank.ship.getCenterY() + 21, 5, 5);

		at.setToRotation(Math.toRadians(cTank.angle), cTank.nozel.getCenterX(), cTank.nozel.getCenterY());
		at.setToRotation(Math.toRadians(cTank.angle), cTank.ship.getCenterX(), cTank.ship.getCenterY());
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.GRAY);
		Shape ship2 = at.createTransformedShape(cTank.ship);
		g2d.fill(ship2);

		g2d.setColor(cTank.color);
		g2d.fill(cTank.circle);
		// g2d.fill(cTank.area);

		g2d.setColor(Color.darkGray);
		g2d.fill(cTank.light1);
		g2d.fill(cTank.light2);
		g2d.fill(cTank.light3);
		g2d.fill(cTank.light4);

		g2d.setColor(cTank.color);
		Shape nozel2 = at.createTransformedShape(cTank.nozel);
		g2d.fill(nozel2);

		// g2d.setColor(Color.BLACK);
		g2d.setColor(cTank.color);
		g2d.draw(nozel2);
		g2d.draw(cTank.circle);

		g2d.setColor(Color.DARK_GRAY);
		/*
		 * float[] fa = { 10, 10, 10 }; BasicStroke bs = new BasicStroke(5,
		 * BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10, fa, 10); g2d.setStroke(bs);
		 */
		g2d.draw(ship2);
		
		
	}

	// Constructors
	public APanel() {
		super();
	}
}
