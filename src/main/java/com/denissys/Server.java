package com.denissys;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;

import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.weld.environment.servlet.Listener;

public class Server {

	private final UndertowJaxrsServer server = new UndertowJaxrsServer();
	
	public Server(Integer port, String host) {
		Undertow.Builder serverBuilder = Undertow.builder().addHttpListener(port, host);
		server.start(serverBuilder);
	}
	
	public DeploymentInfo deployApplication(String appPath, Class<? extends Application> applicationClass) {
		final ResteasyDeployment deployment = new ResteasyDeployment();
		deployment.setInjectorFactoryClass(CdiInjectorFactory.class.getName());
		deployment.setApplicationClass(applicationClass.getName());
		return server.undertowDeployment(deployment, appPath);
	}
	
	public void deploy(DeploymentInfo deploymentInfo) throws ServletException {
		server.deploy(deploymentInfo);
	}
	
	public static void main(String[] args) throws ServletException {
		final Server server = new Server(8080, "0.0.0.0");
		
		final DeploymentInfo deploymentInfo = server.deployApplication("/api", ResourceFactory.class)
		        .setClassLoader(Server.class.getClassLoader())
		        .setContextPath("/app-name")
		        .setDeploymentName("app-name")
		        .addListeners(Servlets.listener(Listener.class));
		server.deploy(deploymentInfo);
	}
}
