import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MapPanel extends JPanel{
  //propertiess
  int intCount1;
  int intCount;
  String strMap[][];
  boolean blnDrawMap;
  String strMapName;
  //methods
  public void paintComponent(Graphics g){
    g.setColor(Color.BLACK);
    g.fillRect(0,0,1280,720);
      strMap = gameutil.loadMap("map.csv");
      for (intCount=0;intCount<18;intCount++){
        for (intCount1 = 0 ; intCount1<24;intCount1++){
          if (strMap[intCount][intCount1].equals("wall")){
            g.setColor(Color.WHITE);
            g.fillRect(intCount1*40,intCount*40,40,40);
          }
        }
      }
  }
  
  //constructors
  public MapPanel(){
    super();
  }
  //main
  
  
}