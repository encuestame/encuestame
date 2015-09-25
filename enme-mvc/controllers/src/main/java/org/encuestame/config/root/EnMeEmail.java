package org.encuestame.config.root;

import java.util.Properties;

import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ImportResource({"classpath:/org/encuestame/config/xml/email-context.xml"})
public class EnMeEmail {


}
