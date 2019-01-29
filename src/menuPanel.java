import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileInputStream;

public class menuPanel extends JPanel{
  // Properties
  Color play = Color.WHITE;
  Color help = Color.WHITE;
  Color scores = Color.WHITE;
  Color exit = Color.WHITE;
  boolean blnPlay = false;
  boolean blnHelp = false;
  boolean blnScores = false;
  
  boolean blnServer = false;
  boolean blnClient = false;
  boolean blnConnect = false;
  String strUser;
  int intPort;
  String strIP;
  
  boolean bln2p = false;
  boolean blnTSelect = false;
  boolean blnMSelect = false;
  boolean blnRed = false;
  boolean blnBlue = false;
  String strUFO;
  
  boolean blnMap1 = false;
  
  // Methods
  public void paintComponent(Graphics g){
    //Images
    BufferedImage helps = null;
    BufferedImage red = null;
    BufferedImage blue = null;
    BufferedImage hred = null;
    BufferedImage hblue = null;
    BufferedImage map1 = null;
    BufferedImage hmap1 = null;
    BufferedImage stars = null;
    
    int width = 600;    
    int height = 400; 

    try{
      stars = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
      stars = ImageIO.read(new File("stars.png"));
      helps = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
      helps = ImageIO.read(new File("how to play.png"));
      red = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      red = ImageIO.read(new File("Red UFO.png"));
      blue = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      blue = ImageIO.read(new File("Blue UFO.png"));
      hred = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      hred = ImageIO.read(new File("HRed UFO.png"));
      hblue = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      hblue = ImageIO.read(new File("HBlue UFO.png"));
      map1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      map1 = ImageIO.read(new File("map1.png"));
      hmap1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      hmap1 = ImageIO.read(new File("hmap1.png"));
    }catch(IOException e){
      
    }

    //g.setColor(Color.BLACK);
    //g.fillRect(0, 0, 1280, 720);
    g.drawImage(stars, 0, 0, 1280, 720, null);
    g.setFont(new Font("Permanent Marker", Font.BOLD, 100));
    g.setColor(Color.RED);
    g.drawString("GALACTIC", 150, 200);
    g.setColor(Color.BLUE);
    g.drawString("        BATTLE", 400, 200);
    g.setColor(play);
    g.drawString("PLAY", 500, 300);
    g.setColor(help);
    g.drawString("HELP", 500, 400);
    g.setColor(scores);
    g.drawString("SCORES", 450, 500);
    g.setColor(exit);
    g.drawString("EXIT", 500, 600);
    
    g.drawImage(red, -100, 300, width, height, null);
    g.drawImage(blue, 780, 300, width, height, null);
    
    if(blnPlay == true){
      g.drawImage(stars, 0, 0, 1280, 720, null);
//      g.setColor(Color.BLACK);
//      g.fillRect(0, 0, 1280, 720);
    }
    
    if(blnHelp == true){
      g.drawImage(helps, 0, 0, 1280, 720, null);
    }
    
    if(blnScores == true){
      g.drawImage(stars, 0, 0, 1280, 720, null);
//      g.setColor(Color.BLACK);
//      g.fillRect(0, 0, 1280, 720);
      g.setFont(new Font("Comic Sans MS", Font.BOLD, 100));
      g.setColor(Color.WHITE);
      g.drawString("SCORES", 400, 100);
      g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
      try{
        FileReader thefile;
        BufferedReader thefiledata;
        thefile = new FileReader("Scores.txt");
        thefiledata = new BufferedReader(thefile);
        int intRow;
        int intCol;
        String strLine;
        String strSplit[];
        String strScores[][] = new String[3][2];
        for(intRow = 0; intRow < 3; intRow++){
          strLine = thefiledata.readLine();
          strSplit = strLine.split(",");
          for(intCol = 0; intCol < 2; intCol++){
            strScores[intRow][intCol] = strSplit[intCol];
            g.drawString(strScores[intRow][intCol], 0+(500*intCol), 150+(100*intRow));
          }
        }
        thefiledata.close();
        thefile.close();
      }catch(IOException e){
        System.out.println("Error reading file Scores.csv");
      }
    }
    
    if(blnServer == true && blnConnect == true){
      g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
      g.setColor(Color.WHITE);
      g.drawString("Waiting for players to connect", 250, 50);
      g.drawString("Username: "+strUser+" Port: "+intPort+" IP: "+strIP, 0, 670);
    }
    
    if(blnClient == true && blnConnect == true){
      g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
      g.setColor(Color.WHITE);
      g.drawString("Username: "+strUser+" Port: "+intPort+" IP: "+strIP, 0, 670);
    }
    
    if(blnTSelect == true){
      if(bln2p == true){
      g.drawImage(red, 100, 200, width, height, null);
      g.drawImage(blue, 600, 200, width, height, null);
      if(blnRed == true){
        g.drawImage(hred, 100, 200, width, height, null);
      }else if(blnBlue == true){
        g.drawImage(hblue, 600, 200, width, height, null);
      }
      g.setFont(new Font("Comic Sans MS", Font.BOLD, 100));
      g.setColor(Color.WHITE);
      g.drawString("TANK SELECT", 280, 150);
      }
    }
    
    if(blnMSelect == true){
      g.drawImage(stars, 0, 0, 1280, 720, null);
//      g.setColor(Color.BLACK);
//      g.fillRect(0, 0, 1280, 720);
      if(blnServer == true){
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 100));
        g.setColor(Color.WHITE);
        g.drawString("MAP SELECT", 300, 150);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        g.drawString("UFO Colour: "+strUFO, 0, 670);
        g.drawImage(map1, 50, 300, 300, 200, null);
        if(blnMap1 == true){
          g.drawImage(hmap1, 50, 300, 300, 200, null);
        }
      }else if(blnClient == true){
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        g.setColor(Color.WHITE);
        g.drawString("Waiting for Server to Choose Map", 200, 50);
        g.drawString("UFO Colour: "+strUFO, 0, 670);
      }
    }
    
  }
  // Constructor
  public menuPanel(){
    super();
  }
}