package edu.pezzati.yo.offer;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("/srv")
public class YoOfferApp extends Application {

	public YoOfferApp() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("0.0.1");
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setHost(System.getProperty("yooffer.swagger.host", "localhost:8080"));
		beanConfig.setBasePath(System.getProperty("yooffer.swagger.path", "/YoOffer/srv"));
		beanConfig.setResourcePackage("edu.pezzati.yo.offer");
		beanConfig.setScan(true);
	}

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new HashSet<Class<?>>();
		resources.add(OfferServiceImpl.class);
		resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
		resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
		return resources;
	}
}
