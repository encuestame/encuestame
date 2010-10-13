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
package org.encuestame.mvc.test.json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import junit.framework.Assert;

import org.encuestame.mvc.controller.json.notifications.NotificationsJsonController;
import org.encuestame.mvc.test.config.AbstractMvcUnitBeans;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

/**
 * Test Json Service.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 26, 2010 8:19:49 PM
 * @version $Id:$
 */
public class TestJsonService extends AbstractMvcUnitBeans{

    private NotificationsJsonController notificationsJsonController;


    /**
     * Test Json.
     * @throws Exception
     */
    @Test
    public void testJson() throws Exception{
         Assert.assertNotNull(this.notificationsJsonController);
         Assert.assertNotNull(getSecondary());
         request.setMethod("GET");
         request.setRequestURI("/notifications.json");
         request.setParameter("limit", "10");
         final ModelAndView mav = handlerAdapter.handle(request, response, this.notificationsJsonController);
         mav.setView(this.jacksonJsonView);
         log.debug("TEST JSON 1 " + mav);
         log.debug("TEST JSON 2 " + mav.getModel().get("success").toString());
         log.debug("TEST JSON 2 " + response);
         File f;
         f = new File("myfile.json");
         if(!f.exists()){
             f.createNewFile();
         }
         BufferedWriter out = new BufferedWriter(new FileWriter(f));
         out.write(mav.getModel().get("success").toString());
         out.close();

//         String input = "{\"id\":1,\"username\":\"admin\",\"enabled\":true,\"location\":null,\"bday\":315529200000}";
//         log.debug("json string "+ input);
//         log.debug("json string 2"+ mav.getModel().toString());
//
//         JsonFactory fs = new JsonFactory();
//         fs.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES,false);
//         fs.enable(Feature.ALLOW_UNQUOTED_FIELD_NAMES);
//         JsonParser jp = fs.createJsonParser(mav.getModel().toString());
//
//         jp.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
//         log.debug("jsonparser get text "+ jp.getText());
//         jp.nextToken(); //START_OBJECT
//
//
//         jp.close();
//
//         assertViewName(mav, null);
//         JsonFactory jsonFactory = new JsonFactory(); // or, for data binding, org.codehaus.jackson.mapper.MappingJsonFactory
//         jsonFactory.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES,false);
//         JsonGenerator jg = jsonFactory.createJsonGenerator(f, JsonEncoding.UTF8); // or Stream, Reader
//
//         ObjectMapper mapper = new ObjectMapper();
//         mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
//         JsonNode rootNode = mapper.readValue(mav.getModel().toString(), JsonNode.class);
//         //boolean enabled = rootNode.path("error").getBooleanValue();
////         log.debug(enabled);
//
//  //       String username = rootNode.path("sucess").getTextValue();
//   //      log.debug(username);
//
//         //JsonFactory f = new JsonFactory();
//         //JsonParser parser = f.
    }

    /**
     * @return the notificationsJsonController
     */
    public NotificationsJsonController getNotificationsJsonController() {
        return notificationsJsonController;
    }

    /**
     * @param notificationsJsonController the notificationsJsonController to set
     */
    @Autowired
    public void setNotificationsJsonController(
            NotificationsJsonController notificationsJsonController) {
        this.notificationsJsonController = notificationsJsonController;
    }
}
