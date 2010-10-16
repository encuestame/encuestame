/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.web.test.config;

import java.util.HashMap;
import java.util.Map;

import org.encuestame.core.service.IServiceManager;
import org.encuestame.core.service.ServiceManager;
import org.encuestame.core.test.security.AbstractSpringSecurityContext;
import org.encuestame.core.test.service.config.AbstractBaseUnitBeans;
import org.encuestame.web.beans.admon.security.UserBean;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.SessionScope;

/**
 * Abstract Base Test Cases.
 * @author Diana, Paola Morales paola@encuestame.org
 * @since March 31, 2010
 * @version $Id: $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager" ,defaultRollback = true)
@Transactional
@org.springframework.context.annotation.Scope("session")
@ContextConfiguration(locations = {
        "classpath:encuestame-beans-jsf-context.xml"
})

public class AbstractBaseWeb extends AbstractSpringSecurityContext implements Scope{

    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;

    /** {@link ServiceManager}. **/
    @Autowired
    private IServiceManager serviceManager;

    private Map<String,Object> scopeMap = new HashMap<String,Object>();
    protected void onSetUp() throws Exception {

    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpSession session = new MockHttpSession();
    request.setSession(session);
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ((ConfigurableApplicationContext) applicationContext).getBeanFactory().registerScope( "session", new SessionScope());
    }

    /***/
    private UserBean userBean;

    /**
     *
     **/
    public final UserBean getUserBean() {
        return userBean;
    }

    /**
     *
     * @param userBean
     */
    public final void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }



    public Object get(String bean, ObjectFactory factory) {
        Object o = scopeMap.get(bean);
        if( o == null ){
            o = factory.getObject();
            scopeMap.put(bean, o);
        }
        return null;
    }

    public String getConversationId() {
        throw new AssertionError("not implimented");
    }

    public void registerDestructionCallback(String arg0, Runnable arg1) {
        return;
    }

    public Object remove(String bean) {
        return scopeMap.remove(bean);
    }


    public Object resolveContextualObject(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return the serviceManager
     */
    public IServiceManager getServiceManager() {
        return serviceManager;
    }

    /**
     * @param serviceManager the serviceManager to set
     */
    public void setServiceManager(IServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }
}
