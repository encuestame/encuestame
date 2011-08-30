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
package org.encuestame.mvc.test.widget;

import java.util.List;

import org.encuestame.core.security.util.WidgetUtil;
import org.encuestame.mvc.test.config.AbstractMvcUnitBeans;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test Widget Utils.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 05, 2011
 * @version $Id:$
 */
public class TestWidgetUtils extends AbstractMvcUnitBeans {

    /**
     * Test Get analytic google file.
     */
    @Test
    public void testGetAnalyticFile(){
        String file = WidgetUtil.getAnalytics("properties-test/test-analytics.inc");
        //System.out.println("file data --->"+file);
        Assert.assertNotNull(file);
    }

    /**
     * Test get black list ip.
     * @throws EnMeExpcetion
     */
    @Test
    public void testGetBlackListIp() throws EnMeExpcetion{
        final List<String> blackList = WidgetUtil.getBlackListIP("properties-test/test-blacklist.inc");
        //System.out.println("black list size --->"+blackList.size());
    }
}
