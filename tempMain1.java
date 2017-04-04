import java.util.ArrayList;

public class tempMain1 {

	public static void main(String[] args) {
		String URL_part2;
		URL_part2="/wiki/Computer_Science";
		String filename= "WikiCS.txt";  //file to save to
		int max= 500;// max pages to be crawled; (to get links of) will sill find connections between it and other already known links
		WikiCrawler1 crawl = new WikiCrawler1(URL_part2, max, filename);
		crawl.extractLinks(URL_part2); //sould be be 
		ArrayList<String> listOfLinks = new ArrayList<String>();
		listOfLinks= crawl.masterList();
		
		//for(int i=0; i< listOfLinks.size(); i++)
		//{
		//	System.out.println(listOfLinks.get(i));
		//}
		
		System.out.println(listOfLinks.size());
		
	}

}
