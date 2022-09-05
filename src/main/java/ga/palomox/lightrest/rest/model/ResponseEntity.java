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
	/**
	 * Creates a {@link ResponseEntity} 
	 * @param <T> the type of the body
	 * @param status the http status of the response
	 * @return the {@link ResponseEntity} representing it
	 */
	public static <T> ResponseEntity<T> of(int status){
		ResponseEntity<T> entity = new ResponseEntity<>();
		entity.responseCode = status;
		return entity;
	}
	
	/**
	 * Creates a {@link ResponseEntity} with a 200 status with the desired body
	 * @param <T> the type of the payload 
	 * @param body the payload
	 * @return the {@link ResponseEntity} representing it
	 */
	public static <T> ResponseEntity<T> ok(T body){
		ResponseEntity<T> entity = ResponseEntity.of(200);
		return entity.body(body);
	}
	/**
	 * Creates a {@link ResponseEntity} with a 400 status with the desired body
	 * @param <T> the type of the payload 
	 * @param body the payload
	 * @return the {@link ResponseEntity} representing it
	 */
	public static <T> ResponseEntity<T> badRequest(T body){
		ResponseEntity<T> entity = ResponseEntity.of(400);
		return entity.body(body);
	}
	/**
	 * Creates a {@link ResponseEntity} with a 403 status with the desired body
	 * @param <T> the type of the payload 
	 * @param body the payload
	 * @return the {@link ResponseEntity} representing it
	 */
	public static <T> ResponseEntity<T> forbidden(T body){
		ResponseEntity<T> entity = ResponseEntity.of(403);
		return entity.body(body);
	}
	/**
	 * Creates a {@link ResponseEntity} telling the client that they have no permissions to access this endpoint. Useful for when you need to manually check the permissions of an user instead of using the annotation system
	 * @return the {@link ResponseEntity} representing it.
	 */
	public static ResponseEntity<String> noPerms(){
		ResponseEntity<String> entity = ResponseEntity.of(403);
		return entity.body("{\"error\": \"You are not allowed to access this endpoint\"}");
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
		} else if (this.content instanceof byte[] byteContent){
			try {
				response.getOutputStream().write(byteContent);

			} catch (IOException e) {
				return;
			}
			response.setStatus(this.responseCode);
			response.setContentLength(byteContent.length);
			return;
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
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public HashMap<String, String> getHeaders() {
		return headers;
	}
	
	
}
