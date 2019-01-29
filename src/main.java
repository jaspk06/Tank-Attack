import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.Timer;

/**
 * <h1>UFO Game</h1>
 * <p>
 * This is a multiplayer third person shooter. Where players are able to control
 * their UFO and shoot other players
 * </p>
 * 
 * @author Damian, Jason, and Isaac.
 *
 */

public class main implements KeyListener, ActionListener, FocusListener {

	/*
	 * Java GUI Variables
	 */
	APanel panel;
	JFrame frame;
	Timer timer;
	JButton map1;
	JButton map2;
	JButton server;
	JButton client;
	JTextArea text;
	JLabel t1Health;
	JLabel t2Health;
	JLabel t1Score;
	JLabel t2Score;
	SuperSocketMaster ssm;
	JTextField send;
	JScrollPane pane;
	JScrollBar vertical;

	/*
	 * Movement Variables
	 */
	boolean blnA;
	boolean blnD;
	boolean blnW;
	boolean blnS;
	boolean blnA2;
	boolean blnD2;
	boolean blnW2;
	boolean blnS2;
	String strW = "";
	String strA = "";
	String strS = "";
	String strD = "";
	boolean blnMove = true;
	boolean blnBack = true;
	boolean blnForwardMoved;
	String strMove;

	/*
	 * Map Variables
	 */
	int intRow = 0;
	int intColumn = 0;
	static boolean blnDrawMap = false;
	Rectangle2D.Double map[][];
	static String strMapName = "null";
	String strMap[][];

	boolean blnLeft = false;
	boolean blnTop = false;
	boolean blnRight = false;
	boolean blnBottom = false;
	boolean blnMapChosen = false;
	boolean blnMapLoaded = false;
	String strReceive;

	String strSend;
	String strMapRead = "read";

	/*
	 * Tank Variables
	 */
	static Tank tank;
	static Tank tank2;
	Rectangle2D.Double cTank;
	Rectangle2D.Double cTank2;
	boolean blnserver;

	/*
	 * Bullet Variables
	 */
	ArrayList<bullet> al = new ArrayList<bullet>();

	/*
	 * connection variables
	 */
	boolean blnConnected = false;
	String strSplit[];
	boolean blnSendText;
	int intCount;
	String strUser;
	int intDum = 0;

	/*
	 *
	 * ========= | METHODS | =========
	 */

	/**
	 * Action Performed Method
	 */

	public void actionPerformed(ActionEvent evt) {
		/*
		 * sending text
		 */
		if (blnSendText == false) {
			frame.setFocusable(true);
			frame.requestFocus();
			send.setFocusable(false);
			vertical = pane.getVerticalScrollBar();
			vertical.setValue( vertical.getMaximum() );
		}else {
			send.setFocusable(true);
			send.setVisible(true);
			send.requestFocus();
		}
		if (evt.getSource() == send) {
			if (!send.getText().equals("")) {
			ssm.sendText(strUser + ": " + send.getText());
			text.append("\n" + strUser + ": " + send.getText());
			}
			send.setText("");
			blnSendText=false;
			frame.setFocusable(true);
			frame.requestFocus();
			send.setFocusable(false);
			send.setText("Press 't' to chat");
			// System.out.print(send.getText());
			vertical = pane.getVerticalScrollBar();
			vertical.setValue( vertical.getMaximum() );
		}
		/*
		 * Timer Statement
		 */
		if (evt.getSource() == timer) {
			panel.passTank(tank);
			panel.passTank2(tank2);
			panel.repaint();
			t1Health.setText(("Player 1 Health: "+tank.health));
			t2Health.setText(("Player 2 Health: "+tank2.health));
			t1Score.setText("Player 1 Score: "+tank.score);
			t2Score.setText("Player 2 Score: "+tank2.score);
			
			if(tank.health <= 0) {
				reset();
				tank2.score +=1;
			}
			if(tank2.health <= 0) {
				reset();
				tank.score += 1;
			}
			
			/*
			 * Bullet
			 */
			for (int intCount = 0; intCount < al.size(); intCount++) {
				bullet b = al.get(intCount);

				if (checkCollision(b.x, b.y, b.angle, true, "bullet", b.color) == true) {
					al.remove(b);
				} else {
					b.moveForward();
					b.moveForward();
				}
			}
			if (blnMapLoaded == true) {
				if (blnserver == true) {
					ssm.sendText((Double.toString(tank.x)) + "," + (Double.toString(tank.y)) + ","
							+ (Integer.toString(tank.angle))+", %");
				} else {
					ssm.sendText((Double.toString(tank2.x)) + "," + (Double.toString(tank2.y)) + ","
							+ (Integer.toString(tank2.angle))+", %");
				}

			}
		}

		/*
		 * SSM Stuff
		 */
		if (blnConnected == true) {
			strReceive = ssm.readText();
			if (strReceive == null) {
				strReceive = "no";
			}
			if (blnserver == false && !strReceive.equals("no") && blnMapChosen == false) {
				strMapName = strReceive;
				blnDrawMap = true;
				loadMap();
				blnMapChosen = true;
			}
			if (strReceive.indexOf("%") != -1 && blnserver == true) {
				strSplit = strReceive.split(",");
				tank2.x = Double.parseDouble(strSplit[0]);
				tank2.y = Double.parseDouble(strSplit[1]);
				tank2.angle = Integer.parseInt(strSplit[2]);
			}
			
			if (strReceive.indexOf("%") != -1 && blnserver == false) {
				strSplit = strReceive.split(",");
				tank.x = Double.parseDouble(strSplit[0]);
				tank.y = Double.parseDouble(strSplit[1]);
				tank.angle = Integer.parseInt(strSplit[2]);
			}
			else if (strReceive.indexOf(",") != -1 && blnserver == true) {
				strSplit = strReceive.split(",");
				Double dblX = Double.parseDouble(strSplit[0]);
				Double dblY = Double.parseDouble(strSplit[1]);
				int intAngle = Integer.parseInt(strSplit[2]);
				if (strSplit[3].equals("BLUE")) {
					bullet b = new bullet(dblX, dblY, intAngle, Color.BLUE);
					al.add(b);
					panel.passBullet(al);

				}
			}
			else if (strReceive.indexOf(",") != -1 &&  blnserver == false) {
				strSplit = strReceive.split(",");
				Double dblX = Double.parseDouble(strSplit[0]);
				Double dblY = Double.parseDouble(strSplit[1]);
				int intAngle = Integer.parseInt(strSplit[2]);
				if (strSplit[3].equals("RED")) {
					bullet b = new bullet(dblX, dblY, intAngle, Color.RED);
					al.add(b);
					panel.passBullet(al);

				}
			}
			
			
		}
		

		/*
		 * Button Event to choose Map
		 */
		if (evt.getSource() == map1) {
			map1.setVisible(false);
			map2.setVisible(false);
			if (blnMapChosen == false) {
				strMapName = "map.csv";
				ssm.sendText(strMapName);
				blnMapChosen = true;
			}
			blnDrawMap = true;
			loadMap();

		} else if (evt.getSource() == map2) {
			map2.setVisible(false);
			map1.setVisible(false);
			if (blnMapChosen == false) {
				strMapName = "map2.csv";
				ssm.sendText(strMapName);
				blnMapChosen = true;
			}
			blnDrawMap = true;
			loadMap();
		}
		if (evt.getSource() == server) {
			ssm = new SuperSocketMaster(1337, this);
			ssm.connect();
			strUser = "Player 1";
			server.setVisible(false);
			client.setVisible(false);
			map1.setVisible(true);
			map2.setVisible(true);
			blnserver = true;
			blnConnected = true;
		} else if (evt.getSource() == client) {
			/*
			 * Choosing Local 127.0.0.1
			 */
			ssm = new SuperSocketMaster("127.0.0.1", 1337, this);
			ssm.connect();
			strUser = "Player 2";
			server.setVisible(false);
			client.setVisible(false);
			blnserver = false;
			blnDrawMap = false;
			blnConnected = true;
			loadMap();
		}

		/*
		 * Movement/Rotation if Statements
		 */
		if (blnserver == true) {
			if (blnW == true) {
				blnForwardMoved = true;
				if (checkCollision(tank.x, tank.y, tank.angle, blnForwardMoved, "tank", tank.color) == false) {
					tank.moveForward();
				
					
				}
			}
			if (blnS == true) {
				blnForwardMoved = false;
				if (checkCollision(tank.x, tank.y, tank.angle, blnForwardMoved, "tank", tank.color) == false) {
					tank.moveBackward();
				
				}
			}
			if (blnA == true) {
				tank.rotateLeft();
		
			}
			if (blnD == true) {
				tank.rotateRight();
		
			}
			
		}
		if (blnserver == false) {
			if (strW.equalsIgnoreCase("w")) {
				blnForwardMoved = true;
				if (checkCollision(tank2.x, tank2.y, tank2.angle, blnForwardMoved, "tank", tank2.color) == false) {
					tank2.moveForward();
	
				}
			}
			if (strS.equalsIgnoreCase("s")) {
				blnForwardMoved = false;
				if (checkCollision(tank2.x, tank2.y, tank2.angle, blnForwardMoved, "tank", tank2.color) == false) {
					tank2.moveBackward();
		
				}
			}
			if (strA.equalsIgnoreCase("a")) {
				tank2.rotateLeft();
	

			}
			if (strD.equalsIgnoreCase("d")) {
				tank2.rotateRight();
	
				
			}
			
		}
		
	}
	public void reset() {
		tank2.health = 100;
		tank2.x = 820;
		tank2.y = 100;
		tank2.angle = 0;
		
		tank.health = 100;
		tank.x = 50;
		tank.y = 100;
		tank.angle = 0;
	}
	public void focusLost(FocusEvent evt) {

	}

	public void focusGained(FocusEvent evt) {
		if (evt.getSource() == send) {
			String strData;
			strData = send.getText();
			if (strData.equals("Press 't' to chat")) {
				send.setText("");
			}
		}
	}

	public boolean checkCollision(double tx, double ty, int intAngle, boolean blnForward, String strObject,
			Color color) {
		/*
		 * Testing if Tank hits Map Walls
		 */
		if (blnForward == true) {
			if (intAngle == 90) {
				ty += 2;
			} else if (intAngle == 270) {
				ty -= 2;
			} else if (intAngle == 180) {
				tx -= 2;
			} else if (intAngle >= 0 && intAngle < 90) {
				ty += 2 * Math.sin(Math.toRadians(intAngle));
				tx += 2 * Math.cos(Math.toRadians(intAngle));

			} else if (intAngle > 270 && intAngle < 360) {
				ty += 2 * Math.sin(Math.toRadians(intAngle));
				tx += 2 * Math.cos(Math.toRadians(intAngle));
			} else if (intAngle > 90 && intAngle < 180) {
				ty += 2 * Math.sin(Math.toRadians(intAngle));
				tx += 2 * Math.cos(Math.toRadians(intAngle));
			} else if (intAngle > 180 && intAngle < 270) {
				ty += 2 * Math.sin(Math.toRadians(intAngle));
				tx += 2 * Math.cos(Math.toRadians(intAngle));
			} else {
				ty -= 2 * Math.sin(Math.toRadians(intAngle));
				tx += 2 * Math.cos(Math.toRadians(intAngle));
			}
		} else {
			if (intAngle == 90) {
				ty -= 2;
			} else if (intAngle == 270) {
				ty += 2;
			} else if (intAngle == 180) {
				tx += 2;
			} else if (intAngle >= 0 && intAngle < 90) {
				ty -= 2 * Math.sin(Math.toRadians(intAngle));
				tx -= 2 * Math.cos(Math.toRadians(intAngle));

			} else if (intAngle > 270 && intAngle < 360) {
				ty -= 2 * Math.sin(Math.toRadians(intAngle));
				tx -= 2 * Math.cos(Math.toRadians(intAngle));
			} else if (intAngle > 90 && intAngle < 180) {
				ty -= 2 * Math.sin(Math.toRadians(intAngle));
				tx -= 2 * Math.cos(Math.toRadians(intAngle));
			} else if (intAngle > 180 && intAngle < 270) {
				ty -= 2 * Math.sin(Math.toRadians(intAngle));
				tx -= 2 * Math.cos(Math.toRadians(intAngle));
			} else {
				ty += 2 * Math.sin(Math.toRadians(intAngle));
				tx -= 2 * Math.cos(Math.toRadians(intAngle));
			}
		}
		Ellipse2D.Double circle;
		if (strObject.equals("tank")) {
			circle = new Ellipse2D.Double(tx, ty, 60, 60);
			if (circle.intersects(tank2.area) == true && color == Color.RED) {
				return true;
			} else if (circle.intersects(tank.area) == true && color == Color.BLUE) {
				return true;
			}
		} else {
			circle = new Ellipse2D.Double(tx, ty, 10, 10);
			if (circle.intersects(tank2.area) == true && color == Color.RED) {
				tank2.health -= 10;
				return true;
			} else if (circle.intersects(tank.area) == true && color == Color.BLUE) {
				tank.health -= 10;
				return true;
			}
		}

		for (intRow = 0; intRow < 18; intRow++) {
			for (intColumn = 0; intColumn < 24; intColumn++) {
				try {
					try {
						if (strMap[intRow][intColumn].equals("wall")) {
							try {
								if (circle.intersects(map[intRow][intColumn]) == true) {
									return true;
								}
							} catch (ArrayIndexOutOfBoundsException e) {
								System.out.println("out of bounds 24");
							}
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("out of bounds 18");
					}
				} catch (NullPointerException e) {
					System.out.println("null pointer exception");
				}
			}
		}
		return false;
	}

	/**
	 * KeyPressed containing if statements for movement of Tanks
	 */
	public void keyPressed(KeyEvent evt) {
		if (evt.getKeyChar() == 't') {
			blnSendText = true;
		}
		if (blnserver == true) {
			if (evt.getKeyChar() == 'a') {
				blnA = true;
			}
			if (evt.getKeyChar() == 'd') {
				blnD = true;
			}
			if (evt.getKeyChar() == 'w') {
				blnW = true;
			}
			if (evt.getKeyChar() == 's') {
				blnS = true;

			}
			if (evt.getKeyChar() == 'f') {
				bullet b = new bullet(tank.x + 27.5, tank.y + 30, tank.angle, tank.color);
				al.add(b);
				panel.passBullet(al);
				ssm.sendText((Double.toString(b.x)) + "," + (Double.toString(b.y)) + "," + (Integer.toString(b.angle))
						+ "," + "RED");
			}
		}
		if (blnserver == false) {
			if (evt.getKeyChar() == 'a') {
				strA = "a";

			}
			if (evt.getKeyChar() == 'd') {
				strD = "d";
			}
			if (evt.getKeyChar() == 'w') {
				strW = "w";
			}
			if (evt.getKeyChar() == 's') {
				strS = "s";
			}
			if (evt.getKeyChar() == 'f') {
				bullet b = new bullet(tank2.x + 27.5, tank2.y + 30, tank2.angle, tank2.color);
				al.add(b);
				panel.passBullet(al);
				ssm.sendText((Double.toString(b.x)) + "," + (Double.toString(b.y)) + "," + (Integer.toString(b.angle))
						+ "," + "BLUE");
			}
		}
	}

	/**
	 * Key Released. Once player releases the key, tank stops moving
	 */
	public void keyReleased(KeyEvent evt) {
		if (blnserver == true) {
			if (evt.getKeyChar() == 'a') {
				blnA = false;
			}
			if (evt.getKeyChar() == 'd') {
				blnD = false;
			}
			if (evt.getKeyChar() == 'w') {
				blnW = false;
			}
			if (evt.getKeyChar() == 's') {
				blnS = false;
			}
		}
		if (blnserver == false) {
			if (evt.getKeyChar() == 'a') {
				strA = "0";
			}
			if (evt.getKeyChar() == 'd') {
				strD = "0";
			}
			if (evt.getKeyChar() == 'w') {
				strW = "0";
			}
			if (evt.getKeyChar() == 's') {
				strS = "0";
			}
		}

	}

	public void keyTyped(KeyEvent e) {

	}

	// Constructor
	public main() {
		/*
		 * Tanks
		 */
		tank = new Tank(50, 100, 0, Color.RED);
		tank2 = new Tank(820, 100, 0, Color.BLUE);

		panel = new APanel();
		panel.setPreferredSize(new Dimension(1280, 720));
		panel.setLayout(null);
		
		/*
		 * In Game Chat
		 */
		text = new JTextArea("Game Chat");
		pane = new JScrollPane(text);
		pane.setSize(320, 520);
		pane.setLocation(960, 0);
		panel.add(pane);
		pane.setVisible(false);
		
		JScrollBar vertical;

		send = new JTextField("Press 't' to chat");
		send.setSize(320, 50);
		send.setLocation(960, 520);
		panel.add(send);
		send.setVisible(false);
		send.addActionListener(this);
		send.addFocusListener(this);
		
		/*
		 * JLabels
		 */
		t1Score = new JLabel("Player 1 Score: "+tank.score);
		t1Score.setSize(150,50);
		t1Score.setLocation(965,580);
		panel.add(t1Score);
		
		t2Score = new JLabel("Player 2 Score: "+tank2.score);
		t2Score.setSize(150,50);
		t2Score.setLocation(965,630);
		panel.add(t2Score);
		
		t1Health = new JLabel("Player 1 Health: "+tank.health);
		t1Health.setSize(200,50);
		t1Health.setLocation(1085, 580);
		panel.add(t1Health);
		
		t2Health = new JLabel("Player 2 Health: "+tank2.health);
		t2Health.setSize(200,50);
		t2Health.setLocation(1085, 630);
		panel.add(t2Health);
		
		
		
		

		/*
		 * Choosing map buttons
		 */
		map1 = new JButton("Map 1");
		map1.setSize(100, 50);
		map1.setLocation(590, 200);
		panel.add(map1);
		map1.addActionListener(this);

		map2 = new JButton("Map 2");
		map2.setSize(100, 50);
		map2.setLocation(590, 400);
		panel.add(map2);
		map2.addActionListener(this);

		map1.setVisible(false);
		map2.setVisible(false);
		
		/*
		 * Server and Client
		 */
		server = new JButton("Server");
		server.setSize(100, 50);
		server.setLocation(0, 0);
		panel.add(server);
		server.addActionListener(this);

		client = new JButton("Client");
		client.setSize(100, 50);
		client.setLocation(1180, 0);
		panel.add(client);
		client.addActionListener(this);

		map1.setFocusable(false);
		map2.setFocusable(false);
		server.setFocusable(false);
		client.setFocusable(false);
		text.setFocusable(false);
		send.setFocusable(false);
		
		/*
		 * String Map Array and Map array which is drawn
		 */
		map = new Rectangle2D.Double[18][24];
		strMap = new String[18][24];

		frame = new JFrame("SC1");
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(this);
		frame.pack();
		frame.setVisible(true);

		timer = new Timer(1000 / 60, this);
		timer.start();
	}

	public void loadMap() {
		if (blnDrawMap == true) {
			strMap = gameutil.loadMap(strMapName);
			for (intRow = 0; intRow < 18; intRow++) {
				for (intColumn = 0; intColumn < 24; intColumn++) {
					if (strMap[intRow][intColumn].equals("wall")) {
						map[intRow][intColumn] = new Rectangle2D.Double(intColumn * 40, intRow * 40, 40, 40);
					}
				}
			}
			pane.setVisible(true);
			send.setVisible(true);
			text.setVisible(true);
			blnMapLoaded = true;
		}
	}

	// Main Program
	public static void main(String[] args) {
		new main();
	}
}
