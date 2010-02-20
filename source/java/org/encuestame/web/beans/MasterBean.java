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

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.service.ISecurityService;
import org.encuestame.core.service.IServiceManager;
import org.encuestame.core.service.ServiceManager;
import org.springframework.context.ApplicationContext;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;

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
    protected IServiceManager servicemanager;

    /** Log. **/
    protected Log log = LogFactory.getLog(this.getClass());

    @Deprecated
    protected boolean isOneRow;

    /** User Session Id. **/
    protected Long userSessionId;

    /** Obtain {@link SecurityContext}.**/
    private SecurityContext secCtx;

    /**
     * Constructor.
     */
    public MasterBean() {
      // this.userSessionId =  this.getUsernameByName().getSecUser().getUid();
    }

    /**
     * Get User Session Id.
     */
    protected Long getUserSessionId(){
        return this.userSessionId;
    }

    /**
     * Description.
     * @param serviceBeanName bean name
     * @return {@link Object}
     */
    public Object lookupService(String serviceBeanName) {
        return appContext.getBean(serviceBeanName);
    }

    /**
     * Getter {@link ServiceManager}.
     * @return {@link ServiceManager}
     */
    public IServiceManager getServicemanager() {
        return servicemanager;
    }

    /**
     * Setter {@link ServiceManager}.
     * @param servicemanagerBean {@link ServiceManager}
     */
    public void setServicemanagerBean(IServiceManager servicemanagerBean) {
        this.servicemanager = servicemanagerBean;
    }

    protected void init() {
    }

    /**
     * Add message to context.
     * @param forS forS
     * @param message message
     * @param description description
     * @param severity severity
     */
    public void showMessage(String forS, String message, String description,
            Severity severity) {
        final FacesContext context = FacesContext.getCurrentInstance();
        final FacesMessage fm = new FacesMessage(message, description);
        fm.setSeverity(severity);
        context.addMessage(forS, fm);
    }

    /**
     * add message to context
     *
     * @param message message
     * @param description description
     * @param severity severity
     */
    public void showMessage(String message, String description, Severity severity) {
        showMessage(null, message, description, severity);
    }

    /**
     * add info message to context
     *
     * @param message message
     * @param description description
     */
    public void addInfoMessage(String message, String description) {
        showMessage(null, message, description, FacesMessage.SEVERITY_INFO);
    }

    /**
     * Add error message to context.
     * @param message message
     * @param description  description
     */
    public void addErrorMessage(String message, String description) {
        showMessage(null, message, description, FacesMessage.SEVERITY_ERROR);
    }

    /**
     * Add warning message to context.
     * @param message message
     * @param description description
     */
    public void addWarningMessage(String message, String description) {
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
    public String getMessageProperties(String propertieId) {
        return  getServicemanager().getMessageSource() == null ? propertieId
                : getServicemanager().getMessageSource().getMessage(
                        propertieId, null, null);
    }

    /**
     * Get Username.
     * @return username
     */
    public String getUsername(){
        return getSecCtx().getAuthentication().getName();
    }

    /**
     * Get {@link SecUserSecondary} by Name.
     * @return {@link SecUserSecondary}
     */
    public SecUserSecondary getUsernameByName(){
        return getServicemanager().getApplicationServices().getSecurityService().findUserByUserName(getUsername());
    }

    /**
     * @return
     */
    @Deprecated
    protected boolean isOneRow() {
        return isOneRow;
    }

    /**
     * @param isOneRow
     */
    @Deprecated
    protected void setOneRow(boolean isOneRow) {
        this.isOneRow = isOneRow;
    }

    /**
     * @return the secCtx
     */
    protected SecurityContext getSecCtx() {
        return this.secCtx = SecurityContextHolder.getContext();
    }

    /**
     * Get Username of user client.
     * @return
     */
    protected String getUserPrincipalUsername(){
       return getSecCtx().getAuthentication().getName();
    }

    /**
     *
     * @return
     */
    protected ISecurityService getSecurityService(){
        return getServicemanager().getApplicationServices().getSecurityService();
    }
}
