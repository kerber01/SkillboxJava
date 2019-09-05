import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Loader
{
    public static void main(String[] args) throws IOException {
        System.out.println("Введите необходимое количество коробок для транспортировки:");
        int boxesCount = Integer.parseInt((new BufferedReader(new InputStreamReader(System.in))).readLine().trim());
        int containersCount = boxesCount / 27;
        int trucksCount = containersCount / 12;
        int boxCounter = 0;

        if (boxesCount%27 > 0)
        {
            containersCount = containersCount + 1;
        }
        if (containersCount%12 > 0)
        {
            trucksCount = trucksCount + 1;
        }
        System.out.println("Для перевозки гуманитарной помощи понадобится:\n" + "грузовиков - " +  trucksCount + "\nконтейнеров - " + containersCount);
        System.out.println();
        for (int i = 0; i < trucksCount; i++)
        {
            System.out.println("Грузовик " + (i + 1) + ": ");
            for (int j = 0; j < 12; j++)
            {
                System.out.println("\t\t   Контейнер " + (j + 1) + ": ");
                for (int t = 0; t < 27; t++)
                {
                    System.out.println("\t\t\t\t\t   Ящик " + (boxCounter + 1));
                    boxCounter++;
                    boxesCount--;
                    if (boxesCount < 1)
                    {
                        break;
                    }
                }
                containersCount--;
                if (containersCount < 1)
                {
                    break;
                }
            }
        }
    }
}
