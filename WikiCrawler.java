import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WikiCrawler {
	private String added_Url;
	private static int max_pages=100;
	private String file_to_Write;
	private final static String BASE_URL = "https://en.wikipedia.org";
	private static ArrayList<String> master_list_O_links; //remove static
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
		//A string seedUrl{relative address of the seed url (within Wiki domain).
		//An integer max representing Maximum number pages to be crawled (number of pages seen (all links gathered)(stop as soon as at max)
		//A string fileName representing name of a file-The graph will be written to this file
	}
	
	//public ArrayList<String> extractLinks(String doc)
	public static void extractLinks(String doc)
	{
		String full_URL=BASE_URL+doc;
		ArrayList<String> list_O_links= new ArrayList<>();
		
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
						String minStart=input.substring(14,input.length());
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
			//add list_O_links to master_list_O_links
			for(int i=0; i< list_O_links.size(); i++)
			{
				int alreadyViseted=0; //>0 meaans visited
				//fist loop through master list
				if(master_list_O_links.size()==0)
				{
					master_list_O_links.add(list_O_links.get(i));
				}
				else
				{
					if(master_list_O_links.size()<max_pages)
					{
						//loop thgout master_list and compare
						master_list_O_links.add(list_O_links.get(i));
						//if (is simmilarity then alreadyViseted
					}
					//if alreadyViseted ==0 then add
				}
				
			}
		}catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		//returns arraylist of liks on this page
		//return null;
	}
	public void crawl()
	{
		//do extract links over links on page
	}
	public ArrayList<String> masterList()
	{
		return master_list_O_links;
	}

}
