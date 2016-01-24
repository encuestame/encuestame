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
package org.encuestame.business.service;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.service.IMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
/**
 * Message Source Factory Bean.
 * @author Picado, Juan juanATencuestame.org
 * @since 12/05/2009 9:17:43
 */
@Service
public class MessageSourceFactoryBean  implements IMessageSource {

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     *
     */
    private MessageSource messageSource;

    /**
     *
     * @param messageSource
     */
    public MessageSourceFactoryBean(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    /**
     *
     */
    @Override
    public String getMessage(final MessageSourceResolvable resolvable,
            final Locale locale)
            throws NoSuchMessageException {
        return messageSource.getMessage(resolvable, getDefaultLocale(locale));
    }

    /**
     *
     */
    @Override
    public String getMessage(
            final String code,
            final Object[] args,
            final Locale locale)
            throws NoSuchMessageException {
        return messageSource.getMessage(
                code,
                args,
                code,
                locale);

    }

    /**
     *
     */
    @Override
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
    @Override
    public Locale getDefaultLocale(Locale locale) {
        final Locale locales = locale ==  null  ? Locale.ENGLISH : locale;
        log.trace("Message Source Factory Bean :::==> " + locales);
        return locales;
    }

}
