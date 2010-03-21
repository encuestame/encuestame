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

import java.io.IOException;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.encuestame.web.beans.MasterBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.context.HttpSessionContextIntegrationFilter;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.security.ui.WebAuthenticationDetails;
import org.springframework.security.ui.rememberme.RememberMeServices;
import org.springframework.security.ui.savedrequest.SavedRequest;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;

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
    private final String TARGET = "/pages/index.me";

    /**
     * Getter.
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter.
     * @param userName userName
     */
    public void setUserName(final String userName) {
        this.userName = userName;
    }

    /**
     * Getter.
     * @return userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Setter.
     * @param userPassword userPassword
     */
    public void setUserPassword(final String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * Getter.
     * @return defaultLocale
     */
    public String getDefaultLocale() {
        return defaultLocale;
    }

    /**
     * Default Locale.
     * @param defaultLocale defaultLocale
     */
    public void setDefaultLocale(final  String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    /**
     * Getter.
     * @return rememberMe
     */
    public Boolean getRememberMe() {
        return rememberMe;
    }

    /**
     * Setter.
     * @param rememberMe rememberMe
     */
    public void setRememberMe(final  Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    /**
     * Getter.
     * @return authenticationManager
     */
    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    /**
     * Setter.
     * @param authenticationManager authenticationManager
     */
    public void setAuthenticationManager(
            final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Getter.
     * Execute in the first phase of life cycle
     * http://java-matias.blogspot.com/2008/12/spring-25-dia-2.html
     */
    public void afterPropertiesSet() throws Exception {
        defaultLocale = getFacesContext().getViewRoot().getLocale().toString();
    }

    /**
     * Send to url.
     * @param urlPath url to foward
     */
    protected void forward(final String urlPath) {
        //get face context
        final FacesContext facesCtx = getFacesContext();
        final UIViewRoot view = getApplication().getViewHandler().createView(facesCtx, urlPath);
        facesCtx.setViewRoot(view);
        facesCtx.renderResponse();
    }

    /**
     * Get application context.
     */
    protected Application getApplication() {
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
    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Get Response.
     * @return http servlet response
     */
    protected HttpServletResponse getResponse() {
        return (HttpServletResponse) getFacesContext().getExternalContext()
        .getResponse();
    }

    /**
     * Login Action
     * http://www.jroller.com/fairTrade/entry/integrating_acegi_and_jsf_revisited
     * @return login
     */
    public String login() {
        HttpServletRequest request = getRequest();
        try {
            final String userName = getUserName();
            log.info("username "+userName);
            final String password = getUserPassword();
            log.info("password "+password);
            //create spring token
            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userName, password);
            log.info("token "+token);
            log.info("tCredentials "+token.getCredentials());
            log.info("tCredentials "+token.getDetails());
            // add to token de servlet request
            token.setDetails(new WebAuthenticationDetails(request));
            // create a http session
            final  HttpSession session = request.getSession();
            // set username attribute to session
            session.setAttribute(
            AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY,
            userName);
            // get authentication manager and setter token
            final Authentication authenticationManager = getAuthenticationManager().authenticate(
                    token);
            log.info("authenticationManager "+authenticationManager);
            //TODO: need investigate this.
            // it is not used - so, commented
            // SecurityContext sessionSecCtx = (SecurityContext) session
            // .getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY);
            // log.debug("SecurityContext from session [{}]"/*, sessionSecCtx !=
            // null ? sessionSecCtx.toString() : "null");

            // get spring security context
            final SecurityContext secCtx = SecurityContextHolder.getContext();

            // set to security context the autentication manager
            secCtx.setAuthentication(authenticationManager);
            session
                    .setAttribute(
                            HttpSessionContextIntegrationFilter.SPRING_SECURITY_CONTEXT_KEY,
                            secCtx);

            // set remember me TODO: need implement.
            /*
             * if (rememberMe && rememberMeServices != null) {
             * rememberMeServices.loginSuccess(request, getResponse(), auth); }
             *
             * if (defaultLocale != null) { Cookie cookie = new
             * Cookie(LocaleRequestWrapper.PREFERED_LOCALE, defaultLocale);
             * cookie.setMaxAge(60 * 60 * 24 * 365);
             * getResponse().addCookie(cookie); }
             */

            //set default locale
            //TODO: need test this.
            // userSettingsService.setDefaultLocale(defaultLocale);

            // get request key
            final String urlKey = AbstractProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY;
            // save request key
            log.info("urlKey "+urlKey);
            final SavedRequest savedRequest = (SavedRequest) session
                    .getAttribute(urlKey);
            // remove key to session
            session.removeAttribute(urlKey);
            String targetUrl = null;
            // if diferent of null get url
            log.info("savedRequest "+savedRequest);
            if (savedRequest != null) {
                targetUrl = savedRequest.getFullRequestUrl();
                log.info("save request "+targetUrl);
                // Redireccionamos a la siguiente URL
                getFacesContext().getExternalContext().redirect(targetUrl);
            }
             log.info("authentication successful, forwarding to ["+TARGET+"] obtained from [{"+targetUrl+"}]");
             forward(TARGET);
        } catch (BadCredentialsException e) {
            log.error("BadCredentialsException "+e);
            addErrorMessage((getMessageProperties("error_credentials")), e
                    .getMessage());
            if (rememberMeServices != null) {
                rememberMeServices.loginFail(request, getResponse());
            }
        } catch (AuthenticationException e) {
            addErrorMessage(getMessageProperties("auth_credentials"), e
                    .getMessage());
            log.error("AuthenticationException "+e);
            if (rememberMeServices != null) {
                rememberMeServices.loginFail(request, getResponse());
            }
        } catch (IOException ioException) {
            log.error("IOException "+ioException);
            addErrorMessage(ioException.getMessage(), ioException.getMessage());
            return null;
        } catch (Exception e) {
            log.error("Exception "+e);
            addErrorMessage(getMessageProperties("indefined_error"), e
                    .getMessage());
        }
        log.info("index login");
        return "index";
    }

}
