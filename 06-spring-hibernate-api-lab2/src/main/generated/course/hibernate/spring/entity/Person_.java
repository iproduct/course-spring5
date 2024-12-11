package course.hibernate.spring.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(Person.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Person_ {

	public static final String GRAPH_PERSON_WITH_BOOKS = "Person.withBooks";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String BOOKS = "books";
	public static final String DATE_OF_BIRTH = "dateOfBirth";
	public static final String ID = "id";

	
	/**
	 * @see course.hibernate.spring.entity.Person#firstName
	 **/
	public static volatile SingularAttribute<Person, String> firstName;
	
	/**
	 * @see course.hibernate.spring.entity.Person#lastName
	 **/
	public static volatile SingularAttribute<Person, String> lastName;
	
	/**
	 * @see course.hibernate.spring.entity.Person#books
	 **/
	public static volatile ListAttribute<Person, Book> books;
	
	/**
	 * @see course.hibernate.spring.entity.Person#dateOfBirth
	 **/
	public static volatile SingularAttribute<Person, LocalDate> dateOfBirth;
	
	/**
	 * @see course.hibernate.spring.entity.Person#id
	 **/
	public static volatile SingularAttribute<Person, Long> id;
	
	/**
	 * @see course.hibernate.spring.entity.Person
	 **/
	public static volatile EntityType<Person> class_;

}

