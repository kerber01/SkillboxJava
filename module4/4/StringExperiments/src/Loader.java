import java.io.IOException;

public class Loader {

  public static void main(String[] args) throws IOException {
    String text = "Вася заработал 5000 рублей, Петя - 7563 рубля, а Маша - 30000 рублей";

    System.out.println(text);
    int a = text.indexOf("л");
    int b = text.indexOf("руб", a);
    int c = text.lastIndexOf("а");
    int d = text.indexOf("руб", c);

    System.out.println(Integer.parseInt(text.substring(a + 1, b).trim()) + Integer
        .parseInt(text.substring(c + 3, d).trim()));
  }
}