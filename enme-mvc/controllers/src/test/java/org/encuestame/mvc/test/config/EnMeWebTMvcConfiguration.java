package org.encuestame.mvc.test.config;

import org.encuestame.business.cron.PublishScheduled;
import org.encuestame.business.cron.RemoveSpamCommentsJob;
import org.encuestame.business.cron.RemoveUnconfirmedAccountJob;
import org.encuestame.business.cron.SendNotificationsJob;
import org.encuestame.core.cron.CalculateHashTagSize;
import org.encuestame.core.cron.CalculateRelevance;
import org.encuestame.core.cron.IndexRebuilder;
import org.encuestame.core.cron.ReIndexJob;
import org.encuestame.mvc.interceptor.CheckInstallInterceptor;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


//@EnableWebMvc
@Configuration
//@EnableScheduling
//@ComponentScan(basePackages = "org.encuestame")
//@EnableAsync
@ImportResource({
    "classpath:/spring-test/encuestame-test-controller-context.xml"
})
public class EnMeWebTMvcConfiguration {


//	/**
//	 *
//	 */
//	@Autowired
//	private OpenSessionInViewInterceptor openSessionInViewInterceptor;
//
//	@Autowired
//	private EnMeMobileInterceptor enMeMobileInterceptor;
//
 
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
 
//
//
//	@Autowired
//	private CheckInstallInterceptor checkInstallInterceptor;
//
//	@Autowired
//	private EnMeSecurityInterceptor enMeSecurityInterceptor;
//
//	/*
//	 * (non-Javadoc)
//	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
//	 */
//	@Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
//    }
//
//	/*
//	 * (non-Javadoc)
//	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry)
//	 */
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/").setViewName("home");
//	}
//
//	/**
//	 *
//	 * @return
//	 */
//	private WebContentInterceptor getWebContentInterceptor(){
//		final WebContentInterceptor contentInterceptor = new WebContentInterceptor();
//		contentInterceptor.setCacheSeconds(Integer.valueOf(EnMePlaceHolderConfigurer.getProperty("cache.period")));
//		contentInterceptor.setUseExpiresHeader(Boolean.valueOf(EnMePlaceHolderConfigurer.getProperty("cache.useExpiresHeader")));
//		contentInterceptor.setUseCacheControlHeader(Boolean.valueOf(EnMePlaceHolderConfigurer.getProperty("cache.useCacheControlHeader")));
//		contentInterceptor.setUseCacheControlNoStore(Boolean.valueOf(EnMePlaceHolderConfigurer.getProperty("ache.useCacheControlNoStore")));
//		return contentInterceptor;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
//	 */
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new Messagei18nInterceptor()).excludePathPatterns("/resource/**").excludePathPatterns("/resources/**");;
//		registry.addInterceptor(enMeMobileInterceptor).excludePathPatterns("/resource/**").excludePathPatterns("/resources/**");;
//		registry.addInterceptor(this.enMeSecurityInterceptor).excludePathPatterns("/resource/**").excludePathPatterns("/resources/**");;
//		registry.addInterceptor(new SetupInterceptor()).excludePathPatterns("/resource/**").excludePathPatterns("/resources/**");;
//		registry.addInterceptor(new SignInInterceptor()).excludePathPatterns("/resource/**").excludePathPatterns("/resources/**");;
//		registry.addInterceptor(this.checkInstallInterceptor).excludePathPatterns("/resource/**").excludePathPatterns("/resources/**");;
//		registry.addInterceptor(getWebContentInterceptor()).excludePathPatterns("/resource/**").excludePathPatterns("/resources/**");;
//		registry.addWebRequestInterceptor(openSessionInViewInterceptor).excludePathPatterns("/resource/**").excludePathPatterns("/resources/**");
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#configureMessageConverters(java.util.List)
//	 */
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		//images
//		final ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
//		//final MediaType mediaType = new MediaType(MediaType.IMAGE_JPEG);
//		//final MediaType jsonMediaType = new MediaType("application/json");
//		final List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
//		supportedMediaTypes.add(MediaType.IMAGE_JPEG);
//		arrayHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
//
//		//json
//		final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//		final List<MediaType> jsonSupportedMediaTypes = new ArrayList<MediaType>();
//		jsonSupportedMediaTypes.add(MediaType.APPLICATION_JSON);
//		jackson2HttpMessageConverter.setSupportedMediaTypes(jsonSupportedMediaTypes);
//		jackson2HttpMessageConverter.setObjectMapper(new com.fasterxml.jackson.databind.ObjectMapper());
//
//		//converters
//		converters.add(arrayHttpMessageConverter);
//		converters.add(new StringHttpMessageConverter());
//		converters.add(new FormHttpMessageConverter());
//		converters.add(new SourceHttpMessageConverter());
//		converters.add(new EnhancedBufferedImageHttpMessageConverter());
//		converters.add(jackson2HttpMessageConverter);
//	}
//
//
// 	@Override
// 	public void addFormatters(FormatterRegistry formatterRegistry) {
// 		//formatterRegistry.addConverter(new MyConverter());
// 	}
//
//	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//
//	}
//
// 	/**
// 	 *
// 	 * @return
// 	 */
// 	@Bean(name="tilesResolver")
//	public ViewResolver viewResolver() {
//		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
//		viewResolver.setViewClass(TilesView.class);
//		return viewResolver;
//	}
//
// 	/**
// 	 *
// 	 * @return
// 	 */
//// 	public @Bean(name="tilesResolver") TilesViewResolver tilesViewResolver() {
////        return new TilesViewResolver();
////    }
//
// 	/**
// 	 *
// 	 * @return
// 	 */
//    public @Bean(name="tilesConfigurer") TilesConfigurer tilesConfigurer() {
//        TilesConfigurer ret = new TilesConfigurer();
//        ret.setCheckRefresh(true);
//        ret.setDefinitions(new String[] { "/WEB-INF/layouts/tiles.xml,/WEB-INF/views/**/tiles.xml" });
//        return ret;
//    }
//
//    /**
//     *
//     * @return
//     */
//    public @Bean(name="multipartResolver") CommonsMultipartResolver setMultiPart(){
//    		final CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
//    		commonsMultipartResolver.setMaxUploadSize(Integer.valueOf(EnMePlaceHolderConfigurer.getProperty("application.file.upload.limit")));
//    		return commonsMultipartResolver;
//    }
//
//    /**
//     *
//     * @return
//     */
//    public @Bean(name="beanNameViewResolver") BeanNameViewResolver beanNameViewResolver(){
//    	final BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
//    	return beanNameViewResolver;
//    }
//
//    /**
//     *
//     * @return
//     */
//    public @Bean(name="localeResolver") CookieLocaleResolver cookieLocaleResolver(){
//    		final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
//    		cookieLocaleResolver.setCookieName("enme-locale");
//    		cookieLocaleResolver.setCookieMaxAge(30);
//    		return cookieLocaleResolver;
//    }
//
//    /**
//     *
//     * @return
//     */
//    public @Bean(name="jsonView") MappingJackson2JsonView jsonView(){
//    	return new MappingJackson2JsonView();
//    }
//

//
//    /**
//     *
//     * @return
//     */
//    public @Bean(name="thumbnailGeneratorEngine") ThumbnailGeneratorEngineImpl generatorEngineImpl(){
//    	final ImageThumbnailGeneratorImpl generatorImpl = new ImageThumbnailGeneratorImpl();
//    	final ThumbnailGeneratorEngineImpl thumbnailGeneratorEngineImpl = new ThumbnailGeneratorEngineImpl();
//    	thumbnailGeneratorEngineImpl.setGeneratedExtension(".jpg");
//    	final List<Integer> supportedSizes = new ArrayList<Integer>();
//    	supportedSizes.add(900);
//    	supportedSizes.add(375);
//    	supportedSizes.add(256);
//    	supportedSizes.add(128);
//    	supportedSizes.add(64);
//    	supportedSizes.add(22);
//    	thumbnailGeneratorEngineImpl.setSupportedSizes(supportedSizes);
//    	final Map<String, ThumbnailGenerator> map = new HashMap<String, ThumbnailGenerator>();
//    	map.put("image/jpeg", generatorImpl);
//    	map.put("image/jpg", generatorImpl);
//    	map.put("image/pjpeg", generatorImpl);
//    	map.put("image/gif", generatorImpl);
//    	map.put("image/png", generatorImpl);
//    	map.put("image/tiff", generatorImpl);
//    	map.put("image/bmp", generatorImpl);
//    	thumbnailGeneratorEngineImpl.setThumbnailGenerators(map);
//    	thumbnailGeneratorEngineImpl.setDefaultThumbnailGenerator(generatorImpl);
//		return thumbnailGeneratorEngineImpl;
//    }
//
}
