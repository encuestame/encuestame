package org.encuestame.business.setup;

import org.encuestame.core.config.AdministratorProfile;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.stereotype.Component;

@Component
public class InstallAdmonValidator {

    public void validateinstallAdmon(AdministratorProfile administratorProfile, ValidationContext context){
        System.out.println("=============== VALIDATE 2=============");
        MessageContext messages = context.getMessageContext();
        if (!administratorProfile.getPassword().equals(administratorProfile.getConfirmPassword())) {
            messages.addMessage(new MessageBuilder().error().source("checkinDate").
                defaultText("Passwors should be equals.").build());
        }
        System.out.println("=============== VALIDATE 2=============");
    }
}
