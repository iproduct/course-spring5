package demos.hibernate;

import com.zaxxer.hikari.HikariDataSource;
import demos.hibernate.model.Student;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class HibernateDemoMain {
    public static void main(String[] args) throws IOException {

        // load connection properties from file
        Properties props = new Properties();
        String appConfigPath = HibernateDemoMain.class.getClassLoader()
                .getResource("db.properties").getPath();
        props.load(new FileInputStream(appConfigPath));

        // Configure the production grade Hikari datasource
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(props.getProperty("driver"));
        dataSource.setJdbcUrl(props.getProperty("url"));//change url
        dataSource.setUsername(props.getProperty("user"));//change username
        dataSource.setPassword(props.getProperty("password"));//change pwd

        //Create Hibernate config
        Configuration cfg = new Configuration();
        cfg.configure();

        //Augment with custom datasource
        try(SessionFactory sessionFactory = cfg
                .buildSessionFactory(
                        new StandardServiceRegistryBuilder()
                                .applySettings(cfg.getProperties())
                                //here you apply the custom dataSource
                                .applySetting(Environment.DATASOURCE, dataSource)
                                .configure()
                                .build());
//        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession()) {

//            System.out.println(session.getSessionFactory().getProperties());
            Student student = new Student("Ivan Petrov");
            try {
                session.beginTransaction();
                session.save(student);
                session.detach(student);
                student.setName("I. Petrov");
                session.save(student);
                // difference between save and persist is that persist throws exception when trying to persist detached entity
//                session.persist(student);
//                session.detach(student);
//                session.persist(student); //throws: org.hibernate.PersistentObjectException: detached entity passed to persist: demos.hibernate.model.Student
                session.getTransaction().commit();
            } catch (Exception e) {
                if (session.getTransaction() != null)
                    session.getTransaction().rollback();
                throw e;
            }

            session.beginTransaction();
            session.setHibernateFlushMode(FlushMode.MANUAL);
            Student s1 = session.get(Student.class, 1, LockMode.READ);
            session.getTransaction().commit();

            System.out.printf("!!! Student: %s\n", s1);

            session.beginTransaction();
//            List<Student> students =
            session.createQuery("FROM Student", Student.class)
                    .setFirstResult(1)
                    .setMaxResults(5)
                    .getResultStream()
                    .forEach(System.out::println);
            session.getTransaction().commit();

            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery();
            Root<Student> r = criteria.from(Student.class);
            criteria.select(r).where(builder.like(r.get("name"), "I%"));
            List<Student> studentList = session.createQuery(criteria).getResultList();
            for (Student s : studentList) {
                System.out.println(s);
            }
            session.getTransaction().commit();

        }  //session.close();
    }

}
