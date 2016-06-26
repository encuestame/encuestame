package org.encuestame.config.annotations;

import org.encuestame.config.startup.EnMePlaceHolderConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class EnMeDevProperties extends PropertiesHolder {


	@Bean(name="propertyConfigurer")
	public static PropertyPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return createPropertyHolder(TEST_PROPERTIES);
	}
}
