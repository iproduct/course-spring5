package org.iproduct.di.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
@Repeatable(Schedules.class)
public @interface Schedule {
	String dayOfMonth() default "first";

	String dayOfWeek() default "Mon";

	int hour() default 12;
}