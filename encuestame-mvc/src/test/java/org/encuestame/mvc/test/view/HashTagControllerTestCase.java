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
package org.encuestame.mvc.test.view;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;
import junit.framework.Assert;

import org.encuestame.mvc.controller.HashTagController;
import org.encuestame.mvc.controller.json.MethodJson;
import org.encuestame.mvc.test.config.AbstractMvcUnitBeans;
import org.encuestame.persistence.domain.HashTag;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *  HashTag Controller TestCase.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 13, 2011
 */
public class HashTagControllerTestCase extends AbstractMvcUnitBeans{

    @Autowired
    private HashTagController hashTagController;

    @Before
     public void initMVc() {

     }

    @Test
    public void test(){
        Assert.assertEquals(true, true);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testHashTagController() throws Exception {
        final HashTag hashTag = createHashTag("software", 50L);
        final String ipAddress = "192.168.1.99";
        createHashTagHit(hashTag, ipAddress);
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/tag/software");
        final ModelAndView mav = handlerAdapter.handle(request, response,
                hashTagController);
        assertViewName(mav, "tag/detail");
        System.out.println("Test hash Tag Controller");

    }
}
