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

import org.encuestame.persistence.dao.imp.HashTagDao;
import org.encuestame.persistence.domain.HashTag;
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
        this.hashTag = createHashTag("software");
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
        final List<HashTag> hashTagList = getHashTagDao().getListHashTagsByKeyword(keyword, 5);
        assertEquals("Should be equals", 2, hashTagList.size());
    }

}
