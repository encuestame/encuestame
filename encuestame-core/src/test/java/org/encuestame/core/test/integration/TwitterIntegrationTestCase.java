package org.encuestame.core.test.integration;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.integration.twitter.core.Tweet;
import org.springframework.integration.twitter.core.Twitter4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@Scope("singleton")
@ContextConfiguration(locations = {
         "classpath:TwitterSendUpdates-context.xml"
          })
@Ignore
public class TwitterIntegrationTestCase {

    @Autowired
    DirectChannel transformerInputChannel;

    @Autowired
    QueueChannel errorChannel;

    @Resource
    QueueChannel twitterTransformedChannel;

    @Autowired
    Twitter4jTemplate twitterTemplate;


    //@Test
    public void validateTemplate(){
        //System.out.println(this.twitterTemplate.search("nicaragua").getTweets().size());
        //System.out.println(this.twitterTemplate.getDirectMessages().size());
        for (Tweet tweet : this.twitterTemplate.search("nicaragua").getTweets()) {
            System.out.println(tweet.getFromUser());
            System.out.println(tweet.getText());
        }
    }

    @Test
    public void testTwitterChannel(){
        System.out.println("testTwitterChannel");
        MessageChannel twitterOutChannel = this.twitterTransformedChannel;
        Message<String> twitterUpdate = new GenericMessage<String>("Testing new Twitter " +
                "http://www.google.es samples for #springintegration "+RandomStringUtils.random(4));
        System.out.println("twitterOutChannel message "+twitterUpdate.getPayload());
        twitterOutChannel.send(twitterUpdate);
        System.out.println("twitterOutChannel");
    }

    @Test
    public void test(){
        System.out.println(this.errorChannel.getQueueSize());
    }
}
