package course.springdate.jpaintro;

import course.springdate.jpaintro.entity.Car;
import course.springdate.jpaintro.entity.PlateNumber;
import course.springdate.jpaintro.entity.Truck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class JpaCodeFirstMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school_jpa");
        EntityManager em = emf.createEntityManager();
        Car car1 = new Car("Audi A8", new BigDecimal(56000), "hybrid", 5);
        Truck truck1 = new Truck("Fuso Canter", new BigDecimal(56000), "hybrid", 5.5);
        em.getTransaction().begin();
        em.persist(car1);
        PlateNumber car1Plate = new PlateNumber(car1);
        car1.setPlate(car1Plate);
        em.persist(car1Plate);
        em.persist(truck1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        Car found = em.find(Car.class, 1L);
        System.out.printf("Found car1: %s%n", found);
        Car truck = em.find(Car.class, 2L);
        System.out.printf("Found truck1: %s%n", found);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createQuery("SELECT v FROM Vehicle v")   // WHERE c.name LIKE :name ORDER BY s.name")
//                .setParameter("name", "%")
                .getResultList().forEach(System.out::println);
        em.getTransaction().commit();

//        em.getTransaction().begin();
////        em.remove(found);
//        em.detach(found);
//        found.setName("Atanas Petrov");
//        Car managedEntity = em.merge(found);
////        System.out.printf("Same reference: %b", managedEntity == found);
//        System.out.printf("!!!  Given car1 identity: %s, Returned from merge car1 identity: %s\n",
//                Integer.toHexString(System.identityHashCode(found)),
//                Integer.toHexString(System.identityHashCode(managedEntity)));
////        Car removed = em.find(Car.class, 1L);
////        System.out.printf("Removed entity: %s",removed );
//        em.getTransaction().commit();

        em.close();
    }
}
