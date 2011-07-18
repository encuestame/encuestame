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

    @Test
    public void testCalculateSizeTag(){
        long f = 32;
        long mi = 10;
        long ma = 85;
        float frecuency = Float.valueOf(f);
        float max = Float.valueOf(ma);
        float min = Float.valueOf(mi);
        double fijo = Double.valueOf(12);

        final float frecDiff = max - min;
        //System.out.println("FRECUENCY Relevance ------> "+ frecuency);
        //System.out.println("MIN FRECUENCY ------> "+ max);
        //System.out.println("MAX FRECUENCY ------> "+ min);
        //System.out.println("DIFFERENCE FRECUENCY ------> "+ frecDiff);
        double perRelative = ((frecuency-min)/frecDiff);
        final double perLog = (Math.log(perRelative)/Math.log(2))+fijo;
     }

}
