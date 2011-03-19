
package org.encuestame.business.setup;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 * Manages encuestame application startup.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 15, 2011
 */
public class ApplicationStartup {

    public ApplicationStartup() {
        System.out.println("**********************ApplicationStartup*************************************");
    }

    protected Logger log = Logger.getLogger(this.getClass());

    @PostConstruct
    public void init(){
        log.debug("*******************************");
        log.debug("*                             *");
        log.debug("*         STARTUP             *");
        log.debug("*                             *");
        log.debug("*******************************");
    }

    @PreDestroy
    public void shuthdown(){
        log.debug("*******************************");
        log.debug("*                             *");
        log.debug("*         SHUTHDOWN           *");
        log.debug("*                             *");
        log.debug("*******************************");
    }

    public void startProcess() {
        log.debug("*******************************");
        log.debug("*                             *");
        log.debug("*         STARTUP 1111           *");
        log.debug("*                             *");
        log.debug("*******************************");

    }

    public void afterPropertiesSet() throws Exception {
        log.debug("*******************************");
        log.debug("*                             *");
        log.debug("*         SHUTHDOWN   222222        *");
        log.debug("*                             *");
        log.debug("*******************************");
    }
}
