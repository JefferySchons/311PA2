import java.util.ArrayList;


public class GraphProcessor {

	public GraphProcessor (String graphDate)
	{
		/*
		graphData hold the absolute path of a le that stores a
		directed graph. This le will be of the following format: First line indicates number of vertices.
		Each subsequent line lists a directed edge of the graph. The vertices of this graph are represented
		as strings.
		This class should create ecient data structures so that the following public methods of
		the class run eciently.
		*/
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
