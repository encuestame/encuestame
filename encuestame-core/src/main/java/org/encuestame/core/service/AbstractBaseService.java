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

import org.encuestame.core.mail.MailServiceImpl;
import org.encuestame.core.service.util.MessageSourceFactoryBean;

/**
 * Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 22/05/2009 1:02:45
 * @version $Id$
 */
public abstract class AbstractBaseService extends AbstractConfigurationService {

    /**
     * {@link MessageSourceFactoryBean}.
     */
    private MessageSourceFactoryBean messageSource;

    /**
     *  {@link MailServiceImpl}.
     */
    private MailServiceImpl serviceMail;

    /**
     * Constructor.
     */
    public AbstractBaseService() {}

    /**
     * Getter.
     * @return {@link MessageSourceFactoryBean}
     */
    public MessageSourceFactoryBean getMessageSource() {
        return messageSource;
    }


    /**
     * Setter.
     * @param messageSource {@link MessageSourceFactoryBean}
     */
    public void setMessageSource(MessageSourceFactoryBean messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Getter by propertie Id.
     * @param propertieId propertieId
     * @return value of propertie
     */
    public String getMessageProperties(String propertieId) {
        return getMessageSource() == null ? propertieId : getMessageSource()
                .getMessage(propertieId, null, null);
    }

    /**
     * @return {@link MailServiceImpl}.
     */
    public MailServiceImpl getServiceMail() {
        return serviceMail;
    }
    /**
     * @param serviceMail {@link MailServiceImpl}.
     */
    public void setServiceMail(MailServiceImpl serviceMail) {
        this.serviceMail = serviceMail;
    }

}
