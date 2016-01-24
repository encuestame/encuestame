package org.encuestame.core.service;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

/**
 * Created by jpicado on 24/01/16.
 */
public interface IMessageSource extends MessageSource {

    /**
     *
     */
    String getMessage(final MessageSourceResolvable resolvable,
                             final Locale locale)
            throws NoSuchMessageException;

    /**
     *
     */
    String getMessage(
            final String code,
            final Object[] args,
            final Locale locale)
            throws NoSuchMessageException;

    /**
     *
     */
    String getMessage(String code, Object[] args, String defaultMessage,
                             Locale locale);

    /**
     * get default locate for faces context
     *
     * @param locale
     * @return
     */
    Locale getDefaultLocale(Locale locale);
}
