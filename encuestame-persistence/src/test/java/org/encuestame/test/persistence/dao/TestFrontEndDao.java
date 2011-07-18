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
package org.encuestame.test.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.encuestame.persistence.dao.imp.FrontEndDao;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.HashTagHits;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.test.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;
/**
 * Test {@link FrontEndDao}..
 * @author Morales Urbina, Diana paola AT encuestame.org
 * @since January 06, 2011
 * @version $Id: $
 */
public class TestFrontEndDao extends AbstractBase {

    /** {@link HashTag} **/
    private HashTag hashTag;

    /** {@link HashTagHits} **/
    private HashTagHits hashTagHit;

    /** {@link UserAccount}. **/
    private UserAccount secondary;

    final String ipAddress = "192.168.1.1";

    @Before
    public void initData(){
        this.secondary = createUserAccount("paola", createAccount());
        this.hashTag = createHashTag("software");
        this.hashTagHit = createHashTagHit(hashTag, ipAddress, this.secondary);
      /*  System.out.println("hashTag ID --->"+ hashTag.getHashTagId());
        System.out.println("hashTagHit IP --->"+ hashTagHit.getIpAddress());
        System.out.println("hashTagHit ID --->"+ hashTagHit.getHitId());
        System.out.println("hashTagHit tag ID--->"+ hashTagHit.getHashTagId().getHashTagId());*/
    }

    /** Test Get hash tags by ip.**/
    @Test
    public void testGetHashTagsHitByIp(){
        assertNotNull(this.hashTagHit);
        flushIndexes();
        final List<HashTagHits> hitsbyIp = getFrontEndDao().getHashTagsHitByIp(this.ipAddress);
       // System.out.print("SIZE HASHTAG hit---> "+ hitsbyIp.size());
        assertNotNull(hitsbyIp);
        assertEquals("Should be equals", hitsbyIp.get(0).getIpAddress(), this.ipAddress);
        assertEquals("Should be equals", hitsbyIp.size(),1);
    }
}
