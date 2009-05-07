package org.jp.core.security.spring;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jp.core.exception.EnMeExpcetion;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.providers.ProviderManager;

/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame
 * Development Team
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Id: EnMeAuthenticationManager.java Date: 07/05/2009
 * 
 * @author juanpicado package: org.jp.core.security.spring
 * @version 1.0
 */
public class EnMeAuthenticationManager extends ProviderManager implements
		ApplicationContextAware {

	ApplicationContext applicationContext;
	protected String providerString;
	private static Logger log = Logger.getLogger(EnMeAuthenticationManager.class);

	public void setProviderString(String providerString) {
		this.providerString = providerString;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (providerString != null) {
			// convierte una candea en una lista de proveedores
			List<AuthenticationProvider> providers = new LinkedList<AuthenticationProvider>();
			String[] names = providerString.split(",");
			log.info("providers split string "+names);
			for (String providerUnit : names) {
				AuthenticationProvider provider = (AuthenticationProvider) applicationContext
						.getBean(providerUnit.trim());
				if (provider == null) {
					//si no existe el proveedor, lanza una excepción
					throw new EnMeExpcetion("AuthenticationProvider "
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
