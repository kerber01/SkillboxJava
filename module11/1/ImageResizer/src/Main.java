import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        String srcFolder = "D:/src";
        String dstFolder = "D:/dst";

        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU cores: " + processors);

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] filesArray = srcDir.listFiles();
        int len = filesArray.length / processors;

        for (int i = 0; i < processors; i++) {
            File[] files;
            if (i == processors - 1 && processors != 1) {
                files = new File[filesArray.length - len * i];
            } else {
                files = new File[len];
            }
            System.arraycopy(filesArray, len * i, files, 0, files.length);

            int finalI = i;
            Thread t = new Thread(() -> {
                try {
                    for (File file : files) {
                        BufferedImage image = ImageIO.read(file);
                        if (image == null) {
                            continue;
                        }

                        int newWidth = 300;
                        int newHeight = (int) Math.round(
                            image.getHeight() / (image.getWidth() / (double) newWidth)
                        );
                        BufferedImage newImage = new BufferedImage(
                            newWidth, newHeight, BufferedImage.TYPE_INT_RGB
                        );

                        int widthStep = image.getWidth() / newWidth;
                        int heightStep = image.getHeight() / newHeight;

                        for (int x = 0; x < newWidth; x++) {
                            for (int y = 0; y < newHeight; y++) {
                                int rgb = image.getRGB(x * widthStep, y * heightStep);
                                newImage.setRGB(x, y, rgb);
                            }
                        }

                        File newFile = new File(dstFolder + "/" + file.getName());
                        ImageIO.write(newImage, "jpg", newFile);
                        System.out.println("Thread " + (finalI+1) );
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println("Thread " + (finalI+1) + " duration: " + (System.currentTimeMillis() - start));
            });
            t.start();
        }

    }
}
