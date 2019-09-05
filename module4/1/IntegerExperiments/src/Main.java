public class Main {

  public static void main(String[] args) {
    Container container = new Container();
    container.count += 7843;

    System.out.println(sumDigits(12345645));

  }

  public static Integer sumDigits(Integer number) {
    int sum = 0;
    String s = number.toString();
    for (int i = 0; i < s.length(); i++) {
      sum += Character.getNumericValue(s.charAt(i));
    }
    return sum;
  }
}
