package course.hibernate.spring.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Book.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Book_ {

	public static final String SUMMARY = "summary";
	public static final String IMAGE = "image";
	public static final String YEAR = "year";
	public static final String AUTHOR = "author";
	public static final String ISBN = "isbn";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String SAMPLE_CONTENT = "sampleContent";

	
	/**
	 * @see course.hibernate.spring.entity.Book#summary
	 **/
	public static volatile SingularAttribute<Book, String> summary;
	
	/**
	 * @see course.hibernate.spring.entity.Book#image
	 **/
	public static volatile SingularAttribute<Book, byte[]> image;
	
	/**
	 * @see course.hibernate.spring.entity.Book#year
	 **/
	public static volatile SingularAttribute<Book, Integer> year;
	
	/**
	 * @see course.hibernate.spring.entity.Book#author
	 **/
	public static volatile SingularAttribute<Book, Person> author;
	
	/**
	 * @see course.hibernate.spring.entity.Book#isbn
	 **/
	public static volatile SingularAttribute<Book, String> isbn;
	
	/**
	 * @see course.hibernate.spring.entity.Book#id
	 **/
	public static volatile SingularAttribute<Book, Long> id;
	
	/**
	 * @see course.hibernate.spring.entity.Book#title
	 **/
	public static volatile SingularAttribute<Book, String> title;
	
	/**
	 * @see course.hibernate.spring.entity.Book
	 **/
	public static volatile EntityType<Book> class_;
	
	/**
	 * @see course.hibernate.spring.entity.Book#sampleContent
	 **/
	public static volatile SingularAttribute<Book, String> sampleContent;

}

