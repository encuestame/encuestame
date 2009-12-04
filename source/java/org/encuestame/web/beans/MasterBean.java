/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.encuestame.web.beans;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.service.IServiceManager;
import org.encuestame.core.service.ServiceManager;
import org.springframework.context.ApplicationContext;

/**
 * Master Bean.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since 26/04/2009
 * File name: $HeadURL$
 * Revision: $Revision$
 * Last modified: $Date$
 * Last modified by: $Author$
 */
public class MasterBean {

    private ApplicationContext appContext;
    protected IServiceManager servicemanager;
    protected Log log = LogFactory.getLog(this.getClass());
    protected boolean isOneRow;

    /**
     * Constructor.
     */
    public MasterBean() {}

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
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(message, description);
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
     * @return
     */
    protected boolean isOneRow() {
        return isOneRow;
    }

    /**
     * @param isOneRow
     */
    protected void setOneRow(boolean isOneRow) {
        this.isOneRow = isOneRow;
    }

}
