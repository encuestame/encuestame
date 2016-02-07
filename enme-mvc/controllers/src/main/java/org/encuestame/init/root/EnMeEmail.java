package org.encuestame.init.root;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:/org/encuestame/config/xml/email-context.xml"})
public class EnMeEmail {


}
