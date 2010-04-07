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

import javax.servlet.http.HttpServletRequest;

import org.encuestame.core.service.IServiceManager;
import org.encuestame.core.service.ISurveyService;
import org.encuestame.core.service.ServiceManager;
import org.encuestame.core.service.AbstractSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Base Controller.
 * @author Picado, Juan juan@encuestame.org
 * @since Mar 13, 2010 10:41:38 PM
 * @version $Id: $
 */
public class BaseController {

    /**
     * {@link ServiceManager}.
     */
    @Autowired
    private IServiceManager serviceManager;

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
     * Get Ip Client.
     * @return ip
     */
    public String getIpClient(){
        //FIXME if your server use ProxyPass you need get IP from x-forwarder-for, we need create
        // a switch change for ProxyPass to normal get client Id.
               //getServletRequestAttributes().getRemoteAddr();
        return getServletRequestAttributes().getHeader("X-FORWARDED-FOR");
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
}
