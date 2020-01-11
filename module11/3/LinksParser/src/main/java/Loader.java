import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class Loader {

    public static String url;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Введите адрес сайта: пример - > https://yandex.ru/ ");
            url = sc.nextLine();
            boolean marker = true;
            try {
                URL mainUrl = new URL(url);
            } catch (MalformedURLException e) {
                marker = false;
                System.out.println("Неправильный формат ссылки, повторите попытку.");
            }
            if (marker) {
                break;
            }
        }

        if (url.lastIndexOf("/") != (url.length() - 1)) {
            url += "/";
        }
        int numThreads = 0;
        while (true) {
            System.out
                .println(
                    "Введите количество создаваемых потоков: - > 0 установит значение по умолчанию.");
            String input = sc.nextLine();
            if (input.matches("-?(0|[1-9]\\d*)")){
                numThreads = Integer.parseInt(input);
                break;
            }
            System.out.println("Неверный формат. Введите число...");
        }

        System.out.println("Сканируем сайт...");
        long start = System.currentTimeMillis();
        Parser parser = new Parser(url, url);

        Set<String> unsortedSiteMap = numThreads == 0 ? new ForkJoinPool().invoke(parser)
            : new ForkJoinPool(numThreads).invoke(parser);

        System.out.println("Сканирование завершено...");
        System.out
            .println(
                "Время сканирования " + ((System.currentTimeMillis() - start) / 1000) + " сек.");
        System.out.println("Выстраиваю иерархию...");

        List<String> sortedSiteMap = new ArrayList<>(unsortedSiteMap);
        Collections.sort(sortedSiteMap);

        int offset = url.length();
        for (int i = 1; i < sortedSiteMap.size(); i++) {
            String link = sortedSiteMap.get(i);
            int depth = link.substring(offset).split("/").length;
            for (int j = 0; j < depth; j++) {
                link = "\t" + link;
            }
            sortedSiteMap.remove(i);
            sortedSiteMap.add(i, link);
        }


        writeFiles(sortedSiteMap);
    }

    private static void writeFiles(List<String> map) {
        System.out.println("Запись в файл...");
        String filePath = "siteMap.txt";

        File file = new File(filePath);
        try (PrintWriter writer = new PrintWriter(file)) {
            Iterator<String> iterator = map.iterator();
            while (iterator.hasNext()) {
                writer.write(iterator.next() + "\r\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Карта создана!");
    }
}