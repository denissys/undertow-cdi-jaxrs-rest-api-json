package com.denissys;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class TestApplication extends Application {

    @Override
	public Set<Class<?>> getClasses() {
        final Set<Class<?>> resourcesList = new LinkedHashSet<Class<?>>();
		resourcesList.add(TestResource.class);
        return resourcesList;
    }

}
