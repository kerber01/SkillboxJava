import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.RecursiveTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser extends RecursiveTask<CopyOnWriteArraySet<String>> {

    private String url;
    private static String startUrl;
    private static CopyOnWriteArraySet<String> allLinks = new CopyOnWriteArraySet<>();
    private static CopyOnWriteArraySet<String> siteMap = new CopyOnWriteArraySet<>();

    public Parser(String url) {
        this.url = url.trim();
    }

    public Parser(String url, String startUrl) {
        this.url = url.trim();
        Parser.startUrl = startUrl.trim();
    }


    @Override
    protected CopyOnWriteArraySet<String> compute() {
        if (url.lastIndexOf("/") != (url.length() - 1)) {
            url += "/";
        }
        siteMap.add(url);
        Set<Parser> subTask = new HashSet<>();
        getChildren(subTask);
        for (Parser task : subTask) {
            siteMap.addAll(task.join());
        }
        return siteMap;
    }

    private void getChildren(Set<Parser> subTask) {
        Document doc;
        Elements elements;
        try {
            Thread.sleep(200);
            doc = Jsoup.connect(url).maxBodySize(0).userAgent("Mozilla").get();
            elements = doc.select("a");
            for (Element el : elements) {
                String attr = el.attr("abs:href");
                if (!attr.isEmpty() && attr.startsWith(startUrl) && !allLinks.contains(attr)
                    && !attr.contains("#") && !attr.contains("?")) {
                    Parser parser = new Parser(attr);
                    parser.fork();
                    subTask.add(parser);
                    allLinks.add(attr);
                }
            }
        } catch (InterruptedException | IOException ignored) {
        }
    }
}