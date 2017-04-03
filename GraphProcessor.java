import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

public class GraphProcessor {
	private int numVertices;
	private HashMap<String, LinkedList<String>> hm;
	private HashMap<String, LinkedList<String>> reverseHm;

	private HashMap<String, String> parents;
	private HashMap<String, Integer> numerators;
	private HashMap<String, Integer> denominators;
	private TreeMap<Integer, String> denomSorted;
	private HashMap<String, Integer> sccID;
	private int largestComponent;

	ArrayList<ArrayList<String>> cycles;
	

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
	
	//Returns the out degree of v.
	public int outDegree(String v)
	{
		if (hm.get(v) == null) return 0;
		
		return hm.get(v).size();
	}
	
	//Returns true if u and v belong to the same SCC; otherwise returns false
	public boolean sameComponent(String u, String v)
	{
		if (sccID.get(u) == null | sccID.get(v) == null) return false;
		return sccID.get(u) == sccID.get(v);
	}
	
	//Return all the vertices that belong to the same 
	//Strongly Connected Component of v (including v). 
	//This method must return an array list of Strings,
	public ArrayList<String> componentVertices(String v)
	{	
		if (sccID.get(v) == null) return new ArrayList<String>();
		
		int cycleIndex = sccID.get(v);
		return cycles.get(cycleIndex);
	}
	
	//Returns the size of the largest component.
	public int largestComponent()
	{
		return largestComponent;
	}
	
	
	public int numComponents()
	{
		//Returns the number of strongly connect components.
		return this.cycles.size();
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

	
	private int finishDFS(String current, int count) {
		
		LinkedList<String> neighbors = hm.get(current);
		
		if (neighbors == null) {
			denominators.put(current, count++);
			denomSorted.put(count, current);
			return count;
		}
		
		for (String neighbor : neighbors) {
			if (!numerators.containsKey(neighbor)) {
				numerators.put(neighbor, count++);
				parents.put(neighbor, current);
				count = finishDFS(neighbor, count);
			}
		}
		
		//if (!denominators.containsKey(current)) {
		denominators.put(current, count++);
		denomSorted.put(count, current);
		
		return count;
	}
	
	private ArrayList<String> findSCC(HashMap<String, LinkedList<String>> map) {
		parents = new HashMap<String, String>();
		numerators = new HashMap<String, Integer>();
		denominators = new HashMap<String, Integer>();
		denomSorted = new TreeMap<Integer, String>(Collections.reverseOrder());
		
		
		Set<String> vertices = hm.keySet();
		
		int count = 1;
		if (vertices == null) {
			return null;
		}
		// DFS and mark numerators with visit number
		// this for loop will catch any nodes not reachable through start
		for (String s: vertices) {
			if (!numerators.containsKey(s)) {
				numerators.put(s, count++);
				count = finishDFS(s, count);
			}
		}

		System.out.println("parents: " + parents.toString());
		System.out.println("numerators: " + numerators.toString());
		System.out.println("denominators: " + denominators.toString());
		
		makeDescendingDenomList();
		reverseGraph();
		findCycles();
		
		return null;
	}
	
	
	//INPUT: denoms from global , OUTPUT: denom-list, descending
	//find highest denom, make a list of vertexes going backwards (only use denoms)
	/// grab the Set from denoms
	//// do a descending sort on it
	private ArrayList<String> makeDescendingDenomList() {
		ArrayList<String> descendingPath = new ArrayList<String>();
		
		// Get a set of the entries
	      Set<Map.Entry<Integer,String>> set = denomSorted.entrySet();
	      
	      // Get an iterator
	      Iterator<Map.Entry<Integer,String>> i = set.iterator();
	      
	      // Display elements
	      while(i.hasNext()) {
	         Map.Entry<Integer,String> me = i.next();
	         String node = String.valueOf(me);
	         descendingPath.add(node);
	         //System.out.print(me.getKey() + ": ");
	         //System.out.println(me.getValue());
	      }
		
		return descendingPath;
	}
	
	//reverse the entire graph  (parents become childs, child become parents)
	  /// foreach node in VertexSet
	   //// if child not in reverseGraph-hashmap, add child to it w/ parent as rev-child
	   //// if child is already in revGraph, add parent to linkedList of rev-childs
	//INPUT: global hm , OUTPUT: new reversed-hashmap<String, LinkedList> (node:rev-childs)
	private void reverseGraph()
	{
		reverseHm = new HashMap<String, LinkedList<String>>();
		
		Iterator<Entry<String, LinkedList<String>>> it = hm.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, LinkedList<String>> pair = it.next();
	        
	        String parent = String.valueOf(pair.getKey());
	        LinkedList<String> childs = (LinkedList<String>) pair.getValue();
	        Iterator<String> child_iter = childs.iterator();

	        // Iterate thru all vertex children
		    while (child_iter.hasNext()) {
		    	String child = String.valueOf(child_iter.next());
		    	LinkedList<String> reverse_children = new LinkedList<String>();
		    	
		 	   //if child not in reverseGraph-hashmap, add child to it w/ parent as rev-child
		    	if (!reverseHm.containsKey(child)) {
		    		
		    		reverse_children.add(parent);
		    		reverseHm.put(child, reverse_children);
		    		
		    	} //if child is already in revGraph, add parent to linkedList of rev-childs
		    	else {
		    		reverseHm.get(child).add(parent);
		    	}
	        }
	    }
	}
	
	
	
	
	//starting at top of descending-denom-list
	/// if N to N-1 of list is connected in rev-graph, then add to current cycle
	/// if N to N-1 of list is not-connected in rev-graph, then save current cycle,
	//// .. and start a new cycle with next node
	private void findCycles() {
		
		cycles = new ArrayList<ArrayList<String>>();
		sccID = new HashMap<String, Integer>();
		int cycleID = 0;
		largestComponent = 0;
		
		System.out.println("denomSorted: " + denomSorted.toString());
		System.out.println("hm-normal: " + hm.toString());
		System.out.println("hm-reverse: " + reverseHm.toString());
		
		Iterator<Map.Entry<Integer,String>> diter;
		diter = this.denomSorted.entrySet().iterator();
		
		
		
		while (diter.hasNext()) {
			String current = diter.next().getValue();
			
			
			if (!sccID.containsKey(current)) {
				ArrayList<String> cycle = new ArrayList<String>();
				
				this.cycles.add(cycle);
				sccID.put(current, cycleID);
				
				cycleBFS(current, cycleID);
				cycleID++;
			}
			
		}
		
		System.out.println("cycles" + cycles.toString());
	}
	
	private int cycleBFS(String current, int cycleIndex) {
		
		int currentCycleSize = 0;
		
		if (current == null  | current.isEmpty()) return 0;
		
		Queue<String> q = new LinkedList<String>();
		q.add(current);
	
		
		while (!q.isEmpty()) {
			String cur = q.remove();
			
			sccID.put(cur,cycleIndex);
			cycles.get(cycleIndex).add(cur);
			currentCycleSize++;
			
			//System.out.println(cur);
			LinkedList<String> neighbors = reverseHm.get(cur);
			if (neighbors == null) {
				continue;
			}
			
			for (String neighbor: neighbors) {
				if (!sccID.containsKey(neighbor)) {
					q.add(neighbor);
				}
				//System.out.println("n: " + neighbor);
			}
		}
		if (largestComponent < currentCycleSize) {
			largestComponent = currentCycleSize;
		}
		return 0;
	}

}
