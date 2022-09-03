package ga.palomox.lightrest;

import java.io.IOException;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import ga.palomox.lightrest.middleware.Middleware;
import ga.palomox.lightrest.rest.RestControllerClass;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ServerHandler extends AbstractHandler {
	
	private RestControllerClass<?, ?, ?> restController = LightrestServer.instance.getRestController();
	private boolean hasMiddlewares;
	private Middleware[] middlewares;
	
	public ServerHandler(RestControllerClass<?, ?, ?> restController, Middleware[] middlewares) {
		this.restController = restController;
		this.middlewares = middlewares;
		if(this.middlewares == null || this.middlewares.length == 0) {
			this.hasMiddlewares = false;
		} else { this.hasMiddlewares = false; }
	}
	
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		baseRequest.setHandled(true);
		
		if(this.hasMiddlewares) {
			for(Middleware middleware : this.middlewares) {
				middleware.doMiddleware(baseRequest, response);
			}
		}
		restController.handle(target, baseRequest, request, response);
		
	}

}
