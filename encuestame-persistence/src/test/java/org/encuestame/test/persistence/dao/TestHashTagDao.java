/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
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

import java.util.List;

import junit.framework.Assert;

import org.encuestame.persistence.dao.imp.HashTagDao;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.HashTagHits;
import org.encuestame.test.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link HashTagDao}..
 * @author Morales Urbina, Diana paola AT encuestame.org
 * @since January 06, 2011
 * @version $Id: $
 */
public class TestHashTagDao  extends AbstractBase{

    /** {@link HashTag} **/
    private HashTag hashTag;

    @Before
    public void initData(){
        this.hashTag = createHashTag("software",10L);
    }

    /**
     * Test Get HashTag by Name.
     */
    @Test
    public void testGetHashTagByName(){
        final HashTag ht = getHashTagDao().getHashTagByName(this.hashTag.getHashTag());
        assertEquals("Should be equals", this.hashTag.getHashTag(), ht.getHashTag());
    }

    /**
     * Test Get List HashTags by Keyword.
     */

    public void testGetListHashTagsByKeyword(){
        this.hashTag = createHashTag("software");
        final String keyword = "software";
        final List<HashTag> hashTagList = getHashTagDao().getListHashTagsByKeyword(keyword, 5, null);
        assertEquals("Should be equals", 2, hashTagList.size());
    }

    /**
     * Get hash tags test.
     */
    @Test
    public void testGetHashTags(){
        final HashTag hashTag1 = createHashTag("America", 2000L);
        final HashTag hashTag2 = createHashTag("Amazonas", 900L);
        final HashTag hashTag3 = createHashTag("Carazo",  50L);
        final HashTag hashTag4 = createHashTag("Nicaragua", 100L);
        final HashTag hashTag5 = createHashTag("Paraguay", 0L);

        final int limit = 20;
        final int start = 0;
        final List<HashTag> results = getHashTagDao().getHashTags(limit, start, "");
        Assert.assertNotNull(results);
        assertEquals("Should be equals", 6, results.size());

        final HashTag expHas1 = results.get(0);
        final HashTag expHas2 = results.get(1);
        final HashTag expHas3 = results.get(2);
        final HashTag expHas4 = results.get(3);

        assertEquals("Should be equals", hashTag1.getHashTag(), expHas1.getHashTag());
        assertEquals("Should be equals", hashTag2.getHashTag(), expHas2.getHashTag());
        assertEquals("Should be equals", hashTag4.getHashTag(), expHas3.getHashTag());
        assertEquals("Should be equals", hashTag3.getHashTag(), expHas4.getHashTag());
    }

    /**
     * Test get max-min tag frecuency.
     */
    @Test
    public void testGetMaxMinTagFrecuency(){
        createHashTag("America", 20L);
        createHashTag("Amazonas", 90L);
        createHashTag("Carazo",  50L);
        final List<Object[]>  frecuency = getHashTagDao().getMaxMinTagFrecuency();
        //System.out.println("MAX 1-------->"+ frecuency.get(0)[0]);
        //System.out.println(" MIN 1-------->"+ frecuency.get(0)[1]);
         for (Object[] objects : frecuency) {
           // System.out.println("---- MAX ----****"+ objects[0]);
           // System.out.println("---- MIN ----****"+ objects[1]);
        }
    }

}
