import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser extends RecursiveTask<Set<String>> {

    private String url;
    private String startUrl;
    private Set<String> allLinks;

    private Parser(String url, String startUrl, Set<String> allLinks) {
        this.url = url.trim();
        this.startUrl = startUrl;
        this.allLinks = allLinks;

    }

    public Parser(String url, String startUrl) {
        this.url = url.trim();
        this.startUrl = startUrl.trim();
        allLinks = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
    }


    @Override
    protected Set<String> compute() {
        Set<String> siteMap = new HashSet<>();
        if (url.lastIndexOf("/") != (url.length() - 1)) {
            url += "/";
        }
        siteMap.add(url);
        Set<Parser> subTaskSet = getChildren();
        for (Parser task : subTaskSet) {
            siteMap.addAll(task.join());
        }
        return siteMap;
    }

    private Set<Parser> getChildren() {
        Document doc;
        Elements elements;
        Set<Parser> subTask = new HashSet<>();
        try {
            Thread.sleep(200);
            doc = Jsoup.connect(url).maxBodySize(0).userAgent("Mozilla").get();
            elements = doc.select("a");
            for (Element el : elements) {
                String attr = el.attr("abs:href");
                if (!attr.isEmpty() && attr.startsWith(startUrl) && !allLinks.contains(attr)
                    && !attr.contains("#") && !attr.contains("?")) {
                    Parser parser = new Parser(attr, startUrl, allLinks);
                    parser.fork();
                    subTask.add(parser);
                    allLinks.add(attr);
                }
            }
        } catch (InterruptedException | IOException ignored) {
        }
        return subTask;
    }
}