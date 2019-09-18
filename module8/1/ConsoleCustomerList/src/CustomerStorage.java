import java.util.HashMap;
import java.util.regex.Pattern;

public class CustomerStorage {

  private HashMap<String, Customer> storage;

  public CustomerStorage() {
    storage = new HashMap<>();
  }

  public void addCustomer(String data) {
    String[] components = data.split("\\s+");
    try {
      String name = components[0] + " " + components[1];
      storage.put(name, new Customer(name, components[3], components[2]));

      Pattern eMailPattern = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
      Pattern phoneNumPattern = Pattern.compile("([+]\\d{12}$)");

      if (!eMailPattern.matcher(components[2]).matches()) {
        throw new WrongFormatException("Wrong e-mail format, please try again");
      }
      if (!phoneNumPattern.matcher(components[3]).matches()) {
        throw new WrongFormatException("Wrong phone format, please try again");
      }

    } catch (WrongFormatException e) {
      System.out.println(e.getMessage());
    }
  }

  public void listCustomers() {
    storage.values().forEach(System.out::println);
  }

  public void removeCustomer(String name) {

    storage.remove(name);

  }

  public int getCount() {
    return storage.size();
  }
}