package com.gen.desafio.api.config;


import javax.servlet.Filter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import com.gen.desafio.api.utils.filters.UserInfoLoggingFilter;


public class SpringServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	
	@Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { WebAppConfig.class };
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
    	return null;
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };  // Spring Dispatcher Main Servlet mapping
    }
    
    @Override
    protected Filter[] getServletFilters() {
    	CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	     characterEncodingFilter.setEncoding("UTF-8");
	     characterEncodingFilter.setForceEncoding(true);
    	Filter [] singletons = { new UserInfoLoggingFilter(), characterEncodingFilter };
    	return singletons;
	}
	
}

