package ga.palomox.lightrest.rest.model;

import java.util.HashMap;

public class EndpointPath {
	private String path; 
	private String[] pathParts; 
	
	public EndpointPath(String path) {
		this.path = path;
		this.pathParts = path.split("/"); 
	}
	
	public MatchResult matches(String path) {
		String[] parts = path.split("/");
		
		if(this.pathParts.length != parts.length) {
			return new MatchResult(false); 
		}
		HashMap<String, String> params = new HashMap<>();
		
		for(int i = 0; i<this.pathParts.length; i++) {
			String part = this.pathParts[i];
			if(part.contains("{")) {
				String parameter = part.replaceAll("[{}]", "");
				params.put(parameter, parts[i]);
				continue;
			}
			if(!part.equals(parts[i])) {
				return new MatchResult(false);
			}
		}
		
		return new MatchResult(true, params);
	}

	public String getPath() {
		return path;
	}
	
	
}
