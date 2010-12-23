/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.rss;

import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.core.MessageSource;


/**
 * Rss Reader.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 2, 2010 10:03:26 AM
 * @version $Id:$
 */
public class EnMeRssReader implements MessageSource{

    protected Logger log = Logger.getLogger(this.getClass());

    public Message<String> receive() {
        log.debug("readRssFeed method is called");
        return MessageBuilder.withPayload("test").setHeader("feedid", "thefeed").build();
    }
}
