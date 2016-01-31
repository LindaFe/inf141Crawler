package src.ir.assignments.three;

import java.util.Collection;
import java.util.Set;
import java.util.regex.Pattern;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MyCrawler extends WebCrawler {

	private static HashMap<String, Set<String>> allPages = new HashMap<String, Set<String>>();
	
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
                && href.contains(".ics.uci.edu");
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
     public static void getSubdomains(String url) {
         String s = url;
         int i = s.indexOf("www.")+4;
         int j = s.indexOf(".ics.uci.edu");
         s = s.substring(i, j);

         if (allPages.containsKey(s)) {
             ((Set<String>) allPages.get(s)).add(url);
         }
         else {
             allPages.put(s, new HashSet<String>());
             ((Set<String>) allPages.get(s)).add(url);
         }
     }

     public static sortAndSave() {
         Map<String, Set<String>> sortedMap = new TreeMap<String, Set<String>>(allPages);
         try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                   new FileOutputStream("Subdomains.txt"), "utf-8"))) {
             for (String s : sortedMap.keySet()) {
                 writer.write(s + " " + sortedMap.get(s).size() + ", ");
             }
         }
     }

 }
