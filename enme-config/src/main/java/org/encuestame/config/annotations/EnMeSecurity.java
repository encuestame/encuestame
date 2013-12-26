package org.encuestame.config.annotations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({
	"classpath:org/encuestame/config/files/security-context.xml", 
	"classpath:org/encuestame/config/files/security-oauth-context.xml"})
public class EnMeSecurity {

}
