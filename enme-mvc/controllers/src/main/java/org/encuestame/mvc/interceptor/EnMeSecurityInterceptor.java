/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.mvc.interceptor;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.config.startup.EnMePlaceHolderConfigurer;
import org.encuestame.core.security.web.SecurityUtils;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.core.util.WidgetUtil;
import org.encuestame.persistence.domain.security.UserAccount;
import org.joda.time.DateTimeZone;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Default Interceptor for all Controllers.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 26, 2010 3:34:59 PM
 */
public class EnMeSecurityInterceptor extends AbstractEnMeInterceptor {

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * default cookie name.
     */
    private final String COOKIE_NAME = "en_me_cookie";

    /**
     * language cookie name.
     */
    private final String COOKIE_LANGUAGE = "en_me-language";

    /**
     * timezone cookie name.
     */
    private final String COOKIE_TIMEZONE = "en_me-timezone";

    /**
     * context cookie nane.
     */
    private final String COOKIE_CONTEXT = "en_me-context";

    /**
     * Default logo path.
     */
    private final String DEFAULT_LOGO = "/images/logos/logo.jpg";

    /**
     * Return customized logo path or default is customized is not defined.
     */
    private String getCustomizedHeaderLogo() {
        String customizedLogo = EnMePlaceHolderConfigurer.getProperty("application.logo.small");
        if (customizedLogo == null) {
            customizedLogo = this.DEFAULT_LOGO;
        }
        log.trace("customized logo:{"+customizedLogo);
        return customizedLogo;
    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
    	request.setAttribute("user_locale", WidgetUtil.getCurrentLocale(request));
        request.setAttribute("hide_header_menu", false);
        request.setAttribute("help_links", false);
        final Authentication auth = getSecCtx().getAuthentication();
        log.trace("preHandle security auth "+auth);
        if (!SecurityUtils.checkIsSessionIsExpired(auth)) {
            log.trace("auth valid");
            if (SecurityUtils.checkIsSessionIsAnonymousUser(auth)) {
                log.trace("session expired");
                request.setAttribute("logged", false);
                request.setAttribute("help_links", false);
            } else {
                //cookies
                log.trace("session is valid");
            	final String username = getUserPrincipalUsername();
                final UserAccount user = getByUsername(username);
                request.setAttribute("account", ConvertDomainBean.convertUserAccountToSignUpBean(user));
                //help links ENCUESTAME-668
                final String mappedPath = request.getRequestURI().replace(request.getContextPath(), "");
                if (getListPaths().indexOf(mappedPath) != -1) { //path inside of the list of helps
                    Boolean status = getSecurityService().checkHelpURL(mappedPath, getUserAccount());
                    request.setAttribute("mappedPath", mappedPath);
                    if (status) {
                        request.setAttribute("help_links", true);
                    } else {
                        request.setAttribute("help_links", false);
                    }
                }
                //
                if (username != EnMeUtils.ANONYMOUS_USER) {
        			getLocaleResolver().setLocale(request, response, getUserAccountLocale(user));			
        		}

                request.setAttribute("isActivated", user.getInviteCode() == null ? true : false);

                log.trace("Account User Interceptor "+user);

                //set language
                final String lang = WidgetUtil.convertToDojoLocale(user.getLanguage());
                log.debug("Language --->" + lang);
                request.setAttribute("user_locale", user.getLanguage() == null ? WidgetUtil.getCurrentLocale(request): lang);
                Cookie cookieName = WebUtils.getCookie(request, this.COOKIE_NAME);
                if (cookieName != null) {
                    log.trace("Cookie "+cookieName.getName());
                    cookieName.setValue(RandomStringUtils.random(4)); //TODO: testing cookies.
                }
                request.setAttribute("logged", true);
            }
        } else {
            log.info("Session Expired");
            request.setAttribute("logged", false);
        }
        //enable or disable development status
        request.setAttribute("development", EnMePlaceHolderConfigurer.getBooleanProperty("encuestame.development"));
        //customized logo
        request.setAttribute("logo", this.getCustomizedHeaderLogo());
        request.setAttribute("domain", WidgetUtil.getDomain(request));
        request.setAttribute("realPath", request.getRequestURI());
        return true;
    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
            //log.trace("postHandle");
            Cookie cookieName = WebUtils.getCookie(request, this.COOKIE_NAME);
            if(cookieName == null){
                createAddCookie(COOKIE_NAME, response, RandomStringUtils.random(4));
            }
            Cookie cookieLanguage = WebUtils.getCookie(request, this.COOKIE_LANGUAGE);
            if(cookieLanguage == null){
                createAddCookie(COOKIE_LANGUAGE, response, request.getLocale().toString());
            }
            Cookie cookieTimeZone = WebUtils.getCookie(request, this.COOKIE_TIMEZONE);
            if(cookieTimeZone == null){
                createAddCookie(COOKIE_TIMEZONE, response, DateTimeZone.getDefault().toTimeZone().toString());
            }
            Cookie cookieContext = WebUtils.getCookie(request, this.COOKIE_CONTEXT);
            if(cookieContext == null){
                createAddCookie(COOKIE_CONTEXT, response, request.getContextPath());
            }
    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
