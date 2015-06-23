package com.denissys.core;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;

import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.weld.environment.servlet.Listener;

public class EmbeddedServer {

	private final UndertowJaxrsServer undertowJaxrsServer = new UndertowJaxrsServer();
	private String contextPath = "/app-name";
	private String deploymentName = "app-name";
	private String appPath = "/api";
	private Class<? extends Application> resourcesClass;

	public EmbeddedServer(final String host, final Integer port) {
		Undertow.Builder serverBuilder = Undertow.builder().addHttpListener(port, host);
		this.undertowJaxrsServer.start(serverBuilder);
	}
	
	public EmbeddedServer contextPath(final String contextPath) {
		this.contextPath = contextPath;
		return this;
	}
	
	public EmbeddedServer deploymentName(final String deploymentName) {
		this.deploymentName = deploymentName;
		return this;
	}
	
	public EmbeddedServer appPath(final String appPath) {
		this.appPath = appPath;
		return this;
	}
	
	public EmbeddedServer resourcesClass(final Class<? extends Application> resourcesClass) {
		this.resourcesClass = resourcesClass;
		return this;
	}
	
	private DeploymentInfo deployApplication() {
		final ResteasyDeployment deployment = new ResteasyDeployment();
		deployment.setInjectorFactoryClass(CdiInjectorFactory.class.getName());
		deployment.setApplicationClass(resourcesClass.getName());
		return this.undertowJaxrsServer.undertowDeployment(deployment, appPath);
	}
	
	public void start() throws ServletException {
		final DeploymentInfo deploymentInfo = deployApplication()
		        .setClassLoader(EmbeddedServer.class.getClassLoader())
		        .setContextPath(contextPath)
		        .setDeploymentName(deploymentName)
		        .addListeners(Servlets.listener(Listener.class));
		
		this.undertowJaxrsServer.deploy(deploymentInfo);
	}
	
}
