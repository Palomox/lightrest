package ga.palomox.lightrest.rest.model;

import java.io.IOException;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletResponse;

public class ResponseEntity {
	private String contentType;
	private String body; 
	private HashMap<String, String> headers; 
	private int responseCode;
	
	public ResponseEntity() {
		this.contentType = "application/json";
		this.body = "";
		this.headers = new HashMap<>();
	}
	
	public static ResponseEntity of(int status){
		ResponseEntity entity = new ResponseEntity();
		entity.responseCode = status;
		return entity;
	}
	
	public ResponseEntity body(String body){
		this.body = body;
		return this;
	}
	
	public void parse(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(this.contentType);
		
		
		this.headers.forEach((k, v) -> {
			response.setHeader(k, v);
		});
		
		try {
			response.getWriter().write(this.body);
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setStatus(this.responseCode);
		response.setContentLength(body.length());
	}
}
