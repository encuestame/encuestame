package org.encuestame.config.annotations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:org/encuestame/config/files/email-context.xml"})
public class EnMeEmail {

}
