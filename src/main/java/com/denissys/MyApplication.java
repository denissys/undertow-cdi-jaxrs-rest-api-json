package com.denissys;

import javax.servlet.ServletException;

import com.denissys.core.EmbeddedServer;
import com.denissys.resource.ResourceFactory;

public class MyApplication {

	public static void main(String[] args) throws ServletException {
		
		new EmbeddedServer("0.0.0.0", 8080)
			.contextPath("/app-name")
			.deploymentName("app-name")
			.appPath("/api")
			.resourcesClass(ResourceFactory.class)
			.start();
	}
	
}
