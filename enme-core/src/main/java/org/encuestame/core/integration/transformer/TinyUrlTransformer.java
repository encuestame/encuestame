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
package org.encuestame.core.integration.transformer;

import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.UrlValidator;
import org.encuestame.core.util.SocialUtils;

/**
 * TinyUrlTransformer
 * I'm a tiny url transformer.
 * I'll convert the url in the message to tiny url.
 * @author Picado, Juan juanATencuestame.org
 * @since
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
