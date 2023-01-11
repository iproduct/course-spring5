package demos.hibernate;

import demos.hibernate.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class JpaDemoMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school");
        EntityManager em = emf.createEntityManager();
        User user = new User("John Smith");
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        System.out.printf("!!! User identity: %s\n",
                Integer.toHexString(System.identityHashCode(user)));

        em.getTransaction().begin();
        em.detach(user); // if you comment out detach the returned object identity from merge() will not change
        user.setName("J. Smith");
        User returnedUser = em.merge(user);
        em.getTransaction().commit();
        System.out.printf("!!!  Given user identity: %s, Returned user identity: %s\n",
                Integer.toHexString(System.identityHashCode(user)),
                Integer.toHexString(System.identityHashCode(returnedUser)));

        User u = em.find(User.class, 1);
        System.out.printf("User: %s\n", u);

        em.getTransaction().begin();
//        List<Student> users =
        em.createQuery("SELECT u FROM User u", User.class)
                .setFirstResult(0)
                .setMaxResults(5)
                .getResultList()
                .stream()
                .forEach(System.out::println);
        em.getTransaction().commit();


        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery criteria = builder.createQuery();
        Root<User> r = criteria.from(User.class);
        criteria.select(r).where(builder.like(r.get("name"), "J%"));
        em.createQuery(criteria).getResultList()
                .stream().forEach(System.out::println);

        em.close();
    }
}
