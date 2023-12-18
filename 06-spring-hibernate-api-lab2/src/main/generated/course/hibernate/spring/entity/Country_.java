package course.hibernate.spring.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Country.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Country_ {

	
	/**
	 * @see course.hibernate.spring.entity.Country#name
	 **/
	public static volatile SingularAttribute<Country, String> name;
	
	/**
	 * @see course.hibernate.spring.entity.Country#id
	 **/
	public static volatile SingularAttribute<Country, Integer> id;
	
	/**
	 * @see course.hibernate.spring.entity.Country
	 **/
	public static volatile EntityType<Country> class_;

	public static final String NAME = "name";
	public static final String ID = "id";

}

