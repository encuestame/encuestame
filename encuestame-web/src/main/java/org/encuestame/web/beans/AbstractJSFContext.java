/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.web.beans;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.encuestame.core.security.AbstractSecurityContext;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Abstract JSF Context.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 16, 2010 6:43:25 PM
 * @version $Id:$
 */
public abstract class AbstractJSFContext extends AbstractSecurityContext {


    private static final String URL = "http://";

    private static final Integer REQUEST_SERVER_PORT = 80;


     /**
     * Get Web Flow Request.
     * @return {@link HttpServletRequest}.
     */
    protected HttpServletRequest getFlowRequest(){
        ServletRequestAttributes attr = (ServletRequestAttributes)
        org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes();
        log.debug("getFlowRequest "+attr.getRequest());
        log.debug("getFlowRequest Server Name "+attr.getRequest().getServerName());
        log.debug("getFlowRequest Server Context Path "+attr.getRequest().getContextPath());
        log.debug("getFlowRequest Server Port "+attr.getRequest().getServerPort());
        return attr.getRequest();
    }

    /**
     * Get Domain.
     * @return domain
     */
    public final String getDomain(){
        final HttpServletRequest request = this.getFlowRequest();
        log.debug("DOMAIN REQUEST "+request);
        final StringBuffer domain = new StringBuffer(this.URL);
        domain.append(request.getServerName());
        if(request.getServerPort() != this.REQUEST_SERVER_PORT){
            domain.append(":");
            domain.append(request.getServerPort());
        }
        //buffer.append("//");
        domain.append(request.getContextPath());
        log.debug("DOMAIN "+domain);
        return domain.toString();
    }

    /**
     * Get Request.
     * @return {@link HttpServletRequest}.
     */
    public final HttpServletRequest getRequest(){
        final HttpServletRequest request =
            (HttpServletRequest) this.getFacesContext().getExternalContext().getRequest();
        if (request == null){
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    /**
     * Get Attribute.
     * @param name name
     * @return object
     */
    public final Object getAttribute(String name){
        final HttpServletRequest request = getRequest();
        return request.getAttribute(name);
    }

    /**
     * add message to context
     *
     * @param message message
     * @param description description
     * @param severity severity
     */
    public final void showMessage(String message, String description, Severity severity) {
        showMessage(null, message, description, severity);
    }

    /**
     * Add info message to context.
     * @param message message
     * @param description description
     */
    public final void addInfoMessage(String message, String description) {
        log.info("addInfoMessage "+message);
        log.info("addInfoMessage description "+description);
        showMessage(null, message, description, FacesMessage.SEVERITY_INFO);
    }

    /**
     * Add error message to context.
     * @param message message
     * @param description  description
     */
    public final void addErrorMessage(String message, String description) {
        log.info("addErrorMessage "+message);
        log.info("addErrorMessage description "+description);
        showMessage(null, message, description, FacesMessage.SEVERITY_ERROR);
    }

    /**
     * Add warning message to context.
     * @param message message
     * @param description description
     */
    public final void addWarningMessage(String message, String description) {
        showMessage(null, message, description, FacesMessage.SEVERITY_WARN);
    }
    /**
     * Return the FacesContext instance for the current request.
     */
    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }


    /**
     * Add message to context.
     * @param messageId messageId
     * @param message message
     * @param description description
     * @param severity severity
     */
    public final void showMessage(String messageId, String message, String description,
            Severity severity) {
        //this.clearMessages();
        final FacesMessage facesMessages = new FacesMessage(message, description);
        log.debug("FACES MESSAGES "+facesMessages);
        //FIXME: nullPointer, maybe this context is not available on webflow context.
        facesMessages.setSeverity(severity);
        if(getFacesContext() != null){
            log.info("Message Context Added");
            getFacesContext().addMessage(messageId, facesMessages);
        } else {
            log.error("Faces Context not found");
        }
    }

    /**
     * Clear Messages.
     */
    @SuppressWarnings("unchecked")
    public final void clearMessages(){
        final Iterator iterator = getFacesContext().getMessages();
        while (iterator.hasNext()) {
            iterator.remove();
        }
    }

}
