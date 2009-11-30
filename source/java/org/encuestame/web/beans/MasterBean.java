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

    public MasterBean() {}

    /**
     *
     * @param serviceBeanName
     * @return
     */
    public Object lookupService(String serviceBeanName) {
        return appContext.getBean(serviceBeanName);
    }

    /**
     *
     * @return
     */
    public IServiceManager getServicemanager() {
        return servicemanager;
    }

    /**
     *
     * @param servicemanagerBean
     */
    public void setServicemanagerBean(IServiceManager servicemanagerBean) {
        this.servicemanager = servicemanagerBean;
    }

    protected void init() {
    }

    /**
     * add message to context
     *
     * @param forS
     * @param message
     * @param descrip
     * @param severity
     */
    public void showMessage(String forS, String message, String descrip,
            Severity severity) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(message, descrip);
        fm.setSeverity(severity);
        context.addMessage(forS, fm);
    }

    /**
     * add message to context
     *
     * @param message
     * @param descrip
     * @param severity
     */
    public void showMessage(String message, String descrip, Severity severity) {
        showMessage(null, message, descrip, severity);
    }

    /**
     * add info message to context
     *
     * @param i_message
     * @param i_detailedMessage
     */
    public void addInfoMessage(String message, String description) {
        showMessage(null, message, description, FacesMessage.SEVERITY_INFO);
    }

    /**
     * add error message to context
     *
     * @param i_message
     * @param i_detailedMessage
     */
    public void addErrorMessage(String message, String description) {
        showMessage(null, message, description, FacesMessage.SEVERITY_ERROR);
    }


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
     * @return the localized message if it is exists, otherwise the specified
     *         property id
     */
    public String getMessageProperties(String i_propertyId) {
        return  getServicemanager().getMessageSource() == null ? i_propertyId
                : getServicemanager().getMessageSource().getMessage(
                        i_propertyId, null, null);
    }

    public boolean isOneRow() {
        return isOneRow;
    }

    public void setOneRow(boolean isOneRow) {
        this.isOneRow = isOneRow;
    }

}
