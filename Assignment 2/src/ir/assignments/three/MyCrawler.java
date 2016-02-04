package src.ir.assignments.three;

import java.util.Collection;
import java.util.Set;
import java.util.regex.Pattern;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MyCrawler extends WebCrawler {

	
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
                                                           + "|png|mp3|mp3|zip|gz))$");

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
     @Override
     public boolean shouldVisit(Page referringPage, WebURL url) {
         String href = url.getURL().toLowerCase();
         return !FILTERS.matcher(href).matches()
                && href.contains(".ics.uci.edu") 
                && !href.contains("duttgroup.ics.uci.edu") 			// telling it not to go here
                && !href.contains("calendar")
         		&& !href.contains("cradl.ics")
         		&& !href.contains("archive.ics");
                //og startsWith("http://www.ics.uci.edu/");			// that contains instead of starts with
     }

     /**
      * This function is called when a page is fetched and ready
      * to be processed by your program.
      */
     @Override
     public void visit(Page page) {
         String url = page.getWebURL().getURL();
         System.out.println("URL: " + url);
         
         AtariController.countEss ++;
        
         String theSub = page.getWebURL().getSubDomain();
         theSub = theSub.replace(".ics", "");
         if (!theSub.equals("www") ){
        	 getSubdomains(url,theSub);
         }
         
         if (page.getParseData() instanceof HtmlParseData) {
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             String text = htmlParseData.getText();
             String html = htmlParseData.getHtml();
             Set<WebURL> links = htmlParseData.getOutgoingUrls();

             System.out.println("Text length: " + text.length());
             System.out.println("Html length: " + html.length());
             System.out.println("Number of outgoing links: " + links.size());
         }
    }
     public static void getSubdomains(String url, String s) {

    	 int count = AtariController.allPages.containsKey(s) ? AtariController.allPages.get(s): 0;		// ? is like an if statement and then what happens after that do what follows after it
         AtariController.allPages.put(s, count + 1);
    	 
         System.out.println("Subdomain: " + s);
     }

 }
