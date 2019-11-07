import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.sound.midi.Soundbank;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {

        String srcFolder = "D:/src";
        String dstFolder = "D:/dst";

        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU cores: " + processors);

        File srcDir = new File(srcFolder);
        final CountDownLatch latch = new CountDownLatch(processors);



        File[] filesArray = srcDir.listFiles();
        if (filesArray == null) {
            for (; ; ) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Wrong source folder, enter another path:");
                filesArray = new File(reader.readLine().trim()).listFiles();
                if (filesArray != null) {
                    break;
                }
            }
        }

        Map<Integer, ArrayList<File>> sortedMap = FilesSorter
            .sortFilesBySize(filesArray, processors);
        long start = System.currentTimeMillis();
        for (int i = 0; i < processors; i++) {

            int finalI = i;
            Thread t = new Thread(() -> {
                try {
                    for (File file : sortedMap.get(finalI)) {
                        BufferedImage image = ImageIO.read(file);
                        if (image == null) {
                            continue;
                        }

                        imageResize(image, dstFolder, file);
                        System.out.println("Thread " + (finalI + 1));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println(
                    "Thread " + (finalI + 1) + " duration: " + (System.currentTimeMillis()
                        - start));
                latch.countDown();
            });
            t.start();
        }

        latch.await();
        System.out.println("Total duration: " + (System.currentTimeMillis() - start));
    }

    public static void imageResize(BufferedImage image, String dstFolder, File file)
        throws IOException {
        int newWidth = 300;
        int newHeight = (int) Math.round(
            image.getHeight() / (image.getWidth() / (double) newWidth)
        );
        BufferedImage newImage = new BufferedImage(
            newWidth, newHeight, BufferedImage.TYPE_INT_RGB
        );

        double widthStep = image.getWidth() / (double) newWidth;
        double heightStep = image.getHeight() / (double) newHeight;

        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {
                int rgb = image.getRGB((int) Math.round(x * widthStep),
                    (int) Math.round(y * heightStep));
                newImage.setRGB(x, y, rgb);
            }
        }

        File newFile = new File(dstFolder + "/" + file.getName());
        ImageIO.write(newImage, "jpg", newFile);
    }
}
