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
package org.encuestame.core.service;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class MessageSourceFactoryBean  implements MessageSource{

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
     */
    public MessageSourceFactoryBean() {}

    /**
     *
     * @param messageSource
     */
    public MessageSourceFactoryBean(MessageSource messageSource) {
        super();
        this.messageSource = messageSource;
    }


    /**
     *
     */
    public String getMessage(final MessageSourceResolvable resolvable,
            final Locale locale)
            throws NoSuchMessageException {
        //log.debug("Message Source Factory Bean 1");
        return messageSource.getMessage(resolvable, getDefaultLocale(locale));
    }

    /**
     *
     */
    public String getMessage(
            final String code,
            final Object[] args,
            final Locale locale)
            throws NoSuchMessageException {
        //log.debug("Message Source Factory Bean 2");
        return messageSource.getMessage(
                code,
                args,
                code,
                locale);

    }

    /**
     *
     */
    public String getMessage(String code, Object[] args, String defaultMessage,
            Locale locale) {
        //log.debug("Message Source Factory Bean 3");
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
        final Locale locales = locale ==  null  ? Locale.ENGLISH : locale;
        //log.debug("Message Source Factory Bean :::==> " + locales);
        return locales;
    }

    /**
     *
     * @return
     */
    public MessageSource getMessageSource() {
        return messageSource;
    }

    /**
     *
     * @param messageSource
     */
    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
