import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class WikiCrawler1 {
	private String added_Url;
	private static int max_pages;
	private static String file_to_Write;
	private final static String BASE_URL = "https://en.wikipedia.org";
	private static ArrayList<String> master_list_O_links = new ArrayList<String>();
	private static int counter;//counts what link im on in master_list_O_links;

	public WikiCrawler1(String seedURL, int max, String fileName)
	{
		added_Url=seedURL;
		max_pages=max;
		file_to_Write=fileName;
		//A string seedUrl{relative address of the seed url (within Wiki domain).
		//An integer max representing Maximum number pages to be crawled (number of pages seen (all links gathered)(stop as soon as at max)
		//A string fileName representing name of a file-The graph will be written to this file
	}
	
	//public ArrayList<String> extractLinks(String doc)
	public static void extractLinks(String doc)
	{
		String full_URL=BASE_URL+doc;
		ArrayList<String> list_O_links= new ArrayList<String>();
		
		//extract wiky links and add to arrayList in order;
			// only after 1st "<p>"
			// none that have "#" or ":"
		try{
			URL wiki = new URL(full_URL);
			BufferedReader in = new BufferedReader(new InputStreamReader(wiki.openStream()));
			String input;
			while ((input = in.readLine()) != null)
			{
				if(input.length()> 15)
				{
					if(input.substring(0,12).equals( "<li><a href="))
					{
						String minStart=input.substring(13,input.length());
						String lineURL = minStart.substring(0,minStart.indexOf("\""));
						if(lineURL.contains("#") || lineURL.contains(":"))
						{
							//do nothing; skip this line
						}
						else
						{
							list_O_links.add(lineURL);
						}
					}
				}
			}
			in.close();
			
			printToText(list_O_links, doc);
		}catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		crawl();
	}
	private static void printToText(ArrayList<String> List_O_links, String doc)
	{
		//this class both updates the master_list_O_links and prints to text file all the links
		//the text file is a 
		
		try(FileWriter fw = new FileWriter(file_to_Write, true); // will be in folder with .classpath
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw))
		{
			if(master_list_O_links==null)
			{
				counter=0;
				master_list_O_links.add(doc);// to add very first link go to
				if(List_O_links.size()<max_pages-1)
				{
					master_list_O_links.addAll(List_O_links);
				}
				else
				{
					//get sub array of List_O_links
					master_list_O_links.addAll(List_O_links.subList(0,  max_pages-1));
				}
				//print
				for(int j=0; j<master_list_O_links.size(); j++)
				{
					out.println(doc + " " + master_list_O_links.get(j).toString());
					//print link doc -> List_O_links.get(i)
				}
			}
			else
			{
				//loop through List_O_links and master_list_O_links
				for(int i=0; i<List_O_links.size(); i++)
				{
					if(master_list_O_links.contains(List_O_links.get(i)))
					{
						//print link doc -> List_O_links.get(i)
						out.println(doc + " " + List_O_links.get(i).toString());
					}
					else
					{
						if(master_list_O_links.size()<max_pages)
						{
							master_list_O_links.add(List_O_links.get(i));
							////print link doc -> List_O_links.get(i)
							out.println(doc + " " + List_O_links.get(i).toString());
						}
						else
						{
							//would be new page so ignore
						}
					}
				}
			}
		}catch (IOException e) {
		}
	}
	public static void crawl()
	{
		if(counter%25==0) //wait 1 secound every 25 requests so it waits > 3sec/100 requests
		{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		//increment counter for what link to look at
		if(counter<max_pages-1) 
		{
			counter++;
			String new_link=master_list_O_links.get(counter);
			extractLinks(new_link);
		}
		//do extract links over links on page
	}
	public ArrayList<String> masterList()
	{
		return master_list_O_links;
	}
}
