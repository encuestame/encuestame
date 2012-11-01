package org.encuestame.business.security;

import org.springframework.security.core.context.SecurityContext;


public interface ISecurityContext {

    /**
     * {@link SecurityContext}.
     * @return
     */
    SecurityContext getContext();

    /**
     * Set Context.
     * @param securityContext
     */
    void setContext(final SecurityContext securityContext);

}
