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
package org.encuestame.core.integration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

/**
 * Mail message service.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
public class MailMessageService {
    public Message<?> processMessage(String bites) {
        MessageBuilder<?> messageBuilder = MessageBuilder.withPayload(bites);
        Pattern p = Pattern
                .compile("(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        Matcher matcher = p.matcher(bites);
        if (matcher.find()) {
            String url = matcher.group(0);
            messageBuilder = MessageBuilder.withPayload(url);
            messageBuilder.setHeader("MESSAGE_TYPE", "URL");
        } else {
            messageBuilder = MessageBuilder.withPayload(bites);
            messageBuilder.setHeader("MESSAGE_TYPE", "TEXT");
        }
        return messageBuilder.build();

    }
}