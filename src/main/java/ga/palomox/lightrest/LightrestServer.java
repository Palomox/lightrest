package ga.palomox.lightrest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import ga.palomox.lightrest.middleware.Middleware;
import ga.palomox.lightrest.rest.RestControllerClass;

public class LightrestServer {
	private int port;
	private Server server;
	private ServerConnector connector;
	private RestControllerClass<?, ?, ?> controller;
	public static LightrestServer instance;

	public LightrestServer(int port, RestControllerClass<?, ?, ?> controller, Middleware[] middlewares) {
		this.port = port;

		this.controller = controller;
		instance = this;

		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setName("server" + port);

		server = new Server(threadPool);

		connector = new ServerConnector(server);

		connector.setPort(port);

		server.addConnector(connector);

		server.setHandler(new ServerHandler(this.controller, middlewares));
		
		server.setErrorHandler(new JsonErrorHandler());

		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RestControllerClass<?, ?, ?> getRestController() {
		return this.controller;
	}

	public int getPort() {
		return this.port;
	}
}
