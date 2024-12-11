package course.hibernate.spring.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class User_ extends course.hibernate.spring.entity.EntityBase_ {

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String PASSWORD = "password";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String ROLES = "roles";
	public static final String ACTIVE = "active";
	public static final String USERNAME = "username";
	public static final String GRAPH_USER_DETAIL = "User.detail";

	
	/**
	 * @see course.hibernate.spring.entity.User#firstName
	 **/
	public static volatile SingularAttribute<User, String> firstName;
	
	/**
	 * @see course.hibernate.spring.entity.User#lastName
	 **/
	public static volatile SingularAttribute<User, String> lastName;
	
	/**
	 * @see course.hibernate.spring.entity.User#password
	 **/
	public static volatile SingularAttribute<User, String> password;
	
	/**
	 * @see course.hibernate.spring.entity.User#phoneNumber
	 **/
	public static volatile SingularAttribute<User, String> phoneNumber;
	
	/**
	 * @see course.hibernate.spring.entity.User#roles
	 **/
	public static volatile SetAttribute<User, Role> roles;
	
	/**
	 * @see course.hibernate.spring.entity.User#active
	 **/
	public static volatile SingularAttribute<User, Boolean> active;
	
	/**
	 * @see course.hibernate.spring.entity.User
	 **/
	public static volatile EntityType<User> class_;
	
	/**
	 * @see course.hibernate.spring.entity.User#username
	 **/
	public static volatile SingularAttribute<User, String> username;

}

