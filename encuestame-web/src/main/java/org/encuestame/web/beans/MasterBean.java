/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.service.ISecurityService;
import org.encuestame.core.service.IServiceManager;
import org.encuestame.core.service.ISurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * Master Bean.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since 26/04/2009
 * @version $Id$
 */
public class MasterBean {

    /** {@link ApplicationContext}. **/
    private ApplicationContext appContext;

    /** {@link ServiceManager} **/
    @Autowired
    protected IServiceManager servicemanager;


    /** Log. **/
    protected Logger log = Logger.getLogger(this.getClass());



    /** User Session Id. **/
    protected Long userSessionId;

    /** Obtain {@link SecurityContext}.**/
    private SecurityContext securityContext;

    /** Short Number String. **/
    private Integer shortNumberString = 30;

    private static final String URL = "http://";
    private static final Integer REQUEST_SERVER_PORT = 80;
    /**
     * Constructor.
     */
    public MasterBean() {
      // this.userSessionId =  this.getUsernameByName().getSecUser().getUid();
    }

    /**
     * Get User Session Id.
     */
    protected final Long getUserSessionId(){
        return this.userSessionId;
    }

    /**
     * Description.
     * @param serviceBeanName bean name
     * @return {@link Object}
     */
    public final Object lookupService(String serviceBeanName) {
        return appContext.getBean(serviceBeanName);
    }

    /**
     * Getter {@link ServiceManager}.
     * @return {@link ServiceManager}
     */
    public final IServiceManager getServicemanager() {
        return servicemanager;
    }

    /**
     * Setter {@link ServiceManager}.
     * @param servicemanagerBean {@link ServiceManager}
     */
    public final void setServicemanagerBean(final IServiceManager servicemanagerBean) {
        this.servicemanager = servicemanagerBean;
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
        this.clearMessages();
        final FacesMessage facesMessages = new FacesMessage(message, description);
        facesMessages.setSeverity(severity);
        getFacesContext().addMessage(messageId, facesMessages);
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

    /**
     * Get Domain.
     * @return domain
     */
    public final String getDomain(){
        final HttpServletRequest request = (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
        final StringBuffer buffer = new StringBuffer(this.URL);
        buffer.append(request.getServerName());
        if(request.getServerPort() != 80){
            buffer.append(":");
            buffer.append(request.getServerPort());
        }
        //buffer.append("//");
        buffer.append(request.getContextPath());
        return buffer.toString();
    }

    /**
     * Get Request.
     * @return {@link HttpServletRequest}.
     */
    public final HttpServletRequest getRequest()
    {
        final HttpServletRequest request =
            (HttpServletRequest) this.getFacesContext().getExternalContext().getRequest();
        if (request == null)
        {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    /**
     * Get Attribute.
     * @param name name
     * @return object
     */
    public final Object getAttribute(String name)
    {
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
     * add info message to context
     *
     * @param message message
     * @param description description
     */
    public final void addInfoMessage(String message, String description) {
        showMessage(null, message, description, FacesMessage.SEVERITY_INFO);
    }

    /**
     * Add error message to context.
     * @param message message
     * @param description  description
     */
    public final void addErrorMessage(String message, String description) {
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
     * @param propertieId propertie Id
     * @return the localized message if it is exists, otherwise the specified
     *         property id
     */
    public final String getMessageProperties(String propertieId) {
        return  getServicemanager().getMessageSource() == null ? propertieId
                : getServicemanager().getMessageSource().getMessage(
                        propertieId, null, null);
    }

    /**
     * Short Long String.
     * @param string string to abbreviate
     * @return abrreviate string.
     */
    public final String shortLongString(final String string){
        return StringUtils.abbreviate(string, getShortNumberString());
    }

    /**
     * Get Username.
     * @return username
     */
    public final String getUsername(){
        log.info("Session Username "+getSecCtx().getAuthentication().getName());
        return getSecCtx().getAuthentication().getName();
    }

    /**
     * Get {@link SecUserSecondary} by Name.
     * @return {@link SecUserSecondary}
     */
    public final SecUserSecondary getUsernameByName(){
        return getServicemanager().getApplicationServices().getSecurityService().findUserByUserName(getUsername());
    }


    /**
     * @return the secCtx
     */
    protected final SecurityContext getSecCtx() {
        return this.securityContext = SecurityContextHolder.getContext();
    }

    /**
     * Get Username of user client.
     * @return
     */
    protected final String getUserPrincipalUsername(){
       return getSecCtx().getAuthentication().getName();
    }

    /**
     *
     * @return
     */
    protected final ISecurityService getSecurityService(){
        return getServicemanager().getApplicationServices().getSecurityService();
    }

    /**
     * @return the shortNumberString
     */
    public final Integer getShortNumberString() {
        return shortNumberString;
    }

    /**
     * @param shortNumberString the shortNumberString to set
     */
    public final void setShortNumberString(final Integer shortNumberString) {
        this.shortNumberString = shortNumberString;
    }



    /**
     * @return the surveyService
     */
    public ISurveyService getSurveyService() {
         return getServicemanager().getApplicationServices().getSurveyService();
    }

    /**
     * @return the securityContext
     */
    public final SecurityContext getSecurityContext() {
        return securityContext;
    }
}
