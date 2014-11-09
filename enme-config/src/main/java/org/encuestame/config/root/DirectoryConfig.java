package org.encuestame.config.root;

import java.util.Properties;

import org.encuestame.business.setup.ApplicationStartup;
import org.encuestame.business.setup.install.DatabaseInstall;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.service.MessageSourceFactoryBean;
import org.encuestame.mvc.interceptor.CheckInstallInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor;

@Configuration
@ComponentScan(basePackages = "org.encuestame.core.service.startup")
@ImportResource({
    "classpath:/config/files/security-context.xml",
    "classpath:/config/files/security-oauth-context.xml",
    })
public class DirectoryConfig {

    /**
    *
    */
   @Autowired
   private MessageSource messageSource;

  /**
     * The Java Mail sender.
     * It's not generally expected for mail sending to work in embedded mode.
     * Since this mail sender is always invoked asynchronously, this won't cause problems for the developer.
     */
    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding(EnMePlaceHolderConfigurer.getProperty("mail.encoding"));
        mailSender.setHost(EnMePlaceHolderConfigurer.getProperty("mail.host"));
        mailSender.setPort(EnMePlaceHolderConfigurer.getIntegerProperty("mail.port"));
        mailSender.setUsername(EnMePlaceHolderConfigurer.getProperty("mail.username"));
        mailSender.setPassword(EnMePlaceHolderConfigurer.getProperty("mail.password"));
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", EnMePlaceHolderConfigurer.getProperty("mail.protocol"));
        properties.put("mail.smtp.auth", EnMePlaceHolderConfigurer.getProperty("mail.smtp.auth"));
        properties.put("mail.smtp.starttls.enable", EnMePlaceHolderConfigurer.getProperty("mail.smtp.starttls.enable"));
        properties.put("mail.debug", EnMePlaceHolderConfigurer.getProperty("mail.smtp.debug"));
        mailSender.setJavaMailProperties(properties);
//        System.out.println( EnMePlaceHolderConfigurer.getProperty("mail.protocol"));
//        System.out.println( EnMePlaceHolderConfigurer.getProperty("mail.smtp.auth"));
//        System.out.println( EnMePlaceHolderConfigurer.getProperty("mail.smtp.starttls.enable"));
//        System.out.println( EnMePlaceHolderConfigurer.getProperty("mail.smtp.debug"));
//        System.out.println( EnMePlaceHolderConfigurer.getProperty("mail.host"));
//        System.out.println( EnMePlaceHolderConfigurer.getProperty("mail.port"));
//        System.out.println( EnMePlaceHolderConfigurer.getProperty("mail.username"));
//        System.out.println( EnMePlaceHolderConfigurer.getProperty("mail.password"));
        return mailSender;
    }

   /**
    *
    * @return
    */
   public @Bean(name="templateMessage") SimpleMailMessage simpleMailMessage(){
       return new SimpleMailMessage();
   }



   /**
    *
    * @return
    */
   @Scope(value="singleton")
   public @Bean(name="install") DatabaseInstall install(){
       return new DatabaseInstall();
   }

   /**
    *
    * @return
    */
   public @Bean(name="applicationStartup") ApplicationStartup applicationStartup(){
       return new ApplicationStartup();
   }

   //@Lazy
   public @Bean(name="messageSourceFactoryBean") MessageSourceFactoryBean messageSourceFactoryBean(){
       return new MessageSourceFactoryBean(this.messageSource);
   }

}
