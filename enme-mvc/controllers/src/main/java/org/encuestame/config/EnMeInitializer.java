package org.encuestame.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.log4j.Logger;
import org.encuestame.config.annotations.ServicesAppConfig;
import org.encuestame.config.annotations.EnMeProperties;
import org.encuestame.config.annotations.web.EnMeWebMvcConfiguration;
import org.encuestame.config.root.DirectoryConfig;
import org.encuestame.config.root.EnMeData;
import org.encuestame.config.root.EnMeEmail;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Initialiaze Encuestame Servlet
 *
 * @author jpicado
 */
public class EnMeInitializer implements WebApplicationInitializer {

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

        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(EnMeProperties.class, DirectoryConfig.class, EnMeData.class, EnMeEmail.class);

        // Manage the lifecycle of the root application context
        servletContext.addListener(new EnMeContext(context));

        // Create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
        dispatcherServlet.register(ServicesAppConfig.class, EnMeWebMvcConfiguration.class);

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherServlet));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
