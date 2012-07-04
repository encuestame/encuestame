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
package org.encuestame.core.test.integration;

import java.util.HashMap;

import junit.framework.Assert;

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.core.integration.gateway.TwitterGateway;
import org.encuestame.core.test.config.AbstractIntegrationConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.test.annotation.Repeat;

 
public class GatewayTestCase extends AbstractIntegrationConfig {


    @Autowired
    TwitterGateway twitterAdapter;

    @Autowired
    QueueChannel errorChannel;

    @Autowired
    DirectChannel twitterChannel;

    @Test
    @Repeat(2)
    public void testTransformation() {
        Assert.assertNotNull(this.twitterAdapter);


        this.twitterChannel.send(new Message<String>() {
            public MessageHeaders getHeaders() {
                return new MessageHeaders(new HashMap<String, Object>());
            }

            public String getPayload() {
                return "original www.xebia.com message http://www.xebia.com "+RandomStringUtils.randomAlphabetic(3);
            }
        });
        log.debug(errorChannel.getQueueSize());

        this.twitterAdapter.publishTweet("text http://blog.jotadeveloper.com "+RandomStringUtils.randomAlphabetic(3));
    }
}
