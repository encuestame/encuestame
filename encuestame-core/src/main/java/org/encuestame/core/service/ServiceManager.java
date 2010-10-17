/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.service;

import org.encuestame.core.service.imp.IApplicationServices;
import org.encuestame.core.service.imp.IServiceManager;
import org.encuestame.core.service.util.MessageSourceFactoryBean;
import org.springframework.stereotype.Service;


/**
 * Service Manager.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since 26/04/2009
 * @version $Id$
 */
@Service
public class ServiceManager implements IServiceManager {

    /**
     * {@link ApplicationServices}.
     */
    private IApplicationServices applicationServices;

    private MessageSourceFactoryBean messageSource;

    /**
     * Setter of {@link ApplicationServices}.
     * @param applicationServices the applicationServices to set
     */
    public void setApplicationServices(final IApplicationServices applicationServices) {
        this.applicationServices = applicationServices;
    }

    /**
     * Getter of {@link ApplicationServices}
     * @return the applicationServices
     */
    public IApplicationServices getApplicationServices() {
        return applicationServices;
    }

    /**
     * @return the messageSource
     */
    public MessageSourceFactoryBean getMessageSource() {
        return messageSource;
    }

    /**
     * @param messageSource the messageSource to set
     */
    public void setMessageSource(MessageSourceFactoryBean messageSource) {
        this.messageSource = messageSource;
    }
}
