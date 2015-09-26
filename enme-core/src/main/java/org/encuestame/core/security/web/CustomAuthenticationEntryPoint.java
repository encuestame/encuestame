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
package org.encuestame.core.security.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * Description Class.
 * @author Picado, Juan juanATencuestame.org
 * @since Jan 30, 2011 1:11:52 PM
 * @version Id:
 */
public class CustomAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private boolean forceHttps = false;

    private boolean useForward = false;

    private static final Log logger = LogFactory.getLog(CustomAuthenticationEntryPoint.class);

    /**
     * 
     * @param loginFormUrl
     * @param redirectStrategy
     */
    public CustomAuthenticationEntryPoint(String loginFormUrl, RedirectStrategy redirectStrategy) {
        super(loginFormUrl);
        this.redirectStrategy = redirectStrategy;
    }

    /**
     *
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            org.springframework.security.core.AuthenticationException authException)
            throws IOException, ServletException {

        if (authException != null) {
            // you can check for the spefic exception here and redirect like
            // this
            logger.debug("respnse"+response.toString());
            logger.debug("respnse"+response.getContentType());
            logger.debug("request"+request.toString());
            logger.debug("request"+request.getContentType());
            //response.sendRedirect("403.html");
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String redirectUrl = null;

        if (useForward) {

            if (forceHttps && "http".equals(request.getScheme())) {
                // First redirect the current request to HTTPS.
                // When that request is received, the forward to the login page
                // will be used.
                redirectUrl = buildHttpsRedirectUrlForRequest(httpRequest);
            }

            if (redirectUrl == null) {
                String loginForm = determineUrlToUseForThisRequest(httpRequest,
                        httpResponse, authException);

                if (logger.isDebugEnabled()) {
                    logger.debug("Server side forward to: " + loginForm);
                }

                RequestDispatcher dispatcher = httpRequest
                        .getRequestDispatcher(loginForm);

                dispatcher.forward(request, response);

                return;
            }
        } else {
            // redirect to login page. Use https if forceHttps true

            redirectUrl = buildRedirectUrlToLoginPage(httpRequest,
                    httpResponse, authException);

        }

        redirectStrategy.sendRedirect(httpRequest, httpResponse, redirectUrl);
    }

    /**
     * @return the redirectStrategy
     */
    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    /**
     * @param redirectStrategy the redirectStrategy to set
     */
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    /**
     * @return the forceHttps
     */
    public boolean isForceHttps() {
        return forceHttps;
    }

    /**
     * @param forceHttps the forceHttps to set
     */
    public void setForceHttps(boolean forceHttps) {
        this.forceHttps = forceHttps;
    }

    /**
     * @return the useForward
     */
    public boolean isUseForward() {
        return useForward;
    }

    /**
     * @param useForward the useForward to set
     */
    public void setUseForward(boolean useForward) {
        this.useForward = useForward;
    }
}
