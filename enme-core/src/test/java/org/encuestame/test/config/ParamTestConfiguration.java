//package org.encuestame.test.config;
//
//import org.encuestame.core.util.EnMePlaceHolderConfigurer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//
///**
// * Created by jpicado on 30/01/16.
// */
//@Configuration
//public class ParamTestConfiguration {
//
//
//    @Bean
//    public static EnMePlaceHolderConfigurer enMePlaceHolderConfigurer() {
//        EnMePlaceHolderConfigurer enMePlaceHolderConfigurer = new EnMePlaceHolderConfigurer();
//        final Resource[] list = new Resource[]{
//                new ClassPathResource("/org/encuestame/config/encuestame-config.properties"),
//                new ClassPathResource("properties-test/encuestame-test-config.properties"),
//                new ClassPathResource("properties-test/hibernate.test.properties"),
//                new ClassPathResource("properties-test/lucene.test.index.properties"),
//                new ClassPathResource("properties-test/test-email-config.properties")
//        };
//        enMePlaceHolderConfigurer.setLocations(list);
//        return enMePlaceHolderConfigurer;
//    }
//}
