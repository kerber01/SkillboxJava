public class Cat {

  private double originWeight;
  private double weight;
  private double foodAmount;
  private static int count;
  private String color;
  private final double MIN_WEIGHT = 1000;
  private final double MAX_WEIGHT = 9000;
  private final int EYES_COUNT = 2;

  public Cat() {
    weight = 1500.0 + 3000.0 * Math.random();
    originWeight = weight;
    foodAmount = 0.0;
    color = Color.getRandomColor().toString();
    count++;
  }

  public Cat(double weight) {
    this();
    this.weight = weight;
    originWeight = weight;
    if (!isAlive()){
      count--;
    }

  }

  public void meow() {
    if (isAlive()) {
      weight = weight - 1;
      System.out.println("Meow");
      if (!isAlive()){
        count--;
      }
    }
  }

  public void feed(Double amount) {
    if (isAlive()) {
      weight = weight + amount;
      foodAmount += amount;
      if (!isAlive()){
        count--;
      }
    }
  }

  public void drink(Double amount) {
    if (isAlive()) {
      weight = weight + amount;
      foodAmount += amount;
      if (!isAlive()){
        count--;
      }
    }
  }

  public void poo() {
    if (isAlive()) {
      weight -= 1000;
      System.out.println("poo complete");
      if (!isAlive()){
        count--;
      }
    }
  }

  public Double getWeight() {
    return weight;
  }

  public String getColor() {
    return color;
  }

  public double getFoodAmount() {
    return foodAmount;
  }

  public static int getCount() {
    return count;
  }

  private boolean isAlive() {
    if (weight >= MIN_WEIGHT && weight <= MAX_WEIGHT) {
      return true;
    } else {
      System.out.println("This cat is no more");
      return false;
    }
  }


  public String getStatus() {
    if (weight < MIN_WEIGHT) {
      return "Dead";
    } else if (weight > MAX_WEIGHT) {
      return "Exploded";
    } else if (weight > originWeight) {
      return "Sleeping";
    } else {
      return "Playing";
    }
  }
}