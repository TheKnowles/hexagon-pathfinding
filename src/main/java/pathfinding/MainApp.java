package pathfinding;

import pathfinding.models.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class MainApp {

  //Breadth First Search
  public static void findPath(final Hex start, final Hex end){
    System.out.println("Searching from " + start.toString() + " to " + end.toString());   
    LinkedList<Hex> ll = new LinkedList<>();
    HashMap<String, String> path = new HashMap<>();
    ll.add(start);
    while(!ll.isEmpty()){
      final Hex tmp = ll.removeFirst();
      if(tmp.equals(end)){
        System.out.println("Reached goal, path:");
        //print out path
        ArrayList<String> output = new ArrayList<>(); 
        output.add(tmp.toString());
        String run = tmp.toString();
        while (path.get(run) != null){
          output.add(path.get(run));
          run = path.get(run);
        }
        Collections.reverse(output);
        for(int i = 0; i < output.size(); i++){
          System.out.print(output.get(i));
          if(i < (output.size() -1)) System.out.print(" -> ");
        }
        System.out.println();
      }
      for(Hex edge : tmp.getEdges()){
        if (edge == null || edge.isVisited() || edge.isBlocked()) continue;
        if(!ll.contains(edge)){
          ll.add(edge);
          path.put(edge.toString(), tmp.toString());
        }
      } 
      tmp.setVisited(true);
    }
  }

  public static void resetGraph(final Hex root){
    LinkedList<Hex> ll = new LinkedList<>();
    LinkedList<Hex> processed = new LinkedList<>();
    ll.add(root);
    while(!ll.isEmpty()){
      final Hex tmp = ll.removeFirst();
      tmp.setVisited(false);
      for(Hex edge : tmp.getEdges()){
        if (edge == null) continue;
        if(!ll.contains(edge) && !processed.contains(edge)) ll.add(edge);
      }
      processed.add(tmp);
    }
  }

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
    //simple one hex traversal
    findPath(middle, middle.getEdges()[0]);
    resetGraph(middle);
    findPath(middle.getEdges()[0], middle.getEdges()[3]);
    resetGraph(middle);
    middle.setBlocked(true);//force route around the outside
    findPath(middle.getEdges()[0], middle.getEdges()[3]);
  }
}
