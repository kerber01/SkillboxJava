
public class Loader {

  public static void main(String[] args) {
    Cat cat = Loader.generateCat(1000.0);

    System.out.println(cat.getWeight());
    System.out.println("Cat population count: " + Cat.getCount());
    System.out.println("cat is " + cat.getStatus());

    cat.feed(900.0);

    System.out.println(cat.getWeight());
    System.out.println("cat is " + cat.getStatus());

    System.out.println("Cat population count: " + Cat.getCount());

    cat.feed(9000.0);
    System.out.println(cat.getWeight());
    System.out.println("cat is " + cat.getStatus());

    System.out.println("Cat population count: " + Cat.getCount());

    System.out.println(cat.getColor());



  }

  public static Cat generateCat(double weight){
      Cat cat = new Cat(weight);
    return cat;
  }
}