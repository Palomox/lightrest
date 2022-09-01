package ga.palomox.lightrest.rest.model;

import java.util.Map;

public class MatchResult {
	private boolean matches;
	private Map<String, String> params;
	
	public MatchResult(boolean matches) {
		this.matches = matches;
		this.params = null;
	}
	
	public MatchResult(boolean matches, Map<String, String> params) {
		this.matches = matches;
		this.params = params; 
	}
	
	public boolean matches() {
		return matches;
	}
	public Map<String, String> getParams() {
		return params;
	} 
	

	
}
