import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GraphProcessor {
private int numVertices;
private HashMap<String, LinkedList<String>> hm;


	/**public static void main(String[] args) {
		GraphProcessor g = new GraphProcessor("/Users/erelsbernd/Documents/IowaState/IowaStateSpring2017/cs311/PA2/wikiCC.txt");	
	}*/
	
	/*hold the absolute path of a file that stores a
	directed graph. This file will be of the following format: First line indicates number of vertices.
	Each subsequent line lists a directed edge of the graph. The vertices of this graph are represented
	as strings. */
	public GraphProcessor (String graphDate)
	{
		//create HashMap 
		hm =  new HashMap<String, LinkedList<String>>();
		
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
    	
    	findSCC(hm);
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
		
		ArrayList<String> dummy = new ArrayList<String>();
		return dummy;
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
	
	
	//Returns the BFS path from u to v. This method returns an array
	//list of strings that represents the BFS path from u to v. Note that this method must return an
	//array list of Strings. First vertex in the path must be u and the last vertex must be v. If there is no
	//path from u to v, then this method returns an empty list.
	
	public ArrayList<String> bfsPath(String u, String v)
	{
		ArrayList<String> bfsPath = new ArrayList<String>();
		
		if (u == null | v == null | u.isEmpty() | v.isEmpty()) return bfsPath;
		
		if (u.equals(v)) {
			bfsPath.add(u);
			bfsPath.add(v);
			return bfsPath;
		}
		
		Queue<String> q = new LinkedList<String>();
		HashMap<String, String> visited = new HashMap<String, String>();
		
		
		q.add(u);
		visited.put(u, null);
		
		while (!q.isEmpty()) {
			String cur = q.remove();
			//System.out.println(cur);
			LinkedList<String> neighbors = hm.get(cur);
			if (neighbors == null) {
				//System.out.println("Fuckin bullshit");
				continue;
			}
			for (String neighbor: neighbors) {
				if (!visited.containsKey(neighbor)) {
					q.add(neighbor);
					visited.put(neighbor,cur);
					if (neighbor.equals(v)) {
						cur = v;
						break;
					}
				}
				//System.out.println("n: " + neighbor);
			}
			if (cur.equals(v)) break;
		}
		
		// didn't find v, return empty list
		if (!visited.containsKey(v)) {
			return bfsPath;
		}
		
		// go through visited and build shortest path
		String parent = v;
		while (!parent.equals(u)) {
			bfsPath.add(parent);
			parent = visited.get(parent);
		}
		
		bfsPath.add(u);
		
		Collections.reverse(bfsPath);
		return bfsPath;
	}
	
	private ArrayList<String> findCycle(String root) {
		return null;
	}
	
	private ArrayList<String> findSCC(HashMap<String, LinkedList<String>> map) {
		return null;
	}

}
