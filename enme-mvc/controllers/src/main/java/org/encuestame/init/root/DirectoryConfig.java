package org.encuestame.init.root;

import org.encuestame.business.service.MessageSourceFactoryBean;
import org.encuestame.business.setup.ApplicationStartup;
import org.encuestame.business.setup.install.DatabaseInstall;
import org.encuestame.core.service.IMessageSource;
import org.encuestame.core.util.EnMePlaceHolderConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "org.encuestame.core.service.startup")
@ImportResource({
    "classpath:/org/encuestame/config/xml/security-context.xml",
    "classpath:/org/encuestame/config/xml/security-oauth-context.xml",
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
        //http://www.tutorialspoint.com/javamail_api/javamail_api_smtp_servers.htm
        // List of properties, only these ones to connect with Gmail, it can be modified
        Properties properties = new Properties();
        if (EnMePlaceHolderConfigurer.getProperty("mail.protocol") != null) {
            properties.put("mail.transport.protocol", EnMePlaceHolderConfigurer.getProperty("mail.protocol"));
        }
        if (EnMePlaceHolderConfigurer.getProperty("mail.smtp.auth") != null) {
            properties.put("mail.smtp.auth", EnMePlaceHolderConfigurer.getProperty("mail.smtp.auth"));
        }
        if (EnMePlaceHolderConfigurer.getProperty("mail.smtp.starttls.enable") != null) {
            properties.put("mail.smtp.starttls.enable", EnMePlaceHolderConfigurer.getProperty("mail.smtp.starttls.enable"));
        }
        if (EnMePlaceHolderConfigurer.getProperty("mail.smtp.debug") != null) {
            properties.put("mail.debug", EnMePlaceHolderConfigurer.getProperty("mail.smtp.debug"));
        }
        mailSender.setJavaMailProperties(properties);
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

    /**
     *
      * @return
     */
   public @Bean(name="messageSourceFactoryBean") IMessageSource messageSourceFactoryBean(){
       return new MessageSourceFactoryBean(this.messageSource);
   }

}
