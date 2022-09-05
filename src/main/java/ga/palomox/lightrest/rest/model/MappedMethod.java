package ga.palomox.lightrest.rest.model;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Optional;

import org.eclipse.jetty.server.Request;

import ga.palomox.lightrest.rest.annotations.Authenticated;
import ga.palomox.lightrest.rest.annotations.PathVariable;
import ga.palomox.lightrest.rest.annotations.Relationship;
import ga.palomox.lightrest.rest.annotations.Weigh;
import ga.palomox.lightrest.rest.permissions.RelationshipStorage;
import jakarta.servlet.http.HttpServletRequest;

public class MappedMethod {
	
	private Method method;
	private EndpointPath path; 
	private String protocol; 
	private boolean needsAuth;
	// Stores string for key of the urlparam and position of the one in the method.
	private HashMap<Integer, String> parameters; 
	private RelationshipStorage[] relationships = null;
	private int weigh;
 	
	public MappedMethod(Method method, EndpointPath path, String protocol, Class<?> idClass) {
		this.method = method;
		this.path = path; 
		this.protocol = protocol; 
		
		// We check if annotated with authentcation
		this.needsAuth = this.method.isAnnotationPresent(Authenticated.class);
		
		this.parameters = new HashMap<>();
		
		Parameter[] parameters = method.getParameters();
		
		for(int i=0; i<parameters.length; i++) {
			Parameter parameter = parameters[i];
			if(parameter.isAnnotationPresent(PathVariable.class)) {
				this.parameters.put(i, ((PathVariable) parameter.getAnnotation(PathVariable.class)).name());
			}
			if(parameter.getType() == Request.class) {
				this.parameters.put(i, "baseRequest");
			}
			if(parameter.getType() == HttpServletRequest.class) {
				this.parameters.put(i, "servletRequest");
			}
			if(parameter.getType() == idClass) {
				this.parameters.put(i, "identity");
			}
		}
		
		if(this.method.isAnnotationPresent(Relationship.class)){
			// There is a relationship annotation, we have to translate it to a storage
			Relationship[] annotations = this.method.getAnnotationsByType(Relationship.class);
			RelationshipStorage[] relationships = new RelationshipStorage[annotations.length];
 			for(int i=0; i<annotations.length; i++) {
 				Relationship relationship = annotations[i];
				relationships[i] = new RelationshipStorage(relationship.relation(), relationship.namespace(), relationship.object(), relationship.isPathVar());
			}
 			this.relationships = relationships;
 			if(this.method.isAnnotationPresent(Weigh.class)) {
 				// There is a weigh specification
 				this.weigh = this.method.getAnnotation(Weigh.class).value();
 			} else {
 				this.weigh = 0; 
 			}
		}
		
	}


	public Method getMethod() {
		return method;
	}


	public EndpointPath getPath() {
		return path;
	}
	
	public Optional<RelationshipStorage[]> getRelationships() {
		if(this.relationships == null) {
			return Optional.empty();
		}
		return Optional.of(this.relationships);
	}

	public String getProtocol() {
		return protocol;
	}

	public boolean needsAuth() {
		return this.needsAuth;
	}
	public HashMap<Integer, String> getParameters() {
		return parameters;
	}
	public int getWeigh() {
		return this.weigh;
	}
	
}
