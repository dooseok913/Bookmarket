package com.springboot.config;

import com.springboot.interceptor.AuditingInterceptor;
import com.springboot.interceptor.MonitoringInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoggingConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MonitoringInterceptor());
        registry.addInterceptor(new AuditingInterceptor());
    }

}
