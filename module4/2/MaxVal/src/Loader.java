import java.util.Arrays;

public class Loader {

  public static void main(String[] args) {
//    System.out.println("Double min value is: " + -Double.MAX_VALUE);
//    System.out.println("Double max value is: " + Double.MAX_VALUE + "\n");
//    System.out.println("Float min value is: " + -Float.MAX_VALUE);
//    System.out.println("Float max value is: " + Float.MAX_VALUE + "\n");
//    System.out.println("Byte min value is: " + Byte.MIN_VALUE);
//    System.out.println("Byte max value is: " + Byte.MAX_VALUE + "\n");
//    System.out.println("Short min value is: " + Short.MIN_VALUE);
//    System.out.println("Short max value is: " + Short.MAX_VALUE + "\n");
//    System.out.println("Integer min value is: " + Integer.MIN_VALUE);
//    System.out.println("Integer max value is: " + Integer.MAX_VALUE + "\n");
//    System.out.println("Long min value is: " + Long.MIN_VALUE);
//    System.out.println("Long max value is: " + Long.MAX_VALUE);
      String test = "https://skillbox.ru/";
    System.out.println(Arrays.toString(test.substring(20).split("/")));
    System.out.println(test.length());
    System.out.println(Arrays.toString(test.substring(20).split("/")).length());
  }

}
