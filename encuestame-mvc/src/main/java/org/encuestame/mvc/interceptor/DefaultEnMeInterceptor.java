/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.encuestame.business.config.EncuestamePlaceHolderConfigurer;
import org.encuestame.core.security.SecurityUtils;
import org.joda.time.DateTimeZone;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

/**
 * Default Interceptor for all Controllers.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 26, 2010 3:34:59 PM
 */
public class DefaultEnMeInterceptor implements HandlerInterceptor {

    private static Logger log = Logger.getLogger(DefaultEnMeInterceptor.class);

    private final String COOKIE_NAME = "en_me_cookie";

    private final String COOKIE_LANGUAGE = "en_me-language";

    private final String COOKIE_TIMEZONE = "en_me-timezone";

    private final String COOKIE_CONTEXT = "en_me-context";

    /**
     * Default logo path.
     */
    private final String DEFAULT_LOGO = "/images/logos/logo.jpg";

    /**
     * Return customized logo path or default is customized is not defined.
     */
    private String getCustomizedHeaderLogo(){
        String customizedLogo = EncuestamePlaceHolderConfigurer.getProperty("application.logo.small");
        if (customizedLogo == null) {
            customizedLogo = this.DEFAULT_LOGO;
        }
        log.debug("customized logo:{"+customizedLogo);
        return customizedLogo;
    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        log.debug("preHandle");
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!SecurityUtils.checkIsSessionIsExpired(auth)) {
            log.debug("auth valid");
            if(SecurityUtils.checkIsSessionIsAnonymousUser(auth)){
                log.debug("session expired");
                request.setAttribute("logged", false);
            } else {
                //cookies
                log.debug("session is valid");
                Cookie cookieName = WebUtils.getCookie(request, this.COOKIE_NAME);
                if(cookieName != null){
                    log.debug("Cookie "+cookieName.getName());
                    cookieName.setValue(RandomStringUtils.random(4)); //TODO: testing cookies.
                }
                request.setAttribute("logged", true);
            }
        } else {
            log.info("Session Expired");
            request.setAttribute("logged", false);
        }
        //customized logo
        request.setAttribute("logo", this.getCustomizedHeaderLogo());
        return true;
    }

    /**
     *
     * @param cookieName
     * @param response
     */
    private void createAddCookie(
            final String cookieName,
            final HttpServletResponse response,
            final String value){
        //Cookie cookie = new Cookie(cookieName, value);
        //cookie.setMaxAge(expiry)
        //response.addCookie(cookie);
    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
            log.debug("postHandle");
            Cookie cookieName = WebUtils.getCookie(request, this.COOKIE_NAME);
            if(cookieName == null){
                this.createAddCookie(COOKIE_NAME, response, RandomStringUtils.random(4));
            }
            Cookie cookieLanguage = WebUtils.getCookie(request, this.COOKIE_LANGUAGE);
            if(cookieLanguage == null){
                this.createAddCookie(COOKIE_LANGUAGE, response, request.getLocale().toString());
            }
            Cookie cookieTimeZone = WebUtils.getCookie(request, this.COOKIE_TIMEZONE);
            if(cookieTimeZone == null){
                this.createAddCookie(COOKIE_TIMEZONE, response, DateTimeZone.getDefault().toTimeZone().toString());
            }
            Cookie cookieContext = WebUtils.getCookie(request, this.COOKIE_CONTEXT);
            if(cookieContext == null){
                this.createAddCookie(COOKIE_CONTEXT, response, request.getContextPath());
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
