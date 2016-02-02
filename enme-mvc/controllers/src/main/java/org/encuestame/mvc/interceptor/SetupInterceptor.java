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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.setup.StartupProcess;
import org.encuestame.core.util.EnMePlaceHolderConfigurer;
import org.encuestame.utils.PathUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

/**
 * Setup interceptor.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 25, 2011
 */
public class SetupInterceptor extends AbstractEnMeInterceptor{

    /**
     *
     */
    private Log log = LogFactory.getLog(this.getClass());

            //@Autowired
    public StartupProcess startup;

    /*
     * (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0,
            HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        log.trace("SetupInterceptor afterCompletion");
        log.info(this.startup);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
         log.trace("SetupInterceptor postHandle");
         log.info(this.startup);

    }

    /*
     * (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Object arg2) throws Exception {
        // get the current context path.
        final String context = httpServletRequest.getContextPath();
        // build the path
        final StringBuilder path = new StringBuilder(context);
        path.append(PathUtil.setup);
        if (log.isTraceEnabled()) {
            log.trace("Request URI "+httpServletRequest.getRequestURI());
            log.trace("Context Path URI "+path.toString());
            log.trace("Context Path URI "+ (httpServletRequest.getRequestURI().equalsIgnoreCase(path.toString())));
            log.trace("Context Path URI "+ (httpServletRequest.getRequestURI().equals(path.toString())));
        }
        // check if the uri match with the setup uri
        if (!httpServletRequest.getRequestURI().toString().equals(path.toString())) {
            final String uuid = EnMePlaceHolderConfigurer.getConfigurationManager().getProperty("install.uuid");
            if (uuid == null) {
                     log.info("system not installed ...");
                     final ModelAndView modelAndView = new ModelAndView("redirect:/setup");
                     throw new ModelAndViewDefiningException(modelAndView);
            }
        } else {
            log.trace("you are on setup interface ...");
        }

        log.trace("SetupInterceptor preHandle");
        return true;
    }

    /**
     * @return the startup
     */
    public StartupProcess getStartup() {
        return startup;
    }

    /**
     * @param startup the startup to set
     */
    public void setStartup(StartupProcess startup) {
        this.startup = startup;
    }
}
