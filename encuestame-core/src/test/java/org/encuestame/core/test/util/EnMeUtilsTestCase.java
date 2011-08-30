/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
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
import org.junit.Test;

import junit.framework.TestCase;

/**
 * EnMe Utils Test Cases.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 15, 2011
 * @version Id:
 */
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
}
