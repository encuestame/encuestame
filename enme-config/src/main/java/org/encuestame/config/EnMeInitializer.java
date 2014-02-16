package org.encuestame.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Initialiaze Encuestame Servlet
 * 
 * @author jpicado
 */
public class EnMeInitializer implements WebApplicationInitializer {

	private static final String DISPATCHER_SERVLET_NAME = "DispatcherServlet";
	private static final String CONFIG_PATH = "org.encuestame.config.annotations";
	private static final String DISPATCHER_SERVLET_MAPPING = "/";
	/**
	 * Log.
	 */
	private Logger log = Logger.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet
	 * .ServletContext)
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {		
		final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation(CONFIG_PATH);				
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(context));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping(DISPATCHER_SERVLET_MAPPING);	
		//customized ContextLoaderListener
		servletContext.addListener(new EnMeContext(context));
	}
}
