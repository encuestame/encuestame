package org.jp.core.security.spring;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.vote.RoleVoter;
import org.springframework.security.Authentication;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.vote.RoleVoter;

/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
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
 * Id: EnMeRoleVoter.java Date: 08/05/2009 13:17:14
 * 
 * @author juanpicado package: org.jp.core.security.spring
 * @version 1.0
 */
public class EnMeRoleVoter extends RoleVoter {

	public static final String ROLE_ALWAYS = "ENCUESTAME_ALWAYS";
	public static final String ROLE_ANONYMOUS = "ENCUESTAME_ANONYMOUS";
	private boolean anonymousAccessAllowed = false;
	private Log log = LogFactory.getLog(this.getClass());
	
	public EnMeRoleVoter() {
		log.info("ROLE VOTER PERSONALIZADO");
		
	}

	public void setAnonymousAccessAllowed(boolean i_anonymousAccessAllowed) {
		anonymousAccessAllowed = i_anonymousAccessAllowed;
	}

	@Override
	public int vote(Authentication authentication, Object object,
			ConfigAttributeDefinition config) {
		int result = ACCESS_ABSTAIN;

		Iterator iter = config.getConfigAttributes().iterator();
		GrantedAuthority[] authorities = authentication.getAuthorities();

		while (iter.hasNext()) {
			ConfigAttribute attribute = (ConfigAttribute) iter.next();
			log.info("ConfigAttribute->"+attribute.getAttribute());
			if (this.supports(attribute)) {
				// always grant access to resources, marked with ROLE_ALWAYS
				if (ROLE_ALWAYS.equals(attribute.getAttribute())) {
					return ACCESS_GRANTED;
				}
				result = ACCESS_DENIED;

				// Attempt to find a matching granted authority
				for (int i = 0; i < authorities.length; i++) {
					if (!anonymousAccessAllowed
							&& ROLE_ANONYMOUS.equals(authorities[i]
									.getAuthority())) {
						// skip checking for anonymous role
						continue;
					}

					if (attribute.getAttribute().equals(
							authorities[i].getAuthority())) {
						return ACCESS_GRANTED;
					}
				}
			}
		}

		return result;
	}

}
