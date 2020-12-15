package course.springdata.codefirst;

import course.springdata.codefirst.entity.*;
import org.hibernate.query.criteria.internal.path.RootImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public class JpaCodeFirstMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("vehicles");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin(); //begin transaction

        // Persist cars and plate numbers
        Car car1 = new Car("Audi A8", new BigDecimal(56000), "hybrid", 5);
        em.persist(car1);
        PlateNumber car1PlateNumber = new PlateNumber("CB1234", car1);
        car1.setPlate(car1PlateNumber);
        em.persist(car1PlateNumber);

        // Persist trucks
        Truck truck1 = new Truck("Fuso Canter", new BigDecimal(120000), "gasoline", 5.5);
        em.persist(truck1);

        // Persist drivers
        Driver driver1 = new Driver("John Smith", Set.of(car1, truck1));
        car1.getDrivers().add(driver1);
        truck1.getDrivers().add(driver1);
        em.persist(driver1);

        // Persist company with all its planes using CascadeType.ALL
        Company company1 = new Company(new BigInteger("1234567890"), "Software AD");
        Plane plane1 = new Plane("Boing", new BigDecimal(1200000), "kerosene", 120, company1);
        Plane plane2 = new Plane("AirBus", new BigDecimal(1500000), "kerosene", 150, company1);
        Plane plane3 = new Plane("Pilatus Business Aircraft", new BigDecimal(890000), "kerosene", 80, company1);
        em.persist(plane1);
        em.persist(plane2);
        em.persist(plane3);
        company1.getPlanes().add(plane1);
        company1.getPlanes().add(plane2);
        company1.getPlanes().add(plane3);
        em.persist(company1);
        em.getTransaction().commit(); //end of transaction

        // Find different types of entities by Id
        Car found = em.find(Car.class, 1L);
        System.out.printf("Found car1: %s%n", found);
        Truck truck = em.find(Truck.class, 2L);
        System.out.printf("Found truck1: %s%n", truck);
        Driver driver = em.find(Driver.class, 1L);
        System.out.printf("Found driver1: %s%n", driver);

        // JPQL query
        em.createQuery("SELECT v FROM Vehicle v")   // WHERE c.name LIKE :name ORDER BY s.name")
//                .setParameter("name", "%")
                .getResultList().forEach(System.out::println);

        // Type safe Criteria query
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Company> query = cb.createQuery(Company.class);
        Root<Company> Company_ = query.from(Company.class);
        ParameterExpression<String> name = cb.parameter(String.class, "name");
        query.select(Company_).where(cb.equal(Company_.get("name"), name));
        TypedQuery<Company> typedQuery = em.createQuery(query);
        Company companyFound = typedQuery.setParameter("name", "Software AD").getSingleResult();

        System.out.printf("Company '%s' planes:%n", companyFound);
        for(Plane p : companyFound.getPlanes()) {
            System.out.println("---> " + p);
        }
    }

}
