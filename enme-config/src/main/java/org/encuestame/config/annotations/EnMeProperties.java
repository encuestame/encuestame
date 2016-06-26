package org.encuestame.config.annotations;

import org.encuestame.config.startup.EnMePlaceHolderConfigurer;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnMeProperties extends PropertiesHolder{


	@Bean(name="propertyConfigurer")
	public static PropertyPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return createPropertyHolder(CUSTOM_PROPERTIES);
	}

}
