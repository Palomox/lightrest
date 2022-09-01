package ga.palomox.lightrest;

import java.io.IOException;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ErrorHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JsonErrorHandler extends ErrorHandler{
	@Override
	protected void generateAcceptableResponse(Request baseRequest, HttpServletRequest request,
			HttpServletResponse response, int code, String message) throws IOException {
		
		response.setStatus(code);
		response.setContentType("application/json");
		response.getWriter().write(String.format("{\"error\": \"%s %s\"}", code, message));
		
	}
}
