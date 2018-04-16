package pathfinding;

import java.util.logging.Logger;
import pathfinding.models.*;


public class MainApp {
  public static final Logger logger = Logger.getLogger(MainApp.class.getName());

  public static void main(String[] args) throws Exception{
    Hex middle = new Hex("middle tile"); 
    //build smallest full map
    for(int i = 0; i < 6; i++){
      middle.assignEdge(i, new Hex("me: " + i));
    }
    //print edges
    System.out.println(middle.toString());
    for(Hex edge : middle.getEdges()){
      System.out.println(edge.toString());
      int j = 0;
      for(Hex e2 : edge.getEdges()){
        if(e2 != null) System.out.println("\t\t" + "side " + j + ": " + e2.toString());
        else System.out.println("\t\tside " + j + ": null");
        ++j;
      }
    }
  }
}
