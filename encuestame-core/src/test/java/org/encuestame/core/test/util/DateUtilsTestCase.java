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
package org.encuestame.core.test.util;

import java.util.Calendar;
import java.util.HashMap;

import junit.framework.TestCase;

import org.encuestame.utils.DateUtil;
import org.encuestame.utils.RelativeTimeEnum;
import org.junit.Test;

/**
 * Date Utils Test Cases.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 14, 2010 11:29:20 PM
 * @version Id:
 */
public class DateUtilsTestCase extends TestCase{

    /**
     * Test Period Date.
     */
    @Test
    public void testGetRelativeDate(){
        final Calendar calendar = Calendar.getInstance();
        //System.out.println(calendar.getTime());
        calendar.set(Calendar.SECOND, -5);
        //System.out.println(calendar.getTime());
        final HashMap<Integer, RelativeTimeEnum> hm = DateUtil.getRelativeTime(calendar.getTime());
        //System.out.println(hm);
        //assertEquals(hm.get(0).name().toString(), new String("5"));
        final Calendar calendar2 = Calendar.getInstance();
        //System.out.println(calendar2.getTime());
        calendar2.set(Calendar.DAY_OF_MONTH, -5);
        //System.out.println(calendar2.getTime());
        final HashMap<Integer, RelativeTimeEnum> hm1 = DateUtil.getRelativeTime(calendar2.getTime());
        //System.out.println(hm1);
    }
}
