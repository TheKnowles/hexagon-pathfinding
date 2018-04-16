package pathfinding.models;

import java.lang.IllegalArgumentException;
import java.lang.StringBuilder;
import java.util.logging.Logger;

import java.util.Map;
import java.util.HashMap;



/*
mapping of edge of placement to edge numbers of adjacent tiles

    n -> (n-1)(n+1)
    0 -> 5,1
    1 -> 0,2
    2 -> 1,3
    3 -> 2,3
    4 -> 3,4
    5 -> 0,4

Adjacent tiles check and assign then map that number to the other number:

i.e. middle tile places on edge 0, you need to check edge 5 and 1 of that middle tile
    the tile of the left (5) will have the new tile in its edge 1
    vice versa for tile on the right (1) will have the new tile on its edge 5
TODO clean up this and make it more intuitive
*/

public class Hex {
  public static final Logger logger = Logger.getLogger(Hex.class.getName());

  private static final Map<Integer, Integer> flipEdges = new HashMap<>();
  static {
    flipEdges.put(0,3);
    flipEdges.put(3,0);
    flipEdges.put(1,4);
    flipEdges.put(4,1);
    flipEdges.put(2,5);
    flipEdges.put(5,2);
  }

  private String id;
  private Hex[] edges;
  private boolean visited;

  public Hex(String id){
    this.id = id;
    this.edges = new Hex[6];
    this.visited = false;
  }

  private static int indent = 0;
  //assume valid placement for now
  public void assignEdge(int edge, final Hex hex) throws Exception {
    if (edge < 0 || edge > 5) throw new IllegalArgumentException("Invalid edge value, must be between 0-5");
    for(int i = 0 ; i < indent; i++) System.out.print("\t");
    System.out.println("e: " + edge + " " + hex.toString());
    ++indent;
    edges[edge] = hex;
    edges[edge].getEdges()[flipEdges.get(edge)] = this;
    int left = edge-1;
    if (left < 0) left = 5;
    int right = edge+1;
    if (right > 5) right = 0;
    if(edges[left] != null){
      for(int i = 0 ; i < indent; i++) System.out.print("\t");
      System.out.println("[LEFT] assigning hex " + hex.toString() + " to edges[" + left + "] on its edge " + right);
      edges[left].getEdges()[right] = hex;
      hex.getEdges()[flipEdges.get(right)] = edges[left];
    }
    if(edges[right] != null){
      for(int i = 0 ; i < indent; i++) System.out.print("\t");
      System.out.println("[RIGHT] assigning hex " + hex.toString() + " to edges[" + right + "] on its edge " + left);
      edges[right].getEdges()[left] = hex;
      hex.getEdges()[flipEdges.get(left)] = edges[right];
    }
    --indent;
  }

  public Hex[] getEdges()           {return edges;}
  public boolean hasBeenVisited()   {return visited;}
 
  public void visit(){
    this.visited = true;
  }

  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append("id: " + id);
    //edges?
    return sb.toString();
  }
}
