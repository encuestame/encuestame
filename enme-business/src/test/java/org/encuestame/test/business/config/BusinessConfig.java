package org.encuestame.test.business.config;

import com.google.common.collect.Lists;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.encuestame.business.cron.RemoveUnconfirmedAccountJob;
import org.encuestame.business.setup.ApplicationStartup;
import org.encuestame.business.setup.install.DatabaseInstall;
import org.encuestame.business.setup.install.InstallDatabaseOperations;
import org.encuestame.core.cron.CalculateHashTagSize;
import org.encuestame.core.cron.CalculateRelevance;
import org.encuestame.core.cron.IndexRebuilder;
import org.encuestame.core.cron.ReIndexJob;
import org.encuestame.core.service.IMessageSource;
import org.encuestame.business.service.MessageSourceFactoryBean;
import org.encuestame.core.util.EnMePlaceHolderConfigurer;
import org.encuestame.persistence.dao.jdbc.InstallerDao;
import org.encuestame.persistence.dao.jdbc.InstallerOperations;
import org.hibernate.boot.model.relational.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactory;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by jpicado on 09/01/16.
 */
@Configuration
@PropertySources({
  @PropertySource("classpath:properties-test/encuestame-test-config.properties")
})
@ComponentScan({ "org.encuestame.business", "org.encuestame.config", "org.encuestame.core", "org.encuestame.security" })
public class BusinessConfig {
  
  @Autowired
  private Environment env;

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
    mailSender.setDefaultEncoding("UTF-8");
    mailSender.setHost(EnMePlaceHolderConfigurer.getProperty("mail.host"));
    mailSender.setPort(Integer.valueOf(EnMePlaceHolderConfigurer.getProperty("mail.port")));
    mailSender.setUsername(EnMePlaceHolderConfigurer.getProperty("mail.username"));
    mailSender.setPassword(EnMePlaceHolderConfigurer.getProperty("mail.password"));
    //http://www.tutorialspoint.com/javamail_api/javamail_api_smtp_servers.htm
    // List of properties, only these ones to connect with Gmail, it can be modified
    Properties properties = new Properties();
    if (EnMePlaceHolderConfigurer.getProperty("mail.protocol") != null) {
      properties.put("mail.transport.protocol", EnMePlaceHolderConfigurer.getProperty("mail.protocol"));
    }
    //gmail config default
    properties.put("mail.smtps.auth", true);
    properties.put("mail.smtps.debug", true);
    properties.put("mail.smtps.starttls.enable", true);
    mailSender.setJavaMailProperties(properties);
    return mailSender;
  }

  @Bean(name="helpsLinks")
  public List<String> stages() {
    return Lists.newArrayList("foo", "barr");
  }

  @Bean(name="templateMessage")
  public SimpleMailMessage templateMessage(){
    return new SimpleMailMessage();
  }

  @Bean(name="velocityEngine")
  public VelocityEngine getVelocityEngine() throws VelocityException, IOException{
    VelocityEngineFactory factory = new VelocityEngineFactory();
    Properties props = new Properties();
    props.put("resource.loader", "class");
    props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    factory.setVelocityProperties(props);
    return factory.createVelocityEngine();
  }

  /**
   *
   * @return
   */
  public @Bean(name="messageSourceFactoryBean") IMessageSource messageSourceFactoryBean(){
    return new MessageSourceFactoryBean(this.messageSource);
  }

  @Bean
  public ResourceBundleMessageSource configureResourceBundleMessageSource() {
    ResourceBundleMessageSource resource = new ResourceBundleMessageSource();
    resource.setBasename("messages");
    return resource;
  }

  /**
   *
   * @return
   */
  @Bean(name="applicationStartup")
  @Scope("singleton")
  public ApplicationStartup applicationStartup(){
    return new ApplicationStartup();
  }


  @Bean(name="databaseInstallTest")
  @Qualifier(value = "installDaoTest")
  @Scope("singleton")
  public InstallDatabaseOperations installDaoTest() {
    final BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
    dataSource.setUrl("jdbc:hsqldb:mem:encuestame_demo");
    dataSource.setUsername("sa");
    dataSource.setPassword("");
    final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    jdbcTemplate.setDataSource(dataSource);
    InstallerOperations installerOperations =  new InstallerDao(jdbcTemplate);
    InstallDatabaseOperations databaseInstall =  new DatabaseInstall();
    databaseInstall.setInstallerOperations(installerOperations);
    return databaseInstall;
  }

  @Bean
  public ReIndexJob reIndexJob() {
      return new ReIndexJob();
  }

  @Bean
  public IndexRebuilder indexRebuilder() {
    return new IndexRebuilder();
  }

  @Bean
  public CalculateHashTagSize calculateHashTagSize() {
    return new CalculateHashTagSize();
  }

  @Bean
  public CalculateRelevance calculateRelevance() {
    return new CalculateRelevance();
  }

  @Bean
  public RemoveUnconfirmedAccountJob removeUnconfirmedAccountJob() {
    return new RemoveUnconfirmedAccountJob();
  }

}
