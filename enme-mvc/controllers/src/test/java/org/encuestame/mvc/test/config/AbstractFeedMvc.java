/*
 *
 *  * Copyright 2014 encuestame
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *
 */

package org.encuestame.mvc.test.config;

import org.encuestame.mvc.controller.syndication.SyndicationController;
import org.encuestame.utils.enums.MethodJson;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * Created by jpicado on 05/12/14.
 */
public abstract class AbstractFeedMvc extends AbstractMvcUnitBeans{

    /**
     *
     */
    @Autowired
    public SyndicationController syndicationController;

    /**
     *
     * @param url
     * @param view
     * @throws Exception
     */
    public void getRss(
            String url,
            String view
    ) throws Exception {
        request = new MockHttpServletRequest(MethodJson.GET.toString(), url);
        final ModelAndView mav = handlerAdapter.handle(request, response, syndicationController);
        Assert.assertNotNull(mav.getModelMap().get("items"));
        Assert.assertEquals(mav.getViewName(),view);
    }

    /**
     *
     * @param type
     * @param mp
     * @return
     * @throws Exception
     */
    public String getRss(
            String path,
            String username,
            HashMap<String, String> mp) throws Exception{
        path = path.replace("{username}", username);
        logPrint(path);
        request = new MockHttpServletRequest(MethodJson.GET.toString(), path);
//        Iterator it = mp.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pairs = (Map.Entry)it.next();
//            it.remove();
//            request.setParameter(pairs.getKey().toString(), pairs.getValue().toString());
//        }
        final ModelAndView mav = handlerAdapter.handle(request, response, syndicationController);
        logPrint(response.getContentAsString());
        logPrint(mav.getModelMap());
        logPrint(mav);
        return mav.getViewName();
    }
}
