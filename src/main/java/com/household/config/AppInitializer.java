package com.household.config;

import com.household.config.security.SecurityConfig;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Created by artemvlasov on 03/09/15.
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {
                AppConfig.class,
                SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {ServletContextConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/*"};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        return new Filter[] {
                characterEncodingFilter, new HttpPutFormContentFilter()
        };
    }
}
