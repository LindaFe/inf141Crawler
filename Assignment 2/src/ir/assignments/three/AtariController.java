package src.ir.assignments.three;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class AtariController {
	
	public static HashMap<String, Integer> allPages = new HashMap<String, Integer>();
	
	public static int countEss = 0;
	
	public static void sortAndSave() throws UnsupportedEncodingException, FileNotFoundException, IOException {
        Map<String, Integer> sortedMap = new TreeMap<String, Integer>(allPages);
        
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                  new FileOutputStream("Subdomains.txt"), "utf-8"))) {
        		writer.write("Number of Unique Subdomains: " + sortedMap.size() + "\n");
        	for (String s : sortedMap.keySet()) {
                writer.write(s + ", " + sortedMap.get(s).intValue() + "\n");
        	}         
        }
    }
	
	public static void main(String[] args) throws Exception {		//basic crawler controller
		LocalDateTime startTime = LocalDateTime.now();
		String crawlStorageFolder = "/data/crawl/root";
        int numberOfCrawlers = 3;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        controller.addSeed("http://www.ics.uci.edu/~lopes/");
        controller.addSeed("http://www.ics.uci.edu/~welling/");
        controller.addSeed("http://www.ics.uci.edu/");
        controller.addSeed("https://intranet.ics.uci.edu/");
        controller.addSeed("http://booklist.ics.uci.edu");
        controller.addSeed("https://booklist.ics.uci.edu/?page_id=167");

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(MyCrawler.class, numberOfCrawlers);
        sortAndSave();
        LocalDateTime endTime = LocalDateTime.now();
        System.out.println("Number of unique pages crawled: " + countEss);
        System.out.println(startTime + " " + endTime );
    }
}
