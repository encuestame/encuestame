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

import org.apache.log4j.Logger;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.security.spring.EnMeUserDetails;
import org.encuestame.core.service.ILocationService;
import org.encuestame.core.service.ISecurityService;
import org.encuestame.core.service.IServiceManager;
import org.encuestame.core.service.ISurveyService;
import org.encuestame.core.service.ITweetPollService;
import org.encuestame.core.service.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * Master Bean.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since 26/04/2009
 * @version $Id$
 */
public class MasterBean{

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

    private static final String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

    public static final Integer GRAVATAR_SIZE = 64;

    /**
     * Constructor.
     */
    public MasterBean() {
    }

    /**
     * Get Gravatar.
     * @param email email.
     * @param size size.
     */
    public final String getGravatar(final String email, Integer size){
        final String hash = MD5Utils.md5Hex(email);
         StringBuilder gravatarUl = new StringBuilder();
         gravatarUl.append(MasterBean.GRAVATAR_URL);
         gravatarUl.append(hash);
         gravatarUl.append("?s=");
         gravatarUl.append(size);
         return gravatarUl.toString();
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
        log.debug("Getting  Services Manager "+this.servicemanager);
        return servicemanager;
    }

    /**
     * Setter {@link ServiceManager}.
     * @param servicemanagerBean {@link ServiceManager}
     */
    public final void setServicemanagerBean(final IServiceManager servicemanagerBean) {
        log.debug("Setting  Services Manager "+servicemanagerBean);
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
        //this.clearMessages();
        //final FacesMessage facesMessages = new FacesMessage(message, description);
        //FIXME: nullPointer, maybe this context is not available on webflow context.
        //facesMessages.setSeverity(severity);
        //getFacesContext().addMessage(messageId, facesMessages);
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
     * Get Request.
     * @return
     */
    protected HttpServletRequest getFlowRequest(){
        ServletRequestAttributes attr = (ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes();
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
        final StringBuffer domain = new StringBuffer(MasterBean.URL);
        domain.append(request.getServerName());
        if(request.getServerPort() != MasterBean.REQUEST_SERVER_PORT){
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
    public final String getSecurityContextUsername(){
        return getSecCtx().getAuthentication().getName();
    }

    /**
     * Get Details.
     */
    public final EnMeUserDetails getSecurityDetails(){
        return (EnMeUserDetails) getSecCtx().getAuthentication().getPrincipal();
    }

    /**
     * Get Email User Detail.
     * @return
     */
    public final String getSecurityContextEmail(){
        final EnMeUserDetails details = this.getSecurityDetails();
        return details.getUserEmail();
    }

    /**
     * Get {@link SecUserSecondary} by Name.
     * @return {@link SecUserSecondary}
     */
    public final SecUserSecondary getUsernameByName(){
        return getServicemanager().getApplicationServices().getSecurityService().findUserByUserName(this.getSecurityContextUsername());
    }

    /**
     * Ignored Null.
     * @param attributeValue
     * @return
     */
    protected final String ignoreNull(String attributeValue) {
        if (attributeValue == null) {
            return "";
        }
        return attributeValue;
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
     * @return the surveyService
     */
    public ITweetPollService getTweetPollService() {
         return getServicemanager().getApplicationServices().getTweetPollService();
    }

    /**
     * @return the securityContext
     */
    public final SecurityContext getSecurityContext() {
        return securityContext;
    }

    /**
     * Get Location Service.
     * @return
     */
    public ILocationService getLocationService(){
        return getServicemanager().getApplicationServices().getLocationService();
    }
}
