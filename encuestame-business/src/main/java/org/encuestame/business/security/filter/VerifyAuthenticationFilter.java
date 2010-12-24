package org.encuestame.business.security.filter;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Verify Authentication Filter.
 * @author Picado, Juan juanATencuestameDOTorg
 * @since Aug 21, 2010 10:41:38 PM
 * @version $Id: $
 */
public class VerifyAuthenticationFilter implements Filter {

    protected Logger log = Logger.getLogger(this.getClass());

    protected  String loginUrl = "/user/signin";

    protected  String redirectUrl = "/dashboard.html";

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
