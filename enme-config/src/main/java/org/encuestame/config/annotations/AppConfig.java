package org.encuestame.config.annotations;

import org.encuestame.business.gadgets.GadgetsLoader;
import org.encuestame.business.search.IndexFSDirectory;
import org.encuestame.business.setup.ApplicationStartup;
import org.encuestame.business.setup.install.DatabaseInstall;
import org.encuestame.mvc.interceptor.CheckInstallInterceptor;
import org.encuestame.mvc.interceptor.EnMeMobileInterceptor;
import org.encuestame.mvc.interceptor.EnMeSecurityInterceptor;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor;

@Configuration
@ComponentScan(basePackages = "org.encuestame")
//@ImportResource({"classpath:/config/files/service-context.xml"})
public class AppConfig {

	/**
	 * 
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
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
				new ClassPathResource("org/encuestame/business/gadgets/activity-stream.properties"),
				new ClassPathResource("org/encuestame/business/gadgets/comments-stream.properties"),
				new ClassPathResource("org/encuestame/business/gadgets/tweetpoll-stream.properties")
				};
		gadgetsLoader.setGadgets(gadgets);
		return gadgetsLoader;
	}
	
	/**
	 * Messages to support internationalization/localization.
	 */    
	@Bean(name="messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/WEB-INF/messages/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1);		
		return messageSource;
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
	public @Bean(name="checkInstallInterceptor") CheckInstallInterceptor checkInstallInterceptor(){
		return new CheckInstallInterceptor();
	}
	
	/**
	 * 
	 * @return
	 */
	public @Bean(name="enMeInterceptor") EnMeSecurityInterceptor enMeSecurityInterceptor(){
		return new EnMeSecurityInterceptor();
	}
	
	/**
	 * 
	 * @return
	 */
	public @Bean(name="mobileInterceptor") EnMeMobileInterceptor enMeMobileInterceptor(){
		return new EnMeMobileInterceptor();
	}

	/**
	 * 
	 * @return
	 */
	public @Bean(name="openSessionInViewInterceptor") OpenSessionInViewInterceptor openSessionInViewInterceptor(){
		final OpenSessionInViewInterceptor openSessionInViewInterceptor = new OpenSessionInViewInterceptor();
		openSessionInViewInterceptor.setSessionFactory(this.sessionFactory);
		openSessionInViewInterceptor.setSingleSession(true);
		return openSessionInViewInterceptor;
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
