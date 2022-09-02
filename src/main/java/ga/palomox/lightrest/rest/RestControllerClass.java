package ga.palomox.lightrest.rest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.eclipse.jetty.server.Request;

import ga.palomox.lightrest.rest.annotations.Map;
import ga.palomox.lightrest.rest.model.EndpointPath;
import ga.palomox.lightrest.rest.model.Identity;
import ga.palomox.lightrest.rest.model.MappedMethod;
import ga.palomox.lightrest.rest.model.MatchResult;
import ga.palomox.lightrest.rest.model.ResponseEntity;
import ga.palomox.lightrest.rest.permissions.PermissionsManager;
import ga.palomox.lightrest.rest.permissions.RelationshipStorage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RestControllerClass<T, I extends Identity<?>, P> {
	private T restController;
	private IdentityManager<I> identityManager;
	private HashMap<EndpointPath, ArrayList<MappedMethod>> restMethods = new HashMap<>();
	private PermissionsManager<P> permsManager;
	
	public RestControllerClass(T restController, IdentityManager<I> idManager, PermissionsManager<P> permsManager) {
		this.restController = restController;
		this.identityManager = idManager;
		this.permsManager = permsManager;
		
		Class<?> controller = restController.getClass();
		Method[] methods = controller.getMethods();

		for (Method method : methods) {
			if (method.isAnnotationPresent(Map.class) && method.getReturnType() == ResponseEntity.class) {
				Map annotation = method.getAnnotation(Map.class);

				String path = annotation.path();

				MappedMethod mappedMethod = new MappedMethod(method, new EndpointPath(path), annotation.protocol(), this.getIdentityType());
				for (EndpointPath ePath : this.restMethods.keySet()) {
					if (ePath.getPath().equals(path)) {
						this.restMethods.get(ePath).add(mappedMethod);
						return;
					}
				}
				ArrayList<MappedMethod> temp = new ArrayList<>();
				temp.add(mappedMethod);
				this.restMethods.put(new EndpointPath(path), temp);

			}
		}
	}

	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String path = target.split("/", 2)[1];
		String protocol = baseRequest.getMethod();

		// We make sure the path is registered

		EndpointPath methodPath = null;
		MatchResult result = null;
		
		

		for (EndpointPath ePath : this.restMethods.keySet()) {
			var tempResult = ePath.matches(path);
			if (tempResult.matches()) {
				methodPath = ePath;
				result = tempResult;
				break;
			}
		}

		if (methodPath == null) {
			response.sendError(404, path + " not found");
			return;
		}

		// We fetch all the methods registered for said path
		var localMethods = this.restMethods.get(methodPath);

		// We filter the methods based on the HTTP protocol wanted
		var wantedMethod = localMethods.parallelStream().filter(mm -> mm.getProtocol().equalsIgnoreCase(protocol)).findFirst();
		// We check whether there is such method
		if (wantedMethod.isEmpty()) {
			// Return unsupported operation 404
			response.sendError(404, "Unsupported operation for " + path);
			return;
		}
		// It does exist
		MappedMethod mappedMethod = wantedMethod.get();

		Method method = mappedMethod.getMethod();

		ResponseEntity responseEntity;
		
		I identity = null;
		
		// We check whether we want authentication or it's not needed
		
		if(mappedMethod.needsAuth()) {
			Optional<I> identityOpt = this.identityManager.loadIdentity(baseRequest);
			if(identityOpt.isEmpty()) {
				// 403 because no session
				response.sendError(403, "This endpoint requires a session");
				return;
			}
			// There is a session
			identity = identityOpt.get();
			P userId = (P) identity.getId();
			// We check if there are wanted zanzibar relationships
			Optional<RelationshipStorage[]> optRelationships = mappedMethod.getRelationships();
			if(!optRelationships.isEmpty()) {
				var relationships = optRelationships.get();
				for(RelationshipStorage relationship : relationships) {
					String actualObject;
					if(relationship.isMapped()) {
						actualObject = result.getParams().get(relationship.getObject());
					} else {
						actualObject = relationship.getObject();
					}
					if(!permsManager.isAllowed(userId, relationship.getRelation(), relationship.getNamespace(), actualObject)) {
						response.sendError(403, "You are not allowed to access this endpoint");
						return; 
					}
				}
			}

		}
		
		
		

		// We check for parameters of the method
		if (mappedMethod.getParameters().isEmpty()) {
			// We invoke the method with empty params
			try {
				responseEntity = (ResponseEntity) method.invoke(this.restController, new Object[0]);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return;
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				return;
			}
		} else {
			// We need to provide the stored params
			Object[] parameters = new Object[mappedMethod.getParameters().keySet().size()];
			for (int position : mappedMethod.getParameters().keySet()) {
				String paramName = mappedMethod.getParameters().get(position);
				switch (paramName) {
				case "baseRequest" -> {
					// We send the baseRequest
					parameters[position] = baseRequest;
				}
				case "servletRequest" -> {
					// We send the HttpServletRequest
					parameters[position] = request;
				}
				case "identity" -> {
					// We send them the Identity with the desired type 
					parameters[position] = identity;
				}
				default -> {
					// it is an url param
					parameters[position] = result.getParams().get(paramName);
				}
				}
			}
			
			// We can finally invoke the method 
			try {
				responseEntity = (ResponseEntity) method.invoke(this.restController, parameters);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return;
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				return;
			}
			
			// We parse the responseEntity into the servlet response
			responseEntity.parse(response);
		}

	}
	
	public Class<?> getIdentityType(){
		return this.identityManager.getIdentityClass();
	}

}