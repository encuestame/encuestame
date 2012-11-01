package org.encuestame.business.setup;

import org.springframework.stereotype.Component;

@Component
public class InstallAdmonValidator {

    /*
     *
     * TODO: Spring Binding still use old spring version.
     * https://github.com/SpringSource/spring-webflow/blob/2.3.x/spring-binding/ivy.xml
     * This validation is disabled because make conflict with Spring Beans 3.1 version.
    public void validateinstallAdmon(AdministratorProfile administratorProfile, ValidationContext context){
        System.out.println("=============== VALIDATE 2=============");
        MessageContext messages = context.getMessageContext();
        if (!administratorProfile.getPassword().equals(administratorProfile.getConfirmPassword())) {
            messages.addMessage(new MessageBuilder().error().source("checkinDate").
                defaultText("Passwors should be equals.").build());
        }
        System.out.println("=============== VALIDATE 2=============");
    }*/
}
