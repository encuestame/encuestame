package org.encuestame.config.annotations;

import org.encuestame.business.gadgets.GadgetsLoader;
import org.encuestame.business.search.IndexFSDirectory;
import org.encuestame.business.setup.ApplicationStartup;
import org.encuestame.business.setup.install.DatabaseInstall;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan(basePackages = "org.encuestame")
public class AppConfig {


	
	/**
	 * 
	 * @return
	 * @throws EnMeExpcetion
	 */
	@Scope("singleton")
	@Bean(name = "indexFSDirectory")
	public  IndexFSDirectory indexFSDirectory() {
		return new IndexFSDirectory();
	}
	
	@Scope("singleton")
	@Bean(name = "gadgetLoader")
	public  GadgetsLoader gadgetsLoader() {
		final GadgetsLoader gadgetsLoader = new GadgetsLoader();
		final Resource[] gadgets  = {
				new ClassPathResource("rg/encuestame/business/gadgets/activity-stream.properties"),
				new ClassPathResource("rg/encuestame/business/gadgets/comments-stream.properties"),
				new ClassPathResource("rg/encuestame/business/gadgets/tweetpoll-stream.properties")
				};
		gadgetsLoader.setGadgets(gadgets);
		return gadgetsLoader;
	}
		
	/**
	 * 
	 * @return
	 */
	@Scope("singleton")
	public @Bean(name="applicationStartup") ApplicationStartup applicationStartup(){
		return new ApplicationStartup();
	}
	
	/**
	 * 
	 * @return
	 */
	@Scope("singleton")
	public @Bean(name="install") DatabaseInstall install(){
		return new DatabaseInstall();
	}	
}
