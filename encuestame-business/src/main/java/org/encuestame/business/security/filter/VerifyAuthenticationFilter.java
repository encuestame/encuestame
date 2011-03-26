/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.security.filter;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Verify Authentication Filter.
 * @author Picado, Juan juanATencuestameDOTorg
 * @since Aug 21, 2010 10:41:38 PM
 */
public class VerifyAuthenticationFilter implements Filter {

    private Logger log = Logger.getLogger(this.getClass());

    private  String loginUrl = "/user/signin";

    private  String redirectUrl = "/account/dashboard";

    /**
     * Do Filter.
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

         final HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
         final HttpServletResponse responseHttp = (HttpServletResponse) response;
         final String pauthUrl = httpServletRequest.getRequestURI();

         try{
             final SecurityContext securityContext = SecurityContextHolder.getContext();
             final Authentication authentication = securityContext.getAuthentication();

             if(authentication != null && authentication.isAuthenticated()){
                 final StringBuffer loginUrlPath = new StringBuffer();
                 loginUrlPath.append(httpServletRequest.getContextPath());
                 loginUrlPath.append(loginUrl);
                 //TODO: login, remember password and sign up should be in this condition
                 if(pauthUrl.equals(loginUrlPath.toString())){
                      log.debug("redirect processing");
                      //responseHttp.sendRedirect("/account/dashboard");
                      response.reset();
                      response.resetBuffer();
                      final Collection<? extends GrantedAuthority>  authorities = authentication
                      .getAuthorities();
                      for (GrantedAuthority grantedAuthority : authorities) {
                          if(grantedAuthority.getAuthority().equals("ENCUESTAME_USER")){
                              final StringBuffer redirectUrlPath = new StringBuffer();
                              redirectUrlPath.append(httpServletRequest.getContextPath());
                              redirectUrlPath.append(this.redirectUrl);
                              responseHttp.sendRedirect(redirectUrlPath.toString());
                          }
                          else{
                              log.debug("not access role");
                          }
                      }
                 }
             }
             else{
                 log.debug("not logged");
             }
         }catch (Exception e) {
            log.error("error on validation filter "+e.getMessage());
        }

         // Forward the request to the next resource in the chain
         chain.doFilter(request, response);
    }

    public void destroy() {
        //nothing
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        //nothing
    }

}
