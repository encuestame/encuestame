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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.service.IServiceManager;
import org.encuestame.core.service.ISurveyService;
import org.encuestame.core.service.ServiceManager;
import org.encuestame.core.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Base Controller.
 * @author Picado, Juan juan@encuestame.org
 * @since Mar 13, 2010 10:41:38 PM
 * @version $Id: $
 */
public class BaseController {

    protected Log log = LogFactory.getLog(this.getClass());

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
     * @param serviceManager
     *            the serviceManager to set
     */
    public void setServiceManager(IServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    /**
     * Get {@link SurveyService}.
     * @return survey service
     */
    public ISurveyService getSurveyService(){
        return getServiceManager().getApplicationServices().getSecurityService().getSurveyService();
    }
}
