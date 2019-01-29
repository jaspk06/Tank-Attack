import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class menu implements ActionListener, FocusListener{
  // Properties
  JFrame theframe;
  menuPanel menu;
  APanel map;
  JLabel play;
  JLabel help;
  JLabel scores;
  JLabel exit;
  Timer thetimer;
  
  JButton servermode;
  JButton clientmode;
  JButton back;
  JButton connect;
  
  JTextField user;
  JTextField port;
  JTextField ip;
  SuperSocketMaster ssm;
  
  JLabel red;
  JLabel blue;
  
  JLabel map1;
  
  // Methods
  public void actionPerformed(ActionEvent evt){
    if(evt.getSource() == thetimer){
      //menu.repaint();
      //map.repaint();
    }else if(evt.getSource() == back){
      menu.removeAll();
      menu.blnPlay = false;
      menu.blnHelp = false;
      menu.blnScores = false;
      menu.add(play);
      menu.add(help);
      menu.add(scores);
      menu.add(exit);
      menu.play = Color.WHITE;
      menu.help = Color.WHITE;
      menu.scores = Color.WHITE;
      user.setText("Username");
      port.setText("Port Number");
      ip.setText("IP Address");
      menu.repaint();
      
    }else if(evt.getSource() == servermode){
      menu.add(connect);
      menu.add(user);
      menu.add(port);
      menu.add(back);
      menu.blnServer = true;
      menu.remove(servermode);
      menu.remove(clientmode);
      back.requestFocus();
      menu.repaint();
      
    }else if(evt.getSource() == clientmode){
      menu.add(connect);
      menu.add(user);
      menu.add(port);
      menu.add(ip);
      menu.add(back);
      menu.blnClient = true;
      menu.remove(servermode);
      menu.remove(clientmode);
      back.requestFocus();
      menu.repaint();
      
    }else if(evt.getSource() == connect){
      menu.blnConnect = true;
      if(menu.blnServer == true){
        menu.strUser = user.getText();
        menu.intPort = Integer.parseInt(port.getText());
        ssm = new SuperSocketMaster(menu.intPort, this);
        ssm.connect();
        menu.strIP = ssm.getMyAddress();
      }else if(menu.blnClient == true){
        menu.strUser = user.getText();
        menu.intPort = Integer.parseInt(port.getText());
        menu.strIP = ip.getText();
        
        ssm = new SuperSocketMaster(menu.strIP, menu.intPort, this);
        ssm.connect();
        ssm.sendText("connected");
        menu.bln2p = true;
        menu.add(red);
        menu.add(blue); 
      }
      menu.removeAll();
      menu.blnTSelect = true;
      menu.repaint();
    }else if(evt.getSource() == ssm){
      String strText = ssm.readText();
      if(strText.equals("connected")){
        menu.bln2p = true;
        menu.add(red);
        menu.add(blue); 
      }else if(strText.equals("map1")){
        theframe.setContentPane(map);
        theframe.revalidate();
        map.repaint();
      }
      System.out.println(strText);
    }
  }
  //Focus
  public void focusLost(FocusEvent e){
    
  }
  public void focusGained(FocusEvent e){
    if(e.getSource() == user){
      String strData;
      strData = user.getText();
      if(strData.equals("Username")){
        user.setText("");
      }
    }else if(e.getSource() == port){
      String strData;
      strData = port.getText();
      if(strData.equals("Port Number")){
        port.setText("");
      }
    }else if(e.getSource() == ip){
      String strData;
      strData = ip.getText();
      if(strData.equals("IP Address")){
        ip.setText("");
      }
    }
  }
  
  // Play
  MouseListener m1 = new MouseListener(){
    @Override
    public void mouseReleased(MouseEvent e){
      
    }
    
    @Override
    public void mousePressed(MouseEvent e){
      
    }
    
    @Override
    public void mouseExited(MouseEvent e){
      menu.play = Color.WHITE;
      menu.repaint();
    }
    
    @Override
    public void mouseEntered(MouseEvent e){
      menu.play = Color.YELLOW;
      menu.repaint();
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
      menu.blnPlay = true;
      menu.blnHelp = false;
      menu.blnScores = false;
      menu.removeAll();
      
      menu.add(servermode);
      menu.add(clientmode);
      menu.add(back);
      menu.repaint();
    }
  };
  
  // Help
  MouseListener m2 = new MouseListener(){
    @Override
    public void mouseReleased(MouseEvent e){
      
    }
    
    @Override
    public void mousePressed(MouseEvent e){
      menu.blnHelp = true;
      menu.blnPlay = false;
      menu.blnScores = false;
      menu.removeAll();
      menu.add(back);
      menu.repaint();
    }
    
    @Override
    public void mouseExited(MouseEvent e){
      menu.help = Color.WHITE;
      menu.repaint();
    }
    
    @Override
    public void mouseEntered(MouseEvent e){
      menu.help = Color.YELLOW;
      menu.repaint();
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
      
    }
  };
  
  // Scores
  MouseListener m3 = new MouseListener(){
    @Override
    public void mouseReleased(MouseEvent e){
      
    }
    
    @Override
    public void mousePressed(MouseEvent e){
      menu.blnScores = true;
      menu.blnPlay = false;
      menu.blnHelp = false;
      menu.removeAll();
      menu.add(back);
      menu.repaint();
    }
    
    @Override
    public void mouseExited(MouseEvent e){
      menu.scores = Color.WHITE;
      menu.repaint();
    }
    
    @Override
    public void mouseEntered(MouseEvent e){
      menu.scores = Color.YELLOW;
      menu.repaint();
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
      
    }
  };
  
  // Exit
  MouseListener m4 = new MouseListener(){
    @Override
    public void mouseReleased(MouseEvent e){
      
    }
    
    @Override
    public void mousePressed(MouseEvent e){
      
    }
    
    @Override
    public void mouseExited(MouseEvent e){
      menu.exit = Color.WHITE;
      menu.repaint();
    }
    
    @Override
    public void mouseEntered(MouseEvent e){
      menu.exit = Color.YELLOW;
      menu.repaint();
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
      System.exit(1);
    }
  };
  
  // Red
  MouseListener m5 = new MouseListener(){
    @Override
    public void mouseReleased(MouseEvent e){
      
    }
    
    @Override
    public void mousePressed(MouseEvent e){
      menu.blnConnect = false;
      menu.blnTSelect = false;
      menu.blnMSelect = true;
      menu.blnRed = false;
      menu.removeAll();
      menu.add(map1);
      menu.strUFO = "Red";
      menu.repaint();
    }
    
    @Override
    public void mouseExited(MouseEvent e){
      menu.blnRed = false;
      menu.repaint();
    }
    
    @Override
    public void mouseEntered(MouseEvent e){
      menu.blnRed = true;
      menu.repaint();
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
      
    }
  };
  
  // Blue
  MouseListener m6 = new MouseListener(){
    @Override
    public void mouseReleased(MouseEvent e){
      
    }
    
    @Override
    public void mousePressed(MouseEvent e){
      menu.blnConnect = false;
      menu.blnTSelect = false;
      menu.blnMSelect = true;
      menu.removeAll();
      menu.add(map1);
      menu.strUFO = "Blue";
      menu.repaint();
    }
    
    @Override
    public void mouseExited(MouseEvent e){
      menu.blnBlue = false;
      menu.repaint();
    }
    
    @Override
    public void mouseEntered(MouseEvent e){
      menu.blnBlue = true;
      menu.repaint();
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
      
    }
  };
  
  // Map 1
  MouseListener m7 = new MouseListener(){
    @Override
    public void mouseReleased(MouseEvent e){
      
    }
    
    @Override
    public void mousePressed(MouseEvent e){
      theframe.setContentPane(map);
      theframe.revalidate();
      map.repaint();
      ssm.sendText("map1");
    }
    
    @Override
    public void mouseExited(MouseEvent e){
      menu.blnMap1 = false;
      menu.repaint();
    }
    
    @Override
    public void mouseEntered(MouseEvent e){
      menu.blnMap1 = true;
      menu.repaint();
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
      
    }
  };
  
  // Constructor  
  public menu(){
    menu = new menuPanel();
    menu.setPreferredSize(new Dimension(1280, 720));
    menu.setLayout(null);
    
    map = new APanel();
    map.setPreferredSize(new Dimension(1280, 720));
    map.setLayout(null);
    
    play = new JLabel("");
    play.setSize(500, 100);
    play.setLocation(400, 210);
    play.addMouseListener(m1);
    menu.add(play);
    
    help = new JLabel("");
    help.setSize(500, 100);
    help.setLocation(400, 310);
    help.addMouseListener(m2);
    menu.add(help);
    
    scores = new JLabel("");
    scores.setSize(500, 100);
    scores.setLocation(400, 410);
    scores.addMouseListener(m3);
    menu.add(scores);
    
    exit = new JLabel("");
    exit.setSize(500, 100);
    exit.setLocation(400, 510);
    exit.addMouseListener(m4);
    menu.add(exit);
    
    back = new JButton("Back");
    back.setSize(200, 100);
    back.setLocation(540, 600);
    back.addActionListener(this);
    
    servermode = new JButton("Server");
    servermode.setSize(200, 100);
    servermode.setLocation(340, 300);
    servermode.addActionListener(this);
    
    clientmode = new JButton("Client");
    clientmode.setSize(200, 100);
    clientmode.setLocation(740, 300);
    clientmode.addActionListener(this);
    
    user = new JTextField("Username");
    user.setSize(200, 50);
    user.setLocation(540, 200);
    user.addFocusListener(this);
    
    port = new JTextField("Port Number");
    port.setSize(200, 50);
    port.setLocation(540, 300);
    port.addFocusListener(this);
    
    ip = new JTextField("IP Address");
    ip.setSize(200, 50);
    ip.setLocation(540, 400);
    ip.addFocusListener(this);
    
    connect = new JButton("Connect");
    connect.setSize(200, 100);
    connect.setLocation(540, 50);
    connect.addActionListener(this);
    
    red = new JLabel("");
    red.setSize(640, 720);
    red.setLocation(0, 0);
    red.addMouseListener(m5);
    
    blue = new JLabel("");
    blue.setSize(640, 720);
    blue.setLocation(640, 0);
    blue.addMouseListener(m6);
    
    map1 = new JLabel("");
    map1.setSize(350, 720);
    map1.setLocation(0, 0);
    map1.addMouseListener(m7);
    
    theframe = new JFrame("Menu");
    theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    theframe.setContentPane(menu);
    theframe.pack();
    theframe.setVisible(true);
    
    thetimer = new Timer(1000/60, this);
    thetimer.start();
  }
  // Main Method
  public static void main(String[] args){
    /*try {
      UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }*/
    new menu();
  }
}