/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.mvc.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Description.
 * @author Picado, Juan juanATencuestame.org
 * @since 22/08/2011
 */
public class EmbebedJSView extends AbstractEnMeCustomView{

    public EmbebedJSView() {
        super();
        setContentType("application/x-javascript");

    }

    @Override
    void renderView(Map<String, ?> map, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub

    }


}
