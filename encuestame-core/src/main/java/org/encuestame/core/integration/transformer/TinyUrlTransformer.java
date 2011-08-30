package org.encuestame.core.integration.transformer;

import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.UrlValidator;
import org.encuestame.core.util.SocialUtils;

/**
 * I'm a tiny url transformer. I'll convert the url in the message to tiny url.
 *
 */
public class TinyUrlTransformer {

    public Log log = LogFactory.getLog(this.getClass());

    public String transform(String message) {
        log.debug("transform 1 "+message);
        StringBuilder tranformedMessage = new StringBuilder("");
        StringTokenizer tokenizer = new StringTokenizer(message, " ");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            log.debug("token "+token);
            if (validUrl(token)) {
                token = SocialUtils.getTinyUrl(token);
            }
            tranformedMessage.append(" ").append(token);
        }
        log.debug("transform 2 "+tranformedMessage);
        return tranformedMessage.toString();
    }

    private boolean validUrl(String token) {
        UrlValidator urlValidator = new UrlValidator();
        return urlValidator.isValid(token);
    }

}
