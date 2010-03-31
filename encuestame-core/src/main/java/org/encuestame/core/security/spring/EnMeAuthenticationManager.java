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
package org.encuestame.core.security.spring;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.encuestame.core.exception.EnMeExpcetion;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

/**
 * Encuestame Authentication Manager extended on {@link ProviderManager}.
 * @author Picado, Juan juan@encuestame.org
 * @since  07/05/2009
 * @version $Id$
 */
public class EnMeAuthenticationManager extends ProviderManager implements
        ApplicationContextAware {

    ApplicationContext applicationContext;
    protected String providerString;
    private static Logger log = Logger.getLogger(EnMeAuthenticationManager.class);

    /**
     * @param providerString provider string.
     */
    public void setProviderString(final String providerString) {
        this.providerString = providerString;
    }

    @Override
    public void afterPropertiesSet() throws Exception{
        if (providerString != null) {
            // convierte una candea en una lista de proveedores
            final List<AuthenticationProvider> providers = new LinkedList<AuthenticationProvider>();
            final String[] names = providerString.split(",");
            log.info("providers split string "+names);
            for (String providerUnit : names) {
                final AuthenticationProvider provider = (AuthenticationProvider) applicationContext
                        .getBean(providerUnit.trim());
                if (provider == null) {
                    //si no existe el proveedor, lanza una excepci�n
                    throw new Exception("AuthenticationProvider "
                            + providerUnit + " don't exist");
                }
                log.info("providers add "+provider);
                providers.add(provider);
            }
            setProviders(providers);
        }
        super.afterPropertiesSet();
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;

    }

}
