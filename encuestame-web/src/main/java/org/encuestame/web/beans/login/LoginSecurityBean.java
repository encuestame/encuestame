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
package org.encuestame.web.beans.login;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.encuestame.web.beans.MasterBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.RememberMeServices;

/**
 * Login Security Bean.
 * @author Picado, Juan juan@encuestame.org
 * @since 08/05/2009 14:40:44
 * @version $Id$
 */
public class LoginSecurityBean extends MasterBean implements InitializingBean {

    /** AuthenticationManager. */
    private AuthenticationManager authenticationManager;
    /** user name. */
    private String userName;
    /** user password. **/
    private String userPassword;
    /** default localte. */
    private String defaultLocale;
    /** remember session flag. */
    private Boolean rememberMe = Boolean.FALSE;
    /** application face. */
    private Application applicationFaces;
    /** source of message */
    protected MessageSource m_messageSource;
    /** remember services. */
    private RememberMeServices rememberMeServices;
    /** index target */
    private static final String TARGET = "/pages/index.me";

    /**
     * Getter.
     * @return userName
     */
    public final String getUserName() {
        return userName;
    }

    /**
     * Setter.
     * @param userName userName
     */
    public final void setUserName(final String userName) {
        this.userName = userName;
    }

    /**
     * Getter.
     * @return userPassword
     */
    public final String getUserPassword() {
        return userPassword;
    }

    /**
     * Setter.
     * @param userPassword userPassword
     */
    public final void setUserPassword(final String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * Getter.
     * @return defaultLocale
     */
    public final String getDefaultLocale() {
        return defaultLocale;
    }

    /**
     * Default Locale.
     * @param defaultLocale defaultLocale
     */
    public final void setDefaultLocale(final  String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    /**
     * Getter.
     * @return rememberMe
     */
    public final Boolean getRememberMe() {
        return rememberMe;
    }

    /**
     * Setter.
     * @param rememberMe rememberMe
     */
    public final void setRememberMe(final  Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    /**
     * Getter.
     * @return authenticationManager
     */
    public final AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    /**
     * Setter.
     * @param authenticationManager authenticationManager
     */
    public final void setAuthenticationManager(
            final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Getter.
     * Execute in the first phase of life cycle
     * http://java-matias.blogspot.com/2008/12/spring-25-dia-2.html
     */
    public final void afterPropertiesSet() throws Exception {
        defaultLocale = getFacesContext().getViewRoot().getLocale().toString();
    }

    /**
     * Send to url.
     * @param urlPath url to foward
     */
    protected final void forward(final String urlPath) {
        //get face context
        final FacesContext facesCtx = getFacesContext();
        final UIViewRoot view = getApplication().getViewHandler().createView(facesCtx, urlPath);
        facesCtx.setViewRoot(view);
        facesCtx.renderResponse();
    }

    /**
     * Get application context.
     */
    protected final Application getApplication() {
        if (applicationFaces == null) {
            ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder
                    .getFactory(FactoryFinder.APPLICATION_FACTORY);
            applicationFaces = appFactory.getApplication();
        }
        return applicationFaces;
    }

    /**
     * Get current face context.
     */
    protected final FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Get Response.
     * @return http servlet response
     */
    protected final HttpServletResponse getResponse() {
        return (HttpServletResponse) getFacesContext().getExternalContext()
        .getResponse();
    }

    /**
     * Login Action
     * http://www.jroller.com/fairTrade/entry/integrating_acegi_and_jsf_revisited
     * @return login
     */
    public final String login() {
        return "index";
    }

}
