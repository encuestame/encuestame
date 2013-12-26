package org.encuestame.config.annotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.encuestame.business.images.ImageThumbnailGeneratorImpl;
import org.encuestame.business.images.ThumbnailGenerator;
import org.encuestame.business.images.ThumbnailGeneratorEngineImpl;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.service.MessageSourceFactoryBean;
import org.encuestame.mvc.converter.EnhancedBufferedImageHttpMessageConverter;
import org.encuestame.mvc.interceptor.CheckInstallInterceptor;
import org.encuestame.mvc.interceptor.EnMeMobileInterceptor;
import org.encuestame.mvc.interceptor.EnMeSecurityInterceptor;
import org.encuestame.mvc.interceptor.Messagei18nInterceptor;
import org.encuestame.mvc.interceptor.SetupInterceptor;
import org.encuestame.mvc.interceptor.SignInInterceptor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;


@EnableWebMvc
@Configuration
public class EnMeMVC extends WebMvcConfigurerAdapter {
	

	/**
	 * 
	 */
	@Autowired
	private OpenSessionInViewInterceptor openSessionInViewInterceptor;
	
	@Autowired
	private EnMeMobileInterceptor enMeMobileInterceptor;
	
    /**
    *
    */
   @Autowired
   private MessageSource messageSource;

	
	@Autowired
	private CheckInstallInterceptor checkInstallInterceptor;
	
	@Autowired
	private EnMeSecurityInterceptor enMeSecurityInterceptor;

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
	 */
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry)
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) { 
		registry.addViewController("/").setViewName("home");
	}	
		
	/**
	 * 
	 * @return
	 */
	private WebContentInterceptor getWebContentInterceptor(){
		final WebContentInterceptor contentInterceptor = new WebContentInterceptor();
		contentInterceptor.setCacheSeconds(Integer.valueOf(EnMePlaceHolderConfigurer.getProperty("cache.period")));
		contentInterceptor.setUseExpiresHeader(Boolean.valueOf(EnMePlaceHolderConfigurer.getProperty("cache.useExpiresHeader")));
		contentInterceptor.setUseCacheControlHeader(Boolean.valueOf(EnMePlaceHolderConfigurer.getProperty("cache.useCacheControlHeader")));
		contentInterceptor.setUseCacheControlNoStore(Boolean.valueOf(EnMePlaceHolderConfigurer.getProperty("ache.useCacheControlNoStore")));
		return contentInterceptor;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new Messagei18nInterceptor()).excludePathPatterns("/resource/**").excludePathPatterns("/resources/**");;
		registry.addInterceptor(enMeMobileInterceptor).excludePathPatterns("/resource/**").excludePathPatterns("/resources/**");;
		registry.addInterceptor(this.enMeSecurityInterceptor).excludePathPatterns("/resource/**").excludePathPatterns("/resources/**");;
		registry.addInterceptor(new SetupInterceptor()).excludePathPatterns("/resource/**").excludePathPatterns("/resources/**");;
		registry.addInterceptor(new SignInInterceptor()).excludePathPatterns("/resource/**").excludePathPatterns("/resources/**");;
		registry.addInterceptor(this.checkInstallInterceptor).excludePathPatterns("/resource/**").excludePathPatterns("/resources/**");;
		registry.addInterceptor(getWebContentInterceptor()).excludePathPatterns("/resource/**").excludePathPatterns("/resources/**");;		
		registry.addWebRequestInterceptor(openSessionInViewInterceptor).excludePathPatterns("/resource/**").excludePathPatterns("/resources/**");
	}	
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#configureMessageConverters(java.util.List)
	 */
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//images
		final ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
		//final MediaType mediaType = new MediaType(MediaType.IMAGE_JPEG);
		//final MediaType jsonMediaType = new MediaType("application/json");
		final List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		supportedMediaTypes.add(MediaType.IMAGE_JPEG);
		arrayHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
			
		//json
		final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();		
		final List<MediaType> jsonSupportedMediaTypes = new ArrayList<MediaType>();
		jsonSupportedMediaTypes.add(MediaType.APPLICATION_JSON);
		jackson2HttpMessageConverter.setSupportedMediaTypes(jsonSupportedMediaTypes);
		jackson2HttpMessageConverter.setObjectMapper(new com.fasterxml.jackson.databind.ObjectMapper());

		//converters
		converters.add(arrayHttpMessageConverter);
		converters.add(new StringHttpMessageConverter());
		converters.add(new FormHttpMessageConverter());		
		converters.add(new SourceHttpMessageConverter());
		converters.add(new EnhancedBufferedImageHttpMessageConverter());	
		converters.add(jackson2HttpMessageConverter);
	}
	
	
 	@Override
 	public void addFormatters(FormatterRegistry formatterRegistry) {
 		//formatterRegistry.addConverter(new MyConverter());
 	}
 	
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {		
		
	}
 	
 	/**
 	 * 
 	 * @return
 	 */
 	@Bean(name="tilesResolver")
	public ViewResolver viewResolver() {
		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
		viewResolver.setViewClass(TilesView.class);
		return viewResolver;
	}
	
 	/**
 	 * 
 	 * @return
 	 */
// 	public @Bean(name="tilesResolver") TilesViewResolver tilesViewResolver() {
//        return new TilesViewResolver();
//    }

 	/**
 	 * 
 	 * @return
 	 */
    public @Bean(name="tilesConfigurer") TilesConfigurer tilesConfigurer() {
        TilesConfigurer ret = new TilesConfigurer();
        ret.setCheckRefresh(true);
        ret.setDefinitions(new String[] { "/WEB-INF/layouts/tiles.xml,/WEB-INF/views/**/tiles.xml" });
        return ret;
    }
        
    /**
     * 
     * @return
     */
    public @Bean(name="multipartResolver") CommonsMultipartResolver setMultiPart(){
    		final CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
    		commonsMultipartResolver.setMaxUploadSize(Integer.valueOf(EnMePlaceHolderConfigurer.getProperty("application.file.upload.limit")));
    		return commonsMultipartResolver;
    }
 	
    /**
     * 
     * @return
     */
    public @Bean(name="beanNameViewResolver") BeanNameViewResolver beanNameViewResolver(){
    	final BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();    
    	return beanNameViewResolver;
    }

    /**
     * 
     * @return
     */
    public @Bean(name="localeResolver") CookieLocaleResolver cookieLocaleResolver(){
    		final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
    		cookieLocaleResolver.setCookieName("enme-locale");
    		cookieLocaleResolver.setCookieMaxAge(30);
    		return cookieLocaleResolver;
    }
    
    /**
     * 
     * @return
     */
    public @Bean(name="jsonView") MappingJackson2JsonView jsonView(){
    	return new MappingJackson2JsonView();
    }
    
    @Lazy
    public @Bean(name="messageSourceFactoryBean") MessageSourceFactoryBean messageSourceFactoryBean(){
    	return new MessageSourceFactoryBean(this.messageSource);
    }
    
    /**
     * 
     * @return
     */
    public @Bean(name="thumbnailGeneratorEngine") ThumbnailGeneratorEngineImpl generatorEngineImpl(){
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
    	final Map<String, ThumbnailGenerator> map = new HashMap<String, ThumbnailGenerator>();
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
