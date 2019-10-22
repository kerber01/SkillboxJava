import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDateTime;
import java.util.List;

public class Loader {

    public static void main(String[] args) {

        try (SessionFactory factory = new Configuration().
                configure("hibernate.cfg.xml")
                .buildSessionFactory();
             Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

//            CriteriaQuery<Subscription> subscriptionCriteriaQuery = session.getCriteriaBuilder().createQuery(Subscription.class);
//            subscriptionCriteriaQuery.from(Subscription.class);
//
//            List<Subscription> subscriptionsList = session.createQuery(subscriptionCriteriaQuery).getResultList();
//
//            CriteriaQuery<PurchaseList> purchaseListCriteriaQuery = session.getCriteriaBuilder().createQuery(PurchaseList.class);
//            purchaseListCriteriaQuery.from(PurchaseList.class);
//
//            List<PurchaseList> purchaseLists = session.createQuery(purchaseListCriteriaQuery).getResultList();
//
//            for (Subscription s: subscriptionsList){
//                for (PurchaseList p: purchaseLists){
//                    if (p.getStudentId() == null && s.getStudent().getName().equals(p.getId().getStudentName())){
//                        p.setStudentId(s.getStudent().getId());
//                    }
//                    if (p.getCourseId() == null && s.getCourse().getName().equals(p.getId().getCourseName())){
//                        p.setCourseId(s.getCourse().getId());
//                    }
//                }
//            }

            transaction.commit();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
