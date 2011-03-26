package org.encuestame.mvc.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.encuestame.business.setup.StartupProcess;
import org.encuestame.business.setup.install.InstallDatabaseOperations;
import org.encuestame.persistence.exception.EnMeStartupException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Initialize encuestame context.
 * Extend spring {@link ContextLoaderListener}.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 19, 2011
 */
public class EnMeContext extends ContextLoaderListener implements
        ServletContextListener {

    /**
     * Local service context.
     */
    private static ServletContext servletContext = null;

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Constructor, inizialize parent context.
     */
    public EnMeContext() {
        super();
    }

    /**
     * On start application.
     */
    @Override
    public void contextInitialized(final ServletContextEvent sce) {

        this.servletContext = sce.getServletContext();

        super.contextInitialized(sce);

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        log.debug("*******************************");
        log.debug("*                             *");
        log.debug("*         CONTEXT             *");
        log.debug("*        "+ctx+"              *");
        log.debug("*******************************");
        log.debug("Security Context "+SecurityContextHolder.getContext());
        final StartupProcess startup = (StartupProcess) ctx.getBean("applicationStartup");
        try {
            startup.startProcess();
        } catch (EnMeStartupException e) {
           log.fatal("EnMe: Error on stat encuestame context");
           throw new IllegalStateException("EnMe: Error on stat encuestame context : "+e.getMessage());
        }

        //log.debug("******************************* "+install);
        //install.install();
        log.debug("*******************************");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
