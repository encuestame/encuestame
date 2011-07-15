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
package org.encuestame.test.business.service;

import org.encuestame.business.service.FrontEndService;
import org.encuestame.business.service.imp.IFrontEndService;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.HashTagHits;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.test.business.service.config.AbstractServiceBase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test for {@link FrontEndService}.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 12, 2011
 * @version $Id:$
 */
public class TestFrontEndService extends AbstractServiceBase{

    @Autowired
    private IFrontEndService frontEndService;

    /** {@link HashTag} **/
    private HashTag hashTag;

    /** {@link HashTagHits} **/
    private HashTagHits hashTagHit;

    /** {@link UserAccount}. **/
    private UserAccount secondary;

    /** ip address. **/
    final String ipAddress = "192.168.1.1";

    final String ipAddress2 = "192.168.1.2";

    @Before
    public void initData(){
        this.secondary = createUserAccount("paola", createAccount());
        this.hashTag = createHashTag("software");
        this.hashTagHit = createHashTagHit(hashTag, this.ipAddress, this.secondary);
        System.out.println("hashTag ID --->"+ hashTag.getHashTagId());
    }

    @Test
    public void testCheckPreviousHashTagHit(){
        flushIndexes();
        final Boolean previousRecord = getFrontEndService().checkPreviousHashTagHit(this.ipAddress);
        System.out.println("Previous record exists? --> "+ previousRecord + "IP" + this.ipAddress);
        final Boolean previousRecord2 = getFrontEndService().checkPreviousHashTagHit(this.ipAddress2);
        System.out.println("Previous record exists 2? --> "+ previousRecord2 + "IP" + this.ipAddress2);
    }

    @Test
    public void testRegisterHashTagHit(){
        System.out.println(" previous tag hit --> "+ this.hashTag.getHits());
        final Boolean registerHit = getFrontEndService().registerHashTagHit(this.hashTag.getHashTag(), this.ipAddress,
                                    this.secondary.getUsername());
        System.out.println(" Se agrego registro nuevo? --> "+ registerHit);
        System.out.println(" tag hit anterior 2--> "+ this.hashTag.getHits());
        getFrontEndService().registerHashTagHit(this.hashTag.getHashTag(), this.ipAddress2,
                             this.secondary.getUsername());
        System.out.println(" tag hit posterior --> "+ this.hashTag.getHits());
    }

    /**
    * @return the frontEndService
    */
    public IFrontEndService getFrontEndService() {
        return frontEndService;
    }

    /**
    * @param frontEndService the frontEndService to set
    */
    public void setFrontEndService(IFrontEndService frontEndService) {
        this.frontEndService = frontEndService;
    }
}
