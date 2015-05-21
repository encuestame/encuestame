package org.encuestame.config.root;

import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
//@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@ImportResource({
	"classpath:/config/files/encrypt-context.xml", 
	"classpath:/config/files/data-context.xml"})
//@ComponentScan({ "org.encuestame.persistence.dao" })
public class EnMeData {


	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(EnMePlaceHolderConfigurer.getProperty("datasource.classname"));
		ds.setUsername(EnMePlaceHolderConfigurer.getProperty("datasource.userbd"));
		ds.setPassword(EnMePlaceHolderConfigurer.getProperty("datasource.pass"));
		ds.setUrl(EnMePlaceHolderConfigurer.getProperty("datasource.urldb"));
		return ds;
	}

	@Bean(name="sessionFactory")
    @Autowired
	public AnnotationSessionFactoryBean sessionFactory(DriverManagerDataSource driverManagerDataSource) throws  Exception{
        AnnotationSessionFactoryBean sessionFactory = new AnnotationSessionFactoryBean();
        sessionFactory.setDataSource(driverManagerDataSource);
        sessionFactory.setPackagesToScan(new String[] { "org.encuestame.persistence.domain"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.afterPropertiesSet();
		return sessionFactory;
	}

    /**
     *
     * @return
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", EnMePlaceHolderConfigurer.getProperty("datasource.hbm2ddl.auto"));
                setProperty("hibernate.dialect", EnMePlaceHolderConfigurer.getProperty("datasource.dialect"));
                setProperty("hibernate.show_sql", EnMePlaceHolderConfigurer.getProperty("datasource.showsql"));
                setProperty("hibernate.hbm2ddl.delimiter", EnMePlaceHolderConfigurer.getProperty("datasource.delimited"));
//                setProperty("hibernate.search.default.directory_provider", EnMePlaceHolderConfigurer.getProperty("hibernate.search.provider"));
                setProperty("hibernate.search.default.indexBase", EnMePlaceHolderConfigurer.getProperty("encuestame.home") + "/indexes/domain");
//                setProperty("hibernate.search.worker.buffer_queue.max", "100");
//                setProperty("hibernate.search.worker.execution", "async");
            }
        };
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

	@Bean
    @Autowired
	public HibernateTemplate hibernateTemplate(SessionFactory sessionFactory) {
		final HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
		hibernateTemplate.setCacheQueries(Boolean.TRUE);
		return hibernateTemplate;
	}

}
