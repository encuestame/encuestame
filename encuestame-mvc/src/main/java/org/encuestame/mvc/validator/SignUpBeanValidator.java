/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.mvc.validator;

import org.apache.log4j.Logger;
import org.encuestame.business.service.SecurityService;
import org.encuestame.business.service.imp.SecurityOperations;
import org.encuestame.utils.security.SignUpBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.stereotype.Component;

/**
 * Validator for SignUp.
 * @author Picado, Juan juanATencuestame.org
 * @since Jan 22, 2011 9:09:11 AM
 * @version $Id:$
 */
@Component
public class SignUpBeanValidator{

    /**
     * Log.
     */
    protected Logger log = Logger.getLogger(this.getClass());

    /**
     * Reference to {@link SecurityService}.
     */
    @Autowired
    private SecurityOperations securityService;

    /**
     * Validate SignUn Bean.
     * @param booking
     * @param context
     */
    public void validateSignup(SignUpBean booking, ValidationContext context) {
        log.debug("Validate Sign Up");
        final ValidateOperations validation = new ValidateOperations(securityService);
        MessageContext messages = context.getMessageContext();
        if(validation.validateUserEmail(booking.getEmail(), null) != null){
            log.warn("Email NOT VALID");
            //result.rejectValue("email", "secure.email.notvalid"); //secure.email.notvalid
            messages.addMessage(new MessageBuilder().error().source("email").
                    defaultText("Email NOT VALID").build());
        }
        if(!validation.validateUsername(booking.getUsername(), null)){
            log.warn("Username NOT VALID");
             //result.rejectValue("username", "secure.user.notvalid"); //secure.user.notvalid
            messages.addMessage(new MessageBuilder().error().source("username").
                    defaultText("Username NOT VALID").build());
        }
    }

    /**
     * @return the securityService
     */
    public SecurityOperations getSecurityService() {
        return securityService;
    }

    /**
     * @param securityService the securityService to set
     */
    public void setSecurityService(final SecurityOperations securityService) {
        this.securityService = securityService;
    }
}
