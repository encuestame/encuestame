package org.encuestame.config.annotations;

import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@ImportResource({
	"classpath:org/encuestame/config/files/encrypt-context.xml", 
	"classpath:org/encuestame/config/files/data-context.xml"})
public class EnMeData {

	/**
	 * 
	 */
	@Inject
	private Environment environment;

	/**
	 * 
	 * @return
	 */
	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(environment.getProperty("datasource.classname"));
		ds.setUsername(environment.getProperty("datasource.userbd"));
		ds.setPassword(environment.getProperty("datasource.pass"));
		ds.setUrl(environment.getProperty("datasource.urldb"));
		return ds;
	}

	/**
	 * 
	 * @return
	 */
	@Bean(name = "jdbcDataSource")
	public DataSource jdbcDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(environment.getProperty("datasource.classname"));
		ds.setUsername(environment.getProperty("datasource.userbd"));
		ds.setPassword(environment.getProperty("datasource.pass"));
		ds.setUrl(environment.getProperty("datasource.urldb"));
		return ds;
	}

	/**
	 * 
	 * @return
	 */
	@Bean(name = "sessionFactory")
	public SessionFactory sessionFactory() {
		AnnotationSessionFactoryBean factoryBean = null;
		try {
			factoryBean = new AnnotationSessionFactoryBean();
			Properties pp = new Properties();
			pp.setProperty("hibernate.hbm2ddl.auto",
					environment.getProperty("datasource.hbm2ddl.auto"));
			pp.setProperty("hibernate.dialect",
					environment.getProperty("datasource.dialect"));
			// pp.setProperty("hibernate.max_fetch_depth",
			// environment.getProperty("datasource.depth"));
			pp.setProperty("hibernate.show_sql",
					environment.getProperty("datasource.showsql"));
			pp.setProperty("hibernate.hbm2ddl.delimiter",
					environment.getProperty("datasource.delimited"));
			pp.setProperty("hibernate.search.default.directory_provider",
					environment.getProperty("hibernate.search.provider"));
			pp.setProperty("hibernate.search.default.indexBase", environment
					.getProperty("encuestame.home.hibernate.search.store.path"));
			pp.setProperty("hibernate.search.worker.buffer_queue.max", "100");
			pp.setProperty("hibernate.search.worker.execution", "async");
			pp.setProperty("hibernate.cache.region.factory_class",
					"net.sf.ehcache.hibernate.EhCacheRegionFactory");
			pp.setProperty("hibernate.cache.use_second_level_cache", "true");
			pp.setProperty("hibernate.cache.use_query_cache", "true");
			factoryBean.setDataSource(dataSource());
			factoryBean
					.setPackagesToScan(new String[] { "org.encuestame.persistence.domain.*" });
			factoryBean.setHibernateProperties(pp);
			factoryBean.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return factoryBean.getObject();
	}

	/**
	 * 
	 * @return
	 */
	@Bean(name = "transactionManager")
	public HibernateTransactionManager transactionManager() {
		return new HibernateTransactionManager(sessionFactory());
	}
		
	/**
	 * 
	 * @return
	 */
	@Bean(name = "hibernateTemplate")
	public HibernateTemplate hibernateTemplate() {
		final HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory());
		hibernateTemplate.setCacheQueries(Boolean.TRUE);
		return hibernateTemplate;
	}	
	
	/**
	 * 
	 * @return
	 */
	@Bean(name = "jdbcTemplate")
	public JdbcTemplate jdbcTemplate() {
		final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
		return jdbcTemplate;
	}	
	
//	@Bean(name = "environmentVariablesConfiguration")
//	public EnvironmentStringPBEConfig getEnvironmentVariablesConfiguration(){
//		final EnvironmentStringPBEConfig environmentStringPBEConfig = new EnvironmentStringPBEConfig();
//		environmentStringPBEConfig.setAlgorithm("spring.sec.encrypt.algorithm.key");
//		environmentStringPBEConfig.setPasswordEnvName(environment.getProperty("spring.sec.encrypt.password.key"));
//		return environmentStringPBEConfig;
//	}

}
