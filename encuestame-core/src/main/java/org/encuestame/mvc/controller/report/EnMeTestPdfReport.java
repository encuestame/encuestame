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
package org.encuestame.mvc.controller.report;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.collections.ListUtils;
import org.encuestame.mvc.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Test PDF Report.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 1, 2010 7:58:42 PM
 * @version $Id:$
 */
@Controller
public class EnMeTestPdfReport extends BaseController{

     @RequestMapping(value ="/report.pdf", method = RequestMethod.GET)
     public String report(ModelMap modelMap) {
          JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(ListUtils.EMPTY_LIST,false);
          modelMap.put("encuestameTestReportList", jrDataSource);
          return "encuestameTestReportList";
     }

}
