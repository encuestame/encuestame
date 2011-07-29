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

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
/**
 * Message Source Factory Bean.
 * @author Picado, Juan juan@encuestame.org
 * @since 12/05/2009 9:17:43
 * @version $Id$
 */
@Service
public class MessageSourceFactoryBean implements MessageSource {

    /**
     *
     */
    @Autowired
    private MessageSource messageSource;

    /**
     *
     * @param messagesource
     */
    public void setMessagesource(final MessageSource messagesource) {
        this.messageSource = messagesource;
    }

    /**
     *
     */
    public String getMessage(MessageSourceResolvable resolvable, Locale locale)
            throws NoSuchMessageException {
        return messageSource.getMessage(resolvable, getDefaultLocale(locale));
    }

    /**
     *
     */
    public String getMessage(String code, Object[] args, Locale locale)
            throws NoSuchMessageException {
        return messageSource.getMessage(code, args, code,
                getDefaultLocale(locale));

    }

    /**
     *
     */
    public String getMessage(String code, Object[] args, String defaultMessage,
            Locale locale) {
        return messageSource.getMessage(code, args, defaultMessage,
                getDefaultLocale(locale));
    }

    /**
     * get default locate for faces context
     *
     * @param locale
     * @return
     */
    protected Locale getDefaultLocale(Locale locale) {
          return Locale.ENGLISH; //TODO: default locale?
    }
}
