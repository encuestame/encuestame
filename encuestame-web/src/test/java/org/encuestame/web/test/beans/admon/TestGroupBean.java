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
package org.encuestame.web.test.beans.admon;

import org.encuestame.web.beans.admon.security.GroupBean;
import org.encuestame.web.beans.admon.security.UserBean;
import org.encuestame.web.test.config.AbstractBaseWeb;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Description Class.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 16, 2010 7:18:12 PM
 * @version Id:
 */
public class TestGroupBean extends AbstractBaseWeb {

    /** {@link GroupBean}. **/
    private GroupBean groupBean = new GroupBean();;

    /**
     * Init.
     */
    @Before
    public void init(){
        getGroupBean().setServicemanagerBean(getServiceManager());
    }

    @Test
    public void test(){
        Assert.assertTrue(true);
    }


    /**
     * @return the groupBean
     */
    public GroupBean getGroupBean() {
        return groupBean;
    }

    /**
     * @param groupBean the groupBean to set
     */
    public void setGroupBean(GroupBean groupBean) {
        this.groupBean = groupBean;
    }
}
