package com.xk;

import com.xk.common.filter.SecurityFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by hengxiaokang
 * Date:2018/6/22
 * Time:16:17
 */

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter
{

    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        registry.addViewController("/").setViewName("forward:/login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

    @Bean
    public FilterRegistrationBean filterRegistration()
    {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SecurityFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("MyFilter");
        registration.setOrder(1);
        return registration;
    }
}