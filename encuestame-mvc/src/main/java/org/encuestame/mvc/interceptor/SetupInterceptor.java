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
import org.encuestame.business.setup.StartupProcess;
import org.springframework.web.servlet.ModelAndView;

/**
 * Setup interceptor.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 25, 2011
 */
public class SetupInterceptor extends AbstractEnMeInterceptor{

    private Logger log = Logger.getLogger(this.getClass());

    //@Autowired
    public StartupProcess startup;

    @Override
    public void afterCompletion(HttpServletRequest arg0,
            HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        log.debug("SetupInterceptor afterCompletion");
        log.info(this.startup);
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
         log.debug("SetupInterceptor postHandle");
         log.info(this.startup);

    }

    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2) throws Exception {
        log.debug("SetupInterceptor preHandle");
        log.info(this.startup);
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
