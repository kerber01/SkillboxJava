import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Loader {

  public static void main(String[] args) {

    try (SessionFactory factory = new Configuration().
        configure("hibernate.cfg.xml")
        .buildSessionFactory();
        Session session = factory.openSession()) {

      Students student = session.get(Students.class, 5);
      System.out.println(student);


    } catch (Exception e){
      e.printStackTrace();
    }


  }

}
