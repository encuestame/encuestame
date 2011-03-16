
package org.encuestame.comet.services;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.java.annotation.Session;
import org.encuestame.business.security.AbstractSecurityContext;
import org.encuestame.business.service.ServiceManager;
import org.encuestame.business.service.imp.IServiceManager;
import org.encuestame.persistence.domain.security.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Base Case to all comet service.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 4, 2011
 */
public abstract class AbstractCometService extends AbstractSecurityContext {


    protected Logger log = Logger.getLogger(this.getClass());

    /**
     * {@link ServiceManager}.
     */
    @Autowired
    private IServiceManager serviceManager;

    @Inject
    private BayeuxServer bayeux;
    @Session
    protected ServerSession serverSession;

    @PostConstruct
    public void init() {
    }

    /**
     * @return the serviceManager
     */
    public IServiceManager getServiceManager() {
        return serviceManager;
    }

    /**
     * Get By Username.
     * @param username username
     * @return
     */
    public UserAccount getByUsername(final String username){
        return getServiceManager().getApplicationServices().getSecurityService().findUserByUserName(username);
    }

    /**
     * @param serviceManager
     *            the serviceManager to set
     */
    public void setServiceManager(IServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

}
