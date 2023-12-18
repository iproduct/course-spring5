package course.hibernate.spring.init;

import course.hibernate.spring.entity.Book;
import course.hibernate.spring.entity.Person;
import course.hibernate.spring.entity.Person_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;

@Component
@Slf4j
public class WhereFilterDemo implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate template;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        template.executeWithoutResult(status -> {
            Person josh = new Person(1L, "Joshua", "Bloch",
                    LocalDate.of(1965, 8, 11));
            Book effectiveJava2ed = new Book(1L, "Effective Java", josh, "0134685997", 2015,
                    "The second edition covers language and library features added in Java 7, 8, and 9, including the functional programming ",
                    null,
                    "As in previous editions, each chapter of Effective Java, Second Edition, consists of several “items,” each presented in the form of a short, stand-alone essay that provides specific advice, insight into Java platform subtleties, and updated code examples. The comprehensive descriptions and explanations for each item illuminate what to do, what not to do, and why.\n"
            );
            Book effectiveJava3ed = new Book(2L, "Effective Java 3-nd ed.", josh, "01346852334", 2020,
                    "The third edition covers language and library features added in Java 7, 8, and 9, including the functional programming ",
                    null,
                    "Java has changed dramatically since the previous edition of Effective Java was published shortly after the release of Java 6. This Jolt award-winning classic has now been thoroughly updated to take full advantage of the latest language and library features. The support in modern Java for multiple paradigms increases the need for specific best-practices advice, and this book delivers.\n" +
                            "As in previous editions, each chapter of Effective Java, Third Edition, consists of several “items,” each presented in the form of a short, stand-alone essay that provides specific advice, insight into Java platform subtleties, and updated code examples. The comprehensive descriptions and explanations for each item illuminate what to do, what not to do, and why.\n" +
                            "The third edition covers language and library features added in Java 7, 8, and 9, including the functional programming constructs that were added to its object-oriented roots. Many new items have been added, including a chapter devoted to lambdas and streams."
            );
            Book effectiveJava4ed = new Book(3L, "Effective Java 4-th ed.", josh, "0134685342", 2022,
                    "The forth edition covers language and library features added in Java 7, 8, and 9, including the functional programming ",
                    null,
                    "Java has changed dramatically since the previous edition of Effective Java was published shortly after the release of Java 6. This Jolt award-winning classic has now been thoroughly updated to take full advantage of the latest language and library features. The support in modern Java for multiple paradigms increases the need for specific best-practices advice, and this book delivers.\n" +
                            "As in previous editions, each chapter of Effective Java, Third Edition, consists of several “items,” each presented in the form of a short, stand-alone essay that provides specific advice, insight into Java platform subtleties, and updated code examples. The comprehensive descriptions and explanations for each item illuminate what to do, what not to do, and why.\n" +
                            "The third edition covers language and library features added in Java 7, 8, and 9, including the functional programming constructs that were added to its object-oriented roots. Many new items have been added, including a chapter devoted to lambdas and streams."
            );
            Person martin = new Person(2L, "Martin", "Fowler",
                    LocalDate.of(1939, 4, 15));
            Book umlDistilled = new Book(4L, "Uml Distilled",martin, "9780321193681", 2009,
                    "More than 300,000 developers have benefited from past editions of UML Distilled . This third edition is the best resource for quick, no-nonsense insights into understanding and using UML 2.0 and prior versions of the UML",
                    null,
                    "Some readers will want to quickly get up to speed with the UML 2.0 and learn the essentials of the UML. Others will use this book as a handy, quick reference to the most common parts of the UML. The author delivers on both of these promises in a short, concise, and focused presentation." +
                            "This book describes all the major UML diagram types, what they're used for, and the basic notation involved in creating and deciphering them. These diagrams include class, sequence, object, package, deployment, use case, state machine, activity, communication, composite structure, component, interaction overview, and timing diagrams. The examples are clear and the explanations cut to the fundamental design logic. Includes a quick reference to the most useful parts of the UML notation and a useful summary of diagram types that were added to the UML 2.0." +
                            "If you are like most developers, you don't have time to keep up with all the new innovations in software engineering. This new edition of Fowler's classic work gets you acquainted with some of the best thinking about efficient object-oriented software design using the UML--in a convenient format that will be essential to anyone who designs software professionally."
            );
            Person john = new Person(3L, "John", "Doe",
                    LocalDate.of(1978, 8, 11));
            Person jane = new Person(4L, "Jane", "Doe",
                    LocalDate.of(1981, 8, 11));

            entityManager.persist(josh);
            entityManager.persist(martin);
            entityManager.persist(john);
            entityManager.persist(jane);
            josh.getBooks().add(effectiveJava2ed);
            entityManager.persist(effectiveJava2ed);
            josh.getBooks().add(effectiveJava3ed);
            entityManager.persist(effectiveJava3ed);
            josh.getBooks().add(effectiveJava4ed);
            entityManager.persist(effectiveJava4ed);
            martin.getBooks().add(umlDistilled);
            entityManager.persist(umlDistilled);
        });

        template.executeWithoutResult(status -> {
//            entityManager.unwrap(Session.class)
//                    .enableFilter("recentBooks");
            EntityGraph<?> eg = entityManager.getEntityGraph("Person.withBooks");
//            List<Person> persons = entityManager.unwrap(Session.class)
//                    .createQuery("select distinct p from Person p", Person.class)
////                    .setParameter("lastName", "Bloch")
//                    .setHint("org.hibernate.cacheable", "true")
//                    .setHint("jakarta.persistence.fetchgraph", eg)
//                    .setHint("org.hibernate.readOnly", true)
//                    .setReadOnly(true)
//                    .getResultList();
//            persons.forEach(p -> log.info(">>> {}, Books: {}", p, p.getBooks()));
//        });

            var builder = entityManager.getCriteriaBuilder();
            var criteria = builder.createQuery(Person.class);
            var root = criteria.from(Person.class);
            criteria.select(root);
            criteria.distinct(true);
            criteria.where(builder.equal(root.get(Person_.lastName), "Bloch"));
            var p = entityManager.createQuery(criteria)
                    .setHint("jakarta.persistence.fetchgraph", eg)
                    .getSingleResult();
            log.info(">>> {}, Books: {}", p, p.getBooks());
        });
//        template.executeWithoutResult(status -> {
//            List<Person> persons = entityManager.unwrap(Session.class)
//                    .createQuery(
//                    "select p from Person p, IN (p.books) b group by p.id having count(b) = 2", Person.class)
//                    .getResultList();
//            persons.forEach(p -> log.info(">>> {}", p));
//        });
//        template.executeWithoutResult(status -> {
//            List<Person> persons = entityManager.unwrap(Session.class)
//                    .createQuery(
//                    "select p from Person p where p.books is empty", Person.class)
//                    .getResultList();
//            persons.forEach(p -> log.info(">>> {}", p));
//        });

//        template.executeWithoutResult(status -> {
//            List<Person> persons = entityManager.unwrap(Session.class)
//                    .createQuery(
//                    "select distinct p from Person p, in(p.books) b where b.year >= :startYear", Person.class)
//                    .setParameter("startYear", 2009)
//                    .getResultList();
//            persons.forEach(p -> log.info(">>> {}", p));
//        });

//        Query query = entityManager.createQuery(
//                        "select p " +
//                                "from Person p " +
//                                "where p.lastName like :lname")
//                // timeout - in milliseconds
//                .setHint("jakarta.persistence.query.timeout", 2000)
//                // flush only at commit time
//                .setFlushMode(FlushModeType.COMMIT);

//        try ( ScrollableResults scrollableResults = session.createQuery(
//                        "select p " +
//                                "from Person p " +
//                                "where p.name like :name" )
//                .setParameter( "name", "J%" )
//                .scroll()
//        ) {
//            while(scrollableResults.next()) {
//                Person person = (Person) scrollableResults.get()[0];
//                process(person);
//            }
//        }

//        try ( Stream<Object[]> persons = session.createQuery(
//                        "select p.name, p.nickName " +
//                                "from Person p " +
//                                "where p.name like :name" )
//                .setParameter( "name", "J%" )
//                .stream() ) {
//
//            persons
//                    .map( row -> new PersonNames(
//                            (String) row[0],
//                            (String) row[1] ) )
//                    .forEach( this::process );
//        }
    }


}
