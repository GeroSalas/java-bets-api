package com.gen.desafio.api.config;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;

import com.gen.desafio.api.config.security.SecurityConfig;
import com.gen.desafio.api.utils.APIConstants;


@Configuration
@EnableWebMvc
//@EnableCaching
@ComponentScan(basePackages = "com.gen.desafio.api")
@PropertySource(value = { "classpath:application.properties" })
@Import({ SecurityConfig.class, PersistenceJPAConfigurer.class })
public class WebAppConfig extends WebMvcConfigurerAdapter {
	
	
	@Bean
	public MessageSource messageSource() {
	    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	     messageSource.setBasename("messages");
	    return messageSource;
	}
	
	@Bean
    public ViewResolver viewResolver() {
        return new BeanNameViewResolver();
    }
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		System.out.println("Configuring Spring CORS support");
		registry.addMapping("/api/**")
			.allowedOrigins("*")
			.allowedMethods("GET", "PUT", "POST", "DELETE")
			.allowedHeaders("Origin", "Accept", "Content-Type", "X-Requested-With", "Authorization", "X-DesafioAPI-Key", "Access-Control-Request-Method", "Access-Control-Request-Headers")
			.exposedHeaders(APIConstants.X_AUTH_TOKEN)
			.allowCredentials(false).maxAge(3600);
	}
	
//	@Bean
//    public CacheManager cacheManager() {
//        SimpleCacheManager cacheManager = new SimpleCacheManager();
//         cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("fixture")));
//        return cacheManager;
//    }
	
	
}

