package course.hibernate.spring.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UserInfo.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class UserInfo_ {

	
	/**
	 * @see course.hibernate.spring.entity.UserInfo#qualifications
	 **/
	public static volatile SingularAttribute<UserInfo, String> qualifications;
	
	/**
	 * @see course.hibernate.spring.entity.UserInfo#id
	 **/
	public static volatile SingularAttribute<UserInfo, Long> id;
	
	/**
	 * @see course.hibernate.spring.entity.UserInfo
	 **/
	public static volatile EntityType<UserInfo> class_;
	
	/**
	 * @see course.hibernate.spring.entity.UserInfo#user
	 **/
	public static volatile SingularAttribute<UserInfo, User> user;

	public static final String QUALIFICATIONS = "qualifications";
	public static final String ID = "id";
	public static final String USER = "user";

}

