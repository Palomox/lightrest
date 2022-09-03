package ga.palomox.lightrest.middleware;

import org.eclipse.jetty.server.Request;

import jakarta.servlet.http.HttpServletResponse;

public interface Middleware {
	public void doMiddleware(Request request, HttpServletResponse response);
}
