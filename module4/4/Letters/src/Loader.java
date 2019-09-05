public class Loader {

  public static void main(String[] args) {
    String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    for (int i = 0; i < letters.length(); i++) {
      System.out.println(letters.charAt(i) + " - " + (int) letters.charAt(i));
    }
  }

}
