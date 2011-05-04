package org.encuestame.core.security;

import org.springframework.security.core.authority.GrantedAuthorityImpl;

@Deprecated
public class EnMeAutorithy  extends GrantedAuthorityImpl {

    public EnMeAutorithy(String role) {
        super(role);
    }

}
