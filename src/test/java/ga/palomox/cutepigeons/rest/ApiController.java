package ga.palomox.cutepigeons.rest;

import java.util.List;
import java.util.Map;

import org.eclipse.jetty.server.Request;

import ga.palomox.cutepigeons.model.Post;
import ga.palomox.cutepigeons.security.KratosIdentity;
import ga.palomox.lightrest.rest.annotations.Authenticated;
import ga.palomox.lightrest.rest.annotations.Mapping;
import ga.palomox.lightrest.rest.annotations.PathVariable;
import ga.palomox.lightrest.rest.annotations.Relationship;
import ga.palomox.lightrest.rest.annotations.RestController;
import ga.palomox.lightrest.rest.annotations.Weigh;
import ga.palomox.lightrest.rest.model.ResponseEntity;

@RestController
public class ApiController {
	public ApiController() {
	}

	@Mapping(path = "api/v1/posts/{id}/image", protocol = "GET")
	public ResponseEntity<?> getImage(@PathVariable(name = "id") int id) {
		return null;
	}

	@Mapping(path = "api/v1/posts", protocol = "GET")
	public ResponseEntity<Map<String, List<Post>>> getPigeons() {
		return null;
	}

	@Mapping(path = "api/v1/posts/{id}", protocol = "GET")
	public ResponseEntity<Post> getPigeonById(@PathVariable(name = "id") String id) {
		return null;
	}

	@Authenticated
	@Relationship(namespace = "groups", relation = "member", object = "admin", isPathVar = false)
	@Mapping(path = "api/v1/posts/{id}", protocol = "POST")
	@Weigh(10)
	public ResponseEntity<?> addPigeonAdmin(Request baseRequest, @PathVariable(name = "id") String id,
			KratosIdentity identity) {

		return null;
	}

	@Authenticated
	@Mapping(path = "api/v1/posts/{id}", protocol = "POST")
	@Weigh(1)
	public ResponseEntity<String> addPigeon(Request baseRequest, @PathVariable(name = "id") String idString,
			KratosIdentity identity) {

		return null;
	}

	@Relationship(object = "admin", namespace = "groups", relation = "member", isPathVar = false)
	@Authenticated
	@Mapping(path = "api/v1/posts/{id}", protocol = "DELETE")
	@Weigh(10)
	public ResponseEntity<String> removePigeonAdmin(@PathVariable(name = "id") String idString) {
		return null;
	}

	@Relationship(object = "id", namespace = "pigeons", relation = "writer", isPathVar = true)
	@Authenticated
	@Mapping(path = "api/v1/posts/{id}", protocol = "DELETE")
	@Weigh(1)
	public ResponseEntity<String> removePigeon(@PathVariable(name = "id") String idString) {
		return null;
	}

	@Mapping(path = "api/v1/user", protocol = "PUT")
	@Authenticated
	public ResponseEntity<String> changeserSettings(Request request, KratosIdentity identity) {
		return null;
	}

	@Mapping(path = "api/v1/modqueue/pigeons", protocol = "GET")
	@Authenticated
	@Relationship(isPathVar = false, namespace = "groups", relation = "member", object = "moderator")
	public ResponseEntity<List<Post>> getModList() {
		return null;
		}

	@Mapping(path = "api/v1/modqueue/pigeons/{id}", protocol = "POST")
	@Authenticated
	@Relationship(isPathVar = false, namespace = "groups", relation = "member", object = "moderator")
	public ResponseEntity<String> doModAction(@PathVariable(name = "id") String id, Request request) {
		return null;
	}

}
