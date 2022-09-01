package ga.palomox.lightrest.rest;

import java.util.Optional;

import org.eclipse.jetty.server.Request;

import ga.palomox.lightrest.rest.model.Identity;

public interface IdentityManager<T extends Identity<?>> {
	public Optional<T> loadIdentity(Request baseRequest);
	public Class<?> getIdentityClass();
}
