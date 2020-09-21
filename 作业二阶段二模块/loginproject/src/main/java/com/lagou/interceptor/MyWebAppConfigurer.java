package com.lagou.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

 
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter{
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor())
        .addPathPatterns("/**")
        .excludePathPatterns("/resume/index","/resume/login","/error","/js/**");
        super.addInterceptors(registry);
    }
}
