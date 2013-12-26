package org.encuestame.config.annotations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@Configuration
@ImportResource({"classpath:org/encuestame/config/files/web-flow-context.xml"})
public class EnMeWebflow {

}
