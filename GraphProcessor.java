import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;

public class GraphProcessor {
private int numVertices;


	public static void main(String[] args) {
		GraphProcessor g = new GraphProcessor("/Users/erelsbernd/Documents/IowaState/IowaStateSpring2017/cs311/PA2/wikiCC.txt");	
	}
	
	/*hold the absolute path of a file that stores a
	directed graph. This file will be of the following format: First line indicates number of vertices.
	Each subsequent line lists a directed edge of the graph. The vertices of this graph are represented
	as strings. */
	public GraphProcessor (String graphDate)
	{
		//create HashMap 
		HashMap<String, LinkedList<String>> hm =  new HashMap<String, LinkedList<String>>();
		
		//read in file and insert edges
    	try {
           File file = new File(graphDate);

           Scanner input = new Scanner(file);
           
         //read in numVertices
           numVertices = input.nextInt();
		   input.nextLine();
		   
           while (input.hasNextLine()) {
        	   
        	   //get start and end nodes
			   String start = input.next();
			   String end = input.next();
			   input.nextLine();
			   LinkedList<String> targetList = hm.get(start);
			   
			   //check to see if targetList already exists in the map
			  if (targetList != null) {
				  targetList.add(end);
			  } else {
				  targetList = new LinkedList<String>();
				  targetList.add(end);
				  hm.put(start, targetList);
			  }
           }
           input.close();
    	} catch (Exception ex) {
            ex.printStackTrace();
        }
    	System.out.println(hm.toString());
	}
	
	
	public int outDegree(String v)
	{
		//Returns the out degree of v.
		return 0;
	}
	
	
	public boolean sameComponent(String u, String v)
	{
		//Returns true if u and v belong to the same SCC; otherwise returns false
		return false;
	}
	
	
	public ArrayList<String> componentVerticies(String v)
	{
		//Return all the vertices that belong to the same 
		//Strongly Connected Component of v (including v). 
		//This method must return an array list of Strings,
		return null;
	}
	
	
	public int largestComponent()
	{
		//Returns the size of the largest component.
		return 0;
	}
	
	
	public int numComponents()
	{
		//Returns the number of strongly connect components.
		return 0;
	}
	
	
	public ArrayList<String> bfsPath(String u, String v)
	{
		//Returns the BFS path from u to v. This method returns an array
		//list of strings that represents the BFS path from u to v. Note that this method must return an
		//array list of Strings. First vertex in the path must be u and the last vertex must be v. If there is no
		//path from u to v, then this method returns an empty list.
		
		return null;
	}

}
