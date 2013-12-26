package org.encuestame.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.log4j.Logger;
import org.encuestame.business.setup.StartupProcess;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class EnMeInitializer implements WebApplicationInitializer {
	
	/**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());	
    
    /*
     * (non-Javadoc)
     * @see org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet.ServletContext)
     */
	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		WebApplicationContext context = getContext();
        servletContext.addListener(new ContextLoaderListener(context));
        final StartupProcess startup = (StartupProcess) context.getBean("applicationStartup");
        try {
            startup.startProcess();
            log.info("**********************************************");
            log.info("*     ENCUESTAME IS RUNNING SUCCESSFULLY     *");
            log.info("*        http://www.encuestame.org           *");
            log.info("**********************************************");
         } catch (Exception e) {
            log.fatal("EnMe: Error on start encuestame context: "+e.getMessage());
            throw new IllegalStateException("EnMe: Error on stat encuestame context : "+e.getMessage());
         }        
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");

	}
	
	 private AnnotationConfigWebApplicationContext getContext() {
	        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
	        context.setConfigLocation("org.encuesstame.business.config.annotations");
	        return context;
	    }	

}
