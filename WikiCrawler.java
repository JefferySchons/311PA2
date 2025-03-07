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

public class WikiCrawler {
	private String added_Url;
	private static int max_pages;
	private String file_to_Write;
	private final static String BASE_URL = "https://en.wikipedia.org";
	private static ArrayList<String> master_list_O_links = new ArrayList<String>();
	private static int counter;//counts what link im on in master_list_O_links;
	/*
	public static void main(String[] args) {
		String URL_part2;
		URL_part2="/wiki/Computer_Science";
		extractLinks(URL_part2);
		for(int i=0; i< master_list_O_links.size(); i++)
		{
			System.out.println(master_list_O_links.get(i));
		}
		
	}
	*/
	public WikiCrawler(String seedURL, int max, String fileName)
	{
		added_Url=seedURL;
		max_pages=max;
		file_to_Write=fileName;
		master_list_O_links.add(seedURL);
		this.extractLinks(seedURL);
		//A string seedUrl{relative address of the seed url (within Wiki domain).
		//An integer max representing Maximum number pages to be crawled (number of pages seen (all links gathered)(stop as soon as at max)
		//A string fileName representing name of a file-The graph will be written to this file
	}
	
	//public ArrayList<String> extractLinks(String doc)
	public static void extractLinks(String doc)
	{
		System.out.println("start of extract links");
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
						//System.out.println(input);
						String minStart=input.substring(13,input.length());
						String lineURL = minStart.substring(0,minStart.indexOf("\""));
						if(lineURL.contains("#") || lineURL.contains(":"))
						{
							//do nothing; skip this line
						}
						else
						{
							list_O_links.add(lineURL);
							//System.out.println(lineURL);
						}
					}
				}
			}
			in.close();
			
			addToMasterList(list_O_links, doc);
		}catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		//returns arraylist of links on this page
		//return null;
		//now crawl
		//if(master_list_O_links.size()<max_pages) //== list would be full
		//{
			//this will cause a looping back and forth between crawl and extractLinks
			//tell master_list_O_links==max_pages;
			crawl();
		//}
	}
	private static void addToMasterList(ArrayList<String> List_O_links, String doc)
	{
		//this class updates the master_list_O_links
		//add list_O_links to master_list_O_links
		/*  //master list is initialized at start, so can't be null
		 *  //if(master_list_O_links==null)
		 */
		//loop through List_O_links and master_list_O_links
		for(int i=0; i<List_O_links.size(); i++)
		{
			if(!master_list_O_links.contains(List_O_links.get(i)))
			{
				if(master_list_O_links.size()<max_pages)
				{
					master_list_O_links.add(List_O_links.get(i));
				}
			}
		}
		
		/*
		need to do( if != null)  
		check if links are in master
			-no 
				check master_list_O_links.size()<max
				-yes
					add to master_list_O_links and print doc -> link
				-no 
					ignore
			-yes 
				print doc -> link 
		*/
		////////
		
		//so i dont get more then max pages
		//should just remove the back extra pages
		if(master_list_O_links.size()>=max_pages)
		{
			//master_list_O_links = (ArrayList<String>) master_list_O_links.subList(0, max_pages);
			master_list_O_links.subList(max_pages,master_list_O_links.size()).clear();
		}
	}
	

	private static void printToText()
	{
		//this class prints to text file all the links
		//the text file is a 
		/////
		try(FileWriter fw = new FileWriter("test/_WikiCrawlerTest_crawl_results.txt", true); // will be in folder with .classpath
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw))
		{
			
			out.println(String.valueOf(master_list_O_links.size()));
			//loop through List_O_links and master_list_O_links
			for(int i=0; i<master_list_O_links.size(); i++)
			{
				master_list_O_links.contains(master_list_O_links.get(i));
			}
		}catch (IOException e) {
			//exception handling left as an exercise for the reader
		}
	}
	
	public static void crawl()
	{
		if(counter%100==0) //wait 4 second every 100 requests so it waits > 3sec/100 requests
		{
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		//increment counter for what link to look at
		if(counter<max_pages-1) 
		{
			System.out.println("start of new craw");
			counter++;
			String new_link=master_list_O_links.get(counter);
			extractLinks(new_link);
		}
		//do extract links over links on page
		printToText();
	}
	public ArrayList<String> masterList()
	{
		return master_list_O_links;
	}

}
