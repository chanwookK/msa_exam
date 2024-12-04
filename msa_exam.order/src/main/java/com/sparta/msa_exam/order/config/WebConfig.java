package com.sparta.msa_exam.order.config;

import com.sparta.msa_exam.order.interceptor.AddPortHeaderInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final AddPortHeaderInterceptor addPortHeaderInterceptor;

    public WebConfig(AddPortHeaderInterceptor addPortHeaderInterceptor) {
        this.addPortHeaderInterceptor = addPortHeaderInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(addPortHeaderInterceptor);
    }
}
