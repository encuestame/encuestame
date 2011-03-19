package org.encuestame.mvc.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.encuestame.persistence.dao.jdbc.InstallerOperations;
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

    private static ServletContext servletContext = null;

    protected Logger log = Logger.getLogger(this.getClass());

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
        log.debug("Testing get Bean Service "+ctx.getBean("installerDao"));
        log.debug("Security Context "+SecurityContextHolder.getContext());
        final InstallerOperations install = (InstallerOperations) ctx.getBean("installerDao");
        log.debug("******************************* "+install);
        install.checkDatabaseConection();
        log.debug("*******************************");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
