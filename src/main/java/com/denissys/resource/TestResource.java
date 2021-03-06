package com.denissys.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.denissys.bean.AnyBean;

@Path("/test")
public class TestResource implements Resource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test(){
		return "Hello Any Test";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/json")
	public AnyBean json(){
		return new AnyBean();
	}
    
}
