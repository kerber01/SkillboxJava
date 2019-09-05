import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Loader {

  public static void main(String[] args) throws IOException {
    System.out.println("Введите Фамилию Имя Отчество:");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String input = reader.readLine().trim();

    String[] strArray = input.split(" ");

    System.out.println("Фамилия: " + strArray[0]);
    System.out.println("Имя: " + strArray[1]);
    System.out.println("Отчество: " + strArray[2]);
  }

}
