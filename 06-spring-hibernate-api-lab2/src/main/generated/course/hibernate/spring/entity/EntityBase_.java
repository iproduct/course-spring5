package course.hibernate.spring.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.MappedSuperclassType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(EntityBase.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class EntityBase_ {

	
	/**
	 * @see course.hibernate.spring.entity.EntityBase#created
	 **/
	public static volatile SingularAttribute<EntityBase, LocalDateTime> created;
	
	/**
	 * @see course.hibernate.spring.entity.EntityBase#modified
	 **/
	public static volatile SingularAttribute<EntityBase, LocalDateTime> modified;
	
	/**
	 * @see course.hibernate.spring.entity.EntityBase#id
	 **/
	public static volatile SingularAttribute<EntityBase, Long> id;
	
	/**
	 * @see course.hibernate.spring.entity.EntityBase
	 **/
	public static volatile MappedSuperclassType<EntityBase> class_;

	public static final String CREATED = "created";
	public static final String MODIFIED = "modified";
	public static final String ID = "id";

}

