/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */

package org.encuestame.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import junit.framework.Assert;

import net.tanesha.recaptcha.ReCaptcha;

import org.apache.log4j.Logger;
import org.encuestame.business.security.AbstractSecurityContext;
import org.encuestame.business.service.AbstractSurveyService;
import org.encuestame.business.service.ProjectService;
import org.encuestame.business.service.SecurityService;
import org.encuestame.business.service.ServiceManager;
import org.encuestame.business.service.TweetPollService;
import org.encuestame.business.service.imp.ILocationService;
import org.encuestame.business.service.imp.IPictureService;
import org.encuestame.business.service.imp.IPollService;
import org.encuestame.business.service.imp.IProjectService;
import org.encuestame.business.service.imp.SecurityOperations;
import org.encuestame.business.service.imp.IServiceManager;
import org.encuestame.business.service.imp.ISurveyService;
import org.encuestame.business.service.imp.ITweetPollService;
import org.encuestame.core.security.EnMeUserDetails;
import org.encuestame.core.security.SecurityUtils;
import org.encuestame.core.security.util.HTMLInputFilter;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.EnMePermission;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.security.ProfileUserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Base Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 13, 2010 10:41:38 PM
 * @version $Id: $
 */
@SuppressWarnings("deprecation")
public abstract class BaseController extends AbstractSecurityContext{

     protected Logger log = Logger.getLogger(this.getClass());

     public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);

     public static final SimpleDateFormat SIMPLE_TIME_FORMAT = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_TIME);

     /**
      * {@link ReCaptcha}.
      */
     private ReCaptcha reCaptcha;

    /**
     * {@link ServiceManager}.
     */
    @Autowired
    private IServiceManager serviceManager;

    /** Force Proxy Pass Enabled. **/
    @Value("${application.proxyPass}") private Boolean proxyPass;

    /**
     * {@link AuthenticationManager}.
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * @return the serviceManager
     */
    public IServiceManager getServiceManager() {
        return serviceManager;
    }

    /**
     * Get Current Request Attributes.
     * @return {@link RequestAttributes}
     */
    public RequestAttributes getContexHolder(){
         return RequestContextHolder.currentRequestAttributes();
    }

    /**
     * Get {@link ServletRequestAttributes}.
     * @return {@link ServletRequestAttributes}
     */
    public HttpServletRequest getServletRequestAttributes(){
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    /**
     * Build Domain.
     * @param request {@link HttpServletRequest}.
     * @return
     */
    public String getDomain(final HttpServletRequest request){
            final StringBuffer stringBuffer = new StringBuffer(this.isSecure(request) ? "https://" : "http://");
            stringBuffer.append(request.getServerName());
            if(request.getRemotePort() != 80){
                stringBuffer.append(":");
                stringBuffer.append(request.getLocalPort());
            }
            stringBuffer.append(request.getContextPath());
            return stringBuffer.toString();
    }

    /**
     *
     * @param request
     * @return
     */
    public Boolean isSecure(final HttpServletRequest request){
        Boolean secure = false;
        final String protocol = request.getProtocol();
        if (protocol.indexOf("HTTPS") > -1) {
            secure = true;
        } else {
            secure = false;
            }
        return secure;
    }

    /**
     * Get By Username.
     * @param username username
     * @return
     */
    public UserAccount getByUsername(final String username){
        return getServiceManager().getApplicationServices().getSecurityService().findUserByUserName(username);
    }

    /**
     * Fetch user account currently logged.
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public UserAccount getUserAccount() throws EnMeNoResultsFoundException{
        final UserAccount account = this.getByUsername(this.getUserPrincipalUsername());
        if(account == null){
            log.fatal("user not found");
            throw new EnMeNoResultsFoundException("user not found");
        }
        return account;
    }

    /**
     * Get Ip Client.
     * @return ip
     */
    public String getIpClient(){
        log.debug("Force Proxy Pass ["+this.proxyPass+"]");
        String ip = getServletRequestAttributes().getRemoteAddr();
        log.debug("Force Proxy Pass ["+ip+"]");
        //FIXME: if your server use ProxyPass you need get IP from x-forwarder-for, we need create
        // a switch change for ProxyPass to normal get client Id.
        // Solution should be TOMCAT configuration.
        log.debug("X-getHeaderNames ["+ getServletRequestAttributes().getHeaderNames()+"]");
        if(this.proxyPass){
            ip = getServletRequestAttributes().getHeader("X-FORWARDED-FOR");
            log.debug("X-FORWARDED-FOR ["+ip+"]");
        }
        return ip;
    }

    /**
     * Authenticate.
     * @param request {@link HttpServletRequest}
     * @param username username
     * @param password password
     */
    public void authenticate(HttpServletRequest request, final String username, final String password) {
        try{
            final UsernamePasswordAuthenticationToken usernameAndPassword = new UsernamePasswordAuthenticationToken(username, password);
            final HttpSession session = request.getSession();
            session.setAttribute(
                    UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY,
                    username);

            final Authentication auth = getAuthenticationManager().authenticate(usernameAndPassword);

            final SecurityContext securityContext = getSecCtx();
            securityContext.setAuthentication(auth);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

        }
        catch (AuthenticationException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            log.error("Authenticate", e);
        }
    }

    /**
     * Authenticate User.
     * @param user
     */
    public void authenticate(final UserAccount user){
        final EnMeUserDetails details = SecurityUtils.convertUserAccount(user, true);
        final Collection<GrantedAuthority> authorities = details.getAuthorities();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(details, null,
                authorities));
        log.debug("SecurityContextHolder.getContext()"+SecurityContextHolder.getContext().getAuthentication());
        log.debug("SecurityContextHolder.getContext()"+SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        log.debug("SecurityContextHolder.getContext()"+SecurityContextHolder.getContext().getAuthentication().getName());
        log.debug("SecurityContextHolder.getContext()"+SecurityContextHolder.getContext().getAuthentication().getAuthorities());
    }


    /**
     * Get Message with Locale.
     * @param message
     * @param request
     * @param args
     * @return
     */
    public String getMessage(final String message, final HttpServletRequest request, Object[] args){
        return getServiceManager().getMessageSource().getMessage(message, args, getLocale(request));
    }

    /**
     * Get Message.
     * @param message
     * @param args
     * @return
     */
    public String getMessage(final String message, Object[] args){
        return getMessage(message, null, args);
    }

    /**
     * Get Message.
     * @param message
     * @return
     */
    public String getMessage(final String message){
        return getMessage(message, null, null);
    }

    /**
     * Get Locale.
     * @param request
     * @return
     */
    private Locale getLocale(final HttpServletRequest request){
        return RequestContextUtils.getLocale(request);
    }

    /**
     * Filter Value.
     * @param value value.
     * @return
     */
    public String filterValue(String value){
        final HTMLInputFilter vFilter = new HTMLInputFilter(true);
        return vFilter.filter(value);
    }

    /**
     * @param serviceManager
     *            the serviceManager to set
     */
    public void setServiceManager(IServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    /**
     * Get {@link AbstractSurveyService}.
     * @return survey service
     */
    public ISurveyService getSurveyService(){
        return getServiceManager().getApplicationServices().getSurveyService();
    }

    /**
     * Get {@link SecurityService}.
     * @return
     */
    public SecurityOperations getSecurityService(){
        return getServiceManager().getApplicationServices().getSecurityService();
    }

    /**
     * Location Service.
     * @return
     */
    public ILocationService getLocationService(){
        return getServiceManager().getApplicationServices().getLocationService();
    }

    /**
     * Get {@link TweetPollService}.
     * @return
     */
    public ITweetPollService getTweetPollService(){
        return getServiceManager().getApplicationServices().getTweetPollService();
    }

    public IPollService getPollService(){
        return getServiceManager().getApplicationServices().getPollService();

    }

    /**
     * Get {@link ProjectService}.
     * @return
     */
    public IProjectService getProjectService(){
        return getServiceManager().getApplicationServices().getProjectService();
    }

    /**
     * Get Picture Service.
     * @return
     */
    public IPictureService getPictureService(){
        return getServiceManager().getApplicationServices().getPictureService();
    }

    /**
     * @return the authenticationManager
     */
    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    /**
     * @param authenticationManager the authenticationManager to set
     */
    public void setAuthenticationManager(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    /**
     * @return the reCaptcha
     */
    public ReCaptcha getReCaptcha() {
        return reCaptcha;
    }

    /**
     * @param reCaptcha
     *            the reCaptcha to set
     */
    @Autowired
    public void setReCaptcha(final ReCaptcha reCaptcha) {
        this.reCaptcha = reCaptcha;
    }

    /**
     * Get Format Date.
     * @param date
     * @return
     */
    public Date getFormatDate(final String date){
        Assert.assertNotNull(date);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);
        simpleDateFormat.format(DateUtil.DEFAULT_FORMAT_DATE);
        return simpleDateFormat.getCalendar().getTime();
    }

    /**
     * Get full profile logged user info.
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public ProfileUserAccount getProfileUserInfo() throws EnMeNoResultsFoundException{
        return ConvertDomainBean.convertUserAccountToUserProfileBean(getUserAccount());
    }
}
