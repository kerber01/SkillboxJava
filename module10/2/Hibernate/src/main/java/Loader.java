import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.LocalDateTime;

public class Loader {

    public static void main(String[] args) {

        try (SessionFactory factory = new Configuration().
                configure("hibernate.cfg.xml")
                .buildSessionFactory();
             Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

//            Student student = session.get(Student.class, 5);
//            student.getSubscriptionList().forEach(System.out::println);
            Student boris = new Student();
            boris.setRegistrationDate(LocalDateTime.now());
            boris.setName("Борис");

            session.save(boris);

            transaction.commit();

            Transaction newCourse = session.beginTransaction();

            Course course = new Course();
            course.setType(CourseType.PROGRAMMING);
            course.setName("Прокрастинация от 0 до ПРО");

            session.save(course);
            newCourse.commit();

            Transaction newSub = session.beginTransaction();
            Subscription subscription = new Subscription(new SubscriptionId(boris.getId(), course.getId()), LocalDateTime.now(), boris, course);
            session.save(subscription);
            newSub.commit();

            Transaction last = session.beginTransaction();
            System.out.println(session.get(Subscription.class, subscription.getId()).getStudent().getName());
            last.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
