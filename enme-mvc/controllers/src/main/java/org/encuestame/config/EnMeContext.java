/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.encuestame.business.setup.StartupProcess;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Initialize encuestame context.
 * Extend spring {@link ContextLoaderListener}.
 * @au thor Picado, Juan juanATencuestame.org
 * @since Mar 19, 2011
 */
public class EnMeContext extends ContextLoaderListener implements ServletContextListener {

    /**
     * Local service context.
     */
    private static ServletContext servletContext = null;

    /**
     * Log.
     */
    private static Log log = LogFactory.getLog(EnMeContext.class);

    /**
     * Constructor, inizialize parent context.
     */
    public EnMeContext() {
        super();
    }

    public EnMeContext(WebApplicationContext context) {
        super(context);
    }

    /**
     * On start application.
     */
    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        EnMeContext.servletContext = sce.getServletContext();
        super.contextInitialized(sce);
        boolean existHomeDirectory = org.encuestame.core.service.startup.DirectorySetupOperations.isHomeDirectoryValid();
        if (!existHomeDirectory) {
            log.fatal("**********************************************");
            log.fatal("*    		 ENCUESTAME HOME IS MISSING");
            log.fatal("*    		 Troubles ?? Visit the wiki a get your answer.");
            log.fatal("*  http://www.encuestame.org/wiki/display/DOC/Troubleshooting+Guides");
            log.fatal("**********************************************");
            throw new IllegalStateException("home not valid, please set a home property in the configuration file");
        } else {
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            servletContext.setInitParameter("spring.profiles.active", "live");
            final StartupProcess startup = (StartupProcess) ctx.getBean("applicationStartup");
            try {
               startup.startProcess();
               log.info("**********************************************");
               log.info("*     ENCUESTAME IS RUNNING SUCCESSFULLY      *");
               log.info("*        http://www.encuestame.org           *");
               log.info("**********************************************");
            } catch (Exception e) {
               log.fatal("EnMe: Error on start encuestame context: "+e.getMessage());
               this.closeWebApplicationContext(servletContext);
               throw new IllegalStateException("EnMe: Error on stat encuestame context : "+e.getMessage());
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
