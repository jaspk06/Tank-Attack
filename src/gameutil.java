import java.io.*;

public class gameutil{
  public static String[][] loadMap(String strFileName){
    FileReader thefile;
    BufferedReader thefiledata;
    String strMap[][];
    int intRow;
    int intColumn;
    String strLine;
    String strSplit[];
    strMap = new String [18][24];
    try{
      thefile = new FileReader (strFileName);
      thefiledata = new BufferedReader (thefile);
      for(intRow =0;intRow <18;intRow++){
        try{
          strLine = thefiledata.readLine();
          strSplit = strLine.split(",");
          for(intColumn =0;intColumn <24;intColumn++){
            strMap[intRow][intColumn] = strSplit[intColumn]; 
            //System.out.println(intRow+", "+intColumn);
          }
        }catch (IOException e){
          System.out.println("Map not loaded");
          
        }
      }
      try{
      thefiledata.close();
          thefile.close();
      }catch (IOException e){
        System.out.println("Files cannot be closed");
      }
    }
    catch (FileNotFoundException e){
      System.out.println("file not found");
    }
    return strMap;
    
  }
  public static void drawMap (String strMap[][]){
    int intCount;
    int intCount1;
    for (intCount=0;intCount<18;intCount++){
      for (intCount1 = 0 ; intCount1<18;intCount1++){
        if (strMap[intCount][intCount1].equals("wall")){
        }
      }
    }
  }  
}