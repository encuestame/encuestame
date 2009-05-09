package org.jp.web.beans.login;

import java.io.IOException;
import java.util.Locale;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
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
 * 
 * Id: LoginSecurityBean.java Date: 08/05/2009 14:40:44
 * 
 * @author juanpicado package: org.jp.web.beans.login
 * @version 1.0
 */
public class LoginSecurityBean implements InitializingBean {

	 private final Log log = LogFactory.getLog(getClass());
	
	private AuthenticationManager authenticationManager;
	private String userName;
	private String userPassword;
	private String defaultLocale;
	private Boolean rememberMe = Boolean.FALSE;
	private Application app;
	protected MessageSource m_messageSource;

	private RememberMeServices rememberMeServices;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getDefaultLocale() {
		return defaultLocale;
	}

	public void setDefaultLocale(String defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

	public Boolean getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(
			AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/**
	 * 
	
	public void setMessageSource(MessageSource i_messageSource) {
		m_messageSource = i_messageSource;
	}*/

	
	/**
	 * Se ejecuta al inicio del ciclo de vida
	 * Explicado en http://java-matias.blogspot.com/2008/12/spring-25-dia-2.html
	 */
	public void afterPropertiesSet() throws Exception {		
		Locale locale = getFacesContext().getViewRoot().getLocale();		
		defaultLocale = locale.toString();
	}

	/**
	 * Ejecuta un envio a un url definido por la navegación JSF
	 * @param viewId
	 */
	protected void forward(String viewId) {
    	ViewHandler viewHandler = getApplication().getViewHandler();
	    FacesContext facesCtx = getFacesContext();
	    UIViewRoot view = viewHandler.createView(facesCtx, viewId);
	    facesCtx.setViewRoot(view);
	    facesCtx.renderResponse();
	}

	/**
	 * Obtiene el contexto de la Aplicación
	 * @return
	 */
	protected Application getApplication() {
		if (app == null) {
			ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
	        app = appFactory.getApplication();
		} 
		return app;
	}

	/**
	 * Obtiene el Contexto de JSF
	 * @return
	 */
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	/**
	 * Obtiene la Petición del HttpServletRequest
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		ExternalContext context = 
			FacesContext.getCurrentInstance().getExternalContext();
	    HttpServletRequest request = 
	    	(HttpServletRequest) context.getRequest();	    
	    return request;
	}

	/**
	 * Obtiene la Respuesta del HttpServletResponse	
	 * @return
	 */
	protected HttpServletResponse getResponse() {
		ExternalContext context = 
			FacesContext.getCurrentInstance().getExternalContext();
		HttpServletResponse response = 
	    	(HttpServletResponse) context.getResponse();
	    
	    return response;
	}
	

	/** Performs Login Action
	 * 
	 * [AKA]Initially I tried to implement it very simple - as it displayed in http://wiki.apache.org/myfaces/JSF_and_Acegi
	 * But seems since we are using Facelets/Acegi it did not worked - during redirecting to the j_acegi_security_check I've got error from facelets
	 * So, I've got solution from here: http://www.jroller.com/page/fairTrade?entry=integrating_acegi_and_jsf_revisited
	 *  
	 * @return always null - because this method (if it is success) should call redirect
	 */
	public String login() {
		HttpServletRequest request = getRequest();
		log.info("1Request -->"+request);
		log.info("3Request -->"+request.getSession());
		
	    try {
	    	
	    	String userName = getUserName();
	    	log.info("userName -->"+userName);
	    	String password = getUserPassword();
	    	log.info("password -->"+password);
	    	//Creamos un token para Spring
	    	UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(userName, password);
	    	log.info("UsernamePasswordAuthenticationToken -->"+authReq);
	    	//le agregamos al toker el request de HttpServletRequest
	    	authReq.setDetails(new WebAuthenticationDetails(request));		
	    	//Creamos una sesión
	    	HttpSession session = request.getSession();
	    	log.info("HttpSession -->"+session);
	    	//Asignamos la sesión el atributo UserName
	    	session.setAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY, userName);
	    	//Obtenemos el manager auth y le asignamos el token
	    	Authentication auth = getAuthenticationManager().authenticate(authReq);	    	
	    	log.info("Authentication -->"+auth);
	    	// it is not used - so, commented
	    	//SecurityContext sessionSecCtx = (SecurityContext) session
		    //  .getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY); 
	    	//log.debug("SecurityContext from session [{}]"/*, sessionSecCtx != null ? sessionSecCtx.toString() : "null");
	    	
	    	//Obtenemos el Contexto de Spring Security
	    	SecurityContext secCtx = SecurityContextHolder.getContext();
	    	//log.debug("SecurityContext from holder [{}]"/*, secCtx != null ? secCtx.toString() : "null");
	    	
	    	//Le asignamos el Autentication Manager al Contexto de Seguridad
	    	secCtx.setAuthentication(auth);
	    	log.info("placing SecurityContext from holder into session");
	    	session.setAttribute(HttpSessionContextIntegrationFilter.SPRING_SECURITY_CONTEXT_KEY, secCtx);
	    			
	    	//set remember me
	    	/*
	    	if (rememberMe && rememberMeServices != null) {
	    		rememberMeServices.loginSuccess(request, getResponse(), auth);
	    	}
	    	
			if (defaultLocale != null) {
				Cookie cookie = new Cookie(LocaleRequestWrapper.PREFERED_LOCALE, defaultLocale);
				cookie.setMaxAge(60 * 60 * 24 * 365);
				getResponse().addCookie(cookie);
			}*/
			
	    	//userSettingsService.setDefaultLocale(defaultLocale);
	    	
	    	//Obtenemos la llave de la petición
	    	String urlKey = AbstractProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY;
	    	log.info("SPRING_SECURITY_SAVED_REQUEST_KEY -->"+urlKey);
	    	//Guardamos la llave
	    	SavedRequest savedRequest = (SavedRequest)session.getAttribute(urlKey);
	    	//Removemos la llave e la sesión
	    	session.removeAttribute(urlKey);
	    	
	    	String target = "/pages/index.me";
	    	
	    	//Si es diferente de nulo, obtenemos la dirección exacta del URL
	    	if (savedRequest != null) {
	    		log.info("Si es diferente de nulo, obtenemos la dirección exacta del URL -->"+savedRequest);
		    	String targetUrl = null;
	    		targetUrl = savedRequest.getFullRequestUrl();
	    		//Redireccionamos a la siguiente URL
	    		FacesContext.getCurrentInstance().getExternalContext().redirect(targetUrl);	    		
	    		return null;
	    		
	    	} else {
	    		//Si fuera nulo entonces
	    		log.info("Si fuera nulo entonces -->"+savedRequest);
	    		//if (m_trailController.getCurrentUrl() != null) {
		    	//	log.debug("authentication successful, forwarded to " + m_trailController.getCurrentUrl());
		    	//	FacesContext.getCurrentInstance().getExternalContext().redirect(m_trailController.getCurrentUrl());		    		
		    	//	return null;
		    	//}
	    	}
	    	
	    	//log.debug("authentication successful, forwarding to [{}] obtained from [{}]"/*, target, targetUrl);
	    	//
	    	forward(target);
	    	
	    } catch (BadCredentialsException e) {
	    	getFacesContext().addMessage(null, new FacesMessage(getLocalizedMessage("BAD_CRIDENTIALS")));
	    	
	    	if (rememberMeServices != null) {
	    		rememberMeServices.loginFail(request, getResponse());
	    	}
	    	
	    	return null;
	    } catch (AuthenticationException e) {
	    	getFacesContext().addMessage(null, new FacesMessage(getLocalizedMessage("AUTHENTICATION_ERROR")));
	    	
	    	if (rememberMeServices != null) {
	    		rememberMeServices.loginFail(request, getResponse());
	    	}
	    	
	    	return null;
	    } catch (IOException ioException) {
	    	getFacesContext().addMessage(null, new FacesMessage(ioException.getMessage()));
	    	
	    	return null;
	    }
	    	
	    // in case we have nothing in trails - go to main wiki page
	    log.info("in case we have nothing in trails - go to main wiki page-->");
		return "index";
	}
	
	public String getLocalizedMessage(String i_propertyId) {
        return m_messageSource == null ? i_propertyId : m_messageSource.getMessage(i_propertyId, null, null);
    }
}
