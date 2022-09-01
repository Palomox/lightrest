package ga.palomox.lightrest.rest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(value = Relationships.class)
/**
 * marks a zanzibar relationship required to be true to execute
 * @author palomox
 *
 */
public @interface Relationship {
	/**
	 * @return the relation name
	 */
	public String relation();
	/**
	 * 
	 * @return the namespace of the object
	 */
	public String namespace();
	/**
	 * @return the name of the wanted object. Usually this will be a path variable, to enable this functionality, set isPathVar to true.
	 */
	public String object();
	/**
	 * @return whether the object name is a pathvar
	 */
	public boolean isPathVar();
}
