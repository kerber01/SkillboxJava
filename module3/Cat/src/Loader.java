
public class Loader {

  public static void main(String[] args) {
    Cat cat = Loader.generateCat(1000.0);

    System.out.println(cat.getWeight());
    System.out.println("Cat population count: " + Cat.getCount());
    System.out.println("cat is " + cat.getStatus());

    cat.feed(900.0);
    Cat murzik = Cat.copy(cat);
    System.out.println(cat.getWeight());
    System.out.println(murzik.getWeight());
    System.out.println(cat.getFoodAmount());
    System.out.println(murzik.getFoodAmount());

    System.out.println("cat is " + cat.getStatus());

    System.out.println("Cat population count: " + Cat.getCount());

    cat.feed(9000.0);
    System.out.println(cat.getWeight());
    System.out.println("cat is " + cat.getStatus());

    System.out.println("Cat population count: " + Cat.getCount());

    System.out.println(cat.getColor());

    Cat vaska = Cat.copy(cat);
    System.out.println(cat.getWeight());
    System.out.println(cat.getFoodAmount());
    System.out.println(vaska.getWeight());
    System.out.println(vaska.getFoodAmount());

    System.out.println("Cat population count: " + Cat.getCount());
  }

  public static Cat generateCat(double weight) {
    return new Cat(weight);
  }
}