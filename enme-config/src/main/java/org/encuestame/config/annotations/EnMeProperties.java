package org.encuestame.config.annotations;

import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@ImportResource({"classpath:/config/files/param-context.xml"})
public class EnMeProperties {

//	private static final Resource[] PROD_PROPERTIES = new ClassPathResource[] { new ClassPathResource(
//			"/org/encuestame/core/config/encuestame-config.properties"), };
//
//	private static final Resource[] CUSTOM_PROPERTIES = new ClassPathResource[] { new ClassPathResource(
//			"encuestame-config-custom.properties"), };
//
//	private static final Resource[] VERSION_PROPERTIES = new ClassPathResource[] { new ClassPathResource(
//			"/org/encuestame/core/config/version.properties"), };
//
//	@Bean(name="propertyConfigurer")
//	public static PropertyPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//		PropertyPlaceholderConfigurer pspc = new EnMePlaceHolderConfigurer();
//		pspc.setIgnoreResourceNotFound(true);
//		pspc.setLocations(PROD_PROPERTIES);
//		pspc.setLocations(CUSTOM_PROPERTIES);
//		pspc.setLocations(VERSION_PROPERTIES);
//		return pspc;
//	}

}
