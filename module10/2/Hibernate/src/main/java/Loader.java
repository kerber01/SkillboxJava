import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Loader {

    public static void main(String[] args) {

        try (SessionFactory factory = new Configuration().
                configure("hibernate.cfg.xml")
                .buildSessionFactory();
             Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            List<Subscription> subscriptions = session.createQuery("" +
                    " from Subscription sub" +
                    " join fetch sub.course c" +
                    " join fetch sub.student s" +
                    "", Subscription.class).getResultList();

            List<PurchaseList> purchaseLists = session.createQuery(" from PurchaseList", PurchaseList.class).getResultList();

            subscriptions.forEach(s -> {
                purchaseLists.forEach(p -> {
                    if (p.getStudentId() == null && s.getStudent().getName().equals(p.getId().getStudentName())) {
                        p.setStudentId(s.getStudent().getId());
                    }
                    if (p.getCourseId() == null && s.getCourse().getName().equals(p.getId().getCourseName())) {
                        p.setCourseId(s.getCourse().getId());
                    }
                });
            });

            transaction.commit();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
