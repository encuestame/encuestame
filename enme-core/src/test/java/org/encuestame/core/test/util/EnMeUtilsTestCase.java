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
package org.encuestame.core.test.util;

import org.encuestame.core.util.EnMeUtils;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.categories.test.DefaultTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import junit.framework.TestCase;

/**
 * EnMe Utils Test Cases.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 15, 2011
 */
@Category(DefaultTest.class)
public class EnMeUtilsTestCase extends TestCase {

    /**
    * Test calculate size tag.
    */
    @Test
    public void testCalculateSizeTag() {
        final double calc1 = EnMeUtils.calculateSizeTag(40, 0, 0);
        assertEquals(calc1, 12D);
        final double calc2 = EnMeUtils.calculateSizeTag(3, 5, 1);
        assertEquals(calc2, 13D);
        final double calc3 = EnMeUtils.calculateSizeTag(40, 75, 15);
        assertEquals(calc3, 17D);
        final double calc4 = EnMeUtils.calculateSizeTag(75, 152, 75);
        assertEquals(calc4, 12D);
        final double calc5 = EnMeUtils.calculateSizeTag(140, 600000, 50);
        assertEquals(calc5, 18D);
        final double calc6 = EnMeUtils.calculateSizeTag(145001, 600000000, 5000);
        assertEquals(calc6, 29D);
        final double calc9 = EnMeUtils.calculateSizeTag(5003, 600000000, 5000);
        assertEquals(calc9, 14D);
        final double calc7 = EnMeUtils.calculateSizeTag(30, 85, 0);
        assertEquals(calc7, 17D);
        final double calc8 = EnMeUtils.calculateSizeTag(79, 85, 0);
        assertEquals(calc8, 18D);
     }

    /**
     * Test Calculate item relevance.
     */
    @Test
    public void testCalculateRelevance(){
        final long relevance = EnMeUtils.calculateRelevance(35, 5, 20,  60, 20, 120,150);
        assertEquals(relevance, 54);
    }

    @Test
    public void testCleanVersion() throws Exception{
        final String v1 = "1.3.4";
        final String v2 = "1.3.4";
        int[] d = EnMeUtils.cleanVersion(v1);
        assertEquals(d.length, 3);
        int[] d2 = EnMeUtils.cleanVersion(v2);
        assertEquals(d2.length, 3);
    }
    
    @Test(expected = EnMeExpcetion.class)
    public void testCleanVersionException() throws EnMeExpcetion{
        final String v1 = "1.3";
        EnMeUtils.cleanVersion(v1);;
    }
}
