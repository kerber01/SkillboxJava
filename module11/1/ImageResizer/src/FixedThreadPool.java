import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;

public class FixedThreadPool {

    public static void main(String[] args) throws IOException, InterruptedException {

        String srcFolder = "D:/src";
        String dstFolder = "D:/dst";

        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU cores: " + processors);

        File srcDir = new File(srcFolder);


        File[] files = srcDir.listFiles();
        if (files == null) {
            for (; ; ) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Wrong source folder, enter another path:");
                files = new File(reader.readLine().trim()).listFiles();
                if (files != null) {
                    break;
                }
            }
        }
        final CountDownLatch latch = new CountDownLatch(files.length);
        ExecutorService service = Executors.newFixedThreadPool(processors);

        Map<Integer, ArrayList<File>> sortedMap = FilesSorter
            .sortFilesBySize(files, 1);

        long start = System.currentTimeMillis();
        for (File file : sortedMap.get(0)) {
            service.submit(() -> {
                try {
                    BufferedImage image = ImageIO.read(file);
                    if (image == null) {
                        service.shutdown();
                    }

                    Main.imageResize(image, dstFolder, file);
                    System.out.println("Thread started");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out
                    .println("Thread " + " duration: " + (System.currentTimeMillis() - start));
                latch.countDown();
            });
        }

        service.shutdown();
        latch.await();
        System.out.println("Total duration: " + (System.currentTimeMillis() - start));

    }
}
