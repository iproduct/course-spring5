package course.hibernate.spring.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(City.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class City_ {

	
	/**
	 * @see course.hibernate.spring.entity.City#name
	 **/
	public static volatile SingularAttribute<City, String> name;
	
	/**
	 * @see course.hibernate.spring.entity.City#id
	 **/
	public static volatile SingularAttribute<City, Long> id;
	
	/**
	 * @see course.hibernate.spring.entity.City
	 **/
	public static volatile EntityType<City> class_;

	public static final String NAME = "name";
	public static final String ID = "id";

}

