package org.encuestame.mvc.test.config;

import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.encuestame.business.cron.PublishScheduled;
import org.encuestame.business.cron.RemoveSpamCommentsJob;
import org.encuestame.business.cron.RemoveUnconfirmedAccountJob;
import org.encuestame.business.cron.SendNotificationsJob;
import org.encuestame.business.images.ImageThumbnailGeneratorImpl;
import org.encuestame.business.images.ThumbnailGenerator;
import org.encuestame.business.images.ThumbnailGeneratorEngineImpl;
import org.encuestame.core.cron.CalculateHashTagSize;
import org.encuestame.core.cron.CalculateRelevance;
import org.encuestame.core.cron.IndexRebuilder;
import org.encuestame.core.cron.ReIndexJob;
import org.encuestame.mvc.JsonViewResolver;
import org.encuestame.mvc.converter.EnhancedBufferedImageHttpMessageConverter;
import org.encuestame.mvc.interceptor.CheckInstallInterceptor;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.orm.hibernate5.support.OpenSessionInViewInterceptor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import javax.xml.transform.Source;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"org.encuestame.rest"})
@ImportResource({
   "classpath:/spring-test/rest-api-v1.xml"
})
@EnableTransactionManagement
public class RestAPITestConfiguration extends WebMvcConfigurerAdapter{

    @Autowired
    private SessionFactory sessionFactory;
 
    /**
     *
     * @return
     * @throws EnMeExpcetion
     */
    public @Bean(name = "sendNotificationsJob")
    SendNotificationsJob sendNotificationsJob() throws EnMeExpcetion {
        return new SendNotificationsJob();
    }

    /**
     *
     * @return
     * @throws EnMeExpcetion
     */
    public @Bean(name = "calculateHashTagSize")
    CalculateHashTagSize calculateHashTagSize() throws EnMeExpcetion {
        return new CalculateHashTagSize();
    }

    /**
     *
     * @return
     * @throws EnMeExpcetion
     */
    public @Bean(name = "calculateRelevance")
    CalculateRelevance calculateRelevance() throws EnMeExpcetion {
        return new CalculateRelevance();
    }

    /**
     *
     * @return
     * @throws EnMeExpcetion
     */
    public @Bean(name = "removeAccountJob")
    RemoveUnconfirmedAccountJob removeUnconfirmedAccountJob()
            throws EnMeExpcetion {
        return new RemoveUnconfirmedAccountJob();
    }

    /**
     *
     * @return
     * @throws EnMeExpcetion
     */
    public @Bean(name = "removeSpamCommentJob")
    RemoveSpamCommentsJob removeSpamCommentsJob() throws EnMeExpcetion {
        return new RemoveSpamCommentsJob();
    }

    /**
     *
     * @return
     * @throws EnMeExpcetion
     */
    public @Bean(name = "publishScheduled")
    PublishScheduled publishScheduled() throws EnMeExpcetion {
        return new PublishScheduled();
    }

    /**
    *
    * @return
    */
   public @Bean(name="checkInstallInterceptor") CheckInstallInterceptor checkInstallInterceptor(){
       return new CheckInstallInterceptor();
   }

   public @Bean(name="indexRebuilder") IndexRebuilder indexRebuilder(){
       return new IndexRebuilder();
   }

    /**
     *
     * @return
     * @throws EnMeExpcetion
     */
    public @Bean(name = "reindexJob")
    ReIndexJob reIndexJob() throws EnMeExpcetion {
        ReIndexJob indexJob = new ReIndexJob();
        return indexJob;
    }

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer contentNegotiationConfigurer) {
        contentNegotiationConfigurer.ignoreAcceptHeader(true)
                .defaultContentType(MediaType.TEXT_HTML);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        OpenSessionInViewInterceptor i = new OpenSessionInViewInterceptor();
        i.setSessionFactory(sessionFactory);
        registry.addWebRequestInterceptor(i);
    }

    @Bean
    public ViewResolver contentNegotiationViewResolver(ContentNegotiationManager contentNegotiationManager) {
        ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
        contentNegotiatingViewResolver.setContentNegotiationManager(contentNegotiationManager);
        List<ViewResolver> resolvers = new ArrayList<>();
        resolvers.add(jsonViewResolver());
        contentNegotiatingViewResolver.setViewResolvers(resolvers);
        return contentNegotiatingViewResolver;
    }

    @Override
    public void configureMessageConverters( List<HttpMessageConverter<?>> converters ) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .dateFormat(new SimpleDateFormat("yyyy-MM-dd"))
                    .modulesToInstall(new ParameterNamesModule());
        //json
        final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(builder.build());
        final List<MediaType> jsonSupportedMediaTypes = new ArrayList<MediaType>();
        jsonSupportedMediaTypes.add(MediaType.APPLICATION_JSON);
        jackson2HttpMessageConverter.setSupportedMediaTypes(jsonSupportedMediaTypes);
        jackson2HttpMessageConverter.setObjectMapper(new com.fasterxml.jackson.databind.ObjectMapper());

        converters.add(jackson2HttpMessageConverter);
        converters.add(new StringHttpMessageConverter());
        converters.add(new FormHttpMessageConverter());
        ByteArrayHttpMessageConverter byteConverter = new ByteArrayHttpMessageConverter();
        List<MediaType> types = new ArrayList<>();
        types.add(MediaType.IMAGE_JPEG);
        types.add(MediaType.IMAGE_PNG);
        types.add(MediaType.IMAGE_GIF);
        byteConverter.setSupportedMediaTypes(types);
        converters.add(byteConverter);
        converters.add(new SourceHttpMessageConverter<Source>());
        converters.add(new EnhancedBufferedImageHttpMessageConverter());
    }

    /*
     * Configure View resolver to provide JSON output using JACKSON library to
     * convert object in JSON format.
     */
    @Bean
    public ViewResolver jsonViewResolver() {
        return new JsonViewResolver();
    }


    /**
     *
     * @return
     */
    public @Bean(name="thumbnailGeneratorEngine")
    ThumbnailGeneratorEngineImpl generatorEngineImpl(){
        final ImageThumbnailGeneratorImpl generatorImpl = new ImageThumbnailGeneratorImpl();
        final ThumbnailGeneratorEngineImpl thumbnailGeneratorEngineImpl = new ThumbnailGeneratorEngineImpl();
        thumbnailGeneratorEngineImpl.setGeneratedExtension(".jpg");
        final List<Integer> supportedSizes = new ArrayList<Integer>();
        supportedSizes.add(900);
        supportedSizes.add(375);
        supportedSizes.add(256);
        supportedSizes.add(128);
        supportedSizes.add(64);
        supportedSizes.add(22);
        thumbnailGeneratorEngineImpl.setSupportedSizes(supportedSizes);
        final Map<String, ThumbnailGenerator> map = new HashMap<>();
        map.put("image/jpeg", generatorImpl);
        map.put("image/jpg", generatorImpl);
        map.put("image/pjpeg", generatorImpl);
        map.put("image/gif", generatorImpl);
        map.put("image/png", generatorImpl);
        map.put("image/tiff", generatorImpl);
        map.put("image/bmp", generatorImpl);
        thumbnailGeneratorEngineImpl.setThumbnailGenerators(map);
        thumbnailGeneratorEngineImpl.setDefaultThumbnailGenerator(generatorImpl);
        return thumbnailGeneratorEngineImpl;
    }

}
