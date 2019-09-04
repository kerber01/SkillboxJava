import java.util.Random;

public enum Color {
  BLACK, WHITE, GRAY;

  public static Color getRandomColor() {
    Random random = new Random();
    return values()[random.nextInt(values().length)];
  }
}



