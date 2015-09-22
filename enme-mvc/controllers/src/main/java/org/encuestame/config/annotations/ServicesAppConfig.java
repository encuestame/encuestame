package org.encuestame.config.annotations;

import org.encuestame.mvc.interceptor.EnMeMobileInterceptor;
import org.encuestame.mvc.interceptor.EnMeSecurityInterceptor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.hibernate5.support.OpenSessionInViewInterceptor;
 
@Configuration
@ComponentScan(basePackages = "org.encuestame")
@ImportResource({"classpath:/config/files/service-context.xml"})
public class ServicesAppConfig {

    /**
     *
     */
    @Autowired
    private SessionFactory sessionFactory;

     

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
        //TODO: MIGRATION -->  openSessionInViewInterceptor.setSingleSession(true);
        return openSessionInViewInterceptor;
    }
}
