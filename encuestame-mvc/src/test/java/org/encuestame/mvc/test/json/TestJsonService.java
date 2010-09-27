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

import junit.framework.Assert;

import org.encuestame.mvc.controller.json.notifications.NotificationsJsonController;
import org.encuestame.mvc.test.config.AbstractMvcUnitBeans;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test Json Service.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 26, 2010 8:19:49 PM
 * @version $Id:$
 */
public class TestJsonService extends AbstractMvcUnitBeans{

    @Autowired
    private NotificationsJsonController notificationsJsonController;

    @Test
    public void testJson(){
         Assert.assertTrue(true);
         Assert.assertNotNull(this.notificationsJsonController);
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
    public void setNotificationsJsonController(
            NotificationsJsonController notificationsJsonController) {
        this.notificationsJsonController = notificationsJsonController;
    }
}
