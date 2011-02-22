
package org.encuestame.test.business.social;

import org.encuestame.business.service.AbstractBaseService;
import org.encuestame.business.service.imp.ILinkedInService;
import org.encuestame.business.service.social.connect.TwitterSocialService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;


public class SocialServiceTestCase extends AbstractBaseService{

    /**
     * Twitter Social Service.
     */
    @Autowired
    private TwitterSocialService twitterSocialService;

    /**
     *
     */
    @Autowired
    private ILinkedInService linkedInService;


    /**
     * Test.
     */
    @Test
    public void twitterSocialServiceTest(){
        Assert.isTrue(true);
        System.out.println(this.linkedInService.getAuthorizeLinkedInUrl());
    }


    /**
     * @return the twitterSocialService
     */
    public TwitterSocialService getTwitterSocialService() {
        return twitterSocialService;
    }

    /**
     * @param twitterSocialService the twitterSocialService to set
     */
    public void setTwitterSocialService(TwitterSocialService twitterSocialService) {
        this.twitterSocialService = twitterSocialService;
    }


    /**
     * @param linkedInService the linkedInService to set
     */
    public void setLinkedInService(ILinkedInService linkedInService) {
        this.linkedInService = linkedInService;
    }
}
