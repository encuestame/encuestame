package org.encuestame.config.annotations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({
	"classpath:/config/files/security-context.xml", 
	"classpath:/config/files/security-oauth-context.xml"})
public class EnMeSecurity {

}
