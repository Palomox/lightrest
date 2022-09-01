package ga.palomox.lightrest.rest.permissions;

public interface PermissionsManager<T> {
	public boolean isAllowed(T userId, String relationship, String namespace, String object);
}
