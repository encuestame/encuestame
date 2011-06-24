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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.encuestame.core.files.PathUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

/**
 * Description.
 * @author Picado, Juan juanATencuestame.org
 * @since May 5, 2011
 */
public class SignInInterceptor extends AbstractEnMeInterceptor{

    private static Logger log = Logger.getLogger(SignInInterceptor.class);

    /*
     * (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0,
            HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2) throws Exception {
        String context = arg0.getContextPath();
        StringBuilder path = new StringBuilder(context);
        path.append(PathUtil.signIn);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.trace("Sign In Auth:{ "+authentication);
        //if (arg0.getRequestURI().equals(path.toString())) {
         //   log.debug("Sign In are equals:{ "+arg0.getRequestURI());
        //    if (authentication != null) {
//                if (!SecurityUtils.checkIsSessionIsAnonymousUser(authentication)) {
//                    log.debug("Sign In session is valid");
//                    for (GrantedAuthority auth : authentication.getAuthorities()) {
//                        log.debug("Sign In Auth:{ "+auth.getAuthority());
//                        if (EnMePermission.getPermissionString(
//                                auth.getAuthority()).equals(
//                                EnMePermission.ENCUESTAME_USER)) {
//                            log.debug("User is logged, redirec to dashboard");
//                            arg1.sendRedirect(arg0.getContextPath()+"/user/dashboard");
//                            break;
//                        }
//                    }
//                }
          //  }
        //}
        return true;
    }

}
