import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class FilesSorter {

    public FilesSorter() {
    }

    public static Map<Integer, ArrayList<File>> sortFilesBySize(File[] files, int partsCount) {
        Map<Integer, ArrayList<File>> sortedMap = new HashMap<>();
        Arrays.sort(files, new FileSizeComparator());

        for (int i = 0; i < partsCount; i++) {
            sortedMap.put(i, new ArrayList<>());
        }

        int count;
        boolean stop = false;
        for (int i = 0; i < files.length; i++) {
            count = i * partsCount;
            for (int j = 0; j < partsCount; j++) {
                if (count < files.length) {
                    sortedMap.get(j).add(files[count]);
                    count++;
                } else {
                    stop = true;
                    break;
                }
                if (stop) {
                    break;
                }
            }
        }
        for (int k = 0; k < sortedMap.size(); k++) {
            System.out.println(
                "Part " + (k + 1) + ":" + sortedMap.get(k).stream().mapToLong(File::length).sum()
                    + " bytes");
        }
        return sortedMap;
    }

}

class FileSizeComparator implements Comparator<File> {

    public int compare(File a, File b) {
        long aSize = a.length();
        long bSize = b.length();
        if (aSize == bSize) {
            return 0;
        } else {
            return Long.compare(aSize, bSize);
        }
    }
}
