package ga.palomox.lightrest.rest.model;

import java.io.IOException;
import java.util.HashMap;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletResponse;

public class ResponseEntity<T> {
	private String contentType;
	private String body; 
	private HashMap<String, String> headers; 
	private int responseCode;
	private T content;
	
	public ResponseEntity() {
		this.contentType = "application/json";
		this.body = "";
		this.headers = new HashMap<>();
	}
	
	public static <T> ResponseEntity<T> of(int status){
		ResponseEntity<T> entity = new ResponseEntity<>();
		entity.responseCode = status;
		return entity;
	}
	
	public ResponseEntity<T> body(T body){
		this.content = body;
		return this;
	}
	
	public void parse(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(this.contentType);
		
		
		this.headers.forEach((k, v) -> {
			response.setHeader(k, v);
		});
		
		if(this.content instanceof String content) {
			this.body = content;
		} else {
			this.body = new Gson().toJson(this.content);
		}
		
		try {
			response.getWriter().write(this.body);
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setStatus(this.responseCode);
		response.setContentLength(body.length());
	}
}
