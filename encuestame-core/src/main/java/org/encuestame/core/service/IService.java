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

import org.encuestame.core.util.MessageSourceFactoryBean;

/**
 * Service Interface.
 * @author Picado, Juan juan@encuestame.org
 * @since 29/11/2009 21:47:14
 * @version $Id$
 */
public interface IService extends SocialOperations{

    /**
     * Getter.
     * @return {@link MessageSourceFactoryBean}
     */
    public MessageSourceFactoryBean getMessageSource();

    /**
     * Setter.
     * @param messageSource {@link MessageSourceFactoryBean}
     */
    public void setMessageSource(MessageSourceFactoryBean messageSource);

    /**
     * Getter by propertie Id.
     * @param propertieId propertieId
     * @return value of propertie
     */
    public String getMessageProperties(String propertieId);

}
