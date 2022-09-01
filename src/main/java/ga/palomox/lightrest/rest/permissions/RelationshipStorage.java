package ga.palomox.lightrest.rest.permissions;

public class RelationshipStorage {
	private String relation;
	private String namespace;
	private String object;
	private boolean isMapped;
	
	public RelationshipStorage(String relation, String namespace, String object, boolean isMapped) {
		this.relation = relation;
		this.namespace = namespace;
		this.object = object;
		this.isMapped = isMapped;
	}

	public String getRelation() {
		return relation;
	}

	public String getNamespace() {
		return namespace;
	}

	public String getObject() {
		return object;
	}

	public boolean isMapped() {
		return isMapped;
	}
	

}
