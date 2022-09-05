package ga.palomox.lightrest.rest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Used to mark the weigh of a rest endpoint if there are more than one with the same protocol and path.
 * Usually used to make multiple endpoints with same path and protocol but different permission levels.
 * 
 * The endpoints with higher weigh get tried first
 * @author palomox
 *
 */
public @interface Weigh {
	/**
	 * The weigh of this method
	 * @return the weigh
	 */
	public int value();
}
