@AnyMetaDef( name= "PropertyMetaDef", metaType = "char", idType = "long",
metaValues = {
        @MetaValue(value="S", targetEntity = StringProperty.class),
        @MetaValue(value="I", targetEntity = IntegerProperty.class)
})
package course.hibernate.spring.entity;

import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;
