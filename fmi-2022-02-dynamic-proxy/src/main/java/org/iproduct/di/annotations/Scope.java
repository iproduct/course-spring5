package org.iproduct.di.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.iproduct.di.BeanScope;

@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface Scope {
	BeanScope value() default BeanScope.SINGLETON;
}
