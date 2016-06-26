package org.encuestame.config.annotations;

import org.encuestame.mvc.interceptor.EnMeMobileInterceptor;
import org.encuestame.mvc.interceptor.EnMeSecurityInterceptor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.hibernate5.support.OpenSessionInViewInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
@ImportResource({"classpath:/org/encuestame/config/xml/service-context.xml"})
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

    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(new Locale("en"));
        return resolver;
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

    /**
     * List of paths where the help will be available
     * @return List
     */
    @Bean(name = "helpsLinks")
    public List<String> helpsPaths() {
        final List<String> paths = new ArrayList<String>();
        paths.add("/admon/members");
        paths.add("/user/tweetpoll/list");
        paths.add("/user/tweetpoll/new");
        paths.add("/user/poll/list");
        paths.add("/user/poll/new");
        paths.add("/settings/configuration");
        paths.add("/settings/social");
        return paths;
    }
}
